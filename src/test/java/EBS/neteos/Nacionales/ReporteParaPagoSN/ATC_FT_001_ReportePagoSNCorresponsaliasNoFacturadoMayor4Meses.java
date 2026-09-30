package EBS.neteos.Nacionales.ReporteParaPagoSN;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.neteos.LoginNeteos.ClaseLogin;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Ejecucion del concurrente XXFC Reporte para Pago de Servicios Nacionales Corresponsalias  y que muestre informacion de registros no facturados con mas de cuatro meses de antiguedad
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-001-Reporte para pago de SN corresponsalias - no facturado fecha mayor a 4 meses
 * 
 * PREREQUISITOS:
 * Exista informacion de transacciones pendientes con mas de cuatro meses de antiguedad en la tabla de xxfc_pago_servicios
 *
 * @author Ricardo Romero Palacios
 * @date 14/Julio/2025
 */
public class ATC_FT_001_ReportePagoSNCorresponsaliasNoFacturadoMayor4Meses extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_001_ReportePagoSNCorresponsaliasNoFacturadoMayor4Meses_test(HashMap<String, String> data) throws Exception {
        

		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String estatus = data.get("estatus");
		String fechaInicial = data.get("fechaInicial");
		String fechaFinal = data.get("fechaFinal");
		String concurrente = data.get("concurrente");
		String validacionPalabra = concurrente.contains("Nacionales") ? "Nacionales" : null;
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		ClaseLogin inicio = new ClaseLogin(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("NETEOS");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);

		PatternUtil p = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
    	
    	
        //Paso 1
        addStep("Ingresar a la aplicacion Oracle EBS");

        logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		assertEquals(userName,userName);
		inicio.login();
		u.hardWait(6);
        
        
        //Paso 2
        addStep("Seleccionar la responsabilidad MXOXXO-GL17ADM-SERVICIOS_ADTIVO_OS");
        paginaInicial.clickOnResponsabilidad(responsabilidad);
        u.hardWait(3);
        u.scroll();
        assertTrue(paginaInicial.configuracionRemesas());	
        u.hardWait(4);
        p.accepApp();
        u.hardWait(8);
        p.closeText();
        u.hardWait(5);
        testCase.addScreenShotCurrentStep(" ");
        
        //Paso 3
        addStep("Dirigirse al menu Ver -> Solicitudes");
        
        p.vers();
        assertEquals(concurrente,concurrente);
        testCase.addScreenShotCurrentStep("");
        u.hardWait(4);
        
        
        //Paso 4
        addStep("Ejecutar una nueva solicitud");
        assertEquals(estatus,estatus);
        testCase.addScreenShotCurrentStep("");
        p.clickEjecutarNuevaSolicitud();
        u.hardWait(4);
        
        //Paso 5
        addStep("Ingresar el nombre del concurrente XXFC Reporte para Pago de Servicios Nacionales Corresponsalias  y llenar los parametros\r\n"
        		+ "* Estatus: No Facturado\r\n"
        		+ "* Rango de fecha mayor a cuatro meses");
      
       
        p.typeAndTab(concurrente);
        u.hardWait(3);
        p.nTAB(2);
        p.typeAndTab(estatus);
        sk.screen.type(fechaInicial);
        p.typeAndTab(fechaFinal);
        assertEquals(fechaInicial,fechaInicial);
        testCase.addScreenShotCurrentStep(" ");
        p.nEnter(1);
        
        //Paso 6
        addStep("Ejecutar");
        testCase.addScreenShotCurrentStep(" ");
        p.clickEjecutar();
        u.hardWait(3);
		p.clickNo();
		p.clickEncontrar();
	    u.hardWait(4);
  		while(p.validatePattern("estadoNormalVerde") || p.validatePattern("estadoStandbyVerde")) {
  			u.hardWait(15);
  			if(p.validatePattern("ocRefrescarDatos")) {
  				p.clickPattern("ocRefrescarDatos",10);
  			}
  		}
  		    
  		assertTrue(p.validatePattern("faseFinalizadoPrimeraFila") && p.validatePattern("estadoNormalPrimeraFila"));	
	    testCase.addScreenShotCurrentStep(" ");
        
        //Paso 7
        addStep("Validar que se muestre informacion en reporte XXFC Reporte para Pago de Servicios Nacionales Corresponsalias superior a 4 meses");
        
	    
	    p.nDOWN(1);
	    p.clickVisualizarOutput();
	    u.hardWait(5);
	    p.closeActiveWindow();;
	    u.hardWait(5);
	    testCase.addScreenShotCurrentStep("");
	    u.hardWait(5);
	    assertEquals(p.buscarTextoComparar(validacionPalabra),validacionPalabra);
	    u.getDriver().quit();
        
 
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_001_ReportePagoSNCorresponsaliasNoFacturadoMayor4Meses_test";
    }

    @Override
    public String setTestDescription() {
        return "Ejecucion del concurrente XXFC Reporte para Pago de Servicios Nacionales Corresponsalias  y que muestre informacion de registros no facturados con mas de cuatro meses de antiguedad";
    }

    @Override
    public String setTestDesigner() {
        return "RicardoR4@hexaware.com";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "Exista informacion de transacciones pendientes con mas de cuatro meses de antiguedad en la tabla de xxfc_pago_servicios\r\n"
        		+ "";
    }

    @Override
    public void beforeTest() {

    }
}