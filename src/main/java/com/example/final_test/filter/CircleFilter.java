package com.example.final_test.filter;

import com.example.final_test.domain.Circle;
import com.example.final_test.domain.Shape;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Map;

import static java.util.Objects.nonNull;


@Component
@AllArgsConstructor
public class CircleFilter implements Filter {
    private static final String TYPE = "CIRCLE";
    private static final String RADIUS = "r";
    private static final String RADIUS_FROM = "rFrom";
    private static final String RADIUS_TO = "rTo";

    @Override
    public Expression<BigDecimal> getExpressionArea(Root<Shape> root, CriteriaBuilder cb) {
        Root<Circle> circleRoot = getCircleRoot(root, cb);
        return cb.prod(cb.prod(circleRoot.get(RADIUS), circleRoot.get(RADIUS)), Math.PI).as(BigDecimal.class);
    }

    @Override
    public Expression<BigDecimal> getExpressionPerimeter(Root<Shape> root, CriteriaBuilder cb) {
        return cb.prod(cb.prod(2, getCircleRoot(root, cb).get(RADIUS)), Math.PI).as(BigDecimal.class);
    }

    @Override
    public Specification<Shape> applyCustomFilter(Map<String, String> map) {
        return Specification.where(nonNull(map.getOrDefault(RADIUS_FROM, null)) ? getAttrGreaterOrEq(RADIUS, map.get(RADIUS_FROM)) : null)
                .and(nonNull(map.getOrDefault(RADIUS_TO, null)) ? getAttrLessOrEq(RADIUS, map.get(RADIUS_TO)) : null);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public Specification<Shape> getAttrGreaterOrEq(String paramName, String valueFrom) {
        return (root, query, cb) -> cb.ge(getCircleRoot(root, cb).get(paramName), new BigDecimal(valueFrom));
    }

    public Specification<Shape> getAttrLessOrEq(String paramName, String valueTo) {
        return (root, query, cb) -> cb.le(getCircleRoot(root, cb).get(paramName), new BigDecimal(valueTo));
    }

    private Root<Circle> getCircleRoot(Root<Shape> root, CriteriaBuilder cb) {
        return cb.treat(root, Circle.class);
    }
}
