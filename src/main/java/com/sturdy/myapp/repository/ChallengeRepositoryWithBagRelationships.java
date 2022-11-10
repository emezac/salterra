package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.Challenge;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ChallengeRepositoryWithBagRelationships {
    Optional<Challenge> fetchBagRelationships(Optional<Challenge> challenge);

    List<Challenge> fetchBagRelationships(List<Challenge> challenges);

    Page<Challenge> fetchBagRelationships(Page<Challenge> challenges);
}
