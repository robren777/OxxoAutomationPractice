package WEB.mermasDeCafe.om;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.util.AccessControl;
import modelo.TestCase;
import utils.ExcelUtil;
import utils.selenium.SeleniumUtil;

public class MermasDeCafe {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	private AccessControl AC;

	By MermaAutorizadaPOS = By.linkText("MERMA AUTORIZADA POS");

	// Elementos Transferencias > Transferencia entre tiendas
	By transferencias = By.xpath("//a[text()=' Transferencias ']");
	By transferenciasEntreTiendas = By.xpath("//a[text()=' Transferencia entre tiendas']");
	By region = By.name("selectRegione");
	By inputRegion = By.xpath("//input[@class='p-dropdown-filter p-inputtext p-component ng-tns-c29-0']");
	By plaza = By.name("selectPlaza");
	By inputPlaza = By.xpath("//input[@class='p-dropdown-filter p-inputtext p-component ng-tns-c29-1']");
	By asesor = By.name("selectAsesor");
	By inputAsesor = By.xpath("//input[@class='p-dropdown-filter p-inputtext p-component ng-tns-c29-2']");
	By tienda = By.name("selectTienda");
	By inputTienda = By.xpath("//input[@class='p-dropdown-filter p-inputtext p-component ng-tns-c29-3']");
	By opcionCombo = By.xpath("//li[@class='p-ripple p-element p-dropdown-item']");
	By inputFechaInicial = By.xpath("//div[1]/input[@type='datetime-local']");
	By inputFechaFinal = By.xpath("//div[2]/input[@type='datetime-local']");
	By empoderamientoSalidaTransferenciaSi = By.id("ajusteEntrada1");
	By empoderamientoSalidaTransferenciaNo = By.id("ajusteEntrada2");
	By btnGuardar = By.xpath("//button[text()='Guardar'][@type='button']");
	By btnGuardarModal = By.xpath("//div[@class='modal-footer']/button[text()='Guardar']");
	By btnOk = By.xpath("//button[text()='OK']");
	By btnExportar = By.xpath("//button[text()='Exportar'][@type='button']");
	By mensajeAviso = By.xpath("//div[@id='swal2-html-container']");
	By reportesAdmin = By.xpath("//a[text()=' Reportes Admin ']");

	By ValorCampoEstatus = By.xpath("//tr[1]/td[6]");

	// Elementos Reportes Admin > Reporte Catalogo de empoderamiento LT
	By reporteCatalogoEmpoderamientoLT = By.xpath("//a[text()=' Reporte Catalogo de empoderamiento LT']");
	By plazaCatalogoEmpoderamiento = By.xpath("//div[@class='p-multiselect-label ng-tns-c31-0 p-placeholder']");
	By checkPlazas = By.xpath("//div[@class='p-checkbox-box ng-tns-c31-0'][@role='checkbox']");
	By btnBuscar = By.xpath("//button[text()='Buscar']");
	By logoOxxo = By.xpath("/html/body/app-root/app-navbar/body/app-transferencias/body/div/div[1]/div/div/img");

	// Elementos Merma Autorizada Autonoma Admin > Catálogo de empoderamiento de
	// líder de tienda
	By MermaAutorizadaAutonomaAdmin = By.xpath("//a[text()=' Merma Autorizada Autonoma Admin ']");
	By CatalogoEmpoderamientoLiderTienda = By
			.xpath("//li[4]/ul/li/a [text()=' Catalogo de empoderamiento de lider de tienda']");
	By comboCatEmpLiderTienda = By.xpath("//div[text()='Seleccione']");
	By inputPlazaCatEmpLiderTienda = By
			.xpath("//input[@class='p-multiselect-filter p-inputtext p-component ng-tns-c31-0']");
	By inputTiendaCatEmpLiderTienda = By
			.xpath("//input[@class='p-multiselect-filter p-inputtext p-component ng-tns-c31-1']");
	By opcionComboPlaza = By.xpath("//li[@class='p-ripple p-element p-multiselect-item']");
	By rbExcluirTiendasSi = By.xpath("//input[@name='excluirTiendas'][@value='Si']");
	By rbEmpMermaLTSi = By.xpath("//input[@name='flexRadioDefault'][@value='1']");
	By rbEmpMermaLTNo = By.xpath("//input[@name='flexRadioDefault'][@value='0']");
	By rbEmpDevolucionLTSi = By.xpath("//input[@name='flexRadioDefault4'][@value='1']");
	By rbEmpAjusteEntradaLTSi = By.xpath("//input[@name='ajusteEntrada'][@value='1']");
	By verTiendas = By.xpath("//tr[8]/td[7]/label[text()='Ver tiendas']");

	// Elementos de la tabla Catálogo de Empoderamiento de Salida de Transferencia
	// Región, Plaza, CR Tienda Empoderamiento Salida de Transferencia, Fecha de
	// activación, Modificado por y Opciones
	By tablaCatalogoEmpoderamientoSalidaTransferencia = By.xpath(
			"//div[@class='cdk-virtual-scroll-content-wrapper']/table[@class='table table-striped table-bordered']");
	By campoRegion = By.xpath("//thead[@class='theadColor']/tr/th[text()='Región']");
	By campoPlaza = By.xpath("//thead[@class='theadColor']/tr/th[text()='Plaza']");
	By campoTienda = By.xpath("//thead[@class='theadColor']/tr/th[text()='Tienda']");
	By campoEmpSalida = By.xpath("//thead[@class='theadColor']/tr/th[text()='Empoderamiento Salida de Transferencia']");
	By campoFechaActivacion = By.xpath("//thead[@class='theadColor']/tr/th[text()='Fecha de activación']");
	By campoEstatus = By.xpath("//thead[@class='theadColor']/tr/th[text()='Estatus']");
	By campoFechaRegistro = By.xpath("//thead[@class='theadColor']/tr/th[text()='Fecha de registro']");
	By campoModificadoPor = By.xpath("//thead[@class='theadColor']/tr/th[text()='Modificado por']");
	By campoOpciones = By.xpath("//thead[@class='theadColor']/tr/th[text()='Opciones']");

	By editarOpcionesEmp = By.xpath("//tr[1]/td[@class='cambioColorIcon cambioDePuntero']");

	/**
	 * Constructor.
	 * 
	 * @param u
	 * @param testCase
	 * @author Sharon S. Estrada
	 */
	public MermasDeCafe(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));
		this.testCase = testCase;
	}

	public void Ingresar(String user, String ps) {
		AC = new AccessControl(u, testCase);
		AC.loginAccessControl(user, ps);
	}

	public boolean validarCampoEstatus(String valor) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(ValorCampoEstatus));
			String textovalor = obtenerTextoCampo(ValorCampoEstatus);
			if (textovalor.equals(valor)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo validar el valor del campo Estatus");
		}
	}

	public void clickEditarOpcionesEmp() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(editarOpcionesEmp));
			testCase.addScreenShotCurrentStep(u, "editarOpcionesEmp");
			u.click(editarOpcionesEmp);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en Editar de la columna Opciones");
		}
	}

	public void clickGuardarModal() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(btnGuardarModal));
			testCase.addScreenShotCurrentStep(u, "btnGuardarModal");
			u.click(btnGuardarModal);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en el botón Guardar de la ventana modal");
		}
	}

	public void clickMermaAutorizada() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(MermaAutorizadaPOS));
			testCase.addScreenShotCurrentStep(u, "MermaAutorizadaPOS");
			u.click(MermaAutorizadaPOS);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se logró acceder a Merma Autorizada POS");
		}
	}

	public void clickMermaAutorizadaAutonomaAdmin() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(MermaAutorizadaAutonomaAdmin));
			testCase.addScreenShotCurrentStep(u, "MermaAutorizadaAutonomaAdmin");
			u.click(MermaAutorizadaAutonomaAdmin);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se logró acceder a Merma Autorizada AutonomaAdmin");
		}
	}

	public void clickCatalogoEmpoderamientoLiderTienda() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(CatalogoEmpoderamientoLiderTienda));
			testCase.addScreenShotCurrentStep(u, "CatalogoEmpoderamientoLiderTienda");
			u.click(CatalogoEmpoderamientoLiderTienda);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en Catalogo De Empoderamiento Lider Tienda");
		}
	}

	public void clickComboCatEmpLiderTienda() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(comboCatEmpLiderTienda));
			testCase.addScreenShotCurrentStep(u, "comboPlazaCatEmpLiderTienda");
			u.click(comboCatEmpLiderTienda);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError(
					"No se logró dar click en el combo Plaza de Catálogo de empoderamiento de líder de tienda");
		}
	}

	public void seleccionarPlazaCatEmp(String opcion) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputPlazaCatEmpLiderTienda));
			u.sendKeys(inputPlazaCatEmpLiderTienda, opcion);
			wait.until(ExpectedConditions.elementToBeClickable(opcionComboPlaza));
			testCase.addScreenShotCurrentStep(u, "opcionComboPlaza");
			u.click(opcionComboPlaza);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo seleccionar una plaza del combo.");
		}
	}

	public void seleccionarTiendaCatEmp(String opcion) {
		try {
			String objeto = "//li[@aria-label='" + opcion + "']";
			By opcionComboTienda = By.xpath(objeto);
			wait.until(ExpectedConditions.elementToBeClickable(inputTiendaCatEmpLiderTienda));
			u.sendKeys(inputTiendaCatEmpLiderTienda, opcion);
			wait.until(ExpectedConditions.elementToBeClickable(opcionComboTienda));
			testCase.addScreenShotCurrentStep(u, "opcionComboTienda");
			u.click(opcionComboTienda);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo seleccionar una tienda del combo.");
		}
	}

	public void excluirTiendas(String opcion) {
		try {
			if (opcion == "SI") {
				wait.until(ExpectedConditions.elementToBeClickable(rbExcluirTiendasSi));
				testCase.addScreenShotCurrentStep(u, "rbExcluirTiendasSi");
				u.click(rbExcluirTiendasSi);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se logr� dar click en la opcion SI de Excluir Tiendas");
		}
	}

	public void EmpoderamientoMermaLT(String opcion) {
		try {
			if (opcion == "SI") {
				wait.until(ExpectedConditions.elementToBeClickable(rbEmpMermaLTSi));
				testCase.addScreenShotCurrentStep(u, "EmpoderamientoMermaLT");
				u.click(rbEmpMermaLTSi);
			} else if (opcion == "NO") {
				wait.until(ExpectedConditions.elementToBeClickable(rbEmpMermaLTNo));
				testCase.addScreenShotCurrentStep(u, "EmpoderamientoMermaLT");
				u.click(rbEmpMermaLTNo);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se logr� dar click en la opcion " + opcion + " de Excluir Tiendas");
		}
	}

	public void EmpoderamientoDevolucionLT(String opcion) {
		try {
			if (opcion == "SI") {
				wait.until(ExpectedConditions.elementToBeClickable(rbEmpDevolucionLTSi));
				testCase.addScreenShotCurrentStep(u, "EmpoderamientoDevolucionLT");
				u.click(rbEmpDevolucionLTSi);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se logr� dar click en la opcion " + opcion + " de Excluir Tiendas");
		}
	}

	public void EmpoderamientoAjusteEntradaLT(String opcion) {
		try {
			if (opcion == "SI") {
				wait.until(ExpectedConditions.elementToBeClickable(rbEmpAjusteEntradaLTSi));
				testCase.addScreenShotCurrentStep(u, "EmpoderamientoAjusteEntradaLT");
				u.click(rbEmpAjusteEntradaLTSi);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se logr� dar click en la opcion " + opcion + " de Excluir Tiendas");
		}
	}

	public boolean validarCamposTabla(String dato, String nombre) {
		try {
			String objeto = "//tr/td[text()='" + dato + "']";
			By campo = By.xpath(objeto);
			wait.until(ExpectedConditions.elementToBeClickable(campo));
			testCase.addScreenShotCurrentStep(u, "validarCampo" + nombre);
			return true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se logr� validar aparezca en la tabla: " + nombre);
		}
	}

	public void clickVerTiendas() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(verTiendas));
			testCase.addScreenShotCurrentStep(u, "verTiendas");
			u.click(verTiendas);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en Ver Tiendas");
		}
	}

	public void validarCamposTablaTE(String dato, String nombre) {
		try {
			String objeto = "//tr/td/label[text()='" + dato + "']";
			By campo = By.xpath(objeto);
			wait.until(ExpectedConditions.elementToBeClickable(campo));
			testCase.addScreenShotCurrentStep(u, "validarCampo" + nombre);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se logr� validar aparezca en la tabla: " + nombre);
		}
	}

	public void clickTransferencias() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(transferencias));
			testCase.addScreenShotCurrentStep(u, "Transferencia");
			u.click(transferencias);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el apartado de Transferencias");
		}
	}

	public void clickReportesAdmin() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(reportesAdmin));
			testCase.addScreenShotCurrentStep(u, "ReportesAdmin");
			u.click(reportesAdmin);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el apartado de Reportes Admin");
		}
	}

	public void clickReporteCatalogoEmpoderamientoLT() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(reporteCatalogoEmpoderamientoLT));
			testCase.addScreenShotCurrentStep(u, " ReporteCatalogoEmpoderamientoLT");
			u.click(reporteCatalogoEmpoderamientoLT);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el apartado de Reporte Catalogo de Empoderamiento LT");
		}
	}

	public void clickPlaza_ReportePermisos() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(plazaCatalogoEmpoderamiento));
			testCase.addScreenShotCurrentStep(u, "PlazaCatalogoEmpoderamiento");
			u.click(plazaCatalogoEmpoderamiento);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el combo de Plaza");
		}
	}

	public void seleccionarTodasLasPlazas() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(checkPlazas));
			testCase.addScreenShotCurrentStep(u, "CheckPlazas_ReportePermisos");
			u.click(checkPlazas);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en el checkbox para seleccionar todas las Plazas");
		}
	}

	public void clickBuscar() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(btnBuscar));
			testCase.addScreenShotCurrentStep(u, "BotonBuscar_ReportePermisos");
			u.click(btnBuscar);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el bot�n Buscar");
		}
	}

	public void clickExportar() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(btnExportar));
			testCase.addScreenShotCurrentStep(u, "BotonExportar_ReportePermisos");
			u.click(btnExportar);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el bot�n Exportar");
		}
	}

	public void clickTransferenciaEntreTiendas() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(transferenciasEntreTiendas));
			testCase.addScreenShotCurrentStep(u, "TransferenciaEntreTiendas");
			u.click(transferenciasEntreTiendas);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el apartado de Transferencia entre Tiendas");
		}
	}

	public void clickRegion() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(region));
			testCase.addScreenShotCurrentStep(u, "Region");
			u.click(region);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el objeto especificado.");
		}
	}

	public void clickPlaza() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(plaza));
			testCase.addScreenShotCurrentStep(u, "Region");
			u.click(plaza);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el objeto especificado.");
		}
	}

	public void clickAsesor() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(asesor));
			testCase.addScreenShotCurrentStep(u, "Region");
			u.click(asesor);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el objeto especificado.");
		}
	}

	public void clickTienda() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(tienda));
			testCase.addScreenShotCurrentStep(u, "Region");
			u.click(tienda);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el objeto especificado.");
		}
	}

	public void clickGuardar() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(btnGuardar));
			testCase.addScreenShotCurrentStep(u, "BotonGuardar");
			u.click(btnGuardar);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el boton Guardar.");
		}
	}

	public void clickOk() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(btnOk));
			testCase.addScreenShotCurrentStep(u, "BotonOk");
			u.click(btnOk);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el boton Ok.");
		}
	}

	public void seleccionarRegion(String opcion) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputRegion));
			u.sendKeys(inputRegion, opcion);
			wait.until(ExpectedConditions.elementToBeClickable(opcionCombo));
			testCase.addScreenShotCurrentStep(u, "comboRegion");
			u.click(opcionCombo);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo seleccionar una region del combo.");
		}
	}

	public void seleccionarPlaza(String opcion) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputPlaza));
			u.sendKeys(inputPlaza, opcion);

			By selectopcion = By.xpath("//*[@id=\"pr_id_2_list\"]/p-dropdownitem/li");
			wait.until(ExpectedConditions.elementToBeClickable(selectopcion));
			testCase.addScreenShotCurrentStep(u, "comboRegion");
			u.click(selectopcion);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo seleccionar una plaza del combo.");
		}
	}

	public void seleccionarAsesor(String opcion) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputAsesor));
			u.sendKeys(inputAsesor, opcion);

			By selectopcion = By.xpath("//*[@id=\"pr_id_3_list\"]/p-dropdownitem/li");
			wait.until(ExpectedConditions.elementToBeClickable(selectopcion));
			testCase.addScreenShotCurrentStep(u, "comboRegion");
			u.click(selectopcion);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo seleccionar un asesor del combo.");
		}
	}

	public void seleccionarTienda(String opcion) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputTienda));
			u.sendKeys(inputTienda, opcion);

			By selectopcion = By.xpath("//*[@id=\"pr_id_4_list\"]/p-dropdownitem/li");
			wait.until(ExpectedConditions.elementToBeClickable(selectopcion));
			testCase.addScreenShotCurrentStep(u, "comboRegion");
			u.click(selectopcion);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo seleccionar una tienda del combo.");
		}
	}

	public void seleccionarFechaInicial(String fecha, String hora) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputFechaInicial));
			WebElement dateBox = u.getDriver().findElement(inputFechaInicial);
			dateBox.sendKeys(fecha);
			dateBox.sendKeys(hora);
			dateBox.sendKeys(Keys.TAB);
			testCase.addScreenShotCurrentStep(u, "FechaInicial");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo seleccionar una fecha inicial.");
		}
	}

	public void seleccionarFechaFinal(String fecha, String hora) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputFechaFinal));
			WebElement dateBox = u.getDriver().findElement(inputFechaFinal);
			dateBox.sendKeys(fecha);
			dateBox.sendKeys(hora);
			testCase.addScreenShotCurrentStep(u, "FechaFinal");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo seleccionar una fecha final.");
		}
	}

	public void empoderamientoSalidadeTransferencia(String opcion) {
		try {
			if (opcion == "SI") {
				wait.until(ExpectedConditions.elementToBeClickable(empoderamientoSalidaTransferenciaSi));
				testCase.addScreenShotCurrentStep(u, "empoderamientoSalidadeTransferencia");
				u.click(empoderamientoSalidaTransferenciaSi);
			} else if (opcion == "NO") {
				wait.until(ExpectedConditions.elementToBeClickable(empoderamientoSalidaTransferenciaNo));
				testCase.addScreenShotCurrentStep(u, "empoderamientoSalidadeTransferencia");
				u.click(empoderamientoSalidaTransferenciaNo);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError(
					"No se encontr� la opci�n \'" + opcion + "\' para el Empoderamiento Salida de Transferencia");
		}
	}

	public void validarTablaEmpSalidaTransferencia() {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(logoOxxo));
			wait.until(ExpectedConditions.elementToBeClickable(tablaCatalogoEmpoderamientoSalidaTransferencia));
			testCase.addScreenShotCurrentStep(u, "tablaCatalogoEmpoderamientoSalidaTransferencia");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.quit();
			throw new AssertionError(
					"No se encontr� la la tabla: \"Cat�logo de Empoderamiento de Salida de Transferencia\"");
		}
	}

	public boolean validarCamposTablaEmpSalidaTransferencia() {
		try {
			String[] campos = { "Regi�n", "Plaza", "CR Tienda", "Empoderamiento Salida de Transferencia",
					"Fecha de activaci�n", "Estatus", "Modificado por", "Opciones" };
			By[] objetos = { campoRegion, campoPlaza, campoTienda, campoEmpSalida, campoFechaActivacion, campoEstatus,
					campoFechaRegistro, campoModificadoPor, campoOpciones };
			for (int i = 0; i < campos.length; i++) {
				validarCampo(campos[i], objetos[i]);
			}
			testCase.addScreenShotCurrentStep(u, "CamposCatalogoEmpoderamientoSalidaTransferencia");
			return true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.quit();
			throw new AssertionError(
					"No se pudo validar que existieran los campos en la tabla de Cat�logo de Empoderamiento de Salida de Transferencia");
		}
	}

	public void validarCampo(String campo, By objeto) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(objeto));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.quit();
			throw new AssertionError("No se pudo validar que existiera el campo: " + campo
					+ "en la tabla de Cat�logo de Empoderamiento de Salida de Transferencia");
		}
	}

	public boolean validarCombosEmpTransAsesor() {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(logoOxxo));
			if (validarExistenciaObjeto(tienda, "comboTienda")) {
				if (validarExistenciaObjeto(region, "comboRegion")) {
					return false;
				} else if (validarExistenciaObjeto(plaza, "comboPlaza")) {
					return false;
				} else if (validarExistenciaObjeto(asesor, "comboAsesor")) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontro el objeto especificado.");
		}
	}

	public boolean validarExistenciaObjeto(By objeto, String nombre) {
		try {
			boolean existe = false;
			wait.until(ExpectedConditions.visibilityOfElementLocated(objeto));
			if (u.getDriver().findElements(objeto).size() != 0) {
				existe = true;
			}

			return existe;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se encontr� el objeto especificado.");
		}
	}

	public String obtenerTextoMensaje() {
		String textomensaje = "";
		try {
			wait.until(ExpectedConditions.elementToBeClickable(mensajeAviso));
			if (u.getDriver().findElements(mensajeAviso).size() != 0) {
				textomensaje = u.getText(mensajeAviso);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo obtener el texto del mensaje en pantalla.");
		}
		return textomensaje;
	}

	public boolean validarMensaje() {
		String mensaje = obtenerTextoMensaje();
		boolean registrado = false;
		try {
			if (mensaje.indexOf("Se ha registrado con exito") > -1) {
				registrado = true;
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo validar el texto: " + mensaje + " del mensaje en pantalla.");
		}
		return registrado;
	}

	public ArrayList<String> obtenerCeldas(String filepath) {
		ArrayList<String> celdas = new ArrayList<String>();

		try {
			ExcelUtil eu = new ExcelUtil(filepath);
			eu.open();
			for (int i = 0; i < 10; i++) {
				celdas.add(eu.getCell(0, 3, i));
			}
			eu.close();
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo acceder al archivo ubicado en: " + filepath);
		}

		return celdas;
	}

	public HashMap<String, ArrayList<String>> obtenerFilas(String filepath) {
		try {
			ExcelUtil eu = new ExcelUtil(filepath);
			eu.open();
			HashMap<String, ArrayList<String>> rows = eu.getRows(0);
			eu.close();
			return rows;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo acceder al archivo ubicado en: " + filepath);
		}
	}

	public boolean validarTiendasExcluidas() {
		boolean valido = true;
		By autorizado = By.xpath(" //*[@id=\"tiendasExcluidas\"]//td[3]");
		By Empoderamiento = By.xpath(" //*[@id=\"tiendasExcluidas\"]//td[4]");
		By Ajuste = By.xpath(" //*[@id=\"tiendasExcluidas\"]//td[5]");
		try {
			if (obtenerTextoCampo(autorizado).indexOf("No") == -1) {
				valido = false;
			}
			if (obtenerTextoCampo(Empoderamiento).indexOf("No") == -1) {
				valido = false;
			}
			if (obtenerTextoCampo(Ajuste).indexOf("No") == -1) {
				valido = false;
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo validar que aparezca la informacion en la tabla");
		}
		return valido;
	}

	public String obtenerTextoCampo(By campo) {
		String textomensaje = "";
		try {
			wait.until(ExpectedConditions.elementToBeClickable(campo));
			if (u.getDriver().findElements(campo).size() != 0) {
				textomensaje = u.getText(campo);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo obtener el texto del mensaje en pantalla.");
		}
		return textomensaje;
	}

	public boolean validarActualizacionRegistro(String fecha) {
		boolean valido = false;
		try {
			if (validarCamposTabla(fecha, "ActualizacionRegistro"))
				valido = true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo validar que se muestre el nuevo registro en la tabla.");
		}
		return valido;
	}

}