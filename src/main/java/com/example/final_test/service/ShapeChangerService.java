package com.example.final_test.service;

import com.example.final_test.changer.Changer;
import com.example.final_test.domain.AttributeChange;
import com.example.final_test.domain.Change;
import com.example.final_test.domain.Shape;
import com.example.final_test.exception.AnyChangeException;
import com.example.final_test.exception.DuplicateTypeException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShapeChangerService {

    private final HashMap<String, Changer> mapChangers;

    public ShapeChangerService(List<Changer> changers) {
        mapChangers = changers.stream()
                .collect(Collectors.toMap(
                        Changer::getType, Function.identity(), detectDuplicatedImplementations(), HashMap::new));
    }

    private BinaryOperator<Changer> detectDuplicatedImplementations() {
        return (prev, next) -> {
            throw new DuplicateTypeException(prev.getClass().getName(), next.getClass().getName());
        };
    }

    public Change change(Shape shape, Map<String, BigDecimal> valueToUpdate, String authorRole) {
        Changer changer = mapChangers.get(shape.getType());
        validAttributeNameToUpdate(changer, valueToUpdate);
        List<AttributeChange> attributeChanges = makeAttributeChanges(shape, valueToUpdate, changer);
        validAttributeChanges(attributeChanges);
        return new Change(LocalDateTime.now(), shape.getId(), authorRole, attributeChanges);
    }

    private void validAttributeNameToUpdate(Changer changer, Map<String, BigDecimal> valueToUpdate) {
        valueToUpdate.forEach((attributeName, value) -> changer.validAttributeToUpdate(attributeName));
    }

    private void validAttributeChanges(List<AttributeChange> attributeChanges) {
        if (attributeChanges.size() == 0) {
            throw new AnyChangeException();
        }
    }

    private List<AttributeChange> makeAttributeChanges(Shape shape, Map<String, BigDecimal> valueToUpdate, Changer changer) {
        List<AttributeChange> list = new ArrayList<>();
        valueToUpdate.forEach((attributeName, value) -> {
            AttributeChange aCh = changer.makeAttributeChange(shape, attributeName, value);
            if (aCh != null) {
                list.add(aCh);
            }
        });
        return list;
    }

}
