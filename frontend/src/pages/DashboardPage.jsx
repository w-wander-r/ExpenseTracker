import React, { useState } from 'react';
import { Box, Typography, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import ExpenseList from '../components/Expenses/ExpenseList';
import AddExpense from '../components/Expenses/AddExpense';
import MonthlyReport from '../components/Expenses/MonthlyReport';
import authService from '../services/authService';

const DashboardPage = () => {
  const [expenses, setExpenses] = useState([]);
  const navigate = useNavigate();

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  const handleExpenseAdded = (newExpense) => {
    setExpenses([...expenses, newExpense]);
  };

  return (
    <Box sx={{ p: 3 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 3 }}>
        <Typography variant="h4">Expense Tracker</Typography>
        <Button onClick={handleLogout} variant="outlined">Logout</Button>
      </Box>
      
      <AddExpense onExpenseAdded={handleExpenseAdded} />
      <ExpenseList />
      <MonthlyReport />
    </Box>
  );
};

export default DashboardPage;