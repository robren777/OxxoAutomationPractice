package WEB.util.MantenimientoTiendas;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.util.AuxMethods;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MantenimientoTiendas {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	private AuxMethods aux;

	// MENU PORTAL APLICACIONES
	private By Expansion = By.xpath("//*[text()[normalize-space() = 'Expansi�n']]");
	private By VerificacionYRecepcion = By.xpath("//*[text()[normalize-space()='Checklist de Verificacion y de Recepcion']]");
	
	// MODULO CHECKLIST DE VERIFICACION Y DE RECEPCION
	private By SubTitleVerificacionYRecepcion = By.xpath("//*[text()[normalize-space()='Verificaci�n y Recepci�n de Tiendas']]");
	private By Funciones = By.xpath("//*[text()[normalize-space()='Funciones']]");
	private By Reportes = By.xpath("//*[text()[normalize-space()='Reportes']]");
	
	//APARTADO FUNCIONES
	private By Encuestas = By.xpath("//*[text()[normalize-space()='Encuestas']]");
	private By RepEncuestaPlazas =  By.xpath("//*[text()[normalize-space()='Reporte Encuesta A Plazas']]");
	
	//SECCION ENCUESTAS
	private By btnAgregar = By.xpath("//input[@name='btnAgregar']");
	private By btnSalvar = By.xpath("//input[@name='btnSalvar']");
	
	//Busqueda Encuesta
	private By selectTipo = By.xpath("//select[@name='txtMonTipo']");
	private By OptionTipoGeneral = By.xpath("//td[@class='formData']/select/option[text()[normalize-space()='GENERAL']]"); 
	private By OptionTipoRecepcion = By.xpath("//td[@class='formData']/select/option[text()[normalize-space()='RECEPCION']]"); 
	private By OptionTipoVerificacion = By.xpath("//td[@class='formData']/select/option[text()[normalize-space()='VERIFICACION']]"); 
	
	//Nueva Encuesta
	private By selectNewTipo = By.xpath("//select[@name='txtMonTipoNew']");
	private By OptionNewGeneral = By.xpath("//select[@name='txtMonTipoNew']/option[text()[normalize-space()='GENERAL']]");
	private By inputNewName = By.xpath("//input[@name='txtMonNameNew']");
	private By inputNewVersion = By.xpath("//input[@name='txtVersionNew']");
	private By selectStatusNew = By.xpath("//select[@name='txtStatusNew']");
	private By OptionANew = By.xpath("//select[@name='txtStatusNew']/option[@value='A']");
	private By OptionINew = By.xpath("//select[@name='txtStatusNew']/option[@value='I']");
	
	private By inputName = By.xpath("//input[@name='txtMonName']");
	private By inputVersion = By.xpath("//input[@name='txtVersion']");
	
	private By selectStatus = By.xpath("//select[@name='txtStatus']");
	private By OptionA = By.xpath("//select[@name='txtStatus']/option[@value='A']");
	private By OptionI = By.xpath("//select[@name='txtStatus']/option[@value='I']");

	private By inputIdEncuesta = By.xpath("//select[@name='txtMonId']");
	private By btnBuscar = By.xpath("//input[@name='btnBuscar']");
	
	private By CeldaSecciones = By.xpath("//*[@class='tableResults0']/td/a[text()[normalize-space()='SECCIONES']]");

	//SECCIONES
	private By AgregarSeccion = By.xpath("//input[@name='btnAgregar']");
	private By NewSectionName = By.xpath("//input[@name='txtSectionNameNew']");
	private By NewEncuestaName = By.xpath("//select[@name='txtMonIdNew']");
	private By NewEncuestaVersion = By.xpath("//select[@name='txtVersionNew']");
	private By NewEncuestaStatus = By.xpath("//select[@name='txtStatusNew']");
	private By btnSecSalvar = By.xpath("//input[@name='btnSalvar']");
	
	private By CeldaSubSecciones = By.xpath("//*[@class='tableResults0']/td/a[text()[normalize-space()='SUBSECCIONES']]");
	
	private By NewSectionIdName = By.xpath("//select[@name='txtSectionIdNew']");
	
	//PREGUNTAS
	private By CeldaPreguntas = By.xpath("//*[@class='tableResults0']/td/a[text()[normalize-space()='PREGUNTAS']]");
	
	private By Categoria = By.xpath("//select[@name='txtCategoriaNew']");
	private By OpCategoriaServicioCliente = By.xpath("//select[@name='txtCategoriaNew']/option[@value='SC']");
	
	private By CausaRaiz = By.xpath("//select[@name='txtCausaRaizNew']");
	private By OpCausaEncuesta = By.xpath("//select[@name='txtCausaRaizNew']/option[@value='ENCUESTA']");
	
	private By Equipo = By.xpath("//select[@name='txtEquipoIdNew']");
	private By OpEquipo = By.xpath("//select[@name='txtEquipoIdNew']/option[text()[normalize-space()='CAFE1']]");
	
	private By FlexField = By.xpath("//select[@name='txtFlexFieldNew']");
	private By OpFlexField = By.xpath("//select[@name='txtFlexFieldNew']/option[text()[normalize-space()='OTRO']]");
	
	private By Pregunta = By.xpath("//input[@name='txtQuestionTextNew']");
	
	private By Accion = By.xpath("//input[@name='txtAccionNew']");
	
	private By Concepto = By.xpath("//select[@name='txtTypeFallaNew']");
	private By OpConcepto = By.xpath("//select[@name='txtTypeFallaNew']/option[@value='Oficina Estacion']");
	
	
	//APARTADO REPORTES
	private By ReporteEncuestas = By.xpath("//*[text()[normalize-space()='Reporte de encuestas']]");
	
	private By ConsultaEncuestaProg = By.xpath("//*[text()[normalize-space()='Consulta de Encuestas Programadas']]");
	
	//CONSULTA DE ENCUESTAS PROGRAMADAS
	private By EncuestaName = By.xpath("//select[@name='txtEncuesta']");
	
	private By PlazaId = By.xpath("//select[@name='txtPlaza']");
	
	private By TiendaId = By.xpath("//select[@name='txtTienda']");
	
	private By UserId = By.xpath("//select[@name='txtUsuario']");
	
	private By periodo = By.xpath("//input[@name='txtPeriodo']");
	
	
	//CONSULTA DE ENCUESTAS PROGRAMADAS A PLAZAS
	private By SelectZonaRect = By.xpath("//select[@name='selectedZona']");
	//private By SelectTiendaRect = By.xpath("//select[@name='selectedTienda']");
	private By ChckBoxZonaNacional = By.xpath("//input[@name='nacional']");
	private By SelectPlazaRect = By.xpath("//select[@name='selectedPlaza']");
	private By InputFechaDel = By.xpath("//input[@name='txtFechaDel']");
	private By InputFechaAl = By.xpath("//input[@name='txtFechaAl']");
	
	private By ChckListVER = By.xpath("//tr[@class='tableResults0']/td/a[text()[normalize-space()='VER']]");
	
	
	//CHECKLIST VERIFICACION
	private By ExcelIconSelector = By.cssSelector("#princTab > tbody > tr:nth-child(1) > td > form > table:nth-child(9) > tbody > tr > td > a:nth-child(3)");
	
	
	//REPORTES DE PENDIENTES
	private By ReportesPendientes = By.xpath("//*[text()[normalize-space()='Reportes de Pendientes']]");
	
	private By ReporteUltimaRevision = By.xpath("//*[text()[normalize-space()='Ultima Revision']]");
	
	private By TiendasOptTodas = By.xpath("//select[@name='txtTienda']/option[text()[normalize-space()='Todas']]");
	
	private By BuscarBtn = By.xpath("//input[@value='Buscar']");
	
	private By ExcelIconRepPendientes = By.cssSelector("#princTab > tbody > tr > td > table > tbody > tr:nth-child(5) > td:nth-child(3) > a:nth-child(3) > img");
	
	private By IconRegresar = By.cssSelector("#princTab > tbody > tr > td > table > tbody > tr:nth-child(5) > td:nth-child(3) > img:nth-child(1)");
	
	/**
	 * Constructor
	 * @param u
	 * @param testCase
	 * @param aux
	 */
	public MantenimientoTiendas(SeleniumUtil u, TestCase testCase, AuxMethods aux) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
		this.aux = aux;
	}
	
	
	/**
	 * Metodo que coloca el string dado sobre el xpath para encontrar y seleccionar el nombre de Encuesta dado
	 * 
	 * @param idEncuesta
	 * @param VersionEncuesta
	 * @return idSecEncuesta
	 * @author Jose Onofre
	 */
	public By idEncuestaXpath(String idEncuesta, String VersionEncuesta) {
		
		String StringXpathEncuesta = "//select[@name='txtMonIdNew']/option[text()[normalize-space()='%s']]";
		
		String XpathEncuesta = String.format(StringXpathEncuesta, idEncuesta + " V." + VersionEncuesta);
		
		By idSecEncuesta = By.xpath(XpathEncuesta);
		
		return idSecEncuesta;
	}
	
	/**
	 * Meotodo que coloca el string dado sobre el xpath para encontrar la opcion con la version dada
	 * 
	 * @param version
	 * @return newVersion
	 * @author Jose Onofre
	 */
	public By VersionNew(String version) {
		
		String SXpathVersion = "//select[@name='txtVersionNew']/option[text()[normalize-space()='%s']]";
		
		String XpathVersion = String.format(SXpathVersion, version);
		
		By newVersion = By.xpath(XpathVersion);
		
		return newVersion;
	}
	

	/**
	 * Metodo que coloca el string dado sobre el xpath para encontrar la opcion con el estado dado
	 * 
	 * @param status
	 * @return newStatus
	 * @author Jose Onofre
	 */
	public By StatusNew(String status) {
		
		String SXpathStatus = "//select[@name='txtStatusNew']/option[text()[normalize-space()='%s']]";
		
		String XpathStatus = String.format(SXpathStatus, status);
		
		By newStatus = By.xpath(XpathStatus);
		
		return newStatus;
	}
	
	/**
	 * Metodo que coloca el string y version dado sobre el xpath para encontrar en el combobox el nombre de encuesta dado
	 * 
	 * @param idEncuesta
	 * @param VersionEncuesta
	 * @return idXEncuesta
	 * @author Jose Onofre
	 */
	public By BusquedaEncuestaXpath(String idEncuesta, String VersionEncuesta) {
		
		String StringXpathIdEncuesta = "//select[@name='txtMonId']/option[text()[normalize-space()='%s']]";
		
		String XpathIdEncuesta = String.format(StringXpathIdEncuesta, idEncuesta + " V." + VersionEncuesta);
		
		By idXEncuesta = By.xpath(XpathIdEncuesta);
		
		return idXEncuesta;
		
		
	}
	
	/**
	 * Metodo que coloca el String dado sobre el Xpath para ubicar en el combobox la opcion con el nombre de Section dado
	 * 
	 * @param idSectionName
	 * @return newSectionId
	 * @author Jose Onofre
	 */
	public By SectionIdNew(String idSectionName) {
		
		String SXpathSectionId = "//select[@name='txtSectionIdNew']/option[text()[normalize-space()='%s']]";
		
		String XpathSectionId = String.format(SXpathSectionId, idSectionName);
		
		By newSectionId = By.xpath(XpathSectionId);
		
		return newSectionId;
	}


	boolean ExpON = false;
	
	/**
	 * Metodo para comprobar que el apartado de 'Expansion' esta activo
	 * 
	 * @return ExpON
	 * @author Jose Onofre
	 */
	public boolean IsExpansionActive() {
		
		aux.scrollToElement(Expansion);
		u.hardWait(3);
		if("Expansi�n".equals(u.getText(Expansion))) {
			
			ExpON = true;
			u.highLight(Expansion);
			u.hardWait(2);
			testCase.addTextEvidenceCurrentStep("Se encontro el Modulo: Expansion "   );
			
			
			return ExpON;
		}
		else {
			
			testCase.addTextEvidenceCurrentStep("No se encontro el Modulo: Expansion "   );

		}
		
		
		return ExpON;
	
	}
	
	
	
	/**
	 * Metodo para hacer click en 'Checklist de Verificacion y Recepcion'
	 * 
	 * @author Jose Onofre
	 */
	
	public void ClickChklistVerifRecept() {
		
		
		
		u.hardWait(3);
		u.highLight(VerificacionYRecepcion);
		u.hardWait(5);
		aux.clickOpcion("VerificacionYRecepcion", VerificacionYRecepcion, true);
		u.hardWait(5);
		
		
	}

	/**
	 * Comprueba que el texto del Subtitulo sea igual al esperado
	 * 
	 * @return true si el texto del Subtitle coincide con el esperado 'Verificaci�n y Recepci�n de Tiendas'
	 * @author Jose Onofre
	 */
	public boolean IsChklstVerifYRecep() {
		
		boolean subtitle = false; 
		aux.scrollToElement(VerificacionYRecepcion);
		
		u.waitForLoadPage();
		
		if("Verificaci�n y Recepci�n de Tiendas".equals(u.getText(SubTitleVerificacionYRecepcion))) {
			
			subtitle = true;
			testCase.addTextEvidenceCurrentStep("Se encontro el Modulo: Verificaci�n y Recepci�n de Tiendas "   );
			aux.clickOpcion("Proveedores", SubTitleVerificacionYRecepcion, true);
			u.hardWait(2);
			
			
			return subtitle;
		}
		
		testCase.addTextEvidenceCurrentStep("No se encontro el Modulo: Verificaci�n y Recepci�n de Tiendas "   );
		return subtitle;
	}
	
	/**
	 * Metodo que hace resalta el boton de 'Funciones' y hace clic en el submenu 'Alta de Productos'
	 * 
	 * @author Jose Onofre
	 */
	public void SelectFunciones() {
		
		u.highLight(Funciones);
		u.mouseOver(Funciones);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Alta Funciones");
		u.hardWait(2);
		
		
	}
	
	/**
	 * Metodo que hace click en la opcion de 'Encuesta' del apartado 'Funciones'
	 * 
	 * @author Jose Onofre
	 */
	public void ClickFEncuesta() {
		
		aux.clickOpcion("Encuestas", Encuestas, true);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Muestra de Pantalla Encuestas ");
		
		
	}
	
	/**
	 * Metodo que hace click sobre el boton 'Agregar' en la seccion 'Encuestas'
	 * 
	 * @author Jose Onofre
	 */
	
	public void ClickAgregar() {
		
		u.highLight(btnAgregar);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("btnAgregar");
		u.click(btnAgregar);
		
	}
	
	/**
	 * Metodo que remarca y hace click al campo Tipo a su vez que realiza una captura del paso.
	 * 
	 * @author Jose Onofre
	 */
	public void SelectTipo() {
		
		u.highLight(selectTipo);
		u.click(selectTipo);
		//testCase.addScreenShotCurrentStep("SelectTipo");	
		
	}
	
	/**
	 * Metodo para llenar la informacion de los campos de una nueva encuesta
	 * 
	 * @author Jose Onofre
	 */
	
	public void FillEncuesta(String id, String name, String version) {
		
		u.highLight(selectNewTipo);
		u.click(selectNewTipo);
		u.hardWait(2);
		u.highLight(OptionNewGeneral);
		u.click(OptionNewGeneral);
		u.hardWait(2);
		u.highLight(inputNewName);
		u.sendKeys(inputNewName, name);
		u.hardWait(2);
		u.highLight(inputNewVersion);
		u.sendKeys(inputNewVersion, version);
		u.hardWait(2);
		u.highLight(selectStatusNew);
		u.click(selectStatusNew);
		u.hardWait(2);
		u.highLight(OptionANew);
		u.click(OptionANew);
		u.hardWait(2);
		u.highLight(btnSalvar);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "Encuesta Filled");
		u.hardWait(2);
		u.click(btnSalvar);
		u.waitForLoadPage();
	
	}
	
	
	/**
	 * Metodo para abrir la opcion 'Secciones'
	 * 
	 * @author Jose Onofre
	 */
	
	public void ClickSecciones() {
		
		u.click(CeldaSecciones);
		u.hardWait(3);
		
	}
	
	/**
	 * Metodo que remarca y hace click sobre el boton 'Agregar' del modulo 'Secciones'
	 * 
	 * @author Jose Onofre
	 */
	public void ClickAgregarSecccion() {
		
		u.highLight(AgregarSeccion);
		u.click(AgregarSeccion);
		testCase.addScreenShotCurrentStep("NewSection");
		u.waitForLoadPage();
		
	}
	
	
	/**
	 * Metodo que ayuda a realizar el llenado del form con la informacion necesaria para crear
	 * una nueva Seccion
	 * 
	 * @author Jose Onofre
	 */
	
	public void FillNewSectionForm(String idEncuesta,String SectionName, String Version, String status) {
		
		u.highLight(NewSectionName);
		u.sendKeys(NewSectionName, SectionName);
		u.hardWait(2);
		u.highLight(NewEncuestaName);
		u.click(NewEncuestaName);
		u.hardWait(3);
		u.highLight(idEncuestaXpath(idEncuesta, Version));
		u.click(idEncuestaXpath(idEncuesta, Version));
		u.hardWait(2);
		u.highLight(NewEncuestaVersion);
		u.click(NewEncuestaVersion);
		u.hardWait(2);
		u.highLight(VersionNew(Version));
		u.click(VersionNew(Version));
		u.hardWait(2);
		u.highLight(NewEncuestaStatus);
		u.click(NewEncuestaStatus);
		u.hardWait(2);
		u.highLight(StatusNew(status));
		u.click(StatusNew(status));
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "NewSection");
		u.hardWait(3);
		
	}
	
	/**
	 * Metodo que remarca y hace click sobre el boton 'Salvar'
	 * 
	 * @author Jose Onofre
	 */
	public void ClickSalvarSeccion() {
		
		u.highLight(btnSecSalvar);
		testCase.addScreenShotCurrentStep(u, "GuardarSect");
		u.click(btnSecSalvar);
		u.hardWait(3);
	}
	
	/**
	 * Metodo que ingresa los campos de nombre y version para buscar la encuesta en base a los campos dados
	 * 
	 * @param name
	 * @param version
	 * @author Jose Onofre
	 */
	public void BusquedaEncuestaByName(String name, String version) {
		
		u.highLight(inputIdEncuesta);
		u.click(inputIdEncuesta);
		u.hardWait(3);
		u.highLight(BusquedaEncuestaXpath(name,version));
		u.click(BusquedaEncuestaXpath(name,version));
		u.hardWait(3);
		u.highLight(btnBuscar);
		u.click(btnBuscar);
		u.hardWait(3);
	}
	
	/**
	 * Metodo que hace click en el apartado SubSecciones
	 * 
	 * @author Jose Onofre
	 */
	public void ClickSubsecciones() {
		
		u.highLight(CeldaSubSecciones);
		testCase.addScreenShotCurrentStep(u, "SubSecciones");
		u.click(CeldaSubSecciones);
		u.waitForLoadPage();
		u.hardWait(5);
		
	}
	
	/**
	 * Metodo que sirve para llenar los campos para el llenado de una nueva Sub seccion
	 * 
	 * @param idEncuesta
	 * @param SubSectionName
	 * @param Version
	 * @param Section
	 * @param status
	 * @author Jose Onofre
	 */
	public void FillNewSubSectionForm(String idEncuesta,String SubSectionName, String Version, String Section, String status) {
		
		u.highLight(NewSectionName);
		u.sendKeys(NewSectionName, SubSectionName);
		u.hardWait(2);
		u.highLight(NewEncuestaName);
		u.click(NewEncuestaName);
		u.hardWait(3);
		u.highLight(idEncuestaXpath(idEncuesta, Version));
		u.click(idEncuestaXpath(idEncuesta, Version));
		u.hardWait(2);
		
		u.highLight(NewSectionIdName);
		u.click(NewSectionIdName);
		u.hardWait(2);
		u.highLight(SectionIdNew(Section));
		u.click(SectionIdNew(Section));
		u.hardWait(2);
		
		u.highLight(NewEncuestaStatus);
		u.click(NewEncuestaStatus);
		u.hardWait(2);
		u.highLight(StatusNew(status));
		u.click(StatusNew(status));
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "NewSubSection");
		u.hardWait(3);
		
	}
	
	/**
	 * Metodo para hacer click en la celda 'PREGUNTAS' de la tabla
	 * 
	 * @author Jose Onofre
	 */
	public void ClickPreguntas() {
		
		u.highLight(CeldaPreguntas);
		testCase.addScreenShotCurrentStep(u, "Preguntas");
		u.click(CeldaPreguntas);
		u.hardWait(3);
	}

	/**
	 * Metodo que sirve para llenar los campos de una nueva pregunta de la subseccion
	 * 
	 * @author Jose Onofre
	 */
	public void ClickCategoria(String pregunta, String accion) {
		
		u.highLight(Categoria);
		u.click(Categoria);
		u.hardWait(3);
		u.highLight(OpCategoriaServicioCliente);
		u.click(OpCategoriaServicioCliente);
		u.hardWait(3);
		u.highLight(CausaRaiz);
		u.click(CausaRaiz);
		u.hardWait(3);
		u.highLight(OpCausaEncuesta);
		u.click(OpCausaEncuesta);
		u.hardWait(3);
		u.highLight(Equipo);
		u.click(Equipo);
		u.hardWait(3);
		u.highLight(OpEquipo);
		u.click(OpEquipo);
		u.hardWait(3);
		u.highLight(FlexField);
		u.click(FlexField);
		u.hardWait(3);
		u.highLight(OpFlexField);
		u.click(OpFlexField);
		u.hardWait(3);
		u.highLight(Pregunta);
		u.sendKeys(Pregunta, pregunta);
		u.hardWait(3);
		u.highLight(Accion);
		u.sendKeys(Accion, accion);
		u.hardWait(3);
		u.highLight(Concepto);
		u.click(Concepto);
		u.hardWait(3);
		u.highLight(OpConcepto);
		u.click(OpConcepto);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "FormPreguntaNueva");
		u.hardWait(3);
		
		
	}
	
	
	/**
	 * Click en filtro 'STATUS'
	 * 
	 * @author Jose Onofre
	 */
	public void clickStatus() {
		
		u.highLight(selectStatus);
		u.click(selectStatus);
		u.hardWait(3);
		
	}
	
	/**
	 * Click en la Opcion A del filtro 'STATUS'
	 * 
	 * @author Jose Onofre
	 */
	public void selectStatusA() {
		
		u.highLight(OptionA);
		u.click(OptionA);
		
	}
	
	
	/**
	 * Click en la Opcion I del filtro 'STATUS'
	 * 
	 * @author Jose Onofre
	 */
	public void selectStatusI() {
		
		u.highLight(OptionI);
		u.highLight(OptionI);
		
	}
	
	
	/**
	 * Click en el boton de Buscar
	 * 
	 * @author Jose Onofre
	 */
	
	public void ClickBuscar() {
		
		u.highLight(btnBuscar);
		u.click(btnBuscar);
		
	}
	
	/**
	 * Metodo que comprueba que la encuesta a buscar exista
	 * @param nombre_encuesta
	 * @return true or false
	 * @author Jose Onofre
	 */
	public boolean EncuestaisActive(String nombre_encuesta) {
		
		String nomEncuesta = "//*[text()[normalize-space()='%s']]";
		String fnomEncuesta = String.format(nomEncuesta, nombre_encuesta);
		
		By nombreEncuesta = By.xpath(fnomEncuesta);
		
		if(u.isDisplayed(nombreEncuesta)) {
			u.highLight(nombreEncuesta);
			u.hardWait(3);
			testCase.addScreenShotCurrentStep(u, "EncuestaActiva");
			testCase.addTextEvidenceCurrentStep("Se encontro la encuesta con el nombre: " + nombre_encuesta);
		return true;
		}
		else
			testCase.addScreenShotCurrentStep(u, "EncuestaNoActiva");
			testCase.addTextEvidenceCurrentStep("No se encontro la encuesta con el nombre: " + nombre_encuesta);
			return false;
		
	}
	
	
	/**
	 * Metodo que remarca la seccion 'Reportes' y posiciona el mouse sobre el.
	 * 
	 * @author Jose Onofre
	 */
	public void SelectReportes() {
		
		u.highLight(Reportes);
		u.mouseOver(Reportes);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Reportes");
		u.hardWait(2);
		
	}
	
	/**
	 * Metodo que hace click sobre el apartado 'Reporte de Encuestas' del Menu Orquestador
	 * 
	 * @author Jose Onofre
	 */
	public void ClickReportEncuesta() {
		
		aux.clickOpcion("ReporteEncuestas", ReporteEncuestas, true);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Muestra de Pantalla Reporte de Encuestas ");
		
		
	}
	
	/**
	 * Metodo que comprueba que el titulo de 'Consulta de Encuestas Programadas' se encuentre en la pagina cargada
	 * 
	 * @return subtitle
	 * @author Jose Onofre
	 */
	public boolean IsConsultaEncuestasProgramadas() {
		
		boolean subtitle = false; 
		
		u.waitForLoadPage();
		
		if("Consulta de Encuestas Programadas".equals(u.getText(ConsultaEncuestaProg))) {
			
			subtitle = true;
			testCase.addTextEvidenceCurrentStep("Se encontro el Modulo: Consulta de Encuestas Programadas "   );
			aux.clickOpcion("ConsultaEncuestasProg", ConsultaEncuestaProg, true);
			u.hardWait(2);
			
			
			return subtitle;
		}
		
		testCase.addTextEvidenceCurrentStep("No se encontro el Modulo: Consulta de Encuestas Programadas "   );
		return subtitle;
	}
	
	
	/**
	 * Metodo que sirve para escribir en el campo Encuesta la encuesta a buscar en el reporte de Encuestas
	 * @param idEncuesta
	 * @param VersionEncuesta
	 * @return idEncuestaName
	 * 
	 * @author Jose Onofre
	 */
	public By SetNameBuscarEncuestaProgramada(String idEncuesta, String VersionEncuesta) {
		
		String SXpathEncuestaName = "//select[@name='txtEncuesta']/option[text()[normalize-space()='%s']]";
		
		String XpathEncuestaName = String.format(SXpathEncuestaName, idEncuesta + " V." + VersionEncuesta);
		
		By idEncuestaName = By.xpath(XpathEncuestaName);
		
		return idEncuestaName;
	}
	
	/**
	 * Metodo que sirve para escribir en el campo Plaza la plaza a buscar en el reporte de Encuestas
	 * @param idPlaza
	 * @return idPlazaName
	 * 
	 * @author Jose Onofre
	 */
	public By SetNameBuscarPlaza(String idPlaza) {
		
		String SXpathPlazaName = "//select[@name='txtPlaza']/option[text()[normalize-space()='%s']]";
		
		String XpathPlazaName = String.format(SXpathPlazaName, idPlaza);
		
		By idPlazaName = By.xpath(XpathPlazaName);
		
		return idPlazaName;
	}
	
	
	/**
	 * Metodo que sirve para escribir en el campo Tienda la tienda a buscar en el reporte de Encuestas 
	 * 
	 * @param idTienda
	 * @return idTiendaName
	 * @author Jose Onofre
	 */
	public By SetNameBuscarTienda(String idTienda) {
		
		String SXpathTiendaName = "//select[@name='txtTienda']/option[text()[normalize-space()='%s']]";
		
		String XpathTiendaName = String.format(SXpathTiendaName, idTienda);
		
		By idTiendaName = By.xpath(XpathTiendaName);
		
		return idTiendaName;
	}
	
	/**
	 * Metodo que sirve para escribir en el campo Usuario el usuario a buscar en el reporte de Encuestas
	 * 
	 * @param usuario
	 * @return idUser
	 * @author Jose Onofre
	 */
	public By SetUserBuscar(String usuario) {
		
		String SXpathUser = "//select[@name='txtUsuario']/option[text()[normalize-space()='%s']]";
		
		String XpathUser = String.format(SXpathUser, usuario);
		
		By idUser = By.xpath(XpathUser);
		
		return idUser;
	}
	
	
	
	/**
	 * Metodo que sirve para llenar los campos de los filtros para buscar una encuesta programada especifica
	 * 
	 * @param name_encuesta
	 * @param VersionEncuesta
	 * @param Plaza
	 * @author Jose Onofre
	 */
	public void FillBusquedaEncuestaProgramada(String name_encuesta, String VersionEncuesta, String Plaza, String Tienda, String Usuario, String fecha) {
		
		By XpathNameToSearch = SetNameBuscarEncuestaProgramada(name_encuesta,VersionEncuesta);
		By XpathPlazaToSearch = SetNameBuscarPlaza(Plaza);
		By XpathTiendaToSearch = SetNameBuscarTienda(Tienda);
		By XpathUsuarioToSearch = SetUserBuscar(Usuario);
		
		u.highLight(EncuestaName);
		u.click(EncuestaName);
		u.hardWait(3);
		u.highLight(XpathNameToSearch);
		u.click(XpathNameToSearch);
		u.hardWait(3);
		u.highLight(PlazaId);
		u.click(PlazaId);
		u.hardWait(3);
		u.highLight(XpathPlazaToSearch);
		u.click(XpathPlazaToSearch);
		u.hardWait(3);
		u.highLight(TiendaId);
		u.click(TiendaId);
		u.hardWait(3);
		u.highLight(XpathTiendaToSearch);
		u.click(XpathTiendaToSearch);
		u.hardWait(3);
		u.highLight(UserId);
		u.click(UserId);
		u.hardWait(3);
		u.highLight(XpathUsuarioToSearch);
		u.click(XpathUsuarioToSearch);
		u.hardWait(3);
		u.highLight(periodo);
		u.sendKeys(periodo, fecha);
		u.hardWait(3);
		
	}
	
	
	/**
	 * Metodo que hace click en la opcion de 'Reporte Encuesta A Plazas' del apartado 'Reportes'
	 * 
	 * @author Jose Onofre
	 */
	public void ClickREncuestaPlaza() {
		
		aux.clickOpcion("Encuesta A Plazas", RepEncuestaPlazas, true);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Muestra de Pantalla Reporte Encuesta A Plazas ");
		
		
	}
	
	
	/*
	 * METODOS PARA EL MODULO DE CONSULTA DE ENCUESTAS PROGRAMADAS A PLAZAS
	 */
	
	/**
	 * Metodo que sirve para seleccionar la Zona por nombre en el recuadro de la
	 * Consulta de Encuestas Programadas A Plazas
	 * @param zona
	 * @return idZonaName
	 * @author Jose Onofre
	 */
	public By SelectZonaByName(String zona) {
	
	String StrSelectZona = "//select/option[text()[normalize-space()='%s']]";
	String XpathZonaName = String.format(StrSelectZona, zona);
	
	By idZonaName = By.xpath(XpathZonaName);
	
	return idZonaName;
	
	}
	
	/**
	 *Metodo que sirve para seleccionar la Plaza por nombre en el recuadro de la
	 *Consulta de Encuestas Programadas A Plazas
	 * @param plaza
	 * @return idPlazaName
	 * @author Jose Onofre
	 */
	
	public By SelectPlazaByName(String plaza) {
		
		String StrSelectPlaza = "//select/option[text()[normalize-space()='%s']]";
		String XpathPlazaName = String.format(StrSelectPlaza, plaza);
		
		By idPlazaName = By.xpath(XpathPlazaName);
		
		return idPlazaName;
		
		}
	
	
	/**
	 * Meotodo que llena los campos de Filtrado para la bisqueda de una Encuesta Programada de Plazas
	 * 
	 * @param zona
	 * @param plaza
	 * @param fecha_inicial
	 * @param fecha_final
	 * @author Jose Onofre
	 */
	public void fillConsultaEncuestaProgramPlazas (String zona, String plaza, String fecha_inicial, String fecha_final) {
		
		By XpathZonaRect = SelectZonaByName(zona);
		By XpathPlazaRect = SelectPlazaByName(plaza);
		
		u.highLight(SelectZonaRect);
		u.hardWait(3);
		u.click(XpathZonaRect);
		u.hardWait(3);
		u.highLight(SelectPlazaRect);
		u.click(XpathPlazaRect);
		u.hardWait(3);
		u.highLight(InputFechaDel);
		u.sendKeys(InputFechaDel, fecha_inicial);
		u.hardWait(3);
		u.highLight(InputFechaAl);
		u.sendKeys(InputFechaAl, fecha_final);
		u.hardWait(3);
		
	}
	
	/**
	 * Metodo para hacer click sobre el boton 'VER' del apartado CHECKLIST
	 * 
	 * @author Jose Onofre
	 */
	public void ClickCheckListVER() {
		
		u.highLight(ChckListVER);
		testCase.addScreenShotCurrentStep(u, "VER");
		u.hardWait(3);
		u.click(ChckListVER);
		u.hardWait(3);
		
	}
	
	/**
	 * Metodo que sirve para hacer click sobre el icono 'Excel' y descargar el xls
	 * del reporte Consulta de Encuestas Programadas a Plazas
	 * 
	 * @author Jose Onofre
	 */
	public void ClickExcelIcon() {
		
		u.highLight(ExcelIconSelector);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "ExcelIcon");
		u.hardWait(3);
		u.click(ExcelIconSelector);
		u.hardWait(25);
	}
	
	
	
	/**
	 * Metodo que remarca la seccion 'Reportes de Pendientes' y posiciona el mouse sobre el.
	 * 
	 * @author Jose Onofre
	 */
	public void SelectReportesPendientes() {
		
		u.highLight(ReportesPendientes);
		u.mouseOver(ReportesPendientes);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("ReportesPendientes");
		u.hardWait(2);
		
	}
	
	
	/**
	 * Metodo que hace click en la opcion de 'Ultima Revision' del apartado 'Reportes de Pendientes'
	 * 
	 * @author Jose Onofre
	 */
	public void ClickUltimaRevision() {
		
		aux.clickOpcion("Ultima Revision", ReporteUltimaRevision, true);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Muestra de Pantalla Ultima Revision ");
		
		
	}
	
	
	/**
	 * Metodo que llena los campos de Busqueda para el modulo 'Reporte de Pendientes de Ultima Revision'
	 * 
	 * @param name
	 * @param version
	 * @param plaza
	 * @param Tienda
	 * @author Jose Onofre
	 */
	public void BusquedaPendienteUltimaRevision(String name, String version, String plaza) {
		
		By XpathPlazaToSearch = SetNameBuscarPlaza(plaza);
		
		u.highLight(inputIdEncuesta);
		u.click(inputIdEncuesta);
		u.hardWait(3);
		u.highLight(BusquedaEncuestaXpath(name,version));
		u.click(BusquedaEncuestaXpath(name,version));
		u.unhighLight(inputIdEncuesta);
		u.hardWait(3);
		u.highLight(PlazaId);
		u.click(XpathPlazaToSearch);
		u.hardWait(3);
		u.highLight(TiendaId);
		u.click(TiendasOptTodas);
		u.hardWait(3);
		u.highLight(BuscarBtn);
		u.click(BuscarBtn);
		u.hardWait(3);
	}
	
	
	
	/**
	 * Metodo que hace click en el Icono Excel del modulo Reporte de Pendientes de Ultima Revision
	 * 
	 * @author Jose Onofre
	 */
	public void ClickExcelIconPendientes() {
		
		u.highLight(ExcelIconRepPendientes);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "ExcelIcon");
		u.hardWait(3);
		u.click(ExcelIconRepPendientes);
		u.hardWait(25);
	}
	
	
	/**
	 * Metodo que hace click en la imagen para regresar a la pantalla de 'Reporte de Pendientes de Ultima Revision'
	 * 
	 * @author Jose Onofre
	 */
	public void ClickIconRegresar() {
		
		u.highLight(IconRegresar);
		u.hardWait(3);
		u.click(IconRegresar);
		
	}
	
	
}
