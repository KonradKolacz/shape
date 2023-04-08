package com.example.final_test.service;

import com.example.final_test.command.ShapeCommand;
import com.example.final_test.command.ShapeUpdateCommand;
import com.example.final_test.domain.Shape;
import com.example.final_test.domain.User;
import com.example.final_test.dto.ChangeDto;
import com.example.final_test.dto.ShapeDto;
import com.example.final_test.exception.ObjectNotFoundException;
import com.example.final_test.repository.ChangeRepository;
import com.example.final_test.repository.ShapeRepository;
import com.example.final_test.repository.Specification.ShapeSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShapeService {
    private final ModelMapper modelMapper;
    private final ShapeRepository shapeRepository;
    private final ChangeRepository changeRepository;
    private final LoggedUserService loggedUserService;
    private final ShapeChangerService shapeChangerService;
    private final ShapeProducerService shapeProducerService;
    private final ShapeSpecification shapeSpecification;

    @Transactional
    public ShapeDto add(ShapeCommand shapeCommand) {
        Shape shape = shapeProducerService.create(shapeCommand);
        shape.setUser(loggedUserService.getCurrentUser());
        return shapeProducerService.createShapeDto(shapeRepository.save(shape));
    }

    @Transactional(readOnly = true)
    public Page<ShapeDto> getShapes(Map<String, String> queryParams, Pageable pageable) {
        long total = shapeRepository.count(shapeSpecification.getByFilter(queryParams));
        List<ShapeDto> results = shapeRepository.findAll(shapeSpecification.getByFilter(queryParams), pageable)
                .stream()
                .map(shapeProducerService::createShapeDto)
                .collect(Collectors.toList());
        return new PageImpl<>(results, pageable, total);
    }

    @Transactional
    public ShapeDto updateShape(Long id, ShapeUpdateCommand shapeUpdateCommand) {
        Shape shape = getShapeWithLock(id);
        User currentUser = loggedUserService.getCurrentUser();
        Map<String, BigDecimal> valueToUpdate = shapeUpdateCommand.getValueToUpdate();
        changeRepository.save(shapeChangerService.change(shape, valueToUpdate, currentUser.getRole()));
        return shapeProducerService.createShapeDto(shape);
    }

    @Transactional(readOnly = true)
    public List<ChangeDto> getShapeChanges(Long shapeId) {
        return changeRepository.findByShapeId(shapeId).stream().map(change -> modelMapper.map(change, ChangeDto.class)).toList();
    }

    private Shape getShapeWithLock(Long id) {
        return shapeRepository.findWithOptimisticLockById(id).orElseThrow(() -> new ObjectNotFoundException(id, Shape.class.getName()));
    }

}
