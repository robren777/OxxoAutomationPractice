package EBS.Sikuli;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.sikuli.script.Button;
import org.sikuli.script.Env;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class PatternUtil {

	SikuliUtil sk;
	SeleniumUtil u;
	ArrayList<Pattern> patterns;
	File Folder;
	HashMap<String, Pattern> ImgPattern;
	ArrayList<String> ListImage;
	Pattern pathImg;
	protected static final Logger logger = LoggerFactory.getLogger(BaseExecution.class);

	/**
	 * @param sk         Instancia de Sikuli
	 * @param u          Instancia de SeleniumUtil
	 * @param pathImg
	 * @param ListImage
	 * @param ImgPattern
	 * @param patterns
	 * @param Folder     Carpeta de Imagenes
	 */
	public PatternUtil(SikuliUtil sk, SeleniumUtil u, Pattern pathImg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPattern, ArrayList<Pattern> patterns, File Folder) {
		this.sk = sk;
		this.u = u;
		this.patterns = patterns;
		this.Folder = Folder;
		this.ListImage = ListImage;
		this.pathImg = pathImg;
		this.ImgPattern = ImgPattern;

	}

	/**
	 * Genera una data de paths de las imagenes.
	 *
	 * @param folder Carpeta en la que se encuentras las imagenes
	 * @return ImgPattern El patter
	 */
	public HashMap<String, Pattern> getPatternsImg(File folder) {
		File[] files = Folder.listFiles();
		for (File s : files) {
			patterns.add(new Pattern(s.getAbsolutePath()));
		}
		for (Pattern p : patterns) {
			String fname = p.getFilename().toString();
			fname = fname.replace("\\", "-");
			fname = fname.replace(".png", "");
			fname = fname.replace(".PNG", "");
			String[] afname = fname.split("-");
			ImgPattern.put(afname[afname.length - 1], p);
		}

		return ImgPattern;
	}

	/**
	 * Metodo para manejar la ventana de advertencia que aparece al abrir un applet
	 *
	 * @author JesusH@hexaware.com
	 */
	public void runApplet() {
		System.out.println("Run Applet");
		ImgPattern = getPatternsImg(Folder);
		try {
			
				sk.screen.click(ImgPattern.get("checkBoxSecurityWarning"));
				
				
				System.out.println(ImgPattern.get("checkBoxSecurityWarning"));
				sk.screen.type(Key.ENTER);
			

		} catch (FindFailed e) {
			logger.error("error: " + e);
			System.out.println("error: " + e);
		}
		appletMaximize();
	}
	
	/**
	 * Metodo para manejar la ventana de advertencia que aparece al abrir un applet
	 *
	 * @author JesusH@hexaware.com
	 */
	public void runApplet2() {
		System.out.println("Run Applet");
		ImgPattern = getPatternsImg(Folder);
		try {
			
				sk.screen.click(ImgPattern.get("checkenviado"));
				System.out.println(ImgPattern.get("checkenviado"));
				sk.screen.type(Key.ENTER);
			

		} catch (FindFailed e) {
			logger.error("error: " + e);
			System.out.println("error: " + e);
		}
		appletMaximize();
	}
	
	public void ejemplo2() throws FindFailed {
		
		ImgPattern = getPatternsImg(Folder);
		sk.screen.click(ImgPattern.get("23"));
	
	}
	
	/**
	 * Da click en la imagen guardada en la carpeta Pattern
	 *
	 * @param imagen nombre de la imagen que se seleccionará
	 *
	 * @author nellyoliviab@hexaware.com
	 */
	public void clickImagen(String imagen) throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		sk.screen.click((ImgPattern.get(imagen)));
	}
	

	/**
	 * Maximiza la ventana enviando la combinacion de teclas WIN + UP
	 *
	 * @author JesusH@hexaware.com
	 */
	public void appletMaximize() {
		sk.screen.keyDown(Key.WIN + Key.UP);
		sk.screen.keyUp(Key.WIN + Key.UP);
	}
	
	/**
	 * ejemploooclick
	 *
	 * @author 2000103178
	 * @throws FindFailed 
	 */
	public void ejemploclick() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		sk.screen.click(ImgPattern.get("bo").similar(95));
	}

	/**
	 * Maneja la pantalla de solicitud unica, verifica si la imagen existe y
	 * presiona la tecla Enter
	 *
	 * @throws FindFailed Problemas con la imagen, no se encuentra o no existe
	 * @author JesusH@hexaware.com
	 */
	public void solicitudUnica() throws FindFailed {
		try {
			System.out.println("solicitud Unica");
			u.hardWait(15);
			ImgPattern = getPatternsImg(Folder);
			sk.screen.exists(ImgPattern.get("solicitudUnica"));
			sk.screen.type(Key.ENTER);
			u.hardWait(3);
		} catch (Exception exception) {
			logger.error("Error " + exception);
			System.out.println("Error " + exception);
		}

	}

	/**
	 * Escribe en una caja de texto que ya tenga el cursor activo y presiona la
	 * tecla TAB
	 *
	 * @param text Texto que se desea ingresar
	 * @author JesusH@hexaware.com
	 */
	public void typeAndTab(String text) {
		sk.screen.type(text);
		sk.screen.type(Key.TAB);
	}

	public String compararCelda(String strToFind)
			throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar todo el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("c");
		// El texto que copiamos
		String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		System.out.println(textFound); // Imprime el texto que copiamos
		String find = strToFind; // El String a buscar dentro de lo que copiamos
		String findResult = ""; // Lo que regresaermos

		int i = textFound.indexOf(find);
		if (i > 0) {
			findResult = textFound.substring(i, i + find.length());
			System.out.println(textFound.substring(i, i + find.length()));
		} else {
			findResult = "string not found";
			System.out.println("string not found");
		}
		return findResult; // Si lo encuentra lo regresa

	}

	/**
	 * Ingresa los parametros para la busqueda en la ventana de Parametros
	 *
	 * @param entidad
	 * @param fechaInicio
	 * @param fechaFin
	 * @author JesusH@hexaware.com
	 */
	public void typeParametros(String entidad, String fechaInicio, String fechaFin) {
		System.out.println("Ingresar Parametros ");
		u.hardWait(2);
		try {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.wait(ImgPattern.get("entidadMxCoAdmin"), 10);
			keyAlt("l");
			u.hardWait(2);
			typeAndTab(entidad);
			u.hardWait(2);
			sk.screen.type(fechaInicio);
			u.hardWait(2);
			sk.screen.type(fechaFin);
			u.hardWait(2);
			keyAlt("a");

		} catch (FindFailed e) {
			logger.error("No se encontro el patron " + e);
			System.out.println("No se encontro el patron " + e);
		}

	}

	/**
	 * Buscar la imagen del boton ejecutar y le da click
	 *
	 * @author JesusH@hexaware.com
	 * @throws FindFailed 
	 */
	public void clickEjecutar()  {
		
		keyAlt("j");
	}
	
	/**
	 * Realiza un click en la opcion no en el applet
	 *
	 * @author ricardor4@hexaware.com
	 * @throws FindFailed 
	 */
   public void clickNo()  {
		
		keyAlt("n");
	}

	/**
	 * Maneja la ventana de pregunta 'Ejecutar otra solicitud?'
	 *
	 * @param opcion Recibe un string "Si" o "No"
	 * @author JesusH@hexaware.com
	 */
	public void ejecutarOtraSolicitud(String opcion) {
		switch (opcion) {
		case "Si":
			keyAlt("s");
		case "No":
			keyAlt("n");
			break;
		default:
			throw new IllegalStateException("Unexpected value: " + opcion);
		}
	}

	/**
	 * Va al menu 'Ver' \ 'Solicitudes', envia la combinacion de teclas adecuada
	 * para buscar una solicitud desde el menu
	 *
	 * @author JesusH@hexaware.com
	 */
	public void encontrarSolicitudes() {
		sk.screen.keyDown(Key.ALT);
		sk.screen.type("x");
		sk.screen.type("s");
		sk.screen.keyUp(Key.ALT);
		u.hardWait(15);

	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'e', esto emula el clic en el boton
	 * encontrar
	 *
	 * @author JesusH@hexaware.com
	 */
	public void clickEncontrar() {
		keyAlt("e");
	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'l', esto emula el click en el boton
	 * Visualizar Output
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickVisualizarOutput() {
		keyAlt("l");
	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 't', esto emula el click en el boton
	 * Terminos de Pago de la ventana de Arrendamientos
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickDetallePago() {
		keyAlt("t");
	}

	/**
	 * Envia la combinacion de teclas 'Ctrl' + 'f', esto abre el atajo Buscar o Find
	 * en la App de Excel
	 *
	 * @param valor
	 * @author JoseO@hexaware.com
	 */
	public void BusquedaExcel(String valor) {

		try {
			sk.screen.type("f", Key.CTRL);
			u.hardWait(5);
			sk.screen.type(valor);
			u.hardWait(2);
			sk.screen.type(Key.ENTER);
			u.hardWait(10);
		} finally {
			System.out.println("Valor no encontrado");
		}

	}

	/**
	 * Emula presionar la tecla 'Alt' + alguna tecla que necesitemos (solo una)
	 *
	 * @param tecla Tecla a presionar
	 * @author JesusH@hexaware.com
	 */
	public void keyAlt(String tecla) {
		sk.screen.keyDown(Key.ALT);
		sk.screen.type(tecla);
		sk.screen.keyUp(Key.ALT);
	}

	/**
	 * Metodo que selecciona boton ver solicitudes
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void vers() throws FindFailed {
		u.hardWait(5);
		sk.screen.type("x", Key.ALT);
		u.hardWait(5);
		sk.screen.type("s");
		u.hardWait(5);

	}
	/**
	 * Cierra la ventana enviando la combinacion de teclas 'Alt' + 'F4'
	 *
	 * @author JesusH@hexaware.com
	 */
	public void closeWindow() {
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
	}

	/**
	 * Menu para cambiar Resposabilidades dentro del Applet enviando la combinacion
	 * de teclas 'Ctrl' + 'R' y seguido solo 'R'
	 *
	 * @author JoseO@hexaware.com
	 */
	public void menuResponsabilidades() {
		u.hardWait(3);
		sk.screen.type("r", Key.ALT);
		u.hardWait(5);
		sk.screen.type("r");
	}

	/**
	 * Cierra la ventana activa dentro del Applet enviando la combinacion de teclas
	 * 'Ctrl' + 'F4'
	 *
	 * @author JoseO@hexaware.com
	 */
	public void closeActiveWindow() {
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.CTRL);
	}

	public void genericCloseActiveWindow(String img) {

		ImgPattern = getPatternsImg(Folder);

		if (sk.screen.exists(ImgPattern.get("deseaSalir")) != null) {
			closeActiveWindow();

		} else {
			logger.debug("Imagen de la ventana activa no encontrada");
			System.out.println("Imagen de la ventana activa no encontrada");
		}
	}

	/**
	 * Hace click sobre el Estado de Pago de la ventana Cronogramas
	 *
	 * @throws FindFailed
	 */
	public void cambiarEstadoPagoCronogramas() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		sk.screen.exists(ImgPattern.get("provisorio"));
		u.hardWait(5);
		sk.screen.click(ImgPattern.get("provisorio"));
		u.hardWait(3);
		sk.screen.type(Key.DOWN);
		u.hardWait(3);
		sk.screen.type(Key.ENTER);

	}

	/**
	 * Ventana de confirmacion para salir de Oracle Applications
	 *
	 * @param x
	 * @throws FindFailed
	 */
	public void ventanaConfirmarSalir(String x) throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		sk.screen.exists(ImgPattern.get("deseaSalir"));
		keyAlt(x);
		u.hardWait(5);
	}
	
	/**
	 * Valida el estatus final de un concurrente
	 *
	 *
	 *@author rromero@hexaware.com
	 */
	public boolean validarEstadoFinal() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		sk.screen.exists(ImgPattern.get("validaestatus"));
		return true;
	}
	
	/**
	 * Valida el estatu sde rfc
	 *
	 *
	 *@author rromero@hexaware.com
	 */
	public boolean validarEstadoRfc()  {
		
		ImgPattern = getPatternsImg(Folder);
		if(sk.screen.exists(ImgPattern.get("validarfc")) != null)
		{
			
			return true;
		}
	   else{
		   
		   return false;

	}	
	}
	
	/**
	 * Valida el actualizacion de item
	 *
	 *
	 *@author rromero@hexaware.com
	 */
	public boolean validarMensajeActualizacion() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		sk.screen.exists(ImgPattern.get("validaractualizacion"));
		return true;
	}
	
	
	/**
	 * Click y escritura en caja de texto de tienda
	 * 
	 * @param tienda
	 * @param fechaInicial
	 * @param fechaFinal
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void escribirCajaTienda(String tienda, String fechaInicial, String fechaFinal) throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("cajaTienda")).click();
		sk.screen.type(Key.DELETE);
		sk.screen.type(tienda);
		this.nTAB(1);
		sk.screen.type(Key.DELETE);
		sk.screen.type(fechaInicial);
		this.nTAB(1);
		sk.screen.type(Key.DELETE);
		sk.screen.type(fechaFinal);
		this.nTAB(1);
	}
	
	/**
	 * Click y escritura en caja de texto de plaza
	 * 
	 * @param plaza
	 * @param tienda
	 * @param inventario
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void escribirCajaPlaza(String plaza, String tienda, String inventario) throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("cajaPlaza")).click();
		sk.screen.type(plaza);
		u.hardWait(2);
		sk.screen.type(tienda);
		this.nTAB(1);
		sk.screen.type(inventario);
		this.nTAB(1);
	}
	
	//ejemplo
	public Boolean clickElementSikuli(String Folder, String image) throws FindFailed {

		   //u.waitForLoadPage();
		   Boolean exist = false;
		   Screen s = new Screen();

	       SikuliUtil sikuli = new SikuliUtil(); //Crea un elemento del tipo Sikuli
	       String s1 = sikuli.PatternPath(Folder); //Si la carpeta donde se indica que esta la imagen no ha sido creada, la crea
	       Pattern p1 = sikuli.PatternImage(s1 + image + ".png"); // Crea la imagen del tipo Patter

	       //System.out.print("Entrando al metodo \n");

	       //Pregunta si existe la imagen en pantalla
	       if (sikuli.screen.exists(p1) != null) {
	    	   //System.out.print("Entrando al if \n");
	    	   exist = true;
	           sikuli.screen.click(p1);
	       }else {
	    	   System.out.print("El elemento no existe");
	    	   //sikuli.screen.wait(p1.similar((float).60),15).click();
	       }
		return exist;

	     
	 }
	

	/**
	 * Ventana de confirmacion para abrir archivo de Excel
	 *
	 * @throws FindFailed
	 * @author JoseO@Hexaware
	 */
	public void ventanaWarningExcel() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);

		if (sk.screen.exists(ImgPattern.get("ExcelOpenFileWarning")) != null) {

			sk.screen.exists(ImgPattern.get("ExcelOpenFileWarning"));
			u.hardWait(3);
			sk.screen.click(ImgPattern.get("ExcelOpenFileWarning"));
			u.hardWait(2);
			sk.screen.type(Key.TAB);
			u.hardWait(1);
			sk.screen.type(Key.ENTER);
		} else
			System.out.println("Advertencia no mostrada");

	}
	
	
	/**
	 * Metodo que ejecuta un Alt+F4
	 */
	public void altF4() {
		sk.screen.keyDown(Key.ALT + Key.F4);
		sk.screen.keyUp(Key.ALT + Key.F4);
	}

	/**
	 * Metodo para seleccionar mediante click a la Imagen de la responsabilidad
	 * seleccionada
	 *
	 * @param responsabilidad
	 * @throws FindFailed
	 * @author JoseO@Hexaware
	 */
	public void ClickenResponsabilidad(String responsabilidad) throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		sk.screen.exists(ImgPattern.get(responsabilidad));
		u.hardWait(3);
		sk.screen.click(ImgPattern.get(responsabilidad));
		u.hardWait(2);
		sk.screen.type(Key.ENTER);
		u.hardWait(10);

	}

	/**
	 * Hace el proceso para la consulta del numero de contrato
	 *
	 * @return Flag con true si status de la consulta es activo
	 * @throws FindFailed
	 * @author JoseO@Hexaware
	 */
	public boolean ConsultaNumeroContrato() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		boolean flag = false;

		if (sk.screen.exists(ImgPattern.get("StatusActivo")) != null) {

			System.out.println("SI ESTA ACTIVO!!!!!");
			sk.screen.exists(ImgPattern.get("ArrendamientosDirecciones"));
			u.hardWait(3);
			sk.screen.click(ImgPattern.get("ArrendamientosDirecciones"));
			u.hardWait(5);

			sk.screen.click(ImgPattern.get("ArrendamientosPagos"));
			u.hardWait(3);
			sk.screen.click(ImgPattern.get("ArrendamientosPagos"));
			u.hardWait(5);
			clickDetallePago();
			u.hardWait(10);
			sk.screen.click(ImgPattern.get("DetalleAlquilerDistCuentas"));
			u.hardWait(3);
			sk.screen.click(ImgPattern.get("DetalleAlquilerDistCuentas"));
			u.hardWait(5);
			flag = true;

		}

		return flag;
	}

	/**
	 * Hace el proceso para la consulta del numero de contrato
	 *
	 * @param usuario
	 * @param password
	 * @throws FindFailed
	 * @author JoseO@Hexaware
	 */
	public void skLogin(String usuario, String password) throws FindFailed {
		ImgPattern = getPatternsImg(Folder);

		if (sk.screen.exists(ImgPattern.get("login")) != null) {

			typeAndTab(usuario);
			u.hardWait(2);
			typeAndTab(password);
			u.hardWait(2);
			sk.screen.type(Key.ENTER);
			u.hardWait(5);
		}

	}

	/**
	 * Selecciona el campo Tipo de Reporte de la ventana Parametros
	 *
	 * @throws FindFailed
	 * @author JoseO@Hexaware
	 * @author JoseO@Hexaware
	 */
	public void CampoTipoReporte() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		sk.screen.exists(ImgPattern.get("TipoReporteParam"));

		u.hardWait(3);
		sk.screen.click(ImgPattern.get("TipoReporteParam"));
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
	}

	/**
	 * Metodo para abrir el menu de opciones de cualquier campo de texto donde se
	 * introducen parametros
	 *
	 * @throws FindFailed
	 * @author JoseO@Hexaware.com
	 */

	public void openOptions() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		sk.screen.exists(ImgPattern.get("trespuntos"));
		u.hardWait(3);
		sk.screen.click(ImgPattern.get("trespuntos"));
		u.hardWait(5);

	}

	/**
	 * Realiza la busqueda de Asientos ingresando los parametros de Lote y Periodo
	 *
	 * @param lote
	 * @param periodo
	 * @author JoseO@Hexaware
	 */
	public void encontrarAsientos(String lote, String periodo) {

		sk.screen.type(lote);
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		u.hardWait(3);
		sk.screen.type(Key.TAB);
		u.hardWait(3);
		sk.screen.type(Key.TAB);
		u.hardWait(3);
		sk.screen.type(Key.TAB);
		u.hardWait(3);
		sk.screen.type(periodo);
		u.hardWait(5);
	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'n', esto emula el click en el boton
	 * Ejecutar Nueva Solicitud
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickEjecutarNuevaSolicitud() {
		keyAlt("n");
		u.hardWait(5);
	}
	
	/**
	 *realiza el llenado de campos de neteos al momento de crear una factura
	 *
	 * @param concurrente: nombre del concurrente
	 * @param origen: nombre del origen
	 * @param lote: nombre del lote
	 * @param fecha: fecha del proceso
	 * @throws FindFailed
	 * 
	 * @author ricardor4@hexaware.com
	 */
	public void ingresarCamposNeteos1(String unidad,String concurrente, String origen, String lote, String fecha) throws FindFailed {

		sk.screen.type(concurrente);
		sk.screen.type(Key.TAB);
		u.hardWait(6);
		this.nTAB(1);
		sk.screen.type(unidad);
		this.nTAB(1);
		u.hardWait(5);
		sk.screen.type(origen);
		this.nTAB(2);
		sk.screen.type(lote);
		this.nTAB(3);
		sk.screen.type(fecha);
		this.nTAB(2);
		this.nEnter(1);
	}
	
	/**
	 *realiza el llenado de campos de neteos al momento de crear una factura sin cargos logisticos
	 *
	 * @param concurrente: nombre del concurrente
	 * @param origen: nombre del origen
	 * @param fecha: fecha del proceso
	 * @throws FindFailed
	 * 
	 * @author ricardor4@hexaware.com
	 */
	public void ingresarCamposNeteos3(String concurrente, String origen, String fecha) throws FindFailed {

		sk.screen.type(concurrente);
		sk.screen.type(Key.TAB);
		this.nEnter(1);
		u.hardWait(6);
		sk.screen.type(origen);
		u.hardWait(3);
		this.nTAB(1);
		sk.screen.type(fecha);
		this.nTAB(22);
		this.nEnter(1);
	}

	/**
	 * Metodo que selecciona boton ejecutar nueva solicitud
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ejecutarNuevaSolicitud() throws FindFailed {

		/*SikuliUtil su = new SikuliUtil();

		///////////////////////////////////
		Screen s = new Screen(0);//Líneas añadidas por segundo monitor
		su.setScreen(s); //Líneas añadidas por segundo monitor
		///////////////////////////////////

		Region botonBuscar = su.getRegion("NETEOS/er.png");

		botonBuscar.click();*/
		ImgPattern = getPatternsImg(Folder);
		//sk.screen.wait(ImgPattern.get("er"), 6).click();
		//sk.screen.click(ImgPattern.get("listacompleta").similar((float).10));
		sk.screen.click(ImgPattern.get("er"));

	}
	
	public void ejemplo() {
		
		try {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.wait(ImgPattern.get("listacompleta"), 10).click();
		} catch (FindFailed e) {
			logger.error("No se encontro el patron " + e);
			System.out.println("No se encontro el patron " + e);
		}

	}
	
	
	/**
	 * Envia la combinacion de teclas 'Alt' + 'l', esto emula el click en el boton
	 * de Limpiar
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickLimpiar() {
		keyAlt("l");
	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'c', esto emula el click en el boton
	 * Cerrar de la Ventana Parametros de una nueva Solicitud
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickCerrarVentanaParametros() {
		keyAlt("c");
	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'i', esto emula el click en el boton
	 * Revisar Asiento de la ventana Asientos
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickRevisarAsiento() {
		keyAlt("i");
	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'n', esto emula el click en el boton
	 * Encontrar Arrendamiento
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickEncontrarArrendamiento() {
		keyAlt("n");
	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'a', esto emula el click en el boton
	 * Aceptar de la ventana parametros de una Nueva solicitud
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickAceptarParametros() {
		keyAlt("a");
	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'b', esto emula el click en el boton
	 * Abrir de la ventana de Arrendamientos
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickAbrirArrendamiento() {
		keyAlt("b");
	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'v', esto emula el click en el boton
	 * del menu de Ventana
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickMenuVentana() {
		keyAlt("v");
	}

	/**
	 * Hace scroll Down en la seccion activa de la pantalla x representa el numero
	 * de veces que simula el giro de la rueda de scroll
	 *
	 * @param x
	 * @author JoseO@hexaware.com
	 */
	public void ScrollDown(int x) {
		sk.screen.wheel(Button.WHEEL_DOWN, x);
	}

	/**
	 * Hace scroll Up en la seccion activa de la pantalla x representa el numero de
	 * veces que simula el giro de la rueda de scroll
	 *
	 * @param x
	 * @author JoseO@hexaware.com
	 */
	public void ScrollUp(int x) {
		sk.screen.wheel(Button.WHEEL_DOWN, x);
	}

	/**
	 * Cierra la ventana actual
	 *
	 * @author JoseO@hexaware.com
	 */
	public void closeWindowUsingX() {

		ImgPattern = getPatternsImg(Folder);
		sk.screen.exists(ImgPattern.get("cerrar"));
		u.hardWait(3);
	}

	/**
	 * Da clic en la Funcion 'Aprobar y Exportar Pagos'
	 *
	 * @throws FindFailed
	 * @author JoseO@Hexaware.com
	 */
	public void clickAprobarExportarPagos() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("aprobarexportarpagos"), 15).click();
		u.hardWait(2);
		sk.screen.doubleClick();

	}

	/**
	 * Da click a un a la celda comparando la imagen con un texto
	 *
	 * @param dato
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void compararImagenEstadoPago(String dato) throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("estadopago"), 15).existsText(dato);

	}

	/**
	 * Da clic en el boton Estado de Pago en la ventana Encontrar Cronogramas de
	 * Pago cambiando de Provisorio que es valor por defecto a Aprobado
	 *
	 * @throws FindFailed
	 * @author JoseO@Hexaware.com
	 */
	public void clickEstadoPago() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("provisorio"), 15).click();
		u.hardWait(2);
		sk.screen.click(ImgPattern.get("aprobado"));

	}

	/**
	 * Busca un cambio en un campo de Estado que aparece en fondo verde, cuando este
	 * estado cambia se visualiza el Registro
	 *
	 * @param patternToSearch Imagen que se desea monitorizar
	 * @param timeOut         Tiempo de espera, se considera que el paso ha fallado
	 *                        si este tiempo es excedido
	 * @author JesusH@hexaware.com
	 */
	public void verificarEstado(String patternToSearch, Integer timeOut) {
		PatternUtil patternUtil = new PatternUtil(sk, u, pathImg, ListImage, ImgPattern, patterns, Folder);
		MonitoringPattern monitoringPattern = new MonitoringPattern(sk, patternUtil, ImgPattern, Folder,
				patternToSearch);
		Thread threadRunJob = new Thread(monitoringPattern);
		threadRunJob.start();
		int time = 0;
		while (threadRunJob.isAlive() && time <= timeOut) {
			try {
				Thread.sleep(2000);
				time = time + 2000;

			} catch (InterruptedException e) {
				logger.error(String.valueOf(e));
				e.printStackTrace();
			}

		}

		if (threadRunJob.isAlive()) {
			monitoringPattern.stopping();
		}

	}

	/**
	 * Envia un pattern para localizar una ventana y darle click para que la ventana
	 * tome el foco
	 *
	 * @param pattern Imagen para localizar la ventana a la que queremos dar click
	 * @param timeOut Tiempo de espera
	 * @author JesusH@hexaware.com
	 */
	public void focusWindow(String pattern, Integer timeOut) {
		try {
			sk.screen.wait(ImgPattern.get(pattern), timeOut).click();
		} catch (FindFailed findFailed) {
			logger.error("No se encontro " + pattern + " " + findFailed);
			System.out.println("No se encontro " + pattern + " " + findFailed);
		}

	}

	/**
	 * Verifica el pattern 'securityWarning' para darle click y poner en foco la
	 * ventana de advertencia
	 *
	 * @author JesusH@hexaware.com
	 */
	public void focusWindow() {
		try {
			sk.screen.wait(ImgPattern.get("securityWarning"), 5).click();
		} catch (FindFailed findFailed) {
			logger.error("No se encontro 'securityWarning' " + findFailed);
			System.out.println("No se encontro 'securityWarning' " + findFailed);
		}

	}

	/**
	 * Maneja la ventana de confirmacion para ejecutar el applet en la plataforma
	 * EBS internacional
	 *
	 * @author JesusH@hexaware.com
	 */
	public void runAppletEbsInternacional() {

		runApplet();
		u.hardWait(5);
		focusWindow();
		runApplet();
		u.hardWait(5);
		// Load another IE instance
		focusWindow();
		runApplet();
		u.hardWait(5);
		focusWindow();
		runApplet();

	}

	/**
	 * Submit a new request window, usa el teclado para seleccionar el tipo de
	 * solicitud y presionar el boton de "Aceptar"
	 *
	 * @param typeOfRequest English: "r" single request, "s" set of requests.
	 *                      Spanish: "s" single request, "" set of requests
	 * @param OK            English: "o"; Spanish: "a";
	 * @author JesusH@hexaware.com
	 */
	public void submitANewRequest(String typeOfRequest, String OK) {
		keyAlt(typeOfRequest);
		keyAlt(OK);
	}

	/**
	 * Metodo que da click en el navegador en base a una imagen, selecciona toda el
	 * texto y busca un String dentro del texto.
	 *
	 * @param nameImgToClick
	 * @param strToFind
	 * @return Regresa el text encontrado
	 * @throws FindFailed
	 * @throws HeadlessException
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 * @author edwin.ramirez
	 */
	public String getTextDelNavegador(String nameImgToClick, String strToFind)
			throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
		sk.screen.wait(ImgPattern.get(nameImgToClick), 15).click(); // Encuentra la imagen a dar click en el navegador

		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+A para marcar todo el texto y suelta las teclas
		sk.screen.keyDown("a");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("a");

		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar todo el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("c");
		// El texto que copiamos
		String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		System.out.println(textFound); // Imprime el texto que copiamos
		String find = strToFind; // El String a buscar dentro de lo que copiamos
		String findResult = ""; // Lo que regresaremos

		int i = textFound.indexOf(find);
		if (i > 0) {
			findResult = textFound.substring(i, i + find.length());
			System.out.println(textFound.substring(i, i + find.length()));
		} else {
			findResult = "string not found";
			System.out.println("string not found");
		}

		// sk.screen.wait(ImgPattern.get("cerrarexplorer"), 15).click(); //cierra el
		// navegador
		return findResult; // Si lo encuentra lo regresa
	}

	/**
	 * Ingresa un parametro para la busqueda en la ventana de Parametros
	 *
	 * @param respaldo
	 * @author JessicaR@hexaware.com
	 */
	public void typeParametro(String respaldo) {
		System.out.println("Ingresar Parametros ");
		u.hardWait(2);
		try {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.wait(ImgPattern.get("respaldo"), 15);
			// keyAlt("l");
			u.hardWait(2);
			typeAndTab(respaldo);
			u.hardWait(2);
			keyAlt("a");

		} catch (FindFailed e) {
			logger.error("No se encontro el patron " + e);
			System.out.println("No se encontro el patron " + e);
		}

	}

	
	/**
	 * Ingresa un parametro para la busqueda 
	 *
	 * @param StrToFind 
	 * parametro a buscar en clipboard
	 * @author 2000103178
	 * 
	 */
	public String getTextParaErrores(String strToFind)
			throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
			
		
		    u.hardWait(5);
		    sk.screen.click();
			sk.screen.keyDown(Key.CTRL); // Presiona CTRL+A para marcar todo el texto y suelta las teclas
			sk.screen.keyDown("a");
			sk.screen.keyUp(Key.CTRL);
			sk.screen.keyUp("a");

			sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar todo el texto y suelta las teclas
			sk.screen.keyDown("c");
			sk.screen.keyUp(Key.CTRL);
			sk.screen.keyUp("c");
			
			
			// El texto que copiamos
			
			
			
			String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			System.out.println(textFound); // Imprime el texto que copiamos
			String find = strToFind; // El String a buscar dentro de lo que copiamos
			String findResult = ""; // Lo que regresaremos

			int i = textFound.indexOf(find);
			if (i > 0) {
				findResult = textFound.substring(i, i + find.length());
				System.out.println(textFound.substring(i, i + find.length()));
			} else {
				findResult = "factura generada";
				System.out.println("string not found");
			}


			return findResult;


			// Si lo encuentra lo regresa
		}
	
	/**
	 * Hace clic en una imagen
	 *
	 * @param pattern Imagen a la que le queremos dar clic
	 * @param timeOut Tiempo de espera para encontrar la imagen
	 * @author JesusH@hexaware.com
	 */
	public void clickPattern(String pattern, Integer timeOut) {
		try {
			System.out.println(pattern);
			sk.screen.wait(ImgPattern.get(pattern), timeOut).click();
		} catch (FindFailed findFailed) {
			logger.error("Error " + findFailed);
			System.out.println("Error " + findFailed);
		}
	}

	/**
	 * Doble clic en una imagen
	 *
	 * @param pattern Imagen a la cual se le dara el doble clic
	 * @param timeOut Tiempo para encontrar la imagen o marcar un error
	 * @author JesusH@hexaware.com
	 */
	public void doubleClickPattern(String pattern, Integer timeOut) {
		try {
			sk.screen.wait(ImgPattern.get(pattern), timeOut).doubleClick();
		} catch (FindFailed findFailed) {
			logger.error("No se encontro " + pattern + " " + findFailed);
			System.out.println("No se encontro " + pattern + " " + findFailed);
		}
	}

	/**
	 * Copiar un texto usando el teclado CTRL+C
	 *
	 * @return Retorna el texto copiado
	 * @throws HeadlessException
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 * @author JesusH@hexaware.com
	 */
	public String textCopy() throws HeadlessException, UnsupportedFlavorException, IOException {
		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		// El texto que copiamos
		String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		System.out.println("Texto copiado " + textFound); // Imprime el texto que copiamos
		logger.info("Texto copiado " + textFound);
		return textFound;
	}

	/**
	 * Simula presionar las teclas SHIFT + TAB
	 *
	 * @author JesusH@hexaware.com
	 */
	public void shiftTab() {
		sk.screen.keyDown(Key.SHIFT);
		sk.screen.type(Key.TAB);
		sk.screen.keyUp(Key.SHIFT);
	}

	/**
	 * Localiza una imagen, da clic al lado derecho de la imagen en un numero de
	 * pixeles y escribe el texto enviado, se utiliza cuando la caja de texto se
	 * encuentra a la derecha del label (imagen buscada)
	 *
	 * @param image      Imagen a localizar
	 * @param number     Distancia en pixeles donde se dara click
	 * @param textToType Texto a escribir en la caja de texto
	 * @author JesusH@hexaware.com
	 */
	public void textBoxTypeRight(String image, int number, String textToType) {
		try {
			sk.screen.find(ImgPattern.get(image)).right(number).click();
			u.hardWait(1);
			typeAndTab(textToType);
		} catch (FindFailed findFailed) {
			logger.error("Imagen no encontrada " + findFailed);
			System.out.println("Imagen no encontrada " + findFailed);
		}

	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'd', esto emula el click en el boton
	 * visualizar detalles
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickVisualizarDetalles() {
		keyAlt("d");
	}

	/**
	 * Da clic en aceptar los Detalles de la solicitud
	 *
	 * @throws FindFailed
	 * @author JessicaR@hexaware.com
	 */
	public void clickAceptarDetalle() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("detalleAceptar"), 15).click();

	}

	/**
	 * Envia la combinacion de teclas 'Alt' + 'a', esto emula el click en el boton
	 * save as
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickSave() {
		keyAlt("s");
		u.hardWait(3);
		// keyAlt("s");
	}

	/**
	 * Da clic en open para ver el archivo
	 *
	 * @throws FindFailed
	 * @author JessicaR@hexaware.com
	 */
	public void clickOpenFile() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("open"), 15).click();
		// keyAlt("y");

	}

	/**
	 * Da clic en el applet para continuar con las demás ejecuciones
	 *
	 * @throws FindFailed
	 * @author JessicaR@hexaware.com
	 */
	public void clickOracle() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("oracle"), 15).click();
		// keyAlt("y");

	}

	/**
	 * Busca un cambio en la descarga de un archivo cuando el estado cambia a Open
	 * se puede ver el archivo
	 *
	 * @param patternToSearch
	 * @param timeOut
	 * @author JessicaR@hexaware.com
	 */
	public void openFile(String patternToSearch, Integer timeOut) {
		PatternUtil patternUtil = new PatternUtil(sk, u, pathImg, ListImage, ImgPattern, patterns, Folder);
		OpenPattern openPattern = new OpenPattern(sk, patternUtil, ImgPattern, Folder, patternToSearch);
		Thread threadFile = new Thread(openPattern);
		threadFile.start();
		int time = 0;
		while (threadFile.isAlive() && time <= timeOut) {
			try {
				Thread.sleep(3000);
				time = time + 3000;

			} catch (InterruptedException e) {
				logger.error(String.valueOf(e));
				e.printStackTrace();
			}

		}

		if (threadFile.isAlive()) {
			openPattern.stopping();
		}

	}

	/**
	 * Busca si existe información en el archivo de excel
	 *
	 * @return Boolean
	 * @throws FindFailed
	 * @throws HeadlessException
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 * @author JessicaR@hexaware.com
	 */
	public boolean existeInformacion() throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {

		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("celda10"), 15).click();

		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar todo el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("c");
		// El texto que copiamos
		String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		System.out.println(textFound); // Imprime el texto que copiamos

		if (textFound.isEmpty()) {
			System.out.println("Sin datos");
			System.out.println("Error");
			return false;
		} else {
			System.out.println("Contiene información");
			System.out.println("Validación exitosa");
			return true;
		}

	}

	/**
	 * Busca si No existe información en el archivo de excel
	 *
	 * @return Boolean
	 * @throws FindFailed
	 * @throws HeadlessException
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 * @author JessicaR@hexaware.com
	 */
	public boolean noExisteInformacion() throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {

		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("celda10"), 15).click();

		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar todo el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("c");
		// El texto que copiamos
		String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		System.out.println(textFound); // Imprime el texto que copiamos

		if (textFound.isEmpty()) {
			System.out.println("Sin datos");
			System.out.println("Validacion exitosa");
			return true;
		}
		System.out.println("Contiene información");
		System.out.println("Error");
		return false;

	}

	/**
	 * Simula presionar F11 para realizar una consulta
	 */
	public void f11() {
		sk.screen.keyDown(Key.F11);
		sk.screen.keyUp(Key.F11);
	}

	/**
	 * Simula presionar las teclas SHIFT + TAB
	 */
	public void shiftKeyTab() {
		sk.screen.keyDown(Key.SHIFT);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyUp(Key.SHIFT);
	}

	/**
	 * Cierra una ventana dentro del applet al emular presionar CTRL + F4
	 */
	public void closeWindowInsideApplet() {
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.CTRL);
	}
	
	/**
	 * Simular 14 downs
	 */
	public void downs() {
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
	}
	
	public void BuscarResponsabilidadNeteos() {
		
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.CTRL);
		
		u.hardWait(5);
		sk.screen.type("r", Key.ALT);
		u.hardWait(5);
		sk.screen.type("r");
		u.hardWait(5);
		
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		
		
		
		
	}

	/**
	 * Simula un CTRL + F11 , utilizado para ejecutar una busqueda en el applet
	 */
	public void ctrlF11() {
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown(Key.F11);
		sk.screen.keyUp(Key.F11);
		sk.screen.keyUp(Key.CTRL);
	}
	
	public void AbrirBuscarFolioExcel(String folio) {
		
		u.hardWait(5);
		sk.screen.keyDown(Key.LEFT);
		u.hardWait(4);
		sk.screen.keyDown(Key.ENTER);
		u.hardWait(30);
		sk.screen.type("f", Key.CTRL);
		u.hardWait(5);
		sk.screen.type(folio);
		sk.screen.type(Key.ENTER);
		u.hardWait(5);
	}

	/**
	 * Localiza una imagen, da clic al abajo de la imagen en un numero de pixeles y
	 * escribe el texto enviado, se utiliza cuando la caja de texto se encuentra a
	 * la derecha del label (imagen buscada)
	 *
	 * @param image      Imagen a localizar
	 * @param number     Distancia en pixeles donde se dara click
	 * @param textToType Texto a escribir en la caja de texto
	 * @author JesusH@hexaware.com
	 */
	public void textBoxTypeBelow(String image, int number, String textToType) {
		try {
			sk.screen.find(ImgPattern.get(image)).below(number).click();
			u.hardWait(1);
			typeAndTab(textToType);
		} catch (FindFailed findFailed) {
			logger.error("Imagen no encontrada " + findFailed);
			System.out.println("Imagen no encontrada " + findFailed);
		}

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
	 * Valida si existe el patron a buscar
	 *
	 * @param target Imagen a buscar
	 * @return True o False utilizado para hacer un assert
	 * @throws FindFailed
	 * @author JesusH@hexaware.com
	 */
	public Boolean validatePattern(String target) throws FindFailed {
		if (sk.screen.exists(ImgPattern.get(target)) != null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Emula presionar la tecla DOWN un numero "n" de veces seguidas
	 *
	 * @param n El numero de veces que quieres que presione la tecla TAB
	 * @author JoseO@Hexaware.com
	 */
	public void nDOWN(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.DOWN);
			x = x + 1;
		}
	}

	/**
	 * Da clic en cargar Contratos
	 *
	 * @throws FindFailed
	 * @author JoseO@hexaware.com
	 */
	public void clickCargaContratos() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("cargaContratos"), 15).click();
		u.hardWait(5);

	}
	
	/**
	 * Da clic en opcion si navegador
	 *
	 * @throws FindFailed
	 * @author JoseO@hexaware.com
	 */
	public void clickSiNavegador()  {

		System.out.println("Run Applet");
		ImgPattern = getPatternsImg(Folder);
		try {
			
				sk.screen.click(ImgPattern.get("cap"));
				System.out.println(ImgPattern.get("cap"));
				sk.screen.type(Key.ENTER);
			

		} catch (FindFailed e) {
			logger.error("error: " + e);
			System.out.println("error: " + e);
		}
	}

	public void SaveData() {

		sk.screen.type(Key.CTRL, 's');
	}

	/**
	 * Metodo para hacer click sobre el boton cancelara de la alerta 'The
	 * application requires an older version of Java'
	 *
	 * @author misraimH@hexaware.com
	 */
	public void cancelarAlerta() {
		u.waitForLoadPage();
		try {
			sk.screen.exists(GlobalVariables.PATTERN_EBS+"EBSColombia\\Alerta.PNG");
			sk.screen.wait(GlobalVariables.PATTERN_EBS+"EBSColombia\\btnCancelarAlerta.PNG", 20).click();
			System.out.println("click en el boton 'Cancel'");
		} catch (Exception e) {
			logger.error("Error AsientosImportRun no encontrada " + e);
			System.out.println("Error AsientosImportRun no encontrada " + e);
		}

	}

	/**
	 * Emula presionar la tecla ENTER un numero "n" de veces seguidas
	 *
	 * @param n El numero de veces que quieres que presione la tecla ENTER
	 * @author JoseO@Hexaware.com
	 */
	public void nEnter(int n) {

		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.ENTER);
			x = x + 1;
		}
	}

	/**
	 * Busca un cambio en un campo de Estado que aparece en fondo verde, cuando este
	 * estado cambia se visualiza el Registro
	 *
	 * @param patternToSearch Imagen que se desea monitorizar
	 * @param timeOut         Tiempo de espera, se considera que el paso ha fallado
	 *                        si este tiempo es excedido
	 * @param botonRefrescar  Tecla a presionar para activar el boton refresh
	 * @author JesusH@hexaware.com
	 */
	public void verificarEstado(String patternToSearch, Integer timeOut, String botonRefrescar) {
		PatternUtil patternUtil = new PatternUtil(sk, u, pathImg, ListImage, ImgPattern, patterns, Folder);
		MonitoringPatternInternacional monitoringPattern = new MonitoringPatternInternacional(sk, patternUtil,
				ImgPattern, Folder, patternToSearch, botonRefrescar);
		Thread threadRunJob = new Thread(monitoringPattern);
		threadRunJob.start();
		int time = 0;
		while (threadRunJob.isAlive() && time <= timeOut) {
			try {
				Thread.sleep(2000);
				time = time + 2000;

			} catch (InterruptedException e) {
				logger.error(String.valueOf(e));
				e.printStackTrace();
			}

		}

		if (threadRunJob.isAlive()) {
			monitoringPattern.stopping();
		}

	}

	/**
	 * Metodo para hacer un copiado de texto en pantalla simple si lo que queremos
	 * copiar ya esta previamente seleccionado
	 *
	 * @return String text
	 * @author JoseO@Hexaware.com
	 */
	public String SimpleCopyText() {

		sk.screen.type("c", Key.CTRL);
		u.hardWait(5);
		@SuppressWarnings("deprecation")
		String text = (String) Env.getClipboard();
		u.hardWait(5);
		System.out.println(text);

		return text;
	}

	/**
	 * Método que selecciona la imagen a la que se le quiere dar click en el Applet
	 *
	 * @param image
	 * @throws FindFailed
	 * @author JessicaR@hexaware.com
	 */
	public void clickImage(String image) throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get(image), 15).click();

	}

	/**
	 * Emula presionar la tecla UP un numero "n" de veces seguidas
	 *
	 * @param n El número de veces que se va a presionar la tecla UP
	 * @author JessicaR@hexaware.com
	 */
	public void nUP(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.UP);
			x = x + 1;
		}
	}

	/**
	 * Emula presionar las teclas ALT + "b" para abrir una funcionalidad del Applet
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickAbrir() {
		keyAlt("b");
	}

	/**
	 * Método para ingresar el texto que queremos en el campo solicitado
	 *
	 * @param text
	 * @author JessicaR@hexaware.com
	 */
	public void enterText(String text) {
		sk.screen.type(text);
		sk.screen.type(Key.TAB);
	}
	
	/**
	 * Método para ingresar el texto en campos de el reporte XXFC-Informe de Requerimientos de Efectivo Detallado
	 *
	 * @param text
	 * @author RicardoR4@hexaware.com
	 */
	public void camposReporteEfectivo(String unidadOperativa,String pagarvencimiento,String fechaPagoDesde,String fechaPagoHasta,String fechaPago,String incluirFacturasNoValidadas,String opcionResumen,String totalPorProvedor) {
		sk.screen.type(unidadOperativa);
		sk.screen.type(Key.TAB);
		sk.screen.type(pagarvencimiento);
		sk.screen.type(Key.TAB);
		sk.screen.type(fechaPagoDesde);
		sk.screen.type(fechaPagoHasta);
		sk.screen.type(fechaPago);
		sk.screen.type(incluirFacturasNoValidadas);
		this.nTAB(2);
		sk.screen.type(opcionResumen);
		this.nTAB(5);
		sk.screen.type(totalPorProvedor);
		u.hardWait(3);
		
	}

	/**
	 * Metodo que cierra una ventana
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarNavegador() throws FindFailed {

		u.hardWait(3);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		u.hardWait(3);
		ImgPattern = getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("cerrar2"), 15).click();

	}
	
	/**
	 * Metodo que cierra una ventana cualquiera
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarFuncion() throws FindFailed {

		u.hardWait(3);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		u.hardWait(3);
		

	}
	
	/**
	 * Metodo que ingresa parametros para reporte de corresponsalias
	 * 
	 * @param servicio
	 * este parametro recibe el numero de servicio
	 * 
	 * @param estaus
	 * este parametro de estatus
	 * 
	 * @param fecha
	 * recibe la fecha de la solicitud
	 * 
	 * @throws FindFailed
	 * @author ricardor4@hexaware.com
	 */
	public void parametrosCorresponsalias(String servicio,String estatus,String fecha) throws FindFailed {

		this.nTAB(1);
		sk.screen.type(servicio);
		this.nTAB(1);
		u.hardWait(4);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.type(estatus);
		this.nTAB(1);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.type(fecha);
		u.hardWait(4);
		sk.screen.type(fecha);
		u.hardWait(4);
		this.nTAB(1);
		this.nEnter(1);
		u.hardWait(5);

	}
	
	/**
	 * Método que emula presionar la tecla F4 para salir después de realizar una
	 * busqueda específica en el Applet
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void closeText() {
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
	}

	/**
	 * Comando del teclado para seleccionar el texto o elementos
	 *
	 * @author JesusH@hexaware.com
	 */
	public void selectAll() {
		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+A para marcar todo el texto y suelta las teclas
		sk.screen.keyDown("a");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("a");
	}

	/**
	 * Método para ingresar a la siguiente ruta:Solicitudes/Ejecutar una Nueva
	 * Solicitud/Solicitud Unica
	 *
	 * @author misraimH@hexaware.com
	 */
	public void IngresarSolicitudUnica() {
		u.waitForLoadPage();
		try {
			u.highLight(By.xpath("//*[contains(text(),'Reportes')]"));
			u.click(By.xpath("//*[contains(text(),'Reportes')]"));
			u.highLight(By.xpath("//*[contains(text(),'Run')]"));
			u.click(By.xpath("//*[contains(text(),'Run')]"));
			u.hardWait(15);
			System.out
					.println("Ingresar a la siguiente ruta: Solicitudes/Ejecutar una Nueva Solicitud/Solicitud Unica");
		} catch (Exception e) {
			logger.error("Error ruta no encontrada " + e);
			System.out.println("Error ruta no encontrada " + e);
		}

	}

	/**
	 * Meotodo para manejar la ventana que aparece al intentar abrir o descargar un
	 * archivo
	 *
	 * @author JoseO@Hexaware.com
	 */
	public void runOpenOrDownload() {
		System.out.println("Open or Download");
		ImgPattern = getPatternsImg(Folder);
		try {
			if (validatePattern("DoYouWantToOpenOrSave")) {
				sk.screen.exists(ImgPattern.get("DoYouWantToOpenOrSave"));
				sk.screen.click(ImgPattern.get("DoYouWantToOpenOrSave"));
				sk.screen.click(ImgPattern.get("open2"));
				System.out.println(ImgPattern.get("open2"));
				sk.screen.type(Key.ENTER);
				u.hardWait(15);
			}

		} catch (FindFailed e) {
			logger.error("Tiempo de ejecución excedido! " + e);
			System.out.println("Tiempo de ejecución excedido! " + e);
		}
		u.hardWait(10);
		appletMaximize();
	}

	/**
	 * Meotodo para manejar la ventana que aparece al intentar abrir o descargar un
	 * archivo
	 *
	 * @author JoseO@Hexaware.com
	 */
	public void runOpenOrDownloadOrange() {

		System.out.println("Open or Download");
		ImgPattern = getPatternsImg(Folder);
		try {
			if (validatePattern("DoYouWantToOpenOrSaveOrange")) {
				sk.screen.exists(ImgPattern.get("DoYouWantToOpenOrSaveOrange"));
				sk.screen.click(ImgPattern.get("DoYouWantToOpenOrSaveOrange"));
				u.hardWait(3);
				sk.screen.click(ImgPattern.get("open2"));
				System.out.println(ImgPattern.get("open2"));
				sk.screen.type(Key.ENTER);
				u.hardWait(15);
			}

		} catch (FindFailed e) {
			logger.error("Tiempo de ejecución excedido! " + e);
			System.out.println("Tiempo de ejecución excedido! " + e);
		}
		u.hardWait(10);
		appletMaximize();
	}

	public void runOpenOrDownloadOrangeReporteCI() {

		System.out.println("Open or Download");
		ImgPattern = getPatternsImg(Folder);
		try {
			if (validatePattern("DoYouWantToOpenOrSaveOrangeReporteCI")) {
				sk.screen.exists(ImgPattern.get("DoYouWantToOpenOrSaveOrangeReporteCI"));
				sk.screen.click(ImgPattern.get("DoYouWantToOpenOrSaveOrangeReporteCI"));
				sk.screen.click(ImgPattern.get("open2"));
				System.out.println(ImgPattern.get("open2"));
				sk.screen.type(Key.ENTER);
				u.hardWait(15);
			}

		} catch (FindFailed e) {
			logger.error("Tiempo de ejecuci�n excedido! " + e);
			System.out.println("Tiempo de ejecuci�n excedido! " + e);
		}
		u.hardWait(10);
		appletMaximize();
	}

	/**
	 * Maximiza la ventana enviando la combinacion de teclas WIN + UP
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void windowMaximize() {
		sk.screen.keyDown(Key.WIN + Key.UP);
		sk.screen.keyUp(Key.WIN + Key.UP);
	}

	/**
	 * Metodo para abrir el menu de acciones del menú principal
	 *
	 * @throws FindFailed
	 * @author JessicaR@hexaware.com
	 */
	public void acciones() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		sk.screen.exists(ImgPattern.get("acciones"));
		u.hardWait(3);
		sk.screen.click(ImgPattern.get("acciones"));
		u.hardWait(5);

	}

	public String copyTxt() throws IOException, UnsupportedFlavorException {
		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		String text = String.valueOf(clipboard.getData(DataFlavor.stringFlavor));
		System.out.println("Texto copiado: " + text);
		return text;
	}

	/**
	 * Metodo que hace clic a la izquiera de una imagen
	 *
	 * @param image
	 * @param number
	 * @return
	 */
	public String copyTextBoxRight(String image, int number)
			throws FindFailed, IOException, UnsupportedFlavorException {
		String textBox = null;
		sk.screen.find(ImgPattern.get(image)).right(number).click();
		u.hardWait(1);
		textBox = copyTxt();
		System.out.println(textBox);
		return textBox;
	}
}
