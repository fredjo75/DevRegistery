package com.fredjo.DevRegistery.adapter.controller;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.application.services.ProgrammingLanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

/**
 * REST controller for managing programming languages.
 */
@RestController
@RequestMapping("/programming_language")
@RequiredArgsConstructor
public class ProgrammingLanguageController {

    private static final Logger logger = LoggerFactory.getLogger(ProgrammingLanguageController.class);
    private final ProgrammingLanguageService programmingLanguageService;

    /**
     * Creates a new programming language.
     *
     * @param requestBody the programming language data transfer object
     * @return the ResponseEntity with the created ProgrammingLanguageDto
     */
    @PostMapping
    public ResponseEntity<ProgrammingLanguageDto> createProgrammingLanguage(@Valid @RequestBody ProgrammingLanguageDto requestBody) {
        logger.info("Creating new programming language");
        ProgrammingLanguageDto savedLanguage = programmingLanguageService.saveProgrammingLanguage(requestBody);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedLanguage.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedLanguage);
    }

    /**
     * Fetches a programming language by its ID.
     *
     * @param id the ID of the programming language
     * @return the ResponseEntity with the ProgrammingLanguageDto if found, otherwise 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProgrammingLanguageDto> getProgrammingLanguageById(@PathVariable Long id) {
        logger.info("Fetching programming language with id: {}", id);
        Optional<ProgrammingLanguageDto> programmingLanguage = programmingLanguageService.getProgrammingLanguageById(id);
        return programmingLanguage.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Fetches developers by the programming language ID.
     *
     * @param id the ID of the programming language
     * @return the ResponseEntity with the list of DeveloperDto
     */
    @GetMapping("/{id}/developers")
    public ResponseEntity<Iterable<DeveloperDto>> getDevelopersByProgrammingLanguageId(@PathVariable Long id) {
        logger.info("Fetching developers for programming language with id: {}", id);
        return ResponseEntity.ok(programmingLanguageService.getDevelopersByProgrammingLanguageId(id));
    }
}