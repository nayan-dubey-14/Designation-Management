import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.io.*;
import java.util.*;
class StudentStudentIdExistsTestCase
{
public static void main(String gg[])
{
String studentId=gg[0];
try
{
StudentDAOInterface studentDAO=new StudentDAO();
System.out.println("Student id Exists : "+studentDAO.studentIdExists(studentId));
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}

}

}