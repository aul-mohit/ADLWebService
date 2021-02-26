package adl;
import java.io.FileInputStream;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * ReadData, to read xml/excel file, acts as OR reader.
 *
 * @author Mohit Goel
 */
public class ReadData {

	/**
	 * Read excel, used as test data file
	 *
	 * @param filePath  the path of excel file
	 * @param sheetName sheet which need to get data
	 * @throws Exception
	 */
	@SuppressWarnings({ "resource" })
	public static Object[][] getExcelData(String fileName, String sheetName) {
		//// read data from data provider excel and appened in string array
		Object[][] excelData = null;
		try {
			//// read file
			FileInputStream fs = new FileInputStream(fileName);
			//// get workbook based on sheeta
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			//// get excel sheet
			Sheet sh = wb.getSheet(sheetName);
			//// no of rows
			int totalNoOfRows = sh.getLastRowNum() - sh.getFirstRowNum();
			excelData = new Object[totalNoOfRows][1];
			//// no of columns
			int totalNoOfColumn = sh.getRow(0).getLastCellNum();
			//// iterate through rows and columns
			for (int i = 1; i <= totalNoOfRows; i++) {
				String[] data = new String[totalNoOfColumn];
				Row row = sh.getRow(i);
				for (int j = 0; j < totalNoOfColumn; j++) {
					String abc = "";
					try {
						CellType cellType = row.getCell(j).getCellType();
						//// Switch case to convert excel data to excel
						switch (cellType.toString().toLowerCase()) {
						case "string":
							abc = row.getCell(j).getStringCellValue();
							break;
						case "blank":
							abc = row.getCell(j).getStringCellValue();
							break;
						case "numeric":
							abc = Double.toString(row.getCell(j).getNumericCellValue());
							break;
						default:
							abc = row.getCell(j).getStringCellValue();
						}
					} catch (Exception e) {
						abc = "";
					}
					//// appened data in string array
					data[j] = abc;
				}
				excelData[i - 1][0] = data;
			}
		} catch (Exception e) {
			//// do nothing

		}
		return excelData;
	}

	public static Object[][] dataProvider(String excelFileName, String sheetName) {
		//// current running directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repo\\";

		//// Excel sheet file name
		String fileName = excelFileName + ".xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, sheetName);
		return arrayObject;
	}

}
