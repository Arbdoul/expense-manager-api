package com.rest.api.expensetrackerapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tbl_expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="expense_name")
    @NotBlank(message = "Expense name must not be blank")
    @Size(min = 3, message = "Expense name must be atleast 3 charaters")
    private String name;

    private String description;

    @Column(name="expense_amount")
    @NotNull(message = "Expense amount must not be null")
    private BigDecimal amount;

    @NotBlank(message = "category should not be null")
    private String category;

    @NotNull(message = "must not be null")
    private Date date;

    @Column(name = "createdAt", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name= "updatedAt")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false )
    @OnDelete(action = OnDeleteAction.CASCADE) //on deleting the user it will delete the expense as well
    @JsonIgnore //hide the user
    private User user;

}
