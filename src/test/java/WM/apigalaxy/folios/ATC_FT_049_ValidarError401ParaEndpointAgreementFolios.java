package WM.apigalaxy.folios;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.ApiGalaxyUtils;
import io.restassured.response.Response;
import modelo.BaseExecution;

/**
 * DESCRIPCIÓN: Se valida el error 401 forzandolo para el endpoints de la
 * APIforzandolo para el endpoint de la API
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -MTC-FT-049-Validar error 401
 * para endpoint agreement/folios
 *
 * PREREQUISITOS: - Acceso a la API - La API funcionando
 *
 * INSUMOS:
 * 
 * @author Guillermo garcia
 *
 * @date 29/05/2026
 */
public class ATC_FT_049_ValidarError401ParaEndpointAgreementFolios extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_049_ValidarError401ParaEndpointAgreementFolios_test(HashMap<String, String> data)
	    throws Exception {

	/*****************************
	 * Instancias
	 **************************************************/
	String ambiente = data.get("ambiente");
	ApiGalaxyUtils util = new ApiGalaxyUtils(ambiente);

	/***************************************
	 * PASO 1 Hacer una llamada tipo GET con los siguinetes valores: page = 1,
	 * pageSize = 1, updateDate = <Valor obtenido en datos de prueba, formato
	 * YYYY-MM-DD>
	 ***************************************/
	addStep("Hacer una llamada tipo GET con los siguinetes valores:page = 1,pageSize = 1,updateDate = <Valor obtenido en datos de prueba, formato YYYY-MM-DD>",
		"Debe regresar un 401");
	Response response = util.getAgreementFoliosNotToken(1, 1, 1, "a", "a");
	testCase.addCodeEvidenceCurrentStep("El estatus encontrado es: " + response.getStatusCode());
	assertEquals(response.getStatusCode(), 401, "Se valida el estatus code");

	/***************************************
	 * PASO 2 Validar el response de la consulta
	 ***************************************/
	addStep("Validar el response de la consulta", "El response debe ser:" + "code" + "X401000" + "message"
		+ "The specified token is invalid or has expired.");
	String realCode = response.jsonPath().getString("code");
	testCase.addCodeEvidenceCurrentStep("Para el envio se obtiene el code " + realCode);
	testCase.addCodeEvidenceCurrentStep(response.asPrettyString());
	assertEquals(realCode, "X401000", "Code erroneo ");
	assertEquals(response.jsonPath().getString("message"), " Token specified is invalid or has expired.",
		"Message erroneo ");

    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
	return "Acceso a la API\\nLa API funcionando";
    }

    @Override
    public String setTestDescription() {
	return "Se valida el error 401 forzandolo para el endpoints de la APIforzandolo para el endpoint de la API";
    }

    @Override
    public String setTestDesigner() {
	return "Guillermo Garcia";
    }

    @Override
    public String setTestFullName() {
	return "ATC_FT_049_ValidarError401ParaEndpointAgreementFolios";
    }

    @Override
    public String setTestInstanceID() {
	return null;
    }

}
