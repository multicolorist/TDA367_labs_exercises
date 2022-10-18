package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Location;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.UUID;

/**
 * Interface for the Chalmers Maps API V2
 */
interface ChalmersMapsAPIInterface {
    /**
     * Gets information about objects inside a building or inside an area.
     *
     * @param uuid The unique identifier of the desired building or area.
     * @return JSON object representing information about objects inside a building or inside an area.
     */
    JsonObject informationBoard(UUID uuid) throws IOException;

    /**
     * Gets a schedule from TimeEdit for a given room and time period.
     *
     * @param identifier The unique identifier of the given room
     * @param year       The year in which the schedule should be checked
     * @param week       The week in which the schedule should be checked
     * @return JSON object representing all bookings for the room and given period
     * @throws IOException If the underlying API request failed for some reason
     */
    JsonArray timeEditSchedule(String identifier, int year, int week) throws IOException;

    /**
     * Gets a walking route between two points.
     *
     * @param origin      The origin location
     * @param destination The destination location
     * @return JSON object representing a walking route between the two points
     * @throws IOException If the underlying API request failed for some reason
     */
    JsonObject route(Location origin, Location destination) throws IOException;

    /**
     * Get information about a TimeEdit object identified by its identifier.
     *
     * @param identifier The TimeEdit identifier of the object
     * @return JSON object representing information about the object
     * @throws IOException If the underlying API request failed for some reason
     */
    JsonObject getTimeEditInfo(String identifier) throws IOException;

    /**
     * Get information about a API object identified by its UID.
     *
     * @param uuid The unique identifier of the object
     * @return JSON object representing information about the object
     * @throws IOException If the underlying API request failed for some reason
     */
    JsonObject getInfo(UUID uuid) throws IOException;

    /**
     * Get GeoJson object for relevant buildings
     *
     * @return A GeoJson object representing different buildings
     * @throws IOException If the underlying API request failed for some reason
     */
    JsonObject geoJsonBuildings() throws IOException;

    /**
     * Get GeoJson object for POIs
     *
     * @return A GeoJson object representing different Points of Interest
     * @throws IOException If the underlying API request failed for some reason
     */
    JsonObject geoJsonPOI() throws IOException;
}
