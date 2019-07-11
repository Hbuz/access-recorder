package com.hbuz.accessrecord.integration;

import com.hbuz.accessrecord.Utils.Status;
import com.hbuz.accessrecord.Utils.Util;
import com.hbuz.accessrecord.model.AccessRecordRequest;
import com.hbuz.accessrecord.repository.AccessRecordStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccessRecordIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AccessRecordStore accessRecordStore;

    private AccessRecordRequest accessRecordRequest;

    @Test
    public void accessRecordEndToEndTest() throws Exception {

        final LocalDateTime checkIn = LocalDateTime.now();
        final LocalDateTime checkOut = LocalDateTime.now();

        accessRecordRequest = new AccessRecordRequest();
        accessRecordRequest.setCheckIn(String.valueOf(checkIn));


        //Submits a new access record to the store
        mockMvc.perform(post("/accessRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.objectToString((accessRecordRequest))))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.checkIn").value(checkIn.toString()))
                .andExpect(jsonPath("$.status").value(Status.IN.toString()));


        final UUID id = accessRecordStore.getStore().get(0).getId();

        //Retrieves a specific access record from the store
        mockMvc.perform(get("/accessRecords/{id}", String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.checkIn").value(checkIn.toString()))
                .andExpect(jsonPath("$.status").value(Status.IN.toString()));


        //Returns the number of check-in and check-out from the store
        mockMvc.perform(get("/accessSummary", String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.checkInCount").value(1L))
                .andExpect(jsonPath("$.checkOutCount").value(0));


        accessRecordRequest = new AccessRecordRequest();
        accessRecordRequest.setCheckOut(String.valueOf(checkOut));

        //Suspends the access record
        mockMvc.perform(put("/accessRecords/{id}", String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.objectToString(accessRecordRequest)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.checkIn").value(checkIn.toString()))
                .andExpect(jsonPath("$.checkOut").value(checkOut.toString()))
                .andExpect(jsonPath("$.status").value(Status.OUT.toString()));


        //Retrieves a specific check-out access record from the store
        mockMvc.perform(get("/accessRecords/{id}", String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.checkIn").value(checkIn.toString()))
                .andExpect(jsonPath("$.checkOut").value(checkOut.toString()))
                .andExpect(jsonPath("$.status").value(Status.OUT.toString()));


        //Returns the number of check-in and check-out from the store
        mockMvc.perform(get("/accessSummary", String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.checkInCount").value(0))
                .andExpect(jsonPath("$.checkOutCount").value(1L));

    }
}
