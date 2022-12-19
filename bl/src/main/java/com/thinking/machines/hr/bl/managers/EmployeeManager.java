package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.enums.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Set<EmployeeInterface> employeeSet;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;
private static EmployeeManager employeeManager=null;
private EmployeeManager() throws BLException
{
populateDataStructures();
}
private void populateDataStructures() throws BLException
{
this.employeeIdWiseEmployeesMap=new HashMap<>();
this.panNumberWiseEmployeesMap=new HashMap<>();
this.aadharCardNumberWiseEmployeesMap=new HashMap<>();
this.employeeSet=new TreeSet<>();
this.designationCodeWiseEmployeesMap=new HashMap<>();
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
Set<EmployeeDTOInterface> dlSet=employeeDAO.getAll();
DesignationManagerInterface blDesignation=DesignationManager.getDesignationManager();
Set<EmployeeInterface> ets;
for(EmployeeDTOInterface dlEmployeeDTO:dlSet)
{
EmployeeInterface blEmployee=new Employee();
blEmployee.setEmployeeId(dlEmployeeDTO.getEmployeeId());
blEmployee.setName(dlEmployeeDTO.getName());
DesignationInterface designation=blDesignation.getDesignationInterfaceByCode(dlEmployeeDTO.getDesignationCode());
blEmployee.setDesignation(designation);
blEmployee.setDateOfBirth(dlEmployeeDTO.getDateOfBirth());
char gender=dlEmployeeDTO.getGender();
if(gender=='M')
{
blEmployee.setGender(GENDER.MALE);
}
if(gender=='F')
{
blEmployee.setGender(GENDER.FEMALE);
}
blEmployee.setIndian(dlEmployeeDTO.getIsIndian());
blEmployee.setBasicSalary(dlEmployeeDTO.getBasicSalary());
blEmployee.setPANNumber(dlEmployeeDTO.getPANNumber());
blEmployee.setAadharCardNumber(dlEmployeeDTO.getAadharNumber());
this.employeeIdWiseEmployeesMap.put(dlEmployeeDTO.getEmployeeId().toUpperCase(),blEmployee);
this.panNumberWiseEmployeesMap.put(dlEmployeeDTO.getPANNumber().toUpperCase(),blEmployee);
this.aadharCardNumberWiseEmployeesMap.put(dlEmployeeDTO.getAadharNumber().toUpperCase(),blEmployee);
this.employeeSet.add(blEmployee);
ets=this.designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(blEmployee);
this.designationCodeWiseEmployeesMap.put(designation.getCode(),ets);
}
else
{
ets.add(blEmployee);
}
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager=new EmployeeManager();
return employeeManager; 
}
public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null) 
{
blException.setGenericException("Employee is null");
throw blException;
}
String employeeId=employee.getEmployeeId();
if(employeeId!=null)
{
employeeId=employeeId.trim();
if(employeeId.length()>0)
{
blException.addException("employeeId","Employee id should be nil/empty");
}
}
String name=employee.getName();
if(name==null) 
{
blException.addException("name","Name required");
}
else
{
name=name.trim();
if(name.length()==0)
{
blException.addException("name","Name required");
}
}
DesignationInterface designation=employee.getDesignation();
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
int designationCode=0;
if(designation==null)
{
blException.addException("designation","Designation required");
}
else
{
designationCode=designation.getCode();
if(!(designationManager.designationCodeExists(designationCode)))
{
blException.addException("designation","Invalid designation code");
}
}
Date dob=employee.getDateOfBirth();
if(dob==null) blException.addException("dateOfBirth","DOB required");
char gender=employee.getGender();
if(gender==' ') blException.addException("gender","Gender not set to Male/Female");
boolean isIndian=employee.getIndian();
BigDecimal basicSalary=employee.getBasicSalary();
if(basicSalary==null) blException.addException("basicSalary","Salary required");
else if(basicSalary.signum()==-1) blException.addException("basicSalary","Salary cannot be negative");
String panNumber=employee.getPANNumber();
if(panNumber==null)
{
blException.addException("panNumber","PAN Number required");
panNumber="";
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","PAN Number required");
}
}
String aadharCardNumber=employee.getAadharCardNumber();
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar card number required");
aadharCardNumber="";
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
}
if(panNumber.length()>0)
{
if(this.panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase())==true) 
{
blException.addException("panNumber","PAN Number exists : "+panNumber);
}
}
if(aadharCardNumber.length()>0)
{
if(this.aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase())==true) 
{
blException.addException("aadharCardNumber","Aadhar card number exists : "+aadharCardNumber);
}
}
if(blException.hasExceptions()) throw blException;
try
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dob);
if(gender=='M') employeeDTO.setGender(GENDER.MALE);
if(gender=='F') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.add(employeeDTO);
employeeId=employeeDTO.getEmployeeId();
employee.setEmployeeId(employeeId);
EmployeeInterface blEmployee=new Employee();
blEmployee.setEmployeeId(employeeId);
blEmployee.setName(name);
blEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designationCode));
blEmployee.setDateOfBirth((Date)dob.clone());
if(gender=='M') blEmployee.setGender(GENDER.MALE);
if(gender=='F') blEmployee.setGender(GENDER.FEMALE);
blEmployee.setIndian(isIndian);
blEmployee.setBasicSalary(basicSalary);
blEmployee.setPANNumber(panNumber);
blEmployee.setAadharCardNumber(aadharCardNumber);
this.employeeIdWiseEmployeesMap.put(employeeId.toUpperCase(),blEmployee);
this.panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),blEmployee);
this.aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),blEmployee);
this.employeeSet.add(blEmployee);
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(blEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(blEmployee);
this.designationCodeWiseEmployeesMap.put(blEmployee.getDesignation().getCode(),ets);
}
else
{
ets.add(blEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null) 
{
blException.setGenericException("Employee is null");
throw blException;
}
String employeeId=employee.getEmployeeId();
if(employeeId==null)
{
blException.addException("employeeId","Employee id required");
employeeId="";
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0) blException.addException("employeeId","Employee id required");
else
{
if(this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Invalid employee id : "+employeeId);
throw blException;
}
}
}
String name=employee.getName();
if(name==null) 
{
blException.addException("name","Name required");
}
else
{
name=name.trim();
if(name.length()==0)
{
blException.addException("name","Name required");
}
}
DesignationInterface designation=employee.getDesignation();
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
int designationCode=0;
if(designation==null)
{
blException.addException("designation","Designation required");
}
else
{
designationCode=designation.getCode();
if(!(designationManager.designationCodeExists(designationCode)))
{
blException.addException("designation","Invalid designation code : "+designationCode);
}
}
Date dob=employee.getDateOfBirth();
if(dob==null) blException.addException("dateOfBirth","DOB required");
char gender=employee.getGender();
if(gender==' ') blException.addException("gender","Gender not set to Male/Female");
boolean isIndian=employee.getIndian();
BigDecimal basicSalary=employee.getBasicSalary();
if(basicSalary==null) blException.addException("basicSalary","Salary required");
else if(basicSalary.signum()==-1) blException.addException("basicSalary","Salary cannot be negative");
String panNumber=employee.getPANNumber();
if(panNumber==null)
{
blException.addException("panNumber","PAN Number required");
panNumber="";
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","PAN Number required");
}
}
String aadharCardNumber=employee.getAadharCardNumber();
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar card number required");
aadharCardNumber="";
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
}
if(panNumber.length()>0)
{
EmployeeInterface emp1=this.panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(emp1!=null)
{
if(emp1.getEmployeeId().equalsIgnoreCase(employeeId)==false && emp1.getPANNumber().equalsIgnoreCase(panNumber)==true)
{
blException.addException("panNumber","PAN Number exists : "+panNumber);
}
}
}
if(aadharCardNumber.length()>0)
{
EmployeeInterface emp=this.aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(emp!=null)
{
if(emp.getEmployeeId().equalsIgnoreCase(employeeId)==false && emp.getAadharCardNumber().equalsIgnoreCase(aadharCardNumber)==true)
{
blException.addException("aadharCardNumber","Aadhar card number exists : "+aadharCardNumber);
}
}
}
if(blException.hasExceptions()) throw blException;
try
{
EmployeeInterface blEmployee=this.employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
String oldPANNumber=blEmployee.getPANNumber();
String oldAadharCardNumber=blEmployee.getAadharCardNumber();
int oldDesignationCode=blEmployee.getDesignation().getCode();
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(blEmployee.getEmployeeId());
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dob);
if(gender=='M') employeeDTO.setGender(GENDER.MALE);
if(gender=='F') employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.update(employeeDTO);
//remove from our D.S.
this.employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
this.panNumberWiseEmployeesMap.remove(oldPANNumber.toUpperCase());
this.aadharCardNumberWiseEmployeesMap.remove(oldAadharCardNumber.toUpperCase());
this.employeeSet.remove(blEmployee);
//now update our D.S.
blEmployee.setName(name);
blEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designationCode));
blEmployee.setDateOfBirth((Date)dob.clone());
if(gender=='M') blEmployee.setGender(GENDER.MALE);
if(gender=='F') blEmployee.setGender(GENDER.FEMALE);
blEmployee.setIndian(isIndian);
blEmployee.setBasicSalary(basicSalary);
blEmployee.setPANNumber(panNumber);
blEmployee.setAadharCardNumber(aadharCardNumber);
this.employeeIdWiseEmployeesMap.put(blEmployee.getEmployeeId().toUpperCase(),blEmployee);
this.panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),blEmployee);
this.aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),blEmployee);
this.employeeSet.add(blEmployee);
if(oldDesignationCode!=blEmployee.getDesignation().getCode())
{
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(oldDesignationCode);
ets.remove(blEmployee);
ets=this.designationCodeWiseEmployeesMap.get(blEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(blEmployee);
this.designationCodeWiseEmployeesMap.put(blEmployee.getDesignation().getCode(),ets);
}
else
{
ets.add(blEmployee);
}
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void deleteEmployee(String employeeId) throws BLException
{
BLException blException=new BLException();
if(employeeId==null)
{
blException.addException("employeeId","Employee id required");
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0) blException.addException("employeeId","Employee id required");
else
{
if(this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Invalid employee id : "+employeeId);
}
}
}
if(blException.hasExceptions()) throw blException;
try
{
EmployeeInterface blEmployee=this.employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
String oldPANNumber=blEmployee.getPANNumber();
String oldAadharCardNumber=blEmployee.getAadharCardNumber();
int oldDesignationCode=blEmployee.getDesignation().getCode();
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.delete(blEmployee.getEmployeeId());
//remove from our D.S.
this.employeeIdWiseEmployeesMap.remove(blEmployee.getEmployeeId().toUpperCase());
this.panNumberWiseEmployeesMap.remove(oldPANNumber.toUpperCase());
this.aadharCardNumberWiseEmployeesMap.remove(oldAadharCardNumber.toUpperCase());
this.employeeSet.remove(blEmployee);
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(oldDesignationCode);
ets.remove(blEmployee);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public Set<EmployeeInterface> getEmployees()
{
Set<EmployeeInterface> cloneSet=new TreeSet<>();
this.employeeSet.forEach((emp)->{
EmployeeInterface blEmployee;
DesignationInterface designation;
blEmployee=new Employee();
blEmployee.setEmployeeId(emp.getEmployeeId());
blEmployee.setName(emp.getName());
designation=new Designation();
designation.setCode(emp.getDesignation().getCode());
designation.setTitle(emp.getDesignation().getTitle());
blEmployee.setDesignation(designation);
blEmployee.setDateOfBirth((Date)emp.getDateOfBirth().clone());
if(emp.getGender()=='M') blEmployee.setGender(GENDER.MALE);
if(emp.getGender()=='F') blEmployee.setGender(GENDER.FEMALE);
blEmployee.setIndian(emp.getIndian());
blEmployee.setBasicSalary(emp.getBasicSalary());
blEmployee.setPANNumber(emp.getPANNumber());
blEmployee.setAadharCardNumber(emp.getAadharCardNumber());
cloneSet.add(blEmployee);
});
return cloneSet;
}
public Set<EmployeeInterface> getEmployeeByDesignationCode(int designationCode) throws BLException
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
if(designationManager.designationCodeExists(designationCode)==false) 
{
BLException blException=new BLException();
blException.addException("designation","Invalid designation code : "+designationCode);
throw blException;
}
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(designationCode);
Set<EmployeeInterface> cloneSet=new TreeSet<>();
if(ets==null) return cloneSet;
EmployeeInterface blEmployee;
DesignationInterface designation;
for(EmployeeInterface emp:ets)
{
blEmployee=new Employee();
blEmployee.setEmployeeId(emp.getEmployeeId());
blEmployee.setName(emp.getName());
designation=new Designation();
designation.setCode(emp.getDesignation().getCode());
designation.setTitle(emp.getDesignation().getTitle());
blEmployee.setDesignation(designation);
blEmployee.setDateOfBirth((Date)emp.getDateOfBirth().clone());
if(emp.getGender()=='M') blEmployee.setGender(GENDER.MALE);
if(emp.getGender()=='F') blEmployee.setGender(GENDER.FEMALE);
blEmployee.setIndian(emp.getIndian());
blEmployee.setBasicSalary(emp.getBasicSalary());
blEmployee.setPANNumber(emp.getPANNumber());
blEmployee.setAadharCardNumber(emp.getAadharCardNumber());
cloneSet.add(blEmployee);
}
return cloneSet;
}
public boolean designationAlloted(int designationCode) throws BLException
{
return this.designationCodeWiseEmployeesMap.containsKey(designationCode);
}
public EmployeeInterface getByEmployeeId(String employeeId) throws BLException
{
BLException blException=new BLException();
if(employeeId==null)
{
blException.addException("employeeId","Employee id required");
throw blException;
}
EmployeeInterface emp=this.employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
if(emp==null)
{
blException.addException("employeeId","Invalid employee id : "+employeeId);
throw blException;
}
EmployeeInterface blEmployee=new Employee();
blEmployee.setEmployeeId(emp.getEmployeeId());
blEmployee.setName(emp.getName());
DesignationInterface designation=new Designation();
designation.setCode(emp.getDesignation().getCode());
designation.setTitle(emp.getDesignation().getTitle());
blEmployee.setDesignation(designation);
blEmployee.setDateOfBirth((Date)emp.getDateOfBirth().clone());
if(emp.getGender()=='M') blEmployee.setGender(GENDER.MALE);
if(emp.getGender()=='F') blEmployee.setGender(GENDER.FEMALE);
blEmployee.setIndian(emp.getIndian());
blEmployee.setBasicSalary(emp.getBasicSalary());
blEmployee.setPANNumber(emp.getPANNumber());
blEmployee.setAadharCardNumber(emp.getAadharCardNumber());
return blEmployee;
}
public EmployeeInterface getByPANNumber(String panNumber) throws BLException
{
BLException blException=new BLException();
if(panNumber==null)
{
blException.addException("panNumber","PAN Number required");
throw blException;
}
EmployeeInterface emp=this.panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(emp==null)
{
blException.addException("panNumber","Invalid PAN Number : "+panNumber);
throw blException;
}
EmployeeInterface blEmployee=new Employee();
blEmployee.setEmployeeId(emp.getEmployeeId());
blEmployee.setName(emp.getName());
DesignationInterface designation=new Designation();
designation.setCode(emp.getDesignation().getCode());
designation.setTitle(emp.getDesignation().getTitle());
blEmployee.setDesignation(designation);
blEmployee.setDateOfBirth((Date)emp.getDateOfBirth().clone());
if(emp.getGender()=='M') blEmployee.setGender(GENDER.MALE);
if(emp.getGender()=='F') blEmployee.setGender(GENDER.FEMALE);
blEmployee.setIndian(emp.getIndian());
blEmployee.setBasicSalary(emp.getBasicSalary());
blEmployee.setPANNumber(emp.getPANNumber());
blEmployee.setAadharCardNumber(emp.getAadharCardNumber());
return blEmployee;
}
public EmployeeInterface getByAadharCardNumber(String aadharCardNumber) throws BLException
{
BLException blException=new BLException();
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar card number required");
throw blException;
}
EmployeeInterface emp=this.aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(emp==null)
{
blException.addException("aadharCardNumber","Invalid aadhar card number : "+aadharCardNumber);
throw blException;
}
EmployeeInterface blEmployee=new Employee();
blEmployee.setEmployeeId(emp.getEmployeeId());
blEmployee.setName(emp.getName());
DesignationInterface designation=new Designation();
designation.setCode(emp.getDesignation().getCode());
designation.setTitle(emp.getDesignation().getTitle());
blEmployee.setDesignation(designation);
blEmployee.setDateOfBirth((Date)emp.getDateOfBirth().clone());
if(emp.getGender()=='M') blEmployee.setGender(GENDER.MALE);
if(emp.getGender()=='F') blEmployee.setGender(GENDER.FEMALE);
blEmployee.setIndian(emp.getIndian());
blEmployee.setBasicSalary(emp.getBasicSalary());
blEmployee.setPANNumber(emp.getPANNumber());
blEmployee.setAadharCardNumber(emp.getAadharCardNumber());
return blEmployee;
}
public boolean employeeIdExists(String employeeId)
{
if(employeeId==null) return false;
return this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase());
}
public boolean panNumberExists(String panNumber)
{
if(panNumber==null) return false;
return this.panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase());
}
public boolean aadharCardNumberExists(String aadharCardNumber)
{
if(aadharCardNumber==null) return false;
return this.aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase());
}
public int getEmployeeCount()
{
return this.employeeSet.size();
}
public int getEmployeeCountByDesignation(int designationCode) throws BLException
{
Set<EmployeeInterface> set=this.designationCodeWiseEmployeesMap.get(designationCode);
if(set==null) return 0;
return set.size();
}
}