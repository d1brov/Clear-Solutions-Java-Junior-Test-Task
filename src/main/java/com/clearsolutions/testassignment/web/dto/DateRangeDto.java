package com.clearsolutions.testassignment.web.dto;

import com.clearsolutions.testassignment.web.validation.DateRange;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@DateRange(before = "fromDate", after = "tillDate", message = "From date has to be earlier than till date")
public class DateRangeDto {
    @NotNull(message = "From date can't be empty")
    LocalDate fromDate;

    @NotNull(message = "Till date can't be empty")
    LocalDate tillDate;
}