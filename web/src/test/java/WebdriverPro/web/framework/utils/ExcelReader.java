package WebdriverPro.web.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {


	public Object[] readFor(String sheetName, String key) throws IOException{


		File file = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\testData.xlsx");

		String fileName = file.getName();
		String extension = fileName.substring(fileName.indexOf("."));
		FileInputStream fileInputStream = new FileInputStream(file);


		Workbook workbook = null;

		if(extension.equals(".xlsx")){
			workbook = new XSSFWorkbook(fileInputStream);
		} else if (extension.equals(".xls")) {
			workbook = new HSSFWorkbook(fileInputStream);
		}


		Sheet sheet = workbook.getSheet(sheetName);

		int rowCount = sheet.getLastRowNum();

		Object[] array = null;

		for (int i = 0; i < rowCount; i++) {

			Row row = sheet.getRow(i);

			if(row.getCell(0).getRichStringCellValue().toString().contains(key) && row.getCell(1).getRichStringCellValue().toString().contains(JavaUtils.getEnvironment())){

				array = new Object[row.getLastCellNum()];
				for(int j = 0; j < row.getLastCellNum(); j++ ){

					array[j] = getCellData(row.getCell(j));
				}

				break;
			}
		}
		
		return array;

	}



	public Object getCellData(Cell cell){
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:  // 1
			return cell.getRichStringCellValue().getString();
		case Cell.CELL_TYPE_NUMERIC: // 0
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();
			}
		case Cell.CELL_TYPE_BOOLEAN:  // 4
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_FORMULA: // 2
			return cell.getCellFormula();
		default:
			return null;
		}
	}

	
	public List<String> getListFor(String sheet, String key) throws IOException{
		
		List<String> list = new ArrayList<String>();
		Object[] objectArray = new ExcelReader().readFor(sheet, key);
		try {
			for (int i = 0; i < objectArray.length; i++) {
				list.add(objectArray[i].toString());
			}			

			return list;

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println();
			return null;
		}
	}
	



	public static void main(String[] args) throws IOException {
			List<String> data =  new ExcelReader().getListFor("dataSheet", "test1");
			for (String item : data) {
				System.out.print(item+" | ");
			}
			
	}
}
