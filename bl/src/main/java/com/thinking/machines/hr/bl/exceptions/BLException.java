package com.thinking.machines.hr.bl.exceptions;
import java.util.*;
public class BLException extends Exception
{
private Map<String,String> exception;
private String genericException;
public BLException()
{
this.genericException=null;
this.exception=new HashMap<>();
}
public void setGenericException(String genericException)
{
this.genericException=genericException;
}
public String getGenericException()
{
if(this.genericException==null) return "";
return this.genericException;
}
public void addException(String property,String exception)
{
this.exception.put(property,exception);
}
public String getException(String property)
{
return this.exception.get(property);
}
public void removeException(String property)
{
this.exception.remove(property);
}
public int getExceptionsCount()
{
if(this.genericException!=null) return this.exception.size()+1;
return this.exception.size();
}
public boolean hasGenericException()
{
return this.genericException!=null;
}
public boolean hasExceptions()
{
return this.getExceptionsCount()>0;
}
public List<String> getProperties()
{
List<String> properties=new ArrayList<>();
this.exception.forEach((k,v)->{
properties.add(k);
});
return properties;
}
public String getMessage()
{
if(this.genericException==null) return "";
return this.genericException;
}
}