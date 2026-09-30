package WEB.monitorObligacionesFiscales.om;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Lideres{

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	
	/*
	 * Localizadores Menu Monitor de Obligaciones Fiscales
	 ********************************************************************************************/
	By OpcionLideres = By.xpath("//*[text()='L�deres']");

	//Opciones menu
	By xmlLideresBusqueda = By.xpath("//*[text()='B�squeda de L�deres']");
	//Fin opciones menu
	
	//Dentro opcion Lideres-Busqueda de Lideres
	By xmlLideresNombre = By.xpath("//*[@id='form1:txtNombre']"); 
	By xmlLideresApellidoP = By.xpath("//*[@id='form1:txtApellidoP']");
	By xmlLideresApellidoM = By.xpath("//*[@id='form1:txtApellidoM']");
	By xmlLideresRFC = By.xpath("//*[@id='form1:txtRfc']");
	By xmlLideresPlaza = By.xpath("//*[@id='form1:cbPlaza']");
	By xmlLideresPlazaOption = By.xpath("//*[@id='form1:cbPlaza_panel']/ul/li"); 
	By xmlLideresBuscar = By.xpath("//*[@id='form1:btnBuscar']");
	By xmlLideresTabla = By.xpath("//*[@id='form1:tablaLideres_data']");
	By xmlLideresLiderList = By.xpath("//*[@id='form1:tablaLideres_data']/tr");

	By xmlLideresDGeneralesPage= By.xpath("//*[text()='Datos Generales']"); 
	By xmlLideresCURP = By.xpath("//*[@id='form1:j_idt201:txtCurp']");
	By xmlLideresFolioFactura = By.xpath("//*[@id='form1:j_idt201:txtFolioUF']");

	By xmlLideresDFiscalPage= By.xpath("//*[text()='Direcci�n Fiscal']"); 
	By xmlLideresEstado = By.xpath("//*[@id='form1:j_idt201:cbEstado']");
	By xmlLideresEstadoOption = By.xpath("//*[@id='form1:j_idt201:cbEstado_panel']/ul/li"); 
	By xmlLideresCiudad = By.xpath("//*[@id='form1:j_idt201:cbCiudad']");
	By xmlLideresCiudadOption = By.xpath("//*[@id='form1:j_idt201:cbCiudad_panel']/ul/li"); 
	By xmlLideresColonia = By.xpath("//*[@id='form1:j_idt201:cbColonia']");
	By xmlLideresColoniaOption = By.xpath("//*[@id='form1:j_idt201:cbColonia_panel']/ul/li"); 
	By xmlLideresCalle = By.xpath("//*[@id='form1:j_idt201:txtCalle']");
	By xmlLideresNoInterior = By.xpath("//*[@id='form1:j_idt201:txtNumE']");
	By xmlLideresNoExterior = By.xpath("//*[@id='form1:j_idt201:txtNumI']");
	By xmlLideresEntreCalle = By.xpath("//*[@id='form1:j_idt201:txtEntre']");
	By xmlLideresY_ = By.xpath("//*[@id='form1:j_idt201:txtY']");
	
	By xmlLideresRSatPage= By.xpath("//*[text()='Registro SAT']"); 
	By xmlLideresCIEC = By.xpath("//*[@id='form1:j_idt201:txtClaveCiec']");
	By xmlLideresContrasena = By.xpath("//*[@id='form1:j_idt201:txtContrasena']");
	
	By xmlLideresRIMSSPage= By.xpath("//*[text()='Registro IMSS']"); 
	By xmlLideresRPatronal= By.xpath("//*[@id='form1:j_idt201:txtRegistroP']");
	By xmlLideresUsuario = By.xpath("//*[@id='form1:j_idt201:txtUsuarioIMSS']");
	By xmlLideresContrasenaIMSS = By.xpath("//*[@id='form1:j_idt201:txtContrasenaIMSS']");
	
	By xmlLideresActualizar = By.xpath("//*[@id='form1:btnActualizar']");
	//Fin dentro opcion Lideres-Busqueda de Lideres

	/*
	 * Constructor
	 ********************************************************************************************/
	public Lideres(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/**
	 * Metodo para dar click en opcion lideres
	 * @author Jorge Ruiz
	 */
	public void clickLideres() 
	{
		u.highLight(OpcionLideres);
		testCase.addScreenShotCurrentStep(u, "Tablas");
		u.unhighLight(OpcionLideres);
		u.mouseOver(OpcionLideres);
		testCase.addScreenShotCurrentStep(u, "clickOpcionTablas");
		clickOpcionBusqueda();
		u.hardWait(3);
	}
	
	/**
	 * Metodo para dar click en opcion Lideres Busqueda
	 * @author Jorge Ruiz
	 */
	public void clickOpcionBusqueda() 
	{	
		u.highLight(xmlLideresBusqueda);
		testCase.addScreenShotCurrentStep(u, "Tarifas");
		u.click(xmlLideresBusqueda);
	}
	
	/**
	 * Metodo para ingresar los datos de busqueda del Lider
	 * @param nombre,AP,AM,RFC,Plaza
	 * @return void
	 * @author Jorge Ruiz
	 */
	public void ingresarDatosLider(String nombre,String AP, String AM, String RFC, String Plaza) 
	{
		if(nombre!="")
			u.sendKeys(xmlLideresNombre,nombre);
		if(AP!="")
			u.sendKeys(xmlLideresApellidoP,AP);
		if(AM!="")
			u.sendKeys(xmlLideresApellidoM,AM);
		if(RFC!="")
			u.sendKeys(xmlLideresRFC,RFC);
		if(Plaza!="")
			selectLideres(xmlLideresPlaza,xmlLideresPlazaOption,Plaza);
		
		cutAndPasteFrom(xmlLideresNombre,"Nombre");
		cutAndPasteFrom(xmlLideresApellidoP,"ApellidoPaterno");
		cutAndPasteFrom(xmlLideresApellidoM,"ApellidoMaterno");
		cutAndPasteFrom(xmlLideresRFC,"RFC");
		
		u.click(xmlLideresBuscar);
		u.hardWait(3);
	}
	
	/**
	 * Metodo para seleccionar valores en select
	 * @param By, option
	 * @return void
	 * @author Jorge Ruiz
	 */
	public Boolean selectLideres(By by, By byoption,String option)
	{
		u.click(by);
		u.hardWait(1);
		List<WebElement> options = u.getDriver().findElements(byoption);
		int i = 0;
		for(WebElement opt:options)
		{
			String text = opt.getText();
			//System.out.println(text + " _ " + option);
			if(text.equals(option))
			{
				opt.click();
				return true;
			}
			i++;
		}
		return false;
	}
	
	/**
	 * Metodo para seleccionar el lider buscado
	 * @param numero
	 * @return void
	 * @author Jorge Ruiz
	 */
	public void selectLideresTabla(int numero)
	{
		u.click(xmlLideresPlaza);
		u.hardWait(1);
		List<WebElement> plazas = u.getDriver().findElements(xmlLideresLiderList);
		int i = 1;
		for(WebElement plaza:plazas)
		{
			String text = plaza.getText();
			if(i == numero)
			{
				plaza.click();
				break;
			}
			i++;
		}
	}
	
	
	public void llenarDatosGenerales(String CURP, String FolioFactura)
	{
		u.click(xmlLideresDGeneralesPage);
		/*if(CURP!="")
			u.sendKeys(xmlLideresCURP, CURP);
		if(FolioFactura!="")
			u.sendKeys(xmlLideresFolioFactura, FolioFactura);*/
		cutAndPasteFrom(xmlLideresCURP, "CURP");
		cutAndPasteFrom(xmlLideresFolioFactura, "FolioFactura");
		u.scrollToElement(xmlLideresActualizar);
		u.hardWait(3);
		u.scrollToElement(xmlLideresActualizar);
		u.click(xmlLideresActualizar);
	}
	
	public void llenarDireccionFiscal(String Estado, String Ciudad, String Colonia, String Calle, String NExterior, String NInterior, String ECalle, String Y)
	{
		u.click(xmlLideresDFiscalPage);
		/*if(Estado!="")
			selectLideres(xmlLideresEstado,xmlLideresEstadoOption,Estado);
		if(Ciudad!="")
			selectLideres(xmlLideresCiudad,xmlLideresCiudadOption, Ciudad);
		if(Colonia!="")
			selectLideres(xmlLideresColonia,xmlLideresColoniaOption, Colonia);
		if(Calle!="")
		{
			u.sendKeys(xmlLideresCalle, Calle);
		}
		if(NExterior!="")
		{
			u.sendKeys(xmlLideresNoExterior, NExterior);
		}
		if(NInterior!="")
		{
			u.sendKeys(xmlLideresNoInterior, NInterior);
		}
		if(ECalle!="")
		{
			u.sendKeys(xmlLideresEntreCalle, ECalle);
		}
		if(Y!="")
		{
			u.sendKeys(xmlLideresY_, Y);
		}*/
		cutAndPasteFrom(xmlLideresCalle,"Calle");
		cutAndPasteFrom(xmlLideresNoExterior,"NumeroExterior");
		cutAndPasteFrom(xmlLideresNoInterior,"NumeroInterior");
		cutAndPasteFrom(xmlLideresEntreCalle,"EntreCalle");
		cutAndPasteFrom(xmlLideresY_,"YCalle");
		u.scrollToElement(xmlLideresActualizar);
		u.hardWait(3);
		u.scrollToElement(xmlLideresActualizar);
		u.click(xmlLideresActualizar);
	}
	
	public void llenarRegistroSat(String CIEC, String Contrasena)
	{
		u.click(xmlLideresRSatPage);
		/*if(CIEC!="")
		{
			u.sendKeys(xmlLideresCIEC,CIEC);
		}
		if(Contrasena!="")
		{
			u.sendKeys(xmlLideresContrasena, Contrasena);
		}*/
		cutAndPasteFrom(xmlLideresContrasena,"Contrase�aSAT");
		cutAndPasteFrom(xmlLideresCIEC,"CIEC_SAT");
		u.scrollToElement(xmlLideresActualizar);
		u.hardWait(3);
		u.scrollToElement(xmlLideresActualizar);
		u.click(xmlLideresActualizar);
	}
	
	public void llenarRegistroIMSS(String RPatronal, String Usuario, String Contrasena)
	{
		u.click(xmlLideresRIMSSPage);
		/*if(RPatronal!="")
		{
			u.sendKeys(xmlLideresRPatronal, RPatronal);
		}
		if(Usuario!="")
		{
			u.sendKeys(xmlLideresUsuario, Usuario);
		}
		if(Contrasena!="")
		{
			u.sendKeys(xmlLideresContrasenaIMSS, Contrasena);
		}*/
		cutAndPasteFrom(xmlLideresRPatronal,"RegistroPatronal");
		cutAndPasteFrom(xmlLideresUsuario,"Usuario");
		cutAndPasteFrom(xmlLideresContrasenaIMSS,"Contrase�aIMSS");
		u.scrollToElement(xmlLideresActualizar);
		u.hardWait(3);
		u.scrollToElement(xmlLideresActualizar);
		u.click(xmlLideresActualizar);
	}
	
	
	/**
	 * Metodo para validar que aparezca el mensaje <msg> 
	 * @param string
	 * @return retorna true si se muestra el mensaje o false en caso contrario
	 * @author Jorge Ruiz
	 */
	 public Boolean validarMensaje(By xmlMensaje,String msg) {
		Boolean exist = false;
		String mensaje = "";
		
		if(u.isDisplayed(xmlMensaje)) {
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
	
	 /**
		* Metodo para cortar informacion de un elemento y pegarlo en el mismo elemento
		* @param from elemento a trabajar, step paso en el que se esta trabajando
		* @return NA
		* @author Jorge Ruiz
		*/
		 
		public void cutAndPasteFrom(By from,String step)
		{
			u.scrollToElement(from);
			Actions act = new Actions(u.getDriver());
			act.moveToElement(u.getDriver().findElement(from)).doubleClick().build().perform();
			testCase.addScreenShotCurrentStep(u,step+"Inicio");
			u.getDriver().findElement(from).sendKeys(Keys.chord(Keys.CONTROL,"x"));
			testCase.addScreenShotCurrentStep(u,step+"Cut");
			u.getDriver().findElement(from).sendKeys(Keys.chord(Keys.CONTROL,"v"));
			testCase.addScreenShotCurrentStep(u,step+"Paste");
		}
	
}
