import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
class CourseAddTestCase
{
public static void main(String gg[])
{
String courseName=gg[0];
try
{
CourseDTOInterface courseDTO=new CourseDTO();
courseDTO.setCourseName(courseName);
CourseDAOInterface courseDAO=new CourseDAO();
courseDAO.add(courseDTO);
System.out.println("Course added : "+courseDTO.getCode()+" "+courseDTO.getCourseName());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}