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

public class ATC_FT_022_EnvioDeCorreoConPedidosConErrorDeUbicacionDeCRTienda_I23052_TI01 extends BaseExecution{
	/*
	 * El objetivo general del proyecto es enviar un correo a los transportistas cuando el área de mesa de control
	 * despacha el viaje, esta acción genera la información de la Carta Porte que contiene los datos del viaje 
	 * que puede estar asignado a 1 o más pedidos de tipo  expansion o reposición. Se realizan las correspondientes
	 * validaciones para asegurar que la carta porte se genere al cumplirse las reglas especificadas en las 
	 * historias de usuario.  
	*/

	@Test(dataProvider = "data-provider")
	public void ATC_FT_022_EnvioDeCorreoConPedidosConErrorDeUbicacionDeCRTienda_I23052_TI01_test(HashMap<String, String> data)throws Exception {
		
		/*
		 * VARIABLES
		 * 
		 * ebsUrl= Url para acceder a la aplicación de EBS
		 * userName = usuario de acceso
		 * password = contraseña encriptada para acceder a la aplicación
		 * responsabilidad = nombre de la responsabilidad de mesa de control
		 * carpeta = carpeta para acceder al applet de java
		 * pedido = número de pedido que se asignó al viaje
		 * tipo= tipo de pedido: EXPANSION | REPOSICION
		 * juegoDocumentos= nombre del juego de documentos que contiene las reglas para validar el envio de correo al despachar los viajes
		 * viaje = número de viaje que contiene el pedido asignado
		 * result = variable para validar los pasos de ejecución
		 * muelle = nombre del muelle al que se le asignará el viaje
		 * ventanaTiempo1 = fecha agregada a la asignación del muelle
		 * ventanaTiempo2 = se agrega un minuto más que ventanaTiempo1 para que solo en ese tiempo no se permita asignar otro muelle
		 * descripción = notas generales para la asignación del muelle
		 * 
	    */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String responsabilidad = data.get("responsabilidad");
		String carpeta= data.get("carpeta");
		String pedido= data.get("pedido");
		String tipoPedido = data.get("tipo");
		String juegoDocumentos=data.get("documento");
		String muelle=data.get("muelle");
		String ventanaTiempo1=data.get("ventanaTiempo1");
		String ventanaTiempo2=data.get("ventanaTiempo2");
		String descripcion=data.get("descripcion");
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
		// PASO 1
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
		paginaInicial.clickOnCarpeta2(carpeta);
		u.hardWait(40);
		result=paginaInicial.clickRunTransaccionesDeEnvio();
		p.clickImagen("Transacciones");
		testCase.addScreenShotCurrentStep(u, "Carpeta");
		assertTrue(result!=null);
		u.hardWait(90);
		p.runApplet();
		u.hardWait(40);
		// PASO 4
		addStep("Buscar el pedido: "+pedido+" en Pantalla de Transacciones");
		p.buscarPedidoEnTransaccionesDeEnvio(pedido);
		String viaje= p.obtenerViaje();
		assertTrue(viaje.length()>1);
		testCase.addScreenShotCurrentStep("NumeroViaje");
		u.hardWait(20); 
		// PASO 5
		addStep("Asignar muelle");
		p.abrirVentanaDeMuelle();
		assertTrue(p.llenarMuelle(viaje, muelle, ventanaTiempo1, ventanaTiempo2, descripcion,5));
		testCase.addScreenShotCurrentStep("AsignaMuelle");
		u.hardWait(20);
		p.clickImagen("aceptarMuelle");
		u.hardWait(20);
		// PASO 6
		addStep("Despachar pedido");
		p.abrirDespacharPedido();
		String reglaPedido=p.obtenerReglaDelTipoDePedido(tipoPedido);
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
		sk.screen.type(viaje);
		u.hardWait(10);
		p.nTAB(1);
		u.hardWait(10);
		p.clickImagen("concurrenteDespacho");
		u.hardWait(10);
		sk.screen.type(Key.ENTER);;
		u.hardWait(20);
		p.clickImagen("relesase");	
		assertTrue(reglaPedido!=null && juegoDocumentos !=null && viaje!=null);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("DespacharViaje");
		//PASO 7
		addStep("Abrir solicitudes");
		u.hardWait(30);
		p.consultarSolicitudes();
		testCase.addScreenShotCurrentStep("Solicitudes");
		u.hardWait(10);
		p.nTAB(10);
		u.hardWait(20);
		p.clickImagen("NavegadorEdicion");
		u.hardWait(10);
		p.nDown(3, true);
		u.hardWait(10);
		result = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);
		assertEquals(result, "Normal");
		//Cerrar todo
		u.hardWait(15);
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
			 + "2.- Viaje con más de un pedido. V"
			 + "3.- Viaje con un pedido con error de ubicación"+"\n"
			 + "3.- Usuario con permisos para modificar las acciones del pedido"+"\n"
			 + "4.- Acceso al aplicativo"+"\n";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se genere archivo XML con viajes que contengan CR Tienda con error de ubicación. ";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_022_EnvioDeCorreoConPedidosConErrorDeUbicacionDeCRTienda_I23052_TI01";
	}

	@Override
	public String setTestInstanceID() {
		
		return null;
	}
	

}
