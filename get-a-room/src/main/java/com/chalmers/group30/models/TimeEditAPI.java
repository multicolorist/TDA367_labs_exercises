package com.chalmers.group30.models;

import com.chalmers.group30.models.utilities.WebRequestsInterface;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;

/**
 * Utility for accessing the TimeEdit API
 */
public class TimeEditAPI implements TimeEditAPIInterface{

    private WebRequestsInterface webRequests;

    public TimeEditAPI(WebRequestsInterface webRequests) {
        this.webRequests = webRequests;
    }

    /**
     * Gets a schedule from TimeEdit for a given room and time period.
     * @param id The ID of the room
     * @return Calendar object representing all bookings for the room and given period
     * @throws IOException If the underlying API request failed for some reason
     * @throws ParserException If the underlying API request returned invalid or malformed data
     */
    public Calendar getSchedule(String id) throws IOException, ParserException {
        String sURL = "https://cloud.timeedit.net/chalmers/web/public/ri.ical?sid=3&object=" + id + "&type=room";
        return webRequests.readIcalendarFromUrl(sURL);
    }
}
