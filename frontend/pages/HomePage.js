import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './HomePage.css';

const FEATURES = [
  { icon: '🤖', title: 'AI-Generated Questions', desc: 'Powered by GPT — fresh, relevant questions for every session.' },
  { icon: '📊', title: 'Instant Evaluation',     desc: 'Get scored and receive detailed feedback within seconds.' },
  { icon: '📈', title: 'Progress Tracking',      desc: 'See your growth across topics over time.' },
  { icon: '🎯', title: '10+ Topics',             desc: 'Java, DBMS, OS, DSA, System Design and more.' },
];

export default function HomePage() {
  const { isLoggedIn } = useAuth();

  return (
    <div className="home-page">
      <div className="hero-glow" />

      <div className="container">
        <section className="hero fade-in">
          <div className="hero-badge badge badge-accent">AI-Powered · Free to use</div>
          <h1 className="hero-title">
            Ace your next<br />
            <span className="hero-accent">tech interview</span>
          </h1>
          <p className="hero-sub">
            Practice with AI-generated questions, get instant scored feedback,
            and track your improvement — all in one place.
          </p>
          <div className="hero-cta">
            {isLoggedIn ? (
              <Link to="/practice" className="btn btn-primary btn-lg">Start practicing →</Link>
            ) : (
              <>
                <Link to="/register" className="btn btn-primary btn-lg">Get started — free</Link>
                <Link to="/login" className="btn btn-secondary btn-lg">Sign in</Link>
              </>
            )}
          </div>
        </section>

        <section className="features fade-in fade-in-d2">
          <div className="features-grid">
            {FEATURES.map((f, i) => (
              <div key={i} className="feature-card fade-in" style={{ animationDelay: `${0.1 + i * 0.08}s` }}>
                <div className="feature-icon">{f.icon}</div>
                <h3>{f.title}</h3>
                <p>{f.desc}</p>
              </div>
            ))}
          </div>
        </section>

        <section className="topics-section fade-in fade-in-d3">
          <h2>Topics covered</h2>
          <div className="topics-row">
            {['Java', 'Spring Boot', 'DBMS', 'OS', 'DSA', 'System Design', 'OOP', 'Networks', 'SQL', 'Algorithms'].map(t => (
              <span key={t} className="topic-pill">{t}</span>
            ))}
          </div>
        </section>
      </div>
    </div>
  );
}