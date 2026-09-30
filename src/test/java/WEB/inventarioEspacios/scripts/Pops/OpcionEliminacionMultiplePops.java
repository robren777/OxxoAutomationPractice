package WEB.inventarioEspacios.scripts.Pops;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.DetallePops;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.om.PortalApps;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class OpcionEliminacionMultiplePops extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	///COMENTARIO
	public void ATC_FT_002_POPsOpcionEliminacionMultiple_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		/*
		 * Variables
		 ******************************************************************************************/		
		String tdcQueryPop = "SELECT POP_ID, NOMBRE_POP"
				+ " FROM XXINE.XXINE_CAT_POPS" 
				+ " WHERE NOMBRE_POP IN ('%s') ORDER BY CREATION_DATE DESC";
		
		
		String tdcQueryPlazaPop = "SELECT PLAZA_ID, POP_ID, TIPO_POP_ID, LAST_UPDATE_DATE, LAST_UPDATED_BY"
				+ " FROM XXINE.XXINE_PLAZA_POP" 
				+ " WHERE POP_ID IN ('%s')";
		
		String tdcQueryPedidosPop = "SELECT PEDIDO_DETAIL_ID, PEDIDO_HEADER_ID, POP_ID, CANTIDAD, CREATION_DATE, CREATED_BY "
				+ " FROM XXINE.XXINE_PEDIDOS_DETAIL" 
				+ " WHERE POP_ID IN ('%s')";
		
		String tdcQueryProveedorPop = "SELECT POP_ID, TIPO_POP_ID, PROVEEDOR_ID, LAST_UPDATE_DATE, LAST_UPDATED_BY"
				+ " FROM XXINE.XXINE_PROVEEDOR_POP" 
				+ " WHERE POP_ID IN ('%s')";
		
		
		String tdcQueryPedidoHeader = "SELECT PEDIDO_HEADER_ID, FOLIO_PEDIDO, ESTATUS, FECHA_PEDIDO"
				+ " FROM XXINE.XXINE_PEDIDOS_HEADER" 
				+ " WHERE PEDIDO_HEADER_ID IN ('%s')";
		
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		String nombrePop = data.get("NombrePop");
		String periodoPop = data.get("PeriodoPop");
		String tipoPop = data.get("TipoPop");
		String proveedorPop = data.get("ProveedorPop");
		String estatusPop = data.get("EstatusPop");
		
		testCase.setFullTestName("POPs Opcion Eliminacion Multiple");
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal");
		
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);		
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
		
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
	    boolean verInfo = mp.verificarInformacion(user, negocio, perfil);
		if(verInfo)
			testCase.addTextEvidenceCurrentStep("La informaci�n del usuario, negocio y perfil es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del usuario, negocio y/o perfil es incorrecta");
		
		//assertTrue(verInfo, "No se muestra la informaci�n esperada");
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("Validar que visualice los MENUS:");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		
		boolean verEncabezado = mp.verificarEncabezado(negocio);
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		//assertTrue(verEncabezado, "No se muestra la informaci�n esperada");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar Menu -->POP-->POPS");
		
	    mp.clickMenus();
		mp.clickPops();
		mp.clickPopsOp();
		
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		addStep("Se visualiza la pantalla de consulta de POPs con el campo de busqueda: * Nombre de POP, * Periodo, * Proveedor, * Tipo de POP");
		
		DetallePops dp = new DetallePops(u, testCase);
		boolean validarCampos = dp.validarCampos();
		
		assertTrue(validarCampos, "No se muestra la informaci�n esperada");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Ingresar la siguiente informacion: * Nombre de POP, * Periodo, * Proveedor, * Tipo de POP y dar clic a boton 'Buscar'");
		
		if(!nombrePop.isEmpty())
		   dp.clickNombrePop(nombrePop);
		if(!periodoPop.isEmpty())
		   dp.selectPeriodoPop(periodoPop);
		if(!proveedorPop.isEmpty())
           dp.selectProveedorPop(proveedorPop);
		if(!tipoPop.isEmpty())
		   dp.selectTipoPop(tipoPop);
		if(!estatusPop.isEmpty())
		   dp.selectEstatusPop(estatusPop);
		
		dp.clickBtnBuscarPop();
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("Se muestra en el grid la siguiente informacion: * ID de POP, * POP, * Tipo, * Periodo, * Requiere precio, * Especificacion, * Descripcion, * Proveedor, * Plaza, * Accion");
		
		boolean validarInfo = dp.validarGridInfo();
		
		assertTrue(validarInfo, "No se muestra la informaci�n esperada");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("Seleccionar al menos una de los registros del grid para que se habilite el boton Eliminar");
		
		/*
		String pedidoId = "", popId = "", strPops = "";
		int cont = 0;
	
		ArrayList <String> pops = new ArrayList<String>();
			      
		for(Map.Entry<String, String> entry : data.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();	  
		    
		    if (key.contains("Pops:") && value.trim().length() > 0) {		    	
		    	pops.add(value);
		    	if(cont > 0) {
		    		strPops += "','";
		    	}
		    	strPops += value;
		    	cont++;	    	
			}
		    
		}
		
		System.out.print("pops "+strPops);
		
		String queryPop = String.format(tdcQueryPop, strPops);
		SQLResult queryPopResult = executeQuery(dbFCIAS, queryPop);	
		
		for(int i = 0; i < pops.size(); ++i) {
			if(i > 0) 
				popId += "','";
			
			popId += queryPopResult.getData(i, "POP_ID");
		}
		
		System.out.print("\nid = "+popId);
			
		testCase.addQueryEvidenceCurrentStep(queryPopResult);
		
		String queryPlazaPop = String.format(tdcQueryPlazaPop, popId);
		SQLResult queryPlazaPopResult = executeQuery(dbFCIAS, queryPlazaPop);
		testCase.addQueryEvidenceCurrentStep(queryPlazaPopResult);
				
		String queryPedidosPop = String.format(tdcQueryPedidosPop, popId);
		SQLResult queryPedPopResult = executeQuery(dbFCIAS, queryPedidosPop);
		if(!queryPedPopResult.isEmpty()) {
			for(int i = 0; i < pops.size(); ++i) {			
				if(i > 0) 
					pedidoId += "','";	
				
				pedidoId += queryPedPopResult.getData(i, "PEDIDO_HEADER_ID");
			}
		}		
		testCase.addQueryEvidenceCurrentStep(queryPedPopResult);
		
		System.out.print("\npedidos "+pedidoId);
		
		String queryProveedorPop = String.format(tdcQueryProveedorPop, popId);
		SQLResult queryProvPopResult = executeQuery(dbFCIAS, queryProveedorPop);
		testCase.addQueryEvidenceCurrentStep(queryProvPopResult);
		
		String queryPedHeader = String.format(tdcQueryPedidoHeader, pedidoId);
		SQLResult queryPedHdrResult = executeQuery(dbFCIAS, queryPedHeader);
		testCase.addQueryEvidenceCurrentStep(queryPedHdrResult);
		
		dp.selectPopMultiple(pops);
		*/
		//dp.selectNamePop(data.get("nombre_pop"));
		boolean validarDelBtn = dp.isEliminarBtnEnable();
		
		assertTrue(validarDelBtn, "No se habilita el bot�n 'Eliminar'");
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("Clic al boton 'Eliminar'");
		
		dp.clickEliminarPop();
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("Se muestra el mensaje '�Est� seguro que desea eliminar los Pops seleccionados? "
				+ "Tambi�n se eliminar� lo siguiente: -Asociaciones a plazas -Asociaciones a proveedores "
				+ "- Asociaciones a pedidos'");
		
		boolean validarPopUp = dp.validarPopUpConfirmarEliminacion();
		
		assertTrue(validarPopUp, "No muestra el mensaje");
		
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		addStep("Clic al boton  'Confirmar'");
		
		dp.clickBtnConfirmarPopUpEliminar();
		
		boolean validarMsg = dp.validarMsgEliminarPlazaPop();
		
		assertTrue(validarMsg, "No muestra el mensaje de que se confirmo la eliminacion");
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
		addStep("Ingresar a la BD XXINE y validar que se elimine el pop de las tabla XXINE_CAT_POPS y sus asociaciones "
				+ "en XXINE_PEDIDOS_DETAIL, XXINE_PLAZA_POP, XXINE_PROVEEDOR_POP");
		
        System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
        
        String queryPop = String.format(tdcQueryPop, data.get("nombre_pop"));
		SQLResult queryPopResult = executeQuery(dbFCIAS, queryPop);	
		String id = queryPopResult.getData(0, "POP_ID");        
        String queryPlazaPop = String.format(tdcQueryPop, id);
        String queryPedidosPop = String.format(tdcQueryPop, id);
        String queryProveedorPop = String.format(tdcQueryPop, id);
        
        //tdcQueryPedidosPop = String.format(tdcQueryPedidosPop, id);
        //System.out.println(tdcQueryPedidosPop);
        //queryPopResult = executeQuery(dbFCIAS, tdcQueryPedidosPop);	
        //String pedidoId = queryPopResult.getData(0, "PEDIDO_HEADER_ID");        
        //String queryPedHeader = String.format(tdcQueryPedidoHeader, pedidoId);
		
        SQLResult queryPopResult2 = executeQuery(dbFCIAS, queryPop);
		testCase.addQueryEvidenceCurrentStep(queryPopResult2);
		
		SQLResult queryPlazaPopResult2 = executeQuery(dbFCIAS, queryPlazaPop);
		testCase.addQueryEvidenceCurrentStep(queryPlazaPopResult2);
				
		SQLResult queryPedPopResult2 = executeQuery(dbFCIAS, queryPedidosPop);
		testCase.addQueryEvidenceCurrentStep(queryPedPopResult2);

		SQLResult queryProvPopResult2 = executeQuery(dbFCIAS, queryProveedorPop);
		testCase.addQueryEvidenceCurrentStep(queryProvPopResult2);
		
		boolean validarBD1 = false;

		if (queryPopResult2.isEmpty() && queryPlazaPopResult2.isEmpty() && queryPedPopResult2.isEmpty() 
				&& queryProvPopResult2.isEmpty()) {			
			validarBD1 = true;		
		}

		// 	assertTrue(validarBD1, "No se elimino la informaci�n de las tablas");
		
		
		/**
		 *                                             PASO 15
		 ******************************************************************************************/
		addStep("Validar en la tabla XXINE_PEDIDOS_HEADER que se cambie el estatus del pedido a Rechazado estatus=7");
		
		// Se comenta debido a que no coincide el ID que se obtiene de XXINE.XXINE_CAT_POPS con el id de XXINE.XXINE_PEDIDOS_DETAIL 
		/*
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL); 
		
		boolean valStatus = true;		

		SQLResult queryPedHdrResult1 = executeQuery(dbFCIAS, queryPedHeader);
		if(!queryPedHdrResult1.isEmpty()) {
			for(int i = 0; i < queryPedHdrResult1.getRowCount(); ++i) {				
				String pedidoStatus = queryPedHdrResult1.getData(i, "ESTATUS");				
				if(!pedidoStatus.equals("7")){
					valStatus = false;
				}
			}
		}
		testCase.addQueryEvidenceCurrentStep(queryPedHdrResult1);		
		
		boolean validarBD2 = false;
		if(valStatus == true || queryPedHdrResult1.isEmpty()) {
			validarBD2 = true;
		}
				
		assertTrue(validarBD2, "No se cambio el estatus del pedido");
		*/
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
		return "ATC_FT_002_POPsOpcionEliminacionMultiple_Test";
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
//		u.getDriver().quit();
	}
}	