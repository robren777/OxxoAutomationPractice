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
 * DESCRIPCION: Validar que los campos de filtro se limpien luego de dar Clic al bot�n "Clear" y se puedan 
 * 				editar los campos
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_027_ValidarLimpiarCamposRestriccionTiendas extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_027_ValidarLimpiarCamposRestriccionTiendas_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String claveDistrito = data.get("claveDistrito");
		String claveCliente = data.get("claveCliente");
		String dataRegistroDeposito = "depositoOxxo_mty";
		String tablaRestriccionTiendas = "tablaRestriccionTiendas";
		ArrayList<String> listaCampos = new ArrayList<String>();
		listaCampos.add("distritoRD");
		listaCampos.add("clienteRD");
		listaCampos.add("depositoNvaT");
		
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
		addStep("Establecer alg�n filtro (Distrito, Cliente y/o Deposito)");
		System.out.println("PASO 5");
		
		rd.establecerDatoCampo(listaCampos.get(0), claveDistrito);
		rd.establecerDatoCampo(listaCampos.get(1), claveCliente);
		
		// PASO 6
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 6");
		
		rd.clickExecute();
		
		// PASO 7
		addStep("Clic en \"Clear\"");
		System.out.println("PASO 7");
		
		rd.clickClear();
		rd.validarExistenciaCampos(listaCampos);
		
		// PASO 8
		addStep("Validar que los campos de filtro se puedan llenar nuevamente");
		System.out.println("PASO 8");
		
		rd.establecerDatoCampo(listaCampos.get(0), claveDistrito);
		rd.establecerDatoCampo(listaCampos.get(1), claveCliente);
		rd.establecerDatoPorCatalogo(listaCampos.get(2), dataRegistroDeposito);
		rd.clickExecute();
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
		return "Validar que los campos de filtro se "
				+ "limpien luego de dar Clic al bot�n "
				+ "\"Clear\" y se puedan editar los campos";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_027_ValidarLimpiarCamposRestriccionTiendas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}