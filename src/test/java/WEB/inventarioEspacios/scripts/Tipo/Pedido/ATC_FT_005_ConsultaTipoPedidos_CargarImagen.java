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
import utils.sql.SQLResult;

public class ATC_FT_005_ConsultaTipoPedidos_CargarImagen extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")

	public void ATC_FT_005_ConsultaTipoPedidos_CargarImagen_Test(HashMap<String, String> data) throws Exception {
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
		String idTipoPed = data.get("IdTipoPedido");
		String rutaImg = data.get("RutaImagen");
		
		testCase.setTest_Description("Consulta Tipo Pedidos - Cargar Imagen - "+perfil+" "+negocio);
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
		addStep("Dar clic en el icono de Imagen");
		
		if(!nomTipoPed.isEmpty()) 
			tp.clickImagenIconPedido(nomTipoPed);
		if(!idTipoPed.isEmpty()) 
			tp.clickImagenIconPedido(idTipoPed);
		if(!nomTipoPed.isEmpty() && !idTipoPed.isEmpty()) 
			tp.clickImagenIconPedido();
		/**
		 *                                             PASO 9
		 ******************************************************************************************/
		addStep("El sistema abre una p�gina mostrando el siguiente contenido habilitado:\r\n" + 
				"Seleccionar Archivo: Abre el explorador de Windows, el usuario debe seleccionar una imagen para cargarse y dar clic en Abrir\r\n" + 
				"Aceptar: Al dar clic sobre este bot�n se carga la imagen seleccionada. Si se desea descartar el archivo, el usuario deber� cerrar la p�gina sin dar clic en Cargar. \r\n" + 
				"Cancelar.");
		
		boolean valVentanaImg = tp.validarVentanaSubirImagen();		
		
		assertTrue(valVentanaImg, "No se muestra la ventana con la informaci�n esperada");
		
		
		/**
		 *                                             PASO 10
		 ******************************************************************************************/
		addStep("Dar clic en Seleccionar Archivo y seleccionar imagen para cargar y dar clic en Abrir");
		
		tp.uploadImg(rutaImg);
		
		/**
		 *                                             PASO 12
		 ******************************************************************************************/
		addStep("El sistema cargar� temporalmente la imagen seleccionada, mostrando nombre, fecha y tama�o de la imagen cargada.");
		
		String [] array = rutaImg.split("\\\\");
		
		boolean valCambioImg = tp.validarImagenCargada(array[array.length-1]);
			
		assertTrue(valCambioImg, "No se muestra la ventana con la nueva imagen e informaci�n esperada");
		
		
		/**
		 *                                             PASO 13
		 ******************************************************************************************/
		addStep("Dar clic en Aceptar");
		
		tp.clickBtnAceptarImagen();

		
		/**
		 *                                             PASO 14
		 ******************************************************************************************/
		addStep("El sistema deber� cargar la imagen seleccionada exitosamente.");
		
		boolean validarMsg = tp.validarMsgImgCargada();
		
		assertTrue(validarMsg, "No se cambio la imagen");
		
		
		/**
		 *                                             PASO 15
		 ******************************************************************************************/
		//addStep("Ingesar al FileSystem del inventario de espacios y validar que la imagen se guarde.");
		//Este paso queda fuera de alcance de automatizacion
		
		
	
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
		return "ATC_FT_005_ConsultaTipoPedidos_CargarImagen_Test";
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