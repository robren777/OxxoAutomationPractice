package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_327_Modificar_a_un_nuevo_Segmento_Maestro_y_Necesidad_a_una_Tienda_con_estatus_Cerrada extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_327_Modificar_a_un_nuevo_Segmento_Maestro_y_Necesidad_a_una_Tienda_con_estatus_Cerrada_test(HashMap<String, String> data) throws SQLException {
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
		String id_tienda = data.get("ID_TIENDA");
		String query1 = "select * from TIENDAS.T_FORMATO_SEGMENTO where ID_TIENDA = '"+id_tienda+"'";
	
		
		// Paso 1
		addStep("Ingresar a la <LigaCNT> del portal de aplicaciones.");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();	
		Login pa = new Login(u, testCase);
		u.get(URL);
		testCase.addScreenShotCurrentStep("Imagen de login");
		u.hardWait(2);
		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");
		
		// Paso 2
		addStep("Ingresar el usuario y contrase�a para despu�s seleccionar el bot�n de Continuar");
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
		addStep("El usuario selecciona una tienda con estatus \"Cerrada\" que corresponda a Oxxo M�xico y da clic en el bot�n de Continuar.");
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
		addStep("Usuario selecciona una opci�n del combo de �Concepto Diferenciador� que solicite la captura de los datos de Segmento de Tienda.");
		fO.Diferenciador(diferenciador);
		
		boolean validarActivacionCampos = fO.validarActivacionCampos();
		System.out.print(validarActivacionCampos);
		boolean validarTextoCampos = fO.ValidarCambioCampoSegmentoTiendaNA();	
		System.out.print(validarTextoCampos);
		testCase.addScreenShotCurrentStep(u, "Paso6");
		assertFalse(!validarActivacionCampos && validarTextoCampos, "El sistema no habilita los campos o carga en automatico el valor NA");			
	
		
		// Paso 7
		addStep("Usuario selecciona el bot�n de \"Asignar\" de la opci�n de Segmento de Tienda.");
		boolean validarBtn = fO.validarBtnAsignarActivo();
		assertTrue(validarBtn, "El sistema no habilita el boton Asignar");					
		fO.click_on_Asignar();
		
		// Paso 8
		addStep("Sistema mostrara el mensaje de \"Si realizo Cambios en la informaci�n, debe de guardar antes de Asignar el Segmento de Tienda �Desea continuar?\" junto con los botones de Aceptar y Cancelar");
		boolean appearsmsg = fO.comprobarMensaje_onAlert("Si realiz� cambios en la informaci�n, debe de guardar antes de Asignar el Segmento de Tienda. �Desea continuar?");
		assertTrue(appearsmsg, "No se encontro el mensaje");
		
		// Paso 9
		addStep("Usuario selecciona Aceptar");
		fO.aceptar_onAlert();
		u.hardWait(3);
		fO.popUpSegmentoTienda();
		
		// Paso 10
		addStep("Sistema muestra el pop-up de Segmento de Tienda con los campos requeridos de:\r\n"
				+ "    -Segmento Maestro\r\n"
				+ "	-Potencial_Hogar\r\n"
				+ "	-Potencial_Comida\r\n"
				+ "	-Potencial_Desayuno\r\n"
				+ "	-Overlay_Hogar\r\n"
				+ "	-Overlay_Comida\r\n"
				+ "	-Overlay_Desayuno\r\n"
				+ "    Con los botones de Aceptar y Cancelar");
		u.hardWait(3);
		boolean validarPopUp = fO.validarPopUpSegmentoTienda();
		assertTrue(validarPopUp, "No se muestra el pop up Segmento de Tienda");
		
		boolean validarEPopUp = fO.validarElementosPopUpSegmentoTienda();	
		assertTrue(validarEPopUp, "No se muestran los campos del pop up Segmento de Tienda");
		
		// Paso 11
		addStep("Usuario selecciona el nuevo valor del Segmento Maestro.");
		fO.SetearElementosNvaPantallaConceptoDiferenciadorConNull(segmentoMaestro,potencialHogar, potencialComida, potencialDesayuno,potencialPba,overlayHogar,overlayComida,overlayDesayuno,overlayPba);
       
		u.hardWait(2);
		//boolean validarSeleccion = fO.getElementosPantallaConceptoDiferenciador(segmentoMaestro,potencialHogar, potencialComida, potencialDesayuno,potencialPba,overlayHogar,overlayComida,overlayDesayuno,overlayPba);
		//assertTrue(validarSeleccion, "No se seleccionaron las opciones indicadas");
		boolean validarNom = fO.verificadorDeNomenclaturaUltimoPotencial(potencialPba);
		assertTrue(validarNom, "No se respeta la nomenclatura");
		
		
		System.out.print(query1);
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query1);
		boolean StatusQuery = !ExecuteQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);		
		assertTrue(StatusQuery, "No se ha encontrado ningun resultado en la consulta de la tabla");
	    
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
		//u.getDriver().quit();
	}

}