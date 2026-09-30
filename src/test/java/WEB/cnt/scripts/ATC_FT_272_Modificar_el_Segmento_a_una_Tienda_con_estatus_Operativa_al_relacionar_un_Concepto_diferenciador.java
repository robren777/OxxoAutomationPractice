package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cnt.om.Formato_y_Entorno;
import WEB.cnt.om.Login;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_272_Modificar_el_Segmento_a_una_Tienda_con_estatus_Operativa_al_relacionar_un_Concepto_diferenciador
extends BaseExecution{
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_272_Modificar_el_Segmento_a_una_Tienda_con_estatus_Operativa_al_relacionar_un_Concepto_diferenciador_test
	(HashMap<String, String> data) throws SQLException {
		
		/*
		 * Utilerias
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
		String id_tienda = data.get("ID_Tienda");
		String cr_tienda = data.get("CR_Tienda");
		String diferenciador = data.get("Diferenciador");
		String diferenciadorT = data.get("DiferenciadorTexto");
		String segmentoMaestro = data.get("Segmento Maestro");
		String potencialHogar = data.get("Potencial Hogar");
		String potencialComida = data.get("Potencial Comida");
		String potencialDesayuno = data.get("Potencial Desayuno");
		String potencialPba = data.get("Potencial Pba Nva");
		String overlayHogar = data.get("Overlay Hogar");
		String overlayComida = data.get("Overlay Comida");
		String overlayDesayuno = data.get("Overlay Desayuno");
		String overlayPba = data.get("Overlay Pba Nva");
		String msj1 = data.get("Mensaje1");
		String msj2 = data.get("Mensaje2");
		String msj3 = data.get("Mensaje3");
		
		String query1 = "select * from TIENDAS.T_FORMATO_SEGMENTO where ID_TIENDA='"+id_tienda+"'";
		
		String query2 = "SELECT T.CR_SUPERIOR, T.CR_TIENDA, S.MAESTRO_SEGMENTO_DESC, SD.POTENCIAL_DESC, SD.NECESIDAD_DESARROLLO_DESC, SD.NIVEL_OVERLAY_DESC" + 
				" FROM TIENDAS.T_TIENDAS T, TIENDAS.T_FORMATO_SEGMENTO S, TIENDAS.T_FORMATO_SEGMENTO_DET SD" + 
				" WHERE T.ID_TIENDA = S.ID_TIENDA" + 
				" AND S.ID_TIENDA = SD.ID_TIENDA" + 
				" AND T.CR_TIENDA = '"+cr_tienda+"'";
		
		
		/*
		 * PASO 1
		 *****************************************************************************************/
		addStep("Ingresar a la '" + URL ); 
		
		u = new SeleniumUtil(new ChromeTest(), true);
		Login pa = new Login(u, testCase);
		u.get(data.get("Url"));
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Paso 1");
		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");
	
		
		/*
		 * PASO 2
		 *****************************************************************************************/
		
		addStep("Ingresar el usuario "  + user + " y contrase�a para despu�s seleccionar el bot�n de Continuar");

		pa.logginInventarioEspacios(user, password);
		u.waitForLoadPage();
		u.hardWait(2);
		boolean validarPlaza = pa.validarComboBoton();
		assertTrue(validarPlaza, "No se muestran el combo Plaza y boton Continuar");


		/*
		 * PASO 2
		 *****************************************************************************************/
		addStep("Usuario selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		pa.clickSeleccionarPlaza(plazaE);
		u.waitForLoadPage();
		u.hardWait(1);
		boolean validarDT = pa.validarDistritoTiendas();
		assertTrue(validarDT, "No despliegan las opciones de Distrito y Tiendas");

		/*
		 * PASO 3
		 *****************************************************************************************/
		addStep("El usuario selecciona una tienda con estatus �Operativa� que corresponda a Oxxo M�xico y da clic en el bot�n de Continuar.");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		u.waitForLoadPage();
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");
		
		/*
		 * PASO 4
		 *****************************************************************************************/
		
		addStep("Usuario selecciona la opci�n de \"Formato y Entorno\".");
		Formato_y_Entorno fO =  new Formato_y_Entorno(u, testCase);
		fO.FormatoyEntorno();
		fO.Framedatos();
		boolean validarFE = fO.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");
		
		/*
		 * PASO 5
		 *****************************************************************************************/
		
		addStep("Usuario selecciona la opci�n de "+ diferenciador +" del combo de \"Concepto Diferenciador\".");
		fO.Framedatos();
		fO.Diferenciador(diferenciador);
		
		/*
		 * PASO 6
		 *****************************************************************************************/
		addStep("El sistema habilitara el bot�n de \"Asignar\" y muestra los siguientes campos vac�os solicit�ndolos como requeridos \r\n"
				+ "    -Segmento Maestro\r\n"
				+ "	-Potencial_Hogar\r\n"
				+ "	-Potencial_Comida\r\n"
				+ "	-Potencial_Desayuno\r\n"
				+ "	-Overlay_Hogar\r\n"
				+ "	-Overlay_Comida\r\n"
				+ "	-Overlay_Desayuno");
		
		boolean validarActivacionCampos = fO.validarActivacionCampos();
		System.out.print(validarActivacionCampos);
		
		boolean validarTextoCampos = fO.ValidarCambioCampoSegmentoTiendaNA();
		System.out.print(validarTextoCampos);
		
		testCase.addScreenShotCurrentStep(u, "Paso6");
		assertFalse(!validarActivacionCampos && validarTextoCampos, "El sistema no habilita los campos o carga en automatico el valor NA");			
				
		boolean validarBtn = fO.validarBtnAsignarActivo();
		assertTrue(validarBtn, "El sistema no habilita el boton Asignar");	
		
		/*
		 * PASO 7
		 *****************************************************************************************/
		addStep("Usuario selecciona el bot�n de \"Asignar\" de la opci�n de Segmento de Tienda.");
		
		fO.ClicBtbSegmentoTienda();
		/*
		 * PASO 8
		 *****************************************************************************************/
		
		addStep("Sistema mostrara el mensaje de \"Si realizo Cambios en la informacion, debe de guardar antes de Asignar el Segmento de Tienda �Desea continuar?\" junto con los botones de Aceptar y Cancelar");
		
		boolean appearsmsg = fO.comprobarMensaje_onAlert(msj1);
		assertTrue(appearsmsg, "No se encontro el mensaje");
		
		fO.aceptar_onAlert();
		u.hardWait(2);

		/*
		 * PASO 9
		 *****************************************************************************************/

		addStep("Sistema muestra el pop-up de Segmento de Tienda con los campos requeridos de:\r\n"
				+ "    -Segmento Maestro\r\n"
				+ "	-Potencial_Hogar\r\n"
				+ "	-Potencial_Comida\r\n"
				+ "	-Potencial_Desayuno\r\n"
				+ "	-Overlay_Hogar\r\n"
				+ "	-Overlay_Comida\r\n"
				+ "	-Overlay_Desayuno\r\n"
				+ "    Con los botones de Aceptar y Cancelar");
		
		boolean validarPopUp = fO.validarPopUpSegmentoTienda();
		assertTrue(validarPopUp, "No se muestra el pop up Segmento de Tienda");
		
		boolean validarEPopUp = fO.validarElementosPopUpSegmentoTienda();	
		assertTrue(validarEPopUp, "No se muestran los campos del pop up Segmento de Tienda");
		
		/*
		 * PASO 9
		 *****************************************************************************************/
		
		addStep("Usuario selecciona los valores solicitados por el pop-up.");
		fO.SetearElementosNvaPantallaConceptoDiferenciador(segmentoMaestro,potencialHogar, potencialComida, potencialDesayuno,potencialPba,overlayHogar,overlayComida,overlayDesayuno,overlayPba);
		
		
		/*
		 * PASO 10
		 *****************************************************************************************/
		addStep("Usuario selecciona Aceptar.");
		fO.botonAceptarConceptoDiferenciador();
		
		/*
		 * PASO 11
		 *****************************************************************************************/
		
		addStep("Sistema mostrara el mensaje de \"�Esta seguro de guardar el Segmento de Tienda?\" junto con los botones de Aceptar y Continuar.");
		u.hardWait(2);
		boolean validarAlerta2 = fO.comprobarMensaje_onAlert(msj2);
		assertTrue(validarAlerta2, "No se muestra el mensaje  ��Est� seguro de guardar el Segmento de Tienda?�");
		fO.aceptar_onAlert();
		
		/*
		 * PASO 12
		 *****************************************************************************************/
		
		addStep("Usuario confirma que se haya cargado los valores seleccionados en el pop-up de Segmento de Tienda en los campos correspondientes de Formato y Entorno");
		//Boolean popUp =  fO.getElementosConceptoDiferenciador(segmentoMaestro, potencialHogar, potencialComida, potencialDesayuno, potencialPba, overlayHogar, overlayComida, overlayDesayuno, overlayPba);		
		Boolean popUp =  fO.getElementosConceptoDiferenciador2(segmentoMaestro, potencialHogar, potencialComida, potencialDesayuno, potencialPba, overlayHogar, overlayComida, overlayDesayuno, overlayPba);
		assertTrue(popUp, "los valores NO se guardaron con exito");
		
		/*
		 * PASO 13
		 *****************************************************************************************/
		addStep("Usuario captura los dem�s datos solicitados en la pantalla y selecciona Actualizar");
		fO.clickBotonActualizarEntorno();

		
		/*
		 * PASO 14
		 *****************************************************************************************/
		addStep(" Sistema guarda la informaci�n capturada, se muestra el mensaje de �La informaci�n ha sido actualizada exitosamente\".");
		u.hardWait(2);
		boolean alerta3 = fO.comprobarMensaje_onAlert(msj3);	
		fO.aceptar_onAlert();	
	    assertTrue(alerta3, "No se mostro el muestra el mensaje: La informacion de \"FORMATO Y ENTORNO\" ha sido grabada.");
		
	    u.hardWait(3);
		
		/*
		 * PASO 14
		 *****************************************************************************************/
		addStep("Se revisa que se haya relacionado la relaci�n de los valores de �Segmento Tienda� a la tienda seleccionada.");
		
		System.out.print(query1);
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query1);
		boolean StatusQuery = ExecuteQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		assertFalse(StatusQuery, "No se ha encontrado ningun resultado en la consulta de la tabla");
		
     	if(!StatusQuery) {		
			String diferenciadorBD = ExecuteQuery.getData(0, "CONCEPTO_DIFERENCIADOR");		
	        if(!diferenciadorBD.equals(diferenciador)) {
	              Assert.fail("No tiene el concepto diferenciador "+diferenciador);
	        }		
		}
		
        
        System.out.print(query2);
        SQLResult ExecuteQuery2 = executeQuery(dbFCIAS, query2);
		boolean StatusQuery2 = ExecuteQuery2.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		assertFalse(StatusQuery2, "No se ha encontrado ningun resultado en la busqueda de las tablas");
	
		
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
		return "Validar que al seleccionar el concepto diferenciador para una Tienda con estatus \"Operativa\" permita modificar la captura de los siguientes campos:\r\n"
				+ "-Segmento Maestro\r\n"
				+ "-Potencial_Hogar\r\n"
				+ "-Potencial_Comida\r\n"
				+ "-Potencial_Desayuno\r\n"
				+ "-Overlay_Hogar\r\n"
				+ "-Overlay_Comida\r\n"
				+ "-Overlay_Desayuno";
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
