package WEB.inventarioEspacios.scripts.AsociacionPopPlaza;

import static org.testng.Assert.assertFalse;
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

/**
 * Desc: Script para asociar POPs a plazas y validar su asociacion
 * 
 * MTC-FT-259-Asociacion de POP a Plaza_Andatti_Admin Oxxo
 * MTC-FT-260-Asociacion de POP a Plaza_Faltante-Adicional_Admin Oxxo
 * MTC-FT-262-Asociacion de POP a Plaza_Accesorios_Admin BARA
 * MTC-FT-263-Asociacion de POP a Plaza_Pedidos Genericos_Admin BARA
 * MTC-FT-264-Asociacion de POP a Plaza_Faltante Adicional_Admin BARA
 * MTC-FT-265-Asociacion de POP a Plaza_Ambientacion de tienda_Admin MARCA
 * MTC-FT-257-Asociacion de POP a Plaza_Accesorios_Admin Oxxo
 * MTC-FT-258-Asociacion de POP a Plaza_Genericos_Admin Oxxo
 * MTC-FT-261-Asociacion de POP a Plaza_Andatti_Admin BARA
 * MTC-FT-266-Asociacion de POP a Plaza_Pedidos Genericos_Admin MARCA
 * 
 * @author Andrea Contreras
 * @date Mayo 2022
 */

public class ATC_FT_001_Asociacion_POP_a_Plaza extends BaseExecution{
	SeleniumUtil u;
		
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Asociacion_POP_a_Plaza_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		utils.sql.SQLUtil dbFCIASQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);

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
		
		testCase.setTest_Description("Asociacion de POP a Plaza - "+tipoPop+" - "+perfil+" "+negocio);
		/*
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
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		 
		addStep("Seleccionar Inventario de Espacios");
		
		pa.clickPortalInventarioEspacios();
		
		/*
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
		
		/*
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
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep(" Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		
		boolean verEncabezado = mp.verificarEncabezado(negocio);
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		assertTrue(verEncabezado, "No se muestra la informaci�n esperada");
		
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar Menu-> Adminsitracion --> Asociacion de POPs");
	   // mp.clickMenus();
		mp.clickAdministracion();
		mp.clickAsociarPopPlazaOp();
		
		/*
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("Se visualiza la pantalla de Asociacion de POPs y los campos: Tipo de POP, POP, Buscar plazas por nombre, Plazas filtradas");
		
		
		boolean existenCampos = app.visualizarCampos();
         
		assertTrue(existenCampos, "No se visualizan todos los campos en la pantalla: Tipo de POP, POP, Plazas por nombre, Plazas filtradas");
		
		/*
		 *                                             PASO 8
		 ******************************************************************************************/
		
		addStep("Seleccionar en <Tipo_POP>, Seleccionar POP");

		
		boolean existeTipoPop = app.selectTipoPop(tipoPop);	
		boolean existePop = app.selectPop(pop);
		
		
		assertTrue(existeTipoPop && existePop, "No existe la opci�n Tipo Pop '"+data.get("TipoPop")+"' o no existe la opci�n POP '"+data.get("Pop")+"'");
		
		/*
		 *                                             PASO 9
		 ******************************************************************************************/
		
		addStep("Al tener seleccionado Tipo de POP y POP se actualiza en automatico lel grid 'Consulta de POPs Asociados' y los paneles: "
				+ "Plazas filtradas: Se muestran las plazas que aun no han sido asociadas al pop seleccionado."
				+ "Plazas asociadas: Se muestran las plazas que ya se encuentran asociadas para el po seleccionado.");
		
		boolean validacion = app.validarGridPaneles();
		assertTrue(validacion, "No se actualizo el Grid y/o no se muestran las plazas de 'Plazas filtradas'");
		
		/*
		 *                                             PASO 10
		 ******************************************************************************************/
		
		addStep("Seleccionamos en 'Plazas filtradas' minimo 1 plaza");
		
		int numPlInicio = app.obtenerNumeroPlazasActual(pop);
		ArrayList <String> valores = new ArrayList<String>();
		
		for(Map.Entry<String, String> entry : data.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();	    
		    if (key.contains("PlazasFiltradas:") && value.trim().length() > 0) {	
		    	valores.add(value);		
			}
		    
		}
		
		boolean selectPF = app.selectPlazaFiltradas(valores);
		assertTrue(selectPF, "No existen las plazas que se desean seleccionar: "+valores.toString());
		
		/*
		 *                                             PASO 11
		 ******************************************************************************************/
		
		addStep("Las Plazas seleccionada(s) se muestran en el campo 'Plazas asociadas' correctamente");
		
        boolean validarPA = app.validarPlazasSeanAsociadas(valores);
		
        assertTrue(validarPA, "No se muestra la plaza seleccionada en el campo 'Plazas asociadas'");
		
		/*
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("Clic al boton 'Guardar'");
		
		app.clickGuardar();
		app.clickBtnConfirmarPopUpGuardar();
		
		/*
		 *                                             PASO 13
		 ******************************************************************************************/
		addStep("Se muestra el mensaje 'Se ha asociado plazas a POP exitosamente' y se visualiza en el grid de "
				+ "'Consulta de POPs asociados' el registro nuevo si no existia o en su defecto se aumentan las "
				+ "plazas al contador de Numero de Plazas.");
		
		boolean validarMsg = app.validarMsgPlazaPopAsoc();
		int numPlFin = app.obtenerNumeroPlazasActual(pop);
		 
        boolean validarAumentoPl = app.validarAumNumPlazas(numPlInicio+valores.size(), numPlFin) ; 
        System.out.print("Aumento PLA-"+validarAumentoPl);
        
		assertTrue(validarMsg&&validarAumentoPl, "No se muestra el mensaje 'Se ha asociado plazas a POP exitosamente''");
		/*
		 *                                             PASO 14
		 ******************************************************************************************/
		addStep("Ingresar a la BD XXINE y validar que se guarde la asociacion en la tabla XXINE_PLAZA_POP");
		
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		
		String queryPlazaPop1 = "select PLAZA_ID, POP_ID, TIPO_POP_ID, LAST_UPDATE_DATE, LAST_UPDATED_BY"+
		        " from "+db+".XXINE_PLAZA_POP" + 
				" where CREATED_BY = '"+user+"'" + 
				" and CREATION_DATE >= to_date(Sysdate)" + 
				" order by CREATION_DATE desc";
		
		
		String queryPlazaPop2 = "select CREATION_DATE, CREATED_BY, CANTIDAD_MINIMA, CANTIDAD_MAXIMA"+
			    " from "+db+".XXINE_PLAZA_POP" + 
				" where CREATED_BY = '"+user+"'" + 
				" and CREATION_DATE >= to_date(Sysdate)" + 
				" order by CREATION_DATE desc";
		
		System.out.println(queryPlazaPop1);
		
		SQLResult queryResults1 = executeQuery(dbFCIASQA, queryPlazaPop1);
		SQLResult queryResults2 = executeQuery(dbFCIASQA, queryPlazaPop2);
		
		boolean empty = queryResults1.isEmpty();

		testCase.addTextEvidenceCurrentStep("select * from "+db+".XXINE_PLAZA_POP" + 
				" where CREATED_BY = '"+user+"'" + 
				" and CREATION_DATE >= to_date(Sysdate)" + 
				" order by CREATION_DATE desc");
		testCase.addQueryEvidenceCurrentStep(queryResults1, false);
		testCase.addQueryEvidenceCurrentStep(queryResults2, false);

		assertFalse(empty, "No se guardo la asociaci�n del Pop en la tabla XXINE_PLAZA_POP");
		
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
		return "ATC_FT_001_Asociacion_POP_a_Plaza_test";
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
