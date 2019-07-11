package com.hbuz.accessrecord.controller;

import com.hbuz.accessrecord.model.AccessSummary;
import com.hbuz.accessrecord.service.AccessSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("accessSummary")
public class AccessSummaryController {

    @Autowired
    AccessSummaryService accessSummaryService;


    @GetMapping()
    public ResponseEntity<AccessSummary> getChargingSessionSummary() {
        return ok().body(accessSummaryService.getAccessSummary());
    }
}
