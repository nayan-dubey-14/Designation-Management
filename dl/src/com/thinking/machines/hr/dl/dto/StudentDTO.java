package com.thinking.machines.hr.dl.dto;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.enums.*;
public class StudentDTO implements StudentDTOInterface
{
private String studentId;
private String name;
private int courseCode;
private char gender;
private String phoneNumber;
private String mailId;
public StudentDTO()
{
this.studentId="";
this.name="";
this.courseCode=0;
this.gender=' ';
this.phoneNumber="";
this.mailId="";
}
public void setStudentId(java.lang.String studentId)
{
this.studentId=studentId;
}
public java.lang.String getStudentId()
{
return this.studentId;
}
public void setName(java.lang.String name)
{
this.name=name;
}
public java.lang.String getName()
{
return this.name;
}
public void setCourseCode(int courseCode)
{
this.courseCode=courseCode;
}
public int getCourseCode()
{
return this.courseCode;
}
public void setGender(GENDER gender)
{
if(gender==GENDER.MALE) this.gender='M';
else this.gender='F';
}
public char getGender()
{
return this.gender;
}
public void setPhoneNumber(java.lang.String phoneNumber)
{
this.phoneNumber=phoneNumber;
}
public java.lang.String getPhoneNumber()
{
return this.phoneNumber;
}
public void setMailId(java.lang.String mailId)
{
this.mailId=mailId;
}
public java.lang.String getMailId()
{
return this.mailId;
}
public boolean equals(Object other)
{
if(!(other instanceof StudentDTO)) return false;
StudentDTOInterface studentDTO=(StudentDTO)other;
return this.studentId.equalsIgnoreCase(studentDTO.getStudentId())==true;
}
public int hashCode()
{
return this.studentId.toUpperCase().hashCode();
}
public int compareTo(StudentDTOInterface studentDTO)
{
return this.studentId.compareToIgnoreCase(studentDTO.getStudentId());
}
}