package WEB.cnt.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.reporteCNT.om.Catalogo_Dominios;
import WEB.reporteCNT.om.Catalogo_Entidades;
import WEB.reporteCNT.om.MenuPrincipal_ReportesCNT;
import WEB.reporteCNT.om.Modificar_Dominio;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.TextDocumentUtil;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_333_Validar_cambios_en_Reportes_de_CNT extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void  ATC_FT_333_Validar_cambios_en_Reportes_de_CNT_test(HashMap<String, String> data) throws Exception {

		/*
		 * Variables
		 ******************************************************************************************/
		String user = GlobalVariables.USER_PORTAL_APPS_6006020;
		String password = PasswordUtil.decryptPassword(GlobalVariables.PASSWORD_PORTAL_APPS_6006020);
		String url = data.get("Url");


		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar a la aplicaci�n de Reportes CNT"); 
		
		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().window().maximize();
	    PortalApps pa = new PortalApps(u, testCase); 
	    MenuPrincipal_ReportesCNT mn = new MenuPrincipal_ReportesCNT(u, testCase); 
	    Catalogo_Entidades ce = new Catalogo_Entidades(u, testCase); 
	    Catalogo_Dominios cd = new Catalogo_Dominios(u, testCase); 
	    Modificar_Dominio md = new Modificar_Dominio(u, testCase); 
	    
		u.get(url);
		//u.hardWait(3); // tiempo en segundos
		pa.proceedToPortal();
		pa.cicloLogin(user, password);	
		
		pa.clickPortalReporteCNT();
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep(" Se abre la aplicaci�n de Reportes CNT donde se muestran las opciones de:\r\n" + 
				"	Administrador Funcional\r\n" + 
				"    Usuario Consulta\r\n" + 
				"    Administrador T�cnico");
		
		boolean opciones = mn.visualizarMenu();
		
		assertTrue(opciones,"No se muestran todas las opciones del menu");
		

		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Usuario selecciona la opci�n de \"Catalogo de entidades\" dentro del men� de Administrador T�cnico");
		
		mn.clickAdministradorTecnico();
		mn.clickCatalogoEntidades();
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep(" Sistema muestra la pantalla de Cat�logo de Entidades con las entidades de PLAZAS_ADMON Y TIENDAS_ADMON");
		
		boolean valCatEnt = false;
		
		if(ce.visualizaPantallaCatalogosEntidades()==true &&
				ce.visualizarEntidades() == true) {
			valCatEnt = true;
		}
		
		assertTrue(valCatEnt,"No se muestran la pantalla Cat�logo de Entidades o las entidades especificadas");
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Usuario selecciona Ver dominio de la entidad de TIENDAS_ADMON");
		
		ce.clickVerDominio("TIENDAS_ADMON");
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Sistema muestra la pantalla de Cat�logo de Dominios, mostrando todos los dominios relacionados a TIENDAS_ADMON");
		
		boolean valCatDom = false;
		
		valCatDom = cd.visualizarDominios("TIENDAS_ADMON");
		
		assertTrue(valCatDom,"No se muestran la pantalla Cat�logo de Dominios o los dominio relacionados a la entidad");
		
		
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Usuario busca el nombre de Dominio de DATOS FORMATO Y PROPIEDAD el cual al ser encontrado selecciona la acci�n de Modificar");
	
		cd.modificarDominio("DATOS FORMATO Y PROPIEDAD");
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Sistema muestra la pantalla de Modificar Dominio donde mostrara la informaci�n correspondiente al dominio de DATOS FORMATO Y PROPIEDAD ");
	
		boolean valModDom = false;
		
		valModDom = md.visualizaPantallaModificarDominio();
		
		assertTrue(valModDom,"No se muestran la pantalla Modificar Dominio");
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Usuario realiza la b�squeda de la consulta de Datos Admvo Formato y Propiedad y selecciona la acci�n de Modificar");
	
		md.modificarConsulta("Datos Admvo Formato y Propiedad");
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Sistema muestra el Query que se tiene actualmente para esta consulta");
	
		boolean valQuery1 = false;
		
		valQuery1 = md.visualizarQuery();
		
		assertFalse(valQuery1,"La consulta no tiene query");
				
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Usuario modifica el query actual, cambi�ndolo por el siguiente query");
	
        TextDocumentUtil tu = new TextDocumentUtil();
		
		String query = tu.readLines("C:\\Users\\48025\\Documents\\Prueba_ATC_FT_333.txt");
		
		md.modificarQuery(query);
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Usuario selecciona la opci�n de Probar Conexi�n y Probar Query para confirmar que se permita realizar el cambio");
	
		
		md.probarConexion_ProbarQuery();
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Sistema confirma que se realiza la conexi�n y que el Query puede guardarse");
	
		
		
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Usuario selecciona la opci�n de Siguiente");
	
		
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Sistema muestra la pantalla de Relacionar Atributos al Dominio");
	
		
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Usuario selecciona Continuar");
	
		
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Sistema muestra los registros que se podr�n relacionar al Dominio, mostrando las nuevas columnas correspondientes a la Nueva Segmentaci�n");
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Usuario realiza la configuraci�n de relaci�n de las nuevas columnas de la Nueva Segmentaci�n y selecciona Confirmar");
	
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Sistema muestra el mensaje de \"Atributos guardados satisfactoriamente\" junto con el bot�n de Aceptar");
	
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Usuario selecciona Aceptar");
	
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Sistema muestra el mensaje de \"Ha finalizado correctamente la creaci�n de la Entidad\" junto con el bot�n de Terminar");
	
	
		
		
		
    }

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "MTC_FT_333 Validar cambios en Reportes de CNT test";
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
		//u.getDriver().quit();
	}
	
}
