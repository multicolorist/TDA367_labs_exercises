package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.utilities.WebRequestsInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 * Utility for accessing the Chalmers Maps API v2
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ChalmersMapsAPI implements ChalmersMapsAPIInterface{

    final String baseUrl = "https://maps.chalmers.se/v2/";
    private WebRequestsInterface requests;

    public ChalmersMapsAPI(WebRequestsInterface requests) {
        this.requests = requests;
    }

    /**
     * Gets information about objects inside a building or inside an area.
     * @param uid The unique identifier of the desired building or area.
     * @return JSON object representing information about objects inside a building or inside an area.
     */
    public JsonObject informationBoard(UUID uid) throws IOException {
        // TODO: Use a correct return type, and parse the JsonObject to a list of Room objects
        final String requestUrl = baseUrl + String.format("information_board/%s/json", uid.toString());
        return requests.readJsonElementFromUrl(requestUrl).getAsJsonObject();
    }

    /**
     * Gets a schedule from TimeEdit for a given room and time period.
     * @param identifier The unique identifier of the given room
     * @param year The year in which the schedule should be checked
     * @param week The week in which the schedule should be checked
     * @return JSON object representing all bookings for the room and given period
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonArray timeEditSchedule(String identifier, int year, int week) throws IOException {
        final String requestUrl = baseUrl + String.format("webservices/timeedit/room/%s/schedules/%d/%d/json", identifier, year, week);
        return requests.readJsonElementFromUrl(requestUrl).getAsJsonArray();
    }

    /**
     * Gets a walking route between two points.
     * @param origin The origin location
     * @param destination The destination location
     * @return JSON object representing a walking route between the two points
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject route(Location origin, Location destination) throws IOException {
        final String requestUrl = baseUrl + String.format("webservices/navigation/route/walking/from/%s/%s/to/%s/%s",
                origin.latitude(), origin.longitude(), destination.latitude(), destination.longitude());
        return requests.readJsonElementFromUrl(requestUrl).getAsJsonObject();
    }

    /**
     * Get information about a API object identified by its UID.
     * @param uuid The unique identifier of the object
     * @return JSON object representing information about the object
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject getInfo(UUID uuid) throws IOException {
        final String requestUrl = baseUrl + String.format("info/%s/json", uuid.toString());
        return requests.readJsonElementFromUrl(requestUrl).getAsJsonObject();
    }

    /**
     * Get information about a TimeEdit object identified by its identifier.
     * @param identifier The TimeEdit identifier of the object
     * @return JSON object representing information about the object
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject getTimeEditInfo(String identifier) throws IOException {
        final String requestUrl = baseUrl + String.format("webservices/timeedit/room/%s/json", identifier);
        return requests.readJsonElementFromUrl(requestUrl).getAsJsonObject();
    }

    /**
     * Get GeoJson object for relevant buildings
     * @return A GeoJson object representing different buildings
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject geoJsonBuildings() throws IOException {
        final String requestUrl = baseUrl + "geojson?types[]=building:*&recursive=true&scopes[]=chalmers&scopes[]=gothenburg&lang=en";
        return requests.readJsonElementFromUrl(requestUrl).getAsJsonObject();
    }

    /**
     * Get GeoJson object for relevant buildings
     * @return A GeoJson object representing different Points of Interest
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject geoJsonPOI() throws IOException {
        final String requestUrl = baseUrl + "geojson?poi=true&recursive=true&scopes[]=chalmers&scopes[]=gothenburg&lang=en";
        return requests.readJsonElementFromUrl(requestUrl).getAsJsonObject();
    }
}
