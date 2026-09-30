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
 * DESCRIPCION: Validar campos num�ricos de pantalla emergente Nueva Tienda
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_012_ValidarCamposNumericosNuevaTienda extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_ValidarCamposNumericosNuevaTienda_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String datoTexto = data.get("datoTexto");
		String btnYes = "btnYes";
		String errorCampoNum = "errorCampoNum";
		ArrayList<String> validacionesNuevaTienda = new ArrayList<String>();
		validacionesNuevaTienda.add("Cedis,cedisNvaT," + data.get("claveCedis")); 
		validacionesNuevaTienda.add("Codigo Cliente,codigoClienteNvaT," + data.get("codigoCliente"));
		validacionesNuevaTienda.add("Tiempo Espera,tiempoEsperaNvaT," + data.get("tiempoEspera"));
		validacionesNuevaTienda.add("Latitud,latitudNvaT," + data.get("latitud"));
		validacionesNuevaTienda.add("Longitud,longitudNvaT," + data.get("longitud"));
		validacionesNuevaTienda.add("Bloque Grupo Ruta,bloqueGrupoNvaT," + data.get("bloqueGrupo"));
		
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
		
		int i = 7;
		for (String validacion : validacionesNuevaTienda){
			String[] dataNvaT = validacion.split(",");
			String nombreCampo = dataNvaT[0];
			String campo = dataNvaT[1];
			String campoDato = dataNvaT[2];
			
			// PASO 7
			addStep("Llenar campo " + nombreCampo + " con dato no num�rico");
			System.out.println("PASO " + i);
			
			rd.establecerDatoCampo(campo, datoTexto);
			rd.nTAB(1);
			i++;
			
			// PASO 8
			addStep("Validar mensaje de error de campo solo permite caracteres num�ricos y corregirlo");
			System.out.println("PASO " + i);
			
			rd.screenshot();
			assertTrue(rd.validarMensajeCuadroDialogo(errorCampoNum, btnYes), "No se mostr� mensaje de error del campo " + nombreCampo);
			rd.nBackspace(1);
			rd.establecerDatoCampo(campo, campoDato);
			i++;
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
		return "Validar campos num�ricos de pantalla emergente Nueva Tienda";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_012_ValidarCamposNumericosNuevaTienda_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
