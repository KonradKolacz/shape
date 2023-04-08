package com.example.final_test.converter;

import com.example.final_test.domain.User;
import com.example.final_test.dto.UserDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class UserToUserDto implements Converter<User, UserDto> {
    @Override
    public UserDto convert(MappingContext<User, UserDto> mappingContext) {
        User user = mappingContext.getSource();
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .login(user.getLogin())
                .role(user.getRole())
                .numberOfShapes(user.getShapes()!=null ? user.getShapes().size(): 0)
                .build();
    }
}
