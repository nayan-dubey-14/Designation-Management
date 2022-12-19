import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
class CourseRemoveTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
CourseDAOInterface courseDAO=new CourseDAO();
courseDAO.remove(code);
System.out.println("Removed Successfully");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}