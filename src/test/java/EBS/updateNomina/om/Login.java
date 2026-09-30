package EBS.updateNomina.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Login {

    //Elementos la conexi�n no es privada Edge
    //By botonAvanzado = By.xpath("//*[@id=\"details-button\"]");
    //By linkContinuar = By.xpath("//*[@id=\"proceed-link\"]");

    /*
     * Localizadores conexion no privada
     ******************************************************************************************/
    By xmlMensaje = By.xpath("//*[@id=\"main-message\"]/h1");
    By xmlAdvanced = By.xpath("//*[@id=\"details-button\"]");
    By xmlProceed = By.xpath("//*[@id=\"proceed-link\"]");

    //By xmlMoreInformation = By.cssSelector("span#moreInfoContainer > a");//By.xpath("//*[@id='moreInfoContainer']/a");
    //By xmlGoOnPage = By.xpath("//*[@id='overridelink']");

    By xmlMoreInformation = By.xpath("//*[text()='More information']");
    By xmlGoOnPage = By.xpath("//*[text()='Go on to the webpage (not recommended)']");



    //Elementos la conexi�n no es privada
    By botonAvanzado = By.xpath("/html/body/div/div[2]/button[3]");
    By linkContinuar = By.xpath("/html/body/div/div[3]/p[2]/a");

    //Elementos AuthFemco
    By inputNombreDeUsuario = By.xpath("/html/body/div/div[1]/div/form/div[1]/input");
    By inputContrasena = By.xpath("/html/body/div/div[1]/div/form/div[2]/input");
    By botonInciarSesion = By.xpath("/html/body/div/div[1]/div/form/button[1]");
    By botonMostrarMasFyA = By.xpath("/html/body/app-root/app-access-control/div/div/div/div[2]/div[1]/div/div[2]/button");
    By integracionPolizasSOL = By.xpath("/html/body/app-root/app-access-control/div/div/div/div[2]/div[1]/div/div[2]/div[1]/p/a");



    //Elementos CNT Colombia
    //Login
    By inputUsuarioCntCol = By.xpath("/html/body/form/table/tbody/tr[1]/td[2]/input");
    By inputContrasenaCntCol = By.xpath("/html/body/form/table/tbody/tr[2]/td[2]/input");
    By inputBotonContinuarCntCol = By.xpath("/html/body/form/table/tbody/tr[3]/td/div/input");
    //Ingresar Plaza
    By selectPlaza = By.xpath("//select[@name='p_cr_plaza']");
    By inputBotonContinuarPlazaCntCol = By.xpath("/html/body/form/table/tbody/tr[2]/td/div/input");
    //Ingresar Tienda
    By selectTienda = By.xpath("/html/body/form/table/tbody/tr[3]/td[2]/select");


    By inputusuario = By.xpath("/html/body/div/div[1]/div/form/div[1]/input");
    By inputpassword = By.xpath("/html/body/div/div[1]/div/form/div[2]/input");
    By btncontinuar = By.xpath("/html/body/div/div[1]/div/form/button[1]");

    private final SeleniumUtil u;
    private final WebDriverWait wait;
    private final TestCase testCase;


    /**
     * Constructor.
     *
     * @param u
     * @param testCase
     * @author Mario A. De Leon Rdz.
     */
    public Login(SeleniumUtil u, TestCase testCase) {
        super(); // Llama al constructor de la clase padre (en este caso, Object)
        this.u = u; // Asigna el objeto SeleniumUtil pasado como par�metro a la variable de instancia
        this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60)); // Inicializa WebDriverWait con el driver de SeleniumUtil y un timeout de 60 segundos
        this.testCase = testCase; // Asigna el objeto TestCase pasado como par�metro a la variable de instancia
    }

    //Metodos para ingresar al portal de aplicaciones
    public void IngresarAAuthFemco(String user, String ps) {
        try {
            u.getDriver().manage().deleteAllCookies();
            String url = "https://fcportalqa.femcom.net:8443/AuthFemcoProx/access-control";
            testCase.addTextEvidenceCurrentStep(url);

            u.get(url);
            u.getDriver().manage().window().maximize();
            u.click(botonAvanzado);
            u.click(linkContinuar);

            u.getDriver().navigate().refresh();
            wait.until(ExpectedConditions.elementToBeClickable(inputNombreDeUsuario));
            u.sendKeys(inputNombreDeUsuario, user);
            u.sendKeys(inputContrasena, ps);
            u.click(botonInciarSesion);
        } catch (Exception e) {
            System.out.println(" -> " + e);
            this.u.close();
            throw new AssertionError("No se pudo ingresar a https://fcportalqa.femcom.net:8443/AuthFemcoProx/login");
        }

    }

    //Metodos para
    public void SeleccionarSOL() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(botonMostrarMasFyA));
            testCase.addScreenShotCurrentStep(u, "linkSADEL");
            u.click(botonMostrarMasFyA);
            u.click(integracionPolizasSOL);
        } catch (Exception e) {
            System.out.println(" -> " + e);
            this.u.close();
            throw new AssertionError("No se pudo dar click en enlace para ingresar a SADEL NOMINA COMISIONISTA");
        }
    }


    public void proceedToPortalIE() {

        Boolean bandera = false;
        try {
            u.getDriver().findElement(xmlMoreInformation);
            bandera = true;
        }catch (Exception e) {
            bandera = false;
        }

        if (bandera) {
            u.click(xmlMoreInformation);
            //u.hardWait(2);
            u.click(xmlGoOnPage);
        }

    }
    /**
	 * M�todo para saltar ventana de "Conexi�n no privada"
	 * @author Mar�a Due�as
	 */
	public void proceedToPortal() {
    	
    	Boolean bandera = false;
    	try {
    		u.getDriver().findElement(xmlMensaje);
    		bandera = true;
    	}catch (Exception e) {
    		bandera = false;
    	}
    	
    	if (bandera) {
    		u.click(xmlAdvanced);
        	u.click(xmlProceed);
    	}
    	
    }

    public void IngresarCntCol(String user, String ps) {
        try {
            u.getDriver().manage().deleteAllCookies();
            String url = "https://opvcntnp1.femcom.net:8443/colombia/tiendas/html/login.html";
            testCase.addTextEvidenceCurrentStep(url);

            u.get(url);
            proceedToPortal();

            u.waitForLoadPage();
            
            System.out.println("User ->"+ user);
            System.out.println("Password ->"+ ps);
            u.sendKeys(inputUsuarioCntCol, user);
            u.sendKeys(inputContrasenaCntCol, ps);
            testCase.addScreenShotCurrentStep("Pantalla Login cnt col");
            u.click(inputBotonContinuarCntCol);

        } catch (Exception e) {
            System.out.println(" -> " + e);
            this.u.close();
            throw new AssertionError("No se pudo ingresar a https://fcportalqa.femcom.net:8443/AuthFemcoProx/login");
        }

    }

    public void SeleccionarPlaza(String plaza){
        // Construye el XPath din�micamente usando el par�metro plaza
        String xpath = String.format("//option[@value='%s']", plaza);
        u.waitForLoadPage();
        WebElement webPlaza = u.getDriver().findElement(By.xpath(xpath));
        u.click(selectPlaza);
        webPlaza.click();
        testCase.addScreenShotCurrentStep(u,"Plaza seleccionada");
        u.click(inputBotonContinuarPlazaCntCol);

        u.waitForLoadPage();
        u.click(selectTienda);
        testCase.addScreenShotCurrentStep("Tiendas mostradas");
    }

        
    
} 