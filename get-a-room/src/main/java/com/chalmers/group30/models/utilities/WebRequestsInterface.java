package com.chalmers.group30.models.utilities;

import com.google.gson.JsonElement;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;

public interface WebRequestsInterface {
    JsonElement readJsonElementFromUrl(String sURL) throws IOException;
    Calendar readIcalendarFromUrl(String sURL) throws IOException, ParserException;
}
