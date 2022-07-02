package com.easy.easyeatsserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonProperty("total_price")
    @Column(name = "total_price")
    private double totalPrice;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OrderedItem> orderedItemList;

    public Cart() {
    }

    public Cart(String id, double totalPrice) {
        this.id = id;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderedItem> getOrderedItemList() {
        return orderedItemList;
    }

    public void setOrderedItemList(List<OrderedItem> orderedItemList) {
        this.orderedItemList = orderedItemList;
    }
}
