package com.rest.api.expensetrackerapi.service;

import com.rest.api.expensetrackerapi.entity.Expense;
import com.rest.api.expensetrackerapi.error.ResoureNotFoundException;
import com.rest.api.expensetrackerapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseRepository expenseRepo;


    @Override
    public Page<Expense> getAllExpenses(Pageable page) {

        return expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepo.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
        if (expense.isPresent()){
            return expense.get();
        }
        throw new ResoureNotFoundException("Expense is not found for the id " +id);
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense expense = getExpenseById(id);
        expenseRepo.delete(expense);
    }

    @Override
    public Expense saveExpenseDetails(Expense expense) {
        expense.setUser(userService.getLoggedInUser());
        return expenseRepo.save(expense);
    }

    @Override
    public Expense updateExpenseDetails(Long id, Expense expense) {
        Expense exitingExpense = getExpenseById(id);

        exitingExpense.setName(expense.getName() != null ? expense.getName() : exitingExpense.getName());
        exitingExpense.setDescription(expense.getDescription() != null ? expense.getDescription(): exitingExpense.getDescription());
        exitingExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : exitingExpense.getCategory());
        exitingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : exitingExpense.getAmount());
        exitingExpense.setDate(expense.getDate()!= null ? expense.getDate() : exitingExpense.getDate());

        return  expenseRepo.save(exitingExpense);

    }

    @Override
    public List<Expense> readByCategory(String category, Pageable page) {
        return expenseRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, page).toList();
    }

    @Override
    public List<Expense> readByName(String keyword, Pageable page) {
        return expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), keyword, page).toList();
    }

    @Override
    public List<Expense> readByDate(Date startDate, Date endDate, Pageable page) {
        if(startDate==null){
            startDate = new Date(0); //0 means it will use the start date
        }
        if (endDate==null){
            endDate= new Date(System.currentTimeMillis()); //get todays date or current date
        }
        return expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDate, page).toList();
    }
}
