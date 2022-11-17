package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.ActivityMoves;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ActivityMovesRepositoryWithBagRelationships {
    Optional<ActivityMoves> fetchBagRelationships(Optional<ActivityMoves> activityMoves);

    List<ActivityMoves> fetchBagRelationships(List<ActivityMoves> activityMoves);

    Page<ActivityMoves> fetchBagRelationships(Page<ActivityMoves> activityMoves);
}
