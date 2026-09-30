package WEB.inventarioEspacios.scripts.Tipo.Pedido;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.NuevoTipoPedidoIEP;
import WEB.inventarioEspacios.om.TipoPedidoIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_003_ConsultaTipoPedidos_BotonNuevo_Guardar extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_003_ConsultaTipoPedidos_BotonNuevo_Guardar_test(HashMap<String, String> data) throws Exception {
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
		String nomTipoPed = data.get("NombreTipoPedido");
		String peridoVis = data.get("PeridosVisibles");
		String tipoPop = data.get("TipoPop");
		String comentario = data.get("Comentario");
			
		testCase.setTest_Description("Consulta Tipo Pedidos - Buscar - Limpiar - "+perfil+" "+negocio);
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal");
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);	
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		TipoPedidoIEP tp = new TipoPedidoIEP(u, testCase);
		NuevoTipoPedidoIEP ntp = new NuevoTipoPedidoIEP(u, testCase);
		
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
			/*
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
		 *****************************************************************************************/
		
		addStep("Validar que visualice los MENUS:");
		
		mp.clickMenus();
		/*
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
		/*
		addStep(" Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		
		boolean verEncabezado = mp.verificarEncabezado(data.get("Negocio"));
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		assertTrue(verEncabezado, "No Muestra la informaci�n correcta");
		
		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Seleccionar Menu->Administraci�n->Tipo Pedido");
		
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
		
		System.out.print("Validacion campos pantalla Tipo Pedidos: "+visualizarTP+"\n");
		
		assertTrue(visualizarTP, "No se abre la pantalla y no se muestran los campos");
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Dar clic en el bot�n Nuevo");
		
		tp.clickNuevoBtn();
		
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("El sistema mostrar� la p�gina \"\"Captura de Tipos de Pedido\"\" mostrando los siguientes campos habilitados: \r\n" + 
				"Nombre: deber� ser un campo alfanum�rico para ingresar el nombre del tipo de pedido\r\n" + 
				"Tipo de POP: es un campo de selecci�n el cual mostrar� el listado de tipos de pop \r\n" + 
				"Per�odos Visibles: es un campo num�rico para ingresar el numero de periodos que tendr� habilitados el tipo de pedido\r\n" + 
				"Comentarios: campo de texto \r\n" + 
				"Bot�n Grabar: al dar clic sobre este bot�n se deber� crear el nuevo tipo de pedido, el sistema asigna en autom�tico el Id  y regresa a la p�gina anterior mostrando la informaci�n del nuevo tipo de pedido\r\n" + 
				"Limpiar:  al dar clic sobre este bot�n se deber� borrar las opciones seleccionadas incluido el campo de nombre\r\n" + 
				"Cancelar: al dar clic sobre este bot�n se descarta la opci�n y se regresa a la p�gina anterior.");
        
  
		boolean visualizarNuevoTP = ntp.visualizarCampos();
		
        System.out.print("Validacion campos pantalla Nuevo Tipo Pedido: "+visualizarNuevoTP+"\n");
		
		assertTrue(visualizarNuevoTP, "No se abre la pantalla y no se muestran los campos");
		
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("Capturar Nombre del tipo de pedido, comentarios, realizar selecci�n m�ltiple de opciones  en Periodos visibles, tipo de pop,  y dar clic en Guardar");
		
		if(!nomTipoPed.isEmpty()) 
			ntp.typeNombre(nomTipoPed);	
		if(!peridoVis.isEmpty()) 
			ntp.typePeriodosVisibles(peridoVis);
		if(!tipoPop.isEmpty()) 
			ntp.selectTipoPop(tipoPop);
		if(!comentario.isEmpty()) 
			ntp.typeComentario(comentario);

        ntp.clickGuardarBtn();
		
		if(ntp.validarPopUpGuardarCambios()==true)
			ntp.clickBtnConfirmarPopUpGuardar();
		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("El sistema deber� crear el nuevo tipo de pedido, el sistema asigna en autom�tico el Id  y regresa a la p�gina anterior mostrando la informaci�n del nuevo tipo de pedido.");

		boolean validarCreacion = false, validarMsg, validarNuevoTipoPedido;

		validarMsg = ntp.validarMsgTipoPedidoCreado();
		validarNuevoTipoPedido = tp.existeNombreTipoPedidoGrid(nomTipoPed);
		String idTipoPedido = tp.obtenerIdTipoPedidoGrid(nomTipoPed);
		
		System.out.print("Validacion mensaje Tipo Pedido Creado Exitosamente: "+validarMsg+"\n");
		System.out.print("Validar existencia de Nuevo Tipo Pedido en Grid: "+validarNuevoTipoPedido+"\n");
		
	
		if(validarMsg == true && validarNuevoTipoPedido == true) {
			validarCreacion = true;
		}
		
		assertTrue(validarCreacion, "No se creo exitosamente el tipo de pedido");
	
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("Ingresar a la BD XXINE para consultar la tabla XXINE_CAT_TIPOS_PEDIDO y validar que se guarde el registro.");
		
		String tdcQueryCatTipoPed1 = "SELECT TIPO_PEDIDO_ID, TIPO_POP_ID, NOMBRE_TIPO_PEDIDO"
				+ " FROM "+db+".XXINE_CAT_TIPOS_PEDIDO"
				+ " WHERE TIPO_PEDIDO_ID = '"+idTipoPedido+"'";
		
		String tdcQueryCatTipoPed2 = "SELECT PERIODOS_VISIBLES, CREATION_DATE, COMENTARIOS"
				+ " FROM "+db+".XXINE_CAT_TIPOS_PEDIDO"
				+ " WHERE TIPO_PEDIDO_ID = '"+idTipoPedido+"'";
		
		
		SQLResult queryProvTipoPedR1 = executeQuery(dbFCIAS, tdcQueryCatTipoPed1);		
		SQLResult queryProvTipoPedR2 = executeQuery(dbFCIAS, tdcQueryCatTipoPed2);	
		
		boolean validarBD = false;
		
		if(queryProvTipoPedR1!=null) {		
			
			validarBD = queryProvTipoPedR1.isEmpty();		
			testCase.addQueryEvidenceCurrentStep(queryProvTipoPedR1);
			testCase.addQueryEvidenceCurrentStep(queryProvTipoPedR2);
			
		}
		
		System.out.print("Consulta esta vacia: "+validarBD+"\n");
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
//		u.getDriver().quit();
	}
}
