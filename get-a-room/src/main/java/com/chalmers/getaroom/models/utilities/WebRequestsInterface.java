package com.chalmers.getaroom.models.utilities;

import com.google.gson.JsonElement;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;

/**
 * Interface defining different parsing for web requests
 */
public interface WebRequestsInterface {
    JsonElement readJsonElementFromUrl(String sURL) throws IOException;

    Calendar readIcalendarFromUrl(String sURL) throws IOException, ParserException;
}
