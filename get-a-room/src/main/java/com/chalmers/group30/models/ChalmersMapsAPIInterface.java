package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Location;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.UUID;

/**
 * Interface for the Chalmers Maps API V2
 */
public interface ChalmersMapsAPIInterface {
    JsonObject informationBoard(UUID uuid) throws IOException;
    JsonArray timeEditSchedule(String identifier, int year, int week) throws IOException;
    JsonObject route(Location origin, Location destination) throws IOException;
    JsonObject getTimeEditInfo(String identifier) throws IOException;
    JsonObject getInfo(UUID uuid) throws IOException;
    JsonObject geoJsonBuildings() throws IOException;
    JsonObject geoJsonPOI() throws IOException;
}
