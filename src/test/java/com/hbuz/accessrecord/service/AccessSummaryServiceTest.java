package com.hbuz.accessrecord.service;

import com.hbuz.accessrecord.Utils.Status;
import com.hbuz.accessrecord.model.AccessRecord;
import com.hbuz.accessrecord.repository.AccessRecordStore;
import com.hbuz.accessrecord.service.impl.AccessSummaryServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccessSummaryServiceTest {


    @Mock
    AccessRecordStore accessRecordStore;

    @InjectMocks
    AccessSummaryServiceImpl accessSummaryService;


    private final AccessRecordStore accessRecordStoreMocked = new AccessRecordStore(new ArrayList<>());

    @Test
    public void shouldReturnCheckInAndCheckOutAccessRecordFromStore_whenGetAccessSummary() {

        when(accessRecordStore.getStore()).thenReturn(accessRecordStoreMocked.getStore());

        AccessRecord accessRecord1 = new AccessRecord(UUID.randomUUID(), LocalDateTime.now(), Status.IN);

        AccessRecord accessRecord2 = new AccessRecord(UUID.randomUUID(), LocalDateTime.now(), Status.IN);
        accessRecord2.setCheckOut(LocalDateTime.now());

        AccessRecord accessRecord3 = new AccessRecord(UUID.randomUUID(), LocalDateTime.now(), Status.IN);

        AccessRecord accessRecord4 = new AccessRecord(UUID.randomUUID(), LocalDateTime.now(), Status.OUT);
        accessRecord4.setCheckOut(LocalDateTime.now());

        AccessRecord accessRecord5 = new AccessRecord(UUID.randomUUID(), LocalDateTime.now(), Status.OUT);
        accessRecord5.setCheckOut(LocalDateTime.now());


        accessRecordStoreMocked.getStore().add(accessRecord1);
        accessRecordStoreMocked.getStore().add(accessRecord2);
        accessRecordStoreMocked.getStore().add(accessRecord3);
        accessRecordStoreMocked.getStore().add(accessRecord4);
        accessRecordStoreMocked.getStore().add(accessRecord5);


        assertEquals(3, accessSummaryService.getAccessSummary().getCheckInCount());
        assertEquals(2, accessSummaryService.getAccessSummary().getCheckOutCount());
    }
}
