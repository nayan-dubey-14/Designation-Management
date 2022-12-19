package com.thinking.machines.hr.bl.pojo;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.enums.*;
public class Student implements StudentInterface
{
private String studentId;
private String name;
private char gender;
private int courseCode;
private String phoneNumber;
private String mailId;
public Student()
{
this.studentId="";
this.name="";
this.gender=' ';
this.courseCode=0;
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
public void setGender(GENDER gender)
{
if(gender==GENDER.MALE) this.gender='M';
if(gender==GENDER.FEMALE) this.gender='F';
}
public char getGender()
{
return this.gender;
}
public void setCourseCode(int courseCode)
{
this.courseCode=courseCode;
}
public int getCourseCode()
{
return this.courseCode;
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
if(!(other instanceof StudentInterface)) return false;
StudentInterface student=(StudentInterface)other;
return this.studentId.equalsIgnoreCase(student.getStudentId()); 
}
public int hashCode()
{
return this.studentId.toUpperCase().hashCode();
}
public int compareTo(StudentInterface student)
{
return this.studentId.compareToIgnoreCase(student.getStudentId());
}
}