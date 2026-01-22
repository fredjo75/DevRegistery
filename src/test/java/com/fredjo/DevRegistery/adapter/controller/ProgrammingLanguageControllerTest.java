package com.fredjo.DevRegistery.adapter.controller;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.application.services.ProgrammingLanguageService;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc = MockMvcBuilders.standaloneSetup(programmingLanguageController).build();
    }

    @Test
    void createProgrammingLanguage_returnsCreatedProgrammingLanguageDto() throws Exception {
        ProgrammingLanguageDto programmingLanguageDto = new ProgrammingLanguageDto();
        programmingLanguageDto.setName("Java");
        programmingLanguageDto.setCreatorsName("James Gosling");
        when(programmingLanguageService.saveProgrammingLanguage(any(ProgrammingLanguageDto.class))).thenReturn(programmingLanguageDto);

        mockMvc.perform(post("/programming_language")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Java\",\"creatorsName\":\"James Gosling\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void getProgrammingLanguageById_returnsProgrammingLanguageDto_whenIdExists() throws Exception {
        ProgrammingLanguageDto programmingLanguageDto = new ProgrammingLanguageDto();
        when(programmingLanguageService.getProgrammingLanguageById(1L)).thenReturn(Optional.of(programmingLanguageDto));

        mockMvc.perform(get("/programming_language/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(programmingLanguageDto.getName()));
    }

    @Test
    void getProgrammingLanguageById_returnsNotFound_whenIdDoesNotExist() throws Exception {
        when(programmingLanguageService.getProgrammingLanguageById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/programming_language/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getDevelopersByProgrammingLanguageId_returnsListOfDeveloperDto() throws Exception {
        when(programmingLanguageService.getDevelopersByProgrammingLanguageId(1L)).thenReturn(Arrays.asList(new DeveloperDto(), new DeveloperDto()));

        mockMvc.perform(get("/programming_language/1/developers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}