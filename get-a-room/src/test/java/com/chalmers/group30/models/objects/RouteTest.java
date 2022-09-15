package com.chalmers.group30.models.objects;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RouteTest {
    @Test
    public void fromJson_shouldProduceProperValues(){
        String jString = "{\"description\":\"\",\"items\":{\"routes\":[{\"weight_name\":\"duration\",\"weight\":661.6,\"distance\":916,\"duration\":661.6}]}}";
        JsonObject jObj = JsonParser.parseString(jString).getAsJsonObject();

        try{
            Route r = Route.fromJSON(jObj);
            assertEquals(r.distance(), 916);
        } catch(Exception e) {
            fail(e);
        }
    }
}
