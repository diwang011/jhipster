package com.next.web.rest;

import com.next.JhipsterDemoApp;

import com.next.domain.JOrderItem;
import com.next.repository.JOrderItemRepository;
import com.next.service.JOrderItemService;
import com.next.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JOrderItemResource REST controller.
 *
 * @see JOrderItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class JOrderItemResourceIntTest {

    private static final String DEFAULT_SKU = "AAAAAAAAAA";
    private static final String UPDATED_SKU = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTY = 1;
    private static final Integer UPDATED_QTY = 2;

    private static final String DEFAULT_ITEM_DESC = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_DESC = "BBBBBBBBBB";

    @Autowired
    private JOrderItemRepository jOrderItemRepository;

    @Autowired
    private JOrderItemService jOrderItemService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJOrderItemMockMvc;

    private JOrderItem jOrderItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JOrderItemResource jOrderItemResource = new JOrderItemResource(jOrderItemService);
        this.restJOrderItemMockMvc = MockMvcBuilders.standaloneSetup(jOrderItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JOrderItem createEntity(EntityManager em) {
        JOrderItem jOrderItem = new JOrderItem()
            .sku(DEFAULT_SKU)
            .qty(DEFAULT_QTY)
            .itemDesc(DEFAULT_ITEM_DESC);
        return jOrderItem;
    }

    @Before
    public void initTest() {
        jOrderItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createJOrderItem() throws Exception {
        int databaseSizeBeforeCreate = jOrderItemRepository.findAll().size();

        // Create the JOrderItem
        restJOrderItemMockMvc.perform(post("/api/j-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jOrderItem)))
            .andExpect(status().isCreated());

        // Validate the JOrderItem in the database
        List<JOrderItem> jOrderItemList = jOrderItemRepository.findAll();
        assertThat(jOrderItemList).hasSize(databaseSizeBeforeCreate + 1);
        JOrderItem testJOrderItem = jOrderItemList.get(jOrderItemList.size() - 1);
        assertThat(testJOrderItem.getSku()).isEqualTo(DEFAULT_SKU);
        assertThat(testJOrderItem.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testJOrderItem.getItemDesc()).isEqualTo(DEFAULT_ITEM_DESC);
    }

    @Test
    @Transactional
    public void createJOrderItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jOrderItemRepository.findAll().size();

        // Create the JOrderItem with an existing ID
        jOrderItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJOrderItemMockMvc.perform(post("/api/j-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jOrderItem)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JOrderItem> jOrderItemList = jOrderItemRepository.findAll();
        assertThat(jOrderItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJOrderItems() throws Exception {
        // Initialize the database
        jOrderItemRepository.saveAndFlush(jOrderItem);

        // Get all the jOrderItemList
        restJOrderItemMockMvc.perform(get("/api/j-order-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jOrderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].itemDesc").value(hasItem(DEFAULT_ITEM_DESC.toString())));
    }

    @Test
    @Transactional
    public void getJOrderItem() throws Exception {
        // Initialize the database
        jOrderItemRepository.saveAndFlush(jOrderItem);

        // Get the jOrderItem
        restJOrderItemMockMvc.perform(get("/api/j-order-items/{id}", jOrderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jOrderItem.getId().intValue()))
            .andExpect(jsonPath("$.sku").value(DEFAULT_SKU.toString()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.itemDesc").value(DEFAULT_ITEM_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJOrderItem() throws Exception {
        // Get the jOrderItem
        restJOrderItemMockMvc.perform(get("/api/j-order-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJOrderItem() throws Exception {
        // Initialize the database
        jOrderItemService.save(jOrderItem);

        int databaseSizeBeforeUpdate = jOrderItemRepository.findAll().size();

        // Update the jOrderItem
        JOrderItem updatedJOrderItem = jOrderItemRepository.findOne(jOrderItem.getId());
        updatedJOrderItem
            .sku(UPDATED_SKU)
            .qty(UPDATED_QTY)
            .itemDesc(UPDATED_ITEM_DESC);

        restJOrderItemMockMvc.perform(put("/api/j-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJOrderItem)))
            .andExpect(status().isOk());

        // Validate the JOrderItem in the database
        List<JOrderItem> jOrderItemList = jOrderItemRepository.findAll();
        assertThat(jOrderItemList).hasSize(databaseSizeBeforeUpdate);
        JOrderItem testJOrderItem = jOrderItemList.get(jOrderItemList.size() - 1);
        assertThat(testJOrderItem.getSku()).isEqualTo(UPDATED_SKU);
        assertThat(testJOrderItem.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testJOrderItem.getItemDesc()).isEqualTo(UPDATED_ITEM_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingJOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = jOrderItemRepository.findAll().size();

        // Create the JOrderItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJOrderItemMockMvc.perform(put("/api/j-order-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jOrderItem)))
            .andExpect(status().isCreated());

        // Validate the JOrderItem in the database
        List<JOrderItem> jOrderItemList = jOrderItemRepository.findAll();
        assertThat(jOrderItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJOrderItem() throws Exception {
        // Initialize the database
        jOrderItemService.save(jOrderItem);

        int databaseSizeBeforeDelete = jOrderItemRepository.findAll().size();

        // Get the jOrderItem
        restJOrderItemMockMvc.perform(delete("/api/j-order-items/{id}", jOrderItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JOrderItem> jOrderItemList = jOrderItemRepository.findAll();
        assertThat(jOrderItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JOrderItem.class);
        JOrderItem jOrderItem1 = new JOrderItem();
        jOrderItem1.setId(1L);
        JOrderItem jOrderItem2 = new JOrderItem();
        jOrderItem2.setId(jOrderItem1.getId());
        assertThat(jOrderItem1).isEqualTo(jOrderItem2);
        jOrderItem2.setId(2L);
        assertThat(jOrderItem1).isNotEqualTo(jOrderItem2);
        jOrderItem1.setId(null);
        assertThat(jOrderItem1).isNotEqualTo(jOrderItem2);
    }
}
