package com.kiev.msupport.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="income")
public class IncomeEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="`id`")
    Long id;

    @ManyToOne
    @JoinColumn(name="`mtr_id`", referencedColumnName = "`id`")
    MTREntity mtr;

    @Column(name="`amount`")
    String amount;

    @Column(name="`no_tax_price`")
    String price;

    @ManyToOne
    @JoinColumn(name="`dep_id`", referencedColumnName = "`id`")
    private DepartmentEntity department;

    @Column(name="`date`")
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "`manager_id`", referencedColumnName = "`id`")
    private Manager manager;

    public IncomeEntity() {
    }

    public IncomeEntity(DepartmentEntity department) {
        this.department = department;
    }

    public IncomeEntity(MTREntity mtr, String amount, String price, DepartmentEntity department, Date date, Manager manager) {
        this.mtr = mtr;
        this.amount = amount;
        this.price = price;
        this.department = department;
        this.date = date;
        this.manager = manager;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MTREntity getMtr() {
        return mtr;
    }

    public void setMtr(MTREntity mtr) {
        this.mtr = mtr;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
