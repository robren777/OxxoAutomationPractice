package WEB.inventarioEspacios.scripts.Pops;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.DetallePops;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.NuevoPopIEP;
import WEB.om.PortalApps;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class CrearPop_BtnNuevo extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_POPsBotonNuevo_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		//comentario
		/*
		 * Variables
		 ******************************************************************************************/		
		String tdcQueryPop = "SELECT POP_ID, NOMBRE_POP, LAST_UPDATE_DATE, LAST_UPDATED_BY, ACTIVO"
				+ " FROM XXINE.XXINE_CAT_POPS" 
				+ " WHERE NOMBRE_POP IN ('%s') ORDER BY CREATION_DATE DESC";

		
		String tdcQueryPlazaPop = "SELECT PLAZA_ID, POP_ID, TIPO_POP_ID, LAST_UPDATE_DATE, LAST_UPDATED_BY"
				+ " FROM XXINE.XXINE_PLAZA_POP" 
				+ " WHERE POP_ID = '%s'";
		
		String tdcQueryProveedorPop = "SELECT POP_ID, TIPO_POP_ID, PROVEEDOR_ID, LAST_UPDATE_DATE, LAST_UPDATED_BY"
				+ " FROM XXINE.XXINE_PROVEEDOR_POP" 
				+ " WHERE POP_ID = '%s'";
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		String tipoPop = data.get("TipoPop");
		String nombrePop = "Auto Pop9";//data.get("NombrePop");
		String espec = data.get("EspecificacionPop");
		String desc = data.get("DescripcionPop");
		String coment = data.get("ComentarioPop");
		String precio = data.get("PrecioPop");
		String reqP = data.get("ReqPrecioPop");
		String cont = data.get("ContenidoPop");
		String seg = data.get("SegmentoPop");
		String kit = data.get("KitPop");
		String tipoNegocio = data.get("TipoNegocioPop");
		
		String proveedorPop = data.get("ProveedorPop");
		
		testCase.setTest_Description("Consulta de POPs - Subir/Cambiar Imagen - "+tipoPop+" - "+ perfil+" "+negocio);
		
		
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal");
		
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		DetallePops dp = new DetallePops(u, testCase);
		NuevoPopIEP n = new NuevoPopIEP(u, testCase);
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
/*		
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
		/*
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
/*		
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
		
		
		boolean validarCampos = dp.validarCampos();
		
		assertTrue(validarCampos, "No se muestra la informaci�n esperada");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Seleccionar el boton 'Nuevo'");
		
		dp.clickBtnNuevoPop();
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("Se abre la pantalla de Captura de POP y se muestran los Datos Generales ");
		
		boolean validarDatosGrls = n.validarDatosGeneralesTab();
		
		assertTrue(validarDatosGrls, "No se muestra los campos de Datos Generales");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("Ingresar los datos y cambiar a la pesta�a 'Plazas'");


		switch(tipoPop.toUpperCase()) {
		
		case "PEDIDOS GENERICOS":
			                  break;
		case "ACCESORIOS":
			break;
		case "FALTANTE ADICIONAL":
			break;
		case "AMBIENTACION DE TIENDAS":
			break;
		case "ANDATTI":
			break;
		case "POP PROMOCION":
			break;
		default:
			break;		
		}
		
		n.selectTipoPop("Faltante-Adicional");
		n.typeNombrePop(nombrePop);
		n.typeEspecificacion(espec);
		n.typeDescripcion(desc);
		//n.typeComentario(coment);
		//n.typePrecio(precio);
		n.checkRequierePrecio(reqP);
		//n.typeContenido(cont);
		//n.typeSegmento(seg);
		//n.typeKit(kit);
		//n.typeTipoNegocio(tipoNegocio);
		
		n.clickGuardar();
		n.clickBtnConfirmarPopUp();
		
		boolean validarMsgPop = n.validarMsgPopCreado();		
				
		//assertTrue(validarMsgPop);
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("Se muestra la pantalla para seleccionar las plazas: Se permite la busqueda de Plazas por nombre, "
				+ "Se muestra el resultado de la busqueda, Se muestra las plazas seleccionadas");
		

		boolean validarPlazas = n.validarPlazasTab();
		
		assertTrue(validarPlazas, "No se muestran los campos de la pantalla 'Plazas'");
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("Seleccionar Plazas y cambiar a pesta�a 'Proveedores'");
		
		ArrayList <String> valores = new ArrayList<String>();
		
		for(Map.Entry<String, String> entry : data.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();	    
		    if (key.contains("PlazasPop:") && value.trim().length() > 0) {	
		    	valores.add(value);		
			}
		    
		}
		
		System.out.print(valores.size());
		
		n.typePlaza("Bogota");
		
		n.selectPlazaFiltradas(valores);
		
		n.clickProveedoresTab();
		
		boolean validarProveedor = n.validarProveedoresTab();
		
		assertTrue(validarProveedor, "No se muestran los campos de la pantalla 'Proveedor'");
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		addStep("Seleccionar Proveedor y  regresar a la pantalla de Datos generales y dar clic en Guardar.");
		
		n.selectOption(proveedorPop);
		//n.selectProveedor(proveedorPop);
		
		n.clickDatosGeneralesTab();

		n.clickGuardar();
		
		n.clickBtnConfirmarPopUp();
		

		
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
		addStep("Se muestra mensaje de confirmacion para el POP nuevo");
		
		
		boolean validarMsgPop2 = n.validarMsgPopActualizado();
		
		assertTrue(validarMsgPop2);
		
		/**
		 *                                             PASO 15
		 ******************************************************************************************/
		addStep("Ingresar a la BD XXINE y validar en las tablas XXINE_CAT_POPS se guarde el POP y en las tablas XXINE_PLAZA_POP / XXINE_PROVEEDORE_POP se guarden las asociaciones.");
			
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL); 
		
		u.hardWait(7);	
		boolean validarBD = false;	
		String popId = "";

		
		String queryPop = String.format(tdcQueryPop, nombrePop);
		SQLResult queryPopResult = executeQuery(dbFCIAS, queryPop);
		testCase.addQueryEvidenceCurrentStep(queryPopResult);
		
		if(!queryPopResult.isEmpty()) {
			popId = queryPopResult.getData(0, "POP_ID");
			//validacionBD = true;
		}    
		
		String queryPlazaPop = String.format(tdcQueryPlazaPop, popId);
		SQLResult queryPlazaPopResult = executeQuery(dbFCIAS, queryPlazaPop);
		testCase.addQueryEvidenceCurrentStep(queryPlazaPopResult);
				
		String queryProveedorPop = String.format(tdcQueryProveedorPop, popId);
		SQLResult queryProvPopResult = executeQuery(dbFCIAS, queryProveedorPop);
		testCase.addQueryEvidenceCurrentStep(queryProvPopResult);
		
		if(!queryPopResult.isEmpty() && !queryPlazaPopResult.isEmpty()
				&& !queryProvPopResult.isEmpty()) {
			validarBD = true;
		} 
		
		
		assertTrue(validarBD, "Se genera el pop y se guardan sus asociaciones");
	
	
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
		return "ATC_FT_005_POPsBotonNuevo_Test";
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
		//u.getDriver().quit();
	}
}
