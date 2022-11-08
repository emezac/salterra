package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.DungeonFloors;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DungeonFloors entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DungeonFloorsRepository extends JpaRepository<DungeonFloors, Long> {}
