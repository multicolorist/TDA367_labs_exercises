package com.chalmers.group30.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonParser;

/**
 * Utility for accessing the Chalmers Maps API v2
 */
public class ChalmersMapsAPI {

    final static String baseUrl = "https://maps.chalmers.se/v2/";

    /**
     * Gets information about objects inside a building or inside an area.
     * @param uid The unique identifier of the desired building or area.
     * @return JSON object representing information about objects inside a building or inside an area.
     */
    public static JsonObject informationBoard(String uid) throws IOException {
        // TODO: Use a correct return type, and parse the JsonObject to a list of Room objects
        final String requestUrl = baseUrl + String.format("information_board/%s/json", uid);
        return readJsonFromUrl(requestUrl);
    }

    /**
     * Gets a schedule from TimeEdit for a given room and time period.
     * @param uid The unique identifier of the given room
     * @param year The year in which the schedule should be checked
     * @param week The week in which the schedule should be checked
     * @return JSON object representing all bookings for the room and given period
     * @throws IOException
     */
    public static JsonArray timeEditSchedule(String uid, int year, int week) throws IOException {
        final String requestUrl = baseUrl + String.format("webservices/timeedit/room/%s/schedules/%i/%i/json", uid, year, week);
        return readJsonFromUrl(requestUrl).getAsJsonArray();
    }

    // Get a JsonObject from a URL endpoint
    private static JsonObject readJsonFromUrl(String sURL) throws IOException {
        // Connect to the URL
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a JSON object
        JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
        return root.getAsJsonObject();
    }
}
