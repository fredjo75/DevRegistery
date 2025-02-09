package com.fredjo.DevRegistery.adapter.controller;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.application.services.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

/**
 * REST controller for managing developers.
 */
@RestController
@RequestMapping("/developer")
@RequiredArgsConstructor
public class DeveloperController {

    private static final Logger logger = LoggerFactory.getLogger(DeveloperController.class);
    private final DeveloperService developerService;

    /**
     * Fetches all developers.
     *
     * @return the ResponseEntity with the list of DeveloperDto
     */
    @GetMapping
    public ResponseEntity<Iterable<DeveloperDto>> getAllDevelopers() {
        logger.info("Fetching all developers");
        return ResponseEntity.ok(developerService.getAllDevelopers());
    }

    /**
     * Creates a new developer.
     *
     * @param requestBody the developer data transfer object
     * @return the ResponseEntity with the created DeveloperDto
     */
    @PostMapping
    public ResponseEntity<DeveloperDto> createDeveloper(@RequestBody DeveloperDto requestBody) {
        logger.info("Creating new developer");
        DeveloperDto savedDeveloper = developerService.saveDeveloper(requestBody);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDeveloper.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedDeveloper);
    }

    /**
     * Fetches a developer by its ID.
     *
     * @param id the ID of the developer
     * @return the ResponseEntity with the DeveloperDto if found, otherwise 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeveloperDto> getDeveloperById(@PathVariable Long id) {
        logger.info("Fetching developer with id: {}", id);
        Optional<DeveloperDto> developer = developerService.getDeveloperById(id);
        return developer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Adds a programming language to a developer.
     *
     * @param id          the ID of the developer
     * @param requestBody the programming language data transfer object
     * @return the ResponseEntity with status 200 OK
     */
    @PostMapping("/{id}/languages")
    public ResponseEntity<Void> addLanguageToDeveloper(@PathVariable Long id, @RequestBody ProgrammingLanguageDto requestBody) {
        logger.info("Adding language to developer with id: {}", id);
        developerService.addLanguageToDeveloper(id, requestBody);
        return ResponseEntity.ok().build();
    }

    /**
     * Fetches programming languages for a developer by its ID.
     *
     * @param id the ID of the developer
     * @return the ResponseEntity with the list of ProgrammingLanguageDto
     */
    @GetMapping("/{id}/languages")
    public ResponseEntity<Iterable<ProgrammingLanguageDto>> getLanguagesByDeveloperId(@PathVariable Long id) {
        logger.info("Fetching languages for developer with id: {}", id);
        return ResponseEntity.ok(developerService.getLanguagesByDeveloperId(id));
    }
}