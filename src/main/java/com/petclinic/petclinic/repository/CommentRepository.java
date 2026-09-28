package com.petclinic.petclinic.repository;

import com.petclinic.petclinic.entity.Comment;
import com.petclinic.petclinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countByAuthor(User author);
    List<Comment> findByAuthorOrderByCreatedAtDesc(User author);
}