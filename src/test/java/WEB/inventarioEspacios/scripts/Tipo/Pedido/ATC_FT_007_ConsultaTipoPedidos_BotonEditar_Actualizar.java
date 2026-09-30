package WEB.inventarioEspacios.scripts.Tipo.Pedido;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.EditarTipoPedidoIEP;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.TipoPedidoIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_007_ConsultaTipoPedidos_BotonEditar_Actualizar extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_007_ConsultaTipoPedidos_BotonEditar_Actualizar_Test(HashMap<String, String> data) throws Exception {
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
		String idTipoPed = data.get("IdTipoPedido");
		String nomTipoPed = data.get("NombreTipoPedido");
		String peridoVis = data.get("PeridosVisibles");
		String tipoPop = data.get("TipoPop");
		String comentario = data.get("Comentario");
		
		String tdcQueryCatTipoPedXinne1 = "SELECT TIPO_PEDIDO_ID, TIPO_POP_ID, NOMBRE_TIPO_PEDIDO"
				+ " FROM XXINE.XXINE_CAT_TIPOS_PEDIDO"
				+ " WHERE TIPO_PEDIDO_ID = '"+idTipoPed+"'";
		
		String tdcQueryCatTipoPedXinne2 = "SELECT PERIODOS_VISIBLES, CREATION_DATE, COMENTARIOS"
				+ " FROM XXINE.XXINE_CAT_TIPOS_PEDIDO"
				+ " WHERE TIPO_PEDIDO_ID = '"+idTipoPed+"'";
		
		String tdcQueryCatTipoPedXinneBA1 = "SELECT TIPO_PEDIDO_ID, TIPO_POP_ID, NOMBRE_TIPO_PEDIDO"
				+ " FROM XXINE_BA.XXINE_CAT_TIPOS_PEDIDO"
				+ " WHERE TIPO_PEDIDO_ID = '"+idTipoPed+"'";
		
		String tdcQueryCatTipoPedXinneBA2 = "SELECT PERIODOS_VISIBLES, CREATION_DATE, COMENTARIOS"
				+ " FROM XXINE_BA.XXINE_CAT_TIPOS_PEDIDO"
				+ " WHERE TIPO_PEDIDO_ID = '"+idTipoPed+"'";
		
		testCase.setTest_Description("Consulta Tipo Pedidos - Bot�n Editar - Actualizar - "+perfil+" "+negocio);
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal");
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);	
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		TipoPedidoIEP tp = new TipoPedidoIEP(u, testCase);
		EditarTipoPedidoIEP etp = new EditarTipoPedidoIEP(u, testCase);
		
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
		/*
		addStep("Validar que visualice los MENUS:");
		
		mp.clickMenus();
		switch(perfil.toUpperCase()) {
			case "ADMINISTRADOR": 
				if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("BARA")){
					mpo.menuAdministrador();
				} 
				
			break;
			
			default:
			break;
			

		}
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep(" Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		
		boolean verEncabezado = mp.verificarEncabezado(data.get("Negocio"));
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
	//	assertTrue(verEncabezado, "No Muestra la informaci�n correcta");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar Menu->Administraci�n->Tipo Pedido");
		
	    //mp.clickMenus();
		switch(negocio.toUpperCase()) {
		   case "OXXO": 
			   mp.clickPedidos();
			   break;
			   
		   case "BARA": 
			   mp.clickPedidos();
			   break;
		
		   default:
		        break;
	     }
		
		mp.clickTipoPedido();

		
		/**
		 *                                             PASO 7
		 ******************************************************************************************/
		
		addStep("Se abre la pantalla Tipos de Pedido mostrando el listado de los tipos de pedidos existentes(en el grid principal) con el siguiente contenido:\r\n" + 
				" - Filtros. Nombre de tipo de POP\r\n" + 
				"   - Bot�n. Nuevo\r\n" + 
				"   - Bot�n. Limpiar\r\n" + 
				"   - Bot�n. Buscar\r\n" + 
				"   - Bot�n. Eliminar(Se activar� al seleccionar uno o m�s tipos de pedidos del grid principal)\r\n" + 
				" - Grid principal. ID Tipo de Pedido/Nombre/Tipo de POP/Periodos Visibles/Editar/Carga de imagen/Comentarios (Se mostrar� un checkbox por registro)");
			
		boolean visualizarTP = tp.visualizarCampos();
		
		System.out.print("\nValidacion campos pantalla Tipo Pedidos: "+visualizarTP+"\n");
		
		assertTrue(visualizarTP, "No se abre la pantalla y no se muestran los campos");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Dar clic en el icono de  editar");
		
        tp.clickEditarRegistro(idTipoPed);
		
		etp.visualizarCampos();
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("Realizar modificaciones y dar clic en Actualizar.");
		
		if(!nomTipoPed.isEmpty()) 
			etp.typeNombre(nomTipoPed);	
		if(!peridoVis.isEmpty()) 
			etp.typePeriodosVisibles(peridoVis);
		if(!tipoPop.isEmpty()) 
			etp.selectTipoPop(tipoPop);
		if(!comentario.isEmpty()) 
			etp.typeComentario(comentario);
		
		
		etp.clickGuardarBtn();
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("El sistema deber� actualizar la informaci�n que se modific� del tipo de pedido y regresar a la p�gina anterior.");
 
		boolean visualizaPaginaAnterior = tp.visualizarPantalla();
		
		boolean validarMsg = etp.validarMsgPedidoEditado();
		
		
		assertTrue(visualizaPaginaAnterior && validarMsg, "El sistema no guardo los cambios");
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("Ingresar a la BD XXINE para consultar la tabla XXINE_CAT_TIPOS_PEDIDO y validar que se guarde el registro.");
		
		
		String tdcQueryCatTipoPed1 = "SELECT TIPO_PEDIDO_ID, TIPO_POP_ID, NOMBRE_TIPO_PEDIDO"
				+ " FROM "+db+".XXINE_CAT_TIPOS_PEDIDO"
				+ " WHERE TIPO_PEDIDO_ID = '"+idTipoPed+"'";
		
		String tdcQueryCatTipoPed2 = "SELECT PERIODOS_VISIBLES, LAST_UPDATE_DATE, COMENTARIOS"
				+ " FROM "+db+".XXINE_CAT_TIPOS_PEDIDO"
				+ " WHERE TIPO_PEDIDO_ID = '"+idTipoPed+"'";
		
	
		
		SQLResult queryProvTipoPedR1 = executeQuery(dbFCIAS, tdcQueryCatTipoPed1);
		SQLResult queryProvTipoPedR2 = executeQuery(dbFCIAS, tdcQueryCatTipoPed2);
		
	
		boolean validarBD = false;
		String updateDate;
		String currentDate;
		
		if(queryProvTipoPedR1!=null) {		
			
			validarBD = queryProvTipoPedR1.isEmpty();	
			
			if(validarBD == false) {
				updateDate = queryProvTipoPedR2.getData(0, "LAST_UPDATE_DATE");}
			
					
			testCase.addTextEvidenceCurrentStep("SELECT TIPO_PEDIDO_ID, TIPO_POP_ID, NOMBRE_TIPO_PEDIDO, PERIODOS_VISIBLES, CREATION_DATE, COMENTARIO "
					                              + "FROM "+db+".XXINE_CAT_TIPOS_PEDIDO WHERE NOMBRE_TIPO_PEDIDO = '"+nomTipoPed+"'");
			testCase.addQueryEvidenceCurrentStep(queryProvTipoPedR1, false);
			testCase.addQueryEvidenceCurrentStep(queryProvTipoPedR2, false);
			
		}
		
		System.out.print("Consulta esta vacia: "+validarBD+"\n");
		//Falta agregar validacion de que no se haya realizado ningun cambio en la BD
	
		assertFalse(validarBD, "No se guardo el registro en la base de datos");

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
		return "ATC_FT_007_ConsultaTipoPedidos_BotonEditar_Actualizar_Test";
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