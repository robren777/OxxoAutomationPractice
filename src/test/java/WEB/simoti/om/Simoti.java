package WEB.simoti.om;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Simoti {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	/**
	 * Localizadores y metodos de portal SIMOTI
	 * 
	 ******************************************************************************************/
	/*
	 * Localizadores del menu principal de SIMOTI
	 */

	By TransDatosCentral = By.xpath("//*[text()='Transmisión de Datos Central']");
	By IntegridadDatosCentral = By.xpath("//*[text()='Integridad de Datos Central']");
	By OrdenesComprasCentral = By.xpath("//*[text()='Órdenes de Compra Central']");
	By FolioPagoCentral = By.xpath("//*[text()='Folio de Pago Central']");

	/* Transacciones electronicas central y submenis */
	By TransElecCentral = By.xpath("//*[text()='Transacciones Electrónicas Central']");
	By EDTransacciones = By.xpath("//*[@id=\"ddsubmenu20107\"]/li[10]/a");
	// div[@class='ui-selectonemenu-trigger ui-state-default ui-corner-right']

	/* Envio de dinero Transacciones (opciones) */
	By TIPO = By.xpath("//label[@id='formRepEnvDineroTransacciones:cmbTipo_label']");
	By TIPOENVIO = By.xpath("//li[@data-label='ENV Envío']");

	By cliente = By.xpath("//label[@id='formRepEnvDineroTransacciones:cmbCliente_label']");
	By beneficiario = By.xpath("//li[@data-label='B Beneficiario']");
	By RRemitente = By.xpath("//li[@data-label='R Remitente']");

	By IFE = By.xpath("//input[@id='formRepEnvDineroTransacciones:txtIfe']");
	By nTransf = By.xpath("//input[@id='formRepEnvDineroTransacciones:txtNumTransaction']");
	By telefono = By.xpath("//input[@id='formRepEnvDineroTransacciones:txtCelTel']");

	By botonCal1 = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:cldfechade\"]/button");
	By botonCal2 = By.xpath("//*[@id=\"formRepEnvDineroTransacciones:cldfechaA\"]/button");

	By cal1 = By.xpath("//*[@id=\"ui-datepicker-div\"]");

	By mes = By.xpath("//select[@class='ui-datepicker-month']");

	By consultar = By.xpath("//a[@id=\"formRepEnvDineroTransacciones:btnBuscar\"]");

	By SinResultados = By.xpath("//td[text()='Sin resultados']");

	By EDineroReportes = By.xpath("//*[text()='Reportes'][@rel='subsubmenu1']");
	By EDineroMontos = By.xpath("//a[contains(@href,'PortalRemesas-web/pages/enviosDeDinero')][text()='Montos']");

	//// a[contains(@href,'PortalRemesas-web/pages/enviosDeDinero')][text()='Montos']
	/*
	 * Localizadores login portal CNT Captura de Datos Menu >Formato y Entorno
	 */
	By FyE = By.xpath("//*[@target=\"framedatos\"][4]");
	By TamTienda = By.xpath("//select[@name=\"p_lid_tamanio_tienda\"]");
	By frame = By.xpath("//frame[contains(@src,'app')]");
	By framed = By.xpath("//frame[contains(@src,'/tiendas')][@name=\"framedatos\"]");// frameoper

	/**
	 * Metodo para validar que existan:Transmisión de Datos, Integridad de Datos,
	 * Órdenes de Compra, Folio de Pago, Transacciones Electrónicas
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return boolean exist
	 */
	public Boolean ValidaR_TD_ID_OC_FP_TE() {
		boolean exist = false;
		u.getDriver().manage().window().maximize();

		try {

			u.highLight(TransDatosCentral);
			u.highLight(IntegridadDatosCentral);
			u.highLight(OrdenesComprasCentral);
			u.highLight(FolioPagoCentral);
			u.highLight(TransElecCentral);
			exist = true;
		} catch (Exception e) {
			// TODO: handle exception
			exist = false;
		}
		return exist;
		// u.mouseOver(EnviosDinero);
//		u.click(EnviosDinero);
//		u.highLight(EDineroReportes);
////		testCase.addScreenShotCurrentStep(u, "EnviosDinero");
//		u.mouseOver(EDineroReportes);
////		u.click(EDineroReportes);
//     	u.highLight(EDineroMontos);
//     	u.mouseOver(EDineroMontos);
//		testCase.addScreenShotCurrentStep(u, "EDineroMontos");
//		u.click(EDineroMontos);
//		u.getDriver().switchTo().window(winHandleBefore);
//		u.waitForLoadPage();
//		testCase.addScreenShotCurrentStep(u, "Plano2");

	}

	/**
	 * Metodo para dar clic en Transacciones Electrónicas y opcion envios de
	 * dinero-transacciones
	 * 
	 * @author Baltazar Lugo
	 * @param string opcion
	 * @return
	 */
	public void Clic_trans_electronicas_EnvDinTrans() {
		Actions action = new Actions(u.getDriver());
action.moveByOffset(0, 0).perform();
		u.highLight(TransElecCentral);
//		testCase.addScreenShotCurrentStep(u, "EnviosDinero");
		u.mouseOver(TransElecCentral);
//		u.click(EDineroReportes);
		u.highLight(EDTransacciones);
		u.mouseOver(EDTransacciones);
		testCase.addScreenShotCurrentStep(u, "EDTransacciones");
		u.click(EDTransacciones);
//		u.getDriver().switchTo().window(winHandleBefore);
//		u.waitForLoadPage();
//		testCase.addScreenShotCurrentStep(u, "Plano2");

	}

	/**
	 * Metodo para rellenar tabla envio de dinero tipo beneficiario
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public void TablaEDBeneficiario(String tipo,String ife, String nTransf1, String tel, String mes1, String ano1, String dia1,
			String mes2, String ano2, String dia2) {
		System.out.print(ife);
		System.out.print(nTransf1);
		System.out.print(tel);
		System.out.print(mes1);
		System.out.print(ano1);
		System.out.print(dia1);
		System.out.print(mes2);
		System.out.print(ano2);
		System.out.print(dia2);
//		u.getDriver().switchTo().window(winHandleBefore);
//		u.waitForLoadPage();
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(TIPO);
		u.highLight(TIPO);
		u.click(TIPO);
		u.click(TIPOENVIO);
		testCase.addScreenShotCurrentStep(u, "TIPOENVIO");
		u.highLight(cliente);
		u.click(cliente);
		
		if (tipo.equals("B Beneficiario")) {
			System.out.println("entro ebenfi");	
			u.click(beneficiario);
		}

		if (tipo.equals("R Remitente")) {
			System.out.println("entro remitentre");
			u.click(RRemitente); 
		}

	
//		u.click(beneficiario);
	
		if (ife.isEmpty() == false) {
			u.highLight(IFE);
			u.click(IFE);
			u.clear(IFE);
			// u.sendKeys(IFE, ife);
		}

		if (nTransf1.isEmpty() == false) {
			u.highLight(nTransf);
			u.click(nTransf);
			u.clear(nTransf);
			// u.sendKeys(nTransf,nTransf1);
		}
		if (tel.isEmpty() == false) {
			u.highLight(telefono);
			u.click(telefono);
			u.clear(telefono);
			// u.sendKeys(telefono,tel);
		}

		MetodosExtra ME2 = new MetodosExtra(u, testCase);
		u.hardWait(6);
		u.highLight(botonCal1);
		u.click(botonCal1);
		u.hardWait(6);

		/* escoje mes */

		WebElement m = u.getDriver().findElement(By.xpath("//select[@class='ui-datepicker-month']"));
		Select s = new Select(m);
		s.selectByVisibleText(mes1);
		// s.selectByValue("6");
//
//			/* escoje año */
		WebElement m1 = u.getDriver().findElement(By.xpath("//select[@class='ui-datepicker-year']"));
		Select s1 = new Select(m1);
		s1.selectByVisibleText(ano1);

		/* escoje dia */

		u.getDriver().findElement(By.xpath("//a[text()='" + dia1 + "']")).click();

		u.hardWait(6);
		u.highLight(botonCal2);
		u.click(botonCal2);
		u.hardWait(6);

		/* escoje mes */
		WebElement m11 = u.getDriver().findElement(By.xpath("//select[@data-handler='selectMonth']"));
		Select s11 = new Select(m11);
		s11.selectByVisibleText(mes2);

		/* escoje año */
		WebElement m122 = u.getDriver().findElement(By.xpath("//select[@class='ui-datepicker-year']"));
		Select s12 = new Select(m122);
		s12.selectByVisibleText(ano2);

		u.getDriver().findElement(By.xpath("//a[text()='" + dia2 + "']")).click();

		u.hardWait(8);

		u.highLight(consultar);
		testCase.addScreenShotCurrentStep(u, "EDTransaccionesconsul");
		u.click(consultar);

//		
	}

	/**
	 * Metodo para dar transferencia y clic en consultar
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public void TablaTransf(String nTransf1) {
		u.hardWait(8);
		System.out.print(nTransf1);
		
//		u.getDriver().switchTo().window(winHandleBefore);
//		u.waitForLoadPage();
		

		if (nTransf1.isEmpty() == false) {
			u.highLight(nTransf);
			u.click(nTransf);
			u.clear(nTransf);
			// u.sendKeys(nTransf,nTransf1);
		}
		
		

		u.highLight(consultar);
		testCase.addScreenShotCurrentStep(u, "EDTransaccionesconsussl");
		u.click(consultar);

//		
	}
	/**
	 * Metodo para validar que se desplejaron datos
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public boolean DatosTabla() {

		u.hardWait(7);
//		u.highLight(SinResultados);
		MetodosExtra mo = new MetodosExtra(u, testCase);
		Boolean existe = mo.existElement(SinResultados);
		return existe;
	}
	/**
	 * Metodo para validar que se descargo el excel de transacciones
	 * @author Maria Dueñas
	 * @param N/A
	 * @return
	 */
	public Boolean validarDescarga() {
		Boolean exist = false;
		
		String pathYourSystem = System.getProperty("user.home") + "\\Downloads\\reportPedido.xlsx";
		File f = new File(pathYourSystem);
		
		if (f.exists()) {
			exist = true;
		}
		
		return exist;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Metodo para dar clic en alerts despues de notificacion
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return
	 */
	public boolean NotificacionAlert() {
		boolean existenoti = true;
		u.hardWait(4);
//	//	u.getDriver().switchTo().frame("framemenu")
////		WebDriverWait w = new WebDriverWait(u.getDriver(),20);

		int x = 0;
//		
//		WebDriverWait w = new WebDriverWait(u.getDriver(),20);
		do {
			u.hardWait(10);
			if (isAlertPresent() == false) {
				System.out.println("Alert not exists");
			} else {
				if (isAlertPresent() == true) {
					System.out.println("Alert exists");
					testCase.addScreenShotCurrentStep("foto3");
					u.getDriver().switchTo().alert().accept();
				}
			}
			x++;

//	for (int i = 0; i < 9; i++) {
//			if ((ExpectedConditions.alertIsPresent()) != null) {
//				testCase.addScreenShotCurrentStep("foto3");
//				u.getDriver().switchTo().alert().accept();
//				i = i + 1;
//				System.out.println("for"+i);
//				
//			}
//		}
////			x=x+1;
//	System.out.print("x: "+x);

		} while (x <= 6);

		u.hardWait(15);
//existenoti=me.existElement(notificacion);

		return existenoti;

//testCase.addScreenShotCurrentStep("foto3");

//		u.click(Plano);
//		u.getDriver().switchTo().window(winHandleBefore);
//		u.waitForLoadPage();
//		testCase.addScreenShotCurrentStep(u, "Plano2");

	}

	public boolean isAlertPresent() {
		try {
			u.getDriver().switchTo().alert();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	} // isAlertPresent()

	/**
	 * Constructor
	 ******************************************************************************************/

	public Simoti(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);

	}

	/*
	 * METODOS - Acceso correcto a Inventario de espacios Publicitarios V2
	 ******************************************************************************************/

}