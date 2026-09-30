package WEB.simoti_Colombia.scripts.ConsultarEditarProveedoresEDI;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.ConsultarEditarProveedoresEDI_SimotiColombia;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
/**Desc: Validar que se permita visualizar el detalle de los datos del proveedor.
 * MTC-FT-003 Administrador_Consultar y Editar Proveedores EDI_Ver
 * Nota. Falta probar el funcionamiento del script con datos
 *       Falta validar que funcione el metodo para dar click en Ver y agregar el localizador
 * @author Andrea Contreras
 * @date 13 Marzo 2023
 */
public class ATC_FT_003_Administrador_Consultar_y_Editar_Proveedores_EDI_Ver extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Administrador_Consultar_y_Editar_Proveedores_EDI_Ver_test(HashMap<String, String> data) throws SQLException {
		
		/*
		 * Variables
		 ******************************************************************************************/
		SQLUtil dbRmsCol = new SQLUtil(GlobalVariables.DB_HOST_BDRMSCQA_QAVIEW, GlobalVariables.DB_USER_BDRMSCQA_QAVIEW, GlobalVariables.DB_PASSWORD_BDRMSCQA_QAVIEW);		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plaza1 = data.get("Plaza1");
		String cedis1 = data.get("Cedis1");
		String prov1 = data.get("Proveedor1");
		String plaza2 = data.get("Plaza2");
		String cedis2 = data.get("Cedis2");
		String prov2 = data.get("Proveedor2");
		String plaza3 = data.get("Plaza3");
		String cedis3 = data.get("Cedis3");
		String prov3 = data.get("Proveedor3");
		String plaza4 = data.get("Plaza4");
		String cedis4 = data.get("Cedis4");
		String prov4 = data.get("Proveedor4");
		String provId = data.get("ProveedorID");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		ConsultarEditarProveedoresEDI_SimotiColombia cep = new ConsultarEditarProveedoresEDI_SimotiColombia(u, testCase);
		
		// Paso 1
		addStep("El usuario ingresa a la aplicaci�n Access Control V3");
		
		u.get(URL);
		u.getDriver().manage().window().maximize();	
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal();			
		testCase.addScreenShotCurrentStep(u, "imagenLogin");
		
		// Paso 2
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n 'Iniciar Sesi�n'.");
		pa.cicloLogin(user, password);
		
		// Paso 3
		addStep("El usuario da clic en opci�n de SIMOTI.");
		pa.clickSimotiColombia();
		
		// Paso 4
		addStep("El usuario despliega el men�: Transacciones Electr�nicas " + 
				"y selecciona el reporte: Consultar y Editar Proveedores EDI");
		mp.clickTransaccionesElectronicasTDC();
		u.hardWait(2);
		mp.clickConsultarEditarProveedoresEDI();
		
		// Paso 5
		addStep("El usuario selecciona un valor en los filtros que se indique:\r\n" + 
				"*Plaza: <Seleccionar Todas >\r\n" + 
				"*Cedis: <Deshabilitado>\r\n" + 
				"*Proveedor: <Deshabilitado>" +
				"El sistema habilita el campo Cedis  el campo Proveedor continua deshabilitado");
		
		cep.capturaFiltros(plaza1, cedis1, prov1);
		
		boolean validacion = cep.validacionCampos("2");
		assertTrue(validacion, "El sistema no habilita el campo Cedis y/o el campo Proveedor no esta deshabilitado");

		
		// Paso 6
		addStep("El usuario selecciona o captura valor en los filtros que se indique:\r\n" + 
				"*Plaza: <Seleccionar Todas >\r\n" + 
				"*Cedis: <Seleccionar un Cedis>\r\n" + 
				"*Proveedor: <Deshabilitado> \r\n" + 
				"El sistema habilita el campo Cedis, deshabilita el campo Plaza y el campo Proveedor sigue deshabilitado");
		
		mp.clickTransaccionesElectronicasTDC();
		u.hardWait(2);
		mp.clickConsultarEditarProveedoresEDI();
		u.hardWait(1);
		
		cep.capturaFiltros(plaza2, cedis2, prov2);
		
		validacion = cep.validacionCampos("3");
		assertTrue(validacion, "El sistema no habilita el campo Cedis, no deshabilita el campo Plaza y/o el campo Proveedor no esta deshabilitado");

		
		// Paso 7
		addStep("El usuario selecciona o captura valor en los filtros que se indique:\r\n" + 
				"*Plaza: <Seleccionar Todas >\r\n" + 
				"*Cedis: <Seleccionar Todas > \r\n" + 
				"*Proveedor: <Seleccione una opci�n> \r\n" + 
				"El sistema habilita el campo Proveedor y el campo Plaza");
		
		mp.clickTransaccionesElectronicasTDC();
		u.hardWait(2);
		mp.clickConsultarEditarProveedoresEDI();
		u.hardWait(1);
		
		cep.capturaFiltros(plaza3, cedis3, prov3);
		
		validacion = cep.validacionCampos("4");
		assertTrue(validacion, "El sistema no habilita el campo Proveedor y/o el campo Plaza");

		
		// Paso 8
		addStep("El usuario selecciona o captura valor en los filtros que se indique:\r\n" + 
				"*Plaza: <Deshabilitado>\r\n" + 
				"*Cedis: <Seleccionar un CEDIS> \r\n" + 
				"*Proveedor: <Deshabilitado> \r\n" + 
				"Pulsar el bot�n Consultar \r\n"+ 
				"El sistema deshabilita los campos Plaza y  Proveedor y muestra la consulta");
		
		mp.clickTransaccionesElectronicasTDC();
		u.hardWait(2);
		mp.clickConsultarEditarProveedoresEDI();
		u.hardWait(1);
		
		cep.capturaFiltros(plaza4, cedis4, prov4);
		
		validacion = cep.validacionCampos("3");
	
		assertTrue(validacion, "El sistema no deshabilita los campos Cedis y Proveedor");
		
		cep.clickConsultarBtn();		
		
		// Paso 9
		addStep("El usuario valida que el sistema muestre los resultados de la consulta en las siguientes columnas, validar que se muestre el tipo de dato adecuado:\r\n" + 
				"1. No. (Tipo de dato: Number)\r\n" + 
				"2. Proveedor: (Tipo de dato: Number)\r\n" + 
				"3. Nombre (Tipo de dato: String)\r\n" + 
				"4. Tipo archivo (Tipo de dato: String)\r\n" + 
				"5. est�do (Tipo de dato: String)\r\n" + 
				"6. Editar (link)\r\n" + 
				"7. Ver (link)\r\n" + 
				"8. Editar plaza y buzones (link)\r\n" + 
				"9. Eliminar (link)");
				
		boolean columnas = cep.validarEncabezadosTablaResultados();
		assertTrue(columnas, "No se muestran las columnas");
		
		boolean resultados = cep.validarExistenResultados();		
		assertTrue(resultados, "No se muestran resultados de la consulta");
				
				
		// Paso 10
		addStep("El usuario valida que el sistema muestre los mismos resultados de la BD\r\n" + 
				"SELECT DISTINCT CAST( ES.SUPPLIER AS                  CHAR(10)),\r\n" + 
				"  CAST( ES.FILETYPE AS                                CHAR(10)) ,\r\n" + 
				"  CAST( ES.STATUS AS                                  CHAR(1) ),\r\n" + 
				"  CAST(DECODE( ES.COST, 0, 'No', 1, 'Si', ES.COST )AS CHAR (3)),\r\n" + 
				"  ID,\r\n" + 
				"  CAST (DECODE( ES.SURTIRPORPIEZA, 0, 'Cajas', 1, 'Piezas', ES.SURTIRPORPIEZA ) AS       CHAR(6)),\r\n" + 
				"  CAST(DECODE( ES.FORMAT_TYPE, 1, 'Orden por caja', 2, 'Multitienda', ES.FORMAT_TYPE )AS CHAR(15)),\r\n" + 
				"  ES.MAX_ATTEMPT,\r\n" + 
				"  CAST( S.SUP_NAME AS CHAR(32))\r\n" + 
				"FROM WMUSER.WM_EDI_MAP_SUPPLIER ES,\r\n" + 
				"  SUPS S\r\n" + 
				"WHERE ES.SUPPLIER = S.SUPPLIER\r\n" + 
				"AND S.SUPPLIER   IN\r\n" + 
				"  (SELECT SUP_ID\r\n" + 
				"  FROM POSUSER.WM_EDI_SUPPLIER_CEDIS@fcwmprd.femcom.net\r\n" + 
				"  WHERE CEDIS_ID ='10'\r\n" + 
				"  )\r\n" + 
				"ORDER BY 1;");
		
		String query = "SELECT DISTINCT CAST( ES.SUPPLIER AS CHAR(10)),\r\n" + 
				"  CAST( ES.FILETYPE AS CHAR(10)) ,\r\n" + 
				"  CAST( ES.STATUS AS CHAR(1) ),\r\n" + 
				"  CAST(DECODE( ES.COST, 0, 'No', 1, 'Si', ES.COST )AS CHAR (3)),\r\n" + 
				"  ID,\r\n" + 
				"  CAST (DECODE( ES.SURTIRPORPIEZA, 0, 'Cajas', 1, 'Piezas', ES.SURTIRPORPIEZA ) AS CHAR(6)),\r\n" + 
				"  CAST(DECODE( ES.FORMAT_TYPE, 1, 'Orden por caja', 2, 'Multitienda', ES.FORMAT_TYPE )AS CHAR(15)),\r\n" + 
				"  ES.MAX_ATTEMPT,\r\n" + 
				"  CAST( S.SUP_NAME AS CHAR(32))\r\n" + 
				"FROM WMUSER.WM_EDI_MAP_SUPPLIER ES,\r\n" + 
				"  SUPS S\r\n" + 
				"WHERE ES.SUPPLIER = S.SUPPLIER\r\n" + 
				"ORDER BY 1";
		
		String query1 = "SELECT DISTINCT CAST( ES.SUPPLIER AS CHAR(10)),\r\n" + 
				" CAST( ES.FILETYPE AS CHAR(10)) ,\r\n" + 
				" CAST( ES.STATUS AS CHAR(1) ),\r\n" + 
				" CAST(DECODE( ES.COST, 0, 'No', 1, 'Si', ES.COST )AS CHAR (3)),\r\n" + 
				" ID\r\n" +  
				"FROM WMUSER.WM_EDI_MAP_SUPPLIER ES,\r\n" + 
				" SUPS S\r\n" + 
				"WHERE ES.SUPPLIER = S.SUPPLIER\r\n" + 
				"ORDER BY 1";
		
		String query2 = "SELECT CAST (DECODE( ES.SURTIRPORPIEZA, 0, 'Cajas', 1, 'Piezas', ES.SURTIRPORPIEZA ) AS CHAR(6)),\r\n" + 
				" CAST(DECODE( ES.FORMAT_TYPE, 1, 'Orden por caja', 2, 'Multitienda', ES.FORMAT_TYPE )AS CHAR(15)),\r\n" + 
				" ES.MAX_ATTEMPT,\r\n" + 
				" CAST( S.SUP_NAME AS CHAR(32))\r\n" + 
				"FROM WMUSER.WM_EDI_MAP_SUPPLIER ES,\r\n" + 
				" SUPS S\r\n" + 
				"WHERE ES.SUPPLIER = S.SUPPLIER\r\n" + 
				"ORDER BY 1";


		SQLResult Resultado1 = executeQuery(dbRmsCol, query1);
		SQLResult Resultado2 = executeQuery(dbRmsCol, query2);
		System.out.print("consulta" + query);

		boolean validacionQuery = Resultado1.isEmpty();

		if (!validacionQuery) {
			testCase.addTextEvidenceCurrentStep("Existen registros");
		} else {
			testCase.addTextEvidenceCurrentStep("No existen registros");
		}
		
		testCase.addTextEvidenceCurrentStep(query);
		testCase.addQueryEvidenceCurrentStep(Resultado1, false);
		testCase.addQueryEvidenceCurrentStep(Resultado2, false);
		
		assertFalse(validacionQuery, "No existen registros en la base de datos");		

		
		// Paso 11
		addStep("El usuario da clic en el link de Ver");
		
		//Falta validar si este metodo funciona
		//Falta agregar el localizador del link Editar
		boolean validarLink = cep.clickLinkOpcion(provId, "VER");
		
		assertTrue(validarLink, "No se dio click en el boton Ver");
		
		// Paso 12
		addStep("El usuario valida que en la pantalla de \"Ver\" se muestren la siguiente informaci�n del Proveedor:\r\n" + 
				"1. Proveedor\r\n" + 
				"2. Nombre\r\n" + 
				"3. Tipo de archivo\r\n" + 
				"4. est�do\r\n" + 
				"5. Costo\r\n" + 
				"6. Formato\r\n" + 
				"7. Surtir en\r\n" + 
				"8. No. de reintentos\r\n" + 
				"9. Funciones\r\n" + 
				"10. Transferencias \r\n" + 
				"- Hora inicio del env�o\r\n" + 
				"- D�as del env�o \r\n" + 
				"11. Inventario\r\n" + 
				"- Hora inicio del env�o\r\n" + 
				"- D�as del env�o \r\n" + 
				"12. Buz�n\r\n" + 
				"13. Calificador\r\n" + 
				"14. est�do\r\n" + 
				"15. Direcci�n IP\r\n" + 
				"16. Puerto\r\n" + 
				"17. Ruta\r\n" + 
				"18. Usuario\r\n" + 
				"19. Contrase�a\r\n" + 
				"20. Plaza o Cedis activos\r\n" + 
				"21. Plaza/Cedis\r\n" + 
				"22. Descripci�n ");
		
		
		// Paso 13
		addStep("El usuario da clic en Regresar.");
		

		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_Administrador_Consultar_y_Editar_Proveedores_EDI_Ver_test";
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