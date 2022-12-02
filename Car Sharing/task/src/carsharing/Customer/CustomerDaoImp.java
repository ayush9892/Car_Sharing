package carsharing.Customer;

import java.sql.*;
import java.util.ArrayList;

import static carsharing.CarSharing.DB_URL;

public class CustomerDaoImp implements CustomerDao{

    @Override
    public void createCustomer(String customerName) {
        try (Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO CUSTOMER(name) VALUES (?)"))
        {
            ps.setString(1, customerName);
            conn.setAutoCommit(true);
            ps.executeUpdate();
            System.out.println("The customer was added!\n");
            conn.setAutoCommit(true);
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }

    @Override
    public ArrayList<CustomerDto> getCustomers() {
        try (Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM CUSTOMER"))
        {
            conn.setAutoCommit(true);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.first()) {
                    System.out.println("Customer list:");
                    ArrayList<CustomerDto> customerList = new ArrayList<>();
                    do {
                        CustomerDto customer = new CustomerDto();
                        customer.setCustomerId(rs.getInt(1));
                        customer.setCustomerName(rs.getString(2));
                        customerList.add(customer);
                    } while (rs.next());
                    return customerList;
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
        return null;
    }

    @Override
    public boolean isCustomerRentedCar(int customerId) {
        try(Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM CUSTOMER WHERE id = ? and rented_car_id IS NOT NULL"))
        {
            ps.setInt(1, customerId);
            conn.setAutoCommit(true);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.first()) {
                    return true;
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
        return false;
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
