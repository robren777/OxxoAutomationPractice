package EBS.AutRegistrosContables.om;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;

import EBS.tools.LoginTool;
import modelo.TestCase;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

import org.openqa.selenium.By;



public class AutRegistrosContablesEBS {
	static SeleniumUtil u;
	static TestCase testCase;
	SikuliUtil sk;
	Pattern pathimg;
	ArrayList<String> ListImage;
	HashMap<String, Pattern> ImgPattern;
	ArrayList<Pattern> patterns;
	File Folder;

	By aServices = By.xpath("//*[@class='ViewpointsHome tab-item--link my-children-show-key-navigation_outline-width-0']");
	By buttonAvanzado = By.xpath("//*[@id='details-button']");
	By aContinuarASitioNoSeguro = By.xpath("//*[@id='proceed-link']");

	/**
	 * DESCRIPCION:Clase de utileria para manejo de proyecto Automatización Registros Contables.
	 * @Author bernardo.mejia@grupotimex.onmicrosoft.com
	 * @date 20/04/2025
	 */	 
	public AutRegistrosContablesEBS(SikuliUtil sk, SeleniumUtil u,TestCase testCase,Pattern pathimg,ArrayList<String> ListImage,HashMap<String, Pattern> ImgPattern,ArrayList<Pattern> patterns,File Folder) 
	{
		this.u = u;
		this.sk = sk;
		this.pathimg = pathimg;
		this.ListImage = ListImage;
		this.ImgPattern = ImgPattern;
		this.patterns = patterns;
		this.Folder = Folder;
		this.testCase = testCase;

	}



	/**
	 * runApplet intento de ejecutar EAM 
	 * @author juan.lopez@grupotimexico.com
	 * @throws FrameworkException  
	 * @date 20/04/2025
	 */
	public void runApplet() {
		System.out.println("Run Applet");		
		try {					
			sk.getRegion("EBS\\EBS\\conservar.png").click();
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");
			sk.getRegion("EBS\\frmservlet").click();
			u.hardWait(10);
			sk.getRegion("EBS\\\\\\AceptoRiesgo").click();
			sk.getRegion("EBS\\\\ejecutarseguridad").click();
			u.hardWait(15);
		} catch (FindFailed e) {					
			System.out.println("Tiempo de ejecucion excedido " + e);
		}				
	}

	/**
	 * Método para Ejecutar un concurrente.
	 * @param Concurrente - recibe el nombre del concurrente a ejecutar
	 * @return true/false
	 * @Author Juan.lopez@grupotimexico.com
	 * @date 27/04/2025
	 */	
	public boolean ejecutarConcurrente(String Concurrente) {
		System.out.println("Ejecutar Concurrente");		
		try {					
			sk.getRegion("EBS\\EBS\\ver.png").click();
			u.hardWait(1);
			sk.getRegion("EBS\\EBS\\solicitudes.png").click();
			u.hardWait(5);
			sk.getRegion("EBS\\EBS\\ejecutarNuevaSolicitudButton.png").click();
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");
			sk.screen.type(Concurrente);
			u.hardWait(1);
			sk.screen.type(Key.TAB);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");
			sk.getRegion("EBS\\EBS\\ejecutar.png").click();
			u.hardWait(3);
			sk.getRegion("EBS\\EBS\\aceptarWarning.png").click();
			u.hardWait(1);
			sk.getRegion("EBS\\EBS\\no.png").click();
			u.hardWait(10);
			sk.getRegion("EBS\\EBS\\encontrar.png").click();
			u.hardWait(5);
			sk.getRegion("EBS\\EBS\\ocRefrescarDatos.png").click();
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");
			sk.getRegion("EBS\\EBS\\visualizardetalles.png").click();
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");
			sk.getRegion("EBS\\EBS\\closeW.png").click();
			sk.getRegion("EBS\\EBS\\closeW.png").click();
			sk.getRegion("EBS\\EBS\\closeW.png").click();
			return true;  
		} catch (FindFailed e) {					
			System.out.println("Tiempo de ejecucion excedido " + e);					
		}				
		return false;
	}

	/**
	 * Método para ver el detalle de ejecucion de un concurrente.
	 * @return true/false
	 * @Author Juan.lopez@grupotimexico.com
	 * @date 27/04/2025
	 */	
	public boolean verDetalleConcurrente() {
		System.out.println("Ver detalles");		
		try {					
			sk.getRegion("EBS\\EBS\\ver.png").click();
			u.hardWait(1);
			sk.getRegion("EBS\\EBS\\solicitudes.png").click();
			u.hardWait(5);
			testCase.addScreenShotCurrentStep("");
			sk.getRegion("EBS\\EBS\\encontrar.png").click();
			u.hardWait(1);
			sk.getRegion("EBS\\EBS\\visualizardetalles.png").click();
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");

			return true;  
		} catch (FindFailed e) {					
			System.out.println("Tiempo de ejecucion excedido " + e);
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método para ver el log generado de un concurrente.
	 * @return true/false
	 * @Author Juan.lopez@grupotimexico.com
	 * @date 27/04/2025
	 */	
	public boolean verLogConcurrente() {
		System.out.println("Ver detalles");		
		try {					
			sk.getRegion("EBS\\EBS\\ver.png").click();
			u.hardWait(1);
			sk.getRegion("EBS\\EBS\\solicitudes.png").click();
			u.hardWait(5);
			testCase.addScreenShotCurrentStep("");
			sk.getRegion("EBS\\EBS\\encontrar.png").click();
			u.hardWait(3);
			sk.getRegion("EBS\\EBS\\visualizarregistro.png").click();
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");

			return true;  
		} catch (FindFailed e) {					
			System.out.println("Tiempo de ejecucion excedido " + e);
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			return false;
		}

	}

	/**
	 * Método para buscar una fctura
	 * @param factura - se recibe la factura que se va a buscar
	 * @return true/false
	 * @Author Juan.lopez@grupotimexico.com
	 * @date 27/04/2025
	 */	
	public boolean buscarFactura(String factura) {
		System.out.println("Buscar Factura");		
		try {					
			sk.getRegion("EBS\\EBS\\lamparabusqueda.png").click();
			u.hardWait(3);
			sk.screen.type(Key.TAB);
			sk.screen.type(Key.TAB);
			sk.screen.type(Key.TAB);
			sk.screen.type(Key.TAB);
			sk.screen.type(Key.TAB);
			sk.screen.type(factura);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			sk.getRegion("EBS\\EBS\\encontrarBotonO.png").click();
			u.hardWait(4);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(1);
			sk.getRegion("EBS\\EBS\\closeW.png").click();

			return true;
		} catch (FindFailed e) {					
			System.out.println("Tiempo de ejecucion excedido " + e);
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Metodo para revisar que se ejecutando el concurrente
	 *@param concurrente para ejecutar el concurrente
	 * @author juan.lopez@grupotimexico.com
	 * @throws Exception 
	 * @date 31/04/2025
	 */
	public boolean validarEjecConcurrenteEBS (String concurrente) throws Exception {
		try{			
			sk.getRegion("EBS\\EBS\\ver.png").click();
			u.hardWait(1);
			sk.getRegion("EBS\\EBS\\solicitudes.png").click();
			u.hardWait(5);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(1);
			sk.getRegion("EBS\\EBS\\solicitudesEspecificas.png").click();			
			testCase.addScreenShotCurrentStep("");
			sk.getRegion("EBS\\EBS\\nomSolConcu.png").click();
			u.hardWait(1);		
			sk.screen.type(concurrente);
			u.hardWait(1);
			sk.getRegion("EBS\\EBS\\encontrar.png").click();		
			testCase.addScreenShotCurrentStep("");
			return true;
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}


	/**
	 *  Cerrar Applet
	 * @author juan.lopez@grupotimexico.com 
	 * @throws Exception   
	 * @since 29/04/2025
	 */
	public void cerrarApplet () throws Exception {
		try{			
			sk.getRegion("EBS\\EBS\\cerrar").click();
			u.hardWait(2);	
			sk.getRegion("EBS\\EBS\\aceptarcerrar").click();		
			testCase.addScreenShotCurrentStep("");
			u.hardWait(5);
			u.getDriver().quit();
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}

	}
	
	/**
	 * Método para obtener el log al visualizar el registro
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public String getDataFromClipboard() throws Exception {
		try {
		u.hardWait(7);
		sk.screen.type("a",Key.CTRL);
		u.hardWait(5);
		sk.screen.type("c",Key.CTRL);
		u.hardWait(3);
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		return c.getData(DataFlavor.stringFlavor).toString();
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}

	}

	/**
	 * Método para buscar una factura
	 * @param factura - se recibe la factura que se va a buscar
	 * @return true/false
	 * @Author Juan.lopez@grupotimexico.com
	 * @date 27/04/2025
	 */	
	public String[] abrirfactura(String factura) throws Exception{
		System.out.println("validar datos Factura");		
		try {	
			String[] data= new String[3];
			sk.getRegion("EBS\\EBS\\lamparabusqueda.png").click();
			u.hardWait(3);
			sk.screen.type(Key.TAB);
			sk.screen.type(Key.TAB);
			sk.screen.type(Key.TAB);
			sk.screen.type(Key.TAB);
			sk.screen.type(Key.TAB);
			sk.screen.type(factura);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			sk.getRegion("EBS\\EBS\\encontrarBotonO.png").click();
			u.hardWait(4);
			testCase.addScreenShotCurrentStep("");
			sk.getRegion("EBS\\EBS\\descripcionfactura.png").click();
			
			String descripcion = this.getDataFromClipboard();
			sk.screen.type(Key.TAB);
			sk.screen.type(Key.TAB);
			sk.screen.type(Key.TAB);
			sk.screen.type(Key.TAB);
			String prov = this.getDataFromClipboard();
			sk.screen.type(Key.TAB);
			sk.screen.type(Key.TAB);
			sk.screen.type(Key.TAB);
			String num_factura = this.getDataFromClipboard();
			
			
			System.out.println("Proveedor: " + prov);
			System.out.println("Numero de Factura: " + num_factura);
			System.out.println("Descripcion: " + descripcion);
			
			data[0]=descripcion;
			data[1]=prov;
			data[2]=num_factura;
			
			return data;
		} catch (FindFailed e) {					
			System.out.println("Tiempo de ejecucion excedido " + e);
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Método para dar tab
	 * @return true/false
	 * @Author Juan.lopez@grupotimexico.com
	 * @date 26/05/2025
	 */	
	public boolean darTab() {
		System.out.println("Dar Tab");
		sk.screen.type(Key.TAB);
		testCase.addScreenShotCurrentStep("Tab");

		return true;
	}
	
	/**
	 * Metodo para hacer loging, seleccionar la responsabilidad y abrir la app
	 * @author Guillermo Garcia
	 * @param String ebsUrl el url de la pagina de ebs,
	 * String userName el nombre de usuario al que conectarse, 
	 * String responsabilidad la responsabilidad que se desea seleccionar, 
	 * LoginTool inicio clase iniciada de la apgina de incio de ebs, 
	 * EBSPaginaInicial paginaInicial clase iniciada de la apgina de incial de ebs 
	 * @date 28/05/2025
	 */
	public void loggingResponsabilidadApp(String ebsUrl, String userName, String responsabilidad, LoginTool inicio, EBSPaginaInicial paginaInicial ) {
		//Loging
		System.out.println("EBS URL: " + ebsUrl);
		System.out.println("User Name: " + userName);
		inicio.login();
		
		//abrir responsabilidad
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		System.out.println("Click Responsabilidad");
		paginaInicial.carpetaFacturasElement.click();
		System.out.println("Click Facturas");
		paginaInicial.carpetaFacturasEntrarElement.click();
		System.out.println("Click Facturas entrar");
		paginaInicial.clickEntrarFacturas();
		System.out.println("Click Facturas");
		
		//abrir app
		this.runApplet();
		
	}

}
