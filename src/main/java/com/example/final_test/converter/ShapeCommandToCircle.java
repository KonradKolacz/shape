package com.example.final_test.converter;

import com.example.final_test.command.ShapeCommand;
import com.example.final_test.domain.Circle;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class ShapeCommandToCircle implements Converter<ShapeCommand, Circle> {
    @Override
    public Circle convert(MappingContext<ShapeCommand, Circle> mappingContext) {
        ShapeCommand source = mappingContext.getSource();
        Circle circle = new Circle();
        circle.setType(source.getType());
        circle.setR(source.getParameters().get(0));
        return circle;
    }

}
