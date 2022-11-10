package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.ChallengeOption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ChallengeOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChallengeOptionRepository extends JpaRepository<ChallengeOption, Long> {}
