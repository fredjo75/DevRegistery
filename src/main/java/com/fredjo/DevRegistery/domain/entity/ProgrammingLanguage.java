package com.fredjo.DevRegistery.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "languages")
    @JsonBackReference
    private Set<Developer> developers = new HashSet<>();
}