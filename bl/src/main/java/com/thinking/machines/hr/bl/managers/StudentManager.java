package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.enums.*;
import java.util.*;
public class StudentManager implements StudentManagerInterface
{
private Map<String,StudentInterface> studentIdWiseStudentsMap;
private Map<String,StudentInterface> phoneNumberWiseStudentsMap;
private Map<String,StudentInterface> mailIdWiseStudentsMap;
private Set<StudentInterface> studentsSet;
private static StudentManagerInterface studentManager=null;
//singleton pattern applied here
private StudentManager() throws BLException
{
populateDataStructures();
}
private void populateDataStructures() throws BLException
{
this.studentIdWiseStudentsMap=new HashMap<>();
this.phoneNumberWiseStudentsMap=new HashMap<>();
this.mailIdWiseStudentsMap=new HashMap<>();
this.studentsSet=new TreeSet<>();
try
{
StudentDAOInterface studentDAO=new StudentDAO();
Set<StudentDTOInterface> dlSet=studentDAO.getAll();
dlSet.forEach((studentDTO)->{
StudentInterface blStudent;
blStudent=new Student();
blStudent.setStudentId(studentDTO.getStudentId());
blStudent.setName(studentDTO.getName());
if(studentDTO.getGender()=='M')
{
blStudent.setGender(GENDER.MALE); 
}
if(studentDTO.getGender()=='F')
{
blStudent.setGender(GENDER.FEMALE);
}
blStudent.setPhoneNumber(studentDTO.getPhoneNumber());
blStudent.setMailId(studentDTO.getMailId());
this.studentIdWiseStudentsMap.put(studentDTO.getStudentId().toUpperCase(),blStudent);
this.phoneNumberWiseStudentsMap.put(studentDTO.getPhoneNumber().toUpperCase(),blStudent);
this.mailIdWiseStudentsMap.put(studentDTO.getMailId().toUpperCase(),blStudent);
this.studentsSet.add(blStudent);
});
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException("No records");
throw blException;
}
}
public static StudentManagerInterface getStudentManager() throws BLException
{
if(studentManager==null) studentManager=new StudentManager();
return studentManager;
}

public void addStudent(StudentInterface student) throws BLException
{
BLException blException=new BLException();
if(student==null) 
{
blException.setGenericException("Student is null");
throw blException;
}
String studentId=student.getStudentId();
String name=student.getName();
char gender=student.getGender();
int courseCode=student.getCourseCode();
String phoneNumber=student.getPhoneNumber();
String mailId=student.getMailId();
if(studentId!=null)
{
studentId=studentId.trim();
if(studentId.length()>0)
{
blException.addException("studentId","Student id should be nil/empty");
}
}
if(name==null)
{
blException.addException("name","Name required");
name="";
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","Name required");
}
if(gender==' ') blException.addException("gender","Gender not set to Male/Female");
CourseManagerInterface courseManager=CourseManager.getCourseManager();
if(courseManager.courseCodeExists(courseCode)==false)
{
blException.addException("courseCode","Invalid course code : "+courseCode);
}
if(phoneNumber==null)
{
blException.addException("phoneNumber","Phone no. required");
phoneNumber="";
}
else
{
phoneNumber=phoneNumber.trim();
if(phoneNumber.length()==0) blException.addException("phoneNumber","Phone no. required");
}
if(mailId==null)
{
blException.addException("mailId","Mail id required");
mailId="";
}
else
{
mailId=mailId.trim();
if(mailId.length()==0) blException.addException("mailId","Mail id required");
}
if(phoneNumber.length()>0)
{
if(this.phoneNumberWiseStudentsMap.containsKey(phoneNumber.toUpperCase())==true)
{
blException.addException("phoneNumber","Phone no. exists : "+phoneNumber);
}
}
if(mailId.length()>0)
{
if(this.mailIdWiseStudentsMap.containsKey(mailId.toUpperCase())==true)
{
blException.addException("mailId","Mail id. exists : "+mailId);
}
}
if(blException.hasExceptions()) throw blException;
try
{
StudentDTOInterface studentDTO=new StudentDTO();
studentDTO.setStudentId(studentId);
studentDTO.setName(name);
if(gender=='M') studentDTO.setGender(GENDER.MALE);
if(gender=='F') studentDTO.setGender(GENDER.FEMALE);
studentDTO.setCourseCode(courseCode);
studentDTO.setPhoneNumber(phoneNumber);
studentDTO.setMailId(mailId);
StudentDAOInterface studentDAO=new StudentDAO();
studentDAO.add(studentDTO);
student.setStudentId(studentDTO.getStudentId());
StudentInterface s=new Student();
s.setStudentId(studentDTO.getStudentId());
s.setName(name);
if(gender=='M') s.setGender(GENDER.MALE);
if(gender=='F') s.setGender(GENDER.FEMALE);
s.setCourseCode(courseCode);
s.setPhoneNumber(phoneNumber);
s.setMailId(mailId);
this.studentIdWiseStudentsMap.put(s.getStudentId().toUpperCase(),s);
this.phoneNumberWiseStudentsMap.put(phoneNumber.toUpperCase(),s);
this.mailIdWiseStudentsMap.put(mailId.toUpperCase(),s);
this.studentsSet.add(s);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateStudent(StudentInterface student) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public void deleteStudent(String studentId) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public Set<StudentInterface> getStudents()
{
return null;
}
public Set<StudentInterface> getStudentByCourseCode(int courseCode) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public boolean courseAlloted(int courseCode) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public StudentInterface getByStudentId(String studentId) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public StudentInterface getByPhoneNumber(String phoneNumber) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public StudentInterface getByMailId(String mailId) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
public boolean studentIdExists(String studentId)
{
return false;
}
public boolean phoneNumberExists(String phoneNumber)
{
return false;
}
public boolean maildIdExists(String mailId)
{
return false;
}
public int getStudentCount()
{
return 0;
}
public int getStudentCountByCourse(int courseCode) throws BLException
{
BLException blException=new BLException();
blException.setGenericException("Not yet implemented");
throw blException;
}
}