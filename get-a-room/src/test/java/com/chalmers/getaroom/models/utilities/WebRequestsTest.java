package com.chalmers.getaroom.models.utilities;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class WebRequestsTest {

    @Test
    public void readJsonElementFromUrl_shouldReturnJSONElement() {
        WebRequests wr = new WebRequests();
        try {
            wr.readJsonElementFromUrl("https://dummyjson.com/products");
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void readIcalendarFromUrl_shouldReturnIcalendar() {
        WebRequests wr = new WebRequests();
        try {
            wr.readIcalendarFromUrl("https://gist.githubusercontent.com/DeMarko/6142417/raw/1cd301a5917141524b712f92c2e955e86a1add19/sample.ics");
        } catch (Exception e) {
            fail(e);
        }
    }
}
