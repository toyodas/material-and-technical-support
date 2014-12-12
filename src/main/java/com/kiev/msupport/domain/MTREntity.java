package com.kiev.msupport.domain;

import javax.persistence.*;

@Entity
@Table(name="mtr")
public class MTREntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="`id`")
    Long id;

    @ManyToOne
    @JoinColumn(name="`cat_id`", referencedColumnName = "`id`")
    CategoryEntity category;

    @Column(name="name")
    String name;

    @ManyToOne
    @JoinColumn(name="`unit_id`", referencedColumnName = "`id`")
    UnitEntity units;

    public MTREntity() {
    }

    public MTREntity(CategoryEntity category, String name, UnitEntity units) {
        this.category = category;
        this.name = name;
        this.units = units;
    }

    public MTREntity(Long id, CategoryEntity category, String name, UnitEntity units) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.units = units;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitEntity getUnits() {
        return units;
    }

    public void setUnits(UnitEntity units) {
        this.units = units;
    }
}
