package com.apps.redditclonebackend.service;

import com.apps.redditclonebackend.dto.SubredditDto;
import com.apps.redditclonebackend.exception.SpringRedditException;
import com.apps.redditclonebackend.mapper.SubredditMapper;
import com.apps.redditclonebackend.model.Subreddit;
import com.apps.redditclonebackend.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto saveSubreddit(SubredditDto subredditDto) {
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAllSubreddits() {
        return subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDto).collect(Collectors.toList());

    }

    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SpringRedditException("No subreddit found with id: " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

    /*private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder().id(subreddit.getId())
                .name(subreddit.getName())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }

    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
        return Subreddit.builder().name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();
    }*/

}