package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
import java.io.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private final static String FILE_NAME="employee.data";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
try
{
//validating the data
String name=employeeDTO.getName().trim();
if(name==null || name.length()==0) throw new DAOException("Length of name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid Designation Code : "+designationCode);
DesignationDAOInterface designationDAO=new DesignationDAO();
if(!(designationDAO.codeExists(designationCode))) throw new DAOException("Invalid designation code : "+designationCode); 
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
String dob=simpleDateFormat.format(employeeDTO.getDateOfBirth());
if(dob==null) throw new DAOException("Date of birth is null");
char gender=employeeDTO.getGender();
if(gender==' ') throw new DAOException("Gender not set to Male/Female");
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("Basic Salary is null");
if(basicSalary.signum()==-1) throw new DAOException("Basic Salary is negative");
String panNumber=employeeDTO.getPANNumber().trim();
if(panNumber==null || panNumber.length()==0) throw new DAOException("Length of PAN Number cannot be zero");
String aadharCardNumber=employeeDTO.getAadharNumber().trim();
if(aadharCardNumber==null || aadharCardNumber.length()==0) throw new DAOException("Length of Aadhar Card Number cannot be zero");
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
String lastGeneratedEmployeeIdString;
String recordCountString;
int lastGeneratedEmployeeId=10000000;
int recordCount=0;
//if there is no record in file
if(randomAccessFile.length()==0)
{
lastGeneratedEmployeeIdString=String.format("%-10s","10000000");
recordCountString=String.format("%-10s","0");
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedEmployeeIdString=randomAccessFile.readLine();
recordCountString=randomAccessFile.readLine();
lastGeneratedEmployeeId=Integer.parseInt(lastGeneratedEmployeeIdString.trim());
recordCount=Integer.parseInt(recordCountString.trim());
}
String fPANNumber;
String fAadharCardNumber;
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
//checking for duplicacy of pan and aadhar card
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(int e=1;e<=7;e++) randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(panNumberExists==false && fPANNumber.equalsIgnoreCase(panNumber)==true) panNumberExists=true;
if(aadharCardNumberExists==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)==true) aadharCardNumberExists=true;
if(panNumberExists && aadharCardNumberExists) break;
}
if(panNumberExists && aadharCardNumberExists) 
{
randomAccessFile.close();
throw new DAOException("PAN Number "+"("+panNumber+")"+" and Aadhar card number "+"("+aadharCardNumber+")"+" exists ");
}
if(panNumberExists) 
{
randomAccessFile.readLine();
throw new DAOException("PAN Number "+"("+panNumber+")"+" exists");
}
if(aadharCardNumberExists)
{
randomAccessFile.readLine();
throw new DAOException("Aadhar card number "+"("+aadharCardNumber+")"+" exists");
}
String employeeId;
lastGeneratedEmployeeId++;
recordCount++;
//if everything is ok then we add it to file
employeeId="A"+lastGeneratedEmployeeId;
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
randomAccessFile.writeBytes(dob+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
employeeDTO.setEmployeeId(employeeId);
randomAccessFile.seek(0);
//updating the header
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
}
catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
//VALIDATING THE DATA
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId=employeeDTO.getEmployeeId().trim();
if(employeeId==null || employeeId.length()==0) throw new DAOException("Length of employee id is zero");
String name=employeeDTO.getName().trim();
if(name==null || name.length()==0) throw new DAOException("Length of name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid Designation Code : "+designationCode);
DesignationDAOInterface designationDAO=new DesignationDAO();
if(!(designationDAO.codeExists(designationCode))) throw new DAOException("Invalid designation code : "+designationCode); 
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
String dob=simpleDateFormat.format(employeeDTO.getDateOfBirth());
if(dob==null) throw new DAOException("Date of birth is null");
char gender=employeeDTO.getGender();
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("Basic Salary is null");
if(basicSalary.signum()==-1) throw new DAOException("Basic Salary is negative");
String panNumber=employeeDTO.getPANNumber().trim();
if(panNumber==null || panNumber.length()==0) throw new DAOException("Length of PAN Number cannot be zero");
String aadharCardNumber=employeeDTO.getAadharNumber().trim();
if(aadharCardNumber==null || aadharCardNumber.length()==0) throw new DAOException("Length of Aadhar Card Number cannot be zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employee id : "+employeeId);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.readLine();
throw new DAOException("Invalid employee id : "+employeeId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
String fPANNumber;
String fAadharCardNumber;
boolean found=false;
//CHECKING,THAT EmployeeId EXISTS OR NOT
//CHEKING,IF PAN OR AADHAR EXISTS AGAINST DIFFERENT EMPLOYEE ID
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
for(int e=0;e<6;e++) randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId)) found=true;
if(fEmployeeId.equalsIgnoreCase(employeeId)==false && (fPANNumber.equalsIgnoreCase(panNumber)==true || fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)==true))
{
randomAccessFile.close();
throw new DAOException("PAN/Aadhar card number Exists");
}
}
if(found==false)
{
randomAccessFile.close();
throw new DAOException("Invalid employee id : "+employeeId);
}
//if everything is ok then we open a temporary file and then copy all record to it
randomAccessFile.seek(0);
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists()==true) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId)==true) 
{
tmpRandomAccessFile.writeBytes(employeeId+"\n");
tmpRandomAccessFile.writeBytes(name+"\n");
tmpRandomAccessFile.writeBytes(designationCode+"\n");
tmpRandomAccessFile.writeBytes(dob+"\n");
tmpRandomAccessFile.writeBytes(gender+"\n");
tmpRandomAccessFile.writeBytes(isIndian+"\n");
tmpRandomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
tmpRandomAccessFile.writeBytes(panNumber+"\n");
tmpRandomAccessFile.writeBytes(aadharCardNumber+"\n");
for(int e=0;e<8;e++) randomAccessFile.readLine();
}
else
{
tmpRandomAccessFile.writeBytes(fEmployeeId+"\n");
for(int e=0;e<8;e++) tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
//now we copy back all the record
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
//set the file length that might change in updation
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void delete(String employeeId) throws DAOException
{
if(employeeId==null || employeeId.length()==0) throw new DAOException("Length of employee id is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employee id : "+employeeId);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.readLine();
throw new DAOException("Invalid employee id : "+employeeId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
for(int e=0;e<8;e++) randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId)) 
{
found=true;
break;
}
}
if(found==false)
{
randomAccessFile.close();
throw new DAOException("Invalid employee id : "+employeeId);
}
randomAccessFile.seek(0);
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists()==true) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId)==false)
{
tmpRandomAccessFile.writeBytes(fEmployeeId+"\n");
for(int e=0;e<8;e++) tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
else for(int e=0;e<8;e++) randomAccessFile.readLine();
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
int recordCount=Integer.parseInt(tmpRandomAccessFile.readLine().trim());
recordCount--;
String recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(recordCountString+"\n");
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> set=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return set;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return set;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
return set;
}
//iterating on our file and making inserting object of StudentDTO in our D.S(Set)
char fGender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(randomAccessFile.readLine());
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
//do nothing because we know that no mistake had been made in above parsing the data
}
fGender=randomAccessFile.readLine().charAt(0);
if(fGender=='M') 
{
employeeDTO.setGender(GENDER.MALE);
}
if(fGender=='F')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharNumber(randomAccessFile.readLine());
set.add(employeeDTO);
}
randomAccessFile.close();
return set;
}
catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
if(!(new DesignationDAO().codeExists(designationCode))) throw new DAOException("Invalid Designation Code : "+designationCode);
Set<EmployeeDTOInterface> set=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return set;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return set;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
return set;
}
char fGender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
String fEmployeeId=randomAccessFile.readLine();
String fName=randomAccessFile.readLine();
int fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode) 
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
//do nothing because we know that no mistake had been made in above parsing the data
}
fGender=randomAccessFile.readLine().charAt(0);
if(fGender=='M') 
{
employeeDTO.setGender(GENDER.MALE);
}
if(fGender=='F')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharNumber(randomAccessFile.readLine());
set.add(employeeDTO);
}
else for(int e=1;e<=6;e++) randomAccessFile.readLine();
}
randomAccessFile.close();
return set;
}
catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean isDesignationAlloted(int designationCode) throws DAOException
{
if(!(new DesignationDAO().codeExists(designationCode))) throw new DAOException("Invalid Designation Code : "+designationCode);
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
return false;
}
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
String fEmployeeId=randomAccessFile.readLine();
String fName=randomAccessFile.readLine();
int fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode) 
{
randomAccessFile.close();
return true;
}
else for(int e=1;e<=6;e++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}
catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Employee Id is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of employee id is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid Employee Id : "+employeeId);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Employee Id : "+employeeId);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Employee Id : "+employeeId);
}
String fEmployeeId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
//do nothing
}
employeeDTO.setGender((randomAccessFile.readLine().charAt(0))=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharNumber(randomAccessFile.readLine());
randomAccessFile.close();
return employeeDTO;
}
for(int e=1;e<=8;e++) randomAccessFile.readLine();
}
randomAccessFile.close();
throw new DAOException("Invalid Employee Id : "+employeeId);
}
catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("PAN Number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of PAN Number is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid PAN Number : "+panNumber);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid PAN Number : "+panNumber);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid PAN Number : "+panNumber);
}
String fEmployeeId;
String fName;
int fDesignationCode;
String fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPANNumber;
String fAadharCardNumber; 
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth=randomAccessFile.readLine();
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPANNumber=randomAccessFile.readLine().trim();
fAadharCardNumber=randomAccessFile.readLine();
if(fPANNumber.equalsIgnoreCase(panNumber))
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(fDateOfBirth));
}catch(ParseException parseException)
{
//do nothing
}
employeeDTO.setGender(fGender=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid PAN Number : "+panNumber);
}
catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length of Aadhar card number is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid Aadhar card number: "+aadharCardNumber);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Aadhar card number : "+aadharCardNumber);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid aadhar card number : "+aadharCardNumber);
}
String fEmployeeId;
String fName;
int fDesignationCode;
String fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPANNumber;
String fAadharCardNumber; 
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth=randomAccessFile.readLine();
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPANNumber=randomAccessFile.readLine().trim();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
EmployeeDTOInterface employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(fDateOfBirth));
}catch(ParseException parseException)
{
// do nothing
}
employeeDTO.setGender(fGender=='M'?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid Aadhar card number : "+aadharCardNumber);
}
catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Employee Id is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of employee id is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
return false;
}
String fEmployeeId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
randomAccessFile.close();
return true;
}
for(int e=1;e<=8;e++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}
catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("PAN Number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of PAN Number is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
return false;
}
String fEmployeeId;
String fName;
int fDesignationCode;
String fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPANNumber;
String fAadharCardNumber; 
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth=randomAccessFile.readLine();
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPANNumber=randomAccessFile.readLine().trim();
fAadharCardNumber=randomAccessFile.readLine();
if(fPANNumber.equalsIgnoreCase(panNumber))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}
catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
return false;
}
String fEmployeeId;
String fName;
int fDesignationCode;
String fDateOfBirth;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPANNumber;
String fAadharCardNumber; 
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
fDateOfBirth=randomAccessFile.readLine();
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPANNumber=randomAccessFile.readLine().trim();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}
catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCount() throws DAOException
{
//iterating on file and return record count from our header
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return  recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCountByDesignation(int designationCode) throws DAOException
{
if(designationCode<=0) return 0;
DesignationDAOInterface designationDAO=new DesignationDAO();
if(!(designationDAO.codeExists(designationCode))) return 0;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0) 
{
randomAccessFile.close();
return 0;
}
int designationCodeCount=0;
int fDesignationCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode) designationCodeCount++;
for(int e=1;e<=6;e++) randomAccessFile.readLine();
}
randomAccessFile.close();
return designationCodeCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
}