package com.hbuz.accessrecord.controller;

import com.hbuz.accessrecord.model.AccessSummary;
import com.hbuz.accessrecord.service.AccessSummaryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccessSummaryControllerTest {

    @Mock
    AccessSummaryService accessSummaryService;

    @Autowired
    AccessSummaryController accessSummaryController;


    @Test
    public void shouldReturnOK_whenGetAccessRecordSummaryFromStore(){

        AccessSummary accessSummary = new AccessSummary(3, 2);

        when(accessSummaryService.getAccessSummary()).thenReturn(accessSummary);

        ResponseEntity<AccessSummary> response =
                accessSummaryController.getChargingSessionSummary();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
