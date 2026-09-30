package WEB.sarv3.om;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class AdmonCatalogosSARv3 {
	
	TestCase testCase;
	SeleniumUtil u;
	ReportesSARv3 r;
	MetodosExtra me;
	
	/*
	 * Localizadores Tipos y Subtipos de Siniestros
	 ***************************************************************************************/
	
	By xmlNegocio = By.id("negocio");
	By xmlBuscarBtn = By.xpath("//*[@id=\"forma\"]/div[6]");
	By xmlNuevoBtn = By.xpath("//*[@id=\"forma\"]/div[8]/div[5]/a[1]");
	By xmlEliminarBtn = By.xpath("//div/a[contains(text(), 'Eliminar')]");
	By xmlRegresarBtn = By.xpath("//div/a[contains(text(), 'Regresar')]");
	By xmlSiBtn = By.xpath("//table/tbody/tr/td[2]/a[contains(text(), 'SI')]");
	By xmlSIBtn = By.xpath("//*[@id=\"confirmElimTipoSin\"]/center/table/tbody/tr/td[2]/a");

	By xmlTable = By.xpath("//*[@id=\"gview_resultadoDeBusquedaTipSin\"]");
	By xmlTableTr = By.xpath("//table[@id='resultadoDeBusquedaTipSin']/tbody/tr");
	
	By xmlMensajeCorrecto = By.xpath("//*[@id=\"mensajeCorrecto\"]"); 
	By xmlMensajeGuardado = By.xpath("//*[@id=\"mensajeCorrectoGSub\"]");
	
	//Localizadores Nuevo Tipo de Siniestro 
	By xmlTipoSiniestro = By.xpath("//*[@id=\"txtNuevoTipoSin\"]");
	By xmlGuardarBtn = By.xpath("//*[@id=\"dialogNuevoTipoSin\"]/div[5]/a[1]");
	By xmlGuardarBtnE = By.xpath("//*[@id=\"dialogTipoSin\"]/div[5]/a[1]");
	By xmlMensajeConfirmacionTS = By.xpath("//*[@id=\"confirmElimTipoSin\"]/center/label");
	By xmlMensajeEliminacionTS = By.xpath("//*[@id=\"mensajeGeneralTexto\"]");
	
	//Localizadores Editar Tipo de Siniestro
	By xmlTipoSiniestroE = By.xpath("//*[@id=\"txtTipoSin\"]");

	//Localizadores Subtipos de siniestros
	By xmlNuevoBtnS = By.xpath("//*[@id=\"displayTagDiv\"]/div[3]/a[1]");
	
	By xmlNombre = By.xpath("//*[@id=\"txtNuevoNombreSubtipo\"]");
	By xmlDescripcion = By.xpath("//*[@id=\"txtNuevoDescSubtipo\"]");
	By xmlNombreE = By.xpath("//*[@id=\"txtNombreSubtipo\"]");
	By xmlDescripcionE = By.xpath("//*[@id=\"txtDescSubtipo\"]");
	By xmlGuardarBtnS = By.xpath("//*[@id=\"dialogNuevoSubtipoSin\"]/div[4]/a[1]");
	By xmlGuardarBtnSE = By.xpath("//*[@id=\"dialogSubtipoSin\"]/div[4]/a[1]");
	
	By xmlTableS = By.xpath("//*[@id=\"gview_resultadoDeBusqueda\"]");
	By xmlTableTrS = By.xpath("//table[@id='resultadoDeBusqueda']/tbody/child::tr");
	
	By xmlEliminarBtnS = By.xpath("//*[@id=\"displayTagDiv\"]/div[3]/a[2]");
	By xmlRegresarBtnS = By.xpath("//*[@id=\"displayTagDiv\"]/div[3]/a[3]");
	By xmlMensajeConfirmacionST = By.xpath("//*[@id=\"confirmElimSubt\"]/center/label");
	By xmlMensajeEliminacionST = By.xpath("//*[@id=\"mensajeGeneralSubTexto\"]");
	By xmlSiBtnS = By.xpath("//*[@id=\"confirmElimSubt\"]/center/table/tbody/tr/td[2]/a");
	
	/*
	 * Localizadores Catalogo otros negocios
	 ***************************************************************************************/
	
	By xmlNuevoBtnCON = By.xpath("//div/a[contains(text(), 'Nuevo')]");
	By xmlGuardarBtnCON = By.xpath("//*[@id=\"forma2\"]/div[6]/a[1]");
	By xmlGuardarBtnCONE = By.xpath("//*[@id=\"forma4\"]/div[6]/a[1]");
	By xmlGuardarBtnN2 = By.xpath("//*[@id=\"forma2\"]/div[4]/a[1]");
	By xmlGuardarBtnN2E = By.xpath("//*[@id=\"forma4\"]/div[5]/a[1]");
	By xmlGuardarBtnN3 = By.xpath("//*[@id=\"forma2\"]/div[6]/a[1]");
	By xmlGuardarBtnN3E = By.xpath("//*[@id=\"edicion\"]/div[6]/a[1]");
	By xmlMensajeConfirmacion = By.xpath("//*[@id=\"forma3\"]/center/label");
	
	By xmlNombreNegocio = By.xpath("//table/tbody/tr[1]/td[1]/textarea[@id='descneg']");
	By xmlNombreNegocioE = By.xpath("//table/tbody/tr[1]/td[1]/textarea[@id='txtdescneg']");
	By xmlGuardado = By.xpath("//*[@id=\"correctoG\"]/div[2]");
	By xmlEliminado = By.xpath("//*[@id=\"eliminadoC\"]/div[2]");
	
	//Localizadores nivel 2
	By xmlClave = By.id("ccplaza");
	By xmlDescripcionPlaza = By.id("descriplaza");
	By xmlDescripcionPlazaE = By.id("txtdescriplaza");
	
	//Localizadores nivel 3
	By xmlClaveN3 = By.id("desccn3");
	By xmlDescripcionN3 = By.id("descn3");
	By xmlCalle = By.id("nomCalle");
	By xmlNumero = By.id("numLocal");
	By xmlEstado = By.id("catEstado");
	By xmlMunicipio = By.id("catMunicipio");
	
	By xmlDescripcionN3E = By.id("txtdescn3");
	By xmlCalleE = By.id("txtnomCalle");
	By xmlNumeroE = By.id("txtnumLocal");
	By xmlEstadoE = By.id("txtcatEstado");
	By xmlMunicipioE = By.id("txtcatMunicipio");
	
	/*
	 * Constructor
	 ***************************************************************************************/
	public AdmonCatalogosSARv3(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.r = new ReportesSARv3(u, testCase);
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Tipos y Subtipos de Siniestros
	 ***************************************************************************************/
	
	/**
	 * Metodo para ingresar los datos en los campos de la ventana Tipos y Subtipos de siniestros
	 * @param negocio
	 * @return exist: true si se ingreso correctamente el negocio o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposTSSieniestros(String negocio) {
		
		Boolean exist = r.selectOption(negocio, xmlNegocio);
		
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton buscar
	 * @author Maria Due�as
	 */
	public void clickBuscarBtn() {
		u.highLight(xmlBuscarBtn);
		testCase.addScreenShotCurrentStep(u, "clickBuscarBtn");
		u.unhighLight(xmlBuscarBtn);
		u.click(xmlBuscarBtn);
	}
	
	/**
	 * Metodo para validar que se muestren los resultados en el grid
	 * @param tableTr
	 * @param table
	 * @return resultado: true si muestra los resultados el grid o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean resultadosBusqueda() {
		
		Boolean exist = r.resultadosBusqueda(xmlTableTr, xmlTable);
		
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton Nuevo
	 * @author Maria Due�as
	 */
	public void clickNuevoBtn() {
		u.waitForLoadPage();
		u.highLight(xmlNuevoBtn);
		testCase.addScreenShotCurrentStep(u, "clickNuevoBtn");
		u.unhighLight(xmlNuevoBtn);
		u.click(xmlNuevoBtn);
	}
	
	/**
	 * Metodo para ingresar los datos en los campos de la ventana Nuevo tipo de siniestro
	 * @param siniestro
	 * @return exist: true si se ingreso correctamente el negocio o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposNuevoTipoSiniestro(String siniestro) {
		Boolean exist = false;
		u.hardWait(2);
		
		try {
			
			u.sendKeys(xmlTipoSiniestro, siniestro);
			u.highLight(xmlTipoSiniestro);
			testCase.addScreenShotCurrentStep(u, "camposNuevoTipoSiniestro");
			u.unhighLight(xmlTipoSiniestro);
			exist = true;
			
		}catch(Exception e) {
			exist = false;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton Guardar
	 * @author Maria Due�as
	 */
	public void clickGuardarBtn() {
		u.highLight(xmlGuardarBtn);
		testCase.addScreenShotCurrentStep(u, "clickGuardarBtn");
		u.unhighLight(xmlGuardarBtn);
		u.click(xmlGuardarBtn);
	}
	
	/**
	 * Metodo para dar click en el boton Guardar
	 * @author Maria Due�as
	 */
	public void clickGuardarBtnE() {
		u.highLight(xmlGuardarBtnE);
		testCase.addScreenShotCurrentStep(u, "clickGuardarBtnE");
		u.unhighLight(xmlGuardarBtnE);
		u.click(xmlGuardarBtnE);
	}
	
	/**
	 * Metodo para buscar el siniestro 
	 * @param siniestro
	 * @param td: columna de la tabla
	 * @return exist: true si lo encontro o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean buscarSiniestro(String tipoSiniestro,  String tdTipoSiniestro, String tdBoton, String accion) {
		Boolean exist = false;
		String row = "//table[@id='resultadoDeBusquedaTipSin']/tbody/tr[%s]/td[" + tdTipoSiniestro + "]";
		String btn = "";
		if (accion.equals("Eliminar")) {
			btn = "//table[@id='resultadoDeBusquedaTipSin']/tbody/tr[%s]/td[" + tdBoton + "]/input";
		}else {
			btn = "//table[@id='resultadoDeBusquedaTipSin']/tbody/tr[%s]/td[" + tdBoton + "]/a";
		}
		
		String siniestro = "";
		
		me.scrollToElement(xmlTable);
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
		Integer row_count = tablaTr.size();
		
		System.out.print(row_count + "\n");
		
		if (row_count >= 1) {
			for (int i=1; i <= row_count; i++) {
				siniestro = String.format(row, Integer.toString(i)); 
				String f = u.getText(By.xpath(siniestro));
				System.out.print("-" + f + "-\n");
				if (tipoSiniestro.equals(f)) {
					exist = true;
					me.scrollToElement(By.xpath(siniestro));
					u.highLight(By.xpath(siniestro));
					testCase.addScreenShotCurrentStep(u, "buscarSiniestro");
					u.unhighLight(By.xpath(siniestro));
					siniestro = String.format(btn, Integer.toString(i));
					u.highLight(By.xpath(siniestro));
					testCase.addScreenShotCurrentStep(u, "buscarSiniestro");
					u.click(By.xpath(siniestro));
					u.unhighLight(By.xpath(siniestro));
					break;
				}
			}
		}
		
		testCase.addScreenShotCurrentStep(u, "buscarSiniestro");
		return exist;
	}
	
	/**
	 * Metodo para editar Tipo de siniestro
	 * @param siniestro
	 * @return exist: true si se se ingreso el Tipo de siniestro o false en caso contrario
	 */
	public Boolean editarTipoSiniestro(String siniestro) {
		Boolean exist = false;
		u.hardWait(2);
		
		try {
			u.highLight(xmlTipoSiniestroE);
			testCase.addScreenShotCurrentStep(u, "TipoSiniestro");
			u.clear(xmlTipoSiniestroE);
			u.sendKeys(xmlTipoSiniestroE, siniestro);
			testCase.addScreenShotCurrentStep(u, "editarTipoSiniestro");
			u.unhighLight(xmlTipoSiniestroE);
			exist = true;
			
		}catch(Exception e) {
			exist = false;
		}
		
		return exist;
	}

	/**
	 * Metodo para validar que aparezca el mensaje "La informaci�n fue guardada exitosamente."
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean validarMensaje() {
		Boolean exist = false;
		String mensaje = "";
		
		if(u.getDriver().findElement(xmlMensajeCorrecto).isDisplayed()) {
			
			u.highLight(xmlMensajeCorrecto);
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
			u.unhighLight(xmlMensajeCorrecto);
			
			mensaje = u.getText(xmlMensajeCorrecto);
			
			if (mensaje.equals("La informaci�n fue guardada exitosamente.")) {
				exist = true;
			}
			
		} else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
		}
		
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton Nuevo en ventana Subtipos
	 * @author Maria Due�as
	 */
	public void clickNuevoBtnS() {
		u.waitForLoadPage();
		u.highLight(xmlNuevoBtnS);
		testCase.addScreenShotCurrentStep(u, "clickNuevoBtnS");
		u.unhighLight(xmlNuevoBtnS);
		u.click(xmlNuevoBtnS);
	}
	
	/**
	 * Metodo para ingresar los datos en los campos de la ventana Nuevo subtipo de siniestro
	 * @param nombre
	 * @param descripcion
	 * @return exist: true si se ingreso correctamente el negocio o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposNuevoSubtipoSiniestro(String nombre, String descripcion) {
		Boolean exist = false;
		u.hardWait(2);
		
		try {
			
			u.sendKeys(xmlNombre, nombre);
			u.highLight(xmlNombre);
			u.sendKeys(xmlDescripcion, descripcion);
			u.highLight(xmlDescripcion);
			testCase.addScreenShotCurrentStep(u, "camposNuevoSubtipoSiniestro");
			u.unhighLight(xmlNombre);
			u.unhighLight(xmlDescripcion);
			exist = true;
			
		}catch(Exception e) {
			exist = false;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton Guardar
	 * @author Maria Due�as
	 */
	public void clickGuardarBtnS() {
		u.highLight(xmlGuardarBtnS);
		testCase.addScreenShotCurrentStep(u, "clickGuardarBtnS");
		u.unhighLight(xmlGuardarBtnS);
		u.click(xmlGuardarBtnS);
	}
	
	/**
	 * Metodo para dar click en el boton Guardar
	 * @author Maria Due�as
	 */
	public void clickGuardarBtnSE() {
		u.highLight(xmlGuardarBtnSE);
		testCase.addScreenShotCurrentStep(u, "clickGuardarBtnSE");
		u.unhighLight(xmlGuardarBtnSE);
		u.click(xmlGuardarBtnSE);
	}
	
	/**
	 * Metodo para buscar resultados en grid (la tabla resultadoDeBusqueda)
	 * @param dato
	 * @param td: columna de la tabla
	 * @return exist: true si lo encontro o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean buscarEnTabla(String dato,  String tdDato, String tdBoton, String accion) {
		Boolean exist = false;
		String btn = "";
		String row = "//table[@id='resultadoDeBusqueda']/tbody/tr[%s]/td[" + tdDato + "]";
		if (accion.equals("Eliminar")) {
			btn = "//table[@id='resultadoDeBusqueda']/tbody/tr[%s]/td[" + tdBoton + "]/input";
		}else {
			btn = "//table[@id='resultadoDeBusqueda']/tbody/tr[%s]/td[" + tdBoton + "]/a";
		}
		
		String data = "";
		
		me.scrollToElement(xmlTableS);
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTrS);
		
		Integer row_count = tablaTr.size();
		
		//System.out.print(row_count + "\n");
		
		if (row_count >= 1) {
			for (int i=1; i <= row_count; i++) {
				data = String.format(row, Integer.toString(i)); 
				String f = u.getText(By.xpath(data));
				//System.out.print("-" + f + "-\n");
				if (dato.equals(f)) {
					exist = true;
					u.highLight(By.xpath(data));
					testCase.addScreenShotCurrentStep(u, "buscarEnTabla");
					u.unhighLight(By.xpath(data));
					
					data = String.format(btn, Integer.toString(i));
					me.scrollToElement(By.xpath(data));
					u.highLight(By.xpath(data));
					testCase.addScreenShotCurrentStep(u, "buscarEnTabla");
					u.unhighLight(By.xpath(data));
					u.click(By.xpath(data));
					break;
				}
			}
		}
		
		testCase.addScreenShotCurrentStep(u, "buscarEnTabla");
		return exist;
	}
	
	/**
	 * Metodo para ingresar los datos en los campos de la ventana Nuevo subtipo de siniestro
	 * @param nombre
	 * @param descripcion
	 * @return exist: true si se ingreso correctamente el negocio o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposEditarSubtipoSiniestro(String nombre, String descripcion) {
		Boolean exist = false;
		u.hardWait(2);
		
		try {
			u.highLight(xmlNombreE);
			u.highLight(xmlDescripcionE);
			
			u.clear(xmlNombreE);
			u.clear(xmlDescripcionE);
			
			testCase.addScreenShotCurrentStep(u, "camposNuevoSubtipoSiniestro");
			
			u.sendKeys(xmlNombreE, nombre);
			u.sendKeys(xmlDescripcionE, descripcion);
			
			testCase.addScreenShotCurrentStep(u, "camposNuevoSubtipoSiniestro");
			
			u.unhighLight(xmlNombreE);
			u.unhighLight(xmlDescripcionE);
			exist = true;
			
		}catch(Exception e) {
			exist = false;
		}
		
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton Eliminar
	 * @author Maria Due�as
	 */
	public void clickEliminarBtnS() {
		me.scrollToElement(xmlEliminarBtnS);
		u.highLight(xmlEliminarBtnS);
		testCase.addScreenShotCurrentStep(u, "clickEliminarBtnS");
		u.unhighLight(xmlEliminarBtnS);
		u.click(xmlEliminarBtnS);
	}
	
	/**
	 * Metodo para dar click en el boton Si
	 * @author Maria Due�as
	 */
	public void clickSiBtnS() {
		u.highLight(xmlSiBtnS);
		testCase.addScreenShotCurrentStep(u, "clickSiBtnS");
		u.unhighLight(xmlSiBtnS);
		u.click(xmlSiBtnS);
	}
	
	/**
	 * Metodo para dar click en el boton Regresar
	 * @author Maria Due�as
	 */
	public void clickRegresarBtnS() {
		me.scrollToElement(xmlRegresarBtnS);
		u.highLight(xmlRegresarBtnS);
		testCase.addScreenShotCurrentStep(u, "clickRegresarBtnS");
		u.unhighLight(xmlRegresarBtnS);
		u.click(xmlRegresarBtnS);
	}
	
	/**
	 * Metodo para dar click en el boton Eliminar
	 * @author Maria Due�as
	 */
	public void clickEliminarBtn() {
		me.scrollToElement(xmlEliminarBtn);
		u.highLight(xmlEliminarBtn);
		testCase.addScreenShotCurrentStep(u, "clickEliminarBtn");
		u.unhighLight(xmlEliminarBtn);
		u.click(xmlEliminarBtn);
	}
	
	/**
	 * Metodo para dar click en el boton Regresar
	 * @author Maria Due�as
	 */
	public void clickRegresarBtn() {
		me.scrollToElement(xmlRegresarBtn);
		u.highLight(xmlRegresarBtn);
		testCase.addScreenShotCurrentStep(u, "clickRegresarBtn");
		u.unhighLight(xmlRegresarBtn);
		u.click(xmlRegresarBtn);
	}
	
	/**
	 * Metodo para dar click en el boton Si
	 * @author Maria Due�as
	 */
	public void clickSiBtn() {
		u.highLight(xmlSiBtn);
		testCase.addScreenShotCurrentStep(u, "clickSiBtn");
		u.unhighLight(xmlSiBtn);
		u.click(xmlSiBtn);
	}
	
	/**
	 * Metodo para dar click en el boton Si
	 * @author Maria Due�as
	 */
	public void clickSIBtn() {
		u.highLight(xmlSIBtn);
		testCase.addScreenShotCurrentStep(u, "clickSIBtn");
		u.unhighLight(xmlSIBtn);
		u.click(xmlSIBtn);
	}
	
	public Boolean validarMensajesTSS(String tipo) {
		Boolean exist = false;
		String mnj = "";
		u.hardWait(1);
		switch(tipo) {
		
		case "1":  
				if (u.getDriver().findElement(xmlMensajeCorrecto).isDisplayed()) {
					mnj = u.getText(xmlMensajeCorrecto);
					u.highLight(xmlMensajeCorrecto);
					testCase.addScreenShotCurrentStep(u, "mensajeGuardado");
					
					if (mnj.equals("La informaci�n fue guardada exitosamente.")) {
						exist = true;
					}
					
					u.unhighLight(xmlMensajeCorrecto);
				}
			break;
			
		case "2":  
			if (u.getDriver().findElement(xmlMensajeGuardado).isDisplayed()) {
				mnj = u.getText(xmlMensajeGuardado);
				u.highLight(xmlMensajeGuardado);
				testCase.addScreenShotCurrentStep(u, "mensajeGuardado");
				
				if (mnj.equals("Guardado correctamente")) {
					exist = true;
				}
				
				u.unhighLight(xmlMensajeGuardado);
			}
		break;
		
		case "3":  
			if (u.getDriver().findElement(xmlMensajeConfirmacionST).isDisplayed()) {
				mnj = u.getText(xmlMensajeConfirmacionST);
				u.highLight(xmlMensajeConfirmacionST);
				testCase.addScreenShotCurrentStep(u, "mensajeConfirmacion");
				
				if (mnj.equals("Desea eliminar el/los registros seleccionado(s)")) {
					exist = true;
				}
				
				u.unhighLight(xmlMensajeConfirmacionST);
			}
		break;
		
		case "4":  
			if (u.getDriver().findElement(xmlMensajeEliminacionST).isDisplayed()) {
				mnj = u.getText(xmlMensajeEliminacionST);
				u.highLight(xmlMensajeEliminacionST);
				testCase.addScreenShotCurrentStep(u, "MensajeEliminacionST");
				
				if (mnj.equals("Eliminado correctamente")) {
					exist = true;
				}
				
				u.unhighLight(xmlMensajeEliminacionST);
			}
		break;
		
		case "5":  
			if (u.getDriver().findElement(xmlMensajeConfirmacionTS).isDisplayed()) {
				mnj = u.getText(xmlMensajeConfirmacionTS);
				u.highLight(xmlMensajeConfirmacionTS);
				testCase.addScreenShotCurrentStep(u, "mensajeConfirmacion");
				
				if (mnj.equals("Desea eliminar el/los registros seleccionado(s)")) {
					exist = true;
				}
				
				u.unhighLight(xmlMensajeConfirmacionTS);
			}
		break;
		
		case "6":  
			if (u.getDriver().findElement(xmlMensajeEliminacionTS).isDisplayed()) {
				mnj = u.getText(xmlMensajeEliminacionTS);
				u.highLight(xmlMensajeEliminacionTS);
				testCase.addScreenShotCurrentStep(u, "MensajeEliminacionST");
				
				if (mnj.equals("Eliminado correctamente")) {
					exist = true;
				}
				
				u.unhighLight(xmlMensajeEliminacionTS);
			}
		break;
			
		}
		
		return exist;
	}
	/*
	 * Metodos Catalogo otros negocios
	 ***************************************************************************************/
	
	/**
	 * Metodo para dar click en el boton Nuevo
	 * @author Maria Due�as
	 */
	public void clickNuevoBtnCON() {
		u.waitForLoadPage();
		me.scrollToElement(xmlNuevoBtnCON);
		u.highLight(xmlNuevoBtnCON);
		testCase.addScreenShotCurrentStep(u, "clickNuevoBtnCON");
		u.unhighLight(xmlNuevoBtnCON);
		u.click(xmlNuevoBtnCON);
	}
	
	/**
	 * Metodo para dar click en el boton Guardar
	 * @author Maria Due�as
	 */
	public void clickGuardarBtnCON() {
		u.getDriver().findElement(xmlGuardarBtnCON);
		u.highLight(xmlGuardarBtnCON);
		testCase.addScreenShotCurrentStep(u, "clickGuardarBtnCON");
		u.highLight(xmlGuardarBtnCON);
		u.click(xmlGuardarBtnCON);
		me.explicitWaitInvisibilityOfElement(xmlGuardarBtnCON, Duration.ofSeconds(10));
	}
	
	/**
	 * Metodo para dar click en el boton Guardar
	 * @author Maria Due�as
	 */
	public void clickGuardarBtnCONE() {
		u.getDriver().findElement(xmlGuardarBtnCONE);
		u.highLight(xmlGuardarBtnCONE);
		testCase.addScreenShotCurrentStep(u, "clickGuardarBtnCONE");
		u.highLight(xmlGuardarBtnCONE);
		u.click(xmlGuardarBtnCONE);
		me.explicitWaitInvisibilityOfElement(xmlGuardarBtnCONE, Duration.ofSeconds(10));
	}
	/**
	 * Metodo para dar click en el boton Guardar
	 * @author Maria Due�as
	 */
	public void clickGuardarBtnN2() {
		u.getDriver().findElement(xmlGuardarBtnN2);
		u.highLight(xmlGuardarBtnN2);
		testCase.addScreenShotCurrentStep(u, "clickGuardarBtnN2");
		u.highLight(xmlGuardarBtnN2);
		u.click(xmlGuardarBtnN2);
		me.explicitWaitInvisibilityOfElement(xmlGuardarBtnN2, Duration.ofSeconds(10));
	}
	
	/**
	 * Metodo para dar click en el boton Guardar
	 * @author Maria Due�as
	 */
	public void clickGuardarBtnN2E() {
		u.getDriver().findElement(xmlGuardarBtnN2E);
		u.highLight(xmlGuardarBtnN2E);
		testCase.addScreenShotCurrentStep(u, "clickGuardarBtnN2E");
		u.highLight(xmlGuardarBtnN2E);
		u.click(xmlGuardarBtnN2E);
		me.explicitWaitInvisibilityOfElement(xmlGuardarBtnN2E, Duration.ofSeconds(10));
	}
	
	/**
	 * Metodo para dar click en el boton Guardar
	 * @author Maria Due�as
	 */
	public void clickGuardarBtnN3() {
		u.getDriver().findElement(xmlGuardarBtnN3);
		u.highLight(xmlGuardarBtnN3);
		testCase.addScreenShotCurrentStep(u, "clickGuardarBtnN3");
		u.highLight(xmlGuardarBtnN3);
		u.click(xmlGuardarBtnN3);
		me.explicitWaitInvisibilityOfElement(xmlGuardarBtnN3, Duration.ofSeconds(10));
	}
	
	/**
	 * Metodo para dar click en el boton Guardar
	 * @author Maria Due�as
	 */
	public void clickGuardarBtnN3E() {
		u.getDriver().findElement(xmlGuardarBtnN3E);
		u.highLight(xmlGuardarBtnN3E);
		testCase.addScreenShotCurrentStep(u, "clickGuardarBtnN3E");
		u.highLight(xmlGuardarBtnN3E);
		u.click(xmlGuardarBtnN3E);
		me.explicitWaitInvisibilityOfElement(xmlGuardarBtnN3E, Duration.ofSeconds(10));
	}
	
	/**
	 * Metodo para ingresar el nombre del nuevo negocio
	 * @param nombre
	 * @author Maria Due�as
	 */
	public Boolean camposNuevoNegocio(String nombre) {
		Boolean exist = false;
		try {
			u.getDriver().findElement(xmlNombreNegocio);
			u.sendKeys(xmlNombreNegocio, nombre);
			u.highLight(xmlNombreNegocio);
			testCase.addScreenShotCurrentStep(u, "camposNuevoNegocio");
			u.unhighLight(xmlNombreNegocio);
			exist = true;
		}catch(Exception e) {
			
		}
		return exist;
	}
	
	/**
	 * Metodo para editar el nombre del negocio
	 * @param nombre
	 * @author Maria Due�as
	 */
	public void editarNegocio(String nombre) {
		u.getDriver().findElement(xmlNombreNegocioE);
		
		u.highLight(xmlNombreNegocioE);
		u.clear(xmlNombreNegocioE);
		testCase.addScreenShotCurrentStep(u, "camposNuevoNegocio");
		
		u.sendKeys(xmlNombreNegocioE, nombre);
		testCase.addScreenShotCurrentStep(u, "camposNuevoNegocio");
		u.unhighLight(xmlNombreNegocioE);
	}
	
	/**
	 * Metodo para ingresar los campos de nuevo negocio nivel 2
	 * @param clave
	 * @param descripcion
	 * @author Maria Due�as
	 */
	public void camposNuevoNegocioNivel2(String clave, String descripcion) {
		u.getDriver().findElement(xmlClave);
		u.getDriver().findElement(xmlDescripcionPlaza);
		
		u.highLight(xmlClave);
		u.sendKeys(xmlClave, clave);
		
		u.highLight(xmlDescripcionPlaza);
		u.sendKeys(xmlDescripcionPlaza, descripcion);
		testCase.addScreenShotCurrentStep(u, "camposNuevoNegocioNivel2");
		
	}
	
	/**
	 * Metodo para ingresar los campos de nuevo negocio nivel 2
	 * @param descripcion
	 * @author Maria Due�as
	 */
	public void editarCamposNegocioNivel2(String descripcion) {
		u.getDriver().findElement(xmlDescripcionPlazaE);
		
		u.highLight(xmlDescripcionPlazaE);
		u.clear(xmlDescripcionPlazaE);
		testCase.addScreenShotCurrentStep(u, "clearcamposNuevoNegocioNivel2");
		
		u.sendKeys(xmlDescripcionPlazaE, descripcion);
		testCase.addScreenShotCurrentStep(u, "editarCamposNegocioNivel2");
		
	}
	
	/**
	 * Metodo para validar eliminacion
	 * @return exist: true si muestra correctamente el mensaje o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean  mensajeConfirmacion() {
		Boolean exist = false;
		u.hardWait(3);
		String mensaje = u.getText(xmlMensajeConfirmacion);
		if (mensaje.equals("Desea eliminar el/los registros seleccionado(s)")) {
			exist = true;
			u.highLight(xmlMensajeConfirmacion);
		}
		testCase.addScreenShotCurrentStep(u, "mensajeConfirmacion");
		return exist;
		
	}
	
	/**
	 * Metodo para registrar nuevo negocio nivel 3
	 * @param clave
	 * @param descripcion
	 * @param calle
	 * @param numero
	 * @param estado
	 * @param municipio
	 * @author Maria Due�as
	 */
	public void camposNegocioNivel3(String clave, String descripcion, String calle, String numero, String estado, String municipio) {
		u.getDriver().findElement(xmlClaveN3);
		u.getDriver().findElement(xmlDescripcionN3);
		u.getDriver().findElement(xmlCalle);
		u.getDriver().findElement(xmlNumero);
		u.getDriver().findElement(xmlEstado);
		u.getDriver().findElement(xmlMunicipio);
		
		u.highLight(xmlClaveN3);
		u.sendKeys(xmlClaveN3, clave);
		
		u.highLight(xmlDescripcionN3);
		u.sendKeys(xmlDescripcionN3, descripcion);
		
		u.highLight(xmlCalle);
		u.sendKeys(xmlCalle, calle);
		
		u.highLight(xmlNumero);
		u.sendKeys(xmlNumero, numero);
		
		r.selectOption(estado, xmlEstado);
	
		r.selectOption(municipio, xmlMunicipio);
		
		testCase.addScreenShotCurrentStep(u, "camposNegocioNivel3");
		
	}
	
	/**
	 * Metodo para registrar nuevo negocio nivel 3
	 * @param descripcion
	 * @param calle
	 * @param numero
	 * @param estado
	 * @param municipio
	 * @author Maria Due�as
	 */
	public void editarCamposNegocioNivel3(String descripcion, String calle, String numero, String estado, String municipio) {
		u.getDriver().findElement(xmlDescripcionN3E);
		u.getDriver().findElement(xmlCalleE);
		u.getDriver().findElement(xmlNumeroE);
		u.getDriver().findElement(xmlEstadoE);
		u.getDriver().findElement(xmlMunicipioE);
		
		u.highLight(xmlDescripcionN3E);
		u.highLight(xmlCalleE);
		u.highLight(xmlNumeroE);
		u.highLight(xmlEstadoE);
		u.highLight(xmlMunicipioE);
		u.clear(xmlDescripcionN3E);
		u.clear(xmlCalleE);
		u.clear(xmlNumeroE);
		testCase.addScreenShotCurrentStep(u, "limpiarCamposNegocioNivel3");
		
		u.sendKeys(xmlDescripcionN3E, descripcion);
		u.sendKeys(xmlCalleE, calle);
		u.sendKeys(xmlNumeroE, numero);
		r.selectOption(estado, xmlEstadoE);
		r.selectOption(municipio, xmlMunicipioE);
		
		testCase.addScreenShotCurrentStep(u, "editarCamposNegocioNivel3");
		
	}
	
	/**
	 * Metodo para validar que se muestren los mensajes de Eliminado o Guardado 
	 * @param tipoMensaje: Guardado o Eliminado
	 * @return exist: true si se muestra en pantalla o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean mensaje(String tipoMensaje) {
		Boolean exist = false;
		
		String mnjGuardado = "";
		String mnjEliminado = "";
		
		switch(tipoMensaje) {
		
		case "Guardado": 
				if (u.getDriver().findElement(xmlGuardado).isDisplayed()) {
					
					mnjGuardado = u.getText(xmlGuardado);
					u.highLight(xmlGuardado);
					testCase.addScreenShotCurrentStep(u, "mensajeGuardado");
					
					if (mnjGuardado.equals("Guardado correctamente")) {
						exist = true;
					}
					
					u.unhighLight(xmlGuardado);
					
				}
				
			break;
			
		case "Eliminado":
				if (u.getDriver().findElement(xmlEliminado).isDisplayed()) {
					
					mnjEliminado = u.getText(xmlEliminado);
					u.highLight(xmlEliminado);
					testCase.addScreenShotCurrentStep(u, "mensajeEliminado");
					
					if (mnjEliminado.equals("Eliminado correctamente")) {
						exist = true;
					}
					
					u.unhighLight(xmlEliminado);
					
				}
			break;
			
		}
		
		return exist;
	}
}
