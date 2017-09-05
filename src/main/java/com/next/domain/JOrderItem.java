package com.next.domain;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * A JOrderItem.
 */
@Entity
@Table(name = "j_order_item")
public class JOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "sku")
    private String sku;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "item_desc")
    private String itemDesc;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true) 
    @JoinColumn(name="order_id")
    private JOrder order;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
 
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JOrderItem jOrderItem = (JOrderItem) o;
        if (jOrderItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jOrderItem.getId());
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public Integer getQty()
    {
        return qty;
    }

    public void setQty(Integer qty)
    {
        this.qty = qty;
    }

    public String getItemDesc()
    {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc)
    {
        this.itemDesc = itemDesc;
    }

    public JOrder getOrder()
    {
        return order;
    }

    public void setOrder(JOrder order)
    {
        this.order = order;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JOrderItem{" +
            "id=" + getId() +
            ", sku='" + getSku() + "'" +
            ", qty='" + getQty() + "'" +
            ", itemDesc='" + getItemDesc() + "'" +
            "}";
    }
}
