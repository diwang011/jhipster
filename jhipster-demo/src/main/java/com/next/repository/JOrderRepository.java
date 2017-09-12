package com.next.repository;

import com.next.domain.JOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the JOrder entity.
 */
@Repository
public interface JOrderRepository extends JpaRepository<JOrder, Long>
{

}
