import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class CourseUpdateTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
String courseName=gg[1];
try
{
CourseInterface course=new Course();
course.setCode(code);
course.setCourseName(courseName);
CourseManagerInterface courseManager=CourseManager.getCourseManager();
courseManager.updateCourse(course);
System.out.println("Updated");
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