package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.RoomConnection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RoomConnection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomConnectionRepository extends JpaRepository<RoomConnection, Long> {}
