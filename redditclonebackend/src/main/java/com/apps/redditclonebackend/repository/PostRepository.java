package com.apps.redditclonebackend.repository;

import com.apps.redditclonebackend.model.Subreddit;
import com.apps.redditclonebackend.model.User;
import org.springframework.stereotype.Repository;

import com.apps.redditclonebackend.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
    

