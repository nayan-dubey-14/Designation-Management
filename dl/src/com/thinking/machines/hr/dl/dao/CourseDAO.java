package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.io.*;
public class CourseDAO implements CourseDAOInterface
{
private final static String FILE_NAME="course.data";
public void add(CourseDTOInterface courseDTO) throws DAOException
{
if(courseDTO==null) throw new DAOException("Course is null");
String courseName=courseDTO.getCourseName();
if(courseName==null) throw new DAOException("Invalid course name : "+courseName);
courseName=courseName.trim();
if(courseName.length()==0) throw new DAOException("Length of course name is zero");
try
{
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
String lastGeneratedCodeString="";
String recordCountString="";
int lastGeneratedCode=0;
int recordCount=0;
if(randomAccessFile.length()==0)
{
lastGeneratedCodeString=String.format("%-10d",lastGeneratedCode);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(lastGeneratedCodeString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedCode=Integer.parseInt(randomAccessFile.readLine().trim());
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
}
int code;
String fCourseName;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
fCourseName=randomAccessFile.readLine();
if(fCourseName.equalsIgnoreCase(courseName))
{
randomAccessFile.close();
throw new DAOException("Course name : "+courseName+" exists");
}
}
code=lastGeneratedCode+1;
randomAccessFile.writeBytes(code+"\n");
randomAccessFile.writeBytes(courseName+"\n");
lastGeneratedCode++;
recordCount++;
lastGeneratedCodeString=String.format("%-10d",lastGeneratedCode);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedCodeString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
courseDTO.setCode(code);
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(CourseDTOInterface courseDTO) throws DAOException
{
if(courseDTO==null) throw new DAOException("Course is null");
int code=courseDTO.getCode();
if(code<=0) throw new DAOException("Invalid code : "+code);
String courseName=courseDTO.getCourseName();
if(courseName==null) throw new DAOException("Invalid course name : "+courseName);
courseName=courseName.trim();
if(courseName.length()==0) throw new DAOException("Length of course name is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid code : "+code);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode;
String fCourseName;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine().trim());
fCourseName=randomAccessFile.readLine();
if(found==false && fCode==code) found=true;
if(fCode!=code && fCourseName.equalsIgnoreCase(courseName)==true)
{
randomAccessFile.close();
throw new DAOException("Course : "+courseName+" exists");
}
}
if(found==false) 
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
randomAccessFile.seek(0);
File tmpFile=new File("tmp.abc");
if(tmpFile.exists()==true) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
if(fCode!=code)
{
tmpRandomAccessFile.writeBytes(fCode+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
else
{
tmpRandomAccessFile.writeBytes(code+"\n");
tmpRandomAccessFile.writeBytes(courseName+"\n");
randomAccessFile.readLine();
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void remove(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code : "+code);
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid code : "+code);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode;
String fTitle="";
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode==code)
{
found=true;
break;
}
}
if(found==false)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
if(new StudentDAO().isCourseCodeAlloted(code)==true) 
{
randomAccessFile.close();
throw new DAOException("Student exists with course : "+fTitle);
}
String fCourseName;
randomAccessFile.seek(0);
File tmpFile=new File("tmp.abc");
if(tmpFile.exists()==true) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fCourseName=randomAccessFile.readLine();
if(fCode!=code)
{
tmpRandomAccessFile.writeBytes(fCode+"\n");
tmpRandomAccessFile.writeBytes(fCourseName+"\n");
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
int recordCount=Integer.parseInt(tmpRandomAccessFile.readLine().trim());
recordCount--;
String recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(recordCountString+"\n");
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<CourseDTOInterface> getAll() throws DAOException
{
Set<CourseDTOInterface> set=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return set;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return set;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
CourseDTOInterface courseDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
courseDTO=new CourseDTO();
courseDTO.setCode(Integer.parseInt(randomAccessFile.readLine().trim()));
courseDTO.setCourseName(randomAccessFile.readLine());
set.add(courseDTO);
}
randomAccessFile.close();
return set;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public CourseDTOInterface getByCode(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code : "+code);
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid code : "+code);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}
int fCode;
String fCourseName;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fCourseName=randomAccessFile.readLine();
if(fCode==code) //if incoming code and code from file matches we return the record
{
randomAccessFile.close();
CourseDTOInterface courseDTO=new CourseDTO();
courseDTO.setCode(fCode);
courseDTO.setCourseName(fCourseName);
return courseDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid code : "+code);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public CourseDTOInterface getByCourseName(String courseName) throws DAOException
{
if(courseName==null || courseName.length()==0) throw new DAOException("Length of course name is zero");
courseName=courseName.trim();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid course name : "+courseName);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid course name : "+courseName);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
throw new DAOException("Invalid course name : "+courseName);
}
int fCode;
String fCourseName;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fCourseName=randomAccessFile.readLine();
if(fCourseName.equalsIgnoreCase(courseName)) 
{
randomAccessFile.close();
CourseDTOInterface courseDTO=new CourseDTO();
courseDTO.setCode(fCode);
courseDTO.setCourseName(fCourseName);
return courseDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid course name : "+courseName);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean isCodeExists(int code) throws DAOException
{
if(code<=0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
return false;
}
int fCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
randomAccessFile.readLine();
if(fCode==code) //if incoming code and code from file matches we return the record
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean isCourseNameExists(String courseName) throws DAOException
{
if(courseName==null || courseName.length()==0) return false;
courseName=courseName.trim();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0)
{
randomAccessFile.close();
return false;
}
String fCourseName;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
fCourseName=randomAccessFile.readLine();
if(fCourseName.equalsIgnoreCase(courseName)) 
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCount() throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
}