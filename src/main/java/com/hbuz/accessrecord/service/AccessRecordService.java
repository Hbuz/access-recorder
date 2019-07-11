package com.hbuz.accessrecord.service;

import com.hbuz.accessrecord.model.AccessRecord;

public interface AccessRecordService {

    AccessRecord submitCheckIn(String checkIn);

    AccessRecord submitCheckOut(String id, String checkOut);

    AccessRecord retrieveAccessRecord(String id);

}