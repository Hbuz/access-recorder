package com.hbuz.accessrecord.service.impl;

import com.hbuz.accessrecord.Utils.Status;
import com.hbuz.accessrecord.exception.AccessRecordNotFoundException;
import com.hbuz.accessrecord.model.AccessRecord;
import com.hbuz.accessrecord.repository.AccessRecordStore;
import com.hbuz.accessrecord.service.AccessRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccessRecordServiceImpl implements AccessRecordService {

    @Autowired
    private AccessRecordStore accessRecordStore;


    @Override
    public AccessRecord submitCheckIn(String checkIn) {

        final UUID id = UUID.randomUUID();

        final AccessRecord accessRecord = new AccessRecord(id, LocalDateTime.parse(checkIn), Status.IN);

        accessRecordStore.getStore().add(accessRecord);

        return accessRecord;
    }


    @Override
    public AccessRecord submitCheckOut(String id, String checkOut) {

        final AccessRecord accessRecord = this.retrieveAccessRecord(id);

        accessRecord.setCheckOut(LocalDateTime.parse(checkOut));
        accessRecord.setStatus(Status.OUT);

        return accessRecord;
    }


    @Override
    public AccessRecord retrieveAccessRecord(String id) {
        return accessRecordStore.getStore().stream()
                .filter(c -> id.equals(c.getId().toString()))
                .findAny()
                .orElseThrow(() -> new AccessRecordNotFoundException(id));
    }
}
