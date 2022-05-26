package com.apps.redditclonebackend.service;

import com.apps.redditclonebackend.dto.CommentsDto;
import com.apps.redditclonebackend.exception.PostException;
import com.apps.redditclonebackend.exception.SpringRedditException;
import com.apps.redditclonebackend.exception.UserException;
import com.apps.redditclonebackend.mapper.CommentMapper;
import com.apps.redditclonebackend.model.Comment;
import com.apps.redditclonebackend.model.NotificationEmail;
import com.apps.redditclonebackend.model.Post;
import com.apps.redditclonebackend.model.User;
import com.apps.redditclonebackend.repository.CommentRepository;
import com.apps.redditclonebackend.repository.PostRepository;
import com.apps.redditclonebackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentService {
    private static final String POST_URL = "";

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void createComment(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostException("Post not found with id " + commentsDto.getPostId()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);
        String message = mailContentBuilder.build(post.getUser().getUsername() + " commenter sur votre post, " + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " commenter sur votre post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostException(postId.toString() + " not found"));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserException(userName + " not found"));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit") || comment.contains("fuck") || comment.contains("bitch") || comment.contains("asshole") || comment.contains("dick")) {
            throw new SpringRedditException("Ce Commentaire contient des mots interdits");
        }
        return false;
    }
}
