package com.kiev.msupport.domain;

import javax.persistence.*;

@Entity
@Table(name="manager")
public class Manager {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="`id`")
    Long id;

    @Column(name="full_name")
    String fullName;

    public Manager() {
    }

    public Manager(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
