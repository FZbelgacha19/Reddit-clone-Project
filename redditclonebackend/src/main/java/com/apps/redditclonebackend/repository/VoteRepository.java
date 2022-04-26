package com.apps.redditclonebackend.repository;

import com.apps.redditclonebackend.model.Post;
import com.apps.redditclonebackend.model.User;
import com.apps.redditclonebackend.model.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
