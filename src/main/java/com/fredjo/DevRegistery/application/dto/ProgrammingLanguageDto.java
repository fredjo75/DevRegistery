package com.fredjo.DevRegistery.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammingLanguageDto {

    private long id;

    @NotBlank(message = "Language name is required")
    private String name;

    @NotBlank(message = "Creator name is required")
    private String creatorsName;
}