package com.hbuz.accessrecord.controller;

import com.hbuz.accessrecord.Utils.Status;
import com.hbuz.accessrecord.Utils.Util;
import com.hbuz.accessrecord.model.AccessRecord;
import com.hbuz.accessrecord.model.AccessRecordRequest;
import com.hbuz.accessrecord.service.AccessRecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccessRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    AccessRecordService accessRecordService;

    @InjectMocks
    AccessRecordController accessRecordController;


    private final AccessRecordRequest accessRecordRequest = new AccessRecordRequest();
    private final LocalDateTime checkIn = LocalDateTime.now();
    private final LocalDateTime checkOut = LocalDateTime.now();
    private final UUID id = UUID.randomUUID();


    @Test
    public void shouldReturnOK_whenSubmitCheckInToStore() {

        accessRecordRequest.setCheckIn(String.valueOf(checkIn));

        AccessRecord accessRecord = new AccessRecord(id, checkIn, Status.IN);

        when(accessRecordService.submitCheckIn(String.valueOf(checkIn))).thenReturn(accessRecord);

        ResponseEntity<AccessRecord> response =
                accessRecordController.submitCheckIn(accessRecordRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void shouldReturnBadRequest_whenSubmitCheckInToStore_andRequestIsInvalid() throws Exception {

        accessRecordRequest.setCheckIn("12345");

        mockMvc.perform(post("/accessRecords").contentType(MediaType.APPLICATION_JSON)
                .content(Util.objectToString((accessRecordRequest))))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldReturnBadRequest_whenSubmitCheckInToStore_andRequestParameterIsNull() throws Exception {

        accessRecordRequest.setCheckIn(null);

        mockMvc.perform(post("/accessRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.objectToString((accessRecordRequest))))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldReturnOK_whenSubmitCheckOutToStore() {

        accessRecordRequest.setCheckIn(String.valueOf(checkIn));

        AccessRecord accessRecord = new AccessRecord(id, checkIn, Status.OUT);
        accessRecord.setCheckOut(checkOut);

        when(accessRecordService.submitCheckOut(String.valueOf(id), String.valueOf(checkIn)))
                .thenReturn(accessRecord);

        ResponseEntity<AccessRecord> response =
                accessRecordController.submitCheckIn(accessRecordRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void shouldReturnNotFound_whenSubmitCheckOutToStore_andAccessRecordIsNotExisting() throws Exception {

        accessRecordRequest.setCheckOut(String.valueOf(checkOut));

        mockMvc.perform(put("/accessRecords/{id}", String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.objectToString(accessRecordRequest)))
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldReturnOK_whenRetrieveSpecificAccessRecordFromStore() {

        AccessRecord accessRecord = new AccessRecord(id, checkIn, Status.IN);

        when(accessRecordService.retrieveAccessRecord(String.valueOf(id))).thenReturn(accessRecord);

        ResponseEntity<AccessRecord> response =
                accessRecordController.retrieveAccessRecord(String.valueOf(id));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void shouldReturnNotFound_whenRetrieveAccessRecordFromStore_andAccessRecordIsNotPresent() throws Exception {

        mockMvc.perform(get("/accessRecords/{id}", String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
