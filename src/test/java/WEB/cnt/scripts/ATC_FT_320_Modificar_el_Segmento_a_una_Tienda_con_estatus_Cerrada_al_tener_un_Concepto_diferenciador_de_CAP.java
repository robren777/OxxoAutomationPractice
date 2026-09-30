package WEB.cnt.scripts;

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
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_320_Modificar_el_Segmento_a_una_Tienda_con_estatus_Cerrada_al_tener_un_Concepto_diferenciador_de_CAP extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_320_Modificar_el_Segmento_a_una_Tienda_con_estatus_Cerrada_al_tener_un_Concepto_diferenciador_de_CAP_test(HashMap<String, String> data) throws SQLException {
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
		String id_tienda = data.get("ID_Tienda");
		String cr_tienda = data.get("CR_Tienda");
		String msjAlerta1 = data.get("MensajeAlerta1");
		String msjAlerta2 = data.get("MensajeAlerta2");
		String msjAlerta3 = data.get("MensajeAlerta3");
		String query1 = "select * from TIENDAS.T_FORMATO_SEGMENTO where ID_TIENDA='"+id_tienda+"'";
		String query2 = "SELECT T.CR_SUPERIOR, T.CR_TIENDA, S.MAESTRO_SEGMENTO_DESC, SD.POTENCIAL_DESC, SD.NECESIDAD_DESARROLLO_DESC, SD.NIVEL_OVERLAY_DESC" + 
				" FROM TIENDAS.T_TIENDAS T, TIENDAS.T_FORMATO_SEGMENTO S, TIENDAS.T_FORMATO_SEGMENTO_DET SD" + 
				" WHERE T.ID_TIENDA = S.ID_TIENDA" + 
				" AND S.ID_TIENDA = SD.ID_TIENDA" + 
				" AND T.CR_TIENDA = '"+cr_tienda+"'";

		// Paso 1
		addStep("Ingresar a la http://10.184.32.40:8000/tiendas/html/login.html ");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: Aparece la pantalla de Login ");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();	
		Login pa = new Login(u, testCase);
		u.get(URL);
		testCase.addScreenShotCurrentStep("Paso 1");
		u.hardWait(2);
		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");
	
		
		// Paso 2
		addStep("Ingresar el usuario y contrase�a para despu�s seleccionar el bot�n de Continuar");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: Se abre la aplicaci�n donde se muestra la pantalla de Tiendas-Femsa con la el Combo de Plaza y el bot�n de Continuar");
		pa.logginInventarioEspacios(user, password);
		u.waitForLoadPage();
		u.hardWait(2);
		boolean validarPlaza = pa.validarComboBoton();
		assertTrue(validarPlaza, "No se muestran el combo Plaza y boton Continuar");
		
		// Paso 3
		addStep("Usuario selecciona la Plaza con la cual trabajara y selecciona el bot�n de Continuar");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: El sistema desplegara las opciones de Distrito y Tiendas que est�n relacionadas con la Plaza seleccionada por el usuario.");
		pa.clickSeleccionarPlaza(plazaE);
		u.waitForLoadPage();
		u.hardWait(1);
		boolean validarDT = pa.validarDistritoTiendas();
		assertTrue(validarDT, "No despliegan las opciones de Distrito y Tiendas");

		
		// Paso 4
		addStep("El usuario selecciona una tienda con estatus \"Cerrada\" que corresponda a Oxxo M�xico y da clic en el bot�n de Continuar.");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: Sistema abre la pantalla con las opciones de Seleccionar Tienda, Datos Generales, Datos de Operaci�n, Formato y Entorno, Planogramaci�n, Condiciones en Propiedad y Sugerencias");	
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		u.waitForLoadPage();
		u.hardWait(2);
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");
		
		
		// Paso 5
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: El sistema muestra la pantalla de \"Formato y Entorno\" con sus campos correspondientes ");
		Formato_y_Entorno fO =  new Formato_y_Entorno(u, testCase);
		fO.FormatoyEntorno();
		fO.Framedatos();
		boolean validarFE = fO.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");
	
		
		// Paso 6
		addStep("Usuario selecciona la opci�n de \"CAP\" del combo de \"Concepto Diferenciador\".");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: El sistema habilitar� el bot�n de �Asignar� y muestra los siguientes campos vac�os solicit�ndolos como requeridos: \r\n" + 
				"-Segmento Maestro\r\n" + 
				"-Potencial_Hogar\r\n" + 
				"-Potencial_Comida\r\n" + 
				"-Potencial_Desayuno\r\n" + 
				"-Overlay_Hogar\r\n" + 
				"-Overlay_Comida\r\n" + 
				"-Overlay_Desayuno");
		fO.Diferenciador(diferenciador);
		testCase.addScreenShotCurrentStep("imagen");
		boolean validarAsignar = fO.validarBtnAsignarActivo();		
		testCase.addScreenShotCurrentStep("valoresCampos");
		assertTrue(validarAsignar, "El sistema no habilita el boton Asignar");
		
		
		// Paso 7
		addStep("Usuario selecciona el bot�n de \"Asignar\" de la opci�n de Segmento de Tienda.");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: Sistema mostrar� el mensaje de �Si realizo cambios en la informaci�n, debe de guardar antes de Asignar el Segmento de Tienda �Desea continuar? Junto con los botones de Aceptar y Cancelar.");		
		fO.ClicBtbSegmentoTienda();
		u.hardWait(2);
		boolean validarAlerta1 = fO.comprobarMensaje_onAlert(msjAlerta1);
		assertTrue(validarAlerta1, "No se muestra el mensaje 'Si realizo cambios en la informaci�n, debe de guardar antes de Asignar el Segmento de Tienda �Desea continuar?'");
		
		// Paso 8
		addStep("Usuario selecciona Aceptar");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: Sistema muestra el pop-up de Segmento de Tienda con los campos requeridos de: \r\n" + 
				"-Segmento Maestro\r\n" + 
				"-Potencial_Hogar\r\n" + 
				"-Potencial_Comida\r\n" + 
				"-Potencial_Desayuno\r\n" + 
				"-Overlay_Hogar\r\n" + 
				"-Overlay_Comida\r\n" + 
				"-Overlay_Desayuno\r\n" + 
				"Con los botones de Aceptar y Cancelar");	
		fO.aceptar_onAlert();
		u.hardWait(2);
		boolean validarPopUp = fO.validarPopUpSegmentoTienda();
		assertTrue(validarPopUp, "No se muestra el pop-up de Segmento de Tienda");
		
		// Paso 9
		addStep("Usuario selecciona los valores solicitados por el pop-up y selecciona Aceptar.");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: Sistema mostrar� el mensaje de ��Est� seguro de guardar el Segmento de Tienda?� junto con los botones de Aceptar y Continuar.");	
		fO.SelecDatosSegmentoDiferenciador(segmentoMaestro, potencialHogar, potencialComida, potencialDesayuno, potencialPba, overlayHogar, overlayComida, overlayDesayuno, overlayPba);
        fO.botonAceptarConceptoDiferenciador();
        u.hardWait(2);
		boolean validarAlerta2 = fO.comprobarMensaje_onAlert(msjAlerta2);
		assertTrue(validarAlerta2, "No se muestra el mensaje  ��Est� seguro de guardar el Segmento de Tienda?�");
		
		
		// Paso 10
		addStep("Usuario selecciona Aceptar.");
		testCase.addBoldTextEvidenceCurrentStep("Usuario confirma que se haya cargado los valores seleccionados en el pop-up de Segmento de Tienda en los campos correspondientes de Formato y Entorno");		
		fO.aceptar_onAlert();
		Boolean popUp =  fO.getElementosConceptoDiferenciador2(segmentoMaestro, potencialHogar, potencialComida, potencialDesayuno, potencialPba, overlayHogar, overlayComida, overlayDesayuno, overlayPba);
		assertTrue(popUp, "los valores NO se guardaron con exito");
		
		// Paso 11
		addStep("Usuario captura los dem�s datos solicitados en la pantalla y selecciona Actualizar.");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: Sistema guarda la informaci�n capturada, se muestra el mensaje de �La informaci�n ha sido actualizada exitosamente�.");	
		fO.clickBotonActualizarEntorno();	
		u.hardWait(2);
		boolean alerta3 = fO.comprobarMensaje_onAlert(msjAlerta3);	
		fO.aceptar_onAlert();	
	    assertTrue(alerta3, "No se mostro el muestra el mensaje: La informacion de \"FORMATO Y ENTORNO\" ha sido grabada.");
		
		
		// Paso 12
		addStep("Se revisa que se haya relacionado la relaci�n de los valores de �Segmento Tienda� a la tienda seleccionada.");		
		System.out.print(query1);
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query1);
		boolean StatusQuery = !ExecuteQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		assertTrue(StatusQuery, "No se ha encontrado ningun resultado en la consulta de la tabla");
		
		String diferenciadorBD = ExecuteQuery.getData(0, "CONCEPTO_DIFERENCIADOR");
        if(!diferenciadorBD.equals("CAP")) {
              Assert.fail("No tiene el concepto diferenciador CAP");
        }
        
        System.out.print(query2);
        SQLResult ExecuteQuery2 = executeQuery(dbFCIAS, query2);
		boolean StatusQuery2 = !ExecuteQuery2.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		assertTrue(StatusQuery2, "No se ha encontrado ningun resultado en la busqueda de las tablas");
			
		
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
		return "ATC_FT_320_Modificar_el_Segmento_a_una_Tienda_con_estatus_Cerrada_al_tener_un_Concepto_diferenciador_de_CAP";
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