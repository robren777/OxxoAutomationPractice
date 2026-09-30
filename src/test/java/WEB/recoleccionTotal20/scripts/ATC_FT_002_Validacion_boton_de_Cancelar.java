package WEB.recoleccionTotal20.scripts;
import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import WEB.recoleccionTotal20.om.recoleccionTotal;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_Validacion_boton_de_Cancelar extends BaseExecution{


		@Test(dataProvider = "data-provider", enabled = true)
		public void ATC_FT_002_Validacion_boton_de_Cancelar_test(HashMap<String, String> data) throws Exception {
			testCase.setProject_Name("Recoleccion Total 2.0");
			
			//variables
			SeleniumUtil u = new SeleniumUtil(new ChromeDriver());
			recoleccionTotal rt = new recoleccionTotal(u,testCase);
			String user = data.get("user");
			String pass = data.get("pass");
			String region = data.get("region");
			String plaza1 = data.get("plaza1");
			String tienda1 = data.get("tienda1");
			String cr1 = rt.getCR(tienda1);
			
			//Code
			addStep("Ingresar a Access Control. Permite ingresar al portal");
			rt.ingresarAuthFemco(user, pass);
			
			addStep("Iniciar sesión con credenciales de QA. Se incia correctamente");
			testCase.addScreenShotCurrentStep("");
			
			addStep("Ingresar a la opción Portal de Efectivo. Permite acceso a Portal de Efectivo.");
			rt.clickPortalEfectivo();
			
			addStep("Seleccionamos y llenamos los campos para realizar un registro, debera llevar Turno, Horario Inicio, Horario Fin, Region, Plaza y Tienda. Se llenan los capos mencionados. ");
			rt.showAllPages();
			rt.validateTiendaToDelete(cr1);
			rt.fillAllFields(region,plaza1,tienda1);
			
			addStep("Seleccionamos el botón de Guardar una vez que tengamos los campos con nuestros datos. Se permite continuar con guardado.");
			rt.clickBtnGuardar();
			testCase.addScreenShotCurrentStep("");
			
			addStep("Validamos que el registro aparesca en la seccion de Busqueda con la informacion correcta. Aparece registro.");
			rt.busqueda(cr1);
			
			addStep("Seleccionamos nuestro registro en la seccion de Busqueda dandole un click sobre el registro. Permite Seleccionar.");
			rt.selectRegistroGrid(cr1);
			
			addStep("Se habilitan los 3 campos de Turno, Hora Inicio, Hora Fin y tambien se habilitan los botónes de Actualizar y Cancelar.");
			rt.validateActiveField();
			
			addStep("Damos click en el botón de cancelar y se cancelara la seleccion del registro. Se cancela la seleccion.");
			rt.clickBtnCancelar();
			
			addStep("Validar que se cancele la seleccion del Registro, se limpien los campos de Turno, Hora Inicio, Hora Fin, y se desaparescan los botónes de Actualizar y Cancelar.");
			rt.validateInactiveField();
		}

		@Override
		public String setTestFullName() {
			return "ATC_FT_002_Validacion_boton_de_Cancelar";
		}

		@Override
		public String setTestDescription() {
			return "Cancelar la actualización de un turno registrado";
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






