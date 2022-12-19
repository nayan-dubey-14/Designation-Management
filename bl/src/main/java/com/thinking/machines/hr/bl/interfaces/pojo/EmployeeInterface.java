package com.thinking.machines.hr.bl.interfaces.pojo;
import com.thinking.machines.enums.*;
import java.math.*;
import java.util.*;
public interface EmployeeInterface extends Comparable<EmployeeInterface>,java.io.Serializable
{
public void setEmployeeId(String employeeId);
public String getEmployeeId();
public void setName(String name);
public String getName();
public void setDesignation(DesignationInterface designation);
public DesignationInterface getDesignation();
public void setDateOfBirth(Date dateOfBirth);
public Date getDateOfBirth();
public void setGender(GENDER gender);
public char getGender();
public void setIndian(boolean isIndian);
public boolean getIndian();
public void setBasicSalary(BigDecimal basicSalary);
public BigDecimal getBasicSalary();
public void setAadharCardNumber(String aadharCardNumber);
public String getAadharCardNumber();
public void setPANNumber(String panNumber);
public String getPANNumber();
}