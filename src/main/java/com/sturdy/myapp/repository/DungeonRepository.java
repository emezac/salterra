package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.Dungeon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Dungeon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DungeonRepository extends JpaRepository<Dungeon, Long> {}
