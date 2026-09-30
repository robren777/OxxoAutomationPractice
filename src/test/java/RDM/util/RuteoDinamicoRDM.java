package RDM.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.sikuli.script.App;
import org.sikuli.script.Key;
import org.sikuli.script.Region;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class RuteoDinamicoRDM extends BaseRDM{

	public RuteoDinamicoRDM(SeleniumUtil u, TestCase testCase, String url) {
		super(u, testCase, url);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * En el menu da click en Distribution Planning > XXFC-Programacion y Balanceo > XXFC-Ruteo Dinamico
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickMenuDistPlanProgramBalanceoRuteoDinamico() {
		try {
			sk.screen.wait(imgPattern.get("distributionPlanning")).highlight(1,"green").click();
			wait(1);
			sk.screen.wait(imgPattern.get("XxfcProgramacionBalanceo")).highlight(1,"green").click();
			wait(1);
			sk.screen.wait(imgPattern.get("XxfcRuteoDinamico")).highlight(1,"green");
			screenshot();
			sk.screen.click();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al tratar de abrir la pantalla de Ruteo Dinamico");
		}
	}
	
	/**
	 * En la pantalla de Ruteo Dinamico da click en Ver TSF Unigis
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickVerTSFUnigis() {
		try {
			maximizar();
			wait(5);
			sk.screen.wait(imgPattern.get("verTSFUnigis")).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Ver TSF Unigis");
		}
	}
	
	/**
	 * En la pantalla da click en Execute
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickExecute() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("executeRD").similar(0.70f), 5).highlight(1,"green").click();
			wait(5);
			screenshot();
			wait(3);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Execute");
		}
	}
	
	/**
	 * En la pantalla da click en Exit
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickExit() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("exitRD").similar(0.70f), 5).highlight(1,"green").click();
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Exit");
		}
	}
	
	/**
	 * En la pantalla da click en Clear
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickClear() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("clearRD").similar(0.70f), 5).highlight(1,"green").click();
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Clear");
		}
	}
	
	/**
	 * En la pantalla da click en boton de catalogo del campo de filtro a usar
	 * 
	 * @param campo Es el parametro que representa el campo de filtro
	 * @author cristhian.m.gonzalez
	 */
	public void clickCatalogoCampo(String campo) {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get(campo).similar(0.70f), 5).getTopRight().offset(-10, 5).click();
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n de catalogo del campo " + campo);
		}
	}
	
	/**
	 * En la pantalla emergente da click en Cancel o Cancelar
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickCancelar() {
		try {
			if (sk.screen.wait(imgPattern.get("btnCancel").similar(0.70f), 5).highlight(1,"green") != null) {
				wait(1);
				sk.screen.click();
				wait(2);
				screenshot();
			}
		} catch (Exception e) {
			try {
				if (sk.screen.wait(imgPattern.get("btnCancelar").similar(0.70f), 5).highlight(1,"green") != null) {
					wait(1);
					sk.screen.click();
					wait(2);
					screenshot();
				}
			} catch (Exception e2) {
				try {
					if (sk.screen.wait(imgPattern.get("botonCancelar").similar(0.70f), 5).highlight(1,"green") != null) {
						wait(1);
						sk.screen.click();
						wait(2);
						screenshot();
					}
				} catch (Exception e3) {
					System.out.println(e.getMessage());
					throw new AssertionError("Fallo al dar clic al bot�n Cancel o Cancelar");
				}
			}
		}
	}
	
	/**
	 * En la pantalla emergente da click en Guardar
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickGuardar() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnGuardar").similar(0.70f), 5).highlight(1,"green").click();
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Guardar");
		}
	}
	
	/**
	 * En la pantalla emergente de catalogo da click en OK
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickOK() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnOK").similar(0.70f), 5).highlight(1,"green").click();
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Ok");
		}
	}
	
	/**
	 * En la pantalla emergente de catalogo da click en Find
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickFind() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnFind").similar(0.70f), 5).highlight(1,"green").click();
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Find");
		}
	}
	
	/**
	 * En la pantalla da click en Eliminar
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickEliminar() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnEliminar").similar(0.70f), 5).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Eliminar");
		}
	}
	
	/**
	 * En la pantalla da click en Restric. Tiendas
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickRestricTiendas() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnRestricTiendas").similar(0.70f), 5).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Restric. Tiendas");
		}
	}
	
	/**
	 * En la pantalla da click en Distro UNIGIS
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickDistroUnigis() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnDistroUnigis").similar(0.70f), 5).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Distro UNIGIS");
		}
	}
	
	/**
	 * En la pantalla da click en Resp. UNIGIS
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickRespUnigis() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnRespUnigis").similar(0.70f), 5).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Resp. UNIGIS");
		}
	}
	
	/**
	 * En la pantalla da click en Nueva Tienda
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickNuevaTienda() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnNuevaTienda").similar(0.70f), 5).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Nueva Tienda");
		}
	}
	
	/**
	 * En la pantalla da click en Modifica Tienda
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickModificaTienda() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnModificaTienda").similar(0.70f), 5).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Modifica Tienda");
		}
	}
	
	/**
	 * En la pantalla da click en Elimina Tienda
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickEliminaTienda() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnEliminaTienda").similar(0.70f), 5).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Elimina Tienda");
		}

	}
	
	/**
	 * En la pantalla da click en Nuevo Distro
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickNuevoDistro() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnNuevoDistro").similar(0.70f), 5).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Nuevo Distro");
		}
	}
	
	/**
	 * En la pantalla da click en Modifica Distro
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickModificaDistro() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnModificaDistro").similar(0.70f), 5).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Modifica Distro");
		}
	}
	
	/**
	 * En la pantalla da click en Elimina Distro
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickEliminaDistro() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnEliminaDistro").similar(0.70f), 5).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Elimina Distro");
		}

	}
	
	/**
	 * En la pantalla da click en Nva Respuesta
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickNvaRespuesta() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnNvaRespuesta").similar(0.70f), 5).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Nva Respuesta");
		}
	}
	
	/**
	 * En la pantalla da click en Modifica Resp
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickModificaResp() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnModificaResp").similar(0.70f), 5).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Modifica Resp");
		}
	}
	
	/**
	 * En la pantalla da click en Elimina Resp
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickEliminaResp() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnEliminaResp").similar(0.70f), 5).highlight(1,"green");
			screenshot();
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Elimina Resp");
		}
	}
	
	/**
	 * En la pantalla da click en Volver a Enviar
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickVolverEnviar() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnVolverEnviar").similar(0.70f), 5).highlight(1,"green");
			wait(1);
			sk.screen.click();
			wait(15);
			screenshot();
			wait(2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Volver a Enviar");
		}
	}
	
	/**
	 * En la pantalla da click en Selec. Todos
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickSeleccionarTodos() {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("btnSelecTodos").similar(0.70f), 5).highlight(1,"green");
			wait(1);
			sk.screen.click();
			wait(2);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar clic al bot�n Selec. Todos");
		}

	}
	
	/**
	 * En la pantalla da doble click en el primer registro de la tabla
	 * 
	 * @param titulosColTabla Es el parametro que representa el titulo de la columna de la tabla
	 * @author cristhian.m.gonzalez
	 */
	public void docleClickRegistro(String titulosColTabla) {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get(titulosColTabla)).getTopLeft().offset(20, 34).doubleClick();
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar doble clic al registro de la tabla " + titulosColTabla);
		}
	}
	
	/**
	 * En la pantalla da doble click en el texto de un registro de la tabla
	 * 
	 * @param titulosColTabla Es el parametro que representa el titulo de la tabla de la pantalla
	 * @return String con el valor del texto al que se le hizo doble click
	 * @author cristhian.m.gonzalez
	 */
	public String dobleClicTextoRegistro(String titulosColTabla) {
		String dato = "";
		App.setClipboard(dato);
		try {
			wait(1);
			datoTabla(titulosColTabla);
			wait(1);
			nTeclaAbajo(5);
			wait(1);
			sk.screen.type(Key.END, Key.SHIFT);
			wait(1);
			screenshot();
			wait(1);
			dato = copyPaste();
			wait(1);
			sk.screen.type(Key.TAB);
			wait(1);
			if(titulosColTabla.equals("lblCveUnigisEstadoNvaR")) {
				sk.screen.findWord(dato).doubleClick();
			} else {
				sk.screen.findText(dato).doubleClick();
			}
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al dar doble clic al texto del registro de la tabla " + titulosColTabla);
		}
		return dato;
	}
	
	/**
	 * En la pantalla de Consulta de pedidos agregar fecha de Creacion de inicio y fin de pedidos
	 * 
	 * @param fCreaIni Es el parametro que representa el valor a establecer en el campo de F. Crea Ini
	 * @param fCreaFin Es el parametro que representa el valor a establecer en el campo de F. Crea Fin
	 * @author cristhian.m.gonzalez
	 */
	public void establecerFechaCreaIniFin(String fCreaIni, String fCreaFin) {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get("FcreaIni")).offset(120, 0).click();
			wait(1);
			this.nBackspace(20);
			sk.screen.type(fCreaIni);
			wait(1);
			sk.screen.wait(imgPattern.get("FcreaFin")).offset(120, 0).click();
			wait(1);
			this.nBackspace(20);
			sk.screen.type(fCreaFin);
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al establecer las fechas de inicio y fin de creacion de pedidos");
		}
	}
	
	/**
	 * Establece fecha en el campo de F. Crea Ini o F. Crea Fin
	 * 
	 * @param campo Es el parametro que representa el campo de fecha a usar
	 * @param fecha Es el parametro que representa el valor a establecer en el campo de fecha
	 * @author cristhian.m.gonzalez
	 */
	public void establecerFechaCampo(String campo, String fecha) {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get(campo)).offset(120, 0).click();
			wait(1);
			sk.screen.type(Key.END);
			this.nBackspace(20);
			sk.screen.type(fecha);
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al establecer la fecha en el campo " + campo);
		}
	}
	
	/**
	 * Establece en el campo a filtrar una clave valida
	 * 
	 * @param campo Es el parametro que representa el campo a usar
	 * @param clave Es el parametro que representa el valor a establecer en el campo
	 * @author cristhian.m.gonzalez
	 */
	public void establecerDatoCampo(String campo, String clave) {
		try {
			wait(1);
			sk.screen.wait(imgPattern.get(campo).similar(0.55f), 1).getCenter().click();
			wait(1);
			/*if(!campo.contains("find")) {
				sk.screen.keyDown(Key.SHIFT);
				sk.screen.keyDown(Key.HOME);
				sk.screen.keyUp(Key.HOME);
				sk.screen.keyUp(Key.SHIFT);
				this.nBackspace(3);
			} */
			wait(1);
			sk.screen.type(clave);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al establecer el valor '" + clave + "' en el campo " + campo);
		}
	}
	
	/**
	 * Establece en el campo a filtrar una clave valida a traves del catalogo
	 * 
	 * @param campo Es el parametro que representa el campo a usar
	 * @param dataRegistro Es el parametro que representa el dato a seleccionar del cat�logo a usar
	 * @author cristhian.m.gonzalez
	 */
	public void establecerDatoPorCatalogo(String campo, String dataRegistro) {
		try {
			clickCatalogoCampo(campo);
			if (campo.contains("claveEstado")) {
				for (int i = 0; i < 5; i++) {
					sk.screen.wait(imgPattern.get("btnScrollDerecha").similar(0.70f), 1).click();
				}
			}
			wait(3);
			sk.screen.wait(imgPattern.get(dataRegistro).similar(0.50f), 1).offset(0, 0).doubleClick();
			screenshot();
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al seleccionar el valor del catalogo del campo " + campo);
		}
	}
	
	/**
	 * Establece vacio el campo de la pantalla de modificacion
	 * 
	 * @param campo Es el parametro que representa el campo a usar
	 * @author cristhian.m.gonzalez
	 */
	public void establecerVacioCampo(String campo) {
		try {
			sk.screen.wait(imgPattern.get(campo)).offset(0, 20).highlight(1,"green").click();
			wait(1);
			sk.screen.type(Key.END);
			this.nBackspace(60);
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al establecer vacio el campo " + campo);
		}
	}
	
	/**
	 * Establece vacios los campos Cedis y Cedis Nombre de la pantalla emergente Nueva Tienda 
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void establecerVacioCampoCedisYCedisNombre() {
		try {
			sk.screen.wait(imgPattern.get("lblCedisNvaT")).offset(0, 20).click();
			wait(1);
			//sk.screen.type(Key.BACKSPACE);
			seleccionarTexto();
			this.nBackspace(1);
			wait(1);
			sk.screen.wait(imgPattern.get("lblCedisNombreNvaT")).offset(0, 20).click();
			wait(1);
			//sk.screen.type(Key.BACKSPACE);
			seleccionarTexto();
			wait(1);
			seleccionarTexto();
			this.nBackspace(20);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al establecer vacios los campos Cedis y Cedis Nombre");
		}
	}
	
	/**
	 * Valida existencia de tabla en la pantalla por medio del titulo de las columnas 
	 * 
	 * @param titulosColTabla Es el parametro que representa el titulo de la tabla de la pantalla a validar
	 * @return Boolean true si existe el titulo de la tabla en la pantalla, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarExistenciaTabla(String titulosColTabla) {
		Boolean existenciaTabla = false;
		try {
			wait(3);
			sk.screen.exists(imgPattern.get(titulosColTabla).similar(0.50f), 1).highlight(1,"green");
			wait(1);
			existenciaTabla = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al validar existencia de la tabla " + titulosColTabla);
		}
		return existenciaTabla;
	}
	
	/**
	 * Valida existencia de botones en la pantalla
	 * 
	 * @param botones Es el parametro que representa el listado de botones a validar en la pantalla
	 * @return Boolean true si existen los botones en la pantalla, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarExistenciaBotones(ArrayList<String> botones) {
		Boolean existenciaBotones = false;
		try {
			wait(3);
			for (String boton: botones) {
				sk.screen.exists(imgPattern.get(boton).similar(0.50f), 5).highlight(1,"green");
			}
			wait(1);
			existenciaBotones = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al validar los botones " + botones);
		}
		return existenciaBotones;
	}
	
	/**
	 * Valida que la tabla en la pantalla est� llena o vac�a
	 * 
	 * @param titulosColTabla Es el parametro que representa el titulo de la tabla de la pantalla a validar
	 * @return Boolean true si la tabla en la pantalla tiene informaci�n, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarTablaVacia(String titulosColTabla) {
		Boolean tablaVacia = true;
		String datoTabla = datoTabla(titulosColTabla);
		try {
			wait(1);
			//if (!datoTabla.isBlank()) {
			if (!datoTabla.trim().isEmpty()) {
				tablaVacia = false;
			}
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al validar tabla vacia " + titulosColTabla);
		}
		return tablaVacia;
	}
	
	/**
	 * Valida que el campo de filtro en la pantalla tenga el valor del filtro
	 * 
	 * @param filtro Es el parametro que representa el campo de filtro a usar
	 * @param dato Es el parametro que representa el valor del campo a validar
	 * @return Boolean true si el dato del filtro es el mismo que se uso en el filtro por cat�logo, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarFiltroValor(String filtro, String dato) {
		Boolean mismoDato = true;
		String datoFiltro = datoFiltro(filtro);
		try {
			wait(1);
			//if (!datoTabla.isBlank() && datoFiltro.contentEquals(dato)) {
			if (!datoFiltro.trim().isEmpty() && datoFiltro.contentEquals(dato)) {
				mismoDato = false;
			}
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al validar dato de filtro con el campo de filtro");
		}
		return mismoDato;
	}
	
	/**
	 * Valida que al dar doble clic a un dato de la tabla sea el mismo en la pantalla de modificaci�n
	 * 
	 * @param campo Es el parametro que representa el campo a validar de la tabla y pantalla de modificaci�n
	 * @param datoTabla Es el parametro que representa valor a validar de la tabla y pantalla de modificaci�n
	 * @return Boolean true si el dato de la tabla es igual al dato de la pantalla de modificacion, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarDatoTablaConPantallaModificar(String campo, String datoTabla) {
		Boolean mismoDato = false;
		String datoCampo = datoTabla(campo);
		try {
			wait(1);
			if (datoCampo.equals(datoTabla)) {
				mismoDato = true;
			}
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al validar registro con la pantalla de Modificacion");
		}
		return mismoDato;
	}
	
	/**
	 * Valida existencia de los campos de filtro de la Pantalla
	 * 
	 * @param listaCampos Es el parametro que representa el listado de campos a validar
	 * @return Boolean true si todos los campos existen, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarExistenciaCampos(ArrayList<String> listaCampos) {
		Boolean existeCampo = false;
		try {
			for (String campo: listaCampos) {
				sk.screen.exists(imgPattern.get(campo).similar(0.70f), 5).highlight(1,"green");
			}
			wait(1);
			screenshot();
			wait(1);
			existeCampo = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al validar la existencia de campos");
		}
		return existeCampo;
	}
	
	/**
	 * Valida existencia del campo de filtro de la Pantalla
	 * 
	 * @param campo Es el parametro que representa el campo a validar
	 * @return Boolean true si el campo existe, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarExistenciaCampo(String campo) {
		Boolean existeCampo = false;
		try {
			wait(1);
			sk.screen.exists(imgPattern.get(campo).similar(0.70f), 5).highlight(1,"green");
			wait(1);
			screenshot();
			wait(1);
			existeCampo = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al validar la existecnia del campo " + campo);
		}
		return existeCampo;
	}
	
	/**
	 * Valida cuadro de dialogo en la pantalla
	 * 
	 * @param mensaje Es el parametro que representa el mensaje a validar
	 * @param btn Es el parametro que representa el bot�n a accionar del mensaje
	 * @return Boolean true si el mensaje se despliega, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarMensajeCuadroDialogo(String mensaje, String btn) {
		Boolean mensajeDialogo = false;
		try {
			if (sk.screen.exists(imgPattern.get(mensaje).similar(0.55f), 3) != null) {
				wait(1);
				sk.screen.wait(imgPattern.get(btn).similar(0.55f), 3).highlight(1,"green").click();
				wait(1);
				screenshot();
				wait(1);
				mensajeDialogo = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al validar el mensaje " + mensaje);
		}
		return mensajeDialogo;
	}
	
	/**
	 * Valida campos bloqueados al dar clic al boton Execute
	 * 
	 * @param mapCampoValor Es el parametro que representa el mapeo de cada campo con su valor para validar campo bloqueado
	 * @return Boolean true si todos los campos est�n bloqueados, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarBloqueoCampos(Map<String, String> mapCampoValor) {
		Boolean bloqueado = false;
		try {
			for (Entry<String, String> campoValor : mapCampoValor.entrySet()){
				String campo = campoValor.getKey();
				String valor = campoValor.getValue();
				if(campo.contains("Fcrea")) {
					wait(1);
					sk.screen.wait(imgPattern.get(campo).similar(0.55f), 1).offset(120, 0).click();
					wait(1);
					sk.screen.type(Key.END);
					this.nBackspace(20);
					sk.screen.type(valor);
					wait(1);
					sk.screen.exists(imgPattern.get(campo).similar(0.55f), 1).highlight(1,"green");
				} else {
					sk.screen.wait(imgPattern.get(campo).similar(0.55f), 1).click();
					wait(1);
					sk.screen.type(valor);
					wait(1);
					sk.screen.wait(imgPattern.get(campo).similar(0.55f), 1).getTopRight().offset(-10, 5).click();
					wait(1);
					sk.screen.exists(imgPattern.get(campo).similar(0.55f), 1).highlight(1,"green");
				}
				bloqueado = true;
			}
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al validar campos bloqueados");
		}
		return bloqueado;
	}
	
	/**
	 * Valida campo bloqueado al dar clic al boton Execute
	 * 
	 * @param campo Es el parametro que representa el campo a validar
	 * @param valor Es el parametro que representa el valor a usar para validar el bloque del campo
	 * @return Boolean true si el campo est� bloqueado, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarBloqueoCampo(String campo, String valor) {
		Boolean bloqueado = false;
		try {
			wait(1);
			sk.screen.wait(imgPattern.get(campo).similar(0.70f), 1).click();
			wait(1);
			sk.screen.type(valor);
			wait(1);
			sk.screen.wait(imgPattern.get(campo).similar(0.70f), 1).getTopRight().offset(-10, 5).click();
			wait(1);
			sk.screen.exists(imgPattern.get(campo).similar(0.70f), 1).highlight(1,"green");
			wait(1);
			screenshot();
			wait(1);
			bloqueado = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al validar bloqueo del campo " + campo);
		}
		return bloqueado;
	}
	
	/**
	 * Valida que exista el numero de Tranferencia en Detalle historico de un registro
	 * 
	 * @param numeroTransferencia Es el parametro que representa el numero de Tranferencia a encontrar en detalle historico
	 * @return Boolean true si se encontr� el numero de transferencia, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarNumTransfDetalleHistorico(String numeroTransferencia) {
		Boolean existe = false;
		try {
			wait(1);
			sk.screen.findT(numeroTransferencia).highlight(1,"green").click();
			wait(1);
			screenshot();
			wait(1);
			existe = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al validar el numero de tranferencia " + numeroTransferencia  + " en el la pantalla de Detalles");
		}
		return existe;
	}
	
	/**
	 * Retorna la una fecha atras de la actual en formato dd-MMM-yyyy
	 * 
	 * @param tiempo Es el parametro que representa el numero a quitar a la fecha actual
	 * @return String con el valor de fecha nueva
	 * @author cristhian.m.gonzalez
	 */
	public String obtenerFechaAnteriorConFormato(int tiempo) {
		String fechaconFormato = "";
		try {
			wait(1);
			Calendar calendario = Calendar.getInstance();
			calendario.add(Calendar.DATE, tiempo);
	        Date fecha = calendario.getTime();
	        LocalDateTime fechaNueva = LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault());
			SimpleDateFormat format = new SimpleDateFormat("dd");
			String dia = format.format(fecha);
			fechaconFormato = dia + "-" + fechaNueva.getMonth().toString().substring(0, 3) + "-" + fechaNueva.getYear();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al obtener fecha anterior");
		}
		return fechaconFormato;
	}
	
	/**
	 * Retorna la fecha actual en formato dd-MMM-yyyy
	 * 
	 * @return String con el valor de fecha actual
	 * @author cristhian.m.gonzalez
	 */
	public String obtenerFechaActualConFormato() {
		String fechaconFormato = "";
		try {
			wait(1);
			LocalDateTime hoy = LocalDateTime.now();
			SimpleDateFormat format = new SimpleDateFormat("dd");
			Date fecha = new Date();
			String dia = format.format(fecha);
			fechaconFormato = dia + "-" + hoy.getMonth().toString().substring(0, 3) + "-" + hoy.getYear();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al obtener fecha");
		}
		return fechaconFormato;
	}
	
	/**
	 * Obtener alg�n dato de la tabla en la pantalla
	 * 
	 * @param titulosColTabla Es el parametro que representa el titulo de la tabla de la pantalla
	 * @return String con el valor del texto al que se le hizo clic
	 * @author cristhian.m.gonzalez
	 */
	public String datoTabla(String titulosColTabla) {
		String dato = "";
		App.setClipboard(dato);
		try {
			wait(1);
			this.nTAB(1);
			wait(1);
			sk.screen.wait(imgPattern.get(titulosColTabla).similar(0.55f), 1).getTopLeft().offset(20, 34).click();
			seleccionarTexto();
			wait(1);
			dato = copyPaste();
			wait(1);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al obtener dato de la tabla " + titulosColTabla);
		}
		return dato;
	}
	
	/**
	 * Obtener alg�n dato del campo de filtro en la pantalla
	 * 
	 * @param filtro Es el parametro que representa el campo de filtro a usar
	 * @return String con el valor del texto al que se le hizo doble clic
	 * @author cristhian.m.gonzalez
	 */
	public String datoFiltro(String filtro) {
		String dato = "";
		App.setClipboard(dato);
		try {
			wait(1);
			sk.screen.wait(imgPattern.get(filtro)).getTopRight().offset(25, 15).doubleClick();
			wait(1);
			dato = copyPaste();
			wait(1);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al obtener dato de filtro " + filtro);
		}
		return dato;
	}
	
	/**
	 * Obtener el n�mero de transferencia dado el n�mero de fila de la tabla
	 * 
	 * @param numeroFila Es el parametro que representa el n�mero de fila de la tabla
	 * @return String con el valor del n�mero de transferencia
	 * @author cristhian.m.gonzalez
	 */
	public String numeroTransferenciaMedianteFila(int numeroFila) {
		String dato = "";
		App.setClipboard(dato);
		try {
			wait(1);
			sk.screen.findT("Transferencia").offset(0, 15).click();
			wait(1);
			if (numeroFila > 2) {
				nTeclaAbajo(numeroFila);
			}
			wait(1);
			dato = copyPaste();
			wait(1);
			if (dato.isEmpty()) {
				seleccionarTexto();
				dato = copyPaste();
			}
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al obtener el numero de tranferencia de la fila " + numeroFila);
		}
		return dato;
	}
	
	/**
	 * Obtener el n�mero total de registros de la tabla
	 * 
	 * @return int con el valor del n�mero de registros totales
	 * @author cristhian.m.gonzalez
	 */
	public int registrosTotales(){
		int numeroRegistrosTotales = 0;
		try {
			wait(1);
			Region capturaRegistrosT = sk.screen.getScreen().newRegion(115, 700, 250, 40);
			//wait(1);
			//capturaRegistrosT.highlight(1, "green");
			wait(1);
			ArrayList<String> lista = (ArrayList<String>) capturaRegistrosT.textWords();
			numeroRegistrosTotales = Integer.valueOf(lista.get(lista.size()-1));
		} catch (Exception e) {
			System.out.println(e.getMessage() + "No se encontr� los registros totales");
			throw new AssertionError("Fallo al obtener el total de registros");
		}
		return numeroRegistrosTotales;
	}
	
	/**
	 * Realiza la selecci�n (casilla checkbox) del registro en la tabla
	 * 
	 * @param numeroTranferencia Es el parametro que representa el numero de tranferenica del registro a seleccionar
	 * @author cristhian.m.gonzalez
	 */
	public void seleccionarRegistro(String numeroTranferencia) {
		try {
			wait(2);
			sk.screen.findT(numeroTranferencia).offset(80,0).click();
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al seleccionar registro con numero de transferenica " + numeroTranferencia);
		}
	}
	
	/**
	 * Realiza la selecci�n (casilla checkbox) de registros en la tabla de manera aleatorio
	 * 
	 * @param numeroRegistros Es el parametro que representa el n�mero de registros a seleccionar
	 * @author cristhian.m.gonzalez
	 */
	public void seleccionarRegistrosAleatorios(int numeroRegistros) {
		int contador = numeroRegistros;
		int registrosTotales = registrosTotales();
		try {
			if (numeroRegistros <= registrosTotales) {
				for (int i = 0; i < numeroRegistros; i++) {
					wait(1);
					sk.screen.wait(imgPattern.get("selecRegistro")).getTopRight().offset(-10, 10).click();
					wait(1);
					contador--;
				}
			}
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			try {
				if (numeroRegistros <= registrosTotales) {
					sk.screen.type(Key.PAGE_DOWN);
					numeroRegistros = contador;
					seleccionarRegistrosAleatorios(numeroRegistros);
				}
			} catch (Exception e2) {
				System.out.println(e.getMessage() + " El n�mero de registros a seleccionar es mayor al total de registros en la tabla");
				throw new AssertionError("Fallo al seleccionar registros de forma aleatoria");
			}
		}
	}
	
	/**
	 * Abrir el detalle historico de un registro dado el n�mero de fila de la tabla
	 * 
	 * @param numeroTransferencia Es el parametro que representa el n�mero de transferencia del registro
	 * @return String con el valor del n�mero de transferencia
	 * @author cristhian.m.gonzalez
	 */
	public void abrirDetalleHistoricoRegistro(String numeroTransferencia) {
		try {
			wait(1);
			sk.screen.type(Key.TAB);
			wait(1);
			sk.screen.findT("Transferencia").offset(0, -20).click();
			wait(3);
			Region registro = sk.screen.findLine(numeroTransferencia).get(0);
			int x = 600;
			int y = registro.y;
			int w = 85;
			int h = registro.h;
			registro = sk.screen.newRegion(x, y, w, h);
			registro.highlight(1, "green").getTopRight().offset(15, 10).click();
			wait(1);
			screenshot();
			wait(1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new AssertionError("Fallo al abrir detalle historio del registro con num de transferencia " + numeroTransferencia);
		}
	}
	
	/**
	 * Emula presionar la tecla Abajo un numero "n" de veces seguidas
	 *
	 * @param n Es el parametro que representa el n�mero de veces a presionar la tecla DOWN
	 * @author cristhian.m.gonzalez
	 */
	public void nTeclaAbajo(int n) {
		int x = 1;
		while (x <= n) {
			sk.screen.type(Key.DOWN);
			x = x + 1;
		}
	}
	
	/**
	 * Emula seleccionar todo el texto
	 *
	 * @author cristhian.m.gonzalez
	 */
	public void seleccionarTexto() {
		//sk.screen.keyDown(Key.END);
		sk.screen.keyDown(Key.HOME);
		sk.screen.keyDown(Key.SHIFT);
		sk.screen.keyDown(Key.END);
		sk.screen.keyUp(Key.END);
		sk.screen.keyUp(Key.SHIFT);
		sk.screen.keyUp(Key.HOME);
		//sk.screen.keyDown(Key.END);
	}

}
