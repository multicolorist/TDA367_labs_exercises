package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.objects.SearchRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GetARoomFacade implements GetARoomFacadeInterface {

    private final BookingServiceInterface bookingService;
    private final RoomServiceInterface roomService;

    @Autowired
    public GetARoomFacade(BookingServiceInterface bookingService, RoomServiceInterface roomService) {

        this.bookingService = bookingService;
        this.roomService = roomService;
    }

    //todo missing room size
    @Override
    public List<SearchRecord> search(Location userLocation, int groupSize, LocalDateTime startTime, LocalDateTime endTime) throws IOException {
        List<Room> candidateRooms = roomService.getRooms();
        List<Room> matchingRooms = new ArrayList<>();
        roomLoop:
        for (Room room : candidateRooms) {
            try {
                List<Booking> bookings = bookingService.getBookings(room);

                for (Booking booking : bookings) {
                    //Booking start time is within the search time
                    if (((booking.startTime().isAfter(startTime) || booking.startTime().isEqual(startTime)) && booking.startTime().isBefore(endTime))
                            //or Booking end time is within the search time
                            || (booking.endTime().isAfter(startTime) && (booking.endTime().isBefore(endTime) || booking.endTime().isEqual(endTime)))) {
                        continue roomLoop;
                    }
                }
                matchingRooms.add(room);

            }catch (Exception e){
                continue;
            }

        }
    }
}

