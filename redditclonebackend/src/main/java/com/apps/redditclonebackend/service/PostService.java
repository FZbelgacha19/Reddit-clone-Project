package com.apps.redditclonebackend.service;


import com.apps.redditclonebackend.dto.PostRequest;
import com.apps.redditclonebackend.dto.PostResponse;
import com.apps.redditclonebackend.exception.PostException;
import com.apps.redditclonebackend.exception.SuberdditException;
import com.apps.redditclonebackend.exception.UserException;
import com.apps.redditclonebackend.mapper.PostMapper;
import com.apps.redditclonebackend.model.Post;
import com.apps.redditclonebackend.model.Subreddit;
import com.apps.redditclonebackend.model.User;
import com.apps.redditclonebackend.repository.PostRepository;
import com.apps.redditclonebackend.repository.SubredditRepository;
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
@Transactional // mean that all the methods in this class are transactional
// Transactional is used to make sure that the data is saved in the database
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(
                ()-> new SuberdditException("Subreddit not found")
        );
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }



    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostException("Post not found"));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return postRepository.findAll()
                .stream() // we use stream to convert the list to a stream (stream is a collection of objects)
                .map(postMapper::mapToDto) // we map the list to a list of PostResponse
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId){
        Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(
                () -> new SuberdditException("Subreddit not found with id: " + subredditId)
        );
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);

        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }


    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserException("User not found with username: " + username)
        );

        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
