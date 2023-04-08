package com.example.final_test.producer;

import com.example.final_test.command.ShapeCommand;
import com.example.final_test.domain.Rectangle;
import com.example.final_test.domain.Shape;
import com.example.final_test.dto.RectangleDto;
import com.example.final_test.dto.ShapeDto;
import com.example.final_test.exception.BadNumberOfParametersExceptions;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RectangleProducer implements Producer {
    private static final String TYPE = "RECTANGLE";
    private static final int NUMBER_OF_PARAMETERS = 2;
    private final ModelMapper modelMapper;

    @Override
    public Shape create(ShapeCommand shapeCommand) {
        validParameters(shapeCommand.getParameters());
        return modelMapper.map(shapeCommand, Rectangle.class);
    }

    @Override
    public ShapeDto createShapeDTO(Shape shape) {
        return modelMapper.map(shape, RectangleDto.class);
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
