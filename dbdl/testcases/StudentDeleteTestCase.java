import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.io.*;
class StudentDeleteTestCase
{
public static void main(String gg[])
{
String studentId=gg[0];
try
{
StudentDAOInterface studentDAO=new StudentDAO();
studentDAO.remove(studentId);
System.out.println("Student removed successfully");
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}

}

}