import React, { useState, useEffect } from 'react';
import { TextField, Button, Box, Typography, Paper } from '@mui/material';
import expenseService from '../../services/expenseService';

const MonthlyReport = () => {
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [report, setReport] = useState(null);
  const [loading, setLoading] = useState(false);

  const fetchReport = async () => {
    setLoading(true);
    try {
      const response = await expenseService.getMonthlyReport(year, month);
      setReport(response.data);
    } catch (error) {
      console.error('Error fetching report:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchReport();
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    fetchReport();
  };

  return (
    <Box sx={{ mt: 3 }}>
      <Typography variant="h6" gutterBottom>Monthly Report</Typography>
      <Paper sx={{ p: 2 }}>
        <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', gap: 2, mb: 2 }}>
          <TextField
            label="Year"
            type="number"
            value={year}
            onChange={(e) => setYear(parseInt(e.target.value))}
            inputProps={{ min: 2000, max: 2100 }}
          />
          <TextField
            label="Month"
            type="number"
            value={month}
            onChange={(e) => setMonth(parseInt(e.target.value))}
            inputProps={{ min: 1, max: 12 }}
          />
          <Button type="submit" variant="contained">Generate Report</Button>
        </Box>
        
        {loading && <Typography>Loading report...</Typography>}
        {report && (
          <Box>
            <Typography variant="body1">
              Total spent in {month}/{year}: <strong>${report.totalSpent.toFixed(2)}</strong>
            </Typography>
          </Box>
        )}
      </Paper>
    </Box>
  );
};

export default MonthlyReport;