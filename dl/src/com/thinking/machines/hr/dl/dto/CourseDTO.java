package com.thinking.machines.hr.dl.dto;
import com.thinking.machines.hr.dl.interfaces.dto.*;
public class CourseDTO implements CourseDTOInterface 
{
private int code;
private String courseName;
public CourseDTO()
{
this.code=0;
this.courseName="";
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setCourseName(java.lang.String courseName)
{
this.courseName=courseName;
}
public java.lang.String getCourseName()
{
return this.courseName;
}
public boolean equals(Object other)
{
if(!(other instanceof CourseDTO)) return false;
CourseDTOInterface courseDTO=(CourseDTO)other;
return this.code==courseDTO.getCode();
}
public int hashCode()
{
return this.code;
}
public int compareTo(CourseDTOInterface courseDTO)
{
return this.code-courseDTO.getCode();
}
}