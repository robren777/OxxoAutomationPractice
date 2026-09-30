package WM.interfaces.PR63_CLOUD;

import static org.junit.Assert.assertFalse;

import java.util.HashMap;

import org.apache.commons.net.ftp.FTPClient;
import org.testng.annotations.Test;

import WM.interfaces.ENRED_OUT_V2.ATC_011_ENREDOUTV2_ValidarACKTiendaFisicaCloud;
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

public class ATC_013_2_PR63_CLOUD_EnvioPromocionSimpleOnpremiseFisica extends BaseExecution {
	@Test
	(dataProvider = "data-provider")

	public void ATC_013_2_PR63_CLOUD_EnvioPromocionSimpleOnpremiseFisica_test (HashMap<String, String> data) throws Exception {
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

		String querySimotiOutDoc2 = "SELECT * FROM POSUSER.POS_OUT_DOC_NAR WHERE PV_CR_PLAZA = '" +data.get("departmentId2")+ "' AND PV_CR_TIENDA = '" +data.get("storeId2")+ "' AND DOC_TYPE='PRM' AND STATUS='" + data.get("statusAckRespuesta") + "' AND DATE_CREATED=SYSDATE";
		
		//FTP
		String ftpHost="10.188.48.8";
		String ftpUserTienda2= data.get("ftpUserTienda2");
		String ftpPasswordTienda2= data.get("ftpPasswordTienda2");
		
		FTPClient ftpClient = new FTPClient();
		//Variables para directorios y nombres de archivos
        String filePathFTP2 = data.get("ftpDir2");
        
        boolean validaStatus = false;

		//***************************Paso 79*****************************************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas_ENRED_OUT el caso de prueba @MTC_FT_018 Validar Archivos ITM (on-premise) y PRM (onpremise y fisica cloud)-Hibrido\r\n"
				+ "\r\n"
				+ "Nota: La plaza y tienda del caso a ejecutar deben ser 10VCZ y 50YDY respectivamente");
		
		ATC_023_ENREDOUTV2_ValidarACKWmCode101TiendaOnpremise atc023_enredoutv2 = new ATC_023_ENREDOUTV2_ValidarACKWmCode101TiendaOnpremise();
		atc023_enredoutv2.setTestCase(this.testCase);
		atc023_enredoutv2.ATC_023_ENREDOUTV2_ValidarACKWmCode101TiendaOnpremise_test(data);
		String nameOutboundv1 = atc023_enredoutv2.name;
		System.out.println("Nombre del documento 2 que regresa la API, flujo E2E: " + nameOutboundv1);
		
		//***************************Paso 80*****************************************************************************
		addStep("Ejecutar de la matriz SIGA_Script de Pruebas_ENRED_OUT el caso de prueba @MTC_FT_018 Validar Archivos ITM (on-premise) y PRM (onpremise y fisica cloud)-Hibrido\r\n"
				+ "\r\n"
				+ "Nota: La plaza y tienda del caso a ejecutar deben ser 10VCZ y 50YDY respectivamente");
		
		//Cambiar a diferente tienda
		data.put("departmentId", "10VCZ");
		data.put("storeId", "50YDY");
		data.put("location", "11524");
		data.put("getPathLisp", "/integration/restv2/development/fl3848159eed0c72d49160fb/EnredOut_API/LISP");
		data.put("getPathOutbound", "/integration/restv2/development/fl3848159eed0c72d49160fb/EnredOut_API/OUTBOUND");
		data.put("getPathACK", "/integration/restv2/development/fl3848159eed0c72d49160fb/EnredOut_API/ACK");
		data.put("status", "C");
		data.put("id", "191882");
		data.put("statusBody", "");
		data.put("getExpectedCode", "116");
		
		ATC_011_ENREDOUTV2_ValidarACKTiendaFisicaCloud atc011_enredoutv2 = new ATC_011_ENREDOUTV2_ValidarACKTiendaFisicaCloud();
		atc011_enredoutv2.setTestCase(this.testCase);
		atc011_enredoutv2.ATC_011_ENREDOUTV2_ValidarACKTiendaFisicaCloud_test(data);
		
		//Regresar a tienda original
		data.put("departmentId", "10TLC");
		data.put("storeId", "50B87");

      //***************************Paso 81*****************************************************************************
        addStep("Validar que el archivo generado este en el buzon de tienda en Filezilla\r\n"
        		+ "/u01/posuser/FEMSA_OXXO/POS/10TLC/50B87/working");
        
        String prmFilePath2 = filePathFTP2 + "/" + nameOutboundv1;
        
        ftpClient.connect(ftpHost);
        if (tr.isFilePresentInFTP(ftpHost, ftpUserTienda2, ftpPasswordTienda2, prmFilePath2)) {
            System.out.println("Archivo encontrado en FTP: " + nameOutboundv1);
            testCase.addTextEvidenceCurrentStep("Archivo encontrado en FTP: " + nameOutboundv1);
            validaStatus = false;
        } else {
            System.out.println("Archivo no encontrado en FTP: " + nameOutboundv1);
            testCase.addTextEvidenceCurrentStep("Archivo no encontrado en FTP: " + nameOutboundv1);
            validaStatus = true;
        }
        assertFalse("Se encontro algun registro de error", validaStatus);
		
		//***************************Paso 82 a 87*****************************************************************************
		addStep("No se pueden automatizar pasos 82 a 87 debido a doble factor de autenticacion");
		
		//***************************Paso 88*****************************************************************************
		addStep("Accesar BD FCWM6QA");
		testCase.addBoldTextEvidenceCurrentStep("La conexion fue exitosa");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWM6QA_NUEVA);
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_USER_FCWMQA_NUEVA);
		
		//***************************Paso 89*****************************************************************************
		addStep("Validar que el documento existe en la tabla ocupada por SIMOTI con la consulta: " + querySimotiOutDoc2);
		
		//validaStatus = tr.queryOracle(querySimotiOutDoc2, dbFCWM6QA);
		//tr.processQueryInChunks(dbFCWM6QA, querySimotiOutDoc2);
		//assertTrue("No se muestra el estatus esperado.", validaStatus);
		 testCase.addBoldTextEvidenceCurrentStep("Este paso queda fuera de alcance en este desarrollo");
				
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-013-2 Envio de promocion compleja Hibrido (on-premise y fisica)";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Envio de promociones complejas a tienda fisica cloud y a tienda on-premise, archivos de respuesta almacenados respectivamente en buzon de tienda y BD Azure, interfaces PR63_MX y PR63_CLOUD ejecutadas manual.";
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
				+ "9. Contar con acceso a Logs de ejecucion de Azure\r\n"
				+ "10. Contar con acceso a buzones de tiendas en Filezilla";

	}
}
