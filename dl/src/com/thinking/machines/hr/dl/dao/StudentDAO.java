package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.io.*;
public class StudentDAO implements StudentDAOInterface
{
private final static String FILE_NAME="student.data";
public void add(StudentDTOInterface studentDTO) throws DAOException
{
if(studentDTO==null) throw new DAOException("Student is null");
String name=studentDTO.getName();
if(name==null) throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name is zero");
int courseCode=studentDTO.getCourseCode();
if(new CourseDAO().isCodeExists(courseCode)==false) throw new DAOException("Invalid course code : "+courseCode);
char gender=studentDTO.getGender();
String phoneNumber=studentDTO.getPhoneNumber();
if(phoneNumber==null) throw new DAOException("Phone number is null");
phoneNumber=phoneNumber.trim();
if(phoneNumber.length()==0) throw new DAOException("Length of phone number is zero");
String mailId=studentDTO.getMailId();
if(mailId==null) throw new DAOException("Mail id is null");
mailId=mailId.trim();
if(mailId.length()==0) throw new DAOException("Length of mail id is zero");
try
{
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
int lastGeneratedStudentId=10000000;
int recordCount=0;
String lastGeneratedStudentIdString;
String recordCountString;
if(randomAccessFile.length()==0)
{
lastGeneratedStudentIdString=String.format("%-10d",lastGeneratedStudentId);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(lastGeneratedStudentIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedStudentId=Integer.parseInt(randomAccessFile.readLine().trim());
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
}
String fPhoneNumber="";
String fMailId="";
boolean phoneNumberExists=false;
boolean mailIdExists=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(int i=1;i<=4;i++) randomAccessFile.readLine();
fPhoneNumber=randomAccessFile.readLine();
fMailId=randomAccessFile.readLine();
if(phoneNumberExists==false && phoneNumber.equalsIgnoreCase(fPhoneNumber)==true)
{
phoneNumberExists=true;
}
if(mailIdExists==false && mailId.equalsIgnoreCase(fMailId)==true)
{
mailIdExists=true;
}
if(phoneNumberExists && mailIdExists) break;
}
if(phoneNumberExists==true && mailIdExists==true)
{
randomAccessFile.close();
throw new DAOException("Phone Number("+phoneNumber+") and mail id("+mailId+") exists");
}
else if(phoneNumberExists)
{
randomAccessFile.close();
throw new DAOException("Phone Number("+phoneNumber+") exists");
}
else if(mailIdExists)
{
randomAccessFile.close();
throw new DAOException("Mail id("+mailId+") exists");
}
String studentId="TM"+(lastGeneratedStudentId+1);
randomAccessFile.writeBytes(studentId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(courseCode+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(phoneNumber+"\n");
randomAccessFile.writeBytes(mailId+"\n");
randomAccessFile.seek(0);
lastGeneratedStudentId++;
recordCount++;
lastGeneratedStudentIdString=String.format("%-10d",lastGeneratedStudentId);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(lastGeneratedStudentIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
studentDTO.setStudentId(studentId);
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(StudentDTOInterface studentDTO) throws DAOException
{
if(studentDTO==null) throw new DAOException("Student is null");
String studentId=studentDTO.getStudentId();
if(studentId==null) throw new DAOException("Student id is null");
studentId=studentId.trim();
if(studentId.length()==0) throw new DAOException("Length of student id is zero");
String name=studentDTO.getName();
if(name==null) throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name is zero");
int courseCode=studentDTO.getCourseCode();
if(new CourseDAO().isCodeExists(courseCode)==false) throw new DAOException("Invalid course code : "+courseCode);
char gender=studentDTO.getGender();
String phoneNumber=studentDTO.getPhoneNumber();
if(phoneNumber==null) throw new DAOException("Phone number is null");
phoneNumber=phoneNumber.trim();
if(phoneNumber.length()==0) throw new DAOException("Length of phone number is zero");
String mailId=studentDTO.getMailId();
if(mailId==null) throw new DAOException("Mail id is null");
mailId=mailId.trim();
if(mailId.length()==0) throw new DAOException("Length of mail id is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid student id : "+studentId);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid studentId : "+studentId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fStudentId;
String fName;
int fCourseCode;
char fGender;
String fPhoneNumber;
String fMailId;
boolean studentIdExists=false;
boolean phoneNumberExists=false;
boolean mailIdExists=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fStudentId=randomAccessFile.readLine();
for(int e=1;e<=3;e++) randomAccessFile.readLine();
fPhoneNumber=randomAccessFile.readLine();
fMailId=randomAccessFile.readLine();
if(studentIdExists==false && fStudentId.equalsIgnoreCase(studentId))
{
studentIdExists=true;
}
if(fStudentId.equalsIgnoreCase(studentId)==false && (fPhoneNumber.equalsIgnoreCase(phoneNumber)==true))
{
phoneNumberExists=true;
}
if(fStudentId.equalsIgnoreCase(studentId)==false && (fMailId.equalsIgnoreCase(mailId)==true))
{
mailIdExists=true;
}
if(studentIdExists && phoneNumberExists && mailIdExists) break;
}
if(studentIdExists==false)
{
randomAccessFile.close();
throw new DAOException("Invalid student id : "+studentId);
}
if(phoneNumberExists==true && mailIdExists==true)
{
randomAccessFile.close();
throw new DAOException("Phone number("+phoneNumber+") and mail id("+mailId+") exists");
}
if(phoneNumberExists==true)
{
randomAccessFile.close();
throw new DAOException("Phone number("+phoneNumber+") exists");
}
if(mailIdExists==true)
{
randomAccessFile.close();
throw new DAOException("Mail id("+mailId+") exists");
}
randomAccessFile.seek(0);
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists()==true) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fStudentId=randomAccessFile.readLine();
if(fStudentId.equalsIgnoreCase(studentId)==false)
{
tmpRandomAccessFile.writeBytes(fStudentId+"\n");
for(int e=1;e<=5;e++) tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
else
{
tmpRandomAccessFile.writeBytes(studentId+"\n");
tmpRandomAccessFile.writeBytes(name+"\n");
tmpRandomAccessFile.writeBytes(courseCode+"\n");
tmpRandomAccessFile.writeBytes(gender+"\n");
tmpRandomAccessFile.writeBytes(phoneNumber+"\n");
tmpRandomAccessFile.writeBytes(mailId+"\n");
for(int e=1;e<=5;e++) randomAccessFile.readLine();
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
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
public void remove(String studentId) throws DAOException
{
if(studentId==null) throw new DAOException("Student id is null");
studentId=studentId.trim();
if(studentId.length()==0) throw new DAOException("Length of student id is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid student id : "+studentId);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid studentId : "+studentId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fStudentId;
boolean studentIdExists=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fStudentId=randomAccessFile.readLine();
for(int e=1;e<=5;e++) randomAccessFile.readLine();
if(fStudentId.equalsIgnoreCase(studentId))
{
studentIdExists=true;
break;
}
}
if(studentIdExists==false)
{
randomAccessFile.close();
throw new DAOException("Invalid student id : "+studentId);
}
randomAccessFile.seek(0);
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists()==true) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fStudentId=randomAccessFile.readLine();
if(fStudentId.equalsIgnoreCase(studentId)==false)
{
tmpRandomAccessFile.writeBytes(fStudentId+"\n");
for(int e=1;e<=5;e++) tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
else for(int e=1;e<=5;e++) randomAccessFile.readLine();
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
int recordCount=Integer.parseInt(tmpRandomAccessFile.readLine().trim());
recordCount--;
randomAccessFile.writeBytes(String.format("%-10d",recordCount)+"\n");
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
public Set<StudentDTOInterface> getAll() throws DAOException
{
Set<StudentDTOInterface> set=new TreeSet<>();
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
randomAccessFile.readLine();
StudentDTOInterface studentDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
studentDTO=new StudentDTO();
studentDTO.setStudentId(randomAccessFile.readLine());
studentDTO.setName(randomAccessFile.readLine());
studentDTO.setCourseCode(Integer.parseInt(randomAccessFile.readLine()));
char gender=(randomAccessFile.readLine().charAt(0));
if(gender=='M')
{
studentDTO.setGender(GENDER.MALE);
}
if(gender=='F')
{
studentDTO.setGender(GENDER.FEMALE);
}
studentDTO.setPhoneNumber(randomAccessFile.readLine());
studentDTO.setMailId(randomAccessFile.readLine());
set.add(studentDTO);
}
randomAccessFile.close();
return set;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<StudentDTOInterface> getByCourseCode(int courseCode) throws DAOException
{
if(new CourseDAO().isCodeExists(courseCode)==false) throw new DAOException("Invalid course code : "+courseCode);
Set<StudentDTOInterface> set=new TreeSet<>();
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
randomAccessFile.readLine();
StudentDTOInterface studentDTO;
String fStudentId;
String fName;
int fCourseCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fStudentId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourseCode=Integer.parseInt(randomAccessFile.readLine());
if(fCourseCode==courseCode)
{
studentDTO=new StudentDTO();
studentDTO.setStudentId(fStudentId);
studentDTO.setName(fName);
studentDTO.setCourseCode(fCourseCode);
char gender=(randomAccessFile.readLine().charAt(0));
if(gender=='M')
{
studentDTO.setGender(GENDER.MALE);
}
if(gender=='F')
{
studentDTO.setGender(GENDER.FEMALE);
}
studentDTO.setPhoneNumber(randomAccessFile.readLine());
studentDTO.setMailId(randomAccessFile.readLine());
set.add(studentDTO);
}
else for(int i=1;i<=3;i++) randomAccessFile.readLine();
}
randomAccessFile.close();
return set;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public StudentDTOInterface getByStudentId(String studentId) throws DAOException
{
if(studentId==null) throw new DAOException("Student id is null");
studentId=studentId.trim();
if(studentId.length()==0) throw new DAOException("Length of student id is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid student id : "+studentId);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid student id : "+studentId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
StudentDTOInterface studentDTO;
String fStudentId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fStudentId=randomAccessFile.readLine();
if(studentId.equalsIgnoreCase(fStudentId)) 
{
studentDTO=new StudentDTO();
studentDTO.setStudentId(fStudentId);
studentDTO.setName(randomAccessFile.readLine());
studentDTO.setCourseCode(Integer.parseInt(randomAccessFile.readLine()));
char gender=randomAccessFile.readLine().charAt(0);
if(gender=='M')
{
studentDTO.setGender(GENDER.MALE);
}
if(gender=='F')
{
studentDTO.setGender(GENDER.FEMALE);
}
studentDTO.setPhoneNumber(randomAccessFile.readLine());
studentDTO.setMailId(randomAccessFile.readLine());
randomAccessFile.close();
return studentDTO;
}
for(int i=1;i<=5;i++) randomAccessFile.readLine();
}
randomAccessFile.close();
throw new DAOException("Invalid student id : "+studentId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public StudentDTOInterface getByPhoneNumber(String phoneNumber) throws DAOException
{
if(phoneNumber==null) throw new DAOException("Phone number is null");
phoneNumber=phoneNumber.trim();
if(phoneNumber.length()==0) throw new DAOException("Length of phone number is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid phone number : "+phoneNumber);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid phone number : "+phoneNumber);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
StudentDTOInterface studentDTO;
String fStudentId;
String fName;
int fCourseCode;
char gender;
String fPhoneNumber;
String fMailId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fStudentId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourseCode=Integer.parseInt(randomAccessFile.readLine());
gender=randomAccessFile.readLine().charAt(0);
fPhoneNumber=randomAccessFile.readLine();
fMailId=randomAccessFile.readLine();
if(phoneNumber.equalsIgnoreCase(fPhoneNumber)) 
{
studentDTO=new StudentDTO();
studentDTO.setStudentId(fStudentId);
studentDTO.setName(fName);
studentDTO.setCourseCode(fCourseCode);
if(gender=='M')
{
studentDTO.setGender(GENDER.MALE);
}
if(gender=='F')
{
studentDTO.setGender(GENDER.FEMALE);
}
studentDTO.setPhoneNumber(fPhoneNumber);
studentDTO.setMailId(fMailId);
randomAccessFile.close();
return studentDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid phone number : "+phoneNumber);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public StudentDTOInterface getByMailId(String mailId) throws DAOException
{
if(mailId==null) throw new DAOException("Mail id is null");
mailId=mailId.trim();
if(mailId.length()==0) throw new DAOException("Length of mail id is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid mail id : "+mailId);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid mail id : "+mailId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
StudentDTOInterface studentDTO;
String fStudentId;
String fName;
int fCourseCode;
char gender;
String fPhoneNumber;
String fMailId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fStudentId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourseCode=Integer.parseInt(randomAccessFile.readLine());
gender=randomAccessFile.readLine().charAt(0);
fPhoneNumber=randomAccessFile.readLine();
fMailId=randomAccessFile.readLine();
if(mailId.equalsIgnoreCase(fMailId)) 
{
studentDTO=new StudentDTO();
studentDTO.setStudentId(fStudentId);
studentDTO.setName(fName);
studentDTO.setCourseCode(fCourseCode);
if(gender=='M')
{
studentDTO.setGender(GENDER.MALE);
}
if(gender=='F')
{
studentDTO.setGender(GENDER.FEMALE);
}
studentDTO.setPhoneNumber(fPhoneNumber);
studentDTO.setMailId(fMailId);
randomAccessFile.close();
return studentDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid mail id : "+mailId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean studentIdExists(String studentId) throws DAOException
{
if(studentId==null) return false;
studentId=studentId.trim();
if(studentId.length()==0) return false;
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
randomAccessFile.readLine();
String fStudentId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fStudentId=randomAccessFile.readLine();
if(studentId.equalsIgnoreCase(fStudentId)) 
{
randomAccessFile.close();
return true;
}
for(int i=1;i<=5;i++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean phoneNumberExists(String phoneNumber) throws DAOException
{
if(phoneNumber==null) return false;
phoneNumber=phoneNumber.trim();
if(phoneNumber.length()==0) return false;
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
randomAccessFile.readLine();
String fPhoneNumber;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(int e=1;e<=4;e++) randomAccessFile.readLine();
fPhoneNumber=randomAccessFile.readLine();
randomAccessFile.readLine();
if(phoneNumber.equalsIgnoreCase(fPhoneNumber)) 
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean mailIdExists(String mailId) throws DAOException
{
if(mailId==null) return false;
mailId=mailId.trim();
if(mailId.length()==0) return false;
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
randomAccessFile.readLine();
String fMailId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(int e=1;e<=5;e++) randomAccessFile.readLine();
fMailId=randomAccessFile.readLine();
if(mailId.equalsIgnoreCase(fMailId)) 
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean isCourseCodeAlloted(int courseCode) throws DAOException
{
if(new CourseDAO().isCodeExists(courseCode)==false) return false;
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
randomAccessFile.readLine();
String fStudentId;
String fName;
int fCourseCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fStudentId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourseCode=Integer.parseInt(randomAccessFile.readLine());
if(fCourseCode==courseCode)
{
randomAccessFile.close();
return true;
}
for(int i=1;i<=3;i++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCount() throws DAOException
{
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
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCountByCourseCode(int courseCode) throws DAOException
{
if(new CourseDAO().isCodeExists(courseCode)==false) return 0;
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
randomAccessFile.readLine();
String fStudentId;
String fName;
int fCourseCode;
int count=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fStudentId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourseCode=Integer.parseInt(randomAccessFile.readLine());
if(fCourseCode==courseCode)
{
count++;
}
for(int i=1;i<=3;i++) randomAccessFile.readLine();
}
randomAccessFile.close();
return count;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
}