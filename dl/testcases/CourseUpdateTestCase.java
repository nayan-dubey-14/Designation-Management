import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
class CourseUpdateTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
String courseName=gg[1];
try
{
CourseDTOInterface courseDTO=new CourseDTO();
courseDTO.setCode(code);
courseDTO.setCourseName(courseName);
CourseDAOInterface courseDAO=new CourseDAO();
courseDAO.update(courseDTO);
System.out.println("Updated Successfully : "+courseDTO.getCode()+" "+courseDTO.getCourseName());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}