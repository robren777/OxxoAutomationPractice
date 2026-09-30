package RDM.ruteoDinamico.consultaDePedidos;

import static org.testng.Assert.assertFalse;

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
 * DESCRIPCION: Validar que se realice filtraci�n por n�mero de transferencia y se muestre en la pantalla de 
 * 				consulta de pedidos
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_012_ValidarFiltroPorNumTransferencia extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_ValidarFiltroPorNumTransferencia_test(HashMap<String, String> data) throws Exception{
		/*
		 * VARIABLES
		 */
		String url = GlobalVariables.urlRDQA;
		String consultaNumTransf = data.get("queryNumTransf");
		String fCreaIni = "";
		String fCreaFin = "";
		String claveNumTransf;
		String campo = "transferenciaRD";
		String tablaInfoPedidos = "tablaConsultaPedidos";
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
		addStep("Establecer en el campo Transf un n�mero de Transferencia v�lido (editando el campo)");
		System.out.println("PASO 5");
		
		// EJECUTAR CONSULTA PARA OBTENER NUMERO DE TRANSFERENCIA DE LA BD PARA CONSULTAR EN RDM
		
		testCase.addCodeEvidenceCurrentStep(consultaNumTransf);
		result = dbRdm.executeQuery(consultaNumTransf);
		
		transaction = result.isEmpty();
		if (!transaction) {
			claveNumTransf = result.getData(0, "TSF_NO");
			testCase.addBoldTextEvidenceCurrentStep("TSF_NO para consultar en campo Transf: ");
			testCase.addCodeEvidenceCurrentStep("\t" + claveNumTransf);
			rd.establecerDatoCampo(campo, claveNumTransf);
		}
		
		// PASO 6
		addStep("Clic en el bot�n \"Execute\"");
		System.out.println("PASO 6");
		
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
		return "Validar que se realice filtraci�n "
				+ "por n�mero de transferencia y se muestre "
				+ "en la pantalla de consulta de pedidos";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_012_ValidarFiltroPorNumTransferencia_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
