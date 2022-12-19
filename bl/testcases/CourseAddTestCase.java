import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class CourseAddTestCase
{
public static void main(String gg[])
{
try
{
CourseInterface course=new Course();
course.setCourseName("Node.js");
CourseManagerInterface courseManager=CourseManager.getCourseManager();
courseManager.addCourse(course);
System.out.println("added : "+course.getCode());
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