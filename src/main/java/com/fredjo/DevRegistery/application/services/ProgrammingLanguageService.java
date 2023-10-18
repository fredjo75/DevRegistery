package com.fredjo.DevRegistery.application.services;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.domain.entity.Developer;
import com.fredjo.DevRegistery.domain.entity.ProgrammingLanguage;
import com.fredjo.DevRegistery.infra.repository.ProgrammingLanguageRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@Service
public class ProgrammingLanguageService {
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private ProgrammingLanguageRepository programmingLanguageRepository;

    public Optional<ProgrammingLanguageDto> getProgrammingLanguage(final Long id) {
        return programmingLanguageRepository.findById(id).map(programmingLanguage -> modelMapper.map(programmingLanguage, ProgrammingLanguageDto.class));
    }

    public Iterable<ProgrammingLanguageDto> getProgrammingLanguage() {
        return StreamSupport.stream(programmingLanguageRepository.findAll().spliterator(), false).map(t -> modelMapper.map(t, ProgrammingLanguageDto.class)).collect(Collectors.toList());
    }

    public void deleteProgrammingLanguage(final Long id) {
        programmingLanguageRepository.deleteById(id);
    }

    public ProgrammingLanguageDto saveProgrammingLanguage(ProgrammingLanguageDto programmingLanguage) {
        return modelMapper.map(programmingLanguageRepository.save(modelMapper.map(programmingLanguage, ProgrammingLanguage.class)),
                ProgrammingLanguageDto.class);
    }

    public Iterable<DeveloperDto> getdevelopers(Long id) {
        Optional<ProgrammingLanguage> programmingLanguage = programmingLanguageRepository.findById(id);
        if (programmingLanguage.isPresent()) {
            Set<Developer> languages = programmingLanguage.get().getDevelopers();
            return languages.stream().map(developer -> modelMapper.map(developer, DeveloperDto.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}