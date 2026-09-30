package EBS.cartaPorteCHG0037801;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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


public class ATC_FT_002_EnvioDeCorreoElectronico_I23052_TI01 extends BaseExecution{
	/*
	 * El objetivo general del proyecto es enviar un correo a los transportistas cuando el área de mesa de control
	 * despacha el viaje, esta acción genera la información de la Carta Porte que contiene los datos del viaje 
	 * que puede estar asignado a 1 o más pedidos de tipo  expansion o reposición. Se realizan las correspondientes
	 * validaciones para asegurar que la carta porte se genere al cumplirse las reglas especificadas en las 
	 * historias de usuario.  
	*/
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_EnvioDeCorreoElectronico_I23052_TI01_test(HashMap<String, String> data)throws Exception {
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
		 * Queries
		 */
		String query1 = "SELECT SV.SHIP_METHOD_CODE"
				+ ", SV.SHIP_METHOD_MEANING"
				+ ", SV.ATTRIBUTE1 "
				+ "FROM  APPS.WSH_CARRIER_SERVICES SV "
				+ "WHERE SV.SHIP_METHOD_MEANING='01 RECOLECCION' ";
		
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
		u.hardWait(40);
		result=paginaInicial.clickRunTransaccionesDeEnvio();
		p.clickImagen("Transacciones");
		testCase.addScreenShotCurrentStep("TransaccionDeEnvio");
		assertTrue(result!=null);
		//Se abre applet
		u.hardWait(90);
		p.runApplet();
		u.hardWait(40);
		//PASO 5
		addStep("Buscar el pedido: "+pedido+" en Pantalla de Transacciones");
		p.buscarPedidoEnTransaccionesDeEnvio(pedido);
		result="";
		result= p.obtenerValorParametros();
		assertTrue(result!=null);
		testCase.addScreenShotCurrentStep("BuscarPedido");
		//PASO 6
		addStep("Asignar viaje");
		p.seleccionarTodo();
		p.asignarViajeAlPedido();	
		String viaje= p.obtenerViaje();
		assertTrue(viaje.length()>1);
		testCase.addScreenShotCurrentStep("NumeroViaje");
		u.hardWait(20); 
		//PASO 7
		addStep("Asignar muelle");
		p.abrirVentanaDeMuelle();
		assertTrue(p.llenarMuelle(viaje, muelle, ventanaTiempo1, ventanaTiempo2, descripcion,5));
		testCase.addScreenShotCurrentStep("AsignaMuelle");
		System.out.println(query1);
		utils.sql.SQLResult ValidChangeExec = dbAvebqa.executeQuery(query1);
		testCase.addQueryEvidenceCurrentStep(ValidChangeExec);
		u.hardWait(20);
		p.clickImagen("aceptarMuelle");
		u.hardWait(20);
		//PASO 8
		addStep("Capturar regla");
		p.abrirDespacharPedido();
		String reglaPedido=p.obtenerReglaDelTipoDePedido(tipoPedido);
		assertTrue(reglaPedido!=null);
		sk.screen.type(reglaPedido);
		testCase.addScreenShotCurrentStep("CapturarRegla");
		u.hardWait(5);
		p.nTAB(1);
		u.hardWait(15);
		//PASO 9
		addStep("Capturar en Juego de Documentos");
		assertTrue(juegoDocumentos!=null);
		sk.screen.type(juegoDocumentos);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("Capturar Juego de Documentos"); 
		p.nTAB(1);
		u.hardWait(15);
		//PASO 10
		addStep("Capturar viaje");
		p.clickImagen("envioDespacho");
		u.hardWait(20);
		sk.screen.type(viaje);
		u.hardWait(5);
		assertTrue(viaje!=null);
		testCase.addScreenShotCurrentStep("Capturar viaje");
		p.nTAB(1);
		u.hardWait(20);
		//PASO 11
		addStep("Seleccionar concurrente");
		p.clickImagen("concurrenteDespacho");
		u.hardWait(40);
		testCase.addScreenShotCurrentStep("Concurrente");
		sk.screen.type(Key.ENTER);;
		u.hardWait(30);
		p.clickImagen("relesase");
		u.hardWait(30);
		result=p.obtenerValorParametros();
		assertEquals(result, juegoDocumentos);
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
				+ "2.- Viaje con tipo de pedido reposición con embarque 99 con estatus Registrado"+"\n"
				+ "3.- Usuario con permisos para modificar las acciones del pedido"+"\n"
				+ "4.- Acceso al aplicativo"+"\n"
				+ "5.- Acceso a BD AVEBQA"+"\n"
				+ "6.- Acceso al correo electrónico del transportista a seleccionar en la ventama de Asignación de puerto";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se envie correo con la información del viaje y del pedido";
	}

	@Override
	public String setTestDesigner() {
		return "nellyoliviab@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_EnvioDeCorreoElectronico_I23052_TI01";
	}

	@Override
	public String setTestInstanceID() {
		
		return null;
	}
	

}
