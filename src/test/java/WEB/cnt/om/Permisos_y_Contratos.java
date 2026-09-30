package WEB.cnt.om;

import java.text.DecimalFormat;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.om.MetodosExtra;

//import com.google.inject.Key;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Permisos_y_Contratos {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	private WebDriverWait wait;

	/**
	 * Localizadores y Principal o Home del portal Catalogo
	 * Nacional de Tiendas
	 ******************************************************************************************/
	/*
	 * Localizadores login portal CNT Permisos y Contratos
	 */

	By TiendaE = By.xpath("//*[text()=\"Seleccionar Tienda\"]");
	// By DatosOp= By.xpath("//*[@target=\"framedatos\"][2]");

	/*
	 * Localizadores login portal CNT Captura de Datos Menu >Permisos y Contratos
	 */
	// By PyC = By.xpath(" //body/center/a[4]");

	By PyC = By.xpath("//*[@target=\"framedatos\"][3]");
	By Cheque = By.xpath("//input[@value=\"CHEQUE\"]");
	By Dolares = By.xpath("//input[@value=\"DOLARES\"]");
	By Dolares1 = By.xpath("//input[@class=\"SelectReadOnly\"][@value=\"DOLARES\"]");
	By Efectivo = By.xpath("//input[@value=\"EFECTIVO\"]");
	By TDD = By.xpath("//input[@value=\"TDD\"]");
	By TDC = By.xpath("//input[@value=\"TDC\"]");
	By Vales = By.xpath("//input[@value=\"VALES_DESPENSA\"]");

	By Cambiario = By.xpath("//input[@name=\"p_lid_corrc\"]");

	By BtnBuscar = By.xpath("//input[@value=\"buscar\"]");
	By IdPunto = By.xpath("//select[@name=\"p_id_punto\"]");
	By CLD = By.xpath("//input[@name=\"p_codigo_area\"]");
	By Telefono = By.xpath("//input[@name=\"p_telefono\"]");

	By frame = By.xpath("//frame[contains(@src,'app')]");
	By framed = By.xpath("//frame[contains(@src,'/tiendas')][@name=\"framedatos\"]");// frameoper

	By CONFIGCUENTADOLARES = By.xpath("//*[(text()=' Configuraci�n cuenta D�lares')]");
	By p_digito_verif = By.xpath("//input[@name='p_digito_verif']");
	By p_cuenta_convenio = By.xpath("//input[@name='p_cuenta_convenio']");
	By p_ref_bancaria = By.xpath("//input[@name='p_ref_bancaria']");
	By retroceder = By.xpath("//*[(text()='<< retroceder')]");

	By p_banco_corresponsal = By.xpath("//select[@name='p_banco_corresponsal']");

	By p_pem_vta_cer_propio = By.xpath("//select[@name=\"p_pem_vta_cer_propio\"]");

	// paths de dar cambio, saldo inicial, y monto inicial max
	By p_lid_darcambio = By.xpath("//*[@id='p_lid_darcambio']");
	By p_lid_saldo_ini = By.xpath("//input[@name='p_lid_saldo_ini']");
	By p_lid_max_ini_sal = By.xpath("//input[@id='p_lid_max_ini_sal']");

	/*
	 * Localizadores Formulario Datos de OP
	 *****************************************************************************************/

	By cot = By.xpath("//select[@name=\"p_lid_caracteristica_operacion\"]");
	By BtnActualizar = By.xpath("//input[@value=\"Actualizar\"]");

	By xmlContainer = By.xpath("//*[@id=\"content-nav\"]");
	By xmlPEP = By.xpath("//*[@id=\"tituloInicio\"]");

	/*
	 * Localizadores formulario permisos y contratos
	 *****************************************************************************************/
	By xmlConfiguracionCuentaPesos = By.xpath("//html/body/center[3]/a[5]");

	By xmlFrame2 = By.xpath("//frame[contains(@src,'/tiendas/html/blank.html')][@name=\"framedatos\"]");
	By xmldatosOpe = By.xpath("//*[@target=\"framedatos\"][2]");

	By xmlCuentaPesos = By.xpath("//*[text()=' Configuración cuenta Pesos']");

	By p_digito_verif_Pesos = By.xpath("//input[@name='p_digito_verif_mxp']");

	By p_ref_bancaria_Pesos = By.xpath("//input[@name='p_ref_bancaria_mxp']");
	
	By campo_Banco_Corresponsal_USD = By.xpath("//select[@name='p_banco_corresponsal']");

	/**
	 * Metodo para seleccionar el Frame correcto y dar clic a datos Generales
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return N/A
	 */
	public void PermCon() {
//		u.getDriver().switchTo().frame("framemenu");
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.highLight(frame);
		u.getDriver().switchTo().frame("framemenu");
		u.highLight(PyC);
		testCase.addScreenShotCurrentStep(u, "DatosGen");
		u.click(PyC);

		u.getDriver().switchTo().window(winHandleBefore);
		u.waitForLoadPage();

	}

	/**
	 * Metodo para seleccionar check box de permisos y contratos> formas de pago
	 * 
	 * @author Baltazar Lugo
	 * @param String cheque, String dolares, String efectivo,String TDD1,String
	 *               TDC1,String vales
	 * @return N/A
	 */
	public void EligecheckBox(String cheque, String dolares, String efectivo, String TDD1, String TDC1, String vales) {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		System.out.print("cheque" + cheque);
		System.out.print("dolares" + dolares);
		if (cheque.equals(si)) {
			u.click(Cheque);
		}
		if (dolares.equals(si)) {
			u.click(Dolares);
		}
		if (efectivo.equals(si)) {
			u.click(Efectivo);
		}
		if (TDD1.equals(si)) {
			u.click(TDD);
		}
		if (TDC1.equals(si)) {
			u.click(TDC);
		}
		if (vales.equals(si)) {
			u.click(Vales);
		}
		testCase.addScreenShotCurrentStep(u, "Formadepago");
	}

	public void clickBotonActualizar() {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(BtnActualizar);
		u.highLight(BtnActualizar);
		testCase.addScreenShotCurrentStep(u, "DatosOpAC");
		u.click(BtnActualizar);
//		u.waitForLoadPage();

		u.hardWait(10);
//		testCase.addScreenShotCurrentStep(u, "Respuesta_POP_PERMISOS_Y_CONTRATOS_ha_sido_grabada");

	}

	/**
	 * Metodo para validar que se haya presentado alert de guardado
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return msjexist (true si se presento, false si no)
	 */
	public boolean validacion() {
		wait.until(ExpectedConditions.alertIsPresent());
		String ventanaE = u.getDriver().switchTo().alert().getText();
		System.out.println("Texto en mensaje de alerta" + ventanaE);
		boolean msjexist = false;
		String str2 = "La informaci�n de \"PERMISOS Y CONTRATOS\" ha sido grabada.";
		if (ventanaE.equals(str2)) {
			testCase.addScreenShotCurrentStep("RespuestaPOP");
			msjexist = true;
			u.getDriver().switchTo().alert().accept();
		} else {
			System.out.println("Fallo Datos de Operacion Grabados");
			msjexist = false;
		}
		return msjexist;
	}

	/**
	 * Metodo para validar que se haya presentado alert de El campo Monto Máximo de
	 * Saldo Inicial debe ser numérico y mayor a cero, por favor verifique
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return msjexist (true si se presento, false si no)
	 */
	public boolean validacion4() {
		String ventanaE = u.getDriver().switchTo().alert().getText();
		System.out.println("Texto en mensaje de alerta" + ventanaE);
		boolean msjexist = false;
		String str2 = "El campo Monto M�ximo de Saldo Inicial debe ser num�rico y mayor a cero, por favor verifique";
		if (ventanaE.equals(str2)) {
			testCase.addScreenShotCurrentStep("RespuestaPOP");
			msjexist = true;
			u.getDriver().switchTo().alert().accept();
		} else {
			System.out.println("Fallo Datos de Operacion Grabados");
			msjexist = false;
		}
		return msjexist;
	}

	/**
	 * Metodo para validar que se haya presentado alert de Ha habilitado el campo
	 * "Saldo inicial" pero no ha capturado un valor para el campo "Monto Máximo de
	 * Saldo Inicial", por favor verifique
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return msjexist (true si se presento, false si no)
	 */
	public boolean validacion3() {
		String ventanaE = u.getDriver().switchTo().alert().getText();
		System.out.println("Texto en mensaje de alerta" + ventanaE);
		boolean msjexist = false;
		String str2 = "Ha habilitado el campo \"Saldo inicial\" pero no ha capturado un valor para el campo \"Monto M�ximo de Saldo Inicial\", por favor verifique";
		if (ventanaE.equals(str2)) {
			testCase.addScreenShotCurrentStep("RespuestaPOP");
			msjexist = true;
			u.getDriver().switchTo().alert().accept();
		} else {
			System.out.println("Fallo Datos de Operacion Grabados");
			msjexist = false;
		}
		return msjexist;
	}

	/**
	 * Metodo para validar que se haya presentado alert de "La tienda no puede
	 * activarse como corresponsal cambiario, ya que no cumple con las reglas de
	 * negocio
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return msjexist (true si se presento, false si no)
	 */
	public boolean validacion1() {
		String ventanaE = u.getDriver().switchTo().alert().getText();
		System.out.println("Texto en mensaje de alerta" + ventanaE);
		boolean msjexist = false;
		String str2 = "La tienda no pertenece a un municipio autorizado para captar d�lares, no podr� activar la tienda como corresponsal";
		if (ventanaE.equals(str2)) {
			testCase.addScreenShotCurrentStep("RespuestaPOP");
			msjexist = true;
			u.getDriver().switchTo().alert().accept();
		} else {
			System.out.println("Fallo Datos de Operacion Grabados");
			msjexist = false;
		}
		return msjexist;
	}

	/**
	 * Metodo para validar que se haya presentado alert de guardado
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return msjexist (true si se presento, false si no)
	 */
	public boolean validacionFormatoyE() {
		String ventanaE = u.getDriver().switchTo().alert().getText();
		System.out.println("Texto en mensaje de alerta" + ventanaE);
		boolean msjexist = false;
		String str2 = "La informaci�n de \"FORMATO Y ENTORNO\" ha sido grabada.";
		if (ventanaE.equals(str2)) {
			testCase.addScreenShotCurrentStep("RespuestaPOP");
			msjexist = true;
			u.getDriver().switchTo().alert().accept();
		} else {
			System.out.println("Fallo Datos de Operacion Grabados");
			msjexist = false;
		}
		return msjexist;
	}

	/**
	 * Metodo para verificar si esta el check box CorresponsalCambiario
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return N/A
	 */
	public boolean EligecheckBoxCorresponsalCambiario() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean enable = false;
		//me.enableElementJs(Cambiario);
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");

		if (u.getDriver().findElement(Cambiario).isEnabled()) {
			enable = true;
			u.highLight(Cambiario);
			// testCase.addScreenShotCurrentStep(u, "CORREn1");
			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y esta habilitado");

			if (u.getDriver().findElement(Cambiario).isSelected()) {
				testCase.addScreenShotCurrentStep(u, "CORRE11");
				u.click(Cambiario);
				u.click(Cambiario);
				testCase.addScreenShotCurrentStep(u, "CORREn21");
			} else {
				testCase.addScreenShotCurrentStep(u, "CORREn11");
				u.click(Cambiario);
				testCase.addScreenShotCurrentStep(u, "CORREn113");
			}
//			u.clear(Cambiario);
		} else {
			enable = false;
			u.highLight(Cambiario);
			testCase.addScreenShotCurrentStep(u, "CORREn2");
			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y No esta habilitado");
			System.out.println("Checkbox existe y No esta habilitado");
		}

		return enable;
	}

	/**
	 * Metodo para seleccionar check box de permisos y contratos> formas de pago
	 * 
	 * @author Baltazar Lugo
	 * @param String cheque, String dolares, String efectivo,String TDD1,String
	 *               TDC1,String vales
	 * @return N/A
	 */
	public boolean checkBoxDLLS() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");

		boolean enable = false;

		boolean exists = u.getDriver().findElement(Dolares1).isSelected();
		if (exists == true) {

//			.size() != 0
			testCase.addScreenShotCurrentStep(u, "CORREn1");
			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y esta Seleccionado");
		} else {

			u.highLight(Dolares);
			testCase.addScreenShotCurrentStep(u, "CORREn2");
			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y NO esta Seleccionado");
		}

		return exists;

	}

	/**
	 * Metodo para dar clic a CorresponsalCambiario
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return N/A
	 */
	public void clickcheckBoxCorresponsalCambiario() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean enable = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");

		if (u.getDriver().findElement(Cambiario).isEnabled()) {
			testCase.addTextEvidenceCurrentStep("Checkbox existe y esta habilitado");
			if (u.getDriver().findElement(Cambiario).isSelected()) {
				enable = true;
				u.highLight(Cambiario);
				testCase.addScreenShotCurrentStep(u, "CORREn1");
//			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y esta habilitado");
			} else {
				enable = false;
				u.highLight(Cambiario);
				u.click(Cambiario);
				testCase.addScreenShotCurrentStep(u, "CORREn2");
//			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y No esta habilitado");
			}
		} else {
			testCase.addScreenShotCurrentStep(u, "CORREn222");
			testCase.addTextEvidenceCurrentStep("Checkbox existe y No esta habilitado");
		}

	}

	/**
	 * Metodo para dar click en el boton SI de mensaje activación de tienda corresponsal
	 * 
	 * @return Retorna true si dio el click correctamente o false en caso contrario
	 * @author Maria Dueï¿½as
	 */
	public Boolean clickSiActivarTiendaCorresponsal() {
		Boolean correct = false;
		// PENDIENTE POR FALTA DE USUARIO TESORERIA
		return correct;
	}

	/**
	 * Metodo para validar que no aparezca el boton ConfiguraciÃ³n cuenta DÃ³lares
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return boolean aparece
	 */
	public boolean validarConfigCuentaDolares() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
//		u.highLight(CONFIGCUENTADOLARES);
		testCase.addScreenShotCurrentStep(u, "SCREENVERIFICADOdddddR");
		Vacio = me.existElement(CONFIGCUENTADOLARES);
		System.out.print(Vacio);

		return Vacio;
	}

	/**
	 * Metodo para validar Codigo verificador y referencia retrocediendo dolares
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return N/A
	 */
	public boolean validarCodVerificadorYRef() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		u.highLight(CONFIGCUENTADOLARES);
		u.click(CONFIGCUENTADOLARES);

//		WebElement inputBox = driver.findElement(By.id("inputBoxId"));
//		String textInsideInputBox = inputBox.getAttribute("value");
//
//		// Check whether input field is blank
//		if(textInsideInputBox.isEmpty())
//		{
//		   System.out.println("Input field is empty");
//		}

		u.click(p_digito_verif);

		String texto3 = u.getDriver().findElement(p_digito_verif).getAttribute("value");
		System.out.print("p_digito_verif: " + texto3);

		u.click(p_ref_bancaria);
		String texto4 = u.getDriver().findElement(p_ref_bancaria).getAttribute("value");
		System.out.print("p_ref_bancaria: " + texto4);
		u.hardWait(2);

		// Check whether input field is blank
		if (texto3.isEmpty() && texto4.isEmpty()) {
			System.out.println("Input field is empty");

			Vacio = true;
		} else {
			Vacio = false;
			testCase.addScreenShotCurrentStep(u, "SCREENVERIFICADOR");
			testCase.addTextEvidenceCurrentStep("Digito Verificador:" + texto3);
			testCase.addTextEvidenceCurrentStep("Referencia:" + texto4);
		}
		u.click(retroceder);
		u.hardWait(2);
		u.click(retroceder);
		u.hardWait(2);
		u.click(retroceder);
		u.hardWait(2);
		u.click(retroceder);
		u.hardWait(2);

		return Vacio;
	}

	/**
	 * Metodo para extraer el Codigo verificador
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return N/A
	 */
	public String ExtraerCodVerificador() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		u.highLight(CONFIGCUENTADOLARES);
		u.click(CONFIGCUENTADOLARES);

//		WebElement inputBox = driver.findElement(By.id("inputBoxId"));
//		String textInsideInputBox = inputBox.getAttribute("value");
//
//		// Check whether input field is blank
//		if(textInsideInputBox.isEmpty())
//		{
//		   System.out.println("Input field is empty");
//		}

		u.click(p_digito_verif);

		String texto3 = u.getDriver().findElement(p_digito_verif).getAttribute("value");
		System.out.print("p_digito_verif: " + texto3);

//		u.click(p_ref_bancaria);
//		String texto4 = u.getDriver().findElement(p_ref_bancaria).getAttribute("value");
//		System.out.print("p_ref_bancaria: " + texto4);
//		u.hardWait(2);

//		// Check whether input field is blank
//		if (texto3.isEmpty() && texto4.isEmpty()) {
//			System.out.println("Input field is empty");
//
//			Vacio = true;
//		} else {
//			Vacio = false;
		testCase.addScreenShotCurrentStep(u, "SCREENVERIFICADOR");
		testCase.addTextEvidenceCurrentStep("Digito Verificador:" + texto3);
//			 testCase.addTextEvidenceCurrentStep("Referencia:"+texto4);
//		}
//		u.click(retroceder);
//		u.hardWait(2);
//		u.click(retroceder);
//		u.hardWait(2);
//		u.click(retroceder);
//		u.hardWait(2);
//		u.click(retroceder);
//		u.hardWait(2);

		return texto3;
	}

	/**
	 * Metodo para extraer el Codigo verificador sin dar click en cuenta dolares nuevamente
	 * @author Alfonso Oyervides
	 * @param NA
	 * @return N/A
	 */
	public String ExtraerCodVerificadorSinCuentaDolares() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

		u.hardWait(4);
		u.getDriver().manage().window().maximize();


		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");

		u.click(p_digito_verif);
		u.highLight(p_digito_verif);

		String texto4 = u.getDriver().findElement(p_digito_verif).getAttribute("value");
		System.out.print("p_digito_verif: " + texto4);
		testCase.addScreenShotCurrentStep(u, "SCREENVERIFICADOR");
		testCase.addTextEvidenceCurrentStep("Digito Verificador:"+texto4);
	

		return texto4;
	}
	
	
	/**
	 * Metodo para extraer la referencia bancaria sin dar click en cuenta dolares nuevamente
	 * @author Alfonso Oyervides
	 * @param NA
	 * @return N/A
	 */
	public String ExtraerReferenciaBancariaSinCuentaDolares() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

		u.hardWait(4);
		u.getDriver().manage().window().maximize();


		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");


		u.click(p_ref_bancaria);
		u.highLight(p_ref_bancaria);
		String texto5 = u.getDriver().findElement(p_ref_bancaria).getAttribute("value");
		System.out.print("p_ref_bancaria: " + texto5);
		testCase.addScreenShotCurrentStep(u, "SCREENREFERENCIA");
		testCase.addTextEvidenceCurrentStep("Referencia bancaria:"+texto5);
		return texto5;
	}

	/**
	 * Metodo para validar Codigo verificador y referencia 
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return
	 * @return N/A
	 */
	public String validarCodVerificadorYRef2() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		u.highLight(CONFIGCUENTADOLARES);
		testCase.addScreenShotCurrentStep(u, "SCREENVERIFICADOR");
		u.click(CONFIGCUENTADOLARES);

//		WebElement inputBox = driver.findElement(By.id("inputBoxId"));
//		String textInsideInputBox = inputBox.getAttribute("value");
//
//		// Check whether input field is blank
//		if(textInsideInputBox.isEmpty())
//		{
//		   System.out.println("Input field is empty");
//		}

		u.click(p_ref_bancaria);
		String texto4 = u.getDriver().findElement(p_ref_bancaria).getAttribute("value");
//		System.out.print("p_ref_bancaria: " + texto4);
		u.hardWait(2);

		// Check whether input field is blank
		testCase.addScreenShotCurrentStep(u, "SCREENVERIFICADOR222");
		testCase.addTextEvidenceCurrentStep("Referencia:" + texto4);

		return texto4;
	}

	/**
	 * Metodo para validar que se puede escribir en el campo banco corresponsal
	 * 
	 * @author Alexis Garcia
	 * @param NA
	 * @return N/A
	 */
	public boolean pantallaConfigCuentaDolares() {

		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

		u.hardWait(4);
		u.getDriver().manage().window().maximize();

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		u.highLight(CONFIGCUENTADOLARES);
		testCase.addScreenShotCurrentStep(u, "Captura");
		u.click(CONFIGCUENTADOLARES);
		// u.highLight(p_banco_corresponsal);
		// u.click(p_banco_corresponsal);

		boolean select = false;

		if (u.getDriver().findElement(p_banco_corresponsal).isEnabled()) {

			select = true;
			u.highLight(p_banco_corresponsal);
			u.click(p_banco_corresponsal);
			testCase.addScreenShotCurrentStep(u, "Captura");
			testCase.addTextEvidenceCurrentStep("el Banco corresponsal USD esta habilitado");
		} else {
			testCase.addScreenShotCurrentStep(u, "Captura");
			testCase.addTextEvidenceCurrentStep(
					"el Banco corresponsal USD NO esta habilitado por lo tanto no puede seleccionar opciones");

		}

		return select;
	}

	/**
	 * Metodo para validar que los campos de la pantalla configuracion cuenta
	 * dolares esten activos y se pueda escribir en ellos
	 * 
	 * @author Alexis Garcia
	 * @param NA
	 * @return N/A
	 */
	public boolean validarDatosCamposConfigCuentaDolares(String campo) {

		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		u.highLight(CONFIGCUENTADOLARES);
		u.click(CONFIGCUENTADOLARES);

		boolean select = false;
		switch (campo.toLowerCase()) {

		case "banco corresponsal usd":
			if (u.getDriver().findElement(p_banco_corresponsal).isEnabled()) {

				select = true;
				u.highLight(p_banco_corresponsal);
				u.click(p_banco_corresponsal);
				testCase.addTextEvidenceCurrentStep("el Banco corresponsal USD esta habilitado");
			} else {

				testCase.addTextEvidenceCurrentStep(
						"el Banco corresponsal USD NO esta habilitado por lo tanto no puede seleccionar opciones");

			}
			break;

		case "cuenta usd":
			if (u.getDriver().findElement(p_cuenta_convenio).isEnabled()) {

				select = true;
				u.highLight(p_cuenta_convenio);
				u.click(p_cuenta_convenio);
				testCase.addTextEvidenceCurrentStep("el campo Cuenta USD esta habilitado");
			} else {

				testCase.addTextEvidenceCurrentStep(
						"el campo Cuenta USD NO esta habilitado por lo tanto no puede escribir en el");

			}
			break;

		case "digito verificador":
			if (u.getDriver().findElement(p_digito_verif).isEnabled()) {

				select = true;
				u.highLight(p_digito_verif);
				u.click(p_digito_verif);
				testCase.addTextEvidenceCurrentStep("el campo Digito Verificador esta habilitado");
			} else {

				testCase.addTextEvidenceCurrentStep(
						"el campo Digito Verificador NO esta habilitado por lo tanto no puede escribir en el");

			}

			break;

		case "referencia bancaria":
			if (u.getDriver().findElement(p_ref_bancaria).isEnabled()) {

				select = true;
				u.highLight(p_ref_bancaria);
				u.click(p_ref_bancaria);
				testCase.addTextEvidenceCurrentStep("el campo Rerefencia bancaria esta habilitado");
			} else {

				testCase.addTextEvidenceCurrentStep(
						"el campo Rerefencia bancaria NO esta habilitado por lo tanto no puede escribir en el");

			}

			break;
		default:
			return select;

		}

		return select;
	}

	/**
	 * Metodo para validar dar cambio, saldo inicial, y monto inicial max
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return N/A
	 */
	public boolean validar_C_SI_MIM() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean enable = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");

		if (u.getDriver().findElement(p_lid_darcambio).isEnabled()
				&& u.getDriver().findElement(p_lid_saldo_ini).isEnabled()
				&& u.getDriver().findElement(p_lid_max_ini_sal).isEnabled()) {
			testCase.addTextEvidenceCurrentStep("Checkboxs existen y estan habilitados");
			enable = true;
//			if (u.getDriver().findElement(p_lid_darcambio).is) {
//				enable = true;
//				u.highLight(p_lid_darcambio);
//				testCase.addScreenShotCurrentStep(u, "CORREn1");
////			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y esta habilitado");
//			} else {
//				enable = false;
//				u.highLight(Cambiario);
//				u.click(Cambiario);
//				testCase.addScreenShotCurrentStep(u, "CORREn2");
////			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y No esta habilitado");
//			}
		} else {
			testCase.addScreenShotCurrentStep(u, "CORREn222");
			testCase.addTextEvidenceCurrentStep("Checkboxs existen y NO estan habilitados");
			enable = false;
		}
		return enable;
	}

	/**
	 * Metodo para validar dar cambio, saldo inicial, y monto inicial max
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return N/A
	 */
	public boolean DarClickEnDarCambio() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean enable = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");

		if (u.getDriver().findElement(p_lid_darcambio).isEnabled()) {
			testCase.addTextEvidenceCurrentStep("Checkboxs existen y estan habilitados");
			enable = true;
			if (u.getDriver().findElement(p_lid_darcambio).isSelected()) {
//				enable = true;
				u.highLight(p_lid_darcambio);
				u.click(p_lid_darcambio);
				u.click(p_lid_darcambio);
				testCase.addScreenShotCurrentStep(u, "CORREn1");
     			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y esta habilitado");
			} else {
				u.highLight(p_lid_darcambio);
				u.click(p_lid_darcambio);
//				enable = false;
//				u.highLight(Cambiario);
//				u.click(Cambiario);
				testCase.addScreenShotCurrentStep(u, "CORREn2");
     			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y No esta habilitado");
			}
		} else {
			testCase.addScreenShotCurrentStep(u, "CORREn222");
			testCase.addTextEvidenceCurrentStep("Checkboxs existen y NO estan habilitados");
			enable = false;
		}
		return enable;
	}
	
	/**
	 * Metodo para validar dar click y desactivar el click en el campo dar cambio, saldo inicial, y monto inicial max
	 * 
	 * @author Alfonso Alan
	 * @param NA
	 * @return N/A
	 */
	public boolean DesactivarEnDarCambio() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean enable = true;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		//me.disableElementJs(p_lid_darcambio);
		u.hardWait(2);

		if (u.getDriver().findElement(p_lid_darcambio).isEnabled()) {
			
			//testCase.addTextEvidenceCurrentStep("Checkboxs existen y estan habilitados");
			enable = true;
			System.out.print("entro al primer if");
			//u.highLight(p_lid_darcambio); //si el campo de dar cambio esta activado va a darle click y lo desactivara
			//u.click(p_lid_darcambio);
			//testCase.addScreenShotCurrentStep(u, "CORREn1");
			testCase.addScreenShotCurrentStep(u, "CORREn4");
			//u.highLight(framed);
			//verifica si el checkbox dar cambio esta seleccionado pues valida que esten desactivados tanto el checkbox de saldo inicial como el de monto m�ximo saldo inicial
			if (!u.getDriver().findElement(p_lid_saldo_ini).isEnabled() && !u.getDriver().findElement(p_lid_max_ini_sal).isEnabled()) {
    			enable = true;
    			System.out.print("entro al segundo if-else");
				u.highLight(p_lid_darcambio);
				//u.click(p_lid_darcambio);
				u.highLight(p_lid_saldo_ini);
				u.highLight(p_lid_max_ini_sal);
				//u.click(p_lid_darcambio);
				testCase.addScreenShotCurrentStep(u, "CORREn1");
     			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y esta habilitado");
	 			//testCase.addBoldTextEvidenceCurrentStep("Checkbox de saldo inicial, monto m�ximo existen y no estan habilitados");
			} else {
				enable = false;
    			System.out.print("entro al else");
				u.highLight(p_lid_darcambio);
				u.click(p_lid_darcambio);				
				u.highLight(p_lid_saldo_ini);
				u.highLight(p_lid_max_ini_sal);
//				enable = false;
//				u.highLight(Cambiario);
//				u.click(Cambiario);
				testCase.addScreenShotCurrentStep(u, "CORREn2");
				///testCase.addScreenShotCurrentStep(u, "CORREn222");
				testCase.addTextEvidenceCurrentStep("Checkboxs existen y NO estan habilitados");
	 			testCase.addBoldTextEvidenceCurrentStep("Checkbox de saldo inicial, monto m�ximo existen y no estan habilitados");
     			//testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y No esta habilitado");
			}
		} else {
			enable = false;
			System.out.print("entro al if-else-else");
			testCase.addScreenShotCurrentStep(u, "CORREn222");
			testCase.addTextEvidenceCurrentStep("Checkboxs existen y NO estan habilitados");
 			testCase.addBoldTextEvidenceCurrentStep("Checkbox de saldo inicial, monto m�ximo existen y no estan habilitados");
		}
		return enable;
}

	/**
	 * Metodo para editar dar cambio, saldo inicial, y monto inicial max
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return N/A
	 */
	public boolean editar_C_SI_MIM(String SaldoM) {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean enable = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");

		if (u.getDriver().findElement(Cambiario).isSelected()) {

			u.click(Cambiario);
			u.click(Cambiario);

		} else {

			u.click(Cambiario);

		}
		if (u.getDriver().findElement(p_lid_darcambio).isEnabled()
		// && u.getDriver().findElement(p_lid_saldo_ini).isEnabled()
		// && u.getDriver().findElement(p_lid_max_ini_sal).isEnabled()
		) {
			testCase.addTextEvidenceCurrentStep("Checkboxs existen y estan habilitados");
			enable = true;
			if (u.getDriver().findElement(p_lid_darcambio).isSelected()) {
				u.click(p_lid_darcambio);
				u.click(p_lid_darcambio);
//				enable = true;
//				u.highLight(p_lid_darcambio);
				// testCase.addScreenShotCurrentStep(u, "cambio");
////			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y esta habilitado");
			} else {
				u.click(p_lid_darcambio);
			}
 
			if (u.getDriver().findElement(p_lid_max_ini_sal).isSelected()) {
				u.click(p_lid_max_ini_sal);
				u.click(p_lid_max_ini_sal);
				u.clear(p_lid_max_ini_sal);
				u.sendKeys(p_lid_max_ini_sal, SaldoM);
				testCase.addTextEvidenceCurrentStep("Se mando el siguiente texto:" + SaldoM);

//				enable = true;
//				u.highLight(p_lid_darcambio);
//				testCase.addScreenShotCurrentStep(u, "CORREn1");
////			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y esta habilitado");
			} else {
				u.click(p_lid_max_ini_sal);
				u.clear(p_lid_max_ini_sal);
				u.sendKeys(p_lid_max_ini_sal, SaldoM);
				testCase.addTextEvidenceCurrentStep("Se mando el siguiente texto:" + SaldoM);

			}
			testCase.addScreenShotCurrentStep(u, "maxini");

		} else {
			testCase.addScreenShotCurrentStep(u, "CORREn222");
			testCase.addTextEvidenceCurrentStep("Checkboxs existen y NO estan habilitados");
			enable = false;
		}
		return enable;
	}

	/**
	 * Metodo para editar dar cambio, saldo inicial, y monto inicial max (ctrl copy
	 * ctrl v)
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return N/A
	 */
	public boolean editar_C_SI_MIM_ctrlcopy_ctrlv(String SaldoM) {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean enable = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");

		if (u.getDriver().findElement(Cambiario).isSelected()) {

			u.click(Cambiario);
			u.click(Cambiario);

		} else {

			u.click(Cambiario);

		}
		if (u.getDriver().findElement(p_lid_darcambio).isEnabled()
		// && u.getDriver().findElement(p_lid_saldo_ini).isEnabled()
		// && u.getDriver().findElement(p_lid_max_ini_sal).isEnabled()
		) {
			testCase.addTextEvidenceCurrentStep("Checkboxs existen y estan habilitados");
			enable = true;
			if (u.getDriver().findElement(p_lid_darcambio).isSelected()) {
				u.click(p_lid_darcambio);
				u.click(p_lid_darcambio);
//				enable = true;
//				u.highLight(p_lid_darcambio);
				// testCase.addScreenShotCurrentStep(u, "cambio");
////			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y esta habilitado");
			} else {
				u.click(p_lid_darcambio);
			}
			if (u.getDriver().findElement(p_lid_saldo_ini).isSelected()) {
				u.click(p_lid_saldo_ini);
				u.click(p_lid_saldo_ini);
				// testCase.addScreenShotCurrentStep(u, "saldoini");
//				enable = true;
//				u.highLight(p_lid_darcambio);
//				testCase.addScreenShotCurrentStep(u, "CORREn1");
////			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y esta habilitado");
			} else {
				u.click(p_lid_saldo_ini);
			}
			if (u.getDriver().findElement(p_lid_max_ini_sal).isSelected()) {
				u.click(p_lid_max_ini_sal);
				u.click(p_lid_max_ini_sal);
				u.clear(p_lid_max_ini_sal);
				u.sendKeys(p_lid_max_ini_sal, SaldoM);

				testCase.addTextEvidenceCurrentStep("Se copio y pego  el siguiente texto:" + SaldoM);

//				enable = true;
//				u.highLight(p_lid_darcambio);
//				testCase.addScreenShotCurrentStep(u, "CORREn1");
////			testCase.addBoldTextEvidenceCurrentStep("Checkbox existe y esta habilitado");
			} else {
				u.click(p_lid_max_ini_sal);
				u.clear(p_lid_max_ini_sal);
				// u.sendKeys(p_lid_max_ini_sal,SaldoM);

				WebElement currentAddress = u.getDriver().findElement(p_lid_max_ini_sal);
				currentAddress.sendKeys(SaldoM);

				// Copy the Current Address
				currentAddress.sendKeys(Keys.CONTROL);
				currentAddress.sendKeys("A");
				currentAddress.sendKeys(Keys.CONTROL);
				currentAddress.sendKeys("C");

				testCase.addTextEvidenceCurrentStep("Se copio y pego  el siguiente texto:" + SaldoM);

			}
			testCase.addScreenShotCurrentStep(u, "maxini");

		} else {
			testCase.addScreenShotCurrentStep(u, "CORREn222");
			testCase.addTextEvidenceCurrentStep("Checkboxs existen y NO estan habilitados");
			enable = false;
		}
		return enable;
	}

	/**
	 * Metodo para comparar texto 
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return N/A
	 */
	public boolean CompText(String SaldoM) {
		boolean si = true;
		WebElement currentAddress = u.getDriver().findElement(p_lid_max_ini_sal);
		String max = 	currentAddress.getAttribute("value");
		//String max = u.getText(p_lid_max_ini_sal);
		System.out.print("max"+max);
		System.out.print("SaldoM"+SaldoM);
		if (max.equals(SaldoM)) {
			si = true;
		} else {
			si = false;
		}

		return si;
	}

	/**
	 * Metodo para seleccionar El permiso de venta de cerveza es propio EN SI
	 * 
	 * @author Baltazar Lugo
	 * @param SI
	 * @return N/A
	 */
	public void CervezaPropia(String SI) {
//		u.waitForLoadPage();
//		u.getDriver().switchTo().frame("framedatos");
		u.hardWait(3);
		Select idp = new Select(u.getDriver().findElement(p_pem_vta_cer_propio));
		u.highLight(p_pem_vta_cer_propio);
		// option[@value="437 | 10MON000014"]
//		By IdPunto1 = By.xpath("//option[@value="+idpunto+"]");

		u.hardWait(3);
		idp.selectByValue(SI);
		testCase.addScreenShotCurrentStep(u, "DistA");
//		u.getDriver().switchTo().alert().accept();
	}

	/**
	 * Formula digito verificador
	 * 
	 * @author Baltazar Lugo
	 * @param SI
	 * @return N/A
	 */
	public int Formula_digito_Verificador(String num1, String num2, String obj3, String obj4, String obj5) {
		testCase.addTextEvidenceCurrentStep(
				"Digitos a multlipicar=" + num1 + " " + num2 + " " + obj3 + " " + obj4 + " " + obj5);
		int A = 1, B = 2, C = 3, D = 4, E = 5, F = 6, G = 7, H = 8, I = 9, J = 1, K = 2, L = 3, M = 4, N = 5, O = 6,
				P = 7, Q = 8, R = 9, S = 1, T = 2, U = 3, V = 4, W = 5, X = 6, Y = 7, Z = 8, UNO = 1, DOS = 2, TRES = 3,
				CUATRO = 4, CINCO = 5, SEIS = 6, SIETE = 7, OCHO = 8, NUEVE = 9;
		testCase.addTextEvidenceCurrentStep("A=" + A + " B=" + B + " C=" + C + " D=" + D + " E=" + E + " F=" + F + " G="
				+ G + " H=" + H + " I=" + I + " J=" + J + " K=" + K + " L=" + L + " M=" + M + " N=" + N + " O=" + O
				+ " P=" + P + " Q=" + Q + " R=" + R + " S=" + S + " T=" + T + " U=" + U + " V=" + V + " W=" + W + " X="
				+ X + " Y=" + Y + " Z=" + Z + " 1=" + UNO + " 2" + DOS + " 3=" + TRES + " 4=" + CUATRO + " 5=" + CINCO
				+ " 6=" + 6 + " 7 =" + SIETE + " 8=" + OCHO + " 9=" + NUEVE);
		int number1 = 0, number2 = 0, number3 = 0, number4 = 0, number5 = 0;
		Double m11 = 0.0, m12 = 0.0, m13 = 0.0, m14 = 0.0, m15 = 0.0;
		if (num1.equals("A")) {
			num1 = "1";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("B")) {
			num1 = "2";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("C")) {
			num1 = "3";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("D")) {
			num1 = "4";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("E")) {
			num1 = "5";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("F")) {
			num1 = "6";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("G")) {
			num1 = "7";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("H")) {
			num1 = "8";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("I")) {
			num1 = "9";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("J")) {
			num1 = "1";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("K")) {
			num1 = "2";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("L")) {
			num1 = "3";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("M")) {
			num1 = "4";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("N")) {
			num1 = "5";
			number1 = Integer.parseInt(num1);
		}

		if (num1.equals("O")) {
			num1 = "6";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("P")) {
			num1 = "7";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("Q")) {
			num1 = "8";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("R")) {
			num1 = "9";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("S")) {
			num1 = "1";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("T")) {
			num1 = "2";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("U")) {
			num1 = "3";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("V")) {
			num1 = "4";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("W")) {
			num1 = "5";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("X")) {
			num1 = "6";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("Y")) {
			num1 = "7";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("Z")) {
			num1 = "8";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("0")) {
			num1 = "0";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("1")) {
			num1 = "1";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("2")) {
			num1 = "2";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("3")) {
			num1 = "3";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("4")) {
			num1 = "4";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("5")) {
			num1 = "5";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("6")) {
			num1 = "6";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("7")) {
			num1 = "7";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("8")) {
			num1 = "8";
			number1 = Integer.parseInt(num1);
		}
		if (num1.equals("9")) {
			num1 = "9";
			number1 = Integer.parseInt(num1);
		}

		if (num2.equals("A")) {
			num2 = "1";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("B")) {
			num2 = "2";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("C")) {
			num2 = "3";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("D")) {
			num2 = "4";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("E")) {
			num2 = "5";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("F")) {
			num2 = "6";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("G")) {
			num2 = "7";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("H")) {
			num2 = "8";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("I")) {
			num2 = "9";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("J")) {
			num2 = "1";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("K")) {
			num2 = "2";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("L")) {
			num2 = "3";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("M")) {
			num2 = "4";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("N")) {
			num2 = "5";
			number2 = Integer.parseInt(num2);
		}

		if (num2.equals("O")) {
			num2 = "6";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("P")) {
			num2 = "7";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("Q")) {
			num2 = "8";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("R")) {
			num2 = "9";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("S")) {
			num2 = "1";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("T")) {
			num2 = "2";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("U")) {
			num2 = "3";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("V")) {
			num2 = "4";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("W")) {
			num2 = "5";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("X")) {
			num2 = "6";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("Y")) {
			num2 = "7";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("Z")) {
			num2 = "8";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("0")) {
			num2 = "0";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("1")) {
			num2 = "1";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("2")) {
			num2 = "2";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("3")) {
			num2 = "3";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("4")) {
			num2 = "4";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("5")) {
			num2 = "5";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("6")) {
			num2 = "6";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("7")) {
			num2 = "7";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("8")) {
			num2 = "8";
			number2 = Integer.parseInt(num2);
		}
		if (num2.equals("9")) {
			num2 = "9";
			number2 = Integer.parseInt(num2);
		}

		if (obj3.equals("A")) {
			obj3 = "1";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("B")) {
			obj3 = "2";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("C")) {
			obj3 = "3";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("D")) {
			obj3 = "4";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("E")) {
			obj3 = "5";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("F")) {
			obj3 = "6";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("G")) {
			obj3 = "7";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("H")) {
			obj3 = "8";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("I")) {
			obj3 = "9";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("J")) {
			obj3 = "1";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("K")) {
			obj3 = "2";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("L")) {
			obj3 = "3";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("M")) {
			obj3 = "4";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("N")) {
			obj3 = "5";
			number3 = Integer.parseInt(obj3);
		}

		if (obj3.equals("O")) {
			obj3 = "6";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("P")) {
			obj3 = "7";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("Q")) {
			obj3 = "8";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("R")) {
			obj3 = "9";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("S")) {
			obj3 = "1";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("T")) {
			obj3 = "2";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("U")) {
			obj3 = "3";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("V")) {
			obj3 = "4";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("W")) {
			obj3 = "5";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("X")) {
			obj3 = "6";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("Y")) {
			obj3 = "7";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("Z")) {
			obj3 = "8";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("0")) {
			obj3 = "0";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("1")) {
			obj3 = "1";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("2")) {
			obj3 = "2";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("3")) {
			obj3 = "3";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("4")) {
			obj3 = "4";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("5")) {
			obj3 = "5";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("6")) {
			obj3 = "6";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("7")) {
			obj3 = "7";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("8")) {
			obj3 = "8";
			number3 = Integer.parseInt(obj3);
		}
		if (obj3.equals("9")) {
			obj3 = "9";
			number3 = Integer.parseInt(obj3);
		}

		if (obj4.equals("A")) {
			obj4 = "1";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("B")) {
			obj4 = "2";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("C")) {
			obj4 = "3";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("D")) {
			obj4 = "4";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("E")) {
			obj4 = "5";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("F")) {
			obj4 = "6";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("G")) {
			obj4 = "7";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("H")) {
			obj4 = "8";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("I")) {
			obj4 = "9";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("J")) {
			obj4 = "1";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("K")) {
			obj4 = "2";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("L")) {
			obj4 = "3";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("M")) {
			obj4 = "4";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("N")) {
			obj4 = "5";
			number4 = Integer.parseInt(obj4);
		}

		if (obj4.equals("O")) {
			obj4 = "6";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("P")) {
			obj4 = "7";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("Q")) {
			obj4 = "8";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("R")) {
			obj4 = "9";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("S")) {
			obj4 = "1";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("T")) {
			obj4 = "2";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("U")) {
			obj4 = "3";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("V")) {
			obj4 = "4";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("W")) {
			obj4 = "5";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("X")) {
			obj4 = "6";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("Y")) {
			obj4 = "7";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("Z")) {
			obj4 = "8";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("0")) {
			obj4 = "0";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("1")) {
			obj4 = "1";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("2")) {
			obj4 = "2";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("3")) {
			obj4 = "3";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("4")) {
			obj4 = "4";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("5")) {
			obj4 = "5";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("6")) {
			obj4 = "6";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("7")) {
			obj4 = "7";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("8")) {
			obj4 = "8";
			number4 = Integer.parseInt(obj4);
		}
		if (obj4.equals("9")) {
			obj4 = "9";
			number4 = Integer.parseInt(obj4);
		}
		if (obj5.equals("A")) {
			obj5 = "1";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("B")) {
			obj5 = "2";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("C")) {
			obj5 = "3";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("D")) {
			obj5 = "4";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("E")) {
			obj5 = "5";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("F")) {
			obj5 = "6";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("G")) {
			obj5 = "7";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("H")) {
			obj5 = "8";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("I")) {
			obj5 = "9";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("J")) {
			obj5 = "1";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("K")) {
			obj5 = "2";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("L")) {
			obj5 = "3";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("M")) {
			obj5 = "4";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("N")) {
			obj5 = "5";
			number5 = Integer.parseInt(obj5);
		}

		if (obj5.equals("O")) {
			obj5 = "6";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("P")) {
			obj5 = "7";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("Q")) {
			obj5 = "8";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("R")) {
			obj5 = "9";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("S")) {
			obj5 = "1";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("T")) {
			obj5 = "2";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("U")) {
			obj5 = "3";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("V")) {
			obj5 = "4";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("W")) {
			obj5 = "5";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("X")) {
			obj5 = "6";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("Y")) {
			obj5 = "7";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("Z")) {
			obj5 = "8";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("0")) {
			obj5 = "0";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("1")) {
			obj5 = "1";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("2")) {
			obj5 = "2";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("3")) {
			obj5 = "3";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("4")) {
			obj5 = "4";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("5")) {
			obj5 = "5";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("6")) {
			obj5 = "6";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("7")) {
			obj5 = "7";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("8")) {
			obj5 = "8";
			number5 = Integer.parseInt(obj5);
		}
		if (obj5.equals("9")) {
			obj5 = "9";
			number5 = Integer.parseInt(obj5);
		}

		testCase.addTextEvidenceCurrentStep("multlipicacion los digitos por 2,1,2,1,2");
		int m1 = 0, m2 = 0, m3 = 0, m4 = 0, m5 = 0;
		int dos = 2;
		int uno = 1;
		m1 = number1 * dos;
		m2 = number2 * uno;
		m3 = number3 * dos;
		m4 = number4 * uno;
		m5 = number5 * dos;
		testCase.addTextEvidenceCurrentStep("multlipicacion  " + m1 + " " + m2 + " " + m3 + " " + m4 + " " + m5 + " ");
		System.out.print("m1: " + m1 + "m2: " + m2 + "m3: " + m3 + "m4: " + m4 + "m5: " + m5);

//	boolean primo1=isPrime(m1);
//	boolean primo2=isPrime(m2);
//	boolean primo3=isPrime(m3);
//	boolean primo4=isPrime(m4);
//	boolean primo5=isPrime(m5);
//	if (primo1==false) {
		m11 = Double.valueOf(m1);
//	}
//	if (primo2==false) {
		m12 = Double.valueOf(m2);
//	}
//	if (primo3==false) {
		m13 = Double.valueOf(m3);
//	}
//	if (primo4==false) {
		m14 = Double.valueOf(m4);
//	}
//	if (primo5==false) {
		m15 = Double.valueOf(m5);
//	}
		System.out.print("m1    :" + m1);

		m11 = m11 / 10;
		System.out.print("m1/10    :" + m11);
		m12 = m12 / 10;
		System.out.print("m2/10    :" + m12);
		m13 = m13 / 10;
		System.out.print("m3/10    :" + m13);
		m14 = m14 / 10;
		System.out.print("m4/10    :" + m14);
		m15 = m15 / 10;
		System.out.print("m5/10    :" + m15);
		testCase.addTextEvidenceCurrentStep("division " + m11 + " " + m12 + " " + m13 + " " + m14 + " " + m15 + " ");

		double parteDecimal = m11 % 1; // Lo que sobra de dividir al nÃºmero entre 1
		int parteEntera = (int) (m11 - parteDecimal);
		DecimalFormat format = new DecimalFormat(".0");
		String aux = format.format(parteDecimal);
		String s = String.valueOf(aux);
		String newString1 = s.replace(".", "");
//	DecimalFormat format = new DecimalFormat("0.0");
//	String aux = format.format(parteDecimal);//Obtenemos el valor neto usando el formateador
//
//	aux = aux.replaceAll(",","");//eliminamos las comas
		System.out.print(" newString1: " + newString1);
		int parteDecimal1 = Integer.parseInt(newString1);

		double parteDecimal2 = (m12 % 1); // Lo que sobra de dividir al nÃºmero entre 1
		int parteEntera2 = (int) (m12 - parteDecimal2);
		DecimalFormat format2 = new DecimalFormat(".0");
		String aux2 = format2.format(parteDecimal2);
		String s2 = String.valueOf(aux2);
		String newString12 = s2.replace(".", "");
//	DecimalFormat format = new DecimalFormat("0.0");
//	String aux = format.format(parteDecimal);//Obtenemos el valor neto usando el formateador
//
//	aux = aux.replaceAll(",","");//eliminamos las comas
		System.out.print(" newString11: " + newString12);
		int parteDecimal12 = Integer.parseInt(newString12);

		double parteDecimal3 = m13 % 1; // Lo que sobra de dividir al nÃºmero entre 1
		int parteEntera3 = (int) (m13 - parteDecimal3);
		DecimalFormat format3 = new DecimalFormat(".0");
		String aux3 = format3.format(parteDecimal3);
		String s3 = String.valueOf(aux3);
		String newString13 = s3.replace(".", "");
//	DecimalFormat format = new DecimalFormat("0.0");
//	String aux = format.format(parteDecimal);//Obtenemos el valor neto usando el formateador
//
//	aux = aux.replaceAll(",","");//eliminamos las comas
		System.out.print(" m13% 1: " + parteDecimal3);
		System.out.print(" newString11: " + newString13);
		int parteDecimal13 = Integer.parseInt(newString13);

		double parteDecimal4 = (m14 % 1); // Lo que sobra de dividir al nÃºmero entre 1
		int parteEntera4 = (int) (m14 - parteDecimal4);
		DecimalFormat format4 = new DecimalFormat(".0");
		String aux4 = format4.format(parteDecimal4);
		String s4 = String.valueOf(aux4);
		String newString14 = s4.replace(".", "");
//	DecimalFormat format = new DecimalFormat("0.0");
//	String aux = format.format(parteDecimal);//Obtenemos el valor neto usando el formateador
//
//	aux = aux.replaceAll(",","");//eliminamos las comas
		System.out.print(" newString11: " + newString14);
		int parteDecimal14 = Integer.parseInt(newString14);

		double parteDecimal5 = (m15 % 1); // Lo que sobra de dividir al nÃºmero entre 1
		int parteEntera5 = (int) (m15 - parteDecimal5);
		DecimalFormat format5 = new DecimalFormat(".0");
		String aux5 = format5.format(parteDecimal5);
		String s5 = String.valueOf(aux5);
		String newString15 = s5.replace(".", "");
//	DecimalFormat format = new DecimalFormat("0.0");
//	String aux = format.format(parteDecimal);//Obtenemos el valor neto usando el formateador
//
//	aux = aux.replaceAll(",","");//eliminamos las comas
		System.out.print(" newString11: " + newString15);
		int parteDecimal15 = Integer.parseInt(newString15);

		System.out.print("parteEntera" + parteEntera + "parteDecimal" + parteDecimal);
//	String parteEnteras=String.valueOf(parteEntera);  
//	String Cadena = new String(parteEnteras);
//	String Cadena2;
//	for(int a = 0; a < Cadena.length(); a++)
//	{
//	   if(Cadena.charAt(a) == '.')
//	       Cadena2[a] = '.';
//	   else
//	      Cadena2[a] = Cadena[a];
//	} 
		int sum = parteEntera + parteDecimal1;
		System.out.print("sumaaaaa" + sum);

		System.out.print("parteEntera2" + parteEntera2 + "parteDecimal2" + parteDecimal12);
		int sum2 = parteEntera2 + parteDecimal12;
		System.out.print("sumaaaaa2" + sum2);

		System.out.print("parteEntera3" + parteEntera3 + "parteDecimal3" + parteDecimal13);
		int sum3 = parteEntera3 + parteDecimal13;
		System.out.print("sumaaaaa3" + sum3);

		System.out.print("parteEnter4" + parteEntera4 + "parteDecimal4" + parteDecimal14);
		int sum4 = parteEntera4 + parteDecimal14;
		System.out.print("sumaaaaa4" + sum4);

		System.out.print("parteEntera5" + parteEntera5 + "parteDecimal5" + parteDecimal15);
		int sum5 = parteEntera5 + parteDecimal15;
		System.out.print("sumaaaaa5" + sum5);
		System.out.print("m1: " + m1 + "m2: " + m12 + "m3: " + m13 + "m4: " + m14 + "m5: " + m15);
		testCase.addTextEvidenceCurrentStep("suma " + sum + " " + sum2 + " " + sum3 + " " + sum4 + " " + sum5 + " ");

		double sumtotal = sum + sum2 + sum3 + sum4 + sum5;
		testCase.addTextEvidenceCurrentStep("suma total " + sumtotal);

		double division = sumtotal / 10;

		System.out.print("division: " + division);
		double divisionultima = (division % 1);

		testCase.addTextEvidenceCurrentStep("Formula" + sumtotal + "/10=" + division);
		testCase.addTextEvidenceCurrentStep("residuo de formula: " + divisionultima);
		System.out.print("divisionultima " + divisionultima);
		DecimalFormat format6 = new DecimalFormat(".0");
		String aux6 = format6.format(divisionultima);
		String s6 = String.valueOf(aux6);
		String newString16 = s6.replace(".", "");
//	DecimalFormat format = new DecimalFormat("0.0");
//	String aux = format.format(parteDecimal);//Obtenemos el valor neto usando el formateador
//
//	aux = aux.replaceAll(",","");//eliminamos las comas
		System.out.print(" newString16: " + newString16);
		int parteDecimal16 = Integer.parseInt(newString16);
		int fin = 10 - parteDecimal16;
		testCase.addTextEvidenceCurrentStep("Formula: 10- " + parteDecimal16 + "=" + fin);
		testCase.addTextEvidenceCurrentStep("Digito verificador segun formula de excel: " + fin);

		return fin;
	}

	static boolean isPrime(int num) {
		if (num <= 1) {
			return false;
		}
		for (int i = 2; i < num / 2; i++) {
			if ((num % i) == 0)
				return false;
		}
		return true;
	}

	/**
	 * Constructor
	 ******************************************************************************************/

	public Permisos_y_Contratos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));
	}

	/*
	 * METODOS - Acceso correcto a Inventario de espacios Publicitarios V2
	 ******************************************************************************************/
	/**
	 * Metodo para validar Codigo verificador y referencia retrocediendo pesos
	 * 
	 * @author Manuel Cuen
	 * @param NA
	 * @return N/A
	 */
	public boolean validarCodVerificadorYRefPesos() {

		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		// u.getDriver().manage().window().maximize();
		u.highLight(framed);
		// u.getDriver().switchTo().frame("framedatos");
		u.hardWait(3);
		u.getDriver().switchTo().frame("framedatos");
		u.highLight(xmlCuentaPesos);
		testCase.addScreenShotCurrentStep(u, "Detalle");
		u.click(xmlCuentaPesos);

		// String si = "SI";
		// String winHandleBefore = u.getDriver().getWindowHandle();
		// boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

//		u.hardWait(10);
//		u.getDriver().manage().window().maximize();
//		u.highLight(framed);
//		u.getDriver().switchTo().frame("framedatos");
//		u.highLight(CONFIGCUENTADOLARES);
//		u.click(CONFIGCUENTADOLARES);

//		WebElement inputBox = driver.findElement(By.id("inputBoxId"));
//		String textInsideInputBox = inputBox.getAttribute("value");
//
//		// Check whether input field is blank
//		if(textInsideInputBox.isEmpty())
//		{
//		   System.out.println("Input field is empty");
//		}

		u.hardWait(4);
		u.getDriver().switchTo().frame("framedatos");

		u.highLight(p_digito_verif_Pesos);
		u.click(p_digito_verif_Pesos);

		String texto3 = u.getDriver().findElement(p_digito_verif_Pesos).getAttribute("value");
		System.out.print("p_digito_verif_Pesos: " + texto3);

		u.click(p_ref_bancaria_Pesos);
		String texto4 = u.getDriver().findElement(p_ref_bancaria_Pesos).getAttribute("value");
		System.out.print("p_ref_bancaria_Pesos: " + texto4);
		u.hardWait(2);

		// Check whether input field is blank
		if (texto3.isEmpty() && texto4.isEmpty()) {
			System.out.println("Input field is empty");

			Vacio = true;
		} else {
			Vacio = false;
			testCase.addScreenShotCurrentStep(u, "SCREENVERIFICADOR");
			testCase.addTextEvidenceCurrentStep("Digito Verificador:" + texto3);
			testCase.addTextEvidenceCurrentStep("Referencia:" + texto4);
		}
		u.click(retroceder);
		u.hardWait(2);
		u.click(retroceder);
		u.hardWait(2);
		u.click(retroceder);
		u.hardWait(2);
		u.click(retroceder);
		u.hardWait(2);
		u.click(retroceder);
		u.hardWait(2);
		return Vacio;
	}

    
	/**
	 * Metodo para extraer el banco corresponsal

	 * @author Alfonso Oyervides
	 * @param NA
	 * @return N/A
	 */
	public String CampoCorr() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		//u.highLight(CONFIGCUENTADOLARES);
		//u.click(CONFIGCUENTADOLARES);


		u.click(p_banco_corresponsal);
		u.highLight(p_banco_corresponsal);
		//String textoS = u.getDriver().findElement(p_banco_corresponsal).getAttribute("value");
		String textoS = u.getDriver().findElement(By.name("p_banco_corresponsal")).getText();
		
		//System.out.print("p_banco_corresponsal: " + textoS);
		testCase.addScreenShotCurrentStep(u, "SCREENBANCOCORRESPONSAL");
		testCase.addTextEvidenceCurrentStep("Banco Corresponsal: "+textoS);
		return textoS;
	}
	
	/**
	 * Metodo para extraer el campo usd/cuenta convenio sin necesidad de darle click de nuevo a la configuracion
	 * cuenta dolares

	 * @author Alfonso Oyervides
	 * @param NA
	 * @return N/A
	 */
	public String CampoCuentaConvenio() {
		String si = "SI";
		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		u.getDriver().manage().window().maximize();
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		//u.highLight(CONFIGCUENTADOLARES);
		//u.click(CONFIGCUENTADOLARES);


		u.click(p_cuenta_convenio);
		u.highLight(p_cuenta_convenio);
		String texto9 = u.getDriver().findElement(p_cuenta_convenio).getAttribute("value");
		
		System.out.print("p_cuenta_convenio: " + texto9);
		testCase.addScreenShotCurrentStep(u, "SCREENCUENTAUSD");
		testCase.addTextEvidenceCurrentStep("Cuenta USD:" + texto9);
		return texto9;
	}

	public void clickRegresaventana() {
//		u.getDriver().switchTo().frame("framemenu");
		String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.highLight(frame);
		u.getDriver().switchTo().frame("framemenu");

		u.highLight(PyC);

		testCase.addScreenShotCurrentStep(u, "DatosGen");
		u.click(PyC);

		u.getDriver().switchTo().window(winHandleBefore);
		u.waitForLoadPage();

		String si = "SI";
//		String winHandleBefore = u.getDriver().getWindowHandle();
		boolean Vacio = false;
		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
		u.getDriver().manage().window().maximize();
//		u.highLight(TiendaE);

		u.hardWait(10);
		// u.getDriver().manage().window().maximize();
		u.highLight(framed);
		// u.getDriver().switchTo().frame("framedatos");

		u.hardWait(3);
		u.getDriver().switchTo().frame("framedatos");
		u.highLight(xmlCuentaPesos);
		testCase.addScreenShotCurrentStep(u, "Detalle");
		u.click(xmlCuentaPesos);

		// u.highLight(xmlFramePie);
//		u.getDriver().switchTo().frame("framemenu2");
//		u.hardWait(2);
//		u.highLight(xmlConfiguracionCuentaPesos);
//		u.click(xmlConfiguracionCuentaPesos);


	}
	
	/**
	 * Metodo para capturar el banco corresponsal

	 * @author David Valdez
	 * @param NA
	 * @return Boolean : true si esta activado el campo para realizar el cambio del banco corresponsal
	 */
	public Boolean capturaBancoCorresponsal() {
		//me.deleteAttributeJs(campo_Banco_Corresponsal_USD); // Simula que estan activos los campos
		u.highLight(campo_Banco_Corresponsal_USD);
		
		me.clickJs(campo_Banco_Corresponsal_USD);
		u.mouseOverAndClick(campo_Banco_Corresponsal_USD);
		testCase.addScreenShotCurrentStep(u, "Captura");
		return me.isEnable(campo_Banco_Corresponsal_USD);
	}
}