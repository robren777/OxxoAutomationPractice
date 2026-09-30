package WEB.updateNomina.om;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;
//import utils.AtributosLogisticos.PortalApps;

public class Empleados{
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	private UpdateNomina updNom;
	//Men� SADEL
	By imgEmpleados = By.xpath("//*[@alt='Empleados']");
	
	//Elementos Secci�n Empleados (Buscar/Editar Empleados)
	By imgBotonBuscarEmpleado = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/form/div/table/tbody/tr/td/table/tbody/tr/td[2]/table[1]/tbody/tr[2]/td/a/img");
	By selectPlaza = By.xpath("//*[@id='plazaId']");
	By optionHermosilloSur = By.xpath("//*[@id='plazaId']/option[3]");

	By selectTienda = By.xpath("//*[@id='storeId']");
	By optionTiendaZapataHMO = By.xpath("//*[@value='50HZA']");
	By optionTiendaDelBosqueHMO = By.xpath("//*[@value='50BWQ']");

	By inputBuscarPorNSS = By.xpath("//*[@id='nss']");

	//By inputRadioEmpleado1 = By.xpath("//*[@id='dataGridPrincipal']/tbody/tr[1]/td[1]/input");
	By inputRadioEmpleado1 = By.xpath("//*[@id='dataGridPrincipal']/tbody/tr/td[1]/input");
	By inputRadioEmpleado2 = By.xpath("//*[@value='1168020--92412']");
	By inputRadioEmpleadoAnaIseth = By.xpath("//*[@value='1354711--750727']");
	By imgBotonSeleccionarEmpleado = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/form/div/table/tbody/tr/td/table/tbody/tr/td[2]/table[2]/tbody/tr[2]/td/a/img");
	
	//Pesta�a Informaci�n B�sica
		//CURP
	By inputCURP = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[10]/td[2]/input");
		//Informaci�n Bancaria
	By inputNumeroDeTarjeta = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[14]/td[2]/input");
	By inputUtilizarTarjetaAnterior = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr[14]/td[3]/input");
	By inputRadioMotivoNuevaCuenta = By.xpath("//*[@id='tipoCambioTarj']");
	By imgBotonGuardarInformacionBasica = By.xpath("//*[@alt='Guardar']");
	
	//Pesta�a Datos Generales
	By liTabDatosGenerales = By.xpath("//*[@tabIndex='1']");
	By liTabInformacionBasica = By.xpath("//*[@tabIndex='0']");
	
		//Elementos Captura de Datos del Empleado Datos Generales
	By selectPreparacionAcademica = By.xpath("//*[@id='scholarityKey']");
	By inputFechaInicioEstudios = By.xpath("//*[@id='startDateStudies']");
	By selectEstatusEscolar = By.xpath("//*[@id='statusStudies']");
	By inputInstitucionEscolar = By.xpath("//*[@id='nameSchool']");
	
	By inputCalleDatosGenerales = By.xpath("//*[@id='calle']");
	By inputRFCDatosGenerales = By.xpath("//*[@id='rfc']");
	By imgBotonGuardarDatosGenerales = By.xpath("/html/body/div[2]/table/tbody/tr[3]/td/table/tbody/tr/td/form/table/tbody/tr/td[2]/div/div/div/table/tbody/tr[2]/td/a[1]/img");
		
	/**
	 * Constructor.
	 * 
	 * @param u
	 * @param testCase
	 * @author Mario A. De Leon Rdz.
	 */

	public Empleados(SeleniumUtil u, TestCase testCase) {
	    super(); // Llama al constructor de la clase padre (en este caso, Object)
	    this.u = u; // Asigna el objeto SeleniumUtil pasado como par�metro a la variable de instancia
	    this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60)); // Inicializa WebDriverWait con el driver de SeleniumUtil y un timeout de 60 segundos
	    this.testCase = testCase; // Asigna el objeto TestCase pasado como par�metro a la variable de instancia
		this.updNom = new UpdateNomina(u, testCase);
	}
	
	/**
	 * M�todo para hacer click en el bot�n Empleados del men� de SADEL (siempre es visible en cualquier secci�n de la aplicaci�n)
	 * @author Mario Alberto De Leon Rdz.
	 */
	public void SeleccionarBotonEmpleados() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(imgEmpleados));
			testCase.addScreenShotCurrentStep(u, "imgEmpleados");
			u.click(imgEmpleados);

		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en enlace para ingresar a boton Realizar Movimientos");
		}
	}
	
	/**
	 * M�todo para hacer clic en boton Buscar Empleado
	 * @author Mario Alberto De Leon Rdz.
	 */
	public void BuscarEmpleados() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(imgBotonBuscarEmpleado));
			testCase.addScreenShotCurrentStep(u, "imgBotonBuscarEmpleado");
			//TODO: Probablemente, agregar llenado para campos del formulario
			u.click(selectPlaza);
			updNom.robotKeyDown(1);
			
			wait.until(ExpectedConditions.presenceOfElementLocated(optionTiendaDelBosqueHMO));
			u.click(selectTienda);
			updNom.robotKeyDown(42);
			u.click(imgBotonBuscarEmpleado);
			testCase.addScreenShotCurrentStep(u, "imgBotonBuscarEmpleado2");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en enlace para ingresar a boton Realizar Movimientos");
		}
	}

	/**
	 * M�todo para buscar empleado por Numero de Seguro Social
	 * @author Mario Alberto De Leon Rdz.
	 */
	public void BuscarEmpleadoPorNSS(String NSS) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(imgBotonBuscarEmpleado));
			testCase.addScreenShotCurrentStep(u, "imgBotonBuscarEmpleado");
			//TODO: Probablemente, agregar llenado para campos del formulario
			u.sendKeys(inputBuscarPorNSS, NSS);
			u.click(imgBotonBuscarEmpleado);
			testCase.addScreenShotCurrentStep(u, "imgBotonBuscarEmpleado2");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en enlace para ingresar a boton Realizar Movimientos");
		}
	}
	
	/**
	 * M�todo para seleccionar el primer empleado mostrado despues buscar empleado
	 * @author Mario Alberto De Leon Rdz.
	 */
	public void SeleccionarEmpleado() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputRadioEmpleado1));
			//Se selecciona siempre el primer empleado porque este metodo depende del filtrado del metodo BuscarEmpleados()
			u.click(inputRadioEmpleado1);
			testCase.addScreenShotCurrentStep(u, "Se selecciona al empleado deseado");
			u.click(imgBotonSeleccionarEmpleado);
			testCase.addScreenShotCurrentStep(u, "Se da clic en el boton Editar");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en enlace para ingresar a boton Realizar Movimientos");
		}
	}
	
	/**
	 * M�todo para ingresar a la pesta�a de datos generales del empleado
	 * @author Mario Alberto De Leon Rdz.
	 */
	public void SeleccionarPestanaDatosGenerales() {
		wait.until(ExpectedConditions.elementToBeClickable(liTabDatosGenerales));
		u.click(liTabDatosGenerales);
		testCase.addScreenShotCurrentStep(u, "Se muestra secci�n Datos Generales");
	}
	
	/**
	 * M�todo para ingresar a la pesta�a de informacion basica del empleado
	 * @author Mario Alberto De Leon Rdz.
	 */
	public void SeleccionarPestanaInformacionBasica() {
		wait.until(ExpectedConditions.elementToBeClickable(liTabInformacionBasica));
		u.click(liTabInformacionBasica);
		testCase.addScreenShotCurrentStep(u, "Se muestra secci�n");
	}
	
	/**
	 * Metodo para ingresar fecha de inicio de estudios del empleado.
	 * @author Mario Alberto De Leon Rdz.
	 */
	public void ModificarDatoDelEmpleadoEstudiosFechaInicio() { //Este metodo debe ejecutarse despues de SeleccionarPestanaDatosGenerales()
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputFechaInicioEstudios));
			u.scrollToElement(inputFechaInicioEstudios);
	        //u.clear(inputCalleDatosGenerales); //no funciona en internet explorer
	        u.sendKeys(inputFechaInicioEstudios, "01072024");
	        testCase.addScreenShotCurrentStep(u, "Se modific� el contenido del campo Fecha Inicio");
	        //u.click(imgBotonGuardarDatosGenerales); //no descomentar hasta estar seguro de querer enviar el caso
	        //updNom.AceptarTodasLasAlertas();
		} catch(Exception e){
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo modificar el campo CURP");
		}
	}
	
	/**
	 * Metodo para ingresarlos datos de estudios del empleado.
	 * @author Mario Alberto De Leon Rdz.
	 */
	public void ModificarDatoDelEmpleadoEstudios() { //Este metodo debe ejecutarse despues de SeleccionarPestanaDatosGenerales()
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputFechaInicioEstudios));
			u.scrollToElement(inputFechaInicioEstudios);
	        //u.clear(inputCalleDatosGenerales); //no funciona en internet explorer
			u.sendKeys(selectPreparacionAcademica, "Bachillerato");
	        u.sendKeys(inputFechaInicioEstudios, "01072024");
	        u.sendKeys(selectEstatusEscolar, "En progreso");
	        u.sendKeys(inputInstitucionEscolar, "Prueba Automatizada");
	        testCase.addScreenShotCurrentStep(u, "Se modifico la preparacion academica");
	        
		} catch(Exception e){
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo modificar el campo CURP");
		}
	}
	
	/**
	 * Metodo para modificar el CURP del empleado, se reemplaza el ultimo caracter del CURP por un "0"
	 * @author Mario Alberto De Leon Rdz.
	 */
	public void ModificarDatoDelEmpleadoCURP() {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(inputCURP));
				testCase.addScreenShotCurrentStep(u, "CURP anterior");
				 // Obtener el valor actual del campo de entrada
				WebElement elementoCURP = u.getDriver().findElement(inputCURP);
				String CURPActual= elementoCURP.getAttribute("value");
				
		        if (CURPActual != null && CURPActual.length() > 0) {
		            // Eliminar el �ltimo car�cter y agregar el nuevo car�cter
		            String newCURP = CURPActual.substring(0, CURPActual.length() - 1) + "0";
		            
		            // Limpiar el campo de entrada
		            u.clear(inputCURP);
		            
		            // Enviar la nueva cadena al campo de entrada
		            u.sendKeys(inputCURP, newCURP);
		            testCase.addScreenShotCurrentStep(u, "CURP nuevo");
		            
		            // Opcionalmente, puedes guardar los cambios
		            u.click(imgBotonGuardarInformacionBasica); // No descomentar hasta estar seguro de querer enviar el caso
		            //testCase.addScreenShotCurrentStep(u, "Se dio click al boton guardar");
		            updNom.AceptarTodasLasAlertas();
		        } else {
		            throw new AssertionError("El campo CURP esta vacio o no es valido");
		        }
				
			} catch(Exception e){
				System.out.println(" -> " + e);
				this.u.close();
				throw new AssertionError("No se pudo modificar el campo CURP");
			}
		}
	
	/**
	 * M�todo para reemplazar el Domicilio del empleado.
	 * @author Mario Alberto De Leon Rdz.
	 */
	public void ModificarDatoDelEmpleadoDomicilio() { //Este metodo debe ejecutarse despues de SeleccionarPestanaDatosGenerales()
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputCalleDatosGenerales));
			u.scrollToElement(inputCalleDatosGenerales);
	        //u.clear(inputCalleDatosGenerales); //no funciona en internet explorer
	        u.sendKeys(inputCalleDatosGenerales, "Prueba");
	        testCase.addScreenShotCurrentStep(u, "Se modific� el contenido del campo Calle");
	        u.click(imgBotonGuardarDatosGenerales); //no descomentar hasta estar seguro de querer enviar el caso
	        testCase.addScreenShotCurrentStep(u, "Se presion� el boton guardar");
			updNom.AceptarTodasLasAlertas();
		} catch(Exception e){
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo modificar el campo CURP");
		}
	}
	
	/**
	 * M�todo para reemplazar el numero de tarjeta bancaria del empleado, se reemplaza por "0000000000000000"
	 * @author Mario Alberto De Leon Rdz.
	 */
	public void ModificarDatoDelEmpleadoTarjetaBancaria() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(inputNumeroDeTarjeta));
			u.clear(inputNumeroDeTarjeta);
			u.sendKeys(inputNumeroDeTarjeta, "0000000000000000");
			u.click(inputRadioMotivoNuevaCuenta);
			updNom.AceptarTodasLasAlertas();
			testCase.addScreenShotCurrentStep(u, "Se modific� el contenido del campo Tarjeta");
			u.click(imgBotonGuardarInformacionBasica); //no descomentar hasta estar seguro de querer enviar el caso
			testCase.addScreenShotCurrentStep(u, "Se presion� el boton guardar");
			updNom.AceptarTodasLasAlertas();
			
		} catch(Exception e){
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo modificar el campo CURP");
		}
	}
	
	/**
	 * M�todo para modificar RFC del empleado en la pesta�a Datos Generales
	 * @author Mario Alberto De Leon Rdz.
	 */
	//Este metodo debe ejecutarse despues de SeleccionarPestanaDatosGenerales()
	public void ModificarDatoDelEmpleadoRFC() { 
		 try {
		        wait.until(ExpectedConditions.elementToBeClickable(inputRFCDatosGenerales));
		        // Obtener el valor actual del campo de entrada
				WebElement elementoCURP = u.getDriver().findElement(inputRFCDatosGenerales);
				String rfcActual= elementoCURP.getAttribute("value");
				testCase.addScreenShotCurrentStep(u, "rfcAnterior");
		        // Aseg�rate de que el RFC tiene la longitud esperada (13 caracteres para personas f�sicas)
		        if (rfcActual.length() == 13) {
		            // Modificar la homoclave (�ltimos 3 caracteres)
		        	String nuevoRFC = rfcActual.substring(0, 10) + "123";  // Cambia la homoclave
		            
		            // Limpiar el campo y establecer el nuevo RFC
		            u.clear(inputRFCDatosGenerales);
		            u.sendKeys(inputRFCDatosGenerales, nuevoRFC);
		            testCase.addScreenShotCurrentStep(u, "rfcNuevo");
		            u.click(imgBotonGuardarDatosGenerales);
		            updNom.AceptarTodasLasAlertas();
		        } else {
		            throw new AssertionError("El RFC actual no tiene la longitud esperada.");
		        }
		    } catch(Exception e) {
		        System.out.println(" -> " + e);
		        this.u.close();
		        throw new AssertionError("No se pudo modificar el campo RFC");
		    }
	}
	
} 