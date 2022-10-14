package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.utilities.WebRequests;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ChalmersMapsAPITest {
    @Test
    public void informationBoard_shouldGiveProperJsonAndIsNotEmpty() {

        ChalmersMapsAPIInterface api = new ChalmersMapsAPI(new WebRequests());

        try {
            // Get the information board for Campus Johanneberg
            JsonObject infoBoard = api.informationBoard(UUID.fromString("a85a8be2-4ff6-4e39-9880-c2adb2a7626f"));
            assertNotEquals(infoBoard.get("suggestions").getAsJsonArray().size(), 0);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void timeEditSchedule_shouldGiveProperJsonAndIsNotEmpty() {

        ChalmersMapsAPIInterface api = new ChalmersMapsAPI(new WebRequests());

        try {
            // Get bookings from a room using same example data from Swagger documentation
            JsonArray bookings = api.timeEditSchedule("chalmers:room_HC1", 2022, 16);
            assertNotEquals(bookings.size(), 0);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getInfo_shouldGiveProperJsonAndIsNotEmpty() {

        ChalmersMapsAPIInterface api = new ChalmersMapsAPI(new WebRequests());

        try {
            // Get info from a room using an example uid for an existing room
            JsonObject info = api.getInfo(UUID.fromString("d1cbbbbf-c9ef-4d41-8bf3-33bd06ab9ac7"));
            assertNotEquals(info.get("properties").getAsJsonObject().size(), 0);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void route_shouldGiveProperJsonAndIsNotEmpty() {
        ChalmersMapsAPIInterface api = new ChalmersMapsAPI(new WebRequests());

        try {
            // Get info from a room using an example uid for an existing room
            JsonObject routeObject = api.route(new Location(57.696484034673915, 11.975264592149706),new Location(57.700295142972465,11.965737593691228));
            assertNotEquals(routeObject.get("items").getAsJsonObject().size(), 0);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void geoJsonBuildings_shouldGiveProperJsonAndIsNotEmpty() {
        ChalmersMapsAPIInterface api = new ChalmersMapsAPI(new WebRequests());

        try {
            // Get POI GeoJSON from API
            JsonObject geoJson = api.geoJsonPOI();
            assertNotEquals(geoJson.get("features").getAsJsonArray().size(), 0);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void geoJsonPOI_shouldGiveProperJsonAndIsNotEmpty() {
        ChalmersMapsAPIInterface api = new ChalmersMapsAPI(new WebRequests());

        try {
            // Get buildings GeoJSON from API
            JsonObject geoJson = api.geoJsonBuildings();
            assertNotEquals(geoJson.get("features").getAsJsonArray().size(), 0);
        } catch (Exception e) {
            fail(e);
        }
    }


}