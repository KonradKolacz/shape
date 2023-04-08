package com.example.final_test.producer;

import com.example.final_test.command.ShapeCommand;
import com.example.final_test.domain.Circle;
import com.example.final_test.domain.Shape;
import com.example.final_test.dto.CircleDto;
import com.example.final_test.dto.ShapeDto;
import com.example.final_test.exception.BadNumberOfParametersExceptions;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CircleProducer implements Producer {
    private static final int NUMBER_OF_PARAMETERS = 1;
    private static final String TYPE = "CIRCLE";
    private final ModelMapper modelMapper;

    @Override
    public Shape create(ShapeCommand shapeCommand) {
        validParameters(shapeCommand.getParameters());
        return modelMapper.map(shapeCommand, Circle.class);
    }

    @Override
    public ShapeDto createShapeDTO(Shape shape) {
        return modelMapper.map(shape, CircleDto.class);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void validParameters(List<BigDecimal> parameters) {
        if (parameters.size() != NUMBER_OF_PARAMETERS) {
            throw new BadNumberOfParametersExceptions(TYPE, NUMBER_OF_PARAMETERS);
        }
    }


}
