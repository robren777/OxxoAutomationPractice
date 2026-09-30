package WorkFlows.Sembrado.MatrizALM.ConfigPlazaPlaza;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Sembrado.pomSembradoALM;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class TC_000_ConfiguracionPlazaPlaza_Legos_ConjuntoAdicional extends BaseExecution {
	private SeleniumUtil u;
	private pomSembradoALM util;
	private WebDriverWait wait;

	/// WEB ELEMENTS
	private By locationConjuntoAdicionalRadio = By.id("radTipoConjunto1");
	private By locationConjuntoAdicionalSelect = By.id("idCmbConjunto");
	private By locationPlazaRadio = By.id("radTipoConjunto2");
	private By locationSegmLegoSelect = By.id("idCmbSegmento");
	private By locationPlanoGramableSelect = By.id("idCmbPlanogramable");
	private By locationPlanoGrupoSelect = By.id("idCmbPlanogrupo");
	private By locationFitInput = By.cssSelector("#idfit > input");
	private By locationMuebleSelect = By.id("idCmbMueble");
	private By locationTamanioSelect = By.id("idCmbTamano");
	private By locationDirecLegoSelect = By.id("idCmbDirLego");
	private By locationVarieDinamicaSelect = By.cssSelector("select[name='idCmbVarDinamica']");
	private By locationAgregarButton = By.cssSelector("#TABLE_9 > tbody > tr:nth-child(10) > td > input:nth-child(1)");
	private By locationExportarButton = By.cssSelector("#TABLE_9 > tbody > tr:nth-child(10) > td > input:nth-child(2)");

	@Test(dataProvider = "data-provider")

	public void ATC_FT_000_ConfiguracionPlazaPlaza_Legos_ConjuntoAdicional_Alta_NoPlanogramable(
			HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows");

		/// GET DATA FROM INPUT FILE ///
		String accion = data.get("accion");
		String tipoConjunto = data.get("tipoConjunto");
		String conjunto = data.get("conjunto");
		String segmentoLego = data.get("segmentoLego");
		String planogramable = data.get("planogramable");
		String planoGrupo = data.get("planoGrupo");
		String fit = data.get("fit");
		String mueble = data.get("mueble");
		String tamanio = data.get("tamanio");
		String direccionLego = data.get("direccionLego");
		String variedadDinamica = data.get("variedadDinamica");

		System.out.println(" accion: " + accion + " Tipo Conjunto: " + tipoConjunto + " Conjunto: " + conjunto
				+ " Segmento Lego: " + segmentoLego + " Planogramable: " + planogramable + " PlanoGrupo: " + planoGrupo
				+ " Fit: " + fit + " Mueble: " + mueble + " Tamanio: " + tamanio + " Direccion Lego: " + direccionLego
				+ " Variedad dinamica: " + variedadDinamica);

		/// REVISION OF MANDATORY DATA IN THE INPUT FILE ///
		if ((accion.equals("")) || (tipoConjunto.equals("")) || (conjunto.equals("")) || (segmentoLego.equals(""))
				|| (planogramable.equals("")) || (planoGrupo.equals("")) || (fit.equals("")) || (mueble.equals(""))
				|| (tamanio.equals("")) || (direccionLego.equals("")) || (variedadDinamica.equals(""))) {
			testCase.failStep("Faltan datos mandatorios en el archivo de entrada");
		}

		/// LOGIN ///
		addStep("Iniciar sesi�n");
		u = new SeleniumUtil(new FirefoxTest(), true);
		util = new pomSembradoALM(u);
		wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
		util.loginSembrado(GlobalVariables.urlSembrado, GlobalVariables.userAdminSembrado,
				GlobalVariables.passSembrado);
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion exitosa");

		/// SEARCH SEMBRADO PLAZA OPTION ///
		addStep("Ingresar a Sembrado Plaza");
		util.goToSembradoPlaza();
		testCase.addScreenShotCurrentStep(u, "Pagina Sembrado Plaza");

		/// SELECT LEGO OPTION ///
		addStep("Ingresar a Lego");
		util.goToLegos();
		testCase.addScreenShotCurrentStep(u, "Lego");

		/// ACCIONES ///
		util.loadingDialogBox();
		String[] resultTable;
		switch (accion.toUpperCase()) {
		case "AGREGAR NO PLANOGRAMABLE": // ---------------------------------------------------------------------------------------------
											// AGREGAR
			addStep("Ingresar datos");
			testCase.addTextEvidenceCurrentStep(
					"Tipo Conjunto: " + tipoConjunto + " Conjunto: " + conjunto + " Segmento Lego: " + segmentoLego
							+ " Planogramable: " + planogramable + " PlanoGrupo: " + planoGrupo + " Fit: " + fit
							+ " Direccion Lego: " + direccionLego + " Variedad dinamica: " + variedadDinamica);

			enterData("Conjunto Adicional-R", tipoConjunto);
			enterData("Conjunto Adicional", conjunto);
			enterData("Segmento Lego", segmentoLego);
			enterData("Planogramable", planogramable);
			enterData("Planogrupo", planoGrupo);
			enterData("Fit", fit);
			enterData("Direccion Lego", direccionLego);
			enterData("Variedad Dinamica", variedadDinamica);
			testCase.addScreenShotCurrentStep(u, "Datos ingresados");

			addStep("Agregar Legos");
			addLegos();
			util.loadingDialogBox();
			
			u.getDriver().navigate().refresh();
			u.hardWait(1);

			addStep("Verificar que se agreg� el registro");
			if (direccionLego.equalsIgnoreCase("NO")) {
				resultTable = searchInTableLegos(conjunto, segmentoLego, planoGrupo, fit, "-", "-", "NA",
						variedadDinamica);
				if (Boolean.parseBoolean(resultTable[0])) {
					testCase.addScreenShotCurrentStep(u, "Se encontr� el registro");
				} else {
					testCase.failStep("No se encontr� el registro en la tabla");
				}
			} else {
				resultTable = searchInTableLegos(conjunto, segmentoLego, planoGrupo, fit, "-", "-", "DI",
						variedadDinamica);
				if (Boolean.parseBoolean(resultTable[0])) {
					testCase.addTextEvidenceCurrentStep("Direccion lego - DI");
					testCase.addScreenShotCurrentStep(u, "Se encontr� el registro - DI");
				} else {
					testCase.failStep("No se encontr� el registro en la tabla");
				}
				resultTable = searchInTableLegos(conjunto, segmentoLego, planoGrupo, fit, "-", "-", "ID",
						variedadDinamica);
				if (Boolean.parseBoolean(resultTable[0])) {
					testCase.addTextEvidenceCurrentStep("Direccion lego - ID");
					testCase.addScreenShotCurrentStep(u, "Se encontr� el registro - ID");
				} else {
					testCase.failStep("No se encontr� el registro en la tabla");
				}
			}
			break;
		case "AGREGAR PLANOGRAMABLE":
			addStep("Ingresar datos");
			testCase.addTextEvidenceCurrentStep("Tipo Conjunto: " + tipoConjunto + " Conjunto: " + conjunto
					+ " Segmento Lego: " + segmentoLego + " Planogramable: " + planogramable + " PlanoGrupo: "
					+ planoGrupo + " Mueble: " + mueble + " Tamanio: " + tamanio + " Direccion Lego: " + direccionLego
					+ " Variedad dinamica: " + variedadDinamica);

			enterData("Conjunto Adicional-R", tipoConjunto);
			enterData("Conjunto Adicional", conjunto);
			enterData("Segmento Lego", segmentoLego);
			enterData("Planogramable", planogramable);
			enterData("Planogrupo", planoGrupo);
			enterData("Mueble", mueble);
			enterData("Tamanio", tamanio);
			enterData("Direccion Lego", direccionLego);
			enterData("Variedad Dinamica", variedadDinamica);
			testCase.addScreenShotCurrentStep(u, "Datos ingresados");

			addStep("Agregar Legos");
			addLegos();

			addStep("Verificar que se agreg� el registro");
			if (direccionLego.equalsIgnoreCase("NO")) {
				resultTable = searchInTableLegos(conjunto, segmentoLego, planoGrupo, "-", mueble, tamanio, "NA",
						variedadDinamica);
				if (Boolean.parseBoolean(resultTable[0])) {
					testCase.addScreenShotCurrentStep(u, "Se encontr� el registro");
				} else {
					testCase.failStep("No se encontr� el registro en la tabla");
				}
			} else {
				resultTable = searchInTableLegos(conjunto, segmentoLego, planoGrupo, "-", mueble, tamanio, "DI",
						variedadDinamica);
				if (Boolean.parseBoolean(resultTable[0])) {
					testCase.addTextEvidenceCurrentStep("Direccion lego - DI");
					testCase.addScreenShotCurrentStep(u, "Se encontr� el registro - DI");
				} else {
					testCase.failStep("No se encontr� el registro DI en la tabla");
				}
				resultTable = searchInTableLegos(conjunto, segmentoLego, planoGrupo, "-", mueble, tamanio, "ID",
						variedadDinamica);
				if (Boolean.parseBoolean(resultTable[0])) {
					testCase.addTextEvidenceCurrentStep("Direccion lego - ID");
					testCase.addScreenShotCurrentStep(u, "Se encontr� el registro - ID");
				} else {
					testCase.failStep("No se encontr� el registro ID en la tabla");
				}
			}
			break;
		case "ELIMINAR": // --------------------------------------------------------------------------------------------
							// ELIMINAR
			addStep("Seleccionar el Conjunto");
			enterData("Conjunto Adicional-R", tipoConjunto);
			enterData("Conjunto Adicional", conjunto);
			testCase.addScreenShotCurrentStep(u, "Seleccionar conjunto");

			addStep("Buscar el registro en la tabla");
			resultTable = searchInTableLegos(conjunto, segmentoLego, planoGrupo, fit, "-", "-", "NA", variedadDinamica);
			if (Boolean.parseBoolean(resultTable[0])) {
				testCase.addScreenShotCurrentStep(u, "Se encontr� el registro");
			} else {
				testCase.failStep("No se encontr� el registro en la tabla");
			}

			addStep("Eliminar pero en la confirmacion seleccionar \"No\"");
			deleteLegos(Integer.parseInt(resultTable[1]), "no");

			addStep("Eliminar pero en la confirmacion seleccionar \"Salir\"");
			deleteLegos(Integer.parseInt(resultTable[1]), "salir");

			addStep("Eliminar pero en la confirmacion seleccionar \"Si\"");
			deleteLegos(Integer.parseInt(resultTable[1]), "si");
			util.selectOptionDialogBox("aceptar");
			break;
		case "EXPORTAR": // -------------------------------------------------------------------------------------------
							// EXPORTAR
			util.loadingDialogBox();
			addStep("Seleccionar Tipo de Conjunto: " + tipoConjunto);
			enterData("Conjunto Adicional-R", tipoConjunto);
			util.loadingDialogBox();
			testCase.addScreenShotCurrentStep(u, "Seleccionar Tipo de Conjunto");

			util.loadingDialogBox();
			addStep("Exportar");
			exportLegos();
			testCase.addScreenShotCurrentStep(u, "Exportar");

			break;
		case "MOSTRAR":
			break;
		default:
			throw new AssertionError("La opci�n de la accion no es v�lida");
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void selectOption(String opcion, String valor, By elementBy) {
		try {
			Select select = new Select(u.getDriver().findElement(elementBy));
			select.selectByVisibleText(valor);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al seleccionar: " + opcion);
		}
	}
	
	
	/**
	 * Contiene los campos de entrada de la p�gina de legos y permite ingresar datos
	 * en ellos. Las opciones disponibles son: Conjunto Adicional-R, Plaza-R,
	 * Conjunto Adicional, Segmento Lego, Planogramable, Planogrupo, Fit, Direccion
	 * Lego y Variedad Dinamica.
	 * 
	 * @param option
	 * @param data
	 */
	private void enterData(String option, String data) {
		try {
			util.loadingDialogBox();
			switch (option.toUpperCase()) {
			case "CONJUNTO ADICIONAL-R":
				wait.until(ExpectedConditions.elementToBeClickable(locationConjuntoAdicionalRadio));
				u.highLight(locationConjuntoAdicionalRadio);
				if (data.equalsIgnoreCase("SI")) {
					if (!u.getDriver().findElement(locationConjuntoAdicionalRadio).isSelected()) {
						u.click(locationConjuntoAdicionalRadio);
					}
				} else {
					if (u.getDriver().findElement(locationConjuntoAdicionalRadio).isSelected()) {
						u.click(locationConjuntoAdicionalRadio);
					}
				}
				break;
			case "PLAZA-R":
				wait.until(ExpectedConditions.elementToBeClickable(locationPlazaRadio));
				u.highLight(locationPlazaRadio);
				if (data.equalsIgnoreCase("SI")) {
					if (!u.getDriver().findElement(locationPlazaRadio).isSelected()) {
						u.click(locationPlazaRadio);
					}
				} else {
					if (u.getDriver().findElement(locationPlazaRadio).isSelected()) {
						u.click(locationPlazaRadio);
					}
				}
				break;
			case "CONJUNTO ADICIONAL":
				wait.until(ExpectedConditions.elementToBeClickable(locationConjuntoAdicionalSelect));
				selectOption("Conjunto Adicional", data, locationConjuntoAdicionalSelect);
				break;
			case "SEGMENTO LEGO":
				wait.until(ExpectedConditions.elementToBeClickable(locationSegmLegoSelect));
				selectOption("Segmento Lego", data, locationSegmLegoSelect);
				break;
			case "PLANOGRAMABLE":
				wait.until(ExpectedConditions.elementToBeClickable(locationPlanoGramableSelect));
				selectOption("Planogramable", data, locationPlanoGramableSelect);
				break;
			case "PLANOGRUPO":
				wait.until(ExpectedConditions.elementToBeClickable(locationPlanoGrupoSelect));
				selectOption("Planogrupo", data, locationPlanoGrupoSelect);
				break;
			case "FIT":
				wait.until(ExpectedConditions.elementToBeClickable(locationFitInput));
				u.clear(locationFitInput);
				u.sendKeys(locationFitInput, data);
				break;
			case "MUEBLE":
				wait.until(ExpectedConditions.elementToBeClickable(locationMuebleSelect));
				selectOption("Mueble", data, locationMuebleSelect);
				break;
			case "TAMANIO":
				wait.until(ExpectedConditions.elementToBeClickable(locationTamanioSelect));
				selectOption("Tamanio", data, locationTamanioSelect);
				break;
			case "DIRECCION LEGO":
				wait.until(ExpectedConditions.elementToBeClickable(locationDirecLegoSelect));
				selectOption("Direccion Lego", data, locationDirecLegoSelect);
				break;
			case "VARIEDAD DINAMICA":
				wait.until(ExpectedConditions.visibilityOfElementLocated(locationVarieDinamicaSelect));
				selectOption("Variedad Dinamica", data, locationVarieDinamicaSelect);
				break;
			default:
				throw new AssertionError("La opci�n de la para agregar el dato no es v�lida");
			}
			u.hardWait(1);
			util.loadingDialogBox();
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al ingresar el dato");
		}
	}

	private void addLegos() {
		try {
			util.loadingDialogBox();
			wait.until(ExpectedConditions.elementToBeClickable(locationAgregarButton));
			u.highLight(locationAgregarButton);
			u.click(locationAgregarButton);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Agregar\"");
		}
		try {
			util.loadingDialogBox();
			String resultText = util.getTextDialogBox();
			if (resultText.contains("ya existe")) {
				testCase.addTextEvidenceCurrentStep("El Lego ya existe");
				testCase.addScreenShotCurrentStep(u, "Ya existe el Lego");
				util.selectOptionDialogBox("aceptar");
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� confirmaci�n al Agregar");
		}
	}

	private void exportLegos() {
		util.loadingDialogBox();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationExportarButton));
			u.highLight(locationExportarButton);
			u.click(locationExportarButton);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Exportar\"");
		}
		util.loadingDialogBox();
		try {
			SikuliUtil windows = new SikuliUtil();
			String pathImages = windows.PatternPath("WorkFlows"+File.separator+"SembradoALM");
			System.out.println("PATH>>> " + pathImages);
			System.out.println(LocalDateTime.now().toString());
			Pattern nameInput = windows.PatternImage(pathImages + "nameInput.png");
			windows.screen.wait(nameInput, 25);
			String timeSave = LocalDateTime.now().toString().replace("/", "-").replace(":", ".");
			windows.screen.type(nameInput,
					"C:\\Projects\\WF\\FrameworkWorkFlows\\Output\\LegosExportar\\Legos_" + timeSave);
			u.hardWait(1);
			Pattern saveButton = windows.PatternImage(pathImages + "saveButton.png");
			windows.screen.wait(saveButton, 25);
			testCase.addTextEvidenceCurrentStep("Guardar w");
			testCase.addScreenShotCurrentStep(u, "IMG");

			windows.screen.click(saveButton);
			System.out.println("CAPTURA>>>" + windows.screen.capture());

		} catch (Exception e) {
			System.out.println(" -> SIKULY >" + e);
		}
	}

	private void deleteLegos(int row, String option) {
		try {
			util.loadingDialogBox();
			By deleteOption = By
					.cssSelector(".tableContainer > table > tbody > tr:nth-child(" + row + ") > td:nth-child(1) > img");
			u.highLight(deleteOption);
			u.click(deleteOption);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Eliminar\"");
		}
		try {
			util.loadingDialogBox();
			util.getTextDialogBox();
			testCase.addScreenShotCurrentStep(u, "Eliminar - " + option.toUpperCase());
			util.selectOptionDialogBox(option);
			u.hardWait(1);
			util.loadingDialogBox();
			testCase.addScreenShotCurrentStep(u, "Result - " + option.toUpperCase());
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError(
					"Fall� al seleccionar la opci�n: " + option + " en la confirmaci�n de la eliminaci�n");
		}
	}

	public String[] searchInTableLegos(String conjunto, String segmentoLego, String planoGrupo, String fit,
			String mueble, String tamanio, String direccionLego, String variedadDinamica) {
		String[] results = new String[2];
		results[0] = "false";
		results[1] = "";
		try {
			u.hardWait(2);
			util.loadingDialogBox();
			List<WebElement> elementsTable = u.getDriver()
					.findElements(By.cssSelector(".tableContainer > table > tbody > tr"));
			System.out.println("Elementos en la Tabla: " + elementsTable.size());
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText").toUpperCase();
				String[] dato = element.split("\t");
				String conjuntoElement = dato[0].trim();
				String segmLegoElement = dato[1].trim();
				String planoGrupoElement = dato[2].trim();
				String muebleElement = dato[3].trim();
				String tamanioElement = dato[4].trim();
				String fitElement = dato[6].trim();
				String dirLegoElement = dato[7].trim();
				String varDinaElement = dato[8].trim();
				System.out.println("Fila " + i + " InnerText: " + element);
				if (conjuntoElement.equalsIgnoreCase(conjunto)) {
					System.out.println(">>>>CONJUNTO<<<<");
					if (segmLegoElement.equalsIgnoreCase(segmentoLego)) {
						System.out.println(">>>>SEGMENTO LEGO<<<<");
						if (planoGrupoElement.equalsIgnoreCase(planoGrupo)) {
							System.out.println(">>>>PLANOGRUPO<<<<");
							if (fitElement.equalsIgnoreCase(fit)) {
								System.out.println(">>>>FIT<<<<");
								if (muebleElement.equalsIgnoreCase(mueble)) {
									System.out.println(">>>>MUEBLE<<<<");
									if (tamanioElement.equalsIgnoreCase(tamanio)) {
										System.out.println(">>>>TAMANIO<<<<");
										if (dirLegoElement.equalsIgnoreCase(direccionLego)) {
											System.out.println(">>>>DIRECCION LEGO<<<<");
											if (varDinaElement.equalsIgnoreCase(variedadDinamica)) {
												System.out.println(">>>>VARIEDAD DINAMICA<<<< OK >>>>");
												By tableElement = By
														.cssSelector(".tableContainer > table > tbody > tr:nth-child("
																+ (i + 1) + ")");
												u.highLight(tableElement);
												WebElement m = u.getDriver().findElement(tableElement);
												((JavascriptExecutor) u.getDriver())
														.executeScript("arguments[0].scrollIntoView(true);", m);
												u.hardWait(2);
												results[0] = "true";
												results[1] = Integer.toString(i + 1);
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al realizar la b�squeda en la tabla");
		}
		return results;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Ingresar los datos en el data-provider correspondiente";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "TC_000_ConfiguracionPlazaPlaza_Legos_ConjuntoAdicional_Alta_NoPlanogramable";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_000_ConfiguracionPlazaPlaza_Legos_ConjuntoAdicional_Alta_NoPlanogramable";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
