package WebdriverPro.web.framework.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ExcelReportGenerator {


	public void generateExcelReport(String destFile) throws ParserConfigurationException, SAXException, IOException{


		String path = System.getProperty("user.dir")+"\\test-output\\testng-results.xml";
		System.out.println(path);

		File xmlFile = new File(path);

		System.out.println(xmlFile.isFile());

		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = fact.newDocumentBuilder();
		Document doc = builder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		XSSFWorkbook workbook = new XSSFWorkbook();

		NodeList testNodeList = doc.getElementsByTagName("test");

		for (int i = 0; i < testNodeList.getLength(); i++) {
			int r = 0;

			Node testNode = testNodeList.item(i);

			String testName = ((Element)testNode).getAttribute("name");
;
			XSSFSheet sheet = workbook.createSheet(testName);



			NodeList classNodeList = ((Element)testNode).getElementsByTagName("class");

			for (int j = 0; j < classNodeList.getLength(); j++) {

				Node classNode = classNodeList.item(j);

				String className = ((Element)classNode).getAttribute("name");

				XSSFRow row = sheet.createRow(r++);

				XSSFCell classCell = row.createCell(0);
				classCell.setCellValue(className);

				NodeList testMethodList = ((Element)classNode).getElementsByTagName("test-method");


				for (int k = 0; k < testMethodList.getLength(); k++) {

					Node testMethod = testMethodList.item(k);

					String testMethodName = ((Element)testMethod).getAttribute("name");

					XSSFCell testMethodCell = row.createCell(1);
					testMethodCell.setCellValue(testMethodName);

					String testMethodStatus = ((Element)testMethod).getAttribute("status");

					XSSFCell testMethodStatusCell = row.createCell(2);
					testMethodStatusCell.setCellValue(testMethodStatus);

					if("fail".equalsIgnoreCase(testMethodStatus)){

						NodeList exceptionList = ((Element)testMethod).getElementsByTagName("exception");

						Node exception = exceptionList.item(0);

						String exceptionMessage = ((Element)exception).getAttribute("class");

						XSSFCell exceptionCell = row.createCell(3);
						exceptionCell.setCellValue(exceptionMessage);
					}
				}
			}



		}
		
		FileOutputStream fos  = new FileOutputStream(destFile);
		workbook.write(fos);
		fos.close();
		System.out.println("Report Generated");

	}


	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub


		new ExcelReportGenerator().generateExcelReport("report.xlsx");
	}

}
