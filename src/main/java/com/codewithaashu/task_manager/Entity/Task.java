package com.codewithaashu.task_manager.Entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.codewithaashu.task_manager.enums.Priority;
import com.codewithaashu.task_manager.enums.Stage;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tasks")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    private String date;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private Stage stage;

    @OneToMany(mappedBy = "task")
    private List<Activites> activites;

    @OneToMany(mappedBy = "task")
    private List<SubTask> subTasks;

    private List<String> assets;
    private Boolean isTrashed;

    @ManyToMany
    @JoinTable(name = "task", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> team;

    // timestamp
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // to define the default valeu
    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = new Date().toString();
        }
        if (isTrashed == null) {
            isTrashed = false;
        }
        if (priority == null) {
            priority = Priority.normal;
        }
        if (stage == null) {
            stage = Stage.todo;
        }

    }
}
