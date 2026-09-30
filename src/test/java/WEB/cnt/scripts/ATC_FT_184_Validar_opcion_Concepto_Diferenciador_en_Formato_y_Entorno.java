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
public class ATC_FT_184_Validar_opcion_Concepto_Diferenciador_en_Formato_y_Entorno extends BaseExecution{
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_184_Validar_opcion_Concepto_Diferenciador_en_Formato_y_Entorno_test(HashMap<String, String> data)
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
	
		String diferenciador = data.get("Diferenciador");
		String diferenciador2 = data.get("Diferenciador2");
		String diferenciador3 = data.get("Diferenciador3");
		String diferenciador4 = data.get("Diferenciador4");
		String diferenciador5 = data.get("Diferenciador5");
		String diferenciador6 = data.get("Diferenciador6");
		String diferenciador7 = data.get("Diferenciador7");
		String diferenciador8 = data.get("Diferenciador8");
		String diferenciador9 = data.get("Diferenciador9");
		String diferenciador10 = data.get("Diferenciador10");
		String diferenciador11 = data.get("Diferenciador11");
		String diferenciador12 = data.get("Diferenciador12");
	


		// ------------------------------------PASO 1

		addStep("Ingresar a la " + URL ); 
		
		u = new SeleniumUtil(new ChromeTest(), true); // IETest para usar internet explorer
		Login pa = new Login(u, testCase); // crea objeto de locator
		u.get(data.get("Url")); // toma el valor Url del data provider
		u.hardWait(3); // tiempo en segundos
		testCase.addScreenShotCurrentStep("Paso 1");
		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");
		
		
		// ------------------------------------PASO 1

		addStep("Ingresar el usuario �"+ user + "� y contrase�a para despu�s seleccionar el bot�n de Continuar"); 

		pa.logginInventarioEspacios(user, password);
		u.waitForLoadPage();
		u.hardWait(2);
		boolean validarPlaza = pa.validarComboBoton();
		assertTrue(validarPlaza, "No se muestran el combo Plaza y boton Continuar");

		//------------------------------------PASO 2
		addStep("Usuario selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		pa.clickSeleccionarPlaza(PlazaE);
		u.waitForLoadPage();
		u.hardWait(1);
		boolean validarDT = pa.validarDistritoTiendas();
		assertTrue(validarDT, "No despliegan las opciones de Distrito y Tiendas");
		
		//------------------------------------PASO 3
		addStep("El usuario selecciona la tienda y da clic en el bot�n de Continuar.");
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		u.waitForLoadPage();
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");

		//------------------------------------PASO 4
		addStep("Usuario selecciona la opci�n de \"Formato y Entorno\"");
		Formato_y_Entorno fe =  new Formato_y_Entorno(u, testCase);
		fe.FormatoyEntorno();
		fe.Framedatos();
		boolean validarFE = fe.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");

		//------------------------------------PASO 5
		addStep("El sistema muestra la pantalla de Formato y Entorno con sus campos correspondientes y mostrara el nuevo campo Concepto Diferenciador el cual mostrara las opciones de:\r\n" + 
				"	-CAP \r\n" + 
				"	-OHAP\r\n" + 
				"	-Oxxo Super\r\n" + 
				"	-NA\r\n" + 
				"	-Dark Store\r\n" + 
				"	-Restaurantes \r\n" + 
				"	-Digital\r\n" + 
				"	-Pronto\r\n" + 
				"    Mostrando adem�s aquellos registros que se vayan agregando en el Cat�logo de Concepto Diferenciador con Estatus Activo");
		
		//fe.Framedatos();
		
		boolean existeDif = fe.existeConceptoDiferenciador();
		assertTrue(existeDif, "No se muestran el campo Concepto Diferenciador");
		
		fe.Diferenciador(diferenciador);
		fe.Diferenciador(diferenciador2);
		fe.Diferenciador(diferenciador3);
		fe.Diferenciador(diferenciador4);
		fe.Diferenciador(diferenciador5);
		fe.Diferenciador(diferenciador6);
		fe.Diferenciador(diferenciador7);
		fe.Diferenciador(diferenciador8);
		fe.Diferenciador(diferenciador9);
		fe.Diferenciador(diferenciador10);
		fe.Diferenciador(diferenciador11);
		fe.Diferenciador(diferenciador12);
		

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
		return "Se valida que en la pantalla de �Formato y Entorno� se muestre la nueva opci�n requerido de �Concepto Diferenciador� la cual contar� con las opciones de:\r\n" + 
				"-CAP\r\n" + 
				"-OHAP\r\n" + 
				"-Oxxo S�per\r\n" + 
				"-No Aplica\r\n" + 
				"-Dark Store\r\n" + 
				"-Restaurantes\r\n" + 
				"-Digital\r\n" + 
				"-Pronto \r\n" ;
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
