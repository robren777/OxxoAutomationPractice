package WEB.cometa.scripts.mvp2;


import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * Validar que el formato de los exceles sea la siguiente
	Titulo de las columnas: Letra Arial 14 color blanco y en negritas: 
	Resto de la informaciï¿½n: Letra Arial 12 color negro y en formato de tabla 
	Toda las columnas centradas 
 * MTC-FT-40-E1- Validar fuentes y contenido de la descarga de excel  masiva
 * @author Yair LizÃ¡rraga
 * @date Octubre 2024
*/
public class ATC_FT_40_E1_ValidarFuentesYContenidoDeLaDescargaDeExcelMasiva extends BaseExecution {
	
	SeleniumUtil u;
	PortalCometa pom;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_40_E1_ValidarFuentesYContenidoDeLaDescargaDeExcelMasiva_test (HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Portal Web: Cometa");
		
        String user = data.get("user");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String url = data.get("Url");

        u = new SeleniumUtil(new ChromeTest(), true);
        PortalCometa pc = new PortalCometa(u, testCase);
		
		 /*
         *                                           PASO 1
         ******************************************************************************************/
        addStep("Ingresar a la liga");
        u.get(url);
        u.refresh();
        u.hardWait(3);
        testCase.addScreenShotCurrentStep(u, "Pantalla cargada");
        pc.PrivatePortal();

        /*
         *                                             PASO 2
         ******************************************************************************************/
        addStep("Ingresar las credenciales correspondientes al perfil de Cometa para el inicio de sesion");
        boolean resultStep2 = pc.IniciarSesionNoToken(user, password);
        if(resultStep2){
            testCase.addTextEvidenceCurrentStep("Se logro iniciar sesion correctamente");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo iniciar sesion");
        }
        assertTrue(resultStep2, "No se pudo iniciar sesion");


        /*
         *                                             PASO 3
         ******************************************************************************************/
        addStep("Ingresar a Portal Cometa");
        boolean resultStep3 = pc.seleccionarPortalCometaQA();
        if(resultStep3){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar el portal Cometa");
        } else{
            testCase.addTextEvidenceCurrentStep("No es visible el elemento de Cometa");
        }
        assertTrue(resultStep3, "No es visible el elemento de Cometa");

        /*
         *                                             PASO 4
         ******************************************************************************************/
        addStep("Ingresar a Comprobantes de compra y Resumen");
        boolean resultStep4 = pc.clicBotonCometaDirectos();
        if(resultStep4){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Directos");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Directos");
        }
        assertTrue(resultStep4, "No se encontro el elemento Directos");

        boolean resultStep4_1 = pc.ClicBotonComprobantesDeCompra();
        if(resultStep4_1){
            testCase.addTextEvidenceCurrentStep("Se pudo seleccionar y entrar en Comprobantes de Compra");
        } else {
            testCase.addTextEvidenceCurrentStep("No se encontro el elemento Comprobantes de Compra");
        }
        assertTrue(resultStep4_1,"No se encontro el elemento Comprobantes de Compra");
		
		/*
		 *                                             PASO 4
		 ******************************************************************************************/
		addStep("Realizar una busqueda mediante el llenado de filtros");
        boolean resultStep5 = pc.busquedaFiltroZona(4); //se manda la el rango de meses a partir de el mes actual para atras.
        if(resultStep5){
            testCase.addTextEvidenceCurrentStep("Se realiza la busqueda por zona");
        } else {
            testCase.addTextEvidenceCurrentStep("No se pudo realizar la busqueda por zona");
        }
        assertTrue(resultStep5, "No se pudo realizar la busqueda por zona");


		/*
		 *                                             PASO 5
		 ******************************************************************************************/
		addStep("Ir a la parte de Descargar MASIVA de los componentes filtrados y descargar el formato de excel");
		pc.ClicExcelMasivo();
		pc.clicDescargaMasiva();
		
		/*
		 *                                             PASO 6
		 ******************************************************************************************/
		addStep("Validar que en la descarga masiva venga fuentes y contenido de acuerdo a lo solicitado");	
        boolean validarJerFiltros = pc.abrirYCompararEncabezadosMasivo();
		if(validarJerFiltros)
			testCase.addTextEvidenceCurrentStep("Los botones existen");
		else
			testCase.addTextEvidenceCurrentStep("Los botones no existen");
		assertTrue(validarJerFiltros, "Se muestra la informaciÃ³n esperada");
	}
	
	@AfterMethod
	public void afterMethod() {
		 // u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales validas para el inicio de sesion de perfil DIRECTOS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el formato de los exceles sea la siguiente\r\n"
				+ "Titulo de las columnas: Letra Arial 14 color blanco y en negritas: \r\n"
				+ "Resto de la informaciï¿½n: Letra Arial 12 color negro y en formato de tabla \r\n"
				+ "Toda las columnas centradas ";
	}

	@Override
	public String setTestDesigner() {
		return"Yair Lizarraga";
	}

	@Override
	public String setTestFullName() {
		return "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
