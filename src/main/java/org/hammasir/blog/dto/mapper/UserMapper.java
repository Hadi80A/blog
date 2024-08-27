package org.hammasir.blog.dto.mapper;

import org.hammasir.blog.dto.UserDto;
import org.hammasir.blog.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "password", ignore = true)
    UserDto userToUserDto(User user);
    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserDto userDto);
}
