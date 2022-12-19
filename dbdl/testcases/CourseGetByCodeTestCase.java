import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
class CourseGetByCodeTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
CourseDAOInterface courseDAO=new CourseDAO();
CourseDTOInterface courseDTO=courseDAO.getByCode(code);
System.out.println("Found : "+courseDTO.getCode()+" "+courseDTO.getCourseName());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}