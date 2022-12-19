package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;
import com.itextpdf.io.image.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.layout.borders.*;
public class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationInterface> list;
private String columnNames[];
private DesignationManagerInterface designationManager;
public DesignationModel()
{
populateDataStructures();
}
private void populateDataStructures()
{
columnNames=new String[2];
columnNames[0]="S.No.";
columnNames[1]="Designation";
try
{
designationManager=DesignationManager.getDesignationManager();
}catch(BLException blException)
{
// think later
}
Set<DesignationInterface> set=designationManager.getDesignations();
this.list=new LinkedList<>();
for(DesignationInterface blDesignation:set)
{
this.list.add(blDesignation);
}
Collections.sort(list,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}
public int getRowCount()
{
return this.list.size();
}
public int getColumnCount()
{
return this.columnNames.length;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public Class getColumnClass(int columnIndex) // for alignment factor
{
Class c=null;
try
{
if(columnIndex==0) c=Class.forName("java.lang.Integer");
if(columnIndex==1) c=Class.forName("java.lang.String");
}catch(ClassNotFoundException cnfe)
{
//do nothing
}
return c;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
if(columnIndex==1)
{
return this.list.get(rowIndex).getTitle();
}
return null;
}
public String getColumnName(int columnIndex)
{
return this.columnNames[columnIndex];
}
//Application Specific
public int indexOfDesignation(DesignationInterface designation) throws BLException
{
Iterator<DesignationInterface> itr=list.iterator();
int index=0;
DesignationInterface d;
while(itr.hasNext())
{
d=itr.next();
if(d.equals(designation))
{
return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid designation : "+designation.getTitle());
throw blException;
}
public int indexOfTitle(String title,boolean partialLeftSearch) throws BLException
{
Iterator<DesignationInterface> itr=list.iterator();
int index=0;
DesignationInterface d;
while(itr.hasNext())
{
d=itr.next();
if(partialLeftSearch)
{
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase())) return index;
}
else
{
if(d.getTitle().toUpperCase().equals(title.toUpperCase())) return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid title : "+title);
throw blException;
}
public void add(DesignationInterface designation) throws BLException
{
this.designationManager.addDesignation(designation);
this.list.add(designation);
Collections.sort(list,(left,right)->{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
});
fireTableDataChanged();
}
public void update(DesignationInterface designation) throws BLException
{
this.designationManager.updateDesignation(designation);
this.list.remove(indexOfDesignation(designation));
this.list.add(designation);
Collections.sort(list,(left,right)->{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
});
fireTableDataChanged();
}
public void remove(int code) throws BLException
{
this.designationManager.removeDesignation(code);
Iterator<DesignationInterface> itr=list.iterator();
DesignationInterface d;
int index=0;
while(itr.hasNext())
{
d=itr.next();
if(d.getCode()==code) 
{
this.list.remove(index);
break;
}
index++;
}
fireTableDataChanged();
}
public DesignationInterface getDesignationAt(int index) throws BLException
{
if(index<0 || index>=this.list.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid index : "+index);
throw blException;
}
return this.list.get(index);
}
public void exportToPDF(File file) throws BLException
{
try
{
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document document=new Document(pdfDocument);

Image logo=new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo.png")));
Paragraph logoPara=new Paragraph();
logoPara.add(logo);

Paragraph companyNamePara=new Paragraph("ABC Services");
PdfFont companyNameFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
companyNamePara.setFont(companyNameFont);
companyNamePara.setFontSize(20);

Paragraph reportTitlePara=new Paragraph("List of Designations");
PdfFont reportTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
reportTitlePara.setFont(reportTitleFont);
reportTitlePara.setFontSize(17);

PdfFont pageNumberFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont columnTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

Paragraph columnTitle1=new Paragraph("S.No.");
columnTitle1.setFont(columnTitleFont);
columnTitle1.setFontSize(15);
Paragraph columnTitle2=new Paragraph("Designations");
columnTitle2.setFont(columnTitleFont);
columnTitle2.setFontSize(15);

Paragraph pageNumberPara;
Paragraph dataPara;
float topTableColumnWidths[]={1,5};
float dataTableColumnWidths[]={1,5};
int sno,x,pageSize;
pageSize=23;
boolean newPage=true;
Table pageNumberTable;
Table topTable;
Table dataTable=null;
Cell cell;
int numberOfPages=this.list.size()/pageSize;
if((this.list.size()%pageSize)!=0) numberOfPages++;
int pageNumber=0;
DesignationInterface designation;
x=sno=0;
while(x<this.list.size())
{
if(newPage==true)
{
//creating new page header
pageNumber++;
topTable=new Table(UnitValue.createPercentArray(topTableColumnWidths));

cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(logoPara);
topTable.addCell(cell);
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(companyNamePara);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
topTable.addCell(cell);
document.add(topTable);
pageNumberPara=new Paragraph("Page : "+pageNumber+"/"+numberOfPages);
pageNumberPara.setFont(pageNumberFont);
pageNumberPara.setFontSize(14);
pageNumberTable=new Table(1);
pageNumberTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(pageNumberPara);
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);
document.add(pageNumberTable);
dataTable=new Table(UnitValue.createPercentArray(dataTableColumnWidths));

dataTable.setWidth(UnitValue.createPercentValue(100));

cell=new Cell(1,2);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(columnTitle1);
dataTable.addHeaderCell(columnTitle2);
newPage=false;
}//if ends here
designation=this.list.get(x);
//add row to table;
sno++;
cell=new Cell();
dataPara=new Paragraph(String.valueOf(sno));
dataPara.setFont(dataFont);
dataPara.setFontSize(14);
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);

cell=new Cell();
dataPara=new Paragraph(designation.getTitle());
dataPara.setFont(dataFont);
dataPara.setFontSize(14);
cell.add(dataPara);
dataTable.addCell(cell);
x++;
if(sno%pageSize==0 || x==this.list.size())
{
document.add(dataTable);
float endTableWidths[]={3,5};
Table endTable=new Table(UnitValue.createPercentArray(endTableWidths));
endTable.setWidth(UnitValue.createPercentValue(100));
Paragraph creditPara=new Paragraph("Software by : Nayan Dubey");
creditPara.setFont(dataFont);
creditPara.setFontSize(17);
creditPara.setUnderline();
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(creditPara);
endTable.addCell(cell);
Date date=new Date();
Paragraph timePara=new Paragraph("Printed on : "+date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900)+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());
timePara.setFont(dataFont);
timePara.setFontSize(15);
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(timePara);
cell.setTextAlignment(TextAlignment.RIGHT);
endTable.addCell(cell);
document.add(endTable);
if(x<this.list.size())
{
document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}
}
}//while ends here
document.close();
}catch(Exception exception)
{
BLException blException=new BLException();
blException.setGenericException(exception.getMessage());
throw blException;
}
}
}