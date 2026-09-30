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

import EBS.util.GlobalVariables;
import EBS.util.LoginCartaPorte.ClaseLogin;
import EBS.util.appletsMethods.MetodosSikuli_CartaPorte;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;


public class ATC_FT_026_DespacharVariosViajesSeguidos_I23052_TI01 extends BaseExecution{
	/*
	 * El objetivo general del proyecto es enviar un correo a los transportistas cuando el área de mesa de control
	 * despacha el viaje, esta acción genera la información de la Carta Porte que contiene los datos del viaje 
	 * que puede estar asignado a 1 o más pedidos de tipo  expansion o reposición. Se realizan las correspondientes
	 * validaciones para asegurar que la carta porte se genere al cumplirse las reglas especificadas en las 
	 * historias de usuario.  
	*/
	@Test(dataProvider = "data-provider")
	public void ATC_FT_026_DespacharVariosViajesSeguidos_I23052_TI01_test(HashMap<String, String> data)throws Exception {
		/*
		 * VARIABLES
		 * 
		 * ebsUrl= Url para acceder a la aplicación de EBS
		 * userName = usuario de acceso
		 * password = contraseña encriptada para acceder a la aplicación
		 * responsabilidad = nombre de la responsabilidad de mesa de control
		 * carpeta = carpeta para acceder al applet de java
		 * viaje = viajes listos para despachar
		 * juegoDocumentos= nombre del juego de documentos que contiene las reglas para validar el envio de correo al despachar los viajes
		 * result = variable para validar los pasos de ejecución
		 * 
	    */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta= data.get("carpeta");
		String viaje=data.get("viajes");
		String juegoDocumentos=data.get("documento");
		String result="";
		String viajes[]= viaje.split(",");
		
		/*
		 * Queries
		 */
		String solicitudAutomatizacion = "SELECT PRIORITY_REQUEST_ID "
				+ "FROM APPS.FND_CONC_REQ_SUMMARY_V "
				+ "WHERE PROGRAM_SHORT_NAME LIKE '%XXFC_CCP_AUTOMATIZACION%' "
				+ "AND ARGUMENT_TEXT LIKE ";
		
		String estadoSolicitud = "SELECT COMPLETION_TEXT "
				+ "FROM APPS.FND_CONC_REQ_SUMMARY_V "
				+ "WHERE PRIORITY_REQUEST_ID = ";
		
		String querySolicitud = "SELECT ARGUMENT_TEXT, PRIORITY_REQUEST_ID,"
				+ " PROGRAM_SHORT_NAME, COMPLETION_TEXT"
				+ " FROM APPS.FND_CONC_REQ_SUMMARY_V "
				+ "WHERE PRIORITY_REQUEST_ID= ";
		
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
		utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		/*
		 * PASOS DEL CASO DE PRUEBA, Matriz: CHG0037801
		 */
		
		//PASO 1
		addStep("Ingresar al ambiente de EBS QA.");
		inicio.login();
		u.hardWait(30);
		result=paginaInicial.encontrarTextoPaginaInicio();
		assertTrue(result!=null);
		u.hardWait(20);
		//PASO 2
		addStep("Ingresar a la responsabilidad: MXOXXO-OM17FAA-MESA CONTROL FRM");
		result=paginaInicial.clickOnResponsabilidad3(responsabilidad);
		testCase.addScreenShotCurrentStep(u, "Responsabilidad");
		assertTrue(result!=null);
		u.hardWait(20);
		//PASO 3
		addStep("Seleccionar la opción: Despachar Pedidos de Venta");		
		result= paginaInicial.clickOnCarpeta2(carpeta);
		testCase.addScreenShotCurrentStep(u, "Carpeta");
		assertTrue(result!=null);
		//PASO 4
		addStep("En sección Criterios de Despacho, seleccionar Transacciones de Envio");
		u.hardWait(60);
		result=paginaInicial.clickRunTransaccionesDeEnvio();
		p.clickImagen("Transacciones");
		testCase.addScreenShotCurrentStep("TransaccionDeEnvio");
		assertTrue(result!=null);
		//Se abre applet
		u.hardWait(90);
		p.runApplet();
		u.hardWait(40);
		//PASO 5
		int i=0;
		p.abrirDespacharPedido();
		String tipo="REPOSICION";
		String solicitudes[]=new String[viajes.length];
		for(i=0;i<viajes.length;i++) {
			if(i==viajes.length-1) {
				tipo="EXPANSION";
			}
			testCase.addTextEvidenceCurrentStep("Despachar viaje: "+(i+1)+") "+viajes[i]);
			String reglaPedido=p.obtenerReglaDelTipoDePedido(tipo);
			sk.screen.type(reglaPedido);
			u.hardWait(10);
			p.nTAB(1);
			u.hardWait(10);
			sk.screen.type(juegoDocumentos);
			u.hardWait(10);
			p.nTAB(1);
			u.hardWait(10);
			p.clickImagen("envioDespacho");
			u.hardWait(10);
			sk.screen.type(viajes[i]);
			u.hardWait(10);
			p.nTAB(1);
			u.hardWait(10);
			p.clickImagen("concurrenteDespacho");
			u.hardWait(25);
			testCase.addScreenShotCurrentStep("DespacharViaje"+viajes[i]);
			sk.screen.type(Key.ENTER);	
			assertTrue(reglaPedido!=null && juegoDocumentos !=null && viajes[i]!=null);
			p.clickImagen("lapiz");
			u.hardWait(25);
			utils.sql.SQLResult obtenerSolicitud = dbAvebqa.executeQuery(solicitudAutomatizacion+"'%"+viajes[i]+"%' ORDER BY REQUEST_ID DESC");
			solicitudes[i]= obtenerSolicitud.getFirstData();
			System.out.println("Solicitud: "+i+") "+solicitudes[i]);
		}
		// PASO 6
			addStep("Abrir solicitudes");
			u.hardWait(20);
			p.consultarSolicitudes();
			testCase.addScreenShotCurrentStep("Solicitudes");
			u.hardWait(10);
			p.nTAB(6);
			u.hardWait(10);
			p.clickImagen("NavegadorEdicion");
			u.hardWait(10);
			p.nDown(3, true);
			u.hardWait(10);
			sk.screen.type(Key.ENTER);
			result = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);
			assertTrue(result!=null);
		//PASO 7
			for(i=0; i<solicitudes.length;i++) {
				addStep("Solicitud: "+solicitudes[i]);
				assertTrue(solicitudes[i]!=null);
				System.out.println("Solicitud a buscar: "+solicitudes[i]);
				utils.sql.SQLResult finalizaSolicitud = dbAvebqa.executeQuery(estadoSolicitud+solicitudes[i]);
				utils.sql.SQLResult consultaS = dbAvebqa.executeQuery(querySolicitud+solicitudes[i]);
				String estado=finalizaSolicitud.getFirstData();
				if(estado==null) {
					result="";
					u.hardWait(30);
					p.encontrarSolicitud(solicitudes[i]);
					u.hardWait(10);
					p.nTAB(10);
					u.hardWait(25);
					p.clickImagen("NavegadorEdicion");
					u.hardWait(10);
					p.nDown(3, true);
					u.hardWait(10);
					result = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);
					testCase.addScreenShotCurrentStep("Advertencias"+solicitudes[i]);
					assertEquals(result, "Advertencia");
				}else {
					assertTrue(estado.indexOf("normal")!=-1);
				}
				testCase.addQueryEvidenceCurrentStep(consultaS);
			}
		u.hardWait(30);
		//Cerrar todo;
		p.clickImagen("archivo");
		u.hardWait(10);
		p.nUp(1);
		u.hardWait(30);
		p.clickImagen("Precaucion_Aceptar");
		u.hardWait(30);
		u.close();   
	}

	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setPrerequisites() {
		return "1.- Contar con la responsabilidad: MXOXXOOM17FAAMESACONTROLFRM."+"\n"
				+ "2.- Viajes listos para despachar"+"\n"
				+ "3.- Usuario con permisos para modificar las acciones del pedido"+"\n"
				+ "4.- Acceso al aplicativo"+"\n"
				+ "5.- Acceso a BD AVEBQA"+"\n"
				+ "6.- El ultimo viaje debe contener pedido EXPANSION";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al despachar varios viajes seguidos se generen sus solicitudes";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_026_DespacharVariosViajesSeguidos_I23052_TI01";
	}

	@Override
	public String setTestInstanceID() {
		
		return null;
	}
	

}
