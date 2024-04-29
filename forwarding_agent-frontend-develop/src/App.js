import * as React from 'react';
import { Routes, Route } from 'react-router-dom';

import WelcomePage from './pages/WelcomePage';
import ErrorPage from './pages/ErrorPage';

export default function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<WelcomePage />} />
        <Route path="/server-error" element={<ErrorPage />} />
      </Routes>
    </div>
  );
}