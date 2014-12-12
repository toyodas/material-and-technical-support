package com.kiev.msupport.domain;

import javax.persistence.*;

@Entity
@Table(name="requested")
public class RequestEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="`id`")
    Long id;

    @ManyToOne
    @JoinColumn(name="`mtr_id`", referencedColumnName = "`id`")
    MTREntity mtr;

    @Column(name="`amount`")
    String amount;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="`dep_id`", referencedColumnName = "`id`")
    private DepartmentEntity department;

    @Column(name="`date`")
    private String date;

    @ManyToOne
    @JoinColumn(name = "`manager_id`", referencedColumnName = "`id`")
    private Manager manager;

    public RequestEntity() {
    }

    public RequestEntity(Long id, MTREntity mtr, String amount, DepartmentEntity department, String date, Manager manager) {
        this.id = id;
        this.mtr = mtr;
        this.amount = amount;
        this.department = department;
        this.date = date;
        this.manager = manager;
    }

    public RequestEntity(MTREntity mtr, String amount, DepartmentEntity department, String date, Manager manager) {
        this.mtr = mtr;
        this.amount = amount;
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

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
