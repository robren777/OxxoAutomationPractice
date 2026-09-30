package RDM.mermaYDev.NoAcreditacion;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.MermaYDev;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION: Se realiza la validacion de los 16 Motivos Originales de no acreditacion 
 * de devoluciones en pantalla XXFC Acreditaciones (Proceso Reingeneria)
 * 
 * PREREQUISITOS: 
 * -Contar con acceso a base de datos RDM
 * -Contar con acceso a aplicativo RDM
 * -Usar Microsoft Edge en modo Internet Explorer
 * -Tener habilitado los 16 Motivos Originales para su validacion
 * 
 * INSUMOS:
 * -Tener generada previamente una cita (Appoinment Schedule) > Ligar ASN a BOL (NSC Appoinment Detail)
 * -Tener generada una ruta Receiving > XXFC Devoluciones > XXFC Seguimiento Operativo
 * -Ligar la ruta a algun ASN disponible de alguna tienda para poder visualizar los articulos a devolver
 * 
 * @author Alejandro.Montalvo
 * @date 15/01/25
 */

public class ATC_FT_005_16MotivosOriginalesReingenieria extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_16MotivosOriginalesReingenieria_Test(HashMap<String, String> data) throws Exception {
		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM_RDM100, GlobalVariables.DB_USER_RDM_RDM100,
				GlobalVariables.DB_PASSWORD_RDM_RDM100);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		MermaYDev m = new MermaYDev(u, testCase, data.get("server"));

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
			
		testCase.setProject_Name("I24046 Simplificación conciliación y motivos de acreditación");
		System.out.println(dbRdm.getHost());
		
		// PASO 1
		addStep("Ingresar usuario y contrasena en el sistema RDM y Presionar el boton \"Login\"");
		System.out.println("PASO 1");
		
		m.logIn();
		
		// PASO 2
		addStep("En el menu principal de RDM ingresar a la opcion \"Receiving\" "
				+ "Ingresar a la opcion XXFC Devoluciones > XXFC Acreditaciones ");
		System.out.println("PASO 2");
		
		m.clickMenuReceivingXxfcAcreditaciones();
		
		// PASO 3
		addStep("Realiza la carga del ASN generado previamente ligado con la ruta generada");
		System.out.println("PASO 3");
		
		m.clickLoadAsnXxfcAcreditacionesReingeneria();	
						
		// PASO 4
		addStep("Verificar los 16 Motivos originales de no acreditacion existentes");
        System.out.println("PASO 4");
        
        boolean result = m.validar16MotivosOriginalesNoAcreditacionReingeneria();
        assertTrue(result, "No todos los motivos originales de no acreditacion fueron encontrados en la pantalla reingeniería.");

        // Finalizar el script
		u.quit();
	}

	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setPrerequisites() {
		return " * -Contar con acceso a base de datos RDM\r\n" 
	            + " * -Contar con acceso a aplicativo RDM\r\n"
				+ " * -Usar Microsoft Edge en modo Internet Explorer\r\n"
				+ " * -Tener habilitado los 16 Motivos Originales para su validacion\r\n"
				+ " * -Tener generada previamente una cita (Appoinment Schedule) > Ligar ASN a BOL (NSC Appoinment Detail)\r\n"
				+ " * -Tener generada una ruta Receiving > XXFC Devoluciones > XXFC Seguimiento Operativo\r\n"
				+ " * -Ligar la ruta a algun ASN disponible de alguna tienda para poder visualizar los articulos a devolver";
	
	}

	@Override
	public String setTestDescription() {
		return "Se realiza la validacion de los 16 motivos originales de no acreditacion"
				+ " en la pantalla XXFC Devoluciones - XXFC Acreditaciones";
	}

	@Override
	public String setTestDesigner() {
		return "alejandro.montalvo@gtim.mx";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_16MotivosOriginalesReingenieria_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}