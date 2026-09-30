package WEB.l24059_Interfaz_mod_BIM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.GlobalVariables;
import WEB.util.PortalEquipamiento.PomPortalEquipamiento;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/**
*
* DESCRIPCION: Verificar que los aprobadores seleccionados se guarden correctamente.
* @author elberth.ruiz@grupotimex.onmicrosoft.com
*
** NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* - MTC-FT-005-Guardar selecci�n de aprobadores
*
* PREREQUISITOS: 
* - En el DataProvider ingresar en las distintas columnas el valor a consultar
* 
* @date 24/Marzo/2025
* 
* INSUMOS
* - Usuarios:
* 6006068
* pruebaswf02@gmail.com
* - Conexion a la BD FCIAS
* 
*/
@AzureTestInfo(
		areaPath = "I24059-interfaz mod BIM con listado de equip y matriz uni",
		iterationPath = "I24059-interfaz mod BIM con listado de equip y matriz uni\\Sprint 2",
		tags = "I24059_Interfaz_mod_BIM;AutomationMPV1",
		userStoryId = 139289
)
public class ATC_FT_005_Guardarselecciondeaprobadores extends BaseExecution{
	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_005_Guardarselecciondeaprobadores_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("FrameworkWeb --> Portal de equipamiento V2");
		
		//*******************************************************************************************
				SQLResult queryResult;
				String userAprobador = data.get("userAprobador");
				String Password = PasswordUtil.decryptPassword(data.get("ps"));
								
				 //Conexiones a BD
				
					//FCIAS***
				 utils.sql.SQLUtil dbFCIAS_XXEX = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQA_QAVIEW,GlobalVariables.DB_USER_FCIASQA_QAVIEW, GlobalVariables.DB_PASSWORD_FCIASQA_QAVIEW);
				 
				// Querys
				//Se valida la Tabla XXEX_APROB_REGION donde se registra la relacion Aprobador-Region
				 String QUERY_DESC_APROB_REGION = "SELECT * FROM XXEX.XXEX_APROB_REGION WHERE APROBADOR = UPPER('"+ userAprobador +"')";

				
				u = new SeleniumUtil(new MEdgeTest(),true);
				
				PomPortalEquipamiento pomPortalEquipamiento = new PomPortalEquipamiento(u, testCase);
				u.get(pomPortalEquipamiento.getUrl());
		//*******************************************************************************************
				
				/**
				 *                                             PASO 1
				 ******************************************************************************************/	
				addStep("Ingresar al aplicativo Portal de equipamiento v2");
				pomPortalEquipamiento.login(data.get("user"), Password);
				
				/**
				 *                                             PASO 2
				 ******************************************************************************************/
				addStep("Damos clic en el aplicativo Expansion >> \"Portal de equipamiento V2\"");
				pomPortalEquipamiento.appPorEquipamientoV2();
				
				/**
				 *                                             PASO 3
				 ******************************************************************************************/
				addStep("Damos clic en el menu (barras horizontales) >> \"Articulos especiales\">>\"Configuracion de aprobadores\"");
				pomPortalEquipamiento.menuApp();
				
				/**
				 *                                             PASO 4
				 ******************************************************************************************/
				addStep("Damos clic en la opcion \"Planeacion cuartos frios\"");
				pomPortalEquipamiento.visorAprobadores();
				
				/**
				 *                                             PASO 5
				 ******************************************************************************************/
				addStep("Dar clic en el boton \"Regiones\"");
				pomPortalEquipamiento.planeacionCuartosFrios(data.get("userAprobador"));
				
				
				boolean validarCorreo = pomPortalEquipamiento.verificarCorreo();
				
				if(validarCorreo)
					testCase.addTextEvidenceCurrentStep("El correo existe");
				else
					testCase.addTextEvidenceCurrentStep("El correo no existe");
				
				assertTrue(validarCorreo, "No Muestra la informaci�n correcta");
				
				pomPortalEquipamiento.clicBtnRegElim(data.get("btnRegionesEliminar"));
				
				/**
				 *                                             PASO 6
				 ******************************************************************************************/
				addStep("Damos clic en algunas filas disponibles que cuenten con el check");
				pomPortalEquipamiento.seleccionarRegion(data.get("regiones"));
				
				/**
				 *                                             PASO 7
				 ******************************************************************************************/
				addStep("Damos clic en el boton \"Guardar\"");
				pomPortalEquipamiento.accionM(data.get("accionModal"));
				
				/**
				 *                                             PASO 8
				 ******************************************************************************************/
				addStep("Realizar la consulta en la tabla XXEX_APROB_REGION para ver la relacion Aprobador-Region");

					queryResult = executeQuery(dbFCIAS_XXEX, QUERY_DESC_APROB_REGION);
					testCase.addQueryEvidenceCurrentStep(queryResult);
					Boolean validacion = false;
					if (queryResult.getRowCount() > 0) 
						validacion = true;
					assertTrue(validacion, "No se encontraro el codigo");


				
		}
			
	@AfterMethod
	public void afterMethod() {
	u.getDriver().quit();
		
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "En el DataProvider ingresar en las distintas columnas el valor a consultar";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verificar que los aprobadores seleccionados se guarden correctamente.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Elberth Humberto Ruiz Cota";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_005_Guardarselecciondeaprobadores_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
