package com.example.final_test.filter;

import com.example.final_test.domain.Shape;
import com.example.final_test.domain.Square;
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
public class SquareFilter implements Filter {
    private static final String TYPE = "SQUARE";
    private static final String A = "a";
    private static final String A_FROM = "aFrom";
    private static final String A_TO = "aTo";

    @Override
    public Expression<BigDecimal> getExpressionArea(Root<Shape> root, CriteriaBuilder cb) {
        Root<Square> squareRoot = getSquareRoot(root, cb);
        return cb.prod(squareRoot.get(A), squareRoot.get(A)).as(BigDecimal.class);
    }

    @Override
    public Expression<BigDecimal> getExpressionPerimeter(Root<Shape> root, CriteriaBuilder cb) {
        return cb.prod(4, getSquareRoot(root, cb).get(A)).as(BigDecimal.class);
    }

    @Override
    public Specification<Shape> applyCustomFilter(Map<String, String> map) {
        return Specification.where(nonNull(map.getOrDefault(A_FROM, null)) ? getAttrGreaterOrEq(map.get(A_FROM)) : null)
                .and(nonNull(map.getOrDefault(A_TO, null)) ? getAttrLessOrEq(map.get(A_TO)) : null);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    private Specification<Shape> getAttrGreaterOrEq(String valueFrom) {
        return (root, query, cb) -> cb.ge(getSquareRoot(root, cb).get(A), new BigDecimal(valueFrom));
    }

    private Specification<Shape> getAttrLessOrEq(String valueTo) {
        return (root, query, cb) -> cb.le(getSquareRoot(root, cb).get(A), new BigDecimal(valueTo));
    }

    private Root<Square> getSquareRoot(Root<Shape> root, CriteriaBuilder cb) {
        return cb.treat(root, Square.class);
    }
}
