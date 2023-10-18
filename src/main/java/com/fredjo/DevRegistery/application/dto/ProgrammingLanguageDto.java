package com.fredjo.DevRegistery.application.dto;

public class ProgrammingLanguageDto {

    private long id;

    private String name;
    private String creatorsName;

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
}