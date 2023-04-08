package com.example.final_test.service;

import com.example.final_test.domain.Role;
import com.example.final_test.domain.Shape;
import com.example.final_test.domain.User;
import com.example.final_test.exception.ObjectNotFoundException;
import com.example.final_test.repository.ShapeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorChecker {
    private final ShapeRepository shapeRepository;
    private final LoggedUserService loggedUserService;

    public boolean hasPermissionToUpdate(Long shapeId) {
        Shape shape = shapeRepository.findById(shapeId).orElseThrow(() -> new ObjectNotFoundException(shapeId, Shape.class.getName()));
        User currentUser = loggedUserService.getCurrentUser();
        return isAdmin(currentUser) || isAuthor(shape, currentUser);
    }

    public boolean isAuthor(Shape shape, User currentUser) {
        return shape.getCreatedBy().equals(currentUser.getLogin());
    }

    public boolean isAdmin(User currentUser) {
        return Role.ADMIN.toString().equals(currentUser.getRole());
    }
}
