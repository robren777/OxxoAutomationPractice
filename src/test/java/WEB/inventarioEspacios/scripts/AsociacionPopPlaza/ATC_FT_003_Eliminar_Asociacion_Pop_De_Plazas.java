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

public class ATC_FT_003_Eliminar_Asociacion_Pop_De_Plazas extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	/**
	 * Desc: Script para eliminar la asociacion de un pop a una o varias plazas
	 * 
	 * 6696 - MTC-FT-270-Eliminar POP de Plazas_Faltante Adicional_ Admin OXXO
     * 6697 - MTC-FT-271-Eliminar POP de Plazas_Pedidos Genericos_ Admin OXXO
     * 6698 - MTC-FT-272-Eliminar POP de Plazas_Accesorios_ Admin OXXO
     * 6699 - MTC-FT-273-Eliminar POP de Plazas_Andatti_ Admin BARA
     * 6700 - MTC-FT-274-Eliminar POP de Plazas_Faltante Adicional_ Admin BARA
     * 6701 - MTC-FT-275-Eliminar POP de Plazas_Pedidos Genericos_ Admin BARA
     * 6702 - MTC-FT-276-Eliminar POP de Plazas_Accesorios_ Admin BARA
     * 6703 - MTC-FT-277-Eliminar POP de Plazas_Pedidos Genericos_ Admin MARCA
     * 6704 - MTC-FT-278-Eliminar POP de Plazas_Ambientacion de tiendas_ Admin MARCA
	 * 
	 * @author Andrea Contreras
	 * @date Mayo 2022
	 */
	public void ATC_FT_003_Eliminar_Asociacion_Pop_De_Plazas_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		///COMENTARIO
		SQLUtil dbFCIASQA = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);

		/*
		 * Variables
		 ******************************************************************************************/
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		String tipoPop = data.get("TipoPop");
	    String pop = data.get("Pop"); 
	    
	    testCase.setTest_Description("Eliminar Asociacion POP de Plazas - "+tipoPop+" - "+perfil+" "+negocio);
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal con el <Usuario> "+user);
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		
		u.getDriver().manage().window().maximize();
		u.get(url);
		
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		AsociarPopPlazasIEP app = new AsociarPopPlazasIEP(u, testCase);
		
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
		
		addStep(" Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
			
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
		
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		
		boolean verEncabezado = mp.verificarEncabezado(data.get("Negocio"));
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		assertTrue(verEncabezado, "No Muestra la informaci�n correcta");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar Menu->Adminsitracion --> Asociacion de POPs");
	   // mp.clickMenus();
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

		
		boolean existeTipoPop = app.selectTipoPop(tipoPop);
		boolean existePop = app.selectPop(pop);
		
		assertTrue(existeTipoPop && existePop, "No existe la opci�n Tipo Pop '"+data.get("TipoPop")+"' o no existe la opci�n POP '"+data.get("Pop")+"'");
		
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
		
		addStep("Seleccionar la informacion del grid Consulta de POPs asociados al menos 1 registro");
		
		String tdcQueryPop = "SELECT POP_ID, NOMBRE_POP FROM "+db+".XXINE_CAT_POPS" + 
				" WHERE NOMBRE_POP = '%s'";
		
		
		String tdcQueryPlazaPop = "select PLAZA_ID, POP_ID, TIPO_POP_ID, LAST_UPDATE_DATE, LAST_UPDATED_BY from "+db+".XXINE_PLAZA_POP" + 
				" where POP_ID = %s";
		
		String queryPop = String.format(tdcQueryPop, pop);
		SQLResult queryPopResult = executeQuery(dbFCIASQA, queryPop);		
		String popId = queryPopResult.getData(0, "POP_ID");
		
		String queryPlazaPop = String.format(tdcQueryPlazaPop, popId);
		SQLResult queryPlazaPopResult = executeQuery(dbFCIASQA, queryPlazaPop);
		
		
		int rowsInicio = queryPlazaPopResult.getRowCount();
		
		testCase.addQueryEvidenceCurrentStep(queryPlazaPopResult);
		
		
		ArrayList <String> valores = new ArrayList<String>();
		
      
		for(Map.Entry<String, String> entry : data.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();	    
		    if (key.contains("PopsAsociados:") && value.trim().length() > 0) {
		    	valores.add(value);
		    
			}
		    
		}
	
		
		boolean selectPops = app.selectPopsAsociados(valores);
		
		assertTrue(selectPops, "No existe el registro a seleciconar:"+valores.toString());
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("Se selecciona el valor correctamente y se habilita el boton 'Eliminar'");
		
			
		boolean validarVE = app.isEliminarBtnActive();

		assertTrue(validarVE, "No se selecciono la informaci�n del Grid");
	
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("Dar clic al boton 'Eliminar'");
		
		app.clickEliminar();
			
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		addStep("Se muestra el mensaje 'Se eliminara el POP de las plazas seleccionadas �Desea continuar?'");
		
		boolean msgConfElim = app.validarPopUpConfirmarEliminacion();
		
		assertTrue(msgConfElim, "No se muestra el mensaje");
		
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
		addStep("Seleccionar el boton 'Confirmar'");
		
		app.clickBtnConfirmarPopUpEliminar();
		
		/**
		 *                                             PASO 15
		 ******************************************************************************************/
		addStep("Se muestra el mensaje 'La asociaci�n pop/plazas se ha eliminado correctamente'");
		
		boolean validarMsg = app.validarMsgEliminarPlazaPop();
		
		assertTrue(validarMsg, "No se muestra el mensaje");
		
		/**
		 *                                             PASO 16
		 ******************************************************************************************/
		addStep("Ingresar a la BD XXINE y validar que se elimino la asociacion en la tabla XXINE_PLAZA_POP");

		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		
		SQLResult queryPlazaPopResult2 = executeQuery(dbFCIASQA, queryPlazaPop);		
		int rowsFin = queryPlazaPopResult2.getRowCount();
		
		boolean validarBD = false;
        System.out.print("_rows inicio: "+rowsInicio);
        System.out.print("_rows fin: "+rowsFin);
		if (rowsInicio-valores.size() == rowsFin) {			
			validarBD = true;		
		}

		testCase.addQueryEvidenceCurrentStep(queryPlazaPopResult2);

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
		return "ATC_FT_003_Eliminar_Asociacion_Pop_De_Plazas_test";
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
	//	u.getDriver().quit();
	}
}
