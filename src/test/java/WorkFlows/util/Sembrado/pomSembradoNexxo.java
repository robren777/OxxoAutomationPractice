package WorkFlows.util.Sembrado;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;

import WorkFlows.util.PlanogramasNexxo.sembradoUtils;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class pomSembradoNexxo {
	SeleniumUtil u;
	private WebDriverWait wait;
	TestCase testCase;
	
	
	//Your connection isn't private//
	
	private By btnAdvance = By.id("details-button");
	private By linkContinue = By.id("proceed-link");

	
	//LOGIN
	
	private By locationUserInput = By.id("login:userName");
	private By locationPassInput = By.id("login:userPass");
	private By locationLoginButton = By.id("login:loginButton");
	
	//INICIO PORTAL APLICACIONES
	private By locationMenuInicio = By.id("menu_cnt");
	By btnSembradoPlaza = By.xpath("//a[text()='Sembrado Plaza']");
	//private By btnSembradoPlaza = By.cssSelector("#homeSubview\\:j_idt61 > div > div.envelope.mtop10 > div:nth-child(3) > div:nth-child(1) > div:nth-child(2) > div > ul > li:nth-child(9) > a");

	
	//SEMBRADO PLAZA 
	private By btnConfigPlaza = By.id("e0_0o");
	private By btnLegos = By.id("e0_3i");
	By opSembradoPlaza = By.id("e0_64i");
	By opSembrado = By.id("e0_66i"); 
	By opSembradoMasivo = By.id("e0_75i");
	
	//CREACION LEGOS OFICIALES
	private By rdPlaza = By.id("radTipoConjunto2");
	private By selectPlaza = By.id("idCmbConjunto");
	private By selectSegLego = By.id("idCmbSegmento");
	private By selectPlanogramable = By.id("idCmbPlanogramable");
	private By selectPlanoGrupo = By.id("idCmbPlanogrupo");
	private By tbxFit = By.name("txtFit");
	private By selectMueble = By.name("idCmbMueble");
	private By selectTamano = By.name("idCmbTamano");
	private By selectDirLego = By.id("idCmbDirLego");
	private By selectVarDinamica = By.name("idCmbVarDinamica");
	private By btnAgregar = By.name("btnAgregarLego");
			By btnExportaLegos = By.name("ExportarLegos");

	//CARGA DE ARCHIVOS
	private By btnUsuarioEspecial = By.cssSelector("#e0_43i");
	private By btnCargaArchivos = By.cssSelector("#e0_49i");
	
	private By selectTipoArchivo = By.id("idFileType");
	private By inputPathExcel = By.name("excelFile");
	private By btnSubeArchivo = By.id("btnUpload");
	private By mensaje = By.id("divMsjSem");
	private By aceptaMensaje = By.cssSelector("body > div.ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-front.ui-dialog-buttons.ui-draggable > div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button > span.ui-button-text");

	//---DEPURAR ARCHIVO
	private By btnDepurar = By.id("btnDepurar");
	private By selectDepArch = By.id("idCmbSeccion");
	private	By btnDepuraArch = By.id("btnDepurarSeleccion");
			
	//SEMBRADO DE LEGOS
	By statusTiedda = By.id("estatusAB");
	private	By selectPlazaSem = By.id("cmbPlazaId"); 
	private	By selectTienda = By.id("idCmbTienda");

	//SEMBRADO MASIVO LEGOS
	By selectPlazaSemMasiv= By.id("idCmbPlaza");
	By tbxTienda = By.id("txtBuscarTiendasMasivo");
	By selectOpTienda = By.id("idCmbTienda");
	By btnAddOne = By.id("btnAddOne");
	By rdBtnAgregar=By.id("radd");
	By rdBtnContracaja= By.id("rdbSeccion4");
	By btnIniciar = By.id("btnIniciar");
	By btnAgregarFila = By.name("Agregar");
	
	//SEMBRADO CARGANDO
	By modalCargando = By.className("blockUI");
	
	
	
	//SEMBRADO PLAZA Bloque 2
	By selectSembradoPlaza = By.cssSelector("#homeSubview\\:j_idt61 > div > div.envelope.mtop10 > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div > ul > li:nth-child(1) > a");//By.id("homeSubview:j_idt61");
	By administrador = By.id("e0_0i");
	By opConjuntos = By.id("e0_2i");
	By opLegos = By.id("e0_4i");
	sembradoUtils sembradoUtils = new sembradoUtils();
	By nomConjunto = By.id("txtNombreConjunto");
	By abrevConjunto = By.id("txtAbreviaConjunto");
	By btnCrearConjunto = By.cssSelector("#TABLE_9 > tbody > tr:nth-child(5) > td > input");
	By validarConj = By.cssSelector("#TABLE_12 > thead > tr > th.table-filterable.table-sortable\\:default.table-sortable > select");
	
	By opCombinabilidad = By.id("e0_9i");
	By planoGrupo1= By.id("idCmbPlanogrupo1");
	By planoGrupo2 =By.id("idCmbPlanogrupo2");
	By selplanoGrupo2 = By.cssSelector("#idCmbPlanogrupo2 > option:nth-child(92)");//cacahuates
	By btnAgregarComb = By.cssSelector("#TABLE_9 > tbody > tr:nth-child(5) > td > input");
	By validarComb = By.cssSelector("#TABLE_13 > tbody > tr:nth-child(5) > td:nth-child(1)");
	
	By opCargaArchivos = By.id("e0_15i");
	By archivoAcargar = By.id("idFileType");
	By campoPathArchivo = By.id("archivoExcel");
	By btnSubirArchivo= By.id("btnUpload");
	
	
	public pomSembradoNexxo(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}
	
	public pomSembradoNexxo(TestCase testCase) {
		this.testCase = testCase;
	}
	
	/**
	 * Selecciona opcion sembrado plaza del menu comercial
	 *///Tania Dena
	public void sembradoPlaza() {
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar en Comercial << Sembrado Plaza >>");
		u.hardWait(2);
		u.highLight(selectSembradoPlaza);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(selectSembradoPlaza);
	}
	/**
	 * Ingresa al apartado de Legos en 
	 * sembrado plaza>Administrador>legos
	 *///Tania Dena
	public void ingresaLegos() {
		try {
			u.mouseOver(administrador);
			u.highLight(opLegos);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(4);
			u.unhighLight(opLegos);
			
			u.mouseOverAndClick(opLegos);
			cargandoMsg();
			u.hardWait(4);
			testCase.addScreenShotCurrentStep("");
			
		}catch(Exception e) {
			throw new AssertionError("Error al ingresar a Legos ");
		}			
	}
	
	/**
	 * Ingresa al apartado de Conjuntos en 
	 * sembrado plaza>Administrador>conjuntos
	 *///Tania Dena
	public void ingresaConjuntos() {
		try {
			u.mouseOver(administrador);
			u.highLight(opConjuntos);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(4);
			u.unhighLight(opConjuntos);
			
			u.mouseOverAndClick(opConjuntos);
			cargandoMsg();
			u.hardWait(4);
			testCase.addScreenShotCurrentStep("");
			
		}catch(Exception e) {
			throw new AssertionError("Error al ingresar a Conjuntos ");
		}			
	}
	
	/**
	 * Ingresa al apartado de Combinabilidad en 
	 * sembrado plaza>Administrador>conjuntos
	 *///Tania Dena
	public void ingresaCombinabilidad() {
		try {
			u.mouseOver(administrador);
			u.highLight(opCombinabilidad);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(4);
			u.unhighLight(opCombinabilidad);
			
			u.mouseOverAndClick(opCombinabilidad);
			cargandoMsg();
			u.hardWait(4);
			testCase.addScreenShotCurrentStep("");
			
		}catch(Exception e) {
			throw new AssertionError("Error al ingresar a Combinabilidad ");
		}			
	}
	
	/**
	 * Ingresa al apartado de Carga de Archivos en 
	 * sembrado plaza>Administrador>carga de archivos
	 *///Tania Dena
	public void ingresaCargaArchivos() {
		try {
			u.mouseOver(administrador);
			u.highLight(opCargaArchivos);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(4);
			u.unhighLight(opCargaArchivos);
			
			u.mouseOverAndClick(opCargaArchivos);
			//cargandoMsg();
			u.hardWait(4);
			testCase.addScreenShotCurrentStep("");
			
		}catch(Exception e) {
			throw new AssertionError("Error al ingresar a Carga de archivos ");
		}			
	}
	
	/**
	 * Metodo para validar columna para sembrado plaza
	 * el nombre de la columna, es el nombre que le ponga a la imagen de sikuli
	 * correspondiente a la columna a validar, para usar este metodo se puede 
	 * utilizar el metodo constructor de pomSembradoNexxo(TestCase testCase)
	 * @param string 
	 * @throws FindFailed 
	 *///Tania Dena
	public void validaArchivo(String nameArchivo,String [] columnaV) throws FindFailed {
		sembradoUtils.validaColArchivo(nameArchivo, columnaV);
		testCase.addScreenShotCurrentStep("archivo");
	}
	
	/**
	 * metodo para cargar archivo en 
	 * sembrado plaza>administrador>carga de archivos
	 * @throws FindFailed 
	 *///Tania Dena
	public void cargarArchivo(String opArchivo,String nameArchivo) throws FindFailed {
		u.hardWait(6);

		u.highLight(archivoAcargar);
		u.click(archivoAcargar);
		u.hardWait(3);
		selectOption(opArchivo,archivoAcargar);
		u.hardWait(3);
		new AssertionError("No se encontro la seleccion de archivo especificada");
		u.hardWait(2);
		
		sembradoUtils.cargarArchivoVal(opArchivo, nameArchivo);
		u.highLight(campoPathArchivo); 
		u.highLight(btnSubirArchivo);
		u.click(btnSubirArchivo);
		u.hardWait(6);
		testCase.addScreenShotCurrentStep("archivoSubido");
		
		sembradoUtils.msgArchivoCargado();
		new AssertionError("No se subio correctamente el archivo");
	}
	
	/**
	 * Metodo para crear conjuntos en Sembrado plaza
	 * administrador> conjuntos
	 *///Tania Dena
	public void crearConjunto(String nombreConj, String abreviacionConj) throws FindFailed {
		sembradoUtils.load();
		
		u.hardWait(5);
		u.click(nomConjunto);
		u.highLight(nomConjunto);
		u.sendKeys(nomConjunto, nombreConj);
		u.hardWait(3);
		
		u.click(abrevConjunto);
		u.highLight(abrevConjunto);
		u.sendKeys(abrevConjunto, abreviacionConj);
		u.hardWait(3);
		
		u.highLight(btnCrearConjunto);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("newConjunto");
		u.click(btnCrearConjunto);
		
		//validar se creo conjunto y esta en la tabla
		sembradoUtils.load();
		//u.hardWait(10);
		testCase.addBoldTextEvidenceCurrentStep("Se valida que el conjunto se creo correctamente");
		
		u.highLight(validarConj);
		sembradoUtils.load();
		//u.click(validarConj);
		selectOption(nombreConj,validarConj);
		new AssertionError("No se encontro el conjunto, no fue creado correctamente");
		testCase.addScreenShotCurrentStep("validarConjunto");
		u.hardWait(2);
	}

	/**
	 * Metodo para crear combinaciones en Sembrado plaza
	 * administrador> combinabilidad
	 *///Tania Dena
	public void crearCombinacion(String plano1, String plano2) throws FindFailed {
		sembradoUtils.load();
		
		u.hardWait(5);
		u.click(planoGrupo1);
		u.sendKeys(planoGrupo1, plano1);
		u.highLight(planoGrupo1);
		
		sembradoUtils.load();
		
		u.hardWait(5);
		u.click(planoGrupo2);
		u.hardWait(3);
		cargandoMsg();
		u.click(selplanoGrupo2);
		u.highLight(planoGrupo2);
		u.hardWait(3);
		
		u.highLight(btnAgregarComb);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("newCombinacion");
		u.unhighLight(btnAgregarComb);
		u.click(btnAgregarComb);
		
		//validar se creo combinacion y esta en la tabla
	    sembradoUtils.load();
		
		u.hardWait(5);	
		testCase.addBoldTextEvidenceCurrentStep("Se valida que la combinacion se creo correctamente");
		sembradoUtils.msgCombinabilidad();
		u.highLight(validarComb);
		new AssertionError("No se encontro la combinacion, no fue creada correctamente");
		testCase.addScreenShotCurrentStep("validarCombinacion");
		u.hardWait(2);
	}
	
	
	
	/**
	 * Inicia sesion en sembrado mediante el portal de aplicaciones
	 * y cicla el login hasta que sea exitoso, si al tercer intento
	 * no se logra entrar, la pagina se refresca
	 * @param user
	 * @param ps
	 */
	public void login (String user, String ps) {
		
		try {
			Boolean bandera = false;
			Integer cont = 0;
			u.getDriver().manage().window().maximize();

			
			if(u.getDriver().findElements(btnAdvance).size() != 0) {
				u.click(btnAdvance);
				u.waitWebElement(linkContinue, 20, 2);
				u.click(linkContinue);
			}
			
			
			u.waitWebElement(locationUserInput, 15, 2);
			testCase.addScreenShotCurrentStep("");

			do {
				cont += 1;
				if (cont < 4) {
					wait.until(ExpectedConditions.elementToBeClickable(locationLoginButton));
					u.highLight(locationUserInput);
					clearInput(locationUserInput);
					u.sendKeys(locationUserInput, user);
					u.highLight(locationPassInput);
					clearInput(locationPassInput);
					u.sendKeys(locationPassInput, ps);
					u.highLight(locationLoginButton);
					u.click(locationLoginButton);
					try {
						wait.until(ExpectedConditions.elementToBeClickable(locationMenuInicio));
						bandera = true;
					} catch (Exception e) {
						bandera = false;
					}
				} else {
					u.getDriver().navigate().refresh();
					cont = 0;
				}
			} while (!bandera);
			
			testCase.addBoldTextEvidenceCurrentStep("Seleccionar en Comercial << Sembrado Plaza >>");
			u.highLight(btnSembradoPlaza);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.click(btnSembradoPlaza);

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo iniciar sesion");
		}
		
	}
	
	/**
	 * Ingresa a la pantalla <<Creacion de Legos Oficiales>> y crea un nuevo 
	 * Lego con los siguientes parametros
	 * @param plaza
	 * @param segLego
	 * @param plano
	 * @param planoGrupo
	 * @param mueble
	 * @param tamano
	 * @param dirLego
	 * @param varDina
	 * @param fit_Np
	 */
	
	public void addLegos(String plaza, String segLego, String plano, 
			String planoGrupo, String mueble,String tamano, String dirLego, String varDina, String fit_Np) {
		
		try {
			cargandoMsg();
			testCase.addTextEvidenceCurrentStep("Ingresa a Administrador y seleccionar <<Legos>>");
			cargandoMsg();
			u.waitWebElement(btnConfigPlaza, 120, 3);
			u.mouseOver(btnConfigPlaza);
			u.hardWait(2);
			u.highLight(btnLegos);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.mouseOverAndClick(btnLegos);
			cargandoMsg();
			
			
			testCase.addTextEvidenceCurrentStep("Seleccionar CheckBox 'Plaza'");
			cargandoMsg();
			u.waitWebElement(rdPlaza, 120, 4);
			u.highLight(rdPlaza);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.click(rdPlaza);
			cargandoMsg();
			
			u.hardWait(3);
			testCase.addTextEvidenceCurrentStep("Seleccionar una opcion de 'Plaza'");
			cargandoMsg();
			u.highLight(selectPlaza);
			selectOption(plaza,selectPlaza);
			cargandoMsg();
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			u.unhighLight(selectPlaza);
			
			testCase.addTextEvidenceCurrentStep("Seleccionar una opcion de 'Segmento de lego'");
			cargandoMsg();
			u.highLight(selectSegLego);
			selectOption(segLego,selectSegLego);
			cargandoMsg();
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			u.unhighLight(selectSegLego);
			
			testCase.addTextEvidenceCurrentStep("Seleccionar una opcion de 'Planogramable'");
			cargandoMsg();
			u.highLight(selectPlanogramable);
			selectOption(plano,selectPlanogramable);
			cargandoMsg();
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			u.unhighLight(selectPlanogramable);
			
			
			testCase.addTextEvidenceCurrentStep("Seleccionar una opcion de 'Planogrupo'");
			cargandoMsg();
			u.highLight(selectPlanoGrupo);
			selectOption(planoGrupo,selectPlanoGrupo);
			cargandoMsg();
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			u.unhighLight(selectPlanoGrupo);
			
			if(u.getDriver().findElement(selectMueble).isDisplayed()) {
				
				testCase.addTextEvidenceCurrentStep("Seleccionar una opcion de 'Mueble'");
				cargandoMsg();
				u.highLight(selectMueble);
				selectOption(mueble,selectMueble);
				cargandoMsg();
				u.hardWait(2);
				testCase.addScreenShotCurrentStep("");
				u.hardWait(3);
				u.unhighLight(selectMueble);
				
				testCase.addTextEvidenceCurrentStep("Seleccionar una opcion de 'Tama�o'");
				cargandoMsg();
				u.highLight(selectTamano);
				selectOption(tamano,selectTamano);
				cargandoMsg();
				u.hardWait(2);
				testCase.addScreenShotCurrentStep("");
				u.hardWait(3);
				u.unhighLight(selectTamano);
			}else {
				testCase.addTextEvidenceCurrentStep("Ingresa un valor en el campo 'Fit (NP)'");
				cargandoMsg();
				u.highLight(tbxFit);
				u.sendKeys(tbxFit, fit_Np);
				cargandoMsg();
				u.hardWait(2);
				testCase.addScreenShotCurrentStep("");
				u.hardWait(3);
				u.unhighLight(tbxFit);
			}
		
			
			testCase.addTextEvidenceCurrentStep("Seleccionar una opcion de 'Direccion Lego'");
			cargandoMsg();
			u.highLight(selectDirLego);
			selectOption(dirLego,selectDirLego);
			cargandoMsg();
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			u.unhighLight(selectDirLego);
			
			testCase.addTextEvidenceCurrentStep("Seleccionar una opcion de 'Variedad Dinamica'");
			cargandoMsg();
			u.highLight(selectVarDinamica);
			selectOption(varDina,selectVarDinamica);
			cargandoMsg();
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			u.unhighLight(selectVarDinamica);
			
			testCase.addTextEvidenceCurrentStep("Dar click en el boton 'Agregar'");
			cargandoMsg();
			u.highLight(btnAgregar);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(1);
			u.click(btnAgregar);
			cargandoMsg();
			u.unhighLight(btnAgregar);

			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al crear Legos");
		}								
	}
	
	/**
	 * Selecciona una opcion del elemento select
	 * @param op
	 * @param selectElement
	 */

	public void selectOption(String op, By selectElement) {
		
		Select dropdown=  new Select (u.getDriver().findElement((selectElement)));
		dropdown.selectByVisibleText(op);
	}
	
	
	
	/**
	 * Realiza la carga de archivos en sembrado
	 * @param path
	 * @param opArchivo
	 * @param nomArchivo
	 */
	public void cargaArchivos(String path, String opArchivo, String nomArchivo) {
		
		try {

			testCase.addTextEvidenceCurrentStep("Ingresa a Administrador y seleccionar <<Carga Archivos>>");
			cargandoMsg();
			u.waitWebElement(btnUsuarioEspecial, 120, 3);
			u.mouseOver(btnUsuarioEspecial);
			u.hardWait(2);
			u.highLight(btnCargaArchivos);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.mouseOverAndClick(btnCargaArchivos);
			cargandoMsg();
			
			
			testCase.addTextEvidenceCurrentStep("Seleccionar Tipo de archivo en \"Archivo a cargar\" > Dar click en "+opArchivo);
			cargandoMsg();
			u.waitWebElement(selectTipoArchivo, 120, 3);
			selectOption(opArchivo,selectTipoArchivo);
			cargandoMsg();
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);

			testCase.addTextEvidenceCurrentStep("Dar click en \"Seleccionar archivo\" y adjuntar archivo de "+opArchivo);
			cargandoMsg();
			u.sendKeys(inputPathExcel, path);
			u.hardWait(2);
			u.highLight(btnSubeArchivo);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.click(btnSubeArchivo);
			cargandoMsg();
			
			u.waitWebElement(aceptaMensaje, 20, 2);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(2);
			u.click(aceptaMensaje);
			cargandoMsg();
			
		}catch(Exception e) {
			
			System.out.println(" -> " + e);
			throw new AssertionError("Error al cargae archivos");
			
		}
		
	}
	
	/**
	 * Elimina un archivo previamente cargado
	 * @param nomArch
	 */
	public void depurar(String nomArch) {
		u.highLight(btnDepurar);
		u.click(btnDepurar);
		
		u.waitWebElement(selectDepArch, 10, 2);
		selectOption(nomArch,selectDepArch);
		cargandoMsg();
		
		u.click(btnDepuraArch);
		cargandoMsg();
		
		u.waitWebElement(aceptaMensaje, 20, 2);
		u.hardWait(2);
		u.click(aceptaMensaje);
		cargandoMsg();

	}
	
	/**
	 * Ingresa al apartado de sembrado dienta
	 */
public void ingresaSembradoTienda () {
		
		try {
			
			u.scrollToElement(opSembradoPlaza);
			u.mouseOver(opSembradoPlaza);
			u.highLight(opSembrado);
			
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			u.unhighLight(opSembrado);
			
			u.mouseOverAndClick(opSembrado);
			cargandoMsg();
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("");
			

			
		}catch(Exception e) {
			System.out.println("Error -> " + e);
			throw new AssertionError("Error al ingresar a Sembrado tienda ");
		}			
	}

	/**
	 * Verifica si el estatus de una tienda fue modificado
	 * @param plaza
	 * @param tienda
	 * @param seccion
	 * @param fase
	 */
	public void statusTienda(String plaza, String tienda, String seccion, String fase) {
		u.waitWebElement(selectPlazaSem, 120, 3);
		
		testCase.addBoldTextEvidenceCurrentStep("Plaza");
		u.highLight(selectPlazaSem);
		selectOption(plaza,selectPlazaSem);
		cargandoMsg();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.unhighLight(selectPlazaSem);
		
		testCase.addBoldTextEvidenceCurrentStep("Tienda");
		u.highLight(selectTienda);
		selectOption(tienda,selectTienda);
		cargandoMsg();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.unhighLight(selectTienda);
		u.hardWait(3);
		
		String status = u.getDriver().findElement(statusTiedda).getText();
		u.highLight(statusTiedda);
		testCase.addScreenShotCurrentStep("");

		if(!status.equals("ENVIADA")) {
			throw new AssertionError("El status de la tienda no es Enviado o cambio al "
					+ "agregar filas en Sembrado Masivo");

		}

	}
	
	/**
	 * Ingresa a la pantalla de sembrado masivo, previamente
	 * se manda una tienda con status terminado, se agrega una fila sin 
	 * modificar o guardar
	 * @param plaza
	 * @param tienda
	 */
	public void sembradoMasivo(String plaza, String tienda) {
		u.mouseOver(opSembradoPlaza);
		u.highLight(opSembradoMasivo);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		
		u.mouseOverAndClick(opSembradoMasivo);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("");
		
		u.waitWebElement(selectPlazaSemMasiv, 120, 3);
		
		testCase.addBoldTextEvidenceCurrentStep("Plaza");
		u.highLight(selectPlazaSemMasiv);
		selectOption(plaza,selectPlazaSemMasiv);
		cargandoMsg();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(3);
		u.unhighLight(selectPlazaSemMasiv);
		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar < \"Tienda\">");
		u.highLight(tbxTienda);
		u.sendKeys(tbxTienda, tienda);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		u.unhighLight(tbxTienda);
		
		selectOption(tienda,selectOpTienda);

		u.mouseOverAndClick(btnAddOne);
		cargandoMsg();
		testCase.addScreenShotCurrentStep("");
		u.hardWait(1);
		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar checkbox \"Agregar\"");
		u.click(rdBtnAgregar);
		testCase.addScreenShotCurrentStep("");
		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar  <\"Contracaja\">");
		u.click(rdBtnContracaja);
		testCase.addScreenShotCurrentStep("");

		testCase.addBoldTextEvidenceCurrentStep("Dar click en [Iniciar]");
		
		u.highLight(btnIniciar);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("");
		u.click(btnIniciar);
		cargandoMsg();
		u.unhighLight(btnIniciar);
		
		u.waitWebElement(btnAgregarFila, 15, 2);
		u.scrollToElement(btnAgregarFila);
		
		testCase.addBoldTextEvidenceCurrentStep("Seleccionar el boton [Agregar Fila] \"sin guardar o cancelar\"");
		u.highLight(btnAgregarFila);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("");
		u.click(btnAgregarFila);
		cargandoMsg();
		
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("");
		

	}
	
	/**
	 * click en el boton exportar legos
	 * este metodo abre excel desde la pc y la ruta del archivo
	 * generado
	 * @throws IOException
	 */
	public void exportaLegos () throws IOException {
		testCase.addTextEvidenceCurrentStep("Ingresa a Administrador y seleccionar <<Legos>>");
		u.waitWebElement(btnConfigPlaza, 120, 3);
		u.mouseOver(btnConfigPlaza);
		u.hardWait(2);
		u.highLight(btnLegos);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.mouseOverAndClick(btnLegos);
		cargandoMsg();
		
		u.hardWait(5);
		testCase.addTextEvidenceCurrentStep("Dar click en Exportar Legos");
		u.highLight(btnExportaLegos);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(2);
		u.click(btnExportaLegos);
		String userEquipo = System.getProperty("user.name");
		userEquipo = StringUtils.right(userEquipo, 5);
		System.out.println("Usuario: "+ userEquipo);
		File pathArchivo = new File("C:/Users/" + userEquipo + "/Downloads/Legos.xls");

		String ruta = pathArchivo.toString();
	
		while(!pathArchivo.exists()) {
			System.out.println("Descargando...");
		}
		System.out.println(" -> "+ruta);
        Runtime.getRuntime().exec("cmd /c start "+ruta);
        
        u.hardWait(5);
		testCase.addScreenShotCurrentStep("");
        u.hardWait(2);

	}
	
	/**
	 * Limpia un input seleccionando el texto que tiene y borrandolo.
	 * 
	 * @param inputBy
	 * @author Esmeralda Garcia Torres
	 */
	public void clearInput(By inputBy) {
		try {
			WebElement toClear = u.getDriver().findElement(inputBy);
			toClear.sendKeys(Keys.CONTROL + "a");
			toClear.sendKeys(Keys.DELETE);
		} catch (Exception e) {
			System.out.println("Fallo al limpiar el campo: " + e);
		}
	}
	
	/**
	 * Revisa cada 2 segundos si el modal de Cargando sigue estando visible. Es una espera dinamica, en cuanto se quite el modal de Cargando, permite avanzar.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void cargandoMsg() {
		try {
			u.hardWait(1);
			while (u.getDriver().findElement(modalCargando).isDisplayed()) {
				u.hardWait(2);				
			}
		} catch (Exception e) {
			System.out.println("-> " + e);
		}
	}
	
	/**
	 * Inicia sesion en el portal de aplicaciones
	 * y cicla el login hasta que sea exitoso, si al tercer intento
	 * no se logra entrar, la pagina se refresca
	 * @param user
	 * @param ps
	 */
	public void loginGral (String user, String ps) {
		try {
			Boolean bandera = false;
			Integer cont = 0;
			u.getDriver().manage().window().maximize();

			if(u.getDriver().findElements(btnAdvance).size() != 0) {
				u.click(btnAdvance);
				u.waitWebElement(linkContinue, 20, 2);
				u.click(linkContinue);
			}
			
			u.waitWebElement(locationUserInput, 15, 2);
			testCase.addScreenShotCurrentStep("");

			do {
				cont += 1;
				if (cont < 4) {
					wait.until(ExpectedConditions.elementToBeClickable(locationLoginButton));
					u.highLight(locationUserInput);
					clearInput(locationUserInput);
					u.sendKeys(locationUserInput, user);
					u.highLight(locationPassInput);
					clearInput(locationPassInput);
					u.sendKeys(locationPassInput, ps);
					u.highLight(locationLoginButton);
					u.click(locationLoginButton);
					try {
						wait.until(ExpectedConditions.elementToBeClickable(locationMenuInicio));
						bandera = true;
					} catch (Exception e) {
						bandera = false;
					}
				} else {
					u.getDriver().navigate().refresh();
					cont = 0;
				}
			} while (!bandera);

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo iniciar sesion");
		}
		
	}
}
