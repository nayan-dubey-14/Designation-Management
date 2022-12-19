import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.io.*;
import java.util.*;
class StudentPhoneNumberExistsTestCase
{
public static void main(String gg[])
{
String phoneNumber=gg[0];
try
{
StudentDAOInterface studentDAO=new StudentDAO();
System.out.println("Phone number exists : "+studentDAO.phoneNumberExists(phoneNumber));
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}

}

}