package com.codewithaashu.task_manager.Entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.codewithaashu.task_manager.enums.ActivityType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "activites")
@Data
public class Activites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActivityType type;
    private String activity;
    private String date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User by;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    // timestamps
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (type == null) {
            type = ActivityType.assigned;
        }
        if (date == null) {
            date = new Date().toString();
        }
    }
}
