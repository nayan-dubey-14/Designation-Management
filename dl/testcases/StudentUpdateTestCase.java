import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.io.*;
class StudentUpdateTestCase
{
public static void main(String gg[])
{
try
{
String studentId=gg[0];
String name=gg[1];
int courseCode=Integer.parseInt(gg[2]);
char gender=gg[3].charAt(0);
String phoneNumber=gg[4];
String mailId=gg[5];
StudentDTOInterface studentDTO=new StudentDTO();
studentDTO.setStudentId(studentId);
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
studentDAO.update(studentDTO);
System.out.println("Student updated successfully");
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}

}

}