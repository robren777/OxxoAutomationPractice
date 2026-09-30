package RDM.ruteoDinamico.consultaPedidosCambioEdo;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.RuteoDinamicoRDM;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * PREREQUISITOS: - Tener instalado el JInitiator - Acceso al aplicativo RDM
 * (VPN, Puertos) - Usar Microsoft Edge en modo Internet Explorer
 * 
 * 
 * DESCRIPCION: Validar que la pantalla muestre la informaci�n establecida de la generaci�n 
 * 				de pedidos y los botones "Volver a Enviar" y "Selec. Todos"
 * 
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_001_InformacionPedidosVolverEnviar extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_InformacionPedidosVolverEnviar_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		//String url = data.get("server");
		String url = GlobalVariables.urlRDQA;
		String fCreaIni = "";
		String fCreaFin = "";
		String tablaInfoPedidos = "tablaConsultaPedidos";
		ArrayList<String> listaBotones = new ArrayList<String>();
		listaBotones.add("btnVolverEnviar");
		listaBotones.add("btnSelecTodos");
		
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

		// VALIDACI�N DE TABLA LLENA DE INFORMACI�N Y LOS BOTONES PARA SELECCIONAR TODOS LOS PEDIDOS Y VOLVER A ENVIARLOS
		System.out.println("Validar que se muestre informaci�n de los pedidos en la tabla");

		assertTrue(rd.validarExistenciaTabla(tablaInfoPedidos), "No existe la tabla");
		assertFalse(rd.validarTablaVacia(tablaInfoPedidos), "La tabla est� vac�a");
		assertTrue(rd.validarExistenciaBotones(listaBotones), "No existe el bot�n");

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
		return "- Tener instalado el JInitiator "
				+ "- Acceso al aplicativo RDM (VPN, Puertos)"
				+ "- Usar Microsoft Edge en modo Internet Explorer";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que la pantalla muestre la informaci�n establecida"
				+ " de la generaci�n de pedidos y los botones"
				+ " \"Volver a Enviar\" y \"Selec. Todos\"";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_InformacionPedidosVolverEnviar_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
