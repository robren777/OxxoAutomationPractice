package WEB.I123057_PagoQRspin_MVP3.scripts;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import WEB.I123057_PagoQRspin_MVP3.om.PagoQRspin_EBS;
import WEB.I123057_PagoQRspin_MVP3.om.PagoQRspin_MVP3;
import WEB.util.pageObjectModel.EBSPaginaInicial;

/**
 * DESCRIPCION: 
 * Se ingresaran datos erroneos al generar reporte de carta y se mostrara en el log generado por EBS el mensaje correspondiente
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-13-Validar mensaje 'No se encontro informacion con los parametros agregados' en los logs de aplicativo EBS
 * 
 * PREREQUISITOS:
 *
 *1.Contar con la configuracion correcta para poder realizar dispersiones en la aplicación Oracle Applications
2.Contar con transacciones del mes en curso ya realizadas (desde punto de venta) en el transcurso de las fechas que se realizara la dispersion
3.Contar con los permisos y accesos necesarios para visualizacion de contenido contable y creacion de dispersiones
4.Contar con los numeros de cuenta habilitados para las pruebas (Se necesita apoyo de Contabilidad)
5.Contar con el concurrente XXFC-Proceso de Dispersión de Pago QR
 *  
 *  INSUMOS
 *  Tener dias con vacio en las transacciones con QR SPIN
 *  
 * @author Juan Gerardo Lopez
 * @date 29/Julio/2025 
 * 
 **/

public class ATC_FT_155_ValidarMensajeNoSeEnControInformacionConLosParametrosAgregadosEnLosLogsDeAplicativoEBS extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_155_ValidarMensajeNoSeEnControInformacionConLosParametrosAgregadosEnLosLogsDeAplicativoEBS_test (HashMap <String, String> data) throws Exception {
	
		testCase.setProject_Name("MVP3 Pago con QR Spin");
		
		/* Utils**************************************************************/
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PagoQRspin_MVP3 pqrs = new PagoQRspin_MVP3(u, testCase);
		EBSPaginaInicial ebsini = new EBSPaginaInicial(u, testCase);
		PagoQRspin_EBS pqrseb = new PagoQRspin_EBS(u, testCase);
		
		/*Definición de Variables*/
		String URL = data.get("url");
		String username = data.get("username");
		String password = data.get("password");
		String responsabilidad = data.get("responsabilidad");
		String concurrente = data.get("concurrente");
		String fechaini = data.get("fechaini");
		String fechafin = data.get("fechafin");
		String compania = data.get("companía");
		String cuentaservicio = data.get("cuentaservicio");
		
		By carpetaInformes = By.xpath("//div[@class='textdivresp' and text()='Informes']");
		By carpetaStandar = By.xpath("//div[@class='textdiv' and text()='Standard']");
		By carpetaRequest= By.xpath("//div[@class='textdivresp' and text()='Request']");
		
		//Script Code Begin
		
		System.out.println("***************PASO 01********************");
		addStep("Ingresar a portal EBS");
		pqrseb.ingresarEBS(URL);
		pqrseb.setLanguage();
		
		System.out.println("***************PASO 02********************");
		addStep("Ingresar credenciales para inicio de sesion");
		pqrseb.setCredentials(username, password);
		
		System.out.println("***************PASO 03********************");
		addStep("Conectarse e ingresar al portal EBS");
		pqrseb.clickLogIn();
		
		System.out.println("***************PASO 04********************");
		addStep("Ingresar a la responsabilidad 10TIJ-CONSULTA y Descargar aplicativo EBS");
		pqrseb.scrollToElementIfPresentGL17ADMConsultas();
		pqrseb.clickOnResponsabilidad(responsabilidad);
		pqrseb.clickOnInformesSolicitudStandard(carpetaInformes, carpetaRequest, carpetaStandar);
		
		System.out.println("***************PASO 05********************");
		addStep("Abrir aplicativo EBS descargado");
		pqrseb.abrirAppletChrome();
		u.hardWait(5);
		u.quit();
		
		System.out.println("***************PASO 06********************");
		addStep("Abrir Nueva Solicitud");
		pqrseb.aceptarNewSolicitud();
		
		System.out.println("***************PASO 07********************");
		addStep("Ingresar el tipo de solicitud XXFC - Reporte Consolidado de Cartas Plaza");
		pqrseb.typeConcurrente(concurrente);
		
		System.out.println("***************PASO 08********************");
		addStep("Agregar los parametros correspondientes del Reporte Conolidado de Carta Plaza");
		pqrseb.paramCartaOS(compania, cuentaservicio, fechaini, fechafin);
		
		System.out.println("***************PASO 09********************");
		addStep("Ejecutar proceso de Reporte de Carta Plaza");
		pqrseb.clickEjecutar();
		
		System.out.println("***************PASO 10********************");
		addStep("Se da clic en boton No deseo ejecutar otra solicitud");
		pqrseb.clicNo();
		
		System.out.println("***************PASO 11********************");
		addStep("Ingresar a Solicitudes realizadas");
		pqrseb.verMenuSol();
		
		System.out.println("***************PASO 12********************");
		addStep("Dar clic en boton encontrar");
		pqrseb.clicEncontrar();
		
		System.out.println("***************PASO 13********************");
		addStep("Descargar reporte de carta plaza con boton Visualizar Reporte");
		u.hardWait(5);
		pqrseb.validRequestCompletCartaOS();
		
		System.out.println("***************PASO 14********************");
		addStep("Visualizar Registro/Log");
		pqrseb.clicVRegistro();
		u.hardWait(3);
		
		System.out.println("***************PASO 15********************");
		addStep("Validar que en el log se encuentre la leyenda: No se encontró información con los parámetros ingresados");
		pqrseb.validLogContainMsjError();
		
		//Cerrar aplicaciones
		pqrseb.killProcess(new String[] {"jp2launcher.exe", "chrome.exe"});
		
		
		
		//Script Code End
	}
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1.Contar con la configuracion correcta para poder realizar dispersiones en la aplicación Oracle Applications\r\n"
				+ "2.Contar con transacciones del mes en curso ya realizadas (desde punto de venta) en el transcurso de las fechas que se realizara la dispersion\r\n"
				+ "3.Contar con los permisos y accesos necesarios para visualizacion de contenido contable y creacion de dispersiones\r\n"
				+ "4.Contar con los numeros de cuenta habilitados para las pruebas (Se necesita apoyo de Contabilidad)\r\n"
				+ "5.Contar con el concurrente XXFC-Proceso de Dispersión de Pago QR";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Gerardo Lopez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_155_ValidarMensajeNoSeEnControInformacionConLosParametrosAgregadosEnLosLogsDeAplicativoEBS";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se ingresaran datos erroneos al generar reporte de carta y se mostrara en el log generado por EBS el mensaje correspondiente";
	}
}