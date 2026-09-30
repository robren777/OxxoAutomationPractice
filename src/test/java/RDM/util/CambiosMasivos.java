package RDM.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.FrameworkException;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class CambiosMasivos {

	SikuliUtil sk;
	SeleniumUtil u;
	ArrayList<Pattern> patterns;
	File Folder;
	HashMap<String, Pattern> ImgPattern;
	ArrayList<String> ListImage;
	Pattern pathImg;
	TestCase testCase;

	private String url = GlobalVariables.serverCambiosMasivosTlajomulco;
	private String userNameCambiosMasivos = GlobalVariables.usuarioCambiosMasivos;
	private String passwordCambiosMasivos = GlobalVariables.contrasenaCambiosMasivos;

	protected static final Logger logger = LoggerFactory.getLogger(CambiosMasivos.class);

	/**
	 * @param sk         Instancia de Sikuli
	 * @param u          Instancia de SeleniumUtil
	 * @param pathImg
	 * @param ListImage
	 * @param ImgPattern
	 * @param patterns
	 * @param Folder     Carpeta de Imagenes
	 */
	public CambiosMasivos(SikuliUtil sk, SeleniumUtil u, Pattern pathImg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPattern, ArrayList<Pattern> patterns, File Folder, TestCase testcase) {
		this.sk = sk;
		this.u = u;
		this.patterns = patterns;
		this.Folder = Folder;
		this.ListImage = ListImage;
		this.pathImg = pathImg;
		this.ImgPattern = ImgPattern;
		this.testCase = testcase;

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
	 * Metodo que da Click en la pagina XXFC CAMBIOS MASIVOS
	 * 
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean clickPmoCambiosMasivos() throws FindFailed {
		try {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("spfun"));
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(" ");
			sk.screen.click(ImgPattern.get("ReportsCambiosMasivos"));
			u.hardWait(5);
			sk.screen.click(ImgPattern.get("XXFCCAMBIOSMASIVOS_2"));
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(15);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo que da Click en la pagina Configuracion de Ruta
	 * 
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean clickPmoConfigRutaCiclo() throws FindFailed {
		try {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("spfun"));
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(" ");
			sk.screen.click(ImgPattern.get("ReportsCambiosMasivos"));
			u.hardWait(5);
			sk.screen.click(ImgPattern.get("configrutaciclo"));
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(20);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo que da Click en la pagina de Historico PMO
	 * 
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean clickHistoricoPmo() throws FindFailed {
		try {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("spfun"));
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(" ");
			sk.screen.click(ImgPattern.get("ReportsCambiosMasivos"));
			u.hardWait(5);
			sk.screen.click(ImgPattern.get("historicoPmo"));
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(20);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo que Ingresa Plaza y fechas y busca informacion
	 * 
	 * @param plaza
	 * @param fecha1
	 * @param fecha2
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean ingresarPlazaFechas(String plaza, String fecha1, String fecha2) throws FindFailed {
		try {
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(5);
			sk.screen.type(plaza);
			this.nTAB(10);
			sk.screen.type(fecha1);
			this.nTAB(1);
			sk.screen.type(fecha2);
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(5);
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("checkrojo"));
			u.hardWait(4);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo que Ingresa los parametros completos para realizar la busqueda de
	 * informacion
	 * 
	 * @param plaza
	 * @param p
	 * @param diaGen
	 * @param ruta
	 * @param fecha1
	 * @param fecha2
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean ingresarPlazaFechas2(String plaza, String p, String diaGen, String ruta, String fecha1, String fecha2)
			throws FindFailed {
		try {
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(5);
			sk.screen.type(plaza);
			this.nTAB(2);
			sk.screen.type(p);
			this.nTAB(1);

			u.hardWait(10);
			sk.screen.type(Key.DOWN);
			this.nEnter(1);
			this.nTAB(1);
			sk.screen.type(diaGen);
			this.nTAB(4);
			sk.screen.type(ruta);
			this.nTAB(2);
			sk.screen.type(fecha1);
			this.nTAB(1);
			sk.screen.type(fecha2);
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(5);
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("checkrojo"));
			u.hardWait(4);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo que valida la existencia de boton Check
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarcheck2() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("checkrojo")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida la existencia la informacion correspondiente a el caso 013
	 * de cambios masivos NOTA: METODO NO REUTIIZABLE
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarInfoLogTienda13() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("infohu013")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que da Click a eliminar la configuracion
	 * 
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean clickEliminarConfiguracion() throws FindFailed {
		try {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("eliminarconfiguracion"));
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(" ");
			this.nEnter(1);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Metodo que da Click en log tienda
	 * 
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean clickLogTienda() throws FindFailed {
		try {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("logTienda"));
			u.hardWait(10);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	/**
	 * Metodo que da Click en exportar ruta
	 * 
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean clickExportarRuta() throws FindFailed {
		try {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("exportarRuta"));
			u.hardWait(20);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	/**
	 * Metodo que da Click en exportar tienda
	 * 
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public void clickExportarTienda() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		sk.screen.click(ImgPattern.get("botonexporttienda"));
		u.hardWait(20);
		testCase.addScreenShotCurrentStep(" ");

	}

	/**
	 * Metodo que da Click en dias de entrega
	 * 
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean clickDiasEntrega() throws FindFailed {
		try {
			u.hardWait(5);
			ImgPattern = getPatternsImg(Folder);
			sk.screen.find(ImgPattern.get("diasentrega")).right(5).click();
			u.hardWait(5);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Metodo que agrega las variables correspondientes para la busqueda
	 * 
	 * @param distribucion
	 * @param ruta
	 * @param plaza
	 * @param cilco
	 * @param produccion
	 * @param carga
	 * @param verificacion
	 * @param jit
	 * @param envio
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean agregarCambiosMasivos(String distribucion, String ruta, String plaza, String cilco,
			String produccion, String carga, String verificacion, String jit, String envio) throws FindFailed {
		try {
			u.hardWait(5);
			this.nTAB(1);
			u.hardWait(3);
			this.nEnter(1);
			sk.type(distribucion);
			this.nTAB(1);
			u.hardWait(3);
			sk.type(ruta);
			this.nTAB(1);
			u.hardWait(3);
			sk.type(plaza);
			this.nTAB(1);
			u.hardWait(3);
			sk.type(cilco);
			this.nTAB(1);
			u.hardWait(3);
			sk.type(produccion);
			this.nTAB(3);
			u.hardWait(3);
			sk.type(verificacion);
			this.nTAB(3);
			u.hardWait(3);
			sk.type(carga);
			this.nTAB(3);
			u.hardWait(3);
			sk.type(jit);
			this.nTAB(3);
			u.hardWait(3);
			sk.type(envio);

			ImgPattern = getPatternsImg(Folder);
			sk.screen.find(ImgPattern.get("guardarregistro")).click();
			testCase.addScreenShotCurrentStep(" ");
			this.nEnter(1);
			u.hardWait(4);
			return true;
		} catch (Exception e) {
			return false;
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
	 * Metodo que valida la existencia de valores
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarListaValoresEntrega() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("listaValoresEntrega")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida la informacion del reporte para el caso 11 Nota este metodo
	 * no es reutilizable
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarReporte02hu11() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("reporte02hu11")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida la informacion del reporte para el caso 11 Nota este metodo
	 * no es reutilizable
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarReporte03hu11() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("reporte03hu11")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida la informacion del reporte para el caso 13 Nota este metodo
	 * no es reutilizable
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarReporte13PMO() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("reporteHistorico13")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida la informacion del reporte para el caso 11 Nota este metodo
	 * no es reutilizable
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarReporte04hu11() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("reporte04hu11")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida dias mayores a 30
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarDiasMayorTreinta() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("diasmayoratreinta")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida tiempo de verificacion sea cero
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarVerificacionCero() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("tiempoVerificacionCero")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida tipo de distribucion sea vacio
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarTipoDistribucionVacio() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("tipoDistribucionVacio")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida tiempo JIT con error
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarErrorTiemposJit() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("errorCorteJit")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida alerta de tiempo existente exista
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarAlertaTiempoExistente() throws FindFailed {

		this.nEnter(1);
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("alertatiempoyaexiste")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida los datos de configuracion de tiempo interno antes de
	 * guardar
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarConsultaTiempoGuardado() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("consultatiempoguardado")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida alerta de no se encuentran registros para consultar
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarAlertaSinRegistos() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("alertasinregistros")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida una modificacion de configuracion
	 * 
	 * @param numero
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean modificarTiempo(String numero) throws FindFailed {

		try {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("modificar"));

			testCase.addScreenShotCurrentStep(" ");
			this.nTAB(1);
			sk.screen.type(numero);
			sk.screen.click(ImgPattern.get("guardarregistro"));
			testCase.addScreenShotCurrentStep(" ");
			this.nEnter(2);
			u.hardWait(4);
			sk.screen.click(ImgPattern.get("alertamodificacion"));
			testCase.addScreenShotCurrentStep(" ");
			return true;

		} catch (Exception e) {

			return false;

		}
	}

	/**
	 * Metodo que valida configuracion
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarListaValoresDeFiltros() throws FindFailed {

		boolean a = false;

		if (a == false) {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.exists(ImgPattern.get("plaza"));
			sk.screen.exists(ImgPattern.get("diageneracion"));
			sk.screen.exists(ImgPattern.get("tipodistribucion"));
			sk.screen.exists(ImgPattern.get("ruta"));
			sk.screen.exists(ImgPattern.get("diaentrega"));
			testCase.addScreenShotCurrentStep(" ");
			return true;

		} else {
			return false;

		}

	}

	/**
	 * Metodo que valida la pantalla de incio este vacia
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarPantallaInicio() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("iniciovacio")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida alerta de distribucion inexistente aparece
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarAlertaDistribucionNoExiste() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("validarTipoDistribucionNoExiste")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}
	}

	/**
	 * Metodo que valida pantalla de configuracion de tiempo interno
	 * 
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarPantallaConfiguracionTiempoInterno() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("configuraciontiempointerno")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}

	}

	/**
	 * Metodo que valida informacion sobre plaza existente
	 * 
	 * @param plaza
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean validarInformacionSobrePlaza15XEG(String plaza) throws FindFailed {

		sk.screen.type(plaza);
		u.hardWait(4);
		ImgPattern = getPatternsImg(Folder);
		sk.screen.click(ImgPattern.get("checkrojo"));
		u.hardWait(6);
		if (sk.screen.exists(ImgPattern.get("infocaso4")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {

			return false;
		}

	}

	/**
	 * validarInformacionSobrePlaza10GUD. Metodo de apoyo validar la correcta
	 * seleccion de plaza
	 * 
	 * @param plaza
	 * @return
	 * @throws FindFailed
	 */
	public boolean validarInformacionSobrePlaza10GUD(String plaza) throws FindFailed {
		sk.screen.type(plaza);
		u.hardWait(4);
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("checkrojo")) != null) {
			sk.screen.click(ImgPattern.get("checkrojo"));
			u.hardWait(6);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ubicarLunesDiaGeneracion. Selecciona los dias lunes de generación
	 * 
	 * @return
	 */
	public boolean ubicarLunesDiaGeneracion() {
		ImgPattern = getPatternsImg(Folder);
		boolean result;

		if (sk.screen.exists(ImgPattern.get("diaGenPedidoLU")) != null) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * ubicarLunesDiaVentanaEntrega. Selecciona los lunes en ventanas de entrega
	 * 
	 * @return
	 */
	public boolean ubicarLunesDiaVentanaEntrega() {
		ImgPattern = getPatternsImg(Folder);
		boolean result;

		if (sk.screen.exists(ImgPattern.get("diaVentanaEntregaLU")) != null) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Metodo que busca configuracion por plaza y ruta
	 * 
	 * @param plaza
	 * @param ruta
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean buscarPorPlazaRuta(String plaza, String ruta) throws FindFailed {
		try {
			sk.screen.type(plaza);
			u.hardWait(4);
			this.nTAB(4);
			u.hardWait(4);
			sk.screen.type(ruta);
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("checkrojo"));
			u.hardWait(6);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * seleccionarRegistro. Metodo de apoyo para seleccionar algun registro del
	 * listado
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean seleccionarRegistro() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("checkgrid")) != null) {
			sk.screen.click(ImgPattern.get("checkgrid"));
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Metodo que busca configuracion por plaza
	 * 
	 * @param plaza
	 * @return
	 * @throws FindFailed
	 * @author RicardoR4@hexaware.com
	 */
	public boolean buscarPorPlaza(String plaza) throws FindFailed {
		try {
			sk.screen.type(plaza);
			u.hardWait(4);
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("checkrojo"));
			u.hardWait(6);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} catch (Exception e) {
			return false;
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
	 * Inicia sesion en RDM con credenciales de cambios masivos
	 *
	 * @author ricardo.palacios
	 * @throws InterruptedException
	 */
	public boolean logInCambiosMasivos() {
		try {
			u.get(url);
			u.waitForLoadPage();
			u.hardWait(10);
			maximizar();
			// ingresa datos de acceso
			sk.screen.type(userNameCambiosMasivos);
			u.hardWait(5);
			sk.screen.type(Key.TAB);
			u.hardWait(5);
			sk.screen.type(passwordCambiosMasivos);
			u.hardWait(8);
			sk.screen.type(Key.TAB);
			u.hardWait(5);
			sk.screen.type(Key.TAB);
			testCase.addScreenShotCurrentStep(" ");
			sk.screen.type(Key.ENTER);
			u.hardWait(15);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * agregarDiasGeneracion. Metodo de apoyo validar el dia de generacion agregado
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean agregarDiasGeneracion() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("agregarDiasGeneracion")) != null) {
			sk.screen.click(ImgPattern.get("agregarDiasGeneracion"));
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * eliminarDiasGeneracion. Metodo de apoyo para eliminar dias de generacion
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean eliminarDiasGeneracion() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("eliminarDiasGeneracion")) != null) {
			sk.screen.click(ImgPattern.get("eliminarDiasGeneracion"));
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * realizarBusqueda. Realiza busqueda de registros
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean realizarBusqueda() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("checkrojo")) != null) {
			sk.screen.click(ImgPattern.get("checkrojo"));
			u.hardWait(5);
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * asignarPlazaRuta. Metodo de apoyo para la seleccion de filtros de los campos
	 * PLAZA y RUTA
	 * 
	 * @param plaza
	 * @param ruta
	 */
	public boolean asignarPlazaRuta(String plaza, String ruta) {
		try {
			sk.screen.type(plaza);
			u.hardWait(4);
			this.nTAB(4);
			u.hardWait(4);
			sk.screen.type(ruta);
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}

	/**
	 * asignarDiaGeneracionYVentanaEntregaValido. Metodo de apoyo para asignar día y
	 * ventana de entrega en la pantalla
	 * 
	 * @param diaGenPedido
	 * @param diaVenEntrega
	 * @return
	 * @throws FindFailed
	 */
	public boolean asignarDiaGeneracionYVentanaEntregaValido(String diaGenPedido, String diaVenEntrega)
			throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		sk.screen.mouseMove(ImgPattern.get("diaGeneracionPedido"));
		sk.screen.type(Key.TAB);
		sk.screen.type(diaGenPedido);
		sk.screen.mouseMove(ImgPattern.get("diaVentanaEntrega"));
		sk.screen.type(Key.TAB);
		sk.screen.type(diaVenEntrega);
		u.hardWait(3);
		if (sk.screen.exists(ImgPattern.get("checkgrid")) != null) {
			sk.screen.mouseMove(ImgPattern.get("checkgrid"));
			sk.screen.click(ImgPattern.get("checkgrid"));
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		try {
			if (sk.screen.exists(ImgPattern.get("alertaRegistrosSeleccionados")) != null) {
				clicBtnYes();
				u.hardWait(5);
				result = true;
			} else {
				result = false;
			}
			if (sk.screen.exists(ImgPattern.get("seAplicaronLosCambios")) != null) {
				clicBtnYes();
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		clearAndExecute();
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("");
		return result;
	}

	/**
	 * asignarNuevoDiaGeneracionYVentanaEntregaValido. Asigan nuevos dias de
	 * generacion
	 * 
	 * @param diaGenPedido
	 * @param diaVenEntrega
	 * @return
	 * @throws FindFailed
	 */
	public boolean asignarNuevoDiaGeneracionYVentanaEntregaValido(String diaGenPedido, String diaVenEntrega)
			throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		sk.screen.type(diaGenPedido);
		u.hardWait(3);
		sk.screen.type(diaVenEntrega);
		u.hardWait(3);
		if (sk.screen.exists(ImgPattern.get("plazaRutaFiltros")) != null) {
			sk.screen.click(ImgPattern.get("aplicarCambios"));
			sk.screen.doubleClick(ImgPattern.get("checkrojo"));
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");

			result = true;
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * asignarDiaGeneracionYVentanaEntregaEnBlanco. Asigna dias de generacion sin
	 * información
	 * 
	 * @param diaGenPedido
	 * @param diaVenEntrega
	 * @return
	 * @throws FindFailed
	 */
	public boolean asignarDiaGeneracionYVentanaEntregaEnBlanco(String diaGenPedido, String diaVenEntrega)
			throws FindFailed {
		boolean result = false;
		ImgPattern = getPatternsImg(Folder);
		sk.screen.mouseMove(ImgPattern.get("diaGeneracionPedido"));
		sk.screen.click(ImgPattern.get("diaGeneracionPedido"));
		sk.screen.type(diaGenPedido);
		sk.screen.mouseMove(ImgPattern.get("diaVentanaEntrega"));
		sk.screen.type(Key.TAB);
		sk.screen.type(diaVenEntrega);
		if (sk.screen.exists(ImgPattern.get("checkgrid")) != null) {
			sk.screen.mouseMove(ImgPattern.get("checkgrid")); // -----
			sk.screen.mouseMove(0, -40); // ----
			sk.screen.click(ImgPattern.get("checkgrid"));
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		try {
			if (sk.screen.exists(ImgPattern.get("formMsgValoresEnBlanco")) != null) {
				clicBtnYes();
				u.hardWait(6);
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		u.hardWait(4);
		testCase.addScreenShotCurrentStep("");
		clearAndExecute();
		return result;
	}

	/**
	 * asignarDiaGeneracionInvalido. Asigna dias de generacion con informacion no
	 * valida
	 * 
	 * @param diaGenPedido
	 * @return
	 * @throws FindFailed
	 */
	public boolean asignarDiaGeneracionInvalido(String diaGenPedido) throws FindFailed {
		boolean result = false;
		ImgPattern = getPatternsImg(Folder);
		sk.screen.mouseMove(ImgPattern.get("diaGeneracionPedido"));
		sk.screen.click(ImgPattern.get("diaGeneracionPedido"));
		sk.screen.type(diaGenPedido);
		if (sk.screen.exists(ImgPattern.get("checkgrid")) != null) {
			sk.screen.click(ImgPattern.get("checkgrid"));
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		try {
			if (sk.screen.exists(ImgPattern.get("formMsgValoresIncorrectos")) != null) {
				clicBtnYes();
				u.hardWait(6);
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("");
		clearAndExecute();
		return result;
	}

	/**
	 * asignarDiaGeneracionYVentanaEntregaInvalido. Asignacion de dia de generacion
	 * cpn ventana de entrega invalida
	 * 
	 * @param diaGenPedido
	 * @param diaVenEntrega
	 * @return
	 * @throws FindFailed
	 */
	public boolean asignarDiaGeneracionYVentanaEntregaInvalido(String diaGenPedido, String diaVenEntrega)
			throws FindFailed {
		boolean result = false;
		ImgPattern = getPatternsImg(Folder);
		sk.screen.mouseMove(ImgPattern.get("diaGeneracionPedido"));
		sk.screen.click(ImgPattern.get("diaGeneracionPedido"));
		sk.screen.type(diaGenPedido);
		sk.screen.mouseMove(ImgPattern.get("diaVentanaEntrega"));
		sk.screen.type(Key.TAB);
		sk.screen.type(diaVenEntrega);
		if (sk.screen.exists(ImgPattern.get("checkgrid")) != null) {
			sk.screen.click(ImgPattern.get("checkgrid"));
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		try {
			if (sk.screen.exists(ImgPattern.get("formMsgValoresIncorrectos")) != null) {
				clicBtnYes();
				u.hardWait(5);
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("");
		clearAndExecute();
		return result;
	}

	/**
	 * asignarDiaGeneracionRepetido. Asignacion de dia de generacion repetidos
	 * 
	 * @param diaGenPedido
	 * @return
	 * @throws FindFailed
	 */
	public boolean asignarDiaGeneracionRepetido(String diaGenPedido) throws FindFailed {
		boolean result = false;
		ImgPattern = getPatternsImg(Folder);
		sk.screen.mouseMove(ImgPattern.get("diaGeneracionPedido"));
		sk.screen.type(Key.TAB);
		sk.screen.type(diaGenPedido);
		if (sk.screen.exists(ImgPattern.get("checkgrid")) != null) {
			sk.screen.click(ImgPattern.get("checkgrid"));
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		try {
			if (sk.screen.exists(ImgPattern.get("alertaDiaGeneracionRepetido")) != null) {
				testCase.addScreenShotCurrentStep("");
				clicBtnYes();
				u.hardWait(5);
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		u.hardWait(3);
		clearAndExecute();
		return result;
	}

	/**
	 * asignarDiaVentanaEntregaRepetido. Asignacion de dia de ventana repetido
	 * 
	 * @param diaGeneracionPedido
	 * @param diaVentanaEntrega
	 * @return
	 * @throws FindFailed
	 */
	public boolean asignarDiaVentanaEntregaRepetido(String diaGeneracionPedido, String diaVentanaEntrega)
			throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		ImgPattern = getPatternsImg(Folder);
		sk.screen.mouseMove(ImgPattern.get("diaGeneracionPedido"));
		sk.screen.type(Key.TAB);
		sk.screen.type(diaGeneracionPedido);
		sk.screen.mouseMove(ImgPattern.get("diaVentanaEntrega"));
		sk.screen.type(Key.TAB);
		sk.screen.type(diaVentanaEntrega);
		if (sk.screen.exists(ImgPattern.get("checkgrid")) != null) {
			sk.screen.click(ImgPattern.get("checkgrid"));
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		try {
			if (sk.screen.exists(ImgPattern.get("alertaInicoVentanaEntregaRepetido")) != null) {
				u.hardWait(3);
				testCase.addScreenShotCurrentStep("");
				clicBtnYes();
				u.hardWait(5);
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		clearAndExecute();
		return result;
	}

	/**
	 * asignarDiaVentanaEntregaRepetidoCorrecto. Asignacion de dia de entrega
	 * repetido
	 * 
	 * @param diaGeneracionPedido
	 * @param diaVentanaEntrega
	 * @return
	 * @throws FindFailed
	 */
	public boolean asignarDiaVentanaEntregaRepetidoCorrecto(String diaGeneracionPedido, String diaVentanaEntrega)
			throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		sk.screen.mouseMove(ImgPattern.get("diaGeneracionPedido"));
		sk.screen.type(Key.TAB);
		sk.screen.type(diaGeneracionPedido);
		sk.screen.mouseMove(ImgPattern.get("diaVentanaEntrega"));
		sk.screen.type(Key.TAB);
		sk.screen.type(diaVentanaEntrega);
		if (sk.screen.exists(ImgPattern.get("checkgrid")) != null) {
			sk.screen.click(ImgPattern.get("checkgrid"));
			sk.screen.click(ImgPattern.get("aplicarCambios"));
			u.hardWait(4);
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		if (sk.screen.exists(ImgPattern.get("alertaRegistrosSeleccionados")) != null) {
			clicBtnYes();
			u.hardWait(5);
			result = true;
		} else {
			result = false;
		}
		if (sk.screen.exists(ImgPattern.get("seAplicaronLosCambios")) != null) {
			clicBtnYes();
			result = true;
		} else {
			result = false;
		}
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("");
		clearAndExecute();
		return result;
	}

	/**
	 * clicBtnYes. Ayuda a dar clic al boton de YES
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean clicBtnYes() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("btnSi")) != null) {
			sk.screen.click(ImgPattern.get("btnSi"));
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * clicBtnMo. Ayuda a dar clic al boton de NO
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean clicBtnNo() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("btnNo")) != null) {
			sk.screen.click(ImgPattern.get("btnNo"));
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * validarRegistrosSeleccionados. Valida seleccion de registros
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean validarRegistrosSeleccionados() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("alertaRegistrosSeleccionados")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			sk.screen.click(ImgPattern.get("btnSi"));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * validarMsgCambiosAplicados. Valida cambios aplicados
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean validarMsgCambiosAplicados() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("seAplicaronLosCambios")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			sk.screen.click(ImgPattern.get("btnSi"));
			u.hardWait(5);
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * modificarHorario. Modifica horario
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean modificarHorario() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("registroDOLU")) != null) {
			testCase.addScreenShotCurrentStep("");
			ScreenImage cap1 = sk.screen.capture();
			sk.screen.mouseMove(2);
			sk.screen.doubleClick();
			sk.screen.type("1");
			ScreenImage cap2 = sk.screen.capture();
			result = cap1.equals(cap2);
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * validarTipoMovimiento. Valida si existe un tipo de movimiento
	 * 
	 * @return
	 */
	public boolean validarTipoMovimiento() {
		boolean result;
		ImgPattern = getPatternsImg(Folder);

		if (sk.screen.exists(ImgPattern.get("tipoMovimiento")) != null) {
			result = true;
			testCase.addScreenShotCurrentStep("");
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * validarOpcionEliminarDiasGeneracion. Valida si existe la opcion de eliminar
	 * dia de generacion
	 * 
	 * @return
	 */
	public boolean validarOpcionEliminarDiasGeneracion() {
		boolean result;
		ImgPattern = getPatternsImg(Folder);

		if (sk.screen.exists(ImgPattern.get("eliminarDiasGeneracion")) != null) {
			result = true;
			testCase.addScreenShotCurrentStep("");
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * seleccionarEliminarDiasGeneracion. Seleccipna la opcion de Eliminar dia de
	 * generacion
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean seleccionarEliminarDiasGeneracion() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("eliminarDiasGeneracion")) != null) {
			sk.screen.click(ImgPattern.get("eliminarDiasGeneracion"));
			if (sk.screen.exists(ImgPattern.get("eliminarDiasGeneracionSeleccionado")) != null) {
				u.hardWait(3);
				testCase.addScreenShotCurrentStep("");
				result = true;
			} else {
				result = false;
			}
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * seleccionarTodosRegistros. Da clic al boton de seleccionar todo
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean seleccionarTodosRegistros() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("btnSelectAll")) != null) {
			sk.screen.doubleClick(ImgPattern.get("btnSelectAll"));
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * eliminarRegistro. Elimina registros
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean eliminarRegistro() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("alertaElimiarRegistros")) != null) {
			clicBtnYes();
			u.hardWait(3);
			if (sk.screen.exists(ImgPattern.get("alertNoEliminacionRegistroConTienda")) != null) {
				testCase.addScreenShotCurrentStep("");
				result = false;
			} else {
				result = true;
			}
			if (sk.screen.exists(ImgPattern.get("alertaRegistroEliminado")) != null) {
				testCase.addScreenShotCurrentStep("");
				result = true;
			} else {
				result = false;
			}
			clicBtnYes();
			testCase.addScreenShotCurrentStep("");
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * seleccionarRegistroEliminar. Selecciona registros a eliminar
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean seleccionarRegistroEliminar() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("checkgrid")) != null) {
			sk.screen.click(ImgPattern.get("checkgrid"));
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * mostrarTiendasPMO. Valida que exista el boton de Tienda PMO
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean mostrarTiendasPMO() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("btnTiendasPMO")) != null) {
			sk.screen.click("btnTiendasPMO");
			u.hardWait(3);
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * mostrarLogError. Valida que se muestre mensaje de error
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean mostrarLogError() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("btnLogError")) != null) {
			sk.screen.click(ImgPattern.get("btnLogError"));
			testCase.addScreenShotCurrentStep("");
			u.hardWait(3);
			sk.screen.click(ImgPattern.get("btnExitLogError"));
			testCase.addScreenShotCurrentStep("");
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * aplicarCambios. Da clic al boton de aplicar cambios y valida que exista el
	 * boton
	 * 
	 * @return
	 * @throws FindFailed
	 */
	public boolean aplicarCambios() throws FindFailed {
		boolean result;
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("aplicarCambios")) != null) {
			sk.screen.doubleClick(ImgPattern.get("aplicarCambios"));
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * clearAndExecute. Limpia y ejecuta consulta
	 * 
	 * @throws FindFailed
	 */
	public void clearAndExecute() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("btnClear")) != null) {
			sk.screen.click(ImgPattern.get("btnClear"));
		}

		if (sk.screen.exists(ImgPattern.get("checkrojo")) != null) {
			sk.screen.click(ImgPattern.get("checkrojo"));
			sk.screen.mouseMove(ImgPattern.get("checkrojo"));
		}
		u.hardWait(4);
	}

	/**
	 * Maximiza la ventana del navegador
	 *
	 * @author guerrero.vargas
	 */
	public void maximizar() {
		sk.screen.keyDown(Keys.WIN);
		sk.screen.keyDown(Keys.UP);
		sk.screen.keyUp(Keys.UP);
		sk.screen.keyUp(Keys.WIN);
	}

	/**
	 * Validar un patron a buscar, se evalua si existe (hace match, imagen de
	 * entrada contra imagen en pantalla)
	 *
	 * @param target
	 * @return True o False utilizado para hacer un assert
	 * @author JesusH4@hexaware.com
	 */
	public Boolean validatePattern(String target) {
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get(target)) != null) {

			return true;
		} else {
			testCase.addScreenShotCurrentStep(target);
			return false;
		}
	}

	/**
	 * Metodo para dar click en una imagen proporcionada
	 *
	 * @param target
	 * @throws FindFailed
	 * @author JesusH4@hexaware.com
	 */
	public void click(String target) throws FindFailed {
		sk.screen.click(ImgPattern.get(target));
		u.hardWait(1);
	}

	/**
	 * Metod utilizado para dar click en la caja de texto find dentro de la ventana
	 * de busqueda de planza, borra el valor que tiene el campo y captura la plaza a
	 * buscar
	 * 
	 * @param plaza Plaza que desea buscarse
	 * @throws FindFailed
	 * @author JesusH4@hexaware.com
	 */
	public void buscarPorPlazaEnListaDeValores(String plaza) throws FindFailed {
		click("plazaFind");
		nDelete(2);
		sk.screen.type(plaza);
	}

	/**
	 * Simula presionar la tecla delete, n numero de veces
	 * 
	 * @param n
	 */
	public void nDelete(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.keyDown(Keys.DELETE);
			sk.screen.keyUp(Keys.DELETE);
			x = x + 1;
		}
	}

	/**
	 * Ingresa el tipo de distribucion que se desea filtrar
	 *
	 * @param tipoDistribucion
	 * @throws FindFailed
	 */
	public void buscarPorTipoDistribucion(String tipoDistribucion) throws FindFailed {
		click("tipodistribucion");
		nDelete(3);
		sk.screen.type(tipoDistribucion);
	}

	/**
	 * Busca la imagen referente al filtro a aplicar, da clic, borra el texto e
	 * ingresa el criterio a buscar
	 *
	 * @param tipoFiltro
	 * @param valorFiltro
	 * @throws FindFailed
	 */
	public void buscarPor(String tipoFiltro, String valorFiltro) throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		click(tipoFiltro);
		nDelete(3);
		sk.screen.type(valorFiltro);
		nTAB(1);
	}

	/**
	 * verificarTextoRuta
	 * 
	 * @return
	 * @throws FrameworkException
	 */
	public String verificarTextoRuta() throws FrameworkException {
		ImgPattern = getPatternsImg(Folder);
		String campo = "RDM\\CambiosMasivos\\rutaFiltroText.png";
		return sk.getTextFromMaskedImageRelativePath(campo).replaceAll("[\\r\\n]", "");
	}

	/**
	 * verificarResultadoRuta
	 * 
	 * @return
	 * @throws FrameworkException
	 */
	public String verificarResultadoRuta() throws FrameworkException {
		ImgPattern = getPatternsImg(Folder);
		String campo = "RDM\\CambiosMasivos\\rutaResultText.png";
		return sk.getTextFromMaskedImageRelativePath(campo).replaceAll("[\\r\\n]", "");
	}

	/**
	 * Retorna el texto extraido del campo de dia de generacion pedido de la tabla
	 * de resultados de la primera fila
	 * 
	 * @return
	 * @throws FrameworkException
	 */
	public String verificarResultadoDiaGenDo() throws FrameworkException {
		ImgPattern = getPatternsImg(Folder);
		String campo = "RDM\\CambiosMasivos\\diaGenPedDoResult.png";
		return sk.getTextFromMaskedImageRelativePath(campo).replaceAll("[\\r\\n]", "");
	}

	/**
	 * Retorna el texto extraido del campo de dia de entrega pedido de la tabla de
	 * resultados de la primera fila
	 * 
	 * @return
	 * @throws FrameworkException
	 */
	public String verificarResultadoDiaEntLu() throws FrameworkException {
		ImgPattern = getPatternsImg(Folder);
		String campo = "RDM\\CambiosMasivos\\diaEntLuResult.png";
		return sk.getTextFromMaskedImageRelativePath(campo).replaceAll("[\\r\\n]", "");
	}

	/**
	 * buscarPorRuta. Filtra una busqueda por por ruta y plaza
	 * 
	 * @param plaza
	 * @param ruta
	 * @return
	 * @throws FindFailed
	 */
	public boolean buscarPorRuta(String plaza, String ruta) throws FindFailed {
		sk.screen.type(plaza);
		u.hardWait(4);
		ImgPattern = getPatternsImg(Folder);
		sk.screen.type(Key.TAB);
		u.hardWait(5);
		sk.screen.type(Key.TAB);
		u.hardWait(5);
		sk.screen.type(Key.TAB);
		u.hardWait(5);
		sk.screen.type(Key.TAB);
		u.hardWait(5);
		sk.screen.type(ruta);
		u.hardWait(4);
		ImgPattern = getPatternsImg(Folder);
		sk.screen.click(ImgPattern.get("checkrojo"));
		u.hardWait(6);
		testCase.addScreenShotCurrentStep(" ");
		return true;

	}

	/**
	 * Metodos de funcion para modulo PMO CARGA MASIVOS
	 *
	 * @throws FindFailed
	 * @author Josué Cruz
	 */
	public boolean clickMenuPmoCargaMasivos() throws FindFailed {
		try {
			maximizar();
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("spfun"));
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(" ");
			sk.screen.click(ImgPattern.get("ReportsCambiosMasivos"));
			u.hardWait(5);
			sk.screen.click(ImgPattern.get("MenuPMOCargaMasivos"));
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(15);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Metodo de apoyo para validar que se visualice la pantalla sin datos
	 *
	 * @return Se valida que la pantalla se encuentre sin información
	 * @author Josué Cruz
	 */
	public boolean validarPantallaVaciaCargaMasiva() {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("CargaMasivoInicialVacio")) != null) {
			u.hardWait(8);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metodo que ayuda a ejecutar una consulta
	 * 
	 * @author Josué Cruz
	 */
	public boolean clickExcecute() throws FindFailed {
		try {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("checkrojo"));
			u.hardWait(6);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Valida que existan informacion de carga
	 *
	 * @return Validacion de informacion de Carga para su posterior validacion.
	 * @author Josué Cruz
	 */
	public boolean validarCargaMasivaConDatos(){
		try {
			validatePattern("CargaMasivoConDatos");
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(3);
			sk.screen.click(ImgPattern.get("CargaMasivosScrollVertical"));
			u.hardWait(6);
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(3);
			sk.screen.click(ImgPattern.get("CargaMasivosScrollVertical"));
			u.hardWait(6);
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(3);
			sk.screen.click(ImgPattern.get("CargaMasivosScrollVertical"));
			u.hardWait(6);
			testCase.addScreenShotCurrentStep(" ");
			u.hardWait(3);
			sk.screen.click(ImgPattern.get("CargaMasivosScrollVertical"));
			u.hardWait(6);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	/**
	 * Valida que exista la opcion de "Respaldar Datos" en la sección del filtros
	 *
	 * @return Validacion de boto "Respaldar Datos" en la sección de filtro
	 * @author Josué Cruz
	 */
	public boolean validarOpcionRespaldarDatos() throws FindFailed {

		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("CamMasOptRespaldarDatos")) != null) {
			sk.screen.click(ImgPattern.get("CamMasOptRespaldarDatos"));
			u.hardWait(6);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Da click en el campo a escribir y presiona la tecla delete 2 veces, luego
	 * teclea el texto a ingresar
	 *
	 * @param image
	 * @param value
	 * @throws FindFailed
	 * @author JesusH4@hexaware.com
	 */
	public void typeInput(String image, String value) throws FindFailed {

		click(image);
		pressDeleteKey(2);
		typeInput(value);
		pressTabKey(1);
	}

	/**
	 * Carga la pagina de RDM para lanzar el aplicativo
	 * 
	 * @author JesusH4@hexaware.com
	 */
	public void cargaPaginaRdm(String a) {
		u.get(a);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "RDM");
		u.hardWait(10);
		maximizar();
	}

	/**
	 * Teclea el texto a ingresar
	 *
	 * @param value
	 * @throws FindFailed
	 * @author JesusH4@hexaware.com
	 */
	public void typeInput(String value) throws FindFailed {
		sk.screen.type(value);
	}

	/**
	 * Presiona la tecla abajo, sirve para moverse entre registros de una tabla
	 * 
	 * @author JesusH4@hexaware.com
	 */
	public void pressDownKey() {
		sk.screen.type(Keys.DOWN);
		u.hardWait(1);
	}

	/**
	 * Presiona la tecla abajo un "n" numero de veces
	 *
	 * @param n
	 * @author JesusH4@hexaware.com
	 */
	public void pressDownKey(int n) {
		int x = 1;
		while (x <= n) {
			pressDownKey();
			x = x + 1;
		}
	}

	/**
	 * Emula presionar la tecla TAB
	 * 
	 * @author JesusH4@hexaware.com
	 */
	public void pressTabKey() {
		sk.screen.type(Key.TAB);
	}

	/**
	 * Emula presionar la tecla TAB un numero "n" de veces
	 *
	 * @param n El numero de veces que quieres que presione la tecla TAB
	 * @author JesusH4@hexaware.com
	 */
	public void pressTabKey(int n) {
		int x = 1;
		while (x <= n) {
			pressTabKey();
			x = x + 1;
		}
	}

	/**
	 * Emula presionar la tecla Delete
	 * 
	 * @author JesusH4@hexaware.com
	 */
	public void pressDeleteKey() {
		sk.screen.type(Keys.DELETE);
	}

	/**
	 * Emula presionar la tecla Delete un numero "n" de veces
	 *
	 * @param n
	 * @author JesusH4@hexaware.com
	 */
	public void pressDeleteKey(int n) {
		int x = 1;
		while (x <= n) {
			pressDeleteKey();
			x = x + 1;
		}
	}

	/**
	 * Emula presionar la combinacion de teclas Shift + Tab
	 * 
	 * @author JesusH4@hexaware.com
	 */
	public void pressShiftTab() {
		sk.screen.type(Key.TAB, KeyModifier.SHIFT);
	}

	/**
	 * Selecciona la opcion Cambiar Dia Generacion
	 * 
	 * @author JesusH4@hexaware.com
	 * @throws FindFailed
	 */
	public void cambiarDiaGeneracion() throws FindFailed {
		click("cambiarDiasGeneracionEntrega");
		u.hardWait(1);
	}

	/**
	 * Da clic en el filtro Plaza, borra el contenido y escribe la plaza deseada
	 * 
	 * @author JesusH4@hexaware.com
	 * @param plaza
	 * @throws FindFailed
	 */
	public void buscarPlaza(String plaza) throws FindFailed {
		click("plaza");
		pressDeleteKey(3);
		typeInput(plaza);
		u.hardWait(1);
	}

	/**
	 * Da clic en el boton de limpiar pantalla
	 * 
	 * @author JesusH4@hexaware.com
	 * @throws FindFailed
	 */
	public void limpiarPantalla() throws FindFailed {
		click("clearButton");
		click("clearButton");
		u.hardWait(1);
	}

	/**
	 * Da clic en el filtro Ruta, borra el contenido y escribe la ruta deseada
	 * 
	 * @author JesusH4@hexaware.com
	 * @param ruta
	 * @throws FindFailed
	 */
	public void buscarRuta(String ruta) throws FindFailed {
		click("rutaFiltro");
		pressDeleteKey(3);
		typeInput(ruta);
		u.hardWait(1);
	}

	/**
	 * Da clic en el filtro Dia Generacion, borra el contenido y escribe el dia
	 * deseado
	 * 
	 * @author JesusH4@hexaware.com
	 * @param dia
	 * @throws FindFailed
	 */
	public void buscarDiaGen(String dia) throws FindFailed {
		click("diaGenPedFiltro");
		pressDeleteKey(3);
		typeInput(dia);
		u.hardWait(1);
	}

	/**
	 * Ingresa el dia de generacion del primer registro
	 * 
	 * @author JesusH4@hexaware.com
	 * @param dia
	 * @throws FindFailed
	 */
	public void ingresarDiaGen(String dia) throws FindFailed {
		click("diaGenPedInputBox");
		typeInput(dia);
		u.hardWait(1);
	}

	/**
	 * Ingresa el dia de ventana de entrega del primer registro
	 * 
	 * @author JesusH4@hexaware.com
	 * @param dia
	 * @throws FindFailed
	 */
	public void ingresarDiaVent(String dia) throws FindFailed {
		click("diaVentEntInputBox");
		typeInput(dia);
		u.hardWait(1);
	}

	/**
	 * Da clic en el boton Select All
	 * 
	 * @author JesusH4@hexaware.com
	 * @throws FindFailed
	 */
	public void selectAll() throws FindFailed {
		click("selectAll");
		u.hardWait(1);
	}

	/**
	 * Da clic en el boton de Aplicar Cambios
	 * 
	 * @author JesusH4@hexaware.com
	 * @throws FindFailed
	 */
	public void aplicarCambio() throws FindFailed {
		click("aplicarCambios");
		u.hardWait(1);
	}

	/**
	 * Da clic en el boton ejecutar
	 * 
	 * @author JesusH4@hexaware.com
	 * @throws FindFailed
	 */
	public void ejecutar() throws FindFailed {
		click("executeButton");
		u.hardWait(8);
		u.hardWait(1);
	}

	/**
	 * Da clic en el boton Yes para confirmar los cambios
	 * 
	 * @author JesusH4@hexaware.com
	 * @throws FindFailed
	 */
	public void confirmarCambios() throws FindFailed {
		click("mensajeYesBoton");
		u.hardWait(1);
	}

	/**
	 * Da clic en el mensaje de confirmacion de cambios aplicados y da clic en el
	 * boton Yes
	 * 
	 * @author JesusH4@hexaware.com
	 * @throws FindFailed
	 */
	public void cambiosAplicados() throws FindFailed {
		click("mensajeConfirmacionDeCambios");
		click("botonYesForms");
		u.hardWait(1);
	}

	/**
	 * Da clic al boton Tiendas PMO
	 * 
	 * @author JesusH4@hexaware.com
	 * @throws FindFailed
	 */
	public void tiendasPMO() throws FindFailed {
		click("tiendasPmo");
		u.hardWait(5);
	}

	/**
	 * Da clic en la opcion Eliminar Dias Generacion
	 * 
	 * @author JesusH4@hexaware.com
	 * @throws FindFailed
	 */
	public void eliminarDiasGen() throws FindFailed {
		click("eliminarDiasGeneracion");
		u.hardWait(1);
	}

	/**
	 * Realizar por medio de los filtros "Plaza" y "Ruta" y seleccionar la opcion de
	 * "Respaldar Datos"
	 *
	 * @return Permite mandar a llamar la opcion de capturar "Nombre Proyecto"
	 * @author Josué Cruz
	 */
	public boolean buscarPorPlazaRutaYSelecRespaldarDatos(String plaza, String ruta) throws FindFailed {
		try {
			sk.screen.type(plaza);
			u.hardWait(4);
			this.nTAB(4);
			u.hardWait(4);
			sk.screen.type(ruta);
			u.hardWait(4);
			validarOpcionRespaldarDatos();
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("checkrojo"));
			u.hardWait(6);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Valida que existan los botones "Select All" y "Respaldar Datos"
	 *
	 * @return Validacion de botones existentes "Select All" y "Respaldar Datos"
	 * @author Josué Cruz
	 */
	public boolean validarBotonRespaldarDatos() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("CambiosMasivosSelectAll")) != null
				&& sk.screen.exists(ImgPattern.get("CambiosMasivosRespaldarDatos")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida que existan los botones "Select All" y "Respaldar Datos"
	 *
	 * @return Validacion de botones existentes "Select All" y "Respaldar Datos"
	 * @author Josué Cruz
	 */
	public boolean clicBotonRespaldarDatos() throws FindFailed {
		try {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.click(ImgPattern.get("CambiosMasivosSelectAll"));
			u.hardWait(4);
			sk.screen.click(ImgPattern.get("CambiosMasivosRespaldarDatos"));
			u.hardWait(4);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Valida que muestre pantalla "Nombre Proyecto"
	 *
	 * @return Valida que existe pantalla "Nombre Proyecto"
	 * @author Josué Cruz
	 */
	public boolean validarVentanaNombreProyecto() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("CambiosMasivosRespladarNombreProyectoVentana")) != null
				&& sk.screen.exists(ImgPattern.get("CambiosMasivosRespladarNombreProyectoOK")) != null
				&& sk.screen.exists(ImgPattern.get("CambiosMasivosRespladarNombreProyectoEXIT")) != null
				&& sk.screen.exists(ImgPattern.get("CambiosMasivosRespladarNombreProyecto")) != null) {
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Capturar nombre del proyecto y guardarlo
	 *
	 * @return Permitira la captura y guardado del respaldo
	 * @author Josué Cruz
	 */
	public boolean capturarNombreProyecto(String nombreProyecto) throws FindFailed {
		try {
			ImgPattern = getPatternsImg(Folder);
			sk.screen.type(nombreProyecto);
			u.hardWait(4);
			sk.screen.click(ImgPattern.get("CambiosMasivosRespladarNombreProyectoOK"));
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Validar la confirmacion del guardado del proyecto
	 *
	 * @return Permitira validar la confirmacion del guardado del proyecto
	 * @author Josué Cruz
	 */
	public boolean validarConfirmacionRespaldo() throws FindFailed {
		ImgPattern = getPatternsImg(Folder);
		if (sk.screen.exists(ImgPattern.get("CambiosMasivosRespladarAplicar")) != null
				&& sk.screen.exists(ImgPattern.get("CambiosMasivosRespladarAplicarYES")) != null) {
			sk.screen.click(ImgPattern.get("CambiosMasivosRespladarAplicarYES"));
			u.hardWait(4);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		}
		if (sk.screen.exists(ImgPattern.get("CambiosMasivosRespladarRealizado")) != null
				&& sk.screen.exists(ImgPattern.get("CambiosMasivosRespladarRealizadoYES")) != null) {
			sk.screen.click(ImgPattern.get("CambiosMasivosRespladarRealizadoYES"));
			u.hardWait(4);
			testCase.addScreenShotCurrentStep(" ");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Hace un highlight a un elemento proporcionado
	 * 
	 * @param target
	 * @author JesusH4@hexaware.com
	 */
	public void highlightOn(String target) {
		sk.screen.exists(ImgPattern.get(target)).highlight("red");
		u.hardWait(1);
	}

	/**
	 * Desactiva todos los highlights de Sikulix
	 * 
	 * @author JesusH4@hexaware.com
	 */
	public void highlightOff() {
		Screen.highlightAllOff();
	}

	/**
	 * Hace un highlight al elemento proporcionado, ademas de agregar una captura de
	 * pantalla al caso de prueba
	 * 
	 * @param imagen
	 * @throws FindFailed
	 * @author JesusH4@hexaware.com
	 */
	public void highlight(String imagen) throws FindFailed {
		highlightOn(imagen);
		testCase.addScreenShotCurrentStep(imagen);
		highlightOff();
	}

	/**
	 * Hace un highlight al elemento proporcionado y da un clic, ademas de agregar
	 * una captura de pantalla al caso de prueba
	 * 
	 * @param imagen
	 * @throws FindFailed
	 * @author JesusH4@hexaware.com
	 */
	public void clickHighlight(String imagen) throws FindFailed {
		highlight(imagen);
		click(imagen);
	}

	/**
	 * Selecciona el filtro de Dia Entrega, borra el texto y escribe el dia
	 * proporcionado
	 * 
	 * @param dia
	 * @throws FindFailed
	 * @author JesusH4@hexaware.com
	 */
	public void buscarDiaEnt(String dia) throws FindFailed {
		click("diaEntFiltro");
		pressDeleteKey(3);
		typeInput(dia);
		u.hardWait(1);
	}

	/**
	 * Presiona la tecla delete n veces, luego teclea el texto a ingresar
	 *
	 * @param text
	 * @param n
	 * @throws FindFailed
	 * @author JesusH4@hexaware.com
	 */
	public void typeInput(String text, Integer n) throws FindFailed {
		pressDeleteKey(n);
		typeInput(text);
	}

	/**
	 * Metodo para dar click en una imagen proporcionada n numero de veces
	 *
	 * @param target imagen a la que se le dara clic
	 * @param n      numero de veces que se necesita hacer click
	 * @throws FindFailed
	 * @author JesusH4@hexaware.com
	 */
	public void click(String target, int n) throws FindFailed {

		int x = 1;
		while (x <= n) {
			click(target);
			x = x + 1;
		}

	}

}
