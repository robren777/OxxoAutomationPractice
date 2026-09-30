	
package RDM.pmo.EliminacionDeRuta;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.Pmo;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_ValidarEliminacionRuta extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarEliminacionRuta_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String ruta = data.get("ruta");

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
		System.out.println("PASO 2");
	
		p.clickSupportFuntionsRutaHorario();
		
		
		// PASO 3
		addStep("Capturar las siguiente informacion en los siguientes campos: "
				+ "- T. Distrib: ALL n"
				+ "- Plaza: ALL "
				+ "- Ruta: ALL "
				+ "- Destino: ALL n"
				+ "- L. Surtido: ALL "
				+ "- Tipo Pedido: ALL"
				+ "Marcar el checkbox del campo para seleccionar todos los dias de la semana. "
				+ "Marcar el checkbox del campo para seleccionar todos Turnos "
				+ "Marcar Radiobutton: Gen. Ped "
				+ "Dar click en el boton [Ejecutar]");
		System.out.println("PASO 3");
		
		//p.ingresarDatosDistribaTipoPedido(distrib, plaza, ruta, destino, surtido, tipoPedido);
		p.rutaHorarioCapturado();
		
		
		// PASO 4
		addStep("Dar click en la casilla que se encuentra del lado derecho del nombre de la Ruta "
				+ "que se desea eliminar "
				+ "Dar click en el boton [Eliminar R/H]");
		System.out.println("PASO 4");
		
		boolean rutaEncontrada = p.buscarRutaEliminar(ruta); 
				
		assertTrue(rutaEncontrada, "No se encontro la ruta");
		
		
		// PASO 5	
		addStep("Dar click en el boton [Yes] "
				+ "Dar click en el boton [Yes] "
				+ "Validar que no se visualice esa ruta en la pantalla XXFC PMO Ruta Horario");
		
		boolean rutaEliminada = p.eliminarYValidarRuta(ruta);
			
		
		assertTrue(rutaEliminada, "Hubo un error en la eliminacion de la ruta");
		
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
		return "Se valida la eliminacion de una ruta";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidarEliminacionRuta_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}