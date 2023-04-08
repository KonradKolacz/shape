package com.example.final_test.converter;

import com.example.final_test.command.ShapeCommand;
import com.example.final_test.domain.Rectangle;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class ShapeCommandToRectangle implements Converter<ShapeCommand, Rectangle> {
    @Override
    public Rectangle convert(MappingContext<ShapeCommand, Rectangle> mappingContext) {
        ShapeCommand shapeCommand = mappingContext.getSource();
        Rectangle rectangle = new Rectangle();
        rectangle.setType(shapeCommand.getType());
        rectangle.setA(shapeCommand.getParameters().get(0));
        rectangle.setB(shapeCommand.getParameters().get(1));
        return rectangle;
    }
}
