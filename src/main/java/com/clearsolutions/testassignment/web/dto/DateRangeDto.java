package com.clearsolutions.testassignment.web.dto;

import com.clearsolutions.testassignment.web.validation.DateRange;
import lombok.Data;
import java.time.LocalDate;

@Data
@DateRange(message = "{message.daterange}")
public class DateRangeDto {
    private LocalDate from;
    private LocalDate to;
}