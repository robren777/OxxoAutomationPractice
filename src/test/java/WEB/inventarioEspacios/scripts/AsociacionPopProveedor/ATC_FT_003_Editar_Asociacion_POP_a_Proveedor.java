package WEB.inventarioEspacios.scripts.AsociacionPopProveedor;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.AsociarPopProveedorIEP;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_003_Editar_Asociacion_POP_a_Proveedor extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_003_Editar_Asociacion_POP_a_Proveedor_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		utils.sql.SQLUtil dbFCIAS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		///COMENTARIO
			
		/*
		 * Variables
		 ******************************************************************************************/				
		
		String url = data.get("Url");
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		String proveedor = data.get("Proveedor"); 
		String tipoPop = data.get("TipoPop");
		String nomPop = data.get("NombrePop");
		String accion = data.get("Accion");
	    String proveedorId = "", tipoPopId = "";
	    int cantPops = 0, cantTipoPops = 0, rowsFin = 0;
		
		testCase.setTest_Description("Eliminar asociacion de POP a Proveedor - "+tipoPop+" - "+perfil+" "+negocio);
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal con el usuario "+perfil+" "+negocio);
		
		u = new SeleniumUtil(new ChromeTest(), true);
		
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		AsociarPopProveedorIEP apr = new AsociarPopProveedorIEP(u, testCase);
		
		u.get(url);
		
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
		SQLResult ExecuteQuery = executeQuery(dbFCIAS, query);
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

		addStep("Validar que visualice los MENUS");
		
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

		addStep("Seleccionar Administracion -->Asociacion de POP's a Proveedores");

		mp.clickAdministracion();
		mp.clickAsociarPopProveedorOp();
		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/

		addStep("Se visualiza los siguientes campos: * Proveedor, * Tipo de POP, * Pop Filtrados (El cual se llenara seg�n el Proveedor y Tipo de Pop seleccionados)");

		boolean visualizaCampos = apr.visualizarCampos();
		
		assertTrue(visualizaCampos, "No se visualizan los campos en la pantalla 'Asociar POP a Proveedor'");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/

		addStep("Seleccionar Proveedor");
		
		apr.selectProveedor(proveedor);
		
		String tdcQueryProveedor = "SELECT PROVEEDOR_ID, NOMBRE_PROVEEDOR "
				+ " FROM "+db+".XXINE_CAT_PROVEEDORES"
				+ " WHERE NOMBRE_PROVEEDOR = '%s'";
		
		
		String queryProveedor = String.format(tdcQueryProveedor, proveedor);
		SQLResult queryProvResult = executeQuery(dbFCIAS, queryProveedor);	
		
		if(!queryProvResult.isEmpty())
			proveedorId = queryProvResult.getData(0, "PROVEEDOR_ID");
		
		testCase.addQueryEvidenceCurrentStep(queryProvResult);
		

			
		/**
		 *                                             PASO 9
		 ******************************************************************************************/

		addStep("Al seleccionar el proveedor se muestra el listado de las asociaciones asignadas a ese proveedor (consulta de proveedores)");


		String tdcQueryCantidadTipoPops = "SELECT TIPO_POP_ID, COUNT(*) AS CANTIDAD_POP"
				+ " FROM "+db+".XXINE_PROVEEDOR_POP"
		        + " WHERE PROVEEDOR_ID = %s"	       
		        + " GROUP BY TIPO_POP_ID";

		
		String queryCantTipoPops = String.format(tdcQueryCantidadTipoPops, proveedorId);
		SQLResult queryCantTipoPopResult = executeQuery(dbFCIAS, queryCantTipoPops);	
				
		if(!queryProvResult.isEmpty())
			cantTipoPops = queryCantTipoPopResult.getRowCount();
		
		System.out.print("Cantidad de tipo de pops: "+cantTipoPops);
		
		boolean visualizaLista = apr.visualizarListaPopsAsocProveedor(cantTipoPops);
		
		System.out.print("\nValidacion list: "+visualizaLista);
		
		assertTrue(visualizaLista, "No se muestra el listado con los pops asociados");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
        
		addStep("Seleccionar el boton Editar en un registro del grid 'Consulta de POPs asociados'");
		
		apr.clickEditarRegistro(tipoPop);
		
		String tdcQueryPop = "SELECT POP_ID, NOMBRE_POP"
				+ " FROM "+db+".XXINE_CAT_POPS" 
				+ " WHERE NOMBRE_POP = '%s'"; 
		
		String tdcQueryTipoPop = "SELECT TIPO_POP_ID, NOMBRE_TIPO_POP"
				+ " FROM "+db+".XXINE_CAT_TIPOS_POP" 
				+ " WHERE NOMBRE_TIPO_POP = '%s'"; 
		
		String queryTipoPop = String.format(tdcQueryTipoPop, tipoPop);
		SQLResult queryTipoPopResult = executeQuery(dbFCIAS, queryTipoPop);	
				
		if(!queryTipoPopResult.isEmpty())
			tipoPopId = queryTipoPopResult.getData(0, "TIPO_POP_ID");
		
		testCase.addQueryEvidenceCurrentStep(queryTipoPopResult);
					
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
    
		addStep("Se muestra la pantalla de captura (Recargando la pagina con los valores del la asociacion a editar) dondre podr� realizar la edici�n de los datos existentes");
	
		boolean validarTipoPopSelect = apr.validarTipoPopSeleccionado(tipoPop);
		
		String tdcQueryProveedorTipoPop = "SELECT POP_ID, TIPO_POP_ID, PROVEEDOR_ID, LAST_UPDATE_DATE, LAST_UPDATED_BY"
				+ " FROM "+db+".XXINE_PROVEEDOR_POP" 
				+ " WHERE PROVEEDOR_ID = '%s'"
				+ " AND TIPO_POP_ID = '%s'";
		
		String queryProvTipoPop = String.format(tdcQueryProveedorTipoPop, proveedorId, tipoPopId);
		SQLResult queryProvTipoPopResult = executeQuery(dbFCIAS, queryProvTipoPop);	
				
		if(!queryProvTipoPopResult.isEmpty())
			cantPops = queryProvTipoPopResult.getRowCount();
			
		testCase.addQueryEvidenceCurrentStep(queryProvTipoPopResult);	
		
		assertTrue(validarTipoPopSelect, "No se muestra la informaci�n del registro seleccionado|");
		
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
    
		addStep("Se agrega o quita pop al proveedor y se da clic en Guardar");
	
		ArrayList <String> valores = new ArrayList<String>();
		ArrayList <String> valoresID = new ArrayList<String>();
		
		for(Map.Entry<String, String> entry : data.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();	    
		    if (key.contains("Pop:") && value.trim().length() > 0) {
		    	
		    	valores.add(value);
		    
			}
		}	
		
		System.out.print("\nCantidad de pops: "+valores.size());
		
		boolean validarEditar = apr.editarPopProveedor(valores, accion);
		
		apr.clickGuardarBtn();
		
		assertTrue(validarEditar, "No se agrego o quito el pop");
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
    
		addStep("Se muestra el mensaje en pantalla '�Desea guardar los cambios aplicados?'");
	
		boolean validarPopUp = apr.validarPopUpGuardarCambios();
		
		assertTrue(validarPopUp, "No se muestra el mensaje");
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
    
		addStep("Seleccionamos en el mensaje la opcion 'Confirmar'");
		
		apr.clickBtnConfirmarPopUpGuardar();
		
		boolean validarMsg = apr.validarMsgPOPyProveedorAsoc();
		
		assertTrue(validarMsg, "No se guardaron los cambios");
		
		/**
		 *                                             PASO 15
		 ******************************************************************************************/
    
		addStep("Ingresar a la BD y validar que se edito la relaci�n proveedor-POP en la tabla XXINE_PROVEEDOR_POP");
	
		String queryProvTipoPop1 = String.format(tdcQueryProveedorTipoPop, proveedorId, tipoPopId);
		SQLResult queryProvTipoPopResult1 = executeQuery(dbFCIAS, queryProvTipoPop1);
		
		if(!queryProvTipoPopResult1.isEmpty())
			rowsFin = queryProvTipoPopResult1.getRowCount();
		
		testCase.addQueryEvidenceCurrentStep(queryProvTipoPopResult1);
	
		boolean validarBD = false;
		
		if(accion.toUpperCase().equals("AGREGAR")) {
			
			validarBD = cantPops+valores.size()==rowsFin;			
			
		}
		
        if(accion.toUpperCase().equals("ELIMINAR")) {
        	
        	validarBD = cantPops-valores.size()==rowsFin;
        	
		}

		assertTrue(validarBD, "No se muestran los cambios en la tabla XXINE_PROVEEDOR_POP");

	
		
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
		return null;
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
