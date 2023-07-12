package com.devanand.todolistapplication.repo;

import com.devanand.todolistapplication.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
