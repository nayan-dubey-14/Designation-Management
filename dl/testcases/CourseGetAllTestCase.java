import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
class CourseGetAllTestCase
{
public static void main(String gg[])
{
try
{
CourseDAOInterface courseDAO=new CourseDAO();
Set<CourseDTOInterface> set=courseDAO.getAll();
set.forEach((m)->{
System.out.println(m.getCode()+" "+m.getCourseName());
});
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}