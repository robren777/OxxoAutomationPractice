package WEB.sarv3.om;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.FindFailed;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class ReportesSARv3 {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	By xmlContenedor = By.xpath("//*[@id=\"contenedorGeneral\"]");
	/*
	 * Localizadores Menu Reportes
	 ********************************************************************************************/
	
	By xmlReportes = By.xpath("//div[contains(text(), 'Reportes')]");
	
	//Consulta de Password para Ajustes en POS

	By xmlAlertaError = By.xpath("//*[@id=\"erroresGral\"]");
	
	By xmlNegocio = By.xpath("//*[@id=\"sourceNegocios\"]");
	
	By xmlPlaza = By.xpath("//*[@id=\"sourcePlaza\"]");
	By xmlBuscarBtn = By.xpath("//*[@id=\"contenedor_capa4\"]/div[5]/div[1]/a");
	
	By xmlTable = By.xpath("//*[@id=\"gview_resultadoDeBusqueda\"]/div[3]");
	By xmlTableTr = By.xpath("//table[@id='resultadoDeBusqueda']/tbody/child::tr");
	
	//Reporte Semaforizaci�n de Siniestros
	
	By xmlNegocioSS = By.xpath("//*[@id=\"negocioSelec\"]");
	By xmlBuscarSSBtn = By.xpath("//div/a[contains(text(), 'Buscar')]");
	By xmlExportarBtn = By.xpath("//a/label[contains(text(), 'Exportar a Excel')]");
	
	//Reporte Nivel de Servicio
	
	By xmlNegocioNS = By.xpath("//*[@id=\"idNegocio\"]");
	By xmlCalendario1 = By.xpath("//*[@id=\"contenedor_capa2\"]/p[7]/button");
	By xmlCalendario2 = By.xpath("//*[@id=\"contenedor_capa3\"]/p[7]/button");
	String xmlDiaNS = "//table/tbody/tr/td/a[contains(text(), '%s')]";
	By xmlMesNS = By.xpath("//div/select[@class='ui-datepicker-month']");
	By xmlAnioNS = By.xpath("//div/select[@class='ui-datepicker-year']");
	By xmlContainer = By.xpath("//*[@id=\"forma\"]");
	By xmlExportarNSBtn = By.xpath("//div/a[contains(text(), 'Exportar a Excel')]");
	By xmlCrearGrafica = By.xpath("//*[@id=\"exportar\"]");
	
	//Indicadores de Gestion
	
	By xmlCompania = By.xpath("//*[@id=\"contenedor_capa2\"]/p[2]/select");
	
	//Carta de reclamacion de mercancia
	
	By xmlPlazaCR = By.xpath("//*[@id=\"idPlaza\"]");
	By xmlTiendaCR = By.xpath("//*[@id=\"idTienda\"]");
	By xmlGenerarReporte = By.xpath("//div/a[contains(text(), 'Generar Reporte')]");
	
	//Seguimiento de la Denuncia
	
	By xmlCalendario1SD = By.xpath("//*[@id=\"contBusDer\"]/div[20]/div[2]/button");
	By xmlCalendario2SD = By.xpath("//*[@id=\"contBusDer\"]/div[20]/div[4]/button");
	By xmlVerDetalles = By.xpath("//div/a[contains(text(), 'Ver Detalle')]");
	By xmlRegresarBusqueda = By.xpath("//div/a[contains(text(), 'Regresar a B�squeda')]");
	
	/*
	 * Constructor
	 ********************************************************************************************/
	public ReportesSARv3(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Menu Reportes
	 ********************************************************************************************/
	
	/**
	 * Metodo para ingresar los campos obligatorios en la ventana Consulta de Password
	 * @param negocio
	 * @param plaza
	 * @return exist: true si se seleccionaron correctamente los datos en la lista desplegable o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposObligatorios(String negocio, String plaza) {
		Boolean exist = true;
		
		Boolean n = selectOption(negocio, xmlNegocio);
		Boolean p = selectOption(plaza, xmlPlaza);
		
		if (n == true && p == true) {
			exist = true;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton buscar
	 * @author Maria Due�as
	 */
	public void clickBuscarBtn() {
		u.highLight(xmlBuscarBtn);
		testCase.addScreenShotCurrentStep(u, "clickBuscarBtn");
		u.mouseOverAndClick(xmlBuscarBtn);
		u.unhighLight(xmlBuscarBtn);
		
	}
	
	/**
	 * Metodo para verificar que se obtengan resultados depues de una busqueda
	 * @return exist: true si encontro resultados en el grid, false der lo contrario
	 * @author Maria Due�as
	 */
	public Boolean validarResultados() {
		Boolean exist = false;
		
		exist = resultadosBusqueda(xmlTableTr, xmlTable);
		
		return exist;
	}
	
	/**
	 * Metodo para ingresar los datos de los campos en la ventana semaforizacion
	 * @param negocio
	 * @return exist: true si se ingresaron correctamente los datos o false de lo contrario
	 * @author Maria Due�as
	 */
	public Boolean camposSemaforizacion(String negocio) {
		Boolean exist = true;
		
		Boolean n = selectOption(negocio, xmlNegocioSS);
		
		if (n == true) {
			exist = true;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton buscar de Semaforizacion
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public void clickBuscarSSBtn() throws FindFailed {
		me.scrollToElement(xmlBuscarSSBtn);
		u.highLight(xmlBuscarSSBtn);
		testCase.addScreenShotCurrentStep(u, "clickBuscarSSBtn");
		u.click(xmlBuscarSSBtn);
		u.unhighLight(xmlBuscarSSBtn);
		
	}
	
	/**
	 * Metodo para dar click en el boton Exportar excel
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public void clickExportarBtn() throws FindFailed {
		/*u.highLight(xmlExportarBtn);
		testCase.addScreenShotCurrentStep(u, "clickExportarBtn");
		u.unhighLight(xmlExportarBtn);
		u.click(xmlExportarBtn);*/
		me.scrollToElement(xmlExportarBtn);
		testCase.addScreenShotCurrentStep("clickExportarBtn");
		Boolean correct = me.clickElementSikuli("SARv3", "Exportar");
	}
	
	/**
	 * Metodo para descargar y abrir el excel NOTA: mientras se utilice este metodo no se debe
	 * abrir ninguna otra ventana ya que se interpone y Sikuli no encuentra los elementos.
	 * @return correct: true si encontro el elemento o false en caso contrario 
	 * @throws FindFailed
	 * @author Maria Due�as
	 */
	public Boolean abrirExcel() throws FindFailed {
	
		u.waitForLoadPage();
		
		testCase.addScreenShotCurrentStep("Open");
		Boolean correct = me.clickElementSikuli("SARv3", "Open"); //Da click en boton Open de la ventana de descarga
		u.waitForLoadPage();
		
		//me.clickElementSikuli("SARv3", "Allow"); //Da click en boton allow 
		
		u.hardWait(5);
		//Da click en boton Yes cuando aparece ventana indicando que el excel esta corrompido NOTA: Si no aparece la ventana no hay problema
		me.clickElementSikuli("SARv3", "Yes"); 
		u.hardWait(3);
		
		testCase.addScreenShotCurrentStep("capturaExcel");
		
		me.clickElementSikuli("SARv3", "CloseExcel"); //Da click en la tachita del excel para cerrarlo y continuar con los pasos
		
		return correct;
	}
	
	 /*
	  * Metodos Nivel de Servicio
	  *****************************************************************************************/
	
	/**
	 * Metodo para ingresar los datos de los campos en ventana de Nivel de servicio
	 * @param negocio
	 * @param fecha1
	 * @param fecha2
	 * @return exist: true si ingresa los datos correctamente o false en caso contrario
	 */
	public Boolean camposNivelServicio(String negocio, String fecha1, String fecha2) {
		Boolean exist = true;
		
		Boolean n = selectOption(negocio, xmlNegocioNS);
		Boolean fechaInicio = selectDate(fecha1, xmlCalendario1, xmlContainer);
		Boolean fechaFin = selectDate(fecha2, xmlCalendario2, xmlContainer);
		
		if (n == true && fechaInicio == true && fechaFin == true) {
			exist = true;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para dar click en Exportar excel de la ventana Nuevo servicio
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public void clickExportarNSBtn() throws FindFailed {
		me.scrollToElement(xmlExportarNSBtn);
		testCase.addScreenShotCurrentStep("clickExportarNSBtn");
		Boolean correct = me.clickElementSikuli("SARv3", "Exportar");
	}
	
	/**
	 * Metodo para dar click en crear grafica }
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public void clickGenerarGrafica() throws FindFailed {
		testCase.addScreenShotCurrentStep("clickGenerarGrafica");
		Boolean correct = me.clickElementSikuli("SARv3", "CrearGrafica");
	}
	
	/*
	  * Metodos Indicadores de Gestion
	  *******************************************************************************************/
	
	/**
	 * Metodo para ingresar los datos solicitados en la ventana Indicadores de Gestion
	 * @param compania
	 * @return exist: true si se ingresaron los datos correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposIndicadoresGestion(String compania) {
		Boolean exist = false;
		
		exist = selectOption(compania, xmlCompania);
		
		return exist;
	}
	
	/*
	  * Metodos Saldo de siniestros
	  *******************************************************************************************/
	
	/**
	 * Metodo para ingresar los datos solicitados en la ventana Saldo siniestros
	 * @param compania
	 * @return exist: true si se ingresaron los datos correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposSaldoSiniestros(String compania) {
		Boolean exist = false;
		
		exist = selectOption(compania, xmlCompania);
		
		return exist;
	}
	
	/*
	 * Metodos Carta de reclamacion
	 *******************************************************************************************/
	
	/**
	 * Metodo para ingresar los datos solicitados en la ventana Carta de reclamacion
	 * @param plaza
	 * @param tienda
	 * @return exist: true si se ingresaron los datos correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposCartaReclamacion(String plaza, String tienda) {
		Boolean correct = false;
		
		Boolean p = selectOption(plaza, xmlPlazaCR);
		u.hardWait(5);
		Boolean t = selectOption(tienda, xmlTiendaCR);
		
		if(p == true && t == true) {
			correct = true;
		}
		
		return correct;
	}
	
	/**
	 * Metodo para buscar el folio en la tabla donde se muestran los resultados de la busqueda
	 * @param folioReporte
	 * @return exist: true si lo encontro o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean buscarFolio(String folioReporte, String td) {
		Boolean exist = false;
		String folio = "//table[@id='resultadoDeBusqueda']/tbody/tr[%s]/td[" + td + "]";
		
		String pedido = "";
		
		me.scrollToElement(xmlTable);
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
		Integer row_count = tablaTr.size();
		
		//System.out.print(row_count + "\n");
		
		if (row_count >= 1) {
			for (int i=1; i <= row_count; i++) {
				pedido = String.format(folio, Integer.toString(i)); 
				String f = u.getText(By.xpath(pedido));
				//System.out.print("-" + f + "-\n");
				if (folioReporte.equals(f)) {
					exist = true;
					u.highLight(By.xpath(pedido));
					testCase.addScreenShotCurrentStep(u, "buscarFolio");
					u.click(By.xpath(pedido));
					u.unhighLight(By.xpath(pedido));
					break;
				}
			}
		}
		testCase.addScreenShotCurrentStep(u, "buscarPedido");
		return exist;
	}
	
	/**
	 * Metodo para dar click en Generar reporte
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public void clickGenerarReporte() throws FindFailed {
		me.scrollToElement(xmlGenerarReporte);
		testCase.addScreenShotCurrentStep(u, "clickGenerarReporte");
		me.clickElementSikuli("SARv3", "GenerarReporte");
	}
	
	/*
	 * Metodos Seguimiento de la denuncia
	 ********************************************************************************************/
	
	/**
	 * Metodo para ingresar los datos de los campos en ventana de Seguimiento de la denuncia
	 * @param negocio
	 * @param fecha1
	 * @param fecha2
	 * @return exist: true si ingresa los datos correctamente o false en caso contrario
	 */
	public Boolean camposSeguimientoDenuncia(String negocio, String fecha1, String fecha2) {
		Boolean exist = true;
		
		Boolean n = selectOption(negocio, xmlNegocio);
		Boolean fechaInicio = selectDate(fecha1, xmlCalendario1SD, xmlContainer);
		Boolean fechaFin = selectDate(fecha2, xmlCalendario2SD, xmlContainer);
		
		if (n == true && fechaInicio == true && fechaFin == true) {
			exist = true;
		}
		
		return exist;
	}
	
	public void clickVerDetalles() {
		u.highLight(xmlVerDetalles);
		testCase.addScreenShotCurrentStep(u, "VerDetalles");
		u.unhighLight(xmlVerDetalles);
		u.click(xmlVerDetalles);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "clickVerDetalles");
	}
	
	public void clickRegresarBusqueda() {
		me.scrollToElement(xmlRegresarBusqueda);
		u.highLight(xmlRegresarBusqueda);
		testCase.addScreenShotCurrentStep(u, "RegresarBusqueda");
		u.unhighLight(xmlRegresarBusqueda);
		u.click(xmlRegresarBusqueda);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "clickRegresarBusqueda");
	}
	
	/*
	 * Metodos Despacho Masivos
	 ********************************************************************************************/
	
	/**
	 * Metodo para ingresar los datos de los campos en ventana de Despacho Masivos
	 * @param negocio
	 * @param fecha1
	 * @param fecha2
	 * @return exist: true si ingresa los datos correctamente o false en caso contrario
	 */
	public Boolean camposDespachoMasivos(String negocio, String fecha1, String fecha2) {
		Boolean exist = true;
		
		Boolean n = selectOption(negocio, xmlNegocio);
		Boolean fechaInicio = selectDate(fecha1, xmlCalendario1SD, xmlContainer);
		Boolean fechaFin = selectDate(fecha2, xmlCalendario2SD, xmlContainer);
		
		if (n == true && fechaInicio == true && fechaFin == true) {
			exist = true;
		}
		
		return exist;
	}

	/*
	 * Metodos extra 
	 *****************************************************************************************/
	/**
	 * Metodo para seleccionar una opcion de un select
	 * @param option
	 * @param by
	 * @return exist: true si se selecciono correctamente la opcion o false en caso contrario
	 * @author Maria Due�as
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
	 * Metodo para validar que se muestren los resultados en el grid
	 * @param tableTr
	 * @param table
	 * @return resultado: true si muestra los resultados el grid o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean resultadosBusqueda(By tableTr, By table) {
		Boolean resultado = false;
		
		me.scrollToElement(table);
		List <WebElement> tablaTr = u.getDriver().findElements(tableTr);
		Integer row_count = tablaTr.size();
		System.out.print(row_count + "\n");
		
		if (row_count >= 2 ) {
			resultado = true;
			u.highLight(table);
			testCase.addScreenShotCurrentStep(u, "resultadosBusqueda");
			u.unhighLight(table);
		}else {
			testCase.addScreenShotCurrentStep(u, "resultadosBusqueda");
		}
		
		
		
		return resultado;
	}
	
	/**
	 * Metodo para ingresar la fecha a buscar
	 * @param fecha
	 * @param calendario
	 * @return correct: true si selecciona correctamente la fecha o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean selectDate(String fecha, By calendario, By container) {
		Boolean correct = false;
		
		me.scrollToElement(calendario);
		u.highLight(calendario);
		testCase.addScreenShotCurrentStep(u, "clickCalendario");
		u.unhighLight(calendario);
		u.click(calendario);
		
		String [] f = fecha.split("\\*");
		//System.out.print(f + "\n");
		Boolean anio = selectOption(f[2], xmlAnioNS);
		
		Boolean mes = selectOption(f[1], xmlMesNS);
		
		String dia = String.format(xmlDiaNS, f[0]);
		u.highLight(By.xpath(dia));
		testCase.addScreenShotCurrentStep(u, "clickDia");
		u.unhighLight(By.xpath(dia));
		u.click(By.xpath(dia));
		
		if(mes == true && anio == true) {
			correct = true;
		}
		
		u.mouseOverAndClick(container);
		
		return correct;
	}
	

}
