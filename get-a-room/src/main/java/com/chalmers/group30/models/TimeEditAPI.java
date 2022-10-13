package com.chalmers.group30.models;

import com.chalmers.group30.models.utilities.WebRequestsInterface;
import com.google.common.net.PercentEscaper;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Utility for accessing the TimeEdit API
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TimeEditAPI implements TimeEditAPIInterface{

    private WebRequestsInterface webRequests;
    private final PercentEscaper percentEscaper = new PercentEscaper("", false);

    public TimeEditAPI(WebRequestsInterface webRequests) {
        this.webRequests = webRequests;
    }

    /**
     * Gets a schedule from TimeEdit for a given room and time period.
     * @param id The ID of the room
     * @param start The start time of the period to get bookings for
     * @param end The end time of the period to get bookings for
     * @return Calendar object representing all bookings for the room and given period
     * @throws IOException If the underlying API request failed for some reason
     * @throws ParserException If the underlying API request returned invalid or malformed data
     */
    public Calendar getSchedule(String id, LocalDateTime start, LocalDateTime end) throws IOException, ParserException {
        return getSchedule(List.of(id), start, end);
    }

    /**
     *
     * @param IDs A list of TimeEdit IDs for rooms
     * @param start The start time of the period to get bookings for
     * @param end The end time of the period to get bookings for
     * @return Calendar object representing all bookings for the rooms and given period
     * @throws IOException If the underlying API request failed for some reason
     * @throws ParserException If the underlying API request returned invalid or malformed data
     */
    public Calendar getSchedule(List<String> IDs, LocalDateTime start, LocalDateTime end) throws IOException, ParserException {
        StringBuilder sURL = new StringBuilder("https://cloud.timeedit.net/chalmers/web/public/ri.ics?sid=3");
        for (String id : IDs) {
            sURL.append("&object=").append(percentEscaper.escape(id)).append("&type=room");
        }
        sURL.append("&l=en");
        return webRequests.readIcalendarFromUrl(sURL.toString());
    }
}
