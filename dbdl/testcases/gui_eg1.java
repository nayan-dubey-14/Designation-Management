import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
class DesignationTableModel extends AbstractTableModel
{
private String heading[];
private Set<DesignationDTOInterface> set;
public DesignationTableModel()
{
populateDataStructures();
}
public void populateDataStructures()
{
heading=new String[3];
heading[0]="S.No.";
heading[1]="Code";
heading[2]="Title";
try
{
set=new DesignationDAO().getAll();
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
public int getRowCount()
{
return this.set.size();
}
public int getColumnCount()
{
return this.heading.length;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public String getColumnName(int columnIndex)
{
return this.heading[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
Iterator<DesignationDTOInterface> itr=this.set.iterator();
DesignationDTOInterface designation=null;
for(int i=0;i<=rowIndex && itr.hasNext()==true;i++)
{
designation=itr.next();
}
if(columnIndex==0)
{
return rowIndex+1;
}
if(columnIndex==1)
{
return designation.getCode();
}
if(columnIndex==2)
{
return designation.getTitle();
}
return null;
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{
if(columnIndex==0 || columnIndex==1) c=Class.forName("java.lang.Integer");
if(columnIndex==2) c=Class.forName("java.lang.String");
}catch(ClassNotFoundException cnfe)
{
System.out.println("Hello");
}
return c;
}
}
class Data extends JFrame
{
private JTable table;
private JScrollPane jsp;
private Container container;
Data()
{
super("List of Designation");
table=new JTable(new DesignationTableModel());
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
table.setRowHeight(26);
table.setSelectionBackground(Color.black);
table.setSelectionForeground(Color.red);
table.setFont(new Font("Times New Roman",Font.PLAIN,18));
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
table.getTableHeader().setFont(new Font("Times New Roman",Font.BOLD,26));
table.getTableHeader().setReorderingAllowed(false);
table.getTableHeader().setResizingAllowed(false);
container=getContentPane();
container.add(jsp);
setSize(800,500);
setLocation(400,200);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
}
public class gui_eg1
{
public static void main(String gg[])
{
Data d=new Data();
}
}