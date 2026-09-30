package WEB.simoti_Colombia.scripts.ConsultarEditarProveedoresEDI;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.ConsultarEditarProveedoresEDI_EditarPlazasBuzones_SimotiColombia;
import WEB.simoti_Colombia.om.ConsultarEditarProveedoresEDI_SimotiColombia;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
/**Desc: Validar que se permita editar el Buzon
 * MTC-FT-005 Administrador_Consultar y Editar Proveedores EDI_Editar Buzon
 * Nota. Falta construir el paso 14
 *       Falta agregar los localizadores de los metodos construidos en los pasos 11, 12 y 13 y validar que funcionen
 *       Falta probar el funcionamiento del script con datos      
 * @author Andrea Contreras
 * @date 16 Marzo 2023
 */
public class ATC_FT_005_Administrador_Consultar_y_Editar_Proveedores_EDI_Editar_Buzon extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_Administrador_Consultar_y_Editar_Proveedores_EDI_Editar_Buzon_test(HashMap<String, String> data) throws SQLException {
		
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
		String buzon = data.get("BuzonEditar");
		u = new SeleniumUtil(new MEdgeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		ConsultarEditarProveedoresEDI_SimotiColombia cep = new ConsultarEditarProveedoresEDI_SimotiColombia(u, testCase);
		ConsultarEditarProveedoresEDI_EditarPlazasBuzones_SimotiColombia epb = new ConsultarEditarProveedoresEDI_EditarPlazasBuzones_SimotiColombia(u, testCase);
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
				"*Proveedor: <Deshabilitado>\r\n" + 
				"El sistema habilita el campo Cedis  el campo Proveedor continua deshabilitado");
		
		cep.capturaFiltros(plaza1, cedis1, prov1);
		
		boolean validacion1 = cep.validacionCampos("2");
		assertTrue(validacion1, "El sistema no habilita el campo Cedis y/o el campo Proveedor se habilita");
		
		// Paso 6
		addStep("El usuario selecciona o captura valor en los filtros que se indique:\r\n" + 
				"*Plaza: <Seleccionar Todas >\r\n" + 
				"*Cedis: <Seleccionar un Cedis>\r\n" + 
				"*Proveedor: <Deshabilitado>\r\n" + 
				"El sistema habilita el campo Cedis, deshabilita el campo Plaza y el campo Proveedor sigue deshabilitado");
		
		mp.clickTransaccionesElectronicasTDC();
		u.hardWait(2);
		mp.clickConsultarEditarProveedoresEDI();
		u.hardWait(1);
		
		cep.capturaFiltros(plaza2, cedis2, prov2);
		
		boolean validacion2 = cep.validacionCampos("3");
		assertTrue(validacion2, "El sistema no habilita el campo Cedis y/o no deshabilita el campo Plaza ni el campo Proveedor");
		
		// Paso 7
		addStep("El usuario selecciona o captura valor en los filtros que se indique:\r\n" + 
				"*Plaza: <Seleccionar Todas >\r\n" + 
				"*Cedis: <Seleccionar Todas > \r\n" + 
				"*Proveedor: <Seleccione una opci�n>\r\n" + 
				"El sistema habilita el campo Proveedor y el campo Plaza");
		
		mp.clickTransaccionesElectronicasTDC();
		u.hardWait(2);
		mp.clickConsultarEditarProveedoresEDI();
		u.hardWait(1);
		
		cep.capturaFiltros(plaza3, cedis3, prov3);
		
		boolean validacion3 = cep.validacionCampos("4");
		assertTrue(validacion3, "El sistema no habilita el campo Proveedor y/o el campo Plaza");
	
		
		
		// Paso 8
		addStep("El usuario selecciona o captura valor en los filtros que se indique:\r\n" + 
				"*Plaza: <Seleccionar Todas >\r\n" + 
				"*Cedis: <Seleccionar Todos> \r\n" + 
				"*Proveedor: <Seleccionar Todos> \r\n" + 
				"Pulsar el bot�n Consultar");
		
		mp.clickTransaccionesElectronicasTDC();
		u.hardWait(2);
		mp.clickConsultarEditarProveedoresEDI();
		u.hardWait(1);
		
		cep.capturaFiltros(plaza4, cedis4, prov4);
		
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
		addStep("El usuario valida que el sistema muestre los mismos resultados de la BD");
				
		String query = "SELECT DISTINCT CAST( ES.SUPPLIER AS CHAR(10)),\r\n" + 
				"CAST( ES.FILETYPE AS CHAR(10)) ,\r\n" + 
				"CAST( ES.STATUS AS CHAR(1) ),\r\n" + 
				"CAST(DECODE( ES.COST, 0, 'No', 1, 'Si', ES.COST )AS CHAR (3)),\r\n" + 
				"ID,\r\n" + 
				"CAST (DECODE( ES.SURTIRPORPIEZA, 0, 'Cajas', 1, 'Piezas', ES.SURTIRPORPIEZA ) AS CHAR(6)),\r\n" + 
				"CAST(DECODE( ES.FORMAT_TYPE, 1, 'Orden por caja', 2, 'Multitienda', ES.FORMAT_TYPE )AS CHAR(15)),\r\n" + 
				"ES.MAX_ATTEMPT,\r\n" + 
				"CAST( S.SUP_NAME AS CHAR(32))\r\n" + 
				"FROM WMUSER.WM_EDI_MAP_SUPPLIER ES, SUPS S\r\n" + 
				"WHERE ES.SUPPLIER = S.SUPPLIER";
		
		String query1 = "SELECT DISTINCT CAST( ES.SUPPLIER AS CHAR(10)),\r\n" + 
				"CAST( ES.FILETYPE AS CHAR(10)) ,\r\n" + 
				"CAST( ES.STATUS AS CHAR(1) ),\r\n" + 
				"CAST(DECODE( ES.COST, 0, 'No', 1, 'Si', ES.COST )AS CHAR (3)),\r\n" + 
				"ID,\r\n" + 
				"FROM WMUSER.WM_EDI_MAP_SUPPLIER ES, SUPS S\r\n" + 
				"WHERE ES.SUPPLIER = S.SUPPLIER";
		
		String query2 = "SELECT CAST (DECODE( ES.SURTIRPORPIEZA, 0, 'Cajas', 1, 'Piezas', ES.SURTIRPORPIEZA ) AS CHAR(6)),\r\n" + 
				"CAST(DECODE( ES.FORMAT_TYPE, 1, 'Orden por caja', 2, 'Multitienda', ES.FORMAT_TYPE )AS CHAR(15)),\r\n" + 
				"ES.MAX_ATTEMPT,\r\n" + 
				"CAST( S.SUP_NAME AS CHAR(32))\r\n" +
				"FROM WMUSER.WM_EDI_MAP_SUPPLIER ES,\r\n" + 
				" SUPS S\r\n" + 
				"WHERE ES.SUPPLIER = S.SUPPLIER";


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
		addStep("El usuario da clic en el link de Editar plaza y Buzones");
		
		//Falta validar si este metodo funciona
		//Falta agregar el localizador del link Editar
		boolean validarLink = cep.clickLinkOpcion(provId, "EDITAR_PLAZA_Y_BUZONES");
		
		assertTrue(validarLink, "No se dio click en el boton Editar plaza y Buzones");
		
		
		// Paso 12
		addStep("El usuario valida que en la pantalla de \"Editar Plaza y Buzones� se muestren los siguientes datos:\r\n" + 
				"1. Proveedor\r\n" + 
				"2. Agregar buz�n\r\n" + 
				"- Buz�n\r\n" + 
				"- Tipo\r\n" + 
				"- Calificador\r\n" + 
				"3. Lista de Buzones\r\n" + 
				"- Buz�n\r\n" + 
				"- Calificador\r\n" + 
				"- Tipo\r\n" + 
				"- Editar\r\n" + 
				"- Eliminar\r\n" + 
				"4. Agregar plaza\r\n" + 
				"- Plaza\r\n" + 
				"5. Agregar Cedis\r\n" + 
				"6. Cedis\r\n" + 
				"7. Lista de plazas y Cedis\r\n" + 
				"- Plaza/Cedis\r\n" + 
				"- Descripci�n\r\n" + 
				"- Eliminar");
		
		//Falta validar si este metodo funciona
		//Falta agregar los localizadores
		 boolean validarPantalla = epb.validarPantalla();
		 
		 assertTrue(validarPantalla, "No se muestran los datos");
		
		
		// Paso 13
		addStep("En la secci�n de Lista de Buzones, el usuario Editar un buzon:\r\n" + 
				"- Buz�n\r\n" + 
				"- Calificador\r\n" + 
				"- Tipo");
		
		//Falta validar si este metodo funciona
		//Falta agregar los localizadores
		epb.seleccionarOpcionDeUnBuzon(buzon, "EDITAR");
		
		
		// Paso 14
		addStep("El usuario confirma que se hayan editado los datos del buz�n de manera correcta.");					
		//falta construir la validacion de que se realizaron los cambios de manera correcta
		
		
		
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
		return "ATC_FT_005_Administrador_Consultar_y_Editar_Proveedores_EDI_Editar_Buzon_test";
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