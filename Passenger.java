package flight.management.system;

/**
 * Passenger class for storing passenger information
 */
public class Passenger {
    private String passengerId;
    private String name;
    private String passportNumber;
    private String dateOfBirth;
    private String specialRequests;
    
    public Passenger(String passengerId, String name, String passportNumber, String dateOfBirth, String specialRequests) {
        this.passengerId = passengerId;
        this.name = name;
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth;
        this.specialRequests = specialRequests;
    }
    
    public void updateInfo(String name, String passportNumber, String dateOfBirth, String specialRequests) {
        this.name = name;
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth;
        this.specialRequests = specialRequests;
        System.out.println("Passenger information updated successfully");
    }
    
    public String getPassengerDetails() {
        return "Passenger ID: " + passengerId + 
               "\nName: " + name + 
               "\nPassport Number: " + passportNumber + 
               "\nDate of Birth: " + dateOfBirth + 
               "\nSpecial Requests: " + specialRequests;
    }
    
    // Getters and Setters
    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }
} 