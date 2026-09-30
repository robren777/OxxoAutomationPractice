package RDM.ruteoDinamico.consultaDePedidos;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;

import RDM.util.RuteoDinamicoRDM;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: - Tener instalado el JInitiator
 * 				  - Acceso al aplicativo RDM (VPN, Puertos)
 * 				  - Usar Microsoft Edge en modo Internet Explorer
 * 
 * 
 * DESCRIPCION: Validar que cuando se consulten los pedidos (Clic bot�n "Execute") ya no se puedan editar 
 * 				los campos de filtrado
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_020_ValidarConsultaCamposBloqueo extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_020_ValidarConsultaCamposBloqueo_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String url = GlobalVariables.urlRDQA;
		String fCreaIni = "";
		String fCreaFin = "";
		Map<String, String> mapCampoValor = new LinkedHashMap<String, String>();
		mapCampoValor.put("FcreaIni", fCreaIni);
		mapCampoValor.put("FcreaFin", fCreaFin);
		mapCampoValor.put("transferenciaRD", data.get("claveNumTransf"));
		mapCampoValor.put("distroRD", data.get("claveDistro"));
		mapCampoValor.put("clienteRD", data.get("claveCliente"));
		mapCampoValor.put("statusRD", data.get("claveStatus"));
		mapCampoValor.put("depositoRD", data.get("claveDeposito"));
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest());
		RuteoDinamicoRDM rd = new RuteoDinamicoRDM(u, testCase, url);
		
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
		addStep("Establecer en los campos F. Crea Ini y F. Crea Fin fechas v�lidas");
		System.out.println("PASO 4");
		
		fCreaIni = rd.obtenerFechaAnteriorConFormato(-90);
		fCreaFin = rd.obtenerFechaActualConFormato();
		rd.establecerFechaCreaIniFin(fCreaIni, fCreaFin);
		
		// PASO 5
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 5");
		
		rd.clickExecute();
		
		// PASO 6
		addStep("Validar que ya no se puedan editar los campos de filtrado "
				+ "(Transf, Distro, Cliente, Status y  Deposito) "
				+ "y que al hacer Clic en los botones de cat�logo "
				+ "no Se muestre las pantallas emergentes");
		System.out.println("PASO 6");
		
		assertTrue(rd.validarBloqueoCampos(mapCampoValor), "Campo no bloqueado");

		// CERRAR APLICATIVO RDM
		System.out.println("Cerrando el aplicativo de RDM");
		
		u.quit();
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
		return "Validar que cuando se consulten los pedidos"
				+ " (Clic bot�n \"Execute\") ya no se puedan"
				+ " editar los campos de filtrado";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_020_ValidarConsultaCamposBloqueo_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
