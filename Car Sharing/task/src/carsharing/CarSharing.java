package carsharing;

import carsharing.Car.CarDao;
import carsharing.Car.CarDaoImp;
import carsharing.Company.CompanyDao;
import carsharing.Company.CompanyDaoImp;
import carsharing.Customer.CustomerDao;
import carsharing.Customer.CustomerDaoImp;

import java.sql.*;

public class CarSharing {
//    static final String DB_URL = "jdbc:mysql://localhost:3306/carsharing";
//    static final String USERNAME = "root";
//    static final String PASS = "***";

//        static final String JDBC_DRIVER = "org.h2.Driver";
        public static final String DB_URL = "jdbc:h2:./src/carsharing/db/carsharing";

        private final CompanyDao company;
        private final CustomerDao customer;
        private final CarDao car;

        public CarSharing() {
            initDatabase();
            company = new CompanyDaoImp();
            car = new CarDaoImp();
            customer = new CustomerDaoImp();
        }


        public void initDatabase() {
            Connection conn;
            try {
                //Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL);
                Statement st = conn.createStatement();
                st.executeUpdate("create table IF NOT EXISTS COMPANY(id int auto_increment primary key, " +
                        "name varchar(55) unique not null)");
                st.executeUpdate("create table IF NOT EXISTS CAR(id int auto_increment primary key, " +
                        "name varchar(55) unique not null, " +
                        "company_id int not null, " +
                        "foreign key(company_id) references COMPANY(id))");
                st.executeUpdate("create table IF NOT EXISTS CUSTOMER(id int auto_increment primary key, " +
                        "name varchar(55) unique not null, " +
                        "rented_car_id int default null, " +
                        "foreign key(rented_car_id) references CAR(id))");

                st.executeUpdate("ALTER TABLE COMPANY ALTER COLUMN id RESTART WITH 1");
                st.executeUpdate("ALTER TABLE CAR ALTER COLUMN id RESTART WITH 1");
                st.executeUpdate("ALTER TABLE CUSTOMER ALTER COLUMN id RESTART WITH 1");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        public void run() {
            Menu mnu = new Menu(company, car, customer);
            mnu.MainMenu();

//            try {
                //conn = DriverManager.getConnection(DB_URL, USERNAME, PASS);


//            st.executeUpdate("drop table customer");
//            st.executeUpdate("drop table car");
//            st.executeUpdate("drop table company");

//            st.executeUpdate("ALTER TABLE COMPANY ALTER COLUMN id RESTART WITH 1");
//            st.executeUpdate("ALTER TABLE CAR ALTER COLUMN id RESTART WITH 1");



//            } catch(SQLException | ClassNotFoundException se) {
//                se.printStackTrace();
//            } finally {
//                try {
//                    conn.setAutoCommit(true);
//                    conn.close();
//                } catch (SQLException sqe) {
//                    sqe.printStackTrace();
//                }
//            }
        }
}
