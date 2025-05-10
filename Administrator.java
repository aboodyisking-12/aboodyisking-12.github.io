package flight.management.system;

import java.util.ArrayList;

/**
 * Administrator class for managing system settings and user access
 */
public class Administrator extends User {
    private String adminId;
    private int securityLevel;
    
    public Administrator(String userId, String username, String password, String name, String email, String contactInfo, 
                         String adminId, int securityLevel) {
        super(userId, username, password, name, email, contactInfo);
        this.adminId = adminId;
        this.securityLevel = securityLevel;
    }
    
    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    @Override
    public void logout() {
        System.out.println("Administrator " + name + " logged out successfully");
    }
    
    @Override
    public void updateProfile(String name, String email, String contactInfo) {
        this.name = name;
        this.email = email;
        this.contactInfo = contactInfo;
        System.out.println("Administrator profile updated successfully");
    }
    
    public User createUser(String userType, String username, String password, String name, String email, String contactInfo) {
        System.out.println("Administrator " + this.name + " creating new " + userType + " user: " + username);
        
        // Create different types of users based on userType
        switch (userType.toLowerCase()) {
            case "customer":
                return new Customer(generateUserId(), username, password, name, email, contactInfo, 
                                   generateCustomerId(), "", "");
            case "agent":
                return new Agent(generateUserId(), username, password, name, email, contactInfo, 
                                generateAgentId(), "General", 0.0);
            case "administrator":
                return new Administrator(generateUserId(), username, password, name, email, contactInfo, 
                                        generateAdminId(), 1);
            default:
                System.out.println("Invalid user type");
                return null;
        }
    }
    
    public boolean modifySystemSettings(String setting, String value) {
        System.out.println("Administrator " + name + " modifying system setting: " + setting + " to " + value);
        // Implement system settings modification logic
        return true;
    }
    
    public ArrayList<String> viewSystemLogs() {
        System.out.println("Administrator " + name + " viewing system logs");
        // This would typically fetch logs from a database or file
        ArrayList<String> logs = new ArrayList<>();
        logs.add("Sample log entry 1");
        logs.add("Sample log entry 2");
        return logs;
    }
    
    public boolean manageUserAccess(String userId, String accessLevel) {
        System.out.println("Administrator " + name + " changing access level for user " + userId + " to " + accessLevel);
        // Implement user access management logic
        return true;
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
    
    // Getters and Setters
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }
} 