package flight.management.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

/**
 * BookingSystem class for coordinating the entire booking system
 */
public class BookingSystem {
    private ArrayList<User> users;
    private ArrayList<Flight> flights;
    private ArrayList<Booking> bookings;
    private ArrayList<Payment> payments;
    
    public BookingSystem() {
        users = new ArrayList<>();
        flights = new ArrayList<>();
        bookings = new ArrayList<>();
        payments = new ArrayList<>();
        
        // Initialize with some sample data
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // Add a default administrator
        Administrator admin = new Administrator("U001", "admin", "admin123", "System Admin", "admin@flightsys.com", "123-456-7890", 
                                              "AD001", 3);
        users.add(admin);
        
        // Add a sample agent
        Agent agent = new Agent("U003", "agent007", "bond007", "Jane Smith", "jane@agency.com", "555-987-6543",
                               "A001", "International Bookings", 5.0);
        users.add(agent);
        
        // Initialize with empty flights list
        // No sample flights added by default
    }
    
    public ArrayList<Flight> searchFlights(String origin, String destination, Date departureDate) {
        ArrayList<Flight> results = new ArrayList<>();
        
        for (Flight flight : flights) {
            if (flight.getOrigin().equalsIgnoreCase(origin) && 
                flight.getDestination().equalsIgnoreCase(destination)) {
                // In a real system, would also check date ranges
                results.add(flight);
            }
        }
        
        return results;
    }
    
    public Booking createBooking(Customer customer, Flight flight, ArrayList<Passenger> passengers) {
        if (!flight.checkAvailability()) {
            System.out.println("Flight is fully booked");
            return null;
        }
        
        String bookingReference = generateBookingReference();
        Booking booking = new Booking(bookingReference, customer, flight, passengers);
        bookings.add(booking);
        
        // Reserve seats for each passenger
        for (int i = 0; i < passengers.size(); i++) {
            flight.reserveSeat();
        }
        
        return booking;
    }
    
    public boolean processPayment(Booking booking, String method, Map<String, String> paymentDetails) {
        double amount = booking.calculateTotalPrice();
        String paymentId = generatePaymentId();
        
        Payment payment = new Payment(paymentId, booking.getBookingReference(), amount, method);
        
        // Set payment details based on the payment method
        if (method.equals("Credit Card")) {
            payment.setCreditCardDetails(
                paymentDetails.get("cardNumber"),
                paymentDetails.get("cvv"),
                paymentDetails.get("expirationYear")
            );
        } else if (method.equals("Bank Account")) {
            payment.setBankAccountDetails(
                paymentDetails.get("accountNumber"),
                paymentDetails.get("iban")
            );
        }
        
        payments.add(payment);
        
        // Process payment - always successful with simplified implementation
        payment.processPayment();
        booking.setPaymentStatus("Paid");
        booking.confirmBooking();
        System.out.println(payment.generateReceipt());
        return true;
    }
    
    // For backward compatibility
    public boolean processPayment(Booking booking, String method) {
        Map<String, String> dummyDetails = new HashMap<>();
        if (method.equals("Credit Card")) {
            dummyDetails.put("cardNumber", "4111111111111111");
            dummyDetails.put("cvv", "123");
            dummyDetails.put("expirationYear", "2025");
        } else if (method.equals("Bank Account")) {
            dummyDetails.put("accountNumber", "123456789");
            dummyDetails.put("iban", "DE89370400440532013000");
        }
        return processPayment(booking, method, dummyDetails);
    }
    
    public String generateTicket(Booking booking) {
        if (!booking.getStatus().equals("Confirmed")) {
            return "Cannot generate ticket. Booking is not confirmed.";
        }
        
        return booking.generateItinerary();
    }
    
    public User registerUser(String userType, String username, String password, String name, String email, String contactInfo) {
        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists");
                return null;
            }
        }
        
        // Simplified password validation - just check length
        if (password.length() < 6) {
            System.out.println("Password must be at least 6 characters long");
            return null;
        }
        
        // Create user based on type
        User newUser = null;
        String userId = generateUserId();
        
        switch (userType.toLowerCase()) {
            case "customer":
                newUser = new Customer(userId, username, password, name, email, contactInfo, 
                                      generateCustomerId(), "", "");
                break;
            case "agent":
                newUser = new Agent(userId, username, password, name, email, contactInfo, 
                                   generateAgentId(), "General", 0.0);
                break;
            case "administrator":
                newUser = new Administrator(userId, username, password, name, email, contactInfo, 
                                          generateAdminId(), 1);
                break;
            default:
                System.out.println("Invalid user type");
                return null;
        }
        
        users.add(newUser);
        System.out.println("User registered successfully");
        return newUser;
    }
    
    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful");
                return user;
            }
        }
        System.out.println("Invalid username or password");
        return null;
    }
    
    private String generateBookingReference() {
        return "BK" + System.currentTimeMillis();
    }
    
    private String generatePaymentId() {
        return "PY" + System.currentTimeMillis();
    }
    
    private String generateUserId() {
        return "U" + System.currentTimeMillis();
    }
    
    private String generateCustomerId() {
        return "C" + System.currentTimeMillis();
    }
    
    private String generateAgentId() {
        return "A" + System.currentTimeMillis();
    }
    
    private String generateAdminId() {
        return "AD" + System.currentTimeMillis();
    }
    
    public ArrayList<Booking> getAllBookings() {
        return bookings;
    }

    // Getters and Setters
    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }
} 