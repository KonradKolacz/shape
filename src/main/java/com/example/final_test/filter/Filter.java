package com.example.final_test.filter;

import com.example.final_test.domain.Shape;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Map;

public interface Filter {

    Expression<BigDecimal> getExpressionArea(Root<Shape> root, CriteriaBuilder cb);

    Expression<BigDecimal> getExpressionPerimeter(Root<Shape> root, CriteriaBuilder cb);

    Specification<Shape> applyCustomFilter(Map<String, String> map);

    String getType();

    default Specification<Shape> getAreaFrom(String typeField, BigDecimal areaFrom) {
        return (root, query, cb) -> cb.ge(getExpressionAreaByType(cb, root, typeField), areaFrom);
    }

    default Specification<Shape> getAreaTo(String typeField, BigDecimal areaTo) {
        return (root, query, cb) -> cb.le(getExpressionAreaByType(cb, root, typeField), areaTo);
    }

    default Specification<Shape> getPerimeterFrom(String typeField, BigDecimal perimeterFrom) {
        return (root, query, cb) -> cb.ge(getExpressionPerimeterByType(cb, root, typeField), perimeterFrom);
    }

    default Specification<Shape> getPerimeterTo(String typeField, BigDecimal perimeterTo) {
        return (root, query, cb) -> cb.le(getExpressionPerimeterByType(cb, root, typeField), perimeterTo);
    }

    private Expression<BigDecimal> getExpressionAreaByType(CriteriaBuilder cb, Root<Shape> root, String typeField) {
        return cb.selectCase()
                .when(cb.equal(root.get(typeField), getType()), getExpressionArea(root, cb))
                .as(BigDecimal.class);
    }

    private Expression<BigDecimal> getExpressionPerimeterByType(CriteriaBuilder cb, Root<Shape> root, String typeField) {
        return cb.selectCase()
                .when(cb.equal(root.get(typeField), getType()), getExpressionPerimeter(root, cb))
                .as(BigDecimal.class);
    }
}
