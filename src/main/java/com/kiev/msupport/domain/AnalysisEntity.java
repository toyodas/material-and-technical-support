package com.kiev.msupport.domain;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name="analysis")
public class AnalysisEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="`id`")
    private Long id;

    @Column(name="`date`")
    private Date date;

    @JoinColumn(name="`manager_id`", referencedColumnName = "`id`")
    private Manager manager;

    @Lob
    @Column(name="`screenshot`")
    private Blob screenShot;


    @JoinColumn(name="`cat_id`", referencedColumnName = "`id`")
    private CategoryEntity category;

    @Column(name="`price`")
    private String fullPrice;

    public AnalysisEntity(Date date, Manager manager, Blob screenShot, CategoryEntity category, String fullPrice) {
        this.date = date;
        this.manager = manager;
        this.screenShot = screenShot;
        this.category = category;
        this.fullPrice = fullPrice;
    }

    public AnalysisEntity(Long id, Date date, Manager manager, Blob screenShot, CategoryEntity category, String fullPrice) {
        this.id = id;
        this.date = date;
        this.manager = manager;
        this.screenShot = screenShot;
        this.category = category;
        this.fullPrice = fullPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Blob getScreenShot() {
        return screenShot;
    }

    public void setScreenShot(Blob screenShot) {
        this.screenShot = screenShot;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(String fullPrice) {
        this.fullPrice = fullPrice;
    }
}
