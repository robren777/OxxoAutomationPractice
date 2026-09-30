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
 * DESCRIPCION: Validar que el bot�n "Find" de las pantallas emergentes de cat�logos funcione correctamente 
 * 				(Distro, Cliente, Status y Deposito)
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_023_ValidarBotonBusqueda extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_023_ValidarBotonBusqueda_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 */
		String url = GlobalVariables.urlRDQA;
		String campoFind = "findRD";
		ArrayList<String> listCampo = new ArrayList<String>();
		listCampo.add("Distro,distroRD," + data.get("busquedaDistro") + ",tablaFiltroDistro");
		listCampo.add("Cliente,clienteRD," + data.get("busquedaCliente") + ",tablaFiltroCliente");
		listCampo.add("Status,statusRD," + data.get("busquedaStatus") + ",tablaFiltroStatus");
		listCampo.add("Deposito,depositoRD," + data.get("busquedaDeposito") + ",tablaFiltroDeposito");
		
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
		
		int i = 4;
		for (String campoFiltro : listCampo){
			String[] dataFiltro = campoFiltro.split(",");
			String nombreCampo = dataFiltro[0];
			String campo = dataFiltro[1];
			String datoCampo = dataFiltro[2];
			String tablaFiltro = dataFiltro[3];
		
			// PASO 4
			addStep("Clic en el bot�n cat�logo del lado derecho del campo de filtro " + nombreCampo);
			System.out.println("PASO " + i);
			
			rd.clickCatalogoCampo(campo);
			assertTrue(rd.validarExistenciaTabla(tablaFiltro), "No existe la tabla");
			i++;
			
			// PASO 5
			addStep("Establecer el dato de b�squeda en el campo del cat�logo de " + nombreCampo);
			System.out.println("PASO " + i);
			
			rd.establecerDatoCampo(campoFind, datoCampo);
			i++;
			
			// PASO 6
			addStep("Clic en el bot�n \"Find\"");
			System.out.println("PASO " + i);
			
			rd.clickFind();
			rd.clickOK();
			i++;
		}

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
		return "Validar que el bot�n \"Find\" de las "
				+ "pantallas emergentes de cat�logos funcione"
				+ " correctamente (Distro, Cliente, Status y Deposito)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_023_ValidarBotonBusqueda_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
