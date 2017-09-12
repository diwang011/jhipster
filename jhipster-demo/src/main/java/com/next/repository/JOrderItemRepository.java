package com.next.repository;

import com.next.domain.JOrderItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the JOrderItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JOrderItemRepository extends JpaRepository<JOrderItem, Long> {

}
