package com.hbuz.accessrecord.model;

import com.hbuz.accessrecord.Utils.Status;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class AccessRecord {

    @NonNull
    private UUID id;

    @NonNull
    private LocalDateTime checkIn;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private LocalDateTime checkOut;

    @NonNull
    private Status status;

}
