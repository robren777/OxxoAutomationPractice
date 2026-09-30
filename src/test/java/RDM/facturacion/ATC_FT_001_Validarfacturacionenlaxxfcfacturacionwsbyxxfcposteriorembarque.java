package RDM.facturacion;

import java.util.HashMap;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import RDM.util.Facturacion;
import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_001_Validarfacturacionenlaxxfcfacturacionwsbyxxfcposteriorembarque extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void  ATC_FT_001_Validarfacturacionenlaxxfcfacturacionwsbyxxfcposteriorembarque_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String status = data.get("status");
		String distro = data.get("distro");

		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,GlobalVariables.DB_PASSWORD_RDM);
		SeleniumUtil u = new SeleniumUtil(new IETest());
	    Facturacion f = new Facturacion(u, testCase, data.get("server"));
		SikuliUtil sk = new SikuliUtil();

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Accesar a RDM mediante la liga: http://10.180.49.81:8011/rdm01x.html");
		System.out.println("PASO 1");
		System.out.println(dbRdm.getHost());
		// PASO 2
		addStep("Ingresar usuario y contrasena y dar click en el boton Login");
		System.out.println("PASO 2");
		f.logIn();
		// PASO 3
		addStep("Ingresar a la siguiente ruta desde el menu principal, Distribution planning>Reports>XXFC Wave Status By Distro.");
		System.out.println("PASO 3");
		testCase.addScreenShotCurrentStep(u,"Ingresar a la siguiente ruta desde el menu principal, Distribution planning>Reports>XXFC Wave Status By Distro.");
		f.clickWaveStatus();
		f.ingresarStatusDistro(status, distro);
		// PASO 4
		addStep("Ingresar el distro y presionar el boton ejecutar");
		System.out.println("PASO 4");
		testCase.addScreenShotCurrentStep(u,"Ingresar el distro y presionar el boton ejecutar");
		f.clickEjecutarCheckFacturacion();
		// PASO 5
		addStep("Seleccionamos la ruta que vamos a facturar, y presionamos el boton facturacion");
		System.out.println("PASO 5");
		testCase.addScreenShotCurrentStep(u, "Seleccionamos la ruta que vamos a facturar, y presionamos el boton facturacion");
		// PASO 6
		addStep("Seleccionamos los datos del embarque, Trailer, Door Id, Seal y presiona el boton facturación y confirmamos el trailer");
		System.out.println("PASO 6");
		testCase.addScreenShotCurrentStep(u, "Seleccionamos los datos del embarque, Trailer, Door Id, Seal y presiona el boton facturación y confirmamos el trailer");
		// PASO 7
		addStep("Validamos la Facturacion en pantalla");
		System.out.println("PASO 7");
		testCase.addScreenShotCurrentStep(u,"Validamos la Facturacion en pantalla");
		// PASO 8
		addStep("Validamos la Facturacion en base de Datos XXFC_FACTURACION_WSB MANIFEST");
		System.out.println("PASO 8");
		testCase.addScreenShotCurrentStep(u,"Validamos la Facturacion en base de Datos XXFC_FACTURACION_WSB MANIFEST");
		// PASO 9
		addStep("Corremos el job en cadena creacion_xxfc_pre_posterior_embarque_mty creacion_xxfc_posterior_embarque_mty");
		System.out.println("PASO 9");
		testCase.addScreenShotCurrentStep(u,"Corremos el job en cadena creacion_xxfc_pre_posterior_embarque_mty creacion_xxfc_posterior_embarque_mty");
		// PASO 10
		addStep("validamos en la base de datos xxfc_posterior_embarque xxfc_posterior_emb_citems");
		System.out.println("PASO 10");
		testCase.addScreenShotCurrentStep(u,"");
		// PASO 11
		addStep("Ejecutar la Interface PR77 en Web Method");
		System.out.println("PASO 11");
		testCase.addScreenShotCurrentStep(u,"Ejecutar la Interface PR77 en Web Method");
		// PASO 12
		addStep("Ejecutar el Query : select * from xxfc_posterior_embarque where tsf_no = 9999999999");
		System.out.println("PASO 12");
		testCase.addScreenShotCurrentStep(u,"Ejecutar el Query : select * from xxfc_posterior_embarque where tsf_no = 9999999999");
		//u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "- Contar con la liga de acceso a RDM"
		+ "- Contar con credenciales para RDM";
	}

	@Override
	public String setTestDescription() {
		return "Mediante el sistema RDM se debe mostrar los pasos para Validar facturacion en la xxfc_facturacion_wsb y xxfc_posterior_embarque";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Validarfacturacionenlaxxfcfacturacionwsbyxxfcposteriorembarque";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
