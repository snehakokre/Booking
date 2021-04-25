package TestApp.Booking;


import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;

import org.openqa.selenium.WebDriver;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AppTest 
{
	WebDriver driver;
	Properties prop; 
	WebDriverWait wait;
	String Journey_type;
	 String Journey_from;
	 String Destination;
	 String flight_class;
	 Double no_children;
	 Double no_adult;
	 Date departure_date;
	@BeforeTest
	public void Initialize() throws IOException {
		String dir = System.getProperty("user.dir");
    	System.setProperty("webdriver.chrome.driver",dir+"\\resources\\chromedriver.exe");
    	driver = new ChromeDriver();   
    	driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
        
        // Read the properties file to get xpath 
    	prop = new Properties();
        try {
            prop.load(new FileInputStream(dir+"\\resources\\path.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Read the excel data file for reading data 
        //File file =    new File(dir+"\\resources\\FlightsBooking.xlsx");
        try {
			OPCPackage pkg = OPCPackage.open(new File(dir+"\\resources\\FlightsBooking.xlsx"));
			XSSFWorkbook wb = new XSSFWorkbook(pkg);
			 XSSFSheet sheet = wb.getSheet("Journey_details");
			 Journey_type= sheet.getRow(1).getCell(0).getStringCellValue();
			 Journey_from=sheet.getRow(1).getCell(1).getStringCellValue();
			 Destination= sheet.getRow(1).getCell(2).getStringCellValue();
			 flight_class= sheet.getRow(1).getCell(3).getStringCellValue();
			 no_children=  sheet.getRow(1).getCell(4).getNumericCellValue();
			 no_adult= sheet.getRow(1).getCell(5).getNumericCellValue();
			departure_date= sheet.getRow(1).getCell(6).getDateCellValue();
		} catch (InvalidFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      
			/*inputStream = new FileInputStream(file);
			 HSSFWorkbook wb=new HSSFWorkbook(inputStream);
			 Sheet sheet = wb.getSheet("Journey_details");
			 System.out.println(sheet.getLastRowNum());*/
		        
	
        wait =new WebDriverWait(driver, 20);
	}
    
    @Test
    public void LaunchBrowser()
    {
    	String baseUrl = "https://www.phptravels.net/home";
        driver.get(baseUrl);
        driver.manage().window().maximize() ;
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("site_logo"))));
        
    }
    
    @Test(dependsOnMethods = { "LaunchBrowser" })
    public void BookFlight() {
    	driver.findElement(By.xpath(prop.getProperty("flight_booking"))).click();
    	String trip_info = prop.getProperty("trip_type");
    	
    	if(Journey_type=="One Way") {
    	String newtrip_type = trip_info.replace('#', '1');
    	System.out.println(newtrip_type);
    	driver.findElement(By.xpath(newtrip_type)).click();
    	}
    	//driver.findElement(By.xpath(prop.getProperty("trip_from"))).click();
    	
    	driver.findElement(By.xpath(prop.getProperty("trip_from_input"))).sendKeys(Journey_from);
    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty("trip_from_selection_value"))));
    	
    	driver.findElement(By.xpath(prop.getProperty("trip_from_selection_value"))).click();
        
    	List<WebElement> location = driver.findElements(By.xpath("trip_from_selection"));
    	System.out.println(location.size());
    	for( WebElement element: location) {
    		System.out.println(element.getText());
    	}
    	
    	driver.findElement(By.xpath(prop.getProperty("trip_to_input"))).sendKeys(Destination);
    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty("trip_to_selection_value"))));
    	
    	driver.findElement(By.xpath(prop.getProperty("trip_to_selection_value"))).click();
      
    	String date= "26/05/2021";
    	String[] date1= date.split("/");
    	String day= date1[0].toString();
    	String month= date1[1].toString();
    	String year = date1[2].toString();
    	driver.findElement(By.xpath("trip_departure")).click();
    	
    	
    	
    	
    	
    }
}
