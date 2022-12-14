package com.chalmers.getaroom.models.objects;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RoomTest {
    @Test
    public void fromJson_shouldProduceProperValues() {
        JsonObject infojObj = JsonParser.parseString("{\"id\":\"02d2ad33-1be9-4389-9882-e8c52e5a54f4\",\"name\":\"F4058\",\"element_type\":\"breakout_room\",\"timeedit_id\":\"chalmers:_te_205254\",\"node_type\":\"leaf\",\"type\":\"Room\",\"description\":\"\",\"element_label\":\"group room\",\"scopes\":[\"chalmers\"],\"subtitle\":\"Fysik Origo, Campus Johanneberg\",\"longitude\":11.97571,\"latitude\":57.690562,\"entrance_latitude\":57.690315,\"entrance_longitude\":11.97572,\"street_address\":\"Fysikgården 2B\",\"area_id\":\"a85a8be2-4ff6-4e39-9880-c2adb2a7626f\",\"area_name\":\"Campus Johanneberg\",\"area_type\":\"campus\",\"building_name\":\"Fysik Origo\",\"building_id\":\"172e3bca-3e05-45ea-ad93-4ba5eff50517\",\"has_photos\":false, \"floor_level\":\"2A\"}")
                .getAsJsonObject();

        JsonObject timeEditInfo = JsonParser.parseString("{\n" +
                "  \"seats\": \"4\",\n" +
                "  \"equipment\": \"Whiteboard\"\n" +
                "}").getAsJsonObject();

        try {
            Room r = Room.fromJSON(infojObj, timeEditInfo);
            assertEquals(r.building(), "Fysik Origo");
            assertEquals(r.entranceLocation(), new Location(57.690315, 11.97572));
            assertEquals(r.location(), new Location(57.690562, 11.97571));
            assertEquals(r.name(), "F4058");
            assertEquals(r.timeEditId(), "chalmers:_te_205254");
            assertEquals(r.uuid(), UUID.fromString("02d2ad33-1be9-4389-9882-e8c52e5a54f4"));
            assertEquals(r.seats(), 4);
            assertEquals(r.floor(), "2A");
            assertEquals(r.streetAddress(), "Fysikgården 2B");
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void fromJson_shouldFallbackWithMissingValues() {
        JsonObject infojObj = JsonParser.parseString("{\"id\":\"02d2ad33-1be9-4389-9882-e8c52e5a54f4\",\"name\":\"F4058\",\"element_type\":\"breakout_room\",\"timeedit_id\":\"chalmers:_te_205254\",\"node_type\":\"leaf\",\"type\":\"Room\",\"description\":\"\",\"element_label\":\"group room\",\"scopes\":[\"chalmers\"],\"subtitle\":\"Fysik Origo, Campus Johanneberg\",\"longitude\":11.97571,\"latitude\":57.690562,\"area_id\":\"a85a8be2-4ff6-4e39-9880-c2adb2a7626f\",\"area_name\":\"Campus Johanneberg\",\"area_type\":\"campus\",\"building_name\":\"Fysik Origo\",\"building_id\":\"172e3bca-3e05-45ea-ad93-4ba5eff50517\",\"has_photos\":false}")
                .getAsJsonObject();

        JsonObject timeEditInfo = JsonParser.parseString("{\n" +
                "  \"seats\": \"4\",\n" +
                "  \"equipment\": \"Whiteboard\"\n" +
                "}").getAsJsonObject();

        try {
            Room r = Room.fromJSON(infojObj, timeEditInfo);
            assertEquals(r.building(), "Fysik Origo");
            assertEquals(r.location(), new Location(57.690562, 11.97571));
            assertEquals(r.entranceLocation(), r.location());
            assertEquals(r.name(), "F4058");
            assertEquals(r.timeEditId(), "chalmers:_te_205254");
            assertEquals(r.uuid(), UUID.fromString("02d2ad33-1be9-4389-9882-e8c52e5a54f4"));
            assertEquals(r.seats(), 4);
            assertEquals(r.floor(), "");
            assertEquals(r.streetAddress(), "");
        } catch (Exception e) {
            fail(e);
        }
    }
}
