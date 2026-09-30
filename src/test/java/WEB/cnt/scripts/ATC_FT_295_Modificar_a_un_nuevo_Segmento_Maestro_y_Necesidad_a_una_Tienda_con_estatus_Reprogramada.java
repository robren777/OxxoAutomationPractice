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

public class ATC_FT_295_Modificar_a_un_nuevo_Segmento_Maestro_y_Necesidad_a_una_Tienda_con_estatus_Reprogramada
extends BaseExecution{
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_295_Modificar_a_un_nuevo_Segmento_Maestro_y_Necesidad_a_una_Tienda_con_estatus_Reprogramada_test
	(HashMap<String, String> data) {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plazaE = data.get("PlazaE");
		String tienda = data.get("Tienda");
		String distrito = data.get("Distrito");
		String referencia = data.get("Referencia");
		String diferenciador= data.get("Diferenciador");
		String segmentoMaestro = data.get("Segmento Maestro");
		String potencialHogar = data.get("Potencial Hogar");
	
		 /* PASO 1
		 *****************************************************************************************/
		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + "");
		u = new SeleniumUtil(new ChromeTest(), true);
		Login pa = new Login(u, testCase);
		u.get(data.get("Url"));
		u.hardWait(3);
		pa.logginInventarioEspacios(user, password);
		
		/*
		 * PASO 2
		 *****************************************************************************************/
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar");
		pa.clickSeleccionarPlaza(plazaE);
		
		/*
		 * PASO 3
		 *****************************************************************************************/
		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		

		/*
		 * PASO 4
		 *****************************************************************************************/
		
		addStep("Dar clic en el link formato y entorno");
		Formato_y_Entorno fO =  new Formato_y_Entorno(u, testCase);
		fO.FormatoyEntorno();
		
		/*
		 * PASO 5
		 *****************************************************************************************/
		
		addStep("Usuario selecciona la opci�n de " + diferenciador + " del combo de \"Concepto Diferenciador\".");
		fO.Framedatos();
		fO.Diferenciador(diferenciador);
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		addStep("Usuario selecciona el bot�n de \"Asignar\" de la opci�n de Segmento de Tienda.");
		fO.ClicBtbSegmentoTienda();
		
		/*
		 * PASO 7
		 *****************************************************************************************/
		addStep("Usuario selecciona Aceptar.");
		fO.alertSegmentoTienda();
		
		/*
		 * PASO 8
		 *****************************************************************************************/
		addStep("Usuario selecciona el nuevo valor del Segmento Maestro.");
		Boolean segm = fO.elegirSegmentoMaestroPopUp(segmentoMaestro);
		assertTrue(segm, "El segmento maestro no esta disponible");
		
		/*
		 * PASO 9
		 *****************************************************************************************/
		addStep("Usuario confirma que se mostrara los valores "
				+ "del potencial con la siguiente nomenclatura "
				+ "-Nombre de la Necesidad en Desarrollo_Potencial.");
		Boolean pot = fO.verificadorDeNomenclaturaPotencial(potencialHogar);
		assertTrue(pot, "La nomenclatura no se cumple");
		
		
		
		
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
		return "Validar que permita agregar a una tienda \"Reprogramada\" el nuevo Segmento Maestro y nueva Necesidad en Desarrollo dados de alta en los cat�logos.\r\n"
				+ "Los valores del potencial deben tener la siguiente nomenclatura:\r\n"
				+ "-Nombre de la Necesidad en Desarrollo_Potencial";
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
