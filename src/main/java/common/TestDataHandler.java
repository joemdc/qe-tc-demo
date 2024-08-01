package common;

import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Fillo.Recordset;


public class TestDataHandler {

	public static XSSFSheet ExcelWSheet;
	public static XSSFWorkbook ExcelWBook;
    public static XSSFCell Cell;
    public static XSSFRow Row;
	
	public String URL;
	public String Email;
	public String Password;
	public String HeaderText;
	public String BreakdownCover;
	

	
	public static TestDataHandler loadTestData(String sheet, String... where) throws Exception {
		
		Recordset record = DataRepository.testDataToBeUsed().getRowData(sheet, where);
		return loadTestData(record);
		
	}
	
	public static TestDataHandler loadTestData(Recordset record) throws Exception {
		TestDataHandler testDataHandler = new TestDataHandler();
		testDataHandler.URL = record.getField("URL");
		testDataHandler.Email = record.getField("Email");
		testDataHandler.Password = record.getField("Password");		
		testDataHandler.HeaderText = record.getField("HeaderText");	
		testDataHandler.BreakdownCover = record.getField("BreakdownCover");	

		
		return testDataHandler;
		
	}
	
	private static int getRowContains(int colNum, String sTestCaseName) throws Exception{
		int i;

		try {
			
			int rowCount = getRowUsed();
			outer:
			for ( i=1 ; i < rowCount; i++){
  				if  (getCellData(i,colNum).equalsIgnoreCase(sTestCaseName)){
	    			break outer;
				} 
			}
			return i;
				}catch (Exception e){
			throw(e);
			}
		}
	
	private static int getRowUsed() throws Exception {
			try{
				int RowCount = ExcelWSheet.getLastRowNum();
				return RowCount;
			}catch (Exception e){
				System.out.println(e.getMessage());
				throw (e);
			}

		} 
	
	 //This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num
	 private static String getCellData(int RowNum, int ColNum) throws Exception{
	      String CellData = null;

	  	try{	        	   
	      	  Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);	        	  
	      	  //Cell.setCellType(Cell.CELL_TYPE_STRING);
	      	  CellData = Cell.getStringCellValue();
	       
	  	  		return CellData;
	            
	            }catch (Exception e){
	              return"";
	            }
	  }	  

	public static void loadIterativeTestData() throws Exception {
		//These steps iterates the input of Worksheet information which are derived from excel
        int lastRow = ExcelWSheet.getLastRowNum();
        System.out.println("Last Row with data is:  "+lastRow);
        //int rowcount = 0;
        int startrow = getRowContains(0, "CreateWorkSheetTest");
        System.out.println("start row is "+startrow);
        int rowUsed = 0;
        for (int i=0; i<lastRow+1; i++ ) {
               String testname = getCellData(i,0);
               //System.out.println(testname);
               if (testname.equalsIgnoreCase("CreateWorksheetTest")) {                           
                     rowUsed +=1;
               }                    
        }

	}
	
	public static String writeCellValue(int RowNum, int ColNum, String text ) throws Exception{
	      String text1 = "exception error";
	  	try{	        	   
	      	  Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
	      	  System.out.println("setCellValue Row Number: "+RowNum+" and colnum "+ColNum);      	 
	      	  Cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);      	    
	      	  String filepath = "C:\\Workspace\\WetravelProject\\src\\acn\\testdata\\SampleTestData.xlsx";
	      	  FileOutputStream webdata=new FileOutputStream(filepath);
	      	  Cell.setCellValue(text); 
	      	  ExcelWBook.write(webdata);
	      	  return text1;            
	            }catch (Exception e){
	            	return text1;
	            }
	  	}
	
}
