package prj.projectjavawebapplication.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopTripDTO {
    private Long tripId;
    private String routeName;
    private Long totalTickets;
    private BigDecimal revenue;
}