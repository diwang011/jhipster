package com.next.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.next.domain.JOrderItem;
import com.next.service.JOrderItemService;
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
 * REST controller for managing JOrderItem.
 */
@RestController
@RequestMapping("/api")
public class JOrderItemResource {

    private final Logger log = LoggerFactory.getLogger(JOrderItemResource.class);

    private static final String ENTITY_NAME = "jOrderItem";

    private final JOrderItemService jOrderItemService;

    public JOrderItemResource(JOrderItemService jOrderItemService) {
        this.jOrderItemService = jOrderItemService;
    }

    /**
     * POST  /j-order-items : Create a new jOrderItem.
     *
     * @param jOrderItem the jOrderItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jOrderItem, or with status 400 (Bad Request) if the jOrderItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/j-order-items")
    @Timed
    public ResponseEntity<JOrderItem> createJOrderItem(@RequestBody JOrderItem jOrderItem) throws URISyntaxException {
        log.debug("REST request to save JOrderItem : {}", jOrderItem);
        if (jOrderItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jOrderItem cannot already have an ID")).body(null);
        }
        JOrderItem result = jOrderItemService.save(jOrderItem);
        return ResponseEntity.created(new URI("/api/j-order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /j-order-items : Updates an existing jOrderItem.
     *
     * @param jOrderItem the jOrderItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jOrderItem,
     * or with status 400 (Bad Request) if the jOrderItem is not valid,
     * or with status 500 (Internal Server Error) if the jOrderItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/j-order-items")
    @Timed
    public ResponseEntity<JOrderItem> updateJOrderItem(@RequestBody JOrderItem jOrderItem) throws URISyntaxException {
        log.debug("REST request to update JOrderItem : {}", jOrderItem);
        if (jOrderItem.getId() == null) {
            return createJOrderItem(jOrderItem);
        }
        JOrderItem result = jOrderItemService.save(jOrderItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jOrderItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /j-order-items : get all the jOrderItems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jOrderItems in body
     */
    @GetMapping("/j-order-items")
    @Timed
    public ResponseEntity<List<JOrderItem>> getAllJOrderItems(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of JOrderItems");
        Page<JOrderItem> page = jOrderItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/j-order-items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /j-order-items/:id : get the "id" jOrderItem.
     *
     * @param id the id of the jOrderItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jOrderItem, or with status 404 (Not Found)
     */
    @GetMapping("/j-order-items/{id}")
    @Timed
    public ResponseEntity<JOrderItem> getJOrderItem(@PathVariable Long id) {
        log.debug("REST request to get JOrderItem : {}", id);
        JOrderItem jOrderItem = jOrderItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jOrderItem));
    }

    /**
     * DELETE  /j-order-items/:id : delete the "id" jOrderItem.
     *
     * @param id the id of the jOrderItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/j-order-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteJOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete JOrderItem : {}", id);
        jOrderItemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
