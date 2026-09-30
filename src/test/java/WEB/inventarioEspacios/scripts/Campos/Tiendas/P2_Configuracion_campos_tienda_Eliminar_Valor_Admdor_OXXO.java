package WEB.inventarioEspacios.scripts.Campos.Tiendas;


import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.Consulta_Movtos_Eliminacion_Campo_Tda;
import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class P2_Configuracion_campos_tienda_Eliminar_Valor_Admdor_OXXO extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_222_P2_Configuracion_campos_tienda_Eliminar_Valor_Admdor_OXXO_Test(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 ******************************************************************************************/
		SQLUtil dbFCIAS = new SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
//		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
		String negocio = data.get("Negocio");
		String perfil = data.get("Perfil");
		String idCampo = data.get("Id_Campo");
		String valor   = data.get("Valor");
		String idTienda = data.get("Id_Tienda");
		String TipoConsulta = data.get("Tipo_Consulta");
		String plaza = data.get("Plaza");
		String nomTienda = data.get("Nombre_Tienda");
		String descripValNvo = data.get("Descripcion_Valor_Nuevo");
		
		String oxxoQuery = "";
		String baraQuery = "";
		
		if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("MARCA")) {
			
			String consulta = "select * from XXINE.xxine_CAMPOS_TIENDA_VAL where id_campo = '%s'" ;
			oxxoQuery = String.format(consulta, idCampo);	//Concatena valor String a la consulta
			
		}else if (negocio.toUpperCase().equals("BARA")) {
			
			String consulta = "select * from XXINE_BA.xxine_CAMPOS_TIENDA_VAL where id_campo = '%s'" ;
			baraQuery = String.format(consulta, idCampo);	//Concatena valor String a la consulta
	
		}
		
		//									PASO 1
		
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
//		u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
		PortalApps pa = new PortalApps(u, testCase);  // crea objeto de locator
		u.get(data.get("Url"));    //toma el valor Url del data provider
		u.hardWait(3);           // tiempo en segundos
		
		pa.proceedToPortal();    // es para lo de la pagina no segura
		//pa.logginInventarioEspacios(user, password);
		pa.cicloLogin(user, password);
						
		//		                            PASO 2
		addStep("Seleccionar Inventario de Espacios");
		pa.clickPortalInventarioEspacios();
		//                                  PASO 3
		addStep("Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
		mp.verificarInformacion(user, negocio, perfil);
		
		//                                 PASO 4
		addStep(" Validar que visualice los MENUS:");
		MenuPrincipalOpcionesIEP mpo = new MenuPrincipalOpcionesIEP(u, testCase);
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

		//                                 PASO 5
		addStep("  Validar que se muestre en el encabezado de la pantalla el Negocio, periodo y estatus del periodo.");
		mp.verificarEncabezado("Administrador");
		
		//                                 PASO 6
		Consulta_Movtos_Eliminacion_Campo_Tda ec = new Consulta_Movtos_Eliminacion_Campo_Tda(u, testCase);
		addStep("Seleccionar men� ->Administraci�n->Campos Tienda.");
		ec.clickSbMenuCamposTienda();
		
		//								   PASO 7
		addStep("Al ingresar a la p�gina configuraci�n de campos tienda se muestra un listado con los 7 campos: \r\n"
				+ "Nombre: listado de Campo1 al Campo7, no se puede editar\r\n"
				+ "Descripci�n: campo alfanum�rico que permite al usuario ingresar el nombre del encabezado que se mostrar� en las tablas de tiendas, es obligatorio tener un valor en Descripci�n, si el campo no contiene un valor y se le da clic en la acci�n Grabar aparecer� el mensaje \"Descripci�n es obligatorio\".\r\n"
				+ "Si el usuario borra el valor de este campo y sale de la pantalla, el sistema en autom�tico le asigna el valor que ten�a anteriormente.\r\n"
				+ "La edici�n se hace directamente al dar clic dentro del campo, no existe una acci�n de editar. \r\n"
				+ "Visible: opci�n para seleccionar (check), por defecto no aparece seleccionada esta opci�n, al seleccionar el campo como visible el sistema deber� mostrar este campo con los valores configurados en las tablas donde se consulte informaci�n de las tiendas. \r\n"
				+ "El sistema muestra la acci�n de Grabar cuando se edita el campo Descripci�n o el campo Visible, al dar clic en grabar el sistema guarda la configuraci�n y muestra los cambios en las tablas de Tiendas\r\n"
				+ "Ver Datos: al dar clic en esta opci�n se abre la p�gina para configurar valores");
		ec.clickMuestraListadoCampos();		
		
		//      						   PASO 8
		addStep("Dar clic en Ver Datos de alg�n campo que este visible.");
		ec.clickVerDatos(idCampo);
		
		//	  							   PASO 9	
		addStep("Se habilita la pantalla de datos con los campos:\r\n"
				+ "Descripci�n y bot�n agregar");
		ec.clickPantallaValorNuevo();

		//		                           PASO 10
		addStep("Dar clic en el check del valor a eliminar");
		ec.clickSeleccionaValorEliminar(valor);
		
		//		                           PASO 11
		addStep("Se habilita el bot�n eliminar");
		ec.clickHabilitaEliminar();

		//		                           PASO 12
		addStep("Dar clic en el bot�n \"Eliminar\", El sistema muestra mensaje: �Esta seguro que desea eliminar el/los registro(s) seleccionado(s)?");
		ec.clickBtnEliminar();
		
		//		                           PASO 13
		addStep("Dar clic en confirmar");
		ec.clickConfirmarEliminar();
		
		//		                           PASO 14
		addStep("El sistema muestra mensaje: \"Se han eliminado los registros exitosamente.\" validar que ya no este el registro en la tabla XXINE_CAMPOS_TIENDA_VAL");
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		if (negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("MARCA")) {
			System.out.println("Entro por Oxxo/marca");
			System.out.println(oxxoQuery);
	
			SQLResult queryResult = executeQuery(dbFCIAS, oxxoQuery);
	
			boolean empty = queryResult.isEmpty();

			if (empty) {
		
				assertTrue(empty, "No se Elimino el valor Campo Tienda, en la tabla XXINE.xxine_CAMPOS_TIENDA_VAL");
	
			}						
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			testCase.addTextEvidenceCurrentStep("Se Elimino Correctamente el Registro, de la tabla XXINE.xxine_CAMPOS_TIENDA_VAL ");
		}else if (negocio.toUpperCase().equals("BARA")) {
			System.out.println("Entro por Bara");
			System.out.println(baraQuery);
	
			SQLResult queryResult = executeQuery(dbFCIAS, baraQuery);
	
			boolean empty = queryResult.isEmpty();

			if (empty) {
		
				assertTrue(empty, "No se Elimino el Valor Campo de Tienda Bara en la tabla XXINE_BA.xxine_CAMPOS_TIENDA_VAL");
	
			}						
			testCase.addQueryEvidenceCurrentStep(queryResult); //Agrega la consulta tal cual que se hace, siempre y cuando solo use una sola consulta
			testCase.addTextEvidenceCurrentStep("Se Elimino Correctamente el Registro Bara, de la tabla XXINE_BA.xxine_CAMPOS_TIENDA_VAL");
		}
		//		                           PASO 15
		addStep("Validar que los cambios se reflejen en  la creaci�n de Tiendas y datos generales de inventario.");
		Boolean bandera = false;
		ec.clickCambiosReflejados(idTienda,TipoConsulta,plaza,nomTienda,descripValNvo,bandera);

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_222_P2_Configuracion_campos_tienda_Eliminar_Valor_Admdor_OXXO_Test + Perfil + Negocio";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_222_P2_Configuracion_campos_tienda_Eliminar_Valor_Admdor_OXXO_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
//	@AfterMethod
//	public void afterMethod() {
//		u.getDriver().quit();
//	}


}
