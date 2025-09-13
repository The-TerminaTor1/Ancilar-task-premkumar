package com.ancilar.ancilartask.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ancilar.ancilartask.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, String> {

    List<Comment> findbyPostID(String postId);

}
