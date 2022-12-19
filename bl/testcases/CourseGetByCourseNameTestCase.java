import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class CourseGetByCourseNameTestCase
{
public static void main(String gg[])
{
String courseName=gg[0];
try
{
CourseManagerInterface courseManager=CourseManager.getCourseManager();
CourseInterface course=courseManager.getCourseInterfaceByCourseName(courseName);
System.out.println(course.getCode()+" "+course.getCourseName());
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