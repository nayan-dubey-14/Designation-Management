import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
class StudentGetCountByCourseCodeTestCase
{
public static void main(String gg[])
{
int courseCode=Integer.parseInt(gg[0]);
try
{
StudentDAOInterface studentDAO=new StudentDAO();
System.out.println("Total number of students at code("+courseCode+")are : "+studentDAO.getCountByCourseCode(courseCode));
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}

}

}