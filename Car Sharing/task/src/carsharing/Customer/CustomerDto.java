package carsharing.Customer;

public class CustomerDto {
    private int CustomerId;
    private String CustomerName;

    public int getCustomerId() {
        return CustomerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }
    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }
}
