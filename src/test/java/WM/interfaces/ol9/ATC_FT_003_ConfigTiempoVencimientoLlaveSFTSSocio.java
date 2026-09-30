package WM.interfaces.ol9;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import org.testng.annotations.Test;
import WEB.om.Pega;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCIÓN: Test Case para validación de configuración Alertas de
 * vencimiento llave
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -Configuración del Tiempo de
 * Vencimiento de Llave SFTP por Socio.
 *
 * PREREQUISITOS: - Tener Acceso a Aplicativo "PEGA" - Usar VPN Femsa.
 *
 * INSUMOS: -NA
 *
 * @author Ernesto Rosas.
 * @date 24/Septiembre/2025
 */
public class ATC_FT_003_ConfigTiempoVencimientoLlaveSFTSSocio extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ConfigTiempoVencimientoLlaveSFTSSocio_test(HashMap<String, String> data) throws Exception {

		String usr = GlobalVariables.PEGA_USER;
		String pwd = GlobalVariables.PEGA_PWD;
		String url = data.get("url");
		String email = data.get("email");
		String nombreAlerta = data.get("nombreAlerta");
		String fechaAlertado = data.get("fechaAlertado");
		String asuntoCorreo = data.get("asuntoCorreo");
		String cuerpoCorreo = data.get("cuerpoCorreo");
		
		
		
		
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);
		Pega pega = new Pega(u, testCase);

		// Inicio sesión
		addStep("Iniciar sesion PEGA");
		u.get(url);
		pega.logIn(usr, pwd);

		addStep("Dirigirse a configuracion de Alertas");
		pega.clickBTNNotificaciones();

		pega.abrirRegistros();
		// Añadir Info a nuevo registro
		addStep("Llenado y guardado de info en nuevo Registro");
		pega.anadirRegistro();

		int antes = pega.obtenerSiguienteId();

		pega.registrarInfoNueva1(antes, email, nombreAlerta, fechaAlertado, asuntoCorreo, cuerpoCorreo);
		
		int despues = pega.obtenerSiguienteId();
		boolean validacionMayorQue = antes < despues;

		// validamos que el ID anterior a insertar, sea menor al siguiente insertable
		assertTrue(validacionMayorQue);

		u.close();

	}

	@Override
	public void beforeTest() {
		String path = "Config/Drivers/MEdge/Windows/140.exe";
		System.setProperty("webdriver.edge.driver", new java.io.File(path).getAbsolutePath());
	}

	@Override
	public String setTestDescription() {
		return "Proceso de creación de alertas en PEGA, módulo de Alertamiento";
	}

	@Override
	public String setTestDesigner() {
		return "Ernesto Rosas";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_Configuracion_Alertamiento_1";
	}

	@Override
	public String setTestInstanceID() {
		return "OL9-OL9-003";
	}

	@Override
	public String setPrerequisites() {
		return "Tener Acceso a Aplicativo PEGA y Usar VPN Femsa.";
	}
}
