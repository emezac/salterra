package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.ActivityMoves;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ActivityMovesRepositoryWithBagRelationshipsImpl implements ActivityMovesRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ActivityMoves> fetchBagRelationships(Optional<ActivityMoves> activityMoves) {
        return activityMoves.map(this::fetchChoices);
    }

    @Override
    public Page<ActivityMoves> fetchBagRelationships(Page<ActivityMoves> activityMoves) {
        return new PageImpl<>(
            fetchBagRelationships(activityMoves.getContent()),
            activityMoves.getPageable(),
            activityMoves.getTotalElements()
        );
    }

    @Override
    public List<ActivityMoves> fetchBagRelationships(List<ActivityMoves> activityMoves) {
        return Optional.of(activityMoves).map(this::fetchChoices).orElse(Collections.emptyList());
    }

    ActivityMoves fetchChoices(ActivityMoves result) {
        return entityManager
            .createQuery(
                "select activityMoves from ActivityMoves activityMoves left join fetch activityMoves.choices where activityMoves is :activityMoves",
                ActivityMoves.class
            )
            .setParameter("activityMoves", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<ActivityMoves> fetchChoices(List<ActivityMoves> activityMoves) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, activityMoves.size()).forEach(index -> order.put(activityMoves.get(index).getId(), index));
        List<ActivityMoves> result = entityManager
            .createQuery(
                "select distinct activityMoves from ActivityMoves activityMoves left join fetch activityMoves.choices where activityMoves in :activityMoves",
                ActivityMoves.class
            )
            .setParameter("activityMoves", activityMoves)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
