package com.fredjo.DevRegistery.domain.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class ProgrammingLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String creatorsName;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Developer> developers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorsName() {
        return creatorsName;
    }

    public void setCreatorsName(String creatorsName) {
        this.creatorsName = creatorsName;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }
}