package com.chalmers.group30.models;

import com.chalmers.group30.models.utilities.WebRequestsInterface;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimeEditAPITest {
    @Test
    public void getSchedule_shouldReturnCalendar() throws IOException, ParserException {
        // Arrange
        WebRequestsInterface webRequests = mock(WebRequestsInterface.class);
        Calendar calendar = new Calendar();
        when(webRequests.readIcalendarFromUrl(anyString())).thenReturn(calendar);
        TimeEditAPIInterface timeEditAPI = new TimeEditAPI(webRequests);
        // Act
        Calendar result = timeEditAPI.getSchedule("HC1", LocalDateTime.MIN, LocalDateTime.MAX);
        // Assert
        assertEquals(calendar, result);
    }
}
