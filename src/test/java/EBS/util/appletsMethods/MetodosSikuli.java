package EBS.util.appletsMethods;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

import org.apache.poi.EncryptedDocumentException;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EBS.util.patternObject.MonitoringPattern;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution;
import utils.ExcelUtil;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class MetodosSikuli {

	SikuliUtil sk;
	SeleniumUtil u;
	Pattern pathimg;
	ArrayList<String> ListImage;
	HashMap<String, Pattern> ImgPatter;
	ArrayList<Pattern> patterns;
	File Fol;
	protected static final Logger logger = LoggerFactory.getLogger(BaseExecution.class);

	public MetodosSikuli(SikuliUtil sk, SeleniumUtil u, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPatter, ArrayList<Pattern> patterns, File Fol) {
		this.u = u;
		this.sk = sk;
		this.pathimg = pathimg;
		this.ListImage = ListImage;
		this.ImgPatter = ImgPatter;
		this.patterns = patterns;
		this.Fol = Fol;
	}

	public void verificarEstado(String patternToSearch, Integer timeOut) {
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		MonitoringPattern monitoringPattern = new MonitoringPattern(sk, patternUtil, ImgPatter, Fol, patternToSearch);
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
	 * Metodo donde se obtiene la ruta de las imagenes
	 * 
	 * @param fol
	 * @return ImgPatter
	 * @author ricardo.palacios
	 */
	public HashMap<String, Pattern> getPatternsImg(File fol) {
		File[] files = Fol.listFiles();
		for (File s : files) {
			patterns.add(new Pattern(s.getAbsolutePath()).similar((float) .95));
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
	 * Maneja la pantalla de solicitud unica, verifica si la imagen existe y
	 * presiona la tecla Enter
	 * 
	 * @throws FindFailed
	 */
	public void cancelarSolicitudUnica() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cancelarsolicitud"), 15).click();
	}

	/**
	 * Maximiza la ventana enviando la combinacion de teclas WIN + UP
	 */
	public void appletMaximize() {
		sk.screen.keyDown(Key.WIN + Key.UP);
		sk.screen.keyUp(Key.WIN + Key.UP);
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

			sk.screen.exists(ImgPatter.get("doYouWantToRunThisApplication"));
			sk.screen.click(ImgPatter.get("checkBoxSecurityWarning"));
			sk.screen.type(Key.ENTER);
			u.hardWait(10);

		} catch (FindFailed e) {
			logger.error("Tiempo de ejecucion excedido " + e);
			System.out.println("Tiempo de ejecucion excedido " + e);
		}
		u.hardWait(5);
		appletMaximize();
	}

	/**
	 * Escribe el texto en una caja de texto que ya tenga el cursor activo y
	 * presiona la tecla TAB
	 *
	 * @param text
	 * @author ricardo.palacios
	 */
	public void typeAndTab(String text) {
		sk.screen.type(text);
		sk.screen.type(Key.TAB);
	}

	/**
	 * Metodo que acepta la solicitud unica en los applets
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void inicio() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("iniciook"), 15).click();

	}

	/**
	 * Metodo que da click en el navegador de applets
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void navegador() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("navegador"), 15).click();

	}

	/**
	 * Metodo que cierra una ventana de el navegador
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrar() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerrar"), 15).click();

	}

	/**
	 * Metodo que da click en drop down de estado
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickTipoEstado() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("aprobacion"), 15).click();

	}

	/**
	 * Metodo que da click en pestana estado
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickPestanaEstado() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("estado"), 15).click();

	}

	/**
	 * Metodo que escribe en fecha del proceso
	 * 
	 * @param fecha
	 * @param plaza
	 * @param metodoIncremento
	 * @param arrendamiento
	 * @param aplicarIncrementos
	 * @throws FindFailed
	 */
	public void fechaProceso(String fecha, String plaza, String metodoIncremento, String arrendamiento,
			String aplicarIncrementos) throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("fechaproceso"), 15).click();
		u.hardWait(2);
		sk.screen.type(fecha);
		sk.screen.type(Key.TAB);
		sk.screen.type(plaza);
		u.hardWait(1);
		sk.screen.type(metodoIncremento);
		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.type(arrendamiento);
		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		u.hardWait(1);
		sk.screen.type(aplicarIncrementos);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.DOWN);
		sk.screen.keyUp(Key.DOWN);
		u.hardWait(1);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);

	}

	/**
	 * Metodo que cierra las ventanas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarTodo() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("case"), 15).click();
		sk.screen.wait(ImgPatter.get("cerrarventana"), 15).click();
		sk.screen.wait(ImgPatter.get("desechar"), 15).click();

	}

	/**
	 * Metodo para ingresar fechas de inicio y fin
	 * 
	 * @param fechainicio
	 * @param fechafin
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ingresarFechaInicioFechaFin(String fechainicio, String fechafin) throws FindFailed {

		sk.screen.type(Key.TAB);
		sk.screen.type(Key.TAB);
		sk.screen.type(fechainicio);
		sk.screen.type(fechafin);

	}

	/**
	 * Metodo para ingresar campo el applet 52070300
	 * 
	 * @param campo
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ingresarCampo(String campo) throws FindFailed {

		sk.screen.type(campo);
		sk.screen.type(Key.TAB);
		System.out.println("Tab already clicked");

	}

	/**
	 * Metodo para abrir responsabilidades
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void abrirResp() throws FindFailed {

		u.hardWait(2);
		sk.screen.wait(ImgPatter.get("1"), 15).click();
		sk.screen.wait(ImgPatter.get("2"), 15).click();
		u.hardWait(2);

	}

	/**
	 * Metodo para abrir solicitudes internas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void abrirSolicitudesInternas() throws FindFailed {

		u.hardWait(2);
		sk.screen.wait(ImgPatter.get("solicitudesinternas"), 15).click();
		u.hardWait(1);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(1);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);

	}

	/**
	 * Metodo para escribir solicitud a buscar
	 * 
	 * @param ns Numero de solicitud
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void EscribirNumeroSolicitud(String ns) throws FindFailed {

		u.hardWait(2);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(ns);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);

		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);

	}

	/**
	 * Metodo para escribir solicitud a buscar
	 * 
	 * @param ns Numero de solicitud
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void EscribirNumeroSolicitud2(String ns) throws FindFailed {

		u.hardWait(2);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(ns);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(1);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(1);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);

		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);

	}

	/**
	 * Metodo para buscar responsabilidad activa
	 * 
	 * @param resp Resp a buscar
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void buscarResp(String resp) throws FindFailed {

		sk.screen.keyDown(Key.F11);
		sk.screen.keyUp(Key.F11);
		sk.screen.type(resp);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown(Key.F11);
		sk.screen.keyUp(Key.F11);
		sk.screen.keyUp(Key.CTRL);

	}

	/**
	 * Metodo para ingresar campo el applet 52070300
	 * 
	 * @param navegador
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void buscarResponsabilidad(String navegador) throws FindFailed {

		u.hardWait(3);
		sk.screen.wait(ImgPatter.get("buscarmx"), 15).click();
		sk.screen.type(navegador);
		sk.screen.wait(ImgPatter.get("buscarnavegador"), 15).click();

	}

	/**
	 * Metodo que ingresa los parametros de compania y periodo
	 * 
	 * @param compania
	 * @param periodo
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ingresarParametros(String compania, String periodo) throws FindFailed {
		sk.screen.mouseMove(14);
		sk.screen.click();
		sk.screen.type(compania);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("puntos"), 15).click();
		sk.screen.type(periodo);

	}

	/**
	 * Metodo para aceptar archivo
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void aceptarArchivo1() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("aceptararchivo"), 15).click();

	}

	/**
	 * Metodo para aceptar archivo
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void aceptarArchivo() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.type(Key.TAB);
		sk.screen.wait(ImgPatter.get("acepuno"), 15).click();

	}

	/**
	 * Metodo para dar click el boton ejecutar
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ejecutar() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("ejecutar"), 15).click();

	}

	/**
	 * Metodo para no realizar otra solicitud
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void otraSolicitud() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("otrasolicitud"), 15).click();

	}

	/**
	 * Metodo que selecciona boton ver solicitudes
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void vers() throws FindFailed {
		u.hardWait(3);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("ver"), 15).click();
		u.hardWait(1);
		sk.screen.wait(ImgPatter.get("solicitudes"), 15).click();

	}

	/**
	 * Metodo que selecciona boton encontrar
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void encontrar() throws FindFailed {

		u.hardWait(3);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("encontrar"), 15).click();

	}

	/**
	 * Metodo que selecciona boton encontrar en boton de tipo distinto
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void encontrarBotonLargo() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("encontrarlargo"), 15).click();

	}

	/**
	 * Metodo que selecciona boton ejecutar nueva solicitud
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ejecutarNuevaSolicitud() throws FindFailed {

		u.hardWait(3);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("nuevasolicitud"), 15).click();

	}

	/**
	 * Metodo para visualizar output
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void visualizarOutput() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("visualizaroutput"), 15).click();

	}

	/**
	 * Metodo para seleccionar ventana Applet
	 * 
	 * @throws FindFailed
	 * @author genaro.arredondo
	 */
	public void clickIconoApplet() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("ebsOracle"), 15).click();

	}

	/**
	 * Metodo para extraer un dato de un archivo excel
	 * 
	 * @param nombreArchivo
	 * @param numeroDeHoja
	 * @param numeroDeRenglon
	 * @param numeroDeColumna
	 * @return contenidoCelda
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 * @author ricardo.palacios
	 */
	public String extraerDatoExcel(String nombreArchivo, int numeroDeHoja, int numeroDeColumna, int numeroDeRenglon)
			throws EncryptedDocumentException, IOException {

		String currentUser = System.getProperty("user.name"); // Obtener el user actual del equipo
		String rutaDelArchivo = "C:\\Users\\" + currentUser + "\\Downloads\\" + nombreArchivo;
		System.out.println("la ruta del archivo es : " + rutaDelArchivo);
		ExcelUtil hoja = new ExcelUtil(rutaDelArchivo);
		hoja.open();
		String contenidoCelda = hoja.getCell(numeroDeHoja, numeroDeRenglon, numeroDeColumna);
		return contenidoCelda;

	}

	/**
	 * Metodo para visualizar output
	 * 
	 * @throws FindFailed
	 * @author edwin.ramirez@neoris.com
	 */
	public void visualizarRegistro() throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.mouseUp();
		sk.screen.wait(ImgPatter.get("visualizarregistro"), 15).click();
	}

	/**
	 * Metodo para visualizar registro
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void visualizaRegistro() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("registro"), 15).click();

	}

	/**
	 * Metodo que cierra boton con foco
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarconfoco() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("acepuno"), 15).click();

	}

	/**
	 * Metodo que ejecuta boton ejecutar
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ejecutarBoton() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("ejecutar"), 15).click();

	}

	/**
	 * Metodo que ejecuta boton no
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ejecutarBotonNo() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("no"), 15).click();

	}

	/**
	 * Metodo que ejecuta boton anexos
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ejecutarBotonAnexos() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("anexo"), 15).click();

	}

	/**
	 * Metodo que cierra una ventana
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrar1() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerrar"), 15).click();

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
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();

	}

	/**
	 * Metodo que cierra ventanas de un applet
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarTodoN() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerraralterno"), 15).click();
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();
		sk.screen.wait(ImgPatter.get("cierra"), 15).click();
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();

	}

	/**
	 * Metodo que cierra solicitud entera
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarSolicitudEntera() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		u.hardWait(3);
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();
		u.hardWait(3);
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();
		u.hardWait(3);
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();

	}

	/**
	 * Metodo que cierra todas las ventanas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarTodo1() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.click(Key.ESC);
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();
		sk.screen.wait(ImgPatter.get("cierra"), 15).click();
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();

	}

	/**
	 * Metodo que cierra todas las ventanas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void c() throws FindFailed {

		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		u.hardWait(3);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();

	}

	/**
	 * Emula presionar la tecla 'Alt' + alguna tecla que necesitemos (solo una)
	 * 
	 * @param x
	 * @author JesusH@hexaware.com
	 */
	public void keyAlt(String x) {
		sk.screen.keyDown(Key.ALT);
		sk.screen.type(x);
		sk.screen.keyUp(Key.ALT);
	}

	/**
	 * Emula presionar la tecla 'Ctrl' + alguna tecla que necesitemos (solo una)
	 * 
	 * @param x
	 * @author GenaroA@hexaware.com
	 */
	public void keyCtrl(String x) {
		sk.screen.keyDown(Key.CTRL);
		sk.screen.type(x);
		sk.screen.keyUp(Key.CTRL);
	}

	/**
	 * Ventana de confirmacion para salir de Oracle Applications
	 * 
	 * @param x
	 * @throws FindFailed
	 */
	public void ventanaConfirmarSalir(String x) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.exists(ImgPatter.get("deseaSalir"));
		keyAlt(x);
		u.hardWait(5);
	}

	/**
	 * Metodo que cierra todas las ventanas
	 * 
	 * @param x Tecla que se presionara
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarResp(String x) throws FindFailed {

		u.hardWait(3);
		sk.screen.keyDown(Key.ALT);
		sk.screen.keyDown(Key.F4);
		sk.screen.keyUp(Key.F4);
		sk.screen.keyUp(Key.ALT);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.exists(ImgPatter.get("deseaSalir"));
		keyAlt(x);

	}

	/**
	 * Cierra la ventana enviando la combinacion de teclas 'Alt' + 'F4'mas ventana
	 * de desea cerrar
	 * 
	 * @throws FindFailed
	 * @author JesusH@hexaware.com
	 */
	public void closeWindow1() throws FindFailed {

		u.hardWait(6);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerrar2")).click();
		u.hardWait(6);
		sk.screen.wait(ImgPatter.get("cerrar2")).click();
		u.hardWait(6);
		sk.screen.wait(ImgPatter.get("cerrar2")).click();
		u.hardWait(3);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);

	}

	/**
	 * Metodo que ingresa texto en una caja de texto
	 * 
	 * @param campoSvc
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void ingresarCampoSvc(String campoSvc) throws FindFailed {

		sk.screen.type(campoSvc);
		sk.screen.type(Key.TAB);

	}

	/**
	 * Metodo que escribe en el campo archivo
	 * 
	 * @param archivo
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void buscarArchivo(String archivo) throws FindFailed {

		sk.screen.type(archivo);

	}

	/**
	 * Metodo que escribe en el campo archivo y da un tab
	 * 
	 * @param archivo
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void buscarArchivoTab(String archivo) throws FindFailed {

		sk.screen.type(archivo);
		sk.screen.type(Key.TAB);

	}

	/**
	 * Metodo que escribe y acepta campos plaza fechauno y fechados
	 * 
	 * @param plaza
	 * @param fechauno
	 * @param fechados
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void CamposPlazaFechas(String plaza, String fechauno, String fechados) throws FindFailed {

		sk.screen.type(plaza);
		sk.screen.type(fechauno);
		sk.screen.type(Key.TAB);
		sk.screen.type(fechados);

	}

	/**
	 * Metodo que escribe y acepta campos plaza fechauno y fechados sin plaza
	 * 
	 * @param fechauno
	 * @param fechados
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void CamposPlazaFechassinplaza(String fechauno, String fechados) throws FindFailed {

		sk.screen.type(Key.TAB);
		sk.screen.type(fechauno);
		sk.screen.type(Key.TAB);
		sk.screen.type(fechados);

	}

	/**
	 * Metodo que cierra todas las ventanas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarTodo2() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerrar"), 15).click();
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();
		sk.screen.wait(ImgPatter.get("cierra"), 15).click();
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();

	}

	/**
	 * Metodo que cierra todas las ventanas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarconteclas() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		u.hardWait(8);
		sk.screen.keyDown(Key.ESC);
		sk.screen.wait(ImgPatter.get("cerrar2"), 15).click();
		sk.screen.wait(ImgPatter.get("cierra"), 15).click();
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();

	}

	/**
	 * Metodo para aceptar la advertencia
	 * 
	 * @throws FindFailed
	 */
	public void aceptarPrecaucion() throws FindFailed {
		sk.screen.wait(ImgPatter.get("aceptarWarning"), 15).click();
	}

	/**
	 * Metodo para dar click en importacion de solicitud interna
	 * 
	 * @throws FindFailed
	 */
	public void ClickImportacionSolicitudInterna() throws FindFailed {
		sk.screen.wait(ImgPatter.get("impsol"), 15).click();
	}

	/**
	 * Metodo para dar click en conciliacion tesoreria
	 * 
	 * @throws FindFailed
	 */
	public void ClickConciliacionTesoreria() throws FindFailed {
		sk.screen.wait(ImgPatter.get("conciliaciontesoreria"), 15).click();
	}

	/**
	 * Metodo que da click en el navegador mostrando el Registro
	 * 
	 * @author edwin.ramirez
	 * @throws FindFailed
	 */
	public void clickRegistroNavegador() throws FindFailed {
		sk.screen.wait(ImgPatter.get("registronavegador"), 15).click();
	}

	/**
	 * Metodo que da click en el navegador en base a una imagen, selecciona toda el
	 * texto y busca un String dentro del texto.
	 * 
	 * @param nameImgToClick
	 * @param strToFind
	 * @return String con el texto
	 * @throws FindFailed
	 * @throws IOException
	 * @throws UnsupportedFlavorException
	 * @throws HeadlessException
	 * @author edwin.ramirez
	 */
	public String getTextDelNavegador(String nameImgToClick, String strToFind)
			throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
		sk.screen.wait(ImgPatter.get(nameImgToClick), 15).click(); // Encuentra la imagen a dar click en el navegador

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
		String findResult = ""; // Lo que regresaermos

		int i = textFound.indexOf(find);
		if (i > 0) {
			findResult = textFound.substring(i, i + find.length());
			System.out.println(textFound.substring(i, i + find.length()));
		} else {
			findResult = "string not found";
			System.out.println("string not found");
		}

		sk.screen.mouseDown(Button.LEFT);
		sk.screen.mouseUp(Button.LEFT);
		return findResult; // Si lo encuentra lo regresa

	}

	/**
	 * Metodo que da click en el boton Ejecutar Nueva Solicitud
	 * 
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void clickNuevaSolicitud() throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("nuevasolicitud"), 15).click();
	}

	/**
	 * Metodo que da click en el boton Refrescar Datos
	 * 
	 * @throws FindFailed
	 * @author Genaro.Arredondo
	 */
	public void clickRefrescarDatos() throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("ocRefrescarDatos"), 15).click();
	}

	/**
	 * Metodo que llena el campo Nombre del Archivo
	 * 
	 * @param nombreArchivo
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void ingresarNombreArchivo(String nombreArchivo) throws FindFailed {
		sk.screen.type(nombreArchivo);
		sk.screen.wait(ImgPatter.get("aceptararchivo"), 15).click();
	}

	/**
	 * Metodo que busca dentro del filtro de Nombre
	 * 
	 * @param nombreSolicitud
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void buscarNombreSolicitud(String nombreSolicitud) throws FindFailed {
		sk.screen.wait(ImgPatter.get("informesbuscar"), 15).click();
		sk.screen.type(Key.BACKSPACE);
		sk.screen.type(Key.BACKSPACE);
		sk.screen.type(Key.BACKSPACE);
		sk.screen.type(Key.BACKSPACE);
		sk.screen.type(Key.BACKSPACE);
		sk.screen.type(nombreSolicitud);
		sk.screen.type(Key.ENTER);
	}

	/**
	 * Metodo que da click en el campo Nombre de Nueva Solicitud
	 *
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void clickNombre() throws FindFailed {
		sk.screen.wait(ImgPatter.get("nombreclick"), 15).click();
	}

	/**
	 * Metodo que da click en los 3 campos dentro del campo Nombre
	 * 
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void clickPuntosNombre() throws FindFailed {
		sk.screen.wait(ImgPatter.get("puntosbusqueda"), 15).click();
	}

	/**
	 * Metodo que da click en el boton de compra
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickCompra() throws FindFailed {
		sk.screen.wait(ImgPatter.get("compra02"), 15).click();
	}

	/**
	 * Metodo que llena los campos de Fecha Inicio y Fecha Fin por parametro
	 * 
	 * @param fechainicio String con la fecha de inicio DD/MMM/AAAA
	 * @param fechafin    String con la fecha de fin DD/MMM/AAAA
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void ingresarFechas(String fechainicio, String fechafin) throws FindFailed {

		sk.screen.type(Key.TAB);
		sk.screen.type(Key.TAB);
		sk.screen.type(Key.TAB);
		sk.screen.type(fechainicio);
		sk.screen.type(Key.TAB);
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();
	}

	/**
	 * Metodo que selecciona Juego Valores y llena el campo Nombre en Encontrar
	 * Juego de Valores
	 *
	 * @param nombre
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void llenarJuegoValores(String nombre) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("juegovalores2"), 15).click();
		sk.screen.wait(ImgPatter.get("nombrevalores"), 15).click();
		sk.screen.type(nombre);
		u.hardWait(2);
		sk.screen.wait(ImgPatter.get("encontrar2"), 15).click();

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
	 * Emula presionar la tecla SHIFT+TAB un numero "n" de veces seguidas (va hacia
	 * atrás)
	 *
	 * @param n El numero de veces que quieres que presione SHIFT+TAB
	 * @author JesusM3@hexaware.com
	 */
	public void nShiftTAB(int n) {
		if (n <= 0) {
			return;
		}
		for (int i = 0; i < n; i++) {
			// Usa el modificador SHIFT para ir hacia atrás con TAB
			sk.screen.type(Key.TAB, KeyModifier.SHIFT);
		}
	}

	// java
	/**
	 * Emula presionar la tecla de flecha hacia abajo un numero "n" de veces
	 * seguidas
	 *
	 * @param n El numero de veces que quieres que presione la tecla Abajo
	 * @author JesusM3@hexaware.com
	 */
	public void nArrowDown(int n) {
		if (n <= 0) {
			return;
		}
		for (int i = 0; i < n; i++) {
			sk.screen.type(Key.DOWN);
		}
	}

	/**
	 * Metodo particular usado en MTCFT001_ValidarConfiguracionDeCuentasEnGL del
	 * paquete devolucionesOxxoPay Consulta los datos de tres Valores
	 * 
	 * @param valor1
	 * @param valor2
	 * @param valor3
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void introducirValores(String valor1, String valor2, String valor3) throws FindFailed {
		sk.screen.wait(ImgPatter.get("valorazul"), 15).click();

		sk.screen.type(Key.F11);
		u.hardWait(2);
		sk.screen.type(valor1);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.CTRL + Key.F11);
		u.hardWait(2);

		sk.screen.type(Key.F11);
		u.hardWait(2);
		sk.screen.type(valor2);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.CTRL + Key.F11);
		u.hardWait(2);

		sk.screen.type(Key.F11);
		u.hardWait(2);
		sk.screen.type(valor3);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.CTRL + Key.F11);
		u.hardWait(2);
	}

	/**
	 * Metodo que cierra el Applet en 2 pasos Da Alt+F4 y luego dentro del applet
	 * confirma
	 *
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void cerrarApplet() throws FindFailed {
		sk.screen.keyDown(Key.ALT + Key.F4);
		sk.screen.keyUp(Key.ALT + Key.F4);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	}

	/**
	 * Metodo que inserta un Tipo y realiza la busqueda
	 * 
	 * @param tipo
	 * @param codigo
	 * @throws FindFailed
	 */
	public void tipoConsultaGL(String tipo, String codigo) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("tipo"), 15).click();
		sk.screen.type(tipo);
		sk.screen.keyUp(Key.CTRL + Key.F11);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(codigo);
		sk.screen.keyUp(Key.CTRL + Key.F11);
		sk.screen.keyDown(Key.CTRL + Key.F11);
	}

	/**
	 * Metodo que ingresa el numero Da varios TAB para moverse en la celda
	 * 
	 * @param ns Numero de solicitud
	 * @throws FindFailed
	 * @author ricardo.palacios@neoris.com
	 */
	public void EscribirNumeroSolicitudConEnter(String ns) throws FindFailed {

		u.hardWait(2);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(ns);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);

	}

	/**
	 * Metodo que ingresa el nï¿½mero Da varios TAB para moverse en la celda
	 * 
	 * @author JessicaR@hexaware.com
	 * @param ns
	 * @throws FindFailed
	 */
	public void EscribirNumeroSolicitudConEnterOtro(String ns) throws FindFailed {

		u.hardWait(2);
		sk.screen.keyDown(Key.DELETE);
		sk.screen.keyUp(Key.DELETE);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(ns);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);

	}

	public void introducirValor(String valor) throws FindFailed {
		sk.screen.wait(ImgPatter.get("valorazul00"), 15).click();

		sk.screen.type(Key.F11);
		u.hardWait(2);
		sk.screen.type(valor);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.CTRL + Key.F11);
		u.hardWait(2);
	}

	/**
	 * Metodo para comparar la informacion que se muestra en la celda
	 * 
	 * @param formaPago   Forma de pago
	 * @param fechaInicio Fecha de inicio
	 * @param fechaFin    Fecha fin
	 * @author ricardo.palacios
	 */
	public void parametros(String formaPago, String fechaInicio, String fechaFin) {
		sk.screen.type(formaPago);
		sk.screen.type(Key.TAB);
		sk.screen.type(fechaInicio);

		sk.screen.type(Key.TAB);
		sk.screen.type(fechaFin);
		sk.screen.type(Key.ENTER);
	}

	/**
	 * Metodo para escribir fechas en celdas
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @author ricardo.palacios
	 */
	public void parametrosFactura(String fechaInicio, String fechaFin) {

		sk.screen.type(Key.TAB);
		sk.screen.type(fechaInicio);
		sk.screen.type(fechaFin);
		sk.screen.type(Key.ENTER);
	}

	/**
	 * Metodo para ingresar un nombre que contiene un acento. Recibe el String en
	 * dos y en medio escribe "o" con acento
	 * 
	 * @author edwin.ramirez
	 * @param mitad1
	 * @param mitad2
	 * @throws FindFailed
	 */
	public void ingresarNombreAcentos(String mitad1, String mitad2) throws FindFailed {
		sk.screen.type(mitad1);
		sk.screen.keyDown(Key.ALT + Key.NUM1 + Key.NUM6 + Key.NUM2);
		sk.screen.keyUp(Key.ALT + Key.NUM1 + Key.NUM6 + Key.NUM2);
		sk.screen.type(mitad2);
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
			System.out.println("El string No fue correcto encontrado fue: " + findResult);
		}
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("a");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("a");
		return findResult; // Si lo encuentra lo regresa

	}

	/**
	 * Metodo para buscar responsabilidad activa
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void validarCambio() throws FindFailed {

		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);

	}

	/**
	 * Metodo que copia el contenido del clipboard y le concatena una variable
	 * 
	 * @param variable
	 * @return
	 * @throws HeadlessException
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 * @author ricardo.palacios
	 */
	public String textoCopiadoConvariable(String variable)
			throws HeadlessException, UnsupportedFlavorException, IOException {
		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);
		// El texto que copiamos
		String textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)
				+ variable;
		System.out.println("Texto copiado " + textFound); // Imprime el texto que copiamos
		logger.info("Texto copiado " + textFound);
		return textFound;
	}

	// java
	/**
	 * Metodo que copia el contenido del clipboard y lo devuelve (reemplazando la
	 * variable).
	 *
	 * @param variable valor fallback si el portapapeles está vacío
	 * @return el texto copiado (o el fallback si el portapapeles está vacío)
	 * @throws HeadlessException
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 * @author JesusM3@hexaware.com
	 */
	public String copiarTextoEnVariable(String variable)
			throws HeadlessException, UnsupportedFlavorException, IOException {

		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		final int maxAttempts = 10;
		final long delayMs = 100L;
		String textFound = null;

		for (int attempt = 0; attempt < maxAttempts; attempt++) {
			if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
				Object data = clipboard.getData(DataFlavor.stringFlavor);
				if (data instanceof String) {
					textFound = ((String) data).trim();
				} else if (data != null) {
					textFound = data.toString().trim();
				} else {
					textFound = "";
				}
				break;
			}
			try {
				Thread.sleep(delayMs);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new IOException("Interrumpido esperando el portapapeles", e);
			}
		}

		if (textFound == null) {
			textFound = "";
		}

		// Devolver el texto del portapapeles; si está vacío usar la variable pasada
		// como fallback.
		String resultado = !textFound.isEmpty() ? textFound : (variable != null ? variable : "");
		System.out.println("Texto copiado " + resultado);
		logger.info("Texto copiado " + resultado);
		return resultado;
	}

	/**
	 * Metodo que ingresa un Nombre en la ventana Tabulador de Cobro de Servicios, y
	 * ejecuta una busqueda con ese nombre.
	 * 
	 * @param nombre String con el nombre a buscar
	 * @throws FindFailed
	 */
	public void ingresarNombreTabuladorCobroServicios(String nombre) throws FindFailed {
		u.hardWait(10);
		sk.screen.keyDown(Key.F11);
		sk.screen.keyUp(Key.F11);
		sk.screen.type(nombre);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.CTRL + Key.F11);
	}

	/**
	 * Metodo que ingresa un Tipo Item en la ventana Tabulador de Cobro de
	 * Servicios, y ejecuta una busqueda con ese Item.
	 * 
	 * @param tipoItem String con el Item a buscar
	 * @throws FindFailed
	 */
	public void ingresarTipoItem(String tipoItem) throws FindFailed {
		u.hardWait(10);
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("tiposervicio"), 15).click();
		sk.screen.wait(ImgPatter.get("tiposervicio"), 15).click();
		sk.screen.keyDown(Key.F11);
		sk.screen.keyUp(Key.F11);
		sk.screen.type(tipoItem);
		sk.screen.keyDown(Key.CTRL + Key.F11);
		sk.screen.keyUp(Key.CTRL + Key.F11);
	}

	public void cerrarDobleAltF4() throws FindFailed {
		u.hardWait(3);
		sk.screen.keyDown(Key.ALT + Key.F4);
		sk.screen.keyUp(Key.ALT + Key.F4);

		sk.screen.keyDown(Key.ALT + Key.F4);
		sk.screen.keyUp(Key.ALT + Key.F4);
		sk.screen.wait(ImgPatter.get("aceptarcerrar"), 15).click();
	}

	public void enter() {
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	}

	/**
	 * Da click en el estado Normal de un solicitud
	 * 
	 * @throws FindFailed
	 */
	public void estadoNormal() throws FindFailed {
		sk.screen.wait(ImgPatter.get("estadonormal"), 15).click();
	}

	/**
	 * Da click en la lampara de busqueda de los applets
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void lamparaBusqueda() throws FindFailed {
		sk.screen.wait(ImgPatter.get("lamparabusqueda"), 15).click();
		u.hardWait(2);
	}

	/**
	 * Esperar Pantalla y Genera Factura
	 * 
	 * @param U_Operativa, proveedor, FactNumber,Importe, Terminos
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public boolean pantallaGenerarFactura() throws FindFailed {
		try {
			sk.screen.wait(ImgPatter.get("GenerarFactura"), 150);

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * Esperar Pantalla y Genera Factura
	 * 
	 * @param U_Operativa, proveedor, FactNumber,Importe, Terminos
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public boolean pantallaOrdenDeCompra() throws FindFailed {

		sk.screen.wait(ImgPatter.get("pantallaOC"), 150);
		return true;

	}

	/**
	 * Esperar Pantalla y Genera Factura
	 * 
	 * @param U_Operativa, proveedor, FactNumber,Importe, Terminos
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public boolean pantallaCancelarSolicitud() throws FindFailed {
		try {
			sk.screen.wait(ImgPatter.get("PantallaEjecutarSolicitud"), 150);
			sk.screen.wait(ImgPatter.get("PantallaEjecutarSolicitud"), 2).click();
			u.hardWait(6);
			sk.screen.type(Key.ALT + "x" + "s");
			u.hardWait(1);

		} catch (Exception e) {
			return false;
		}

		return true;

	}

	/**
	 * Esperar Pantalla y Genera Factura
	 * 
	 * @param U_Operativa, proveedor, FactNumber,Importe, Terminos
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public boolean pantallaEncontrarSolicitudes() throws FindFailed {
		try {
			sk.screen.wait(ImgPatter.get("PantallaEncontrarSolicitudes"), 150);
			u.hardWait(1);

		} catch (Exception e) {
			return false;
		}

		return true;

	}

	/**
	 * Esperar Pantalla y Genera Factura
	 * 
	 * @param U_Operativa, proveedor, FactNumber,Importe, Terminos
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public boolean clickEncontrarSolicitudes() throws FindFailed {
		try {
			sk.screen.wait(ImgPatter.get("btnEncontrarSolicitudes"), 150).click();
			u.hardWait(1);

		} catch (Exception e) {
			return false;
		}

		return true;

	}

	/**
	 * Metodo para hacer TAB N veces
	 * 
	 * @param num
	 * @throws
	 * @author Gilberto Aguayo
	 */
	public void typeNTab(int num) {

		for (int i = 0; i < num; i++) {
			try {
				sk.screen.type(Key.TAB);
				u.hardWait(1);

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Buscando campo formato de fecha.");
		}
	}

	/**
	 * Da click en caja de texto de CR los applets
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void clickCR() throws FindFailed {
		sk.screen.wait(ImgPatter.get("cr"), 15).click();
		u.hardWait(2);
	}

	/**
	 * Metodo que llena los campos de la ventana Parametros con 7 campos.
	 * 
	 * @author edwin.ramirez
	 * @param compania
	 * @param plaza
	 * @param origen
	 * @param cuentaCorriente
	 * @param tipoSalida
	 * @param periodo
	 * @throws FindFailed
	 */
	public void parametros7campos(String compania, String plaza, String origen, String cuentaCorriente,
			String tipoSalida, String periodo) throws FindFailed {
		sk.screen.wait(ImgPatter.get("compania"), 15).click();
		sk.screen.type(compania);

		sk.screen.keyDown(Key.BACKSPACE);
		sk.screen.keyUp(Key.BACKSPACE);
		sk.screen.type(plaza);

		sk.screen.type(origen);

		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(cuentaCorriente);

		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(tipoSalida);

		sk.screen.type(periodo);

		sk.screen.wait(ImgPatter.get("aceptararchivo"), 15).click();
	}

	/**
	 * Metodo que escribe en el campo Entidad de la ventana Parametros, da click en
	 * aceptar.
	 * 
	 * @param entidad El nombre de la entidad a escribir.
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void parametrosEntidad(String entidad) throws FindFailed {
		u.hardWait(3);
		sk.screen.type(entidad);
		sk.screen.wait(ImgPatter.get("aceptararchivo"), 15).click();
	}

	/**
	 * MÃ©todo que selecciona el texto de una celda
	 * 
	 * 
	 * @return textFound, si lo encuentra lo regresa
	 * @throws FindFailed
	 * @throws HeadlessException
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 * @author ricardo.palacios@neoris.com
	 */
	public String controlCopiar() throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {

		String textFound = "";
		// Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).equals("");
		sk.screen.keyDown(Key.CTRL); // Presiona CTRL+C para copiar todo el texto y suelta las teclas
		sk.screen.keyDown("c");
		sk.screen.keyUp(Key.CTRL);
		sk.screen.keyUp("c");
		// El texto que copiamos
		textFound = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		System.out.println(textFound); // Imprime el texto que copiamos
		return textFound; // Si lo encuentra lo regresa

	}

	public void insertarText(String usuario, String contrasena) throws FindFailed {

		sk.screen.type(usuario);
		sk.screen.keyDown(Key.TAB);
		sk.screen.keyUp(Key.TAB);
		sk.screen.type(contrasena);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	}

	/**
	 * Metodo que cierra las ventanas
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void cerrarV() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("cerrarventana"), 15).click();

	}

	/**
	 * Metodo cuya funcion es cerra el applet usando la combinacion de Alt+F4 y
	 * despues Enter para aceptar confirmacion
	 * 
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void altF4Aceptar() throws FindFailed {
		sk.screen.keyDown(Key.ALT + Key.F4);
		sk.screen.keyUp(Key.ALT + Key.F4);

		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
	}

	/**
	 * Metodo para ingresar texto en un campo sin dar Enter o TAB
	 * 
	 * @param campo
	 * @throws FindFailed
	 * @author edwin.ramirez
	 */
	public void soloIngresarCampo(String campo) throws FindFailed {
		sk.screen.type(campo);
	}

	/**
	 * Metodo cuya funcion es ejecutar TAB de teclado la cantidad de veces dadas por
	 * parametro
	 * 
	 * @param num Cantidad de veces a ejecutar un TAB
	 * @author edwin.ramirez
	 */
	public void presionarTabNVeces(int num) {
		int n = num;
		while (n > 0) {
			sk.screen.type(Key.TAB);
			u.hardWait(2);
			n--;
			System.out.println("Quedan: " + n + " por hacer.");
		}
	}

	/**
	 * Metodo cuya funcion es ejecutar flecha abajo de teclado la cantidad de veces
	 * dadas por parametro
	 * 
	 * @param num Cantidad de veces a ejecutar un flecha abajo
	 * @author edwin.ramirez
	 */
	public void presionarKeyDownNVeces(int num) {
		int n = num;
		while (n > 0) {
			sk.screen.type(Key.DOWN);
			u.hardWait(2);
			n--;
			System.out.println("Quedan: " + n + " por hacer.");
		}
	}

	/**
	 * Metodo que ejecuta un Alt+F4
	 */
	public void altF4() {
		sk.screen.keyDown(Key.ALT + Key.F4);
		sk.screen.keyUp(Key.ALT + Key.F4);
	}

	/**
	 * Metodo que ejecuta un Alt+F4
	 */
	public void f5() {
		sk.screen.keyDown(Key.F5);
		sk.screen.keyUp(Key.F5);
	}

	public ArrayList<String> getTextContains(String strToFind)
			throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
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
		// System.out.println(textFound); // Imprime el texto que copiamos
		String find = strToFind; // El String a buscar dentro de lo que copiamos
		// String findResult = ""; // Lo que regresaermos
		String[] parts = textFound.split(" ");
		ArrayList<String> al = new ArrayList<String>();

		boolean exist = false;
		int contador = 0;
		for (int i = 0; i < parts.length - 1; i++) {
			if (parts[i].contains(find)) {
				al.add(parts[i]);
				System.out.println("String encontrado: " + parts[i]);
				exist = true;
				contador++;
			}
		}

		sk.screen.mouseDown(Button.LEFT);
		sk.screen.mouseUp(Button.LEFT);
		return al; // Si lo encuentra lo regresa

	}

	public String getText(String strToFind)
			throws FindFailed, HeadlessException, UnsupportedFlavorException, IOException {
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
		// System.out.println(textFound); // Imprime el texto que copiamos
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

		sk.screen.mouseDown(Button.LEFT);
		sk.screen.mouseUp(Button.LEFT);
		return findResult; // Si lo encuentra lo regresa

	}

	/**
	 * Metodo para aceptar el popup Authentication
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public void popUpAuthenticacion() throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("yesButton"), 25).click();

		// sk.screen.wait(ImgPatter.get("cerrarventana"), 15).click();
		// sk.screen.wait(ImgPatter.get("desechar"), 15).click();

	}

	/**
	 * Metodo para aceptar el popup app java
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public void accepApp() throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("checkboxStart2"), 120).click();// check11
		sk.screen.type(Key.ENTER);
	}

	/**
	 * Metodo para hacer click en Cluster configurados
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */

	public void navigateclusterConfigurados() throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("ClusterValue"), 50).click();
		sk.screen.wait(ImgPatter.get("ClusterFind"), 15).click();

	}

	/**
	 * Metodo dar clic en boton New
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public void clickNewButton() throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("valueCluster"), 15).click();
		sk.screen.wait(ImgPatter.get("addNew"), 15).click();
		u.hardWait(5);

	}

	/**
	 * Metodo metodo para llenar linea de factura
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */

	public void facturaTabLineas(String lineaContable, String Monto, String TAXIVA) throws FindFailed {

		sk.screen.wait(ImgPatter.get("facLineasTab"), 50).click();
		u.hardWait(5);
		sk.screen.wait(ImgPatter.get("tabLineScreen"), 50);
		sk.screen.type(Key.TAB);
		u.hardWait(3);
		sk.screen.type(Key.TAB);
		u.hardWait(3);
		sk.screen.type(Monto);
		u.hardWait(2);
		for (int i = 0; i < 13; i++) {
			try {
				sk.screen.type(Key.TAB);
				u.hardWait(1);

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Buscando campo formato de fecha.");
		}
		sk.screen.type(lineaContable);
		sk.screen.type(Key.TAB);
		u.hardWait(1);

		for (int i = 0; i < 27; i++) {
			try {
				sk.screen.type(Key.TAB);
				u.hardWait(1);

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Buscando campo formato de fecha.");
		}

		sk.screen.type(TAXIVA);
		u.hardWait(1);

	}

	/**
	 * Metodo para buscar un cluster por nombre
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public void buscarCluster(String ClName) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("ClusterValue"), 50).click();
		u.hardWait(2);
		sk.screen.type(ClName);
		sk.screen.wait(ImgPatter.get("ClusterFind"), 15).click();
		u.hardWait(5);

	}

	/**
	 * Metodo para deshabilitar checkbox
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public void deshabilitarClusterCheckBox() throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("enableDisable"), 15).click();
		u.hardWait(3);
		sk.screen.wait(ImgPatter.get("SaveClick"), 15).click();
		u.hardWait(1);

	}

	/**
	 * Metodo para deseleccionar checkbox de fecha fin
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public void deshabilitarClusterFechaFin(String fechaFin) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("FechaFinBox"), 15).click();
		u.hardWait(1);
		sk.screen.type(fechaFin);
		sk.screen.wait(ImgPatter.get("SaveClick"), 15).click();
		u.hardWait(1);
	}

	/**
	 * Metodo para dar click en boton Save
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */

	public void btnSaveClick() throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		u.hardWait(2);
		sk.screen.wait(ImgPatter.get("SaveClick"), 15).click();
		u.hardWait(1);
	}

	/**
	 * Metodo modificar descripcion
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public void modificarDescripcionCluster(String desCL, String Newtext) throws FindFailed {

		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get(desCL), 15).click();
		u.hardWait(1);
		sk.screen.type(Key.TAB);
		u.hardWait(1);
		sk.screen.type(Key.SPACE);
		sk.screen.type(Key.BACKSPACE);
		sk.screen.type(desCL + " " + Newtext);
		u.hardWait(1);
		sk.screen.wait(ImgPatter.get("SaveClick"), 15).click();
		u.hardWait(1);
	}

	/**
	 * Metodo para cerrar ventana
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */

	public void cerrarVentana() throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("closeW"), 15).click();
		u.hardWait(3);
		sk.screen.type(Key.ENTER);

	}

	/**
	 * Metodo para validar cluster encontrado
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */

	public boolean ValidarCluster(String imagePath) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.type(Key.ENTER);
		sk.screen.wait(ImgPatter.get("ClusterFind"), 15).click();
		u.hardWait(5);
		boolean found = false;
		while (!found) {

			try {

				sk.screen.wait(ImgPatter.get("scrollDown"), 15).click();
				u.hardWait(1);
				sk.screen.wait(ImgPatter.get(imagePath), 1).highlight(1, "red").click();
				u.hardWait(1);
				sk.screen.type(Key.TAB);
				u.hardWait(1);
				found = true;
			} catch (FindFailed e) {

				System.out.println("Imagen no encontrada, intentando nuevamente...");
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return found;

	}

	public void addNewCluster(String ClName, String ClDes) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.type(ClName);
		sk.screen.type(Key.TAB);
		u.hardWait(5);
		sk.screen.type(ClDes);
		sk.screen.type(Key.TAB);
		sk.screen.wait(ImgPatter.get("SaveClick"), 15).click();
		u.hardWait(5);
	}

	public void cerrarApplicacion() throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.wait(ImgPatter.get("ClosePage"), 15).click();
		u.hardWait(2);
		sk.screen.type(Key.ENTER);
		u.hardWait(2);
		sk.screen.type(Key.ENTER);
	}

	public void clickImagen(String imagen) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.click((ImgPatter.get(imagen)));
	}

	public void clickDerecho(String imagen) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.rightClick((ImgPatter.get(imagen)));
	}

	public void f11(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.F11);
			x = x + 1;
		}
	}

	public void ctrlF11() {
		u.hardWait(2);
		u.hardWait(1);
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown(Key.F11);
		sk.screen.keyUp(Key.F11);
		sk.screen.keyUp(Key.CTRL);
	}

	public void enviarTexto(String n) {
		sk.screen.type(n);
	}

	public void switchWindowAltTab() throws FindFailed {
		sk.screen.keyDown(Key.ALT); // Presiona la tecla ALT
		sk.screen.keyDown(Key.TAB); // Presiona la tecla TAB
		sk.screen.keyUp(Key.TAB); // Suelta la tecla TAB
		sk.screen.keyUp(Key.ALT); // Suelta la tecla ALT
	}

	public void pegarTexto(String n) {
		sk.screen.paste(n);
	}

	public void buscarJob(String n) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.type(n);
		sk.screen.click((ImgPatter.get("find_lupa")));
	}

	public void cerrarApplicacion2() throws FindFailed {
		try {
			ImgPatter = getPatternsImg(Fol);
			sk.screen.wait(ImgPatter.get("ClosePage"), 15).click();
			u.hardWait(5);
			sk.screen.type(Key.ENTER);
			u.hardWait(10);
		} catch (FindFailed e) {
			throw new AssertionError("Error al cerrar la aplicacion.", e);
		}
	}

	public void cerrarApplicacion3() throws FindFailed {
		try {
			ImgPatter = getPatternsImg(Fol);
			sk.screen.wait(ImgPatter.get("close_page_explorer"), 15).click();
			u.hardWait(5);
			sk.screen.wait(ImgPatter.get("close_page_red"), 15).click();
			u.hardWait(15);
			sk.screen.type(Key.ENTER);
			u.hardWait(15);
		} catch (FindFailed e) {
			throw new AssertionError("Error al cerrar la aplicacion.", e);
		}
	}

	public String copiarYObtenerTextoDePantalla() {
		// Simular Ctrl+A para seleccionar todo el texto en la pantalla
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("a");
		sk.screen.keyUp("a");
		sk.screen.keyUp(Key.CTRL);

		// Simular Ctrl+C para copiar el texto al portapapeles
		sk.screen.keyDown(Key.CTRL);
		sk.screen.keyDown("c");
		sk.screen.keyUp("c");
		sk.screen.keyUp(Key.CTRL);

		// Esperar un momento para que el texto se copie correctamente al portapapeles
		try {
			Thread.sleep(500); // Un delay corto para asegurar que se complete el copiado
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Obtener el texto del portapapeles
		try {
			return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (Exception e) {
			throw new RuntimeException("Error al obtener el texto del portapapeles", e);
		}
	}

	/**
	 * Método que extrae el Concurrent Request ID de un log proporcionado.
	 * 
	 * @param log El String que contiene el log del cual se extraer el Concurrent
	 *            Request ID.
	 * @return El Concurrent Request ID extraído del log.
	 * @throws RuntimeException Si no se encuentra el Concurrent Request ID en el
	 *                          log.
	 * @author JesusM3@hexaware.com
	 */
	public String extractConcurrentRequestId(String log) {
		// Uso de java.util.regex.Pattern sin importarlo
		java.util.regex.Pattern regexPattern = java.util.regex.Pattern.compile("Concurrent Request ID:\\s*(\\S+)");

		// Crear un matcher para buscar el patron en el log
		Matcher matcher = regexPattern.matcher(log);

		// Si se encuentra una coincidencia, devolver el Concurrent Request ID
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			throw new RuntimeException("Concurrent Request ID no encontrado en el log.");
		}
	}

	/**
	 * Método que extrae el Batch Name de un log proporcionado. Este método busca la
	 * línea que contiene el mes especificado, recorta la línea hasta el mes, y
	 * elimina el último ID para formar el Batch Name.
	 *
	 * @param log El String que contiene el log del cual se extraer� el Batch Name.
	 * @param mes El mes que marca el final del Batch Name.
	 * @return El Batch Name extraído del log.
	 * @throws RuntimeException Si no se puede extraer el Batch Name.
	 * @author JesusM3@hexaware.com
	 */
	public String extractBatchName(String log, String mes) {
		// Dividir el log en l íneas
		String[] lines = log.split("\n");
		for (String line : lines) {
			// Verificar si la l ínea contiene el mes
			if (line.contains(mes)) {
				// Encontrar la posici �n del mes en la l ínea
				int mesIndex = line.indexOf(mes);
				if (mesIndex != -1) {
					// Recortar la l ínea hasta el mes
					String trimmedLine = line.substring(0, mesIndex).trim();

					// Dividir la l ínea recortada por espacios
					String[] parts = trimmedLine.split(" ");

					// Verificar si hay suficientes partes para eliminar el último ID
					if (parts.length > 1) {
						// Unir las partes hasta el pen último ID
						StringBuilder batchName = new StringBuilder();
						for (int i = 0; i < parts.length - 1; i++) {
							if (i > 0) {
								batchName.append(" ");
							}
							batchName.append(parts[i]);
						}
						return batchName.toString();
					}
				}
			}
		}
		throw new RuntimeException("No se pudo extraer el Batch Name.");
	}

	/**
	 * Método que extrae el PIE de un batchName proporcionado. El PIE se define como
	 * los 8 caracteres que siguen inmediatamente a "PF".
	 *
	 * @param batchName La cadena que contiene el batchName del cual se extraer� el
	 *                  PIE.
	 * @return El PIE extraído del batchName.
	 * @throws RuntimeException Si el PIE no se encuentra o est� fuera de rango en
	 *                          el batchName.
	 * @author JesusM3@hexaware.com
	 */
	public String extractPIE(String batchName) {
		// Buscar la posici�n de "PF" en la cadena
		int pfIndex = batchName.indexOf("PF");

		// Verificar si "PF" se encuentra en la cadena
		if (pfIndex != -1 && pfIndex + 2 + 8 <= batchName.length()) {
			// Extraer los 8 caracteres siguientes a "PF"
			return batchName.substring(pfIndex + 2, pfIndex + 2 + 8).trim();
		} else {
			throw new RuntimeException("PIE no encontrado o fuera de rango en el Batch Name.");
		}
	}

	/**
	 * M�todo que extrae el id_documento_pago de la �ltima l�nea de un log. El
	 * id_documento_pago se define como los primeros 8 caracteres de la �ltima
	 * l�nea.
	 *
	 * @param log El log completo como una cadena de texto.
	 * @return El id_documento_pago extra�do de la �ltima l�nea del log.
	 * @throws RuntimeException Si el log est� vac�o o la �ltima l�nea tiene menos
	 *                          de 8 caracteres.
	 * @author EMAIL_ADDRESS_2
	 */
	public String extractIdDocumentoPago(String log) {
		// Dividir el log en l�neas
		String[] lines = log.split("\n");

		// Verificar si el log no est� vac�o
		if (lines.length == 0) {
			throw new RuntimeException("El log est� vac�o.");
		}

		// Obtener la �ltima l�nea
		String lastLine = lines[lines.length - 1];

		// Verificar si la �ltima l�nea tiene al menos 8 caracteres
		if (lastLine.length() < 8) {
			throw new RuntimeException("La �ltima l�nea tiene menos de 8 caracteres.");
		}

		// Extraer los primeros 8 caracteres de la �ltima l�nea
		return lastLine.substring(0, 8).trim();
	}

	public void buscarAdvertencia() {
		try {
			ImgPatter = getPatternsImg(Fol); // Cargar los patrones de im�genes
			sk.screen.wait(ImgPatter.get("advertencia"), 5); // Esperar a que aparezca la advertencia (5 segundos)
			u.hardWait(5); // Esperar 2 segundos
			sk.screen.wait(ImgPatter.get("ok_advertencia"), 5).click(); // Esperar y hacer clic en el bot�n
																		// "ok_advertencia" (5 segundos)
			u.hardWait(2); // Esperar 2 segundos
		} catch (FindFailed e) {
			System.out.println("No se encontr� la ventana de advertencia."); // Imprimir mensaje en lugar de lanzar
																				// error
		}
	}

	public void esperarConcurrenteEnRunning() {
		long startTime = System.currentTimeMillis();
		long maxDuration = 30 * 60 * 1000; // 20 minutes in milliseconds

		try {
			ImgPatter = getPatternsImg(Fol); // Cargar los patrones de im�genes

			while (true) {
				// Esperar 20 segundos
				u.hardWait(20);

				// Buscar la imagen "running"
				if (sk.screen.exists(ImgPatter.get("running"), 5) != null) {
					// Si se encuentra, hacer clic en la imagen "refresh"
					sk.screen.wait(ImgPatter.get("refresh"), 5).click();
				} else {
					// Si no se encuentra, el proceso ha terminado
					System.out.println("Concurrente finalizado con �xito");
					break;
				}

				// Verificar si ha pasado m�s de 20 minutos
				if (System.currentTimeMillis() - startTime > maxDuration) {
					System.out.println("Error: el proceso se qued� en 'running' m�s de 20 minutos.");
					break;
				}
			}
		} catch (FindFailed e) {
			System.out.println("No se encontr� la imagen.");
		}
	}

	/**
	 * Metodo para agregar un nuevo hijo a un Cluster
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public void addNewChildToCluster() throws FindFailed {
		try {
			ImgPatter = getPatternsImg(Fol);
			sk.screen.wait(ImgPatter.get("HierarchyBTN"), 15).click();
			u.hardWait(1);
			sk.screen.wait(ImgPatter.get("enableParent"), 15).click();
			u.hardWait(1);
			sk.screen.wait(ImgPatter.get("defineChildBTN"), 15).click();
			u.hardWait(1);
			sk.screen.wait(ImgPatter.get("fromBox"), 15).type("04LAP");
			sk.screen.type(Key.TAB);
			u.hardWait(2);
			sk.screen.wait(ImgPatter.get("SaveClick"), 15).click();
			u.hardWait(1);
		} catch (FindFailed e) {
			throw new AssertionError("Error al agregar un nuevo hijo al Cluster", e);
		}
	}

	/**
	 * Metodo hacer click Responsabilidad Cluster
	 * 
	 * @param campo
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public void CheckResponsabilidad() throws FindFailed {
		try {
			ImgPatter = getPatternsImg(Fol);
			sk.screen.wait(ImgPatter.get("responsabilidadCluster"), 60).click();
		} catch (FindFailed e) {
			throw new AssertionError("Error al hacer click en Responsabilidad Cluster", e);
		}
	}

	/**
	 * Metodo para hacer alt+tab
	 * 
	 * @param n
	 * @author JesusM3@hexaware.com
	 */
	public void nDown(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.DOWN);
			x = x + 1;
		}
	}

	/**
	 * Metodo para validar el child salvado en Cluster
	 * 
	 * @param campo
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public boolean ValidarChildClusterSave(String imagePath) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);

		boolean found = false;
		try {

			sk.screen.wait(ImgPatter.get(imagePath), 15).click();
			u.hardWait(1);
			sk.screen.type(Key.ENTER);
			u.hardWait(1);
			found = true;
		} catch (FindFailed e) {

			System.out.println("Imagen no encontrada, intentando nuevamente...");
		}

		return found;

	}

	/**
	 * Metodo para validar responsabilidad
	 * 
	 * @param campo
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public boolean ValidarCarpeta(String imagePath) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);

		boolean found = false;
		try {

			sk.screen.wait(ImgPatter.get(imagePath), 15).click();
			u.hardWait(1);
			sk.screen.type(Key.ENTER);
			u.hardWait(1);
			found = true;
		} catch (FindFailed e) {

			System.out.println("Imagen no encontrada, intentando nuevamente...");
		}

		return found;

	}

	/**
	 * Metodo metodo para llenar linea de factura
	 * 
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */

	public void validarImagen(String imagen) {
		try {
			ImgPatter = getPatternsImg(Fol);
			if (sk.screen.exists(ImgPatter.get(imagen)) != null) {
				System.out.println("La imagen '" + imagen + "' existe en la pantalla.");
			} else {
				System.out.println("La imagen '" + imagen + "' no existe en la pantalla.");
			}
		} catch (Exception e) {
			System.out.println("Ocurri� un error al validar la imagen: " + e.getMessage());
		}
	}

	/**
	 * Validar la presencia de una imagen en pantalla esperando hasta 3 minutos.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param imagen Nombre de la imagen a validar en pantalla.
	 * @throws FindFailed Excepción lanzada si la imagen no se encuentra.
	 * @date 16/02/2026
	 */
	public void validarImagen2(String imagen) throws FindFailed {
		try {
			ImgPatter = getPatternsImg(Fol);
			// Esperar hasta 180 segundos (3 minutos) a que la imagen aparezca
			if (sk.screen.wait(ImgPatter.get(imagen), 180) != null) {
				System.out.println("La imagen '" + imagen + "' existe en la pantalla.");
			} else {
				System.out
						.println("La imagen '" + imagen + "' no existe en la pantalla despuï¿½s de esperar 3 minutos.");
			}
		} catch (FindFailed e) {
			System.out.println("La imagen '" + imagen + "' no existe en la pantalla despuï¿½s de esperar 3 minutos.");
		} catch (Exception e) {
			System.out.println("Ocurriï¿½ un error al validar la imagen: " + e.getMessage());
		}
	}

	/**
	 * Verificar si una imagen existe en pantalla y retornar un valor booleano.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param imagen Nombre de la imagen a buscar en pantalla.
	 * @return true si la imagen existe; false si no existe o en caso de error.
	 * @throws FindFailed Excepción lanzada si falla la búsqueda de la imagen.
	 * @date 16/02/2026
	 */
	public boolean validarImagenBoolean(String imagen) throws FindFailed {
		try {
			ImgPatter = getPatternsImg(Fol);
			// Verificar si la imagen existe en la pantalla
			if (sk.screen.exists(ImgPatter.get(imagen)) != null) {
				System.out.println("La imagen '" + imagen + "' existe en la pantalla.");
				return true; // La imagen fue encontrada
			} else {
				System.out.println("La imagen '" + imagen + "' no existe en la pantalla.");
				return false; // La imagen no fue encontrada
			}
		} catch (Exception e) {
			System.out.println("Ocurriï¿½ un error al validar la imagen: " + e.getMessage());
			return false; // Error inesperado
		}
	}

	public void doubleClickImagen(String imagen) throws FindFailed {
		ImgPatter = getPatternsImg(Fol);
		sk.screen.doubleClick((ImgPatter.get(imagen)));
	}

	/**
	 * muestra lo de el lado derecho
	 * 
	 * @throws FindFailed
	 * @author ricardo.palacios
	 */
	public void moverDerecha() throws FindFailed {
		sk.screen.keyUp(Key.RIGHT);
		u.hardWait(4);
	}

	/**
	 * Metodo que elige y luego escribe
	 * 
	 * @param pattern, text
	 * @author monica.soto
	 */
	public void clickAndType(String pattern, String text, Boolean tab) throws FindFailed {

		sk.screen.wait(ImgPatter.get(pattern), 15).click();
		u.hardWait(2);
		sk.screen.type(text);

		if (tab) {
			sk.screen.type(Key.TAB);
		}
	}

	/**
	 * Metodo que escribe los 7 parametros necesarios para proveedor
	 * 
	 * @param proveedor, formaPago, plaza, fechaInicio, fechaFin, tipoReporte,
	 *                   tipoInfo
	 * @author monica.soto
	 */
	public void proveedor7parametros(String proveedor, String formaPago, String plaza, String fechaInicio,
			String fechaFin, String tipoReporte, String tipoInfo) throws FindFailed {

		sk.screen.wait(ImgPatter.get("proveedor"), 15).click();
		sk.screen.type(proveedor);

		sk.screen.wait(ImgPatter.get("formadePago"), 15).click();
		sk.screen.type(formaPago);

		if (proveedor.equals("COMPROPAGO SAPI DE CV") || proveedor.equals("GRUPO CONEKTAME SA. DE CV.")
				|| proveedor.equals("Ocsi Soluciones S.A. DE C.V.")) {
			sk.screen.wait(ImgPatter.get("plaza2"), 15).click();
			sk.screen.type(plaza);
		} else {

			System.out.println(proveedor);
		}

		sk.screen.wait(ImgPatter.get("fechaTransaccionInicio"), 15).click();
		sk.screen.type(fechaInicio);

		sk.screen.keyUp(Key.BACKSPACE);
		sk.screen.type(fechaFin);

		sk.screen.type(tipoReporte);

		sk.screen.wait(ImgPatter.get("tipoInfo"), 15).click();
		sk.screen.type(tipoInfo);

	}

	/**
	 * Metodo que conserva archivos no seguros
	 * 
	 * @param pattern, text
	 * @author monica.soto
	 */
	public void archivoNoSeguro() throws FindFailed {

		sk.screen.wait(ImgPatter.get("tresPuntosDispersion"), 15).click();
		sk.screen.wait(ImgPatter.get("conservarArchivo"), 15).click();
		sk.screen.wait(ImgPatter.get("conservarTodosModos"), 15).click();
	}

	/**
	 * Metodo que sale de Oracle si en este faltan parametros
	 * 
	 * @param pattern, text
	 * @author monica.soto
	 */
	public void salirAppletFaltaParametros() throws FindFailed {

		sk.screen.wait(ImgPatter.get("aceptarAdvertencia"), 15).click();
		sk.screen.wait(ImgPatter.get("cancelar"), 15).click();
		sk.screen.wait(ImgPatter.get("cerrarQa"), 15).click();
		sk.screen.wait(ImgPatter.get("desechar"), 15).click();

	}

	// java
	/**
	 * Emula presionar la tecla ESC (detener carga / stop) una vez
	 *
	 * @author JesusM3@hexaware.com
	 */
	public void presionarESC() {
		// Usando la API de Sikuli (ajusta si usas otra biblioteca)
		try {
			sk.screen.type(Key.ESC);
		} catch (Throwable t) {
			// Si la API de Sikuli falla, intentar con keyDown/keyUp como fallback
			try {
				sk.screen.keyDown(Key.ESC);
				sk.screen.keyUp(Key.ESC);
			} catch (Throwable t2) {
				logger.error("No se pudo enviar ESC al sistema", t2);
			}
		}
	}

}