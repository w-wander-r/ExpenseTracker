package com.wander.ExpenseTracker.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wander.ExpenseTracker.model.Expense;
import com.wander.ExpenseTracker.model.ExpenseDTO;
import com.wander.ExpenseTracker.model.User;
import com.wander.ExpenseTracker.repo.ExpenseRepo;
import com.wander.ExpenseTracker.repo.UserRepo;

class ExpenseControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private ExpenseRepo expenseRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private ExpenseController expenseController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();
    }

    @Test
    void getAllExpenses_ShouldReturnEmptyList() throws Exception {
        User user = new User();
        user.setUsername("testuser");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testuser");
        when(userRepo.findByUsername("testuser")).thenReturn(java.util.Optional.of(user));
        when(expenseRepo.findByUser(user)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/expenses").principal(auth))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void addExpense_ShouldReturnSavedExpense() throws Exception {
        User user = new User();
        user.setUsername("testuser");

        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setDescription("Test expense");
        expenseDTO.setAmount(new BigDecimal("10.00"));
        expenseDTO.setDate(LocalDate.now());

        Expense savedExpense = new Expense();
        savedExpense.setId(1L);
        savedExpense.setDescription(expenseDTO.getDescription());
        savedExpense.setAmount(expenseDTO.getAmount());
        savedExpense.setDate(expenseDTO.getDate());
        savedExpense.setUser(user);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testuser");
        when(userRepo.findByUsername("testuser")).thenReturn(java.util.Optional.of(user));
        when(expenseRepo.save(any(Expense.class))).thenReturn(savedExpense);

        mockMvc.perform(post("/api/expenses")
                        .principal(auth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expenseDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Test expense"));
    }

    @Test
    void getMonthlyReport_ShouldReturnTotal() throws Exception {
        User user = new User();
        user.setUsername("testuser");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testuser");
        when(userRepo.findByUsername("testuser")).thenReturn(java.util.Optional.of(user));
        when(expenseRepo.findByUserAndDateBetween(eq(user), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/expenses/report")
                        .principal(auth)
                        .param("year", "2023")
                        .param("month", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSpent").value(0));
    }
}