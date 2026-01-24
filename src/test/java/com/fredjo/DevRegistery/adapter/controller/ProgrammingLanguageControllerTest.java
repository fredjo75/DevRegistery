package com.fredjo.DevRegistery.adapter.controller;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.application.services.ProgrammingLanguageService;
import com.fredjo.DevRegistery.config.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProgrammingLanguageControllerTest {

    @Mock
    private ProgrammingLanguageService programmingLanguageService;

    @InjectMocks
    private ProgrammingLanguageController programmingLanguageController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(programmingLanguageController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getAllProgrammingLanguages_returnsListOfProgrammingLanguageDto() throws Exception {
        when(programmingLanguageService.getAllProgrammingLanguages()).thenReturn(Arrays.asList(new ProgrammingLanguageDto(), new ProgrammingLanguageDto()));

        mockMvc.perform(get("/v1/programming_language"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void createProgrammingLanguage_returnsCreatedProgrammingLanguageDto() throws Exception {
        ProgrammingLanguageDto programmingLanguageDto = new ProgrammingLanguageDto();
        programmingLanguageDto.setId(1L);
        programmingLanguageDto.setName("Java");
        programmingLanguageDto.setCreatorsName("James Gosling");
        when(programmingLanguageService.saveProgrammingLanguage(any(ProgrammingLanguageDto.class))).thenReturn(programmingLanguageDto);

        mockMvc.perform(post("/v1/programming_language")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Java\",\"creatorsName\":\"James Gosling\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.creatorsName").value("James Gosling"));
    }

    @Test
    void getProgrammingLanguageById_returnsProgrammingLanguageDto_whenIdExists() throws Exception {
        ProgrammingLanguageDto programmingLanguageDto = new ProgrammingLanguageDto();
        programmingLanguageDto.setName("Python");
        programmingLanguageDto.setCreatorsName("Guido van Rossum");
        when(programmingLanguageService.getProgrammingLanguageById(1L)).thenReturn(Optional.of(programmingLanguageDto));

        mockMvc.perform(get("/v1/programming_language/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Python"))
                .andExpect(jsonPath("$.creatorsName").value("Guido van Rossum"));
    }

    @Test
    void getProgrammingLanguageById_returnsNotFound_whenIdDoesNotExist() throws Exception {
        when(programmingLanguageService.getProgrammingLanguageById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/programming_language/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateProgrammingLanguage_returnsUpdatedProgrammingLanguageDto() throws Exception {
        ProgrammingLanguageDto programmingLanguageDto = new ProgrammingLanguageDto();
        programmingLanguageDto.setId(1L);
        programmingLanguageDto.setName("JavaScript");
        programmingLanguageDto.setCreatorsName("Brendan Eich");
        when(programmingLanguageService.updateProgrammingLanguage(eq(1L), any(ProgrammingLanguageDto.class))).thenReturn(programmingLanguageDto);

        mockMvc.perform(put("/v1/programming_language/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"JavaScript\",\"creatorsName\":\"Brendan Eich\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("JavaScript"))
                .andExpect(jsonPath("$.creatorsName").value("Brendan Eich"));
    }

    @Test
    void deleteProgrammingLanguage_returnsNoContent() throws Exception {
        doNothing().when(programmingLanguageService).deleteProgrammingLanguageById(1L);

        mockMvc.perform(delete("/v1/programming_language/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getDevelopersByProgrammingLanguageId_returnsListOfDeveloperDto() throws Exception {
        when(programmingLanguageService.getDevelopersByProgrammingLanguageId(1L)).thenReturn(Arrays.asList(new DeveloperDto(), new DeveloperDto()));

        mockMvc.perform(get("/v1/programming_language/1/developers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}