package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.Move;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Move entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {}
