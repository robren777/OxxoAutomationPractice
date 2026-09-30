package WEB.sarv3.om;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CatalogoDelincuentesSARv3 {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	MenuPrincipalSAR mp;
	ReportesSARv3 r;
	
	/*
	 * Localizadores Busqueda Delincuente
	 *******************************************************************************************/
	By xmlContainer = By.id("contenido");
	
	String xmlRenglonMultiple = "//*[@id=\"renglonmultiopciones\"][%s]"; //Modus operandi(1) zona(2) plaza(3)
	String xmlModusOperandiOp = "//div[contains(@id,'modusOperandi')]/span[contains(text(), '%s')]";
	String xmlZonaOp = "//div[contains(@id,'zona')]/span[contains(text(), '%s')]";
	String xmlPlazaOp = "//div[contains(@id,'plaza')]/span[contains(text(), '%s')]";
	String xmlTienda = "//*[@id=\"Contenedorforma\"]/div[%s]";
	String xmlTiendaOp = "//div[contains(@id,'tienda')]/span[contains(text(), '%s')]";
	
	By xmlAliasBuscar = By.id("aliasAuto");
	By xmlCalendario1 = By.xpath("//*[@id=\"Contenedorforma\"]/div[8]/button");
	By xmlCalendario2 = By.xpath("//*[@id=\"Contenedorforma\"]/div[10]/button");
	
	By xmlBuscarBtn = By.id("botonbuscar");
	
	By xmlContDelincuentes = By.xpath("//div[@id='Contenedorresutlado']/div/div");
	By xmlExportarInput = By.xpath("//div[@class='contenedorExportar']/input");
	By xmlExportarExcel = By.xpath("//*[@id=\"formCompleta\"]/div[3]/div[2]/div[1]");
	By xmlVerDetalle = By.xpath("//div[@class='contenedorVerDetalle']/a");
	By xmlCerrarVentana = By.id("cierraVentanaDelincuente");
	
	By xmlCarpetaBandaBtn = By.xpath("//*[@id=\"botonreporte\"]/a");
	
	
	/*
	 * Localizadores Creacion Delincuente
	 *******************************************************************************************/
	
	By xmlMasculino = By.id("1");
	By xmlFemenino = By.id("2");
	By xmlIndefinido = By.id("3");
	By xmlAlias = By.id("idAliasAuxText");
	By xmlEdadAproxDe = By.id("idEdadDesde");
	By xmlEdadAproxHasta = By.id("idEdadHasta");
	By xmlAgregarBtn = By.id("agregaBanda");
	
	By xmlEstaturaDesde = By.id("idEstaturaDesde");
	By xmlEstaturaHasta = By.id("idEstaturaHasta");
	By xmlOjos = By.id("idOjos");
	By xmlColorCabello = By.id("idCabelloColor");
	By xmlTamCabello = By.id("idCabelloTamano");
	By xmlTipoCabello = By.id("idCabelloTipo");
	By xmlTez = By.id("idTez");
	By xmlCejas = By.id("idCejas");
	By xmlBarba = By.id("idBarba");
	By xmlBigote = By.id("idBigote");
	
	By xmlCamisa = By.id("vestimenta1");
	By xmlPantalon = By.id("vestimenta2");
	By xmlGorra = By.id("vestimenta3");
	By xmlZapatos = By.id("novestimenta4");
	By xmlSaco = By.id("vestimenta5");
	By xmlAccesorios = By.id("novestimenta6");
	
	String xmlModusOperandi = "//div/span[contains(text(), '%s')]";
	
	By xmlSenasParticulares = By.id("senasParticulares");
	By xmlDescripcion = By.id("descripcionSP");
	By xmlUbicacion = By.xpath("//*[@id=\"imgBrazoAnteriorIZQ\"]");
	By xmlAgregarSPBtn = By.id("agregaSenaParticular");
	
	By xmlTipoArma = By.id("tipoarma");
	By xmlSubtipoArma = By.id("subtipoarma");
	By xmlArma = By.id("nuevaArma");
	By xmlColorArma = By.id("colorarma");
	By xmlAgregarArmaBtn = By.id("agregaArma");
	By xmlVehiculo = By.id("vehiculos");
	
	By xmlGuardarCambiosBtn = By.id("guardarCambios");
	By xmlVincularReporteBtn = By.id("vincularReporte");
	By xmlAgregarOtroDelincuenteBtn = By.xpath("//*[@id=\"vinculaIdReporte\"]");
	By xmlEliminarReporteBtn = By.xpath("//*[@id=\"eliminarReporte\"]");
	
	
	/*
	 * Constructor
	 *******************************************************************************************/
	
	public CatalogoDelincuentesSARv3(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		me = new MetodosExtra(u, testCase);
		mp = new MenuPrincipalSAR(u, testCase);
		r = new ReportesSARv3(u, testCase);
		
	}
	

	/*
	 * Metodos Busqueda Delincuente
	 *******************************************************************************************/

	/**
	 * Metodo para seleccionar una opcion de un div
	 * @param option
	 * @param by
	 * @return exist: true si se selecciono correctamente la opcion o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean selectOption(String option, String by, String renglon, String id) {
		Boolean exist = false;
		
		By renglonOpciones = By.xpath(String.format(renglon, id));
		me.scrollToElement(renglonOpciones);
		
		try {
			By opcion = By.xpath(String.format(by, option));
			u.click(opcion);
			exist = true;
		}catch(Exception e) {
			exist = false;
		}
		
		u.highLight(renglonOpciones);
		testCase.addScreenShotCurrentStep(u, "selectOption");
		u.unhighLight(renglonOpciones);
		
		return exist;
	}
	
	/**
	 * Metodo para llenar los campos en la ventana Busqueda de delincuentes
	 * @param operandi
	 * @param zona
	 * @param plaza
	 * @param tienda
	 * @param fecha1
	 * @param fecha2
	 * @return exist: true si toda la informacion fue ingresada correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposBusquedaDelincuentes(String operandi, String zona, String plaza, String tienda, String fecha1, String fecha2) {
		Boolean exist = false;
		
		u.waitForLoadPage();
		Boolean o = selectOption(operandi, xmlModusOperandiOp, xmlRenglonMultiple, "1");
		Boolean z = selectOption(zona, xmlZonaOp, xmlRenglonMultiple, "2");
		Boolean p = selectOption(plaza, xmlPlazaOp, xmlRenglonMultiple, "3");
		Boolean t = selectOption(tienda, xmlTiendaOp, xmlTienda, "5");
		Boolean f1 = r.selectDate(fecha1, xmlCalendario1, xmlContainer);
		u.hardWait(5);
		Boolean f2 = r.selectDate(fecha2, xmlCalendario2, xmlContainer);
		u.hardWait(5);
		
		if (o == true && z == true && p == true && t == true && f1 == true && f2 == true ) {
			exist = true;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton Buscar
	 */
	public void clickBuscar() {
		me.scrollToElement(xmlBuscarBtn);
		u.highLight(xmlBuscarBtn);
		testCase.addScreenShotCurrentStep(u, "clickBuscar");
		u.unhighLight(xmlBuscarBtn);
		u.click(xmlBuscarBtn);
	}
	
	/**
	 * Metodo para comprobar que se muestren resultados en el grid
	 * @return exist: true si se muestran delincuentes o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean resultadosGrid() {
		Boolean exist = false;
		
		List <WebElement> rows = u.getDriver().findElements(xmlContDelincuentes);
		Integer rows_count = rows.size();
		
		if (rows_count >= 1) exist = true;
		
		return exist;
	}
	
	/**
	 * Metodo para seleccionar la informacion del delincuente a exportar
	 * @author Maria Due�as
	 */
	public void selectFolio() {
		me.scrollToElement(xmlExportarInput);
		u.highLight(xmlExportarInput);
		testCase.addScreenShotCurrentStep(u, "selectFolioExportar");
		u.unhighLight(xmlExportarInput);
		u.click(xmlExportarInput);
	}
	
	/**
	 * Metodo para dar click en boton exportar excel
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public void exportarExcel() throws FindFailed {
		me.scrollToElement(xmlExportarExcel);
		testCase.addScreenShotCurrentStep("exportarExcel");
		Boolean correct = me.clickElementSikuli("SARv3", "Excel");
	}
	
	/**
	 * Metodo para dar click en ver Detalle
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public void clickVerDetalle() throws FindFailed {
		me.scrollToElement(xmlVerDetalle);
		testCase.addScreenShotCurrentStep("clickVerDetalle");
		Boolean correct = me.clickElementSikuli("SARv3", "VerDetalle");
	}
	
	/**
	 * Metodo para cerrar la ventana ver detalles
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public void clickCerrarVentana() throws FindFailed {
		me.scrollToElement(xmlCerrarVentana);
		testCase.addScreenShotCurrentStep("clickCerrarVentana");
		Boolean correct = me.clickElementSikuli("SARv3", "CerrarDetalles");
	}
	
	/*
	 * Metodos Creacion Delincuente
	 *******************************************************************************************/
	
	/**
	 * Metodo para seleccionar el sexo del delincuente
	 * @param by
	 * @author Maria Due�as
	 */
	public void selectSexo(By by) {
		u.click(by);
		u.highLight(by);
		testCase.addScreenShotCurrentStep(u, "selectSexo");
		u.unhighLight(by);
	}
	
	/**
	 * Metodo para ingresar los datos generales del nuevo delincuente
	 * @param sexo
	 * @param alias
	 * @param edadDe
	 * @param edadHasta
	 * @return exist: true si se ingresaron los datos correctamente o false en caso contrario
	 */
	public Boolean datosGeneralesDelincuente(String sexo, String alias, String edadDe, String edadHasta) {
		Boolean exist = false;
		u.waitForLoadPage();
		try {
			me.scrollToElement(xmlAlias);
			
			switch(sexo) {
			case "Masculino":
					selectSexo(xmlMasculino);
				break;
				
			case "Femenino":
					selectSexo(xmlFemenino);
				break;
				
			case "Indefinido":
					selectSexo(xmlIndefinido);
				break;
				
			}
			
			u.sendKeys(xmlAlias, alias);
			u.highLight(xmlAlias);
			testCase.addScreenShotCurrentStep(u, "sendAlias");
			u.unhighLight(xmlAlias);
			
			r.selectOption(edadDe, xmlEdadAproxDe);
			r.selectOption(edadHasta, xmlEdadAproxHasta);
			
			u.highLight(xmlAgregarBtn);
			testCase.addScreenShotCurrentStep(u, "Agregar");
			u.click(xmlAgregarBtn);
			testCase.addScreenShotCurrentStep(u, "clickAgregar");
			u.unhighLight(xmlAgregarBtn);
			
			exist = true;
		}catch(Exception e) {
			exist = false;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para ingresar los datos de media filiaci�n del nuevo delincuente
	 * @param sexo
	 * @param alias
	 * @param edadDe
	 * @param edadHasta
	 * @return exist: true si se ingresaron los datos correctamente o false en caso contrario
	 */
	public Boolean mediaFiliacionDelincuente(String estaturaDe, String estaturaHasta, String ojos, String colorCab,
			String tamCabello, String tipoCabello, String piel, String cejas, String barba, String bigote) {
		
		Boolean exist = false;
		
		try {
			
			me.scrollToElement(xmlEstaturaDesde);
			
			r.selectOption(estaturaDe, xmlEstaturaDesde);
			r.selectOption(estaturaHasta, xmlEstaturaHasta);
			r.selectOption(ojos, xmlOjos);
			r.selectOption(colorCab, xmlColorCabello);
			r.selectOption(tamCabello, xmlTamCabello);
			r.selectOption(tipoCabello, xmlTipoCabello);
			r.selectOption(piel, xmlTez);
			r.selectOption(cejas, xmlCejas);
			r.selectOption(barba, xmlBarba);
			r.selectOption(bigote, xmlBigote);
			
			exist = true;
			
		}catch(Exception e) {
			exist = false;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para ingresar los datos de vestimenta del nuevo delincuente
	 * @param sexo
	 * @param alias
	 * @param edadDe
	 * @param edadHasta
	 * @return exist: true si se ingresaron los datos correctamente o false en caso contrario
	 */
	public Boolean vestimentaDelincuente(String camisa, String pantalon, String saco, String gorra) {
		
		Boolean exist = false;
		
		try {
			
			me.scrollToElement(xmlCamisa);
			
			u.sendKeys(xmlCamisa, camisa);
			u.highLight(xmlCamisa);
			testCase.addScreenShotCurrentStep(u, "sendCamisa");
			u.unhighLight(xmlCamisa);
			
			u.sendKeys(xmlPantalon, pantalon);
			u.highLight(xmlPantalon);
			testCase.addScreenShotCurrentStep(u, "sendPantalon");
			u.unhighLight(xmlPantalon);
			
			u.sendKeys(xmlSaco, saco);
			u.highLight(xmlSaco);
			testCase.addScreenShotCurrentStep(u, "sendSaco");
			u.unhighLight(xmlSaco);
			
			u.highLight(xmlZapatos);
			u.click(xmlZapatos);
			testCase.addScreenShotCurrentStep(u, "Zapatos");
			u.unhighLight(xmlZapatos);
			
			u.sendKeys(xmlGorra, gorra);
			u.highLight(xmlGorra);
			testCase.addScreenShotCurrentStep(u, "sendGorra");
			u.unhighLight(xmlGorra);
			
			u.highLight(xmlAccesorios);
			u.click(xmlAccesorios);
			testCase.addScreenShotCurrentStep(u, "Accesorios");
			u.unhighLight(xmlAccesorios);
			
			exist = true;
			
		}catch(Exception e) {
			exist = false;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para ingresar los datos de MODUS OPERANDI del nuevo delincuente
	 * @param sexo
	 * @param alias
	 * @param edadDe
	 * @param edadHasta
	 * @return exist: true si se ingresaron los datos correctamente o false en caso contrario
	 */
	public Boolean modusOperandiDelincuente(String modus) {
		
		Boolean exist = false;
		
		try {
			
			By by = By.xpath(String.format(xmlModusOperandi, modus));
			me.scrollToElement(by);
			
			u.highLight(by);
			u.click(by);
			testCase.addScreenShotCurrentStep(u, "modusOperandiDelincuente");
			u.unhighLight(by);
			
			exist = true;
			
		}catch(Exception e) {
			exist = false;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para ingresar los datos de se�as particulares del nuevo delincuente
	 * @param sexo
	 * @param alias
	 * @param edadDe
	 * @param edadHasta
	 * @return exist: true si se ingresaron los datos correctamente o false en caso contrario
	 */
	public Boolean senasParticularesDelincuente(String senas, String descripcion) {
		
		Boolean exist = false;
		
		try {
			
			me.scrollToElement(xmlSenasParticulares);
			
			r.selectOption(senas, xmlSenasParticulares);
			
			u.sendKeys(xmlDescripcion, descripcion);
			u.highLight(xmlDescripcion);
			testCase.addScreenShotCurrentStep(u, "sendDescripcion");
			u.unhighLight(xmlDescripcion);
			
			u.highLight(xmlUbicacion);
			u.click(xmlUbicacion);
			testCase.addScreenShotCurrentStep(u, "Ubicacion");
			u.unhighLight(xmlUbicacion);
			
			exist = true;
			
		}catch(Exception e) {
			exist = false;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para ingresar los datos de arma del nuevo delincuente
	 * @param sexo
	 * @param alias
	 * @param edadDe
	 * @param edadHasta
	 * @return exist: true si se ingresaron los datos correctamente o false en caso contrario
	 */
	public Boolean armaDelincuente(String tipoArma, String subtipoArma, String arma, String colorArma) {
		
		Boolean exist = false;
		
		try {
			
			me.scrollToElement(xmlTipoArma);
			
			r.selectOption(tipoArma, xmlTipoArma);
			r.selectOption(subtipoArma, xmlSubtipoArma);
			r.selectOption(arma, xmlArma);
			r.selectOption(colorArma, xmlColorArma);
			
			exist = true;
			
		}catch(Exception e) {
			exist = false;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para ingresar los datos de arma del nuevo delincuente
	 * @param sexo
	 * @param alias
	 * @param edadDe
	 * @param edadHasta
	 * @return exist: true si se ingresaron los datos correctamente o false en caso contrario
	 */
	public Boolean vehiculoDelincuente(String vehiculo) {
		
		Boolean exist = false;
		
		try {
			
			u.sendKeys(xmlVehiculo, vehiculo);
			u.highLight(xmlVehiculo);
			testCase.addScreenShotCurrentStep(u, "sendVehiculo");
			u.unhighLight(xmlVehiculo);
			
			exist = true;
			
		}catch(Exception e) {
			exist = false;
		}
		
		return exist;
	}
	/**
	 * Metodo para dar click en el boton Agregar SP de Crear delincuente externo
	 * @author Maria Due�as
	 */
	public void clickAgregarSPBtn() {
		u.highLight(xmlAgregarSPBtn);
		testCase.addScreenShotCurrentStep(u, "AgregarSPBtn");
		u.click(xmlAgregarSPBtn);
		testCase.addScreenShotCurrentStep(u, "clickAgregarSPBtn");
		u.unhighLight(xmlAgregarSPBtn);
	}
	
	/**
	 * Metodo para dar click en el boton Agregar Arma de Crear delincuente externo
	 * @author Maria Due�as
	 */
	public void clickAgregarArmaBtn() {
		u.highLight(xmlAgregarArmaBtn);
		testCase.addScreenShotCurrentStep(u, "AgregarArmaBtn");
		u.click(xmlAgregarArmaBtn);
		testCase.addScreenShotCurrentStep(u, "clickAgregarArmaBtn");
		u.unhighLight(xmlAgregarArmaBtn);
	}
	
	/**
	 * Metodo para dar click en el boton Guardar Cambios de Crear delincuente externo
	 * @author Maria Due�as
	 */
	public void clickGuardarCambiosBtn() {
		me.scrollToElement(xmlGuardarCambiosBtn);
		u.highLight(xmlGuardarCambiosBtn);
		testCase.addScreenShotCurrentStep(u, "clickGuardarCambiosBtn");
		u.unhighLight(xmlGuardarCambiosBtn);
		u.click(xmlGuardarCambiosBtn);
	}
	
	/**
	 * Metodo para dar click en el boton Vincular reporte de Crear delincuente externo
	 * @author Maria Due�as
	 */
	public void clickVincularReporteBtn() {
		u.highLight(xmlVincularReporteBtn);
		testCase.addScreenShotCurrentStep(u, "clickVincularReporteBtn");
		u.unhighLight(xmlVincularReporteBtn);
		u.click(xmlVincularReporteBtn);
	}
	
	/**
	 * Metodo para realizar busqueda en Busqueda de Delincuentes
	 * @param alias
	 * @param fecha1
	 * @param fecha2
	 * @return exist: true si ingresa correctamente todos los datos o false en caso contrario
	 */
	public Boolean buscarDelincuentes(String alias, String fecha1, String fecha2) {
		Boolean exist = false;
		
		u.waitForLoadPage();
		
		try {
			
			u.sendKeys(xmlAliasBuscar, alias);
			u.highLight(xmlAliasBuscar);
			testCase.addScreenShotCurrentStep(u, "sendAliasBuscar");
			u.unhighLight(xmlAliasBuscar);
			
			Boolean f1 = r.selectDate(fecha1, xmlCalendario1, xmlContainer);
			u.hardWait(5);
			Boolean f2 = r.selectDate(fecha2, xmlCalendario2, xmlContainer);
			u.hardWait(5);
			
			if (f1 == true && f2 == true ) {
				exist = true;
			}
			
		}catch(Exception e) {
			exist = false;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton Ir a Carpeta de Banda
	 * @author Maria Due�as
	 */
	public void clickCarpetaBandaBtn() {
		me.scrollToElement(xmlCarpetaBandaBtn);
		u.highLight(xmlCarpetaBandaBtn);
		testCase.addScreenShotCurrentStep(u, "CarpetaBandaBtn");
		u.unhighLight(xmlCarpetaBandaBtn);
		u.click(xmlCarpetaBandaBtn);
		testCase.addScreenShotCurrentStep(u, "clickCarpetaBandaBtn");
	}
	
	/**
	 * Metodo para dar click en el boton Agregar otro Delincuente
	 * @author Maria Due�as
	 */
	public void clickAgregarDelincuenteBtn() {
		me.scrollToElement(xmlAgregarOtroDelincuenteBtn);
		u.highLight(xmlAgregarOtroDelincuenteBtn);
		testCase.addScreenShotCurrentStep(u, "AgregarDelincuenteBtn");
		u.unhighLight(xmlAgregarOtroDelincuenteBtn);
		u.click(xmlAgregarOtroDelincuenteBtn);
		testCase.addScreenShotCurrentStep(u, "clickAgregarDelincuenteBtn");
	}
	
	/**
	 * Metodo para dar click en el boton Eliminar Reporte
	 * @author Maria Due�as
	 */
	public void clickEliminarReporteBtn() {
		me.scrollToElement(xmlEliminarReporteBtn);
		u.highLight(xmlEliminarReporteBtn);
		testCase.addScreenShotCurrentStep(u, "EliminarReporteBtn");
		u.unhighLight(xmlEliminarReporteBtn);
		u.click(xmlEliminarReporteBtn);
		testCase.addScreenShotCurrentStep(u, "clickEliminarReporteBtn");
	}
	
	/**
	 * Metodo para dar click en Aceptar o Ok en alertas
	 * @return exist: true si dio click correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean confirmarAlerta() {
		Boolean exist = false;
		
		try {
		
			//Store the alert in a variable
			Alert alert = u.getDriver().switchTo().alert();

			//Store the alert in a variable for reuse
			String text = alert.getText();
			System.out.print(text + "\n");
			testCase.addScreenShotCurrentStep(u, "alerta");
			//Press the OK button
			alert.accept();
			testCase.addScreenShotCurrentStep(u, "confirmarAlerta");
			exist = true;
			
		}catch(Exception e) {
			exist = false;
		}
		
		return exist;
	}
	
}
