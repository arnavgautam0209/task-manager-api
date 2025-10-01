package com.arnavgautam.taskmanager.service;

import com.arnavgautam.taskmanager.dto.task.TaskRequest;
import com.arnavgautam.taskmanager.dto.task.TaskResponse;
import com.arnavgautam.taskmanager.entity.Task;
import com.arnavgautam.taskmanager.entity.User;
import com.arnavgautam.taskmanager.enums.Priority;
import com.arnavgautam.taskmanager.enums.TaskStatus;
import com.arnavgautam.taskmanager.exception.ResourceNotFoundException;
import com.arnavgautam.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Service class for Task operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    /**
     * Create a new task
     */
    public TaskResponse createTask(TaskRequest request, User owner) {
        log.debug("Creating new task with title: {} for user: {}", request.getTitle(), owner.getUsername());

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO)
                .priority(request.getPriority() != null ? request.getPriority() : Priority.MEDIUM)
                .dueDate(request.getDueDate())
                .owner(owner)
                .build();

        Task savedTask = taskRepository.save(task);
        log.info("Task created successfully with ID: {}", savedTask.getId());
        return mapToResponse(savedTask);
    }

    /**
     * Get all tasks for a user with optional filters
     */
    @Transactional(readOnly = true)
    public Page<TaskResponse> getTasks(User owner, TaskStatus status, Priority priority, LocalDate dueDate, Pageable pageable) {
        log.debug("Fetching tasks for user: {} with filters - status: {}, priority: {}, dueDate: {}", 
                  owner.getUsername(), status, priority, dueDate);

        Page<Task> tasks = taskRepository.findByOwnerWithFilters(owner, status, priority, dueDate, pageable);
        return tasks.map(this::mapToResponse);
    }

    /**
     * Get task by ID
     */
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id, User owner) {
        log.debug("Fetching task with ID: {} for user: {}", id, owner.getUsername());

        Task task = taskRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));

        return mapToResponse(task);
    }

    /**
     * Update task
     */
    public TaskResponse updateTask(Long id, TaskRequest request, User owner) {
        log.debug("Updating task with ID: {} for user: {}", id, owner.getUsername());

        Task task = taskRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));

        // Update fields
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null ? request.getStatus() : task.getStatus());
        task.setPriority(request.getPriority() != null ? request.getPriority() : task.getPriority());
        task.setDueDate(request.getDueDate());

        Task savedTask = taskRepository.save(task);
        log.info("Task updated successfully with ID: {}", savedTask.getId());
        return mapToResponse(savedTask);
    }

    /**
     * Delete task
     */
    public void deleteTask(Long id, User owner) {
        log.debug("Deleting task with ID: {} for user: {}", id, owner.getUsername());

        Task task = taskRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));

        taskRepository.delete(task);
        log.info("Task deleted successfully with ID: {}", id);
    }

    /**
     * Map Task entity to TaskResponse DTO
     */
    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .owner(TaskResponse.OwnerInfo.builder()
                        .id(task.getOwner().getId())
                        .username(task.getOwner().getUsername())
                        .email(task.getOwner().getEmail())
                        .build())
                .build();
    }
}
