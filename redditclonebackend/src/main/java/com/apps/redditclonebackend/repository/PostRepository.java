package com.apps.redditclonebackend.repository;

import org.springframework.stereotype.Repository;

import com.apps.redditclonebackend.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
    

