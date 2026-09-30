package EBS.util.appletsMethods;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//import org.openqa.selenium.interactions.DoubleClickAction;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class MetodosSikuli_CartaPorte {
	SikuliUtil sk;
	SeleniumUtil u;
	Pattern pathimg;
	ArrayList<String> ListImage;
	HashMap<String, Pattern> ImgPatter;
	ArrayList<Pattern> patterns;
	File Fol;
	protected static final Logger logger = LoggerFactory.getLogger(BaseExecution.class);

	public MetodosSikuli_CartaPorte(SikuliUtil sk, SeleniumUtil u, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter, ArrayList<Pattern> patterns, File Fol) {
		this.u = u;
		this.sk = sk;
		this.pathimg = pathimg;
		this.ListImage = ListImage;
		this.ImgPatter = ImgPatter;
		this.patterns = patterns;
		this.Fol = Fol;
	}
	/**
	 * Metodo donde se obtiene la ruta de las imagenes
	 * 
	 * @param fol
	 * @return ImgPatter
	 * @author ricardo.palacios
	 */
	public HashMap<String, Pattern> getPatternsImg(File fol) {
		File[] files = Fol.listFiles();
		for (File s : files) {
			patterns.add(new Pattern(s.getAbsolutePath()));
		}
		for (Pattern p : patterns) {
			String fname = p.getFilename().toString();
			fname = fname.replace("\\", "-");
			fname = fname.replace(".png", "");
			fname = fname.replace(".PNG", "");
			String[] afname = fname.split("-");
			ImgPatter.put(afname[afname.length - 1], p);
		}

		return ImgPatter;
	}
	
	/**
	 * Maximiza la ventana enviando la combinacion de teclas WIN + UP
	 */
	public void appletMaximize() {
		sk.screen.keyDown(Key.WIN + Key.UP);
		sk.screen.keyUp(Key.WIN + Key.UP);
	}
	/**
	 * Valida si existe el patron a buscar
	 *
	 * @param target Imagen a buscar
	 * @return True o False utilizado para hacer un assert
	 * @throws FindFailed
	 * @author JesusH@hexaware.com
	 */
	public Boolean validatePattern(String target) throws FindFailed {
		if (sk.screen.exists(ImgPatter.get(target)) != null) {
			return true;
		} else {
			return false;
		}

	}
	/**
	 * *Selecciona la ventana de ebs oracle
	 *
	 * @author 
	 */
		public void appletSelect() {
			ImgPatter = getPatternsImg(Fol);
			try {
				if (!validatePattern("OracleLogo")) {
					sk.screen.exists(ImgPatter.get("ebsOracle"));
					sk.screen.click(ImgPatter.get("ebsOracle"));
					u.hardWait(8);
				}
	 
			} catch (FindFailed e) {
				logger.error("No se pudo seleccionar el icono de EBS! " + e);
				System.out.println("No se pudo seleccionar el icono de EBS! " + e);
			}
		}
	
	/**
	 * Metodo para manejar la ventana de advertencia que aparece al abrir un applet
	 *
	 * @author JesusH@hexaware.com
	 */
	public void runApplet() {
		System.out.println("Run Applet");
		ImgPatter = getPatternsImg(Fol);
		try {
			if (validatePattern("doYouWantToRunThisApplication")) {
				sk.screen.exists(ImgPatter.get("doYouWantToRunThisApplication"));
				sk.screen.click(ImgPatter.get("checkBoxSecurityWarning"));
				System.out.println(ImgPatter.get("checkBoxSecurityWarning"));
				sk.screen.type(Key.ENTER);
				u.hardWait(10);
			}

		} catch (FindFailed e) {
			logger.error("Tiempo de ejecución excedido! " + e);
			System.out.println("Tiempo de ejecución excedido! " + e);
		}
		u.hardWait(5);
		appletSelect();
		appletMaximize();
	}
	

	/**
	 * Da click en la imagen guardada en la carpeta Pattern
	 * 
	 * @param imagen nombre de la imagen que se seleccionará
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public void clickImagen(String imagen) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.click((ImgPatter.get(imagen)));
	}
	
	/**
	 * Emula presionar la tecla TAB un numero "n" de veces seguidas
	 *
	 * @param n El numero de veces que quieres que presione la tecla TAB
	 */
	public void nTAB(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.TAB);
			x = x + 1;
		}
	}
	
	/**
	 * Se presiona la tecla de flecha arriba n veces
	 * 
	 * @param n número de veces que se precionará la tecla
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public void nUp(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.UP);
			x = x + 1;
		}
		sk.screen.type(Key.ENTER);
	}
	
	/**
	 * Se presiona la tecla de flecha abajo n veces
	 * 
	 * @param n número de veces que se precionará la tecla
	 		  enter agregar enter al final
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public void nDown(int n,boolean enter) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.DOWN);
			x = x + 1;
		}
		if(enter) {
			sk.screen.type(Key.ENTER);
		}
	}
	
	/**
	 * 
	 *  En la pantalla de Transacciones de envío, después de consultar el pedido y asignarle un viaje,
	 *  Se selecciona el botón "CaminoViaje2" para obtener el valor del viaje asignado al pedido, se selecciona
	 *  y se recupera del portapapeles para poder enviar el viaje.
	 *  
	 *  @return viaje es el número de viaje asignado al pedido
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerViaje() throws HeadlessException, UnsupportedFlavorException, IOException, FindFailed {		
		clickImagen("CaminoViaje2");
		u.hardWait(5);
		clickImagen("NavegadorEdicion");
		u.hardWait(5);
		nDown(3, true);
		u.hardWait(5);
		String viaje = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		return viaje;
	}
	
	/**
	 *  Se captura la asignación de muelle del viaje indicado, este debe estar asignado a uno o más pedidos, se debe
	 *  verificar que las fechas de ventanaTiempo1 y ventanaTiempo2 sean válidas en la asignación de muelle.
	 *  
	 *  @param viaje           número de viaje al que se le asignará el muelle
	 *  @param muelle valor    predeterminado del muelle, por lo general es: PP.11.00
	 *  @param ventanaTiempo1  fecha de salida del muelle, formato ejemplo: 19-OCT-2023 01:23:00
	 *  @param ventanaTiempo2  fecha de llegada del muelle, formato ejemplo: 19-OCT-2023 01:23:00
	 *  @param descripcion     texto para agregar comentarios
	 *  @param transportista   transportista a seleccionar para la prueba
	 *  
	 *  @return boolean        true si los paramétros contienen información | false si alguno de los parámetros esta vacío
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean llenarMuelle(String viaje, String muelle, String ventanaTiempo1, String ventanaTiempo2, String descripcion, int transportista) throws FindFailed  {		
		sk.screen.type(viaje);
		nTAB(2);
		sk.screen.type(muelle);
		nTAB(1);
		u.hardWait(10);
		clickImagen("Transportista");
		nDown(transportista, true);
		u.hardWait(10);
		sk.screen.type(viaje);
		u.hardWait(5);
		clickImagen("comboMuelleEstado");
		u.hardWait(5);
		nUp(2);
		u.hardWait(5);
		nTAB(1);
		u.hardWait(5);
		sk.screen.type(ventanaTiempo1);
		nTAB(1);
		u.hardWait(2);
		sk.screen.type(ventanaTiempo2);
		nTAB(4);
		sk.screen.type(descripcion);
		if(viaje !=null && muelle !=null && ventanaTiempo1 !=null && ventanaTiempo2 !=null && descripcion !=null) {
			return true;
		}
		return false;
	}
	

	/**
	 *  Para despachar un viaje con pedidos, se requiere agregar la regla con la que deben ser asignados, hasta el
	 *  momento se evaluan los pedidos tipo EXPANSION y REPOSICION
	 *  
	 *  @param tipoPedido tipo de pedido EXPANSION o REPOSICION para asignar la regla correspondiente
	 *  @return regla     nombre de la regla que se le debe asignar al viaje según su tipo de pedidos
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	
	public String obtenerReglaDelTipoDePedido(String tipoPedido) throws FindFailed {
		String regla="OXXO ACA REP AUTPICK";
		if(tipoPedido.compareToIgnoreCase("EXPANSION")==0) {
			regla="OXXO ACA EXPANSION";
		}
		return regla;
	}
	
	/**
	 *  Para despachar un viaje con pedidos, se requiere agregar la regla con la que deben ser asignados, hasta el
	 *  momento se evaluan los pedidos tipo EXPANSION y REPOSICION
	 *  
	 *  @param tipoPedido tipo de pedido EXPANSION o REPOSICION para asignar la regla correspondiente
	 *  @return regla     nombre de la regla que se le debe asignar al viaje según su tipo de pedidos
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	/*public void despacharPedidoSinJuegoDocumentos(String tipoPedido, String viaje) throws FindFailed {
		String regla="OXXO ACA REP AUTPICK";
		if(tipoPedido.compareToIgnoreCase("EXPANSION")==0) {
			regla="OXXO ACA EXPANSION";
		}
		sk.screen.type(regla);
		nTAB(1);
		u.hardWait(3);
		clickImagen("envioDespacho");
		u.hardWait(6);
		sk.screen.type(viaje);
		nTAB(1);
		u.hardWait(2);
		clickImagen("concurrenteDespacho");
		u.hardWait(5);
	}*/
	
	/**
	 *  Método para abrir las solicitudes desde el navegador de la aplicación
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public void navegadorVerSolicitudes() throws FindFailed {
		clickImagen("ver");
		u.hardWait(10);
		nDown(10, true);
	}
	
	/**
	 *  Método para seleccionar la casilla de pedido y capturarlo
	 *  
	 *  @param texto  número de pedido a capturar
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public void capturarPedido(String texto) {
		nTAB(8);
		sk.screen.type(texto);
		nTAB(2);
	}
	
	
	/**
	 * Abrir la información de la solicitud
	 *  
	 * @param texto  número de pedido a capturar
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public void abrirSolicitudes() throws FindFailed {
		nTAB(8);
		u.hardWait(15);
		sk.screen.type(Key.ENTER);
	}
	
	/**
	 *  Captura el pedido que que se va a buscar para asignar o consultar el viaje en la pantalla
	 *  Transacciones de Envio
	 *  
	 * @param pedido  número de pedido que se va a capturar en la pantalla
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public void buscarPedidoEnTransaccionesDeEnvio(String pedido) throws FindFailed {
		clickImagen("AdministracionConsultasSistemaOrigen");
		capturarPedido(pedido);
		clickImagen("AdministradorConsultaEncontrar");
		u.hardWait(20);
	}
	
	/**
	 *  Captura el pedido que que se va a buscar para asignar o consultar el viaje en la pantalla
	 *  Transacciones de Envio
	 *  
	 * @param pedido  número de pedido que se va a capturar en la pantalla
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	
	public void seleccionarTodo() throws FindFailed {
		clickImagen("SeleccionArea");
		u.hardWait(10);
		clickImagen("NavegadorEdicion");
		u.hardWait(10);
		nDown(8,true);
		u.hardWait(20);
	}
	
	/**
	 *  Después de realizar la búsqueda del pedido y asignarle una entrega, se asigna el número de viaje
	 *  para continuar con el flujo
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	
	public void asignarViajeAlPedido() throws FindFailed {
		clickImagen("ComboAccionesTransaccion");
		u.hardWait(15);
		nDown(2, false);
		u.hardWait(10);
		nUp(9);
		u.hardWait(10);
		clickImagen("TransaccionIr");
		u.hardWait(10);
	}
	
	/**
	 *  Método para abrir el navegador de pantallas y seleccionar la pantalla para asignar puerto
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	
	public void abrirVentanaDeMuelle() throws FindFailed {
		clickImagen("Ventana");
		u.hardWait(10);
		nDown(4, true);
		u.hardWait(15);
		clickImagen("Admon");
		u.hardWait(10);
		clickImagen("Mas");
		u.hardWait(10);
		clickImagen("logicaSalida");
		u.hardWait(10);
		clickImagen("Mas");
		u.hardWait(10);
		clickImagen("asignarMuelle");
		u.hardWait(15);
		clickImagen("AbrirNavegador");
		u.hardWait(15);
		clickImagen("BotonNuevoMuelle");
		u.hardWait(10);
	}
	
	
	/**
	 *  Método para abrir el navegador de pantallas y seleccionar despachar pedido
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public void abrirDespacharPedido() throws FindFailed {
		clickImagen("Ventana");
		u.hardWait(30);
		nDown(4, true);
		u.hardWait(40);
		clickImagen("pedidosVenta");
		u.hardWait(40);
		clickImagen("Mas");
		u.hardWait(40);
		clickImagen("despacharPedidos");
		u.hardWait(45);
		clickImagen("AbrirNavegador");
		u.hardWait(45);
	}
	
	/**
	 *  Método para cerrar el mensaje de solicitud generada después de despachar el viaje
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public void aceptarMensajeSolicitud() throws FindFailed {
		clickImagen("AceptarSolicitud");
	}
	/**
	 *  Método para abrir ventana de Solicitudes 
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	
	public void consultarSolicitudes() throws FindFailed {
		navegadorVerSolicitudes();
		u.hardWait(15);
		abrirSolicitudes();
		u.hardWait(20);
	}
	
	
	/**
	 *  Método para capturar y encontrar una colicitud específica
	 *  
	 *  @param solicitud  Número de solicitud a consultar
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public void encontrarSolicitud(String solicitud) throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
		clickImagen("encontrarSolicitudBoton");
		u.hardWait(10);
		clickImagen("solicitudesEspecificas");
		u.hardWait(10);
		nTAB(1);
		u.hardWait(10);
		sk.screen.type(solicitud);
		u.hardWait(10);
		nTAB(6);
		u.hardWait(10);
		sk.screen.type(Key.ENTER);
		u.hardWait(10);
	}
	
	/**
	 *  Método para seleccionar la información de parámetros en la pantalla solicitudes y abrir su información
	 *  
	 *  @param numTab  indica el npumero de casillas a recorrer en la pantalla solicitudes
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public void visualizarSolicitud(int numTab) throws FindFailed {
		nTAB(numTab);
		u.hardWait(10);
		sk.screen.type(Key.ENTER);
	}
	
	/**
	 *  Método para obtener el valor del texto actual seleccionado copiado y almacenado en portapapeles
	 *  
	 *  @return valorParametro valor del texto seleccionado y copiado
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerValorParametros() throws FindFailed, UnsupportedOperationException, IOException, UnsupportedFlavorException{
		sk.screen.type("c",Key.CTRL);
		String valorParametro = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);
		return valorParametro;
	}
	
	
	/**
	 *  Método para obtener los campos de fecha de la ventana Parámetros cuando se consulta la información de la solicitud
	 *  
	 *  @param fechaSalida indica si se obtendrá la fecha de la ventana anteriro a los parámetros de solicitud o de la ventana parámetros
	 *  
	 *  @return fechaInicio regresa el valor de fecha obtenido 
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public String fechasParametros(boolean fechaSalida) throws FindFailed, UnsupportedOperationException, IOException, UnsupportedFlavorException{
		if(fechaSalida) {
			clickImagen("normal");
			u.hardWait(5);	
			nTAB(1);
		}else {
			nTAB(1);
		}
		u.hardWait(10);
		sk.screen.type("c",Key.CTRL);
		String fechaInicio = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);
		return fechaInicio;
	}
	

	/**
	 *  Método para obtener el estatus de un pedido desde la pantalla de organización de pedido
	 *  
	 *  @param pedido número de pedido a buscar en organización de pedido
	 *  
	 *  @return regresa el estatus del pedido, el cual debería ser ingresado
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public String buscarPedidoIngresado(String pedido) throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
		sk.screen.type(pedido);
		u.hardWait(20);
		nTAB(1);
		u.hardWait(20);
		clickImagen("encontrarPedidoIngresado");
		u.hardWait(20);
		nTAB(2);
		u.hardWait(20);
		clickImagen("NavegadorEdicion");
		u.hardWait(20);
		nDown(3, true);
		u.hardWait(20);
		String estadoPedido = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);
		return estadoPedido;
	}
	

	/**
	 *  Método para registrar un pedido después de consultarlo en organizador de pedido
	 *  
	 *  @param pedido número de pedido con estatus ingresado listo para registrar
	 *  @return estadoPedido obtener estatus del pedido después de realizar su registo, el valor esperado debe ser Registrado  
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	
	public String registrarPedido(String pedido) throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
		clickImagen("abrirPedidoIngresado");
		u.hardWait(10);
		clickImagen("registrarPedido");
		u.hardWait(10);
		sk.screen.type(Key.ENTER);
		u.hardWait(10);
		clickImagen("linterna");
		u.hardWait(20);
		nTAB(1);
		u.hardWait(10);
		clickImagen("encontrarPedidoIngresado");
		u.hardWait(10);
		nTAB(2);
		u.hardWait(10);
		clickImagen("NavegadorEdicion");
		u.hardWait(10);
		nDown(3, true);
		u.hardWait(10);
		String estadoPedido = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);
		return estadoPedido;
	}
	
	/**
	 *  Método para abrir la ventana de Organizador de Pedido desde el navegador de la aplicación
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public void abrirOrganizadorPedido() throws FindFailed {
		clickImagen("Ventana");
		u.hardWait(10);
		nDown(4, true);
		u.hardWait(10);
		clickImagen("Organizadorp");
		u.hardWait(10);
		clickImagen("AbrirNavegador");
		u.hardWait(40);
	}

	/**
	 *  Método para obtener la requisición del pedido consultado en Organizador de pedido
	 *  
	 *  @param pedido número de pedido a consultar
	 *  
	 *  @return req número de la requisición del pedido consultado
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerReq(String pedido) throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
		sk.screen.type(pedido);
		sk.screen.type(Key.ENTER);
		u.hardWait(5);
		nTAB(6);
		u.hardWait(5);
		clickImagen("NavegadorEdicion");
		nDown(3, true);
		u.hardWait(3);
		String req = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);
		return req;
	}
	
	/**
	 *  Método para obtener el número de pedido después de consultar la requisicón en organizador de pedido
	 *  
	 *  @param req número de requisicón a consultar en organizador de pedido
	 *  
	 *  @return pedidoExistente valor del primer pedido encontrado en la requisición
	 *  
	 * @author nellyoliviab@hexaware.com
	 */
	public String obtenerPedido(String req) throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
		nTAB(6);
		u.hardWait(3);
		sk.screen.type(req);
		sk.screen.type(Key.ENTER);
		clickImagen("NavegadorEdicion");
		nDown(3, true);
		u.hardWait(3);
		String pedidoExistente = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);
		return pedidoExistente;
	}
}
	

