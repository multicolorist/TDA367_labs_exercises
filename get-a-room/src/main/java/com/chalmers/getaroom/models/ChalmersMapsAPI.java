package com.chalmers.getaroom.models;

import com.chalmers.getaroom.models.objects.Location;
import com.chalmers.getaroom.models.utilities.WebRequestsInterface;
import com.google.common.net.PercentEscaper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility for accessing the Chalmers Maps API v2
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.NO)
class ChalmersMapsAPI implements ChalmersMapsAPIInterface {

    private static final String baseUrl = "https://maps.chalmers.se/v2/";
    private final Logger logger = Logger.getLogger(ChalmersMapsAPI.class.getName());
    private final PercentEscaper percentEscaper = new PercentEscaper("", false);
    private final WebRequestsInterface requests;

    public ChalmersMapsAPI(WebRequestsInterface requests) {
        this.requests = requests;
    }

    /**
     * Gets information about objects inside a building or inside an area.
     *
     * @param uuid The unique identifier of the desired building or area.
     * @return JSON object representing information about objects inside a building or inside an area.
     */
    public JsonObject informationBoard(UUID uuid) throws IOException {
        try {
            final String requestUrl = baseUrl + String.format("information_board/%s/json", uuid.toString());
            return requests.readJsonElementFromUrl(requestUrl).getAsJsonObject();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to get information board from API", e);
            throw e;
        }
    }

    /**
     * Gets a schedule from TimeEdit for a given room and time period.
     *
     * @param identifier The unique identifier of the given room
     * @param year       The year in which the schedule should be checked
     * @param week       The week in which the schedule should be checked
     * @return JSON object representing all bookings for the room and given period
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonArray timeEditSchedule(String identifier, int year, int week) throws IOException {
        try {
            final String requestUrl = baseUrl + String.format("webservices/timeedit/room/%s/schedules/%d/%d/json", percentEscaper.escape(identifier), year, week);
            return requests.readJsonElementFromUrl(requestUrl).getAsJsonArray();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to get timeedit schedule from API", e);
            throw e;
        }
    }

    /**
     * Gets a walking route between two points.
     *
     * @param origin      The origin location
     * @param destination The destination location
     * @return JSON object representing a walking route between the two points
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject route(Location origin, Location destination) throws IOException {
        try {
            final String requestUrl = baseUrl + String.format("webservices/navigation/route/walking/from/%s/%s/to/%s/%s",
                    origin.latitude(), origin.longitude(), destination.latitude(), destination.longitude());
            return requests.readJsonElementFromUrl(requestUrl).getAsJsonObject();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to get route from API", e);
            throw e;
        }
    }

    /**
     * Get information about an API object identified by its UID.
     *
     * @param uuid The unique identifier of the object
     * @return JSON object representing information about the object
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject getInfo(UUID uuid) throws IOException {
        try {
            final String requestUrl = baseUrl + String.format("info/%s/json", uuid.toString());
            return requests.readJsonElementFromUrl(requestUrl).getAsJsonObject();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to get object info from API", e);
            throw e;
        }
    }

    /**
     * Get information about a TimeEdit object identified by its identifier.
     *
     * @param identifier The TimeEdit identifier of the object
     * @return JSON object representing information about the object
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject getTimeEditInfo(String identifier) throws IOException {
        try {
            final String requestUrl = baseUrl + String.format("webservices/timeedit/room/%s/json", percentEscaper.escape(identifier));
            return requests.readJsonElementFromUrl(requestUrl).getAsJsonObject();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to get timeedit room info from API", e);
            throw e;
        }
    }

    /**
     * Get GeoJson object for relevant buildings
     *
     * @return A GeoJson object representing different buildings
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject getGeoJsonBuildings() throws IOException {
        try {
            final String requestUrl = baseUrl + "geojson?types[]=building:*&recursive=true&scopes[]=chalmers&scopes[]=gothenburg&lang=en";
            return requests.readJsonElementFromUrl(requestUrl).getAsJsonObject();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to get geojson buildings from API", e);
            throw e;
        }
    }

    /**
     * Get GeoJson object for POIs
     *
     * @return A GeoJson object representing different Points of Interest
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject getGeoJsonPOI() throws IOException {
        try {
            final String requestUrl = baseUrl + "geojson?poi=true&recursive=true&scopes[]=chalmers&scopes[]=gothenburg&lang=en";
            return requests.readJsonElementFromUrl(requestUrl).getAsJsonObject();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to get geojson POI from API", e);
            throw e;
        }
    }
}
