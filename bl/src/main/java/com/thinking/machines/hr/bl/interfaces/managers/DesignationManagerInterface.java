package com.thinking.machines.hr.bl.interfaces.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public interface DesignationManagerInterface
{
public void addDesignation(DesignationInterface designation) throws BLException;
public void updateDesignation(DesignationInterface designation) throws BLException;
public void removeDesignation(int code) throws BLException;
public Set<DesignationInterface> getDesignations();
public DesignationInterface getDesignationInterfaceByCode(int code) throws BLException;
public DesignationInterface getDesignationInterfaceByTitle(String title) throws BLException;
public int getDesignationCount();
public boolean designationCodeExists(int code);
public boolean designationTitleExists(String title);
}