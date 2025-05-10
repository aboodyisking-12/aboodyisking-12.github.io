/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package flight.management.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Main class for the Flight Management System
 * @author Abdelrahman
 */
public class FlightManagementSystem {

    private static BookingSystem bookingSystem;
    private static Scanner scanner;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Initialize the booking system
        bookingSystem = new BookingSystem();
        scanner = new Scanner(System.in);
        
        int userTypeChoice;
        
        do {
            // Display main menu
            System.out.println("\n===== FLIGHT MANAGEMENT SYSTEM =====");
            System.out.println("Select User Type:");
            System.out.println("1. Customer");
            System.out.println("2. Agent");
            System.out.println("3. Administrator");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            userTypeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (userTypeChoice >= 1 && userTypeChoice <= 3) {
                String userType;
                switch (userTypeChoice) {
                    case 1:
                        userType = "customer";
                        break;
                    case 2:
                        userType = "agent";
                        break;
                    case 3:
                        userType = "administrator";
                        break;
                    default:
                        userType = "";
                }
                
                handleLoginRegister(userType);
            } else if (userTypeChoice != 0) {
                System.out.println("Invalid choice. Please try again.");
            }
            
        } while (userTypeChoice != 0);
        
        System.out.println("Thank you for using the Flight Management System!");
        scanner.close();
    }
    
    private static void handleLoginRegister(String userType) {
        int choice;
        
        do {
            System.out.println("\n===== " + userType.toUpperCase() + " MENU =====");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    loginUser(userType);
                    break;
                case 2:
                    registerUser(userType);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
        } while (choice != 0);
    }
    
    private static void loginUser(String userType) {
        System.out.println("\n===== LOGIN =====");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        User user = bookingSystem.loginUser(username, password);
        
        if (user != null) {
            // Check if the user type matches
            boolean validUserType = false;
            
            switch (userType) {
                case "customer":
                    validUserType = user instanceof Customer;
                    break;
                case "agent":
                    validUserType = user instanceof Agent;
                    break;
                case "administrator":
                    validUserType = user instanceof Administrator;
                    break;
            }
            
            if (validUserType) {
                switch (userType) {
                    case "customer":
                        showCustomerMenu((Customer) user);
                        break;
                    case "agent":
                        showAgentMenu((Agent) user);
                        break;
                    case "administrator":
                        showAdminMenu((Administrator) user);
                        break;
                }
            } else {
                System.out.println("Invalid user type. Please login with the correct account type.");
            }
        }
    }
    
    private static void registerUser(String userType) {
        System.out.println("\n===== REGISTER =====");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();
        
        User user = bookingSystem.registerUser(userType, username, password, name, email, contactInfo);
        
        if (user != null) {
            System.out.println("Registration successful! Please login.");
        }
    }
    
    private static void showCustomerMenu(Customer customer) {
        int choice;
        
        do {
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("Welcome, " + customer.getName() + "!");
            System.out.println("1. Search Flights");
            System.out.println("2. Create Booking");
            System.out.println("3. View Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Update Profile");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    searchFlights(customer);
                    break;
                case 2:
                    createBooking(customer);
                    break;
                case 3:
                    viewBookings(customer);
                    break;
                case 4:
                    cancelBooking(customer);
                    break;
                case 5:
                    updateProfile(customer);
                    break;
                case 0:
                    customer.logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
        } while (choice != 0);
    }
    
    private static void showAgentMenu(Agent agent) {
        int choice;
        
        do {
            System.out.println("\n===== AGENT MENU =====");
            System.out.println("Welcome, " + agent.getName() + "!");
            System.out.println("1. Manage Flights");
            System.out.println("2. Create Booking for Customer");
            System.out.println("3. Modify Booking");
            System.out.println("4. Generate Reports");
            System.out.println("5. Update Profile");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    manageFlights(agent);
                    break;
                case 2:
                    createBookingForCustomer(agent);
                    break;
                case 3:
                    modifyBooking(agent);
                    break;
                case 4:
                    generateReports(agent);
                    break;
                case 5:
                    updateProfile(agent);
                    break;
                case 0:
                    agent.logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
        } while (choice != 0);
    }
    
    private static void showAdminMenu(Administrator admin) {
        int choice;
        
        do {
            System.out.println("\n===== ADMINISTRATOR MENU =====");
            System.out.println("Welcome, " + admin.getName() + "!");
            System.out.println("1. Create User");
            System.out.println("2. Modify System Settings");
            System.out.println("3. View System Logs");
            System.out.println("4. Manage User Access");
            System.out.println("5. Update Profile");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    createUser(admin);
                    break;
                case 2:
                    modifySystemSettings(admin);
                    break;
                case 3:
                    viewSystemLogs(admin);
                    break;
                case 4:
                    manageUserAccess(admin);
                    break;
                case 5:
                    updateProfile(admin);
                    break;
                case 0:
                    admin.logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
        } while (choice != 0);
    }
    
    // Customer methods
    private static void searchFlights(Customer customer) {
        System.out.println("\n===== SEARCH FLIGHTS =====");
        System.out.print("Enter origin city: ");
        String origin = scanner.nextLine();
        System.out.print("Enter destination city: ");
        String destination = scanner.nextLine();
        System.out.print("Enter departure date (for demo purposes, any input works): ");
        String departureDate = scanner.nextLine();
        
        // Get all available flights from the booking system
        ArrayList<Flight> availableFlights = bookingSystem.getFlights();
        
        if (availableFlights.isEmpty()) {
            System.out.println("No flights available in the system. Please check back later.");
            return;
        }
        
        // Filter flights based on search criteria
        ArrayList<Flight> matchingFlights = new ArrayList<>();
        for (Flight flight : availableFlights) {
            if (flight.getOrigin().equalsIgnoreCase(origin) && 
                flight.getDestination().equalsIgnoreCase(destination)) {
                matchingFlights.add(flight);
            }
        }
        
        System.out.println("\nSearch Results:");
        
        if (matchingFlights.isEmpty()) {
            System.out.println("No flights found matching your criteria.");
            System.out.println("\nHowever, here are all available flights:");
            displayFlights(availableFlights);
            
            System.out.print("\nWould you like to select one of these flights instead? (Y/N): ");
            String choice = scanner.nextLine();
            
            if (choice.equalsIgnoreCase("Y")) {
                System.out.print("Select flight number (1-" + availableFlights.size() + "): ");
                int flightChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                if (flightChoice >= 1 && flightChoice <= availableFlights.size()) {
                    Flight selectedFlight = availableFlights.get(flightChoice - 1);
                    System.out.println("\nSelected flight: " + selectedFlight.getFlightNumber() + 
                                      " - " + selectedFlight.getAirline() + 
                                      " - From: " + selectedFlight.getOrigin() + 
                                      " - To: " + selectedFlight.getDestination());
                    
                    System.out.print("\nWould you like to book this flight? (Y/N): ");
                    String bookChoice = scanner.nextLine();
                    
                    if (bookChoice.equalsIgnoreCase("Y")) {
                        // Create a booking for this flight
                        createBookingForFlight(customer, selectedFlight);
                    }
                } else {
                    System.out.println("Invalid flight selection.");
                }
            }
        } else {
            // Display matching flights
            System.out.println("Found " + matchingFlights.size() + " matching flights:");
            
            for (int i = 0; i < matchingFlights.size(); i++) {
                Flight flight = matchingFlights.get(i);
                System.out.println((i + 1) + ". " + flight.getFlightNumber() + " - " + flight.getAirline() + 
                                  " - From: " + flight.getOrigin() + " - To: " + flight.getDestination() + 
                                  " - Departure: " + flight.getDepartureTime() + 
                                  " - Price: $" + flight.getPrice());
            }
            
            System.out.print("\nSelect flight number to book (0 to cancel): ");
            int flightChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (flightChoice >= 1 && flightChoice <= matchingFlights.size()) {
                Flight selectedFlight = matchingFlights.get(flightChoice - 1);
                createBookingForFlight(customer, selectedFlight);
            } else if (flightChoice != 0) {
                System.out.println("Invalid flight selection.");
            }
        }
    }
    
    // Helper method to create booking for a specific flight
    private static void createBookingForFlight(Customer customer, Flight flight) {
        System.out.print("Enter number of passengers: ");
        int passengerCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        ArrayList<Passenger> passengers = new ArrayList<>();
        
        for (int i = 0; i < passengerCount; i++) {
            System.out.println("\nPassenger " + (i + 1) + " details:");
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter passport number: ");
            String passportNumber = scanner.nextLine();
            System.out.print("Enter date of birth: ");
            String dateOfBirth = scanner.nextLine();
            System.out.print("Enter special requests (if any): ");
            String specialRequests = scanner.nextLine();
            
            Passenger passenger = new Passenger("P" + System.currentTimeMillis() + i, name, passportNumber, dateOfBirth, specialRequests);
            passengers.add(passenger);
        }
        
        Booking booking = customer.createBooking(flight, passengers);
        
        if (booking != null) {
            // Set seat classes for each passenger
            for (Passenger passenger : passengers) {
                System.out.println("\nSelect seat class for " + passenger.getName() + ":");
                System.out.println("1. Economy - $" + flight.getPrice("Economy"));
                System.out.println("2. Business - $" + flight.getPrice("Business"));
                System.out.println("3. First Class - $" + flight.getPrice("First Class"));
                System.out.print("Enter choice (1-3): ");
                int seatClassChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                String seatClass;
                switch (seatClassChoice) {
                    case 1:
                        seatClass = "Economy";
                        break;
                    case 2:
                        seatClass = "Business";
                        break;
                    case 3:
                        seatClass = "First Class";
                        break;
                    default:
                        System.out.println("Invalid choice. Defaulting to Economy.");
                        seatClass = "Economy";
                        break;
                }
                
                booking.setSeatClass(passenger, seatClass);
                
                // Add special request if provided
                if (passenger.getSpecialRequests() != null && !passenger.getSpecialRequests().isEmpty()) {
                    booking.setSpecialRequest(passenger, passenger.getSpecialRequests());
                }
            }
            
            System.out.println("\nBooking created successfully!");
            System.out.println("Booking Reference: " + booking.getBookingReference());
            System.out.println("Total Price: $" + booking.calculateTotalPrice());
            
            System.out.print("\nWould you like to proceed with payment? (Y/N): ");
            String payChoice = scanner.nextLine();
            
            if (payChoice.equalsIgnoreCase("Y")) {
                processPayment(customer, booking);
            }
        }
    }
    
    private static void createBooking(Customer customer) {
        System.out.println("\n===== CREATE BOOKING =====");
        
        // Show available flights
        System.out.println("\nAvailable Flights:");
        ArrayList<Flight> flights = bookingSystem.getFlights();
        
        if (flights.isEmpty()) {
            System.out.println("No flights available. Please check back later.");
            return;
        }
        
        displayFlights(flights);
        
        System.out.print("\nSelect flight number (1-" + flights.size() + "): ");
        int flightChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (flightChoice < 1 || flightChoice > flights.size()) {
            System.out.println("Invalid flight selection.");
            return;
        }
        
        Flight selectedFlight = flights.get(flightChoice - 1);
        createBookingForFlight(customer, selectedFlight);
    }
    
    private static void processPayment(Customer customer, Booking booking) {
        System.out.println("\nSelect payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Bank Account");
        System.out.print("Enter your choice (1-2): ");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String paymentMethod;
        Map<String, String> paymentDetails = new HashMap<>();
        
        switch (paymentChoice) {
            case 1:
                paymentMethod = "Credit Card";
                System.out.println("\n===== CREDIT CARD PAYMENT =====");
                System.out.print("Enter card number: ");
                String cardNumber = scanner.nextLine();
                System.out.print("Enter CVV: ");
                String cvv = scanner.nextLine();
                System.out.print("Enter expiration year (YYYY): ");
                String expirationYear = scanner.nextLine();
                
                paymentDetails.put("cardNumber", cardNumber);
                paymentDetails.put("cvv", cvv);
                paymentDetails.put("expirationYear", expirationYear);
                break;
            case 2:
                paymentMethod = "Bank Account";
                System.out.println("\n===== BANK ACCOUNT PAYMENT =====");
                System.out.print("Enter bank account number: ");
                String accountNumber = scanner.nextLine();
                System.out.print("Enter IBAN: ");
                String iban = scanner.nextLine();
                
                paymentDetails.put("accountNumber", accountNumber);
                paymentDetails.put("iban", iban);
                break;
            default:
                // Default to Credit Card if invalid choice
                paymentMethod = "Credit Card";
                paymentDetails.put("cardNumber", "1234567890123456");
                paymentDetails.put("cvv", "123");
                paymentDetails.put("expirationYear", "2025");
                System.out.println("Using default payment method: Credit Card");
                break;
        }
        
        // Process payment - always successful with simplified implementation
        boolean paymentSuccess = customer.makePayment(booking, paymentMethod, paymentDetails, bookingSystem);
        
        System.out.println("\nBooking confirmed!");
        System.out.println("\n" + bookingSystem.generateTicket(booking));
    }
    
    private static void viewBookings(Customer customer) {
        System.out.println("\n===== YOUR BOOKINGS =====");
        
        ArrayList<Booking> bookings = customer.viewBookings();
        
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
            return;
        }
        
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            System.out.println((i + 1) + ". Booking Reference: " + booking.getBookingReference() + 
                              " - Flight: " + booking.getFlight().getFlightNumber() + 
                              " - From: " + booking.getFlight().getOrigin() + 
                              " - To: " + booking.getFlight().getDestination() + 
                              " - Status: " + booking.getStatus() + 
                              " - Payment: " + booking.getPaymentStatus());
        }
        
        System.out.print("\nEnter booking number to view details (0 to go back): ");
        int bookingChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (bookingChoice > 0 && bookingChoice <= bookings.size()) {
            Booking selectedBooking = bookings.get(bookingChoice - 1);
            System.out.println("\n" + selectedBooking.generateItinerary());
        }
    }
    
    private static void cancelBooking(Customer customer) {
        System.out.println("\n===== CANCEL BOOKING =====");
        
        ArrayList<Booking> bookings = customer.viewBookings();
        
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings to cancel.");
            return;
        }
        
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            System.out.println((i + 1) + ". Booking Reference: " + booking.getBookingReference() + 
                              " - Flight: " + booking.getFlight().getFlightNumber() + 
                              " - Status: " + booking.getStatus());
        }
        
        System.out.print("\nEnter booking number to cancel (0 to go back): ");
        int bookingChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (bookingChoice > 0 && bookingChoice <= bookings.size()) {
            Booking selectedBooking = bookings.get(bookingChoice - 1);
            
            System.out.print("Are you sure you want to cancel this booking? (Y/N): ");
            String confirmation = scanner.nextLine();
            
            if (confirmation.equalsIgnoreCase("Y")) {
                customer.cancelBooking(selectedBooking.getBookingReference());
            }
        }
    }
    
    // Agent methods
    private static void manageFlights(Agent agent) {
        System.out.println("\n===== MANAGE FLIGHTS =====");
        System.out.println("1. View All Flights");
        System.out.println("2. Add New Flight");
        System.out.println("3. Update Flight Schedule");
        System.out.println("4. Update Seat Availability");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1:
                // View all flights
                ArrayList<Flight> flights = bookingSystem.getFlights();
                if (flights.isEmpty()) {
                    System.out.println("\nNo flights available. Please add flights first.");
                } else {
                    displayFlights(flights);
                }
                break;
            case 2:
                // Add new flight
                addNewFlight(agent);
                break;
            case 3:
                // Update flight schedule
                updateFlightSchedule(agent);
                break;
            case 4:
                // Update seat availability
                updateSeatAvailability(agent);
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    
    private static void displayFlights(ArrayList<Flight> flights) {
        System.out.println("\n===== FLIGHT LIST =====");
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
            return;
        }
        
        for (int i = 0; i < flights.size(); i++) {
            Flight flight = flights.get(i);
            System.out.println((i + 1) + ". " + flight.getFlightNumber() + " - " + flight.getAirline() + 
                              " - From: " + flight.getOrigin() + " - To: " + flight.getDestination());
            System.out.println("   Economy: $" + flight.getPrice("Economy") + " (" + flight.getAvailableSeats("Economy") + " seats)");
            System.out.println("   Business: $" + flight.getPrice("Business") + " (" + flight.getAvailableSeats("Business") + " seats)");
            System.out.println("   First Class: $" + flight.getPrice("First Class") + " (" + flight.getAvailableSeats("First Class") + " seats)");
        }
    }
    
    private static void addNewFlight(Agent agent) {
        System.out.println("\n===== ADD NEW FLIGHT =====");
        
        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine();
        
        System.out.print("Enter airline: ");
        String airline = scanner.nextLine();
        
        System.out.print("Enter origin: ");
        String origin = scanner.nextLine();
        
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine();
        
        // Get departure date and time in simpler format
        System.out.print("Enter departure date (DD/MM/YYYY): ");
        String depDateStr = scanner.nextLine();
        String[] depDateParts = depDateStr.split("/");
        
        System.out.print("Enter departure time (HH:MM): ");
        String depTimeStr = scanner.nextLine();
        String[] depTimeParts = depTimeStr.split(":");
        
        // Parse date and time components
        int depDay = Integer.parseInt(depDateParts[0]);
        int depMonth = Integer.parseInt(depDateParts[1]);
        int depYear = Integer.parseInt(depDateParts[2]);
        int depHour = Integer.parseInt(depTimeParts[0]);
        int depMinute = Integer.parseInt(depTimeParts[1]);
        
        // Create departure date
        Date departureTime = new Date(depYear - 1900, depMonth - 1, depDay, depHour, depMinute);
        
        // Get arrival date and time in simpler format
        System.out.print("Enter arrival date (DD/MM/YYYY): ");
        String arrDateStr = scanner.nextLine();
        String[] arrDateParts = arrDateStr.split("/");
        
        System.out.print("Enter arrival time (HH:MM): ");
        String arrTimeStr = scanner.nextLine();
        String[] arrTimeParts = arrTimeStr.split(":");
        
        // Parse date and time components
        int arrDay = Integer.parseInt(arrDateParts[0]);
        int arrMonth = Integer.parseInt(arrDateParts[1]);
        int arrYear = Integer.parseInt(arrDateParts[2]);
        int arrHour = Integer.parseInt(arrTimeParts[0]);
        int arrMinute = Integer.parseInt(arrTimeParts[1]);
        
        // Create arrival date
        Date arrivalTime = new Date(arrYear - 1900, arrMonth - 1, arrDay, arrHour, arrMinute);
        
        System.out.print("Enter economy seats: ");
        int economySeats = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter business seats: ");
        int businessSeats = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter first class seats: ");
        int firstClassSeats = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter economy price: $");
        double economyPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter business price: $");
        double businessPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter first class price: $");
        double firstClassPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        // Use the agent's method to create the flight
        Flight newFlight = agent.createFlight(flightNumber, airline, origin, destination, 
                                             departureTime, arrivalTime, 
                                             economySeats, businessSeats, firstClassSeats, 
                                             economyPrice, businessPrice, firstClassPrice, 
                                             bookingSystem);
        
        if (newFlight != null) {
            System.out.println("Flight is now available in search results.");
        }
    }
    
    private static void updateFlightSchedule(Agent agent) {
        System.out.println("\n===== UPDATE FLIGHT SCHEDULE =====");
        
        // Show all flights
        ArrayList<Flight> flights = bookingSystem.getFlights();
        
        if (flights.isEmpty()) {
            System.out.println("No flights available. Please add flights first.");
            return;
        }
        
        displayFlights(flights);
        
        System.out.print("\nSelect flight number to update (1-" + flights.size() + "): ");
        int flightChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (flightChoice < 1 || flightChoice > flights.size()) {
            System.out.println("Invalid flight selection.");
            return;
        }
        
        Flight selectedFlight = flights.get(flightChoice - 1);
        
        System.out.println("\nCurrent departure time: " + selectedFlight.getDepartureTime());
        System.out.println("Current arrival time: " + selectedFlight.getArrivalTime());
        
        // Get new departure date and time in simpler format
        System.out.print("Enter new departure date (DD/MM/YYYY): ");
        String depDateStr = scanner.nextLine();
        String[] depDateParts = depDateStr.split("/");
        
        System.out.print("Enter new departure time (HH:MM): ");
        String depTimeStr = scanner.nextLine();
        String[] depTimeParts = depTimeStr.split(":");
        
        // Parse date and time components
        int depDay = Integer.parseInt(depDateParts[0]);
        int depMonth = Integer.parseInt(depDateParts[1]);
        int depYear = Integer.parseInt(depDateParts[2]);
        int depHour = Integer.parseInt(depTimeParts[0]);
        int depMinute = Integer.parseInt(depTimeParts[1]);
        
        // Create new departure date
        Date newDeparture = new Date(depYear - 1900, depMonth - 1, depDay, depHour, depMinute);
        
        // Get new arrival date and time in simpler format
        System.out.print("Enter new arrival date (DD/MM/YYYY): ");
        String arrDateStr = scanner.nextLine();
        String[] arrDateParts = arrDateStr.split("/");
        
        System.out.print("Enter new arrival time (HH:MM): ");
        String arrTimeStr = scanner.nextLine();
        String[] arrTimeParts = arrTimeStr.split(":");
        
        // Parse date and time components
        int arrDay = Integer.parseInt(arrDateParts[0]);
        int arrMonth = Integer.parseInt(arrDateParts[1]);
        int arrYear = Integer.parseInt(arrDateParts[2]);
        int arrHour = Integer.parseInt(arrTimeParts[0]);
        int arrMinute = Integer.parseInt(arrTimeParts[1]);
        
        // Create new arrival date
        Date newArrival = new Date(arrYear - 1900, arrMonth - 1, arrDay, arrHour, arrMinute);
        
        if (agent.updateFlightSchedule(selectedFlight, newDeparture, newArrival)) {
            System.out.println("Flight schedule updated successfully.");
        }
    }
    
    private static void updateSeatAvailability(Agent agent) {
        System.out.println("\n===== UPDATE SEAT AVAILABILITY =====");
        
        // Show all flights
        ArrayList<Flight> flights = bookingSystem.getFlights();
        
        if (flights.isEmpty()) {
            System.out.println("No flights available. Please add flights first.");
            return;
        }
        
        displayFlights(flights);
        
        System.out.print("\nSelect flight number to update (1-" + flights.size() + "): ");
        int flightChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (flightChoice < 1 || flightChoice > flights.size()) {
            System.out.println("Invalid flight selection.");
            return;
        }
        
        Flight selectedFlight = flights.get(flightChoice - 1);
        
        System.out.println("\nSelect seat class to update:");
        System.out.println("1. Economy");
        System.out.println("2. Business");
        System.out.println("3. First Class");
        System.out.print("Enter choice: ");
        
        int seatClassChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String seatClass;
        switch (seatClassChoice) {
            case 1:
                seatClass = "Economy";
                break;
            case 2:
                seatClass = "Business";
                break;
            case 3:
                seatClass = "First Class";
                break;
            default:
                System.out.println("Invalid seat class selection.");
                return;
        }
        
        System.out.print("Enter new number of available " + seatClass + " seats: ");
        int newAvailability = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (agent.updateSeatAvailability(selectedFlight, seatClass, newAvailability)) {
            System.out.println("Seat availability updated successfully.");
        }
    }
    
    private static void createBookingForCustomer(Agent agent) {
        System.out.println("\n===== CREATE BOOKING FOR CUSTOMER =====");
        
        // Get customer details from agent
        System.out.println("\nEnter customer details:");
        System.out.print("Customer Name: ");
        String customerName = scanner.nextLine();
        System.out.print("Customer Email: ");
        String customerEmail = scanner.nextLine();
        System.out.print("Customer Contact Info: ");
        String customerContact = scanner.nextLine();
        
        // Create a temporary customer object for the booking
        String customerId = "C" + System.currentTimeMillis();
        Customer selectedCustomer = new Customer("U" + System.currentTimeMillis(), customerEmail, "password", 
                                               customerName, customerEmail, customerContact, 
                                               customerId, "", "");
        
        // Show available flights
        System.out.println("\nAvailable Flights:");
        ArrayList<Flight> flights = bookingSystem.getFlights();
        
        if (flights.isEmpty()) {
            System.out.println("No flights available. Please add flights first.");
            return;
        }
        
        for (int i = 0; i < flights.size(); i++) {
            Flight flight = flights.get(i);
            System.out.println((i + 1) + ". " + flight.getFlightNumber() + " - " + flight.getAirline() + 
                              " - From: " + flight.getOrigin() + " - To: " + flight.getDestination() + 
                              " - Departure: " + flight.getDepartureTime() + 
                              " - Price: $" + flight.getPrice());
        }
        
        System.out.print("Select flight number (1-" + flights.size() + "): ");
        int flightChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (flightChoice < 1 || flightChoice > flights.size()) {
            System.out.println("Invalid flight selection.");
            return;
        }
        
        Flight selectedFlight = flights.get(flightChoice - 1);
        
        System.out.print("Enter number of passengers: ");
        int passengerCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        ArrayList<Passenger> passengers = new ArrayList<>();
        
        for (int i = 0; i < passengerCount; i++) {
            System.out.println("\nPassenger " + (i + 1) + " details:");
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter passport number: ");
            String passportNumber = scanner.nextLine();
            System.out.print("Enter date of birth: ");
            String dateOfBirth = scanner.nextLine();
            System.out.print("Enter special requests (if any): ");
            String specialRequests = scanner.nextLine();
            
            passengers.add(new Passenger("P" + System.currentTimeMillis() + i, name, passportNumber, dateOfBirth, specialRequests));
        }
        
        // Add the customer to the system if not already present
        boolean customerExists = false;
        for (User user : bookingSystem.getUsers()) {
            if (user instanceof Customer && user.getEmail().equals(customerEmail)) {
                selectedCustomer = (Customer) user;
                customerExists = true;
                break;
            }
        }
        
        if (!customerExists) {
            bookingSystem.getUsers().add(selectedCustomer);
        }
        
        // Create booking using BookingSystem instead of agent's method directly
        Booking booking = bookingSystem.createBooking(selectedCustomer, selectedFlight, passengers);
        
        if (booking != null) {
            // Set seat classes for each passenger
            for (Passenger passenger : passengers) {
                System.out.println("\nSelect seat class for " + passenger.getName() + ":");
                System.out.println("1. Economy - $" + selectedFlight.getPrice("Economy"));
                System.out.println("2. Business - $" + selectedFlight.getPrice("Business"));
                System.out.println("3. First Class - $" + selectedFlight.getPrice("First Class"));
                System.out.print("Enter choice (1-3): ");
                int seatClassChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                String seatClass;
                switch (seatClassChoice) {
                    case 1:
                        seatClass = "Economy";
                        break;
                    case 2:
                        seatClass = "Business";
                        break;
                    case 3:
                        seatClass = "First Class";
                        break;
                    default:
                        System.out.println("Invalid choice. Defaulting to Economy.");
                        seatClass = "Economy";
                        break;
                }
                
                booking.setSeatClass(passenger, seatClass);
                
                // Add special request if provided
                if (passenger.getSpecialRequests() != null && !passenger.getSpecialRequests().isEmpty()) {
                    booking.setSpecialRequest(passenger, passenger.getSpecialRequests());
                }
            }
            
            System.out.println("\nBooking created successfully!");
            System.out.println("Booking Reference: " + booking.getBookingReference());
            System.out.println("Total Price: $" + booking.calculateTotalPrice());
            
            System.out.print("\nWould you like to proceed with payment? (Y/N): ");
            String payChoice = scanner.nextLine();
            
            if (payChoice.equalsIgnoreCase("Y")) {
                processPayment(selectedCustomer, booking);
            }
        }
    }
    
    private static void modifyBooking(Agent agent) {
        System.out.println("\n===== MODIFY BOOKING =====");
        
        // Get all bookings from the system
        ArrayList<Booking> allBookings = bookingSystem.getAllBookings();
        
        if (allBookings.isEmpty()) {
            System.out.println("No bookings available in the system.");
            return;
        }
        
        // Display all bookings
        System.out.println("\nAll Bookings:");
        for (int i = 0; i < allBookings.size(); i++) {
            Booking booking = allBookings.get(i);
            System.out.println((i + 1) + ". Booking Reference: " + booking.getBookingReference() + 
                              " - Customer: " + booking.getCustomer().getName() + 
                              " - Flight: " + booking.getFlight().getFlightNumber() + 
                              " - From: " + booking.getFlight().getOrigin() + 
                              " - To: " + booking.getFlight().getDestination() + 
                              " - Status: " + booking.getStatus());
        }
        
        // Select booking to modify
        System.out.print("\nSelect booking number to modify (0 to cancel): ");
        int bookingChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (bookingChoice <= 0 || bookingChoice > allBookings.size()) {
            System.out.println("Operation cancelled.");
            return;
        }
        
        Booking selectedBooking = allBookings.get(bookingChoice - 1);
        
        // Check if booking is cancelled
        if (selectedBooking.getStatus().equals("Cancelled")) {
            System.out.println("Cannot modify a cancelled booking.");
            return;
        }
        
        // Display selected booking details
        System.out.println("\nSelected Booking Details:");
        System.out.println("Reference: " + selectedBooking.getBookingReference());
        System.out.println("Customer: " + selectedBooking.getCustomer().getName());
        System.out.println("Flight: " + selectedBooking.getFlight().getFlightNumber() + 
                          " - " + selectedBooking.getFlight().getOrigin() + 
                          " to " + selectedBooking.getFlight().getDestination());
        System.out.println("Status: " + selectedBooking.getStatus());
        System.out.println("Payment Status: " + selectedBooking.getPaymentStatus());
        
        // Display passengers
        ArrayList<Passenger> passengers = selectedBooking.getPassengers();
        System.out.println("\nPassengers:");
        for (int i = 0; i < passengers.size(); i++) {
            Passenger passenger = passengers.get(i);
            String seatClass = selectedBooking.getSeatClasses().get(passenger);
            System.out.println((i + 1) + ". " + passenger.getName() + 
                              " - Passport: " + passenger.getPassportNumber() + 
                              " - Seat Class: " + seatClass);
            
            if (selectedBooking.getSpecialRequests().containsKey(passenger)) {
                System.out.println("   Special Request: " + selectedBooking.getSpecialRequests().get(passenger));
            }
        }
        
        // Modification options
        System.out.println("\nWhat would you like to modify?");
        System.out.println("1. Passenger Details");
        System.out.println("2. Seat Class");
        System.out.println("3. Special Requests");
        System.out.println("0. Cancel");
        System.out.print("Enter choice: ");
        
        int modificationChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (modificationChoice) {
            case 1:
                modifyPassengerDetails(selectedBooking);
                break;
            case 2:
                modifySeatClass(selectedBooking);
                break;
            case 3:
                modifySpecialRequests(selectedBooking);
                break;
            case 0:
                System.out.println("Modification cancelled.");
                return;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        System.out.println("\nBooking updated successfully!");
    }
    
    // Helper method to modify passenger details
    private static void modifyPassengerDetails(Booking booking) {
        ArrayList<Passenger> passengers = booking.getPassengers();
        
        // Select passenger to modify
        System.out.print("\nSelect passenger number to modify (1-" + passengers.size() + "): ");
        int passengerChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (passengerChoice < 1 || passengerChoice > passengers.size()) {
            System.out.println("Invalid passenger selection.");
            return;
        }
        
        Passenger selectedPassenger = passengers.get(passengerChoice - 1);
        
        // Display current details
        System.out.println("\nCurrent Passenger Details:");
        System.out.println("Name: " + selectedPassenger.getName());
        System.out.println("Passport Number: " + selectedPassenger.getPassportNumber());
        System.out.println("Date of Birth: " + selectedPassenger.getDateOfBirth());
        
        // Get new details
        System.out.println("\nEnter new details (leave blank to keep current value):");
        
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            selectedPassenger.setName(newName);
        }
        
        System.out.print("Enter new passport number: ");
        String newPassport = scanner.nextLine();
        if (!newPassport.isEmpty()) {
            selectedPassenger.setPassportNumber(newPassport);
        }
        
        System.out.print("Enter new date of birth: ");
        String newDOB = scanner.nextLine();
        if (!newDOB.isEmpty()) {
            selectedPassenger.setDateOfBirth(newDOB);
        }
    }
    
    // Helper method to modify seat class
    private static void modifySeatClass(Booking booking) {
        ArrayList<Passenger> passengers = booking.getPassengers();
        Flight flight = booking.getFlight();
        
        // Select passenger to modify
        System.out.print("\nSelect passenger number to modify (1-" + passengers.size() + "): ");
        int passengerChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (passengerChoice < 1 || passengerChoice > passengers.size()) {
            System.out.println("Invalid passenger selection.");
            return;
        }
        
        Passenger selectedPassenger = passengers.get(passengerChoice - 1);
        String currentSeatClass = booking.getSeatClasses().get(selectedPassenger);
        
        // Display current seat class
        System.out.println("\nCurrent Seat Class: " + currentSeatClass);
        
        // Select new seat class
        System.out.println("\nSelect new seat class:");
        System.out.println("1. Economy - $" + flight.getPrice("Economy"));
        System.out.println("2. Business - $" + flight.getPrice("Business"));
        System.out.println("3. First Class - $" + flight.getPrice("First Class"));
        System.out.print("Enter choice (1-3): ");
        
        int seatClassChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String newSeatClass;
        switch (seatClassChoice) {
            case 1:
                newSeatClass = "Economy";
                break;
            case 2:
                newSeatClass = "Business";
                break;
            case 3:
                newSeatClass = "First Class";
                break;
            default:
                System.out.println("Invalid choice. No changes made.");
                return;
        }
        
        // Update seat class if different
        if (!newSeatClass.equals(currentSeatClass)) {
            booking.setSeatClass(selectedPassenger, newSeatClass);
            System.out.println("Seat class updated to " + newSeatClass + ".");
            System.out.println("New total price: $" + booking.calculateTotalPrice());
        } else {
            System.out.println("Same seat class selected. No changes made.");
        }
    }
    
    // Helper method to modify special requests
    private static void modifySpecialRequests(Booking booking) {
        ArrayList<Passenger> passengers = booking.getPassengers();
        
        // Select passenger to modify
        System.out.print("\nSelect passenger number to modify (1-" + passengers.size() + "): ");
        int passengerChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (passengerChoice < 1 || passengerChoice > passengers.size()) {
            System.out.println("Invalid passenger selection.");
            return;
        }
        
        Passenger selectedPassenger = passengers.get(passengerChoice - 1);
        String currentRequest = booking.getSpecialRequests().getOrDefault(selectedPassenger, "None");
        
        // Display current special request
        System.out.println("\nCurrent Special Request: " + currentRequest);
        
        // Enter new special request
        System.out.print("Enter new special request (leave blank to remove): ");
        String newRequest = scanner.nextLine();
        
        if (newRequest.isEmpty()) {
            // Remove special request
            booking.getSpecialRequests().remove(selectedPassenger);
            System.out.println("Special request removed.");
        } else {
            // Update special request
            booking.setSpecialRequest(selectedPassenger, newRequest);
            System.out.println("Special request updated.");
        }
    }
    
    private static void generateReports(Agent agent) {
        System.out.println("\n===== GENERATE REPORTS =====");
        
        System.out.println("1. Sales Report");
        System.out.println("2. Flight Occupancy Report");
        System.out.println("3. Customer Activity Report");
        System.out.print("Enter report type: ");
        
        int reportType = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();
        
        String reportName;
        switch (reportType) {
            case 1:
                reportName = "Sales";
                break;
            case 2:
                reportName = "Flight Occupancy";
                break;
            case 3:
                reportName = "Customer Activity";
                break;
            default:
                reportName = "Unknown";
        }
        
        String report = agent.generateReports(reportName, startDate, endDate);
        System.out.println("\n" + report);
    }
    
    // Admin methods
    private static void createUser(Administrator admin) {
        System.out.println("\n===== CREATE USER =====");
        
        System.out.println("Select user type:");
        System.out.println("1. Customer");
        System.out.println("2. Agent");
        System.out.println("3. Administrator");
        System.out.print("Enter choice: ");
        
        int userTypeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String userType;
        switch (userTypeChoice) {
            case 1:
                userType = "customer";
                break;
            case 2:
                userType = "agent";
                break;
            case 3:
                userType = "administrator";
                break;
            default:
                System.out.println("Invalid user type.");
                return;
        }
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine();
        
        User newUser = admin.createUser(userType, username, password, name, email, contactInfo);
        
        if (newUser != null) {
            System.out.println("User created successfully!");
        }
    }
    
    private static void modifySystemSettings(Administrator admin) {
        System.out.println("\n===== MODIFY SYSTEM SETTINGS =====");
        
        System.out.println("1. Security Settings");
        System.out.println("2. Payment Settings");
        System.out.println("3. Notification Settings");
        System.out.print("Enter setting type: ");
        
        int settingType = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String setting;
        switch (settingType) {
            case 1:
                setting = "security";
                break;
            case 2:
                setting = "payment";
                break;
            case 3:
                setting = "notification";
                break;
            default:
                System.out.println("Invalid setting type.");
                return;
        }
        
        System.out.print("Enter new value: ");
        String value = scanner.nextLine();
        
        boolean success = admin.modifySystemSettings(setting, value);
        
        if (success) {
            System.out.println("System settings updated successfully!");
        }
    }
    
    private static void viewSystemLogs(Administrator admin) {
        System.out.println("\n===== SYSTEM LOGS =====");
        
        ArrayList<String> logs = admin.viewSystemLogs();
        
        for (String log : logs) {
            System.out.println(log);
        }
    }
    
    private static void manageUserAccess(Administrator admin) {
        System.out.println("\n===== MANAGE USER ACCESS =====");
        
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        
        System.out.println("Select access level:");
        System.out.println("1. Basic");
        System.out.println("2. Advanced");
        System.out.println("3. Full");
        System.out.print("Enter choice: ");
        
        int accessChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String accessLevel;
        switch (accessChoice) {
            case 1:
                accessLevel = "basic";
                break;
            case 2:
                accessLevel = "advanced";
                break;
            case 3:
                accessLevel = "full";
                break;
            default:
                System.out.println("Invalid access level.");
                return;
        }
        
        boolean success = admin.manageUserAccess(userId, accessLevel);
        
        if (success) {
            System.out.println("User access updated successfully!");
        }
    }
    
    // Common method for updating profile
    private static void updateProfile(User user) {
        System.out.println("\n===== UPDATE PROFILE =====");
        
        System.out.print("Enter new name (leave blank to keep current): ");
        String name = scanner.nextLine();
        name = name.isEmpty() ? user.getName() : name;
        
        System.out.print("Enter new email (leave blank to keep current): ");
        String email = scanner.nextLine();
        email = email.isEmpty() ? user.getEmail() : email;
        
        System.out.print("Enter new contact info (leave blank to keep current): ");
        String contactInfo = scanner.nextLine();
        contactInfo = contactInfo.isEmpty() ? user.getContactInfo() : contactInfo;
        
        user.updateProfile(name, email, contactInfo);
    }
}
