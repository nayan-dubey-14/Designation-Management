import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.io.*;
import java.util.*;
class StudentGetByStudentIdTestCase
{
public static void main(String gg[])
{
String studentId=gg[0];
try
{
StudentDAOInterface studentDAO=new StudentDAO();
StudentDTOInterface studentDTO=studentDAO.getByStudentId(studentId);
System.out.println("Student id : "+studentDTO.getStudentId());
System.out.println("Name : "+studentDTO.getName());
System.out.println("Course code : "+studentDTO.getCourseCode());
System.out.println("Gender : "+studentDTO.getGender());
System.out.println("Phone Number : "+studentDTO.getPhoneNumber());
System.out.println("Mail Id : "+studentDTO.getMailId());
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}

}

}