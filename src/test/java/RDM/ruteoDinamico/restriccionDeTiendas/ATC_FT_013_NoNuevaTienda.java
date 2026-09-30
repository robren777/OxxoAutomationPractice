package RDM.ruteoDinamico.restriccionDeTiendas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
 * DESCRIPCION: Validar que al dar click al bot�n "No" de la pantalla emergente de confirmaci�n al intentar 
 * 				crear un registro se regrese a la pantalla emergente Nueva Tienda
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_013_NoNuevaTienda extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_013_NoNuevaTienda_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String btnNoConfirmacion = "btnNo";
		String mensajeConfirmacion = "confirmacion";
		Map<String, String> mapCampoValor = new LinkedHashMap<String, String>();
		mapCampoValor.put("cedisNvaT", data.get("claveCedis"));
		mapCampoValor.put("cedisNombreNvaT", data.get("nombreCedis"));
		mapCampoValor.put("depositoClaveNvaT", data.get("claveDeposito"));
		mapCampoValor.put("depositoNombreNvaT", data.get("nombreDeposito"));
		mapCampoValor.put("codigoClienteNvaT", data.get("codigoCliente"));
		mapCampoValor.put("clienteNombreNvaT", data.get("nombreCliente"));
		mapCampoValor.put("horaInicioNvaT", data.get("horaInicio"));
		mapCampoValor.put("horaFinNvaT", data.get("horaFin"));
		mapCampoValor.put("tiempoEsperaNvaT", data.get("tiempoEspera"));
		mapCampoValor.put("latitudNvaT", data.get("latitud"));
		mapCampoValor.put("longitudNvaT", data.get("longitud"));
		mapCampoValor.put("tarimasVehiculoNvaT", data.get("tarimasVehiculo"));
		mapCampoValor.put("distritoNvaT", data.get("distrito"));
		mapCampoValor.put("rutaOriginalNvaT", data.get("rutaOriginal"));
		mapCampoValor.put("bloqueGrupoNvaT", data.get("bloqueGrupo"));
		
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
		addStep("Limpiar campos Cedis y Cedis Nombre");
		System.out.println("PASO 6");
		
		rd.establecerVacioCampoCedisYCedisNombre();
		
		// PASO 7
		addStep("Llenar los campos requeridos para la nueva tienda");
		System.out.println("PASO 7");
		
		for (Entry<String, String> campoValor : mapCampoValor.entrySet()){
			String campo = campoValor.getKey();
			String valor = campoValor.getValue();
			
			rd.establecerDatoCampo(campo, valor);
		}
		
		// PASO 8
		addStep("Clic en el bot�n \"Guardar\"");
		System.out.println("PASO 8");
		
		rd.clickGuardar();
		
		// PASO 9
		addStep("Clic en el bot�n \"No\"");
		System.out.println("PASO 9");
		
		assertTrue(rd.validarMensajeCuadroDialogo(mensajeConfirmacion, btnNoConfirmacion), "No se mostr� mensaje de confirmaci�n");

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
		return "Validar que al dar click al bot�n \"No\""
				+ " de la pantalla emergente de confirmaci�n"
				+ " al intentar crear un registro se regrese"
				+ " a la pantalla emergente Nueva Tienda";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_013_NoNuevaTienda_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
