package com.apps.redditclonebackend.repository;

import com.apps.redditclonebackend.model.Subreddit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {


    
}
