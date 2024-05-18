package com.example.my_blockchain.model.entity.UDT;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.*;

@UserDefinedType(value = "udt_item_attribute")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder 
@ToString
public class ItemAttribute {
    @Column("item_id")
    Long itemId;
    String name;
    Integer quantity;

    @Column("unit_price")
    Integer unitPrice;
    Float weight;
    
    @Column("item_category")
    String itemCategory;
}
