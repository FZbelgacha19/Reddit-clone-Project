package com.apps.redditclonebackend.mapper;

import com.apps.redditclonebackend.dto.SubredditDto;
import com.apps.redditclonebackend.model.Post;
import com.apps.redditclonebackend.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring") // componentModel means that we are using spring framework
public interface SubredditMapper {

/*    @Mapping(expression = "java(mapPosts(subreddit.getPosts())", target = "numberOfPosts")
    SubredditDto mapSubredditToDto(Subreddit subreddit);*/
    // mapPosts method is used to map the number of posts in the subreddit
    // target is the target property in the SubredditDto
    // expression is the expression to be executed
    @Mapping(source = "posts", target = "numberOfPosts")
    SubredditDto mapSubredditToDto(Subreddit subreddit);


    // mapPosts method is used to map the number of posts in the subreddit
    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}
