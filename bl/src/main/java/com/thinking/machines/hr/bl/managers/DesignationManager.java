package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationsMap;
private Map<String,DesignationInterface> titleWiseDesignationsMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManager designationManager=null;
/*We have applied Singleton Pattern for this class,for that reason we have made our 
constructor private so no one can make object and neither extends it.One should have to call
that getDesignationManager method to get a instance of this class*/
private DesignationManager() throws BLException
{
populateDataStructures();
}
private void populateDataStructures() throws BLException
{
// in this method we populate our D.S. after taking the data from Data layer.
this.codeWiseDesignationsMap=new HashMap<>();
this.titleWiseDesignationsMap=new HashMap<>();
this.designationsSet=new TreeSet<>();
try
{
DesignationDAOInterface designationDAO=new DesignationDAO();
Set<DesignationDTOInterface> dlDesignationsSet=designationDAO.getAll();
DesignationInterface blDesignation;
for(DesignationDTOInterface dlDesignation:dlDesignationsSet)
{
blDesignation=new Designation();
blDesignation.setCode(dlDesignation.getCode());
blDesignation.setTitle(dlDesignation.getTitle());
this.codeWiseDesignationsMap.put(blDesignation.getCode(),blDesignation);
this.titleWiseDesignationsMap.put(blDesignation.getTitle().toUpperCase(),blDesignation);
this.designationsSet.add(blDesignation);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static DesignationManagerInterface getDesignationManager() throws BLException
{
if(designationManager==null) designationManager=new DesignationManager();
return designationManager;
}
public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
//validating the data
if(designation==null)
{
blException.setGenericException("Designation is null");
throw blException;
}
int code=designation.getCode();
if(code!=0)
{
blException.addException("code","Code should be zero");
}
String title=designation.getTitle();
if(title==null) 
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","Title required");
}
}
//checking for duplicacy
if(title.length()>0)
{
if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase()))
{
blException.addException("title","Title exists : "+title);
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
DesignationDTOInterface dlDesignationDTO=new DesignationDTO();
dlDesignationDTO.setTitle(title);
DesignationDAOInterface dlDesignationDAO=new DesignationDAO();
dlDesignationDAO.add(dlDesignationDTO); //adding data to dl
code=dlDesignationDTO.getCode();
//adding new instance of designation in our D.S.
DesignationInterface blDesignation=new Designation();
blDesignation.setCode(code);
blDesignation.setTitle(title);
this.codeWiseDesignationsMap.put(code,blDesignation);
this.titleWiseDesignationsMap.put(title.toUpperCase(),blDesignation);
this.designationsSet.add(blDesignation);
designation.setCode(code);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}
public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
//validating the data
if(designation==null)
{
blException.setGenericException("Designation is null");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(this.codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","Invalid code: "+code);
}
if(title==null)
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0) blException.addException("title","Title required");
}
//checking if title exists with different code
if(title.length()>0)
{
if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase())==true)
{
DesignationInterface blDesignation=this.titleWiseDesignationsMap.get(title.toUpperCase());
if(blDesignation.getCode()!=code) blException.addException("title","Title exists: "+title);
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
DesignationDTOInterface dlDesignationDTO=new DesignationDTO();
dlDesignationDTO.setCode(code);
dlDesignationDTO.setTitle(title);
DesignationDAOInterface dlDesignationDAO=new DesignationDAO();
dlDesignationDAO.update(dlDesignationDTO); //updating data of dl
//taking instance of DesignationInterface from map to delete from our D.S.
DesignationInterface blDesignation=this.codeWiseDesignationsMap.get(code); 
//first removing from our D.S.
this.codeWiseDesignationsMap.remove(code);
this.titleWiseDesignationsMap.remove(blDesignation.getTitle().toUpperCase());
this.designationsSet.remove(blDesignation);
//blDesignation=new Designation();
//blDesignation.setCode(code);
blDesignation.setTitle(title);
//then adding new instance of DesignationInterface to update it
this.codeWiseDesignationsMap.put(code,blDesignation);
this.titleWiseDesignationsMap.put(title.toUpperCase(),blDesignation);
this.designationsSet.add(blDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
}
}
public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException();
//checking if incoming code is valid or not
if(this.codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","Invalid code: "+code);
throw blException;
}
try
{
DesignationDAOInterface dlDesignationDAO=new DesignationDAO();
dlDesignationDAO.delete(code); //deleting data from dl
DesignationInterface blDesignation=this.codeWiseDesignationsMap.get(code);
this.codeWiseDesignationsMap.remove(code);  //removing from D.S.
this.titleWiseDesignationsMap.remove(blDesignation.getTitle().toUpperCase());
this.designationsSet.remove(blDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public Set<DesignationInterface> getDesignations() 
{
Set<DesignationInterface> cloneSet=new TreeSet<>();
this.designationsSet.forEach((s)->{
DesignationInterface blDesignation=new Designation();
blDesignation.setCode(s.getCode());
blDesignation.setTitle(s.getTitle());
cloneSet.add(blDesignation);
});
return cloneSet;
}
public DesignationInterface getDesignationInterfaceByCode(int code) throws BLException
{
BLException blException=new BLException();
DesignationInterface designation=this.codeWiseDesignationsMap.get(code);
if(designation==null)
{
blException.addException("code","Invalid code : "+code);
throw blException;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}
/*the below method is for internal use because in our EmployeeManager we are putting the 
DesignationInterface object instead of designationCode,so why we put different object is D.S 
if we have that object in DesignationManager D.S.
It is not declared as public so it can only be accesed in same package only*/
DesignationInterface getDSDesignationByCode(int code) 
{
DesignationInterface designation=this.codeWiseDesignationsMap.get(code);
return designation;
}
public DesignationInterface getDesignationInterfaceByTitle(String title) throws BLException
{
/*BLException blException=new BLException();
DesignationInterface designation=this.titleWiseDesignationsMap.get(title.toUpperCase());
if(designation==null)
{
blException.addException("title","Invalid title : "+title);
throw blException;
}
return designation;
}*/
BLException blException=new BLException();
if(title==null) 
{
blException.addException("title","Title required");
throw blException;
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","Title required");
throw blException;
}
}
DesignationInterface designation=this.titleWiseDesignationsMap.get(title.toUpperCase());
if(designation==null)
{
blException.addException("title","Invalid title : "+title);
throw blException;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}
public int getDesignationCount()
{
return this.codeWiseDesignationsMap.size();
}
public boolean designationCodeExists(int code)
{
if(this.codeWiseDesignationsMap.containsKey(code)==false) return false;
return true;
}
public boolean designationTitleExists(String title)
{
if(title==null) return false;
if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase())==false) return false;
return true;
}
}