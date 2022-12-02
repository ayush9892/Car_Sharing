package carsharing.Company;

import java.util.ArrayList;

public interface CompanyDao {
    ArrayList<CompanyDto> getCompanies();
    void createCompany(String cmpName);
    String rentedCarCompName(int CarId);
}
