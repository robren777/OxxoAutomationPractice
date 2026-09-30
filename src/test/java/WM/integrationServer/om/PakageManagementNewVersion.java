
package WM.integrationServer.om;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class PakageManagementNewVersion {

	// utilerias
	SeleniumUtil u;
	TestCase testCase;
	
	/** software
	 * Product webMethods Integration Server Version 9.9.0.0 Updates TNS_9.9_Fix5
	 * IS_9.9_SPM_Fix4 IS_9.9_Core_Fix13
	 */
	
	By userField = By.id("isac_usernameElementRef");
	By passwordField = By.id("isac_passwordFieldRef");
	By loginButton = By.xpath("//button[text()='Log in']");
	
	By menu = By.id("primaryitemsRoot");
	
	By packageOption = By.xpath("//div[@id=\"ac.nav.label.packages\"]");
	By managment = By.xpath("//a[@id=\"ac.nav.label.packageManagement\"]");
	

	By settingsOption = By.xpath("//*[@id=\"ac.nav.label.settings\"]");
	By messaging = By.xpath("//*[@id=\"asettings-messaging.dsp\"]");
	By jmsTriggerManagement = By.xpath("//a[text()='JMS Trigger Management']");
	By browseServices = By.xpath("//*[@id=\"BrowseServicesLink\"]/a");
	By runService = By.xpath("/html/body/table/tbody/tr[2]/td/ul/li[2]/a");
	By whInpunts = By.xpath("/html/body/table/tbody/tr[3]/td[2]/table/tbody/tr[2]/td/input[1]");
	By whOutInputs = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr[2]/td/input[2]");
	By whOutInputs2 = By.xpath("//input[@class='button2'][@value='Test (without inputs)']"); //po2 
	By globalVariables = By.xpath("//*[@id=\"ac.nav.label.settingsGlobalVariables\"]/div/span");
	
	By banderaUmbral = By.xpath("/html/body/table/tbody/tr[3]/td/table/tbody/tr[47]/td[1]/a");
	
	/** software
	 * Product webMethods Integration Server Version 10.5.0.0 Updates
	 * IS_10.5_Core_Fix6 Trading Networks Server 10.5 Fix 6 IS_10.5_SPM_Fix6
	 */
	
	By packageOption10 = By.xpath("//*[@id=\"elmt_Packages_subMenu\"]/a");
	By management10 = By.xpath("//*[@id=\"apackage-list.dsp\"]");
	By browseServices10 = By.xpath("/html/body/table/tbody/tr[2]/td/ul/li[2]/a");
	By runService10 = By.xpath("/html/body/table/tbody/tr[2]/td/ul/li[2]/a");
	By whOutInputs10 = By.xpath("//input[@class='button2'][@value='Test (without inputs)']");

	// -----Adapters

	By adapterOption = By.xpath("//*[@id='Adapters_twistie']");
	By nextpage = By.xpath("//*[@id='connection_page_no_next']");
	By forJDBC = By.xpath("//*[text()=' webMethods Adapter for JDBC  ...  ']");
	String adapterName = "//tr/td[text()='AdapterConnections:%s']";
	String adapterEnable = "//tr/td[text()='AdapterConnections:%s']/../td[4]/a";
	By x = By.xpath("//*[@id='view_connection10']");


	

	By adapterConnectionBy = By.xpath("/html/body/form/table[1]/tbody/tr[3]/td");
	By connectionTypeBy = By.xpath("/html/body/form/table[1]/tbody/tr[4]/td[2]");
	By conPackageBy = By.xpath("/html/body/form/table[1]/tbody/tr[5]/td[2]");

	//By xmlInput = By.xpath("//input[@name='%s']");
	By withInputButton = By.xpath("//input[@value='Test (with inputs)']");
	By withoutInputButton = By.xpath("//input[@value='Test (without inputs)']");
	
	By withoutInputButton10 = By.xpath("//input[@class =\"button2\"][@value = \"Test (without inputs)\"]");
	
	

	// vaiable que recibira el parametro
	String interfaceNameBy = "//a[text()='%s']";
	String selectServices = "//a[text()='%s']";
	String input = "//input[@name='%s']";
	String UUID = "//input[@name='UUID']";
	String Folio = "//input[@name='Folio']";
	String Serie = "//input[@name='Serie']";
	String StringXML = "//input[@name='StringXML']";
	String Reintento = "//input[@name='Reintento']";
	String RFCEmisor = "//input[@name='RFCEmisor']";
	String RFCReceptor = "//input[@name='RFCReceptor']";
	String adapterElement = "//td[contains(text(),'AdapterConnections:%s')]/following-sibling::td[3]/a";
	String nextPage = "//*[@id='paginationContainer']/a[5]";
	String triggerEnabled = "//table[@id=\"JMS_TABLE\"]/tbody/tr[td//text()[contains(., '%s')]]";
	

	// constructor
	public PakageManagementNewVersion(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;

	}
	
	public void inicarSesion(String user, String ps) {
		
		u.sendKeys(userField, user);
		u.sendKeys(passwordField, ps);
		u.click(loginButton);	
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "login");
	}
	
	
	
	public void clickpackage() {
		
		
		u.getDriver().switchTo().frame(0);
//		u.highLight(By.xpath("//html"));
		u.getDriver().switchTo().frame(0);
//		u.highLight(By.xpath("//html"));
		u.getDriver().switchTo().frame(1);
//		u.highLight(By.xpath("//html"));
				
		u.click(packageOption10);
//		u.highLight(packageOption10);
		u.click(management10);
		u.getDriver().switchTo().parentFrame();
		// te swicheas al deseado
		u.getDriver().switchTo().frame(2);
		testCase.addScreenShotCurrentStep(u, "package list");
	}
	
public void clickpackage2() {
		u.getDriver().switchTo().frame(0);
		u.getDriver().switchTo().frame(1);
		u.click(packageOption10);
		u.click(management10);
		u.getDriver().switchTo().parentFrame();
		// te swicheas al deseado
		u.getDriver().switchTo().frame(2);
		testCase.addScreenShotCurrentStep(u, "package list");
	}
	
	
	
	public String GetText(String Xpath) {
		
		String text = u.getText(By.xpath(Xpath));
		testCase.addScreenShotCurrentStep(u, "interfaz");
		return text;
	}

	


	public void clickManagment(){
		//u.getDriver().switchTo().frame(0);
		//u.getDriver().switchTo().frame(0);
		//u.getDriver().switchTo().frame(1);
		u.hardWait(5);
		u.mouseOver(menu);
		u.scrollToElement(packageOption);
		u.mouseOverAndClick(packageOption);
		u.mouseOverAndClick(managment);
		testCase.addScreenShotCurrentStep(u, "IS");

	}
	
	
	/*
	 * Nuevo metodo para ingresar a Managment actualmente
	 */
	
	public void clickManagment2() {
		u.getDriver().switchTo().frame(0);
		u.getDriver().switchTo().frame(1);

		u.click(packageOption);
		u.click(managment);
		testCase.addScreenShotCurrentStep(u, "IS");

	}
	
	public String getIpSwitch(String variable, boolean resumen ) {

		u.getDriver().switchTo().frame(0);
		u.getDriver().switchTo().frame(1);
		u.click(settingsOption);
		if (resumen) {
			testCase.addScreenShotCurrentStep(u, "IS");
		}
		u.click(globalVariables);
		u.getDriver().switchTo().parentFrame();
		u.getDriver().switchTo().frame(2);
		boolean exist;
		int row_num, col_num;
		boolean getvalue = false;
		String value = "";
		try {
				u.getDriver().findElement(By.xpath("//a[contains(text(),'"+variable+"')]"));
				List <WebElement> tabla=u.getDriver().findElements(By.xpath("//table[@class='tableView']/tbody/tr"));	
				System.out.print(tabla.toString());
				System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tabla.size());
				exist=true;

		        row_num=1;
		        for(WebElement trElement : tabla)
		        {
		            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
		            System.out.println("NUMBER OF COLUMNS="+td_collection.size());
		            col_num=1;
		            for(WebElement tdElement : td_collection)
		            {
		                System.out.println("row # "+row_num+", col # "+col_num+ "text="+tdElement.getText());
		                if(tdElement.getText().equalsIgnoreCase(variable)) {
		                	getvalue=true;
		                }
		                if(getvalue==true&&col_num==2) {
		                	value=tdElement.getText();
		                	getvalue=false;
		                }
		                col_num++;
		            }
		            row_num++;
		        } 

		} catch (Exception e) {
			exist=false;
		}
		System.out.println(exist);
		if (resumen) {
			testCase.addScreenShotCurrentStep(u, "IS");
		}
		return value;
	}
	

	public void selectInterface(String interfaceWM) {

		String interF = String.format(interfaceNameBy, interfaceWM);
		u.getDriver().switchTo().parentFrame();
		u.getDriver().switchTo().frame(0);
		
		u.hardWait(15);
		u.scrollToElement(By.xpath(interF));
		u.highLight(By.xpath(interF));
		testCase.addScreenShotCurrentStep(u, "selectionIter");
//        button.click();
		u.click(By.xpath(interF));

		//testCase.addScreenShotCurrentStep(u, "selectionIter");

	}
	
	

	public void clickBrowserService() {

		u.highLight(browseServices);
		testCase.addScreenShotCurrentStep(u, "buscarServicio");
		u.click(browseServices);

	}

	public void selectService(String nameService) {

		String browserService = String.format(selectServices, nameService);
		u.scrollToElement(By.xpath(browserService));
		u.highLight(By.xpath(browserService));
		testCase.addScreenShotCurrentStep(u, "servicio1");
		u.click(By.xpath(browserService));

		//testCase.addScreenShotCurrentStep(u, "servicio2");

	}

	public void clickTestRun() {

		u.highLight(runService);
		testCase.addScreenShotCurrentStep(u, "runServicio");
		u.click(runService);

	}

	public void clickSelectNoInput() {

		u.highLight(whOutInputs);
		u.click(whOutInputs);
		u.hardWait(8);
		testCase.addScreenShotCurrentStep(u, "fin");
		u.close();

	}
	


	public String runIntefaceWM(String interfaceWM, String service, String inputs) {

		clickManagment();
		selectInterface(interfaceWM);
		clickBrowserService();
		selectService(service);
		clickTestRun();
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String dateExecution = ft.format(dNow);
		u.hardWait(5);
		clickSelectNoInput();

		return dateExecution;

	}
	
	public void getBanderaUmbral() {

		u.scrollToElement(settingsOption);
		u.click(settingsOption);
		u.click(globalVariables);
		u.getDriver().switchTo().frame(0);
		u.scrollToElement(banderaUmbral);
		u.click(banderaUmbral);
		testCase.addScreenShotCurrentStep(u,"Umbral");
		
	}
	

}
