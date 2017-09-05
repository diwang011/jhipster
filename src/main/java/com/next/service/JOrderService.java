package com.next.service;

import com.next.domain.JOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing JOrder.
 */
public interface JOrderService {

    /**
     * Save a jOrder.
     *
     * @param jOrder the entity to save
     * @return the persisted entity
     */
    JOrder save(JOrder jOrder);

    /**
     *  Get all the jOrders.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JOrder> findAll(Pageable pageable);

    /**
     *  Get the "id" jOrder.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JOrder findOne(Long id);

    /**
     *  Delete the "id" jOrder.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
