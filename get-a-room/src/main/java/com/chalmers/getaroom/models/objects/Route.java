package com.chalmers.getaroom.models.objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a route
 *
 * @param distance  The distance between the two locations
 * @param maneuvers A list of locations, describing the separate parts of the route
 */
public record Route(double distance, List<Location> maneuvers) {
    /**
     * Parses a given JSON to a Route object.
     *
     * @param obj A JSON object representing the route
     * @return A Route object from the parsed JSON
     */
    public static Route fromJSON(JsonObject obj) {
        List<Location> maneuvers = new ArrayList<>();
        double distance = obj.getAsJsonObject("items")
                .getAsJsonArray("routes").get(0)
                .getAsJsonObject()
                .get("distance").getAsDouble();

        JsonArray maneuversJson = obj.getAsJsonObject("items").getAsJsonArray("routes").get(0).getAsJsonObject().getAsJsonArray("legs").get(0).getAsJsonObject().getAsJsonArray("steps");
        for (JsonElement step : maneuversJson) {
            JsonArray maneuver = step.getAsJsonObject().get("maneuver").getAsJsonObject().get("location").getAsJsonArray();
            maneuvers.add(new Location(maneuver.get(1).getAsFloat(), maneuver.get(0).getAsFloat()));
        }

        return new Route(distance, maneuvers);
    }
}