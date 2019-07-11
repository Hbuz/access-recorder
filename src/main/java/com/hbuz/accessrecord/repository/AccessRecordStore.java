package com.hbuz.accessrecord.repository;

import com.hbuz.accessrecord.model.AccessRecord;
import lombok.Data;

import java.util.List;

@Data
public class AccessRecordStore {

    private final List<AccessRecord> store;

}