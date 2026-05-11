package prj.projectjavawebapplication.model.entity;

import jakarta.persistence.*;
import lombok.*;
import prj.projectjavawebapplication.enums.TripStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Route route;

    @ManyToOne
    private Bus bus;

    @ManyToOne
    private Driver driver;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    private LocalDateTime createdAt;
}