package com.example.final_test.repository.Specification;

import com.example.final_test.domain.Shape;
import com.example.final_test.filter.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static liquibase.repackaged.org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@RequiredArgsConstructor
public class ShapeSpecification {
    private final List<Filter> filters;
    private static final String TYPE_FIELD = "type";
    private static final String CREATED_BY_FIELD = "createdBy";
    private static final String CREATED_AT_FIELD = "createdAt";
    private static final String CREATED_AT_FROM = "createdAtFrom";
    private static final String CREATED_AT_TO = "createdAtTo";
    private static final String AREA_FROM = "areaFrom";
    private static final String AREA_TO = "areaTo";
    private static final String PERIMETER_FROM = "perimeterFrom";
    private static final String PERIMETER_TO = "perimeterTo";

    public Specification<Shape> getByFilter(Map<String, String> map) {
        return Specification.where(isNotBlank(map.getOrDefault(TYPE_FIELD, null)) ? getStringAttrEq(TYPE_FIELD, map.get(TYPE_FIELD)) : null)
                .and(isNotBlank(map.getOrDefault(CREATED_BY_FIELD, null)) ? getStringAttrEq(CREATED_BY_FIELD, map.get(CREATED_BY_FIELD)) : null)
                .and(nonNull(map.getOrDefault(CREATED_AT_FROM, null)) ? getDateFromAttr(CREATED_AT_FIELD, map.get(CREATED_AT_FROM)) : null)
                .and(nonNull(map.getOrDefault(CREATED_AT_TO, null)) ? getDateToAttr(CREATED_AT_FIELD, map.get(CREATED_AT_TO)) : null)
                .and(nonNull(map.getOrDefault(AREA_FROM, null)) ? getAreaFrom(map.get(AREA_FROM)) : null)
                .and(nonNull(map.getOrDefault(AREA_TO, null)) ? getAreaTo(map.get(AREA_TO)) : null)
                .and(nonNull(map.getOrDefault(PERIMETER_FROM, null)) ? getPerimeterFrom(map.get(PERIMETER_FROM)) : null)
                .and(nonNull(map.getOrDefault(PERIMETER_TO, null)) ? getPerimeterTo(map.get(PERIMETER_TO)) : null)
                .and(getCustomFilter(map));
    }

    public Specification<Shape> getStringAttrEq(String paramName, String value) {
        return (root, query, cb) ->
                cb.equal(root.get(paramName), value);
    }


    public Specification<Shape> getDateFromAttr(String paramName, String createdFromValue) {
        LocalDate createdFrom = LocalDate.parse(createdFromValue);
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(paramName), createdFrom);
    }

    public Specification<Shape> getDateToAttr(String paramName, String createdToValue) {
        LocalDate createdTo = LocalDate.parse(createdToValue);
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(paramName), createdTo);
    }

    public Specification<Shape> getAreaFrom(String areaFromValue) {
        BigDecimal areaFrom = new BigDecimal(areaFromValue);
        List<Specification<Shape>> specifications = filters.stream().map(filter -> filter.getAreaFrom(TYPE_FIELD, areaFrom)).toList();
        return getSpecOrFromList(specifications);
    }

    public Specification<Shape> getAreaTo(String areaToValue) {
        BigDecimal areaTo = new BigDecimal(areaToValue);
        List<Specification<Shape>> specifications = filters.stream().map(filter -> filter.getAreaTo(TYPE_FIELD, areaTo)).toList();
        return getSpecOrFromList(specifications);
    }

    public Specification<Shape> getPerimeterFrom(String perimeterFromValue) {
        BigDecimal perimeterFrom = new BigDecimal(perimeterFromValue);
        List<Specification<Shape>> specifications = filters.stream().map(filter -> filter.getPerimeterFrom(TYPE_FIELD, perimeterFrom)).toList();
        return getSpecOrFromList(specifications);
    }

    public Specification<Shape> getPerimeterTo(String perimeterToValue) {
        BigDecimal perimeterTo = new BigDecimal(perimeterToValue);
        List<Specification<Shape>> specifications = filters.stream().map(filter -> filter.getPerimeterTo(TYPE_FIELD, perimeterTo)).toList();
        return getSpecOrFromList(specifications);
    }

    public Specification<Shape> getCustomFilter(Map<String, String> map) {
        List<Specification<Shape>> specifications = filters.stream().map(filter -> filter.applyCustomFilter(map)).toList();
        return getSpecAndFromList(specifications);
    }

    private Specification<Shape> getSpecOrFromList(List<Specification<Shape>> specifications) {
        Specification<Shape> spec = Specification.where(null);
        for (Specification<Shape> s : specifications) {
            spec = spec.or(s);
        }
        return spec;
    }

    private Specification<Shape> getSpecAndFromList(List<Specification<Shape>> specifications) {
        Specification<Shape> spec = Specification.where(null);
        for (Specification<Shape> s : specifications) {
            spec = spec.and(s);
        }
        return spec;
    }


}
