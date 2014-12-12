package com.kiev.msupport.domain;

import javax.persistence.*;

@Entity
@Table(name = "material")
public class MaterialEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "`id`")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="`mtr_id`", referencedColumnName = "`id`")
    private MTREntity mtr;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="`dep_id`", referencedColumnName = "`id`")
    private DepartmentEntity department;

    @Column(name = "`t_residue`")
    private String residueForToday;

    @Column(name = "`p_residue`")
    private String residueForPeriod;

    @Column(name = "`fprice`")
    private String fullPrice;

    @Column(name = "`demand`")
    private String demand;

    @Column(name = "`m_income`")
    private String monthIncome;

    @Column(name = "`last_updated`")
    private String lastUpdated;



    public MaterialEntity(Long id, MTREntity mtr, DepartmentEntity department, String residueForToday, String residueForPeriod, String fullPrice, String demand, String monthIncome) {
        this.id = id;
        this.mtr = mtr;
        this.department = department;
        this.residueForToday = residueForToday;
        this.residueForPeriod = residueForPeriod;
        this.fullPrice = fullPrice;
        this.demand = demand;
        this.monthIncome = monthIncome;
    }

    public MaterialEntity(MTREntity mtr, DepartmentEntity department, String residueForToday, String residueForPeriod, String fullPrice, String demand, String monthIncome) {
        this.mtr = mtr;
        this.department = department;
        this.residueForToday = residueForToday;
        this.residueForPeriod = residueForPeriod;
        this.fullPrice = fullPrice;
        this.demand = demand;
        this.monthIncome = monthIncome;
    }

    public MaterialEntity(Long id, MTREntity mtr, DepartmentEntity department, String residueForToday, String residueForPeriod, String fullPrice, String demand, String monthIncome, String lastUpdated) {
        this.id = id;
        this.mtr = mtr;
        this.department = department;
        this.residueForToday = residueForToday;
        this.residueForPeriod = residueForPeriod;
        this.fullPrice = fullPrice;
        this.demand = demand;
        this.monthIncome = monthIncome;
        this.lastUpdated = lastUpdated;
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

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public String getResidueForToday() {
        return residueForToday;
    }

    public void setResidueForToday(String residueForToday) {
        this.residueForToday = residueForToday;
    }

    public String getResidueForPeriod() {
        return residueForPeriod;
    }

    public void setResidueForPeriod(String residueForPeriod) {
        this.residueForPeriod = residueForPeriod;
    }

    public String getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(String fullPrice) {
        this.fullPrice = fullPrice;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(String monthIncome) {
        this.monthIncome = monthIncome;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
