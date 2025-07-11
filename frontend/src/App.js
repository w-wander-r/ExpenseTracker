import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, useLocation } from 'react-router-dom';
import HomePage from './pages/HomePage';
import AuthPage from './pages/AuthPage';
import DashboardPage from './pages/DashboardPage';
import authService from './services/authService';

const App = () => {
  const PrivateRoute = ({ children }) => {
    const currentUser = authService.getCurrentUser();
    return currentUser?.accessToken ? children : <Navigate to="/auth/login" />;
  };

  // This wrapper helps with initial tab selection based on route
  const AuthPageWrapper = () => {
    const location = useLocation();
    const initialTab = location.pathname.includes('register') ? 1 : 0;
    return <AuthPage initialTab={initialTab} />;
  };

  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        
        {/* Consolidated auth routes */}
        <Route path="/auth">
          <Route path="login" element={<AuthPageWrapper />} />
          <Route path="register" element={<AuthPageWrapper />} />
          <Route index element={<Navigate to="/auth/login" />} />
        </Route>
        
        {/* Backward compatible routes */}
        <Route path="/login" element={<Navigate to="/auth/login" />} />
        <Route path="/register" element={<Navigate to="/auth/register" />} />
        
        <Route 
          path="/dashboard" 
          element={
            <PrivateRoute>
              <DashboardPage />
            </PrivateRoute>
          } 
        />
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </Router>
  );
};

export default App;