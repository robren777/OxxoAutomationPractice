package RDM.ruteoDinamico.tipoDistroUnigis;

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
 * DESCRIPCION: Validar que el bot�n "Cancel" de la pantalla emergente del cat�logo Tipo Distro funcione correctamente
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_023_ValidarBotonCancelarDistroUnigis extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_023_ValidarBotonCancelarDistroUnigis_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 */
		String campoTipoDistro = "tipoDistroRD";
		String tablaFiltro = "tablaFiltroTipoDistro";
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
		addStep("Clic en el bot�n cat�logo del lado derecho del campo de filtro Tipo Distro");
		System.out.println("PASO 5");
		
		rd.clickCatalogoCampo(campoTipoDistro);
		assertTrue(rd.validarExistenciaTabla(tablaFiltro), "No existe la tabla");
		
		// PASO 6
		addStep("Clic en el bot�n \"Cancel\"");
		System.out.println("PASO 6");
		
		rd.clickCancelar();
		assertTrue(rd.validarExistenciaTabla(tablaDistroUnigis), "No existe la tabla");

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
		return "Validar que el bot�n \"Cancel\" de "
				+ "la pantalla emergente del cat�logo "
				+ "Tipo Distro funcione correctamente";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_023_ValidarBotonCancelarDistroUnigis_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
