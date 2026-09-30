package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.cnt.om.Planogramacion;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_247_Asignar_el_Segmento_a_una_Tienda_con_estatus_Nueva_al_relacionar_un_Concepto_diferenciador_de_Dark_Store extends BaseExecution{
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_247_Asignar_el_Segmento_a_una_Tienda_con_estatus_Nueva_al_relacionar_un_Concepto_diferenciador_de_Dark_Store_test(HashMap<String, String> data)
			throws Exception{
		
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCDW2QA_PORTAL, GlobalVariables.DB_USER_FCDW2QA_PORTAL,
				GlobalVariables.DB_PASSWORD_FCDW2QA_PORTAL);
		

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
		String id_tienda = data.get("ID_Tienda");
		String query = "select ID_TIENDA,NOMBRE, CR_SUPERIOR, CR_TIENDA, STATUS from tiendas.t_tiendas where id_tienda = '"+id_tienda+"'";
		
		/*
		 * PASO 1
		 *****************************************************************************************/
		addStep("Ingresar a la http://10.184.32.40:8000/tiendas/html/login.html ");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();	
		Login pa = new Login(u, testCase);
		u.get(data.get("Url"));
		testCase.addScreenShotCurrentStep("Imagen de login");
		u.hardWait(2);
		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");
		
		/*
		 * PASO 2
		 *****************************************************************************************/
		
		addStep("Ingresar el usuario �"+ user + "� y contrase�a para despu�s seleccionar el bot�n de Continuar");
		pa.logginInventarioEspacios(user, password);
		u.waitForLoadPage();
		u.hardWait(2);
		boolean validarPlaza = pa.validarComboBoton();
		assertTrue(validarPlaza, "No se muestran el combo Plaza y boton Continuar");
		
		/*
		 * PASO 3
		 *****************************************************************************************/
		addStep("Seleccionar la Plaza y dar clic en el bot�n Continuar");
		pa.clickSeleccionarPlaza(plazaE);
		u.waitForLoadPage();
		u.hardWait(1);
		boolean validarDT = pa.validarDistritoTiendas();
		assertTrue(validarDT, "No despliegan las opciones de Distrito y Tiendas");
		
		/*
		 * PASO 4
		 *****************************************************************************************/
		addStep("Seleccionar el Distrito y Tienda; a continuaci�n dar clic en el bot�n Continuar");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		u.waitForLoadPage();
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");
		
		/*
		 * PASO 5
		 *****************************************************************************************/
		
		addStep("Usuario selecciona la opci�n de \"Formato y Entorno\".");
		Formato_y_Entorno fO =  new Formato_y_Entorno(u, testCase);
		fO.FormatoyEntorno();
		fO.Framedatos();
		boolean validarFE = fO.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		
		addStep("Usuario selecciona la opci�n de \"Dark Store\" del combo de \"Concepto Diferenciador\".");
		fO.Diferenciador(diferenciador);
		
		//Boolean naValidacion = fO.ElementDisConValidacionDeTexto();
     	Boolean naValidacion = fO.ValidarCambioCampoSegmentoTiendaNA();
		testCase.addScreenShotCurrentStep("valoresCampos");
		
		if(!naValidacion) {		
			testCase.addTextEvidenceCurrentStep("El sistema no cargo en automatico el valor NA en los campos");
		}
		
		/*
		 * PASO 7
		 *****************************************************************************************/
		addStep("Usuario confirma que se haya precargado los valores en los campos de Segmento de Tienda");
		testCase.addScreenShotCurrentStep(u, "Paso7");			
		assertTrue(naValidacion, "No se precargaron los valores");	
		
		/*
		 * PASO 8
		 *****************************************************************************************/
		addStep("Usuario captura los dem�s datos solicitados en la pantalla y selecciona Actualizar. Sistema guarda la informaci�n capturada y cambia el estatus de la tienda de \"Nueva\" a \"Notificada\", se muestra el mensaje de �La informaci�n ha sido actualizada exitosamente");
	
		fO.clickBotonActualizarEntorno();
		u.hardWait(1);
		fO.aceptar_onAlert();
		
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query);
		boolean StatusQuery = !ExecuteQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		assertTrue(StatusQuery, "No se ha encontrado ningun resultado en la busqueda de las tablas");
		

		Planogramacion pl = new Planogramacion(u, testCase);
		pl.Notificacion();
	    boolean noti=    pl.NotificacionAlert();
	    assertTrue(!noti, "Notificacion no existe, se mandaron los datos correctamente");
	    
		ExecuteQuery = executeQuery(dbFCIAS, query);
		StatusQuery = !ExecuteQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		assertTrue(StatusQuery, "No se ha encontrado ningun resultado en la busqueda de las tablas");
		u.hardWait(2);
		String estatusBD = ExecuteQuery.getData(0, "STATUS");
        if(!estatusBD.equals("S_NOTIFICADA")) {
              Assert.fail("No cambio a notificada");
        }	else {
        	
        }
	    
		
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
		return "Asignar el Segmento a una Tienda con estatus Nueva al relacionar un Concepto diferenciador de Dark Store.";
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
