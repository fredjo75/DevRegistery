package com.fredjo.DevRegistery.application.services;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.domain.entity.Developer;
import com.fredjo.DevRegistery.domain.entity.ProgrammingLanguage;
import com.fredjo.DevRegistery.infra.repository.DeveloperRepository;
import com.fredjo.DevRegistery.utils.DeveloperNotFoundException;
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
        ProgrammingLanguage lang1 = new ProgrammingLanguage();
        lang1.setId(1L);
        ProgrammingLanguage lang2 = new ProgrammingLanguage();
        lang2.setId(2L);
        Set<ProgrammingLanguage> languages = new LinkedHashSet<>(Arrays.asList(lang1, lang2));
        ProgrammingLanguageDto langDto1 = new ProgrammingLanguageDto();
        ProgrammingLanguageDto langDto2 = new ProgrammingLanguageDto();

        developer.setLanguages(languages);

        when(developerRepository.findById(id)).thenReturn(Optional.of(developer));
        when(modelMapper.map(lang1, ProgrammingLanguageDto.class)).thenReturn(langDto1);
        when(modelMapper.map(lang2, ProgrammingLanguageDto.class)).thenReturn(langDto2);

        Iterable<ProgrammingLanguageDto> result = developerService.getLanguagesByDeveloperId(id);
        List<ProgrammingLanguageDto> resultList = (List<ProgrammingLanguageDto>) result;

        assertEquals(2, resultList.size());
        assertTrue(resultList.contains(langDto1));
        assertTrue(resultList.contains(langDto2));
    }

    @Test
    void getLanguagesByDeveloperId_returnsEmptyList_whenIdDoesNotExist() {
        Long id = 1L;

        when(developerRepository.findById(id)).thenReturn(Optional.empty());

        Iterable<ProgrammingLanguageDto> result = developerService.getLanguagesByDeveloperId(id);

        assertTrue(((List<ProgrammingLanguageDto>) result).isEmpty());
    }

    @Test
    void updateDeveloper_updatesAndReturnsDeveloperDto_whenIdExists() {
        Long id = 1L;
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setFirstName("Jane");
        developerDto.setLastName("Smith");

        Developer existingDeveloper = new Developer();
        existingDeveloper.setId(id);
        existingDeveloper.setFirstName("John");
        existingDeveloper.setLastName("Doe");

        Developer updatedDeveloper = new Developer();
        updatedDeveloper.setId(id);
        updatedDeveloper.setFirstName("Jane");
        updatedDeveloper.setLastName("Smith");

        when(developerRepository.findById(id)).thenReturn(Optional.of(existingDeveloper));
        when(developerRepository.save(existingDeveloper)).thenReturn(updatedDeveloper);
        when(modelMapper.map(updatedDeveloper, DeveloperDto.class)).thenReturn(developerDto);

        DeveloperDto result = developerService.updateDeveloper(id, developerDto);

        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        verify(developerRepository, times(1)).save(existingDeveloper);
    }

    @Test
    void updateDeveloper_throwsDeveloperNotFoundException_whenIdDoesNotExist() {
        Long id = 1L;
        DeveloperDto developerDto = new DeveloperDto();

        when(developerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DeveloperNotFoundException.class, () -> {
            developerService.updateDeveloper(id, developerDto);
        });
    }

    @Test
    void addLanguageToDeveloper_throwsDeveloperNotFoundException_whenIdDoesNotExist() {
        Long id = 1L;
        ProgrammingLanguageDto programmingLanguageDto = new ProgrammingLanguageDto();

        when(developerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DeveloperNotFoundException.class, () -> {
            developerService.addLanguageToDeveloper(id, programmingLanguageDto);
        });
    }
}