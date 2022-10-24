package com.chalmers.getaroom.models;

import com.chalmers.getaroom.models.objects.Location;
import com.chalmers.getaroom.models.utilities.WebRequests;
import com.chalmers.getaroom.models.utilities.WebRequestsInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

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
    public void informationBoard_shouldFailProperly() throws IOException {
        WebRequestsInterface wr = mock(WebRequests.class);
        when(wr.readJsonElementFromUrl(any())).thenThrow(new IOException("Test exception"));

        ChalmersMapsAPI api = new ChalmersMapsAPI(wr);
        assertThrows(IOException.class, () -> api.informationBoard(UUID.randomUUID()));
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
    public void timeEditSchedule_shouldFailProperly() throws IOException {
        WebRequestsInterface wr = mock(WebRequests.class);
        when(wr.readJsonElementFromUrl(any())).thenThrow(new IOException("Test exception"));

        ChalmersMapsAPI api = new ChalmersMapsAPI(wr);
        assertThrows(IOException.class, () -> api.timeEditSchedule("", 0, 0));
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
    public void getInfo_shouldFailProperly() throws IOException {
        WebRequestsInterface wr = mock(WebRequests.class);
        when(wr.readJsonElementFromUrl(any())).thenThrow(new IOException("Test exception"));

        ChalmersMapsAPI api = new ChalmersMapsAPI(wr);
        assertThrows(IOException.class, () -> api.getInfo(UUID.randomUUID()));
    }

    @Test
    public void route_shouldGiveProperJsonAndIsNotEmpty() {
        ChalmersMapsAPIInterface api = new ChalmersMapsAPI(new WebRequests());

        try {
            // Get info from a room using an example uid for an existing room
            JsonObject routeObject = api.route(new Location(57.696484034673915, 11.975264592149706), new Location(57.700295142972465, 11.965737593691228));
            assertNotEquals(routeObject.get("items").getAsJsonObject().size(), 0);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void route_shouldFailProperly() throws IOException {
        WebRequestsInterface wr = mock(WebRequests.class);
        when(wr.readJsonElementFromUrl(any())).thenThrow(new IOException("Test exception"));

        ChalmersMapsAPI api = new ChalmersMapsAPI(wr);
        assertThrows(IOException.class, () -> api.route(new Location(0, 0), new Location(0, 0)));
    }

    @Test
    public void getGeoJsonBuildings_shouldGiveProperJsonAndIsNotEmpty() {
        ChalmersMapsAPIInterface api = new ChalmersMapsAPI(new WebRequests());

        try {
            // Get POI GeoJSON from API
            JsonObject geoJson = api.getGeoJsonPOI();
            assertNotEquals(geoJson.get("features").getAsJsonArray().size(), 0);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getGeoJsonBuildings_shouldFailProperly() throws IOException {
        WebRequestsInterface wr = mock(WebRequests.class);
        when(wr.readJsonElementFromUrl(any())).thenThrow(new IOException("Test exception"));

        ChalmersMapsAPI api = new ChalmersMapsAPI(wr);
        assertThrows(IOException.class, () -> api.getGeoJsonBuildings());
    }

    @Test
    public void getGeoJsonPOI_shouldGiveProperJsonAndIsNotEmpty() {
        ChalmersMapsAPIInterface api = new ChalmersMapsAPI(new WebRequests());

        try {
            // Get buildings GeoJSON from API
            JsonObject geoJson = api.getGeoJsonBuildings();
            assertNotEquals(geoJson.get("features").getAsJsonArray().size(), 0);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getGeoJsonPOI_shouldFailProperly() throws IOException {
        WebRequestsInterface wr = mock(WebRequests.class);
        when(wr.readJsonElementFromUrl(any())).thenThrow(new IOException("Test exception"));

        ChalmersMapsAPI api = new ChalmersMapsAPI(wr);
        assertThrows(IOException.class, () -> api.getGeoJsonPOI());
    }

    @Test
    public void getTimeEditInfo_shouldGiveProperJsonAndIsNotEmpty() {
        ChalmersMapsAPIInterface api = new ChalmersMapsAPI(new WebRequests());

        try {
            // Get info from a room using an example timeedit id for an existing room
            JsonObject info = api.getTimeEditInfo("chalmers:room_F3316");
            assertNotEquals(info.get("info").getAsString().length(), 0);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void getTimeEditInfo_shouldFailProperly() throws IOException {
        WebRequestsInterface wr = mock(WebRequests.class);
        when(wr.readJsonElementFromUrl(any())).thenThrow(new IOException("Test exception"));

        ChalmersMapsAPI api = new ChalmersMapsAPI(wr);
        assertThrows(IOException.class, () -> api.getTimeEditInfo("test"));
    }
}