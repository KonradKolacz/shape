package com.example.final_test.service;

import com.example.final_test.command.ShapeCommand;
import com.example.final_test.domain.Shape;
import com.example.final_test.dto.ShapeDto;
import com.example.final_test.exception.DuplicateTypeException;
import com.example.final_test.exception.NoShapeTypeException;
import com.example.final_test.producer.Producer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShapeProducerService {
    private final HashMap<String, Producer> mapProducers;

    public ShapeProducerService(List<Producer> producers) {
        mapProducers = producers.stream()
                .collect(Collectors.toMap(
                        Producer::getType, Function.identity(), detectDuplicatedImplementations(), HashMap::new));
    }

    private BinaryOperator<Producer> detectDuplicatedImplementations() {
        return (prev, next) -> {
            throw new DuplicateTypeException(prev.getClass().getName(), next.getClass().getName());
        };
    }

    public Shape create(ShapeCommand shapeCommand) {
        return Optional.ofNullable(mapProducers.get(shapeCommand.getType()))
                .orElseThrow(() -> new NoShapeTypeException(shapeCommand.getType()))
                .create(shapeCommand);
    }

    public ShapeDto createShapeDto(Shape shape) {
        return Optional.ofNullable(mapProducers.get(shape.getType()))
                .orElseThrow(() -> new NoShapeTypeException(shape.getType()))
                .createShapeDTO(shape);
    }
}
