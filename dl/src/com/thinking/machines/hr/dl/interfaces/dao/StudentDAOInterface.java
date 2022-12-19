package com.thinking.machines.hr.dl.interfaces.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
public interface StudentDAOInterface 
{
public void add(StudentDTOInterface studentDTO) throws DAOException;
public void update(StudentDTOInterface studentDTO) throws DAOException;
public void remove(String studentId) throws DAOException;
public Set<StudentDTOInterface> getAll() throws DAOException;
public Set<StudentDTOInterface> getByCourseCode(int code) throws DAOException;
public StudentDTOInterface getByStudentId(String studentId) throws DAOException;
public StudentDTOInterface getByPhoneNumber(String phoneNumber) throws DAOException;
public StudentDTOInterface getByMailId(String mailId) throws DAOException;
public boolean studentIdExists(String studentId) throws DAOException;
public boolean phoneNumberExists(String phoneNumber) throws DAOException;
public boolean mailIdExists(String mailId) throws DAOException;
public boolean isCourseCodeAlloted(int courseCode) throws DAOException;
public int getCount() throws DAOException;
public int getCountByCourseCode(int courseCode) throws DAOException;
}