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

public class ATC_FT_313_Modificar_el_Segmento_a_una_Tienda_con_estatus_Cerrada_al_tener_un_Concepto_diferenciador_de_Digital extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_313_Modificar_el_Segmento_a_una_Tienda_con_estatus_Cerrada_al_tener_un_Concepto_diferenciador_de_Digital_test(HashMap<String, String> data) throws SQLException {
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
		String id_tienda = data.get("ID_Tienda");
		String query = "select * from TIENDAS.T_FORMATO_SEGMENTO where ID_TIENDA = '"+id_tienda+"'";
		
		// Paso 1
		addStep("Ingresar a la <LigaCNT> del portal de aplicaciones.");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: Aparece la pantalla de Login ");
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();	
		Login pa = new Login(u, testCase);
		u.get(URL);
		testCase.addScreenShotCurrentStep("Imagen de login");
		u.hardWait(2);
		boolean validarAcceso = pa.accesoCorrectoPortal();
		assertTrue(validarAcceso, "No se pudo acceder al portal");
		
		// Paso 2
		addStep("Ingresar el usuario <UsuarioJefe> y contrase�a <PassJefe> para despu�s seleccionar el bot�n de Continuar");
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
		
		// Paso 4
		addStep("El usuario selecciona una tienda con estatus \"Cerrada\" que corresponda a Oxxo M�xico.");
		pa.clickSeleccionarDistritoTienda(distrito, tienda);
		
		// Paso 5
		addStep("Se confirmara que la tienda que se seleccion� cuenta con un Concepto diferenciador de \"Digital\" y da clic en el bot�n de Continuar.");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: Sistema abre la pantalla con las opciones de Seleccionar Tienda, Datos Generales, Datos de Operaci�n, Formato y Entorno, Planogramaci�n, Condiciones en Propiedad y Sugerencias");
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query);
		boolean StatusQuery = !ExecuteQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		assertTrue(StatusQuery, "No se ha encontrado ningun resultado en la busqueda de las tablas");
		
		String diferenciadorBD = ExecuteQuery.getData(0, "CONCEPTO_DIFERENCIADOR");
        if(!diferenciadorBD.equals("Digital")) {
              Assert.fail("No tiene el concepto diferenciador Digital");
        }	

		u.hardWait(2);
		boolean validarPantalla = pa.validarOpcionesVentanaEmergente();
		assertTrue(validarPantalla, "No se muestra la pantalla con las opciones");
		
		// Paso 6
		addStep("Usuario selecciona la opci�n de Formato y Entorno");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: El sistema muestra la pantalla de \"Formato y Entorno\" con sus campos correspondientes ");
		Formato_y_Entorno fO =  new Formato_y_Entorno(u, testCase);
		fO.FormatoyEntorno();
		fO.Framedatos();
		boolean validarFE = fO.validarFormatoyEntorno();
		assertTrue(validarFE, "No se muestra la pantalla de formato y entorno");

		
		// Paso 7
		addStep("Usuario intenta cambiar la opci�n de \"Concepto Diferenciador\" que se tiene seleccionado para la tienda.");
		testCase.addBoldTextEvidenceCurrentStep("Resultado Esperado: El sistema permitir� modificar la informaci�n al ser n concepto diferenciador que corresponde a Excepci�n.");	
		fO.Diferenciador(diferenciador);
		testCase.addScreenShotCurrentStep("imagen");
		u.hardWait(2);
		String difActual = fO.diferenciadorTexto();
		if(!difActual.equals(diferenciador)) {
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
		//u.getDriver().quit();
	}

}