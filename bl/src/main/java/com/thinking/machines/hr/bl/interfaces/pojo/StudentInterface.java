package com.thinking.machines.hr.bl.interfaces.pojo;
import com.thinking.machines.enums.*;
public interface StudentInterface extends Comparable<StudentInterface>,java.io.Serializable
{
public void setStudentId(String studentId);
public String getStudentId();
public void setName(String name);
public String getName();
public void setGender(GENDER gender);
public char getGender();
public void setCourseCode(int courseCode);
public int getCourseCode();
public void setPhoneNumber(String phoneNumber);
public String getPhoneNumber();
public void setMailId(String mailId);
public String getMailId();
}