package WEB.I123057_PagoQRspin_MVP3.scripts;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import modelo.TestCase;
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

import WEB.I123057_PagoQRspin_MVP3.om.PagoQRspin_MVP3;
import WEB.util.pageObjectModel.EBSPaginaInicial;
import WEB.I123057_PagoQRspin_MVP3.om.PagoQRspin_EBS;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.sikuli.script.Pattern;
import WEB.ebs.tools.LoginTool;
import WEB.util.GlobalVariables;
import WEB.util.SikuliMethodsUtil;
import utils.SikuliUtil;

/**
 * DESCRIPCION: 
 * Se realizara el proceso de dispercion manual mediante el aplicativo EBS en donde se podra ejecutar y descargar los archivos logs y otros archivos correspondientes
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-144-Ejecutar el proceso de dispersión en forma manual de Pago QR - FP TTYP1814 desde Aplicativo EBS
 * 
 * PREREQUISITOS:
 *1.Contar con la configuracion correcta para poder realizar dispersiones en la aplicación Oracle Applications
2.Contar con transacciones del mes en curso ya realizadas (desde punto de venta) en el transcurso de las fechas que se realizara la dispersion
3.Contar con los permisos y accesos necesarios para visualizacion de contenido contable y creacion de dispersiones
4.El JOB debe de estar creado en QA - instalado y planificado en ambiente de QA
5.Contar con los numeros de cuenta habilitados para las pruebas (Se necesita apoyo de Contabilidad)
6.Contar con el concurrente XXFC-Proceso de Dispersión de Pago QR
 *  
 *  INSUMOS
 *  Transacciones plasmadas en la BD de EBS
 *  Credenciales de aplicativo con permisos de realizar dispersion
 *  
 * @author Juan Gerardo Lopez
 * @date 08/Septiembre/2025 
 * 
 **/

public class ATC_FT_144_EjecutarElProcesoDeDispersionEnFormaManualDePagoQRSpinDesdeAplicativoEBS extends BaseExecution {

	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_144_EjecutarElProcesoDeDispersionEnFormaManualDePagoQRSpinDesdeAplicativoEBS_test (HashMap <String, String> data) throws Exception {
	
		
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
		String formapago = data.get("formapago");
		String fechaini = data.get("fechaini");
		String fechafin = data.get("fechafin");
		String usernameDsip = data.get("usernamedisp");
		
		By carpetaInformes = By.xpath("//div[@class='textdivresp' and text()='Informes']");
		By carpetaSolicitar = By.xpath("//div[@class='textdivresp' and text()='Solicitar']");
		By btnStandar = By.xpath("//div[@class='textdiv' and text()='Standard']");
		
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
		addStep("Ingresar a la responsabilidad 17ADM-SERVICIOS y Descargar aplicativo EBS");
		pqrseb.scrollToElementIfPresent17ADM();
		ebsini.clickOnResponsabilidad(responsabilidad);
		pqrseb.clickOnInformesSolicitudStandard(carpetaInformes, carpetaSolicitar, btnStandar);
		
		System.out.println("***************PASO 05********************");
		addStep("Abrir aplicativo EBS descargado");
		pqrseb.abrirAppletChrome();
		u.hardWait(5);
		u.quit();
		
		System.out.println("***************PASO 06********************");
		addStep("Abrir Nueva Solicitud");
		pqrseb.aceptarNewSolicitud();
		
		System.out.println("***************PASO 07********************");
		addStep("Ingresar el tipo de solicitud Proceso de dispersion");
		pqrseb.typeConcurrente(concurrente);
		
		System.out.println("***************PASO 08********************");
		addStep("Agregar los parametros correspondientes de la dispersion");
		pqrseb.typeFormaPago(formapago);
		pqrseb.typeFechaIni(fechaini);
		pqrseb.typeFechaFinal(fechafin);
		
		System.out.println("***************PASO 09********************");
		addStep("Aceptar los parametros agregados");
		pqrseb.aceptarParametros();
		
		System.out.println("***************PASO 10********************");
		addStep("Ejecutar proseso de dispersion de manera manual");
		pqrseb.clickEjecutar();
		
		System.out.println("***************PASO 11********************");
		addStep("Se da clic en boton No deseo ejecutar otra solicitud");
		pqrseb.clicNo();
		
		System.out.println("***************PASO 12********************");
		addStep("Ingresar a Solicitudes realizadas");
		pqrseb.verMenuSol();
		
		System.out.println("***************PASO 13********************");
		addStep("Dar clic en boton encontrar");
		pqrseb.clicEncontrar();
		
		System.out.println("***************PASO 14********************");
		addStep("Validar ejecucion de la Dispersion en BD");
		u.hardWait(5);
		pqrseb.validProcesoDispersionQRSPIN(usernameDsip, formapago);
		
		//Cerar aplicacion EBS
		pqrseb.closeAppEBS();
		
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
				+ "4.El JOB debe de estar creado en QA - instalado y planificado en ambiente de QA\r\n"
				+ "5.Contar con los numeros de cuenta habilitados para las pruebas (Se necesita apoyo de Contabilidad)\r\n"
				+ "6.Contar con el concurrente XXFC-Proceso de Dispersión de Pago QR";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Gerardo Lopez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_144_EjecutarElProcesoDeDispersionEnFormaManualDePagoQRSpinDesdeAplicativoEBS";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se realizara el proceso de dispercion manual mediante el aplicativo EBS en donde se podra ejecutar y descargar los archivos logs y otros archivos correspondientes";
	}
}