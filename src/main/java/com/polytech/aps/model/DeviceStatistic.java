package com.polytech.aps.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Table(name = "device")
public class DeviceStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "common_device_time")
    private Double commonDeviceTime;
}
