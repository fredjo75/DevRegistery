package com.fredjo.DevRegistery.adapter.controller;

import com.fredjo.DevRegistery.application.dto.DeveloperDto;
import com.fredjo.DevRegistery.application.dto.ProgrammingLanguageDto;
import com.fredjo.DevRegistery.application.services.DeveloperService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeveloperControllerTest {

    @Mock
    private DeveloperService developerService;

    @InjectMocks
    private DeveloperController developerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(developerController).build();
    }

    @Test
    void getAllDevelopers_returnsListOfDeveloperDto() throws Exception {
        when(developerService.getAllDevelopers()).thenReturn(Arrays.asList(new DeveloperDto(), new DeveloperDto()));

        mockMvc.perform(get("/developer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void createDeveloper_returnsCreatedDeveloperDto() throws Exception {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setFirstName("John");
        developerDto.setLastName("Doe");
        when(developerService.saveDeveloper(any(DeveloperDto.class))).thenReturn(developerDto);

        mockMvc.perform(post("/developer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\", \"lastName\":\"Doe\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void getDeveloperById_returnsDeveloperDto_whenIdExists() throws Exception {
        DeveloperDto developerDto = new DeveloperDto();
        when(developerService.getDeveloperById(1L)).thenReturn(Optional.of(developerDto));

        mockMvc.perform(get("/developer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(developerDto.getFirstName()));
    }

    @Test
    void getDeveloperById_returnsNotFound_whenIdDoesNotExist() throws Exception {
        when(developerService.getDeveloperById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/developer/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addLanguageToDeveloper_returnsOk_whenDeveloperExists() throws Exception {
        doNothing().when(developerService).addLanguageToDeveloper(eq(1L), any(ProgrammingLanguageDto.class));

        mockMvc.perform(post("/developer/1/languages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Java\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void getLanguagesByDeveloperId_returnsListOfProgrammingLanguageDto() throws Exception {
        when(developerService.getLanguagesByDeveloperId(1L)).thenReturn(Arrays.asList(new ProgrammingLanguageDto(), new ProgrammingLanguageDto()));

        mockMvc.perform(get("/developer/1/languages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}