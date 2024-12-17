package com.purewash.dto.User.Order;


import com.purewash.models.WashingMachine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private UUID id;
    private Date startTime;
    private Date endTime;
    private String status;
    private WashingMachine washingMachine;
    private String code;
    private int totalTime;
    private boolean validTime;

}
