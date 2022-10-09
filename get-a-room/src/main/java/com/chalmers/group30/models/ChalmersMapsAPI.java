package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Location;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonParser;
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
    final Logger logger = Logger.getLogger(ChalmersMapsAPI.class.getName());

    /**
     * Gets information about objects inside a building or inside an area.
     * @param uid The unique identifier of the desired building or area.
     * @return JSON object representing information about objects inside a building or inside an area.
     */
    public JsonObject informationBoard(UUID uid) throws IOException {
        try{
            // TODO: Use a correct return type, and parse the JsonObject to a list of Room objects
            final String requestUrl = baseUrl + String.format("information_board/%s/json", uid.toString());
            return readJsonElementFromUrl(requestUrl).getAsJsonObject();
        }catch (Exception e){
            logger.log(Level.WARNING, "Failed to get information board from API", e);
            throw e;
        }
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
        try {
            final String requestUrl = baseUrl + String.format("webservices/timeedit/room/%s/schedules/%d/%d/json", identifier, year, week);
            return readJsonElementFromUrl(requestUrl).getAsJsonArray();
        }catch (Exception e){
            logger.log(Level.WARNING, "Failed to get timeedit schedule from API", e);
            throw e;
        }
    }

    /**
     * Gets a walking route between two points.
     * @param origin The origin location
     * @param destination The destination location
     * @return JSON object representing a walking route between the two points
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject route(Location origin, Location destination) throws IOException {
        try {
            final String requestUrl = baseUrl + String.format("webservices/navigation/route/walking/from/%s/%s/to/%s/%s",
                    origin.latitude(), origin.longitude(), destination.latitude(), destination.longitude());
            return readJsonElementFromUrl(requestUrl).getAsJsonObject();
        }catch (Exception e){
            logger.log(Level.WARNING, "Failed to get route from API", e);
            throw e;
        }
    }

    /**
     * Get information about a API object identified by its UID.
     * @param uuid The unique identifier of the object
     * @return JSON object representing information about the object
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject getInfo(UUID uuid) throws IOException {
        try{
            final String requestUrl = baseUrl + String.format("info/%s/json", uuid.toString());
            return readJsonElementFromUrl(requestUrl).getAsJsonObject();
        }catch (Exception e){
            logger.log(Level.WARNING, "Failed to get object info from API", e);
            throw e;
        }
    }

    /**
     * Get information about a TimeEdit object identified by its identifier.
     * @param identifier The TimeEdit identifier of the object
     * @return JSON object representing information about the object
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject getTimeEditInfo(String identifier) throws IOException {
        try {
            final String requestUrl = baseUrl + String.format("webservices/timeedit/room/%s/json", identifier);
            return readJsonElementFromUrl(requestUrl).getAsJsonObject();
        }catch (Exception e){
            logger.log(Level.WARNING, "Failed to get timeedit room info from API", e);
            throw e;
        }
    }

    /**
     * Get GeoJson object for relevant buildings
     * @return A GeoJson object representing different buildings
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject geoJsonBuildings() throws IOException {
        try {
            final String requestUrl = baseUrl + "geojson?types[]=building:*&recursive=true&scopes[]=chalmers&scopes[]=gothenburg&lang=en";
            return readJsonElementFromUrl(requestUrl).getAsJsonObject();
        }catch (Exception e){
            logger.log(Level.WARNING, "Failed to get geojson buildings from API", e);
            throw e;
        }
    }

    /**
     * Get GeoJson object for POIs
     * @return A GeoJson object representing different Points of Interest
     * @throws IOException If the underlying API request failed for some reason
     */
    public JsonObject geoJsonPOI() throws IOException {
        try{
            final String requestUrl = baseUrl + "geojson?poi=true&recursive=true&scopes[]=chalmers&scopes[]=gothenburg&lang=en";
            return readJsonElementFromUrl(requestUrl).getAsJsonObject();
        }catch (Exception e){
            logger.log(Level.WARNING, "Failed to get geojson POI from API", e);
            throw e;
        }
    }


    /** Get a JsonElement from a URL endpoint
     *
     * @param sURL The URL to get the JSON from
     * @return A JsonElement representing the JSON from the URL
     * @throws IOException If the request failed for some reason
     */
    private JsonElement readJsonElementFromUrl(String sURL) throws IOException {
        // Connect to the URL
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a JSON object
        return JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
    }
}
