import React, { useState, useEffect } from 'react';
import { TextField, Button, Box, Typography, Paper } from '@mui/material';
import expenseService from '../../services/expenseService';

const YearlyReport = () => {
  const [year, setYear] = useState(new Date().getFullYear());
  const [report, setReport] = useState(null);
  const [loading, setLoading] = useState(false);

  const fetchReport = async () => {
    setLoading(true);
    try {
      const response = await expenseService.getYearlyReport(year);
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
      <Typography variant="h6" gutterBottom>Yearly Report</Typography>
      <Paper sx={{ p: 2 }}>
        <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', gap: 2, mb: 2 }}>
          <TextField
            label="Year"
            type="number"
            value={year}
            onChange={(e) => setYear(parseInt(e.target.value))}
            inputProps={{ min: 2000, max: 2100 }}
          />
          <Button type="submit" variant="contained">Generate Report</Button>
        </Box>
        
        {loading && <Typography>Loading report...</Typography>}
        {report && (
          <Box>
            <Typography variant="body1">
              Total spent in {year}: <strong>${report.totalSpent.toFixed(2)}</strong>
            </Typography>
          </Box>
        )}
      </Paper>
    </Box>
  );
};

export default YearlyReport;