package com.hbuz.accessrecord.service;

import com.hbuz.accessrecord.Utils.Status;
import com.hbuz.accessrecord.exception.AccessRecordNotFoundException;
import com.hbuz.accessrecord.model.AccessRecord;
import com.hbuz.accessrecord.repository.AccessRecordStore;
import com.hbuz.accessrecord.service.impl.AccessRecordServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccessRecordServiceTest {

    @Mock
    AccessRecordStore accessRecordStore;

    @InjectMocks
    AccessRecordServiceImpl accessRecordService;

    private final AccessRecordStore accessRecordStoreMocked = new AccessRecordStore(new ArrayList<>());

    private final String checkIn = String.valueOf(LocalDateTime.now());
    private final String checkOut = String.valueOf(LocalDateTime.now());


    @Before
    public void init(){

        when(accessRecordStore.getStore()).thenReturn(accessRecordStoreMocked.getStore());
    }


    @Test
    public void shouldAddAccessRecordToStore_whenSubmitCheckIn(){

        AccessRecord accessRecord = accessRecordService.submitCheckIn(checkIn);

        assertTrue(accessRecordStoreMocked.getStore().contains(accessRecord));
    }


    @Test(expected = DateTimeParseException.class)
    public void shouldThrowsDateTimeParseException_whenSubmitCheckIn_andBodyParameterIsInvalid(){

        accessRecordService.submitCheckIn("12345");
    }


    @Test(expected = NullPointerException.class)
    public void shouldThrowsNullPointerException_whenSubmitCheckIn_andBodyParameterIsNull(){

        accessRecordService.submitCheckIn(null);
    }


    @Test
    public void shouldEditAccessRecordInStore_whenSubmitCheckOut(){

        AccessRecord accessRecord = accessRecordService.submitCheckIn(checkIn);

        AccessRecord accessRecordEdited =
                accessRecordService.submitCheckOut(accessRecord.getId().toString(), checkOut);

        assertTrue(accessRecordStoreMocked.getStore().contains(accessRecord));
        assertEquals(checkOut, String.valueOf(accessRecordEdited.getCheckOut()));
        assertEquals(Status.OUT, accessRecordEdited.getStatus());
    }


    @Test(expected = AccessRecordNotFoundException.class)
    public void shouldThrowsAccessRecordNotFoundException_whenSuspend_andBodyParameterIsInvalid(){

        accessRecordService.submitCheckOut("12345", checkOut);
    }


    @Test(expected = DateTimeParseException.class)
    public void shouldThrowsDateTimeParseException_whenSuspend_andBodyParameterIsInvalid(){

        AccessRecord accessRecord = accessRecordService.submitCheckIn(checkIn);

        accessRecordService.submitCheckOut(accessRecord.getId().toString(),"12345");
    }


    @Test(expected = NullPointerException.class)
    public void shouldThrowsNullPointerException_whenSuspend_andBodyParameterIsNull(){

        AccessRecord accessRecord = accessRecordService.submitCheckIn(checkIn);

        accessRecordService.submitCheckOut(accessRecord.getId().toString(), null);
    }


    @Test
    public void shouldRetrieveAccessRecordFromStore_whenRetrieve(){

        AccessRecord accessRecord = accessRecordService.submitCheckIn(checkIn);

        AccessRecord accessRecordRetrieved =
                accessRecordService.retrieveAccessRecord(accessRecord.getId().toString());

        assertEquals(accessRecord, accessRecordRetrieved);
    }


    @Test(expected = AccessRecordNotFoundException.class)
    public void shouldThrowsAccessRecordNotFoundException_whenRetrieve_andBodyParameterIsInvalid(){

        accessRecordService.retrieveAccessRecord("12345");
    }

}
