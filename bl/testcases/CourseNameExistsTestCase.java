import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class CourseNameExistsTestCase
{
public static void main(String gg[])
{
String courseName=gg[0];
try
{
CourseManagerInterface courseManager=CourseManager.getCourseManager();
System.out.println("Course exists : "+courseManager.courseNameExists(courseName));
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