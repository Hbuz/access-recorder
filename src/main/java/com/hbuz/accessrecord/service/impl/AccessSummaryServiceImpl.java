package com.hbuz.accessrecord.service.impl;

import com.hbuz.accessrecord.Utils.Status;
import com.hbuz.accessrecord.model.AccessSummary;
import com.hbuz.accessrecord.repository.AccessRecordStore;
import com.hbuz.accessrecord.service.AccessSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccessSummaryServiceImpl implements AccessSummaryService {

    @Autowired
    private AccessRecordStore accessRecordStore;

    @Override
    public AccessSummary getAccessSummary() {

        long checkInCounter = accessRecordStore.getStore().stream()
                .filter(c -> c.getStatus().equals(Status.IN))
                .count();

        long checkOutCounter = accessRecordStore.getStore().stream()
                .filter(c -> c.getStatus().equals(Status.OUT))
                .count();

        return new AccessSummary(checkInCounter, checkOutCounter);
    }

}