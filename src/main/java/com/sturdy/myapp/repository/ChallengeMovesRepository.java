package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.ChallengeMoves;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ChallengeMoves entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChallengeMovesRepository extends JpaRepository<ChallengeMoves, Long> {}
