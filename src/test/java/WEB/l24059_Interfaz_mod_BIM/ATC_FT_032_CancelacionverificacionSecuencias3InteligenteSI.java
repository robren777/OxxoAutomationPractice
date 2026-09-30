package WEB.l24059_Interfaz_mod_BIM;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.util.GlobalVariables;
import WEB.util.PortalEquipamiento.PomPortalEquipamiento;
import WEB.util.PortalEquipamiento.PomPortalEquipamientoBandejaAprobador;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/**
* DESCRIPCION: Verificar que el usuario con ROL PLANEACION_CF pueda realizar la accion de CANCELAR
* y que en la tabla "Xxex_Niveles_Aprobacion" se muestre la secuencia con estatus CANCELADO
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
* - MTC-FT-032-Cancelacion de un equipo y verificaci�n Secuencias de 3, Tienda Inteligente(SI)
* 
* PREREQUISITOS: 
* - Existan registros en la pantalla "WF Controlador CF Bandeja Aprobador" que se encuentren en la secuencia 3 para aplicar la accion de CANCELADO
* - En el DataProvider ingresar en la columna IdLE el valor a consultar
* 
* @author elberth.ruiz@grupotimex.onmicrosoft.com
*
* @date 05/Mayo/2025
* 
* INSUMOS
---------------------------------------------------------------------------------
Secuencia	| ROL			| Usuario											|
---------------------------------------------------------------------------------
1			| PLANEACION_CF	|	pruebaswf02@gmail.com							|
1 			| PLANEACION_CF	|	6006068											|
2 			| PLAZA_CF		|	pruebaswf01@gmail.com (Quien creo el listado)	|
3 			| PLANEACION_CF	|	pruebaswf02@gmail.com							|
3 			| PLANEACION_CF	|	6006068											|
4 			| AUTO_CTRL_CF	|	pruebaswf03@gmail.com							|
---------------------------------------------------------------------------------
* Nota: donde se muestren repetidos las secuencias quiere decir que pueden utilizar cualquiera de los usuarios
* Revisar en la BD de FCIAS la tabla Xxex_Aprobador con el esquema XXEX para ver la configuracion de los usuarios 
*/
@AzureTestInfo(
		areaPath = "I24059-interfaz mod BIM con listado de equip y matriz uni",
		iterationPath = "I24059-interfaz mod BIM con listado de equip y matriz uni\\Sprint 2",
		tags = "I24059_Interfaz_mod_BIM;AutomationMPV2",
		userStoryId = 139345
)
public class ATC_FT_032_CancelacionverificacionSecuencias3InteligenteSI extends BaseExecution{
	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	
	 public void ATC_FT_032_CancelacionverificacionSecuencias3InteligenteSI_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("FrameworkWeb --> Portal de equipamiento V2");
		
		//*******************************************************************************************
		
		SQLResult queryResult;
		String Password = PasswordUtil.decryptPassword(data.get("ps"));
		String Menu1 = data.get("MenuN1");
		String Menu2 = data.get("MenuN2");
		String IdLE = data.get("IdLE");
		String Aprobacion = data.get("Aprobacion");
		String Comentarios = data.get("Comentarios");
		
		 //Conexiones a BD
		
		//FCIAS***
		utils.sql.SQLUtil dbFCIAS_XXEX = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQA_QAVIEW,GlobalVariables.DB_USER_FCIASQA_QAVIEW, GlobalVariables.DB_PASSWORD_FCIASQA_QAVIEW);
 	
		// Querys
		//Se valida la Tabla XXEX_APROB_REGION donde se registra la relacion Aprobador-Region
		 String QUERY_NIV_APRO_1 = "SELECT ID_ASIGNACION, TIPO_ASIGNACION, SECUENCIA,ID_APROBADOR, ESTATUS_APROBACION FROM XXEX.Xxex_Niveles_Aprobacion WHERE \r\n"
		 		+ "Id_Asignacion IN (SELECT Id_Asignacion FROM XXEX.Xxex_Alta_Cf WHERE \r\n"
		 		+ "ID_LE IN (SELECT ID_LE FROM XXEX.XXEX_LE_HEADER \r\n"
		 		+ "WHERE ID_LE = '"+IdLE+"'))";
		
		 String QUERY_NIV_APRO_2 = "SELECT ID_ASIGNACION, OBSERVACIONES, LAST_UPDATE_DATE, LAST_UPDATED_BY, CREATION_DATE, CREATED_BY FROM XXEX.Xxex_Niveles_Aprobacion WHERE \r\n"
		 		+ "Id_Asignacion IN (SELECT Id_Asignacion FROM XXEX.Xxex_Alta_Cf WHERE \r\n"
		 		+ "ID_LE IN (SELECT ID_LE FROM XXEX.XXEX_LE_HEADER \r\n"
		 		+ "WHERE ID_LE = '"+IdLE+"'))";
		 
		u = new SeleniumUtil(new MEdgeTest(),true);
		
		PomPortalEquipamiento pomPortalEquipamiento = new PomPortalEquipamiento(u, testCase);
		PomPortalEquipamientoBandejaAprobador pomPortalEquipamientoBA = new PomPortalEquipamientoBandejaAprobador(u, testCase);
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
		addStep("Damos clic en el menu (barras horizontales) >> \"Articulos especiales\">>\"WF Controlador CF Bandeja Aprobador\"");
		pomPortalEquipamientoBA.menuApp(Menu1,Menu2);
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Damos clic en el boton \"Cancela \" en uno de los insumos que se muestra en el grid que tenga la secuencia \"3\"");
		pomPortalEquipamientoBA.BandejaAprob(IdLE);
		
		addStep("Ingresamos el mensaje \"Ingenieria no autorizada\" y damos clic en el boton Guardar");
		pomPortalEquipamientoBA.BttnAprobRechCan(IdLE,Aprobacion,Comentarios);
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Realizar la consulta en la tabla Xxex_Niveles_Aprobacion para ver el estatus del registro "+IdLE+"");

			queryResult = executeQuery(dbFCIAS_XXEX, QUERY_NIV_APRO_1);
			testCase.addQueryEvidenceCurrentStep(queryResult);
			Boolean validacion = false;
			if (queryResult.getRowCount() > 0) 
				validacion = true;
			assertTrue(validacion, "No se encontraro el codigo");
			
			queryResult = executeQuery(dbFCIAS_XXEX, QUERY_NIV_APRO_2);
			testCase.addQueryEvidenceCurrentStep(queryResult);
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
		return "Existan registros en la pantalla \"WF Controlador CF Bandeja Aprobador\" que se encuentren en la secuencia 3 para aplicar la accion de CANCELADO";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verificar que el usuario con ROL PLANEACION_CF pueda realizar la accion de CANCELAR.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Elberth Humberto Ruiz Cota";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_032_CancelacionverificacionSecuencias3InteligenteSI_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
