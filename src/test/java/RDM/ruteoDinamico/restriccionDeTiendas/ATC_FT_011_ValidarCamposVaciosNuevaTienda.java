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
 * DESCRIPCION: Validar campos vacios de pantalla emergente Nueva Tienda
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_011_ValidarCamposVaciosNuevaTienda extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_ValidarCamposVaciosNuevaTienda_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String btnYesConfirmacion = "btnYes";
		String btnYesError = "yesRD";
		String mensajeConfirmacion = "confirmacion";
		ArrayList<String> validacionesNuevaTienda = new ArrayList<String>();
		validacionesNuevaTienda.add("Cedis,cedisNvaT," + data.get("claveCedis") + ",errorCedisVacio"); 
		validacionesNuevaTienda.add("Cedis Nombre,cedisNombreNvaT," + data.get("nombreCedis") + ",errorCedisNombreVacio");
		validacionesNuevaTienda.add("Deposito Clave,depositoClaveNvaT," + data.get("claveDeposito") + ",errorDepositoVacio");
		validacionesNuevaTienda.add("Deposito Nombre,depositoNombreNvaT," + data.get("nombreDeposito") + ",errorDepositoDescVacio");
		validacionesNuevaTienda.add("Codigo Cliente,codigoClienteNvaT," + data.get("codigoCliente") + ",errorClienteClaveVacio");
		validacionesNuevaTienda.add("Cliente Nombre,clienteNombreNvaT," + data.get("nombreCliente") + ",errorClienteNombreVacio");
		validacionesNuevaTienda.add("Hr. Inicio,horaInicioNvaT," + data.get("horaInicio") + ",errorHoraInicioVacio");
		validacionesNuevaTienda.add("Hora Fin,horaFinNvaT," + data.get("horaInicio") + ",errorHoraFinVacio");
		validacionesNuevaTienda.add("Hora Fin,horaFinNvaT," + data.get("horaFin") + ",errorHoraInicioFinIgual");
		validacionesNuevaTienda.add("Tiempo Espera,tiempoEsperaNvaT," + data.get("tiempoEspera") + ",errorTiempoEsperaVacio");
		validacionesNuevaTienda.add("Latitud,latitudNvaT," + data.get("latitud") + ",errorLatitudVacio");
		validacionesNuevaTienda.add("Longitud,longitudNvaT," + data.get("longitud") + ",errorLongitudVacio");
		validacionesNuevaTienda.add("Tarimas Vehiculo,tarimasVehiculoNvaT," + data.get("tarimasVehiculo") + ",errorTarimasVehiculoVacio");
		validacionesNuevaTienda.add("Distrito,distritoNvaT," + data.get("distrito") + ",errorDistritoVacio");
		validacionesNuevaTienda.add("Ruta Original,rutaOriginalNvaT," + data.get("rutaOriginal") + ",errorRutaOriginalVacio");
		validacionesNuevaTienda.add("Bloque Grupo Ruta,bloqueGrupoNvaT," + data.get("bloqueGrupo") + ",errorBloqueGrupoVacio");
		ArrayList<String> listaCampos = new ArrayList<String>();
		for (String validacion : validacionesNuevaTienda) {
			String[] campo = validacion.split(",");
			listaCampos.add(campo[1]);
		}
		
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
		addStep("Clic en el bot�n \"Nueva Tienda\"");
		System.out.println("PASO 5");
		
		rd.clickNuevaTienda();
		
		// PASO 6
		addStep("Limpiar campos Cedis y Cedis Nombre para validar campos vacios");
		System.out.println("PASO 6");
		
		rd.establecerVacioCampoCedisYCedisNombre();
		rd.validarExistenciaCampos(listaCampos);
		
		int i = 7;
		for (String validacion : validacionesNuevaTienda){
			String[] dataNvaT = validacion.split(",");
			String nombreCampo = dataNvaT[0];
			String campo = dataNvaT[1];
			String campoDato = dataNvaT[2];
			String mensajeError = dataNvaT[3];
			
			if(mensajeError.contains("errorHoraInicioFinIgual")) {
				// PASO 7
				addStep("Clic en el bot�n \"Guardar\" con el campo Hora Fin con el mismo valor que el campo Hr. Inicio");
				System.out.println("PASO " + i);
				
				rd.clickGuardar();
				i++;
				
				// PASO 8
				addStep("Clic en el bot�n \"Yes\"");
				System.out.println("PASO " + i);
				
				rd.validarMensajeCuadroDialogo(mensajeConfirmacion, btnYesConfirmacion);
				i++;
				
				// PASO 9
				addStep("Validar mensaje de los campo Hr. Inicio y Hora Fin no deben ser iguales y corregirlo");
				System.out.println("PASO " + i);
				
				assertTrue(rd.validarMensajeCuadroDialogo(mensajeError, btnYesError), "No se mostr� mensaje de error");
				rd.establecerDatoCampo(campo, campoDato);
				i++;
			} else {
				// PASO 7
				addStep("Clic en el bot�n \"Guardar\" con el campo " + nombreCampo + " vacio");
				System.out.println("PASO " + i);
				
				rd.clickGuardar();
				i++;
				
				// PASO 8
				addStep("Clic en el bot�n \"Yes\"");
				System.out.println("PASO " + i);
				
				rd.validarMensajeCuadroDialogo(mensajeConfirmacion, btnYesConfirmacion);
				i++;
				
				// PASO 9
				addStep("Validar mensaje del campo " + nombreCampo + " vacio y llenarlo");
				System.out.println("PASO " + i);
				
				assertTrue(rd.validarMensajeCuadroDialogo(mensajeError, btnYesError), "No se mostr� mensaje de error");
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
		return "Validar campos vacios de pantalla emergente Nueva Tienda";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_011_ValidarCamposVaciosNuevaTienda_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
