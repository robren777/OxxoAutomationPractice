package WEB.l24059_Interfaz_mod_BIM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.PortalEquipamiento.PomPortalEquipamiento;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
*
* DESCRIPCION: Verificar que se puedan seleccionar aprobadores en el modal "Selecci�n de aprobadores por Regi�n".
* @author elberth.ruiz@grupotimex.onmicrosoft.com
* 
** NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* - MTC-FT-004-Selecci�n de aprobadores en el modal 
* 
* PREREQUISITOS: 
* - En el DataProvider ingresar en las distintas columnas el valor a consultar
* 
* @date 24/Marzo/2025
* 
* INSUMOS
* - N/A
*/
@AzureTestInfo(
		areaPath = "I24059-interfaz mod BIM con listado de equip y matriz uni",
		iterationPath = "I24059-interfaz mod BIM con listado de equip y matriz uni\\Sprint 2",
		tags = "I24059_Interfaz_mod_BIM;AutomationMPV1",
		userStoryId = 139289
)
public class ATC_FT_004_Seleccionaprobadoresenelmodal extends BaseExecution{
	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_004_Seleccionaprobadoresenelmodal_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("FrameworkWeb --> Portal de equipamiento V2");
		
		//*******************************************************************************************
		
		u = new SeleniumUtil(new MEdgeTest(),true);
		String Password = PasswordUtil.decryptPassword(data.get("ps"));
		
		
		PomPortalEquipamiento pomPortalEquipamiento = new PomPortalEquipamiento(u, testCase);
		u.get(pomPortalEquipamiento.getUrl());
		//*******************************************************************************************
		
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
		
		boolean validacion=pomPortalEquipamiento.seleccionarRegion(data.get("regiones"));
		assertTrue(validacion,"Fallo el script");


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
		return "Verificar que se puedan seleccionar aprobadores en el modal \"Selecci�n de aprobadores por Regi�n\".";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Elberth Humberto Ruiz Cota";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_Seleccion_aprobadores_en_el_modal_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
