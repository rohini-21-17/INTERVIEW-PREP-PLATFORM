import axios from 'axios';

const API = axios.create({ baseURL: '/api' });

// Attach JWT to every request
API.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

// Auto-logout on 401
API.interceptors.response.use(
  res => res,
  err => {
    if (err.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(err);
  }
);

// ── Auth ──────────────────────────────────────────
export const authAPI = {
  register: (data) => API.post('/auth/register', data),
  login:    (data) => API.post('/auth/login', data),
};

// ── Questions ─────────────────────────────────────
export const questionsAPI = {
  generate:           (data)  => API.post('/questions/generate', data),
  getByTopic:         (topic) => API.get(`/questions/topic/${encodeURIComponent(topic)}`),
  getByTopicAndDiff:  (topic, diff) => API.get(`/questions/topic/${encodeURIComponent(topic)}/difficulty/${diff}`),
  getAllTopics:        ()      => API.get('/questions/topics'),
};

// ── Answers ───────────────────────────────────────
export const answersAPI = {
  submit:     (data) => API.post('/answers/submit', data),
  getHistory: ()     => API.get('/answers/history'),
};

// ── Dashboard ─────────────────────────────────────
export const dashboardAPI = {
  getStats: () => API.get('/dashboard/stats'),
};