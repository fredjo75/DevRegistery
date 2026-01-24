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
@RequestMapping("/v1/programming_language")
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

    /**
     * Fetches all programming languages.
     *
     * @return the ResponseEntity with the list of ProgrammingLanguageDto
     */
    @GetMapping
    public ResponseEntity<Iterable<ProgrammingLanguageDto>> getAllProgrammingLanguages() {
        logger.info("Fetching all programming languages");
        return ResponseEntity.ok(programmingLanguageService.getAllProgrammingLanguages());
    }

    /**
     * Updates an existing programming language.
     *
     * @param id the ID of the programming language to update
     * @param requestBody the updated programming language data transfer object
     * @return the ResponseEntity with the updated ProgrammingLanguageDto
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProgrammingLanguageDto> updateProgrammingLanguage(@PathVariable Long id, @Valid @RequestBody ProgrammingLanguageDto requestBody) {
        logger.info("Updating programming language with id: {}", id);
        ProgrammingLanguageDto updatedLanguage = programmingLanguageService.updateProgrammingLanguage(id, requestBody);
        return ResponseEntity.ok(updatedLanguage);
    }

    /**
     * Deletes a programming language by its ID.
     *
     * @param id the ID of the programming language to delete
     * @return the ResponseEntity with status 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgrammingLanguage(@PathVariable Long id) {
        logger.info("Deleting programming language with id: {}", id);
        programmingLanguageService.deleteProgrammingLanguageById(id);
        return ResponseEntity.noContent().build();
    }
}