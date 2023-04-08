package com.example.final_test.producer;

import com.example.final_test.command.ShapeCommand;
import com.example.final_test.domain.Shape;
import com.example.final_test.dto.ShapeDto;

import java.math.BigDecimal;
import java.util.List;

public interface Producer {
    Shape create(ShapeCommand shapeCommand);
    ShapeDto createShapeDTO(Shape shape);
    String getType();
    void validParameters(List<BigDecimal> parameters);
}
