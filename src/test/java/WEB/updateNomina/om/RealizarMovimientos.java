package WEB.updateNomina.om;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

/**
 * Clase con Elementos y Metodos provenientes de la sección Realizar Movimientos de SADEL y alguna subsecciones derivadas de esta."
 *
 * @author Mario Alberto De León Rodríguez
 * @version 1.0
 * 
 */
public class RealizarMovimientos{
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	private MetodosExtra me;
	private UpdateNomina updNom;
	private SikuliSADEL sikuliSADEL;
	private Empleados empleados;

	//Menu
	By imgRealizarMovimientos = By.xpath("/html/body/div[2]/table/tbody/tr[2]/td[2]/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td[3]/a/img");
	
	//Elementos Seccion Realizar Movimiento
	By filtroTipoDeMovimiento = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/form/table/tbody/tr/td[2]/div/table/tbody/tr[1]/td/table/tbody/tr/td[2]/select");
	By optionAlta = By.xpath("//select[@id='idMovementType']/option[text()='Alta']");
	By optionBaja = By.xpath("//*[@id=\"idMovementType\"]/option[3]");
	By optionCambioDeSueldo = By.xpath("//*[@id=\"idMovementType\"]/option[4]");
	By filtroSubtipoDeMovimiento = By.id("idMovementSubtype");
	By imgSeleccionar = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/form/table/tbody/tr/td[2]/div/table/tbody/tr[1]/td/table/tbody/tr/td[5]/a/img");
	
	By aLupaEmpleados = By.xpath("//img[@name='Image11' and @alt='Buscar' and @onclick='displaySearch();']");
	
	//Elementos Captura de Baja del Empleado
	By inputFechaDeBaja = By.xpath("//input[@id='endDateJob']");
	By inputRadioBotonDeseaFiniquito = By.xpath("//input[@id='rdBajaFiniquito']");
	By selectMotivoDeBaja = By.id("motivoBaja");
	By selectFeedback = By.id("feedback");
	By textareaComentarios = By.id("comments");
	By imgBotonGuardarBaja = By.xpath("//img[@name='Image035' and @alt='Guardar']");
	
	//Elementos Captura de Cambio de Sueldo del Empleado
	By inputSueldoNuevo = By.xpath("//input[@name='salary_changeSayary' and @onchange='clearConfirmSalary(); validarSueldo(this);']");
	By inputEfectivoAPartirDe = By.id("dateNewSalary");
	
	//Elementos Captura de Puesto Nuevo del Empleado
	By selectPuestoNuevo = By.id("idNewPosition");
	
	By inputSueldoBrutoDiarioNuevo = By.xpath("//*[@name='salary_changePosition']");
	By inputValesDeDespensaNuevo = By.xpath("//*[@id='salary_valesDespensaPosition']");
	By inputFechaDeMovimiento = By.xpath("//*[@id='dateNewPosition']");
	
	//Elementos Captura Alta del Empleado 
	//Informacion Basica
	By selectPlaza = By.xpath("//*[@id='idPlaza']");
	By option10DCUColima = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[1]/td[2]/select/option[16]");
	By selectTiendaLider = By.xpath("//*[@id='idStore']");

	By option20NovCLA = By.xpath("//option[@value='Image035' and text()='20 de Noviembre CLA']");
		//Numero de Seguro Social
	By inputNSS = By.xpath("//*[@id='nss']");
	By imgBotonBuscarNSS = By.xpath("//*[@alt='Buscar']");
		//Nombres
	By inputNombres = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[2]/td[2]/input");
	By inputApellidoPaterno = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[3]/td[2]/input");
	By inputApellidoMaterno = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[4]/td[2]/input");
		//Genero
	By selectGenero = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[5]/td[2]/select");
	By optionMasculino = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[5]/td[2]/select/option[2]");
	By optionFemenino = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[5]/td[2]/select/option[3]");
		//Lugar de Nacimiento
	By inputFechaDeNacimiento = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[6]/td[2]/input");
	By selectPaísDeNacimiento = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[7]/td[2]/select[1]");
	By optionMexico = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[7]/td[2]/select[1]/option[149]");
	By selectEstadoDeNacimiento = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[7]/td[2]/select[2]");		
		//CURP
	By inputCURP = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[10]/td[2]/input");
		//Información Bancaria
	By inputNumeroDeTarjeta = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[14]/td[2]/input");
	By inputUtilizarTarjetaAnterior = By.xpath("tipoCambioTarj");
	By inputCheckboxNoPagoTransferencia = By.xpath("//*[@id='noFondeo']");
		//Jornada Laboral
	//By selectJornadaLaboral = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[16]/td[4]/select");
	By selectJornadaLaboral = By.id("idWorkingDay");
	//By optionTrabaja6DiasDescansa1 = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[16]/td[4]/select/option[2]");
	//By optionTrabaja1Dia = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[16]/td[4]/select/option[8]");
	By optionTrabaja1Dia = By.xpath("//*[@id=\"idWorkingDay\"]/option[8]");
	//By inputCheckboxLunes = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[17]/td[3]/table/tbody/tr/td/table/tbody/tr[2]/td[2]/input");
	By inputCheckboxLunes = By.xpath("//*[@id='false' and @name='lunes']");
	//By inputCheckboxDomingo = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[17]/td[3]/table/tbody/tr/td/table/tbody/tr[8]/td[2]/input");
	//By selectHorariosDesde = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[17]/td[3]/table/tbody/tr/td/table/tbody/tr[2]/td[3]/select");
	By selectHorariosDesde = By.id("hourFrom0");
	By option0HorariosDesde = By.xpath("//*[@id='hourFrom0']/option[2]");
	By selectHorariosHasta = By.id("hourTo0");
	By option16HorariosHasta = By.xpath("//*[@id='hourTo0']/option[18]");
		//PUESTO
	By imgBotonPuesto = By.xpath("//*[@id='Image8']");
	
		//Salario Diario Bruto
	By inputSalarioDiarioBruto = By.id("salary");
	By inputValesDeDespensaDiarios = By.id("valeDespensa");
	By inputFechaDeingresoALaborar = By.id("hireDate");
		//Guardar
	By imgBotonGuardarInformacionBasica = By.xpath("//*[@id='Image35' and @alt='Guardar']");
	
	//Autorizacion de movimientos
	By selectTienda = By.xpath("//*[@id='idStore']");
	By inputCheckboxAutorizar = By.xpath("//*[@id='autorizado_0']");
	By imgBotonGuardarAutorizacionCambioDeSueldo = By.xpath("//*[@id='Image34' and @alt='Guardar']");
	By imgBotonGuardarAutorizacionBaja = By.xpath("//*[@id='Image34' and @alt='Aceptar']");
	
	SikuliUtil sk = new SikuliUtil();
    String pathImg = sk.PatternPath("WEB\\"+"SADEL");
    Pattern pathimg = new Pattern();
    ArrayList<String> ListImage = new ArrayList<String>();
    HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
    ArrayList<Pattern> patterns = new ArrayList<Pattern>();
    File Fol = new File(pathImg);
    File Folder = new File(pathImg);
    
	/**
	 * Constructor.
	 * 
	 * @param u
	 * @param testCase
	 * @author Mario A. De Leon Rdz.
	 */
	public RealizarMovimientos(SeleniumUtil u, TestCase testCase) {
	    super(); 
	    this.u = u; // Asigna el objeto SeleniumUtil pasado como parámetro a la variable de instancia
	    this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60)); // Inicializa WebDriverWait con el driver de SeleniumUtil y un timeout de 60 segundos
	    this.testCase = testCase; // Asigna el objeto TestCase pasado como parámetro a la variable de instancia
		this.updNom = new UpdateNomina(u, testCase);
		this.sikuliSADEL = new SikuliSADEL(u, testCase, sk, pathimg, ListImage, ImgPatter, patterns, Fol);
		this.empleados = new Empleados(u, testCase);
	}
	
	/**
	 * M�todo para hacer click en el bot�n Realizar Movimientos del men� de SADEL (visible en todo el aplicativo)
	 * @author Mario A. De Leon Rdz.
	 */
	public void SeleccionarBotonRealizarMovimiento() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(imgRealizarMovimientos));
			testCase.addScreenShotCurrentStep(u, "imgRealizarMovimientos");
			u.click(imgRealizarMovimientos);
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en enlace para ingresar a boton Realizar Movimientos");
		}
	}
	
	/**
	 * Metodo para seleccionar un tipo de movimiento y presionar el bot�n seleccionar
	 * 
	 * @param mov (Ingresar un solo movimiento "Alta", "Baja" o "Cambio de sueldo")
	 * @author Mario A. De Leon Rdz.
	 */
	public void SeleccionarMovimiento(String mov) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(filtroTipoDeMovimiento));
			u.click(filtroTipoDeMovimiento);
			WebElement optionElement;
			switch (mov) {
			case "Alta":
				updNom.robotKeyDown(1);
				testCase.addScreenShotCurrentStep(u, "seleccionarAlta");
                break;
			case "Baja":

				updNom.robotKeyDown(2);
                testCase.addScreenShotCurrentStep(u, "seleccionarBaja");
                break;
			case "Cambio de sueldo":

				updNom.robotKeyDown(3);
                testCase.addScreenShotCurrentStep(u, "seleccionarCambioDeSueldo");
                break;
			default:
                throw new AssertionError("Movimiento no reconocido: " + mov);
			}
			wait.until(ExpectedConditions.elementToBeClickable(imgSeleccionar));
	        u.click(imgSeleccionar);
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en el filtro Tipo de Movimiento");
		}
	}
	
	/**
	 * M�todo para seleccionar el movimiento tipo Alta y presionar el bot�n seleccionar
	 * @author Mario A. De Leon Rdz.
	 */
	//Metodo para ATC_FT_001
	public void SeleccionarMovimientoTipoAlta() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(filtroTipoDeMovimiento));
			System.out.println("termina wait");
			u.click(filtroTipoDeMovimiento);
			System.out.println("click en tipo de movimiento");

			testCase.addScreenShotCurrentStep(u, "seleccionarAlta");

			System.out.println("robot");
			Robot robot;
			try {
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_DOWN);
				robot.keyRelease(KeyEvent.VK_DOWN);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			} catch (AWTException e) {
				e.printStackTrace();
			}
			testCase.addScreenShotCurrentStep(u, "seleccionarAlta2");
			u.click(imgSeleccionar);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en el filtro Tipo de Movimiento");
		}
	}
	
	/**
	 * Metodo para seleccionar el movimiento tipo Baja y presionar el boton seleccionar
	 * @author Mario A. De Leon Rdz.
	 */
	//Metodo para ATC_FT_002
	public void SeleccionarMovimientoTipoBaja() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(filtroTipoDeMovimiento));
			System.out.println("empieza SeleccionarMovimientoTipoBaja");
			u.click(filtroTipoDeMovimiento);
			System.out.println("click en tipo de movimiento");
			testCase.addScreenShotCurrentStep("movimiento baja");
			System.out.println("inicia robot");
			//Inputs
			Robot robot;
			try {
				robot = new Robot();
				robot.keyPress(KeyEvent.VK_DOWN);
				robot.keyRelease(KeyEvent.VK_DOWN);
				robot.keyPress(KeyEvent.VK_DOWN);
				robot.keyRelease(KeyEvent.VK_DOWN);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);

			} catch (AWTException e) {
				e.printStackTrace();
			}
			System.out.println("termina robot");
			testCase.addScreenShotCurrentStep("movimiento baja 2");
			u.highLight(imgSeleccionar);
			u.click(imgSeleccionar);
			System.out.println("esperando lupa");
			//esperar a que cargue la pagina y se muestre enlace empleados
			wait.until(ExpectedConditions.elementToBeClickable(aLupaEmpleados));
			System.out.println("Se encontró lupa empleados");
			u.click(aLupaEmpleados);
			System.out.println("Click en lupa empleados");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en el filtro Tipo de Movimiento");
		}
	}
	
	/**
	 * Metodo para seleccionar el movimiento tipo Cambio de sueldo y presionar el boton seleccionar
	 * @author Mario A. De Leon Rdz.
	 */
	//Metodo para ATC_FT_003
	public void SeleccionarMovimientoCambioDeSueldo() {
		try {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(filtroTipoDeMovimiento));
				System.out.println("empieza SeleccionarMovimientoTipoBaja");
				u.click(filtroTipoDeMovimiento);
				System.out.println("click en tipo de movimiento");
				System.out.println("inicia robot");
				//Inputs
				Robot robot;
				try {
					robot = new Robot();
					//1
					robot.keyPress(KeyEvent.VK_DOWN);
					robot.keyRelease(KeyEvent.VK_DOWN);
					//2
					robot.keyPress(KeyEvent.VK_DOWN);
					robot.keyRelease(KeyEvent.VK_DOWN);
					//3
					robot.keyPress(KeyEvent.VK_DOWN);
					robot.keyRelease(KeyEvent.VK_DOWN);
					//ENTER
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);

				} catch (AWTException e) {
					e.printStackTrace();
				}
				System.out.println("termina robot");
				u.highLight(imgSeleccionar);
				testCase.addScreenShotCurrentStep("movimiento seleccionado");
				u.click(imgSeleccionar);
				System.out.println("esperando lupa");
				//esperar a que cargue la pagina y se muestre enlace empleados
				wait.until(ExpectedConditions.elementToBeClickable(aLupaEmpleados));
				System.out.println("Se encontra lupa empleados");
				testCase.addScreenShotCurrentStep("se muestra empleado");
				u.click(aLupaEmpleados);
				System.out.println("Click en lupa empleados");
			} catch (Exception e) {
				System.out.println(" -> " + e);
				this.u.close();
				throw new AssertionError("No se pudo dar click en el filtro Tipo de Movimiento");
			}
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en el filtro Tipo de Movimiento");
		}
	}
	
	/**
	 * Selecciona el tipo de movimiento "Cambio de Puesto" en la interfaz.
	 * 
	 * Utiliza un `robotKeyDown` para simular la selecci�n en la interfaz.
	 * Captura una captura de pantalla del paso completado.
	 * 
	 * @author Mario A. De Le�n Rdz.
	 */
	public void SeleccionarMovimientoTipoCambioDePuesto(){
			try {
				wait.until(ExpectedConditions.elementToBeClickable(filtroTipoDeMovimiento));
				System.out.println("empieza SeleccionarMovimientoTipoCambioDePuesto");
				u.click(filtroTipoDeMovimiento);
				System.out.println("click en tipo de movimiento");
				System.out.println("inicia robot");
				//Inputs
				updNom.robotKeyDown(4);
				testCase.addScreenShotCurrentStep("se selecciona cambio de puesto");
			} catch (Exception e) {
				System.out.println(" -> " + e);
				this.u.close();
				throw new AssertionError("No se pudo dar click en el filtro Tipo de Movimiento");
			}
	}
	/**
	 * Selecciona el subtipo de movimiento "Cambio de Sueldo" en la interfaz.
	 * 
	 * Tambi�n realiza un click en la imagen para continuar con la selecci�n
	 * de empleados, tomando capturas de pantalla durante el proceso.
	 * 
	 * @author Mario A. De Le�n Rdz.
	 */
	public void SeleccionarMovimientoSubtipoCambioDeSueldo(){
		try {
			wait.until(ExpectedConditions.elementToBeClickable(filtroSubtipoDeMovimiento));
			//System.out.println("empieza SeleccionarMovimientoSubtipoCambioDeSueldo");
			u.click(filtroSubtipoDeMovimiento);
			System.out.println("click en subtipo de movimiento");
			updNom.robotKeyDown(2);
			testCase.addScreenShotCurrentStep("se selecciona cambio de sueldo");
			u.click(imgSeleccionar);
			
			//esperar a que cargue la pagina y se muestre enlace empleados
			
			wait.until(ExpectedConditions.elementToBeClickable(aLupaEmpleados));
			u.click(aLupaEmpleados);
			System.out.println("Click en lupa empleados");
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en el filtro Tipo de Movimiento");
		}
	}
	
	/**
	 * Metodo para capturar los datos necesarios para realizar el Alta de empleado
	 * @param plaza es el texto identico del nombre de la plaza a la que se quiere asignar al empleado, debe de ser identico al nombre mostrado en SADEL
	 * @param NSS debe de ser el numero de seguro social del empleado
	 * @param fechaActual debe de estar escrito por numeros consecutivos DDMMYYYY ejemplo->27082024
	 * @param tiendaPorAsignar debe de ser la tienda en las que se quiere dar de alta al empleado
	 * @param salarioDiario debe de ser el salario 
	 * @author Mario A. De Leon Rdz.
	 */
	//Metodo necesario para Alta de Empleado
	public void CapturarDatosDelEmpleado(String plaza, String NSS, String fechaActual, String tiendaPorAsignar, String salarioDiario) {
		try {
			System.out.println("Empieza captura de datos para alta del empleado");
			//wait.until(ExpectedConditions.elementToBeClickable(selectPlaza));
			testCase.addScreenShotCurrentStep(u, "selectPlaza");
			
			//Buscar empleado por Numero de Seguro Social
			u.sendKeys(inputNSS, NSS);
			u.click(imgBotonBuscarNSS);
			//Se abre ventana
			sikuliSADEL.FiltroVentanaAltaNSSEmpleado(Folder);
			System.out.println("NSS capturado");
			 //se anade este wait porque despues de presionar el boton tiene que cargar toda la pagina de nuevo
			//u.waitForLoadPage();
			u.hardWait(1);
			empleados.SeleccionarPestanaDatosGenerales();
	    	empleados.ModificarDatoDelEmpleadoEstudios();
	    	empleados.SeleccionarPestanaInformacionBasica();
	   
			u.click(selectPlaza);
			u.sendKeys(selectPlaza, plaza);
			
			//LLenado Tienda Lider
			wait.until(ExpectedConditions.elementToBeClickable(inputCheckboxNoPagoTransferencia));
			u.click(selectTiendaLider);
//			u.click(option20NovCLA);
		

			u.sendKeys(selectTiendaLider, tiendaPorAsignar);
			System.out.println("Tienda capturada");
			u.hardWait(1);
			//Seleccionar Utilizar Tarjeta Anterior, cambiar el foco a la alerta y aceptar alerta
			u.click(inputCheckboxNoPagoTransferencia);
			//u.getDriver().switchTo().alert().accept();
			updNom.AceptarTodasLasAlertas();
			System.out.println("Se selecciona No pago por Transferencia Bancaria");
			
			//Llenar Jornada Laboral
			u.click(selectJornadaLaboral);
			//u.click(optionTrabaja1Dia);
			updNom.robotKeyDown(7);
			u.click(inputCheckboxLunes);
			u.click(selectHorariosDesde);

			updNom.robotKeyDown(2);
			u.click(selectHorariosHasta);

			updNom.robotKeyDown(4);
			System.out.println("Jornada Laboral capturada");

			u.click(imgBotonPuesto);
			sikuliSADEL.FiltroVentanaAltaPuestoEmpleado(Folder);
			
			//Salario Diario, vales de despensa y Fecha de ingreso a laborar
			u.waitForLoadPage();
			u.sendKeys(inputValesDeDespensaDiarios, "0.0");
			System.out.println("Vales de despensa capturado");
			
			u.sendKeys(inputSalarioDiarioBruto, salarioDiario);
			u.click(inputValesDeDespensaDiarios);//click afuera del campo Salario Diario para activar ventana
			//se abre ventana en Internet Explorer
			sikuliSADEL.FiltroVentanaSalarioDiarioBruto(Folder);
			System.out.println("Salario Diario capturado");
			
			u.sendKeys(inputFechaDeingresoALaborar, fechaActual);
			System.out.println("Fecha de ingreso capturado");
			testCase.addScreenShotCurrentStep(u, "Datos Llenados");
	
			u.click(imgBotonGuardarInformacionBasica);
			updNom.AceptarTodasLasAlertas();
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			//this.u.close();
			throw new AssertionError("No se pudo llenar los datos del empleado correctamente");
		}
	}

	/**
	 * Metodo para capturar los datos necesarios para realizar la Baja de empleado
	 * @param fechaActual debe de estar escrito por numeros consecutivos DDMMYYYY ejemplo->27082024
	 * @author Mario A. De Leon Rdz.
	 */
	public void CapturarBajaDelEmpleado(String fechaActual) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputFechaDeBaja));
			u.sendKeys(inputFechaDeBaja, fechaActual); //siempre hay que modificar a la fecha actual
			u.click(inputRadioBotonDeseaFiniquito);
			u.click(selectMotivoDeBaja);
			System.out.println("click en select Motivo de Baja");
			//Input
			updNom.robotKeyDown(2);

			u.click(selectFeedback);
			System.out.println("click en select feedback");
			//Input
			updNom.robotKeyDown(2);

			u.sendKeys(textareaComentarios, "prueba automatizada");
			System.out.println("Texto escrito en campo comentarios");
			u.click(imgBotonGuardarBaja);
			System.out.println("Click en guardar");
			updNom.AceptarTodasLasAlertas(); //No descomentar al menos de que se requiera
		} catch (Exception e) {
	        System.out.println(" -> " + e);
	        throw new AssertionError("No se pudo realizar la baja del empleado correctamente");
	    }
	}

	/**
	 * Metodo para capturar los datos necesarios para realizar el movimiento Cambio de sueldo del empleado.
	 *
	 * @author Mario A. De Leon Rdz.
	 */
	public void CapturarSueldoDelEmpleado() {
		try {
		wait.until(ExpectedConditions.elementToBeClickable(inputSueldoNuevo));
		u.sendKeys(inputSueldoNuevo, "255");
		u.sendKeys(inputEfectivoAPartirDe, "01082024");
		testCase.addScreenShotCurrentStep(u, "Datos Llenados");
		u.click(imgBotonGuardarBaja);
		sikuliSADEL.FiltroVentanaSalarioDiarioBruto(Folder);
		updNom.AceptarTodasLasAlertas();
		} catch (Exception e) {
			System.out.println(" -> " + e);
			//this.u.close();
			throw new AssertionError("No se acepto el cambio de sueldo");
		}
		
	}

	/**
	 * Metodo para capturar el cambio de puesto y sueldo del empleado.
	 * Se selecciona el nuevo puesto, se captura el nuevo sueldo, vales de despensa y fecha del movimiento.
	 * @param sueldoNuevo el nuevo sueldo bruto diario del empleado
	 * @param fechaActual la fecha en la que se realiza el cambio, escrita en formato DDMMYYYY
	 * @author Mario A. De Leon Rdz.
	 */
	public void CapturarPuestoYSueldoNuevoDelEmpleado(String sueldoNuevo, String fechaActual) {
		try {
			//CAMBIO DE PUESTO
			wait.until(ExpectedConditions.elementToBeClickable(selectPuestoNuevo));
			u.click(selectPuestoNuevo);
			System.out.println("click en select Puesto Nuevo");
			sikuliSADEL.FiltroVentanaCambioDePuestoEmpleado(Folder); //OJO!!!
			//CAMBIO DE SUELDO
			u.sendKeys(inputSueldoBrutoDiarioNuevo, sueldoNuevo);
			u.sendKeys(inputValesDeDespensaNuevo , "0.0");
			u.sendKeys(inputFechaDeMovimiento, fechaActual);//mover siempre a la fecha actual
			u.click(imgBotonGuardarBaja);
			sikuliSADEL.FiltroVentanaSalarioDiarioBruto(Folder);
			u.hardWait(1);
			updNom.AceptarTodasLasAlertas();
		} catch (Exception e) {
			System.out.println(" -> " + e);
			//this.u.close();
			throw new AssertionError("No se pudo capturar el puesto");
		}
		 
	}

	/**
	 * Metodo para autorizar la baja del empleado.
	 * Se navega a la p�gina de autorizaci�n de baja, se selecciona la tienda, se autoriza la baja y se guarda la informaci�n.
	 * @param tiendaDelEmpleado nombre de la tienda en la que el empleado est� registrado
	 * @author Mario A. De Leon Rdz.
	 */
	public void autorizarBajaDelEmpleado(String tiendaDelEmpleado) {
		try {
			u.get("http://fcowlq06.femcom.net:8000/sadelnom/authorizeDismissal.do");
			wait.until(ExpectedConditions.elementToBeClickable(selectTienda));
			u.click(selectTienda);
			// updNom.robotKeyDown(42); // esto deber�a de seleccionar la tienda Del Bosque HMO
			u.sendKeys(selectTienda, tiendaDelEmpleado);
			wait.until(ExpectedConditions.elementToBeClickable(inputCheckboxAutorizar));
			u.click(inputCheckboxAutorizar);
			testCase.addScreenShotCurrentStep(u, "Datos Llenados");
			u.click(imgBotonGuardarAutorizacionBaja);
			testCase.addScreenShotCurrentStep(u, "Datos Llenados");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo autorizar la baja del empleado");
		}
	}

	/**
	 * Metodo para autorizar el cambio de sueldo del empleado.
	 * Se navega a la p�gina de autorizaci�n de cambio de sueldo, se selecciona la tienda, se autoriza el cambio y se guarda la informaci�n.
	 * @param tiendaDelEmpleado nombre de la tienda en la que el empleado est� registrado
	 * @author Mario A. De Leon Rdz.
	 */
	public void autorizarCambioDeSueldoDelEmpleado(String tiendaDelEmpleado) {
		try {
			u.get("http://fcowlq06.femcom.net:8000/sadelnom/authorizeSalaryChange.do");
			wait.until(ExpectedConditions.elementToBeClickable(selectTienda));
			u.click(selectTienda);
			// updNom.robotKeyDown(42); // esto deber�a de seleccionar la tienda Del Bosque HMO
			// updNom.robotKeyDown(52);
			u.sendKeys(selectTienda, tiendaDelEmpleado);
			wait.until(ExpectedConditions.elementToBeClickable(inputCheckboxAutorizar));
			u.click(inputCheckboxAutorizar);
			testCase.addScreenShotCurrentStep(u, "Datos Llenados");
			u.click(imgBotonGuardarAutorizacionCambioDeSueldo);
			testCase.addScreenShotCurrentStep(u, "Datos Llenados");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo autorizar el cambio de sueldo del empleado");
		}
	}
	
} 