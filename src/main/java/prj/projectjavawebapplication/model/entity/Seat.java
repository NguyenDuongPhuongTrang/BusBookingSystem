package prj.projectjavawebapplication.model.entity;

import jakarta.persistence.*;
import lombok.*;
import prj.projectjavawebapplication.enums.SeatStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "seats", uniqueConstraints = @UniqueConstraint(columnNames = {"trip_id", "seatNumber"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Trip trip;

    private String seatNumber;

    private Integer floor;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    private LocalDateTime holdUntil;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "hold_by_user_id")
    private User holdByUser;
}

