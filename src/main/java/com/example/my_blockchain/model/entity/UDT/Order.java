package com.example.my_blockchain.model.entity.UDT;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.example.my_blockchain.model.entity.Enum.OrderStatus;

import lombok.*;

@UserDefinedType(value = "udt_order")
@Data 
@NoArgsConstructor @AllArgsConstructor
@Builder
@ToString
public class Order {
    Long order_id;
    LocalDateTime created_at;
    String created_by;

    @Column("total_weight")
    float totalWeight;
    @Column("total_price")
    float totalPrice;

    @Column("notice")
    String note;
    
    OrderStatus status;
    List<ItemAttribute> items;
}
