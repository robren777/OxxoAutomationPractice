package WorkFlows.Sembrado.MatrizALM.ConfigPlazaPlaza;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Sembrado.pomSembradoALM;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class TC_000_ConfiguracionPlazaPlaza_Equivalencias extends BaseExecution {
	private SeleniumUtil u;
	private pomSembradoALM util;
	// private WebDriverWait wait;

	/// EQUIVALENCIAS WEB ELEMENTS - EQ
	private By locationEQplazaSelect = By.id("idCmbPlaza");
	private By locationEQplanoGrupoSelect = By.id("idCmbPlanogrupo");
	private By locationEQrecuperarValoresButton = By
			.cssSelector("tr.tablaDatos:nth-child(9) > td:nth-child(3) > input:nth-child(1)");
	private By locationEQguardarButton = By.cssSelector("input.boton:nth-child(2)");
	private By locationEQeliminarButton = By
			.cssSelector("#TABLE_11 > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(2) > input:nth-child(1)");

	@Test(dataProvider = "data-provider")

	public void ATC_FT_000_ConfiguracionPlazaPlaza_Equivalencias(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows");

		/// GET DATA FROM INPUT FILE ///
		String accion = data.get("accion");
		String plaza = data.get("plaza");
		String planoGrupo = data.get("planoGrupo");
		System.out.println(" Accion: " + accion + " Plaza: " + plaza + " Plano Grupo: " + planoGrupo);

		/// REVISION OF MANDATORY DATA IN THE INPUT FILE ///
		if ((accion.equals("")) || (plaza.equals("")) || (planoGrupo.equals(""))) {
			testCase.failStep("Faltan datos mandatorios en el archivo de entrada");
		}

		/// LOGIN ///
		addStep("Iniciar sesi�n");
		u = new SeleniumUtil(new FirefoxTest(), true);
		util = new pomSembradoALM(u);
		// wait = new WebDriverWait(u.getDriver(),10);
		util.loginSembrado(GlobalVariables.urlSembrado, GlobalVariables.userAdminSembrado,
				GlobalVariables.passSembrado);
		testCase.addScreenShotCurrentStep(u, "Inicio de sesion exitosa");

		/// SEARCH SEMBRADO PLAZA OPTION ///
		addStep("Ingresar a Sembrado Plaza");
		util.goToSembradoPlaza();
		testCase.addScreenShotCurrentStep(u, "Pagina Sembrado Plaza");

		/// SELECT EQUIVALENCIAS OPTION ///
		addStep("Ingresar a Equivalencias");
		util.goToEquivalencias();
		testCase.addScreenShotCurrentStep(u, "Equivalencias");

		/// ACCIONES ///
		String tamanio;
		String resultText;
		switch (accion.toUpperCase()) {
		case "AGREGAR": // ------------------------------------------------------------------------------------------------
						// ALTA
			addStep("Ingresar Plaza y PlanoGrupo");
			testCase.addTextEvidenceCurrentStep("Plaza: \"" + plaza + "\" - Plano Grupo: \"" + planoGrupo + "\"");
			selectPlazaPlanoGrupo(plaza, planoGrupo);
			testCase.addScreenShotCurrentStep(u, "Ingresar Plaza-PlanoGrupo");

			tamanio = "7";
			enterTamanioEQ(tamanio);
			testCase.addTextEvidenceCurrentStep("Ingresar Tama�o");
			testCase.addScreenShotCurrentStep(u, "Ingresar Tama�o");

			addStep("Agregar Plaza-PlanoGrupo");
			addEquivalencia();
			resultText = util.getTextDialogBox();
			if (resultText.contains("No pueden existir tama�os relativos iguales")) {
				testCase.addTextEvidenceCurrentStep("El tama�o ya existe");
				testCase.addScreenShotCurrentStep(u, "Ya existe el tama�o");
				util.selectOptionDialogBox("aceptar");
			} else {
				testCase.addTextEvidenceCurrentStep("Se guard� correctamente");
				testCase.addScreenShotCurrentStep(u, "Se guard� correctamente");

				addStep("Verificar cambios");
				if (getTamanioEQ().equalsIgnoreCase(tamanio)) {
					testCase.addScreenShotCurrentStep(u, "Verificar");
				} else {
					testCase.failStep("No se guardaron los cambios");
				}
			}
			break;
		case "ELIMINAR": // --------------------------------------------------------------------------------------------
							// ELIMINAR
			addStep("Ingresar Plaza y PlanoGrupo");
			testCase.addTextEvidenceCurrentStep("Plaza: \"" + plaza + "\" - Plano Grupo: \"" + planoGrupo + "\"");
			selectPlazaPlanoGrupo(plaza, planoGrupo);
			testCase.addScreenShotCurrentStep(u, "Ingresar Plaza-PlanoGrupo");

			addStep("Eliminar pero en la confirmacion seleccionar \"No\"");
			deleteEquivalencia("no");

			addStep("Eliminar pero en la confirmacion seleccionar \"Salir\"");
			deleteEquivalencia("salir");

			addStep("Eliminar pero en la confirmacion seleccionar \"Si\"");
			deleteEquivalencia("si");
			util.selectOptionDialogBox("aceptar");

			addStep("Verificar eliminaci�n");
			if (getTamanioEQ().equalsIgnoreCase("")) {
				testCase.addScreenShotCurrentStep(u, "Verificado");
			} else {
				testCase.failStep("No se elimin� correctamente");
			}

			break;
		case "RECUPERAR": // -------------------------------------------------------------------------------------------
							// RECUPERAR
			addStep("Ingresar Plaza y PlanoGrupo");
			testCase.addTextEvidenceCurrentStep("Plaza: \"" + plaza + "\" - Plano Grupo: \"" + planoGrupo + "\"");
			selectPlazaPlanoGrupo(plaza, planoGrupo);
			testCase.addScreenShotCurrentStep(u, "Ingresar Plaza-PlanoGrupo");

			String tamanioPrevio = getTamanioEQ();
			tamanio = "7";
			enterTamanioEQ(tamanio);
			testCase.addTextEvidenceCurrentStep(
					"Cambiar el primer valor del primer registro de \"" + tamanioPrevio + "\" a \"" + tamanio + "\"");
			testCase.addScreenShotCurrentStep(u, "Ingresar Tama�o");

			addStep("Agregar Plaza-PlanoGrupo");
			addEquivalencia();
			resultText = util.getTextDialogBox();
			if (resultText.contains("No pueden existir tama�os relativos iguales")) {
				testCase.addTextEvidenceCurrentStep("El tama�o ya existe");
				testCase.addScreenShotCurrentStep(u, "Ya existe el tama�o");
				util.selectOptionDialogBox("aceptar");
			} else {
				testCase.addTextEvidenceCurrentStep("Se guard� correctamente");
				testCase.addScreenShotCurrentStep(u, "Se guard� correctamente");
			}

			addStep("Recuperar pero en la confirmacion seleccionar \"No\"");
			recuperarValoresEQ("no");

			addStep("Recuperar pero en la confirmacion seleccionar \"Salir\"");
			recuperarValoresEQ("salir");

			addStep("Recuperar pero en la confirmacion seleccionar \"Si\"");
			recuperarValoresEQ("si");
			util.selectOptionDialogBox("aceptar");

			addStep("Verificar que el cambio se revirtio, el valor esperado en el primer registro es: \""
					+ tamanioPrevio + "\"");
			if (getTamanioEQ().equalsIgnoreCase(tamanioPrevio)) {
				testCase.addScreenShotCurrentStep(u, "Verificado");
			} else {
				testCase.failStep("No se revirtieron los cambios");
			}
			break;
		default:
			throw new AssertionError("La opci�n de la accion no es v�lida");
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void selectPlazaPlanoGrupo(String plaza, String planoGrupo) {
		try {
			Select plazaSelect = new Select(u.getDriver().findElement(locationEQplazaSelect));
			plazaSelect.selectByVisibleText(plaza);
			u.hardWait(1);
			Select planoGrupoSelect = new Select(u.getDriver().findElement(locationEQplanoGrupoSelect));
			planoGrupoSelect.selectByVisibleText(planoGrupo);
			u.hardWait(2);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar alguna de las opciones");
		}
	}

	public void enterTamanioEQ(String size) {
		try {
			u.hardWait(2);
			By tamanioInput = By.cssSelector(
					".tableContainer > table > tbody > tr:nth-child(2) > td:nth-child(1) > input:nth-child(1)");
			u.clear(tamanioInput);
			u.sendKeys(tamanioInput, size);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar el tama�o: 1ra fila");
		}
	}

	public void addEquivalencia() {
		try {
			u.highLight(locationEQguardarButton);
			u.hardWait(1);
			u.click(locationEQguardarButton);
			u.hardWait(3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Guardar\"");
		}
	}

	public String getTamanioEQ() {
		String result = "";
		try {
			By tamanioInput = By.cssSelector(
					".tableContainer > table > tbody > tr:nth-child(2) > td:nth-child(1) > input:nth-child(1)");
			result = u.getDriver().findElement(tamanioInput).getAttribute("value");
			u.hardWait(1);
			System.out.println("GET TAMA�O: " + result);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo extraer el valor del input tama�o");
		}
		return result;
	}

	public void deleteEquivalencia(String option) {
		try {
			u.highLight(locationEQeliminarButton);
			u.hardWait(1);
			u.click(locationEQeliminarButton);
			u.hardWait(3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Eliminar\"");
		}
		try {
			util.getTextDialogBox();
			testCase.addScreenShotCurrentStep(u, "Eliminar - " + option.toUpperCase());
			util.selectOptionDialogBox(option.toUpperCase());
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "Result - " + option.toUpperCase());
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al seleccionar la opci�n");
		}
	}

	public void recuperarValoresEQ(String option) {
		try {
			u.highLight(locationEQrecuperarValoresButton);
			u.hardWait(1);
			u.click(locationEQrecuperarValoresButton);
			u.hardWait(3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontr� el bot�n \"Recuperar Valores\"");
		}
		try {
			util.getTextDialogBox();
			testCase.addScreenShotCurrentStep(u, "Recuperar - " + option.toUpperCase());
			util.selectOptionDialogBox(option.toUpperCase());
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "Result - " + option.toUpperCase());
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al seleccionar la opci�n");
		}
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
		return "TC_000_ConfiguracionPlazaPlaza_Equivalencias";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Esmeralda Garcia Torres";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_000_ConfiguracionPlazaPlaza_Equivalencias";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
