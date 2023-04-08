package com.example.final_test.filter;

import com.example.final_test.domain.Rectangle;
import com.example.final_test.domain.Shape;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Map;

import static java.util.Objects.nonNull;

@Component
@NoArgsConstructor
public class RectangleFilter implements Filter {
    private static final String TYPE = "RECTANGLE";
    private static final String A = "a";
    private static final String A_FROM = "aFrom";
    private static final String A_TO = "aTo";
    private static final String B = "b";
    private static final String B_FROM = "bFrom";
    private static final String B_TO = "bTo";

    @Override
    public Expression<BigDecimal> getExpressionArea(Root<Shape> root, CriteriaBuilder cb) {
        Root<Rectangle> rectangleRoot = getRectangleRoot(root, cb);
        return cb.prod(rectangleRoot.get(A), rectangleRoot.get(B)).as(BigDecimal.class);
    }

    @Override
    public Expression<BigDecimal> getExpressionPerimeter(Root<Shape> root, CriteriaBuilder cb) {
        Root<Rectangle> rectangleRoot = getRectangleRoot(root, cb);
        return cb.sum(cb.prod(2, rectangleRoot.get(A)), cb.prod(2, rectangleRoot.get(B))).as(BigDecimal.class);
    }

    @Override
    public Specification<Shape> applyCustomFilter(Map<String, String> map) {
        return Specification.where(nonNull(map.getOrDefault(A_FROM, null)) ? getAttrGreaterOrEq(A, map.get(A_FROM)) : null)
                .and(nonNull(map.getOrDefault(A_TO, null)) ? getAttrLessOrEq(A, map.get(A_TO)) : null)
                .and(nonNull(map.getOrDefault(B_FROM, null)) ? getAttrGreaterOrEq(B, map.get(B_FROM)) : null)
                .and(nonNull(map.getOrDefault(B_TO, null)) ? getAttrLessOrEq(B, map.get(B_TO)) : null);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public Specification<Shape> getAttrGreaterOrEq(String paramName, String valueFrom) {
        return (root, query, cb) -> cb.ge(getRectangleRoot(root, cb).get(paramName), new BigDecimal(valueFrom));
    }

    public Specification<Shape> getAttrLessOrEq(String paramName, String valueTo) {
        return (root, query, cb) -> cb.le(getRectangleRoot(root, cb).get(paramName), new BigDecimal(valueTo));
    }

    private Root<Rectangle> getRectangleRoot(Root<Shape> root, CriteriaBuilder cb) {
        return cb.treat(root, Rectangle.class);
    }
}
