package com.polytech.aps.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "application")
@EqualsAndHashCode
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "probability_of_failure")
    private Double probabilityOfFailure;

    @Column(name = "system_request_time_average")
    private Double systemRequestTimeAverage;
}
