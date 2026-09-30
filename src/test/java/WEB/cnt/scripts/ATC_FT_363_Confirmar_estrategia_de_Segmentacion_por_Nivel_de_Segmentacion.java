package WEB.cnt.scripts;

import static org.testng.Assert.assertTrue;

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
import utils.sql.SQLUtil;

public class ATC_FT_363_Confirmar_estrategia_de_Segmentacion_por_Nivel_de_Segmentacion extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_363_Confirmar_estrategia_de_Segmentacion_por_Nivel_de_Segmentacion_Test(HashMap<String, String> data)
			throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,
				GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);

		/*
		 * Variables
		 ******************************************************************************************/
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();	
		Login pa = new Login(u, testCase); 
		Formato_y_Entorno fe = new Formato_y_Entorno(u, testCase);
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String PlazaE = data.get("PlazaE");
		String Distrito = data.get("Distrito");
		String Tienda = data.get("Tienda");
		String URL = data.get("Url");
		Boolean validacion;

		String diferenciador = data.get("Diferenciador");

		String query = " select * from (SELECT ID_PUNTO,CODIGO_POSTAL , ID_MUNICIPIO "
				+ "FROM TIENDAS.XXCNT_BIT_GIS_INFO  " + "WHERE ID_PUNTO NOT IN " + "(SELECT T_TIENDAS.ID_PUNTO "
				+ "FROM T_TIENDAS "
				+ "WHERE T_TIENDAS.CR_SUPERIOR ='CRPLAZA' AND ID_PUNTO IS NOT NULL))where rownum = 1";
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/

		addStep("Ingresar a la " + URL + "del portal de aplicaciones");
		
		u.get(data.get("Url")); 
		u.hardWait(3); 
		testCase.addScreenShotCurrentStep(u, "pantallaLogin");
		u.hardWait(2);

		/**
		 *                                             PASO 2
		 ******************************************************************************************/

		addStep("Aparece la pantalla de Loggin");

		testCase.addScreenShotCurrentStep(u, "pantallaLogin");

		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");

		/**
		 *                                             PASO 3
		 ******************************************************************************************/

		addStep("Ingresar el usuario " + user + " y contrase�a " + password + " para despu�s seleccionar "
				+ "el bot�n de Continuar"); 

		pa.logginInventarioEspacios(user, password);
		
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep(" Se abre la aplicaci�n donde se muestra la pantalla de Tiendas-Femsa con la el Combo "
				+ "de Plaza y el bot�n de Continuar");
		u.hardWait(2);
		boolean validarPlaza = pa.validarComboBoton();
		assertTrue(validarPlaza, "No se muestran el combo Plaza y boton Continuar");
		pa.clickSeleccionarPlaza(PlazaE);
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Usuario realiza la b�squeda de una tienda \"Nueva\" que corresponda a Oxxo M�xico "
				+ "y da clic en el bot�n de Continuar.");
		
		pa.clickSeleccionarDistritoTienda(Distrito, Tienda);
		

		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Sistema abre la pantalla con las opciones de Seleccionar Tienda, Datos Generales, "
				+ "Datos de Operaci�n, Formato y Entorno, Planogramaci�n, Condiciones en Propiedad y "
				+ "Sugerencias");
		
		u.hardWait(2);
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		
		fe.FormatoyEntorno();

		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("Sistema muestra la pantalla de Formato y Entorno permitiendo al usuario que se pueda "
				+ "seleccionar los nuevos campos correspondientes a Nueva Segmentaci�n as� como los "
				+ "campos solicitados en el proceso Actual de Segmento correspondiente al combo de "
				+ "\"Segmento al que pertenece\".");
		
		fe.Framedatos();
		boolean validarFE = fe.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");
		
		fe.camposDiferenciadorSegTienda();
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Usuario confirma que se pueda seleccionar cualquier de las opciones correspondientes "
				+ "al proceso de Segmentaci�n Actual y Nuevo.");
		
		fe.Diferenciador(diferenciador);
		fe.ClicBtbSegmentoTienda();
		fe.alertSegmentoTienda();
		validacion = fe.ElementDis();
		assertTrue(validacion, "No se pueden seleccionar los elementos");
		fe.aceptarOnSegmentodeTienda();
		fe.alertaGuardarSegmentoTienda();
		fe.cambiar_ventana();
		fe.Framedatos();
		fe.SegmentoOpciones();

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
		return "ATC_FT_363_Confirmar_estrategia_de_Segmentacion_por_Nivel_de_Segmentacion_test";
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
