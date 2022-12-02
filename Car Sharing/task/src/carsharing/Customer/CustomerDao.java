package carsharing.Customer;

import java.util.ArrayList;

public interface CustomerDao {
    ArrayList<CustomerDto> getCustomers();
    void createCustomer(String customerName);
    boolean isCustomerRentedCar(int customerId);
}
