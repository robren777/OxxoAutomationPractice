package WEB.I123057_PagoQRspin_MVP3.scripts;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import WEB.I123057_PagoQRspin_MVP3.om.PagoQRspin_MVP3;

/**
 * 
 * DESCRIPCION: 
 * Al contar con transacciones de diferentes plazas sobre QR SPIN al ejecutarse la interfaz PO21_TEMP se valida que esas transacciones sean procesadas correctamente
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-082-Procesar transacciones de QR SPIN con diferentes plazas y validar que se realicen mediante la PO21_TEMP
 * 
 * PREREQUISITOS:
1.Contar con acceso a BD TPEREP
2. Contar con transacciones de QR SPIN
3. Contar con acceso a BD WMLOG
4.Contar con acceso a Control-M
5. Contar con acceso a BD AVEBQA
 *  
 *INSUMOS
 *Informacion de transacciones de QR SPIN en BD Replica con status L y con diferentes plazas
 *  
 * @author Juan Gerardo Lopez
 * @date 14/Julio/2025 
 * 
 **/

public class ATC_FT_082_ProcesarTransaccionesDeQrSpinConDiferentesPlazasYValidarQueSeRealicenMedianteLaPO21TEMP extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_082_ProcesarTransaccionesDeQrSpinConDiferentesPlazasYValidarQueSeRealicenMedianteLaPO21TEMP_test (HashMap <String, String> data) throws Exception {
	
		testCase.setProject_Name("MVP3 Pago con QR Spin");
		
		/* Utils**************************************************************/
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		u.quit();
		PagoQRspin_MVP3 pqrs = new PagoQRspin_MVP3(u, testCase);
		
		/*Definición de Variables*/
		String Njob = data.get("Njob");
		String ps = data.get("ps");
		String user = data.get("user");
		String server = data.get("server");
		String interfaz = data.get("interfaz");
		String status = data.get("statusPO21_TEMP");
		String store1 = data.get("store1");
		String store2 = data.get("store2");
		String store3 = data.get("store3");
		
		//Script Code Begin
		
		System.out.println("***************PASO 01********************");
		addStep("Se valida que en la tabla XXFC_MPC_GL_TPC en BD AVEBQA_OSI"
		        +" No se encuentren las TRX a procesar por la PO21_TEMP");
		Map<String, String> map = pqrs.validTrxToProcessPO21_TEMP(status, store1, store2, store3);
		String folio = map.get("FOLIO");
		String oldStatus = map.get("WM_STATUS_PO21");
		boolean validacion = pqrs.validFolioToProssNotInXXFC_MPC_GL_TPC(folio);
		if(validacion) {
		    System.out.println("El folio " + folio + " NO existe en XXFC_MPC_GL_TPC.");
		} else {
		    System.out.println("El folio " + folio + " SÍ existe en XXFC_MPC_GL_TPC.");
		    throw new NoSuchElementException("El folio " + folio + " SÍ existe en XXFC_MPC_GL_TPC.");
		}
		
		System.out.println("***************PASO 02********************");
		addStep("Se valida que la tabla TPG_TRANSACTIONS_SPIN en la BD FCTPEREPQA"
				+"Contenga TRX a procesar por la PO21_TEMP con estatus en L y diferentes plazas");
		pqrs.validTrxToProcessPO21_TEMP(status, store1, store2, store3);
		
		System.out.println("***************PASO 03********************");
		addStep("Acceder a control M y Ejecutar el job PO21_TEMP");
		pqrs.ExecJobNewStatus(Njob, ps, user, server, testCase);
				
		System.out.println("***************PASO 04********************");
		addStep("Se valida la ejecucion de la interface PO21_TEMP");
		Map<String, String> logInfo = pqrs.validLogExecInDB(interfaz);
		String runid = logInfo.get("RUN_ID");
        Assert.assertNotNull(runid, "El RUN_ID no debe ser null o vacío");
		
		System.out.println("***************PASO 05********************");
		addStep("Se valida los threads lanzados por la ejecucion realizada");
		String parent_id = runid;
		Map<String, String> threadInfo = pqrs.validThreadsfromIntz(parent_id);

		String threadStatus = threadInfo.get("STATUS");
		String threadParentId = threadInfo.get("PARENT_ID");
		
		//Assert.assertEquals(threadStatus, "S", "El STATUS del thread no es 'S'");
		Assert.assertEquals(threadParentId, parent_id, "El PARENT_ID del thread no coincide con el esperado");
		
		System.out.println("***************PASO 06********************");
		addStep("Se valida que la ejecucion de la interface cambie los estatus de las TRX a E");
		boolean cambioStatus = pqrs.validTrxProcessedByPO21_TEMP(folio, oldStatus);

		if(cambioStatus) {
		    System.out.println("El folio " + folio + " cambió de status correctamente.");
		} else {
		    System.out.println("El folio " + folio + " no cambió de status, sigue con " + oldStatus);
			throw new NoSuchElementException("El folio no cambió de status, sigue con" + oldStatus);
		}
				
		//Script Code End

	}
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1.Contar con acceso a BD TPEREP\r\n"
				+ "2. Contar con transacciones de QR SPIN\r\n"
				+ "3. Contar con acceso a BD WMLOG\r\n"
				+ "4.Contar con acceso a Control-M\r\n"
				+ "5. Contar con acceso a BD AVEBQA";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Gerardo Lopez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_082_ProcesarTransaccionesDeQrSpinConDiferentesPlazasYValidarQueSeRealicenMedianteLaPO21TEMP";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Al contar con transacciones de diferentes plazas sobre QR SPIN al ejecutarse la interfaz PO21_TEMP se valida que esas transacciones sean procesadas correctamente";
	}
}