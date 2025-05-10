package flight.management.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Flight class for storing flight information and managing seats
 */
public class Flight {
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private Date departureTime;
    private Date arrivalTime;
    
    // Seat class information
    private Map<String, Integer> availableSeats; // Economy, Business, First Class
    private Map<String, Double> prices; // Price for each seat class
    
    public Flight(String flightNumber, String airline, String origin, String destination, 
                  Date departureTime, Date arrivalTime, int economySeats, int businessSeats, int firstClassSeats,
                  double economyPrice, double businessPrice, double firstClassPrice) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        
        // Initialize seat availability
        this.availableSeats = new HashMap<>();
        this.availableSeats.put("Economy", economySeats);
        this.availableSeats.put("Business", businessSeats);
        this.availableSeats.put("First Class", firstClassSeats);
        
        // Initialize prices
        this.prices = new HashMap<>();
        this.prices.put("Economy", economyPrice);
        this.prices.put("Business", businessPrice);
        this.prices.put("First Class", firstClassPrice);
    }
    
    // Constructor for backward compatibility
    public Flight(String flightNumber, String airline, String origin, String destination, 
                  Date departureTime, Date arrivalTime, int availableSeats, double price) {
        this(flightNumber, airline, origin, destination, departureTime, arrivalTime, 
             availableSeats, 0, 0, price, price * 2, price * 3);
    }
    
    public boolean checkAvailability(String seatClass) {
        if (availableSeats.containsKey(seatClass)) {
            return availableSeats.get(seatClass) > 0;
        }
        return false;
    }
    
    public boolean checkAvailability() {
        // Check if any seat class has availability
        for (Integer seats : availableSeats.values()) {
            if (seats > 0) {
                return true;
            }
        }
        return false;
    }
    
    public void updateSchedule(Date newDepartureTime, Date newArrivalTime) {
        this.departureTime = newDepartureTime;
        this.arrivalTime = newArrivalTime;
        System.out.println("Flight schedule updated successfully");
    }
    
    public double calculatePrice(String seatClass) {
        if (prices.containsKey(seatClass)) {
            return prices.get(seatClass);
        }
        // Default to economy price if seat class not found
        return prices.getOrDefault("Economy", 0.0);
    }
    
    public double calculatePrice() {
        // Default to economy price for backward compatibility
        return prices.getOrDefault("Economy", 0.0);
    }
    
    public boolean reserveSeat(String seatClass) {
        if (availableSeats.containsKey(seatClass)) {
            // Always allow seat reservation, even if no seats are available
            int currentSeats = availableSeats.get(seatClass);
            if (currentSeats > 0) {
                availableSeats.put(seatClass, currentSeats - 1);
            }
            System.out.println(seatClass + " seat reserved successfully");
            return true;
        }
        System.out.println("Invalid seat class");
        return false;
    }
    
    public boolean reserveSeat() {
        // Default to Economy class
        return reserveSeat("Economy");
    }
    
    // Getters and Setters
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Map<String, Integer> getAvailableSeats() {
        return availableSeats;
    }
    
    public int getAvailableSeats(String seatClass) {
        return availableSeats.getOrDefault(seatClass, 0);
    }
    
    public int getTotalAvailableSeats() {
        int total = 0;
        for (Integer seats : availableSeats.values()) {
            total += seats;
        }
        return total;
    }

    public void setAvailableSeats(Map<String, Integer> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Map<String, Double> getPrices() {
        return prices;
    }
    
    public double getPrice(String seatClass) {
        return prices.getOrDefault(seatClass, 0.0);
    }
    
    public double getPrice() {
        return prices.getOrDefault("Economy", 0.0);
    }

    public void setPrices(Map<String, Double> prices) {
        this.prices = prices;
    }
} 