package com.chalmers.group30.models.objects;

import java.io.Serializable;

/**
 * Represents a physical location in the world.
 */
public record Location(double latitude, double longitude) implements Serializable {
}
