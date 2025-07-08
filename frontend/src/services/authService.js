import axios from 'axios';

const API_URL = 'http://localhost:8090/api/auth';

const register = (username, password, role = 'USER') => {
  return axios.post(API_URL + '/register', {
    username,
    password,
    role
  });
};

const login = async (username, password) => {
  try {
    const response = await axios.post(API_URL + '/login', {
      username,
      password
    });
    
    if (response.data.accessToken) {
      localStorage.setItem('user', JSON.stringify(response.data));
      return response.data;
    }
    throw new Error('Login failed - no token received');
  } catch (error) {
    console.error('Login error:', error.response?.data || error.message);
    throw error;
  }
};

const logout = () => {
  localStorage.removeItem('user');
};

const getCurrentUser = () => {
  const user = localStorage.getItem('user');
  return user ? JSON.parse(user) : null;
};

export default {
  register,
  login,
  logout,
  getCurrentUser
};