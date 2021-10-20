package com.lab4.c;

import java.util.HashMap;
import java.util.Map;

public class StopPoint {
    private final String city;
    private final Map<StopPoint, Integer> neighborCities;

    StopPoint(String city) {
        this.city = city;
        this.neighborCities = new HashMap<>();
    }

    void connect(StopPoint node, int price) {
        if (this == node) { throw new IllegalArgumentException(); }
        this.neighborCities.put(node, price);
        node.neighborCities.put(this, price);
    }

    void removeConnection(StopPoint node) {
        neighborCities.remove(node);
        node.neighborCities.remove(this);
    }

    boolean isConnected(StopPoint node) {
        return neighborCities.containsKey(node);
    }

    Map<StopPoint, Integer> getNeighborCities() {
        return neighborCities;
    }

    String getCity() {
        return city;
    }

    Integer getPrice(StopPoint other) {
        if (this.neighborCities.containsKey(other)) { return this.neighborCities.get(other); }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) { return false; }

        if (this.getClass() != other.getClass()) { return false; }

        return this.city.equals(((StopPoint) other).city);
    }
}