package com.rest.api.expensetrackerapi.controller;

import com.rest.api.expensetrackerapi.entity.Expense;
import com.rest.api.expensetrackerapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses(Pageable page){

        return service.getAllExpenses(page).toList();
    }


    @ResponseStatus(value= HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam Long id){
        //note if the variable in the url and the parameter are the same you dont need to put it in the string("id")
         service.deleteExpenseById(id);

    }

    @GetMapping("/expenses/{id}")
    public Expense getExpense(@PathVariable long id){
        return  service.getExpenseById(id);
    }

    @ResponseStatus(value= HttpStatus.CREATED)
    @PostMapping("/expenses")
    public  Expense saveExpenseDetails(@Valid @RequestBody Expense expense){
      return  service.saveExpenseDetails(expense);
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpenseDetails(@RequestBody Expense expense, @PathVariable long id){
        return service.updateExpenseDetails(id, expense);
    }

    @GetMapping("/expenses/category")
    public List<Expense> getExpenseByCategory(@RequestParam String category, Pageable page){
        return service.readByCategory(category, page);
    }

    @GetMapping("/expenses/name")
    public List<Expense> getExpenseByNameContaining(@RequestParam String keyword, Pageable page){
        return service.readByName(keyword, page);
    }

    @GetMapping("/expenses/date")
    public List<Expense> getExpenseByDate(@RequestParam(required = false)Date startDate,
                                          @RequestParam(required = false)Date endDate, Pageable page){
        return  service.readByDate(startDate, endDate, page);
    }

}
