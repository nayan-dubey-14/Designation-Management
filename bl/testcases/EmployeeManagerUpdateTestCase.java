import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.enums.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
class EmployeeManagerUpdateTestCase
{
public static void main(String gg[])
{
String employeeId=gg[0];
String name=gg[1];
int designationCode=Integer.parseInt(gg[2]);
String dob=gg[3];
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
Date date=null;
try
{
date=simpleDateFormat.parse(dob);
}catch(ParseException parseException)
{
}
char gender=gg[4].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[5]);
String basicSalaryString=gg[6];
BigDecimal basicSalary=new BigDecimal(basicSalaryString);
String panNumber=gg[7];
String aadharCardNumber=gg[8];
try
{
EmployeeInterface blEmployee=new Employee();
blEmployee.setEmployeeId(employeeId);
blEmployee.setName(name);
DesignationInterface designation=new Designation();
designation.setCode(designationCode);
blEmployee.setDesignation(designation);
blEmployee.setDateOfBirth(date);
if(gender=='M') blEmployee.setGender(GENDER.MALE);
if(gender=='F') blEmployee.setGender(GENDER.FEMALE);
blEmployee.setIndian(isIndian);
blEmployee.setBasicSalary(basicSalary);
blEmployee.setPANNumber(panNumber);
blEmployee.setAadharCardNumber(aadharCardNumber);
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.updateEmployee(blEmployee);
System.out.println("Employee updated");
}catch(BLException blException)
{
if(blException.hasGenericException()) System.out.println(blException.getGenericException());
List<String> list=blException.getProperties();
for(String s:list)
{
System.out.println(blException.getException(s));
}
}
}
}