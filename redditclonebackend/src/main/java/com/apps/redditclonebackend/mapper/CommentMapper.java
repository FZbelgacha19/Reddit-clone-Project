package com.apps.redditclonebackend.mapper;


import com.apps.redditclonebackend.dto.CommentsDto;
import com.apps.redditclonebackend.model.Comment;
import com.apps.redditclonebackend.model.Post;
import com.apps.redditclonebackend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source ="commentDTO.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source ="post")
    Comment map(CommentsDto commentDTO, Post post, User user);


    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentsDto mapToDto(Comment comment);
}
