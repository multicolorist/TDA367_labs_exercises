package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Room;
import com.google.gson.JsonParser;
import org.junit.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {
    @Test
    public void getBookings_shouldReturnProperBookings() throws IOException {
        Room mockRoom = new Room("Chalmers Test", "Building A", "chalmers:test", UUID.randomUUID(), new Location(0, 0), new Location(1, 1));
        ChalmersMapsAPIInterface api = mock(ChalmersMapsAPIInterface.class);

        when(api.timeEditSchedule("chalmers:test", 2022, 16)).thenReturn(JsonParser.parseString("[{\"reservation_begin\":\"20220418T111500\",\"reservation_end\":\"20220418T150000\",\"reservation_id\":1619637,\"reservation_modified\":\"20220418T111128\",\"rooms\":[{\"title\":\"F4058\",\"id\":\"chalmers:_te_205254\",\"entry_id\":\"02d2ad33-1be9-4389-9882-e8c52e5a54f4\"}],\"fields\":[]},{\"reservation_begin\":\"20220418T150000\",\"reservation_end\":\"20220418T180000\",\"reservation_id\":1619638,\"reservation_modified\":\"20220418T111136\",\"rooms\":[{\"title\":\"F4058\",\"id\":\"chalmers:_te_205254\",\"entry_id\":\"02d2ad33-1be9-4389-9882-e8c52e5a54f4\"}],\"fields\":[]},{\"reservation_begin\":\"20220419T164500\",\"reservation_end\":\"20220419T184500\",\"reservation_id\":1619692,\"reservation_modified\":\"20220418T145027\",\"rooms\":[{\"title\":\"F4058\",\"id\":\"chalmers:_te_205254\",\"entry_id\":\"02d2ad33-1be9-4389-9882-e8c52e5a54f4\"}],\"fields\":[]}]").getAsJsonArray());

        BookingService service = new BookingService(api);

        try {
            List<Booking> bookings = service.getBookings(mockRoom, Instant.ofEpochSecond(1650283200), 1);
        } catch (Exception e) {
            fail(e);
        }
    }
}
