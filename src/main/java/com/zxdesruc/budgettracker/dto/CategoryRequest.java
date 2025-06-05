package com.zxdesruc.budgettracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO for creating or updating a category.
 */
@Data
public class CategoryRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;
}
