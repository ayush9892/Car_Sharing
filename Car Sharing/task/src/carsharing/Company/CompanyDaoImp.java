package carsharing.Company;

import static carsharing.CarSharing.DB_URL;

import java.sql.*;
import java.util.ArrayList;

public class CompanyDaoImp implements CompanyDao {

    @Override
    public ArrayList<CompanyDto> getCompanies() {
        try (Connection con = connect();
             PreparedStatement prepStmt = con.prepareStatement("SELECT * FROM COMPANY ORDER BY id"))
        {
            con.setAutoCommit(true);
            try (ResultSet resultSet = prepStmt.executeQuery()) {
                if(resultSet.first()) {
                    System.out.println("Choose a company:");
                    ArrayList<CompanyDto> companyList = new ArrayList<>();
                    do {
                        CompanyDto company = new CompanyDto();
                        company.setCompanyId(resultSet.getInt(1));
                        company.setCompanyName(resultSet.getString(2));
                        companyList.add(company);
                    } while(resultSet.next());
                    return companyList;
                }
            }

        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
        return null;
    }

    @Override
    public void createCompany(String cmpName) {
        try (Connection con = connect();
            PreparedStatement prepStmt = con.prepareStatement("insert into COMPANY(name) values (?)"))
        {
            prepStmt.setString(1, cmpName);
            con.setAutoCommit(true);
            prepStmt.executeUpdate();
            System.out.println("The company was created!\n");
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }

    @Override
    public String rentedCarCompName(int carId) {
        try(Connection con = connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM COMPANY WHERE id IN (SELECT company_id FROM CAR WHERE id = ?)"))
        {
            ps.setInt(1, carId);
            con.setAutoCommit(true);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.first()) {
                    return rs.getString(2);
                }
            }
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe);
        }
        return null;
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
