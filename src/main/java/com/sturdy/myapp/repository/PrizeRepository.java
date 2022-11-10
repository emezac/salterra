package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.Prize;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Prize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrizeRepository extends JpaRepository<Prize, Long> {}
