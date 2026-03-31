import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { dashboardAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';
import './DashboardPage.css';

function StatCard({ label, value, sub, accent }) {
  return (
    <div className={`stat-card fade-in ${accent ? 'stat-card-accent' : ''}`}>
      <div className="stat-value">{value}</div>
      <div className="stat-label">{label}</div>
      {sub && <div className="stat-sub">{sub}</div>}
    </div>
  );
}

function TopicBar({ topic, attempts, avgScore }) {
  const pct = Math.min(100, (avgScore / 10) * 100);
  const cls = avgScore >= 7 ? 'high' : avgScore >= 4 ? 'mid' : 'low';
  return (
    <div className="topic-bar">
      <div className="topic-bar-header">
        <span className="topic-bar-name">{topic}</span>
        <span className={`topic-score score-text-${cls}`}>{avgScore.toFixed(1)}/10</span>
      </div>
      <div className="progress-bar">
        <div className={`progress-fill fill-${cls}`} style={{ width: `${pct}%` }} />
      </div>
      <div className="topic-bar-sub">{attempts} attempt{attempts !== 1 ? 's' : ''}</div>
    </div>
  );
}

export default function DashboardPage() {
  const { user } = useAuth();
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    dashboardAPI.getStats()
      .then(r => setStats(r.data))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  if (loading) return (
    <div className="page-loading">
      <span className="spinner" style={{ width: 32, height: 32, borderWidth: 3 }} />
    </div>
  );

  return (
    <div className="dashboard-page">
      <div className="container">
        <div className="dash-hero fade-in">
          <div>
            <h1>Hey, <span className="accent-text">{user?.username}</span> 👋</h1>
            <p className="dash-subtitle">Here's your interview preparation overview</p>
          </div>
          <Link to="/practice" className="btn btn-primary btn-lg">
            Start Practicing →
          </Link>
        </div>

        {!stats || stats.totalAttempts === 0 ? (
          <div className="empty-state fade-in fade-in-d1">
            <div className="empty-icon">🎯</div>
            <h3>No attempts yet</h3>
            <p>Start practicing to see your performance stats here.</p>
            <Link to="/practice" className="btn btn-primary mt-4">Go to Practice</Link>
          </div>
        ) : (
          <>
            <div className="stat-grid">
              <StatCard label="Total Attempts" value={stats.totalAttempts} accent />
              <StatCard label="Average Score" value={`${stats.averageScore}/10`} />
              <StatCard label="Highest Score" value={`${stats.highestScore}/10`} />
              <StatCard label="Strongest Topic" value={stats.strongestTopic} sub="Best performing" />
              <StatCard label="Needs Work" value={stats.weakestTopic} sub="Focus here" />
            </div>

            {stats.topicBreakdown?.length > 0 && (
              <div className="card fade-in fade-in-d2" style={{ marginTop: 32 }}>
                <h2 className="section-title">Performance by topic</h2>
                <div className="topic-bars">
                  {stats.topicBreakdown.map(t => (
                    <TopicBar key={t.topicName} topic={t.topicName}
                      attempts={t.attempts} avgScore={t.avgScore} />
                  ))}
                </div>
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
}