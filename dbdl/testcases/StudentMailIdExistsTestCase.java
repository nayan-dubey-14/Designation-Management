import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.io.*;
import java.util.*;
class StudentMailIdExistsTestCase
{
public static void main(String gg[])
{
String mailId=gg[0];
try
{
StudentDAOInterface studentDAO=new StudentDAO();
System.out.println("Mail id exists : "+studentDAO.mailIdExists(mailId));
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}

}

}