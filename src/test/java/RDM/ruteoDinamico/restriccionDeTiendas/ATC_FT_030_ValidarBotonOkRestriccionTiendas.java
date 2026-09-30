package RDM.ruteoDinamico.restriccionDeTiendas;

import static org.testng.Assert.assertFalse;

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
 * DESCRIPCION: Validar que el bot�n "Ok" de las pantallas emergentes de cat�logos funcione correctamente 
 * 				(Distrito, Cliente y Deposito)
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_030_ValidarBotonOkRestriccionTiendas extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_030_ValidarBotonOkRestriccionTiendas_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String campoFind = "findRD";
		ArrayList<String> listaCampos = new ArrayList<String>();
		listaCampos.add("Distrito,distritoRD,filtroDistrito," + data.get("claveDistrito"));
		listaCampos.add("Cliente,clienteRD,filtroCliente," + data.get("claveCliente"));
		listaCampos.add("Deposito,depositoRD,filtroDeposito," + data.get("claveDeposito"));
		
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
		
		int i = 5;
		for (String campoFiltro : listaCampos){
			String[] dataFiltro = campoFiltro.split(",");
			String nombreCampo = dataFiltro[0];
			String campo = dataFiltro[1];
			String filtro = dataFiltro[2];
			String datoCampo = dataFiltro[3];
			
			// PASO 5
			addStep("Clic en el bot�n cat�logo del lado derecho del campo de filtro " + nombreCampo);
			System.out.println("PASO " + i);
			
			rd.clickCatalogoCampo(campo);
			i++;
			
			// PASO 6
			addStep("Establecer el dato de b�squeda en el campo del cat�logo de " + nombreCampo);
			System.out.println("PASO " + i);
			
			rd.establecerDatoCampo(campoFind, datoCampo);
			i++;
			
			// PASO 7
			addStep("Clic en el bot�n \"Find\"");
			System.out.println("PASO " + i);
						
			rd.clickFind();
			i++;
			
			// PASO 8
			addStep("Clic en el bot�n \"OK\"");
			System.out.println("PASO " + i);
			
			rd.clickOK();
			assertFalse(rd.validarFiltroValor(filtro, datoCampo), "El filtro " + nombreCampo + " est� vac�o");
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
		return "Validar que el bot�n \"Ok\" de las pantallas"
				+ " emergentes de cat�logos funcione correctamente"
				+ " (Distrito, Cliente y Deposito)";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_030_ValidarBotonOkRestriccionTiendas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
