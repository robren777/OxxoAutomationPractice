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
 * Se realizara un cambio en alguna transaccion con ADMIN_DATE que se encuentre dentro de la configuracion de los dias a procesar y se le cambiara la feca administrativa por una menor al rango configurado para posteriormente ejecutar interfaz PO21_TEMP y esta no se procese
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-085-Validar error obtenido al procesar transacciones por la PO21_TEMP con ADMIN_DATE menor al actual configurado
 * 
 * PREREQUISITOS:
1.Contar con acceso a BD TPEREP
2. Contar con transacciones de QR SPIN
3. Contar con acceso a BD WMLOG
4.Contar con acceso a Control-M
5. Contar con acceso a BD AVEBQA
 * 
 *INSUMOS
 *Informacion de transacciones de QR SPIN en BD Replica con status L  con fecha administrativa con menor a la configuracion  
 *  
 * @author Juan Gerardo Lopez
 * @date 13/Julio/2025 
 * 
 **/

public class ATC_FT_085_ValidarErrorObtenidoAlProcesarTransaccionesPorLaPO21TEMPConADMIN_DATEMenorAlActualConfigurado extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_085_ValidarErrorObtenidoAlProcesarTransaccionesPorLaPO21TEMPConADMIN_DATEMenorAlActualConfigurado_test (HashMap <String, String> data) throws Exception {
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
		        +"No se encuentren las TRX a procesar por la PO21_TEMP");
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
		addStep("Se valida que existan transacciones a procesar por la PO21_TEMP");
		//addStep("Se valida que existan transacciones a procesar por la PO21_TEMP y que alguna transaccion contenga Admin_date menor a creation_date");
		//pqrs.validTrxToProcessPO21_TEMP(status, store1, store2, store3);
		pqrs.validAdminDateMenorTPEREP(status, store1, store2, store3);
		
		System.out.println("***************PASO 03********************");
		addStep("Se ejecuta interface mediante Job de Control-M");
		pqrs.ExecJobNewStatus(Njob, ps, user, server, testCase);
		
		System.out.println("***************PASO 04********************");
		addStep("Se valida que la ejecucion de la interface PO21_TEMP sea exitosa");
		Map<String, String> logInfo = pqrs.validLogExecInDB(interfaz);
		String runid = logInfo.get("RUN_ID");
        Assert.assertNotNull(runid, "El RUN_ID no debe ser null o vacío");
		
		System.out.println("***************PASO 05********************");
		addStep("Se valida los threads lanzados por la ejecucion realizada");
		String parent_id = runid;
		Map<String, String> threadInfo = pqrs.validThreadsfromIntz(parent_id);

		String threadStatus = threadInfo.get("STATUS");
		String threadParentId = threadInfo.get("PARENT_ID");
		
		Assert.assertEquals(threadStatus, "S", "El STATUS del thread no es 'S'");
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
		return "ATC_FT_085_ValidarErrorObtenidoAlProcesarTransaccionesPorLaPO21TEMPConADMIN_DATEMenorAlActualConfigurado";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se realizara un cambio en alguna transaccion con ADMIN_DATE que se encuentre dentro de la configuracion de los dias a procesar y se le cambiara la feca administrativa por una menor al rango configurado para posteriormente ejecutar interfaz PO21_TEMP y esta no se procese";
	}
}