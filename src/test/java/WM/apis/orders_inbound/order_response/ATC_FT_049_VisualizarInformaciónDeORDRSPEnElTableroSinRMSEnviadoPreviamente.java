package WM.apis.orders_inbound.order_response;

import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import WM.om.Tableau;
import WM.om.OrdersInbound;
import WM.util.GlobalVariables;
import azure.annotation.AzureTestInfo;

@AzureTestInfo(

		areaPath = "I25086-Proceso End to End\\MVP2 Proceso End to End",
		iterationPath = "I25086-Proceso End to End\\Sprints MVP2\\Sprint 9",
		tags = "",
		userStoryId = 217684

)
/*
DESCRIPCIÓN:
Validar que los campos del Tablero End To End de la seccion de RMS se actualicen con los datos recibidos en el mensaje ORDRSP cuando no exista en la seccion de RMS la orden recibida en el mensaje ORDRSP
PREREQUISITOS: 
1.- Realizar busqueda de ordenes existentes (Seccion RMS) en el Tablero End to End para generar tres numeros de Ordenes que no existan y utilizarlas en la generacion de insumos
2.- Caso de prueba de insumos
MTC_FT_033_ORDRSP Aceptada de OC para Tienda
MTC_FT_036_ORDRSP Aceptada de OC para Cedis
MTC_FT_039_ORDRSP Aceptada de OC para Punto de Transbordo
@author	Sergio Robles Ramos
@date  09/12/2025
*/
public class ATC_FT_049_VisualizarInformaciónDeORDRSPEnElTableroSinRMSEnviadoPreviamente extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_049_VisualizarInformaciónDeORDRSPEnElTableroSinRMSEnviadoPreviamente_test (HashMap<String, String> data) throws Exception {
		/*Utilerias (Conexiones a BD)
		*********************************************************************/
		OrdersInbound oi = new OrdersInbound(testCase);
		//Crea una instancia de chrome para la prueba
		SeleniumUtil u=new SeleniumUtil (new ChromeTest());
		//Instancia para la clase de om.Tableau
		Tableau tableau = new Tableau (testCase, u);
		// ahora sí
		//Variables para consultas de BD Tableau
		String execProc="EXEC [BDAppExt].[dbo].[SpBIp_PSG_Importar_ORDRSP_DESADV];";
		//String select_ORDRSP_HEADER="select * from tbl_stg_SQL_PSG_ORDRSP_HEADER where id_cedis in "+"("+purchaseOrder1+")"+"("+purchaseOrder2+")"+"("+purchaseOrder3+");
		String añoOC = data.get("añoOC"); 
		String mesOC = data.get("mesOC");
		String mes = data.get("mes"); //mes en letra
		String diaOC = data.get("diaOC");
		boolean validaResultBD;
		
		//***************************Paso 1*****************************************************************************		
		addStep("Ejecutar el siguiente caso de prueba para generar el insumo que se requiere para este escenario considerando enviar un numero de Orden de compra que no exista en la DB de Tableaur\n"
				+ "\r\n"
				+ "MTC_FT_033 ORDRSP Aceptado de OC para Tienda");
		LocalDateTime horaInicial = tableau.obtenerHoraDesdeSysdate();
		//Se crea una instancia del caso 31
		ATC_FT_033_ORDRSPAceptadaDeOCParaTienda ATC_FT_033 = new ATC_FT_033_ORDRSPAceptadaDeOCParaTienda();
		//Incluir la evidencia del caso 31 en el caso 46
		ATC_FT_033.setTestCase(this.testCase);
		//Manda llamar la ejecución del caso 31
		ATC_FT_033.ATC_FT_033_ORDRSPAceptadaDeOCParaTienda_test(data);
		//Se crea una variable y se asigna el valor de una variable a nivel de la clase del caso 31 para utilizarlo en este caso 46
		String purchaseOrder1 = ATC_FT_033.purchaseOrder;
		System.out.println(purchaseOrder1);
		
		//***************************Paso 2*****************************************************************************		
		addStep("Ejecutar el siguiente caso de prueba para generar el insumo que se requiere para este escenario considerando enviar un numero de Orden de compra que no exista en la DB de Tableau\r\n"
				+ "\r\n"
				+ "MTC_FT_036 ORDRSP Aceptado de OC para Cedis");
						
		ATC_FT_036_ORDRSPAceptadaDeOCParaCedis ATC_FT_036 = new ATC_FT_036_ORDRSPAceptadaDeOCParaCedis();
		ATC_FT_036.setTestCase(this.testCase);
		ATC_FT_036.ATC_FT_036_ORDRSPAceptadaDeOCParaCedis_test(data);
		String purchaseOrder2 = ATC_FT_036.purchaseOrder;
		System.out.println(purchaseOrder2);
		
		//***************************Paso 3*****************************************************************************		
		
		addStep("Ejecutar el siguiente caso de prueba para generar el insumo que se requiere para este escenario considerando enviar un numero de Orden de compra que no exista en la DB de Tableau\r\n"
				+ "\r\n"
				+ "MTC_FT_039 ORDRSP Aceptada de OC para Punto de Trasbordois");
								
		ATC_FT_039_ORDRSPAceptadaDeOCParaPuntoDeTransbordo ATC_FT_039 = new ATC_FT_039_ORDRSPAceptadaDeOCParaPuntoDeTransbordo();
		ATC_FT_039.setTestCase(this.testCase);
		ATC_FT_039.ATC_FT_039_ORDRSPAceptadaDeOCParaPuntoDeTransbordo_test(data);
		String purchaseOrder3 = ATC_FT_039.purchaseOrder;
		System.out.println(purchaseOrder3);
		//***************************Paso 4*****************************************************************************		
		addStep("Abrir el manejador de base de datos postgresql, Ingresar usuario, contraseña, y la conexión indicada en los datos de prueba, Dar clic a conectar.");
		testCase.addTextEvidenceCurrentStep("Validar que se establece conexión con la DB");

		//***************************Paso 5*****************************************************************************		
		addStep("Realizar la siguiente consulta en la DB de Postgresql de las ordenes generadas en los casos de insumos para comprobar los datos que seran enviados a Tableau\r\n"
				+ "Select * from ordrsp.ordrsp_header where orderid in () ;");
		String select_ORDRSP_HEADER="SELECT * FROM ordrsp.ordrsp_header " +
			    "WHERE purchase_order IN (" + purchaseOrder1 + ", " + purchaseOrder2 + ", " + purchaseOrder3 + ")";

		validaResultBD = oi.queryBDPostgresql(select_ORDRSP_HEADER, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println ("ValidaResultBD: " + validaResultBD);
		assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");
		testCase.addTextEvidenceCurrentStep("Validar que se muestran los datos generados en el paso de insumos");
		//***************************Paso 6*****************************************************************************		
		addStep("Realizar la siguiente consulta en la DB de Postgresql de las ordenes generadas en los casos de insumos para comprobar los datos que seran enviados a Tableau\r\n"
				+ "Select * from ordrsp.ordrsp_detail where orderid in () ;");
		String select_ORDRSP_DETAILS="SELECT * FROM ordrsp.ordrsp_detail " +
			   "WHERE purchase_order IN (" + purchaseOrder1 + ", " + purchaseOrder2 + ", " + purchaseOrder3 + ")";

		validaResultBD = oi.queryBDPostgresql(select_ORDRSP_DETAILS, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println ("ValidaResultBD: " + validaResultBD);
		assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");	
		testCase.addTextEvidenceCurrentStep("Validar que se muestran los datos generados en el paso de insumos");
		//***************************Paso 7*****************************************************************************		
		
		addStep("Abrir el manejador de base de datos Tableau, Ingresar usuario, contraseña, y la conexión indicada en los datos de prueba, Dar clic a conectar");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se establece conexión con la DB");
		
		//***************************Paso 8*****************************************************************************
		addStep("Ejecutar el proceso de extraccion de datos Tableau que toma los datos de la DB postgresql de las tablas ordrsp_header y ordrsp_details y las inserta en la DB de Tableau");

		String cadenaConexion = oi.DB_JDBC_TABLEAU_BDAppExt;
		int indexAmp = cadenaConexion.lastIndexOf('&');

		if (indexAmp != -1) {
		    testCase.addTextEvidenceCurrentStep(
		        cadenaConexion.substring(0, indexAmp)
		    );
		} else {
		    // Si no existe &, muestra la cadena completa
		    testCase.addTextEvidenceCurrentStep(cadenaConexion);
		}

		// imprimir lo que se ejecutará en la BD
		System.out.println("Query a ejecutar en BD:");
		System.out.println(execProc);
		testCase.addTextEvidenceCurrentStep("Query ejecutado: " + execProc);

		try {

		    Connection conn = DriverManager.getConnection(oi.DB_JDBC_TABLEAU_BDAppExt);
		    Statement stmt = conn.createStatement();

		    // Timeout de 2 horas
		    stmt.setQueryTimeout(7200);

		    ResultSet rs = stmt.executeQuery(execProc);

		    validaResultBD = rs.next();

		} catch (SQLException e) {

		    System.out.println("Error o timeout en exec: " + e.getMessage());

		    assertTrue(false, "El proceso de extracción excedió las 2 horas o falló");

		}
		System.out.println("ValidaResultBD: " + validaResultBD);

		assertTrue(validaResultBD, "No se obtuvo resultado esperado en BD");

		testCase.addBoldTextEvidenceCurrentStep("Validar que el proceso de extraccion finaliza con exito");
		
		//***************************Paso 9*****************************************************************************
		addStep("Validar que se crearon registros con las ordenes de compras generadas en los pasos de insumos realizando las consultas");
		
		String select_ORDRSP_HEADER_T =
			    "SELECT * FROM tbl_stg_SQL_PSG_ORDRSP_HEADER " +
			    "WHERE purchase_order IN ('" 
			    + purchaseOrder1 + "','" 
			    + purchaseOrder2 + "','" 
			    + purchaseOrder3 + "');";

		int intentos = 0;
		int maxIntentos = 35; // ajusta según el tiempo esperado

		do {
		    validaResultBD = oi.queryBDSQLServer(
		            select_ORDRSP_HEADER_T,
		            oi.DB_JDBC_TABLEAU_BDAppExt,
		            "No"
		    );

		    System.out.println("ValidaResultBD: " + validaResultBD);

		    if (!validaResultBD) {
		        Thread.sleep(60000); // espera 1 minuto entre intentos
		    }

		    intentos++;

		} while (!validaResultBD && intentos < maxIntentos);

		assertTrue(validaResultBD, "No se obtuvo resultado esperado en BD");
		//ValidaResultBD = oi.queryBDSQLServer(select_ORDRSP_HEADER, oi.DB_JDBC_TABLEAU_BDAppExt, "No"); System.out.println ("ValidaResultBD: " + ValidaResultBD); assertTrue (ValidaResultBD,"No se obtuvo resultado esperado en BD");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se muestran los mismos datos que en la consulta de Postgresql de los datos generados en el paso de insumos");
		
		//***************************Paso 10*****************************************************************************
		addStep("Iniciar sesion en Tableau ingresando los siguientes datos en el navegador\r\n"
				+ "URL:\r\n"
				+ "User:\r\n"
				+ "Password:");
		tableau.loginTableau(GlobalVariables.PORTAL_URL_TABLEAU, GlobalVariables.PORTAL_USER_TABLEAU, GlobalVariables.PORTAL_PASSWORD_TABLEAU);
		//***************************Paso 11*****************************************************************************
		addStep("Realizar busqueda en el Tablero End To End de las tres Ordenes de compra generadas en los insumos Tienda, Cedis, Punto de Transbordo utilizando el campo Orden de Compra para comprobar que los datos recibidos en el mensaje ORDRSP sean vinculados en los registros de las ordenes de compra de la seccion de RMS y poder visualizar la informacion de la seccion RMS con su complemento en la seccion ORDRSP");
		tableau.esperarActualizacionTableau(horaInicial);
		tableau.seleccionarMensajeORDRSP();
		tableau.seleccionarAñoCombo(añoOC);
		tableau.seleccionarMesCombo(mesOC, mes);
		tableau.seleccionarDiaCombo(diaOC);
		tableau.busquedaTableau(purchaseOrder1, purchaseOrder2, purchaseOrder3);
		tableau.expandirdetalleOrdenes();
		tableau.logoutTableau();
		testCase.addBoldTextEvidenceCurrentStep("Se valida que los datos del mensaje ORDRSP se registran en su seccion correspondiente y que se vinculan a los datos de la seccion RMS complementando sus registros y se visualice la informacion de la seccion RMS con su complemento en la seccion ORDRSP");
	
	}

	@Override
	public void beforeTest() {
		
		
	}

	@Override
	public String setPrerequisites() {
		return "1.- Realizar busqueda de ordenes existentes (Seccion RMS) en el Tablero End to End para generar tres numeros de Ordenes que no existan y utilizarlas en la generacion de insumos\r\n"
				+ "\r\n"
				+ "2.- Caso de prueba de insumos\r\n"
				+ "MTC_FT_033_ORDRSP Aceptada de OC para Tienda\r\n"
				+ "MTC_FT_036_ORDRSP Aceptada de OC para Cedis\r\n"
				+ "MTC_FT_039_ORDRSP Aceptada de OC para Punto de Transbordo";
	}

	@Override
	public String setTestDescription() {
		
		return "Validar que los campos del Tablero End To End de la seccion de RMS se actualicen con los datos recibidos en el mensaje ORDRSP cuando no exista en la seccion de RMS la orden recibida en el mensaje ORDRSP";
	}

	@Override
	public String setTestDesigner() {
		
		return "Sergio Robles Ramos";
	}

	@Override
	public String setTestFullName() {
		
		return "ATC_FT_049_VisualizarInformaciónDeORDRSPEnElTableroSinRMSEnviadoPreviamente";
	}

	@Override
	public String setTestInstanceID() {
		
		return null;
	}
}