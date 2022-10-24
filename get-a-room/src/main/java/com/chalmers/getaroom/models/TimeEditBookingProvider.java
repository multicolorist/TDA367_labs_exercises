package com.chalmers.getaroom.models;

import com.chalmers.getaroom.models.objects.Booking;
import com.chalmers.getaroom.models.objects.Room;
import com.chalmers.getaroom.models.utilities.CacheUpdateProvider;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.NO)
class TimeEditBookingProvider implements CacheUpdateProvider<Dictionary<Room, List<Booking>>> {

    private final RoomServiceInterface roomServiceInterface;
    private final TimeEditAPIInterface timeEditAPIInterface;
    private final Logger logger = Logger.getLogger(TimeEditBookingProvider.class.getName());

    private static final int weeksForwardToCache = 2;

    @Autowired
    public TimeEditBookingProvider(RoomServiceInterface roomServiceInterface, TimeEditAPIInterface timeEditAPIInterface) {
        this.roomServiceInterface = roomServiceInterface;
        this.timeEditAPIInterface = timeEditAPIInterface;
    }

    /**
     * Gets all bookings for the next x weeks based on weeksForwardToCache
     *
     * @return A dictionary with rooms as keys and a list of bookings as values
     * @throws IOException If the underlying API call fails
     */
    @Override
    public Dictionary<Room, List<Booking>> getNewDataToCache() throws IOException {
        Dictionary<Room, List<Booking>> bookings = new Hashtable<>();

        for (Room room : roomServiceInterface.getRooms()) {
            try {
                bookings.put(room, getBookings(room, LocalDateTime.now(ZoneId.of("Europe/Paris"))));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Could not get or parse bookings for a specific room " + room.name(), e);
            }
        }
        return bookings;
    }

    private List<Booking> getBookings(Room room, LocalDateTime startTime) throws IOException, ParserException {
        List<Booking> bookings = new ArrayList<>();

        LocalDateTime endTime = startTime.plusWeeks(weeksForwardToCache);
        net.fortuna.ical4j.model.Calendar schedule = timeEditAPIInterface.getSchedule(room.timeEditId(), startTime, endTime);

        for (CalendarComponent component : schedule.getComponents()) {
            if (!component.getProperty("LOCATION").isPresent()) continue;
            if (component.getProperty("LOCATION").get().getValue().isEmpty()) continue;

            bookings.add(Booking.fromVEvent((VEvent) component));
        }


        return bookings;
    }
}
