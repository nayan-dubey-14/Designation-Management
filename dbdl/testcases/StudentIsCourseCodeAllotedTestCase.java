import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
class StudentIsCourseCodeAllotedTestCase
{
public static void main(String gg[])
{
int courseCode=Integer.parseInt(gg[0]);
try
{
StudentDAOInterface studentDAO=new StudentDAO();
System.out.println("Course code alloted: "+studentDAO.isCourseCodeAlloted(courseCode));
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}

}

}