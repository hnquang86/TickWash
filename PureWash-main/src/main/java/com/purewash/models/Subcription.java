package com.purewash.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subcription")
public class Subcription extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "subscription_name")
    private String subscriptionName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "duration_time")
    private Date durationTime;

    @JsonIgnore
    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    private List<Order> orderList;

}
