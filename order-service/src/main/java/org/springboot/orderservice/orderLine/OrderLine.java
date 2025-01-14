package org.springboot.orderservice.orderLine;

import jakarta.persistence.*;
import lombok.*;
import org.springboot.orderservice.order.OrderApp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity

public class OrderLine {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderApp order;
    private Integer certificationId;
    private double quantity;
}
