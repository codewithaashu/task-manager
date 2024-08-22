package com.codewithaashu.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithaashu.task_manager.Entity.Activites;

public interface ActivitiesRepository extends JpaRepository<Activites, Long> {

}
