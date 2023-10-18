package com.fredjo.DevRegistery.adapter.controller;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.application.services.ProgrammingLanguageService;
import com.fredjo.DevRegistery.domain.entity.ProgrammingLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController()
@RequestMapping("/programming_language")
public class ProgrammingLanguageController {

    @Autowired
    private ProgrammingLanguageService programmingLanguageService;

    @GetMapping()
    public Iterable<ProgrammingLanguageDto> getProgrammingLanguage() {
        return programmingLanguageService.getProgrammingLanguage();
    }

    @PostMapping()
    public ProgrammingLanguageDto saveProgrammingLanguage(@RequestBody ProgrammingLanguageDto requestBody) {
        return programmingLanguageService.saveProgrammingLanguage(requestBody);
    }

    @GetMapping("/{id}")
    public Optional<ProgrammingLanguageDto> getById(@PathVariable Long id){
        return programmingLanguageService.getProgrammingLanguage(id);
    }

    @GetMapping("/{id}/developers")
    public Iterable<DeveloperDto> getdevelopers(@PathVariable Long id){
        return programmingLanguageService.getdevelopers(id);
    }
}