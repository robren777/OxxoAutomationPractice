package WEB.simoti_Colombia.om.transaccionesElectronicas.transaccionesTDC;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.om.MetodosExtra;
import WEB.simoti_Colombia.om.TransaccionesTDC_SimotiColombia;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Transacciones_TDC {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	TransaccionesTDC_SimotiColombia TSM;

	String Replace = "";
	/*
	 * Localizadores pantalla Tarjeta de credito - Transacciones TDC
	 ********************************************************************************************/

	By radioBTNDatosGenerales = By.xpath("//td/table/tbody/tr/td/label[contains(text(), 'Datos Generales')]");
	
	By PlazaTD = By.id("formEnvDineroTdc:cmbPlaza");
	By Plazas = By.xpath("//*[@id=\"formEnvDineroTdc:cmbPlaza_items\"]/li");
	
	By tiendaTD = By.id("formEnvDineroTdc:cmbTienda_label");
	By tiendas = By.xpath("//*[@id=\"formEnvDineroTdc:cmbTienda_items\"]/li");
	
	By tipoTransaccionTD = By.id("formEnvDineroTdc:cmbTipoTransaccDatos");
	By tipoTransacciones = By.xpath("//*[@id=\"formEnvDineroTdc:cmbTipoTransaccDatos_items\"]/li");
	
	By tipoTarjetaTD = By.id("formEnvDineroTdc:cmbTipoTarjeta");
	By tipoTarjetas = By.xpath("//*[@id=\"formEnvDineroTdc:cmbTipoTarjeta_items\"]/li");
	
	
	By TXTNumeroTarjeta = By.id("formEnvDineroTdc:txtNumTarjeta");
	By TXTNumeroAutorizacion = By.id("formEnvDineroTdc:txtNumAutorizacion");
	
	
	By BTNBuscar = By.id("formEnvDineroTdc:btnBuscar");
	By BTNExportar = By.id("formEnvDineroTdc:btnExportar");
// Elementos del primer calendario	
	By calendarInicio = By.xpath("//*[@id=\"formEnvDineroTdc:cldfechade\"]/button/span[1]");
	By selectMes1 = By.xpath("//*[@id=\"ui-datepicker-div\"]/div[1]/div/select[1]");
	By selectAnio1 = By.xpath("//*[@id=\"ui-datepicker-div\"]/div[1]/div/select[2]");
	
// Elementos del segundo calendario
	By calendarFin = By.xpath("//*[@id=\"formEnvDineroTdc:cldfechaA\"]/button/span[1]");
	By selectMes2 = By.xpath("//*[@id=\"ui-datepicker-div\"]/div[1]/div/select[1]");
	By selectAnio2 = By.xpath("//*[@id=\"ui-datepicker-div\"]/div[1]/div/select[2]");
	
	String seldia = "//tbody/tr/td/a[contains(text(), '%s')]";

	
	By xmlDivTable = By.xpath("//*[@id='formEnvDineroTdc:gridRepTransaccTdc']");
	String xmltableHead = "//*[@id='formEnvDineroTdc:gridRepTransaccTdc']/div/table/thead/tr/th[%s]"; //th [15]
	
	
	
	
	
	
	
	
	
	By xmlMeses = By.xpath("//*[@id=\"j_idt151:cmbMes_items\"]/li");
	
	
	
	

	/*
	 * Constructor
	 ********************************************************************************************/
	public Transacciones_TDC(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		
		
	}
	
	
	public void clickRadioDatosGenerales() {
		u.hardWait(2);
		u.highLight(radioBTNDatosGenerales);
		u.click(radioBTNDatosGenerales);
	}
	
	
	
	public boolean selecPlaza(String plaza) {
		boolean existOption = false;
		WebElement element = me.explicitWaitToBeClickable(PlazaTD, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(Plazas);
		for (WebElement option : allOptions) {
			if (option.getText().equals(plaza)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}
		//me.explicitWaitToBeInvisible(15);
		u.highLight(PlazaTD);
		testCase.addScreenShotCurrentStep(u, "selectMes");
		u.unhighLight(PlazaTD);
		return existOption;
	}
	/**Metodo para selecionar tienda
	 * @autor Herbe  Cauich
	 * @param tienda
	 * @return Retorna exist si la opcion es seleccionada y se le da click
	 */
	
	public boolean selecTienda(String tienda) {
		boolean existOption = false;
		WebElement element = me.explicitWaitToBeClickable(tiendaTD, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(tiendas);
		for (WebElement option : allOptions) {
			if (option.getText().equals(tienda)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}
		//me.explicitWaitToBeInvisible(15);
		u.highLight(tiendaTD);
		testCase.addScreenShotCurrentStep(u, "selectMes");
		u.unhighLight(tiendaTD);
		return existOption;
	}
	/**Metodo para selecionar el tipo de transaccion
	 * @autor Herbe Cauich
	 * @param opcion
	 * @return Retorna exist si la opcion es seleccionada y se le da click
	 */
	public boolean selecTipoTransaccion(String tipoTransaccion) {
		boolean existOption = false;
		WebElement element = me.explicitWaitToBeClickable(tipoTransaccionTD, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(tipoTransacciones);
		for (WebElement option : allOptions) {
			if (option.getText().equals(tipoTransaccion)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}
		//me.explicitWaitToBeInvisible(15);
		u.highLight(tipoTransaccionTD);
		testCase.addScreenShotCurrentStep(u, "selectMes");
		u.unhighLight(tipoTransaccionTD);
		return existOption;
	}
	/**Metodo para selecionar el tipo de tarjeta
	 * @autor Herbe Cauich
	 * @param tipoTarjeta
	 * @return Retorna exist si la opcion es seleccionada y se le da click
	 */
	public boolean selecTipoTarjeta(String tipoTarjeta) {
		boolean existOption = false;
		WebElement element = me.explicitWaitToBeClickable(tipoTarjetaTD, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(tipoTarjetas);
		for (WebElement option : allOptions) {
			if (option.getText().equals(tipoTarjeta)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}
		//me.explicitWaitToBeInvisible(15);
		u.highLight(tipoTarjetaTD);
		testCase.addScreenShotCurrentStep(u, "selectMes");
		u.unhighLight(tipoTarjetaTD);
		return existOption;
	}
	
	
	
	/**Metodo para ingresar el numero de tarjeta
	 * @autor Herbe Cauich
	 * @param numeroTarjeta
	 * @return Retorna exist si la opcion es seleccionada y se le da click
	 */
    public void ingresarNumeroTarjeta(String numeroTarjeta) {
    	u.highLight(TXTNumeroTarjeta);
		u.sendKeys(TXTNumeroTarjeta, numeroTarjeta);
		testCase.addScreenShotCurrentStep(u, "campoNumeroTarjeta");
		
		
    }
    /**Metodo para ingresar el numero de autorizacion
	 * @autor Herbe Cauich
	 * @param numeroAutorizacion
	 * @return Retorna exist si la opcion es seleccionada y se le da click
	 */
    public void ingresarNumeroAutorizacion(String numeroAutorizacion) {
    	u.highLight(TXTNumeroAutorizacion);
		u.sendKeys(TXTNumeroAutorizacion, numeroAutorizacion);
		testCase.addScreenShotCurrentStep(u, "campoNumeroAutorizacion");
    }
    
    /**Metodo para hacer click en el boton buscar
	 * @autor Herbe Cauich
	 * @param 
	 * @return 
	 */
    public void clickBTNBuscar() {
    	u.highLight(TXTNumeroAutorizacion);
		u.click(BTNBuscar);
		testCase.addScreenShotCurrentStep(u, "campoNumeroAutorizacion");
    }
    /**Metodo para hacer click en el boton exportar
   	 * @autor Herbe Cauich
   	 * @param 
   	 * @return 
   	 */
    public void clickBTNExportar() {
    	u.highLight(TXTNumeroAutorizacion);
		u.click(BTNExportar);
		testCase.addScreenShotCurrentStep(u, "campoNumeroAutorizacion");
    }
    
    
    
    /**
	 * Metodo para realizar la seleccion de un elemento de un componente select mediante texto visible
	 * @author Herbe Cauich
	 * @param optionn, by
	 * @return N/A
	 */	
    public Boolean selectOption(String option, By by) {
		Boolean exist = false;
		
		try {
			Select selectObject = new Select(u.getDriver().findElement(by));
			selectObject.selectByVisibleText(option);
			exist = true;
		}catch(Exception e) {
			exist = false;
		}
		
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "selectOption");
		u.unhighLight(by);
		
		return exist;
	}
    
    /**
	 * Metodo para realizar la seleccion de un elemento de un componente select mediante valor de atributo
	 * @author Herbe Cauich
	 * @param optionn, by
	 * @return N/A
	 */	
    public Boolean selectOptionValue(String option, By by) {
		Boolean exist = false;
		
		try {
			Select selectObject = new Select(u.getDriver().findElement(by));
			selectObject.selectByValue(option);
			exist = true;
		}catch(Exception e) {
			exist = false;
		}
		
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "selectOption");
		u.unhighLight(by);
		
		return exist;
	}
    
    
    
    /**
	 * Metodo para seleccionar fecha
	 * @author Herbe Cauich
	 * @param mes, mes2, anio,anio2, diaSel1, diaSel2
	 * @return N/A
	 */
 	
 	public Boolean SelecDate1(String mes, String anio, String diaSel1) {
        Boolean exist = false;
	
        u.highLight(calendarInicio);
        u.click(calendarInicio);
        u.hardWait(2);
        u.highLight(selectMes1);
        exist = selectOption (mes, selectMes1);
        u.highLight(selectAnio1);
        exist = selectOptionValue (anio, selectAnio1);
       // u.click(dias);
        String DiaSelect1 = String.format(seldia, diaSel1);
        u.highLight(By.xpath(DiaSelect1));
		testCase.addScreenShotCurrentStep(u, "clickDia");
		u.unhighLight(By.xpath(DiaSelect1));
		u.click(By.xpath(DiaSelect1));
      /*  u.highLight(calendarFin);
        u.click(calendarFin);
        u.hardWait(2);
        u.highLight(selectMes2);
        exist = selectOption (mes, selectMes2);
        u.highLight(selectAnio2);
        exist = selectOptionValue (anio, selectAnio2);
       // u.click(dias);
        String diasSel = String.format(seldia,diaSel2);
        String DiaSelect2 = String.format(seldia, diaSel2);
        u.highLight(By.xpath(DiaSelect2));
		testCase.addScreenShotCurrentStep(u, "clickDia");
		u.unhighLight(By.xpath(DiaSelect2));
		u.click(By.xpath(DiaSelect2));
	*/	
		return exist;
 	}
 	
 	public Boolean SelecDate2(String mes, String anio, String diaSel1) {
        Boolean exist = false;
	
    /*    u.highLight(calendarInicio);
        u.click(calendarInicio);
        u.hardWait(2);
        u.highLight(selectMes1);
        exist = selectOption (mes, selectMes1);
        u.highLight(selectAnio1);
        exist = selectOptionValue (anio, selectAnio1);
       // u.click(dias);
        String DiaSelect1 = String.format(seldia, diaSel1);
        u.highLight(By.xpath(DiaSelect1));
		testCase.addScreenShotCurrentStep(u, "clickDia");
		u.unhighLight(By.xpath(DiaSelect1));
		u.click(By.xpath(DiaSelect1));
    */  u.highLight(calendarFin);
        u.click(calendarFin);
        u.hardWait(2);
        u.highLight(selectMes2);
        exist = selectOption (mes, selectMes2);
        u.highLight(selectAnio2);
        exist = selectOptionValue (anio, selectAnio2);
       // u.click(dias);
        String diasSel = String.format(seldia,diaSel1);
        String DiaSelect2 = String.format(seldia, diaSel1);
        u.highLight(By.xpath(DiaSelect2));
		testCase.addScreenShotCurrentStep(u, "clickDia");
		u.unhighLight(By.xpath(DiaSelect2));
		u.click(By.xpath(DiaSelect2));
		
		return exist;
 	}
 	
 	
 	
 	
	

	

	/*
	 * Metodos extra
	 *****************************************************************************************/
	public Boolean selectOption(By select, By opciones, String opcion) {
		Boolean existOption = false;
		// WebElement element = me.explicitWaitToBeClickable(select, 30);
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(select));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(opciones);

		for (WebElement option : allOptions) {
			// System.out.print("-"+option.getText()+"-"+opcion+"-\n");
			if (option.getText().equals(opcion)) {// busca la opcion que se pasa por parametro
				u.hardWait(3);
				option.click(); // da clic a esa opcion
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		u.highLight(select);
		testCase.addScreenShotCurrentStep(u, "selectOption");
		u.unhighLight(select);
		return existOption;
	}

	public void typingInput(By by, String dato) {
		u.sendKeys(by, dato);
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "typingInput");
		u.unhighLight(by);
	}

	public void clickBoton(By by) {
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "clickBoton");
		u.unhighLight(by);
		u.click(by);
	}

	public void clickopcion(By by) {
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "clickopcion");
		u.unhighLight(by);
		u.click(by);
	}

	
	/**
	 * Metodo para validar que aparezca el mensaje <msg>
	 * 
	 * @param string
	 * @return retorna true si se muestra el mensaje o false en caso contrario
	 * @author Jorge Ruiz
	 */
/*	public Boolean validarMensaje(String msg) {
		Boolean exist = false;
		String mensaje = "";

		if (u.isDisplayed(xmlMensaje)) {
			me.scrollToElement(xmlMensaje);
			mensaje = u.getText(xmlMensaje).trim();
			u.highLight(xmlMensaje);
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
			u.unhighLight(xmlMensaje);
			if (mensaje.equals(msg)) {
				exist = true;
			}

		} else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
		}

		return exist;
	}
*/	
	/*metodo para seleccionar opciones de Tipo y cliente
	/*public Boolean selectOption(By select, String tipo, String opcion) { 
				Boolean existOption = false;
				//WebElement element =  me.explicitWaitToBeClickable(select, 30);
				WebDriverWait wait = new WebDriverWait(u.getDriver(), 30);
				WebElement element = wait.until(ExpectedConditions.elementToBeClickable(select));
				u.hardWait(2);
				element.click();
				String xpath = "";
				if(tipo == "Cliente")
					xpath = xmlClientOp + "/li[text() = '" + opcion + "']";
				else
					xpath = xmlTypeOp + "/li[text() = '" + opcion + "']";
				//System.out.println(xpath);
				By op = By.xpath(xpath);
				u.hardWait(3);
				u.click(op);
				
				u.highLight(select);
				testCase.addScreenShotCurrentStep(u, "selectOption");
				u.unhighLight(select);
				return existOption;
			}
	 public void typingInput(By by, String dato) {
			u.sendKeys(by, dato);
			u.highLight(by);
			testCase.addScreenShotCurrentStep(u, "typingInput");
			u.unhighLight(by);
		}
	 
	 public void clickBoton(By by) {
			u.highLight(by);
			testCase.addScreenShotCurrentStep(u, "clickBoton");
			u.unhighLight(by);
			u.click(by);
		}*/
	
	/**
	 * Metodo para subrayar, tomar captura y dar click en xpath
	 * @param xpath
	 * @return 
	 * @author Andrea Cano
	 */
	public void clickByXpath(String xpath) {
		u.highLight(By.xpath(xpath));
		testCase.addScreenShotCurrentStep(u, "clickopcion");
		u.unhighLight(By.xpath(xpath));
		u.click(By.xpath(xpath));
	}
	
	/**
	 * Metodo para validar los headers de la tabla en excel
	 * @param string
	 * @return true si la informacion en el xlsx corresponde a la consulta realizada, false en caso contrario
	 * @author Andrea Cano 
	 */
	public Boolean validarHeadersTabla()
	{ 
		boolean resultado = false;
		u.scrollToElement(xmlDivTable);
		int cont = 0;
		String[] val = {"No.", "Folio","Fecha","Plaza","Tienda","Caja","Afiliaci�n","Tarjeta","Emisor","Monto","Autorizaci�n","Reversado","Estatus","Error","Casa Valera"};
		ArrayList<String> values = new ArrayList<String>(Arrays.asList(val));
		int i = 1;
		for(String value : values)
		{
			String text = u.getDriver().findElement(By.xpath(String.format(xmltableHead, String.valueOf(i)))).getText().trim();
			if(!(text.equals(values.get(i-1)))) 
				return true; 
				resultado = true;
			System.out.println("Si coinciden los valores " + text + " " + values.get(i-1));
			assertTrue(resultado, "Error: la Validacion no es Correcta");
			//testCase.addBoldTextEvidenceCurrentStep(" Validacion es Correcta");
			i++;
		}
		System.out.println("Resultado " + resultado);
		return resultado;
	}
}
