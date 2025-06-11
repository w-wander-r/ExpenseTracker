import React, { useState } from 'react';
import { Tabs, Tab, Box } from '@mui/material';
import Login from '../components/Auth/Login';
import Register from '../components/Auth/Register';

const AuthPage = () => {
  const [activeTab, setActiveTab] = useState(0);

  const handleTabChange = (event, newValue) => {
    setActiveTab(newValue);
  };

  return (
    <Box sx={{ maxWidth: 400, mx: 'auto', mt: 4 }}>
      <Tabs value={activeTab} onChange={handleTabChange} centered>
        <Tab label="Login" />
        <Tab label="Register" />
      </Tabs>
      <Box sx={{ p: 2 }}>
        {activeTab === 0 ? <Login /> : <Register />}
      </Box>
    </Box>
  );
};

export default AuthPage;