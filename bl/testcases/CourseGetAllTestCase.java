import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class CourseGetAllTestCase
{
public static void main(String gg[])
{
try
{
CourseManagerInterface courseManager=CourseManager.getCourseManager();
Set<CourseInterface> set=courseManager.getCourses();
set.forEach((c)->{
System.out.println(c.getCode()+" "+c.getCourseName());
});
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> list=blException.getProperties();
for(String s:list)
{
System.out.println(blException.getException(s));
}
}
}
}