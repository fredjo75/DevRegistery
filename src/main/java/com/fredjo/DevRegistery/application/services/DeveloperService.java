package com.fredjo.DevRegistery.application.services;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.domain.entity.Developer;
import com.fredjo.DevRegistery.domain.entity.ProgrammingLanguage;
import com.fredjo.DevRegistery.infra.repository.DeveloperRepository;
import com.fredjo.DevRegistery.utils.DeveloperNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service class for managing developers.
 */
@Service
@RequiredArgsConstructor
public class DeveloperService {

    private static final Logger logger = LoggerFactory.getLogger(DeveloperService.class);
    private final ModelMapper modelMapper;
    private final DeveloperRepository developerRepository;

    /**
     * Fetches a developer by its ID.
     *
     * @param id the ID of the developer
     * @return an Optional containing the DeveloperDto if found, otherwise empty
     */
    public Optional<DeveloperDto> getDeveloperById(final Long id) {
        logger.info("Fetching developer with id: {}", id);
        return developerRepository.findById(id)
                .map(developer -> modelMapper.map(developer, DeveloperDto.class));
    }

    /**
     * Fetches all developers.
     *
     * @return an Iterable of DeveloperDto
     */
    public Iterable<DeveloperDto> getAllDevelopers() {
        logger.info("Fetching all developers");
        return StreamSupport.stream(developerRepository.findAll().spliterator(), false)
                .map(developer -> modelMapper.map(developer, DeveloperDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Fetches all developers with pagination.
     *
     * @param pageable the pagination information
     * @return a Page of DeveloperDto
     */
    public Page<DeveloperDto> getAllDevelopers(Pageable pageable) {
        logger.info("Fetching all developers with pagination");
        return developerRepository.findAll(pageable)
                .map(developer -> modelMapper.map(developer, DeveloperDto.class));
    }

    /**
     * Deletes a developer by its ID.
     *
     * @param id the ID of the developer
     */
    @Transactional
    public void deleteDeveloperById(final Long id) {
        logger.info("Deleting developer with id: {}", id);
        developerRepository.deleteById(id);
    }

    /**
     * Saves a developer.
     *
     * @param developerDto the developer data transfer object
     * @return the saved DeveloperDto
     */
    @Transactional
    public DeveloperDto saveDeveloper(DeveloperDto developerDto) {
        logger.info("Saving developer");
        Developer developer = modelMapper.map(developerDto, Developer.class);
        Developer savedDeveloper = developerRepository.save(developer);
        return modelMapper.map(savedDeveloper, DeveloperDto.class);
    }

    /**
     * Adds a programming language to a developer.
     *
     * @param id                     the ID of the developer
     * @param programmingLanguageDto the programming language data transfer object
     * @throws DeveloperNotFoundException if the developer is not found
     */
    @Transactional
    public void addLanguageToDeveloper(Long id, ProgrammingLanguageDto programmingLanguageDto) {
        logger.info("Adding language to developer with id: {}", id);
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException("Developer not found with id: " + id));

        Set<ProgrammingLanguage> languages = developer.getLanguages();
        if (languages == null) {
            languages = new java.util.HashSet<>();
            developer.setLanguages(languages);
        }
        languages.add(modelMapper.map(programmingLanguageDto, ProgrammingLanguage.class));
        developerRepository.save(developer);
    }

    /**
     * Fetches programming languages for a developer by its ID.
     *
     * @param id the ID of the developer
     * @return an Iterable of ProgrammingLanguageDto
     */
    public Iterable<ProgrammingLanguageDto> getLanguagesByDeveloperId(Long id) {
        logger.info("Fetching languages for developer with id: {}", id);
        Optional<Developer> developer = developerRepository.findById(id);
        if (developer.isPresent()) {
            Set<ProgrammingLanguage> languages = developer.get().getLanguages();
            return languages.stream()
                    .map(language -> modelMapper.map(language, ProgrammingLanguageDto.class))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Updates an existing developer.
     *
     * @param id the ID of the developer to update
     * @param developerDto the updated developer data transfer object
     * @return the updated DeveloperDto
     * @throws DeveloperNotFoundException if the developer is not found
     */
    @Transactional
    public DeveloperDto updateDeveloper(Long id, DeveloperDto developerDto) {
        logger.info("Updating developer with id: {}", id);
        Developer existingDeveloper = developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException("Developer not found with id: " + id));

        existingDeveloper.setFirstName(developerDto.getFirstName());
        existingDeveloper.setLastName(developerDto.getLastName());

        Developer updatedDeveloper = developerRepository.save(existingDeveloper);
        return modelMapper.map(updatedDeveloper, DeveloperDto.class);
    }
}