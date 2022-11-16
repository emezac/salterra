package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.Choices;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Choices entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChoicesRepository extends JpaRepository<Choices, Long> {}
