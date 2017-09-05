package com.next.service;

import com.next.domain.JOrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing JOrderItem.
 */
public interface JOrderItemService {

    /**
     * Save a jOrderItem.
     *
     * @param jOrderItem the entity to save
     * @return the persisted entity
     */
    JOrderItem save(JOrderItem jOrderItem);

    /**
     *  Get all the jOrderItems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JOrderItem> findAll(Pageable pageable);

    /**
     *  Get the "id" jOrderItem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JOrderItem findOne(Long id);

    /**
     *  Delete the "id" jOrderItem.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
