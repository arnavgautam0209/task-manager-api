package com.arnavgautam.taskmanager.dto.task;

import com.arnavgautam.taskmanager.enums.Priority;
import com.arnavgautam.taskmanager.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for task responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Task response")
public class TaskResponse {

    @Schema(description = "Task ID", example = "1")
    private Long id;

    @Schema(description = "Task title", example = "Complete project documentation")
    private String title;

    @Schema(description = "Task description", example = "Write comprehensive README and API documentation")
    private String description;

    @Schema(description = "Task status", example = "TODO")
    private TaskStatus status;

    @Schema(description = "Task priority", example = "HIGH")
    private Priority priority;

    @Schema(description = "Due date", example = "2024-01-15")
    private LocalDate dueDate;

    @Schema(description = "Creation timestamp")
    private Instant createdAt;

    @Schema(description = "Last update timestamp")
    private Instant updatedAt;

    @Schema(description = "Task owner information")
    private OwnerInfo owner;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OwnerInfo {
        private Long id;
        private String username;
        private String email;
    }
}
