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

public class ATC_FT_317_Modificar_el_Segmento_a_una_Tienda_con_estatus_Cerrada_al_relacionar_un_Concepto_diferenciador_de_Digital extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_317_Modificar_el_Segmento_a_una_Tienda_con_estatus_Cerrada_al_relacionar_un_Concepto_diferenciador_de_Digital_test(HashMap<String, String> data) throws SQLException {
		/*
		 * Utiler�as
		 ******************************************************************************************/

		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCDW2QA_PORTAL, GlobalVariables.DB_USER_FCDW2QA_PORTAL,
				GlobalVariables.DB_PASSWORD_FCDW2QA_PORTAL);
		
		 /* Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plazaE = data.get("PlazaE");
		String distrito = data.get("Distrito");
		String tienda = data.get("Tienda");
		String diferenciador1 = data.get("Diferenciador1");
		String diferenciador2 = data.get("Diferenciador2");
		String id_tienda = data.get("ID_Tienda");
		String cr_tienda = data.get("CR_Tienda");
		String msjAlerta = data.get("MensajeAlerta");
		String query1 = "select * from TIENDAS.T_FORMATO_SEGMENTO where ID_TIENDA='"+id_tienda+"'";
		String query2 = "SELECT T.CR_SUPERIOR, T.CR_TIENDA, S.MAESTRO_SEGMENTO_DESC, SD.POTENCIAL_DESC, SD.NECESIDAD_DESARROLLO_DESC, SD.NIVEL_OVERLAY_DESC" + 
				" FROM TIENDAS.T_TIENDAS T, TIENDAS.T_FORMATO_SEGMENTO S, TIENDAS.T_FORMATO_SEGMENTO_DET SD" + 
				" WHERE T.ID_TIENDA = S.ID_TIENDA" + 
				" AND S.ID_TIENDA = SD.ID_TIENDA" + 
				" AND T.CR_TIENDA = '"+cr_tienda+"'";

		
		
		// Paso 1
		addStep("Ingresar a la http://10.184.32.40:8000/tiendas/html/login.html");
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
	
		//Paso 4
		addStep("El usuario selecciona una tienda con estatus \"Cerrada\" que corresponde a Oxxo Mexico y da clic en el boton de Continuar.");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: Sistema abre la pantalla con las opciones de Seleccionar Tienda, Datos Generales, Datos de Operaci�n, Formato y Entorno, Planogramaci�n, Condiciones en Propiedad y Sugerencias");	
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		u.waitForLoadPage();
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");
	
		//Paso 5

		addStep("Usuario selecciona la opcion de Formato y Entorno");		
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: El sistema muestra la pantalla de \"Formato y Entorno\" con sus campos correspondientes ");
		Formato_y_Entorno fO =  new Formato_y_Entorno(u, testCase);
		fO.FormatoyEntorno();
		fO.Framedatos();
		boolean validarFE = fO.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");
		

		
		//Paso 6
		addStep("Usuario selecciona la opcion de \"Digital\" del combo de \"Concepto Diferenciador\"");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: El sistema precargar� los siguientes valores en los campos correspondientes al �Segmento de Tienda� los cu�les estar�n inactivados:  \r\n" + 
				"-Segmento Maestro: NA\r\n" + 
				"-Potencial_Hogar: NA\r\n" + 
				"-Potencial_Comida: NA\r\n" + 
				"-Potencial_Desayuno: NA\r\n" + 
				"-Overlay_Hogar: NA\r\n" + 
				"-Overlay_Comida: NA\r\n" + 
				"-Overlay_Desayuno: NA");
		fO.Diferenciador(diferenciador1);

		boolean validarValores = fO.ValidarCambioCampoSegmentoTiendaNA();		
		testCase.addScreenShotCurrentStep("valoresCampos");
		
		if(!validarValores) {		
			testCase.addTextEvidenceCurrentStep("El sistena no cargo en automatico el valor NA en los campos");
		}
				
		//Paso 7
	    addStep("Usuario confirma que se haya precargado los valores en los campos de Segmento de Tienda");			
	    testCase.addScreenShotCurrentStep(u, "Paso 7");			
		assertTrue(validarValores, "No se precargaron los valores");
		
		//Paso 8
		addStep("Usuario captura los demas datos solicitados en la pantalla y selecciona Actualizar");		
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: Sistema guarda la informaci�n capturada, se muestra el mensaje de �La informaci�n ha sido actualizada exitosamente�");		
		fO.clickBotonActualizarEntorno();	
		boolean alerta = fO.comprobarMensaje_onAlert(msjAlerta);	
		fO.aceptar_onAlert();	
	    assertTrue(alerta, "No se mostro el muestra el mensaje: La informacion de \"FORMATO Y ENTORNO\" ha sido grabada.");
		
		
		//Paso 9
		addStep("Se revisa que se haya relacionado la relacion de los valores de \"Segmento Tienda\" a la tienda seleccionada");		
	
		Boolean popUp =  fO.getElementosConceptoDiferenciador2("NA", "NA", "NA", "NA", "NA", "NA", "NA", "NA", "NA");
		assertTrue(popUp, "los valores NO se guardaron con exito");
		
		System.out.print(query1);
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query1);
		boolean StatusQuery = !ExecuteQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		assertTrue(StatusQuery, "No se ha encontrado ningun resultado en la consulta de la tabla");
		
		String diferenciadorBD = ExecuteQuery.getData(0, "CONCEPTO_DIFERENCIADOR");
        if(!diferenciadorBD.equals("Digital")) {
              Assert.fail("No tiene el concepto diferenciador Digital");
        }
        
        System.out.print(query2);
        SQLResult ExecuteQuery2 = executeQuery(dbFCIAS, query2);
		boolean StatusQuery2 = !ExecuteQuery2.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		assertTrue(StatusQuery2, "No se ha encontrado ningun resultado en la busqueda de las tablas");
		
		int cont = 0, sum = 0;
		while(cont < ExecuteQuery2.getRowCount()) {
			if(ExecuteQuery2.getData(cont, 3).equals("NA")) {
				sum++;
			}
			cont++;
		}
		assertTrue(sum == ExecuteQuery2.getRowCount() , "No se relacionaron los valores");
		
		
		// Paso 10
		addStep("Usuario cambia de pantallas para regresar a la pantalla de \"Formato y Entorno\".");
		fO.scrollToFormatoYEntorno();	
		testCase.addScreenShotCurrentStep("imagenFE");


		//Paso 11
		addStep("Usuario intenta cambiar la opcion del Concepto Diferenciador");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: El sistema permitir� modificarse la informaci�n del Segmento de la Tienda.");
		fO.Diferenciador(diferenciador2);
     	u.hardWait(2);
		String difActual = fO.diferenciadorTexto();
		if(difActual.equals(diferenciador2)==false) {
			Assert.fail("No se cambio el concepto diferenciador");
		}else {
			testCase.addTextEvidenceCurrentStep("Se cambio el concepto diferenciador");
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
