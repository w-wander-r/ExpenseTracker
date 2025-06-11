import axios from 'axios';

const API_URL = 'http://localhost:8090/api/expenses';

const getExpenses = () => {
  const user = JSON.parse(localStorage.getItem('user'));
  return axios.get(API_URL, {
    headers: {
      Authorization: 'Bearer ' + user.accessToken
    }
  });
};

const addExpense = (description, amount, date) => {
  const user = JSON.parse(localStorage.getItem('user'));
  return axios.post(API_URL, {
    description,
    amount,
    date
  }, {
    headers: {
      Authorization: 'Bearer ' + user.accessToken
    }
  });
};

const deleteExpense = (id) => {
  const user = JSON.parse(localStorage.getItem('user'));
  return axios.delete(`${API_URL}/${id}`, {
    headers: {
      Authorization: 'Bearer ' + user.accessToken
    }
  });
};

const getMonthlyReport = (year, month) => {
  const user = JSON.parse(localStorage.getItem('user'));
  return axios.get(`${API_URL}/report`, {
    params: { year, month },
    headers: {
      Authorization: 'Bearer ' + user.accessToken
    }
  });
};

export default {
  getExpenses,
  addExpense,
  deleteExpense,
  getMonthlyReport
};