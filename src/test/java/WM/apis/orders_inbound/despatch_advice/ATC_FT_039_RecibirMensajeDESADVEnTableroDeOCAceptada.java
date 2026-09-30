package WM.apis.orders_inbound.despatch_advice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;
import static org.testng.Assert.assertTrue;


import org.testng.annotations.Test;

import WM.om.OrdersInbound;
import modelo.BaseExecution;

import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

import WM.om.Tableau;
import WM.util.GlobalVariables;
import azure.annotation.AzureTestInfo;

@AzureTestInfo(

		areaPath = "I25086-Proceso End to End\\MVP2 Proceso End to End",
		iterationPath = "I25086-Proceso End to End\\Sprints MVP2\\Sprint 15",
		tags = "",
		userStoryId = 217667

)
/*
DESCRIPCIÓN:
Validar que la información del mensaje DESADV se muestr correctamente desde las tablas de Tableau hacia el Tablero End To End, mostrando todos los campos obligatorios (campos color azul en el excel) conservando la integridad de la información.
PREREQUISITOS: 
1.- Casos de prueba de insumos:
MTC_FT_031_DESADV Aceptado de OC para Tienda 
MTC_FT_032_DESADV Aceptada de OC para Cedis 
MTC_FT_033_DESADV Aceptada de OC para Punto de Trasbordo
2. Contar con OC confirmadas por ORDRSP con estatus Aceptado en el tablero
3.- Microservicio ETE DESADV desplegado y disponible.
4. Aplicación y BD de Tableau desplegados y disponibles.
@author	Sergio Robles Ramos
@date  23/02/2026
*/
public class ATC_FT_039_RecibirMensajeDESADVEnTableroDeOCAceptada extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_039_DESADVDeOCAceptadaParcialParaPuntoDeTransbordo_test (HashMap<String, String> data) throws Exception  {
		/*Utilerias (Conexiones a BD)
		*********************************************************************/
		OrdersInbound oi = new OrdersInbound(testCase);
		//Crea una instancia de chrome para la prueba
		SeleniumUtil u=new SeleniumUtil (new ChromeTest());
		//Instancia para la clase de om.Tableau
		Tableau tableau = new Tableau (testCase, u);
		//Variables para consultas de BD Tableau
		String execProc="EXEC [BDAppExt].[dbo].[SpBIp_PSG_Importar_ORDRSP_DESADV];";
		//String select_ORDRSP_HEADER="select * from tbl_stg_SQL_PSG_ORDRSP_HEADER where id_cedis in "+"("+purchaseOrder1+")"+"("+purchaseOrder2+")"+"("+purchaseOrder3+");
		String añoOC = data.get("añoOC"); 
		String mesOC = data.get("mesOC");
		String mes = data.get("mes"); //mes en letra
		String diaOC = data.get("diaOC");
		boolean validaResultBD;
		//***************************Paso 1*****************************************************************************		
		addStep("Ejecutar el siguiente caso de prueba para generar el insumo que se requiere para este escenario \r\n"
				+ "MTC_FT_031_DESADV Aceptado de OC para Tienda ");
		LocalDateTime horaInicial = tableau.obtenerHoraDesdeSysdate();
		//Se crea una instancia del caso 31
		ATC_FT_031_DESADVDeOCAceptadaParaTienda ATC_FT_031 = new ATC_FT_031_DESADVDeOCAceptadaParaTienda();
		//Incluir la evidencia del caso 31 en el caso 46
		ATC_FT_031.setTestCase(this.testCase);
		//Manda llamar la ejecución del caso 31
		ATC_FT_031.ATC_FT_031_DESADVDeOCAceptadaParaTienda_test(data);
		//Se crea una variable y se asigna el valor de una variable a nivel de la clase del caso 31 para utilizarlo en este caso 46
		String purchaseOrder1 = ATC_FT_031.purchaseOrder;
		System.out.println(purchaseOrder1);
		testCase.addTextEvidenceCurrentStep("Validar que se genero el mensaje DESADV para Tienda con estatus Aceptado");
		
		//***************************Paso 2*****************************************************************************	
		
		addStep("Ejecutar el siguiente caso de prueba para generar el insumo que se requiere para este escenario \r\n"
				+ "MTC_FT_032_DESADV Aceptado de OC para Cedis");
						
		ATC_FT_032_DESADVDeOCAceptadaParaCedis ATC_FT_032 = new ATC_FT_032_DESADVDeOCAceptadaParaCedis();
		ATC_FT_032.setTestCase(this.testCase);
		ATC_FT_032.ATC_FT_032_DESADVDeOCAceptadaParaCedis_test(data);
		String purchaseOrder2 = ATC_FT_032.purchaseOrder;
		System.out.println(purchaseOrder2);
		testCase.addTextEvidenceCurrentStep("Validar que se genero el mensaje DESADV para Cedis con estatus Aceptado");
		
		//***************************Paso 3*****************************************************************************	
		
		addStep("Ejecutar el siguiente caso de prueba para generar el insumo que se requiere para este escenario \r\n"
				+ "MTC_FT_033_DESADV Aceptado de OC para Punto de Transbordo");
								
		ATC_FT_033_DESADVDeOCAceptadaParaPuntoDeTransbordo ATC_FT_033 = new ATC_FT_033_DESADVDeOCAceptadaParaPuntoDeTransbordo();
		ATC_FT_033.setTestCase(this.testCase);
		ATC_FT_033.ATC_FT_033_DESADVDeOCAceptadaParaPuntoDeTransbordo_test(data);
		String purchaseOrder3 = ATC_FT_033.purchaseOrder;
		System.out.println(purchaseOrder3);
		testCase.addTextEvidenceCurrentStep("Validar que se genero el mensaje DESADV para Punto de Trasbordo con estatus Aceptado");
		
		//***************************Paso 4*****************************************************************************	
		
		addStep("Abrir el manejador de base de datos POSTGRESQL:\r\n"
				+ "IP: 172.26.53.100\r\n"
				+ "Puerto: 5432\r\n"
				+ "BD: terra_oxxo_rspn\r\n"
				+ "Dar clic a conectar");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se establece conexión con la DB");
		
		//***************************Paso 5*****************************************************************************
		addStep("Consutar la tabla DESADV_HEADER con el campo purchase_order para identificar las OC enviadas en estatus Aceptado\r\n"
				+ "\r\n"
				+ "Select * from desadv_header where purchase_order in () ;");
		String select_DESADV_HEADER =
		        "SELECT * FROM desadv.desadv_header " +
		        "WHERE purchase_order IN ('" + purchaseOrder1 + "', '" + purchaseOrder2 + "', '" + purchaseOrder3 + "')";

		validaResultBD = oi.queryBDPostgresql(select_DESADV_HEADER, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println ("ValidaResultBD: " + validaResultBD);
		assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se insertaron registros para las ordenes que se enviaron en el archivo JSON con mensaje DESADV");
		
		//***************************Paso 6*****************************************************************************		
		
		addStep("Consutar la tabla DESADV_DETAIL con el campo purchase_order para identificar las OC enviadas en estatus Aceptado\r\n"
				+ "\r\n"
				+ "Select * from desadv_detail where purchase_order in () ;");
		String select_DESADV_DETAIL =
		        "SELECT * FROM desadv.desadv_detail " +
		        "WHERE purchase_order IN ('" + purchaseOrder1 + "', '" + purchaseOrder2 + "', '" + purchaseOrder3 + "')";

		validaResultBD = oi.queryBDPostgresql(select_DESADV_DETAIL, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println ("ValidaResultBD: " + validaResultBD);
		assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se insertaron registros para las ordenes que se enviaron en el archivo JSON con mensaje DESADV");
		//***************************Paso 7*****************************************************************************		
		addStep("Consultar la tabla DESADV_TRANSACTION con el campo  purchase_order para identificar las OC enviadas en estatus Aceptado\r\n"
				+ "\r\n"
				+ "Select * from desadv_transaction where  purchase_order in () ;;");
		String select_DESADV_TRANSACTION =
		        "SELECT * FROM desadv.desadv_transaction " +
		        "WHERE purchase_order IN ('" + purchaseOrder1 + "', '" + purchaseOrder2 + "', '" + purchaseOrder3 + "')";

		validaResultBD = oi.queryBDPostgresql(select_DESADV_TRANSACTION, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println ("ValidaResultBD: " + validaResultBD);
		assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se inserta un registro de transaccion para cada OC con los datos de fecha y hora, id de transacción, Status de transaccion, Detalle");
		//***************************Paso 8*****************************************************************************		
		addStep("Abrir el aplicativo SQL Server con la siguiente conexión:\r\n"
				+ "Servidor: fccasubi03\r\n"
				+ "IP: 10.184.56.250\r\n" 
				+ "Base de datos SQL Server: BDAppExt\r\n"
				+ "Y hacer clic en el botón Conectar");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se establece conexión con la DB");
		//***************************Paso 9*****************************************************************************
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
		//***************************Paso 10*****************************************************************************
		addStep("Realizar la siguiente consulta en la DB de Tableau de las ordenes generadas en los casos de insumos para comprobar que los datos llegaron de manera integra a Tableau\r\n"
				+ "\r\n"
				+ "SELECT * FROM tbl_stg_SQL_PSG_DESADV_HEADER where purchase_order in () ;");
		
		String select_DESADV_HEADER_T =
			    "SELECT * FROM tbl_stg_SQL_PSG_DESADV_HEADER " +
			    "WHERE purchase_order IN ('" 
			    + purchaseOrder1 + "','" 
			    + purchaseOrder2 + "','" 
			    + purchaseOrder3 + "')";
		int intentos = 0;
		int maxIntentos = 35; // ajusta según el tiempo esperado

		do {
		    validaResultBD = oi.queryBDSQLServer(
		    		select_DESADV_HEADER_T,
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
		//testCase.addBoldTextEvidenceCurrentStep("Validar que se muestran los mismos datos que en la consulta de Postgresql de los datos generados en el paso de insumos");
		
		//***************************Paso 11*****************************************************************************
		addStep("Validar que se crearon registros con las ordenes de compras generadas en los pasos de insumos realizando las consultas");
		String select_DESADV_DETAIL_T= 
			    "SELECT * FROM tbl_stg_SQL_PSG_DESADV_DETAIL " +
			    "WHERE purchase_order IN ('" 
			    + purchaseOrder1 + "','" 
			    + purchaseOrder2 + "','" 
			    + purchaseOrder3 + "')";
		validaResultBD = oi.queryBDPostgresql(select_DESADV_DETAIL_T, oi.DB_JDBC_TABLEAU_BDAppExt, "No");
		System.out.println ("ValidaResultBD: " + validaResultBD);
		assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se muestran los mismos datos que en la consulta de Postgresql de los datos generados en el paso de insumos");
		//***************************Paso 12*****************************************************************************
		addStep("Iniciar sesion en Tableau ingresando los siguientes datos en el navegador\r\n"
				+ "URL:\r\n"
				+ "User:\r\n"
				+ "Password:");
		tableau.loginTableau(GlobalVariables.PORTAL_URL_TABLEAU, GlobalVariables.PORTAL_USER_TABLEAU, GlobalVariables.PORTAL_PASSWORD_TABLEAU);
		testCase.addBoldTextEvidenceCurrentStep("Validar que se muestra la pantalla tablero \"End to End\"");
		//***************************Paso 13*****************************************************************************
		addStep("Hacer clic en el tablero \"End to End\"");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se visualiza el Tablero End to End con la Información más actual de ORDRSP");
		//***************************Paso 14*****************************************************************************
		addStep("Realizar busqueda en el Tablero End To End de las tres Ordenes de compra generadas en los insumos Tienda, Cedis, Punto de Transbordo utilizando el campo Orden de Compra para comprobar que los datos recibidos en el mensaje ORDRSP sean vinculados en los registros de las ordenes de compra de la seccion de RMS y poder visualizar la informacion de la seccion RMS con su complemento en la seccion ORDRSP");
		tableau.esperarActualizacionTableau(horaInicial);
		tableau.seleccionarMensajeDesadv();
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
		return "1.- Casos de prueba de insumos:\r\n"
				+ "MTC_FT_031_DESADV Aceptado de OC para Tienda \r\n"
				+ "MTC_FT_032_DESADV Aceptada de OC para Cedis \r\n"
				+ "MTC_FT_033_DESADV Aceptada de OC para Punto de Trasbordo\r\n"
				+ "2. Contar con OC confirmadas por ORDRSP con estatus Aceptado en el tablero\r\n"
				+ "3.- Microservicio ETE DESADV desplegado y disponible.\r\n"
				+ "4. Aplicación y BD de Tableau desplegados y disponibles.";
	}

	@Override
	public String setTestDescription() {
		
		return "Validar que la información del mensaje DESADV se muestr correctamente desde las tablas de Tableau hacia el Tablero End To End, mostrando todos los campos obligatorios (campos color azul en el excel) conservando la integridad de la información.";
	}

	@Override
	public String setTestDesigner() {
		return "Sergio Robles Ramos";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_039_DESADVDeOCAceptadaParcialParaPuntoDeTransbordo";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
