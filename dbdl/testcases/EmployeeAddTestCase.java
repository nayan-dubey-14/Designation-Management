import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.text.*;
class EmployeeAddTestCase
{
public static void main(String gg[])
{
try
{
String name=gg[0];
int designationCode=Integer.parseInt(gg[1].trim());
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
Date dob=sdf.parse(gg[2]);
char gender=gg[3].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[4]);
BigDecimal basicSalary=new BigDecimal(gg[5]);
String panNumber=gg[6];
String aadharNumber=gg[7];
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dob);
if(gender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
if(gender=='F')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharNumber(aadharNumber);
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.add(employeeDTO);
System.out.println("Employee Added Successfully with employee id : "+employeeDTO.getEmployeeId());
}catch(ParseException parseException)
{
System.out.println(parseException.getMessage());
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}

}

}