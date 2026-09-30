package WEB.seguridadSADEL.om;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.updateNomina.om.UpdateNomina;
import utils.selenium.SeleniumUtil;
import modelo.TestCase;

public class EmpleadoPage {
    private final SeleniumUtil u;
    private final TestCase testCase;
    private UpdateNomina updNom;

    //Buscar Empleado
    private final By imgBotonBuscarEmpleado = By.id("Image12");
    private final By inputBuscarPorNSS = By.id("nss");
    
    //Seccion Información Básica
    private final By imgBotonGuardarInformacionBasica = By.xpath("//*[@alt='Guardar']");
    private final By botonCancelar = By.id("btnCancelar");
    
    //Seccion Datos Generales
    private final By botonGuardarDatosGenerales = By.id("botonGuardar");
    
	//Información Bancaria
    private final By inputNumeroDeTarjeta = By.id("numeroCuenta");
    private final By inputRadioMotivoNuevaCuenta = By.id("tipoCambioTarj");
    private final By inputRadioMotivoRobo = By.xpath("//*[@value='reposicion']"); 

    //CURP
    private final By inputCURP = By.id("curp");
    //RFC
    private final By inputRFC = By.id("rfc");
    //Telefono
    private final By inputLada = By.id("areaCode");
    private final By inputTel = By.id("phoneNumber");
    private final By inputCalle = By.id("calle");
    //Se define variable random a nivel clase por sugerencia de correccion Sonar Qube
    private final java.util.Random random = new java.util.Random();
    

    /**
    * Constructor de la clase EmpleadoPage.
    * Inicializa las utilidades de Selenium y el caso de prueba asociado.
    * @author Mario A. De León Rdz.
    * @param u Instancia de SeleniumUtil para la interacción con el navegador.
    * @param testCase Instancia de TestCase para el registro de pasos y screenshots.
    * @date 10/Junio/2025
    */
    public EmpleadoPage(SeleniumUtil u, TestCase testCase) {
        this.u = u;
        this.testCase = testCase;
        this.updNom = new UpdateNomina(u, testCase);
    }

    /**
     * Busca un empleado por Número de Seguro Social en la pantalla de Empleados.<br>
     * Debe ejecutarse después de haber navegado a la sección de búsqueda de empleados.
     * @author Mario Alberto De Leon Rdz.
     * @param NSS Número de Seguro Social del empleado a buscar.
     * @date 10/Junio/2025
     */
	public boolean buscarEmpleadoPorNSS(String NSS) {
		boolean validacion = false;
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(imgBotonBuscarEmpleado));
			testCase.addScreenShotCurrentStep(u, "imgBotonBuscarEmpleado");
			u.sendKeys(inputBuscarPorNSS, NSS);
			u.click(imgBotonBuscarEmpleado);
			testCase.addScreenShotCurrentStep(u, "imgBotonBuscarEmpleado2");
			validacion = true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			e.printStackTrace();
		}
		return validacion;
	}
	
    /**
     * Modifica el número de tarjeta bancaria del empleado en el formulario de información básica.<br>
     * Espera a que el campo sea interactuable, limpia el valor actual, ingresa el nuevo número,<br>
     * selecciona el motivo "Nueva Cuenta", acepta las alertas generadas y guarda los cambios.<br>
     * @author Mario A. De León Rdz.
     * @date 10/Junio/2026
     */
	public boolean modificarDatoDelEmpleadoTarjetaBancaria(String numTarjeta) {
		boolean validacion = false;
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(inputNumeroDeTarjeta));
			u.clear(inputNumeroDeTarjeta);
			u.sendKeys(inputNumeroDeTarjeta, numTarjeta);
			u.click(inputRadioMotivoNuevaCuenta);
			updNom.AceptarTodasLasAlertas();
			testCase.addScreenShotCurrentStep(u, "Se modific el contenido del campo Tarjeta");
			u.click(imgBotonGuardarInformacionBasica); //no descomentar hasta estar seguro de querer enviar el caso
			testCase.addScreenShotCurrentStep(u, "Se presion el boton guardar");
			validacion = true;
		} catch(Exception e){
			e.printStackTrace();
		}
		return validacion;
	}
	
    /**
     * Modifica el número de tarjeta bancaria del empleado en el formulario de información básica.<br>
     * Espera a que el campo sea interactuable, limpia el valor actual, ingresa el nuevo número,<br>
     * selecciona el motivo "Robo o extravio", acepta las alertas generadas y guarda los cambios.
     * @author Mario A. De León Rdz.
     * @date 10/Junio/2026
     */
	public boolean modificarDatoDelEmpleadoTarjetaBancariaRoboOExtravio(String numTarjeta) {
		boolean validacion = false;
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(inputNumeroDeTarjeta));
			u.clear(inputNumeroDeTarjeta);
			u.sendKeys(inputNumeroDeTarjeta, numTarjeta);
			u.click(inputRadioMotivoRobo);
			updNom.AceptarTodasLasAlertas();
			testCase.addScreenShotCurrentStep(u, "Se modificó el contenido del campo Tarjeta");
			u.click(imgBotonGuardarInformacionBasica);
			testCase.addScreenShotCurrentStep(u, "Se presionó el boton guardar");
			validacion = true;
		} catch(Exception e){
			e.printStackTrace();
		}
		return validacion;
	}

	/**
	 * Modifica el RFC del empleado en la pestaña Información Básica cambiando la homoclave por "123" <br>
	 * Es necesario haber ingresado con usuario con rol Despacho.<br>
	 * Debe ejecutarse después de SeleccionarInformacionBasica().<br>
	 * @author Mario A. De León Rdz.
	 * @date 12/Junio/2026
	 */
	public boolean modificarDatoDelEmpleadoRFCConUsuarioDespacho() {
        boolean validacion = false;
        try {
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(inputRFC));

            WebElement elementoRFC = u.getDriver().findElement(inputRFC);
            String rfcActual = elementoRFC.getAttribute("value");
            testCase.addScreenShotCurrentStep(u, "rfc_anterior");

            if (rfcActual.length() == 13) {
                // 2. Reutilizas la variable aquí
                String homoclaveAleatoria = String.format("%02d", random.nextInt(100)); 
                String nuevoRFC = rfcActual.substring(0, 11) + homoclaveAleatoria;
                
                u.clear(inputRFC);
                u.sendKeys(inputRFC, nuevoRFC);
                testCase.addScreenShotCurrentStep(u, "rfc_nuevo");
                u.click(imgBotonGuardarInformacionBasica);
            } else {
                throw new Exception("El RFC actual no tiene la longitud esperada (13 caracteres). RFC obtenido: " + rfcActual);
            }
            validacion = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return validacion;
    }
    
	/**
	 * Metodo para modificar el CURP del empleado, se reemplazan los ultimos dos caracteres<br>
	 * Es necesario haber ingresado con usuario con rol Despacho.<br>
	 * Debe ejecutarse después de SeleccionarInformacionBasica().<br>
	 * @author Mario A. De León Rdz.
	 * @date 12/Junio/2026
	 */
	public boolean modificarDatoDelEmpleadoCURPConUsuarioDespacho() {
		boolean validacion = false;
	    try {
	        WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(inputCURP));

	        WebElement elementoCURP = u.getDriver().findElement(inputCURP);
	        String curpActual = elementoCURP.getAttribute("value");
	        testCase.addScreenShotCurrentStep(u, "CURP_anterior");

	        if (curpActual != null && curpActual.length() >= 2) {
	            String sufijoCURP = String.format("%02d", random.nextInt(100));
	            String nuevoCURP = curpActual.substring(0, curpActual.length() - 2) + sufijoCURP;
	            u.clear(inputCURP);
	            u.sendKeys(inputCURP, nuevoCURP);
	            testCase.addScreenShotCurrentStep(u, "CURP_nuevo");
	            u.click(imgBotonGuardarInformacionBasica);
	        } else {
	            throw new Exception("El campo CURP esta vacío o no tiene la longitud mínima esperada. CURP obtenido: " + curpActual);
	        }
	        validacion = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return validacion;
	}
    
    /**
     * Modifica el número de teléfono del empleado en la pestaña Datos Generales
     * ingresando el valor recibido como parámetro, dividiéndolo en lada (3 dígitos) y número (7 dígitos).<br>
     * Es necesario haber ingresado con usuario con rol Despacho.<br>
     * Debe ejecutarse después de SeleccionarPestanaDatosGenerales().<br>
     * @author Mario A. De León Rdz.
     * @param numTel Número de teléfono completo de 10 dígitos (3 de lada + 7 de número).
     * @date 12/Junio/2026
     */
    public boolean modificarDatoDelEmpleadoTelefono(String numTel) {
    	boolean validacion = false;
        try {
            String lada = numTel.substring(0, 3);
            String telefono = numTel.substring(3);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(inputLada));
            testCase.addScreenShotCurrentStep(u, "telefono_anterior");

            u.clear(inputLada);
            u.sendKeys(inputLada, lada);

            u.clear(inputTel);
            u.sendKeys(inputTel, telefono);
            testCase.addScreenShotCurrentStep(u, "telefono_nuevo");

            u.click(botonGuardarDatosGenerales);
            validacion = true;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return validacion;
    }
    
    /**
     * Modifica el número de teléfono del empleado en la pestaña Datos Generales...
     */
    public boolean modificarDatoDelEmpleadoDomicilioCalle(String calle) {
    	boolean validacion = false;
        try {
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(inputCalle));
            testCase.addScreenShotCurrentStep(u, "calle_anterior");
            u.clear(inputCalle);
            u.sendKeys(inputCalle, calle);
            testCase.addScreenShotCurrentStep(u, "calle_nuevo");
            
            Thread.sleep(500);
            
            u.click(botonGuardarDatosGenerales);
            validacion = true;
        } catch (InterruptedException e) {
            // Solución SonarQube: Restauramos el estado de interrupción del hilo
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return validacion;
    }
    
    /**
     * Espera la aparición de la alerta de actualización exitosa, valida su texto y la acepta.
     * Si la alerta no aparece o el texto no coincide, lanza una excepción que falla el test.
     * @author Mario A. De León Rdz.
     * @date 10/Junio/2026
     */
    public boolean validarMensajeActualizacionExitosa() {
    	boolean validacion = false;
        try {
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = u.getDriver().switchTo().alert();
            String mensajeAlerta = alert.getText();
            System.out.println(mensajeAlerta);
            if (!mensajeAlerta.equals("El empleado ha sido actualizado exitosamente")) {
                throw new Exception("El mensaje de la alerta no es el esperado. Mensaje obtenido: " + mensajeAlerta);
            }
            testCase.addScreenShotCurrentStep("mensaje_actualizacion_exitosa");
            testCase.addTextEvidenceCurrentStep("Se mostró alerta: "+mensajeAlerta);
            alert.accept();
            validacion = true;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return validacion;
    }
    
    /**
     * Acepta alertas intermedias hasta encontrar la de actualización exitosa,
     * la valida, toma evidencia y la acepta. Si no aparece, falla el test.
     * @author Mario A. De León Rdz.
     * @date 10/Junio/2026
     */
    public boolean validarMensajesActualizacionExitosaDatoGenerales() {
        boolean validacion = false;
        long tiempoLimite = System.currentTimeMillis() + 30_000;

        try {
            // El bucle sigue hasta que el método procesarAlerta devuelva true (éxito) 
            // o se acabe el tiempo.
            while (System.currentTimeMillis() < tiempoLimite) {
                if (procesarSiguienteAlerta()) {
                    validacion = true;
                    break;
                }
            }

            if (!validacion) {
                throw new Exception("No se encontró la alerta de actualización exitosa en 30 segundos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return validacion;
    }
    /**
     * Método auxiliar para procesar una sola alerta. 
     * Retorna true si encontró la alerta exitosa, false si fue intermedia, 
     * e interrumpe el flujo si no hay más alertas.
     * @author Mario A. De León Rdz.
     * @date 10/Junio/2026
     */
    private boolean procesarSiguienteAlerta() throws InterruptedException {
        try {
            WebDriverWait waitCorto = new WebDriverWait(u.getDriver(), Duration.ofSeconds(5));
            waitCorto.until(ExpectedConditions.alertIsPresent());

            Alert alert = u.getDriver().switchTo().alert();
            String mensajeAlerta = alert.getText();
            System.out.println("Alerta detectada: " + mensajeAlerta);

            if (mensajeAlerta.equals("La informacion ha sido creada exitosamente ")) {
                testCase.addScreenShotCurrentStep("mensaje_actualizacion_exitosa");
                testCase.addTextEvidenceCurrentStep("Se mostró alerta: " + mensajeAlerta);
                alert.accept();
                return true; // Éxito, termina el proceso
            } 
            
            // Si es intermedia, aceptar y continuar
            alert.accept();
            Thread.sleep(500);
            return false;

        } catch (org.openqa.selenium.TimeoutException te) {
            // No hubo más alertas en 5 segundos, esto detiene el while del método principal
            // Forzamos el fin del tiempo para salir del bucle en el método superior
            Thread.sleep(30_000); 
            return false;
        }
    }
    
    /**
     * Valida que el campo NSS se encuentre en estado deshabilitado (disabled).
     * Si el campo está habilitado o no se encuentra, lanza una excepción que falla el test.
     * @author Mario A. De León Rdz.
     * @date 18/Junio/2026
     */
    public boolean validarCampoNssDisabled() {
    	boolean validacion = false;
        try {
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            WebElement campoNss = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nss")));

            if (campoNss.isEnabled()) {
                throw new Exception("Se esperaba que el campo NSS estuviera deshabilitado, pero está habilitado.");
            }

            testCase.addScreenShotCurrentStep("campo_nss_disabled");
            testCase.addTextEvidenceCurrentStep("El campo NSS se encuentra en estado disabled correctamente.");
            validacion = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return validacion;
    }
    
    /**
     * Hace click en el botón Cancelar para recargar la página sin guardar cambios.
     * Si el botón no se encuentra o no es clickeable, lanza una excepción que falla el test.
     * @author Mario A. De León Rdz.
     * @date 18/Junio/2026
     */
    public boolean clickBotonCancelar() {
    	boolean validacion = false;
        try {
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(botonCancelar)).click();

            testCase.addScreenShotCurrentStep("click_boton_cancelar");
            testCase.addTextEvidenceCurrentStep("Se hace click en el botón Cancelar.");
            validacion = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return validacion;
    }

}