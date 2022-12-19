import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
class CourseCourseNameExistsTestCase
{
public static void main(String gg[])
{
String courseName=gg[0];
try
{
CourseDAOInterface courseDAO=new CourseDAO();
System.out.println("Exists : "+courseDAO.isCourseNameExists(courseName));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}