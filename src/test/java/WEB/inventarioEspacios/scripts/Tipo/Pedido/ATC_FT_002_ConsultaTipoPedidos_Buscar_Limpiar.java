package WEB.inventarioEspacios.scripts.Tipo.Pedido;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.TipoPedidoIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_002_ConsultaTipoPedidos_Buscar_Limpiar extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_002_ConsultaTipoPedidos_Buscar_Limpiar_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		String nomTipoPed1 = data.get("NombreTipoPedido1");
		String nomTipoPed2 = data.get("NombreTipoPedido2");
		
		testCase.setTest_Description("Consulta Tipo de Pedidos_Boton Buscar-Limpiar - "+perfil+" "+negocio);
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal");
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);	
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		TipoPedidoIEP tp = new TipoPedidoIEP(u, testCase);
		
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
		
		assertTrue(verEncabezado, "No Muestra la informaci�n correcta");
		
		
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
			
		boolean visualizarCampos = tp.visualizarCampos();
		System.out.print("\nValidacion campos: "+visualizarCampos+"\n");
		assertTrue(visualizarCampos, "No se abre la pantalla y no se muestran los campos");
		
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Realizar la busqueda capturando <Tipo_Pedido>");
		
		tp.typeNombreTipoPedido(nomTipoPed1);
		
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("El sistema unicamente permitir� capturar valores alfanumericos en el campo de filtro.");
        
		boolean val = tp.validarNombreTipoPedido(nomTipoPed1);
		System.out.print("\nValidacion campo: "+val+"\n");
		assertTrue(val, "No se capturo el tipo de pedido y/o el valor no es alfanumerico");
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("Dar clic en el bot�n Buscar");
				
		tp.clickBuscarBtn();

		
		/**
		 *                                             PASO 11
		 ******************************************************************************************/
		addStep("El sistema muestra los tipo de pedido que coinciden con el criterio de busqueda, por cada registro se mostrar� un checkbox con la siguiente informaci�n:\r\n" + 
				"ID Tipo de Pedido: El sistema asigna en autom�tico el ID cuando se da de alta un tipo de pedido\r\n" + 
				"Nombre: Muestra el nombre con el que se dio de alta el tipo de Pedido\r\n" + 
				"Tipo de POP: Muestra el nombre del tipo de pop asociado al tipo de pedido\r\n" + 
				"Periodos Visibles: Muestra la cantidad de periodos que se ingresaron en el alta del tipo de pedido\r\n" + 
				"Comentarios: Muestra un campo de texto para ingresar informaci�n \r\n" + 
				"Los siguientes botones por cada tipo de pedido:\r\n" + 
				"Eliminar: Al dar clic sobre este bot�n deber� aparecer un mensaje \"�Est� seguro de eliminar el registro? y las opciones Aceptar / Cancelar. Al dar clic en Aceptar se cierra la ventana del mensaje, se borra el registro y se actualiza el listado. Al dar clic en Cancelar se cierra la ventana del mensaje y regresa al listado. \r\n" + 
				"Editar: Abre la p�gina Edici�n de Tipos de Pedido\r\n" + 
				"Imagen: Al dar clic sobre este bot�n abre una p�gina con las opciones");

		boolean validarResultados1 = tp.visualizarResultadosBusqueda(nomTipoPed1);	
		System.out.print("\nSe muestran los resultados de la busqueda: "+validarResultados1);
		assertTrue(validarResultados1, "No se muestran los resultados de la busqueda");
		
	
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("Dar clic en el bot�n Limpiar");
		
		tp.clickLimpiarBtn();
		
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		addStep("El sistema limpia la pantalla, ocultando nuevamente los resultados de busqueda que se hab�an obtenido.");
		
		boolean valFiltrosVacios = tp.validarLimpiezaFiltrosGrid(nomTipoPed1);	
		System.out.print("\nSe limpian los datos de los filtros: "+valFiltrosVacios+"\n");
		assertTrue(valFiltrosVacios, "No se limpian los filtros ni los resultados de la busqueda");
		
		
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
		addStep("Realizar la busqueda capturando <Tipo_Pedido>  y dar clic en Buscar");
		
		tp.typeNombreTipoPedido(nomTipoPed2);	
		tp.clickBuscarBtn();
		
		/**
		 *                                             PASO 15
		 ******************************************************************************************/
		addStep("El sistema muestra los tipo de pedido que coinciden con el criterio de busqueda.");
		
		
        boolean validarResultados2 = tp.visualizarResultadosBusqueda(nomTipoPed2);	
		System.out.print("\nSe muestran los resultados de la busqueda: "+validarResultados2+"\n");
		assertTrue(validarResultados2, "No se muestran los resultados de la busqueda");


	
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