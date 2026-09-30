package RDM.ruteoDinamico.restriccionDeTiendas;

import static org.testng.Assert.assertFalse;
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
 * DESCRIPCION: Validar que la pantalla muestre la informaci�n establecida del cat�logo de restricciones de 
 * 				las tiendas al dar Clic en el bot�n "Execute"
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_001_InformacionCatalogoRestriccionTiendas extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_InformacionCatalogoRestriccionTiendas_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
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
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 5");
		
		rd.clickExecute();
		
		// VALIDACI�N DE TABLA LLENA DE INFORMACI�N
		System.out.println("Validar que se muestre informaci�n de las restricciones de las Tiendas en la tabla");
		
		assertTrue(rd.validarExistenciaTabla(tablaRestriccionTiendas), "No existe la tabla");
		assertFalse(rd.validarTablaVacia(tablaRestriccionTiendas), "La tabla est� vac�a");

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
		return "Validar que la pantalla muestre la "
				+ "informaci�n establecida del cat�logo "
				+ "de restricciones de las tiendas al "
				+ "dar Clic en el bot�n \"Execute\"";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_InformacionCatalogoRestriccionTiendas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
