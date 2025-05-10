package flight.management.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Agent class for managing bookings and assisting customers
 */
public class Agent extends User {
    private String agentId;
    private String department;
    private double commission;
    
    public Agent(String userId, String username, String password, String name, String email, String contactInfo, 
                 String agentId, String department, double commission) {
        super(userId, username, password, name, email, contactInfo);
        this.agentId = agentId;
        this.department = department;
        this.commission = commission;
    }
    
    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    @Override
    public void logout() {
        System.out.println("Agent " + name + " logged out successfully");
    }
    
    @Override
    public void updateProfile(String name, String email, String contactInfo) {
        this.name = name;
        this.email = email;
        this.contactInfo = contactInfo;
        System.out.println("Agent profile updated successfully");
    }
    
    /**
     * Performs an action on a flight
     * @param action The action to perform
     * @param flight The flight to act on
     * @return List of affected flights
     */
    public ArrayList<Flight> manageFlights(String action, Flight flight) {
        System.out.println("Agent " + name + " performing " + action + " on flight " + flight.getFlightNumber());
        // This would typically interact with a flight database
        return new ArrayList<>(); // Return empty list for now
    }
    
    /**
     * Creates a new flight
     * @param flightNumber The flight number
     * @param airline The airline
     * @param origin The origin city
     * @param destination The destination city
     * @param departureTime The departure time
     * @param arrivalTime The arrival time
     * @param economySeats Number of economy seats
     * @param businessSeats Number of business seats
     * @param firstClassSeats Number of first class seats
     * @param economyPrice Price for economy seats
     * @param businessPrice Price for business seats
     * @param firstClassPrice Price for first class seats
     * @param bookingSystem The booking system to add the flight to
     * @return The created flight
     */
    public Flight createFlight(String flightNumber, String airline, String origin, String destination,
                               Date departureTime, Date arrivalTime, 
                               int economySeats, int businessSeats, int firstClassSeats,
                               double economyPrice, double businessPrice, double firstClassPrice,
                               BookingSystem bookingSystem) {
        
        // Create the flight
        Flight newFlight = new Flight(flightNumber, airline, origin, destination, departureTime, arrivalTime,
                                     economySeats, businessSeats, firstClassSeats,
                                     economyPrice, businessPrice, firstClassPrice);
        
        // Add to booking system
        bookingSystem.getFlights().add(newFlight);
        
        System.out.println("Flight " + flightNumber + " added successfully!");
        return newFlight;
    }
    
    /**
     * Updates the schedule of a flight
     * @param flight The flight to update
     * @param newDepartureTime The new departure time
     * @param newArrivalTime The new arrival time
     * @return true if updated successfully
     */
    public boolean updateFlightSchedule(Flight flight, Date newDepartureTime, Date newArrivalTime) {
        flight.updateSchedule(newDepartureTime, newArrivalTime);
        return true;
    }
    
    /**
     * Updates the seat availability of a flight
     * @param flight The flight to update
     * @param seatClass The seat class to update
     * @param newAvailability The new availability
     * @return true if updated successfully
     */
    public boolean updateSeatAvailability(Flight flight, String seatClass, int newAvailability) {
        // In a real system, this would update the seat availability
        System.out.println(seatClass + " seat availability updated to " + newAvailability);
        return true;
    }
    
    /**
     * Creates a booking for a customer
     * @param customer The customer
     * @param flight The flight
     * @param passengers The passengers
     * @return The created booking
     */
    public Booking createBookingForCustomer(Customer customer, Flight flight, ArrayList<Passenger> passengers) {
        Booking booking = new Booking(generateBookingReference(), customer, flight, passengers);
        System.out.println("Agent " + name + " created booking for customer " + customer.getName());
        // Add commission logic here
        return booking;
    }
    
    /**
     * Modifies an existing booking
     * @param booking The booking to modify
     * @param action The action to perform
     * @param details The details of the modification
     * @return true if modified successfully
     */
    public boolean modifyBooking(Booking booking, String action, Object details) {
        System.out.println("Agent " + name + " modifying booking " + booking.getBookingReference() + " with action: " + action);
        // Implement modification logic based on action and details
        return true;
    }
    
    /**
     * Processes payment for a booking
     * @param booking The booking to pay for
     * @param paymentMethod The payment method
     * @param paymentDetails The payment details
     * @param bookingSystem The booking system to process the payment
     * @return true if payment was successful, false otherwise
     */
    public boolean processPaymentForCustomer(Booking booking, String paymentMethod, Map<String, String> paymentDetails, BookingSystem bookingSystem) {
        return bookingSystem.processPayment(booking, paymentMethod, paymentDetails);
    }
    
    /**
     * Generates a report
     * @param reportType The type of report
     * @param startDate The start date
     * @param endDate The end date
     * @return The generated report
     */
    public String generateReports(String reportType, String startDate, String endDate) {
        System.out.println("Agent " + name + " generating " + reportType + " report from " + startDate + " to " + endDate);
        // Generate different types of reports based on reportType
        return "Report content would be generated here";
    }
    
    /**
     * Generates a unique booking reference
     * @return The booking reference
     */
    private String generateBookingReference() {
        // Simple booking reference generator
        return "AG" + System.currentTimeMillis();
    }
    
    // Getters and Setters
    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }
} 