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
class EmployeeUpdateTestCase
{
public static void main(String gg[])
{
try
{
String employeeId=gg[0];
String name=gg[1];
int designationCode=Integer.parseInt(gg[2].trim());
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
Date dob=sdf.parse(gg[3]);
char gender=gg[4].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[5]);
BigDecimal basicSalary=new BigDecimal(gg[6]);
String panNumber=gg[7];
String aadharNumber=gg[8];
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
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
employeeDAO.update(employeeDTO);
System.out.println("Employee Updated Successfully with employee id : "+employeeDTO.getEmployeeId());
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