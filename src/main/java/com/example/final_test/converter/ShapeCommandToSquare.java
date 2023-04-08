package com.example.final_test.converter;

import com.example.final_test.command.ShapeCommand;
import com.example.final_test.domain.Square;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class ShapeCommandToSquare implements Converter<ShapeCommand, Square> {
    @Override
    public Square convert(MappingContext<ShapeCommand, Square> mappingContext) {
        ShapeCommand shapeCommand = mappingContext.getSource();
        Square square = new Square();
        square.setType(shapeCommand.getType());
        square.setA(shapeCommand.getParameters().get(0));
        return square;
    }
}
