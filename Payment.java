package flight.management.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Payment class for handling payment information and processing
 */
public class Payment {
    private String paymentId;
    private String bookingReference;
    private double amount;
    private String method; // Credit Card, Bank Account
    private String status; // Successful, Failed, Pending, Refunded
    private Date transactionDate;
    private Map<String, String> paymentDetails; // Stores details specific to payment method
    
    public Payment(String paymentId, String bookingReference, double amount, String method) {
        this.paymentId = paymentId;
        this.bookingReference = bookingReference;
        this.amount = amount;
        this.method = method;
        this.status = "Pending";
        this.transactionDate = new Date();
        this.paymentDetails = new HashMap<>();
    }
    
    /**
     * Sets credit card details for payment
     * @param cardNumber The credit card number
     * @param cvv The CVV code
     * @param expirationYear The expiration year
     * @return true if details are valid, false otherwise
     */
    public boolean setCreditCardDetails(String cardNumber, String cvv, String expirationYear) {
        // Store the details without validation
        paymentDetails.put("cardNumber", maskCardNumber(cardNumber));
        paymentDetails.put("cvv", "***");
        paymentDetails.put("expirationYear", expirationYear);
        
        return true;
    }
    
    /**
     * Sets bank account details for payment
     * @param accountNumber The bank account number
     * @param iban The IBAN code
     * @return true if details are valid, false otherwise
     */
    public boolean setBankAccountDetails(String accountNumber, String iban) {
        // Store the details without validation
        paymentDetails.put("accountNumber", maskAccountNumber(accountNumber));
        paymentDetails.put("iban", maskIban(iban));
        
        return true;
    }
    
    /**
     * Processes the payment with the provided details
     * @return true if payment is successful, false otherwise
     */
    public boolean processPayment() {
        // Simplified payment processing - always succeeds
        System.out.println("Processing payment of $" + amount + " using " + method + "...");
        
        // Simulate connection to payment gateway
        System.out.println("Connecting to payment gateway...");
        
        // Simulate processing time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Always successful
        status = "Successful";
        System.out.println("Payment processed successfully");
        System.out.println("Transaction ID: " + paymentId);
        System.out.println("Amount: $" + amount);
        System.out.println("Date: " + transactionDate);
        
        return true;
    }
    
    /**
     * Validates that we have all necessary details for the payment method
     * @return true if all required details are present, false otherwise
     */
    public boolean validatePaymentDetails() {
        // Simplified validation - always returns true
        return true;
    }
    
    /**
     * Updates the payment status
     * @param newStatus The new status to set
     */
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Payment status updated to: " + newStatus);
    }
    
    /**
     * Masks a credit card number for security
     * @param cardNumber The card number to mask
     * @return The masked card number
     */
    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() <= 4) {
            return cardNumber;
        }
        
        String lastFour = cardNumber.substring(cardNumber.length() - 4);
        return "****-****-****-" + lastFour;
    }
    
    /**
     * Masks a bank account number for security
     * @param accountNumber The account number to mask
     * @return The masked account number
     */
    private String maskAccountNumber(String accountNumber) {
        if (accountNumber.length() <= 4) {
            return accountNumber;
        }
        
        String lastFour = accountNumber.substring(accountNumber.length() - 4);
        return "******" + lastFour;
    }
    
    /**
     * Masks an IBAN for security
     * @param iban The IBAN to mask
     * @return The masked IBAN
     */
    private String maskIban(String iban) {
        if (iban.length() <= 4) {
            return iban;
        }
        
        String lastFour = iban.substring(iban.length() - 4);
        return "**********" + lastFour;
    }
    
    /**
     * Generates a payment receipt
     * @return The payment receipt as a string
     */
    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("===== PAYMENT RECEIPT =====\n");
        receipt.append("Payment ID: ").append(paymentId).append("\n");
        receipt.append("Booking Reference: ").append(bookingReference).append("\n");
        receipt.append("Amount: $").append(amount).append("\n");
        receipt.append("Payment Method: ").append(method).append("\n");
        
        if (method.equals("Credit Card")) {
            receipt.append("Card Number: ").append(paymentDetails.getOrDefault("cardNumber", "N/A")).append("\n");
        } else if (method.equals("Bank Account")) {
            receipt.append("Account Number: ").append(paymentDetails.getOrDefault("accountNumber", "N/A")).append("\n");
        }
        
        receipt.append("Status: ").append(status).append("\n");
        receipt.append("Transaction Date: ").append(transactionDate).append("\n");
        receipt.append("===========================\n");
        
        return receipt.toString();
    }
    
    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public Map<String, String> getPaymentDetails() {
        return paymentDetails;
    }
    
    public void setPaymentDetails(Map<String, String> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
} 