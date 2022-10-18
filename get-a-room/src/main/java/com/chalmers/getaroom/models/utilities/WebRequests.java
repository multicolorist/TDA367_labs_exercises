package com.chalmers.getaroom.models.utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Utility for reading data from the web
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.NO)
public class WebRequests implements WebRequestsInterface {

    /**
     * Reads a JSON element from a given URL
     *
     * @param sURL The URL to read from
     * @return The JSON element
     * @throws IOException If the underlying web request failed
     */
    public JsonElement readJsonElementFromUrl(String sURL) throws IOException {
        // Connect to the URL
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a JSON object
        return JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
    }

    /**
     * Reads an iCalendar from a given URL
     *
     * @param sURL The URL to read from
     * @return The iCalendar object
     * @throws IOException     If the underlying web request failed
     * @throws ParserException If the underlying web request returned invalid or malformed data
     */
    public Calendar readIcalendarFromUrl(String sURL) throws IOException, ParserException {
        // Connect to the URL
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a Calendar object
        CalendarBuilder builder = new CalendarBuilder();
        return builder.build(new InputStreamReader((InputStream) request.getContent()));
    }
}
