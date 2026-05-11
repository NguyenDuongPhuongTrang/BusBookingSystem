package prj.projectjavawebapplication.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RevenueByRouteDTO {
    private String routeName;
    private BigDecimal revenue;
}