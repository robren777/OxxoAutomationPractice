package WEB.inventarioEspacios.scripts.AsociacionPopProveedor;

import static org.testng.Assert.assertFalse;
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
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_001_Asociacion_POP_a_Proveedor extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_Asociacion_POP_a_Proveedor_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		utils.sql.SQLUtil dbFCIAS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		/*
		 * Variables
		 ******************************************************************************************/				
		
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		String proveedor = data.get("Proveedor"); 
		String tipoPop = data.get("TipoPop");
	    String nomPop = data.get("NombrePop");
	    String proveedorId = "", popId = "", tipoPopId = "";
	    int cantPops = 0;
		
		testCase.setTest_Description("Asociacion de POP a Proveedor - "+tipoPop+" - "+perfil+" "+negocio);
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal con el usuario "+perfil+" "+negocio);
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		AsociarPopProveedorIEP apr = new AsociarPopProveedorIEP(u, testCase);
		u.get(data.get("Url"));
		
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
			
		/**
		 *                                             PASO 9
		 ******************************************************************************************/

		addStep("Al seleccionar el proveedor se muestra el listado de las asociaciones asignadas a ese proveedor (consulta de proveedores)");
		
		String tdcQueryTipoPop = "SELECT TIPO_POP_ID, NOMBRE_TIPO_POP"
				+ " FROM "+db+".XXINE_CAT_TIPOS_POP" 
				+ " WHERE NOMBRE_TIPO_POP = '%s'"; 	
		
		String tdcQueryCantidadPops = "SELECT TIPO_POP_ID, COUNT(*) AS CANTIDAD_POP"
				+ " FROM "+db+".XXINE_PROVEEDOR_POP"
		        + " WHERE PROVEEDOR_ID = %s"
		        + " GROUP BY TIPO_POP_ID";
		
		String queryCantPops = String.format(tdcQueryCantidadPops, proveedorId);
		SQLResult queryCantPopsResult = executeQuery(dbFCIAS, queryCantPops);	
				
		if(!queryProvResult.isEmpty())
			cantPops = queryCantPopsResult.getRowCount();
		
		System.out.print("Cantidad pops: "+cantPops);
		
		boolean visualizaLista = apr.visualizarListaPopsAsocProveedor(cantPops);
		
		System.out.print("\nValidacion list: "+visualizaLista);
		
		assertTrue(visualizaLista, "No se muestra el listado con los pops asociados");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/

		addStep("Seleccionar 'Tipo de POP'");
		
		apr.selectTipoPop(tipoPop);
		
		String queryTipoPop = String.format(tdcQueryTipoPop, tipoPop);
		SQLResult queryTipoPopResult = executeQuery(dbFCIAS, queryTipoPop);	
		
		if(!queryTipoPopResult.isEmpty()) 
			tipoPopId = queryTipoPopResult.getData(0, "TIPO_POP_ID");
			
		/**
		 *                                             PASO 11
		 ******************************************************************************************/

		addStep("Al tener seleccionado proveedor y tipo de POP el sistema carga automaticamente los POPs ya asociados "
				+ "y por asociar  en los paneles y se muestra en el grid 'Consulta de POPs asociados' el listado de "
				+ "las asociaciones asignadas a ese proveedor.");
		
		boolean  visualizaGP = apr.validarGridPaneles(cantPops);
		
		System.out.print("\nValidacion grid y panel opciones: "+visualizaGP);
		
		//assertTrue(visualizaGP, "No se muestra la informaci�n esperada");

		/**
		 *                                             PASO 12
		 ******************************************************************************************/

		addStep("Realizar la busqueda por POP especifico en el campo 'Buscar POP por nombre'");
		
		apr.typeNombrePop(nomPop);
		/**
		 *                                             PASO 13
		 ******************************************************************************************/

		addStep("La lista de POPS filtrados se va haciendo mas peque�a a como se va ingresando el nombre del pop y se muestra en orden alfabetico.");

		apr.visualizarPOPsFiltrados(nomPop);
		/**
		 *                                             PASO 14
		 ******************************************************************************************/

		addStep("Seleccionar en 'POPs filtrados' minimo 1 valor, al seleccionarlo debe pasar al panel POPs asociados y despues "
				+ "damos clic al boton 'Guardar'");

		int numPopInicio = apr.obtenerNumeroPopsAsocProv(tipoPop);
		ArrayList <String> valores = new ArrayList<String>();
		
		for(Map.Entry<String, String> entry : data.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();	    
		    if (key.contains("Pop:") && value.trim().length() > 0) {	
		    	valores.add(value);		
			}
		    
		}
		
		System.out.print("\nCantidad de pops a seleccionar: "+valores.size());
		
		apr.selectPOPsFiltrados(valores);
		
		boolean validarAsociacion = apr.validarPopsAsociados(valores);
		System.out.print("\nSe asociaron los pops: "+validarAsociacion);
		
		apr.clickGuardarBtn();
		
		assertTrue(validarAsociacion, "No se pasaron al panel POPs Asociados");
		
		
		/**
		 *                                             PASO 15
		 ******************************************************************************************/

		addStep("El sistem muestra mensaje: �Desea guardar los cambios aplicados? " + 
				"Y los botones cancelar y confirmar.");

		boolean validarMsgGuardarCambios = apr.validarPopUpGuardarCambios();
		
		assertTrue(validarMsgGuardarCambios, "No se muestra el mensaje '�Desea guardar los cambios aplicados?'");
		
		/**
		 *                                             PASO 16
		 ******************************************************************************************/

		addStep("Dar clic en Confirmar");
		
		apr.clickBtnConfirmarPopUpGuardar();
		
		/**
		 *                                             PASO 17
		 ******************************************************************************************/

		addStep("Se asocia el Pop seleccionado al provedor correctamente.");
		
		boolean validarAsoc = false;
		boolean validarMsgPOPyProvAsoc = apr.validarMsgPOPyProveedorAsoc();
		int numPopFinal = apr.obtenerNumeroPopsAsocProv(tipoPop);
		
		if(numPopInicio+valores.size()==numPopFinal)
			validarAsoc = true;
		
		System.out.print("\nValidacion de msg de asociacion correcta: "+validarMsgPOPyProvAsoc);
		System.out.print("\nValidacion de cantidad pops pantalla: "+validarAsoc);
		assertTrue(validarMsgPOPyProvAsoc && validarAsoc, "No se muestra el mensaje '�Desea guardar los cambios aplicados?'");
		
		
		/**
		 *                                             PASO 18
		 ******************************************************************************************/

		addStep("Ingresar a la BD y validar que se genere la relaci�n proveedor-POP en la tabla XXINE_PROVEEDRO_POP");
		
		String tdcQueryProveedorPop = "SELECT POP_ID, TIPO_POP_ID, PROVEEDOR_ID, LAST_UPDATE_DATE, LAST_UPDATED_BY"
				+ " FROM "+db+".XXINE_PROVEEDOR_POP" 
				+ " WHERE PROVEEDOR_ID = '%s'"
				+ " AND TIPO_POP_ID = '%s'"
				+ " AND TRUNC(LAST_UPDATE_DATE) = TRUNC(SYSDATE)";
		
		String queryProveedorPop = String.format(tdcQueryProveedorPop, proveedorId, tipoPopId);
		SQLResult queryProvPopResult = executeQuery(dbFCIAS, queryProveedorPop);	
		
		testCase.addQueryEvidenceCurrentStep(queryProvPopResult);
		
		System.out.print("\nConsulta esta vacia: "+queryProvPopResult.isEmpty());
			
		assertFalse(queryProvPopResult.isEmpty(), "No se genero el registro en la tabla XXINE_PROVEEDRO_POP");
			

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
