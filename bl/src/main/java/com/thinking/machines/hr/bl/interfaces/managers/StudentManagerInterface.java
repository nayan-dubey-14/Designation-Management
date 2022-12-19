package com.thinking.machines.hr.bl.interfaces.managers;
import java.util.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
public interface StudentManagerInterface
{
public void addStudent(StudentInterface student) throws BLException;
public void updateStudent(StudentInterface student) throws BLException;
public void deleteStudent(String studentId) throws BLException;
public Set<StudentInterface> getStudents();
public Set<StudentInterface> getStudentByCourseCode(int courseCode) throws BLException;
public boolean courseAlloted(int courseCode) throws BLException;
public StudentInterface getByStudentId(String studentId) throws BLException;
public StudentInterface getByPhoneNumber(String phoneNumber) throws BLException;
public StudentInterface getByMailId(String mailId) throws BLException;
public boolean studentIdExists(String studentId);
public boolean phoneNumberExists(String phoneNumber);
public boolean maildIdExists(String mailId);
public int getStudentCount();
public int getStudentCountByCourse(int courseCode) throws BLException;
}