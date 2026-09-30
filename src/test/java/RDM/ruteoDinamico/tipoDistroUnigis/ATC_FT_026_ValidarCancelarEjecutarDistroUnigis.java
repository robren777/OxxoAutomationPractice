package RDM.ruteoDinamico.tipoDistroUnigis;

import static org.testng.Assert.assertFalse;

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
 * DESCRIPCION: Validar que el bot�n Ejecutar presente informaci�n al salir de una pantalla emergente 
 * 				(Creaci�n, Modificaci�n, Eliminaci�n de registro)
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_026_ValidarCancelarEjecutarDistroUnigis extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_026_ValidarCancelarEjecutarDistroUnigis_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 */
		String tablaDistroUnigis = "tablaDistroUnigis";
		
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
		addStep("Clic en el bot�n \"Distro UNIGIS\"");
		System.out.println("PASO 4");
		
		rd.clickDistroUnigis();
		
		// PASO 5
		addStep("Clic en el bot�n de \"Nuevo Distro\"");
		System.out.println("PASO 5");
		
		rd.clickNuevoDistro();
		
		// PASO 6
		addStep("Clic en el bot�n \"Cancelar\"");
		System.out.println("PASO 6");
		
		rd.clickCancelar();
		
		// PASO 7
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 7");
		
		rd.clickExecute();
		assertFalse(rd.validarTablaVacia(tablaDistroUnigis), "La tabla est� vac�a");
		
		// PASO 8
		addStep("Clic en el bot�n de \"Modifica Distro\"");
		System.out.println("PASO 8");
		
		rd.clickModificaDistro();
		
		// PASO 9
		addStep("Clic en el bot�n \"Cancelar\"");
		System.out.println("PASO 9");
		
		rd.clickCancelar();
		
		// PASO 10
		addStep("Clic en el bot�n \"Clear\"");
		System.out.println("PASO 10");
		
		rd.clickClear();
		
		// PASO 11
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 11");
		
		rd.clickExecute();
		assertFalse(rd.validarTablaVacia(tablaDistroUnigis), "La tabla est� vac�a");
		
		// PASO 12
		addStep("Clic en el bot�n de \"Elimina Distro\"");
		System.out.println("PASO 8");
		
		rd.clickEliminaDistro();
		
		// PASO 13
		addStep("Clic en el bot�n \"Cancelar\"");
		System.out.println("PASO 13");
		
		rd.clickCancelar();
		
		// PASO 14
		addStep("Clic en el bot�n \"Clear\"");
		System.out.println("PASO 14");
		
		rd.clickClear();
		
		// PASO 15
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 15");
		
		rd.clickExecute();
		assertFalse(rd.validarTablaVacia(tablaDistroUnigis), "La tabla est� vac�a");

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
		return "Validar que el bot�n Ejecutar presente informaci�n"
				+ " al salir de una pantalla emergente (Creaci�n, "
				+ "Modificaci�n, Eliminaci�n de registro)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_026_ValidarCancelarEjecutarDistroUnigis_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
