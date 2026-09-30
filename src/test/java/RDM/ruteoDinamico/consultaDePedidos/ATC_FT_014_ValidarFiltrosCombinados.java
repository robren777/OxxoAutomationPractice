package RDM.ruteoDinamico.consultaDePedidos;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
 * DESCRIPCION: Validar que se realice filtraci�n usando m�s de un filtro y se muestre en la pantalla de 
 * 				consulta de pedidos
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_014_ValidarFiltrosCombinados extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_ValidarFiltrosCombinados_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 */
		String url = GlobalVariables.urlRDQA;
		String consultaNumTransf = data.get("queryNumTransf");
		String claveNumTransf = data.get("claveNumTransf");
		String claveDistro = data.get("claveDistro");
		String claveCliente = data.get("claveCliente");
		String claveStatus = data.get("claveStatus");
		String claveDeposito = data.get("claveDeposito");
		String campoTransf = "transferenciaRD";
		String campoDistro = "distroRD";
		String campoCliente = "clienteRD";
		String campoStatus = "statusRD";
		String campoDeposito = "depositoRD";
		String tablaInfoPedidos = "tablaConsultaPedidos";
		String fCreaIni = "";
		String fCreaFin = "";
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
		addStep("- USANDO DOS CAMPOS DE FILTRO -");
		addStep("Establecer en los campos F. Crea Ini y F. Crea Fin fechas v�lidas");
		System.out.println("PASO 4");
		
		fCreaIni = rd.obtenerFechaAnteriorConFormato(-90);
		fCreaFin = rd.obtenerFechaActualConFormato();
		rd.establecerFechaCreaIniFin(fCreaIni, fCreaFin);
		
		// PASO 5
		addStep("Establecer en los campos Status y Distro claves v�lidas");
		System.out.println("PASO 5");
		
		assertTrue(rd.validarExistenciaCampo(campoStatus), "No existe el campo");
		rd.establecerDatoCampo(campoStatus, claveStatus);
		assertTrue(rd.validarExistenciaCampo(campoDistro), "No existe el campo");
		rd.establecerDatoCampo(campoDistro, claveDistro);
		
		// PASO 6
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 6");
		
		rd.clickExecute();
		assertFalse(rd.validarTablaVacia(tablaInfoPedidos), "La tabla est� vac�a");
		
		// PASO 7
		addStep("Establecer en los campos Status y Deposito claves v�lidas");
		System.out.println("PASO 7");
		
		rd.clickClear();
		rd.establecerFechaCreaIniFin(fCreaIni, fCreaFin);
		assertTrue(rd.validarExistenciaCampo(campoStatus), "No existe el campo");
		rd.establecerDatoCampo(campoStatus, claveStatus);
		assertTrue(rd.validarExistenciaCampo(campoDeposito), "No existe el campo");
		rd.establecerDatoCampo(campoDeposito, claveDeposito);
		
		// PASO 8
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 8");
		
		rd.clickExecute();
		assertFalse(rd.validarTablaVacia(tablaInfoPedidos), "La tabla est� vac�a");
		
		// PASO 9
		addStep("Establecer en los campos Distro y Deposito claves v�lidas");
		System.out.println("PASO 9");
		
		rd.clickClear();
		rd.establecerFechaCreaIniFin(fCreaIni, fCreaFin);
		assertTrue(rd.validarExistenciaCampo(campoDeposito), "No existe el campo");
		rd.establecerDatoCampo(campoDeposito, claveDeposito);
		assertTrue(rd.validarExistenciaCampo(campoDistro), "No existe el campo");
		rd.establecerDatoCampo(campoDistro, claveDistro);
		
		// PASO 10
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 10");
		
		rd.clickExecute();
		assertFalse(rd.validarTablaVacia(tablaInfoPedidos), "La tabla est� vac�a");
		
		// PASO 11
		addStep("- USANDO TRES CAMPOS DE FILTRO -");
		addStep("Establecer en los campos Status, Distro y Deposito claves v�lidas");
		System.out.println("PASO 11");
		
		rd.clickClear();
		rd.establecerFechaCreaIniFin(fCreaIni, fCreaFin);
		assertTrue(rd.validarExistenciaCampo(campoStatus), "No existe el campo");
		rd.establecerDatoCampo(campoStatus, claveStatus);
		assertTrue(rd.validarExistenciaCampo(campoDistro), "No existe el campo");
		rd.establecerDatoCampo(campoDistro, claveDistro);
		assertTrue(rd.validarExistenciaCampo(campoDeposito), "No existe el campo");
		rd.establecerDatoCampo(campoDeposito, claveDeposito);
		
		// PASO 12
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 12");
		
		rd.clickExecute();
		assertFalse(rd.validarTablaVacia(tablaInfoPedidos), "La tabla est� vac�a");
		
		// PASO 13
		addStep("- USANDO TODOS LOS CAMPOS DE FILTRO -");
		
		// EJECUTAR CONSULTA PARA OBTENER NUMERO DE TRANSFERENCIA DE LA BD PARA CONSULTAR EN RDM
		
		testCase.addCodeEvidenceCurrentStep(consultaNumTransf);
		result = dbRdm.executeQuery(consultaNumTransf);
		
		transaction = result.isEmpty();
		if (!transaction) {
			claveNumTransf = result.getData(0, "TSF_NO");
			testCase.addBoldTextEvidenceCurrentStep("TSF_NO para consultar en campo Transf: ");
			testCase.addCodeEvidenceCurrentStep("\t" + claveNumTransf);
			
			addStep("Establecer en todos los campos claves v�lidas");
			System.out.println("PASO 13");
			
			rd.clickClear();
			rd.establecerFechaCreaIniFin(fCreaIni, fCreaFin);
			assertTrue(rd.validarExistenciaCampo(campoTransf), "No existe el campo");
			rd.establecerDatoCampo(campoTransf, claveNumTransf);
			assertTrue(rd.validarExistenciaCampo(campoDistro), "No existe el campo");
			rd.establecerDatoCampo(campoDistro, claveDistro);
			assertTrue(rd.validarExistenciaCampo(campoCliente), "No existe el campo");
			rd.establecerDatoCampo(campoCliente, claveCliente);
			assertTrue(rd.validarExistenciaCampo(campoStatus), "No existe el campo");
			rd.establecerDatoCampo(campoStatus, claveStatus);
			assertTrue(rd.validarExistenciaCampo(campoDeposito), "No existe el campo");
			rd.establecerDatoCampo(campoDeposito, claveDeposito);
		}
		
		// PASO 14
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 14");
		
		rd.clickExecute();
		assertFalse(rd.validarTablaVacia(tablaInfoPedidos), "La tabla est� vac�a");

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
		return "Validar que se realice filtraci�n usando"
				+ " m�s de un filtro y se muestre en la "
				+ "pantalla de consulta de pedidos";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_014_ValidarFiltrosCombinados_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
