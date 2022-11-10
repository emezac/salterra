package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.ActivityMoves;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ActivityMoves entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityMovesRepository extends JpaRepository<ActivityMoves, Long> {}
