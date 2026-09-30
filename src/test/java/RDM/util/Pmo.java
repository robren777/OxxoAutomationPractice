
/**
 *	Clase para automatizar el modulo de PMO de RDM
 *
 * @author guerrero.vargas
 */

package RDM.util;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Mouse;
import org.sikuli.script.Pattern;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Pmo extends BaseRDM {

	public Pmo(SeleniumUtil u, TestCase testCase, String url) {
		super(u, testCase, url);

	}

	/**
	 * En el menu da click en Support Functions > Reports > XXFC PMO Ruta Destino
	 * 
	 * @author ricardo.palacios
	 */
	public void clickSupportFuntionsRutaDestino() {
		try {
			sk.screen.wait(imgPattern.get("supportFunctions")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("reports")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("XxfcPmoRutaDestino")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en Support Functions > Reports > XXFC PMO Ruta Horario
	 * 
	 * @author ricardo.palacios
	 */
	public void clickSupportFuntionsRutaHorario() {
		try {
			sk.screen.wait(imgPattern.get("supportFunctions")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("reports")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("xxfcPmoRutaHorario")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Ingresar informacion en los parametros: - T. Distrib: ALL - Plaza: ALL -
	 * Ruta: ALL - Destino: ALL - L. Surtido: ALL - Tipo Pedido: ALL
	 * 
	 * @param distrib
	 * @param plaza
	 * @param ruta
	 * @param destino
	 * @param surtido
	 * @param tipoPedido
	 * @author ricardo.palacios
	 */
	public void ingresarDatosDistribaTipoPedido(String distrib, String plaza, String ruta, String destino,
			String surtido, String tipoPedido) {
		try {
			wait(2);
			sk.screen.wait(imgPattern.get("distribpmo")).offset(90, 0).click();
			wait(3);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(distrib);
			wait(3);
			sk.screen.wait(imgPattern.get("plazapmo")).offset(90, 0).click();
			wait(3);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(plaza);
			wait(3);
			sk.screen.wait(imgPattern.get("rutapmo")).offset(90, 0).click();
			wait(3);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(ruta);
			wait(3);
			sk.screen.wait(imgPattern.get("destinopmo")).offset(90, 0).click();
			wait(3);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(destino);
			wait(3);
			sk.screen.wait(imgPattern.get("surtidopmo")).offset(90, 0).click();
			wait(3);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(surtido);
			wait(3);
			sk.screen.wait(imgPattern.get("tipoPedidopmo")).offset(90, 0).click();
			wait(3);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(Key.BACKSPACE);
			sk.screen.type(tipoPedido);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en check all
	 * 
	 * @author ricardo.palacios
	 */
	public void clickCheckAll() {
		try {
			if (sk.screen.exists(imgPattern.get("allPmo")) != null) {
				sk.screen.wait(imgPattern.get("allPmo")).click();
				wait(2);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en check gen.ped
	 * 
	 * @author ricardo.palacios
	 */
	public void clickCheckGen() {
		try {
			if (sk.screen.exists(imgPattern.get("genPed")) != null) {
				sk.screen.wait(imgPattern.get("genPed")).click();
				wait(2);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en ejecutar
	 * 
	 * @author ricardo.palacios
	 */
	public void clickEjecutar() {
		try {
			sk.screen.wait(imgPattern.get("checkAprovacionpmo")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en save edit ruta
	 * 
	 * @author ricardo.palacios
	 */
	public void clickSaveEditRuta() {
		try {
			sk.screen.wait(imgPattern.get("saveEnEditarRuta")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * En el menu da click en editar ruta hrio
	 * 
	 * @author ricardo.palacios
	 */
	public void clickEditarRutaHrio() {
		try {
			sk.screen.wait(imgPattern.get("editarRutaHrioPmo")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Coloca la informacion necesaria para crear una nueva ruta
	 * 
	 * @param ruta Nombre de la ruta que se va a crear
	 * @param descripcion Descripcion de la nueva ruta
	 * @param service Servicio de la nueva ruta
	 * @author guerrero.vargas
	 */
	public void crearNuevaRuta(String ruta, String descripcion, String service) {
		try {
			sk.screen.wait(imgPattern.get("nuevaRutaHrio")).click();
			wait(1);
			screenshot();
			
			sk.screen.type(Key.TAB);
			sk.screen.type(ruta);
			sk.screen.type(Key.TAB);
			sk.screen.type(descripcion);
			sk.screen.type(Key.TAB);
			sk.screen.type(service);
			sk.screen.type(Key.TAB);
			
			wait(1);
			screenshot();
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Coloca la informacion de una ruta existente para editarla
	 * 
	 * @param ruta Nombre de la ruta existente
	 * @param descripcion Descripcion de la ruta existente
	 * @param service Servicio de la ruta existente
	 * @author guerrero.vargas
	 */
	public void editarRuta(String ruta, String descripcion, String service) {
		try {
			screenshot();
			
			sk.screen.type(Key.TAB);
			sk.screen.type(ruta);
			sk.screen.type(Key.TAB);
			sk.screen.type(descripcion);
			sk.screen.type(Key.TAB);
			sk.screen.type(service);
			sk.screen.type(Key.TAB);
			
			wait(1);
			screenshot();
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Llena el contenido de la ruta
	 * @param horarios Horarios que se van a colocar en la ruta
	 * @param dia Dia que se activara la ruta
	 * @return true si se llena la informacion correctamente, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean llenarInfoRuta(String[] horarios, String dia) {
		try {
			// cambia la vista de la pantalla cuando estamos en el mismo dia en el que se dara de alta
			// la ruta, entonces hay que validar si el aplicativo dice "L" o "Lunes"
			if (sk.screen.exists(imgPattern.get("seleccionar" + dia), 0) != null) {
				sk.screen.wait(imgPattern.get("seleccionar" + dia)).click();
				wait(1);
			} else if (sk.screen.exists(imgPattern.get("seleccionar" + dia + "Resum"), 0) != null) {
				sk.screen.wait(imgPattern.get("seleccionar" + dia + "Resum")).click();
				wait(1);
			} else {
				return false;
			}
			
			sk.screen.wait(imgPattern.get(dia)).offset(0, 10).click();
			sk.screen.type(Key.TAB);
			
			Mouse.move(0, -20);
			
			sk.screen.wait(imgPattern.get("nextToSelected" + dia)).offset(20, 0).click();
			
			// se tienen que llenar checkboxes que no se pueden recorrer con el teclado
			// puede el mouse 22px mas abajo (al siguiente checkbox) y da click
			for (int i = 0; i < 9; i++) {
				Mouse.move(0, 22);
				Mouse.at().click();
			}
			
			Mouse.move(0, 40);
			Mouse.at().click();
			Mouse.move(0, 22);
			Mouse.at().click();
			
			sk.screen.type(Key.TAB + Key.SPACE);
			
			wait(1);
			screenshot();
			
			sk.screen.wait(imgPattern.get("horario")).click();
			// llena los horarios
			for (int i = 0; i < horarios.length; i++) {
				sk.screen.type(horarios[i]);
				sk.screen.type(Key.TAB);
			}
			
			wait(1);
			screenshot();
			
			sk.screen.wait(imgPattern.get("scrollBar")).click();
			wait(1);
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * Verifica si en la tabla de la pantalla XXFC PMO Ruta Horario aparece la nueva ruta creada
	 * 
	 * @param ruta Nombre de la ruta que se va a buscar
	 * @return true si encuentra la ruta, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean validarRutaCreada(String ruta) {
		String rutaCreada = null;
		
		numLockOff();	
		
		try {
			sk.screen.wait(imgPattern.get("pmoSave")).click();
			wait(1);
			screenshot();
			
			nTAB(2);
			
			sk.screen.type(Key.END);
			sk.screen.type(Key.HOME, Key.SHIFT);
			
			rutaCreada = copyPaste();
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
		return rutaCreada.equals(ruta);
	}
	
	/**
	 * Cancela la creacion de una ruta y verifica que no se haya creado
	 * @return true, si no se creo la ruta, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean validarRutaCancelada() {		
		try {
			sk.screen.wait(imgPattern.get("pmoExitCancel")).click();
			wait(1);
			screenshot();
			
			if (sk.screen.exists(imgPattern.get("rutaVacia")) != null) {
				return true;
			}
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * Valida que se cancele la creacion de una ruta copiada de una existente
	 * @param rutaNueva nombre nuevo que se le dara la ruta
	 * @return true si se cancela la creacion, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean validarCancelarRutaCopiada(String rutaNueva) {		
		Pattern endingImage = imgPattern.get("pmoLastRecord");
		String ruta = "";	
		
		try {
			sk.screen.wait(imgPattern.get("pmoExitCancel")).click();
			wait(1);
			sk.screen.wait(imgPattern.get("cp")).offset(0, 10).click();
			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
		}
		
		wait(1);
		screenshot();
		
		nTAB(2);
		
		while (true) {
			wait(1);
			ruta = copyPaste();
			
			if (ruta.equals(rutaNueva)) {				
				return false;
			}
			
			if (ruta.charAt(0) != rutaNueva.charAt(0)) {
				if (sk.screen.exists(endingImage, 0) != null) {
					break;
				}
			}
			
			sk.screen.type(Key.DOWN);
			
		}
		
		return true;
	}
	
	/**
	 * Valida la creacion de una ruta copiada de una existente
	 * 
	 * @param rutaNueva Nombre de la ruta nueva
	 * @return true si se crea la ruta, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean validarRutaCopiada(String rutaNueva) {		
		Pattern lastRecord = imgPattern.get("pmoLastRecord");
		String ruta = "";	
		
		try {	
			sk.screen.wait(imgPattern.get("pmoSave")).click();
			wait(1);
			screenshot();
			
			if (sk.screen.exists(imgPattern.get("estaRutaYaExiste")) != null) {
				return false;
			}
			
			while (true) {
				nTAB(2);
				
				ruta = copyPaste();
					
				if (ruta.equals(rutaNueva)) {			
					wait(1);
					screenshot();
					
					return true;
				}
				
				sk.screen.type(Key.DOWN);
				
				if (sk.screen.exists(lastRecord) != null) {
					break;
				}				
				
			 }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * Da click en "Execute Query" con la info cargada por default en la pantalla XXFC PMO RUTA HORARIO
	 * 
	 * @author guerrero.vargas
	 */
	public void rutaHorarioCapturado() {
		wait(1);
		screenshot();
		
		try {
			sk.screen.wait(imgPattern.get("checkAprovacionpmo")).click();
			wait(1);
			screenshot();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Busca la ruta que se va a copiar para crear una ruta nueva
	 * 
	 * @param rutaBase Numero de ruta que se va a copiar
	 * @return true si encuentra la ruta especificada, false de lo contrario
	 */
	public boolean buscarRuta(String rutaBase) {
		nTAB(2);
		
		Pattern endingImage = imgPattern.get("scrollEnd");
		String ruta = "";	
		
		while (true) {
			wait(1);
			ruta = copyPaste();
			
			if (ruta.equals(rutaBase)) {
				nTAB(2);
				sk.screen.type(Key.SPACE);				
				wait(1);
				screenshot();
				
				try {
					sk.screen.wait(imgPattern.get("copiarRh")).click();
				
				} catch (FindFailed e) {
					System.out.println(e.getMessage());
				}
				
				return true;
			}
			
			
			if (sk.screen.exists(endingImage, 0) != null) {
				break;
			}

			sk.screen.type(Key.DOWN);
			
		}
		
		return false;
		
	}
	
	/**
	 * Busca una ruta para eliminarla 
	 * 
	 * @param rutaEliminar nombre de la ruta que se va a eliminar
	 * @return true si encuentra la ruta especificada, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean buscarRutaEliminar(String rutaEliminar) {
		nTAB(2);
		
		Pattern endingImage = imgPattern.get("scrollEnd");
		String ruta = "";	
		
		while (true) {
			wait(1);
			ruta = copyPaste();
			
			if (ruta.equals(rutaEliminar)) {
				nTAB(2);
				sk.screen.type(Key.SPACE);				
				wait(1);
				screenshot();
				
				try {
					sk.screen.wait(imgPattern.get("eliminarRh")).click();
				
				} catch (FindFailed e) {
					System.out.println(e.getMessage());
				}
				
				return true;
			}
			
			if (ruta.charAt(0) == 'Z' || ruta.charAt(0) == 'z') {
				if (sk.screen.exists(endingImage) != null) {
					break;
				}
			}
			
			sk.screen.type(Key.DOWN);
			
		}
		
		return false;
		
	}
	
	/**
	 * Da click en eliminar y valida la eliminacion de la ruta
	 * 
	 * @param rutaEliminada numero de ruta eliminada
	 * @return true si la elimino, false de lo contrario
	 * @author guerrero.vargas
	 */
	public boolean eliminarYValidarRuta(String rutaEliminada) {
		wait(1);
		screenshot();
		
		try {
			sk.screen.wait(imgPattern.get("yesEliminar")).click();
			wait(1);
			screenshot();

			
		} catch (FindFailed e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}
}