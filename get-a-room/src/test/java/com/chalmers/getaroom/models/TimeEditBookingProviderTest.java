package com.chalmers.getaroom.models;

import com.chalmers.getaroom.models.objects.Booking;
import com.chalmers.getaroom.models.objects.Location;
import com.chalmers.getaroom.models.objects.Room;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimeEditBookingProviderTest {

    @Test
    void getNewDataToCache_shouldReturnEvents() throws IOException, ParserException {
        Room mockRoom = new Room("Chalmers Test", 1, "Building A", "Floor 1", "Adress A", "chalmers:test", UUID.randomUUID(), new Location(0, 0), new Location(1, 1));
        TimeEditAPIInterface api = mock(TimeEditAPIInterface.class);
        RoomServiceInterface roomServiceInterface = mock(RoomServiceInterface.class);
        when(roomServiceInterface.getRooms()).thenReturn(Arrays.asList(new Room[]{mockRoom}));

        String calendarString = "BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "METHOD:PUBLISH\n" +
                "X-WR-CALNAME:TimeEdit-TKITE-2\\, INFORM\n" +
                " ATIONSTEKNIK\\, CIVILINGENJ...-20221\n" +
                " 013\n" +
                "X-WR-CALDESC:Date limit -\n" +
                "X-PUBLISHED-TTL:PT20M\n" +
                "CALSCALE:GREGORIAN\n" +
                "PRODID:-//TimeEdit\\\\\\, //TimeEdit//EN\n" +
                "BEGIN:VEVENT\n" +
                "DTSTART:20221013T111500Z\n" +
                "DTEND:20221013T130000Z\n" +
                "UID:1592159-569704435-0@timeedit.com\n" +
                "DTSTAMP:20221013T094851Z\n" +
                "LAST-MODIFIED:20221013T094851Z\n" +
                "SUMMARY:DIT213GU. Objektorienterat programmeringspro\n" +
                " jekt\\, TDA367. Objektorienterat programmeringspro\n" +
                " jekt\n" +
                "LOCATION:EG-2515\\, EG-2516\\, Chalmers Test\\, EG-3506\\, EG-3507\\, EG-3508\n" +
                "DESCRIPTION:TKITE-2\\nTutorial\\nID 1592159\n" +
                "END:VEVENT\n" +
                "BEGIN:VEVENT\n" +
                "DTSTART:20221013T131500Z\n" +
                "DTEND:20221013T150000Z\n" +
                "UID:1661816-569704435-0@timeedit.com\n" +
                "DTSTAMP:20221013T094851Z\n" +
                "LAST-MODIFIED:20221013T094851Z\n" +
                "SUMMARY:MVE045. Matematisk analys\n" +
                "LOCATION:HC1\n" +
                "DESCRIPTION:TKITE-2\\nLecture\\nID 1661816\n" +
                "END:VEVENT\n" +
                "BEGIN:VEVENT\n" +
                "DTSTART:20221014T111500Z\n" +
                "DTEND:20221014T130000Z\n" +
                "UID:1592157-569704435-0@timeedit.com\n" +
                "DTSTAMP:20221013T094851Z\n" +
                "LAST-MODIFIED:20221013T094851Z\n" +
                "SUMMARY:DIT213GU. Objektorienterat programmeringspro\n" +
                " jekt\\, TDA367. Objektorienterat programmeringspro\n" +
                " jekt\n" +
                "LOCATION:Chalmers Test\\, EG-2516\\, EG-3505\\, EG-3506\\, EG-3507\\, EG-3508\n" +
                "DESCRIPTION:TKITE-2\\nTutorial\\nID 1592157\n" +
                "END:VEVENT\n" +
                "BEGIN:VEVENT\n" +
                "DTSTART:20221014T131500Z\n" +
                "DTEND:20221014T150000Z\n" +
                "UID:1592158-569704435-0@timeedit.com\n" +
                "DTSTAMP:20221013T094851Z\n" +
                "LAST-MODIFIED:20221013T094851Z\n" +
                "SUMMARY:DIT213GU. Objektorienterat programmeringspro\n" +
                " jekt\\, TDA367. Objektorienterat programmeringspro\n" +
                " jekt\n" +
                "LOCATION:EG-2515\\, EG-2516\\, EG-3505\\, Chalmers Test\\, EG-3507\\, EG-3508\n" +
                "DESCRIPTION:TKITE-2\\nTutorial\\nID 1592158\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR\n";
        CalendarBuilder builder = new CalendarBuilder();
        Calendar c = builder.build(new StringReader(calendarString));

        when(api.getSchedule(eq("chalmers:test"), any(), any())).thenReturn(c);

        TimeEditBookingProvider service = new TimeEditBookingProvider(roomServiceInterface, api);

        try {
            Dictionary<Room, List<Booking>> bookings = service.getNewDataToCache();
            List<Booking> roomBookings = bookings.get(mockRoom);
            assertNotNull(roomBookings);
        } catch (Exception e) {
            fail(e);
        }

    }
}
