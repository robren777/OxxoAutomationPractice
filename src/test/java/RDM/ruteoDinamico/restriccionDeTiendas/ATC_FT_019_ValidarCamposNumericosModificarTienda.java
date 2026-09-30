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
 * DESCRIPCION: Validar campos num�ricos de pantalla emergente Modificar Tienda
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_019_ValidarCamposNumericosModificarTienda extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_019_ValidarCamposNumericosModificarTienda_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String claveCliente = data.get("claveCliente");
		String datoTexto = data.get("datoTexto");
		String btnYes = "btnYes";
		String errorCampoNum = "errorCampoNum";
		String campoFiltro = "clienteRD";
		ArrayList<String> validacionesNuevaTienda = new ArrayList<String>();
		validacionesNuevaTienda.add("Cedis,cedisNvaT,lblCedisNvaT," + data.get("claveCedis")); 
		validacionesNuevaTienda.add("Codigo Cliente,codigoClienteNvaT,lblCodigoClienteNvaT," + data.get("codigoCliente"));
		validacionesNuevaTienda.add("Tiempo Espera,tiempoEsperaNvaT,lblTiempoEsperaNvaT," + data.get("tiempoEspera"));
		validacionesNuevaTienda.add("Latitud,latitudNvaT,lblLatitudNvaT," + data.get("latitud"));
		validacionesNuevaTienda.add("Longitud,longitudNvaT,lblLongitudNvaT," + data.get("longitud"));
		validacionesNuevaTienda.add("Bloque Grupo Ruta,bloqueGrupoNvaT,lblBloqueGrupoNvaT," + data.get("bloqueGrupo"));
		
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
			
			// PASO 8
			addStep("Llenar campo " + nombreCampo + " con dato no num�rico");
			System.out.println("PASO " + i);
			
			rd.establecerVacioCampo(campoVacio);
			rd.establecerDatoCampo(campo, datoTexto);
			rd.nTAB(1);
			i++;
			
			// PASO 9
			addStep("Validar mensaje de error de campo solo permite caracteres num�ricos y corregirlo");
			System.out.println("PASO " + i);
			
			rd.screenshot();
			assertTrue(rd.validarMensajeCuadroDialogo(errorCampoNum, btnYes), "No se mostr� mensaje de error de campo " + nombreCampo);
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
		return "Validar campos num�ricos de pantalla emergente Modificar Tienda";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_019_ValidarCamposNumericosModificarTienda_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}

