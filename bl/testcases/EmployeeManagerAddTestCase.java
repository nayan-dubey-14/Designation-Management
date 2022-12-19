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
class EmployeeManagerAddTestCase
{
public static void main(String gg[])
{
String name=gg[0];
int designationCode=Integer.parseInt(gg[1]);
String dob=gg[2];
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
Date date=null;
try
{
date=simpleDateFormat.parse(dob);
}catch(ParseException parseException)
{
}
char gender=gg[3].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[4]);
String basicSalaryString=gg[5];
BigDecimal basicSalary=new BigDecimal(basicSalaryString);
String panNumber=gg[6];
String aadharCardNumber=gg[7];
try
{
EmployeeInterface blEmployee=new Employee();
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
employeeManager.addEmployee(blEmployee);
System.out.println("Employee added with employee id : "+blEmployee.getEmployeeId());
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