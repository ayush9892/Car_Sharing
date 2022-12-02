package carsharing;

import carsharing.Car.CarDao;
import carsharing.Car.CarDto;
import carsharing.Company.CompanyDao;
import carsharing.Company.CompanyDto;
import carsharing.Customer.CustomerDao;
import carsharing.Customer.CustomerDto;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    static Scanner scanner = new Scanner(System.in);
    int response;
    CompanyDao cmpDao;
    CarDao carDao;
    CustomerDao cusDao;

    public Menu(CompanyDao cmpDao, CarDao carDao, CustomerDao cusDao) {
        this.cmpDao = cmpDao;
        this.carDao = carDao;
        this.cusDao = cusDao;
    }

    public void MainMenu() {
        while (true) {
            System.out.println("""
             1. Log in as a manager
             2. Log in as a customer
             3. Create a customer
             0. Exit"""
            );
            response = scanner.nextInt();
            System.out.println();
            if (response == 1) {
                ManagerMenu();
            } else if(response == 2) {
                CustomerList();
            } else if(response == 3) {
                System.out.println("Enter the customer name:");
                scanner.nextLine();
                String cusName = scanner.nextLine();
                cusDao.createCustomer(cusName);
            } else if (response == 0) {
                break;
            } else {
                System.out.print("Wrong response! Enter again.");
            }
        }
    }

    private void ManagerMenu() {
        while (true) {
            System.out.println("""
                    1. Company list
                    2. Create a company
                    0. Back"""
            );
            response = scanner.nextInt();
            System.out.println();
            if (response == 1) {
                CompanyDto company = companyList();
                if(company != null) {
                    CarMenu(company);
                }
            } else if (response == 2) {
                System.out.println("Enter the company name: ");
                scanner.nextLine();
                String compName = scanner.nextLine();
                cmpDao.createCompany(compName);
            } else if (response == 0) {
                break;
            } else {
                System.out.print("Wrong response! Enter again.");
            }
        }
    }

    private void CustomerList() {
        ArrayList<CustomerDto> cusList = cusDao.getCustomers();
        if (cusList != null) {
            cusList.forEach((cus) -> System.out.println(cus.getCustomerId() + ". " + cus.getCustomerName()));
            System.out.println("0. Back");
            response = scanner.nextInt();
            System.out.println();
            if (response != 0) {
                CustomerMenu(cusList.get(response-1).getCustomerId());
            }
        } else {
            System.out.println("The customer list is empty!\n");
        }
    }

    private void CustomerMenu(int customerID) {
        while (true) {
            System.out.println("""
                1. Rent a car
                2. Return a rented car
                3. My rented car
                0. Back"""
            );
            response = scanner.nextInt();
            System.out.println();
            if (response == 1) {
                if(!cusDao.isCustomerRentedCar(customerID)) {
                    CompanyDto company = companyList();
                    if(company != null) {
                        ArrayList<CarDto> carList = carDao.availableCars(company.getCompanyId());
                        if(carList != null) {
                            System.out.println("Choose a car:");
                            CarList(carList);
                            System.out.println("0. Back");
                            int selectCar = scanner.nextInt();
                            if (selectCar != 0) {
                                carDao.rentCar(carList.get(selectCar-1).getCarId(), customerID);
                                System.out.println("You rented '" + carList.get(selectCar-1).getCarName() + "'");
                            }
                        } else {
                            System.out.println("No available cars in the " + company.getCompanyId() + " company.");
                        }
                    }
                } else {
                    System.out.println("You've already rented a car!");
                }
            } else if (response == 2) {
                if(cusDao.isCustomerRentedCar(customerID)) {
                    carDao.returnRentedCar(customerID);
                    System.out.println("You've returned a rented car!");
                } else {
                    System.out.println("You didn't rent a car!\n");
                }
            } else if (response == 3) {
                CarDto car = carDao.rentedCar(customerID);
                if (car != null) {
                    System.out.println("Your rented car:");
                    System.out.println(car.getCarName());
                    System.out.println("Company:");
                    System.out.println(cmpDao.rentedCarCompName(car.getCarId()));
                    System.out.println();
                } else {
                    System.out.println("You didn't rent a car!\n");
                }
            } else if (response == 0) {
                break;
            }
        }
    }


    private CompanyDto companyList() {
        ArrayList<CompanyDto> companyList = cmpDao.getCompanies();
        if (companyList != null) {
            companyList.forEach((cmp) -> System.out.println(cmp.getCompanyId() + ". " + cmp.getCompanyName()));
            System.out.println("0. Back");
            int companyID = scanner.nextInt();
            System.out.println();
            if (companyID != 0) {
                return companyList.get(companyID-1);
            }
        } else {
            System.out.println("The company list is empty!\n");
        }
        return null;
    }

    private void CarMenu(CompanyDto company) {
        System.out.println("'" + company.getCompanyName() + "' company");
        while (true) {
            System.out.println("""
                1. Car list
                2. Create a car
                0. Back"""
            );
            response = scanner.nextInt();
            System.out.println();
            if(response == 1) {
                ArrayList<CarDto> carList = carDao.getCars(company.getCompanyId());
                if (carList != null) {
                    CarList(carList);
                    System.out.println();
                } else {
                    System.out.println("The car list is empty!");
                }
            } else if (response == 2) {
                System.out.println("Enter the car name: ");
                scanner.nextLine();
                String compName = scanner.nextLine();
                carDao.createCar(compName, company.getCompanyId());
            } else if (response == 0) {
                break;
            } else {
                System.out.print("Wrong response! Enter again.");
            }
        }
    }

    private void CarList(ArrayList<CarDto> carList) {
        int i = 1;
        for (CarDto car: carList) {
            System.out.println(i + ". " + car.getCarName());
            i++;
        }
    }
}
