package com.thinking.machines.hr.dl.interfaces.dto;
public interface CourseDTOInterface extends Comparable<CourseDTOInterface>,java.io.Serializable
{
public void setCode(int code);
public int getCode();
public void setCourseName(String title);
public String getCourseName();
}