import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
class CourseGetByCourseNameTestCase
{
public static void main(String gg[])
{
String courseName=gg[0];
try
{
CourseDAOInterface courseDAO=new CourseDAO();
CourseDTOInterface courseDTO=courseDAO.getByCourseName(courseName);
System.out.println("Found : "+courseDTO.getCode()+" "+courseDTO.getCourseName());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}