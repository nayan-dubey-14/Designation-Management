package com.thinking.machines.hr.bl.interfaces.pojo;
public interface CourseInterface extends Comparable<CourseInterface>,java.io.Serializable
{
public void setCode(int code);
public int getCode();
public void setCourseName(String courseName);
public String getCourseName();
}