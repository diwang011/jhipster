package com.next.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.next.domain.JOrder;
import com.next.service.JOrderService;
import com.next.web.rest.util.HeaderUtil;
import com.next.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JOrder.
 */
@RestController
@RequestMapping("/api")
public class JOrderResource {

    private final Logger log = LoggerFactory.getLogger(JOrderResource.class);

    private static final String ENTITY_NAME = "jOrder";

    private final JOrderService jOrderService;

    public JOrderResource(JOrderService jOrderService) {
        this.jOrderService = jOrderService;
    }

    /**
     * POST  /j-orders : Create a new jOrder.
     *
     * @param jOrder the jOrder to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jOrder, or with status 400 (Bad Request) if the jOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/j-orders")
    @Timed
    public ResponseEntity<JOrder> createJOrder(@RequestBody JOrder jOrder) throws URISyntaxException {
        log.debug("REST request to save JOrder : {}", jOrder);
        if (jOrder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jOrder cannot already have an ID")).body(null);
        }
        JOrder result = jOrderService.save(jOrder);
        return ResponseEntity.created(new URI("/api/j-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /j-orders : Updates an existing jOrder.
     *
     * @param jOrder the jOrder to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jOrder,
     * or with status 400 (Bad Request) if the jOrder is not valid,
     * or with status 500 (Internal Server Error) if the jOrder couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/j-orders")
    @Timed
    public ResponseEntity<JOrder> updateJOrder(@RequestBody JOrder jOrder) throws URISyntaxException {
        log.debug("REST request to update JOrder : {}", jOrder);
        if (jOrder.getId() == null) {
            return createJOrder(jOrder);
        }
        JOrder result = jOrderService.save(jOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /j-orders : get all the jOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jOrders in body
     */
    @GetMapping("/j-orders")
    @Timed
    public ResponseEntity<List<JOrder>> getAllJOrders(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of JOrders");
        Page<JOrder> page = jOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/j-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /j-orders/:id : get the "id" jOrder.
     *
     * @param id the id of the jOrder to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jOrder, or with status 404 (Not Found)
     */
    @GetMapping("/j-orders/{id}")
    @Timed
    public ResponseEntity<JOrder> getJOrder(@PathVariable Long id) {
        log.debug("REST request to get JOrder : {}", id);
        JOrder jOrder = jOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jOrder));
    }

    /**
     * DELETE  /j-orders/:id : delete the "id" jOrder.
     *
     * @param id the id of the jOrder to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/j-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteJOrder(@PathVariable Long id) {
        log.debug("REST request to delete JOrder : {}", id);
        jOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
