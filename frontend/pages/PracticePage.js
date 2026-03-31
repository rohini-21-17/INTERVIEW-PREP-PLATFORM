import React, { useState, useEffect } from 'react';
import { questionsAPI, answersAPI } from '../services/api';
import './PracticePage.css';

const DIFFICULTIES = ['EASY', 'MEDIUM', 'HARD'];
const COUNTS = [1, 2, 3];


// ── Step 1: Topic selector ───────────────────────
function TopicSelector({ onStart }) {
  const [topics, setTopics] = useState([]);
  const [selected, setSelected] = useState('');
  const [custom, setCustom] = useState('');
  const [difficulty, setDifficulty] = useState('MEDIUM');
  const [count, setCount] = useState(5);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    questionsAPI.getAllTopics().then(r => setTopics(r.data)).catch(() => {});
  }, []);

  const topic = custom.trim() || selected;

  const handleStart = async () => {
    if (!topic) return;
    setLoading(true);
    try {
      const res = await questionsAPI.generate({ topic, difficulty, count });
      onStart(res.data, topic, difficulty);
    } catch (err) {
      alert(err.response?.data?.message || 'Failed to generate questions. Check your OpenAI key.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="practice-setup fade-in">
      <div className="setup-header">
        <h1>Start a session</h1>
        <p>Choose a topic and let AI craft your questions</p>
      </div>

      <div className="setup-card card">
        <div className="input-group">
          <label className="input-label">Topic</label>
          <div className="topic-grid">
            {topics.map(t => (
              <button
                key={t}
                className={`topic-chip ${selected === t && !custom ? 'active' : ''}`}
                onClick={() => { setSelected(t); setCustom(''); }}
              >
                {t}
              </button>
            ))}
          </div>
          <input
            className="input mt-4"
            placeholder="Or type a custom topic…"
            value={custom}
            onChange={e => { setCustom(e.target.value); setSelected(''); }}
          />
        </div>

        <div className="setup-row">
          <div className="input-group" style={{ flex: 1 }}>
            <label className="input-label">Difficulty</label>
            <div className="toggle-group">
              {DIFFICULTIES.map(d => (
                <button
                  key={d}
                  className={`toggle-btn ${difficulty === d ? `active-${d.toLowerCase()}` : ''}`}
                  onClick={() => setDifficulty(d)}
                >
                  {d}
                </button>
              ))}
            </div>
          </div>

          <div className="input-group" style={{ flex: 1 }}>
            <label className="input-label">Questions</label>
            <div className="toggle-group">
              {COUNTS.map(c => (
                <button
                  key={c}
                  className={`toggle-btn ${count === c ? 'active-count' : ''}`}
                  onClick={() => setCount(c)}
                >
                  {c}
                </button>
              ))}
            </div>
          </div>
        </div>

        <button
          className="btn btn-primary btn-full btn-lg"
          onClick={handleStart}
          disabled={!topic || loading}
        >
          {loading
            ? <><span className="spinner" /> Generating {count} questions…</>
            : `Generate ${count} ${difficulty.toLowerCase()} questions →`}
        </button>
      </div>
    </div>
  );
}

// ── Step 2: Question answering ───────────────────
function QuestionSession({ questions, topic, difficulty, onFinish }) {
  const [index, setIndex] = useState(0);
  const [answer, setAnswer] = useState('');
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [evaluated, setEvaluated] = useState(null);

  const current = questions[index];
  const isLast = index === questions.length - 1;

  const handleSubmit = async () => {
    if (!answer.trim()) return;
    setLoading(true);
    try {
      const res = await answersAPI.submit({ questionId: current.id, answerText: answer });
      setEvaluated(res.data);
    } catch (err) {
      alert(err.response?.data?.message || 'Failed to evaluate. Try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleNext = () => {
    const updated = [...results, evaluated];
    if (isLast) {
      onFinish(updated);
    } else {
      setResults(updated);
      setEvaluated(null);
      setAnswer('');
      setIndex(i => i + 1);
    }
  };

  const scoreClass = s => s >= 7 ? 'score-high' : s >= 4 ? 'score-mid' : 'score-low';

  return (
    <div className="session-wrap fade-in">
      <div className="session-progress">
        <span className="session-meta mono">
          {topic} · {difficulty} · {index + 1}/{questions.length}
        </span>
        <div className="progress-bar" style={{ flex: 1 }}>
          <div className="progress-fill" style={{ width: `${((index + 1) / questions.length) * 100}%` }} />
        </div>
      </div>

      <div className="card session-card">
        <div className="q-number">Q{index + 1}</div>
        <h2 className="q-text">{current.questionText}</h2>

        {!evaluated ? (
          <>
            <textarea
              className="input answer-area"
              placeholder="Type your answer here…"
              value={answer}
              onChange={e => setAnswer(e.target.value)}
              rows={6}
            />
            <button
              className="btn btn-primary btn-full"
              onClick={handleSubmit}
              disabled={!answer.trim() || loading}
            >
              {loading ? <><span className="spinner" /> Evaluating…</> : 'Submit answer'}
            </button>
          </>
        ) : (
          <div className="eval-result fade-in">
            <div className="eval-header">
              <div className={`score-ring ${scoreClass(evaluated.score)}`}>
                {evaluated.score}
              </div>
              <div>
                <div className="eval-score-label">Score</div>
                <div className="eval-score-text">
                  {evaluated.score >= 7 ? 'Great answer!' : evaluated.score >= 4 ? 'Good attempt' : 'Needs improvement'}
                </div>
              </div>
            </div>
            <div className="eval-feedback">
              <div className="eval-feedback-label">AI Feedback</div>
              <p>{evaluated.feedback}</p>
            </div>
            <button className="btn btn-primary btn-full" onClick={handleNext}>
              {isLast ? 'See results →' : 'Next question →'}
            </button>
          </div>
        )}
      </div>
    </div>
  );
}

// ── Step 3: Session results ──────────────────────
function SessionResults({ results, topic, onRestart }) {
  const avg = (results.reduce((s, r) => s + r.score, 0) / results.length).toFixed(1);
  const scoreClass = s => s >= 7 ? 'score-high' : s >= 4 ? 'score-mid' : 'score-low';

  return (
    <div className="results-wrap fade-in">
      <div className="results-header">
        <h1>Session complete 🎉</h1>
        <p>{topic} · {results.length} questions</p>
        <div className={`score-ring ${scoreClass(parseFloat(avg))}`} style={{ width: 96, height: 96, fontSize: 28, margin: '20px auto 0' }}>
          {avg}
        </div>
        <div className="results-avg-label">Average score</div>
      </div>

      <div className="results-list">
        {results.map((r, i) => (
          <div key={i} className="result-item card fade-in" style={{ animationDelay: `${i * 0.08}s` }}>
            <div className="result-item-header">
              <span className="mono" style={{ color: 'var(--muted)', fontSize: 12 }}>Q{i + 1}</span>
              <div className={`score-ring ${scoreClass(r.score)}`} style={{ width: 44, height: 44, fontSize: 14 }}>
                {r.score}
              </div>
            </div>
            <p className="result-q">{r.questionText}</p>
            <p className="result-feedback">{r.feedback}</p>
          </div>
        ))}
      </div>

      <div className="results-actions">
        <button className="btn btn-primary btn-lg" onClick={onRestart}>
          New session →
        </button>
      </div>
    </div>
  );
}

// ── Main page ────────────────────────────────────
export default function PracticePage() {
  const [stage, setStage] = useState('setup'); // setup | session | results
  const [questions, setQuestions] = useState([]);
  const [sessionMeta, setSessionMeta] = useState({});
  const [results, setResults] = useState([]);

  const handleStart = (qs, topic, difficulty) => {
    setQuestions(qs);
    setSessionMeta({ topic, difficulty });
    setStage('session');
  };

  const handleFinish = (res) => {
    setResults(res);
    setStage('results');
  };

  const handleRestart = () => {
    setStage('setup');
    setQuestions([]);
    setResults([]);
  };

  if (stage === 'session') return (
    <div className="practice-page">
      <div className="container">
        <QuestionSession
          questions={questions}
          topic={sessionMeta.topic}
          difficulty={sessionMeta.difficulty}
          onFinish={handleFinish}
        />
      </div>
    </div>
  );

  if (stage === 'results') return (
    <div className="practice-page">
      <div className="container">
        <SessionResults results={results} topic={sessionMeta.topic} onRestart={handleRestart} />
      </div>
    </div>
  );

  return (
    <div className="practice-page">
      <div className="container">
        <TopicSelector onStart={handleStart} />
      </div>
    </div>
  );
}