package com.fredjo.DevRegistery.application.services;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.domain.entity.Developer;
import com.fredjo.DevRegistery.domain.entity.ProgrammingLanguage;
import com.fredjo.DevRegistery.infra.repository.DeveloperRepository;
import jakarta.transaction.Transactional;
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
public class DeveloperService {

    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private DeveloperRepository developerRepository;

    public Optional<DeveloperDto> getDeveloper(final Long id) {
        return developerRepository.findById(id).map(developer -> modelMapper.map(developer, DeveloperDto.class));
    }

    public Iterable<DeveloperDto> getDeveloper() {
        return StreamSupport.stream(developerRepository.findAll().spliterator(), false).map(developer -> modelMapper.map(developer, DeveloperDto.class)).collect(Collectors.toList());
    }

    public void deleteDeveloper(final Long id) {
        developerRepository.deleteById(id);
    }

    public DeveloperDto saveDeveloper(DeveloperDto developer) {
        return modelMapper.map(developerRepository.save(modelMapper.map(developer, Developer.class)), DeveloperDto.class);
    }

    public void setLanguage(Long id, ProgrammingLanguageDto dto) {
        developerRepository.findById(id).ifPresent(developer -> {
            Set<ProgrammingLanguage> languages = developer.getLanguages();
            languages.add(modelMapper.map(dto, ProgrammingLanguage.class));
            developer.setLanguages(languages);
            developerRepository.save(developer);
        });
    }

    public Iterable<ProgrammingLanguageDto> getLanguages(Long id) {
        Optional<Developer> developer = developerRepository.findById(id);
        if (developer.isPresent()) {
            Set<ProgrammingLanguage> languages = developer.get().getLanguages();
            return languages.stream().map(programmingLanguage -> modelMapper.map(programmingLanguage, ProgrammingLanguageDto.class)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}