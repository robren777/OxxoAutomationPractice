package RDM.ruteoDinamico.consultaDePedidos;

import static org.testng.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.RuteoDinamicoRDM;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

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

public class ATC_FT_021_ValidarLimpiarCampos extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_021_ValidarLimpiarCampos_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String url = GlobalVariables.urlRDQA;
		String fCreaIni = "";
		String fCreaFin = "";
		String consultaDatos= data.get("queryDatos");
		String claveNumTransf = data.get("claveNumTransf");
		String claveDistro = data.get("claveDistro");
		String claveCliente = data.get("claveCliente");
		String claveDeposito = data.get("claveDeposito");
		String dataRegistroStatus = "statusPendienteEnvio";
		String dataRegistroDeposito = "depositoOxxo_";
		String tablaInfoPedidos = "tablaConsultaPedidos";
		ArrayList<String> listaCampos = new ArrayList<String>();
		listaCampos.add("FcreaIni");
		listaCampos.add("FcreaFin");
		listaCampos.add("transferenciaRD");
		listaCampos.add("distroRD");
		listaCampos.add("clienteRD");
		listaCampos.add("statusRD");
		listaCampos.add("depositoRD");
		boolean transaction;
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest());
		RuteoDinamicoRDM rd = new RuteoDinamicoRDM(u, testCase, url);
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM_RDQA, GlobalVariables.DB_USER_RDM_RD,
				GlobalVariables.DB_PASSWORD_RDM_RDQA);
		SQLResult result;
		
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
		addStep("Establecer alg�n filtro (Transf, Distro, Cliente, Status y/o Deposito)");
		System.out.println("PASO 5");
		
		rd.establecerDatoCampo(listaCampos.get(3), claveDistro);
		rd.establecerDatoCampo(listaCampos.get(6), claveDeposito);
		
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
		
		// EJECUTAR CONSULTA PARA OBTENER NUMERO DE TRANSFERENCIA y CLAVE CLIENTE DE LA BD PARA CONSULTAR EN RDM
		
		testCase.addCodeEvidenceCurrentStep(consultaDatos);
		result = dbRdm.executeQuery(consultaDatos);
		
		transaction = result.isEmpty();
		if (!transaction) {
			claveNumTransf = result.getData(0, "TSF_NO");
			claveCliente = result.getData(0, "REFCLIENTE");
			testCase.addBoldTextEvidenceCurrentStep("TSF_NO para consultar en campo Transf: ");
			testCase.addCodeEvidenceCurrentStep("\t" + claveNumTransf);
			testCase.addBoldTextEvidenceCurrentStep("REFCLIENTE para consultar en campo Cliente: ");
			testCase.addCodeEvidenceCurrentStep("\t" + claveCliente);
			
			rd.establecerFechaCreaIniFin(fCreaIni, fCreaFin);
			rd.establecerDatoCampo(listaCampos.get(2), claveNumTransf);
			rd.establecerDatoCampo(listaCampos.get(3), claveDistro);
			rd.establecerDatoCampo(listaCampos.get(4), claveCliente);
			rd.establecerDatoPorCatalogo(listaCampos.get(5), dataRegistroStatus);
			rd.establecerDatoPorCatalogo(listaCampos.get(6), dataRegistroDeposito);
			rd.clickExecute();
			assertFalse(rd.validarTablaVacia(tablaInfoPedidos), "La tabla est� vac�a");
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
		return "Validar que los campos de filtro se"
				+ " limpien luego de dar Clic al bot�n"
				+ " \"Clear\" y se puedan editar los campos";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_021_ValidarLimpiarCampos_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}