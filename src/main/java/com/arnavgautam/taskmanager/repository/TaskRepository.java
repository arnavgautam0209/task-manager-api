package com.arnavgautam.taskmanager.repository;

import com.arnavgautam.taskmanager.entity.Task;
import com.arnavgautam.taskmanager.entity.User;
import com.arnavgautam.taskmanager.enums.Priority;
import com.arnavgautam.taskmanager.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Task entity
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find all tasks by owner
     */
    Page<Task> findByOwner(User owner, Pageable pageable);

    /**
     * Find task by ID and owner
     */
    Optional<Task> findByIdAndOwner(Long id, User owner);

    /**
     * Find tasks by owner and status
     */
    Page<Task> findByOwnerAndStatus(User owner, TaskStatus status, Pageable pageable);

    /**
     * Find tasks by owner and priority
     */
    Page<Task> findByOwnerAndPriority(User owner, Priority priority, Pageable pageable);

    /**
     * Find tasks by owner and due date
     */
    Page<Task> findByOwnerAndDueDate(User owner, LocalDate dueDate, Pageable pageable);

    /**
     * Find tasks by owner with multiple filters
     */
    @Query("SELECT t FROM Task t WHERE t.owner = :owner " +
           "AND (:status IS NULL OR t.status = :status) " +
           "AND (:priority IS NULL OR t.priority = :priority) " +
           "AND (:dueDate IS NULL OR t.dueDate = :dueDate)")
    Page<Task> findByOwnerWithFilters(
        @Param("owner") User owner,
        @Param("status") TaskStatus status,
        @Param("priority") Priority priority,
        @Param("dueDate") LocalDate dueDate,
        Pageable pageable
    );

    /**
     * Find overdue tasks
     */
    @Query("SELECT t FROM Task t WHERE t.owner = :owner AND t.dueDate < :currentDate AND t.status != 'DONE'")
    List<Task> findOverdueTasks(@Param("owner") User owner, @Param("currentDate") LocalDate currentDate);

    /**
     * Count tasks by owner and status
     */
    long countByOwnerAndStatus(User owner, TaskStatus status);
}
