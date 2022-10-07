package com.chalmers.group30.models;

import com.google.gson.JsonObject;
import net.fortuna.ical4j.data.ParserException;

import java.io.IOException;
import java.time.LocalDateTime;

import net.fortuna.ical4j.model.Calendar;

public interface TimeEditAPIInterface {
    Calendar getSchedule(String id, LocalDateTime start, LocalDateTime end) throws IOException, ParserException;
}
