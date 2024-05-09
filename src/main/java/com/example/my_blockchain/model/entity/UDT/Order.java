package com.example.my_blockchain.model.entity.UDT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;
import org.springframework.format.annotation.DateTimeFormat;

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
    List<Item_attribute> items;
}
