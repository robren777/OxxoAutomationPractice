package WEB.inventarioEspacios.scripts.Reglas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.inventarioEspacios.om.MenuPrincipalIEP;
import WEB.inventarioEspacios.om.MenuPrincipalOpcionesIEP;
import WEB.inventarioEspacios.om.Reglas_IEP;
import WEB.om.PortalApps;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_006_P2_Regla_obligatoria_Inventario extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_006_P2_Regla_obligatoria_Inventario_test(HashMap<String, String> data) throws Exception {
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
		String idRegla = data.get("Id_Regla");
		String NomReglaBuscar = data.get("Nombre_Regla_Buscar");
		String TipoConsulta = data.get("Tipo_Consulta");
		String plaza = data.get("Plaza");
		String nomTienda = data.get("NOMBRE_TIENDA");
		String cr = data.get("CR");
		String nomEspacioEquipo = data.get("Nombre_Espacio_Equipo");
		String cantEspacios = data.get("Cantidad_Espacios");
		
		
//		//									PASO 1
		addStep("Ingresar al portal https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf con el <Usuario>"); //viene en excel
		//u = new SeleniumUtil(new ChromeTest(), true); //IETest para usar internet explorer
		u = new SeleniumUtil(new MEdgeTest(), true);  // para usar navegador Edge
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
		addStep(" Verificar la informaci�n del usuario y que se muestre el negocio y el perfil.");
		MenuPrincipalIEP mp = new MenuPrincipalIEP(u, testCase);
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
		boolean verEncabezado = mp.verificarEncabezado(negocio);
		
		if(verEncabezado)
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es correcta");
		else
			testCase.addTextEvidenceCurrentStep("La informaci�n del negocio, periodo y estatus del periodo es incorrecta");
		
		assertTrue(verEncabezado, "No se muestra la informaci�n esperada");
		
		//                                 PASO 6
		addStep("Seleccionar menu ->Administrador->Reglas.");
		Reglas_IEP dc = new Reglas_IEP(u, testCase);
		mp.clickAdministracion();
		mp.clickReglas();
		
		//                                 PASO 7
		addStep("Se visualizan los filtros y campos:\r\n"
				+ "Nombre: \r\n"
				+ "Descripci�n:\r\n"
				+ "Estatus:\r\n"
				+ "Fecha Creaci�n: \r\n"
				+ "Botones: Nuevo, Exportar, Limpiar y buscar\r\n"
				+ "Id Regla: Muestra el ID que le asign� autom�ticamente el sistema\r\n"
				+ "Nombre: Muestra el nombre que se le asign� a la regla\r\n"
				+ "Descripci�n: Muestra la descripci�n que se le asign� a la regla\r\n"
				+ "Obligatoria: Muestra el valor Si / No que se le asign� desde el alta de la regla\r\n"
				+ "Estatus: Muestra el valor Activo / Inactivo que se le asign� desde el alta de la regla\r\n"
				+ "Fecha Creaci�n: Muestra la fecha en que se cre� la regla\r\n"
				+ "Por cada regla deber� mostrar las acciones Eliminar y Editar \r\n"
				+ "\r\n"
				+ "\r\n"
				+ "");
		dc.clickIdConsultaReglas();

		//                                 PASO 8
		addStep("Seleccionar la regla obligatoria a validar y dar al bot�n editar(L�piz)");
		Boolean band = false;
		dc.clickIdReglatxt(NomReglaBuscar,band);		
		dc.clickBotonBuscar();
		dc.clickSelReglaEditar(idRegla);
		
		//                                 PASO 9
		addStep("Se abre la pantalla de edici�n y validar la regla guardada en la pesta�a condici�n, Postcondici�n, aplicar a y mensaje.");
		dc.clickPesCondicion();

		//                                 PASO 10
		addStep("Ingresar al inventario de espacios y equipos, realizar la b�squeda de alguna plaza y tienda que aplique la regla.");
		mp.clickEspacios();
		mp.clickConsultaInventarios();
		dc.clickIngresarEspaciosEquipos(TipoConsulta,plaza,nomTienda);

		//                                 PASO 11
		addStep("El sistema mustrea el resultado de la busqueda");
		dc.clickResultBusquedaPlazaTda();

		//                                 PASO 12
		addStep("Buscar los espacios que apliquen la regla y cambiar los valores para validar la Regla obligatoria.");
		dc.clickCambiaValoresEspacios(nomEspacioEquipo,cantEspacios);

//    									  PASO 13	
		addStep("El sistema muestra mensaje de Regla Obligatoria , no deja a�adir el valor y resetea a las Postcondici�n (si existe). La regla no se a�ade al reporte de reglas no cumplidas por tienda.");
		u.hardWait(3);
        Boolean validacion = dc.existeMensajeReglaObligatoria();
	
		if (validacion == true) {
			dc.clickMensajeReglaObligatoria();		
		} else if(validacion == false) {
			testCase.addScreenShotCurrentStep(u, "clickMensajeReglaObligatoria");
		}
		
		assertTrue(validacion, "El sistema no muestra el mensaje de Regla Obligatoria");


	}
 
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_006_P2_Regla_obligatoria_Inventario";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_P2_Regla_obligatoria_Inventario_test";
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
