import React, { useEffect, useState } from 'react';
import { answersAPI } from '../services/api';
import './HistoryPage.css';

export default function HistoryPage() {
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState('');

  useEffect(() => {
    answersAPI.getHistory()
      .then(r => setHistory(r.data))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  const scoreClass = s => s >= 7 ? 'score-high' : s >= 4 ? 'score-mid' : 'score-low';
  const badgeClass = s => s >= 7 ? 'badge-success' : s >= 4 ? 'badge-warning' : 'badge-danger';

  const topics = [...new Set(history.map(h => h.topicName))];
  const filtered = filter ? history.filter(h => h.topicName === filter) : history;

  const fmt = iso => new Date(iso).toLocaleDateString('en-IN', {
    day: 'numeric', month: 'short', year: 'numeric',
    hour: '2-digit', minute: '2-digit'
  });

  if (loading) return (
    <div className="page-loading">
      <span className="spinner" style={{ width: 32, height: 32, borderWidth: 3 }} />
    </div>
  );

  return (
    <div className="history-page">
      <div className="container">
        <div className="history-header fade-in">
          <div>
            <h1>Answer history</h1>
            <p>{history.length} total attempt{history.length !== 1 ? 's' : ''}</p>
          </div>
          {topics.length > 1 && (
            <div className="topic-filter">
              <button
                className={`filter-chip ${!filter ? 'active' : ''}`}
                onClick={() => setFilter('')}
              >All</button>
              {topics.map(t => (
                <button
                  key={t}
                  className={`filter-chip ${filter === t ? 'active' : ''}`}
                  onClick={() => setFilter(t)}
                >
                  {t}
                </button>
              ))}
            </div>
          )}
        </div>

        {filtered.length === 0 ? (
          <div className="empty-state fade-in">
            <div className="empty-icon">📋</div>
            <h3>No history yet</h3>
            <p>Your answer history will appear here after you practice.</p>
          </div>
        ) : (
          <div className="history-list">
            {filtered.map((item, i) => (
              <div key={item.answerId} className="history-item card fade-in"
                style={{ animationDelay: `${Math.min(i, 10) * 0.05}s` }}>
                <div className="history-item-left">
                  <div className={`score-ring ${scoreClass(item.score)}`}>{item.score}</div>
                  <div className="history-item-body">
                    <div className="history-item-meta">
                      <span className="badge badge-accent">{item.topicName}</span>
                      <span className={`badge ${badgeClass(item.score)}`}>
                        {item.score >= 7 ? 'Strong' : item.score >= 4 ? 'Okay' : 'Weak'}
                      </span>
                    </div>
                    <p className="history-q">{item.questionText}</p>
                    <p className="history-feedback">{item.feedback}</p>
                    <span className="history-date">{fmt(item.submittedAt)}</span>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}