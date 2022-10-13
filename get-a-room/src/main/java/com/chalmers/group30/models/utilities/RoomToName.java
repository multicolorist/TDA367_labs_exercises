package com.chalmers.group30.models.utilities;

import com.chalmers.group30.models.objects.Room;
import com.google.common.base.Function;

public enum RoomToName implements Function<Room, String> {
    INSTANCE;

    @Override
    public String apply(Room input) {
        return input.name();
    }
}