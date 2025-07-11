import React, { useState, useEffect } from 'react';
import { Tabs, Tab, Box, Alert } from '@mui/material';
import Login from '../components/Auth/Login';
import Register from '../components/Auth/Register';
import { useLocation, useNavigate } from 'react-router-dom';

const AuthPage = ({ initialTab = 0 }) => {
  const [activeTab, setActiveTab] = useState(initialTab);
  const location = useLocation();
  const navigate = useNavigate();

  // Sync tab with route
  useEffect(() => {
    if (location.pathname.includes('register')) {
      setActiveTab(1);
    } else {
      setActiveTab(0);
    }
  }, [location.pathname]);

  const handleTabChange = (event, newValue) => {
    setActiveTab(newValue);
    navigate(newValue === 0 ? '/auth/login' : '/auth/register');
  };

  return (
    <Box sx={{ maxWidth: 400, mx: 'auto', mt: 4 }}>
      <Tabs value={activeTab} onChange={handleTabChange} centered>
        <Tab label="Login" />
        <Tab label="Register" />
      </Tabs>
      <Box sx={{ p: 2 }}>
        {location.state?.fromRegistration && (
          <Alert severity="success" sx={{ mb: 2 }}>
            Registration successful! Please login.
          </Alert>
        )}
        {activeTab === 0 ? <Login /> : <Register />}
      </Box>
    </Box>
  );
};

export default AuthPage;