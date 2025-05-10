package flight.management.system;

import java.util.ArrayList;
import java.util.Map;

/**
 * Customer class for managing customer bookings and profiles
 */
public class Customer extends User {
    private String customerId;
    private String address;
    private ArrayList<Booking> bookingHistory;
    private String preferences;
    
    public Customer(String userId, String username, String password, String name, String email, String contactInfo, 
                    String customerId, String address, String preferences) {
        super(userId, username, password, name, email, contactInfo);
        this.customerId = customerId;
        this.address = address;
        this.preferences = preferences;
        this.bookingHistory = new ArrayList<>();
    }
    
    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    @Override
    public void logout() {
        System.out.println("Customer " + name + " logged out successfully");
    }
    
    @Override
    public void updateProfile(String name, String email, String contactInfo) {
        this.name = name;
        this.email = email;
        this.contactInfo = contactInfo;
        System.out.println("Customer profile updated successfully");
    }
    
    /**
     * Searches for flights based on origin, destination, and departure date
     * @param origin The origin city
     * @param destination The destination city
     * @param departureDate The departure date
     * @return A list of matching flights
     */
    public ArrayList<Flight> searchFlights(String origin, String destination, String departureDate) {
        System.out.println("Searching flights from " + origin + " to " + destination + " on " + departureDate);
        
        // In a real system, this would call the booking system's searchFlights method
        // with proper date parsing
        ArrayList<Flight> results = new ArrayList<>();
        
        // This is a placeholder implementation
        return results;
    }
    
    /**
     * Creates a new booking for the customer
     * @param flight The flight to book
     * @param passengers The list of passengers
     * @return The created booking
     */
    public Booking createBooking(Flight flight, ArrayList<Passenger> passengers) {
        String bookingReference = generateBookingReference();
        Booking booking = new Booking(bookingReference, this, flight, passengers);
        bookingHistory.add(booking);
        System.out.println("Booking created successfully with reference: " + booking.getBookingReference());
        return booking;
    }
    
    /**
     * Gets all bookings for this customer
     * @return The list of bookings
     */
    public ArrayList<Booking> viewBookings() {
        return bookingHistory;
    }
    
    /**
     * Cancels a booking with the given reference
     * @param bookingReference The booking reference
     * @return true if cancelled successfully, false otherwise
     */
    public boolean cancelBooking(String bookingReference) {
        for (Booking booking : bookingHistory) {
            if (booking.getBookingReference().equals(bookingReference)) {
                booking.cancelBooking();
                System.out.println("Booking " + bookingReference + " cancelled successfully");
                return true;
            }
        }
        System.out.println("Booking not found");
        return false;
    }
    
    /**
     * Processes payment for a booking
     * @param booking The booking to pay for
     * @param paymentMethod The payment method
     * @param paymentDetails The payment details
     * @param bookingSystem The booking system to process the payment
     * @return true if payment was successful, false otherwise
     */
    public boolean makePayment(Booking booking, String paymentMethod, Map<String, String> paymentDetails, BookingSystem bookingSystem) {
        return bookingSystem.processPayment(booking, paymentMethod, paymentDetails);
    }
    
    /**
     * Generates a unique booking reference
     * @return The booking reference
     */
    private String generateBookingReference() {
        // Simple booking reference generator
        return "BK" + System.currentTimeMillis();
    }
    
    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Booking> getBookingHistory() {
        return bookingHistory;
    }

    public void setBookingHistory(ArrayList<Booking> bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
} 