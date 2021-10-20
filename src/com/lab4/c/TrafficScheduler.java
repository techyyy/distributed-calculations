package com.lab4.c;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrafficScheduler {

    private final List<StopPoint> busStops;

    public TrafficScheduler() {
        busStops = new ArrayList<>();
    }

    void addBusStop(String city) {
        busStops.add(new StopPoint(city));
        System.out.println("Bus stop added at " + city + ".");
    }

    void deleteBusStop(String city) {
        StopPoint stopPointToDelete = null;
        for (StopPoint stopPoint : busStops) {
            if (stopPoint.getCity().equals(city)) {
                stopPointToDelete = stopPoint;
                break;
            }
        }
        if (stopPointToDelete == null) {
            return;
        } else {
            Set<StopPoint> stopsConnectedToDeleted = new HashSet<>(
                    stopPointToDelete.getNeighborCities().keySet());
            for (StopPoint stopPoint : stopsConnectedToDeleted) {
                stopPointToDelete.removeConnection(stopPoint);
            }
            busStops.remove(stopPointToDelete);
        }
        System.out.println("Deleted bus stop: " + city);
    }

    void changeFlightPrice(String firstCity, String secondCity, int price) {
        StopPoint firstStopPointToChange = getCityStopByName(firstCity);
        StopPoint secondStopPointToChange = getCityStopByName(secondCity);
        if (firstStopPointToChange != null && secondCity != null
                && firstStopPointToChange.isConnected(secondStopPointToChange)) {
            firstStopPointToChange.connect(secondStopPointToChange, price);
            System.out.println("Changed price for flight from " + firstCity + " to " + secondCity
                    + ". New price is " + price);
        }
    }

    void addFlight(String firstCity, String secondCity, int price) {
        StopPoint firstStopPointToChange = getCityStopByName(firstCity);
        StopPoint secondStopPointToChange = getCityStopByName(secondCity);
        if (firstStopPointToChange != null && secondCity != null) {
            firstStopPointToChange.connect(secondStopPointToChange, price);
            System.out.println("Added flight from " + firstCity + " to " + secondCity
                    + " with price of " + price + ".");
        }
    }

    void deleteFlight(String firstCity, String secondCity) {
        StopPoint firstStopPointToChange = getCityStopByName(firstCity);
        StopPoint secondStopPointToChange = getCityStopByName(secondCity);
        if (firstStopPointToChange != null && secondCity != null
                && firstStopPointToChange.isConnected(secondStopPointToChange)) {
            firstStopPointToChange.removeConnection(secondStopPointToChange);
            System.out.println("Deleted flight from " + firstCity + " to " + secondCity + ".");
        }
    }

    Integer getFlightPrice(String firstCity, String secondCity) {
        StopPoint firstStopPointToChange = getCityStopByName(firstCity);
        StopPoint secondStopPointToChange = getCityStopByName(secondCity);
        if (firstStopPointToChange == null || secondStopPointToChange == null) {
            throw new IllegalArgumentException("No such stop.");
        }

        return firstStopPointToChange.getPrice(secondStopPointToChange);
    }

    private StopPoint getCityStopByName(String cityName) {
        StopPoint stopPoint = new StopPoint(cityName);
        if (busStops.contains(stopPoint)) {
            return busStops.get(busStops.indexOf(stopPoint));
        }
        return null;
    }
}