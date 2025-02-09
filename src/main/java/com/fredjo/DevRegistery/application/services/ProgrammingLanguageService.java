package com.fredjo.DevRegistery.application.services;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.domain.entity.Developer;
import com.fredjo.DevRegistery.domain.entity.ProgrammingLanguage;
import com.fredjo.DevRegistery.infra.repository.ProgrammingLanguageRepository;
import com.fredjo.DevRegistery.utils.ProgrammingLanguageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service class for managing programming languages.
 */
@Service
@RequiredArgsConstructor
public class ProgrammingLanguageService {

    private static final Logger logger = LoggerFactory.getLogger(ProgrammingLanguageService.class);
    private final ModelMapper modelMapper;
    private final ProgrammingLanguageRepository programmingLanguageRepository;

    /**
     * Fetches a programming language by its ID.
     *
     * @param id the ID of the programming language
     * @return an Optional containing the ProgrammingLanguageDto if found, otherwise empty
     */
    public Optional<ProgrammingLanguageDto> getProgrammingLanguageById(final Long id) {
        logger.info("Fetching programming language with id: {}", id);
        return programmingLanguageRepository.findById(id)
                .map(programmingLanguage -> modelMapper.map(programmingLanguage, ProgrammingLanguageDto.class));
    }

    /**
     * Fetches all programming languages.
     *
     * @return a list of ProgrammingLanguageDto
     */
    public List<ProgrammingLanguageDto> getAllProgrammingLanguages() {
        logger.info("Fetching all programming languages");
        return StreamSupport.stream(programmingLanguageRepository.findAll().spliterator(), false)
                .map(t -> modelMapper.map(t, ProgrammingLanguageDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Fetches programming languages by their IDs.
     *
     * @param ids the set of IDs of the programming languages
     * @return a list of ProgrammingLanguageDto
     */
    public List<ProgrammingLanguageDto> getProgrammingLanguagesByIds(final Set<Long> ids) {
        logger.info("Fetching programming languages by ids");
        return StreamSupport.stream(programmingLanguageRepository.findAllById(ids).spliterator(), false)
                .map(t -> modelMapper.map(t, ProgrammingLanguageDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Deletes a programming language by its ID.
     *
     * @param id the ID of the programming language
     * @throws ProgrammingLanguageNotFoundException if the programming language is not found
     */
    @Transactional
    public void deleteProgrammingLanguageById(final Long id) {
        logger.info("Deleting programming language with id: {}", id);
        if (!programmingLanguageRepository.existsById(id)) {
            throw new ProgrammingLanguageNotFoundException("Programming language not found with id: " + id);
        }
        programmingLanguageRepository.deleteById(id);
    }

    /**
     * Saves a programming language.
     *
     * @param programmingLanguageDto the programming language data transfer object
     * @return the saved ProgrammingLanguageDto
     */
    @Transactional
    public ProgrammingLanguageDto saveProgrammingLanguage(ProgrammingLanguageDto programmingLanguageDto) {
        logger.info("Saving programming language");
        ProgrammingLanguage programmingLanguage = modelMapper.map(programmingLanguageDto, ProgrammingLanguage.class);
        ProgrammingLanguage savedLanguage = programmingLanguageRepository.save(programmingLanguage);
        return modelMapper.map(savedLanguage, ProgrammingLanguageDto.class);
    }

    /**
     * Fetches developers by the programming language ID.
     *
     * @param id the ID of the programming language
     * @return a list of DeveloperDto
     */
    public List<DeveloperDto> getDevelopersByProgrammingLanguageId(final Long id) {
        logger.info("Fetching developers for programming language with id: {}", id);
        Optional<ProgrammingLanguage> programmingLanguage = programmingLanguageRepository.findById(id);
        if (programmingLanguage.isPresent()) {
            Set<Developer> developers = programmingLanguage.get().getDevelopers();
            return developers.stream()
                    .map(developer -> modelMapper.map(developer, DeveloperDto.class))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}