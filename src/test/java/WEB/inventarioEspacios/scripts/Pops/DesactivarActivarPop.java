package WEB.inventarioEspacios.scripts.Pops;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.DetallePops;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class DesactivarActivarPop extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_POPsDesactivarActivarPOP_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		/*
		 * Variables
		 ******************************************************************************************/		
		String tdcQueryPop = "SELECT POP_ID, NOMBRE_POP, LAST_UPDATE_DATE, LAST_UPDATED_BY, ACTIVO"
				+ " FROM XXINE.XXINE_CAT_POPS" 
				+ " WHERE NOMBRE_POP IN ('%s') ORDER BY CREATION_DATE DESC";

		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		String nombrePop = data.get("NombrePop");
		String periodoPop = data.get("PeriodoPop");
		String tipoPop = data.get("TipoPop");
		String proveedorPop = data.get("ProveedorPop");
		
		testCase.setTest_Description("POPs Desactivar o Activar POP - "+perfil+" "+negocio);
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal");
		
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);	
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
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
		addStep("Seleccionar el checkbox de 'Activo para activar o inactivar el POP'");
		
		boolean valEstatusApp = false;
		String estatusEsperado = "";
	    String estatusInicial = dp.obtenerEstatusActualPop(nombrePop);
	
		if(estatusInicial.equals("1"))
			estatusEsperado = "0";			
		if(estatusInicial.equals("0")) 
			estatusEsperado = "1";
				
		dp.activarDesactivarPop(nombrePop);
		dp.clickBtnConfirmarPopUpActivarDesactivar();
		
		String estatusFinal = dp.obtenerEstatusActualPop(nombrePop);
		
		 if (estatusFinal.equals(estatusEsperado)) {
			 valEstatusApp = true;
		 }
				
		assertTrue(valEstatusApp, "No cambio el estatus del pop");
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("Validar en la tabla XXINE_CAT_POPS que el campo Activo cambie para el pop seleccionado.");
	
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL); 
		
		boolean valEstatusBD = false;	
		String estatusBD = "";

		
		String queryPop = String.format(tdcQueryPop, nombrePop);
		SQLResult queryPopResult = executeQuery(dbFCIAS, queryPop);	
		if(!queryPopResult.isEmpty()){
			estatusBD = queryPopResult.getData(0, "ACTIVO");	
		}
		
		testCase.addQueryEvidenceCurrentStep(queryPopResult);	
		
		 if (estatusBD.equals(estatusEsperado)) {
			 valEstatusBD = true;
		 }
				
		assertTrue(valEstatusBD, "No cambio el estatus del pop en la tabla XXINE_CAT_POPS");
	
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
		return "ATC_FT_003_POPsDesactivarActivarPOP_Test";
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
