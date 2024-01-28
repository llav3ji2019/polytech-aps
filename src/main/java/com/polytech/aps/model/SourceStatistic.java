package com.polytech.aps.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "source")
public class SourceStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_amount")
    private Integer requestAmount;

    @Column(name = "rejected_amount")
    private Integer rejectedAmount;

    @Column(name = "avg_requests_time")
    private Double avgRequestsTime;

    @Column(name = "common_requests_time")
    private Double commonRequestsTime;

    @Column(name = "probability_of_failure")
    private Double probabilityOfFailure;
}
