package carsharing.Car;


import java.sql.*;
import java.util.ArrayList;

import static carsharing.CarSharing.DB_URL;

public class CarDaoImp implements CarDao {

    @Override
    public ArrayList<CarDto> getCars(int compId) {
        try (Connection con = connect();
             PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM CAR WHERE company_id = ?"))
        {
            prepStmt.setInt(1, compId);
            con.setAutoCommit(true);
            try(ResultSet resultSet = prepStmt.executeQuery()) {
                if(resultSet.first()) {
                    ArrayList<CarDto> carList = new ArrayList<>();
                    System.out.println("Car list:");
                    do {
                        CarDto car = new CarDto();
                        car.setCarId(resultSet.getInt(1));
                        car.setCarName(resultSet.getString(2));
                        carList.add(car);
                    } while(resultSet.next());
                    return carList;
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
        return null;
    }

    @Override
    public void createCar(String carName, int compId) {
        try (Connection con = connect();
            PreparedStatement prepStmt = con.prepareStatement("insert into CAR(name, company_id) values (?, ?)"))
        {
            prepStmt.setString(1, carName);
            prepStmt.setInt(2, compId);
            con.setAutoCommit(true);
            prepStmt.executeUpdate();
            System.out.println("The car was added!\n");
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }

    @Override
    public ArrayList<CarDto> availableCars (int companyId) {
        try(Connection con = connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM CAR WHERE company_id = ? and id NOT IN (SELECT rented_car_id FROM CUSTOMER WHERE rented_car_id IS NOT NULL) ORDER BY id"))
        {
            ps.setInt(1, companyId);
            con.setAutoCommit(true);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.first()) {
                    ArrayList<CarDto> carList = new ArrayList<>();
                    do {
                        CarDto car = new CarDto();
                        car.setCarId(rs.getInt(1));
                        car.setCarName(rs.getString(2));
                        carList.add(car);
                    } while (rs.next());
                    return carList;
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
        return null;
    }

    @Override
    public CarDto rentedCar(int customerId) {
        try(Connection con = connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM CAR WHERE id IN (SELECT rented_car_id FROM CUSTOMER WHERE CUSTOMER.id = ? and rented_car_id IS NOT NULL)"))
        {
            ps.setInt(1, customerId);
            con.setAutoCommit(true);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.first()) {
                    CarDto car = new CarDto();
                    car.setCarId(rs.getInt(1));
                    car.setCarName(rs.getString(2));
                    return car;
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
        return null;
    }

    @Override
    public void returnRentedCar(int customerId) {
        try(Connection con = connect();
            PreparedStatement ps = con.prepareStatement("UPDATE CUSTOMER SET rented_car_id = NULL WHERE id = ?"))
        {
            ps.setInt(1, customerId);
            con.setAutoCommit(true);
            ps.executeUpdate();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
    }

    @Override
    public void rentCar(int carId, int customerId) {
        try(Connection con = connect();
            PreparedStatement ps = con.prepareStatement("UPDATE CUSTOMER SET rented_car_id = ? WHERE id = ?"))
        {
            ps.setInt(1, carId);
            ps.setInt(2, customerId);
            con.setAutoCommit(true);
            ps.executeUpdate();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
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
