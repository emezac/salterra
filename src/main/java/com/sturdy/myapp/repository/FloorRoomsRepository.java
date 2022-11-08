package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.FloorRooms;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FloorRooms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FloorRoomsRepository extends JpaRepository<FloorRooms, Long> {}
