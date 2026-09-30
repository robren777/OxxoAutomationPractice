package RDM.ruteoDinamico.restriccionDeTiendas;

import static org.testng.Assert.assertTrue;

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
 * DESCRIPCION: Validar que al dar doble click sobre un registro se abra la pantalla emergente Modificar 
 * 				Tienda para editar los datos
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_016_ModificarTiendaDobleClic extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_016_ModificarTiendaDobleClic_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String claveCliente = data.get("codigoCliente");
		String campo = "clienteRD";
		String campoClientePantallaModifica = "codigoClienteModificaT";
		String campoRutaPantallaModifica = "rutaOriginalModificaT";
		String tablaRestriccionTiendas = "tablaRestriccionTiendas";
		
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
		
		rd.establecerDatoCampo(campo, claveCliente);
		
		// PASO 6
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 6");
		
		rd.clickExecute();
		
		// PASO 7
		addStep("Doble Clic en el registro a modificar");
		System.out.println("PASO 7");
		
		rd.docleClickRegistro(tablaRestriccionTiendas);
		assertTrue(rd.validarExistenciaCampo(campoClientePantallaModifica), "No se muestra la informaci�n del registro a modificar");
		assertTrue(rd.validarExistenciaCampo(campoRutaPantallaModifica), "No se muestra la informaci�n del registro a modificar");

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
		return "Validar que al dar doble click sobre un"
				+ " registro se abra la pantalla emergente"
				+ " Modificar Tienda para editar los datos";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_016_ModificarTiendaDobleClic_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
