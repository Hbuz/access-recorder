package com.hbuz.accessrecord.controller;

import com.hbuz.accessrecord.model.AccessRecord;
import com.hbuz.accessrecord.model.AccessRecordRequest;
import com.hbuz.accessrecord.service.AccessRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("accessRecords")
public class AccessRecordController {

    @Autowired
    AccessRecordService accessRecordService;

    @PostMapping()
    public ResponseEntity<AccessRecord> submitCheckIn(@Valid @RequestBody AccessRecordRequest request) {
        return ok().body(accessRecordService.submitCheckIn(request.getCheckIn()));
    }


    @PutMapping("/{id}")
    public ResponseEntity<AccessRecord> submitCheckOut(@PathVariable String id,
                                                       @Valid @RequestBody AccessRecordRequest request) {
        return ok().body(accessRecordService.submitCheckOut(id, request.getCheckOut()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<AccessRecord> retrieveAccessRecord(@PathVariable String id) {
        return ok().body(accessRecordService.retrieveAccessRecord(id));
    }

}