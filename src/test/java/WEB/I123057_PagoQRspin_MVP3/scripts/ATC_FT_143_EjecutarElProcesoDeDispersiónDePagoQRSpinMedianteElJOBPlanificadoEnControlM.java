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

import WEB.I123057_PagoQRspin_MVP3.om.PagoQRspin_EBS;
import WEB.I123057_PagoQRspin_MVP3.om.PagoQRspin_MVP3;

/**
 * DESCRIPCION: 
 *Se realizara el proceso de dispercion correctamente mediante la ejecucion de JOB y se validara que se genere la dispercion correcta en EBS
 *
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-143-Ejecutar el proceso de dispersión de Pago QR-FP TTYP1814 mediante el JOB planificado en controlM 
 * 
 * PREREQUISITOS:
 *1.Contar con la configuracion correcta para poder realizar dispersiones en la aplicación Oracle Applications
2.Contar con transacciones del mes en curso ya realizadas (desde punto de venta) en el transcurso de las fechas que se realizara la dispersion
3.Contar con los permisos y accesos necesarios para visualizacion de contenido contable y creacion de dispersiones
4.El JOB debe de estar creado en QA - instalado y planificado en ambiente de QA
5.Contar con los numeros de cuenta habilitados para las pruebas (Se necesita apoyo de Contabilidad)
6.Contar con el concurrente XXFC-Proceso de Dispersión de Pago QR
 *  
 *  INSUMOS
 *  Job ordenado en Control-M XXFC_GL_QR_DISPERSION_SPIN_MX
 *  
 * @author Juan Gerardo Lopez
 * @date 05/Septiembre/2025 
 * 
 **/

public class ATC_FT_143_EjecutarElProcesoDeDispersiónDePagoQRSpinMedianteElJOBPlanificadoEnControlM extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_143_EjecutarElProcesoDeDispersiónDePagoQRSpinMedianteElJOBPlanificadoEnControlM_test (HashMap <String, String> data) throws Exception {
	
		testCase.setProject_Name("MVP3 Pago con QR Spin");
		
		/* Utils**************************************************************/
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		u.quit();
		PagoQRspin_MVP3 pqrs = new PagoQRspin_MVP3(u, testCase);
		PagoQRspin_EBS pqrseb = new PagoQRspin_EBS(u, testCase);
		
		/*Definición de Variables*/
		String Njob = data.get("Njob");
		String ps = data.get("ps");
		String user = data.get("user");
		String server = data.get("server");
		String usrname = data.get("username");
		String formapago = data.get("formapago");
		
		//Script Code Begin
		
		System.out.println("***************PASO 01********************");
		addStep("Se ejecuta Job de proceso de dispercion QR SPIN");
		pqrs.ExecJobNewStatus(Njob, ps, user, server, testCase);
		
		System.out.println("***************PASO 02********************");
		addStep("Se valida que la ejecucion del job genere la dispersion en BD");
		u.hardWait(3);
		pqrseb.validProcesoDispersionQRSPIN(usrname, formapago);
		
		//Script Code End
	}
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1.Contar con la configuracion correcta para poder realizar dispersiones en la aplicación Oracle Applications\r\n"
				+ "2.Contar con transacciones del mes en curso ya realizadas (desde punto de venta) en el transcurso de las fechas que se realizara la dispersion\r\n"
				+ "3.Contar con los permisos y accesos necesarios para visualizacion de contenido contable y creacion de dispersiones\r\n"
				+ "4.El JOB debe de estar creado en QA - instalado y planificado en ambiente de QA\r\n"
				+ "5.Contar con los numeros de cuenta habilitados para las pruebas (Se necesita apoyo de Contabilidad)\r\n"
				+ "6.Contar con el concurrente XXFC-Proceso de Dispersión de Pago QR";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Gerardo Lopez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_143_EjecutarElProcesoDeDispersiónDePagoQRSpinMedianteElJOBPlanificadoEnControlM";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se realizara el proceso de dispercion correctamente mediante la ejecucion de JOB y se validara que se genere la dispercion correcta en EBS";
	}
}