package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.GameStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GameStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameStatusRepository extends JpaRepository<GameStatus, Long> {}
