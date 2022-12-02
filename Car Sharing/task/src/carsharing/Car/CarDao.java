package carsharing.Car;

import java.util.ArrayList;

public interface CarDao {
    ArrayList<CarDto> getCars(int companyId);
    void createCar(String carName, int cmpId);
    ArrayList<CarDto> availableCars(int companyId);
    CarDto rentedCar(int customerId);
    void returnRentedCar(int customerId);
    void rentCar(int carId, int customerId);
}