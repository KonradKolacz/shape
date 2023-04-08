package com.example.final_test.repository;

import com.example.final_test.domain.Change;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChangeRepository extends JpaRepository<Change, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "attributeChanges"}
    )
    List<Change> findByShapeId(Long shapeId);
}
