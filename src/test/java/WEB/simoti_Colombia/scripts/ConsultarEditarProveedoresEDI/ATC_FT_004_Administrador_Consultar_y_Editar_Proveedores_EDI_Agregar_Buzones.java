package WEB.simoti_Colombia.scripts.ConsultarEditarProveedoresEDI;

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
import utils.sql.SQLUtil;
/**Desc: Validar que se permita agregar buzones 
 * ATC_FT_004_Administrador_Consultar_y_Editar_Proveedores_EDI_Agregar_Buzones
 * Nota. Falta construir el paso 14 y 10
 *       Falta agregar los localizadores de los metodos construidos en los pasos 7,8,9 y 13 y validar que funcionen
 *       Falta probar el funcionamiento del script con datos 
 * @author Andrea Contreras
 * @date 15 Marzo 2023
 */
public class ATC_FT_004_Administrador_Consultar_y_Editar_Proveedores_EDI_Agregar_Buzones extends BaseExecution{
	SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_Administrador_Consultar_y_Editar_Proveedores_EDI_Agregar_Buzones_test(HashMap<String, String> data) throws SQLException {
		
		/*
		 * Variables
		 ******************************************************************************************/
		SQLUtil dbRmsCol = new SQLUtil(GlobalVariables.DB_HOST_BDRMSCQA_QAVIEW, GlobalVariables.DB_USER_BDRMSCQA_QAVIEW, GlobalVariables.DB_PASSWORD_BDRMSCQA_QAVIEW);		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String URL = data.get("Url");
		String plaza = data.get("Plaza");
		String cedis = data.get("Cedis");
		String prov = data.get("Proveedor");
		String provId = data.get("ProveedorID");
		String buzon1 = data.get("Buzon1");
		String buzon2 = data.get("Buzon2");
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
		addStep("El usuario selecciona o captura valor en los filtros que se indique:\r\n" + 
				"*Plaza: <Seleccionar una plaza>\r\n" + 
				"*Cedis: <Deshabilitado> \r\n" + 
				"*Proveedor: <Deshabilitado> \r\n" +
				"Pulsar el bot�n Consultar\r\n" + 
				"El sistema deshabilita los campos Cedis, Proveedor y muestra la consulta");
		
		cep.capturaFiltros(plaza, cedis, prov);
		
		boolean validacion = cep.validacionCampos("1");
		assertTrue(validacion, "El sistema no deshabilita el campo Cedis y/o el campo Proveedor");
		
		cep.clickConsultarBtn();		
		
		// Paso 6
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
				
				
		// Paso 7
		addStep("El usuario da clic en el link de Editar plaza y Buzones");
		
		//Falta validar si este metodo funciona
		//Falta agregar el localizador del link Editar
		boolean validarLink = cep.clickLinkOpcion(provId, "EDITAR_PLAZA_Y_BUZONES");
		
		assertTrue(validarLink, "No se dio click en el boton Editar plaza y Buzones");
		
		
		// Paso 8
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
		
		// Paso 9
		addStep("El usuario da clic en Agregar Buz�n");
		
		//Falta validar si este metodo funciona
		//Falta agregar el localizador para seleccionar un buzon
		epb.seleccionarBuzon(buzon1);
		
		//Falta validar si este metodo funciona
		//Falta agregar el localizador
		epb.clickAgregarBtn();
		
		
		// Paso 10
		addStep("El usuario Agrega un buzon:\r\n" + 
				"- Buz�n\r\n" + 
				"- Tipo\r\n" + 
				"- Calificador");		
		//falta construir la validacion de que se realizaron los cambios de manera correcta
		
		
		// Paso 11 y 12
		addStep("El usuario da clic en Regresar");		
		testCase.addTextEvidenceCurrentStep("No Aplica");
		
		addStep("El usuario da clic para Agregar otro Buz�n");	
		testCase.addTextEvidenceCurrentStep("No Aplica");
		
		// Paso 13
		addStep("El usuario Agrega un buzon:\r\n" + 
				"- Buz�n\r\n" + 
				"- Tipo\r\n" + 
				"- Calificador");
		
		//Falta validar si este metodo funciona
		//Falta agregar el localizador para seleccionar un buzon
		epb.seleccionarBuzon(buzon2);
		
		//Falta validar si este metodo funciona
		//Falta agregar el localizador
		epb.clickAgregarBtn();	
		
		
		// Paso 14
		addStep("El usuario confirma que se hayan agregado los buzones de manera correcta.");				
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
		return "ATC_FT_004_Administrador_Consultar_y_Editar_Proveedores_EDI_Agregar_Buzones_test";
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