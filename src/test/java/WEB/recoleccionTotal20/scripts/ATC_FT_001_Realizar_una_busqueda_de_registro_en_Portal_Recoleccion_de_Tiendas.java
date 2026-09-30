package WEB.recoleccionTotal20.scripts;
import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import WEB.recoleccionTotal20.om.recoleccionTotal;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_Realizar_una_busqueda_de_registro_en_Portal_Recoleccion_de_Tiendas extends BaseExecution{


		@Test(dataProvider = "data-provider", enabled = true)
		public void ATC_FT_001_Realizar_una_busqueda_de_registro_en_Portal_Recoleccion_de_Tiendas_test(HashMap<String, String> data) throws Exception {
			testCase.setProject_Name("Recoleccion Total 2.0");
			
			//variables
			SeleniumUtil u = new SeleniumUtil(new ChromeDriver());
			recoleccionTotal rt = new recoleccionTotal(u,testCase);
			String user = data.get("user");
			String pass = data.get("pass");
			String region = data.get("region");
			String plaza1 = data.get("plaza1");
			String tienda1 = data.get("tienda1");
			String plaza2 = data.get("plaza2");
			String tienda2 = data.get("tienda2");
			String plaza3 = data.get("plaza3");
			String tienda3 = data.get("tienda3");
			String cr1 = rt.getCR(tienda1);
			String cr2 = rt.getCR(tienda2);
			String cr3 = rt.getCR(tienda3);
			
			//Code
			addStep("Ingresar a Access Control. Permite ingresar al portal");
			rt.ingresarAuthFemco(user, pass);
			
			addStep("Iniciar sesión con credenciales de QA. Se incia correctamente");
			testCase.addScreenShotCurrentStep("");
			
			addStep("Ingresar a la opción Portal de Efectivo. Permite acceso a Portal de Efectivo.");
			rt.clickPortalEfectivo();
			
			addStep("Seleccionamos y llenamos los campos para realizar un registro, debera llevar Turno, Horario Inicio, Horario Fin, Region, Plaza y Tienda. Se llenan los capos mencionados.");
			rt.showAllPages();
			rt.validateTiendaToDelete(cr1);
			rt.fillAllFields(region,plaza1,tienda1);
			
			addStep("Seleccionamos el botón de Guardar una vez que tengamos los campos con nuestros datos. Se permite continuar con guardado.");
			rt.clickBtnGuardar();
			testCase.addScreenShotCurrentStep("");
			
			addStep("Realizar Dos registros mas llenando todos los campos con diferentes datos. Se realizan registros.");
			rt.validateTiendaToDelete(cr2);
			rt.fillAllFields(region,plaza2,tienda2);
			rt.clickBtnGuardar();
			testCase.addScreenShotCurrentStep("");
			
			rt.validateTiendaToDelete(cr3);
			rt.fillAllFields(region,plaza3,tienda3);
			rt.clickBtnGuardar();
			testCase.addScreenShotCurrentStep("");
			
			addStep("Validamos que los registros aparescan en la seccion de Busqueda. Se validan registros.");
			addStep("Conforme a los registros realizados empezaremos a escribir en el campo de Busqueda, pondremos informacion de alguno de los campos, por ejemplo, escribiremos \"10MON\", \"50QBX\", \"Diurno\", etc, Entonces nos mostrara los Registros que coincidan con la informacion que escribamos en el campo de Busqueda. Se realiza busqueda.");
			addStep("Validar que se muestren los registros que realizamos al escribir algun dato que coincida con ellos. ");
			rt.busqueda(cr1);
			rt.busqueda(cr2);
			rt.busqueda(cr3);
		}

		@Override
		public String setTestFullName() {
			return "ATC_FT_001_Realizar_una_busqueda_de_registro_en_Portal_Recoleccion_de_Tiendas";
		}

		@Override
		public String setTestDescription() {
			return "Se valida el registro exitoso de varios Turnos y la busqueda con los filtros";
		}
	
		@Override
		public String setTestDesigner() {
			return "Roberto Renteria";
		}
 
		@Override
		public String setTestInstanceID() {
			return null;
		}

		@Override
		public String setPrerequisites() {
			return "Credenciales vigentes al portal de Recoleccion en Tienda";
		}

		@Override
		public void beforeTest() {
	// TODO Auto-generated method stub
		}

	}