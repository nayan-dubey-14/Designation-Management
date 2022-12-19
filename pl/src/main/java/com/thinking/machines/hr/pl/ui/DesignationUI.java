package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
public class DesignationUI extends JFrame implements DocumentListener,ActionListener,ListSelectionListener
{
private JLabel titleLabel;
private JLabel searchLabel;
private JLabel searchErrorLabel;
private JTextField searchTextField;
private JButton clearSearchFieldButton;
private JTable table;
private JScrollPane scrollPane;
private DesignationModel designationModel;
private Container container;
private DesignationPanel designationPanel;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
private ImageIcon logoIcon;
private ImageIcon crossIcon;
private ImageIcon cancelIcon;
private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon deleteIcon;
private ImageIcon saveIcon;
private ImageIcon pdfIcon;
public DesignationUI()
{
super("Designation Management");
initComponents();
setAppearance();
addListeners();
setViewMode();
designationPanel.setViewMode();
}
private void initComponents()
{
addIcon=new ImageIcon(this.getClass().getResource("/icons/add.png"));
editIcon=new ImageIcon(this.getClass().getResource("/icons/edit.png"));
deleteIcon=new ImageIcon(this.getClass().getResource("/icons/delete.png"));
cancelIcon=new ImageIcon(this.getClass().getResource("/icons/cancel.png"));
crossIcon=new ImageIcon(this.getClass().getResource("/icons/cross.png"));
saveIcon=new ImageIcon(this.getClass().getResource("/icons/save.png"));
pdfIcon=new ImageIcon(this.getClass().getResource("/icons/pdf.png"));
logoIcon=new ImageIcon(this.getClass().getResource("/icons/logo.png"));
designationModel=new DesignationModel();
titleLabel=new JLabel("Designations");
searchLabel=new JLabel("Search");
searchErrorLabel=new JLabel("");
searchTextField=new JTextField();
clearSearchFieldButton=new JButton(crossIcon);
table=new JTable(designationModel);
scrollPane=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
designationPanel=new DesignationPanel();
container=getContentPane();
}
private void setAppearance()
{
int lm=0;  //lm=left margin
int tm=0;  //tm=top margin
Font titleFont=new Font("Nirmala UI",Font.BOLD,22);
Font captionFont=new Font("Verdana",Font.BOLD,15);
Font dataFont=new Font("Verdana",Font.PLAIN,15);
Font searchErrorFont=new Font("Verdana",Font.BOLD,13);
JTableHeader tableHeader=new JTableHeader();
tableHeader=this.table.getTableHeader();
tableHeader.setResizingAllowed(false);
tableHeader.setReorderingAllowed(false);
table.setRowHeight(30);
table.getColumnModel().getColumn(0).setPreferredWidth(60);
table.getColumnModel().getColumn(1).setPreferredWidth(320);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

titleLabel.setFont(titleFont);
searchLabel.setFont(captionFont);
searchTextField.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
tableHeader.setFont(captionFont);
table.setFont(dataFont);
searchErrorLabel.setForeground(new Color(204,0,0));

titleLabel.setBounds(lm+10,tm+10,220,30);
searchLabel.setBounds(lm+10,tm+10+30+15,150,20);
searchErrorLabel.setBounds(lm+10+150+320-131,tm+5+30,120,18);
searchTextField.setBounds(lm+10+150-60,tm+10+30+15,320,25);
clearSearchFieldButton.setBounds(lm+10+150-60+320+3,tm+10+30+15,40,25);
scrollPane.setBounds(lm+10+5,tm+10+30+15+35,460,260);
designationPanel.setBounds(lm+10+5,tm+10+30+15+35+260+10,460,250);

container.setLayout(null);
container.add(titleLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchFieldButton);
container.add(searchErrorLabel);
container.add(scrollPane);
container.add(designationPanel);
int w=500;
int h=700;
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));
setSize(w,h-38);
setIconImage(logoIcon.getImage());
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void addListeners()
{
this.searchTextField.getDocument().addDocumentListener(this);
this.clearSearchFieldButton.addActionListener(this);
this.table.getSelectionModel().addListSelectionListener(this);
}
public void valueChanged(ListSelectionEvent lse)
{
DesignationInterface designation=null;
try
{
designation=designationModel.getDesignationAt(table.getSelectedRow());
designationPanel.setDesignation(designation);
}catch(BLException blException)
{
designationPanel.cancelDesignation();
}
}
private void stateChanged()
{
this.searchErrorLabel.setText("");
String title=this.searchTextField.getText().trim();
if(title.length()==0) return;
int index=0;
try
{
index=this.designationModel.indexOfTitle(title,true);
}catch(BLException blException)
{
this.searchErrorLabel.setText("Not Found");
return;
}
this.table.setRowSelectionInterval(index,index);
Rectangle rectangle=this.table.getCellRect(index,0,true);
this.table.scrollRectToVisible(rectangle);
}
public void changedUpdate(DocumentEvent de)
{
stateChanged();
}
public void removeUpdate(DocumentEvent de)
{
stateChanged();
}
public void insertUpdate(DocumentEvent de)
{
stateChanged();
}
public void actionPerformed(ActionEvent ae)
{
this.searchTextField.setText("");
this.searchTextField.requestFocusInWindow();
}

private void setViewMode()
{
mode=MODE.VIEW;
if(designationModel.getRowCount()==0) 
{
this.searchTextField.setEnabled(false);
this.clearSearchFieldButton.setEnabled(false);
this.table.setEnabled(false);
}
else
{
this.searchTextField.setEnabled(true);
this.clearSearchFieldButton.setEnabled(true);
this.table.setEnabled(true);
}
}
private void setAddMode()
{
mode=MODE.ADD;
this.searchTextField.setEnabled(false);
this.clearSearchFieldButton.setEnabled(false);
this.table.setEnabled(false);
}
private void setEditMode()
{
mode=MODE.EDIT;
this.searchTextField.setEnabled(false);
this.clearSearchFieldButton.setEnabled(false);
this.table.setEnabled(false);
}
private void setDeleteMode()
{
mode=MODE.DELETE;
this.searchTextField.setEnabled(false);
this.clearSearchFieldButton.setEnabled(false);
this.table.setEnabled(false);
}
private void setExportToPDFMode()
{
mode=MODE.EXPORT_TO_PDF;
this.clearSearchFieldButton.setEnabled(false);
this.searchTextField.setEnabled(false);
this.table.setEnabled(false);
}
//inner class
class DesignationPanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JButton addButton;
private JButton editButton;
private JButton deleteButton;
private JButton cancelButton;
private JButton exportToPDFButton;
private JPanel buttonsPanel;
private DesignationInterface designation;
public DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
initComponents();
setAppearance();
addListeners();
}
private void setDesignation(DesignationInterface designation)
{
this.designation=designation;
titleLabel.setText(this.designation.getTitle());
}
private void cancelDesignation()
{
titleLabel.setText("");
}
private void initComponents()
{
titleCaptionLabel=new JLabel("Designation");
titleLabel=new JLabel();
titleTextField=new JTextField(20);
clearTitleTextFieldButton=new JButton(crossIcon);
addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
cancelButton=new JButton(cancelIcon);
deleteButton=new JButton(deleteIcon);
exportToPDFButton=new JButton(pdfIcon);
buttonsPanel=new JPanel();
designation=new Designation();
}
private void setAppearance()
{
Font titleFont=new Font("Nirmala UI",Font.BOLD,18);
Font dataFont=new Font("Verdana",Font.PLAIN,17);
int lm=0;
int tm=0;
titleCaptionLabel.setFont(titleFont);
titleLabel.setFont(dataFont);
titleTextField.setFont(dataFont);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));

titleCaptionLabel.setBounds(lm+15,tm+20,130,26);
titleLabel.setBounds(lm+15+130+5,tm+20+4,220,22);
titleTextField.setBounds(lm+15+130+5,tm+20+4,220,26);
clearTitleTextFieldButton.setBounds(lm+15+130+220+5,tm+20+4,40,25);
buttonsPanel.setBounds(lm+15+22,tm+20+4+80,332,80);
addButton.setBounds(lm+15,tm+14,50,50);
editButton.setBounds(lm+15+50+12,tm+14,50,50);
cancelButton.setBounds(lm+15+50+50+24,tm+14,50,50);
deleteButton.setBounds(lm+15+50+50+50+24+14,tm+14,50,50);
exportToPDFButton.setBounds(lm+15+50+50+50+50+24+14+14,tm+14,50,50);

setLayout(null);
buttonsPanel.setLayout(null);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);
add(titleCaptionLabel);
add(titleLabel);
add(titleTextField);
add(clearTitleTextFieldButton);
add(buttonsPanel);
}
private boolean addDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation required");
titleTextField.requestFocusInWindow();
return false;
}
else
{
DesignationInterface d=new Designation();
d.setTitle(title);
int index=0;
try
{
designationModel.add(d);
try
{
index=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
// do nothing
}
table.setRowSelectionInterval(index,index);
Rectangle rectangle=table.getCellRect(index,0,true);
table.scrollRectToVisible(rectangle);
return true;
}catch(BLException blException)
{
if(blException.hasGenericException()) 
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
titleTextField.requestFocusInWindow();
return false;
}
private boolean updateDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation required");
titleTextField.requestFocusInWindow();
return false;
}
else
{
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(title);
int index=0;
try
{
designationModel.update(d);
try
{
index=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
// do nothing
}
table.setRowSelectionInterval(index,index);
Rectangle rectangle=table.getCellRect(index,0,true);
table.scrollRectToVisible(rectangle);
return true;
}catch(BLException blException)
{
if(blException.hasGenericException()) 
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
titleTextField.requestFocusInWindow();
return false;
}
private void removeDesignation()
{
try
{
String title=this.designation.getTitle();
int selectedOption=JOptionPane.showConfirmDialog(this,"Delete "+title,"Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.YES_OPTION)
{
designationModel.remove(this.designation.getCode());
JOptionPane.showMessageDialog(this,title+" deleted");
}
else return;
}catch(BLException blException)
{
if(blException.hasGenericException()) 
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
private void addListeners()
{
addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
if(mode==MODE.VIEW)
{
setAddMode();
}
else
{
if(addDesignation()==true) setViewMode();
}
}
});
cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
setViewMode();
}
});
editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
if(mode==MODE.VIEW) setEditMode();
else
{
if(updateDesignation()) setViewMode();
}
}
});
deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
setDeleteMode();
}
});
clearTitleTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
titleTextField.setText("");
titleTextField.requestFocusInWindow();
}
});
exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
JFileChooser j=new JFileChooser();
j.setCurrentDirectory(new File("."));
int s=j.showSaveDialog(null);
if(s==JFileChooser.APPROVE_OPTION)
{
try
{
File selectedFile=j.getSelectedFile();
String pdfFilePath=selectedFile.getAbsolutePath();
if(pdfFilePath.endsWith(".")==true) pdfFilePath+="pdf";
else if(pdfFilePath.endsWith(".pdf")==false) pdfFilePath+=".pdf";
File file=new File(pdfFilePath);
File parent=new File(file.getParent());
if(parent.exists()==false || parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(DesignationPanel.this,"Incorret path :"+file.getAbsolutePath());
return;
}
if(file.exists()==true)
{
int selectedOption=JOptionPane.showConfirmDialog(DesignationPanel.this,"Do you want to delete the existing file and create new one","Warning",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
if(selectedOption==JOptionPane.YES_OPTION)
{
file.delete();
designationModel.exportToPDF(file);
JOptionPane.showMessageDialog(DesignationPanel.this,"Data exported to PDF :"+file.getAbsolutePath());
}
}
else
{
designationModel.exportToPDF(file);
JOptionPane.showMessageDialog(DesignationPanel.this,"Data exported to PDF :"+file.getAbsolutePath());
}
}catch(BLException blException)
{
JOptionPane.showMessageDialog(DesignationPanel.this,blException.getGenericException());
}catch(Exception exception)
{
System.out.println(exception);
}
}
}
});
}//addListeners ends here
void setViewMode()
{
DesignationUI.this.setViewMode();
titleLabel.setVisible(true);
titleTextField.setVisible(false);
clearTitleTextFieldButton.setVisible(false);
addButton.setIcon(addIcon);
addButton.setEnabled(true);
cancelButton.setEnabled(false);
editButton.setIcon(editIcon);
if(designationModel.getRowCount()>0)
{
editButton.setEnabled(true);
deleteButton.setEnabled(true);
exportToPDFButton.setEnabled(true);
}
else
{
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
}
void setAddMode()
{
DesignationUI.this.setAddMode();
titleTextField.setText("");
titleTextField.setVisible(true);
titleTextField.requestFocusInWindow();
titleLabel.setVisible(false);
clearTitleTextFieldButton.setVisible(true);
addButton.setIcon(saveIcon);
cancelButton.setEnabled(true);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
void setEditMode()
{
if(table.getSelectedRow()<0 || table.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to edit");
return ;
}
DesignationUI.this.setEditMode();
titleTextField.setText(this.designation.getTitle());
titleTextField.setVisible(true);
titleLabel.setVisible(false);
clearTitleTextFieldButton.setEnabled(true);
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
editButton.setIcon(editIcon);
}
void setDeleteMode()
{
if(table.getSelectedRow()<0 || table.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to delete");
return;
}
DesignationUI.this.setDeleteMode();
addButton.setEnabled(false);
cancelButton.setEnabled(false);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
removeDesignation();
DesignationUI.this.setViewMode();
this.setViewMode();
}
void setExportToPDFMode()
{
DesignationUI.this.setExportToPDFMode();
addButton.setEnabled(false);
cancelButton.setEnabled(false);
editButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
}//inner class ends here
}