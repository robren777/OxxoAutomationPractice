package WEB.cnt.scripts;

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

public class ATC_FT_311_Modificar_a_un_nuevo_Segmento_Maestro_y_Necesidad_a_una_Tienda_con_estatus_Cancelada extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_311_Modificar_a_un_nuevo_Segmento_Maestro_y_Necesidad_a_una_Tienda_con_estatus_Cancelada_test(HashMap<String, String> data) {
		
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
		String segmentoMaestro = data.get("Segmento Maestro");
		String potencialHogar = data.get("Potencial Hogar");
		String potencialComida = data.get("Potencial Comida");
		String potencialDesayuno = data.get("Potencial Desayuno");
		String potencialPba = data.get("Potencial Pba Nva");
		String overlayHogar = data.get("Overlay Hogar");
		String overlayComida = data.get("Overlay Comida");
		String overlayDesayuno = data.get("Overlay Desayuno");
		String overlayPba = data.get("Overlay Pba Nva");
		
		// Paso 1
		addStep("Ingresar a la <LigaCNT> del portal de aplicaciones.");
		u = new SeleniumUtil(new ChromeTest(), true);
		Login pa = new Login(u, testCase);
		u.get(data.get("Url"));
		testCase.addScreenShotCurrentStep("Imagen de login");
		
		// Paso 2
		addStep("Ingresar el usuario y contrase�a para despu�s seleccionar el bot�n de Continuar");
		pa.logginInventarioEspacios(user, password);
		
		// Paso 3
		addStep("Selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		pa.clickSeleccionarPlaza(plazaE);
		
		// Paso 4
		addStep("El usuario selecciona una tienda con estatus \"Cancelada\" que corresponda a Oxxo M�xico y da clic en el bot�n de Continuar.");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		
		// Paso 5
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		Formato_y_Entorno fO =  new Formato_y_Entorno(u, testCase);
		fO.FormatoyEntorno();
		testCase.addScreenShotCurrentStep(u, "imagen");
		
		// Paso 6
		addStep("Usuario selecciona una opci�n del combo de \"Concepto Diferenciador\" que solicite la captura de los datos de Segmento de Tienda.");
		fO.Framedatos();
		fO.Diferenciador(diferenciador);
		testCase.addScreenShotCurrentStep("imagen");
		
		// Paso 7
		addStep("Usuario selecciona el bot�n de \"Asignar\" de la opci�n de Segmento de Tienda.");
		fO.click_on_Asignar();
		
		// Paso 8
		addStep("Sistema mostrara el mensaje de \"Si realizo Cambios en la informaci�n, debe de guardar antes de Asignar el Segmento de Tienda �Desea continuar?\" junto con los botones de Aceptar y Cancelar");
		boolean appearsmsg = fO.comprobarMensaje_onAlert("Si realiz� cambios en la informaci�n, debe de guardar antes de Asignar el Segmento de Tienda. �Desea continuar?");
		assertTrue(appearsmsg, "No se encontro el mensaje");
		
		// Paso 9
		addStep("Usuario selecciona Aceptar");
		fO.aceptar_onAlert();
		fO.popUpSegmentoTienda();
		
		// Paso 10
		addStep("Usuario selecciona el nuevo valor del Segmento Maestro.");
		fO.popUpSegmentoTienda();
		fO.SetearElementosNvaPantallaConceptoDiferenciador(segmentoMaestro,potencialHogar, potencialComida, potencialDesayuno,potencialPba,overlayHogar,overlayComida,overlayDesayuno,overlayPba);
		
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
		return "ATC_FT_311_Modificar_a_un_nuevo_Segmento_Maestro_y_Necesidad_a_una_Tienda_con_estatus_Cancelada";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@AfterMethod
	public void afterMethod() {
		//u.getDriver().quit();
	}

}