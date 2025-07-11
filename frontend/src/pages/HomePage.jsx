import React from 'react';
import { Box, Typography, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const HomePage = () => {
  const navigate = useNavigate();

  return (
    <Box sx={{ textAlign: 'center', mt: 10 }}>
      <Typography variant="h3" gutterBottom>Welcome to Expense Tracker</Typography>
      <Typography variant="body1" gutterBottom>
        Track your expenses and get monthly reports
      </Typography>
      <Box sx={{ mt: 4 }}>
        <Button 
          variant="contained" 
          size="large"
          onClick={() => navigate('/auth/login')}
        >
          Get Started
        </Button>
      </Box>
    </Box>
  );
};

export default HomePage;