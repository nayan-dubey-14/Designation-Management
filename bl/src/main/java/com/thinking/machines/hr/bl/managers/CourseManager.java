package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
public class CourseManager implements CourseManagerInterface
{
private Map<Integer,CourseInterface> codeWiseCourseMap;
private Map<String,CourseInterface> courseNameWiseCourseMap;
private Set<CourseInterface> courseSet;
private static CourseManager courseManager;
private CourseManager() throws BLException
{
populateDataStructures();
}
public void populateDataStructures() throws BLException
{
codeWiseCourseMap=new HashMap<>();
courseNameWiseCourseMap=new HashMap<>();
courseSet=new TreeSet<>();
try
{
CourseInterface blCourse;
CourseDAOInterface courseDAO=new CourseDAO();
Set<CourseDTOInterface> set=courseDAO.getAll();
for(CourseDTOInterface courseDTO:set)
{
blCourse=new Course();
blCourse.setCode(courseDTO.getCode());
blCourse.setCourseName(courseDTO.getCourseName());
codeWiseCourseMap.put(blCourse.getCode(),blCourse);
courseNameWiseCourseMap.put(blCourse.getCourseName().toUpperCase(),blCourse);
courseSet.add(blCourse);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static CourseManagerInterface getCourseManager() throws BLException
{
if(courseManager==null) courseManager=new CourseManager();
return courseManager;
}
public void addCourse(CourseInterface course) throws BLException
{
BLException blException=new BLException();
if(course==null)
{
blException.setGenericException("Course is null");
throw blException;
}
int code=course.getCode();
String courseName=course.getCourseName();
if(code!=0)
{
blException.addException("code","Code should be zero");
}
if(courseName==null)
{
blException.addException("courseName","Course name required");
courseName="";
}
else
{
courseName=courseName.trim();
if(courseName.length()==0)
{
blException.addException("courseName","Course name required");
}
}
if(courseName.length()>0)
{
if(this.courseNameWiseCourseMap.containsKey(courseName.toUpperCase()))
{
blException.addException("courseName","Course("+courseName+") exists");
}
}
if(blException.hasExceptions()) throw blException;
try
{
CourseDTOInterface courseDTO=new CourseDTO();
courseDTO.setCourseName(courseName);
CourseDAOInterface courseDAO=new CourseDAO();
courseDAO.add(courseDTO);
course.setCode(courseDTO.getCode());
CourseInterface blCourse=new Course();
blCourse.setCode(course.getCode());
blCourse.setCourseName(course.getCourseName());
this.codeWiseCourseMap.put(blCourse.getCode(),blCourse);
this.courseNameWiseCourseMap.put(blCourse.getCourseName().toUpperCase(),blCourse);
this.courseSet.add(blCourse);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateCourse(CourseInterface course) throws BLException
{
BLException blException=new BLException();
if(course==null)
{
blException.setGenericException("Course is null");
throw blException;
}
int code=course.getCode();
String courseName=course.getCourseName();
if(this.codeWiseCourseMap.containsKey(code)==false)
{
blException.addException("code","Invalid code : "+code);
}
if(courseName==null)
{
blException.addException("courseName","Course name required");
courseName="";
}
else
{
courseName=courseName.trim();
if(courseName.length()==0)
{
blException.addException("courseName","Course name required");
}
}
if(courseName.length()>0)
{
//first checking that incoming title exists or not 
if(this.courseNameWiseCourseMap.containsKey(courseName.toUpperCase())==true)
{
CourseInterface c=this.courseNameWiseCourseMap.get(courseName.toUpperCase());
//if it exists then comparing the code
if(c.getCode()!=code) blException.addException("courseName","Course("+courseName+") exists");
}
}
if(blException.hasExceptions()) throw blException;
try
{
CourseInterface blCourse=this.codeWiseCourseMap.get(code);
String oldCourseName=blCourse.getCourseName();
CourseDTOInterface courseDTO=new CourseDTO();
courseDTO.setCode(code);
courseDTO.setCourseName(courseName);
CourseDAOInterface courseDAO=new CourseDAO();
courseDAO.update(courseDTO);
this.codeWiseCourseMap.remove(code);
this.courseNameWiseCourseMap.remove(oldCourseName.toUpperCase());
this.courseSet.remove(blCourse);
blCourse.setCode(courseDTO.getCode());
blCourse.setCourseName(courseDTO.getCourseName());
this.codeWiseCourseMap.put(blCourse.getCode(),blCourse);
this.courseNameWiseCourseMap.put(blCourse.getCourseName().toUpperCase(),blCourse);
this.courseSet.add(blCourse);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeCourse(int code) throws BLException
{
BLException blException=new BLException();
if(this.codeWiseCourseMap.containsKey(code)==false)
{
blException.addException("code","Invalid code : "+code);
throw blException;
}
try
{
CourseDAOInterface courseDAO=new CourseDAO();
courseDAO.remove(code);
CourseInterface blCourse=this.codeWiseCourseMap.get(code);
String oldCourseName=blCourse.getCourseName();
this.codeWiseCourseMap.remove(code);
this.courseNameWiseCourseMap.remove(oldCourseName.toUpperCase());
this.courseSet.remove(blCourse);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public Set<CourseInterface> getCourses()
{
Set<CourseInterface> cloneSet=new TreeSet<>();
CourseInterface blCourse;
for(CourseInterface course:this.courseSet)
{
blCourse=new Course();
blCourse.setCode(course.getCode());
blCourse.setCourseName(course.getCourseName());
cloneSet.add(blCourse);
}
return cloneSet;
}
public CourseInterface getCourseInterfaceByCode(int code) throws BLException
{
BLException blException=new BLException();
CourseInterface course=this.codeWiseCourseMap.get(code);
if(course==null)
{
blException.addException("code","Invalid code : "+code);
throw blException;
}
CourseInterface blCourse=new Course();
blCourse.setCode(course.getCode());
blCourse.setCourseName(course.getCourseName());
return blCourse;
}
public CourseInterface getCourseInterfaceByCourseName(String courseName) throws BLException
{
BLException blException=new BLException();
if(courseName==null)
{
blException.addException("courseName","Course name required");
throw blException;
}
CourseInterface course=this.courseNameWiseCourseMap.get(courseName.toUpperCase());
if(course==null)
{
blException.addException("courseName","Invalid course name : "+courseName);
throw blException;
}
CourseInterface blCourse=new Course();
blCourse.setCode(course.getCode());
blCourse.setCourseName(course.getCourseName());
return blCourse;
}
public int getCourseCount()
{
return this.courseSet.size();
}
public boolean courseCodeExists(int code)
{
return this.codeWiseCourseMap.containsKey(code);
}
public boolean courseNameExists(String courseName)
{
if(courseName==null) return false;
return this.courseNameWiseCourseMap.containsKey(courseName.toUpperCase());	
}
}