package com.fredjo.DevRegistery.adapter.controller;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.application.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController()
@RequestMapping("/developer")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    @GetMapping()
    public Iterable<DeveloperDto> getDeveloper() {
        return developerService.getDeveloper();
    }

    @PostMapping()
    public DeveloperDto saveDeveloper(@RequestBody DeveloperDto requestBody) {
        return developerService.saveDeveloper(requestBody);
    }

    @GetMapping("/{id}")
    public Optional<DeveloperDto> getById(@PathVariable Long id){
        return developerService.getDeveloper(id);
    }

    @PostMapping("/{id}/languages")
    public void setLanguage(@PathVariable Long id, @RequestBody ProgrammingLanguageDto requestBody){
        developerService.setLanguage(id, requestBody);
    }

    @GetMapping("/{id}/languages")
    public Iterable<ProgrammingLanguageDto> getLanguages(@PathVariable Long id){
        return developerService.getLanguages(id);
    }
}