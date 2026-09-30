	
package RDM.pmo.CreacionNuevaRuta;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.Pmo;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_ValidarCancelarCreacionNuevaRuta extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarCancelarCreacionNuevaRuta_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ruta = data.get("ruta");
		String descripcion = data.get("descripcion");
		String service = data.get("service");
		String[] horarios = data.get("horarios").replace(" ", "").split(",");
		String dia = data.get("dia");

		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest());
		Pmo p = new Pmo(u, testCase, data.get("server"));

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Accesar a RDM mediante la liga: http://10.180.49.81:8011/rdm01x.html."
				+ "Ingresar usuario y contrasena y dar click en el boton Login");
		System.out.println("PASO 1");
	
		p.logIn();
		
		// PASO 2
		addStep("Dar click en las siguientes opciones del menu: Support Functions > Reports > "
				+ "XXFC-PMO Ruta Horario");
		System.out.println("PASO 3");
	
		p.clickSupportFuntionsRutaHorario();
		
		
		// PASO 3
		addStep("Dar click en el boton [Nueva Ruta/Hrio] "
				+ "Capturar los datos correspondientes a los campos: "
				+ "- Route "
				+ "- Descrip. "
				+ "Dar click en el boton [Listado] del campo Service "
				+ "Seleccionar una opcion y dar click en el boton [OK]");
		System.out.println("PASO 3");
		
		p.crearNuevaRuta(ruta, descripcion, service);
		
		// PASO 4
		addStep(" Capturar los dias y horarios de generacion de las siguientes opciones: "
				+ "- Generacion de Pedido "
				+ "- Programacion "
				+ "- Inicio Produccion "
				+ "- Fin Produccion "
				+ "- Inicio Verificacion "
				+ "- Fin Verificacion "
				+ "- Inicia Carga "
				+ "- Fin Carga "
				+ "- Salida "
				+ "- Llegada PT "
				+ "- Salida PT "
				+ "- Recorrido "
				+ "- Inicia Entrega "
				+ "- Termina Entrega "
				+ "- Ciclo de Atencion "
				+ "- Ciclo Total "
				+ "- Disp Veh/Cedis "
				+ "- JIT - Corte OC "
				+ "- JIT - Envio OC Poveedor ");
		System.out.println("PASO 4");
		
		p.llenarInfoRuta(horarios, dia);
		
		addStep("Dar click en el boton [Exit/Cancel] y validar que no se guarden los cambios en el sistema");
		
		boolean validarRutaCancelada = p.validarRutaCancelada();
		
		assertTrue(validarRutaCancelada, "Hubo un error en la cancelacion de la ruta");
		
		u.close();
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Construido. Se valida que el sistema permita realizar a cancelacion de una nueva ruta";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidarCancelarCreacionNuevaRuta_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}