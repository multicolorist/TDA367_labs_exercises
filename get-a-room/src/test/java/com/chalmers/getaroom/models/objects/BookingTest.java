package com.chalmers.getaroom.models.objects;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BookingTest {
    @Test
    public void fromJson_shouldProduceProperValues() {
        String jString = "{\"reservation_begin\":\"20220420T100000\",\"reservation_end\":\"20220420T114500\",\"reservation_id\":1508592,\"reservation_modified\":\"20211024T225255\",\"rooms\":[{\"title\":\"HC1\",\"id\":\"chalmers:room_HC1\",\"entry_id\":\"25cbc4e6-aaaf-4680-969d-05c5d22f7f9e\"}],\"fields\":[\"Produktionsergonomi och arbetsdesign, MPP027\",\"MPPEN-1\"]}";
        JsonObject jObj = JsonParser.parseString(jString).getAsJsonObject();

        try {
            Booking b = Booking.fromJSON(jObj);
            assertEquals(b.startTime(), LocalDateTime.of(2022, 4, 20, 10, 0, 0));
            assertEquals(b.endTime(), LocalDateTime.of(2022, 4, 20, 11, 45, 0));
        } catch (Exception e) {
            fail(e);
        }

    }
}
