package WEB.portal_Remesas.limiteDispersionRemesas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.portal_Remesas.om.GenerarReporteMontos;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_001_Revision_Pantalla_Configuracion_Portal_Remesas extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_Revision_Pantalla_Configuracion_Portal_Remesas_test(HashMap<String, String> data) throws Exception {
		
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = "Comercio*10";//PasswordUtil.decryptPassword(data.get("Password"));
		String proveedor = data.get("Proveedor");
		String proveedorBD = data.get("ProveedorBD");
		String opcionPantalla = data.get("OpcionPantalla");
		String valorParametro = data.get("ValorParametro");
		String msj1 = data.get("Mensaje1");
		String msj2 = data.get("Mensaje2");
		
		
		utils.sql.SQLUtil dbFCIAS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
		
		//Query forma de pago electronico
		String queryElectronicoFP = "select * from XXREM.XXREM_CONF_CAT_ATR A" + 
				" INNER JOIN XXREM.XXREM_CONF_ATR_COMPARTIDOS C ON A.PKEY_ID = C.FKEY_ID_CONF_CAT_ATR" + 
				" WHERE A.ATRIBUTO = '"+proveedorBD+"' AND C.VALOR = 'L'";
		
		String queryElectronicoFP1 = "select A.PKEY_ID, A.ATRIBUTO, C.PKEY_ID, C.FKEY_ID_CONF_CAT_ATR, C.ID from XXREM.XXREM_CONF_CAT_ATR A" + 
				" INNER JOIN XXREM.XXREM_CONF_ATR_COMPARTIDOS C ON A.PKEY_ID = C.FKEY_ID_CONF_CAT_ATR" + 
				" WHERE A.ATRIBUTO = '"+proveedorBD+"' AND C.VALOR = 'L'";
		
		String queryElectronicoFP2 = "select C.VALOR, C.ATRIBUTO_DESC, C.FECHA_MODIF, C.UNIDAD_NEGOCIO from XXREM.XXREM_CONF_CAT_ATR A" + 
				" INNER JOIN XXREM.XXREM_CONF_ATR_COMPARTIDOS C ON A.PKEY_ID = C.FKEY_ID_CONF_CAT_ATR" + 
				" WHERE A.ATRIBUTO = '"+proveedorBD+"' AND C.VALOR = 'L'";

		
		if(data.get("Test").contains("TransNetwork")) {
			testCase.setTest_Description("MTC-FT-002-Revision de Pantalla Configuracion Portal Remesas -Remesador TransNetwork Forma de pago electronica\r\n"
					+ "Revisar la pantalla de configuración para medios de pago de remesas");}
		
		if(data.get("Test").contains("WesternUnion")) {
			testCase.setTest_Description("MTC-FT-001-Revision de Pantalla Configuracion -Portal Remesas -Remesador Western Union Forma de pago electronica\r\n"
					+ "Revisar la pantalla de configuración para medios de pago de remesas");
		}
		
		if(data.get("Test").contains("MoneyGram")) {
			testCase.setTest_Description("MTC-FT-003-Revision de Pantalla Configuracion  -Portal Remesas -Remesador MoneyGram Forma de pago electronica\r\n"
					+ "Revisar la pantalla de configuración para medios de pago de remesas");
		}
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ir al portal de aplicaciones."); 
		//u = new SeleniumUtil(new ChromeTest(), true); 
		u = new SeleniumUtil(new MEdgeTest(), true); 
		GenerarReporteMontos rm = new GenerarReporteMontos(u, testCase);
		PortalApps pa = new PortalApps(u, testCase); 
		u.getDriver().manage().window().maximize();
		u.get(data.get("Url"));    
		u.hardWait(3);           
		
		pa.proceedToPortal();    // es para lo de la pagina no segura	
		pa.cicloLogin(user, password);
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		
		addStep("Visualizar la seccion \"Finanzas y Administracion\" "); 
	
		boolean validarSeccion = pa.validarModuloFinanzasAdministracion();
		
		if(validarSeccion) {
			testCase.addTextEvidenceCurrentStep("Se visualiza la sección 'Finanzas y Administracion'");
		}
		
		assertTrue(validarSeccion,"No se visualiza la seccion");
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("Presionar  enlace \"Portal Remesas\"");
		
		rm.clickSeleccionarPortalRemesas();

		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		                              
		addStep("Posicionarse en \"Configuracion\"");	
		
		rm.clickMenuConfiguracion();
		
		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		                                 
		addStep("Presionar enlace \"Atributos De Proveedores\"");
		
		rm.clickAtributosProveedor();
		u.hardWait(5);
		                      
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		                                
		addStep("Posicionarse en \"Seleccionar el Proveedor\"");	
		
        rm.MouseOverProveedor();
		
		/*
		 *                                             PASO 7
		 ******************************************************************************************/
		                                
		addStep("Presionar  opcion \"Seleccione un Proveedor\"");	
		
		rm.PresionarSeleccioneProveedor();
		                                
		/*
		 *                                             PASO 8
		 ******************************************************************************************/
		                              
		addStep("Visualizar los remesadores (Western Union, MoneyGram, TransNetwork)");	
		
		boolean validarRemesadores = rm.visualizaRemesadores("MoneyGram","TransNetwork","WESTERN UNION");
		
		if(validarRemesadores) {
			testCase.addTextEvidenceCurrentStep("Se visualizan los remesadores");
		}
		
		assertTrue(validarRemesadores,"No se muestran los remesadores");

		/*
		 *                                             PASO 9
		 ******************************************************************************************/
		                                 
		addStep("Presionar \""+proveedor+"\"");
		
		rm.seleccionarProveedor(proveedor);
		u.hardWait(10);
		/*
		 *                                             PASO 10
		 ******************************************************************************************/
		                                
		addStep("Presionar el boton \"parametros\"");
		
        boolean validarOpcion = rm.seleccionarOpcion(opcionPantalla);
		
		assertTrue(validarOpcion,"No se encontro la opcion "+opcionPantalla);	
		u.hardWait(10);
		/*
		 *                                             PASO 11
		 ******************************************************************************************/
		                               
		addStep("Presionar parametro \"Forma-Pago\"");	
		
		rm.PresionarFormaDePago();
		u.hardWait(10);	
		
		/*
		 *                                             PASO 12
		 ******************************************************************************************/
		                                
		addStep("Revisar opciones de \"Forma de pago\"");	
		
		boolean validarOpciones = rm.revisarOpcionesFormaPago();		
		assertTrue(validarOpciones,"No se muestra informacion en las opciones de 'Forma de pago'");
		
		/*
		 *                                             PASO 13
		 ******************************************************************************************/
		                                
		addStep("Presionar el boton de modificar para ver los valores del parametro");	
		
		rm.PresionarBtnModificar();
		u.hardWait(5);
		/*
		 *                                             PASO 14
		 ******************************************************************************************/
		                                
		addStep("Visualizar los valores del parametro");	
		
		boolean validarOP = rm.visualizarValoresParametro();

		if(validarOP) {
			testCase.addTextEvidenceCurrentStep("Se visualizan los valores del parametro");
		}
	
		assertTrue(validarOP,"No se encontro la opcion Valor del Parametro");	
		
		
		/*
		 *                                             PASO 15
		 ******************************************************************************************/
		                                
		addStep("Validar este opcion \"L\" en campo \"Valor del parametro\", en caso contrario teclear \"L\"");	
		
		boolean validarOpFP = rm.revisarOpcionesValorParametro("Efectivo","Electronico");

		//testCase.addScreenShotCurrentStep("visualizaOpciones");
		
		if(validarOpFP) {
			testCase.addTextEvidenceCurrentStep("Se visualiza la opcion  Electronico (L)");
		}
		
		assertTrue(validarOpFP,"No se visualiza la opcion Electronico");	
		
		rm.selectValorParametro(valorParametro);
		u.hardWait(1);
		
	
		/*
		 *                                             PASO 16
		 ******************************************************************************************/
		addStep("Presionar boton \"Actualizar\"");	
		
		rm.ClicBtnActualizar();
		u.hardWait(1);
		boolean validarMsj = rm.validarTextoDosMensajes(msj1, msj2);
		assertTrue(validarMsj,"No se mostro el mensaje de que se actualizo el registro");
			
		
		/*
		 *                                             PASO 17
		 ******************************************************************************************/
		                                
		addStep("Realizar conexion a base de datos en SQL Developer");	
		
		testCase.addTextEvidenceCurrentStep("Conexion a Base de Datos: " + GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		testCase.addTextEvidenceCurrentStep("Usuario: "+ GlobalVariables.DB_USER_FCIASQAS_PORTAL);
		testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + dbFCIAS.getConn());

		
		/*
		 *                                             PASO 18
		 ******************************************************************************************/
		                                
		addStep("Realizar Consulta");	
		
		System.out.println(GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
		System.out.println(queryElectronicoFP1);
		System.out.println(queryElectronicoFP2);
		
		SQLResult queryElec1 = executeQuery(dbFCIAS, queryElectronicoFP1);
		SQLResult queryElec2 = executeQuery(dbFCIAS, queryElectronicoFP2);
		
		testCase.addTextEvidenceCurrentStep(queryElectronicoFP);
		testCase.addQueryEvidenceCurrentStep(queryElec1, false);
		testCase.addQueryEvidenceCurrentStep(queryElec2, false);                      		                        	

		if(queryElec1.isEmpty() == true) {
			Assert.fail("No se realizo el cambio de la forma de pago en la base de datos");
		}
		
	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Revisar la pantalla de configuracion para medios de pago de remesas";
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
		return "Contar con la pantalla generada para la configuracion de los parametros";
	}
	
	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

}
