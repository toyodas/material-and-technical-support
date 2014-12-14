package com.kiev.msupport.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "analysis")
public class AnalysisEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "`id`")
    private Long id;

    @Column(name = "`date`")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "`manager_id`", referencedColumnName = "`id`")
    private Manager manager;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type="org.hibernate.type.PrimitiveByteArrayBlobType")
    @Column(name = "`screenshot`", length = 100000)
    private byte[] screenShot;


    @ManyToOne
    @JoinColumn(name = "`cat_id`", referencedColumnName = "`id`")
    private CategoryEntity category;

    @Column(name = "`price`")
    private String fullPrice;

    public AnalysisEntity() {
    }

    public AnalysisEntity(Date date, Manager manager, byte[] screenShot) {
        this.date = date;
        this.manager = manager;
        this.screenShot = screenShot;
    }

    public AnalysisEntity(Date date, Manager manager, byte[] screenShot, CategoryEntity category, String fullPrice) {
        this.date = date;
        this.manager = manager;
        this.screenShot = screenShot;
        this.category = category;
        this.fullPrice = fullPrice;
    }

    public AnalysisEntity(Long id, Date date, Manager manager, byte[] screenShot, CategoryEntity category, String fullPrice) {
        this.id = id;
        this.date = date;
        this.manager = manager;
        this.screenShot = screenShot;
        this.category = category;
        this.fullPrice = fullPrice;
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


    public Long getId() {
        return id;
    }

    public byte[] getScreenShot() {
        return screenShot;
    }

    public void setScreenShot(byte[] screenShot) {
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
