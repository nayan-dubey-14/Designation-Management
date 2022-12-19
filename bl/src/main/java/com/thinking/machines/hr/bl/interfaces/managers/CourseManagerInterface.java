package com.thinking.machines.hr.bl.interfaces.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public interface CourseManagerInterface
{
public void addCourse(CourseInterface course) throws BLException;
public void updateCourse(CourseInterface course) throws BLException;
public void removeCourse(int code) throws BLException;
public Set<CourseInterface> getCourses();
public CourseInterface getCourseInterfaceByCode(int code) throws BLException;
public CourseInterface getCourseInterfaceByCourseName(String courseName) throws BLException;
public int getCourseCount();
public boolean courseCodeExists(int code);
public boolean courseNameExists(String courseName);
}