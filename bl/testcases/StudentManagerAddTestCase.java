import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.enums.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
class StudentManagerAddTestCase
{
public static void main(String gg[])
{
String name=gg[0];
char gender=gg[1].charAt(0);
int courseCode=Integer.parseInt(gg[2]);
String phoneNumber=gg[3];
String mailId=gg[4];
try
{
StudentInterface blStudent=new Student();
blStudent.setName(name);
if(gender=='M') blStudent.setGender(GENDER.MALE);
if(gender=='F') blStudent.setGender(GENDER.FEMALE);
blStudent.setCourseCode(courseCode);
blStudent.setPhoneNumber(phoneNumber);
blStudent.setMailId(mailId);
StudentManagerInterface studentManager=StudentManager.getStudentManager();
studentManager.addStudent(blStudent);
System.out.println("Student added with student id : "+blStudent.getStudentId());
}catch(BLException blException)
{
if(blException.hasGenericException()) System.out.println(blException.getGenericException());
List<String> list=blException.getProperties();
for(String s:list)
{
System.out.println(blException.getException(s));
}
}
}
}