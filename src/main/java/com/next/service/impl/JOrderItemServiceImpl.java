package com.next.service.impl;

import com.next.service.JOrderItemService;
import com.next.domain.JOrderItem;
import com.next.repository.JOrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing JOrderItem.
 */
@Service
@Transactional
public class JOrderItemServiceImpl implements JOrderItemService
{

    private final Logger log = LoggerFactory.getLogger(JOrderItemServiceImpl.class);
    @Autowired
    private JOrderItemRepository jOrderItemRepository;

    /**
     * Save a jOrderItem.
     *
     * @param jOrderItem
     *            the entity to save
     * @return the persisted entity
     */
    @Override
    public JOrderItem save(JOrderItem jOrderItem)
    {
        log.debug("Request to save JOrderItem : {}", jOrderItem);
        return jOrderItemRepository.save(jOrderItem);
    }

    /**
     * Get all the jOrderItems.
     *
     * @param pageable
     *            the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JOrderItem> findAll(Pageable pageable)
    {
        log.debug("Request to get all JOrderItems");
        return jOrderItemRepository.findAll(pageable);
    }

    /**
     * Get one jOrderItem by id.
     *
     * @param id
     *            the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JOrderItem findOne(Long id)
    {
        log.debug("Request to get JOrderItem : {}", id);
        return jOrderItemRepository.findOne(id);
    }

    /**
     * Delete the jOrderItem by id.
     *
     * @param id
     *            the id of the entity
     */
    @Override
    public void delete(Long id)
    {
        log.debug("Request to delete JOrderItem : {}", id);
        jOrderItemRepository.delete(id);
    }
}
