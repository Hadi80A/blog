package org.hammasir.blog.dto.mapper;

import org.hammasir.blog.dto.PostDto;
import org.hammasir.blog.dto.mapper.UserMapper;
import org.hammasir.blog.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
    @Mapping(target = "author.posts",ignore = true)
    @Mapping(target = "author.password",ignore = true)
    @Mapping(source = "author", target = "author")
    PostDto postToPostDto(Post post);

    @Mapping(source = "author", target = "author")
    Post postDtoToPost(PostDto postDto);
}
