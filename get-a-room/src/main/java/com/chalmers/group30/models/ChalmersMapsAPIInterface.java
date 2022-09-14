package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Location;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;

public interface ChalmersMapsAPIInterface {
    JsonObject informationBoard(String uid) throws IOException;
    JsonArray timeEditSchedule(String uid, int year, int week) throws IOException;
    JsonObject route(Location origin, Location destination) throws IOException;
    JsonObject getInfo(String uid) throws IOException;
}
