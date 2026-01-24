package com.fredjo.DevRegistery.application.services;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.domain.entity.Developer;
import com.fredjo.DevRegistery.domain.entity.ProgrammingLanguage;
import com.fredjo.DevRegistery.infra.repository.ProgrammingLanguageRepository;
import com.fredjo.DevRegistery.utils.ProgrammingLanguageNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgrammingLanguageServiceTest {

    @Mock
    private ProgrammingLanguageRepository programmingLanguageRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProgrammingLanguageService programmingLanguageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProgrammingLanguageById_returnsProgrammingLanguageDto_whenIdExists() {
        Long id = 1L;
        ProgrammingLanguage programmingLanguage = new ProgrammingLanguage();
        ProgrammingLanguageDto programmingLanguageDto = new ProgrammingLanguageDto();

        when(programmingLanguageRepository.findById(id)).thenReturn(Optional.of(programmingLanguage));
        when(modelMapper.map(programmingLanguage, ProgrammingLanguageDto.class)).thenReturn(programmingLanguageDto);

        Optional<ProgrammingLanguageDto> result = programmingLanguageService.getProgrammingLanguageById(id);

        assertTrue(result.isPresent());
        assertEquals(programmingLanguageDto, result.get());
    }

    @Test
    void getProgrammingLanguageById_returnsEmptyOptional_whenIdDoesNotExist() {
        Long id = 1L;

        when(programmingLanguageRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ProgrammingLanguageDto> result = programmingLanguageService.getProgrammingLanguageById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllProgrammingLanguages_returnsListOfProgrammingLanguageDto() {
        List<ProgrammingLanguage> programmingLanguages = Arrays.asList(new ProgrammingLanguage(), new ProgrammingLanguage());
        List<ProgrammingLanguageDto> programmingLanguageDtos = Arrays.asList(new ProgrammingLanguageDto(), new ProgrammingLanguageDto());

        when(programmingLanguageRepository.findAll()).thenReturn(programmingLanguages);
        when(modelMapper.map(any(ProgrammingLanguage.class), eq(ProgrammingLanguageDto.class)))
                .thenReturn(programmingLanguageDtos.get(0), programmingLanguageDtos.get(1));

        List<ProgrammingLanguageDto> result = programmingLanguageService.getAllProgrammingLanguages();

        assertEquals(2, result.size());
        assertEquals(programmingLanguageDtos, result);
    }

    @Test
    void getProgrammingLanguagesByIds_returnsListOfProgrammingLanguageDto_whenIdsExist() {
        Set<Long> ids = new HashSet<>(Arrays.asList(1L, 2L));
        List<ProgrammingLanguage> programmingLanguages = Arrays.asList(new ProgrammingLanguage(), new ProgrammingLanguage());
        List<ProgrammingLanguageDto> programmingLanguageDtos = Arrays.asList(new ProgrammingLanguageDto(), new ProgrammingLanguageDto());

        when(programmingLanguageRepository.findAllById(ids)).thenReturn(programmingLanguages);
        when(modelMapper.map(any(ProgrammingLanguage.class), eq(ProgrammingLanguageDto.class)))
                .thenReturn(programmingLanguageDtos.get(0), programmingLanguageDtos.get(1));

        List<ProgrammingLanguageDto> result = programmingLanguageService.getProgrammingLanguagesByIds(ids);

        assertEquals(2, result.size());
        assertEquals(programmingLanguageDtos, result);
    }

    @Test
    void deleteProgrammingLanguageById_deletesProgrammingLanguage_whenIdExists() {
        Long id = 1L;

        when(programmingLanguageRepository.existsById(id)).thenReturn(true);

        programmingLanguageService.deleteProgrammingLanguageById(id);

        verify(programmingLanguageRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteProgrammingLanguageById_throwsException_whenIdDoesNotExist() {
        Long id = 1L;

        when(programmingLanguageRepository.existsById(id)).thenReturn(false);

        assertThrows(ProgrammingLanguageNotFoundException.class, () -> programmingLanguageService.deleteProgrammingLanguageById(id));
    }

    @Test
    void saveProgrammingLanguage_savesAndReturnsProgrammingLanguageDto() {
        ProgrammingLanguageDto programmingLanguageDto = new ProgrammingLanguageDto();
        ProgrammingLanguage programmingLanguage = new ProgrammingLanguage();
        ProgrammingLanguage savedProgrammingLanguage = new ProgrammingLanguage();

        when(modelMapper.map(programmingLanguageDto, ProgrammingLanguage.class)).thenReturn(programmingLanguage);
        when(programmingLanguageRepository.save(programmingLanguage)).thenReturn(savedProgrammingLanguage);
        when(modelMapper.map(savedProgrammingLanguage, ProgrammingLanguageDto.class)).thenReturn(programmingLanguageDto);

        ProgrammingLanguageDto result = programmingLanguageService.saveProgrammingLanguage(programmingLanguageDto);

        assertEquals(programmingLanguageDto, result);
    }

    @Test
    void getDevelopersByProgrammingLanguageId_returnsListOfDeveloperDto_whenIdExists() {
        Long id = 1L;
        ProgrammingLanguage programmingLanguage = new ProgrammingLanguage();
        Developer dev1 = new Developer();
        dev1.setId(1L);
        Developer dev2 = new Developer();
        dev2.setId(2L);
        Set<Developer> developers = new LinkedHashSet<>(Arrays.asList(dev1, dev2));
        DeveloperDto devDto1 = new DeveloperDto();
        DeveloperDto devDto2 = new DeveloperDto();

        programmingLanguage.setDevelopers(developers);

        when(programmingLanguageRepository.findById(id)).thenReturn(Optional.of(programmingLanguage));
        when(modelMapper.map(dev1, DeveloperDto.class)).thenReturn(devDto1);
        when(modelMapper.map(dev2, DeveloperDto.class)).thenReturn(devDto2);

        List<DeveloperDto> result = programmingLanguageService.getDevelopersByProgrammingLanguageId(id);

        assertEquals(2, result.size());
        assertTrue(result.contains(devDto1));
        assertTrue(result.contains(devDto2));
    }

    @Test
    void getDevelopersByProgrammingLanguageId_returnsEmptyList_whenIdDoesNotExist() {
        Long id = 1L;

        when(programmingLanguageRepository.findById(id)).thenReturn(Optional.empty());

        List<DeveloperDto> result = programmingLanguageService.getDevelopersByProgrammingLanguageId(id);

        assertTrue(result.isEmpty());
    }

    @Test
    void updateProgrammingLanguage_updatesAndReturnsProgrammingLanguageDto_whenIdExists() {
        Long id = 1L;
        ProgrammingLanguageDto programmingLanguageDto = new ProgrammingLanguageDto();
        programmingLanguageDto.setName("Python");
        programmingLanguageDto.setCreatorsName("Guido van Rossum");

        ProgrammingLanguage existingLanguage = new ProgrammingLanguage();
        existingLanguage.setId(id);
        existingLanguage.setName("Java");
        existingLanguage.setCreatorsName("James Gosling");

        ProgrammingLanguage updatedLanguage = new ProgrammingLanguage();
        updatedLanguage.setId(id);
        updatedLanguage.setName("Python");
        updatedLanguage.setCreatorsName("Guido van Rossum");

        when(programmingLanguageRepository.findById(id)).thenReturn(Optional.of(existingLanguage));
        when(programmingLanguageRepository.save(existingLanguage)).thenReturn(updatedLanguage);
        when(modelMapper.map(updatedLanguage, ProgrammingLanguageDto.class)).thenReturn(programmingLanguageDto);

        ProgrammingLanguageDto result = programmingLanguageService.updateProgrammingLanguage(id, programmingLanguageDto);

        assertEquals("Python", result.getName());
        assertEquals("Guido van Rossum", result.getCreatorsName());
        verify(programmingLanguageRepository, times(1)).save(existingLanguage);
    }

    @Test
    void updateProgrammingLanguage_throwsProgrammingLanguageNotFoundException_whenIdDoesNotExist() {
        Long id = 1L;
        ProgrammingLanguageDto programmingLanguageDto = new ProgrammingLanguageDto();

        when(programmingLanguageRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProgrammingLanguageNotFoundException.class, () -> {
            programmingLanguageService.updateProgrammingLanguage(id, programmingLanguageDto);
        });
    }
}