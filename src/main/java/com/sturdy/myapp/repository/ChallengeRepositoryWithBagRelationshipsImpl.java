package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.Challenge;
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
public class ChallengeRepositoryWithBagRelationshipsImpl implements ChallengeRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Challenge> fetchBagRelationships(Optional<Challenge> challenge) {
        return challenge.map(this::fetchChallenges);
    }

    @Override
    public Page<Challenge> fetchBagRelationships(Page<Challenge> challenges) {
        return new PageImpl<>(fetchBagRelationships(challenges.getContent()), challenges.getPageable(), challenges.getTotalElements());
    }

    @Override
    public List<Challenge> fetchBagRelationships(List<Challenge> challenges) {
        return Optional.of(challenges).map(this::fetchChallenges).orElse(Collections.emptyList());
    }

    Challenge fetchChallenges(Challenge result) {
        return entityManager
            .createQuery(
                "select challenge from Challenge challenge left join fetch challenge.challenges where challenge is :challenge",
                Challenge.class
            )
            .setParameter("challenge", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Challenge> fetchChallenges(List<Challenge> challenges) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, challenges.size()).forEach(index -> order.put(challenges.get(index).getId(), index));
        List<Challenge> result = entityManager
            .createQuery(
                "select distinct challenge from Challenge challenge left join fetch challenge.challenges where challenge in :challenges",
                Challenge.class
            )
            .setParameter("challenges", challenges)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
