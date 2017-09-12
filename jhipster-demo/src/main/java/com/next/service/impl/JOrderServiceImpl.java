package com.next.service.impl;

import com.next.service.JOrderService;
import com.next.domain.JOrder;
import com.next.repository.JOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing JOrder.
 */
@Service
@Transactional
public class JOrderServiceImpl implements JOrderService
{

    private final Logger log = LoggerFactory.getLogger(JOrderServiceImpl.class);

    @Autowired
    private JOrderRepository jOrderRepository;

    /**
     * Save a jOrder.
     *
     * @param jOrder
     *            the entity to save
     * @return the persisted entity
     */
    @Override
    public JOrder save(JOrder jOrder)
    {
        log.debug("Request to save JOrder : {}", jOrder);
        return jOrderRepository.save(jOrder);
    }

    /**
     * Get all the jOrders.
     *
     * @param pageable
     *            the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JOrder> findAll(Pageable pageable)
    {
        log.debug("Request to get all JOrders");
        return jOrderRepository.findAll(pageable);
    }

    /**
     * Get one jOrder by id.
     *
     * @param id
     *            the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JOrder findOne(Long id)
    {
        log.debug("Request to get JOrder : {}", id);
        return jOrderRepository.findOne(id);
    }

    /**
     * Delete the jOrder by id.
     *
     * @param id
     *            the id of the entity
     */
    @Override
    public void delete(Long id)
    {
        log.debug("Request to delete JOrder : {}", id);
        jOrderRepository.delete(id);
    }
}
