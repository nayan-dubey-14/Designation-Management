import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
class StudentGetCountTestCase
{
public static void main(String gg[])
{
try
{
StudentDAOInterface studentDAO=new StudentDAO();
System.out.println("Total number of students are : "+studentDAO.getCount());
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}

}

}