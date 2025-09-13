package com.ancilar.ancilartask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ancilar.ancilartask.entities.Post;

public interface PostRepository extends JpaRepository<Post, String> {
}
