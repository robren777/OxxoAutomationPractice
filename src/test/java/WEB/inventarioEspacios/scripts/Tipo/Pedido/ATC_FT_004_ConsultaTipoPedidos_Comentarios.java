package WEB.inventarioEspacios.scripts.Tipo.Pedido;

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
import utils.selenium.MEdgeEspTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_004_ConsultaTipoPedidos_Comentarios extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_004_ConsultaTipoPedidos_Comentarios_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Utiler�as
		 ******************************************************************************************/	
		
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		String url = data.get("Url");
		String user = data.get("User");
		String password = "Comercio.36";//PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		String nomTipoPed = data.get("NombreTipoPedido");
		String idTipoPed = data.get("IdTipoPedido");
		
		testCase.setTest_Description("ConsultaTipoPedidos_Buscar_Limpiar - "+perfil+" "+negocio);
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal");
		
		u = new SeleniumUtil(new MEdgeEspTest(), true);
		PortalApps pa = new PortalApps(u, testCase);	
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
		TipoPedidoIEP tp = new TipoPedidoIEP(u, testCase);
		NuevoTipoPedidoIEP ntp = new NuevoTipoPedidoIEP(u, testCase);
		
		u.getDriver().manage().window().maximize();
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
			
		boolean visualizarTP = tp.visualizarCampos();
		
		System.out.print("\nValidacion campos pantalla Tipo Pedidos: "+visualizarTP+"\n");
		
		assertTrue(visualizarTP, "No se abre la pantalla y no se muestran los campos");
		/**
		 *                                             PASO 8
		 ******************************************************************************************/
		addStep("Dar clic en ID Tipo de Pedido");
		
        System.out.print("\nID ingresado: "+idTipoPed+"\n");
        
		tp.desplegarComentarios(idTipoPed);
		
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("El sistema muestra el campo Comentarios, el cual contiene los comentarios agregados al momento de crear el Tipo de pedido seleccionado.");
        
  
		boolean campoComentario =  tp.visualizarCampoComentario();
		
        System.out.print("\nValidacion que se muestre el comentario: "+campoComentario+"\n");
		
		assertTrue(campoComentario, "No se muestra el campo 'Comentario'");
		
		



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