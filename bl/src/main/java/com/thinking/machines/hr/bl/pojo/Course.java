package com.thinking.machines.hr.bl.pojo;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
public class Course implements CourseInterface
{
private int code;
private String courseName;
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setCourseName(String courseName)
{
this.courseName=courseName;
}
public String getCourseName()
{
return this.courseName;
}
public boolean equals(Object other)
{
if(!(other instanceof CourseInterface)) return false;
CourseInterface course=(CourseInterface)other;
return this.code==course.getCode(); 
}
public int hashCode()
{
return this.code;
}
public int compareTo(CourseInterface course)
{
return this.code-course.getCode();
}
}