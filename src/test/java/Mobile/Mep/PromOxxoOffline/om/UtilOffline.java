package Mobile.Mep.PromOxxoOffline.om;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

/**
 * DESCRIPCIÓN: Clase utilitaria con métodos de soporte para la ejecución de scripts
 * en modo sin conexión (Offline). Incluye utilidades para gestión de conectividad,
 * configuración de red Wi-Fi y acciones auxiliares del framework.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *   - Casos de prueba del módulo PromOXXO Offline que requieren control de conectividad.
 *
 * PREREQUISITOS:
 *   - El dispositivo tiene permisos de administración de red habilitados.
 *
 * INSUMOS:
 *   - N/A
 *
 * @author Mario A. De León Rdz.
 * @date 15/Diciembre/2025
 */
public class UtilOffline {
	/**
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase PromOxxoOffline
	 * 
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 * @author Mario A. De León Rdz.
	 * @return PromOxxoOffline Instancia de la clase.
	 * @date 15/Diciembre/2025
	 */
	public UtilOffline(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/**
	 * Cambia el estado de la conectividad de red (Wi-Fi y Datos Móviles) del
	 * dispositivo
	 * simulando el modo Online/Offline mediante comandos ADB directos (svc wifi /
	 * svc data).
	 *
	 * Deshabilitar la red (Simulando Offline):
	 * 1. Desactivar Wi-Fi
	 * 2. Desactivar Datos Móviles
	 * Nota: Evitamos el Modo Avión por sus problemas de permisos y asincronía.
	 *
	 * @param online: true para habilitar las conexiones (encender WiFi y Datos),
	 *                false para deshabilitar las conexiones (apagar WiFi y Datos,
	 *                simulación Offline).
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 15/Diciembre/2025
	 */
	
	public synchronized void setConectividadSimple(boolean online) throws Exception {
		try {
			if (online) {
				// Habilitar la red
				Runtime.getRuntime().exec("adb shell svc wifi enable");
				Runtime.getRuntime().exec("adb shell svc data enable");
				System.out.println("Wifi & Data: Enabled");
			} else {
				// Deshabilitar la red (Simulando Offline)
				Runtime.getRuntime().exec("adb shell svc wifi disable");
				Runtime.getRuntime().exec("adb shell svc data disable");
				System.out.println("Wifi & Data: Disabled");
			}
			Thread.sleep(5000);
		} catch (Exception e) {
			System.err.println("Error al cambiar estado de red con ADB: " + e.getMessage());
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al cambiar estado de red con ADB: ", e);
		}
	}
	
	/**
	 * Modifica la fecha del sistema del dispositivo Android a 48 horas
	 * antes de la fecha y hora actual, utilizando comandos ADB.
	 *
	 * Deshabilita la sincronización automática de fecha y hora antes del cambio.
	 *
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception En caso de error al modificar la fecha del sistema.
	 * @date 08/Enero/2026
	 */
	public void setFechaSistemaMenos48Horas() throws Exception {
	    try {
	        // Desactivar fecha y hora automática
	        Runtime.getRuntime().exec("adb shell settings put global auto_time 0");
	        Runtime.getRuntime().exec("adb shell settings put global auto_time_zone 0");

	        // Calcular fecha actual menos 48 horas
	        java.time.LocalDateTime fechaMenos48 = java.time.LocalDateTime.now().minusHours(48);
	        java.time.format.DateTimeFormatter formatter =
	                java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss");

	        String fechaFormateada = fechaMenos48.format(formatter);

	        // Establecer nueva fecha en el dispositivo
	        Runtime.getRuntime().exec("adb shell date -s " + fechaFormateada);

	        System.out.println("Fecha del sistema modificada a (48h menos): " + fechaFormateada);

	        Thread.sleep(5000);
	    } catch (Exception e) {
	        System.err.println("Error al cambiar la fecha del sistema con ADB: " + e.getMessage());
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al cambiar la fecha del sistema con ADB", e);
	    }
	}
	
	/**
	 * Restaura la fecha y hora del sistema del dispositivo Android
	 * habilitando nuevamente la sincronización automática.
	 *
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception En caso de error al restaurar la fecha y hora del sistema.
	 * @date 08/Enero/2026
	 */
	public void restaurarFechaSistemaActual() throws Exception {
	    try {
	        // Habilitar fecha y hora automática
	        Runtime.getRuntime().exec("adb shell settings put global auto_time 1");
	        Runtime.getRuntime().exec("adb shell settings put global auto_time_zone 1");

	        System.out.println("Fecha y hora del sistema restauradas a modo automático");

	        Thread.sleep(5000);
	    } catch (Exception e) {
	        System.err.println("Error al restaurar la fecha del sistema con ADB: " + e.getMessage());
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al restaurar la fecha y hora del sistema con ADB", e);
	    }
	}



	
	/**
	 * Cambia el estado de la conectividad de red (Wi-Fi y Datos Móviles) del
	 * dispositivo
	 * simulando el modo Online/Offline mediante comandos ADB directos (svc wifi /
	 * svc data).
	 *
	 * Deshabilitar la red (Simulando Offline):
	 * 1. Desactivar Wi-Fi
	 * 2. Desactivar Datos Móviles
	 * Nota: Evitamos el Modo Avión por sus problemas de permisos y asincronía.
	 *
	 * @param online: true para habilitar las conexiones (encender WiFi y Datos),
	 *                false para deshabilitar las conexiones (apagar WiFi y Datos,
	 *                simulación Offline).
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 15/Diciembre/2025
	 */
	
	public void setConectividadSimpleConEv(boolean online) throws Exception {
		try {
			if (online) {
				// Habilitar la red
				Runtime.getRuntime().exec("adb shell svc wifi enable");
				Runtime.getRuntime().exec("adb shell svc data enable");
				System.out.println("Wifi & Data: Enabled");
				testCase.addTextEvidenceCurrentStep("Se activa WiFi & Datos");
			} else {
				// Deshabilitar la red (Simulando Offline)
				Runtime.getRuntime().exec("adb shell svc wifi disable");
				Runtime.getRuntime().exec("adb shell svc data disable");
				System.out.println("Wifi & Data: Disabled");
				testCase.addTextEvidenceCurrentStep("Se desactiva WiFi & Datos");
			}
			Thread.sleep(500);
			testCase.addScreenShotCurrentStep(au, "");
		} catch (Exception e) {
			System.err.println("Error al cambiar estado de red con ADB: " + e.getMessage());
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al cambiar estado de red con ADB: ", e);
		}
	}
	
	/**
	 * Cambia el estado de la conectividad de red (Wi-Fi y Datos Móviles) del
	 * dispositivo
	 * simulando el modo Online/Offline mediante comandos ADB directos (svc wifi /
	 * svc data).
	 *
	 * Deshabilitar la red (Simulando Offline):
	 * 1. Desactivar Wi-Fi
	 * 2. Desactivar Datos Móviles
	 * Nota: Evitamos el Modo Avión por sus problemas de permisos y asincronía.
	 *
	 * @param online: true para habilitar las conexiones (encender WiFi y Datos),
	 *                false para deshabilitar las conexiones (apagar WiFi y Datos,
	 *                simulación Offline).
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 15/Diciembre/2025
	 */
	
	public void setConectividadSimpleWifi(boolean online) throws Exception {
		try {
			if (online) {
				// Habilitar la red
				Runtime.getRuntime().exec("adb shell svc wifi enable");
				System.out.println("Wifi: Enabled");
			} else {
				// Deshabilitar la red (Simulando Offline)
				Runtime.getRuntime().exec("adb shell svc wifi disable");
				System.out.println("Wifi: Disabled");
			}
		} catch (Exception e) {
			System.err.println("Error al cambiar estado de red con ADB: " + e.getMessage());
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al cambiar estado de red con ADB: ", e);
		}
	}
	/**
	 * Cambia el estado de la conectividad de red (Wi-Fi y Datos Móviles) del
	 * dispositivo
	 * simulando el modo Online/Offline mediante comandos ADB directos (svc wifi /
	 * svc data).
	 *
	 * Deshabilitar la red (Simulando Offline):
	 * 1. Desactivar Wi-Fi
	 * 2. Desactivar Datos Móviles
	 * Nota: Evitamos el Modo Avión por sus problemas de permisos y asincronía.
	 *
	 * @param online: true para habilitar las conexiones (encender WiFi y Datos),
	 *                false para deshabilitar las conexiones (apagar WiFi y Datos,
	 *                simulación Offline).
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 15/Diciembre/2025
	 */
	
	public void setConectividadSimpleWifiConEv(boolean online) throws Exception {
		try {
			if (online) {
				// Habilitar la red
				Runtime.getRuntime().exec("adb shell svc wifi enable");
				System.out.println("Wifi: Enabled");
				testCase.addTextEvidenceCurrentStep("Se activa WiFi");
			} else {
				// Deshabilitar la red (Simulando Offline)
				Runtime.getRuntime().exec("adb shell svc wifi disable");
				System.out.println("Wifi: Disabled");
				testCase.addTextEvidenceCurrentStep("Se desactiva WiFi");
			}
			testCase.addScreenShotCurrentStep(au, "");
		} catch (Exception e) {
			System.err.println("Error al cambiar estado de red con ADB: " + e.getMessage());
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al cambiar estado de red con ADB: ", e);
		}
	}
	/**
	 * Cambia el estado de la conectividad de red (Wi-Fi y Datos Móviles) del
	 * dispositivo
	 * simulando el modo Online/Offline mediante comandos ADB directos (svc wifi /
	 * svc data).
	 *
	 * Deshabilitar la red (Simulando Offline):
	 * 1. Desactivar Wi-Fi
	 * 2. Desactivar Datos Móviles
	 * Nota: Evitamos el Modo Avión por sus problemas de permisos y asincronía.
	 *
	 * @param online: true para habilitar las conexiones (encender WiFi y Datos),
	 *                false para deshabilitar las conexiones (apagar WiFi y Datos,
	 *                simulación Offline).
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 15/Diciembre/2025
	 */
	
	public void setConectividadSimpleDatos(boolean online) throws Exception {
		try {
			if (online) {
				// Habilitar la red
				Runtime.getRuntime().exec("adb shell svc data enable");
				System.out.println("Data: Enabled");
			} else {
				// Deshabilitar la red (Simulando Offline)
				Runtime.getRuntime().exec("adb shell svc data disable");
				System.out.println("Data: Disabled");
			}
		} catch (Exception e) {
			System.err.println("Error al cambiar estado de red con ADB: " + e.getMessage());
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al cambiar estado de red con ADB: ", e);
		}
	}
	/**
	 * Cambia el estado de la conectividad de red (Wi-Fi y Datos Móviles) del
	 * dispositivo
	 * simulando el modo Online/Offline mediante comandos ADB directos (svc wifi /
	 * svc data).
	 *
	 * Deshabilitar la red (Simulando Offline):
	 * 1. Desactivar Wi-Fi
	 * 2. Desactivar Datos Móviles
	 * Nota: Evitamos el Modo Avión por sus problemas de permisos y asincronía.
	 *
	 * @param online: true para habilitar las conexiones (encender WiFi y Datos),
	 *                false para deshabilitar las conexiones (apagar WiFi y Datos,
	 *                simulación Offline).
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 15/Diciembre/2025
	 */
	
	public void setConectividadSimpleDatosConEv(boolean online) throws Exception {
		try {
			if (online) {
				// Habilitar la red
				Runtime.getRuntime().exec("adb shell svc data enable");
				System.out.println("Data: Enabled");
				testCase.addTextEvidenceCurrentStep("Se activan Datos");
			} else {
				// Deshabilitar la red (Simulando Offline)
				Runtime.getRuntime().exec("adb shell svc data disable");
				System.out.println("Wifi & Data: Disabled");
				testCase.addTextEvidenceCurrentStep("Se desactivan Datos");
			}
			testCase.addScreenShotCurrentStep(au, "");
			
		} catch (Exception e) {
			System.err.println("Error al cambiar estado de red con ADB: " + e.getMessage());
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al cambiar estado de red con ADB: ", e);
		}
	}
	
	/**
	 * Obtiene el tamaño del almacenamiento en caché de la aplicación antes de iniciar el flujo.
	 *
	 * @author Mario A. De León Rdz.
	 * @return int Tamaño de la caché en bytes
	 * @throws Exception
	 * @date 15/Diciembre/2025
	 */
	public int obtenerAlmacenamientoCacheSinEv() throws Exception {
	    try {
	        Process process = Runtime.getRuntime()
	                .exec("adb shell dumpsys package " + au.driver.getCapabilities().getCapability("appPackage"));

	        BufferedReader reader = new BufferedReader(
	                new InputStreamReader(process.getInputStream()));

	        String line;
	        int cacheBytes = 0;

	        while ((line = reader.readLine()) != null) {
	            if (line.contains("cacheSize=")) {
	                cacheBytes = Integer.parseInt(
	                        line.replaceAll(".*cacheSize=(\\d+).*", "$1"));
	                break;
	            }
	        }

	        System.out.println("Tamaño de caché obtenido antes de iniciar la app: " + cacheBytes + " bytes");
	        return cacheBytes;

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error al obtener el tamaño de caché: " + e.getMessage());
	        throw new Exception("Error al obtener el almacenamiento en caché con ADB", e);
	    }
	}

	/**
	 * Valida que el tamaño del almacenamiento en caché se mantenga igual.
	 *
	 * @author Mario A. De León Rdz.
	 * @param cacheBytesAntes Tamaño de caché antes de iniciar el flujo
	 * @throws Exception
	 * @date 15/Diciembre/2025
	 */
	public void validarAlmacenamientoCacheIgual(int cacheBytesAntes) throws Exception {
	    try {
	        int cacheBytesDespues = obtenerAlmacenamientoCacheSinEv();

	        testCase.addTextEvidenceCurrentStep(
	                "Cache inicial: " + cacheBytesAntes +
	                " bytes | Cache final: " + cacheBytesDespues + " bytes");

	        if (cacheBytesDespues != cacheBytesAntes) {
	            throw new Exception(
	                    "La caché cambió. Inicial: " + cacheBytesAntes +
	                    " | Final: " + cacheBytesDespues);
	        }

	        testCase.addTextEvidenceCurrentStep(
	                "El almacenamiento en caché se mantuvo sin cambios.");
	        testCase.addScreenShotCurrentStep(au, "Validación de caché sin cambios");

	    } catch (Exception e) {
	        e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep(
	                "Error al validar que la caché se mantenga igual: " + e.getMessage());
	        throw new Exception("Error al validar almacenamiento en caché", e);
	    }
	}
	
	/**
	 * Valida que se muestre un texto específico en la vista actual
	 * 
	 * @author Mario A. De León Rdz.
	 * @param text Texto esperado a validar en pantalla
	 * @return void
	 * @throws Exception El método falla el test en caso de que no se encuentre el texto esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarVisibilidadDeTexto(String text) throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(240));
	        By textBy = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")");
	        wait.until(ExpectedConditions.visibilityOfElementLocated(textBy));

	        testCase.addTextEvidenceCurrentStep("Se muestra el texto esperado en pantalla: \"" + text + "\"");
	        testCase.addScreenShotCurrentStep(au, "Texto visible en pantalla");
	    } catch (Exception e) {
	        e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("No se mostró el texto esperado en pantalla: \"" + text + "\"", e);
	    }
	}
	
	/**
	 * Valida que se muestre un texto específico en la vista actual
	 * 
	 * @author Mario A. De León Rdz.
	 * @param text Texto esperado a validar en pantalla
	 * @return void
	 * @throws Exception El método falla el test en caso de que no se encuentre el texto esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarVisibilidadDeTextoSinEv(String text) throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        By textBy = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")");
	        wait.until(ExpectedConditions.visibilityOfElementLocated(textBy));
	    } catch (Exception e) {
	        e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("No se mostró el texto esperado en pantalla: \"" + text + "\"", e);
	    }
	}
	
	/**
	 * Valida que un texto específico ya no se muestre en la vista actual
	 * 
	 * @author Mario A. De León Rdz.
	 * @param text Texto que no debe estar visible en pantalla
	 * @return void
	 * @throws Exception El método falla el test en caso de que el texto continúe visible.
	 * @date 22/Diciembre/2025
	 */
	public void esperarHastaQueNoSeaVisibleTexto(String text) throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        By textBy = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")");

	        wait.until(ExpectedConditions.invisibilityOfElementLocated(textBy));

	        testCase.addTextEvidenceCurrentStep("El texto ya no se muestra en pantalla: \"" + text + "\"");
	        testCase.addScreenShotCurrentStep(au, "Texto no visible en pantalla");
	    } catch (Exception e) {
	        e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception(
	            "El texto sigue mostrándose en pantalla cuando se esperaba que desapareciera: \"" + text + "\"",
	            e
	        );
	    }
	}

    /**
     * Metodo para hacer click cualquier elemento que contenga el texto ingresado en los parametros"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickTexto(String text) throws Exception {
        try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        
	        System.out.println(text);
	        System.out.println("new UiSelector().textContains(\"" + text + "\")");
	        
	        By textBy = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")");
	        wait.until(ExpectedConditions.elementToBeClickable(textBy)).click();
	        
	        testCase.addScreenShotCurrentStep(au, "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en el botón 'Período actual'", e);
        }
    }

    /**
     * Metodo para hacer click cualquier elemento que contenga el texto ingresado en los parametros"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickTextoSinEv(String text) throws Exception {
        try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        
	        System.out.println(text);
	        System.out.println("new UiSelector().textContains(\"" + text + "\")");
	        
	        By textBy = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")");
	        wait.until(ExpectedConditions.elementToBeClickable(textBy)).click();
	        
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en el botón 'Período actual'", e);
        }
    }
}