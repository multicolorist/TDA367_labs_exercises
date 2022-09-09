package com.chalmers.group30.models.objects;

import com.google.gson.JsonObject;

/**
 * Represents a route
 */
public class Route {
    //TODO: Add the rest of the fields for the route
    //TODO: Discuss visibility for fields
    double distance;

    /**
     * Initialises a new Route object.
     * @param distance The distance between the two locations
     */
    public Route(double distance) {
        this.distance = distance;
    }

    /**
     * Parses a given JSON to a Route object.
     * @param obj A JSON object representing the route
     * @return A Route object from the parsed JSON
     */
    public static Route fromJSON(JsonObject obj) {
        //TODO: Update this as fields change
        double distance = obj.getAsJsonObject("items")
                .getAsJsonArray("routes").get(0)
                .getAsJsonObject()// Todo: Verify - does it always return a single route?
                .get("distance").getAsDouble();

        return new Route(distance);
    }

    public double getDistance() {
        return this.distance;
    }
}