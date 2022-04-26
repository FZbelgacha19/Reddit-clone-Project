package com.apps.redditclonebackend.service;

import com.apps.redditclonebackend.dto.VoteDto;
import com.apps.redditclonebackend.exception.PostException;
import com.apps.redditclonebackend.exception.SpringRedditException;
import com.apps.redditclonebackend.model.Post;
import com.apps.redditclonebackend.model.Vote;
import com.apps.redditclonebackend.repository.PostRepository;
import com.apps.redditclonebackend.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.apps.redditclonebackend.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        System.out.println("voteDto: " + voteDto.getPostId());
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostException("Post n'existe pas - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("Vous avez déjà " + voteDto.getVoteType() + " ce post");

        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
