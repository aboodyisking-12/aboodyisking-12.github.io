package flight.management.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Booking class for managing booking information and status
 */
public class Booking {
    private String bookingReference;
    private Customer customer;
    private Flight flight;
    private ArrayList<Passenger> passengers;
    private Map<Passenger, String> seatClasses; // Economy, Business, First Class
    private ArrayList<String> seatSelections;
    private String status; // Confirmed, Cancelled, Pending
    private String paymentStatus; // Paid, Unpaid, Refunded
    private Map<Passenger, String> specialRequests; // Meal preferences, assistance needs
    private Date bookingDate;
    
    public Booking(String bookingReference, Customer customer, Flight flight, ArrayList<Passenger> passengers) {
        this.bookingReference = bookingReference;
        this.customer = customer;
        this.flight = flight;
        this.passengers = passengers;
        this.seatClasses = new HashMap<>();
        this.seatSelections = new ArrayList<>();
        this.specialRequests = new HashMap<>();
        this.status = "Reserved"; // Changed from "Pending" to "Reserved"
        this.paymentStatus = "Unpaid";
        this.bookingDate = new Date();
        
        // Default all passengers to Economy class
        for (Passenger passenger : passengers) {
            seatClasses.put(passenger, "Economy");
        }
    }
    
    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
        seatClasses.put(passenger, "Economy"); // Default to Economy
        System.out.println("Passenger added to booking");
    }
    
    public double calculateTotalPrice() {
        double total = 0;
        for (Passenger passenger : passengers) {
            String seatClass = seatClasses.get(passenger);
            total += flight.calculatePrice(seatClass);
        }
        return total;
    }
    
    public void confirmBooking() {
        if (paymentStatus.equals("Paid")) {
            status = "Confirmed";
            System.out.println("Booking confirmed successfully");
        } else {
            System.out.println("Cannot confirm booking. Payment required.");
        }
    }
    
    public void cancelBooking() {
        status = "Cancelled";
        if (paymentStatus.equals("Paid")) {
            paymentStatus = "Refunded";
        }
        System.out.println("Booking cancelled successfully");
    }
    
    public void setSeatClass(Passenger passenger, String seatClass) {
        if (seatClasses.containsKey(passenger)) {
            // Always allow seat class change
            flight.reserveSeat(seatClass);
            seatClasses.put(passenger, seatClass);
            System.out.println("Seat class updated to " + seatClass + " for passenger " + passenger.getName());
        } else {
            System.out.println("Passenger not found in booking");
        }
    }
    
    public void setSpecialRequest(Passenger passenger, String request) {
        specialRequests.put(passenger, request);
        System.out.println("Special request added for passenger " + passenger.getName());
    }
    
    public String generateItinerary() {
        StringBuilder itinerary = new StringBuilder();
        itinerary.append("BOOKING REFERENCE: ").append(bookingReference).append("\n");
        itinerary.append("BOOKING DATE: ").append(bookingDate).append("\n");
        itinerary.append("STATUS: ").append(status).append("\n");
        itinerary.append("PAYMENT STATUS: ").append(paymentStatus).append("\n\n");
        
        itinerary.append("CUSTOMER INFORMATION:\n");
        itinerary.append("Name: ").append(customer.getName()).append("\n");
        itinerary.append("Email: ").append(customer.getEmail()).append("\n");
        itinerary.append("Contact: ").append(customer.getContactInfo()).append("\n\n");
        
        itinerary.append("FLIGHT DETAILS:\n");
        itinerary.append("Flight Number: ").append(flight.getFlightNumber()).append("\n");
        itinerary.append("Airline: ").append(flight.getAirline()).append("\n");
        itinerary.append("From: ").append(flight.getOrigin()).append("\n");
        itinerary.append("To: ").append(flight.getDestination()).append("\n");
        itinerary.append("Departure: ").append(flight.getDepartureTime()).append("\n");
        itinerary.append("Arrival: ").append(flight.getArrivalTime()).append("\n\n");
        
        itinerary.append("PASSENGER DETAILS:\n");
        for (int i = 0; i < passengers.size(); i++) {
            Passenger passenger = passengers.get(i);
            itinerary.append("Passenger ").append(i + 1).append(":\n");
            itinerary.append("Name: ").append(passenger.getName()).append("\n");
            itinerary.append("Passport: ").append(passenger.getPassportNumber()).append("\n");
            itinerary.append("Seat Class: ").append(seatClasses.get(passenger)).append("\n");
            
            if (i < seatSelections.size()) {
                itinerary.append("Seat: ").append(seatSelections.get(i)).append("\n");
            }
            
            if (specialRequests.containsKey(passenger)) {
                itinerary.append("Special Requests: ").append(specialRequests.get(passenger)).append("\n");
            }
            
            itinerary.append("\n");
        }
        
        itinerary.append("PAYMENT INFORMATION:\n");
        itinerary.append("Total Amount: $").append(calculateTotalPrice()).append("\n");
        itinerary.append("Payment Status: ").append(paymentStatus).append("\n\n");
        
        itinerary.append("Thank you for choosing our service!\n");
        itinerary.append("For any assistance, please contact customer support.");
        
        return itinerary.toString();
    }
    
    // Getters and Setters
    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public ArrayList<String> getSeatSelections() {
        return seatSelections;
    }

    public void setSeatSelections(ArrayList<String> seatSelections) {
        this.seatSelections = seatSelections;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public Map<Passenger, String> getSeatClasses() {
        return seatClasses;
    }
    
    public void setSeatClasses(Map<Passenger, String> seatClasses) {
        this.seatClasses = seatClasses;
    }
    
    public Map<Passenger, String> getSpecialRequests() {
        return specialRequests;
    }
    
    public void setSpecialRequests(Map<Passenger, String> specialRequests) {
        this.specialRequests = specialRequests;
    }
    
    public Date getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
} 