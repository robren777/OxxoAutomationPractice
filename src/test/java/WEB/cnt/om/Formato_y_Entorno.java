package WEB.cnt.om;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Formato_y_Entorno {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	/**
	 * Localizadores y Principal o Home del portal
	 * Catalogo Nacional de Tiendas
	 ******************************************************************************************/
	/*
	 * Localizadores login portal CNT Formato y Entorno
	 */

	By tituloPantalla = By.xpath("//center/font/strong"); 
	By TiendaE = By.xpath("//*[text()=\"Seleccionar Tienda\"]");
	// By DatosOp= By.xpath("//*[@target=\"framedatos\"][2]");

	/*
	 * Localizadores login portal CNT Captura de Datos Menu >Formato y Entorno
	 */

	By FyE = By.xpath("//*[@target=\"framedatos\"][4]");
	By TamTienda = By.xpath("//select[@name=\"p_lid_tamanio_tienda\"]");
	By Diferenciador = By.xpath("//select[@name=\"p_concepto_diferenciador_id\"]");
	By diferenciadorTd = By.xpath("//form/table[1]/tbody/tr[2]/td[3]/table/tbody/tr[1]/td[2]");
	By Segmento = By.xpath("//select[@name=\"p_lid_segmento\"]");
	By SubtipoUbiTienda = By.name("p_lid_subtipo_ubicacion_tienda");
	By Ubi = By.xpath("//select[@name=\"p_lid_ubicacion_tienda\"]");
	By TiendaEspejo = By.xpath("//select[@name=\"p_retek_like_store\"]");
	By TiendaSister = By.xpath("//select[@name=\"p_retek_sister_store\"]");
	By Porcentaje = By.xpath("//input[@name=\"p_retek_por_ajuste_venta\"]");
	By NivelSocioE = By.xpath("//select[@name=\"p_lid_nivel_socioeconomico\"]");
	By ZonaEco = By.xpath("//select[@name=\"p_lid_zona_economica\"]");

	By Entorno = By.xpath("//*[contains(@href,'p_subopcion=2')]");
	By Vialidad = By.xpath("//select[@name=\"p_lid_tipo_vialidad\"]");
	By CamRef = By.xpath("//input[@name=\"p_lid_referencia_ubicacion\"]");

	By frame = By.xpath("//frame[contains(@src,'app')]");
	By framed = By.xpath("//frame[contains(@src,'/tiendas')][@name=\"framedatos\"]");// frameoper
	By datos = By.xpath("//form[@name='datos']");

	By BtnSegmentoTienda = By.xpath("//input[@name=\"segmento_tienda\"]");
	By btnSegTiendaTd = By.xpath("//form/table[1]/tbody/tr[2]/td[3]/table/tbody/tr[2]/td[2]");
	By btnAceptarPantallaNueva = By.xpath("//input[@value=\"Aceptar\"]");
	By btnAsignar = By.name("segmento_tienda");

	By p_segmento_maestro = By.xpath("//select[@name=\"p_segmento_maestro_id\"]");
	By p_potencial = By.xpath("//select[@id=\"p_potencial_id_1\"]");
	By p_potencial_2 = By.xpath("//select[@id=\"p_potencial_id_2\"]");
	By p_potencial_3 = By.xpath("//select[@id=\"p_potencial_id_3\"]");
	By p_potencial_4= By.xpath("//select[@id=\"p_potencial_id_4\"]");
	By potencial = By.xpath("//select[@name = 'p_potencial_id']");
	
	By p_overlay_1= By.xpath("//select[@id=\"p_overlay_id_1\"]");
	By p_overlay_2= By.xpath("//select[@id=\"p_overlay_id_2\"]");
	By p_overlay_4= By.xpath("//select[@id=\"p_overlay_id_4\"]");
	By p_overlay_3= By.xpath("//select[@id=\"p_overlay_id_3\"]");
	
	By p2_table = By.xpath("//input[@name='p_segmento_maestro']//ancestor::table[1]");
	By p2_segmento_maestro= By.xpath("//input[@name=\"p_segmento_maestro\"]");
	By p2_potencial_1= By.xpath("/html/body/form/table[1]/tbody/tr[2]/td[3]/table/tbody/tr[2]/td[3]/table//tr[1]/td[2]/input[1]");
	By p2_potencial_2= By.xpath("/html/body/form/table[1]/tbody/tr[2]/td[3]/table/tbody/tr[2]/td[3]/table//tr[2]/td[2]/input[1]");
	By p2_potencial_3 = By.xpath("/html/body/form/table[1]/tbody/tr[2]/td[3]/table/tbody/tr[2]/td[3]/table//tr[3]/td[2]/input[1]");
	By p2_potencial_4= By.xpath("/html/body/form/table[1]/tbody/tr[2]/td[3]/table/tbody/tr[2]/td[3]/table//tr[4]/td[2]/input[1]");
	By p2_overlay_1= By.xpath("/html/body/form/table[1]/tbody/tr[2]/td[3]/table/tbody/tr[2]/td[3]/table//tr[1]/td[4]/input[1]");
	By p2_overlay_2= By.xpath("/html/body/form/table[1]/tbody/tr[2]/td[3]/table/tbody/tr[2]/td[3]/table//tr[2]/td[4]/input[1]");
	By p2_overlay_3= By.xpath("/html/body/form/table[1]/tbody/tr[2]/td[3]/table/tbody/tr[2]/td[3]/table//tr[3]/td[4]/input[1]");
	By p2_overlay_4= By.xpath("/html/body/form/table[1]/tbody/tr[2]/td[3]/table/tbody/tr[2]/td[3]/table//tr[4]/td[4]/input[1]");
	
	
	/*
	 * Localizadores para segmento tienda sin boton asignar
	 *****************************************************************************************/
	By p_segmento_maestro1 = By.xpath("//input[@name=\"p_segmento_maestro\"]");
	By p_potencial1 = By.xpath("//input[@id=\"p_potencial_1\"]");
	By p_potencial_21 = By.xpath("//input[@id=\"p_potencial_2\"]");
	By p_potencial_31 = By.xpath("//input[@id=\"p_potencial_4\"]");
	By p_potencial_41 = By.xpath("//input[@id=\"p_potencial_3\"]");
	By p_overlay_11 = By.xpath("//input[@id=\"p_overlay_1\"]");
	By p_overlay_21 = By.xpath("//input[@id=\"p_overlay_2\"]");
	By p_overlay_41 = By.xpath("//input[@id=\"p_overlay_3\"]");
	By p_overlay_31 = By.xpath("//input[@id=\"p_overlay_4\"]");
	
	/*
	 * Localizadores para Diferenciador
	 *****************************************************************************************/
	By segmentoMaestro = By.xpath("/html/body/form/table[1]/tbody/tr[2]/td[3]/table/tbody/tr[1]/td[4]/input[1]");
	By potencialHogar = By.xpath("//*[@id=\"p_potencial_1\"]");
	By potencialComida = By.xpath("//*[@id=\"p_potencial_2\"]");
	By potencialDesayuno = By.xpath("//*[@id=\"p_potencial_3\"]");
	By potencialPbaNvaNece = By.xpath("//*[@id=\"p_potencial_4\"]");
	By overlayHogar = By.xpath("//*[@id=\"p_overlay_1\"]");
	By overlayComida = By.xpath("//*[@id=\"p_overlay_2\"]");
	By overlayDesayuno = By.xpath("//*[@id=\"p_overlay_3\"]");
	By overlayPbaNvaNece = By.xpath("//*[@id=\"p_overlay_4\"]");
	
	By tituloVentanaSegmentoTienda = By.xpath("//head/title[contains(text(),'Segmento de Tienda')]");
	
	By segmentoMaestroFrame = By.xpath("//*[@id=\"p_segmento_maestro_id\"]");
	By potencialHogarFrame = By.xpath("//*[@id=\"p_potencial_id_1\"]");
	By potencialComidaFrame = By.xpath("//*[@id=\"p_potencial_id_2\"]");
	By potencialDesayunoFrame = By.xpath("//*[@id=\"p_potencial_id_4\"]");
	By potencialPbaNvaNeceFrame = By.xpath("//*[@id=\"p_potencial_id_3\"]");
	By overlayHogarFrame = By.xpath("//*[@id=\"p_overlay_id_1\"]");
	By overlayComidaFrame = By.xpath("//*[@id=\"p_overlay_id_2\"]");
	By overlayDesayunoFrame = By.xpath("//*[@id=\"p_overlay_id_4\"]");
	By overlayPbaNvaNeceFrame = By.xpath("//*[@id=\"p_overlay_id_3\"]");

	/*
	 * Localizadores Formulario Datos de OP
	 *****************************************************************************************/

	By cot = By.xpath("//select[@name=\"p_lid_caracteristica_operacion\"]");
	By BtnActualizar = By.xpath("//input[@value=\"Actualizar\"]");

	By xmlContainer = By.xpath("//*[@id=\"content-nav\"]");
	By xmlPEP = By.xpath("//*[@id=\"tituloInicio\"]");
	
	// Localizador del boton asignar del segmento de tienda
	By asignar_segmentoTienda = By.xpath("//input[@value='Asignar']");
	By cancelar_segmentoTienda = By.xpath("//input[@value='Cancelar']");
	By aceptar_segmentoTienda = By.xpath("//input[@value='Aceptar']");	

	/**
	 * Metodo para seleccionar el Frame correcto y dar clic a Formato y Entorno
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return N/A
	 */
	public void FormatoyEntorno() {
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
		u.highLight(FyE);

		u.click(FyE);
		testCase.addScreenShotCurrentStep(u, "DatosGen");

		u.getDriver().switchTo().window(winHandleBefore);
		u.waitForLoadPage();
		u.hardWait(2);

	}
	
	/**
	 * Metodo para validar que se esta en la pantalla de Formato y Entorno
	 * @author Andrea Contreras
	 * @return true o false
	 */
	public boolean validarFormatoyEntorno() {
        boolean exist = false;
        
        me.explicitWaitVisibilityOfElement(tituloPantalla, Duration.ofSeconds(10));
        if(u.getText(tituloPantalla).equals("FORMATO Y ENTORNO")) {
        	u.scrollToElement(tituloPantalla);
        	u.highLight(tituloPantalla);
        	testCase.addScreenShotCurrentStep("FormatoEntorno");
        	u.unhighLight(tituloPantalla);
        	exist = true;
        }
        return exist;
	}


	/**
	 * Metodo para seleccionar check box de permisos y contratos> formas de pago
	 * 
	 * @author Baltazar Lugo
	 * @param String cheque, String dolares, String efectivo,String TDD1,String
	 *               TDC1,String vales
	 * @return N/A
	 */
	public void TamanoTienda(String tam) {
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
		Select tama = new Select(u.getDriver().findElement(TamTienda));
		tama.selectByVisibleText(tam);
		u.highLight(TamTienda);
		testCase.addScreenShotCurrentStep(u, "tamanoTienda");
//		u.getDriver().switchTo().window(winHandleBefore);
//		u.waitForLoadPage();

	}

	/**
	 * Metodo para seleccionar select tipo de segemento
	 * 
	 * @author Baltazar Lugo
	 * @param seg (data provider)
	 * @return NA
	 */
	public void Segmento(String seg) {
		Select segm = new Select(u.getDriver().findElement(Segmento));
		u.highLight(Segmento);
		segm.selectByVisibleText(seg);
		testCase.addScreenShotCurrentStep(u, "Segmento");

	}
	
	public void clickBtnAsignar() {
		u.highLight(btnAsignar);
		u.click(btnAsignar);
		//testCase.addScreenShotCurrentStep(u, "click Asignar");

		

	}
	/**
	 * Metodo para ver que el segmento tenga opciones
	 * 
	 * @author Baltazar Lugo
	 * @param seg (data provider)
	 * @return NA
	 */
	public String SegmentoOpciones() {
	//	Select segm = new Select(u.getDriver().findElement(Segmento));
		u.highLight(Segmento);
		String text= u.getDriver().findElement(Segmento).getText();
		testCase.addBoldTextEvidenceCurrentStep(text+" son las opciones encontradas");
		u.getDriver().findElement(Segmento).click();
		testCase.addScreenShotCurrentStep(u, "Segmento");
		u.getDriver().findElement(Segmento).click();
		return text;
	}

	/**
	 * Metodo para ver si el boton asignar esta activado
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return boolean
	 */
	public boolean ElementBtnAsingar() {

		boolean enable = false;
		if (u.getDriver().findElement(BtnSegmentoTienda).isDisplayed()
				&& u.getDriver().findElement(BtnSegmentoTienda).isEnabled()) {
			enable = true;
			u.highLight(BtnSegmentoTienda);
			testCase.addScreenShotCurrentStep(u, "COasignar");
			testCase.addBoldTextEvidenceCurrentStep("El Boton esta Activado");

		} else {
			
			enable = false;
			testCase.addScreenShotCurrentStep(u, "COasignar2");
			testCase.addBoldTextEvidenceCurrentStep("El Boton no esta Activado");
		}

		return enable;
	}
	
	public Boolean validarBtnAsignarActivo() {
		Boolean exist = false;
		if(me.isEnable(btnAsignar)) {
			exist = true;
			u.highLight(btnAsignar);
			testCase.addScreenShotCurrentStep(u, "validarBotonAsignar");
			u.unhighLight(btnAsignar);
		} 	
		return exist;
		
	}	
	
	
	
	public Boolean validarSegmentoVacio() {
		Boolean exist = false;
		String option ="";
		//option.getText().equals("Seleccione una opci�n")
		if(me.isEnable(btnAsignar)) {
			exist = true;
			u.highLight(btnAsignar);
			testCase.addScreenShotCurrentStep(u, "validarBotonespaginado");
			u.unhighLight(btnAsignar);		
		} 	
		return exist;
		
	}
	
	
	
	
	
	/**
	 * Metodo para seleccionar una opcion del campo 'Subtipo Ubicacion Tienda'
	 * @author Andrea Contreras
	 * @param opcion (String): opcion a seleccionar
	 */
	public void SubtipoUbicacionTienda(String opcion) {
		Select segm = new Select(u.getDriver().findElement(SubtipoUbiTienda));
		u.highLight(SubtipoUbiTienda);
		segm.selectByVisibleText(opcion);
		testCase.addScreenShotCurrentStep(u, "SubtipoUbiTienda");

	}

	/**
	 * Metodo para ver si los campos estan diferente de disabled y diferentes de NA
	 * en pantalla del menu -Segmento de Tienda -Segmento Maestro -Potencial Hogar
	 * -Potencial Comida -Potencial Desayuno -Overlay Hogar -Overlay Comida -Overlay
	 * Desayuno
	 *
	 * @author Alexis Garcia
	 * @param NA
	 * @return NA
	 */
	public boolean ElementDisConValidacionDeTexto1() {

		String na = "NA";

		boolean enable = false;
		if (u.getDriver().findElement(p_segmento_maestro1).isDisplayed()
				&& u.getDriver().findElement(p_potencial1).isDisplayed()
				&& u.getDriver().findElement(p_potencial_21).isDisplayed()
				&& u.getDriver().findElement(p_potencial_31).isDisplayed()
				&& u.getDriver().findElement(p_potencial_41).isDisplayed()
				&& u.getDriver().findElement(p_overlay_21).isDisplayed()
				&& u.getDriver().findElement(p_overlay_11).isDisplayed()
				&& u.getDriver().findElement(p_overlay_31).isDisplayed()
				&& u.getDriver().findElement(p_overlay_41).isDisplayed()) {

			u.highLight(p_segmento_maestro1);
			u.highLight(p_potencial1);
			u.highLight(p_potencial_21);
			u.highLight(p_potencial_31);
			u.highLight(p_potencial_41);
			u.highLight(p_overlay_21);
			u.highLight(p_overlay_11);
			u.highLight(p_overlay_31);
			u.highLight(p_overlay_41);

			if (u.getDriver().findElement(p_segmento_maestro1).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_potencial1).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_potencial_21).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_potencial_31).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_potencial_41).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_overlay_21).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_overlay_11).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_overlay_31).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_overlay_41).getAttribute("value").equals(na)) {

				enable = true;
				testCase.addScreenShotCurrentStep(u, "CORREn111");
				testCase.addBoldTextEvidenceCurrentStep(
						"El sistema muestra la pantalla de \"Formato y Entorno\" con sus campos correspondientes y mostrara los nuevos campos correspondientes a \"Segmento de Tienda\" de:\r\n"
								+ "    -Segmento de Tienda \r\n" + "    -Segmento Maestro\r\n"
								+ "    -Potencial Hogar\r\n" + "    -Potencial Comida\r\n"
								+ "    -Potencial Desayuno\r\n" + "    -Overlay Hogar\r\n" + "    -Overlay Comida\r\n"
								+ "    -Overlay Desayuno ");

			}
			String p_segmento_maestro01 = u.getDriver().findElement(p_segmento_maestro1).getAttribute("value");
			System.out.print("p_digito_verif: " + p_segmento_maestro01);
			String p_potencial01 = u.getDriver().findElement(p_potencial1).getAttribute("value");
			System.out.print("\np_potencial: " + p_potencial01);
			String p_potencial_021 = u.getDriver().findElement(p_potencial_21).getAttribute("value");
			System.out.print("\np_digito_verif2: " + p_potencial_021);
			String p_potencial_031 = u.getDriver().findElement(p_potencial_31).getAttribute("value");
			System.out.print("\np_digito_verif3: " + p_potencial_031);
			String p_potencial_041 = u.getDriver().findElement(p_potencial_41).getAttribute("value");
			System.out.print("\np_digito_verif4: " + p_potencial_041);
			String p_overlay_021 = u.getDriver().findElement(p_overlay_21).getAttribute("value");
			System.out.print("\np_overlay_21: " + p_overlay_021);
			String p_overlay_031 = u.getDriver().findElement(p_overlay_31).getAttribute("value");
			System.out.print("\np_overlay_31: " + p_overlay_031);
			String p_overlay_011 = u.getDriver().findElement(p_overlay_11).getAttribute("value");
			System.out.print("\np_overlay_11: " + p_overlay_011);
			String p_overlay_041 = u.getDriver().findElement(p_overlay_41).getAttribute("value");
			System.out.print("\np_overlay_41: " + p_overlay_041 + "\n");

//			testCase.addScreenShotCurrentStep(u, "CORREn111");
//			testCase.addBoldTextEvidenceCurrentStep(
//					"El sistema muestra la pantalla de \"Formato y Entorno\" con sus campos correspondientes y mostrara los nuevos campos correspondientes a \"Segmento de Tienda\" de:\r\n"
//							+ "    -Segmento de Tienda \r\n" + "    -Segmento Maestro\r\n" + "    -Potencial Hogar\r\n"
//							+ "    -Potencial Comida\r\n" + "    -Potencial Desayuno\r\n" + "    -Overlay Hogar\r\n"
//							+ "    -Overlay Comida\r\n" + "    -Overlay Desayuno ");
		} else {

			enable = false;
			testCase.addBoldTextEvidenceCurrentStep("No Existe");
		}

		return enable;
	}
	/**
	 * Metodo para verificar la nomenclatura del potencial hogar
	 *
	 * @author Alexis Garcia
	 * @param overlay (String) que recibe el potencial a verificar
	 * @return true/false
	 */
	
	public boolean verificadorDeNomenclaturaPotencial(String nomen) {
		Boolean verificador = false;
		
		if (u.getDriver().findElement(p_potencial).isDisplayed()) {
			System.out.println("is displayed");
			u.highLight(p_potencial);
			Select ub2 = new Select(u.getDriver().findElement(p_potencial));
			u.highLight(p_potencial);
			ub2.selectByVisibleText(nomen);	
			u.hardWait(4);
			System.out.println("\np_potencial: " + ub2.getFirstSelectedOption().getText());
			if(ub2.getFirstSelectedOption().getText().equals(nomen)) {
				testCase.addScreenShotCurrentStep(u, "CORREn111");
				verificador =  true;
				
				System.out.println("\np_potencial: " + ub2.getFirstSelectedOption().getText());
			}
			
		}
		return verificador;
	}
	
	/**
	 * Metodo para verificar la nomenclatura del ultimo potencial
	 * @author Andrea Contreras
	 * @param overlay (String) que recibe el potencial a verificar
	 * @return true/false
	 */	
	public boolean verificadorDeNomenclaturaUltimoPotencial(String nomen) {
		Boolean verificador = false;
		
		me.explicitWaitVisibilityOfElement(potencial, Duration.ofSeconds(10));
		List<WebElement> allOptions = u.getDriver().findElements(potencial);
		
		System.out.println("is displayed");
		me.highlight(allOptions.get(allOptions.size()-1));
		Select ub2 = new Select(allOptions.get(allOptions.size()-1));
		ub2.selectByVisibleText(nomen);	
		u.hardWait(4);
		System.out.println("\np_potencial: " + ub2.getFirstSelectedOption().getText());
		
		if(ub2.getFirstSelectedOption().getText().equals(nomen)) {
			testCase.addScreenShotCurrentStep(u, "nomenclatura");
			verificador =  true;
			
			System.out.println("\np_potencial: " + ub2.getFirstSelectedOption().getText());
		}
		return verificador;
	}
	
	
	/**
	 * Metodo para verificar que las opciones de overlay del popup de segmento tienda 
	 * contengan algun dato o no y cuales son(no los regresa),
	 *  independientemente si se le asigno algun valor a algun campo potencial
	 * , por defecto siempre contiene un campo vacio
	 *
	 * @author Alexis Garcia
	 * @param overlay (String) que recibe el tipo de overlay completo por ejemplo (Overlay Hogar)
	 * @return true/false
	 */
	public boolean verificadorDeOverlays(String overlay) {
		u.hardWait(5);

		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);

		u.getDriver().switchTo().window(sq.get(2));
		u.getDriver().manage().window().maximize();
		boolean tiene = false;
		switch (overlay.toLowerCase()) {

		case "overlay hogar":

			u.hardWait(3);
			Select xpths = new Select(u.getDriver().findElement(p_overlay_1));
			List<WebElement> opciones = xpths.getOptions();
			System.out.println("tamaÃ±o de la lista: " + opciones.size());

			if (opciones.size() == 1) {
				if(u.getDriver().findElement(p_overlay_1).getAttribute("value").isEmpty()) {
					tiene = false;
					u.highLight(p_overlay_1);
					u.click(p_overlay_1);
					testCase.addBoldTextEvidenceCurrentStep("La opcion Overlay Hogar no contiene datos");
					testCase.addScreenShotCurrentStep(u, "OverlayHogar");
				}
				
			} else {
				tiene = true;
				u.highLight(p_overlay_1);
				u.click(p_overlay_1);
				testCase.addBoldTextEvidenceCurrentStep("La opcion Overlay Hogar si contiene datos");
				testCase.addScreenShotCurrentStep(u, "OverlayHogar");
				for (int j = 0; j < opciones.size(); j++) {
					System.out.println("opcion: " + " " + j +opciones.get(j).getText());
				}

			}

			break;

		case "overlay comida":

			u.hardWait(3);
			xpths = new Select(u.getDriver().findElement(p_overlay_2));
			opciones = xpths.getOptions();

			if (opciones.size() == 1) {
				if(u.getDriver().findElement(p_overlay_2).getAttribute("value").isEmpty()) {
					tiene = false;
					u.highLight(p_overlay_2);
					u.click(p_overlay_2);
					testCase.addBoldTextEvidenceCurrentStep("La opcion Overlay Hogar no contiene datos");
					testCase.addScreenShotCurrentStep(u, "OverlayHogar");
				}
			} else {
				tiene = true;
				u.highLight(p_overlay_2);
				u.click(p_overlay_2);
				testCase.addBoldTextEvidenceCurrentStep("La opcion Overlay Hogar si contiene datos");
				testCase.addScreenShotCurrentStep(u, "OverlayHogar");
				for (int j = 0; j < opciones.size(); j++) {
					System.out.println("opcion: " + " " + j +opciones.get(j).getText());
				}

			}
			break;

		case "overlay desayuno":

			u.hardWait(3);
			xpths = new Select(u.getDriver().findElement(p_overlay_4));
			opciones = xpths.getOptions();

			if (opciones.size() == 1) {
				if(u.getDriver().findElement(p_overlay_4).getAttribute("value").isEmpty()) {
					tiene = false;
					u.highLight(p_overlay_4);
					u.click(p_overlay_4);
					testCase.addBoldTextEvidenceCurrentStep("La opcion Overlay Hogar no contiene datos");
					testCase.addScreenShotCurrentStep(u, "OverlayHogar");
				}
			} else {
				tiene = true;
				u.highLight(p_overlay_4);
				u.click(p_overlay_4);
				testCase.addBoldTextEvidenceCurrentStep("La opcion Overlay Hogar si contiene datos");
				testCase.addScreenShotCurrentStep(u, "OverlayHogar");
				for (int j = 0; j < opciones.size(); j++) {
					System.out.println("opcion: " + " " + j + opciones.get(j).getText());
				}

			}
			break;

		case "overlay pba nva nece":

			u.hardWait(3);
			xpths = new Select(u.getDriver().findElement(p_overlay_3));
			opciones = xpths.getOptions();

			if (opciones.size() == 1) {
				if(u.getDriver().findElement(p_overlay_3).getAttribute("value").isEmpty()) {
					tiene = false;
					u.highLight(p_overlay_3);
					u.click(p_overlay_3);
					testCase.addBoldTextEvidenceCurrentStep("La opcion Overlay Hogar no contiene datos");
					testCase.addScreenShotCurrentStep(u, "OverlayHogar");
				}
			} else {
				tiene = true;
				u.highLight(p_overlay_3);
				u.click(p_overlay_3);
				testCase.addBoldTextEvidenceCurrentStep("La opcion Overlay Hogar si contiene datos");
				testCase.addScreenShotCurrentStep(u, "OverlayHogar");
				for (int j = 0; j < opciones.size(); j++) {
					System.out.println("opcion: " + " " + j + opciones.get(j).getText());
				}

			}
			break;
			
			default:
				System.out.println("No existe el overlay especificado");
				break;
		}
		return tiene;
	}
	/**
	 * Metodo para ver si los campos estan diferente de disabled y diferentes de NA
	 * en pantalla del menu -Segmento de Tienda -Segmento Maestro -Potencial Hogar
	 * -Potencial Comida  -Overlay Hogar -Overlay Comida 
	 *
	 * @author Alexis Garcia
	 * @param NA
	 * @return NA
	 */
	public boolean ElementDisConValidacionDeTexto() {

		String na = "NA";

		boolean enable = false;
		if (u.getDriver().findElement(p_segmento_maestro1).isDisplayed()
				&& u.getDriver().findElement(p_potencial1).isDisplayed()
				&& u.getDriver().findElement(p_potencial_21).isDisplayed()
				&& u.getDriver().findElement(p_potencial_41).isDisplayed()
				&& u.getDriver().findElement(p_overlay_21).isDisplayed()
				&& u.getDriver().findElement(p_overlay_11).isDisplayed()
				&& u.getDriver().findElement(p_overlay_41).isDisplayed()) {

			u.highLight(p_segmento_maestro1);
			u.highLight(p_potencial1);
			u.highLight(p_potencial_21);
			u.highLight(p_potencial_41);
			u.highLight(p_overlay_21);
			u.highLight(p_overlay_11);
			u.highLight(p_overlay_41);

			if (u.getDriver().findElement(p_segmento_maestro1).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_potencial1).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_potencial_21).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_potencial_41).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_overlay_21).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_overlay_11).getAttribute("value").equals(na)
					&& u.getDriver().findElement(p_overlay_41).getAttribute("value").equals(na)) {

				enable = true;
				testCase.addScreenShotCurrentStep(u, "CORREn111");
				testCase.addBoldTextEvidenceCurrentStep(
						"El sistema muestra la pantalla de \"Formato y Entorno\" con sus campos correspondientes y mostrara los nuevos campos correspondientes a \"Segmento de Tienda\" de:\r\n"
								+ "    -Segmento de Tienda \r\n" + "    -Segmento Maestro\r\n"
								+ "    -Potencial Hogar\r\n" + "    -Potencial Comida\r\n"
								+ "    -Potencial Desayuno\r\n" + "    -Overlay Hogar\r\n" + "    -Overlay Comida\r\n"
								+ "    -Overlay Desayuno ");

			}
			String p_segmento_maestro01 = u.getDriver().findElement(p_segmento_maestro1).getAttribute("value");
			System.out.print("p_digito_verif: " + p_segmento_maestro01);
			String p_potencial01 = u.getDriver().findElement(p_potencial1).getAttribute("value");
			System.out.print("\np_potencial: " + p_potencial01);
			String p_potencial_021 = u.getDriver().findElement(p_potencial_21).getAttribute("value");
			System.out.print("\np_digito_verif2: " + p_potencial_021);
			String p_potencial_041 = u.getDriver().findElement(p_potencial_41).getAttribute("value");
			System.out.print("\np_digito_verif4: " + p_potencial_041);
			String p_overlay_021 = u.getDriver().findElement(p_overlay_21).getAttribute("value");
			System.out.print("\np_overlay_21: " + p_overlay_021);
			String p_overlay_011 = u.getDriver().findElement(p_overlay_11).getAttribute("value");
			System.out.print("\np_overlay_11: " + p_overlay_011);
			String p_overlay_041 = u.getDriver().findElement(p_overlay_41).getAttribute("value");
			System.out.print("\np_overlay_41: " + p_overlay_041 + "\n");

			
		} else {

			enable = false;
			testCase.addBoldTextEvidenceCurrentStep("No Existe");
		}

		return enable;
	}

	
	/**
	 * Metodo para dar clic en el boton asignar en segmento de tienda
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void ClicBtbSegmentoTienda() {

		u.highLight(BtnSegmentoTienda);
		testCase.addScreenShotCurrentStep("Segmentotiensa");
		u.click(BtnSegmentoTienda);

	}

	/**
	 * Metodo para quitar alert despues del boton segmento de tienda
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void alertSegmentoTienda() {
		u.hardWait(6);
		String ventanaE = u.getDriver().switchTo().alert().getText();
		String str2 = "Si realizo cambios en la informaci�n, debe de guardar antes de Asignar el Segmento de Tienda. �Desea continuar? ";
		String str1 = "Si realiz� cambios en la informaci�n, debe de guardar antes de Asignar el Segmento de Tienda. �Desea continuar?";
		if (ventanaE.equals(str2) || ventanaE.equals(str1)) {
			testCase.addScreenShotCurrentStep("RespuestaPOP");
			u.getDriver().switchTo().alert().accept();
		} else {
			System.out.println("No se muestra el mensaje 'Si realiza cambios en la informacion, debe de guardar antes de Asignar el Segmento de Tienda. �Desea continuar?'");
			testCase.addScreenShotCurrentStep("RespuestaPOP");
			Assert.fail("No se muestra la alerta");
		}
		
	}
	
	
	
	/**
	 * Metodo para quitar alert despues del boton segmento de tienda
	 * 
	 * @author Alfonso Alan Oyervides
	 * @param NA
	 * @return NA
	 */
	public void alertSegmentoTienda2() {
		u.hardWait(6);
		String ventanaE = u.getDriver().switchTo().alert().getText();
		String str2 = "Si realizÃ³ cambios en la informaciÃ³n, debe de guardar antes de Asignar el Segmento de Tienda. Â¿Desea continuar?";
		if (ventanaE.equals(str2)) {
			testCase.addScreenShotCurrentStep("RespuestaPOP");

			u.getDriver().switchTo().alert().accept();
		} else {
			System.out.println(
					"Si realizÃ³ cambios en la informaciÃ³n, debe de guardar antes de Asignar el Segmento de Tienda. Â¿Desea continuar?");
		}
	}
	
	public boolean alerta() {
		Boolean exist = false;

		try {

			Alert alert = u.getDriver().switchTo().alert();
			testCase.addScreenShotCurrentStep("confirmarAlerta1");
			alert.accept();
			testCase.addScreenShotCurrentStep("confirmarAlerta2");
			exist = true;

		} catch (Exception e) {
			exist = false;
		}
		return exist;
	}
	
	/**
	 * Metodo para seleccionar select tipo de ubicacion
	 * 
	 * @author Baltazar Lugo
	 * @param ubi (data provider)
	 * @return NA
	 */
	public void Ubicacion(String ubi) {
		Select ub = new Select(u.getDriver().findElement(Ubi));
		u.highLight(Ubi);
		ub.selectByVisibleText(ubi);
		testCase.addScreenShotCurrentStep(u, "Ubicacion1");

	}
	/**
	 * Metodo para cambairse a la 2 pantalla
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void cambiar_ventana() {

		u.hardWait(10);
		// String parent=u.getDriver().getWindowHandle();

		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);

		u.getDriver().switchTo().window(sq.get(1));
		System.out.println(u.getDriver().getCurrentUrl());
		// u.getDriver().switchTo().frame("datos");
		u.getDriver().manage().window().maximize();
		// u.getDriver().switchTo().frame("datos");
		}
	
	/**
	 * Metodo para cambairse a la x pantalla
	 * @author Baltazar Lugo & David Valdez
	 * @param int: numpantalla - El identificador de la pantalla a cambiar
	 * @return NA
	 */
	public void cambiar_ventana(int numpantalla) {

		u.hardWait(10);
		// String parent=u.getDriver().getWindowHandle();

		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);

		u.getDriver().switchTo().window(sq.get(numpantalla));
		System.out.println(u.getDriver().getCurrentUrl());
		// u.getDriver().switchTo().frame("datos");
		u.getDriver().manage().window().maximize();
		// u.getDriver().switchTo().frame("datos");
		}
	/**
	 * Metodo para ver si los campos estan diferente de disabled en pantalla nueva
	 * -Segmento de Tienda -Segmento Maestro -Potencial Hogar -Potencial Comida
	 * -Potencial Desayuno -Overlay Hogar -Overlay Comida -Overlay Desayuno
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public boolean ElementDis() {

		u.hardWait(10);
		// String parent=u.getDriver().getWindowHandle();

		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);

		u.getDriver().switchTo().window(sq.get(2));

		// u.getDriver().switchTo().frame("datos");
		u.getDriver().manage().window().maximize();
		// u.getDriver().switchTo().frame("datos");
		boolean enable = false;

		if (u.getDriver().findElement(p_segmento_maestro).isDisplayed()
				&& u.getDriver().findElement(p_potencial).isDisplayed()
				&& u.getDriver().findElement(p_potencial_2).isDisplayed()
				&& u.getDriver().findElement(p_potencial_4).isDisplayed()
				&& u.getDriver().findElement(p_overlay_2).isDisplayed()
				&& u.getDriver().findElement(p_overlay_1).isDisplayed()
				&& u.getDriver().findElement(p_overlay_4).isDisplayed()) {
			enable = true;
			u.highLight(p_segmento_maestro);
			u.highLight(p_potencial);
			u.highLight(p_potencial_2);
			u.highLight(p_potencial_4);
			u.highLight(p_overlay_2);
			u.highLight(p_overlay_1);
			u.highLight(p_overlay_4);
			testCase.addScreenShotCurrentStep(u, "CORREn111");
			testCase.addBoldTextEvidenceCurrentStep(
					"El sistema muestra la pantalla de \"Formato y Entorno\" con sus campos correspondientes y mostrara los nuevos campos correspondientes a \"Segmento de Tienda\" de:\r\n"
							+ "	-Segmento de Tienda \r\n" + "	-Segmento Maestro\r\n" + "	-Potencial Hogar\r\n"
							+ "	-Potencial Comida\r\n" + "	-Potencial Desayuno\r\n" + "	-Overlay Hogar\r\n"
							+ "	-Overlay Comida\r\n" + "	-Overlay Desayuno ");
		} else {

			enable = false;
			testCase.addBoldTextEvidenceCurrentStep("No Existe");
		}

		return enable;
	}
	
	/**
	 * Metodo para ver si estan las opciones en elementos segmento maestro
	 * 
	 * @author Alfonso Alan Oyervides
	 * @param NA
	 * @return NA
	 */
	public void ElementosSegmentoMaestro(String clasico, String barrio, String hogar, String Oficinista, String paradaT) {

		// String parent=u.getDriver().getWindowHandle();

//				Set<String> s = u.getDriver().getWindowHandles();
//				List<String> sq = new ArrayList<>(s);
//
//				u.getDriver().switchTo().window(sq.get(2));
		Select ub = new Select(u.getDriver().findElement(p_segmento_maestro));
//		u.highLight(p_overlay_1);
		ub.selectByVisibleText(clasico);
		testCase.addScreenShotCurrentStep(u, "Diferenciador");
		Select ub1 = new Select(u.getDriver().findElement(p_segmento_maestro));
		ub1.selectByVisibleText(barrio);
		testCase.addScreenShotCurrentStep(u, "Diferenciador1");
		Select ub2 = new Select(u.getDriver().findElement(p_segmento_maestro));
		ub2.selectByVisibleText(hogar);
		testCase.addScreenShotCurrentStep(u, "Diferenciador2");
		Select ub3 = new Select(u.getDriver().findElement(p_segmento_maestro));
		ub3.selectByVisibleText(Oficinista);
		testCase.addScreenShotCurrentStep(u, "Diferenciador3");
		Select ub4 = new Select(u.getDriver().findElement(p_segmento_maestro));
		ub4.selectByVisibleText(paradaT);
		testCase.addScreenShotCurrentStep(u, "Diferenciador4");
		//Select ub5 = new Select(u.getDriver().findElement(p_potencial));
		//ub5.selectByVisibleText(barrio);
		testCase.addTextEvidenceCurrentStep(
		"Estan las opciones: 	-Clasico" + "	-Barrio Competido" + "	-Hogar reunion" + "-Oficinistas" + "  -Parada Tecnica");

	}

	/**
	 * Metodo para ver si estan las opciones en elementos potencial hogar
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void ElementosPotencialHogar(String bajo, String medio, String alto) {

		// String parent=u.getDriver().getWindowHandle();

//				Set<String> s = u.getDriver().getWindowHandles();
//				List<String> sq = new ArrayList<>(s);
//
//				u.getDriver().switchTo().window(sq.get(2));
		Select ub = new Select(u.getDriver().findElement(p_potencial));
//		u.highLight(p_overlay_1);
		ub.selectByVisibleText(bajo);
		testCase.addScreenShotCurrentStep(u, "Diferenciador");
		Select ub1 = new Select(u.getDriver().findElement(p_potencial));
		ub1.selectByVisibleText(medio);
		testCase.addScreenShotCurrentStep(u, "Diferenciador1");
		Select ub2 = new Select(u.getDriver().findElement(p_potencial));
		ub2.selectByVisibleText(alto);
		testCase.addScreenShotCurrentStep(u, "Diferenciador2");

		testCase.addTextEvidenceCurrentStep(
				"Estan las opciones: 	-Hogar_Alto\r\n" + "	-Hogar_Medio\r\n" + "	-Hogar_Bajo");

	}

	/**
	 * Metodo para seleccionar una opcion de potencial hogar
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void ElementosPotencialHogar1opcion(String opcion) {

		Select ub = new Select(u.getDriver().findElement(p_potencial));
//		u.highLight(p_overlay_1);
		ub.selectByVisibleText(opcion);
		testCase.addScreenShotCurrentStep(u, "Diferenciador123");

	}

	/**
	 * Metodo para seleccionar una opcion de potencial comida
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void ElementosPotencialDesayuno1opcion(String opcion) {

		Select ub = new Select(u.getDriver().findElement(p_potencial_4));
//		u.highLight(p_overlay_1);
		ub.selectByVisibleText(opcion);
		testCase.addScreenShotCurrentStep(u, "Diferenciador123c");

	}

	/**
	 * Metodo para seleccionar una opcion de potencial comida
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void ElementosPotencialComida1opcion(String opcion) {

		Select ub = new Select(u.getDriver().findElement(p_potencial_2));
//		u.highLight(p_overlay_1);
		ub.selectByVisibleText(opcion);
		testCase.addScreenShotCurrentStep(u, "Diferenciador123c");

	}

	/**
	 * Metodo para ver las opciones de overlay hogar
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void OpcionesOverlayHogar(String o1, String o2, String o3) {

		if (o1 != null) {
			Select ub = new Select(u.getDriver().findElement(p_overlay_1));
//	u.highLight(p_overlay_1);
			ub.selectByVisibleText(o1);
			testCase.addScreenShotCurrentStep(u, "Diferenciadoroi11");
		}

		if (o2 != null) {

			Select ub1 = new Select(u.getDriver().findElement(p_overlay_1));
//		u.highLight(p_overlay_1);
			ub1.selectByVisibleText(o2);
			testCase.addScreenShotCurrentStep(u, "Diferenciadoroi11");

		}
		if (o3 != null) {
			Select ub2 = new Select(u.getDriver().findElement(p_overlay_1));
//		u.highLight(p_overlay_1);
			ub2.selectByVisibleText(o3);
			testCase.addScreenShotCurrentStep(u, "Diferenciadoroi111");
		}

	}

	/**
	 * Metodo para ver las opciones de overlay comida
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void OpcionesOverlayComida(String o1, String o2, String o3) {

		if (o1 != null) {
			Select ub = new Select(u.getDriver().findElement(p_overlay_2));
//	u.highLight(p_overlay_1);
			ub.selectByVisibleText(o1);
			testCase.addScreenShotCurrentStep(u, "Diferenciadoroi11o");
		}

		if (o2 != null) {

			Select ub1 = new Select(u.getDriver().findElement(p_overlay_2));
//		u.highLight(p_overlay_1);
			ub1.selectByVisibleText(o2);
			testCase.addScreenShotCurrentStep(u, "Diferenciadoroi11o");

		}
		if (o3 != null) {
			Select ub2 = new Select(u.getDriver().findElement(p_overlay_2));
//		u.highLight(p_overlay_1);
			ub2.selectByVisibleText(o3);
			testCase.addScreenShotCurrentStep(u, "Diferenciadoroi111o");
		}
	}

	/**
	 * Metodo para ver las opciones de overlay desayuno
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void OpcionesOverlayDesayuno(String o1, String o2, String o3) {

		if (o1 != null) {
			Select ub = new Select(u.getDriver().findElement(p_overlay_4));
//u.highLight(p_overlay_1);
			ub.selectByVisibleText(o1);
			testCase.addScreenShotCurrentStep(u, "Diferenciadoroi11od");
		}

		if (o2 != null) {

			Select ub1 = new Select(u.getDriver().findElement(p_overlay_4));
//	u.highLight(p_overlay_1);
			ub1.selectByVisibleText(o2);
			testCase.addScreenShotCurrentStep(u, "Diferenciadoroi11od");

		}
		if (o3 != null) {
			Select ub2 = new Select(u.getDriver().findElement(p_overlay_4));
//	u.highLight(p_overlay_1);
			ub2.selectByVisibleText(o3);
			testCase.addScreenShotCurrentStep(u, "Diferenciadoroi111od");
		}

	}

	/**
	 * Metodo para ver si estan las opciones en elementos potencial comida
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void ElementosPotencialComida(String bajo, String medio, String alto) {

		// String parent=u.getDriver().getWindowHandle();

//				Set<String> s = u.getDriver().getWindowHandles();
//				List<String> sq = new ArrayList<>(s);
//
//				u.getDriver().switchTo().window(sq.get(2));
		Select ub = new Select(u.getDriver().findElement(p_potencial_2));
//		u.highLight(p_overlay_1);
		ub.selectByVisibleText(bajo);
		testCase.addScreenShotCurrentStep(u, "Diferenciador");
		Select ub1 = new Select(u.getDriver().findElement(p_potencial_2));
		ub1.selectByVisibleText(medio);
		testCase.addScreenShotCurrentStep(u, "Diferenciador1");
		Select ub2 = new Select(u.getDriver().findElement(p_potencial_2));
		ub2.selectByVisibleText(alto);
		testCase.addScreenShotCurrentStep(u, "Diferenciador2");

		testCase.addTextEvidenceCurrentStep(
				"Estan las opciones: -Comida_Alto\r\n" + "	-Comida_Medio\r\n" + "	-Comida_Bajo");

	}

	/**
	 * Metodo para ver si estan las opciones en elementos potencial Desayuno
	 * 
	 * @author Andrea Contreras
	 * @param bajo  (String):
	 * @param medio (String):
	 * @param alto  (String):
	 * @return NA
	 */
	public void ElementosPotencialDesayuno(String bajo, String medio, String alto) {
		try {

			Select ub = new Select(u.getDriver().findElement(p_potencial_4));
			ub.selectByVisibleText(bajo);
			testCase.addScreenShotCurrentStep(u, "Diferenciador");
			Select ub1 = new Select(u.getDriver().findElement(p_potencial_4));
			ub1.selectByVisibleText(medio);
			testCase.addScreenShotCurrentStep(u, "Diferenciador1");
			Select ub2 = new Select(u.getDriver().findElement(p_potencial_4));
			ub2.selectByVisibleText(alto);
			testCase.addScreenShotCurrentStep(u, "Diferenciador2");

			testCase.addTextEvidenceCurrentStep(" Sistema mostrara en este combo los valores de:\r\n"
					+ "	-Desayuno_Alto\r\n" + "	-Desayuno_Medio\r\n" + "	-Desayuno_Bajo");

		} catch (NoSuchElementException e) {

			testCase.addTextEvidenceCurrentStep("No se muestran todos los valores en el combo");
			Assert.fail();
		}

	}

	/**
	 * Metodo para ver si estan las opciones en elementos potencial desayuno
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void ElementosPotencialDesayuno1(String bajo, String medio, String alto) {

		// String parent=u.getDriver().getWindowHandle();

//				Set<String> s = u.getDriver().getWindowHandles();
//				List<String> sq = new ArrayList<>(s);
//
//				u.getDriver().switchTo().window(sq.get(2));
		Select ub = new Select(u.getDriver().findElement(p_potencial_4));
//		u.highLight(p_overlay_1);
		ub.selectByVisibleText(bajo);
		testCase.addScreenShotCurrentStep(u, "Diferenciador44");
		Select ub1 = new Select(u.getDriver().findElement(p_potencial_4));
		ub1.selectByVisibleText(medio);
		testCase.addScreenShotCurrentStep(u, "Diferenciador14");
		Select ub2 = new Select(u.getDriver().findElement(p_potencial_4));
		ub2.selectByVisibleText(alto);
		testCase.addScreenShotCurrentStep(u, "Diferenciador24");

		testCase.addTextEvidenceCurrentStep(
				"Estan las opciones: -Desayuno_Alto\r\n" + "	-Desayuno_Medio\r\n" + "	-Desayuno_Bajo");

	}

	/**
	 * Metodo para ver si los campos estan diferente de disabled en pantalla del
	 * menu -Segmento de Tienda -Segmento Maestro -Potencial Hogar -Potencial Comida
	 * -Potencial Desayuno -Overlay Hogar -Overlay Comida -Overlay Desayuno
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public boolean ElementDis1() {

		boolean enable = false;
		if (u.getDriver().findElement(p_segmento_maestro1).isDisplayed()
				&& u.getDriver().findElement(p_potencial1).isDisplayed()
				&& u.getDriver().findElement(p_potencial_21).isDisplayed()
				&& u.getDriver().findElement(p_potencial_41).isDisplayed()
				&& u.getDriver().findElement(p_overlay_21).isDisplayed()
				&& u.getDriver().findElement(p_overlay_11).isDisplayed()
				&& u.getDriver().findElement(p_overlay_41).isDisplayed()) {
			enable = true;
			u.highLight(p_segmento_maestro1);
			u.highLight(p_potencial1);
			u.highLight(p_potencial_21);
			u.highLight(p_potencial_41);
			u.highLight(p_overlay_21);
			u.highLight(p_overlay_11);
			u.highLight(p_overlay_41);
			testCase.addScreenShotCurrentStep(u, "CORREn111");
			testCase.addBoldTextEvidenceCurrentStep(
					"El sistema muestra la pantalla de \"Formato y Entorno\" con sus campos correspondientes y mostrara los nuevos campos correspondientes a \"Segmento de Tienda\" de:\r\n"
							+ "	-Segmento de Tienda \r\n" + "	-Segmento Maestro\r\n" + "	-Potencial Hogar\r\n"
							+ "	-Potencial Comida\r\n" + "	-Potencial Desayuno\r\n" + "	-Overlay Hogar\r\n"
							+ "	-Overlay Comida\r\n" + "	-Overlay Desayuno ");
		} else {

			enable = false;
			testCase.addBoldTextEvidenceCurrentStep("No Existe");
		}

		return enable;
	}

	/**
	 * Metodo para elegir nada mas el segmento maestro del popup
	 *
	 * @author Alexis Garcia
	 * @param string segmento maestro
	 * @return boolean 
	 * 
	 */
	
	public Boolean elegirSegmentoMaestroPopUp(String sM) {

		u.hardWait(10);
		// String parent=u.getDriver().getWindowHandle();

		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);
		u.getDriver().switchTo().window(sq.get(2));

		// u.getDriver().switchTo().frame("datos");
		u.getDriver().manage().window().maximize();
		// u.getDriver().switchTo().frame("datos");
		Select ub;
		Boolean activado = false;
		if (u.getDriver().findElement(p_segmento_maestro).isDisplayed()){
			
			u.hardWait(2);
			ub = new Select(u.getDriver().findElement(p_segmento_maestro));
			activado = true;
			u.highLight(p_segmento_maestro);
			u.hardWait(2);
			ub.selectByVisibleText(sM);
			testCase.addScreenShotCurrentStep(u, "CORREn111");
		}
			
		return activado;
	
	}
	
	/**
	 * Metodo para setear los campos en pantalla nueva con parametros -Segmento de
	 * Tienda -Segmento Maestro -Potencial Hogar -Potencial Comida -Potencial
	 * Desayuno - Potencial Pba Nva Nece -Overlay Hogar -Overlay Comida -Overlay
	 * Desayuno -Overlay Pba Nva Nece
	 * 
	 * @author Alexis Garcia
	 * @param NA
	 * @return N/A
	 */

	public void SetearElementosNvaPantallaConceptoDiferenciador(String sM, String pH, String pC, String pD, String pPNN,
			String oH, String oC, String oD, String oPNN) {

		u.hardWait(10);
		// String parent=u.getDriver().getWindowHandle();

		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);
		u.getDriver().switchTo().window(sq.get(2));
		u.getDriver().manage().window().maximize();
		Select ub;

		if (u.getDriver().findElement(p_segmento_maestro).isDisplayed()
				&& u.getDriver().findElement(p_potencial).isDisplayed()
				&& u.getDriver().findElement(p_potencial_2).isDisplayed()
				&& u.getDriver().findElement(p_potencial_4).isDisplayed()
				&& u.getDriver().findElement(p_potencial_3).isDisplayed()
				&& u.getDriver().findElement(p_overlay_2).isDisplayed()
				&& u.getDriver().findElement(p_overlay_1).isDisplayed()
				&& u.getDriver().findElement(p_overlay_3).isDisplayed()
				&& u.getDriver().findElement(p_overlay_4).isDisplayed()) {

			u.hardWait(2);
			ub = new Select(u.getDriver().findElement(p_segmento_maestro));
			u.highLight(p_segmento_maestro);
			u.hardWait(2);	
			ub.selectByVisibleText(sM);
		
			Select ub2 = new Select(u.getDriver().findElement(p_potencial));
			u.highLight(p_potencial);
			ub2.selectByVisibleText(pH);
			
			Select ub3 = new Select(u.getDriver().findElement(p_potencial_2));
			u.highLight(p_potencial_2);
			u.hardWait(2);
			ub3.selectByVisibleText(pC);
			
			u.hardWait(2);
			Select ub4 = new Select(u.getDriver().findElement(p_potencial_4));
			u.highLight(p_potencial_4);
			u.hardWait(2);
			ub4.selectByVisibleText(pD);
			
			Select ub5 = new Select(u.getDriver().findElement(p_potencial_3));
			u.highLight(p_potencial_3);
			u.hardWait(2);
			ub5.selectByVisibleText(pPNN);
			
			Select ub6 = new Select(u.getDriver().findElement(p_overlay_1));
			u.highLight(p_overlay_1);
			u.hardWait(2);
			ub6.selectByVisibleText(oH);
			
			Select ub7 = new Select(u.getDriver().findElement(p_overlay_2));
			u.highLight(p_overlay_2);
			u.hardWait(2);
			ub7.selectByVisibleText(oC);
			
			u.hardWait(2);
			Select ub8 = new Select(u.getDriver().findElement(p_overlay_4));
			u.highLight(p_overlay_4);
			ub8.selectByVisibleText(oD);
			
			u.hardWait(2);
			Select ub9 = new Select(u.getDriver().findElement(p_overlay_3));
			u.highLight(p_overlay_3);
			u.hardWait(2);
			ub9.selectByVisibleText(oPNN);
			

			testCase.addScreenShotCurrentStep(u, "CORREn111");

		} else {

			// enable = false;
			testCase.addBoldTextEvidenceCurrentStep("No Existe");
		}

	}
	
	/**
	 * Metodo para setear los campos en pantalla nueva con parametros -Segmento de
	 * Tienda -Segmento Maestro -Potencial Hogar -Potencial Comida -Potencial
	 * Desayuno - Potencial Pba Nva Nece -Overlay Hogar -Overlay Comida -Overlay
	 * Desayuno -Overlay Pba Nva Nece
	 * 
	 * @author Alexis Garcia
	 * @param NA
	 * @return N/A
	 */

	public void SetearElementosNvaPantallaConceptoDiferenciadorConNull(String sM, String pH, String pC, String pD, String pPNN,
			String oH, String oC, String oD, String oPNN) {

		u.hardWait(10);
		// String parent=u.getDriver().getWindowHandle();

		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);
		u.getDriver().switchTo().window(sq.get(2));
		u.getDriver().manage().window().maximize();
		Select ub;

		if (u.getDriver().findElement(p_segmento_maestro).isDisplayed()
				&& u.getDriver().findElement(p_potencial).isDisplayed()
				&& u.getDriver().findElement(p_potencial_2).isDisplayed()
				&& u.getDriver().findElement(p_potencial_4).isDisplayed()
				&& u.getDriver().findElement(p_potencial_3).isDisplayed()
				&& u.getDriver().findElement(p_overlay_2).isDisplayed()
				&& u.getDriver().findElement(p_overlay_1).isDisplayed()
				&& u.getDriver().findElement(p_overlay_3).isDisplayed()
				&& u.getDriver().findElement(p_overlay_4).isDisplayed()) {

			u.hardWait(2);
			ub = new Select(u.getDriver().findElement(p_segmento_maestro));
			if(!sM.isEmpty()) {
			u.highLight(p_segmento_maestro);
			u.hardWait(2);	
			ub.selectByVisibleText(sM);
			}
			if(!pH.isEmpty()) {
			Select ub2 = new Select(u.getDriver().findElement(p_potencial));
			u.highLight(p_potencial);
			ub2.selectByVisibleText(pH);
			}
			if(!pC.isEmpty()) {
			Select ub3 = new Select(u.getDriver().findElement(p_potencial_2));
			u.highLight(p_potencial_2);
			u.hardWait(2);
			ub3.selectByVisibleText(pC);
			}
			u.hardWait(2);
			if(!pD.isEmpty()) {
			Select ub4 = new Select(u.getDriver().findElement(p_potencial_4));
			u.highLight(p_potencial_4);
			u.hardWait(2);
			ub4.selectByVisibleText(pD);
			}
			if(!pPNN.isEmpty()) {
			Select ub5 = new Select(u.getDriver().findElement(p_potencial_3));
			u.highLight(p_potencial_3);
			u.hardWait(2);
			ub5.selectByVisibleText(pPNN);
			}
			if(!oH.isEmpty()) {
			Select ub6 = new Select(u.getDriver().findElement(p_overlay_1));
			u.highLight(p_overlay_1);
			u.hardWait(2);
			ub6.selectByVisibleText(oH);
			}
			if(!oC.isEmpty()) {
			Select ub7 = new Select(u.getDriver().findElement(p_overlay_2));
			u.highLight(p_overlay_2);
			u.hardWait(2);
			ub7.selectByVisibleText(oC);
			}
			u.hardWait(2);
			if(!oD.isEmpty()) {
			Select ub8 = new Select(u.getDriver().findElement(p_overlay_4));
			u.highLight(p_overlay_4);
			ub8.selectByVisibleText(oD);
			}
			u.hardWait(2);
			if(!oPNN.isEmpty()) {
			Select ub9 = new Select(u.getDriver().findElement(p_overlay_3));
			u.highLight(p_overlay_3);
			u.hardWait(2);
			ub9.selectByVisibleText(oPNN);
			}

			testCase.addScreenShotCurrentStep(u, "CORREn111");

		} else {

			// enable = false;
			testCase.addBoldTextEvidenceCurrentStep("No Existe");
		}

	}
	
	
	/**
	 * Metodo para regresar los campos en pantalla Segmento Tienda con parametros
	 * -Segmento de Tienda -Segmento Maestro -Potencial Hogar -Potencial Comida
	 * -Potencial Desayuno - Potencial Pba Nva Nece -Overlay Hogar -Overlay Comida
	 * -Overlay Desayuno -Overlay Pba Nva Nece
	 * 
	 * @author Alexis Garcia
	 * @param NA
	 * @return N/A
	 */
	public boolean getElementosPantallaConceptoDiferenciador(String sM, String pH, String pC, String pD, String pPNN, String oH,
			String oC, String oD, String oPNN) {

		// String parent=u.getDriver().getWindowHandle();
		u.hardWait(5);
		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);
		// System.out.println(sq.get(2));
		u.getDriver().switchTo().window(sq.get(1));

		// u.getDriver().switchTo().frame("datos");
		u.getDriver().manage().window().maximize();
		// u.getDriver().switchTo().frame("datos");
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		u.hardWait(10);
		Boolean enable = false;
		if (u.getDriver().findElement(p_segmento_maestro).isDisplayed()
				&& u.getDriver().findElement(p_potencial1).isDisplayed()
				&& u.getDriver().findElement(p_potencial_2).isDisplayed()
				&& u.getDriver().findElement(p_potencial_4).isDisplayed()
				&& u.getDriver().findElement(p_potencial_3).isDisplayed()
				&& u.getDriver().findElement(p_overlay_2).isDisplayed()
				&& u.getDriver().findElement(p_overlay_1).isDisplayed()
				&& u.getDriver().findElement(p_overlay_3).isDisplayed()
				&& u.getDriver().findElement(p_overlay_4).isDisplayed()) {

			if (u.getDriver().findElement(p_segmento_maestro).getAttribute("value").equals(sM)
					&& u.getDriver().findElement(p_potencial1).getAttribute("value").equals(pH)
					&& u.getDriver().findElement(p_potencial_2).getAttribute("value").equals(pC)
					&& u.getDriver().findElement(p_potencial_4).getAttribute("value").equals(pD)
					&& u.getDriver().findElement(p_potencial_3).getAttribute("value").equals(pPNN)
					&& u.getDriver().findElement(p_overlay_1).getAttribute("value").equals(oH)
					&& u.getDriver().findElement(p_overlay_2).getAttribute("value").equals(oC)
					&& u.getDriver().findElement(p_overlay_4).getAttribute("value").equals(oD)
					&& u.getDriver().findElement(p_overlay_3).getAttribute("value").equals(oPNN)) {

				enable = true;
				testCase.addScreenShotCurrentStep(u, "validarDatosCampos");

			}
			String p_segmento_maestro01 = u.getDriver().findElement(p_segmento_maestro1).getAttribute("value");
			System.out.print("p_digito_verif: " + p_segmento_maestro01);
			String p_potencial01 = u.getDriver().findElement(p_potencial1).getAttribute("value");
			System.out.print("\np_potencial: " + p_potencial01);
			String p_potencial_021 = u.getDriver().findElement(p_potencial_21).getAttribute("value");
			System.out.print("\np_digito_verif2: " + p_potencial_021);
			String p_potencial_041 = u.getDriver().findElement(p_potencial_41).getAttribute("value");
			System.out.print("\np_digito_verif4: " + p_potencial_041);
			String p_overlay_021 = u.getDriver().findElement(p_overlay_21).getAttribute("value");
			System.out.print("\np_overlay_21: " + p_overlay_021);
			String p_overlay_011 = u.getDriver().findElement(p_overlay_11).getAttribute("value");
			System.out.print("\np_overlay_11: " + p_overlay_011);
			String p_overlay_041 = u.getDriver().findElement(p_overlay_41).getAttribute("value");
			System.out.print("\np_overlay_41: " + p_overlay_041 + "\n");

		} else {

			enable = false;
			testCase.addBoldTextEvidenceCurrentStep("No Existe");
		}
		return enable;

	}

	/**
	 * Metodo para regresar los campos en pantalla formato y entorno con parametros, agregando comentario en la evidencia
	 * -Segmento de Tienda -Segmento Maestro -Potencial Hogar -Potencial Comida
	 * -Potencial Desayuno - Potencial Pba Nva Nece -Overlay Hogar -Overlay Comida
	 * -Overlay Desayuno -Overlay Pba Nva Nece
	 * 
	 * @author Alexis Garcia
	 * @param NA
	 * @return N/A
	 */
	public boolean getElementosConceptoDiferenciador(String sM, String pH, String pC, String pD, String pPNN, String oH,
			String oC, String oD, String oPNN) {

		// String parent=u.getDriver().getWindowHandle();
		u.hardWait(5);
		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);
		// System.out.println(sq.get(2));
		u.getDriver().switchTo().window(sq.get(1));

		// u.getDriver().switchTo().frame("datos");
		u.getDriver().manage().window().maximize();
		// u.getDriver().switchTo().frame("datos");
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		u.hardWait(10);
		Boolean enable = false;
		if (u.getDriver().findElement(p_segmento_maestro1).isDisplayed()
				&& u.getDriver().findElement(p_potencial1).isDisplayed()
				&& u.getDriver().findElement(p_potencial_21).isDisplayed()
				&& u.getDriver().findElement(p_potencial_41).isDisplayed()
				&& u.getDriver().findElement(p_potencial_31).isDisplayed()
				&& u.getDriver().findElement(p_overlay_21).isDisplayed()
				&& u.getDriver().findElement(p_overlay_11).isDisplayed()
				&& u.getDriver().findElement(p_overlay_31).isDisplayed()
				&& u.getDriver().findElement(p_overlay_41).isDisplayed()) {

			if (u.getDriver().findElement(p_segmento_maestro1).getAttribute("value").equals(sM)
					&& u.getDriver().findElement(p_potencial1).getAttribute("value").equals(pH)
					&& u.getDriver().findElement(p_potencial_21).getAttribute("value").equals(pC)
					&& u.getDriver().findElement(p_potencial_41).getAttribute("value").equals(pD)
					&& u.getDriver().findElement(p_potencial_31).getAttribute("value").equals(pPNN)
					&& u.getDriver().findElement(p_overlay_11).getAttribute("value").equals(oH)
					&& u.getDriver().findElement(p_overlay_21).getAttribute("value").equals(oC)
					&& u.getDriver().findElement(p_overlay_41).getAttribute("value").equals(oD)
					&& u.getDriver().findElement(p_overlay_31).getAttribute("value").equals(oPNN)) {

				enable = true;
				testCase.addScreenShotCurrentStep(u, "validarDatosCampos");
				testCase.addBoldTextEvidenceCurrentStep(
						"El sistema muestra la pantalla de \"Formato y Entorno\" con sus campos correspondientes y mostrara los nuevos campos correspondientes a \"Segmento de Tienda\" de:\r\n"
								+ "    -Segmento de Tienda \r\n" + "    -Segmento Maestro\r\n"
								+ "    -Potencial Hogar\r\n" + "    -Potencial Comida\r\n"
								+ "    -Potencial Desayuno\r\n" + "    -Overlay Hogar\r\n" + "    -Overlay Comida\r\n"
								+ "    -Overlay Desayuno ");

			}
			String p_segmento_maestro01 = u.getDriver().findElement(p_segmento_maestro1).getAttribute("value");
			System.out.print("p_digito_verif: " + p_segmento_maestro01);
			String p_potencial01 = u.getDriver().findElement(p_potencial1).getAttribute("value");
			System.out.print("\np_potencial: " + p_potencial01);
			String p_potencial_021 = u.getDriver().findElement(p_potencial_21).getAttribute("value");
			System.out.print("\np_digito_verif2: " + p_potencial_021);
			String p_potencial_041 = u.getDriver().findElement(p_potencial_41).getAttribute("value");
			System.out.print("\np_digito_verif4: " + p_potencial_041);
			String p_overlay_021 = u.getDriver().findElement(p_overlay_21).getAttribute("value");
			System.out.print("\np_overlay_21: " + p_overlay_021);
			String p_overlay_011 = u.getDriver().findElement(p_overlay_11).getAttribute("value");
			System.out.print("\np_overlay_11: " + p_overlay_011);
			String p_overlay_041 = u.getDriver().findElement(p_overlay_41).getAttribute("value");
			System.out.print("\np_overlay_41: " + p_overlay_041 + "\n");

		} else {

			enable = false;
			testCase.addBoldTextEvidenceCurrentStep("No Existe");
		}
		return enable;

	}
	
	/**
	 * Metodo para regresar los campos en pantalla formato y entorno con parametros, sin agregar comentario en la evidencia
	 * -Segmento de Tienda -Segmento Maestro -Potencial Hogar -Potencial Comida
	 * -Potencial Desayuno - Potencial Pba Nva Nece -Overlay Hogar -Overlay Comida
	 * -Overlay Desayuno -Overlay Pba Nva Nece
	 * @author Alexis Garcia
	 * @param NA
	 * @return N/A
	 */
	public boolean getElementosConceptoDiferenciador2(String sM, String pH, String pC, String pD, String pPNN, String oH,
			String oC, String oD, String oPNN) {

		// String parent=u.getDriver().getWindowHandle();
		u.hardWait(5);
		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);
		// System.out.println(sq.get(2));
		u.getDriver().switchTo().window(sq.get(1));

		// u.getDriver().switchTo().frame("datos");
		u.getDriver().manage().window().maximize();
		// u.getDriver().switchTo().frame("datos");
		u.highLight(framed);
		u.getDriver().switchTo().frame("framedatos");
		u.hardWait(10);
		Boolean enable = false;
		if (u.getDriver().findElement(p_segmento_maestro1).isDisplayed()
				&& u.getDriver().findElement(p_potencial1).isDisplayed()
				&& u.getDriver().findElement(p_potencial_21).isDisplayed()
				&& u.getDriver().findElement(p_potencial_41).isDisplayed()
				&& u.getDriver().findElement(p_potencial_31).isDisplayed()
				&& u.getDriver().findElement(p_overlay_21).isDisplayed()
				&& u.getDriver().findElement(p_overlay_11).isDisplayed()
				&& u.getDriver().findElement(p_overlay_31).isDisplayed()
				&& u.getDriver().findElement(p_overlay_41).isDisplayed()) {
            u.scrollToElement(p_segmento_maestro1);
			if (u.getDriver().findElement(p_segmento_maestro1).getAttribute("value").equals(sM)
					&& u.getDriver().findElement(p_potencial1).getAttribute("value").equals(pH)
					&& u.getDriver().findElement(p_potencial_21).getAttribute("value").equals(pC)
					&& u.getDriver().findElement(p_potencial_41).getAttribute("value").equals(pD)
					&& u.getDriver().findElement(p_potencial_31).getAttribute("value").equals(pPNN)
					&& u.getDriver().findElement(p_overlay_11).getAttribute("value").equals(oH)
					&& u.getDriver().findElement(p_overlay_21).getAttribute("value").equals(oC)
					&& u.getDriver().findElement(p_overlay_41).getAttribute("value").equals(oD)
					&& u.getDriver().findElement(p_overlay_31).getAttribute("value").equals(oPNN)) {

				enable = true;
				testCase.addScreenShotCurrentStep(u, "validarDatosCampos");


			}
			String p_segmento_maestro01 = u.getDriver().findElement(p_segmento_maestro1).getAttribute("value");
			System.out.print("p_digito_verif: " + p_segmento_maestro01);
			String p_potencial01 = u.getDriver().findElement(p_potencial1).getAttribute("value");
			System.out.print("\np_potencial: " + p_potencial01);
			String p_potencial_021 = u.getDriver().findElement(p_potencial_21).getAttribute("value");
			System.out.print("\np_digito_verif2: " + p_potencial_021);
			String p_potencial_041 = u.getDriver().findElement(p_potencial_41).getAttribute("value");
			System.out.print("\np_digito_verif4: " + p_potencial_041);
			String p_overlay_021 = u.getDriver().findElement(p_overlay_21).getAttribute("value");
			System.out.print("\np_overlay_21: " + p_overlay_021);
			String p_overlay_011 = u.getDriver().findElement(p_overlay_11).getAttribute("value");
			System.out.print("\np_overlay_11: " + p_overlay_011);
			String p_overlay_041 = u.getDriver().findElement(p_overlay_41).getAttribute("value");
			System.out.print("\np_overlay_41: " + p_overlay_041 + "\n");

		} else {

			enable = false;
			testCase.addBoldTextEvidenceCurrentStep("No Existe");
		}
		return enable;

	}

	/**
	 * Metodo dar click en aceptar de pantalla nueva de concepto diferenciador
	 * 
	 * @author Alexis Garcia
	 * @param NA
	 * @return N/A
	 */
	public void botonAceptarConceptoDiferenciador() {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(btnAceptarPantallaNueva);
		u.highLight(btnAceptarPantallaNueva);
		testCase.addScreenShotCurrentStep(u, "BotonAceptar");
		u.click(btnAceptarPantallaNueva);
	}

	/**
	 * Metodo para validar que se haya presentado alert de guardado
	 * 
	 * @author Alexis Garcia
	 * @param N/A
	 * @return
	 */
	public boolean alertaGuardarSegmentoTienda() {
		u.hardWait(5);
		Boolean exist = false;

		try {

			Alert alert = u.getDriver().switchTo().alert();

			String text = alert.getText();
			System.out.print(text + "\n");
			testCase.addScreenShotCurrentStep("alerta");

			alert.accept();
			
			testCase.addScreenShotCurrentStep("confirmarAlerta");
			exist = true;

		} catch (Exception e) {
			exist = false;
		}
		return exist;
	}

	/**
	 * Metodo cambiarte framedatos
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return N/A
	 */
	public void Framedatos() {
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

	}
	
	public void Framedatos2() {
		String si = "SI";
		//String winHandleBefore = u.getDriver().getWindowHandle();

		// Perform the click operation that opens new window

		// Switch to new window opened
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}

//		u.waitForLoadPage();
		u.hardWait(4);
//		u.highLight(TiendaE);
		u.getDriver().switchTo().defaultContent();
		
		//u.highLight(framed);
		//u.getDriver().switchTo().frame("framedatos");

	}
	
	/**
	 * Metodo para validar que exista el campo Concepto diferenciador
	 * 
	 * @author Andrea Contreras
	 * @return true o false
	 */
	public boolean existeConceptoDiferenciador() {
		
		boolean exist = me.existElement(Diferenciador);
		
		if(exist) {
			u.highLight(Diferenciador);
			testCase.addScreenShotCurrentStep(u, "DiferenciadorExiste");
			u.unhighLight(Diferenciador);
		}
		
		return exist;

	}

	/**
	 * Metodo para seleccionar select con diferenciador
	 * 
	 * @author Baltazar Lugo
	 * @param dif (data provider)
	 * @return NA
	 */
	public void Diferenciador(String dif) {
		testCase.addScreenShotCurrentStep(u,"Diferenciador1");
		Select ub = new Select(u.getDriver().findElement(Diferenciador));
		u.highLight(Diferenciador);
		ub.selectByVisibleText(dif);
		testCase.addScreenShotCurrentStep(u, "Diferenciador2");

	}
	
	/**
	 * Metodo para traer el texto del select selccionado
	 * @author Baltazar Lugo
	 * @param dif (data provider)
	 * @return NA
	 */
	public String DiferenciadorText() {
		u.hardWait(5);
		String texto;
		Select ub = new Select(u.getDriver().findElement(Diferenciador));
		WebElement o = ub.getFirstSelectedOption();
	  texto= o.getText();
		u.highLight(Diferenciador);
//texto=	 u.getDriver().findElement(Diferenciador).getAttribute("value");
		testCase.addScreenShotCurrentStep(u, "Diferenciador");
		testCase.addTextEvidenceCurrentStep("TEXTO ES:"+texto);
		return texto;
	}

	/**
	 * Metodo para regresar el texto del diferenciador
	 * 
	 * @author Alexis Garcia
	 * @param dif (data provider)
	 * @return na
	 */
	public String diferenciadorTexto() {
		String difText = "";
		Select ub = new Select(u.getDriver().findElement(Diferenciador));
		u.highLight(Diferenciador);
		difText = ub.getFirstSelectedOption().getText();
		testCase.addScreenShotCurrentStep(u, "Diferenciador");
		System.out.println(difText);
		return difText;

	}
	
	/**
	 * Metodo para ver si esta activado el diferenciador
	 * 
	 * @author Baltazar Lugo
	 * @param dif (data provider)
	 * @return boolean
	 */
	public boolean DiferenciadorActivo() {
		boolean activo=true;
		if (u.getDriver().findElement(p_segmento_maestro1).isEnabled()) {
			System.out.print("entro if activo");
			activo=true;
			testCase.addScreenShotCurrentStep(u, "Diferenciador");
			testCase.addTextEvidenceCurrentStep("Esta activo");
		} else {
			activo=false;
		
			System.out.print("entro if NO activo");
			testCase.addScreenShotCurrentStep(u, "Diferenciador");
			testCase.addTextEvidenceCurrentStep("NO Esta activo");	

		}
		
	return activo;

	}
	
	public boolean ValidarCamposVaciosDiferenciador() {
        Boolean correct = false;
       
        String segmentoMaestroText = u.getText(segmentoMaestro);
        u.highLight(segmentoMaestro);
        String potencialHogarText =u.getText(potencialHogar);
        u.highLight(potencialHogar);
        String potencialComidaText =u.getText(potencialComida);
        u.highLight(potencialComida);
        String potencialDesayunoText =u.getText(potencialDesayuno);
        u.highLight(potencialDesayuno);
        String potencialPbaNvaNeceText =u.getText(potencialPbaNvaNece);
        u.highLight(potencialPbaNvaNece);
        String overlayHogarText =u.getText(overlayHogar);
        u.highLight(overlayHogar);
        String overlayComidaText =u.getText(overlayComida);
        u.highLight(overlayComida);
        String overlayDesayunoText =u.getText(overlayDesayuno);
        u.highLight(overlayDesayuno);
        String overlayPbaNvaNeceText =u.getText(overlayPbaNvaNece);
        u.highLight(overlayPbaNvaNece);
       
        if (segmentoMaestroText.length()==0 && potencialHogarText.length()==0 && potencialComidaText.length()==0 
        	&& potencialDesayunoText.length()==0 && potencialPbaNvaNeceText.length()==0 && overlayHogarText.length()==0 
        	&& overlayComidaText.length()==0 && overlayDesayunoText.length()==0 && overlayPbaNvaNeceText.length()==0) {
			System.out.print("los campos estan vacios");
			correct=true;
			//testCase.addScreenShotCurrentStep(u, "Diferenciador");
			//testCase.addTextEvidenceCurrentStep("Esta activo");
		} else {
			correct=false;
		
			System.out.print("los campos no estan vacios");
			//testCase.addScreenShotCurrentStep(u, "Diferenciador");
			//testCase.addTextEvidenceCurrentStep("NO Esta activo");	

		}
        
        
        return correct;
			
		} 
        

	/**
	 * Metodo para seleccionar select con tienda espejo
	 * 
	 * @author Baltazar Lugo
	 * @param tiendaae (data provider)
	 * @return NA
	 */
	public void TiendaEspejo(String tiendae) {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(TiendaEspejo);
		u.highLight(TiendaEspejo);
		Select TE = new Select(u.getDriver().findElement(TiendaEspejo));
		TE.selectByIndex(2);
		testCase.addScreenShotCurrentStep(u, "TiendaEspejo");

	}

	/**
	 * Metodo para seleccionar select con tienda hermana
	 * 
	 * @author Baltazar Lugo
	 * @param tiendasis (data provider)
	 * @return NA
	 */
	public void TiendaSister(String tiendasis) {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(TiendaSister);
		u.highLight(TiendaSister);
		Select TS = new Select(u.getDriver().findElement(TiendaSister));
		TS.selectByIndex(2);
		testCase.addScreenShotCurrentStep(u, "TiendaSister");

	}

	/**
	 * Metodo para mandar cadena string del porcentaje
	 * 
	 * @author Baltazar Lugo
	 * @param porce (data provider)
	 * @return NA
	 */
	public void Proce(String porce) {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(Porcentaje);
		u.highLight(Porcentaje);
		u.clear(Porcentaje);
		u.sendKeys(Porcentaje, porce);
		testCase.addScreenShotCurrentStep(u, "Porcentaje");

	}

	/**
	 * Metodo para seleccionar select de nivel socio economico
	 * 
	 * @author Baltazar Lugo
	 * @param NS (data provider)
	 * @return NA
	 */
	public void NivelSocio(String NS) {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(NivelSocioE);
		u.highLight(NivelSocioE);
		Select NS1 = new Select(u.getDriver().findElement(NivelSocioE));
		NS1.selectByVisibleText(NS);
		testCase.addScreenShotCurrentStep(u, "NIVELSOCIO");

	}

	/**
	 * Metodo para seleccionar select de zona socioeconomica
	 * 
	 * @author Baltazar Lugo
	 * @param zona (desde data provider)
	 * @return NA
	 */
	public void ZonaE(String Zona) {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(ZonaEco);
		u.highLight(ZonaEco);
		Select ZE = new Select(u.getDriver().findElement(ZonaEco));
		ZE.selectByVisibleText(Zona);
		testCase.addScreenShotCurrentStep(u, "ZonaE");

	}

	/**
	 * Metodo para dar clic en boton actualizar en formato y retorno
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void clickBotonActualizar() {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(BtnActualizar);
		u.highLight(BtnActualizar);
		testCase.addScreenShotCurrentStep(u, "DatosFORMATOAC");
		u.click(BtnActualizar);
//		u.waitForLoadPage();

		u.hardWait(10);
//		testCase.addScreenShotCurrentStep(u, "Respuesta_POP_PERMISOS_Y_CONTRATOS_ha_sido_grabada");
		String ventanaE = u.getDriver().switchTo().alert().getText();
		System.out.println("Texto en mensaje de alerta" + ventanaE);

		String str2 = "La informacion de \"FORMATO y ENTORNO\" ha sido grabada.";
		if (ventanaE.equals(str2)) {
			testCase.addScreenShotCurrentStep("RespuestaPOP");
			u.getDriver().switchTo().alert().accept();
		} else {
			System.out.println("Fallo Datos de La informacion de \"FORMATO y ENTORNO\" ha sido grabada.");
			Assert.fail("No se muestra el mensaje 'La informacion de \"FORMATO y ENTORNO\" ha sido grabada.'");
		}
		u.hardWait(4);
	}

	/**
	 * Metodo para dar clic en boton entorno
	 * 
	 * @author Baltazar Lugo
	 * @param NA
	 * @return NA
	 */
	public void BtnEntorno() {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(Entorno);
		u.highLight(Entorno);
		testCase.addScreenShotCurrentStep(u, "DatosEntorno");
		u.click(Entorno);
//		u.waitForLoadPage();

		u.hardWait(10);
	}

	/**
	 * Metodo para seleccionar select de vialidad
	 * 
	 * @author Baltazar Lugo
	 * @param vialidad (desde data provider)
	 * @return NA
	 */
	public void Vialidad(String Vialidad1) {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(Vialidad);
		u.highLight(Vialidad);
		Select ZE = new Select(u.getDriver().findElement(Vialidad));
		ZE.selectByVisibleText(Vialidad1);
		testCase.addScreenShotCurrentStep(u, "Vialidad");

	}

	/**
	 * Metodo para mandar string a ubicacion en pantalla de entorno
	 * 
	 * @author Baltazar Lugo
	 * @param ubicacion (desde data provider)
	 * @return NA
	 */
	public void UbicacionEntorno(String ubicacion) {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(CamRef);
		u.highLight(CamRef);
		u.clear(CamRef);
		u.sendKeys(CamRef, ubicacion);
		testCase.addScreenShotCurrentStep(u, "Referenciaubicacion");

	}

	/**
	 * Metodo para dar click en boton actualizar en pantalla de entorno
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return NA
	 */
	public void clickBotonActualizarEntorno() {
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(BtnActualizar);
		u.highLight(BtnActualizar);
		testCase.addScreenShotCurrentStep(u, "DatosFORMATOAC");
		u.click(BtnActualizar);
//		u.waitForLoadPage();

		u.hardWait(10);
//		testCase.addScreenShotCurrentStep(u, "Respuesta_POP_PERMISOS_Y_CONTRATOS_ha_sido_grabada");
//		String ventanaE = u.getDriver().switchTo().alert().getText();
//		System.out.println("Texto en mensaje de alerta" + ventanaE);
//
//		String str2 = "La informacion de \"ENTORNO\" ha sido grabada.";
//		if (ventanaE.equals(str2)) {
////			testCase.addScreenShotCurrentStep(u, "RespuestaPOP");
//
//			u.getDriver().switchTo().alert().accept();
//		} else {
//			System.out.println("Fallo Datos de La informacion de \"ENTORNO\" ha sido grabada.");
//			
//		}
	}
	
	/**
	 * Metodo para esperar
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return NA
	 */
	public void Metodoesperar() {
		
//		u.waitForLoadPage();

		u.hardWait(10);

	}

	/**
	 * Metodo para validar que se haya presentado alert de guardado
	 * 
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return msjexist (true si se presento, false si no)
	 */
	public boolean validacion() {
		String ventanaE = u.getDriver().switchTo().alert().getText();
		System.out.println("Texto en mensaje de alerta" + ventanaE);
		boolean msjexist = false;
		String str2 = "La informacion de \"ENTORNO\" ha sido grabada.";
		if (ventanaE.equals(str2)) {
//			testCase.addScreenShotCurrentStep(u, "RespuestaPOP");
			msjexist = true;
			u.getDriver().switchTo().alert().accept();
		} else {
			System.out.println("Fallo Datos de La informacion de \"ENTORNO\" ha sido grabada.");
			msjexist = false;
		}
		return msjexist;
	}
	
	
	
	/**
	 * Metodo para validar que el sistema cambio en automatico los valores a NA en los campos de: 
	 * -Segmento Maestro
	 * -Potencial_Hogar
	 * -Potencial_Comida
	 * -Potencial_Desayuno
	 * -Overlay_Hogar
	 * -Overlay_Comida 
	 * @return True o false si se cambiaron los valores a NA
	 * @author Andrea Contreras
	 */
	public boolean ValidarCambioCampoSegmentoTiendaNA() {
		u.hardWait(3);
		u.scrollToElement(p2_table);
		
		boolean validacion = false;
		String segMaestro, ptlHogar, ptlComida, ptlDesayuno, overlayHogar, overlayComida, overlayDesayuno ;
		
		segMaestro = u.getDriver().findElement(p2_segmento_maestro).getAttribute("value");
		//System.out.print(segMaestro);
		u.highLight(p2_segmento_maestro);
		ptlHogar = u.getDriver().findElement(p2_potencial_1).getAttribute("value");
		//System.out.print(ptlHogar);
		u.highLight(p2_potencial_1);
		ptlComida = u.getDriver().findElement(p2_potencial_2).getAttribute("value");
		//System.out.print(ptlComida);
		u.highLight(p2_potencial_2);
		ptlDesayuno = u.getDriver().findElement(p2_potencial_3).getAttribute("value");
		//System.out.print(ptlDesayuno);
		u.highLight(p2_potencial_3);
		overlayHogar = u.getDriver().findElement(p2_overlay_1).getAttribute("value");
		//System.out.print(overlayHogar);
		u.highLight(p2_overlay_1);
		overlayComida = u.getDriver().findElement(p2_overlay_2).getAttribute("value");
		//System.out.print(overlayComida);
		u.highLight(p2_overlay_2);
		overlayDesayuno = u.getDriver().findElement(p2_overlay_3).getAttribute("value");
		//System.out.print(overlayDesayuno);
		u.highLight(p2_overlay_3);
		
		if(segMaestro.equals("NA") && ptlHogar.equals("NA") && ptlComida.equals("NA")
				&& ptlDesayuno.equals("NA") && overlayHogar.equals("NA") && overlayComida.equals("NA")
				&& overlayDesayuno.equals("NA")) {
			validacion = true;
			
		}		
		
		return validacion;

	}
	
	/**
	 * Metodo highlight para campos Concepto Diferenciador y Segmento de Tienda
	 * @author Maria Due as
	 */
	public void camposDiferenciadorSegTienda() {
		u.hardWait(3);
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}
		u.getDriver().switchTo().frame("framedatos");
		u.highLight(diferenciadorTd);
		u.highLight(btnSegTiendaTd);
		testCase.addScreenShotCurrentStep(u, "camposDiferenciadorSegTienda");
		u.unhighLight(diferenciadorTd);
		u.unhighLight(btnSegTiendaTd);
	}
	
	/**
	 * Metodo para verificar que esten deshabilitados los campos Concepto Diferenciador y Segmento de Tienda
	 * @return Retorna true si estan deshabilitados o false en caso contrario
	 * @author Maria Due as
	 */
	public Boolean validarCamposDiferenciadorSegTienda() {
		
		Boolean correct = false;
		
		if(!u.getDriver().findElement(Diferenciador).isEnabled() 
				&& !u.getDriver().findElement(BtnSegmentoTienda).isEnabled()) {
			correct = true;
			testCase.addBoldTextEvidenceCurrentStep("Los campos Concepto Diferenciador y Segmento de Tienda estan deshabilitados");
		}
		
		camposDiferenciadorSegTienda();
		
		return correct;
		
	}
	
	/**
	 * Metodo para seleccionar opcion de Diferenciador
	 * @param option
	 * @return Retorna true si se selecciono correctamente la opcion
	 * @author Maria Due as
	 */
	public Boolean selectDiferenciador(String option) {
		
		u.hardWait(3);
		for (String winHandle : u.getDriver().getWindowHandles()) {
			u.getDriver().switchTo().window(winHandle);
		}
		u.getDriver().switchTo().frame("framedatos");
		
		Boolean exist = selectOption(option, Diferenciador);
		
		return exist;
	}

	/**
	 * Maetodo para validar que los campos -Segmento Maestro
	 *Potencial_Hogar
	 *Potencial_Comida
	 *Potencial_Desayuno
	 *Overlay_Hogar
	 *Overlay_Comida
	 *Overlay_Desayuno se habiliten
	 * @return Retorna true si se habilitan
	 * @author Maria Due as
	 */
	public Boolean validarActivacionCampos() {
		Boolean correct = false;
		try {
			if(!u.getDriver().findElement(BtnSegmentoTienda).isEnabled() && !u.getDriver().findElement(p_segmento_maestro).isEnabled()
					&& !u.getDriver().findElement(p_potencial).isEnabled() && !u.getDriver().findElement(p_potencial_2).isEnabled()
					&& !u.getDriver().findElement(p_potencial_4).isEnabled() && !u.getDriver().findElement(p_overlay_1).isEnabled()
					&& !u.getDriver().findElement(p_overlay_2).isEnabled() && !u.getDriver().findElement(p_overlay_4).isEnabled()) {
			
				correct = true;
				testCase.addBoldTextEvidenceCurrentStep("Los campos estan habilitados");

			}
		}catch(Exception e) {
			correct = false;
		}
		
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el boton Asinar de Segmentacion de tienda
	 * @author Maria Due as
	 */
	public void clickAsignarSegTienda() {
		u.highLight(BtnSegmentoTienda);
		testCase.addScreenShotCurrentStep(u, "AsignarSegTienda");
		u.unhighLight(BtnSegmentoTienda);
		u.click(BtnSegmentoTienda);
		testCase.addScreenShotCurrentStep(u, "clickAsignarSegTienda");
	}
	
	/**
	 * Metodo para validar que exista el mensaje 'Si realizo cambios en la informacion, debe de guardar antes de Asignar el Segmento de Tienda.  Desea continuar?' sin dar click en el boton Aceptar
	 * @return Retorna true si el texto coincide
	 * @author Andrea Contreras
	 */
	public Boolean existeMensajeAlerta() {
        Boolean exist = false;
        
        try {  
            Alert alert = u.getDriver().switchTo().alert();
            String text = alert.getText();
            System.out.print(text + "\n");           
            if(text.equals("Si realiz  cambios en la informaci n, debe de guardar antes de Asignar el Segmento de Tienda.  Desea continuar?")) {
               exist = true;                
            }
            
        }catch(Exception e) {
            exist = false;
       }
        
        testCase.addScreenShotCurrentStep("alerta1");
        return exist;
	}
	
	
	/**
	 * Metodo para validar que exista el mensaje 'Si realizo cambios en la informacion, debe de guardar antes de Asignar el Segmento de Tienda.  Desea continuar?' sin dar click en el boton Aceptar
	 * @return Retorna true si el texto coincide
	 * @author Andrea Contreras
	 */
	public Boolean existeMensajeAlerta2() {
        Boolean exist = false;
        
        try { 
            Alert alert = u.getDriver().switchTo().alert();
            String text = alert.getText();
            System.out.print(text + "\n");           
            if(text.equals("�Esta seguro de guardar el Segmento de Tienda?")) {	           	              
            	exist = true;            
            }
            
        }catch(Exception e) {
            exist = false;
       }
        
        testCase.addScreenShotCurrentStep("alerta2"); 
        return exist;
	}
	
	/**
	 * Metodo para confirmar mensaje de Alerta
	 * @return Retorna true si el texto coincide
	 * @author Maria Due as
	 */
	public Boolean confirmarMensajeAlerta() {
        Boolean exist = false;
        
        try {
    
            Alert alert = u.getDriver().switchTo().alert();

            String text = alert.getText();
            System.out.print(text + "\n");
            
            if(text.equals("Si realizó cambios en la información, debe de guardar antes de Asignar el Segmento de Tienda. ¿Desea continuar?")) {
            	
            	testCase.addScreenShotCurrentStep("alerta");
                
                alert.accept();
                testCase.addScreenShotCurrentStep("confirmarAlerta");
                exist = true;
                
            }
            
        }catch(Exception e) {
            exist = false;
       }
        return exist;
	}
	
	/**
	 * Metodo para aceptar alerta
	 * @return Retorna true si la acepta
	 * @author Maria Due as
	 */
	public Boolean confirmarAlerta() {
        Boolean exist = false;
        
        try {
    
            Alert alert = u.getDriver().switchTo().alert();

            alert.accept();
            testCase.addScreenShotCurrentStep("confirmarAlerta");
            exist = true;
            
        }catch(Exception e) {
            exist = false;
       }
        return exist;
    }
	
	/**
	 * Metodo para aceptar alerta y enforcar frame de datos
	 * @param N/A
	 * @return Retorna true si la acepta
	 * @author Jorge Ruiz
	 * 
	 */
	public Boolean confirmarAlertasinSS() {
        Boolean exist = false;
        
        try {
    
            Alert alert = u.getDriver().switchTo().alert();

            alert.accept();
            Set<String> s = u.getDriver().getWindowHandles();
    		List<String> sq = new ArrayList<>(s);
    		u.getDriver().switchTo().window(sq.get(1));
            u.getDriver().switchTo().frame("framedatos");
            exist = true;
            
        }catch(Exception e) {
            exist = false;
       }
        return exist;
    }

	/** Metodo para resaltar el boton de asignar y tomar captura
	 * @author David Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void resaltaAsignar() {
		u.highLight(asignar_segmentoTienda);	
		testCase.addScreenShotCurrentStep("captura", false);
	}
	
	/**
	 * Metodo para realizar click en el boton de asignar un segmento de tienda
	 * @author David Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void click_on_Asignar() {
		u.click(asignar_segmentoTienda);	
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("captura", false);
	}

	/**
	 * Metodo para comprobar si un mensaje existe en una alerta del navegador
	 * @author David Valdez
	 * @param String
	 * @return boolean
	 */
	public boolean comprobarMensaje_onAlert(String mensaje) {
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
		wait.until(ExpectedConditions.alertIsPresent());
		String ventanaE = u.getDriver().switchTo().alert().getText();
		System.out.println("Texto en mensaje de alerta " + ventanaE);
		if (ventanaE.equals(mensaje)) {
			u.screenShotFull("captura");
			testCase.addScreenShotCurrentStep("captura", false);			
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo para comprobar si un mensaje existe en una alerta del navegador
	 * @author David Valdez
	 * @param String
	 * @return boolean
	 */
	public boolean comprobarMensajeSinEspacios_onAlert(String mensaje) {
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
		wait.until(ExpectedConditions.alertIsPresent());
		String ventanaE = u.getDriver().switchTo().alert().getText().replaceAll("(\n|\r)", "");
		System.out.println("Texto en mensaje de alerta " + ventanaE);
		if (ventanaE.equals(mensaje)) {
			u.screenShotFull("captura");
			testCase.addScreenShotCurrentStep("captura", false);			
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo para aceptar una alerta del navegador dando click en OK
	 * @author David Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void aceptar_onAlert() {
		u.getDriver().switchTo().alert().accept();	
		//testCase.addScreenShotCurrentStep("captura");
	}
	
	/**
	 * Metodo para capturar la pantalla en el segmento de tienda
	 * @author David Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void popUpSegmentoTienda() {
		
		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);
		u.getDriver().switchTo().window(sq.get(2));
		u.getDriver().manage().window().maximize();
		
		testCase.addScreenShotCurrentStep("captura");
	}
	
	/**
	 * Metodo para validar que se muestre el Pop Up de Segmento de Tienda
	 * @author Andrea Contreras
	 * @return true o false si se muestra el pop up
	 */
	public boolean validarPopUpSegmentoTienda() {
		
		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);
		u.getDriver().switchTo().window(sq.get(2));
		u.getDriver().manage().window().maximize();
		
		boolean exist = false;
		if(me.existElement(tituloVentanaSegmentoTienda)) {
			exist = true;
		}
		
		testCase.addScreenShotCurrentStep("captura");
		return exist;
	}
	
	/**
	 * Metodo para validar que se muestren los campos y botones del Pop Up de Segmento de Tienda
	 * @author Andrea Contreras
	 * @return true o false si se muestran
	 */
	public boolean validarElementosPopUpSegmentoTienda() {
		
		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);
		u.getDriver().switchTo().window(sq.get(2));
		u.getDriver().manage().window().maximize();
		
		boolean exist = false;
		if (u.getDriver().findElement(p_segmento_maestro).isDisplayed()
				&& u.getDriver().findElement(p_potencial).isDisplayed()
				&& u.getDriver().findElement(p_potencial_2).isDisplayed()
				&& u.getDriver().findElement(p_potencial_4).isDisplayed()
				&& u.getDriver().findElement(p_overlay_2).isDisplayed()
				&& u.getDriver().findElement(p_overlay_1).isDisplayed()
				&& u.getDriver().findElement(p_overlay_4).isDisplayed()
				&& me.existElement(cancelar_segmentoTienda)
				&& me.existElement(aceptar_segmentoTienda)) {
			exist = true;
			u.highLight(p_segmento_maestro);
			u.highLight(p_potencial);
			u.highLight(p_potencial_2);
			u.highLight(p_potencial_4);
			u.highLight(p_overlay_2);
			u.highLight(p_overlay_1);
			u.highLight(p_overlay_4);
			u.highLight(cancelar_segmentoTienda);
			u.highLight(aceptar_segmentoTienda);
			testCase.addScreenShotCurrentStep(u, "validarCampos");
			u.unhighLight(p_segmento_maestro);
			u.unhighLight(p_potencial);
			u.unhighLight(p_potencial_2);
			u.unhighLight(p_potencial_4);
			u.unhighLight(p_overlay_2);
			u.unhighLight(p_overlay_1);
			u.unhighLight(p_overlay_4);
			u.unhighLight(cancelar_segmentoTienda);
			u.unhighLight(aceptar_segmentoTienda);
		
		} else {

			exist = false;
			testCase.addBoldTextEvidenceCurrentStep("noExiste");
		}

		return exist;
	}
	
	/**
	 * Metodo para validar que se muestren los campos del Pop Up de Segmento de Tienda
	 * @author Andrea Contreras
	 * @return true o false si se muestran
	 */
	public boolean validarCamposPopUpSegmentoTienda() {
		
		Set<String> s = u.getDriver().getWindowHandles();
		List<String> sq = new ArrayList<>(s);
		u.getDriver().switchTo().window(sq.get(2));
		u.getDriver().manage().window().maximize();
		
		boolean exist = false;
		if (u.getDriver().findElement(p_segmento_maestro).isDisplayed()
				&& u.getDriver().findElement(p_potencial).isDisplayed()
				&& u.getDriver().findElement(p_potencial_2).isDisplayed()
				&& u.getDriver().findElement(p_potencial_4).isDisplayed()
				&& u.getDriver().findElement(p_overlay_2).isDisplayed()
				&& u.getDriver().findElement(p_overlay_1).isDisplayed()
				&& u.getDriver().findElement(p_overlay_4).isDisplayed()) {
			exist = true;
			u.highLight(p_segmento_maestro);
			u.highLight(p_potencial);
			u.highLight(p_potencial_2);
			u.highLight(p_potencial_4);
			u.highLight(p_overlay_2);
			u.highLight(p_overlay_1);
			u.highLight(p_overlay_4);
			testCase.addScreenShotCurrentStep(u, "validarCampos");
			u.unhighLight(p_segmento_maestro);
			u.unhighLight(p_potencial);
			u.unhighLight(p_potencial_2);
			u.unhighLight(p_potencial_4);
			u.unhighLight(p_overlay_2);
			u.unhighLight(p_overlay_1);
			u.unhighLight(p_overlay_4);
		
		} else {

			exist = false;
			testCase.addBoldTextEvidenceCurrentStep("noExiste");
		}

		return exist;
	}
	
	
	/**
	 * Metodo para dar click en el boton de cancelar en el segmento de tienda
	 * @author David Valdez
	 * @param N/A
	 * @return N/A
	 */
	public void cancelarOnSegmentodeTienda() {
		u.highLight(cancelar_segmentoTienda);
		testCase.addScreenShotCurrentStep(u, "captura");
		u.click(cancelar_segmentoTienda);	
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("captura2");
	}
	
	/**
	 * Metodo para dar click en el boton de aceptar en el segmento de tienda
	 * @author Maria Due as
	 */
	public void aceptarOnSegmentodeTienda() {
		u.highLight(aceptar_segmentoTienda);
		testCase.addScreenShotCurrentStep(u, "captura");
		u.click(aceptar_segmentoTienda);	
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("captura2");
	}
	
	/**
	 * Metodo para validar que se haya presentado alert de error
	 *  El Concepto Diferenciador es requerido.
	 * @author Baltazar Lugo
	 * @param N/A
	 * @return  msjexist (true si se presento, false si no)
	 */
	public boolean validacionErrorFaltaConcpDif() {
		u.hardWait(10);
		String ventanaE = u.getDriver().switchTo().alert().getText();
		System.out.println("Texto en mensaje de alerta" + ventanaE);
		boolean msjexist = false;
		String str2 = "El Concepto Diferenciador es requerido.";
		if (ventanaE.equals(str2)) {
//			testCase.addScreenShotCurrentStep(u, "RespuestaPOP");
			msjexist = true;
			testCase.addScreenShotCurrentStep("captura");
			u.getDriver().switchTo().alert().accept();
             testCase.addTextEvidenceCurrentStep("Pasado: Presento alerta de El Concepto Diferenciador es requerido");
		} else {
			
			System.out.println("Fallo Datos de Operacion Grabados");
			testCase.addScreenShotCurrentStep("captura");
			msjexist = false;
		}
		return msjexist;
	}
	
	/**
	 * Metodo para hacer click en botón de actualizar para activar el select "Concepto Diferenciador" y regresar al mismo
	 * @author Jorge Ruiz
	 * @param N/A
	 * @return void
	 */
	public void actualizarHabilitarConcepto()
	{
		MetodosExtra ME = new MetodosExtra(u, testCase);
		ME.scrollToElement(BtnActualizar);
		u.click(BtnActualizar);
		ME.scrollToElement(Diferenciador);
	}
	
	/**
	 * Constructor
	 ******************************************************************************************/

	public Formato_y_Entorno(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}

	/*
	 * Metodos extra 
	 *****************************************************************************************/
	/**
	 * Metodo para seleccionar una opcion de un select
	 * @param option
	 * @param by
	 * @return exist: true si se selecciono correctamente la opcion o false en caso contrario
	 * @author Maria Due as
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
	
	public Boolean SelecDatosSegmentoDiferenciador(String segmentoMaestro, String potencialHogar, String potencialComida, String potencialDesayuno,String potencialPba,
			String overlayHogar, String overlayComida, String overlayDesayuno,String overlayPba) {
        Boolean exist = false;
        
        exist = selectOption (segmentoMaestro, segmentoMaestroFrame);
        u.highLight(segmentoMaestroFrame);
        exist = selectOption (potencialHogar, potencialHogarFrame);
        u.highLight(potencialHogarFrame);
        exist = selectOption (potencialComida, potencialComidaFrame);
        u.highLight(potencialComidaFrame);
        exist = selectOption (potencialDesayuno, potencialDesayunoFrame);
        u.highLight(potencialDesayunoFrame);
        exist = selectOption (potencialPba, potencialPbaNvaNeceFrame);
        u.highLight(potencialPbaNvaNeceFrame);
        exist = selectOption (overlayHogar, overlayHogarFrame);
        u.highLight(overlayHogarFrame);
        exist = selectOption (overlayComida, overlayComidaFrame);
        u.highLight(overlayComidaFrame);
        exist = selectOption (overlayDesayuno, overlayDesayunoFrame);
        u.highLight(overlayDesayunoFrame);
        exist = selectOption (overlayPba, overlayPbaNvaNeceFrame);
        u.highLight(overlayPbaNvaNeceFrame);
        
        

        return exist;
    }
	
	/**
	 * Metodo para dar scroll al elemento de formato y entorno
	 * @author Andrea Contreras
	 */
	public void scrollToFormatoYEntorno() {
		u.scrollToElement(tituloPantalla);
	}
	
	
	
	
}