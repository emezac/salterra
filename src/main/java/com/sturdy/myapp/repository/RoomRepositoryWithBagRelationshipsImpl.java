package com.sturdy.myapp.repository;

import com.sturdy.myapp.domain.Room;
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
public class RoomRepositoryWithBagRelationshipsImpl implements RoomRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Room> fetchBagRelationships(Optional<Room> room) {
        return room.map(this::fetchPrizes);
    }

    @Override
    public Page<Room> fetchBagRelationships(Page<Room> rooms) {
        return new PageImpl<>(fetchBagRelationships(rooms.getContent()), rooms.getPageable(), rooms.getTotalElements());
    }

    @Override
    public List<Room> fetchBagRelationships(List<Room> rooms) {
        return Optional.of(rooms).map(this::fetchPrizes).orElse(Collections.emptyList());
    }

    Room fetchPrizes(Room result) {
        return entityManager
            .createQuery("select room from Room room left join fetch room.prizes where room is :room", Room.class)
            .setParameter("room", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Room> fetchPrizes(List<Room> rooms) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, rooms.size()).forEach(index -> order.put(rooms.get(index).getId(), index));
        List<Room> result = entityManager
            .createQuery("select distinct room from Room room left join fetch room.prizes where room in :rooms", Room.class)
            .setParameter("rooms", rooms)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
