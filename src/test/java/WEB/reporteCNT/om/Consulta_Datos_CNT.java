package WEB.reporteCNT.om;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.FindFailed;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.JsonUtil;
import utils.selenium.SeleniumUtil;

/**
 * Localizadores y metodos de la pantalla 'Consulta Datos CNT' del portal
 * Reportes CNT
 ******************************************************************************************/
public class Consulta_Datos_CNT {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	By xmlPantalla = By.xpath("//span[text() = 'Estadsticas de Informacin']");

	/*
	 * Localizadores apartado 'Reporte Estadístico'
	 ******************************************************************************************/

	// Selecciona la Entidad sobre la cual deseas generar el reporte
	By xmlSeccion1 = By.id("Entidades");
	By xmlEntidad = By.xpath("//select[@id='Entity_input']");
	By xmlEntidadActual = By.xpath("//label[@id='Entity_label']");

	// Selecciona las columnas a incluir en el reporte
	By xmlSeccion2 = By.id("camposaIncluir");
	By xmlAgregaCampos = By.xpath("//a[@id='agregarcampos']");

	// Selecciona los filtros a incluir en el reporte.
	By xmlSeccion3 = By.id("Paso3");
	
	By xmlSeccionFiltros = By.xpath("//div[@id='arbol']/ul/li/ul[@class= 'ui-treenode-children']");
	By xmlDesplegarFiltros = By.xpath("//div[@id='arbol']/ul/li/span/span[1]");
	By xmlSelectAllFiltros = By.xpath("//div[@id='arbol']/ul/li/span/div//span");
	By xmlOpcionesFiltros = By.xpath("//div[@id='arbol']/ul/li/ul/li/span/span[3]");
	
	

	// Selecciona la configuracin deseada
	By xmlSeccion4 = By.id("Contenedor_paso_4");

	By xmlDominio = By.id("dominio1");
	By xmlDato = By.id("dato1");
	By xmlCondicion = By.id("condicion");

	By xmlBtnAgregar = By.id("agregarcondicion");

	String strDominio = "//div[@id='Contenedor_condicion%s']/div[1]/div[2]/select";
	String strDato = "//div[@id='Contenedor_condicion%s']/div[2]/div[2]/select";
	String strCondicion = "//div[@id='Contenedor_condicion%s']/div[3]/div[2]/select";
	String strValor = "//div[@id='Contenedor_condicion%s']/div[4]/div/input";
	String strBtnAgregar = "//div[@id='Contenedor_condicion%s']/div[5]/div/button[1]";

	// el numero de la condicion +1
	String strCondicionAndOr = "//div[@id='Contenedor_condicion%s']/select";

	// Genera Tu Reporte
	By xmlSeccion5 = By.xpath("//div[@id='PasosGeneracionReporte']/div[11]");

	By xmlReportePantalla = By.xpath("//input[@value='1']");
	By xmlReporteExcel = By.xpath("//input[@value='2']");

	By xmlBtnGenerarEstadPantalla = By.id("enPanalla");
	By xmlBtnGenerarEstadExcel = By.id("EnExcel");
	
	By xmlDescargas = By.xpath("//div[@class='downloads-list']/div/div/div/div/div/div/div[2]/div[1]/button[1]");

	/*
	 * Localizadores apartado 'Resultados'
	 ******************************************************************************************/

	By xmlTituloResultados = By.id("SubMostrarRep1");

	By xmlTablaResultados = By.xpath("//div[@id='tbl']//table");

	By xmlSinResultados = By
			.xpath("//div[@id='tbl']//table//tr/td/div[text()='No hay registros que coincidan con la bsqueda.']");
	By xmlConResultados = By.xpath("//div[@id='tbl']//table//tr/td/div");

	/*
	 * Localizadores ventana/panel 'Atributos'
	 ******************************************************************************************/

	By xmlVentanaAttributos = By.id("panelAtributos");

	By xmlTipoInfo = By.id("seleccionarInformacion");

	By xmlTabla = By.id("seleccionarInformacion");
	By xmlTablaDatos = By.xpath("//div[@id='atributosEntidad']//table/tbody/tr/td[2]//span");
	By xmlAllColumns = By.xpath("//div[@id='atributosEntidad']//table/thead/tr/th[1]/div/div[1]/div[2]");

	By xmlSelectedAtributos = By.id("textAtributos");

	By xmlBtnAceptar = By.xpath("//button[@id='aceptarPanel']");

	/*
	 * Constructor
	 ******************************************************************************************/

	/**
	 * Constructor de la clase Consulta_Datos_CNT
	 * 
	 * @author Andrea Contreras
	 * @param u        (SeleniumUtil): objeto de la clase
	 * @param testCase (testCase): objeto de la clase
	 */
	public Consulta_Datos_CNT(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}

	/**
	 * Método para esperar a que se muestre la pantalla
	 * 
	 * @author Andrea Contreras
	 */
	public void visualizaPantallaConsultaDatosCNT() {
		me.explicitWaitVisibilityOfElement(xmlPantalla, Duration.ofSeconds(10));
	}

	/*
	 * Métodos apartado 'Reporte Estadístico'
	 ******************************************************************************************/
	// SECCIÓN 1 - Selecciona la Entidad sobre la cual deseas generar el reporte

	/**
	 * Método para seleccionar una opción de 'Entidad'
	 * @param entidad (String): valor de la entidad a seleccionar
	 * @author Andrea Contreras
	 */
	public void seleccionarEntidad(String entidad) {
		
		me.explicitWaitToBeClickable(xmlEntidad, Duration.ofSeconds(90));
		WebElement e = u.getDriver().findElement(xmlEntidad);

		Select plazasS = new Select(e);
		plazasS.selectByVisibleText(entidad);

		u.hardWait(15);

		boolean selected = false;
		
		do { 
		   
		   if(u.getText(xmlEntidadActual).equals(entidad)){ 
			   selected = true;
		       System.out.print("se encontro texto"); 
		  }
		  
		  }while(!selected);		 

		testCase.addScreenShotCurrentStep(u, "Entidad");

	}

	// SECCIÓN 2 - Selecciona las columnas a incluir en el reporte

	/**
	 * Método para dar click en la opción 'Agrega campos'
	 * @author Andrea Contreras
	 */
	public void clickAgregaCampos() {

		u.highLight(xmlAgregaCampos);
		testCase.addScreenShotCurrentStep(u, "AgregarCampos1");
		u.click(xmlAgregaCampos);
		u.unhighLight(xmlAgregaCampos);
		me.explicitWaitVisibilityOfElement(xmlVentanaAttributos, Duration.ofSeconds(20));
		testCase.addScreenShotCurrentStep(u, "AgregarCampos2");

	}
	
	//SECCION 3 - Selecciona los filtros a incluir en el reporte
	
	/**
	 * Método para seleccionar todos los filtros a incluir en el reporte
	 * @author Andrea Contreras
	 */
	public void seleccionarFiltros() {

		u.scrollToElement(xmlSeccion3);
		
		if(!u.isDisplayed(xmlSeccionFiltros)) {
			u.click(xmlDesplegarFiltros);
		}
		
		u.hardWait(1);
		u.click(xmlSelectAllFiltros);
		u.hardWait(1);

		testCase.addScreenShotCurrentStep(u, "filtroSeleccionado");

	}
	
	/**
	 * Método para dar seleccionar los filtros a incluir en el reporte
	 * @param filtros (ArrayList<String>): filtros a seleccionar
	 * @author Andrea Contreras
	 */
	public void seleccionarFiltros(ArrayList<String> filtros) {

		u.scrollToElement(xmlSeccion3);
		
		if(u.isDisplayed(xmlSeccionFiltros)==false) {
			u.click(xmlDesplegarFiltros);
		}
		
		me.explicitWaitToBeClickable(xmlSeccionFiltros, Duration.ofSeconds(10));
		
		for (int i = 0; i < filtros.size(); ++i) {
			
			List<WebElement> allOptions = u.getDriver().findElements(xmlOpcionesFiltros);

			for (WebElement option : allOptions) {
				
				if (option.getText().equals(filtros.get(i))) {

					me.highlight(option);
					option = option.findElement(By.xpath(".//preceding-sibling::div/div"));
					u.hardWait(1);
					option.click();
					u.hardWait(1);
				}
			}


		}

		testCase.addScreenShotCurrentStep(u, "filtroSeleccionado");

	}

	// SECCIÓN 4 - Selecciona la configuracin deseada

	/**
	 * Metodo para seleccionar las configuraciones deseadas
	 * @param jobsArray (JSONArray): JSON con las configuraciones a utilizar
	 * @author Andrea Contreras
	 */
	public void seleccionarConfiguracion(JSONArray jobsArray) {
		u.scrollToElement(xmlSeccion4);
		JSONObject obj;

		for (int i = 0; i < jobsArray.length(); ++i) {

			obj = (JSONObject) jobsArray.get(i);
			String dominio = JsonUtil.getJsonElement(obj, "dominio");
			String dato = JsonUtil.getJsonElement(obj, "dato");
			String condicion = JsonUtil.getJsonElement(obj, "condicion");
			String valor = JsonUtil.getJsonElement(obj, "valor");
			Boolean otro = JsonUtil.getJsonBElement(obj, "next");

			String xmlDominio = String.format(strDominio, i + 1);
			String xmlDato = String.format(strDato, i + 1);
			String xmlCondicion = String.format(strCondicion, i + 1);
			String xmlValor = String.format(strValor, i + 1);
			String xmlAgregar = String.format(strBtnAgregar, i + 1);
			String xmlCondAndOr = String.format(strCondicionAndOr, i + 1);

			seleccionarOpcion(xmlDominio, dominio);
			u.hardWait(2);
			seleccionarOpcion(xmlDato, dato);
			u.hardWait(2);
			seleccionarOpcion(xmlCondicion, condicion.toUpperCase());
			u.hardWait(2);
			u.sendKeys(By.xpath(xmlValor), valor);
			u.hardWait(2);
			
			if (otro) {
				u.click(By.xpath(xmlAgregar));
				u.hardWait(2);
				String condAndOr = JsonUtil.getJsonElement(obj, "andOr");
				seleccionarOpcion(xmlCondAndOr, condAndOr);
			}

		}

		testCase.addScreenShotCurrentStep(u, "opcionSeleccionada");

	}

	/**
	 * Metodo para seleccionar una opcion de un select
	 * @param locator (String): valor del locator del select
	 * @param opcion  (String): opcion a seleccionar
	 * @author Andrea Contreras
	 */
	public void seleccionarOpcion(String locator, String opcion) {

		By selectBy = By.xpath(locator);
		me.explicitWaitToBeClickable(selectBy, Duration.ofSeconds(10));
		
		Select select = new Select(u.getDriver().findElement(selectBy));
		u.hardWait(1);		
		select.selectByVisibleText(opcion);
		u.hardWait(1);

	}

	/*   *//**
			 * Método para seleccionar una opción de 'Dominio'
			 * 
			 * @param dominio (String): valor del dominio a seleccionar
			 * @author Andrea Contreras
			 */
	/*
	 * public void seleccionarDominio(String dominio) {
	 * 
	 * u.highLight(xmlDominio); Select plazasS = new
	 * Select(u.getDriver().findElement(xmlDominio));
	 * plazasS.selectByVisibleText(dominio); testCase.addScreenShotCurrentStep(u,
	 * "Dominio"); u.hardWait(2);
	 * 
	 * }
	 * 
	 *//**
		 * Método para seleccionar una opción de 'Dato'
		 * 
		 * @param dato (String): valor del dato a seleccionar
		 * @author Andrea Contreras
		 */
	/*
	 * public void seleccionarDato(String dato) { u.scrollToElement(xmlSeccion4);
	 * 
	 * u.highLight(xmlDato); Select plazasS = new
	 * Select(u.getDriver().findElement(xmlDato));
	 * plazasS.selectByVisibleText(dato); testCase.addScreenShotCurrentStep(u,
	 * "Dato"); u.hardWait(2);
	 * 
	 * }
	 * 
	 *//**
		 * Método para seleccionar una opción de 'condicion'
		 * 
		 * @param condicion (String): valor de la condicion a seleccionar
		 * @author Andrea Contreras
		 */
	/*
	 * public void seleccionarCondicion(String condicion) {
	 * u.scrollToElement(xmlSeccion4);
	 * 
	 * u.highLight(xmlCondicion); Select plazasS = new
	 * Select(u.getDriver().findElement(xmlCondicion));
	 * plazasS.selectByVisibleText(condicion); testCase.addScreenShotCurrentStep(u,
	 * "Condicion"); u.hardWait(2);
	 * 
	 * }
	 * 
	 * 
	 *//**
		 * Método para dar click en el botón Agregar
		 * 
		 * @author Andrea Contreras
		 *//*
			 * public void clickBtnAgregar() { u.scrollToElement(xmlSeccion4);
			 * 
			 * u.highLight(xmlBtnAgregar); testCase.addScreenShotCurrentStep(u, "Agregar1");
			 * u.click(xmlBtnAgregar); testCase.addScreenShotCurrentStep(u, "Agregar2");
			 * 
			 * }
			 */



	//SECCION 5 - Genera Tu Reporte

	/**
	 * Método para dar check a alguna de las opciones sobre como generar el
	 * reporte:'En Pantalla' o 'Excel'
	 * 
	 * @param opcion(String): opciones para generar el reporte 'Pantalla' o 'Excel'
	 * @author Andrea Contreras
	 */
	public void seleccionarGeneraTuReporte(String opcion) {

		u.scrollToElement(xmlSeccion5);

		switch (opcion.toUpperCase()) {
		
		case "PANTALLA":
			u.click(xmlReportePantalla);
			break;

		case "EXCEL":
			u.click(xmlReporteExcel);
			break;

		}

		testCase.addScreenShotCurrentStep("Reporte");

	}

	/**
	 * Método para dar click en el botón 'Genera Estadísticas'
	 * 
	 * @param opcion(String): opcion que se selecciona para generar el reporte
	 *                        'Pantalla' o 'Excel'
	 * @author Andrea Contreras
	 */
	public void clickBtnGeneraEstadisticas(String opcion) {

		switch (opcion.toUpperCase()) {
		case "PANTALLA":
			u.highLight(xmlBtnGenerarEstadPantalla);
			testCase.addScreenShotCurrentStep(u, "Reporte2");
			u.click(xmlBtnGenerarEstadPantalla);
			break;

		case "EXCEL":
			u.highLight(xmlBtnGenerarEstadExcel);
			testCase.addScreenShotCurrentStep(u, "Reporte2");
			u.click(xmlBtnGenerarEstadExcel);
			break;

		}

		testCase.addScreenShotCurrentStep(u, "Reporte3");

	}

	/*
	 * Métodos apartado 'Resultados'
	 ******************************************************************************************/

	/*
	 * Métodos ventana/panel 'Agregar Campos'
	 ******************************************************************************************/
	/**
	 * Método para seleccionar una opción de 'Tipo de informacion' de la ventana
	 * 'Agregar Campos'
	 * 
	 * @param informacion (String): valor del tipo de informacion a seleccionar
	 * @author Andrea Contreras
	 */
	public void seleccionarInformacion(String informacion) {
		// u.scrollToElement(xmlSeccion3);

		u.highLight(xmlTipoInfo);
		Select plazasS = new Select(u.getDriver().findElement(xmlTipoInfo));
		plazasS.selectByVisibleText(informacion);
		testCase.addScreenShotCurrentStep(u, "Informacion");
		u.hardWait(2);

	}

	/**
	 * Método para dar click en el boton 'Aceptar' de la ventana 'Agregar Campos'
	 * 
	 * @author Andrea Contreras
	 */
	public void clickBtnAceptar() {

		u.highLight(xmlBtnAceptar);
		testCase.addScreenShotCurrentStep(u, "aceptar");
		u.click(xmlBtnAceptar);
		// u.unhighLight(xmlBtnAceptar);

	}

	/**
	 * Método para seleccionar las columnas a incluir de la ventana 'Agregar Campos'
	 * 
	 * @param dato (String): dato de las columnas a incluir
	 * @author Andrea Contreras
	 */
	public void seleccionarColumnas(ArrayList<String> dato) {

		for (int i = 0; i < dato.size(); ++i) {

			me.explicitWaitToBeClickable(xmlTablaDatos, Duration.ofSeconds(20));
			List<WebElement> allOptions = u.getDriver().findElements(xmlTablaDatos);

			for (WebElement option : allOptions) {
				if (option.getText().equals(dato.get(i))) {
					u.hardWait(2);
					option = option.findElement(By.xpath(".//ancestor::td[1]/preceding-sibling::td/div/div/div[2]"));
					u.hardWait(1);
					option.click();
					u.hardWait(1);
				}
			}

		}

		testCase.addScreenShotCurrentStep(u, "seleccionarCampos");

	}

	/**
	 * Metodo para seleccionar todas las columnas de la ventana 'Agregar Campos' 
	 * @author Andrea Contreras
	 */
	public void seleccionarTodasLasColumnas() {

		me.explicitWaitToBeClickable(xmlAllColumns, Duration.ofSeconds(30)).click();
		testCase.addScreenShotCurrentStep(u, "despuesAgregarTodosCampos");

	}
	
	public String obtenerFechaActual(String formato) {
		
		SimpleDateFormat formatFecha = new SimpleDateFormat(formato);
		//Date date = Calendar.getInstance().getTime();
		//String fecha = formatFecha.format(date);
		
		LocalDateTime datetime = LocalDateTime.now();
		
		//se resta un mes
		datetime = datetime.minusMonths(1);
		
		String fecha = DateTimeFormatter.ofPattern(formato).format(datetime); 
		
		return fecha;
	}

		
	
	/**
	 * Metodo para descargar y abrir el excel desde la pestaña de descargas del navegador
	 * @return correct: true si dio click al archivo de excel
	 * @author Andrea Contreras
	 * @throws FindFailed 
	 */
	public Boolean abrirExcelEdge2(String fecha, int numeroPestana) throws FindFailed {
		 
		boolean correct = false; 
		
		u.waitForLoadPage();
		
		u.hardWait(2);
			
		ArrayList<String> tabs = me.addNewTab();
		
		u.getDriver().switchTo().window(tabs.get(numeroPestana));
		 		
		u.get("edge://downloads/all");
		
		u.waitForLoadPage();
		
		testCase.addScreenShotCurrentStep("downloads");
		
		List<WebElement> allOptions = u.getDriver().findElements(xmlDescargas);
		
		for(WebElement option : allOptions) {
			
			String text = option.getText();
			System.out.print(text);
			
			 if(text.contains("_reporte.xlsx")&&text.contains(fecha+"_")) { 
				 // me.highlight(option);				 
				 option.click(); 
				 testCase.addScreenShotCurrentStep("abrirExcel");
				 correct = true; 
				 break; 
				 }
			 
			
		}
		
		u.hardWait(30);
		
		testCase.addScreenShotCurrentStep("capturaExcel");
		
		me.clickElementSikuli("ReportesCNT", "CloseExcel"); // Da click en la tachita del excel para cerrarlo y continuar

		return correct;
	}

	/**
	 * Metodo para descargar y abrir el excel NOTA: mientras se utilice este metodo
	 * no se debe abrir ninguna otra ventana ya que se interpone y Sikuli no
	 * encuentra los elementos.
	 * @return correct: true si encontro el elemento o false en caso contrario
	 * @throws FindFailed
	 * @author Maria Dueñas
	 */
	public Boolean abrirExcelEdge(String nomArchivo) throws FindFailed {

		u.waitForLoadPage();
		
		u.hardWait(10);

		testCase.addScreenShotCurrentStep("Open");
		Boolean correct = me.clickElementSikuli("ReportesCNT", nomArchivo); // Da click en boton Open de la ventana de
																		// descarga
		if (correct == false) {
			correct = me.clickElementSikuli("ReportesCNT", "OpenFile2");
		}

		u.waitForLoadPage();

		u.hardWait(20);
		// Da click en boton Yes cuando aparece ventana indicando que el excel esta
		// corrompido NOTA: Si no aparece la ventana no hay problema
		me.clickElementSikuli("ReportesCNT", "Yes");
		u.hardWait(3);

		testCase.addScreenShotCurrentStep("capturaExcel");

		me.clickElementSikuli("ReportesCNT", "CloseExcel"); // Da click en la tachita del excel para cerrarlo y continuar con
														// los pasos

		return correct;
	}
	
		/**
	 * Metodo para agregar una nueva pesta�a en el navegador y cambiar el control del driver
	 * @author Andrea Contreras
	 */
	public ArrayList<String> addNewTab(){
		
		((JavascriptExecutor) u.getDriver()).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(u.getDriver().getWindowHandles()); 
		
		return tabs;
	}
	

}
