package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_204_Modificar_el_Concepto_Diferenciador_a_Restaurante_a_una_Tienda_con_estatus_Reprogramada extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_204_Modificar_el_Concepto_Diferenciador_a_Restaurante_a_una_Tienda_con_estatus_Reprogramada_test(HashMap<String, String> data) {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plazaE = data.get("PlazaE");
		String tienda = data.get("Tienda");
		String distrito = data.get("Distrito");
		String diferenciador= data.get("Diferenciador");
		
		// Paso 1
		addStep("Ingresar a la <LigaCNT> del portal de aplicaciones.");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();	
		Login pa = new Login(u, testCase);
		u.get(data.get("Url"));
		testCase.addScreenShotCurrentStep("Imagen de login");
		u.hardWait(2);
		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");
		
		// Paso 2
		addStep("Ingresar el usuario <UsuarioAdm> y contrase�a <PassAdm> para despu�s seleccionar el bot�n de Continuar");
		pa.logginInventarioEspacios(user, password);
		u.waitForLoadPage();
		u.hardWait(2);
		boolean validarPlaza = pa.validarComboBoton();
		assertTrue(validarPlaza, "No se muestran el combo Plaza y boton Continuar");
		
		// Paso 3
		addStep("Selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		pa.clickSeleccionarPlaza(plazaE);
		u.waitForLoadPage();
		u.hardWait(1);
		boolean validarDT = pa.validarDistritoTiendas();
		assertTrue(validarDT, "No despliegan las opciones de Distrito y Tiendas");
		
		// Paso 4
		addStep("El usuario selecciona una tienda con estatus \"Reprogramada\" correspondiente a Oxxo M�xico y da clic en el bot�n de Continuar.");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		u.waitForLoadPage();
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");
		
		// Paso 5
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		Formato_y_Entorno fO =  new Formato_y_Entorno(u, testCase);
		fO.FormatoyEntorno();
		fO.Framedatos();
		boolean validarFE = fO.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");
		
		// Paso 6
		addStep("Usuario selecciona la opci�n de \"Restaurante\" en el combo de Concepto Diferenciador.");
		fO.Diferenciador(diferenciador);
		boolean validarValores = fO.ValidarCambioCampoSegmentoTiendaNA();		
		testCase.addScreenShotCurrentStep("valoresCampos");
		
		if(!validarValores) {		
			testCase.addTextEvidenceCurrentStep("El sistema no cargo en automatico el valor NA en los campos");
		}
		
		
		// Paso 7
		addStep("Usuario confirma que se haya precargado los valores en los campos de Segmento de Tienda.");
		testCase.addScreenShotCurrentStep(u, "Paso7");			
		assertTrue(validarValores, "No se precargaron los valores");	
		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}