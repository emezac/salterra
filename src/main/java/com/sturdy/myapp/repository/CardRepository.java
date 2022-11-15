package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.Card;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Card entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {}
