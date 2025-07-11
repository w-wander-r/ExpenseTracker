import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { TextField, Button, Box, Typography, Alert } from '@mui/material';
import authService from '../../services/authService';

const Register = () => {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    try {
      await authService.register(data.username, data.password, 'USER');
      navigate('/auth', { state: { fromRegistration: true } });
    } catch (err) {
      setError('Registration failed. Username might be taken.');
    }
  };

  return (
    <Box component="form" onSubmit={handleSubmit(onSubmit)} sx={{ mt: 3 }}>
      <Typography variant="h5" gutterBottom>Register</Typography>
      {error && <Alert severity="error">{error}</Alert>}
      <TextField
        margin="normal"
        fullWidth
        label="Username"
        {...register('username', { required: 'Username is required' })}
        error={!!errors.username}
        helperText={errors.username?.message}
      />
      <TextField
        margin="normal"
        fullWidth
        label="Password"
        type="password"
        {...register('password', { required: 'Password is required' })}
        error={!!errors.password}
        helperText={errors.password?.message}
      />
      <Button
        type="submit"
        fullWidth
        variant="contained"
        sx={{ mt: 3, mb: 2 }}
      >
        Register
      </Button>
    </Box>
  );
};

export default Register;