package com.kiev.msupport.domain;

import javax.persistence.*;

@Entity
@Table(name = "material")
public class MaterialEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "`id`")
    private Long id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`available`")
    private Long available;

    @Column(name = "`needed`")
    private Long needed;

    @Column(name = "`last_updated`")
    private String lastUpdated;

    @Column(name = "`desc`")
    private String description;

    @Column(name = "`comment`")
    private String comment;

    public MaterialEntity(String name, Long available, Long needed, String lastUpdated, String description) {
        this.name = name;
        this.available = available;
        this.needed = needed;
        this.lastUpdated = lastUpdated;
        this.description = description;
        this.comment = null;
    }

    public MaterialEntity(String name, Long available, Long needed, String lastUpdated, String description, String comments) {
        this.name = name;
        this.available = available;
        this.needed = needed;
        this.lastUpdated = lastUpdated;
        this.description = description;
        this.comment = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAvailable() {
        return available;
    }

    public void setAvailable(Long available) {
        this.available = available;
    }

    public Long getNeeded() {
        return needed;
    }

    public void setNeeded(Long needed) {
        this.needed = needed;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
