package EBS.moduloAP.proveedores;

import java.util.HashMap;

import org.testng.annotations.Test;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSEntryProveedores;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_ConsultaProveedores extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_002_ConsultaProveedores_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String ebsUrl = data.get("server");
		String userName = GlobalVariables.getJreyesHexEbsUsr;
		String password = GlobalVariables.getJreyesHexEbsPwd;
		String responsabilidad = data.get("responsabilidad");
		String carpeta1 = data.get("carpeta1");
		String numeroProveedor = data.get("numeroProveedor");
		
		/*
		 * UTILERIAS
		 */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		EBSEntryProveedores proveedores = new EBSEntryProveedores(u, testCase);

		
		/*
		 * PASOS DEL CASO DE PRUEBA, MATRIZ: CREAR PROVEEDOR
		 */

		// PASO 1
		addStep("Ingresar al ambiente de EBS.");
		logger.info("EBS URL: " + ebsUrl);
		System.out.println("EBS URL: " + ebsUrl);
		logger.info("User Name: " + userName);
		System.out.println("User Name: " + userName);
		// PASO 2
		addStep("Seleccionar idioma: Espanol Latinoamericano.");
		// PASO 3
		addStep("Ingresar usuario y contrasena.");
		// PASO 4
		addStep("Dar clic en el boton de 'Conectese'.");
		inicio.login();
		// PASO 5
		addStep("Ingresar a la responsabilidad 'MXOXXO-AP15IXM-Admon Proveedores'");
		paginaInicial.clickOnResponsabilidad(responsabilidad);
		paginaInicial.clickOnCarpeta(carpeta1);
		paginaInicial.clickformularioEntryAP15AdmonProveedores();
		u.hardWait(3);
		// PASO 6
		addStep("Entramos a la opción de CONSULTA PROVEEDOR");
		u.sendKeys(proveedores.numeroProveedor(), numeroProveedor);
		proveedores.clickIr();
		u.hardWait(3);
		u.close();
		
		
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Descripcion y Prerequisitos";
	}

	@Override
	public String setTestDesigner() {
		return "JessicaR@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Validar la Migración de Proveedor tipo Empleado con Archivo de Carga '.txt' . 1.- Acceso a la responsabilidad 'MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS' 2.- Contar con el archivo para procesar la carga en formato '.txt'.";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}