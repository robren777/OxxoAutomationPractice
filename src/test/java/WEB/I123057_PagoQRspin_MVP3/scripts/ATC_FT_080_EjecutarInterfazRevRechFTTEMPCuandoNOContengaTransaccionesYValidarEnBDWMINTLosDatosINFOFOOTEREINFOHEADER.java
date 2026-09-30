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
 * Se valida que el cambio en los campos mostrados en la BD con respecto a los archivos de conciliacion de Reversos se muestren con las reglas de negocio correspondientes
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_080-Ejecutar interfaz RevRechFT_TEMP cuando NO contenga transacciones y validar en BD WMINT los datos INFO_FOOTER e INFO_HEADER
 * 
 * PREREQUISITOS:
1. Contar con acceso a Control-M
2. Contar con el job runFT_SINTARJ_SPIN en ordenamiento para su ejecucion
3. Contar con apoyo para el acceso y obtencion del archivo de conciliacion en el buzon FTP
4. Contar con acceso a BD WMINT
5.Contar con acceso a la BD WMLOG
6. Contar con acceso a la BD TPEREP
7. No se debe de contar con transacciones rechazadas o reversadas realizadas por QR SPIN en el dia que se obtendra el archivo
 *  
 *
 *INSUMOS
 *Informacion de transacciones Reversadas de QR SPIN en BD Replica con status L
 *
 * @author Juan Gerardo Lopez
 * @date 12/Julio/2025 
 * 
 **/

public class ATC_FT_080_EjecutarInterfazRevRechFTTEMPCuandoNOContengaTransaccionesYValidarEnBDWMINTLosDatosINFOFOOTEREINFOHEADER extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_080_EjecutarInterfazRevRechFTTEMPCuandoNOContengaTransaccionesYValidarEnBDWMINTLosDatosINFOFOOTEREINFOHEADER_test (HashMap <String, String> data) throws Exception {
	
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
		
		//Script Code Begin
		
		//PRE-REQUISITO
		System.out.println("***************PRE-REQUISITO********************");
		addStep("Validar que NO se encuentre informacion a procesar para ejecucion de JOB con WM_CODE = 102");
		pqrs.validRevNoToProcessFT_TEMP();
		
		System.out.println("***************PASO 01********************");
		addStep("Acceder a control M y Ejecutar el job runFT_REV_SINTARJ");
		pqrs.ExecJobNewStatus(Njob, ps, user, server, testCase);
		
		System.out.println("***************PASO 02********************");
		addStep("Validar que el job se ejecuto correctamente en el log de BD");
        Map<String, String> logInfo = pqrs.validLogExecInDB(interfaz); 
        String runid = logInfo.get("RUN_ID");
        String status = logInfo.get("STATUS");
        //Assert.assertEquals(status, "S", "El STATUS del primer resultado no es 'S'");
        Assert.assertNotNull(runid, "El RUN_ID no debe ser null o vacío");
		
		System.out.println("***************PASO 03********************");
		addStep("Validar que se vea reflejado el archivo en la BD WMINT sobre los reversos");
		Map<String, String> valores = pqrs.getInfoHeaderFooterFromWmIntFT_TEMP(runid);

		//VALIDACIONES INFO_HEADER
		String infoHeader = valores.get("INFO_HEADER");
		Assert.assertNotNull(infoHeader, "INFO_HEADER es null");
		Assert.assertFalse(infoHeader.trim().isEmpty(), "INFO_HEADER está vacío");

		String[] headerParts = infoHeader.split("\\|");
		Assert.assertEquals(headerParts.length, 4, "INFO_HEADER debe contener 4 elementos");

		String fecha = headerParts[0];
		String hora = headerParts[1];
		String entidad = headerParts[2];
		String cantidadRegistros = headerParts[3];

		Assert.assertTrue(fecha.matches("\\d{8}"), "FECHA no tiene el formato correcto (yyyyMMdd): " + fecha);
		Assert.assertTrue(hora.matches("\\d{6}"), "HORA no tiene el formato correcto (HHmmss): " + hora);
		Assert.assertTrue(entidad.matches("[A-Z]+"), "ENTIDAD debe contener solo mayúsculas: " + entidad);
		Assert.assertTrue(cantidadRegistros.matches("\\d+"), "CANTIDAD_REGISTROS no es un número válido: " + cantidadRegistros);

		testCase.addBoldTextEvidenceCurrentStep(infoHeader);

		//VALIDACIONES INFO_FOOTER
		String infoFooter = valores.get("INFO_FOOTER");
		Assert.assertNotNull(infoFooter, "INFO_FOOTER es null");
		Assert.assertFalse(infoFooter.trim().isEmpty(), "INFO_FOOTER está vacío");

		String[] footerParts = infoFooter.split("\\|");
		Assert.assertEquals(footerParts.length, 2, "INFO_FOOTER debe contener 2 elementos");

		String leftValue = footerParts[0];
		String rightValue = footerParts[1];

		Assert.assertTrue(leftValue.matches("\\d{14}"), "El primer valor de INFO_FOOTER no tiene 14 dígitos: " + leftValue);
		Assert.assertTrue(rightValue.matches("\\d{14}"), "El segundo valor de INFO_FOOTER no tiene 14 dígitos: " + rightValue);

		testCase.addBoldTextEvidenceCurrentStep(infoFooter);
		
		//Script Code End
	}
	
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "\"1. Contar con acceso a Control-M\r\n"
				+ "2. Contar con el job runFT_SINTARJ_SPIN en ordenamiento para su ejecucion\r\n"
				+ "3. Contar con apoyo para el acceso y obtencion del archivo de conciliacion en el buzon FTP\r\n"
				+ "4. Contar con acceso a BD WMINT\r\n"
				+ "5.Contar con acceso a la BD WMLOG\r\n"
				+ "6. Contar con acceso a la BD TPEREP\r\n"
				+ "7. No se debe de contar con transacciones rechazadas o reversadas realizadas por QR SPIN en el dia que se obtendra el archivo";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Gerardo Lopez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_080_EjecutarInterfazRevRechFTTEMPCuandoNOContengaTransaccionesYValidarEnBDWMINTLosDatosINFOFOOTEREINFOHEADER";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se valida que el cambio en los campos mostrados en la BD con respecto a los archivos de conciliacion de Reversos se muestren con las reglas de negocio correspondientes";
	}
}