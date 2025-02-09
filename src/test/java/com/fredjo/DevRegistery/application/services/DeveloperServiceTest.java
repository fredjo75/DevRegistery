package com.fredjo.DevRegistery.application.services;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.domain.entity.Developer;
import com.fredjo.DevRegistery.domain.entity.ProgrammingLanguage;
import com.fredjo.DevRegistery.infra.repository.DeveloperRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeveloperServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DeveloperService developerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDeveloperById_returnsDeveloperDto_whenIdExists() {
        Long id = 1L;
        Developer developer = new Developer();
        DeveloperDto developerDto = new DeveloperDto();

        when(developerRepository.findById(id)).thenReturn(Optional.of(developer));
        when(modelMapper.map(developer, DeveloperDto.class)).thenReturn(developerDto);

        Optional<DeveloperDto> result = developerService.getDeveloperById(id);

        assertTrue(result.isPresent());
        assertEquals(developerDto, result.get());
    }

    @Test
    void getDeveloperById_returnsEmptyOptional_whenIdDoesNotExist() {
        Long id = 1L;

        when(developerRepository.findById(id)).thenReturn(Optional.empty());

        Optional<DeveloperDto> result = developerService.getDeveloperById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllDevelopers_returnsListOfDeveloperDto() {
        List<Developer> developers = Arrays.asList(new Developer(), new Developer());
        List<DeveloperDto> developerDtos = Arrays.asList(new DeveloperDto(), new DeveloperDto());

        when(developerRepository.findAll()).thenReturn(developers);
        when(modelMapper.map(any(Developer.class), eq(DeveloperDto.class)))
                .thenReturn(developerDtos.get(0), developerDtos.get(1));

        Iterable<DeveloperDto> result = developerService.getAllDevelopers();

        assertEquals(2, ((List<DeveloperDto>) result).size());
        assertEquals(developerDtos, result);
    }

    @Test
    void deleteDeveloperById_deletesDeveloper_whenIdExists() {
        Long id = 1L;

        doNothing().when(developerRepository).deleteById(id);

        developerService.deleteDeveloperById(id);

        verify(developerRepository, times(1)).deleteById(id);
    }

    @Test
    void saveDeveloper_savesAndReturnsDeveloperDto() {
        DeveloperDto developerDto = new DeveloperDto();
        Developer developer = new Developer();
        Developer savedDeveloper = new Developer();

        when(modelMapper.map(developerDto, Developer.class)).thenReturn(developer);
        when(developerRepository.save(developer)).thenReturn(savedDeveloper);
        when(modelMapper.map(savedDeveloper, DeveloperDto.class)).thenReturn(developerDto);

        DeveloperDto result = developerService.saveDeveloper(developerDto);

        assertEquals(developerDto, result);
    }

    @Test
    void addLanguageToDeveloper_addsLanguage_whenDeveloperExists() {
        Long id = 1L;
        Developer developer = new Developer();
        ProgrammingLanguageDto programmingLanguageDto = new ProgrammingLanguageDto();
        ProgrammingLanguage programmingLanguage = new ProgrammingLanguage();
        Set<ProgrammingLanguage> languages = new HashSet<>();

        developer.setLanguages(languages);

        when(developerRepository.findById(id)).thenReturn(Optional.of(developer));
        when(modelMapper.map(programmingLanguageDto, ProgrammingLanguage.class)).thenReturn(programmingLanguage);

        developerService.addLanguageToDeveloper(id, programmingLanguageDto);

        assertTrue(developer.getLanguages().contains(programmingLanguage));
        verify(developerRepository, times(1)).save(developer);
    }

    @Test
    void getLanguagesByDeveloperId_returnsListOfProgrammingLanguageDto_whenIdExists() {
        Long id = 1L;
        Developer developer = new Developer();
        Set<ProgrammingLanguage> languages = new HashSet<>(Arrays.asList(new ProgrammingLanguage(), new ProgrammingLanguage()));
        List<ProgrammingLanguageDto> languageDtos = Arrays.asList(new ProgrammingLanguageDto(), new ProgrammingLanguageDto());

        developer.setLanguages(languages);

        when(developerRepository.findById(id)).thenReturn(Optional.of(developer));
        when(modelMapper.map(any(ProgrammingLanguage.class), eq(ProgrammingLanguageDto.class)))
                .thenReturn(languageDtos.get(0), languageDtos.get(1));

        Iterable<ProgrammingLanguageDto> result = developerService.getLanguagesByDeveloperId(id);

        assertEquals(2, ((List<ProgrammingLanguageDto>) result).size());
        assertEquals(languageDtos, result);
    }

    @Test
    void getLanguagesByDeveloperId_returnsEmptyList_whenIdDoesNotExist() {
        Long id = 1L;

        when(developerRepository.findById(id)).thenReturn(Optional.empty());

        Iterable<ProgrammingLanguageDto> result = developerService.getLanguagesByDeveloperId(id);

        assertTrue(((List<ProgrammingLanguageDto>) result).isEmpty());
    }
}