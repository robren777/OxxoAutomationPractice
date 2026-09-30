package RDM.ruteoDinamico.restriccionDeTiendas;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.RuteoDinamicoRDM;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: - Tener instalado el JInitiator
 * 				  - Acceso al aplicativo RDM (VPN, Puertos)
 * 				  - Usar Microsoft Edge en modo Internet Explorer
 * 
 * 
 * DESCRIPCION: Validar campos vacios de pantalla emergente Modificar Tienda
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_018_ValidarCamposVaciosModificarTienda extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_018_ValidarCamposVaciosModificarTienda_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String claveCliente = data.get("claveCliente");
		String btnYesConfirmacion = "btnYes";
		String btnYesError = "yesRD";
		String mensajeConfirmacion = "confirmacion";
		String campoFiltro = "clienteRD";
		ArrayList<String> validacionesNuevaTienda = new ArrayList<String>();
		validacionesNuevaTienda.add("Cedis,cedisNvaT,lblCedisNvaT," + data.get("claveCedis") + ",errorCedisVacio"); 
		validacionesNuevaTienda.add("Cedis Nombre,cedisNombreNvaT,lblCedisNombreNvaT," + data.get("nombreCedis") + ",errorCedisNombreVacio");
		validacionesNuevaTienda.add("Deposito Clave,depositoClaveNvaT,lblDepositoClaveNvaT," + data.get("claveDeposito") + ",errorDepositoVacio");
		validacionesNuevaTienda.add("Deposito Nombre,depositoNombreNvaT,lblDepositoNombreNvaT," + data.get("nombreDeposito") + ",errorDepositoDescVacio");
		validacionesNuevaTienda.add("Codigo Cliente,codigoClienteNvaT,lblCodigoClienteNvaT," + data.get("codigoCliente") + ",errorClienteClaveVacio");
		validacionesNuevaTienda.add("Cliente Nombre,clienteNombreNvaT,lblClienteNombreNvaT," + data.get("nombreCliente") + ",errorClienteNombreVacio");
		validacionesNuevaTienda.add("Hr. Inicio,horaInicioNvaT,lblHoraInicioNvaT," + data.get("horaInicio") + ",errorHoraInicioVacio");
		validacionesNuevaTienda.add("Hora Fin,horaFinNvaT,lblHoraFinNvaT," + data.get("horaInicio") + ",errorHoraFinVacio");
		validacionesNuevaTienda.add("Hora Fin,horaFinNvaT,lblHoraFinNvaT," + data.get("horaFin") + ",errorHoraInicioFinIgual");
		validacionesNuevaTienda.add("Tiempo Espera,tiempoEsperaNvaT,lblTiempoEsperaNvaT," + data.get("tiempoEspera") + ",errorTiempoEsperaVacio");
		validacionesNuevaTienda.add("Latitud,latitudNvaT,lblLatitudNvaT," + data.get("latitud") + ",errorLatitudVacio");
		validacionesNuevaTienda.add("Longitud,longitudNvaT,lblLongitudNvaT," + data.get("longitud") + ",errorLongitudVacio");
		validacionesNuevaTienda.add("Tarimas Vehiculo,tarimasVehiculoNvaT,lblTarimasVehiculoNvaT," + data.get("tarimasVehiculo") + ",errorTarimasVehiculoVacio");
		validacionesNuevaTienda.add("Distrito,distritoNvaT,lblDistritoNvaT," + data.get("distrito") + ",errorDistritoVacio");
		validacionesNuevaTienda.add("Ruta Original,rutaOriginalNvaT,lblRutaOriginalNvaT," + data.get("rutaOriginal") + ",errorRutaOriginalVacio");
		validacionesNuevaTienda.add("Bloque Grupo Ruta,bloqueGrupoNvaT,lblBloqueGrupoNvaT," + data.get("bloqueGrupo") + ",errorBloqueGrupoVacio");
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest());
		RuteoDinamicoRDM rd = new RuteoDinamicoRDM(u, testCase, data.get("server"));
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		// PASO 1
		addStep("Acceder al enlace http://fcret3.femcom.net:8036/rdmDES36.html por Microsoft Edge");
		System.out.println("PASO 1");
		
		rd.logIn();
		
		// PASO 2
		addStep("Seguir la ruta dando Clic en Distribution Planning -> XXFC-Programacion y Balanceo -> XXFC-Ruteo Dinamico");
		System.out.println("PASO 2");
		
		rd.clickMenuDistPlanProgramBalanceoRuteoDinamico();
		
		// PASO 3
		addStep("Clic en el bot�n \"Ver TSF Unigis\"");
		System.out.println("PASO 3");
		
		rd.clickVerTSFUnigis();
		
		// PASO 4
		addStep("Clic en el bot�n \"Restric. Tiendas\"");
		System.out.println("PASO 4");
		
		rd.clickRestricTiendas();
		
		// PASO 5
		addStep("Establecer en el campo Cliente una clave de Cliente v�lida (editando el campo)");
		System.out.println("PASO 5");
		
		rd.establecerDatoCampo(campoFiltro, claveCliente);
		
		// PASO 6
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 6");
		
		rd.clickExecute();
		
		// PASO 7
		addStep("Clic en el bot�n \"Modifica Tienda\"");
		System.out.println("PASO 7");
		
		rd.clickModificaTienda();
		
		int i = 8;
		for (String validacion : validacionesNuevaTienda){
			String[] dataNvaT = validacion.split(",");
			String nombreCampo = dataNvaT[0];
			String campo = dataNvaT[1];
			String campoVacio = dataNvaT[2];
			String campoDato = dataNvaT[3];
			String mensajeError = dataNvaT[4];
			
			if(mensajeError.contains("errorHoraInicioFinIgual")) {
				// PASO 8
				addStep("Clic en el bot�n \"Guardar\" con el campo Hora Fin con el mismo valor que el campo Hr. Inicio");
				System.out.println("PASO " + i);
				
				rd.clickGuardar();
				i++;
				
				// PASO 9
				addStep("Clic en el bot�n \"Yes\"");
				System.out.println("PASO " + i);
				
				rd.validarMensajeCuadroDialogo(mensajeConfirmacion, btnYesConfirmacion);
				i++;
				
				// PASO 10
				addStep("Validar mensaje de los campo Hr. Inicio y Hora Fin no deben ser iguales y corregirlo");
				System.out.println("PASO " + i);
				
				assertTrue(rd.validarMensajeCuadroDialogo(mensajeError, btnYesError), "No se mostr� mensaje de error de campo " + nombreCampo);
				rd.establecerDatoCampo(campo, campoDato);
				i++;
			} else {
				
				rd.establecerVacioCampo(campoVacio);
				
				// PASO 8
				addStep("Clic en el bot�n \"Guardar\" con el campo " + nombreCampo + " vacio");
				System.out.println("PASO " + i);
				
				rd.clickGuardar();
				i++;
				
				// PASO 9
				addStep("Clic en el bot�n \"Yes\"");
				System.out.println("PASO " + i);
				
				rd.validarMensajeCuadroDialogo(mensajeConfirmacion, btnYesConfirmacion);
				i++;
				
				// PASO 10
				addStep("Validar mensaje del campo " + nombreCampo + " vacio y llenarlo");
				System.out.println("PASO " + i);
				
				assertTrue(rd.validarMensajeCuadroDialogo(mensajeError, btnYesError), "No se mostr� mensaje de error del campo " + nombreCampo);
				rd.establecerDatoCampo(campo, campoDato);
				i++;
			}
		}

		// CERRAR APLICATIVO RDM
		System.out.println("Cerrando el aplicativo de RDM");
		
		u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar campos vacios de pantalla"
				+ " emergente Modificar Tienda";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_018_ValidarCamposVaciosModificarTienda_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
