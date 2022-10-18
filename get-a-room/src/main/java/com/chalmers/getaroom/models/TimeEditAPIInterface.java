package com.chalmers.getaroom.models;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

interface TimeEditAPIInterface {
    Calendar getSchedule(String id, LocalDateTime start, LocalDateTime end) throws IOException, ParserException;

    Calendar getSchedule(List<String> IDs, LocalDateTime start, LocalDateTime end) throws IOException, ParserException;
}
