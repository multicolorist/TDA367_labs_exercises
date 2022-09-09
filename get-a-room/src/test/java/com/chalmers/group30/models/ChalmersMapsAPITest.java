package com.chalmers.group30.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Test;

import static com.chalmers.group30.models.ChalmersMapsAPI.*;
import static org.junit.jupiter.api.Assertions.*;

class ChalmersMapsAPITest {
    @Test
    public void informationBoard_shouldGiveProperJsonAndIsNotEmpty() {
        try {
            // Get the information board for Campus Johanneberg
            JsonObject infoBoard = informationBoard("a85a8be2-4ff6-4e39-9880-c2adb2a7626f");
            assertNotEquals(infoBoard.get("suggestions").getAsJsonObject().size(), 0);
        } catch (Exception e) {
            fail(e);
        }

    }

    @Test
    public void timeEditSchedule_shouldGiveProperJsonAndIsNotEmpty() {
        try {
            // Get bookings from a room using same example data from Swagger documentation
            JsonArray bookings = timeEditSchedule("chalmers:room_HC1", 2022, 16);
            assertNotEquals(bookings.size(), 0);
        } catch (Exception e) {
            fail(e);
        }

    }
}