package WEB.portal_Remesas.limiteDispersionRemesas;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.portal_Remesas.om.GenerarReporteMontos;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_004_Configurar_Medios_Pago_Remesas_Portal_Remesas_Habilitar_Pago_Efectivo extends BaseExecution{

	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_004_Configurar_Medios_Pago_Remesas_Portal_Remesas_Habilitar_Pago_Efectivo_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utilerías
		 ******************************************************************************************/
		utils.sql.SQLUtil dbFCIAS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCIASQAS_PORTAL, GlobalVariables.DB_USER_FCIASQAS_PORTAL,GlobalVariables.DB_PASSWORD_FCIASQAS_PORTAL);
	

		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = "Comercio*10";//PasswordUtil.decryptPassword(data.get("Password"));
		String proveedor = data.get("Proveedor");
		String proveedorBD = data.get("ProveedorBD");
		String formadepago = data.get("FormaDePago");
		String opcionPantalla = data.get("OpcionPantalla");
		String msj1 = data.get("Mensaje1");
		String msj2 = data.get("Mensaje2");
		
		String FormaDePagoQuery = "select * from "
				+ "XXREM.XXREM_CONF_CAT_ATR A INNER JOIN XXREM.XXREM_CONF_ATR_COMPARTIDOS C ON A.PKEY_ID = C.FKEY_ID_CONF_CAT_ATR "
				+ "WHERE A.ATRIBUTO = '"+proveedorBD+"' AND C.VALOR = 'F'";
		
		String FormaDePagoQuery1 = "select A.PKEY_ID, A.ATRIBUTO, C.PKEY_ID, C.FKEY_ID_CONF_CAT_ATR, C.ID from "
				+ "XXREM.XXREM_CONF_CAT_ATR A INNER JOIN XXREM.XXREM_CONF_ATR_COMPARTIDOS C ON A.PKEY_ID = C.FKEY_ID_CONF_CAT_ATR "
				+ "WHERE A.ATRIBUTO = '"+proveedorBD+"' AND C.VALOR = 'F'";
		
		String FormaDePagoQuery2 = "select C.VALOR, C.ATRIBUTO_DESC, C.FECHA_MODIF, C.UNIDAD_NEGOCIO from "
				+ "XXREM.XXREM_CONF_CAT_ATR A INNER JOIN XXREM.XXREM_CONF_ATR_COMPARTIDOS C ON A.PKEY_ID = C.FKEY_ID_CONF_CAT_ATR "
				+ "WHERE A.ATRIBUTO = '"+proveedorBD+"' AND C.VALOR = 'F'";
	
		
		if(data.get("Test").contains("TransNetwork")) {
			testCase.setTest_Description("MTC-FT-005-Configurar Medios de Pago para Remesas-Portal Remesas Portal Remesador Habilitar Pago En Efectivo TransNetwork\r\n"
					+ "Revisar que la configuracion de los medios de pago se pueda realizar correctamente y se pueda realizar el cobro en Xpos");}
		
		if(data.get("Test").contains("WesternUnion")) {
			testCase.setTest_Description("MTC-FT-004-Configurar Medios de Pago para Remesas-Portal Remesas Portal Remesador Habilitar Pago En Efectivo WesternUnion\r\n"
					+ "Revisar que la configuracion de los medios de pago se pueda realizar correctamente y se pueda realizar el cobro en Xpos");
		}
		
		if(data.get("Test").contains("MoneyGram")) {
			testCase.setTest_Description("MTC-FT-006-Configurar Medios de Pago para Remesas-Portal Remesas Habilitar Pago En Efectivo-MoneyGram\r\n"
					+ "Revisar que la configuracion de los medios de pago se pueda realizar correctamente y se pueda realizar el cobro en Xpos");
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
	
		boolean validacion = pa.validarModuloFinanzasAdministracion();
		
		if(validacion) {
			testCase.addTextEvidenceCurrentStep("Se visualiza la seccion 'Finanzas y Administracion'");
		}
		
		assertTrue(validacion,"No se visualiza la seccion");
		
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
		
		addStep("Posicionarse en \"Atributos De Proveedores\"");
		
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
		                              
		addStep("Presionar  opcion \"Seleccione un Proveedor\"" );
		
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
		addStep("Presionar "+proveedor);
		
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
		addStep("Presionar opcion \"Forma de Pago\"");
		
		rm.PresionarFormaDePago();
		u.hardWait(10);
		
		/*
		 *                                             PASO 13
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
		 *                                             PASO 13
		 ******************************************************************************************/
		addStep("Visualizar los valores del parametro");
		
		boolean validarOP = rm.visualizarValoresParametro();
		
		if(validarOP) {
			testCase.addTextEvidenceCurrentStep("Se visualizan los valores del parametro");
		}
		assertTrue(validarOP,"No se encontro la opcion Valor del Parametro");	
	
		
		/*
		 *                                             PASO 14
		 ******************************************************************************************/
		addStep("Revisar las opciones Efectivo y Electronica ");
		
		//rm.VisualizarMediosDePago();
		
		boolean validarOpFP = rm.revisarOpcionesValorParametro("Efectivo","Electronico");

		if(validarOpFP) {
			testCase.addTextEvidenceCurrentStep("Se visualizan las opciones Efectivo y Electronico");
		}
		
		assertTrue(validarOpFP,"No se visualizan las opciones Efectivo y Electronico");	
	
		
		/*
		 *                                             PASO 15
		 ******************************************************************************************/
		addStep("Visualizar las opciones y seleccionar Efectivo");
		
		testCase.addScreenShotCurrentStep("visualizarOpciones");
		
		rm.selectValorParametro(formadepago);
		
		/*
		 *                                             PASO 16
		 ******************************************************************************************/
		addStep("Presionar el boton de actualizar para que realice el cambio");
		
		u.hardWait(5);
		rm.ClicBtnActualizar();
		u.hardWait(1);
		
		boolean validarMsj = rm.validarTextoDosMensajes(msj1, msj2);
		assertTrue(validarMsj,"No se mostro el mensaje de que se actualizo el registro");	
		
		/*
		 *                                             PASO 17
		 ******************************************************************************************/
		addStep("Realizar conexion a la base de datos en SQL Developer");
		
		testCase.addTextEvidenceCurrentStep("Conexion a Base de Datos: " + GlobalVariables.DB_HOST_FCIASQAS_PORTAL);
        testCase.addTextEvidenceCurrentStep("Usuario: "+ GlobalVariables.DB_USER_FCIASQAS_PORTAL);
        testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + dbFCIAS.getConn());
		
		/*
		 *                                             PASO 18
		 ******************************************************************************************/
		addStep("Realizar Consulta");
			
		SQLResult queryCambioFDP1 = executeQuery(dbFCIAS, FormaDePagoQuery1);
		SQLResult queryCambioFDP2 = executeQuery(dbFCIAS, FormaDePagoQuery2);
		
		testCase.addTextEvidenceCurrentStep(FormaDePagoQuery);
		testCase.addQueryEvidenceCurrentStep(queryCambioFDP1, false);
		testCase.addQueryEvidenceCurrentStep(queryCambioFDP2, false);
		
		System.out.print(FormaDePagoQuery1);
		
		assertFalse(queryCambioFDP1.isEmpty(), "No se realizo el cambio de la forma de pago en la base de datos");
}
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con la pantalla generada para la configuracion de los parametros";
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Revisar que la configuracion de los medios de pago se pueda realizar correctamente y se pueda realizar el cobro en Xpos ";
	}
	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}
	@Override
	public String setTestFullName() {
		return null;
	}
	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
	    u.getDriver().quit();
	}
}
