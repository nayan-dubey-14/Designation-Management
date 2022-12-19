package com.thinking.machines.hr.dl.interfaces.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.util.*;
public interface CourseDAOInterface 
{
public void add(CourseDTOInterface courseDTO) throws DAOException;
public void update(CourseDTOInterface courseDTO) throws DAOException;
public void remove(int code) throws DAOException;
public Set<CourseDTOInterface> getAll() throws DAOException;
public CourseDTOInterface getByCode(int code) throws DAOException;
public CourseDTOInterface getByCourseName(String courseName) throws DAOException;
public boolean isCodeExists(int code) throws DAOException;
public boolean isCourseNameExists(String courseName) throws DAOException;
public int getCount() throws DAOException;
}