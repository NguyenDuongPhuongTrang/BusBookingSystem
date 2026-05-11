package prj.projectjavawebapplication.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueByMonthDTO {
    private String month;
    private BigDecimal revenue;
}