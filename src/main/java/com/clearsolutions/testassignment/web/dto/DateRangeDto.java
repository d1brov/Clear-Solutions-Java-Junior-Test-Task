package com.clearsolutions.testassignment.web.dto;

import java.time.LocalDate;

import com.clearsolutions.testassignment.validation.DateRange;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@DateRange(message = "{message.daterange}")
public class DateRangeDto {
    private LocalDate from;
    private LocalDate to;
}