package com.next.web.rest;

import com.next.JhipsterDemoApp;

import com.next.domain.JOrder;
import com.next.repository.JOrderRepository;
import com.next.service.JOrderService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JOrderResource REST controller.
 *
 * @see JOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class JOrderResourceIntTest {

    private static final String DEFAULT_ORDER_NUM = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ORDER_DESC = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_DESC = "BBBBBBBBBB";

    @Autowired
    private JOrderRepository jOrderRepository;

    @Autowired
    private JOrderService jOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJOrderMockMvc;

    private JOrder jOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JOrderResource jOrderResource = new JOrderResource(jOrderService);
        this.restJOrderMockMvc = MockMvcBuilders.standaloneSetup(jOrderResource)
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
    public static JOrder createEntity(EntityManager em) {
        JOrder jOrder = new JOrder()
            .orderNum(DEFAULT_ORDER_NUM)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .orderDate(DEFAULT_ORDER_DATE)
            .orderDesc(DEFAULT_ORDER_DESC);
        return jOrder;
    }

    @Before
    public void initTest() {
        jOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createJOrder() throws Exception {
        int databaseSizeBeforeCreate = jOrderRepository.findAll().size();

        // Create the JOrder
        restJOrderMockMvc.perform(post("/api/j-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jOrder)))
            .andExpect(status().isCreated());

        // Validate the JOrder in the database
        List<JOrder> jOrderList = jOrderRepository.findAll();
        assertThat(jOrderList).hasSize(databaseSizeBeforeCreate + 1);
        JOrder testJOrder = jOrderList.get(jOrderList.size() - 1);
        assertThat(testJOrder.getOrderNum()).isEqualTo(DEFAULT_ORDER_NUM);
        assertThat(testJOrder.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testJOrder.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testJOrder.getOrderDesc()).isEqualTo(DEFAULT_ORDER_DESC);
    }

    @Test
    @Transactional
    public void createJOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jOrderRepository.findAll().size();

        // Create the JOrder with an existing ID
        jOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJOrderMockMvc.perform(post("/api/j-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jOrder)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<JOrder> jOrderList = jOrderRepository.findAll();
        assertThat(jOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJOrders() throws Exception {
        // Initialize the database
        jOrderRepository.saveAndFlush(jOrder);

        // Get all the jOrderList
        restJOrderMockMvc.perform(get("/api/j-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNum").value(hasItem(DEFAULT_ORDER_NUM.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderDesc").value(hasItem(DEFAULT_ORDER_DESC.toString())));
    }

    @Test
    @Transactional
    public void getJOrder() throws Exception {
        // Initialize the database
        jOrderRepository.saveAndFlush(jOrder);

        // Get the jOrder
        restJOrderMockMvc.perform(get("/api/j-orders/{id}", jOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderNum").value(DEFAULT_ORDER_NUM.toString()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.orderDesc").value(DEFAULT_ORDER_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJOrder() throws Exception {
        // Get the jOrder
        restJOrderMockMvc.perform(get("/api/j-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJOrder() throws Exception {
        // Initialize the database
        jOrderService.save(jOrder);

        int databaseSizeBeforeUpdate = jOrderRepository.findAll().size();

        // Update the jOrder
        JOrder updatedJOrder = jOrderRepository.findOne(jOrder.getId());
        updatedJOrder
            .orderNum(UPDATED_ORDER_NUM)
            .orderStatus(UPDATED_ORDER_STATUS)
            .orderDate(UPDATED_ORDER_DATE)
            .orderDesc(UPDATED_ORDER_DESC);

        restJOrderMockMvc.perform(put("/api/j-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJOrder)))
            .andExpect(status().isOk());

        // Validate the JOrder in the database
        List<JOrder> jOrderList = jOrderRepository.findAll();
        assertThat(jOrderList).hasSize(databaseSizeBeforeUpdate);
        JOrder testJOrder = jOrderList.get(jOrderList.size() - 1);
        assertThat(testJOrder.getOrderNum()).isEqualTo(UPDATED_ORDER_NUM);
        assertThat(testJOrder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testJOrder.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testJOrder.getOrderDesc()).isEqualTo(UPDATED_ORDER_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingJOrder() throws Exception {
        int databaseSizeBeforeUpdate = jOrderRepository.findAll().size();

        // Create the JOrder

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJOrderMockMvc.perform(put("/api/j-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jOrder)))
            .andExpect(status().isCreated());

        // Validate the JOrder in the database
        List<JOrder> jOrderList = jOrderRepository.findAll();
        assertThat(jOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJOrder() throws Exception {
        // Initialize the database
        jOrderService.save(jOrder);

        int databaseSizeBeforeDelete = jOrderRepository.findAll().size();

        // Get the jOrder
        restJOrderMockMvc.perform(delete("/api/j-orders/{id}", jOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JOrder> jOrderList = jOrderRepository.findAll();
        assertThat(jOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JOrder.class);
        JOrder jOrder1 = new JOrder();
        jOrder1.setId(1L);
        JOrder jOrder2 = new JOrder();
        jOrder2.setId(jOrder1.getId());
        assertThat(jOrder1).isEqualTo(jOrder2);
        jOrder2.setId(2L);
        assertThat(jOrder1).isNotEqualTo(jOrder2);
        jOrder1.setId(null);
        assertThat(jOrder1).isNotEqualTo(jOrder2);
    }
}
