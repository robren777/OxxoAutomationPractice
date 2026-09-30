
package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Datos_de_Operacion;
import WEB.cnt.om.Login;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_CNT_Captura_de_Datos_de_Tienda_en_CNT_Datos_de_Operacion extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_004_Captura_de_Datos_de_Tienda_en_CNT_Datos_de_Operacion(HashMap<String, String> data)
			throws Exception {
		/*
		 * Utilerías
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
		String Asesor = data.get("Asesor");

		// ------------------------------------PASO 1

		addStep("Ingresar al portal " + URL + " con el <Usuario>" + user + ""); 
		
		u = new SeleniumUtil(new ChromeTest(), true); 
		Login pa = new Login(u, testCase); 
		u.get(data.get("Url")); 
		u.hardWait(3);

		pa.logginInventarioEspacios(user, password);

//		//------------------------------------PASO 2
		addStep("Seleccionar la Plaza y dar clic en el botón Continuar");
		pa.clickSeleccionarPlaza(PlazaE);
//		//------------------------------------PASO 3
		addStep("Seleccionar el Distrito y Tienda; a continuación dar clic en el botón Continuar");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);


//		//------------------------------------PASO 4
		Datos_de_Operacion po = new Datos_de_Operacion(u, testCase);
		addStep("Dar Click en Link Datos de Operacion"); // PENDIENTE
		po.DatoOp();
//		//------------------------------------PASO 5
		addStep("Seleccionar una opción en el campo Características de operación de la tienda");
	
		po.capturaDatosOp(Asesor);
		po.BANCOMEX();
//		
//		//------------------------------------ PASO 6

		addStep("Dar clic en le boton Actualizar");
		po.clickBotonActualizar();
		boolean msj =po.validacion();
		assertTrue(msj, "No se presento pantalla de cambios guardados");

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_004_Captura_de_Datos_de_Tienda_en_CNT_Datos_de_Operacion";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}
