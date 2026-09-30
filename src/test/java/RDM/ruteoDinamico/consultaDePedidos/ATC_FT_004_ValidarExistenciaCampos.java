package RDM.ruteoDinamico.consultaDePedidos;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

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
 * DESCRIPCION: Validar la existencia de los campos F. Crea Ini, F. Crea Fin, Transf, Distro, Cliente, Status 
 * 				y Deposito de la pantalla consulta de pedidos
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_004_ValidarExistenciaCampos extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarExistenciaCampos_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 */
		String url = GlobalVariables.urlRDQA;
		ArrayList<String> listaCampos = new ArrayList<String>();
		listaCampos.add("FcreaIni");
		listaCampos.add("FcreaFin");
		listaCampos.add("transferenciaRD");
		listaCampos.add("distroRD");
		listaCampos.add("clienteRD");
		listaCampos.add("statusRD");
		listaCampos.add("depositoRD");
		
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
		addStep("Validar existencia de los campos de filtro:\r\n"
				+ "   - F. Crea Ini\r\n"
				+ "   - F. Crea Fin\r\n"
				+ "   - Transf\r\n"
				+ "   - Distro\r\n"
				+ "   - Cliente\r\n"
				+ "   - Status\r\n"
				+ "   - Deposito");
		System.out.println("PASO 4");
		
		assertTrue(rd.validarExistenciaCampos(listaCampos), "No existe el campo");
		
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
		return "Validar la existencia de los campos "
				+ "F. Crea Ini, F. Crea Fin, Transf, "
				+ "Distro, Cliente, Status y Deposito "
				+ "de la pantalla consulta de pedidos.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_ValidarExistenciaCampos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
