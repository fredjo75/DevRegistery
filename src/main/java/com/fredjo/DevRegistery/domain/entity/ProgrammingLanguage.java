package com.fredjo.DevRegistery.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammingLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String creatorsName;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "languages")
    private Set<Developer> developers = new HashSet<>();
}