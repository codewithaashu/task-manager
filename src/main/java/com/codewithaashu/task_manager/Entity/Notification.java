package com.codewithaashu.task_manager.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private String notiType;

    @ManyToMany
    @JoinTable(name = "notification_user", joinColumns = @JoinColumn(name = "notifi_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> team;

    @OneToOne
    @JoinColumn(name = "task")
    private Task task;

    @ManyToMany
    @JoinTable(name = "notification_userRead", joinColumns = @JoinColumn(name = "notifi_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> isRead;

    // timestamp
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        if (notiType == null) {
            notiType = "alert";
        }
    }

}
