package WM.apis.orders_inbound.despatch_advice;

import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.OrdersInbound;
import WM.om.Tableau;
import WM.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

@AzureTestInfo(

		areaPath = "I25086-Proceso End to End\\MVP2 Proceso End to End",
		iterationPath = "I25086-Proceso End to End\\Sprints MVP2\\Sprint 15",
		tags = "",
		userStoryId = 217668

)
/*
DESCRIPCIÓN:
Visualizar en el tablero End to End todas las citas que le correspondan a una OC para Punto de Transbordo visualizando las entregas parciales y validar que en el total de entregas sea el total correspondiente y no se duplique la cantidad total solicitada en la OC.
Validar que en cada cita se cambie el valor de Cita, Cajas Pend Entrega y Cajas Diferencia, el resto de la información permanecera igual.
*Cajas Pend Entrega = Cajas Ordenadas -  (la cantidad para entregar de la cita actual + la cantidad para entregar de las citas anteriores)
*Cajas Diferencia = Cajas Ordenadas - Cajas para Entrega
PREREQUISITOS:
1.- Debera existir información previa de ORDRSP o RMS en el tablero, para asociar el DESADV a esa orden de Punto de Transbordo.
2.- Caso de prueba de insumos
MTC_FT_033_DESADV de OC Aceptada para Punto de Transbordo
3. Contar con OC confirmadas por ORDRSP con estatus Aceptado en el tablero
4.- Microservicio ETE DESADV desplegado y disponible.
5. Aplicación y BD de Tableau desplegados y disponibles.
6.- Se requiere enviar un mensaje ORDRSP ACEPTADO para Punto de Trasbordo de OC Preexistente de RMS en Tableau de OXXO.
7.- Se requiere enviar varios mensajes DESADV's de ORDRSP ACEPTADO de OC para Punto de Trasbordo Preexistente de RMS en Tableau de OXXO. La OC debe contar con almenos 2 Items y deberan generarse 2 o mas citas para los DESADV parciales:
7.1.- En la primer cita se deberan entregar algunos items con cantidades parciales o completas.
7.2.- Y en la segunda cita o mas citas se deberan entregar el resto de los items con el resto de las cantidades faltantes para completar la orden de compra. 
@author	Sergio Robles Ramos
@date  17/03/2026
*/
public class ATC_FT_048_RegistroDeVariasCitasDeUnaOCPuntoDeTransbordoEnEltablero extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_048_RegistroDeVariasCitasDeUnaOCPuntoDeTransbordoEnEltablero_test (HashMap<String, String> data) throws Exception {
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
				+ "MTC_FT_032_DESADV Aceptado de OC para Punto de Transbordo");
		LocalDateTime horaInicial = tableau.obtenerHoraDesdeSysdate();				
		ATC_FT_033_DESADVDeOCAceptadaParaPuntoDeTransbordo ATC_FT_033 = new ATC_FT_033_DESADVDeOCAceptadaParaPuntoDeTransbordo();
		ATC_FT_033.setTestCase(this.testCase);
		ATC_FT_033.ATC_FT_033_DESADVDeOCAceptadaParaPuntoDeTransbordo_test(data);
		String purchaseOrder1 = ATC_FT_033.purchaseOrder;
		System.out.println(purchaseOrder1);
		testCase.addTextEvidenceCurrentStep("Validar que se genero el mensaje DESADV para Cedis con estatus Aceptado");
		//***************************Paso 2*****************************************************************************	
		addStep("Abrir el manejador de base de datos POSTGRESQL:\r\n"
				+ "IP: 172.26.53.100\r\n"
				+ "Puerto: 5432\r\n"
				+ "BD: terra_oxxo_rspn\r\n"
				+ "Dar clic a conectar");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se establece conexión con la DB");
		//***************************Paso 3*****************************************************************************
		addStep("Consutar la tabla DESADV_HEADER con el campo purchase_order para identificar las OC enviadas en estatus Aceptado\r\n"
				+ "\r\n"
				+ "Select * from desadv_header where purchase_order in () ;");
		String select_DESADV_HEADER =
		        "SELECT * FROM desadv.desadv_header " +
		        "WHERE purchase_order IN ('" + purchaseOrder1 + "')";

		validaResultBD = oi.queryBDPostgresql(select_DESADV_HEADER, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println ("ValidaResultBD: " + validaResultBD);
		assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se insertaron registros para las ordenes que se enviaron en el archivo JSON con mensaje DESADV");
		
		//***************************Paso 4*****************************************************************************		
		addStep("Consutar la tabla DESADV_DETAIL con el campo purchase_order para identificar las OC enviadas en estatus Aceptado\r\n"
				+ "\r\n"
				+ "Select * from desadv_detail where purchase_order in () ;");
		String select_DESADV_DETAIL =
		        "SELECT * FROM desadv.desadv_detail " +
		        "WHERE purchase_order IN ('" + purchaseOrder1 + "')";

		validaResultBD = oi.queryBDPostgresql(select_DESADV_DETAIL, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println ("ValidaResultBD: " + validaResultBD);
		assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se insertaron registros para las ordenes que se enviaron en el archivo JSON con mensaje DESADV");
		
		//***************************Paso 5*****************************************************************************		
		addStep("Consultar la tabla DESADV_TRANSACTION con el campo  purchase_order para identificar las OC enviadas en estatus Aceptado\r\n"
				+ "\r\n"
				+ "Select * from desadv_transaction where  purchase_order in () ;;");
		String select_DESADV_TRANSACTION =
		        "SELECT * FROM desadv.desadv_transaction " +
		        "WHERE purchase_order IN ('" + purchaseOrder1 + "')";

		validaResultBD = oi.queryBDPostgresql(select_DESADV_TRANSACTION, oi.DB_JDBC_POSTGRES_TERRA_OXXO_RSPN, "No");
		System.out.println ("ValidaResultBD: " + validaResultBD);
		assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se inserta un registro de transaccion para cada OC con los datos de fecha y hora, id de transacción, Status de transaccion, Detalle");
		//***************************Paso 6*****************************************************************************		
		addStep("Abrir el aplicativo SQL Server con la siguiente conexión:\r\n"
				+ "Servidor: fccasubi03\r\n"
				+ "IP: 10.184.56.250\r\n" 
				+ "Base de datos SQL Server: BDAppExt\r\n"
				+ "Y hacer clic en el botón Conectar");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se establece conexión con la DB");
		//***************************Paso 7*****************************************************************************
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
		String select_DESADV_DETAIL_T= 
			    "SELECT * FROM tbl_stg_SQL_PSG_DESADV_DETAIL " +
			    "WHERE purchase_order IN ('" 
			    + purchaseOrder1 + "')";
		validaResultBD = oi.queryBDPostgresql(select_DESADV_DETAIL_T, oi.DB_JDBC_TABLEAU_BDAppExt, "No");
		System.out.println ("ValidaResultBD: " + validaResultBD);
		assertTrue (validaResultBD,"No se obtuvo resultado esperado en BD");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se muestran los mismos datos que en la consulta de Postgresql de los datos generados en el paso de insumos");
		//***************************Paso 10*****************************************************************************
		addStep("Iniciar sesion en Tableau ingresando los siguientes datos en el navegador\r\n"
				+ "URL:\r\n"
				+ "User:\r\n"
				+ "Password:");
		tableau.loginTableau(GlobalVariables.PORTAL_URL_TABLEAU, GlobalVariables.PORTAL_USER_TABLEAU, GlobalVariables.PORTAL_PASSWORD_TABLEAU);
		testCase.addBoldTextEvidenceCurrentStep("Validar que se muestra la pantalla tablero \"End to End\"");
		//***************************Paso 11*****************************************************************************
		addStep("Hacer clic en el tablero \"End to End\"");
		testCase.addBoldTextEvidenceCurrentStep("Validar que se visualiza el Tablero End to End con la Información más actual de ORDRSP");
		//***************************Paso 12*****************************************************************************
		addStep("Consultar el tablero End to End que se visualicen varios registros por las diferentes citas que contiene la OC dando visibilidad de las entregas parciales");
		tableau.esperarActualizacionTableau(horaInicial);
		tableau.seleccionarMensajeDesadv();
		tableau.seleccionarAñoCombo(añoOC);
		tableau.seleccionarMesCombo(mesOC,mes);
		tableau.seleccionarDiaCombo(diaOC);
		tableau.busquedaTableauDESADV(purchaseOrder1);
		tableau.expandirdetalleOrdenes();
		testCase.addBoldTextEvidenceCurrentStep("Validar que se muestre la información del mensaje DESADV en el tablero en los siguientes campos: \r\n"
				+ "- ID. Entrega\r\n"
				+ "- Entrega\r\n"
				+ "- Cita\r\n"
				+ "- Ruta\r\n"
				+ "- Fecha Entrega\r\n"
				+ "- UUID\r\n"
				+ "- Factura\r\n"
				+ "- Posicion Ent\r\n"
				+ "- UM\r\n"
				+ "- Lote\r\n"
				+ "- Caducidad\r\n"
				+ "- Cajas Ordenadas\r\n"
				+ "- Cajas para Entrega\r\n"
				+ "- Cajas Pend Entrega\r\n"
				+ "- Cajas Diferencia");
		//***************************Paso 13*****************************************************************************
		addStep("Consultar el valor del campo \"Cajas Ordenadas\" en todas las citas de la OC contenga la misma cantidad");
		tableau.cajasOrdenadas();
		testCase.addBoldTextEvidenceCurrentStep("Validar que se visualice la misma cantidad en el campo \"Cajas Ordenadas\" en todas las citas de la OC");
		//***************************Paso 14*****************************************************************************
		addStep("Consultar el valor del campo \"Cajas Pend Entrega\" sea igual a la siguiente operación:\r\n"
				+ "Cajas Ordenadas -  (la cantidad de \"Cajas para Entrega\" de la cita actual + la cantidad \"Cajas para Entrega\" de las citas anteriores)");
		tableau.cajasPendEntrega();
		testCase.addBoldTextEvidenceCurrentStep("Validar que en cada cita se realice la operación correctamente para ir visualizando la \"Cajas Pend Enttrega\" y en la última cita se muestre el valor 0 (ya no hay productos pendientes por entregar)");
		//***************************Paso 15*****************************************************************************
		addStep("Consultar el valor del campo \"Cajas Diferencia\" sea igual a la siguiente operación:\r\n"
				+ "Cajas Ordenadas - Cajas para Entrega");
		tableau.cajasDiferencia();
		testCase.addBoldTextEvidenceCurrentStep("Validar que en cada cita se realice la operación correctamente para ir visualizando la \"Cajas Diferencia\"");
		//***************************Paso 16*****************************************************************************
		addStep("Consultar que no se duplique o sea diferente las \"Cajas Ordenadas\" vs la sumatoria de las \"Cajas para Entrega\"");
		tableau.cajasOrdenadasEntrega();
		testCase.addBoldTextEvidenceCurrentStep("Validar realizando una suma de las cantidades para entrega de las citas totales de la OC de como resultado el valor de \"Cajas Ordenadas\"");
		tableau.logoutTableau();
	}

	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setPrerequisites() {
		return "1.- Debera existir información previa de ORDRSP o RMS en el tablero, para asociar el DESADV a esa orden de Punto de Transbordo.\r\n"
				+ "2.- Caso de prueba de insumos\r\n"
				+ "MTC_FT_033_DESADV de OC Aceptada para Punto de Transbordo\r\n"
				+ "3. Contar con OC confirmadas por ORDRSP con estatus Aceptado en el tablero\r\n"
				+ "4.- Microservicio ETE DESADV desplegado y disponible.\r\n"
				+ "5. Aplicación y BD de Tableau desplegados y disponibles.\r\n"
				+ "6.- Se requiere enviar un mensaje ORDRSP ACEPTADO para Punto de Trasbordo de OC Preexistente de RMS en Tableau de OXXO.\r\n"
				+ "7.- Se requiere enviar varios mensajes DESADV's de ORDRSP ACEPTADO de OC para Punto de Trasbordo Preexistente de RMS en Tableau de OXXO. La OC debe contar con almenos 2 Items y deberan generarse 2 o mas citas para los DESADV parciales:\r\n"
				+ "7.1.- En la primer cita se deberan entregar algunos items con cantidades parciales o completas.\r\n"
				+ "7.2.- Y en la segunda cita o mas citas se deberan entregar el resto de los items con el resto de las cantidades faltantes para completar la orden de compra.";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Visualizar en el tablero End to End todas las citas que le correspondan a una OC para Punto de Transbordo visualizando las entregas parciales y validar que en el total de entregas sea el total correspondiente y no se duplique la cantidad total solicitada en la OC.\r\n"
				+ "Validar que en cada cita se cambie el valor de Cita, Cajas Pend Entrega y Cajas Diferencia, el resto de la información permanecera igual.\r\n"
				+ "*Cajas Pend Entrega = Cajas Ordenadas -  (la cantidad para entregar de la cita actual + la cantidad para entregar de las citas anteriores)\r\n"
				+ "*Cajas Diferencia = Cajas Ordenadas - Cajas para Entrega";
	}

	@Override
	public String setTestDesigner() {
		return "Sergio Robles Ramos";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_048_RegistroDeVariasCitasDeUnaOCPuntoDeTransbordoEnEltablero";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
