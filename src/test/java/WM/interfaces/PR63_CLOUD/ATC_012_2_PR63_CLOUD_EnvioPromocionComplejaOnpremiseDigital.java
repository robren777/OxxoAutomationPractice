package WM.interfaces.PR63_CLOUD;

import static org.junit.Assert.assertFalse;

import java.util.HashMap;

import org.apache.commons.net.ftp.FTPClient;
import org.testng.annotations.Test;

import WM.interfaces.ENRED_OUT_V2.ATC_010_ENREDOUTV2_ValidarACKEstatusPWmCode101TiendaDigitalCloud;
import WM.interfaces.ENRED_OUT_V2.ATC_023_ENREDOUTV2_ValidarACKWmCode101TiendaOnpremise;
import WM.om.PR63_CLOUD;
import WM.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

@AzureTestInfo(
		areaPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise",
		iterationPath = "I240701_EvolucionAlProcesoDe_ERDD_enLaNube\\Onpremise\\Sprint 3",
		tags = "",
		userStoryId = 71351
)

public class ATC_012_2_PR63_CLOUD_EnvioPromocionComplejaOnpremiseDigital extends BaseExecution {
	@Test
	(dataProvider = "data-provider")

	public void ATC_012_2_PR63_CLOUD_EnvioPromocionComplejaOnpremiseDigital_test (HashMap<String, String> data) throws Exception {
		/*Nombre del caso de prueba manual que cubre: MTC-FT-004-2 Envio de promocion simple a tienda on-premise
		Desc:
		Continuacion del caso de prueba MTC-FT-004 Envio de promocion simple a tienda on-premise
		Se divide ya que antes de este caso se tiene que pedir manual a L2 el archivo y ponerlo en el buzon de FTP
		@Valeria Fernandez
		@date   2024/12/26*/
				
		/*
		Utilerias (Conexiones a BD)
		*********************************************************************/
		PR63_CLOUD tr = new PR63_CLOUD(testCase);
		
		utils.sql.SQLUtil dbFCWM6QA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWM6QA_NUEVA,GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);

		String querySimotiOutDoc1 = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId")+ "' AND PV_CR_TIENDA = '" +data.get("storeId")+ "' AND DOC_TYPE='PRM' AND STATUS='" + data.get("statusAckRespuesta") + "' AND DATE_CREATED=SYSDATE";
		String querySimotiOutDoc2 = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId2")+ "' AND PV_CR_TIENDA = '" +data.get("storeId2")+ "' AND DOC_TYPE='PRM' AND STATUS='" + data.get("statusAckRespuesta") + "' AND DATE_CREATED=SYSDATE";
		
		//Azure Storage
		String azureConnectionString = data.get("azureConnectionString");
        String azureContainerName = data.get("azureContainerName");
		
		//FTP
		String ftpHost="10.188.48.8";
		String ftpUserTienda2= data.get("ftpUserTienda2");
		String ftpPasswordTienda2= data.get("ftpPasswordTienda2");
		
		FTPClient ftpClient = new FTPClient();
		//Variables para directorios y nombres de archivos
        String filePathFTP2 = data.get("ftpDir2");
        
        boolean validaStatus = false;

		//***************************Paso 79*****************************************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas_ENRED_OUT el caso de prueba @MTC_FT_017 Validar Archivos ITM (on-premise) y PRM (onpremise y digital cloud)-Hibrido\r\n"
				+ "\r\n"
				+ "Nota: La plaza y tienda del caso a ejecutar deben ser 10VCZ y 50YDY respectivamente");
		
		ATC_010_ENREDOUTV2_ValidarACKEstatusPWmCode101TiendaDigitalCloud atc010_enredoutv2 = new ATC_010_ENREDOUTV2_ValidarACKEstatusPWmCode101TiendaDigitalCloud();
		atc010_enredoutv2.setTestCase(this.testCase);
		atc010_enredoutv2.ATC_010_ENREDOUTV2_ValidarACKEstatusPWmCode101TiendaDigitalCloud_test(data);
		
		//Obtener el NOMBRE del documento OUTBOUND
		String nameOutbound2 = atc010_enredoutv2.name;
		String nameOutbound = nameOutbound2.substring(0, nameOutbound2.length() - 3);
		System.out.println("Nombre del documento 1 que regresa la API, flujo E2E: " + nameOutbound2);
		
		Thread.sleep(2 * 60 * 1000);
		
		//***************************Paso 80*****************************************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas_ENRED_OUT el caso de prueba @MTC_FT_017 Validar Archivos ITM (on-premise) y PRM (onpremise y digital cloud)-Hibrido\r\n"
				+ "\r\n"
				+ "Nota: La plaza y tienda del caso a ejecutar deben ser 10TLC y 50B87 respectivamente");
		
		//Cambiar a diferente tienda
		data.put("departmentId", "10TLC");
		data.put("storeId", "50B87");
		data.put("location", "41341");
		data.put("getPathLisp", "/integration/restv2/development/fl8a4a4ab3b9b495781d0a8d/EnredOut_API/LISP");
		data.put("getPathOutbound", "/integration/restv2/development/fl8a4a4ab3b9b495781d0a8d/EnredOut_API/OUTBOUND");
		data.put("getPathACK", "/integration/restv2/development/fl8a4a4ab3b9b495781d0a8d/EnredOut_API/ACK");
		data.put("status", "W");
		data.put("statusOutboundRespuesta", "E");
		data.put("statusAckRespuesta", "P");
		data.put("id", "28689023");
		
		ATC_023_ENREDOUTV2_ValidarACKWmCode101TiendaOnpremise atc023_enredoutv2 = new ATC_023_ENREDOUTV2_ValidarACKWmCode101TiendaOnpremise();
		atc023_enredoutv2.setTestCase(this.testCase);
		atc023_enredoutv2.ATC_023_ENREDOUTV2_ValidarACKWmCode101TiendaOnpremise_test(data);
		String nameOutboundv1 = atc023_enredoutv2.name;
		System.out.println("Nombre del documento 2 que regresa la API, flujo E2E: " + nameOutboundv1);
		
		//Regresar a tienda original
		data.put("departmentId", "10VCZ");
		data.put("storeId", "50YDY");

      //***************************Paso 81*****************************************************************************
		addStep("Validar que el archivo generado de plaza 10VCZ y tienda 50YDY NO este en la ruta de Azure Storage:\r\n"
				+ "\r\n"
				+ "Ir a Resource Group -> Dar click a OXZRGERDDQ01 -> Dar click en pestania Overview - Buscar y dar click a oxzsterddqap01\r\n"
				+ "Dar click en Data Storage -> Dar click a Container -> Dar click en el contenedor ct-json-mappedjsonstorepromotiondata -> Buscar archivo con nombre en tabla PRM_HEAD");
		
		boolean noExisteAzure = tr.archivoAzureNoExiste(azureConnectionString, azureContainerName, nameOutbound);
        
        if (noExisteAzure) {
        	System.out.println("Archivo PRM V2 NO encontrado en Azure Storage: " + nameOutbound);
            testCase.addTextEvidenceCurrentStep("Archivo PRM V2 NO encontrado en Azure Storage: " + nameOutbound);
        }
        
        assertFalse("El archivo sigue existiendo en Blob Storage", !noExisteAzure);
        
	  //***************************Paso 82*****************************************************************************
        addStep("Validar que el archivo generado este en el buzon de tienda en Filezilla\r\n"
        		+ "/u01/posuser/FEMSA_OXXO/POS/10TLC/50B87/working");
        
        String prmFilePath2 = filePathFTP2 + "/" + nameOutboundv1;
        
        ftpClient.connect(ftpHost);
        if (tr.isFilePresentInFTP(ftpHost, ftpUserTienda2, ftpPasswordTienda2, prmFilePath2)) {
            System.out.println("Archivo encontrado en FTP: " + nameOutboundv1);
            testCase.addTextEvidenceCurrentStep("Archivo encontrado en FTP: " + nameOutboundv1);
            validaStatus = false;
        } else {
            System.out.println("Archivo no encontrado en FTP" + nameOutboundv1);
            testCase.addTextEvidenceCurrentStep("Archivo no encontrado en FTP: " + nameOutboundv1);
            validaStatus = true;
        }
        assertFalse("Se encontro algun registro de error", validaStatus);
		
		//***************************Paso 83 a 89*****************************************************************************
		addStep("No se pueden automatizar pasos 83 a 89 debido a doble factor de autenticacion");
		
		//***************************Paso 90*****************************************************************************
		addStep("Accesar BD FCWM6QA");
		testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWM6QA_NUEVA);
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_FCWMQA_NUEVA);
		
		//***************************Paso 91*****************************************************************************
		addStep("Validar que el documento existe en la tabla ocupada por SIMOTI con la consulta: " + querySimotiOutDoc1);
		
		//validaStatus = tr.queryOracle(querySimotiOutDoc1, dbFCWM6QA);
		//tr.processQueryInChunks(dbFCWM6QA, querySimotiOutDoc1);
		//assertTrue("No se muestra el estatus esperado.", validaStatus);
		testCase.addBoldTextEvidenceCurrentStep("Este paso queda fuera de alcance en este desarrollo");
		
		//***************************Paso 92*****************************************************************************
		addStep("Validar que el documento existe en la tabla ocupada por SIMOTI con la consulta: " + querySimotiOutDoc2);
		
		//validaStatus = tr.queryOracle(querySimotiOutDoc2, dbFCWM6QA);
		//tr.processQueryInChunks(dbFCWM6QA, querySimotiOutDoc2);
		//assertTrue("No se muestra el estatus esperado.", validaStatus);
		testCase.addBoldTextEvidenceCurrentStep("Este paso queda fuera de alcance en este desarrollo");
				
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-012-2 Envio de promocion compleja Hibrido (on-premise y digital)";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Envio de promociones complejas a tienda digital cloud y a tienda on-premise, archivos de respuesta almacenados respectivamente en buzon de tienda y Azure Storage, interfaces PR63_MX y PR63_CLOUD ejecutadas manual.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Valeria Fernandez";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Acceso a las BD's: RMSv16, FCDASPFR, FCWM6QA y FCWMLQA\r\n"
				+ "2. Acceso a la BD de Azure: MDB_ERDD_PromotionCloud\r\n"
				+ "3. Acceso al aplicativo RMS16, Control M y portal SIMOTI\r\n"
				+ "4. Contar con un SKU existente en la tienda 50YDY plaza 10VCZ (LOC=11524) en las BDs RMSv16 y FCDASPFR.\r\n"
				+ "Y con otro SKU existente en la tienda 50B87 plaza 10TLC (LOC=41341) en las mismas BDs.\r\n"
				+ "5. Tener ordenados los jobs en Control M, el listado con sus datos esta en la pestania Cadena Jobs de esta matriz. (solicitado por correo)\r\n"
				+ "6. Haber descargado el archivo .ods template de promocion simple de RMS16 (Tasks -> Buscar: Promotions -> Download Template Simple Promotion)\r\n"
				+ "7. Haber descargado el archivo .ods template cambio estatus promocion de RMS16 (Tasks -> Buscar: Filter Promotions -> Download Template)\r\n"
				+ "8. Tener acceso a las matrices de prueba de MARK_PRM y ENRED OUT V2 con sus correspondientes componentes de QA\r\n"
				+ "9. Contar con acceso a Azure Storage y Logs de ejecucion de Azure\r\n"
				+ "10. Contar con acceso a buzones de tiendas en Filezilla";

	}
}
