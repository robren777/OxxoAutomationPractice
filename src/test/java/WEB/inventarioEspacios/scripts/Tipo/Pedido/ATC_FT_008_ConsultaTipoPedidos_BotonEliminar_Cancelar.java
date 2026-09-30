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
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_008_ConsultaTipoPedidos_BotonEliminar_Cancelar extends BaseExecution {
	
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_008_ConsultaTipoPedidos_BotonEliminar_Cancelar_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String periodo = data.get("Periodo");
		String nomTipoPed = data.get("NombreTipoPedido");
		String idTipoPed = data.get("IdTipoPedido");
		String rutaImg = data.get("RutaImagen");
		
	    testCase.setTest_Description("Consulta Tipo Pedidos - Bot�n Editar - Cancelar - "+perfil+" "+negocio);
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
			
	    boolean verInfo = mp.verificarInformacion(user, negocio, perfil);
		if(verInfo)
			testCase.addTextEvidenceCurrentStep("La informaci�n del usuario, negocio y perfil es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del usuario, negocio y/o perfil es incorrecta");
		
	//	assertTrue(verInfo, "No Muestra la informaci�n correcta");
		
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
		return "ATC_FT_008_ConsultaTipoPedidos_BotonEliminar_Cancelar_Test";
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
