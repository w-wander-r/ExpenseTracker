import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { TextField, Button, Box, Typography, Alert } from '@mui/material';
import expenseService from '../../services/expenseService';

const AddExpense = ({ onExpenseAdded }) => {
  const { register, handleSubmit, reset, formState: { errors } } = useForm();
  const [error, setError] = useState('');

  const onSubmit = async (data) => {
    try {
      const formattedDate = new Date(data.date).toISOString().split('T')[0];
      
      const response = await expenseService.addExpense(
        data.description,
        parseFloat(data.amount),
        formattedDate
      );
      onExpenseAdded(response.data);
      reset();
    } catch (err) {
      console.error('Error adding expense:', err); // Add logging
      setError(err.response?.data?.message || 'Failed to add expense');
    }
  };

  return (
    <Box component="form" onSubmit={handleSubmit(onSubmit)} sx={{ mt: 3, mb: 3 }}>
      <Typography variant="h6" gutterBottom>Add New Expense</Typography>
      {error && <Alert severity="error">{error}</Alert>}
      <TextField
        margin="normal"
        fullWidth
        label="Description"
        {...register('description', { required: 'Description is required' })}
        error={!!errors.description}
        helperText={errors.description?.message}
      />
      <TextField
        margin="normal"
        fullWidth
        label="Amount"
        type="number"
        inputProps={{ step: "0.01" }}
        {...register('amount', { 
          required: 'Amount is required',
          min: { value: 0.01, message: 'Amount must be positive' }
        })}
        error={!!errors.amount}
        helperText={errors.amount?.message}
      />
      <TextField
        margin="normal"
        fullWidth
        label="Date"
        type="date"
        InputLabelProps={{ shrink: true }}
        {...register('date', { required: 'Date is required' })}
        error={!!errors.date}
        helperText={errors.date?.message}
      />
      <Button
        type="submit"
        fullWidth
        variant="contained"
        sx={{ mt: 2 }}
      >
        Add Expense
      </Button>
    </Box>
  );
};

export default AddExpense;