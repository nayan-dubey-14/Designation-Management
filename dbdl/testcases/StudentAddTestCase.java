import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.io.*;
import java.text.*;
class StudentAddTestCase
{
public static void main(String gg[])
{
try
{
String name=gg[0];
int courseCode=Integer.parseInt(gg[1]);
char gender=gg[2].charAt(0);
String phoneNumber=gg[3];
String mailId=gg[4];
StudentDTOInterface studentDTO=new StudentDTO();
studentDTO.setName(name);
studentDTO.setCourseCode(courseCode);
if(gender=='M')
{
studentDTO.setGender(GENDER.MALE);
}
if(gender=='F')
{
studentDTO.setGender(GENDER.FEMALE);
}
studentDTO.setPhoneNumber(phoneNumber);
studentDTO.setMailId(mailId);
StudentDAOInterface studentDAO=new StudentDAO();
studentDAO.add(studentDTO);
System.out.println("Student added successfully with student id : "+studentDTO.getStudentId());
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}

}

}