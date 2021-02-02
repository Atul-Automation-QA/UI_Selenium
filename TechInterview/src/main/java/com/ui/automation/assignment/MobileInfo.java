package com.ui.automation.assignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.Test;

import bsh.org.objectweb.asm.ClassWriter;

public class MobileInfo {

	
	@Test
	public void getMobilesInfo() throws InterruptedException, IOException {

	  // Setting the BrowserDriver Path
		String driver_Path = "C:\\Users\\lenovn\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", driver_Path);

	  // Invoking Chrome browser
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // Providing implicit wait globally
		
	// Navigating to Flipkart.com
		String url="https://www.flipkart.com/";
		driver.get(url);
		WebElement close_login_Prompt =driver.findElement(By.cssSelector("._2KpZ6l._2doB4z"));
		close_login_Prompt.click();// Close the Login Prompts screen , Continue with Login
		
	// On Search box , Search for Apple mobile 	
		WebElement search_Box =driver.findElement(By.xpath("//input[@type='text']"));
		search_Box.sendKeys(Keys.ENTER, "apple mobiles", Keys.ENTER);
		
	// Set the Price filter to get mobile under 30000
		WebElement price_Filter=driver.findElement(By.xpath("//div[@class='_3uDYxP']//select[@class='_2YxCDZ']"));
		Select clickpriceFilter = new Select(price_Filter);
		clickpriceFilter.selectByValue("30000"); 
		
		
	//Getting the Count of all Apple Phone after Price filter
		int mobile_Count = driver.findElements(By.cssSelector("._4rR01T")).size();
		String mobileprice;
		int int_mobilePrice = 0;
		
	//	Store all Mobiles and their respective prices in a List
		Thread.sleep(4000);
		List<WebElement> List_of_Mobile=driver.findElements(By.cssSelector("._4rR01T"));
		
	// Created HasHmap so that we can store the Price & Device details in Key value pair
		HashMap<Integer, String> map_final_products = new HashMap<Integer, String>();
		

		for (int i = 0; i <mobile_Count; i++) {

			String mobile_name = List_of_Mobile.get(i).getText();
			String mobile_Price = driver.findElements(By.cssSelector("._30jeq3._1_WHN1")).get(i).getText();
			

			mobile_Price = mobile_Price.replaceAll("[^0-9]", "");// Replace anything with space other than numbers
			int_mobilePrice = Integer.parseInt(mobile_Price);// Convert to Integer
			map_final_products.put(int_mobilePrice, mobile_name);// Add product and price in HashMap

		}
		//Getting the Product Name and Price in logs
		Reporter.log("Product Name and price fetched from UI and saved in HashMap as:" + map_final_products.toString()
				+ "<br>", true);

		Set set_final_products =map_final_products.entrySet(); //converting HashMap into set
		Iterator it= set_final_products.iterator();//iterating the set
		
		while(it.hasNext()) {
			
			
			Map.Entry final_products = (Map.Entry)it.next(); //casting to Map.enrtry
			
			System.out.println(final_products.getValue() +" " +final_products.getKey()); 
			
			String separator = System.getProperty("line.separator");
			try (Writer writer = new FileWriter("iphoneDetails.csv")) {
			  for (Entry<Integer, String> entry : map_final_products.entrySet()) {
			    writer.append(entry.getValue())
			          .append(',')
			          .append( separator, entry.getKey(), int_mobilePrice);
			         
			  }
			} catch (IOException ex) {
			  ex.printStackTrace(System.err);
			}
			
			
			
                
		}
		
	
        

	}

	

}
