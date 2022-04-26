package com.apps.redditclonebackend.repository;

import com.apps.redditclonebackend.model.Comment;

import com.apps.redditclonebackend.model.Post;
import com.apps.redditclonebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
