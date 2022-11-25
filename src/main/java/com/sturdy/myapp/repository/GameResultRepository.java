package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.GameResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GameResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameResultRepository extends JpaRepository<GameResult, Long> {}
