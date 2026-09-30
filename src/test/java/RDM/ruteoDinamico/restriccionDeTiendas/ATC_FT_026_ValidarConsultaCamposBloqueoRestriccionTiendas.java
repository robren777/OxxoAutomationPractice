package RDM.ruteoDinamico.restriccionDeTiendas;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
 * DESCRIPCION: Validar que cuando se consulten las restricciones de tienda (Clic bot�n "Execute") ya no se 
 * 				puedan editar los campos de filtrado
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_026_ValidarConsultaCamposBloqueoRestriccionTiendas extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_026_ValidarConsultaCamposBloqueoRestriccionTiendas_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		Map<String, String> mapCampoValor = new LinkedHashMap<String, String>();
		mapCampoValor.put("distritoRD", data.get("claveDistrito"));
		mapCampoValor.put("clienteRD", data.get("claveCliente"));
		mapCampoValor.put("depositoNvaT", data.get("claveDeposito"));
		
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
		
		// PASO 6
		addStep("Validar que ya no se puedan editar los campos de filtrado"
				+ " (Distro, Cliente y Deposito) y que al hacer clic en los "
				+ "botones de cat�logo no se muestren las pantallas emergentes");
		System.out.println("PASO 6");
		
		assertTrue(rd.validarBloqueoCampos(mapCampoValor), "Campo no bloqueado");

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
		return "Validar que cuando se consulten las restricciones"
				+ " de tienda (Clic bot�n \"Execute\") ya no se "
				+ "puedan editar los campos de filtrado";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_026_ValidarConsultaCamposBloqueoRestriccionTiendas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
