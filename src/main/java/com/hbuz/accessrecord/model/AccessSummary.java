package com.hbuz.accessrecord.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessSummary {

    private long checkInCount;

    private long checkOutCount;

}