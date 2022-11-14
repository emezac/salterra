package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.PrizePool;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PrizePool entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrizePoolRepository extends JpaRepository<PrizePool, Long> {}
