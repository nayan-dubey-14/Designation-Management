import com.thinking.machines.hr.pl.model.*;
import java.awt.*;
import javax.swing.*;
public class DesignationModelTestCase extends JFrame
{
private JTable table;
private JScrollPane jsp;
private Container container;
public DesignationModelTestCase()
{
DesignationModel designationModel=new DesignationModel();
table=new JTable(designationModel);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);
setSize(500,500);
setLocation(300,150);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public static void main(String gg[])
{
DesignationModelTestCase dmtc=new DesignationModelTestCase();
}
}