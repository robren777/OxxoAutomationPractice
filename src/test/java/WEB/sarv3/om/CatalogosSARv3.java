package WEB.sarv3.om;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CatalogosSARv3 {
	TestCase testCase;
	SeleniumUtil u;
	ReportesSARv3 r;
	MetodosExtra me;
	
	
	/*
	 * Localizadores Configuracion de Siniestros y Responsables de PP
	 ***************************************************************************************/
	
	By xmlNegocio = By.id("idNegocioSel");
	By xmlTipoSiniestro = By.id("idTipoSiniestroSel");
	By xmlSubtipo = By.id("idSubtipoSel");
	By xmlBuscarBtn = By.id("btnBuscarPlantilla");
	
	By xmlProtPatrimonialDiv = By.xpath("//*[@id=\"divFormularioPlantillaPre\"]");
	By xmlPlaza = By.id("idPlazaSel");
	
	By xmlCriteriosDiv = By.xpath("//*[@id=\"divFormularioPlantillaCompleta\"]/div[1]");
	By xmlContSinTab1 = By.xpath("//*[@id=\"divFormularioPlantillaCompleta\"]/div[2]");
	By xmlEncargadosDiv = By.xpath("//*[@id=\"divFormularioPlantillaCompleta\"]/div[3]");
	By xmlContSinTab2 = By.xpath("//*[@id=\"divFormularioPlantillaCompleta\"]/div[4]");
	By xmlOSRegionalesDiv = By.xpath("//*[@id=\"divFormularioPlantillaCompleta\"]/div[5]");
	By xmlContSinTab3 = By.xpath("//*[@id=\"divFormularioPlantillaCompleta\"]/div[6]");
	By xmlGerentesDiv = By.xpath("//*[@id=\"divFormularioPlantillaCompleta\"]/div[7]");
	
	By xmlTableEncargados = By.xpath("//*[@id=\"gview_tableEncargadosAsesoresExtAgregados\"]");
	By xmlTableCandidatosEncargados = By.xpath("//*[@id=\"gview_tableListCandidatosEncargados\"]");
	By xmlTableCandidatosExternos = By.xpath("//*[@id=\"gview_tableModalListCandidatosExternosRegistrados\"]");
	By xmlCandidatosOS = By.xpath("//*[@id=\"gview_tableModalListCandidatosOsRegionales\"]");
	By xmlTableCandidatosGerentes = By.xpath("//*[@id=\"gview_tableModalListCandidatosGrte\"]");
	By xmlTableGerentes = By.xpath("//*[@id=\"gview_tableGrte\"]");
	
	By xmlGrabarBtn = By.xpath("//*[@id=\"btnGrabarSiniestro\"]");
	By xmlGuardado = By.xpath("//div[contains(text(), 'Guardado correctamente')]");
	
	//Botones Encargados, Asesores y Externos.
	By xmlEliminarEncBtn = By.xpath("//*[@id=\"btnEliminarAgrgados\"]");
	By xmlVerCandidatosEncBtn = By.xpath("//*[@id=\"btnVerEncargados\"]");
	By xmlVerExternosEncBtn = By.xpath("//*[@id=\"btnVerEncargadosRegistrados\"]");
	By xmlAgregarEncBtn = By.xpath("//*[@id=\"modalListaEncargadosAsesoresExt\"]/div/a[1]");
	By xmlRegresarEncBtn = By.xpath("//*[@id=\"modalListaEncargadosAsesoresExt\"]/div/a[2]");
	By xmlAgregarEnc2Btn = By.xpath("//*[@id=\"modalListaExternosRegistrados\"]/div/a[1]");
	By xmlRegresarEnc2Btn = By.xpath("//*[@id=\"modalListaExternosRegistrados\"]/div/a[2]");
	
	//Botones OS y Regionales
	By xmlVerExternosOSBtn = By.xpath("//*[@id=\"btnVerRegional\"]");
	By xmlAgregarOSBtn = By.xpath("//*[@id=\"modalListaOsRegionales\"]/div/a[1]");
	By xmlRegresarOSBtn = By.xpath("//*[@id=\"modalListaOsRegionales\"]/div/a[2]");
	
	//Botones Gerentes y Equipo Basico
	By xmlEliminarGerBtn = By.xpath("//*[@id=\"btnEliminarGrte\"]");
	By xmlVerCandidatosGerBtn = By.xpath("//*[@id=\"btnVerGrte\"]");
	By xmlAgregarGerBtn = By.xpath("//*[@id=\"modalListaGrteEqupoBasico\"]/div/a[1]");
	By xmlRegresarGerBtn = By.xpath("//*[@id=\"modalListaGrteEqupoBasico\"]/div/a[2]");
	
	/*
	 * Localizadores Catalogo de Delincuentes
	 ***************************************************************************************/
	
	By xmlNuevoBtn = By.xpath("//div/a[contains(text(), 'Nuevo')]");
	By xmlEliminarBtn = By.xpath("//*[@id=\"forma\"]/div[3]/div[4]/a[2]");
	
	By xmlMensajeConfirmacion = By.xpath("//*[@id=\"confirmElimTipoDel\"]/center/label");
	By xmlSIBtn = By.xpath("//table/tbody/tr/td[2]/a/label[contains(text(), 'SI')]");
	By xmlEliminado = By.xpath("//*[@id=\"lbMensajesExito\"]");
	
	By xmlTable = By.xpath("//*[@id=\"gview_resultadoDeBusqDelitos\"]");
	By xmlTableTr = By.xpath("//table[@id='resultadoDeBusqDelitos']/tbody/tr");
	
	//Nuevo Delito
	By xmlTipoDelito = By.id("txtNombreDelito");
	By xmlDescDelito = By.id("txtDescDelito");
	By xmlGuardarBtn = By.xpath("//*[@id=\"dialogTipoDel\"]/div[3]/a[1]");
	By xmlMensajeGuardado = By.xpath("//*[@id=\"lbMensajesExito\"]");
	
	
	/*
	 * Localizadores Catalogo de Empleados
	 ***************************************************************************************/
	
	By xmlEmpleado = By.name("tipoEmpleado");
	By xmlNegocioE = By.id("negocios_consulta");
	
	By xmlBuscarEBtn = By.xpath("//*[@id=\"datos\"]/tbody/tr[4]/td/div/a");
	
	By xmlTablaEmpleados = By.xpath("//*[@id=\"gview_listaEmpleados\"]");
	By xmlTablaEmpleadosTr = By.xpath("//table[@id='listaEmpleados']/tbody/tr");
	By xmlEditarEBtn = By.id("btnEditar");
	By xmlNegocios = By.id("negocios");
	By xmlPlazas = By.id("plazas");
	By xmlBotonMover = By.xpath("//*[@id=\"fR\"]");
	By xmlGrabarEBtn = By.xpath("//*[@id=\"divBtnGrabarExterno\"]/table/tbody/tr[2]/td/a");
	By xmlExportar = By.xpath("//*[@id=\"btnExportar\"]");
	
	By xmlActivar = By.xpath("//div/a[contains(text(),'Activar')]");
	By xmlDesactivar = By.xpath("//div/a[contains(text(),'Inactivar')]");
	By xmlMensaje = By.xpath("//*[@id=\"mensajeCorrecto\"]");
	
	/*
	 * Constructor
	 ***************************************************************************************/
	public CatalogosSARv3(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.r = new ReportesSARv3(u, testCase);
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Configuracion de Siniestros y Responsables de PP
	 ***************************************************************************************/
	
	/**
	 * Metodo para ingresar la informacion en los campos de la ventana Configuracion de Siniestros y Responsables de PP
	 * @param negocio
	 * @param tipo
	 * @param subtipo
	 * @return correct: true si se ingresaron correctamente los datos o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposConfigSiniestros(String negocio, String tipo, String subtipo) {
		Boolean correct = false;
		u.hardWait(3);
		Boolean n = r.selectOption(negocio, xmlNegocio);
		Boolean t = r.selectOption(tipo, xmlTipoSiniestro);
		Boolean st = r.selectOption(subtipo, xmlSubtipo);
		
		if (n && t && st) {
			correct = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "camposConfigSiniestros");
		return correct;
	}
	
	/**
	 * Metodo para dar click en el boton Buscar
	 * @author Maria Due�as
	 */
	public void clickBuscarBtn() {
		u.highLight(xmlBuscarBtn);
		testCase.addScreenShotCurrentStep(u, "clickBuscarBtn");
		u.unhighLight(xmlBuscarBtn);
		u.click(xmlBuscarBtn);
		testCase.addScreenShotCurrentStep(u, "clickBuscarBtn");
	}
	 
	/**
	 * Metodo para validar que se muestra  la informaci�n Requerida 
	 * en Protecci�n Patrimonial y se habilita el filtro de Plaza.
	 * @return exist: true asi se muestra la informacion o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean validarInformacionProteccionPlaza() {
		Boolean exist = false;
		
		if (validarInformacion(xmlProtPatrimonialDiv) && validarInformacion(xmlPlaza)) exist = true;
		
		return exist;
	}
	
	/**
	 * Metodo para validar que se muestren en pantalla elementos
	 * @param by
	 * @return exist: true si se muestra correctamente el elemento o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean validarInformacion(By by) {
		Boolean exist = false;
		
		if (u.getDriver().findElement(by).isDisplayed()) {
			
			exist = true;
			
			me.scrollToElement(by);
			u.highLight(by);
			testCase.addScreenShotCurrentStep(u, "validarInformacion");
			u.unhighLight(by);
			
		}else {
			testCase.addScreenShotCurrentStep(u, "validarInformacion");
		}
		
		return exist;
	}
	
	/**
	 * Metodo para seleccionar la plaza
	 * @param plaza
	 * @return correct: true si se selecciono correctamente la plaza o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean selectPlaza(String plaza) {
		
		u.hardWait(2);
		me.scrollToElement(xmlPlaza);
		Boolean correct = r.selectOption(plaza, xmlPlaza);
		
		return correct;
	}
	
	/**
	 * Metodo para validar que se muestre la informaci�n de Criterios Denunciables, 
	 * Encargados, Asesores y Externos, OS y Regionales, Gerentes y Equipo b�sico.
	 * @return exist: true si se muestran correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean validarInfDespuesDePlaza() {
		
		Boolean exist = false;
		
		Boolean c = validarInformacion(xmlCriteriosDiv);
		Boolean e = validarInformacion(xmlEncargadosDiv);
		Boolean r = validarInformacion(xmlOSRegionalesDiv);
		Boolean g = validarInformacion(xmlGerentesDiv);
		
		if (c && e && r && g) exist = true;
		
		return exist;
	}
	
	/**
	 * Metodo para buscar en las tablas los datos de la persona que se desea encontrar
	 * @param nombre
	 * @param apPat
	 * @param apMat
	 * @param tabla
	 * @return exist: true si encuentra los datos o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean buscarEnTabla(String nombre, String apPat, String apMat, String tabla) {
		
		Boolean exist = false;
		
		By xmlTable = By.xpath("");
		String nomTable = "";
		By scroll = By.xpath("");
		
		switch(tabla) {
		case "Encargados, Asesores y Externos":
				xmlTable = xmlTableEncargados;
				nomTable = "tableEncargadosAsesoresExtAgregados";
				scroll = xmlContSinTab1;
			break;
		case "Candidatos Encargados":
			xmlTable = xmlTableCandidatosEncargados;
			nomTable = "tableListCandidatosEncargados";
			scroll = xmlContSinTab1;
		break;
		case "Candidatos Gerentes":
			xmlTable = xmlTableCandidatosGerentes;
			nomTable = "tableModalListCandidatosGrte";
			scroll = xmlContSinTab3;
		break;
		case "Gerentes y Equipo Basico":
			xmlTable = xmlTableGerentes;
			nomTable = "tableGrte";
			scroll = xmlContSinTab3;
		break;
		}
		
		
		By xmlTableTr = By.xpath("//table[@id='"+ nomTable +"']/tbody/tr");
		
		String row = "//table[@id='"+ nomTable +"']/tbody/tr[%s]";
		String name = "//table[@id='"+ nomTable +"']/tbody/tr[%s]/td[3]";
		String apepat = "//table[@id='"+ nomTable +"']/tbody/tr[%s]/td[4]";
		String apemat = "//table[@id='"+ nomTable +"']/tbody/tr[%s]/td[5]";
		String btn = "//table[@id='"+ nomTable +"']/tbody/tr[%s]/td[1]/input";
		
		String dataName = "";
		String dataApePat = "";
		String dataApeMat = "";
		String boton = "";
		
		me.scrollToElement(xmlTable);
		u.highLight(xmlTable);
		testCase.addScreenShotCurrentStep(u, "Tabla");
		u.unhighLight(xmlTable);
		
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
		
		Integer row_count = tablaTr.size();
		
		//System.out.print(row_count + "\n");
		
		if (row_count >= 1) {
			for (int i=1; i <= row_count; i++) {
				
				dataName = String.format(name, Integer.toString(i)); 
				dataApePat = String.format(apepat, Integer.toString(i)); 
				dataApeMat = String.format(apemat, Integer.toString(i)); 
				
				String n = u.getText(By.xpath(dataName));
				String p = u.getText(By.xpath(dataApePat));
				String m = u.getText(By.xpath(dataApeMat));
				//System.out.print("-" + f + "-\n");
				
				if (nombre.equals(n) && apPat.equals(p) && apMat.equals(m)) {
					
					exist = true;
					
					me.scrollToElement(By.xpath(dataName));
					
					boton = String.format(btn, Integer.toString(i));
					dataName = String.format(row, Integer.toString(i)); 
					
					u.highLight(By.xpath(dataName));
					testCase.addScreenShotCurrentStep(u, "buscarEnTabla");
					u.unhighLight(By.xpath(dataName));
					u.click(By.xpath(boton));
					
					break;
					
				}
			}
		}
		
		testCase.addScreenShotCurrentStep(u, "buscarEnTabla");
		return exist;
	}
	
	/**
	 * Metodo para buscar en las tablas los datos de la persona que se desea encontrar
	 * @param nombre
	 * @param apellido
	 * @param tabla
	 * @return exist: true si encuentra los datos o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean buscarEnTabla2(String nombre, String apellido, String tabla) {
		
		Boolean exist = false;
		
		By xmlTable = By.xpath("");
		String nomTable = "";
		By scroll = By.xpath("");
		
		switch(tabla) {
		case "Candidatos Externos":
				xmlTable = xmlTableCandidatosExternos;
				nomTable = "tableModalListCandidatosExternosRegistrados";
				scroll = xmlContSinTab1;
			break;
		case "Candidatos OS":
			xmlTable = xmlCandidatosOS;
			nomTable = "tableModalListCandidatosOsRegionales";
			scroll = xmlContSinTab2;
		break;
		}
		
		
		By xmlTableTr = By.xpath("//table[@id='"+ nomTable +"']/tbody/tr");
		
		String row = "//table[@id='"+ nomTable +"']/tbody/tr[%s]";
		String name = "//table[@id='"+ nomTable +"']/tbody/tr[%s]/td[3]";
		String lastName = "//table[@id='"+ nomTable +"']/tbody/tr[%s]/td[4]";
		String btn = "//table[@id='"+ nomTable +"']/tbody/tr[%s]/td[1]/input";
		
		String dataName = "";
		String dataLastName = "";
		String boton = "";
		
		me.scrollToElement(scroll);
		u.highLight(xmlTable);
		testCase.addScreenShotCurrentStep(u, "Tabla");
		u.unhighLight(xmlTable);
		
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
		
		Integer row_count = tablaTr.size();
		
		//System.out.print(row_count + "\n");
		
		if (row_count >= 1) {
			for (int i=1; i <= row_count; i++) {
				
				dataName = String.format(name, Integer.toString(i)); 
				dataLastName = String.format(lastName, Integer.toString(i)); 
				
				String n = u.getText(By.xpath(dataName));
				String l = u.getText(By.xpath(dataLastName));
				//System.out.print("-" + f + "-\n");
				
				if (nombre.equals(n) && apellido.equals(l)) {
					
					exist = true;
					
					me.scrollToElement(By.xpath(dataName));
					
					boton = String.format(btn, Integer.toString(i));
					dataName = String.format(row, Integer.toString(i)); 
					
					u.highLight(By.xpath(dataName));
					testCase.addScreenShotCurrentStep(u, "buscarEnTabla");
					u.unhighLight(By.xpath(dataName));
					u.click(By.xpath(boton));
					
					break;
					
				}
			}
		}
		
		testCase.addScreenShotCurrentStep(u, "buscarEnTabla");
		return exist;
	}
	
	/**
	 * Metodo para dar click en Eliminar Encargados
	 * @author Maria Due�as
	 */
	public void clickEliminarEncBtn() {
		u.highLight(xmlEliminarEncBtn);
		testCase.addScreenShotCurrentStep(u, "clickEliminarEncBtn");
		u.click(xmlEliminarEncBtn);
		u.unhighLight(xmlEliminarEncBtn);
		
	}
	
	/**
	 * Metodo para dar click en Ver Candidatos encargados
	 * @author Maria Due�as
	 */
	public void clickVerCandidatosEncBtn() {
		u.highLight(xmlVerCandidatosEncBtn);
		testCase.addScreenShotCurrentStep(u, "clickVerCandidatosEncBtn");
		u.click(xmlVerCandidatosEncBtn);
		u.unhighLight(xmlVerCandidatosEncBtn);
		
	}
	
	/**
	 * Metodo para dar click en Agregar de Candidatos encargados
	 * @author Maria Due�as
	 */
	public void clickAgregarEncBtn() {
		u.highLight(xmlAgregarEncBtn);
		testCase.addScreenShotCurrentStep(u, "clickAgregarEncBtn");
		u.click(xmlAgregarEncBtn);
		u.unhighLight(xmlAgregarEncBtn);
		
	}
	
	/**
	 * Metodo para dar click en Regresar de Candidatos encargados
	 * @author Maria Due�as
	 */
	public void clickRegresarEncBtn() {
		u.highLight(xmlRegresarEncBtn);
		testCase.addScreenShotCurrentStep(u, "clickRegresarEncBtn");
		u.click(xmlRegresarEncBtn);
		u.unhighLight(xmlRegresarEncBtn);
		
	}

	/**
	 * Metodo para dar click en Ver Externos encargados
	 * @author Maria Due�as
	 */
	public void clickVerExternosEncBtn() {
		u.highLight(xmlVerExternosEncBtn);
		testCase.addScreenShotCurrentStep(u, "clickVerExternosEncBtn");
		u.click(xmlVerExternosEncBtn);
		u.unhighLight(xmlVerExternosEncBtn);
		
	}
	
	/**
	 * Metodo para dar click en Agregar de Externos encargados
	 * @author Maria Due�as
	 */
	public void clickAgregarEnc2Btn() {
		u.highLight(xmlAgregarEnc2Btn);
		testCase.addScreenShotCurrentStep(u, "clickAgregarEnc2Btn");
		u.click(xmlAgregarEnc2Btn);
		u.unhighLight(xmlAgregarEnc2Btn);
		
	}
	
	/**
	 * Metodo para dar click en Regresar de Externos encargados
	 * @author Maria Due�as
	 */
	public void clickRegresarEnc2Btn() {
		u.highLight(xmlRegresarEncBtn);
		testCase.addScreenShotCurrentStep(u, "clickRegresarEncBtn");
		u.click(xmlRegresarEncBtn);
		u.unhighLight(xmlRegresarEncBtn);
		
	}
	
	/**
	 * Metodo para dar click en Ver Externos OS
	 * @author Maria Due�as
	 */
	public void clickVerExternosOSBtn() {
		u.highLight(xmlVerExternosOSBtn);
		testCase.addScreenShotCurrentStep(u, "clickVerExternosOSBtn");
		u.click(xmlVerExternosOSBtn);
		u.unhighLight(xmlVerExternosOSBtn);
		
	}
	
	/**
	 * Metodo para dar click en Agregar de OS
	 * @author Maria Due�as
	 */
	public void clickAgregarOSBtn() {
		u.highLight(xmlAgregarOSBtn);
		testCase.addScreenShotCurrentStep(u, "clickAgregarOSBtn");
		u.click(xmlAgregarOSBtn);
		u.unhighLight(xmlAgregarOSBtn);
		
	}
	
	/**
	 * Metodo para dar click en Regresar de OS
	 * @author Maria Due�as
	 */
	public void clickRegresarOSBtn() {
		u.highLight(xmlRegresarOSBtn);
		testCase.addScreenShotCurrentStep(u, "clickRegresarOSBtn");
		u.click(xmlRegresarOSBtn);
		u.unhighLight(xmlRegresarOSBtn);
		
	}
	
	/**
	 * Metodo para dar click en Eliminar Gerentes
	 * @author Maria Due�as
	 */
	public void clickEliminarGerBtn() {
		u.highLight(xmlEliminarGerBtn);
		testCase.addScreenShotCurrentStep(u, "clickEliminarGerBtn");
		u.click(xmlEliminarGerBtn);
		u.unhighLight(xmlEliminarGerBtn);
		
	}
	
	/**
	 * Metodo para dar click en Ver Candidatos Gerentes
	 * @author Maria Due�as
	 */
	public void clickVerCandidatosGerBtn() {
		u.highLight(xmlVerCandidatosGerBtn);
		testCase.addScreenShotCurrentStep(u, "clickVerCandidatosGerBtn");
		u.click(xmlVerCandidatosGerBtn);
		u.unhighLight(xmlVerCandidatosGerBtn);
		
	}
	
	/**
	 * Metodo para dar click en Agregar de Candidatos Gerentes
	 * @author Maria Due�as
	 */
	public void clickAgregarGerBtn() {
		u.highLight(xmlAgregarGerBtn);
		testCase.addScreenShotCurrentStep(u, "clickAgregarGerBtn");
		u.unhighLight(xmlAgregarGerBtn);
		u.click(xmlAgregarGerBtn);
	}
	
	/**
	 * Metodo para dar click en Regresar de Candidatos Gerentes
	 * @author Maria Due�as
	 */
	public void clickRegresarGerBtn() {
		u.highLight(xmlRegresarGerBtn);
		testCase.addScreenShotCurrentStep(u, "clickRegresarGerBtn");
		u.click(xmlRegresarGerBtn);
		u.unhighLight(xmlRegresarGerBtn);
		
	}
	/**
	 * Metodo para ir a la seccion OS
	 * @author Maria Due�as
	 */
	public void irSeccionOs() {
		me.scrollToElement(xmlOSRegionalesDiv);
		u.highLight(xmlOSRegionalesDiv);
		testCase.addScreenShotCurrentStep(u, "irSeccionOs");
		u.unhighLight(xmlOSRegionalesDiv);
	}
	
	/**
	 * Metodo para ir a la seccion Gerentes
	 * @author Maria Due�as
	 */
	public void irSeccionGerentes() {
		me.scrollToElement(xmlGerentesDiv);
		u.highLight(xmlGerentesDiv);
		testCase.addScreenShotCurrentStep(u, "irSeccionGerentes");
		u.unhighLight(xmlGerentesDiv);
	}
	
	/**
	 * Metodo para dar click en Grabar
	 * @author Maria Due�as
	 */
	public void clickGrabarBtn() {
		u.highLight(xmlGrabarBtn);
		testCase.addScreenShotCurrentStep(u, "clickGrabarBtn");
		u.click(xmlGrabarBtn);
		u.unhighLight(xmlGrabarBtn);
		
	}
	
	/**
	 * Metodo para validar que aparezca el mensaje "Guardado correctamente"
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean validarMensaje() {
		Boolean exist = false;
		String mensaje = "";
		
		if(u.getDriver().findElement(xmlGuardado).isDisplayed()) {
			
			me.scrollToElement(xmlGuardado);
			u.highLight(xmlGuardado);
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
			u.unhighLight(xmlGuardado);
			
			mensaje = u.getText(xmlGuardado);
			
			if (mensaje.equals("Guardado correctamente")) {
				exist = true;
			}
			
		} else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
		}
		
		return exist;
	}
	
	/*
	 * Metodos Catalogo de Delincuentes
	 ***************************************************************************************/
	
	/**
	 * Metodo para dar click en Nuevo
	 * @author Maria Due�as
	 */
	public void clickNuevoBtn() {
		me.scrollToElement(xmlNuevoBtn);
		u.highLight(xmlNuevoBtn);
		testCase.addScreenShotCurrentStep(u, "NuevoBtn");
		u.unhighLight(xmlNuevoBtn);
		u.click(xmlNuevoBtn);
		testCase.addScreenShotCurrentStep(u, "clickNuevoBtn");
	}
	
	/**
	 * Metodo para ingresar los datos en la ventana Tipo de Delito
	 * @param delito
	 * @param descripcion
	 * @return correct: true si ingresa los datos correctamente o false en caso contrario
	 */
	public Boolean camposDelito(String delito, String descripcion) {
		Boolean correct = false;
		
		try {
			u.highLight(xmlTipoDelito);
			u.sendKeys(xmlTipoDelito, delito);
			testCase.addScreenShotCurrentStep(u, "camposDelito");
			u.unhighLight(xmlTipoDelito);
			
			u.highLight(xmlDescDelito);
			u.sendKeys(xmlDescDelito, descripcion);
			testCase.addScreenShotCurrentStep(u, "camposDelito");
			u.unhighLight(xmlDescDelito);
			
			correct = true;
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "camposDelito");
		}
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en Guardar
	 * @author Maria Due�as
	 */
	public void clickGuardarBtn() {
		u.highLight(xmlGuardarBtn);
		testCase.addScreenShotCurrentStep(u, "GuardarBtn");
		u.unhighLight(xmlGuardarBtn);
		u.click(xmlGuardarBtn);
		testCase.addScreenShotCurrentStep(u, "clickGuardarBtn");
	}
	
	/**
	 * Metodo para editar los datos en la ventana Tipo de Delito
	 * @param delito
	 * @param descripcion
	 * @return correct: true si ingresa los datos correctamente o false en caso contrario
	 */
	public Boolean camposDelitoEditar(String delito, String descripcion) {
		Boolean correct = false;
		
		try {
			testCase.addScreenShotCurrentStep(u, "camposDelito");
			
			u.highLight(xmlTipoDelito);
			u.clear(xmlTipoDelito);
			testCase.addScreenShotCurrentStep(u, "camposDelito");
			u.sendKeys(xmlTipoDelito, delito);
			testCase.addScreenShotCurrentStep(u, "camposDelito");
			u.unhighLight(xmlTipoDelito);
			
			u.highLight(xmlDescDelito);
			u.clear(xmlDescDelito);
			testCase.addScreenShotCurrentStep(u, "camposDelito");
			u.sendKeys(xmlDescDelito, descripcion);
			testCase.addScreenShotCurrentStep(u, "camposDelito");
			u.unhighLight(xmlDescDelito);
			
			correct = true;
		}catch(Exception e) {
			testCase.addScreenShotCurrentStep(u, "camposDelito");
		}
		
		return correct;
	}
	
	/**
	 * Metodo para validar que aparezca el mensaje "Guardado correctamente"
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean validarMensaje2() {
		Boolean exist = false;
		String mensaje = "";
		
		if(u.getDriver().findElement(xmlMensajeGuardado).isDisplayed()) {
			
			me.scrollToElement(xmlMensajeGuardado);
			u.highLight(xmlMensajeGuardado);
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
			u.unhighLight(xmlMensajeGuardado);
			
			mensaje = u.getText(xmlMensajeGuardado);
			
			if (mensaje.equals("Guardado correctamente")) {
				exist = true;
			}
			
		} else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
		}
		
		return exist;
	}
	
	/**
	 * Metodo para validar que aparezca el mensaje "Desea eliminar el/los registros seleccionado(s)"
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean mensajeConfirmacion() {
		Boolean exist = false;
		String mnj = "";
		u.hardWait(2);
		
		if (u.getDriver().findElement(xmlMensajeConfirmacion).isDisplayed()) {
			mnj = u.getText(xmlMensajeConfirmacion);
			u.highLight(xmlMensajeConfirmacion);
			testCase.addScreenShotCurrentStep(u, "mensajeConfirmacion");
			
			if (mnj.equals("Desea eliminar el/los registros seleccionado(s)")) {
				exist = true;
			}
			
			u.unhighLight(xmlMensajeConfirmacion);
		}else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
		}
		
		return exist;
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
	
	/**
	 * Metodo para validar que aparezca el mensaje "Eliminado correctamente"
	 * @return Retorna true si se muestra el mensaje o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean mensajeEliminacion() {
		Boolean exist = false;
		String mnj = "";
		u.hardWait(2);
		
		if (u.getDriver().findElement(xmlEliminado).isDisplayed()) {
			me.scrollToElement(xmlEliminado);
			mnj = u.getText(xmlEliminado);
			u.highLight(xmlEliminado);
			testCase.addScreenShotCurrentStep(u, "mensajeEliminacion");
			
			if (mnj.equals("Eliminado correctamente")) {
				exist = true;
			}
			
			u.unhighLight(xmlEliminado);
		}else {
			testCase.addScreenShotCurrentStep(u, "validarMensaje");
		}
		
		return exist;
	}
	
	/**
	 * Metodo para buscar resultados en grid (la tabla resultadoDeBusqueda)
	 * @param dato
	 * @param td: columna de la tabla
	 * @return exist: true si lo encontro o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean buscarEnTabla3(String dato,  String tdDato, String tdBoton, String accion) {
		Boolean exist = false;
		String btn = "";
		String row = "//table[@id='resultadoDeBusqDelitos']/tbody/tr[%s]/td[" + tdDato + "]";
		if (accion.equals("Eliminar")) {
			btn = "//table[@id='resultadoDeBusqDelitos']/tbody/tr[%s]/td[" + tdBoton + "]/input";
		}else {
			btn = "//table[@id='resultadoDeBusqDelitos']/tbody/tr[%s]/td[" + tdBoton + "]/a";
		}
		
		String data = "";
		
		me.scrollToElement(xmlTable);
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTableTr);
		
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
	
	/*
	 * Metodos Catalogo de Empleados
	 ***************************************************************************************/
	
	/**
	 * Metodo para realizar busqueda en la ventana Catalogo de empleados
	 * @param empleado
	 * @param negocio
	 * @return correct: true si se ingresan correctamente los datos o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean camposCatalogoEmpleados(String empleado, String negocio) {
		Boolean correct = false;
		
		u.click(xmlEmpleado);
		Boolean e = r.selectOption(empleado, xmlEmpleado);
		Boolean n = r.selectOption(negocio, xmlNegocioE);
		
		if (e && n) correct = true;
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el boton Buscar de catalogo de empleados
	 * @author Maria Due�as
	 */
	public void clickBuscarEBtn() {
		u.highLight(xmlBuscarEBtn);
		testCase.addScreenShotCurrentStep(u, "clickBuscarEBtn");
		u.unhighLight(xmlBuscarEBtn);
		u.click(xmlBuscarEBtn);
	}
	
	/**
	 * Metodo para verificar que se muestren resultados en el grid
	 * @return correct: true si se muestran empleados o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean resultadosBusqueda() {
		Boolean correct = r.resultadosBusqueda(xmlTablaEmpleadosTr, xmlTablaEmpleados);
		return correct;
	}
	
	/**
	 * Metodo para buscar en la tabla Empleados los datos de la persona que se desea encontrar
	 * @param nombre
	 * @param apellido
	 * @return exist: true si encuentra los datos o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean buscarEnTablaEmpleados(String nombre, String apellido) {
		
		Boolean exist = false;
		
		String row = "//table[@id='listaEmpleados']/tbody/tr[%s]";
		String name = "//table[@id='listaEmpleados']/tbody/tr[%s]/td[4]";
		String lastName = "//table[@id='listaEmpleados']/tbody/tr[%s]/td[5]";
		String btn = "//table[@id='listaEmpleados']/tbody/tr[%s]/td[1]/input";
		
		String dataName = "";
		String dataLastName = "";
		String boton = "";
		
		me.scrollToElement(xmlTablaEmpleados);
		u.highLight(xmlTablaEmpleados);
		testCase.addScreenShotCurrentStep(u, "Tabla");
		u.unhighLight(xmlTablaEmpleados);
		
		List <WebElement> tablaTr = u.getDriver().findElements(xmlTablaEmpleadosTr);
		
		Integer row_count = tablaTr.size();
		
		//System.out.print(row_count + "\n");
		
		if (row_count >= 1) {
			for (int i=1; i <= row_count; i++) {
				
				dataName = String.format(name, Integer.toString(i)); 
				dataLastName = String.format(lastName, Integer.toString(i)); 
				
				String n = u.getText(By.xpath(dataName));
				String l = u.getText(By.xpath(dataLastName));
				//System.out.print("-" + n + " " + l +"-\n");
				
				if (nombre.equals(n) && apellido.equals(l)) {
					
					exist = true;
					
					boton = String.format(btn, Integer.toString(i));
					dataName = String.format(row, Integer.toString(i)); 
					
					u.highLight(By.xpath(dataName));
					testCase.addScreenShotCurrentStep(u, "buscarEnTabla");
					u.unhighLight(By.xpath(dataName));
					u.click(By.xpath(boton));
					
					break;
					
				}
			}
		}
		
		testCase.addScreenShotCurrentStep(u, "buscarEnTabla");
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton Editar de catalogo de empleados
	 * @author Maria Due�as
	 */
	public void clickEditarEBtn() {
		u.highLight(xmlEditarEBtn);
		testCase.addScreenShotCurrentStep(u, "EditarEBtn");
		u.unhighLight(xmlEditarEBtn);
		u.click(xmlEditarEBtn);
		testCase.addScreenShotCurrentStep(u, "clickEditarEBtn");
	}
	
	/**
	 * Metodo para editar la informacion del empleado
	 * @param negocio
	 * @param plaza
	 * @return correct: true si se ingresaron correctamente los datos, o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean editarEmpleado(String negocio, String plaza) {
		Boolean correct = false;
		
		try {
			
			Boolean n = r.selectOption(negocio, xmlNegocios);
			Boolean p = r.selectOption(plaza, xmlPlazas);
			
			u.highLight(xmlBotonMover);
			testCase.addScreenShotCurrentStep(u, "editarEmpleado");
			u.unhighLight(xmlBotonMover);
			u.click(xmlBotonMover);
			testCase.addScreenShotCurrentStep(u, "editarEmpleado");
			
			if (n && p) correct = true;
			
		}catch(Exception e) {
			correct = false;
		}
		
		return correct;
	}
	
	/**
	 * Metodo para dar click en el boton Grabar de catalogo de empleados
	 * @author Maria Due�as
	 */
	public void clickGrabarEBtn() {
		u.highLight(xmlGrabarEBtn);
		testCase.addScreenShotCurrentStep(u, "GrabarEBtn");
		u.unhighLight(xmlGrabarEBtn);
		u.click(xmlGrabarEBtn);
		testCase.addScreenShotCurrentStep(u, "clickGrabarEBtn");
	}
	
	/**
	 * Metodo para validar que se muestren mensajes de Activacion o inactivacion de usuarios
	 * @return exist: true si aparece correctamente o false en caso contrario
	 * @author Maria Due�as
	 */
	public Boolean validarConfirmacion(String tipo) {
		Boolean exist = false;
		
		String mensaje = "";
		
		switch(tipo) {
		case "Activar":
				if(u.getDriver().findElement(xmlMensaje).isDisplayed()) {
					
					u.highLight(xmlMensaje);
					testCase.addScreenShotCurrentStep(u, "validarConfirmacion");
					u.unhighLight(xmlMensaje);
					
					mensaje = u.getText(xmlMensaje);
					
					if (mensaje.equals("Se activaron los usuarios.")) {
						exist = true;
					}
					
				} 
			break;
		case "Inactivar":
				if(u.getDriver().findElement(xmlMensaje).isDisplayed()) {
					
					u.highLight(xmlMensaje);
					testCase.addScreenShotCurrentStep(u, "validarConfirmacion");
					u.unhighLight(xmlMensaje);
					
					mensaje = u.getText(xmlMensaje);
					
					if (mensaje.equals("Se desactivaron los usuarios.")) {
						exist = true;
					}
					
				} 
			break;
		case "Grabar":
			if(u.getDriver().findElement(xmlMensaje).isDisplayed()) {
				
				u.highLight(xmlMensaje);
				testCase.addScreenShotCurrentStep(u, "validarConfirmacion");
				u.unhighLight(xmlMensaje);
				
				mensaje = u.getText(xmlMensaje);
				
				if (mensaje.equals("Se guard� correctamente el empleado.")) {
					exist = true;
				}
				
			} 
		break;

		}
			
		testCase.addScreenShotCurrentStep(u, "confirmarAlerta");
			
		return exist;
	}
	
	/**
	 * Metodo para dar click en el boton Activar de catalogo de empleados
	 * @author Maria Due�as
	 */
	public void clickActivar() {
		u.highLight(xmlActivar);
		testCase.addScreenShotCurrentStep(u, "Activar");
		u.unhighLight(xmlActivar);
		u.click(xmlActivar);
		testCase.addScreenShotCurrentStep(u, "clickActivar");
	}
	
	/**
	 * Metodo para dar click en el boton Desactivar de catalogo de empleados
	 * @author Maria Due�as
	 */
	public void clickDesactivar() {
		u.highLight(xmlDesactivar);
		testCase.addScreenShotCurrentStep(u, "Desactivar");
		u.unhighLight(xmlDesactivar);
		u.click(xmlDesactivar);
		testCase.addScreenShotCurrentStep(u, "clickDesactivar");
	}
	
	/**
	 * Metodo para dar click en Exportar excel de la ventana Catalogo de empleados
	 * @author Maria Due�as
	 * @throws FindFailed 
	 */
	public void clickExportarBtn() throws FindFailed {
		me.scrollToElement(xmlExportar);
		testCase.addScreenShotCurrentStep("clickExportar");
		Boolean correct = me.clickElementSikuli("SARv3", "Exportar2");
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
		
		u.hardWait(5);
		
		testCase.addScreenShotCurrentStep("capturaExcel");
		
		me.clickElementSikuli("SARv3", "CloseExcel"); //Da click en la tachita del excel para cerrarlo y continuar con los pasos
		
		return correct;
	}
}
