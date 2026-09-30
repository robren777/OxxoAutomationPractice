package EBS.cartaPorteCHG0037801;

import static org.testng.Assert.assertTrue;

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

public class ATC_FT_021_FiltroDeRequision_I23052_TI01 extends BaseExecution{
	/*
	 * El objetivo general del proyecto es enviar un correo a los transportistas cuando el área de mesa de control
	 * despacha el viaje, esta acción genera la información de la Carta Porte que contiene los datos del viaje 
	 * que puede estar asignado a 1 o más pedidos de tipo  expansion o reposición. Se realizan las correspondientes
	 * validaciones para asegurar que la carta porte se genere al cumplirse las reglas especificadas en las 
	 * historias de usuario.  
	*/
	@Test(dataProvider = "data-provider")
	public void ATC_FT_021_FiltroDeRequision_I23052_TI01_test(HashMap<String, String> data)throws Exception {
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
		 * viaje1 = número de viaje expansion que contiene el pedido asignado
		 * viaje2 = número de viaje expansion que contiene el pedido asignado
		 * viaje3 = número de viaje expansion que contiene el pedido asignado
		 * result = variable para validar los pasos de ejecución
		 * muelle = nombre del muelle al que se le asignará el viaje
		 * ventanaTiempo1 = fecha agregada a la asignación del muelle del viaje 1
		 * ventanaTiempo2 = se agrega un minuto más que ventanaTiempo1 para que solo en ese tiempo no se permita asignar otro muelle del viaje 1
		 * ventanaTiempo3 = fecha agregada a la asignación del muelle del viaje 2 
		 * ventanaTiempo4 = se agrega un minuto más que ventanaTiempo1 para que solo en ese tiempo no se permita asignar otro muelle del viaje 2
		 * ventanaTiempo5 = fecha agregada a la asignación del muelle del viaje 3
		 * ventanaTiempo6 = se agrega un minuto más que ventanaTiempo1 para que solo en ese tiempo no se permita asignar otro muelle del viaje 3
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
		String viaje1=data.get("viaje1");
    	String viaje2=data.get("viaje2"); 
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
		u.hardWait(50);
		result=paginaInicial.clickRunTransaccionesDeEnvio();
		p.clickImagen("Transacciones");
		testCase.addScreenShotCurrentStep("TransaccionDeEnvio");
		assertTrue(result!=null);
		//Se abre applet
		u.hardWait(90);
		p.runApplet();
		u.hardWait(40);
		addStep("Buscar pedido");
		p.buscarPedidoEnTransaccionesDeEnvio(pedido);
		testCase.addScreenShotCurrentStep("BuscarPedido");
		p.obtenerViaje();
		u.hardWait(20); 
		//PASO 
		addStep("Despachar viaje");
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
		sk.screen.type(viaje1);
		u.hardWait(10);
		p.nTAB(1);
		u.hardWait(10);
		p.clickImagen("concurrenteDespacho");
		u.hardWait(10);
		sk.screen.type(Key.ENTER);;
		u.hardWait(10);
		p.clickImagen("relesase");	
		assertTrue(reglaPedido!=null && juegoDocumentos !=null && viaje1!=null);
		u.hardWait(20);
		testCase.addScreenShotCurrentStep("DespacharViaje");
		if(!viaje2.isEmpty()) {
			//PASO 
			p.clickImagen("lapiz");
			u.hardWait(20);
			addStep("Despachar viaje 2");
			reglaPedido=p.obtenerReglaDelTipoDePedido(tipoPedido);
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
			sk.screen.type(viaje2);
			u.hardWait(10);
			p.nTAB(1);
			u.hardWait(10);
			p.clickImagen("concurrenteDespacho");
			u.hardWait(10);
			sk.screen.type(Key.ENTER);;
			u.hardWait(10);
			p.clickImagen("relesase");	
			assertTrue(reglaPedido!=null && juegoDocumentos !=null && viaje2!=null);
			u.hardWait(10);
			testCase.addScreenShotCurrentStep("DespacharViaje2");
		}
		//PASO 14
		addStep("Consultar pedido");
		p.abrirOrganizadorPedido();
		String req=p.obtenerReq(pedido);
		assertTrue(!req.isEmpty());
		testCase.addScreenShotCurrentStep("ConsultarPedido");
		//PASO 15
		addStep("Consultar requisición");
		p.abrirOrganizadorPedido();
		result=p.obtenerPedido(req);
		assertTrue(!result.isEmpty());
		testCase.addScreenShotCurrentStep("ConsultarPedido");
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
				+ "2.- Almenos 2 viajes que pertenezcan a una misma requisición"+"\n"
				+ "3.- Usuario con permisos para modificar las acciones del pedido"+"\n"
				+ "4.- Acceso al aplicativo  "
				+ "5.- Pedido que pertenezca a la requisición"+"\n"
				+ "6.- Almenos 2 viajes que contengan tipo de pedido EXPANSION"+"\n";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al despachar el viaje se respete el filtro de la requisición ";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "TC_FT_021_FiltroDeRequision_I23052_TI01";
	}

	@Override
	public String setTestInstanceID() {
		
		return null;
	}
	

}
