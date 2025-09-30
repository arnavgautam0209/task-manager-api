package com.arnavgautam.taskmanager.dto.task;

import com.arnavgautam.taskmanager.enums.Priority;
import com.arnavgautam.taskmanager.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for task creation and update requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Task request")
public class TaskRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title must be less than 150 characters")
    @Schema(description = "Task title", example = "Complete project documentation")
    private String title;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    @Schema(description = "Task description", example = "Write comprehensive README and API documentation")
    private String description;

    @Schema(description = "Task status", example = "TODO")
    private TaskStatus status = TaskStatus.TODO;

    @Schema(description = "Task priority", example = "HIGH")
    private Priority priority = Priority.MEDIUM;

    @Schema(description = "Due date", example = "2024-01-15")
    private LocalDate dueDate;
}
