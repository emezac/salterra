package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.ProfileGameStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProfileGameStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileGameStatusRepository extends JpaRepository<ProfileGameStatus, Long> {}
