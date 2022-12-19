package com.thinking.machines.hr.bl.interfaces.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public interface EmployeeManagerInterface
{
public void addEmployee(EmployeeInterface employee) throws BLException;
public void updateEmployee(EmployeeInterface employee) throws BLException;
public void deleteEmployee(String employeeId) throws BLException;
public Set<EmployeeInterface> getEmployees();
public Set<EmployeeInterface> getEmployeeByDesignationCode(int designationCode) throws BLException;
public boolean designationAlloted(int designationCode) throws BLException;
public EmployeeInterface getByEmployeeId(String employeeId) throws BLException;
public EmployeeInterface getByPANNumber(String panNumber) throws BLException;
public EmployeeInterface getByAadharCardNumber(String aadharCardNumber) throws BLException;
public boolean employeeIdExists(String employeeId);
public boolean panNumberExists(String panNumber);
public boolean aadharCardNumberExists(String aadharCardNumber);
public int getEmployeeCount();
public int getEmployeeCountByDesignation(int designationCode) throws BLException;
}