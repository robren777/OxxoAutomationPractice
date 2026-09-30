package WEB.inventarioEspacios.scripts.AsociacionPopPlaza;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.AsociarPopPlazasIEP;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * Desc: Script para editar la asociacion de un POP a una o varias plazas
 * 
 * 6705 - MTC-FT-279-Editar asociaci�n de POPs a plazas_Admin MARCA
 * 6706 - MTC-FT-280-Editar asociaci�n de POPs a plazas_Admin BARA
 * 6707 - MTC-FT-282-Editar asociaci�n de POPs a plazas_Admin OXXO
 * 
 * @author Andrea Contreras
 * @date Mayo 2022
 */
public class ATC_FT_004_Editar_Asociacion_POP_a_Plazas extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_004_Editar_Asociacion_POP_a_Plazas_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIASQA = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		///COMENTARIO
		/*
		 * Variables
		 ******************************************************************************************/		
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		String tipoPop = data.get("TipoPop");
	    String pop = data.get("Pop"); 
	    String editarPop = data.get("EditarPop"); 
	    String accion = data.get("Accion");
		
		testCase.setTest_Description("Editar Asociacion de POPs a Plazas - "+tipoPop+" - "+perfil+" "+negocio);
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal");
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		AsociarPopPlazasIEP app = new AsociarPopPlazasIEP(u, testCase);
		
		u.getDriver().manage().window().maximize();
		u.get(pa.getUrl());
		
		pa.proceedToPortal();
		u.waitForLoadPage();
		pa.cicloLogin(user, password);
		
		/**
		 *                                             PASO 2
		 ******************************************************************************************/
		 
		addStep("Seleccionar Inventario de Espacios");
		
		pa.clickPortalInventarioEspacios();
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
		
		String db = "";
		switch(negocio.toUpperCase()) {
			case "BARA": 
				db = "XXINE_BA";
			break;
			
			default: db = "XXINE";
			break;
		
		}
		
		String query = "select XXAC_USERNAME from " + db + ".xxine_users_empleados_v where XXAC_USER_ID = '" + user + "'";
		SQLResult ExecuteQuery = executeQuery(dbFCIASQA, query);
		String nameUser = ExecuteQuery.getData(0, "XXAC_USERNAME");
		
		boolean validacionInfo = mp.verificarInformacion(nameUser, negocio, perfil);
			
		if (validacionInfo) {
			testCase.addTextEvidenceCurrentStep("Se muestra la informacion del usuario, negocio y perfil");
		}else {
			testCase.addTextEvidenceCurrentStep("La informacion del usuario y/o negocio y/o perfil no son correctas");
		}
		
		assertTrue(validacionInfo, "No se muestra la informaci�n esperada");
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Validar que visualice los MENUS:");
		
		mp.clickMenus();
		switch(perfil.toUpperCase()) {
			case "ADMINISTRADOR": 
				if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
					mpo.menuAdministrador();
				} else if (negocio.toUpperCase().equals("MARCA")) {
					mpo.menuAdministradorM();
				}
				
			break;
			
			case "PLAZA": mpo.menuPlaza();
			break;
			
			case "ESTAFETA": mpo.menuEstafeta();
			break;
		}
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep(" Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		
		boolean verEncabezado = mp.verificarEncabezado(negocio);
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		assertTrue(verEncabezado, "No se muestra la informaci�n esperada");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar Menu-> Adminsitracion --> Asociacion de POPs");
	    //mp.clickMenus();
		mp.clickAdministracion();
		mp.clickAsociarPopPlazaOp();
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("Se visualiza la pantalla de Asociacion de POPs y los campos: Tipo de POP, POP, Buscar plazas por nombre, Plazas filtradas");
		
		boolean existenCampos = app.visualizarCampos();
         
		assertTrue(existenCampos, "No se visualizan todos los campos en la pantalla: Tipo de POP, POP, Plazas por nombre, Plazas filtradas");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("Seleccionar en <Tipo_POP>, Seleccionar POP");

		
		app.selectTipoPop(tipoPop);	
		app.selectPop(pop);	
		
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Al tener seleccionado Tipo de POP y POP se actualiza en automatico lel grid 'Consulta de POPs Asociados' y los paneles: "
				+ "Plazas filtradas: Se muestran las plazas que aun no han sido asociadas al pop seleccionado."
				+ "Plazas asociadas: Se muestran las plazas que ya se encuentran asociadas para el po seleccionado.");
		
		boolean validacion = app.validarGridPaneles();
		assertTrue(validacion, "No se actualizo el Grid y/o no se muestran las plazas de 'Plazas filtradas'");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/

		addStep("Seleccionar del grid 'Consulta de POPs Asociados' un registro y dar clic en el boton editar.");

		app.clickEditarPop(editarPop);
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/

		addStep("El actualizan los datos de busqueda y mostra las asociaciones ya existentes para el POP que se selecciono con el boton editar.");		
		
		boolean validarActDatos = app.validarActualizacionDatos(editarPop);

		assertTrue(validarActDatos, "No se actualizaron los datosde busqueda");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/

		addStep("El usuario agrega o quita asociaciones y da clic en Guardar.");
		
		String tdcQueryPop = "SELECT POP_ID, NOMBRE_POP FROM "+db+".XXINE_CAT_POPS" + 
				" WHERE NOMBRE_POP = '%s' ORDER BY CREATION_DATE DESC";
		
		System.out.println(tdcQueryPop);
		
		String queryPop = String.format(tdcQueryPop, editarPop);
		SQLResult queryPopResult = executeQuery(dbFCIASQA, queryPop);	
		String popId, queryPlazaPop = null; 
		SQLResult queryPlazaPopResult = null;
		int rowsInicio = 0;	
		
		String tdcQueryPlazaPop = "select PLAZA_ID, POP_ID, TIPO_POP_ID, LAST_UPDATE_DATE, LAST_UPDATED_BY from "+db+".XXINE_PLAZA_POP" + 
				" where POP_ID = %s";
		
		String tdcQueryPlazaPop1 = "select PLAZA_ID, POP_ID, TIPO_POP_ID, LAST_UPDATE_DATE, LAST_UPDATED_BY from "+db+".XXINE_PLAZA_POP" + 
				" where POP_ID = %s" + 
				" and CREATION_DATE >= to_date(Sysdate)" + 
				" order by CREATION_DATE desc";
		
		
		String tdcQueryPlazaPop2 = "select CREATION_DATE, CREATED_BY, CANTIDAD_MINIMA, CANTIDAD_MAXIMA from "+db+".XXINE_PLAZA_POP" + 
				" where POP_ID = %s" + 
				" and CREATION_DATE >= to_date(Sysdate)" + 
				" order by CREATION_DATE desc";
		
		int cont = 0;
		while(cont <  queryPopResult.getRowCount()) {
				
				popId = queryPopResult.getData(cont, "POP_ID");
				
				queryPlazaPop = String.format(tdcQueryPlazaPop, popId);
				queryPlazaPopResult = executeQuery(dbFCIASQA, queryPlazaPop);
				rowsInicio = queryPlazaPopResult.getRowCount();
				if(!queryPlazaPopResult.isEmpty()) {
					break;
				}
				
			}		
	
		testCase.addQueryEvidenceCurrentStep(queryPlazaPopResult);
		

		ArrayList <String> valores = new ArrayList<String>();
		
		for(Map.Entry<String, String> entry : data.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();	    
		    if (key.contains("PlazasFiltradas:") && value.trim().length() > 0) {	
		    	valores.add(value);
			}
		    
		}
		
		app.editarPopsPlazas(valores, accion);
		app.clickGuardar();
		
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/

		addStep("El sistema muestra mensaje: �Esta seguro de guardar los cambios realizados?");
		
		boolean validarPopUp = app.validarPopUpConfirmarCambios();
		
		assertTrue(validarPopUp, "No se muestra el mensaje �Esta seguro de guardar los cambios realizados?");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/

		addStep("Clic al boton 'Confirmar'");
		
		app.clickBtnConfirmarPopUpGuardar();
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/

		addStep("Se muestra el mensaje 'Se ha asociado plazas a POP exitosamente' y se visualiza en el grid de 'Consulta de POPs asociados'.");
		
		boolean validarMsg = app.validarMsgPlazaPopAsoc();
		//int numPlFin = app.obtenerNumeroPlazasActual(editarPop);
        
		assertTrue(validarMsg, "No se muestra el mensaje 'Se ha asociado plazas a POP exitosamente'");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/

		addStep("Ingresar a la BD XXINE y validar que se guarde la asociacion en la tabla XXINE_PLAZA_POP");
		
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		System.out.println(queryPlazaPop);
			
		SQLResult queryPlazaPopResult2 = executeQuery(dbFCIASQA, queryPlazaPop);
		
		int rowsFin = queryPlazaPopResult2.getRowCount();
		
		boolean validarBD = false;

		if(accion.toUpperCase().equals("AGREGAR")) {
			
			validarBD = rowsInicio+valores.size()==rowsFin;			
			testCase.addTextEvidenceCurrentStep("Se agregaron las asociaciones a las plazas");
		}
		
        if(accion.toUpperCase().equals("ELIMINAR")) {
        	
        	validarBD = rowsInicio-valores.size()==rowsFin;
        	testCase.addTextEvidenceCurrentStep("Se eliminaron las asociaciones a las plazas");
		}


		testCase.addQueryEvidenceCurrentStep(queryPlazaPopResult);

		assertTrue(validarBD, "No se guardaron los cambios de la asociacion en la tabla XXINE_PLAZA_POP");
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_Editar_Asociacion_POP_a_Plazas_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}
}
