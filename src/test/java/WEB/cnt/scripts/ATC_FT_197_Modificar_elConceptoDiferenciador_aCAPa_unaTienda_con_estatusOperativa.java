package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
public class ATC_FT_197_Modificar_elConceptoDiferenciador_aCAPa_unaTienda_con_estatusOperativa extends BaseExecution{
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_197_Modificar_elConceptoDiferenciador_aCAPa_unaTienda_con_estatusOperativa_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/


		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		String diferenciador6 = data.get("Diferenciador6");
		


		// ------------------------------------PASO 1
	
		addStep("Ingresar a la '" + URL ); 
		
		u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos
		testCase.addScreenShotCurrentStep("Paso 1");
		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");
	
		// ------------------------------------PASO 2
		
		addStep("Ingresar el usuario "  + user + " y contrase�a para despu�s seleccionar el bot�n de Continuar");

		pa.logginInventarioEspacios(user, password);
		u.waitForLoadPage();
		u.hardWait(2);
		boolean validarPlaza = pa.validarComboBoton();
		assertTrue(validarPlaza, "No se muestran el combo Plaza y boton Continuar");

		//------------------------------------PASO 3
		addStep("Usuario selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		pa.clickSeleccionarPlaza(PlazaE);
		u.waitForLoadPage();
		u.hardWait(1);
		boolean validarDT = pa.validarDistritoTiendas();
		assertTrue(validarDT, "No despliegan las opciones de Distrito y Tiendas");
		
		//------------------------------------PASO 4
		addStep("El usuario selecciona una tienda con estatus �Operativa� correspondiente a Oxxo M�xico y da clic en el bot�n de Continuar.");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		u.waitForLoadPage();
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");
		

		//------------------------------------PASO 5
	
		addStep("Usuario selecciona la opci�n de \"Formato y Entorno\".");
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();
		fe.Framedatos();
		boolean validarFE = fe.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");

		//------------------------------------PASO 6
		
		addStep("Usuario selecciona la opci�n de \"CAP\" en el combo de Concepto Diferenciador.");
		
		//fe.Framedatos();
	
		fe.Diferenciador(diferenciador6);
		
		boolean validarActivacionCampos = fe.validarActivacionCampos();
		System.out.print(validarActivacionCampos);
		boolean validarTextoCampos = fe.ValidarCambioCampoSegmentoTiendaNA();
		System.out.print(validarTextoCampos);
		testCase.addScreenShotCurrentStep(u, "Paso6");
		assertFalse(!validarActivacionCampos && validarTextoCampos, "El sistema no habilita los campos o carga en automatico el valor NA");			
		
		//------------------------------------PASO 7
		
		addStep("Usuario confirma que se soliciten los campos de Segmento de Tienda habilitando el bot�n de Asignar");
		
		boolean validarBtn = fe.validarBtnAsignarActivo();
		assertTrue(validarBtn, "El sistema no habilita el boton Asignar");					
		fe.click_on_Asignar();	
		u.hardWait(3);
		
		fe.aceptar_onAlert();
		u.hardWait(2);
		
		boolean validarPopUp = fe.validarPopUpSegmentoTienda();
		assertTrue(validarPopUp, "No se muestra el pop up Segmento de Tienda");


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
		return "Validar que al modificar el Concepto Diferenciador para una tienda con estatus �Operativa� cambiando de un Concepto Diferenciador que es Excepci�n a �CAP�.\r\n" + 
				"Validando que solicite la captura de los siguientes campos:\r\n" + 
				"-Segmento Maestro\r\n" + 
				"-Potencial_Hogar\r\n" + 
				"-Potencial_Comida\r\n" + 
				"-Potencial_Desayuno\r\n" + 
				"-Overlay_Hogar\r\n" + 
				"-Overlay_Comida\r\n" + 
				"-Overlay_Desayuno\r\n";
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
