package com.thinking.machines.hr.dl.interfaces.dto;
import com.thinking.machines.enums.*;
public interface StudentDTOInterface extends Comparable<StudentDTOInterface>,java.io.Serializable
{
public void setStudentId(String studentId);
public String getStudentId();
public void setName(String name);
public String getName();
public void setGender(GENDER gender);
public char getGender();
public void setCourseCode(int code);
public int getCourseCode();
public void setPhoneNumber(String phoneNumber);
public String getPhoneNumber();
public void setMailId(String mailId);
public String getMailId();
}