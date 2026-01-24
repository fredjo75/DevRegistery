package com.fredjo.DevRegistery.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammingLanguageDto {

    private long id;

    @NotBlank(message = "Language name is required")
    @Size(min = 1, max = 100, message = "Language name must be between 1 and 100 characters")
    private String name;

    @NotBlank(message = "Creator name is required")
    @Size(min = 1, max = 200, message = "Creator name must be between 1 and 200 characters")
    private String creatorsName;
}