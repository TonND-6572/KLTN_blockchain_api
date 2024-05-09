package com.example.my_blockchain.model.entity.UDT;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.*;

@UserDefinedType(value = "udt_item_attribute")
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder 
@ToString
public class Item_attribute {
    Long item_id;
    String name;
    Integer quantity;

    @Column("unit_price")
    Integer unitPrice;
    Float weight;
    Float length;
    Float height;
    Float width;
    
    @Column("item_category")
    String itemCategory;
}
