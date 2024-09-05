package com.assignment.task_manager.repo;

import com.assignment.task_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
