package WEB.simoti_Colombia.scripts.ReporteDeSeguimientoDePendientes;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import WEB.simoti_Colombia.om.MenuPrincipal_SimotiColombia;
import WEB.simoti_Colombia.om.ReporteDeSeguimientoDePendientes_SimotiColombia;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
/**Desc: Validar que se desplieguen todos los valores y nomenclatura adecuados para los campos de filtros con usuario plaza asociado a un perfil de acceso a SIMOTI: PLAZA.  
 * MTC-FT-002 Plaza_Reporte de Seguimiento de Pendientes_Validar Filtros
 * Nota. Script construido, falta probarlo con datos en la pantalla.
 * @author Marichuy Due�as
 * @date Noviembre 2022*/
public class ATC_FT_002_ValidarFiltros extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarFiltros_test(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String url = data.get("Url");
		String plaza = data.get("Plaza");
		String fechaInicio = data.get("FechaInicio");
		String fechaFin = data.get("FechaFin");
		String fechaInicio2 = data.get("FechaInicio2");
		String fechaFin2 = data.get("FechaFin2");
		String fechaInicio3 = data.get("FechaInicio3");
		String fechaFin3 = data.get("FechaFin3");
		String msj1 = data.get("Mensaje1");
		String msj2 = data.get("Mensaje2");

		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase);
		MenuPrincipal_SimotiColombia mp = new MenuPrincipal_SimotiColombia(u, testCase);
		ReporteDeSeguimientoDePendientes_SimotiColombia rsp = new ReporteDeSeguimientoDePendientes_SimotiColombia (u, testCase);
		Boolean validacion, validacion2;
		fechaInicio = rsp.formatDate(fechaInicio);
		fechaFin = rsp.formatDate(fechaFin);
		fechaInicio2 = rsp.formatDate(fechaInicio2);
		fechaFin2 = rsp.formatDate(fechaFin2);
		fechaInicio3 = rsp.formatDate(fechaInicio3);
		fechaFin3 = rsp.formatDate(fechaFin3);
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal de aplicaciones");
	
		u.get(url);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "portalAplicaciones");
		pa.proceedToPortal();
		
        /**
		 *                                             PASO 2
		 ******************************************************************************************/
	
		addStep("El usuario ingresa usuario y contrase�a y da clic en el bot�n �Iniciar Sesi�n�.");
		
		pa.logginInventarioEspacios(user, password);
		
		/**
		 *                                             PASO 3
		 ******************************************************************************************/
		
		addStep("El usuario da clic en opci�n de SIMOTI.");
		
		pa.clickSimotiColombia();
		boolean validarMenusCedis = mp.validarMenuUsuarioCedis();	
		assertTrue(validarMenusCedis, "No se muestran los menus especificados");
		
		/**
		 *                                             PASO 4
		 ******************************************************************************************/
		
		addStep("El usuario despliega el men�: Transmisi�n de Datos y selecciona el reporte: Reporte "
				+ "Promociones");
		
		mp.clickTransmisionDatosPlazaCedisCentral();
		u.hardWait(3);
		mp.clickReportePromociones();
		
		validacion = rsp.validarPantalla();
		validacion2 = rsp.validarBotonespaginado();
		assertTrue(validacion && validacion2, "No se despliega la pantalla de Reporte Promociones, donde se muestran las secciones de: \r\n" + 
				"T�tulo del Reporte, Filtros, Bot�nes, Secci�n de Resultados y Secci�n de paginado");
		
		/**
		 *                                             PASO 5
		 ******************************************************************************************/
		
		addStep("El usuario valida que para el filtro:\r\n" + 
				"<filtro plaza>  \r\n" + 
				"Se mostrar� la plaza a la que pertenece el usuario Plaza sin posibilidad de cambio,\r\n" + 
				"Los valores a presentar deben ser con nomenclatura:  CR_TIENDA + DESCRIPCI�N.\r\n" + 
				"El listado de tiendas debe mostrarse con la siguiente nomenclatura: CR_TIENDA + DESCRIPCI�N ordenadas alfab�ticamente por el campo descripci�n.\r\n" + 
				"La lista de Tiendas deber� mostrarse de acuerdo al query:\r\n" + 
				"SELECT CR_TIENDA, DESC_TIENDA\r\n" + 
				"   FROM XX_CNT_PLAZAS_TIENDAS_SIMOTI\r\n" + 
				"   WHERE CR_PLAZA ='10CAN' ;\"");

		//boolean validarPlaza = rsp.validarFiltroPlaza();
		//assertTrue(validarPlaza, "No se muestra el filtro de plaza");
		
		validacion = rsp.selectPlaza(plaza);
		assertTrue(validacion,"No se encontro la plaza");

		
		/**
		 *                                             PASO 6
		 ******************************************************************************************/
		
		addStep("Fecha Inicio, aplique lo siguiente:  \n" +
				"1.- Por default deber� mostrarse la fecha del d�a anterior a la fecha actual. El formato deber� ser dd/mm/aaaa.\n" +
				"2.- El rango de fechas a consultar ser� en periodos m�ximo de 1 a�o\n" +
				"3.- Se podr� consultar informaci�n desde el a�o 2010 respetando el rango indicado,\"\n" +
				"\n");

	
		validacion = rsp.validarFiltroMes();
		assertTrue(validacion, "No se muestran correctamente el filtro mes");


		/**
		 *                                             PASO 7
		 ******************************************************************************************/

		addStep("El usuario valida que para el filtro:\n" +
				"* Fecha Fin, aplique lo siguiente:  \n" +
				"1.-Por default fecha actual. El formato deber� ser dd/mm/aaaa.\n" +
				"2.- El rango de fechas a consultar ser� en periodos m�ximo de 1 a�o\n" +
				"3.- Se podr� consultar informaci�n desde el a�o 2010 respetando el rango indicado,\"\n");

		validacion = rsp.validarFiltroMesFin();
		assertTrue(validacion, "No se muestran correctamente el filtro mes");

		/**
		 *                                             PASO 8
		 ******************************************************************************************/

		addStep("El usuario captura fecha inicio  mayor que fecha fin.El sistema valida que la fecha "
				+ "inicial es mayor que la fecha final, se deber� mostrar el mensaje �Estimado usuario,"
				+ " la Fecha Inicial no puede ser mayor a la Fecha Final�.");
		
		
		u.hardWait(5);
		rsp.selectDate("Inicio", fechaInicio);
		u.hardWait(2);
		rsp.selectDate("Fin", fechaFin);
		rsp.clickConsultar();	
        
		boolean validacionMsg1 = rsp.validarMensaje(msj1);	
		assertTrue(validacionMsg1, "No se muestra el mensaje: "+msj1);

		/**
		 *                                             PASO 9
		 ******************************************************************************************/

		addStep("\"El usuario selecciona un rango de fechas mayor a 1 a�o.\n" +
				"Fecha inicio: 01-01-2022\n" +
				"Fecha Fin: 02-02-2023\"\n" +
				"El sistema debe mostrar el mensaje: \r\n" + 
				"�Estimado usuario, el rango de fecha seleccionado no puede ser mayor a 1 a�o�.\"");
		u.hardWait(5);
		rsp.selectDate("Inicio", fechaInicio2);
		u.hardWait(2);
		rsp.selectDate("Fin", fechaFin2);
		rsp.clickConsultar();
			
	        
		boolean validacionMsg2 = rsp.validarMensaje(msj2);	
		assertTrue(validacionMsg2, "No se muestra el mensaje: "+msj2);

		/**
		 *                                             PASO 10
		 ******************************************************************************************/

		addStep("\"El usuario selecciona un rango de fechas a 1 a�o.\n" +
				"Fecha inicio: 10/01/2021   \n" +
				"Fecha Fin: 10/01/2022\"\n" +
				"El sistema debe aceptar correctamente el filtro fecha de la consulta.");
		u.hardWait(5);
		rsp.selectDate("Inicio", fechaInicio3);
		u.hardWait(2);
		rsp.selectDate("Fin", fechaFin3);
		rsp.clickConsultar();	
		
		boolean validacionResultados = rsp.validarExistanResultados();
		assertTrue(validacionResultados, "No se muestran resultados");

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido. Validar que se desplieguen todos los valores y nomenclatura adecuados para los campos de filtros con usuario plaza asociado a un perfil de acceso a SIMOTI: PLAZA.";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_ValidarFiltros_test";
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
