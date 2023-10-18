package com.fredjo.DevRegistery.domain.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ProgrammingLanguage> languages;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<ProgrammingLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<ProgrammingLanguage> languages) {
        this.languages = languages;
    }
}