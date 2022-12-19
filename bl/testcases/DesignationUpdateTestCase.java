import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class DesignationUpdateTestCase
{
public static void main(String gg[])
{
try
{
DesignationInterface designation=new Designation();
designation.setCode(2);
designation.setTitle("Police");
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
designationManager.updateDesignation(designation);
System.out.println("updated : "+designation.getCode());
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