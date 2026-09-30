package EBS.cartaPorteCHG0037801;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.LoginCartaPorte.ClaseLogin;
import EBS.util.appletsMethods.MetodosSikuli_CartaPorte;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_011_PantallaDeParametrosCartaPorteAlmacenUbicacionOrigen_I23052_TI01 extends BaseExecution{
	/*
	 * El objetivo general del proyecto es enviar un correo a los transportistas cuando el área de mesa de control
	 * despacha el viaje, esta acción genera la información de la Carta Porte que contiene los datos del viaje 
	 * que puede estar asignado a 1 o más pedidos de tipo  expansion o reposición. Se realizan las correspondientes
	 * validaciones para asegurar que la carta porte se genere al cumplirse las reglas especificadas en las 
	 * historias de usuario.  
	*/
	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_PantallaDeParametrosCartaPorteAlmacenUbicacionOrigen_I23052_TI01_test(HashMap<String, String> data)throws Exception {
		
		/*
		 * VARIABLES
		 * 
		 * ebsUrl= Url para acceder a la aplicación de EBS
		 * userName = usuario de acceso
		 * password = contraseña encriptada para acceder a la aplicación
		 * responsabilidad = nombre de la responsabilidad de mesa de control
		 * carpeta = carpeta para acceder al applet de java
		 * ubicacion = número de viaje esperado
		 * solicitud = número de solicitud de almacen que se consultara
		 * result = seguimiento a los pasos
		 * 
	    */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta= data.get("carpeta");
		String ubicacion= data.get("ubicacion");
		String solicitud= data.get("solicitud");
		String result="";
		
		/*
		 * UTILERIAS
		*/
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		ClaseLogin inicio = new ClaseLogin(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial (u, testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\CARTAPORTE");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli_CartaPorte p = new MetodosSikuli_CartaPorte(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);	
		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: CHG0037801
		 */
		
		//PASO 1
		addStep("Ingresar al ambiente de EBS.");
		inicio.login();
		u.hardWait(30);
		result=paginaInicial.encontrarTextoPaginaInicio();
		assertTrue(result!=null);
		u.hardWait(20);
		//PASO 2
		addStep("Seleccionar la responsabilidad: 'MXOXXO-OM17FAA-MESA CONTROL FRM'");
		result=paginaInicial.clickOnResponsabilidad3(responsabilidad);
		testCase.addScreenShotCurrentStep(u, "Responsabilidad");
		assertTrue(result!=null);
		u.hardWait(20);
		//PASO 3
		addStep("Seleccionar carpeta: Pedidos de Venta");		
		result=paginaInicial.clickOnCarpeta2(carpeta);
		testCase.addScreenShotCurrentStep(u, "Carpeta");
		assertTrue(result!=null);
		//PASO 4
		addStep("Seleccionar: Transacciones de Envio");
		u.hardWait(40);
		result=paginaInicial.clickRunTransaccionesDeEnvio();
		p.clickImagen("Transacciones");
		testCase.addScreenShotCurrentStep(u, "TransaccionDeEnvio");
		assertTrue(result!=null);
		u.hardWait(90);
		p.runApplet();
		u.hardWait(40);
		//PASO 5
		addStep("Abrir solicitudes");		
		p.consultarSolicitudes();
		testCase.addScreenShotCurrentStep("Solicitudes");
		u.hardWait(10);
		p.nTAB(6);
		u.hardWait(10);
		p.clickImagen("NavegadorEdicion");
		u.hardWait(10);
		p.nDown(3, true);
		u.hardWait(10);
		result = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);
		assertTrue(result!=null);
		//PASO 6
		addStep("Capturar solicitud de recolección");	
		result="";
		p.encontrarSolicitud(solicitud);
		u.hardWait(10);
		p.nTAB(6);
		u.hardWait(10);
		p.clickImagen("NavegadorEdicion");
		u.hardWait(10);
		p.nDown(3, true);
		u.hardWait(10);
		sk.screen.type(Key.ENTER);
		result = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);
		testCase.addScreenShotCurrentStep("EncontrarSolicitud");
		System.out.println(result);
		assertEquals(result, solicitud);
		//PASO 7
		addStep("Abrir pantalla parametros");
		u.hardWait(10);
		p.visualizarSolicitud(5);
		u.hardWait(10);
		p.nTAB(4);
		u.hardWait(20);
		/*u.hardWait(20);
		org.sikuli.script.Region appRegion= App.focusedWindow();
		Screen screen = new Screen();
		Region region=screen.selectRegion();
		Image im=region.getImage();
		File f=new File(Fol+"/Parametro.png");
		ImageIO.write((RenderedImage) im.get(),"png", f);*/
		testCase.addScreenShotCurrentStep("Parametro");
		u.hardWait(30);
		String ubicacionVentana = p.obtenerValorParametros();
		assertEquals(ubicacionVentana, ubicacion);
		//cerrar
		u.hardWait(15);
		p.clickImagen("Precaucion_Aceptar");
		u.hardWait(10);
		p.clickImagen("aceptar2");
		u.hardWait(10);
		p.clickImagen("archivo");
		u.hardWait(10);
		p.nUp(1);
		u.hardWait(10);
		p.clickImagen("Precaucion_Aceptar");
		u.hardWait(10);
		u.close(); 
	
	}

	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setPrerequisites() {
		return  "1.- Solicitud generada"+"\n"
				+ "2.- Ubicación esperada de la solicitud"+"\n"
				+ "3.- Acceso al aplicativo";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se muestre en la pantalla Parámetros la Ubicación Origenn";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_011_PantallaDeParametrosCartaPorteAlmacenUbicacionOrigen_I23052_TI01";
	}

	@Override
	public String setTestInstanceID() {
		
		return null;
	}
	

}
