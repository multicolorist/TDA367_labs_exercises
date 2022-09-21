package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Room;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomProviderTest {

    @Test
    void getNewDataToCache() throws IOException {
        ChalmersMapsAPIInterface api = mock(ChalmersMapsAPIInterface.class);

        JsonObject jObj1 = JsonParser.parseString(
                "{\n" +
                        "  \"suggestions\": [\n" +
                        "    {\n" +
                        "      \"data\": \"495a8bdb-ae0d-4c94-8be5-68240f9a5686\",\n" +
                        "      \"value\": \"Physics Soliden\",\n" +
                        "      \"doc_id\": \"495a8bdb-ae0d-4c94-8be5-68240f9a5686\",\n" +
                        "      \"title\": \"Physics Soliden\",\n" +
                        "      \"description\": \"Byggnaden döptes till Soliden p g a att personalen som var verksamma i den önskade ett stabilt och säkert hus. Byggår 1945. Byggdes på med en våning 1988. Ombyggnad 1996.\",\n" +
                        "      \"type\": \"Building\",\n" +
                        "      \"object_type\": \"building\",\n" +
                        "      \"element_type\": \"university\",\n" +
                        "      \"element_label\": \"university building\",\n" +
                        "      \"sorting\": 0,\n" +
                        "      \"scopes\": [\n" +
                        "        \"chalmers\"\n" +
                        "      ],\n" +
                        "      \"poi\": false,\n" +
                        "      \"archived\": false,\n" +
                        "      \"parent\": \"941a9412-c241-46db-a028-1e4e9d307ee7\",\n" +
                        "      \"entrance_latitude\": 57.690065,\n" +
                        "      \"entrance_longitude\": 11.974993,\n" +
                        "      \"node_type\": \"inner\",\n" +
                        "      \"latitude\": 57.69028,\n" +
                        "      \"longitude\": 11.974987500000001,\n" +
                        "      \"category_label\": \"building\",\n" +
                        "      \"category_type\": \"building\",\n" +
                        "      \"subtitle\": \"Origovägen 6B, Campus Johanneberg\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"data\": \"e9d284db-f758-494f-bda5-81b7da57ec90\",\n" +
                        "      \"value\": \"CHABO\",\n" +
                        "      \"doc_id\": \"e9d284db-f758-494f-bda5-81b7da57ec90\",\n" +
                        "      \"title\": \"CHABO\",\n" +
                        "      \"description\": \"Chabo has a total of 480 apartments with a large roof terrace on top of the building. All the apartments have French balconies.\",\n" +
                        "      \"type\": \"Building\",\n" +
                        "      \"object_type\": \"building\",\n" +
                        "      \"element_type\": \"dormitory\",\n" +
                        "      \"element_label\": \"student accommodation\",\n" +
                        "      \"sorting\": 0,\n" +
                        "      \"scopes\": [\n" +
                        "        \"chalmers\"\n" +
                        "      ],\n" +
                        "      \"poi\": false,\n" +
                        "      \"archived\": false,\n" +
                        "      \"parent\": \"a85a8be2-4ff6-4e39-9880-c2adb2a7626f\",\n" +
                        "      \"entrance_latitude\": 57.691558,\n" +
                        "      \"entrance_longitude\": 11.976607,\n" +
                        "      \"node_type\": \"inner\",\n" +
                        "      \"latitude\": 57.691734499999995,\n" +
                        "      \"longitude\": 11.976310000000002,\n" +
                        "      \"category_label\": \"building\",\n" +
                        "      \"category_type\": \"building\",\n" +
                        "      \"subtitle\": \"Kemivägen 7A, Campus Johanneberg\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}").getAsJsonObject();

        when(api.informationBoard(UUID.fromString("a85a8be2-4ff6-4e39-9880-c2adb2a7626f"))).thenReturn(jObj1);

        JsonObject jObj2 = JsonParser.parseString(
                "{\n" +
                        "  \"suggestions\": [\n" +
                        "    {\n" +
                        "      \"data\": \"0ccfd810-dd40-42a3-899b-7a59a7bae4bc\",\n" +
                        "      \"value\": \"Signalen\",\n" +
                        "      \"doc_id\": \"0ccfd810-dd40-42a3-899b-7a59a7bae4bc\",\n" +
                        "      \"title\": \"Signalen\",\n" +
                        "      \"description\": \"\",\n" +
                        "      \"type\": \"Room\",\n" +
                        "      \"object_type\": \"room\",\n" +
                        "      \"element_type\": \"seminar_room\",\n" +
                        "      \"element_label\": \"seminar room\",\n" +
                        "      \"sorting\": 1,\n" +
                        "      \"scopes\": [\n" +
                        "        \"chalmers\"\n" +
                        "      ],\n" +
                        "      \"poi\": false,\n" +
                        "      \"archived\": false,\n" +
                        "      \"parent\": \"2d1054fb-a415-471c-9b56-6b953e195a99\",\n" +
                        "      \"entrance_latitude\": 57.690395,\n" +
                        "      \"entrance_longitude\": 11.975147,\n" +
                        "      \"floor_level\": \"3\",\n" +
                        "      \"node_type\": \"leaf\",\n" +
                        "      \"latitude\": 57.690296,\n" +
                        "      \"longitude\": 11.974976,\n" +
                        "      \"subtitle\": \"Fysik Soliden, Campus Johanneberg\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}").getAsJsonObject();

        when(api.informationBoard(UUID.fromString("495a8bdb-ae0d-4c94-8be5-68240f9a5686"))).thenReturn(jObj2);

        JsonObject jObj3 = JsonParser.parseString("{\n" +
                "  \"suggestions\": [\n" +
                "    {\n" +
                "      \"data\": \"02d2ad33-1be9-4389-9882-e8c52e5a54f4\",\n" +
                "      \"value\": \"F4058\",\n" +
                "      \"doc_id\": \"02d2ad33-1be9-4389-9882-e8c52e5a54f4\",\n" +
                "      \"title\": \"F4058\",\n" +
                "      \"description\": \"\",\n" +
                "      \"type\": \"Room\",\n" +
                "      \"object_type\": \"room\",\n" +
                "      \"element_type\": \"breakout_room\",\n" +
                "      \"element_label\": \"group room\",\n" +
                "      \"sorting\": 1,\n" +
                "      \"scopes\": [\n" +
                "        \"chalmers\"\n" +
                "      ],\n" +
                "      \"poi\": false,\n" +
                "      \"archived\": false,\n" +
                "      \"parent\": \"5cff6d13-3076-436d-9be8-30bf59dbe73e\",\n" +
                "      \"entrance_latitude\": 57.690315,\n" +
                "      \"entrance_longitude\": 11.97572,\n" +
                "      \"node_type\": \"leaf\",\n" +
                "      \"latitude\": 57.690562,\n" +
                "      \"longitude\": 11.97571,\n" +
                "      \"subtitle\": \"Fysik Origo, Campus Johanneberg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"data\": \"2cf8c73c-2ef3-47f6-9bd7-8269cc88da27\",\n" +
                "      \"value\": \"Euler\",\n" +
                "      \"doc_id\": \"2cf8c73c-2ef3-47f6-9bd7-8269cc88da27\",\n" +
                "      \"title\": \"Euler\",\n" +
                "      \"description\": \"\",\n" +
                "      \"type\": \"Room\",\n" +
                "      \"object_type\": \"room\",\n" +
                "      \"element_type\": \"lecture_hall\",\n" +
                "      \"element_label\": \"lecture hall\",\n" +
                "      \"sorting\": 0,\n" +
                "      \"scopes\": [\n" +
                "        \"chalmers\"\n" +
                "      ],\n" +
                "      \"poi\": false,\n" +
                "      \"archived\": false,\n" +
                "      \"parent\": \"ddbf78a9-1116-49ec-8d4f-28fe18b88600\",\n" +
                "      \"entrance_latitude\": 57.689956,\n" +
                "      \"entrance_longitude\": 11.976514,\n" +
                "      \"floor_level\": \"4\",\n" +
                "      \"stair\": \"F\",\n" +
                "      \"node_type\": \"leaf\",\n" +
                "      \"latitude\": 57.690309,\n" +
                "      \"longitude\": 11.976133,\n" +
                "      \"subtitle\": \"Fysik, Campus Johanneberg\"\n" +
                "    }\n" +
                "  ]\n" +
                "}").getAsJsonObject();

        when(api.informationBoard(UUID.fromString("e9d284db-f758-494f-bda5-81b7da57ec90"))).thenReturn(jObj3);

        JsonObject jObj4 = JsonParser.parseString("{\n" +
                "  \"id\": \"02d2ad33-1be9-4389-9882-e8c52e5a54f4\",\n" +
                "  \"type\": \"Feature\",\n" +
                "  \"properties\": {\n" +
                "    \"id\": \"02d2ad33-1be9-4389-9882-e8c52e5a54f4\",\n" +
                "    \"name\": \"F4058\",\n" +
                "    \"element_type\": \"breakout_room\",\n" +
                "    \"timeedit_id\": \"chalmers:_te_205254\",\n" +
                "    \"node_type\": \"leaf\",\n" +
                "    \"type\": \"Room\",\n" +
                "    \"description\": \"\",\n" +
                "    \"element_label\": \"group room\",\n" +
                "    \"scopes\": [\n" +
                "      \"chalmers\"\n" +
                "    ],\n" +
                "    \"subtitle\": \"Fysik Origo, Campus Johanneberg\",\n" +
                "    \"longitude\": 11.97571,\n" +
                "    \"latitude\": 57.690562,\n" +
                "    \"entrance_latitude\": 57.690315,\n" +
                "    \"entrance_longitude\": 11.97572,\n" +
                "    \"street_address\": \"Fysikgården 2B\",\n" +
                "    \"area_id\": \"a85a8be2-4ff6-4e39-9880-c2adb2a7626f\",\n" +
                "    \"area_name\": \"Campus Johanneberg\",\n" +
                "    \"area_type\": \"campus\",\n" +
                "    \"building_name\": \"Fysik Origo\",\n" +
                "    \"building_id\": \"172e3bca-3e05-45ea-ad93-4ba5eff50517\",\n" +
                "    \"has_photos\": false\n" +
                "  }\n" +
                "}").getAsJsonObject();

        when(api.getInfo(UUID.fromString("02d2ad33-1be9-4389-9882-e8c52e5a54f4"))).thenReturn(jObj4);

        RoomProvider roomProvider = new RoomProvider(api);

        try {
            List<Room> rooms = roomProvider.getNewDataToCache();
            assertEquals(1, rooms.size());
            assertEquals(UUID.fromString("02d2ad33-1be9-4389-9882-e8c52e5a54f4"), rooms.get(0).uuid());
        } catch (IOException e) {
            fail(e);
        }
    }
}