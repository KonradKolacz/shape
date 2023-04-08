package com.example.final_test.repository;

import com.example.final_test.domain.Shape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ShapeRepository extends JpaRepository<Shape, Long>, JpaSpecificationExecutor<Shape> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Shape> findWithOptimisticLockById(Long id);
}
