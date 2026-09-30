package RMS.util.PageObject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import utils.selenium.SeleniumUtil;

/**
 * Clase implementando el POM con los elementos necesarios para
 * la busqueda y ejecucion de jobs en la plataforma de Ctrl M
 * @author Jesus Hernadez
 * @author JL Chacon
 *
 */
public class Monitoring {

    SeleniumUtil u;
    /**
     * Constructor Monitoring, recibe un objeto SeleniumUtil
     * @param u
     */
    public Monitoring(SeleniumUtil u) {
        super();
        this.u = u;
    }

    // Locators de la pagina Monitoring en Cotrol-M
    By pwdExpire = By.xpath("/html/body/div[1]/div/div/div/div[3]/button[1]");
    By monitoring = By.xpath("//*[@id=\"SelfServiceDomainTabHeader\"]/div/a");
    By viewpoints = By.xpath("//*[@id='viewpointTabDiv']/a/div/span");
    By services = By.xpath("//div[@class='containerTab']//span[normalize-space()='Services']");

    By addView = By.xpath("//span[normalize-space()='Add Viewpoint']");
    By closeTips = By.xpath("//a[@class='d-icon-left-cross close-button']");
    By folderName = By.xpath(
            "//*[@id='filter_FULL_TABLE_NAME']/div/div/viewpoint-editor-tag-input/ng-select/div/div/div[2]/input");
    //By anyPlace = By.xpath("/html/body/modal-container/div/div/viewpoint-editor/div/div[1]/h3");
    By anyPlace = By.xpath("//*[@id=\"filter_APPLICATION\"]/div/div/span");

    By canvas = By.xpath("//div[@id='diagramElement']");

    By jobNameField = By.xpath("//*[@id=\"filter_JOB_NAME\"]/div/div/input");
    By openButton = By.xpath("//*[@id=\"btnViewpointEditorOpen\"]");
    By actions = By.xpath("//*[@id='slsDomainTabset']/div/div[5]/tab-content/service-viewpoint/div/div[1]/div[1]/div[3]");
    By jobStatusValue = By.xpath(
            "//*[@id=\"slsDomainTabset\"]/div/div[4]/tab-content/service-viewpoint/div/div[2]/div[3]/job-details/scrollable-tabset/div/div[1]/div/div/div[1]/job-details-general/div/table/tbody[2]/tr[2]/td[2]/span[2]");

    By releaseOption = By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Release']");
    By runNow = By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Run Now']");
    By rerunOption = By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Rerun']");
    By hold = By.xpath(" //*[@id=\"slsDomainTabset\"]/div/div[4]/tab-content/service-viewpoint/div/div[1]/div[1]/div[3]/ul/li[1]/a/span[2]");
    By closeViewpoint = By.xpath("//button[@class='d-icon-cross ng-scope']");
    By closeViewpointConfirm = By.xpath("//button[normalize-space()='Close Viewpoint']");
    By setOk = By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Set to OK']");
    
    By listView = By.xpath("//*[contains(@class,'active')]//service-viewpoint//button[@ng-click='vm.switchToListView()']");
    By jobOnList;
    By output =  By.xpath("//*[@id=\"slsDomainTabset\"]/div/div[4]/tab-content/service-viewpoint/div/div[2]/div[3]/job-details/scrollable-tabset/div/div[1]/div/ul/li[4]/a/uib-tab-heading");
  
    By jobOutputValue =By.xpath("//*[@id=\"slsDomainTabset\"]/div/div[4]/tab-content/service-viewpoint/div/div[2]/div[3]/job-details/scrollable-tabset/div/div[1]/div/div/div[4]/job-output/div/div[2]/find/div[2]");
    By closeNotification = By.xpath("//a[@id = 'notification-close']");
    
    By statusWaitCondition = By.xpath("//span[text()='Wait Condition']");
    By statusEndedOK = By.xpath("//span[text()='Ended OK']");
    By statusFailure = By.xpath("//span[text()='Failure']");
    By actions2 = By.xpath("//*[@id='single-button' and text()='Actions']");
    
    By newSetOk = By.xpath("//*[@id='slsDomainTabset']/div/div[5]/tab-content/service-viewpoint/div/div[1]/div[1]/div[3]/ul/li[6]");
    
    
    /**
     * Click en la pesta�a de Monitoring
     */
    public void clickMonitoring() {
    	u.waitWebElement(monitoring, 90, 2);
        u.highLight(monitoring);
        u.click(monitoring);
        u.waitForLoadPage();

    }

    /**
     * Cierra la ventana de expiracion de password
     */
    public void clickAboutExpire() {
        u.hardWait(5);
        List<WebElement> pwd = u.listElements(pwdExpire);
        
        if (pwd.size() != 0) {
            u.click(pwdExpire);
        }    
  
    }

    /**
     * Click en add Viewpoint, se debe considerar que un usuario podria
     * tener o no, viewpoints guardados, dependiendo de esto puede aparecer un
     * tip que intercepta el click a la opcion de AddViewpoint
     */
    public void addViewpoint() {
        u.hardWait(5);
        List<WebElement> closeTipsVisible = u.listElements(closeTips);
        
        if (closeTipsVisible.size() != 0) {
            u.click(closeTips);
        }    
        u.click(services);
        u.click(viewpoints);
        u.hardWait(3);
        u.highLight(addView);
        u.click(addView);
    
    }

    /**
     * Metodo para ingresar la carpeta donde se encuentra el Job
     * @param Folder
     */
    public void userTypesJobfolder(String Folder) {
        u.waitWebElement(folderName, 50, 2);
        u.highLight(folderName);
        u.click(folderName);
        u.sendKeys(folderName, Folder);
        u.click(anyPlace);

    }

    /**
     * Metodo para ingresar el nombre del Job
     * @param JobName
     */
    public void userTypesJobname(String JobName) {
        u.highLight(jobNameField);
        u.click(jobNameField);
        u.sendKeys(jobNameField, JobName);
        u.click(anyPlace);
    }

    /**
     * CLick en el boton Open
     */
    public void userClicksOpenBtn() {
        u.click(openButton);
    }

    public void waitForCanvasLoad() {
        u.waitWebElement(canvas, 120, 500);
    }


    /**
     * Este metodo da click en el modo lista y selecciona el Job en caso de que
     * la busqueda sea exitosa.
     * @param JobName
     * @return falso o verdadero si el Job fue encontrado
     * @author Roberto Flores & JL CHacon
     */
    public boolean userSelectsJob(String JobName){

    	try {
    		//jobOnList = By.xpath("//*[contains(@class,'active')]//div/span[text()='"+JobName+"']");
    		jobOnList = By.xpath("//span[@initial-text='"+JobName+"']");

        	u.click(listView);
        	u.click(jobOnList);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    	return true;
    }

    /**
     * Este metodo devuelve el estado actual del Job
     * @return
     */
    public String getJobStatusValue() {
    	try {
    		u.waitWebElement(jobStatusValue, 20, 2);
    		return u.getText(jobStatusValue);
    	} catch(Exception e) {
    		System.out.println("NO ENCONTRO ELEMENTO PARA STATUS, SE BUSCA POR TEXTO VISIBLE");
    		
    		try {
    			u.waitWebElement(statusWaitCondition, 5, 2);
        		return "Wait Condition";
    		}catch(Exception e1) {
        		try {
        			u.waitWebElement(statusEndedOK, 5, 2);
            		return "Ended OK";
        		}catch(Exception e2) {
        			u.waitWebElement(statusFailure, 5, 2);
            		return "Failure";
        		}
    		}
    	}
    }
    
    /**
     * Este metodo devuelve el resultado del job en la pesta�a de output
     * @return String
     */
    public String getOutputValue() {
    	return u.getText(jobOutputValue);
    }
     
    /**
     * CLick en la lista de Acciones
     */
    public void userClicksActions() {
    	try {
    		u.highLight(actions); //test
    		u.click(actions);
    	} catch(Exception e){
    		u.highLight(actions2); //test
    		u.click(actions2);
    	}
    }
    
    /**
     * Click en las opciones de run now o re run de la lista de Actions
     * Si el job esta en status "wait condition" se hace click en run now,
     * cualquier otro estado se hace click en re run.
     * @param status - Es el estatus actual del job a ejecutar
     * @author Jesus Hdz & JL Chacon
     */
    public void userClicksExecute(String status) {

        if (status.equals("Wait Condition")) {
   // Si esta el job en waiting condition entonces siempre dara click en run Now
            u.highLight(runNow);
            u.click(runNow);
            
        } else {
            u.highLight(rerunOption);
            u.click(rerunOption);
        }

    }
    /**
     * Este metodo se podria usar para los jobs ciclicos o especiales
     * cuyo status esta en hold o release. Si la opcion de release esta
     * disponible se hace click ahi , caso contrario primero se da click en hold
     * para poder habilitar la opcion de release.
     * (este metodo es llamado solo cuando en el dataprovider el key release esta
     * presente y esta como true.
     * @author JL Chacon
     */
    public void userClicksRelease() {
    	
    	System.out.println("EL elemento release esta disponible: "+ u.isDisplayed(releaseOption));
        
           if (u.isDisplayed(hold)) {  // poner en hold el job primero
              	System.out.println("Ejecuto el hold");
              	u.highLight(hold);
                u.click(hold);
                u.hardWait(5);
                u.highLight(actions); 
                u.click(actions);
           } 

           u.highLight(releaseOption);   // ejecutar job dando click en release
           u.click(releaseOption);
           System.out.println("Ejecuto el release");
    	   u.takeScreenshot(getJobStatusValue());
    }
    
    /**
     * Este metodo da click en la pesta�a de output ya despues de haber ejecutado
     * el job
     */
    public void clickOutput() {
    	u.highLight(output);
    	u.hardWait(10);
    	u.click(output);
    }
    
    /**
     * CLick para cerrar la pesta�a de viewpoint
     */
    public void userClosesViewpoint() {
        u.click(closeViewpoint);
        u.hardWait(2); //test
        u.click(closeViewpointConfirm);
    }
    
    /**
     * Metodo para hacer click en action Hold
     * @return
     */
    public Boolean clickHold() {
      Boolean ok= false;
      if (u.isDisplayed(hold)) {  
         u.highLight(hold);
         u.click(hold);
         ok=true;
      }
      return ok;
    }
    /**
     * Metodo para hacer click en action Release
     * @return
     */
    public Boolean clickRelease(By releaseOption) {
      Boolean ok= false;
      if (u.isDisplayed(releaseOption)) {  
         u.highLight(releaseOption);
         u.click(releaseOption);
         ok=true;
      }
      return ok;
    }
    /**
     * Metodo para hacer click en action Rerun
     * @return
     */
    public Boolean clickRerun(By rerunOption) {
      Boolean ok= false;
      if (u.isDisplayed(rerunOption)) {  
         u.highLight(rerunOption);
         u.click(rerunOption);
         ok=true;
      }
      return ok;
    }
    /**
     * Metodo para hacer click en action Run Now
     * @return
     */
    public Boolean clickRunNow(By runNow) {
      Boolean ok= false;
      if (u.isDisplayed(runNow)) {  
         u.highLight(runNow);
         u.click(runNow);
         ok=true;
      }
      return ok;
    }
    
    /**
     * Metodo para hacer click en action Release
     * @return
     */
    public Boolean clickRelease() {
      Boolean ok= false;
      if (u.isDisplayed(releaseOption)) {  
         u.highLight(releaseOption);
         u.click(releaseOption);
         ok=true;
      }
      return ok;
    }
    /**
     * Metodo para hacer click en action Rerun
     * @return
     */
    public Boolean clickRerun() {
      Boolean ok= false;
      if (u.isDisplayed(rerunOption)) {  
         u.highLight(rerunOption);
         u.click(rerunOption);
         ok=true;
      }
      return ok;
    }
    /**
     * Metodo para hacer click en action Run Now
     * @return
     */
    public Boolean clickRunNow() {
      Boolean ok= false;
      if (u.isDisplayed(runNow)) {  
         u.highLight(runNow);
         u.click(runNow);
         ok=true;
      }
      return ok;
    }
    
    /**
     * Metodo para hacer click en action Hold
     * @return
     */
    public Boolean clickSetOk() {
      Boolean ok= false;
      try {
		if (u.isDisplayed(setOk)) {  
		     u.highLight(setOk);
		     u.click(setOk);
		     ok=true;
		  }
	} catch (Exception e) {
		if (u.isDisplayed(newSetOk)) {  
		     u.highLight(newSetOk);
		     u.click(newSetOk);
		     ok=true;
		  }
	}
      return ok;
    }
    
    /**
     * Metodo para hacer click en action Hold
     * @return
     */
    public Boolean closeNotification() {
      Boolean ok= false;
      if (u.isDisplayed(closeNotification)) {  
         u.highLight(closeNotification);
         u.click(closeNotification);
         ok=true;
      }
      return ok;
    }
}
