package it.omicron.esercizio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class ServiceMenu 
{
	private static int depth=0;
	private static int lastDepth=0;
	private static int counter=0;
	private static CellStyle style;
	
	private static String[] firstRow= {"ServiceId", "NodeName", "NodeType", "GroupType", "FlowType", "ResourceId"};
	
		
	public static void readMenuContent(MenuContent menuC, XSSFWorkbook wb)
	{
		XSSFSheet sheet = wb.createSheet("Menu "+menuC.getVersion());
		XSSFFont bold = wb.createFont();
		style = wb.createCellStyle();
		bold.setBold(true);
		style.setFont(bold);
		
		createFirstRow(sheet.createRow(0));
		readList(menuC.getNodes(), sheet);
		
		//applico lo Style
		for(Cell cell : sheet.getRow(0))
		{
			cell.setCellStyle(style);
		}
		
		//faccio l'autosize delle colonne
		for(int i=0; i<sheet.getRow(0).getLastCellNum(); i++)
		{
			sheet.autoSizeColumn(i);
		}
		
	}
	
	private static void createFirstRow(XSSFRow row)
	{ 
		int i;
		for(i=0; i<=lastDepth; i++)
		{
			row.createCell(i).setCellValue(i);
		}
		
		for(int j=0; j<firstRow.length; j++)
		{
			
			row.createCell(i+1+j).setCellValue(firstRow[j]);
		}

	}
	
	private static void readList(List<MenuNode> list, XSSFSheet sheet)
	{
		
		for(MenuNode node : list)
		{
			counter++;
			XSSFRow row = sheet.createRow(counter);

			if(depth>lastDepth)
			{
				createFirstRow(sheet.getRow(0));
				shiftColumns(sheet);
				sheet.getRow(0).createCell(depth).setCellValue(depth);
				lastDepth++;
			}
			
			row.createCell(depth).setCellValue("X");
			
			if(node.getNodeType().equalsIgnoreCase("service"))
				row.createCell(lastDepth+1).setCellValue(node.getNodeId());
			row.createCell(lastDepth+2).setCellValue(node.getNodeName());
			row.createCell(lastDepth+3).setCellValue(node.getNodeType());
			row.createCell(lastDepth+4).setCellValue(node.getGroupType());
			row.createCell(lastDepth+5).setCellValue(node.getFlowType());
			if(node.getResourceId()!=-1)
				row.createCell(lastDepth+6).setCellValue(node.getResourceId());
			
			if (node.getNodes()!=null) 
			{
				depth++;
				readList(node.getNodes(),sheet);	
			}
		}
		
		depth--;
	}
	
	private static void shiftColumns(XSSFSheet sheet)
	{
		for(int i=1; i<sheet.getLastRowNum(); i++)
		{
			sheet.getRow(i).shiftCellsRight(depth, sheet.getRow(i).getLastCellNum(), 1);
		}
		
	}

	
	public static void main(String[]args) throws JsonSyntaxException, JsonIOException, IOException, FileNotFoundException
	{
		
		Properties properties = new Properties();
		
		Gson gson = new Gson();
		XSSFWorkbook wb = new XSSFWorkbook();
		String inputFile,config,outputFile;
		
		char sep = File.separatorChar;

		config = "."+sep+"config.properties";
		properties.load(new FileInputStream(config));
		inputFile = "."+sep+properties.getProperty("inputFolder")+sep+properties.getProperty("jsonFile")+".json";
		outputFile = "."+sep+properties.getProperty("outputFolder")+sep+properties.getProperty("excelFile")+".xlsx";
		
		try
		{
			MenuContent menuContent = gson.fromJson(new FileReader (inputFile), MenuContent.class);
			readMenuContent(menuContent,wb);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		try
		{
			OutputStream fileOut = new FileOutputStream(outputFile);
			wb.write(fileOut);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		wb.close();

	}
}
