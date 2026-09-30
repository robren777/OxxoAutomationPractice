package WM.EvolucionRetiroSinTarjeta.HU07;

import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.tools.CrearComisionesTool;
import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import EBS.util.pageObjectModel.EBSCrearComisiones;
import EBS.util.pageObjectModel.EBSMantenimientoCuentasComisiones;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_012_Validar_Alta_de_Comisiones_que_se_efectue_correctamente_el_alta_de_una_comision extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_012_Validar_Alta_de_Comisiones_que_se_efectue_correctamente_el_alta_de_una_comision_test(HashMap<String, String> data)
			throws Exception{

				/**
				 * Plataforma de EBS http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp
				 * Desc:
				 * Validar Alta de Comisiones que se efectue correctamente el alta de una comision
				 * @author Mónica Soto
				 * @date 01/03/2024
				 **/

				/*
		         * VARIABLES
		         */
				String ebsUrl = GlobalVariables.getAvanteServer;
		        String userName = GlobalVariables.get6008201User;
		        String password = GlobalVariables.get6008201Pswd;
		        
		        String superUsuario = data.get("superUsuario");
		        String carpetaOtros = data.get("carpetaOtros");
		        String mantenimientoCuentas = data.get("mantenimientoCuentas");
		        String formaPago = data.get("formaPago");
				String comision = data.get("comision");
				
				// esto se hace para obtener la fecha del dia de hoy en la variable "fechaDesde", 
				String fechaDesde = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(format.parse(fechaDesde));
				
				// asi como la fecha del dia de hoy + 1 para la variable "fechaHasta"
				calendar.add(Calendar.DATE, 1); 
				String fechaHasta = format.format(calendar.getTime());
				
				String msjComisionCreada = data.get("msjComisionCreada");
		
				testCase.setTest_Description(data.get("id") + data.get("description"));
				
				/*
				 * Utilerias
				 *********************************************************************/
		
				SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		        EBSPaginaInicial paginaInicial = new EBSPaginaInicial(u, testCase);
		    	LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		      
				EBSMantenimientoCuentasComisiones mantCuentasContables = new EBSMantenimientoCuentasComisiones(u, testCase);
				EBSCrearComisiones crearComisiones = new EBSCrearComisiones(u, testCase);
				CrearComisionesTool comisionesTool = new CrearComisionesTool(u, testCase);
		
				System.out.println(
						"*****************************************PASO 1 y 2************************************************");
				addStep("Se ingresa a la página de EBS");
				inicio.login();
		
				addStep("Iniciar sesion con el usuario y contraseña al portal de EBS");	
				testCase.addScreenShotCurrentStep(u, "Portal de EBS");
				
				System.out.println(
						"*****************************************PASO 3************************************************");
				
				addStep("Ingresar a la responsabilidad MXOXXO-GLXXXXX-Super Usuario");
				
		        paginaInicial.clickOnResponsabilidad(superUsuario);
				testCase.addScreenShotCurrentStep(u, "MXOXXO-GLXXXXX-Super Usuario");
				
				System.out.println(
						"*****************************************PASO 4************************************************");
				
				addStep("Ingresar a la opcion Otros: MTC Mantenimiento Cuentas y Comisiones");

		        paginaInicial.clickOnCarpeta(carpetaOtros);
		        paginaInicial.clickOnResponsabilidad(mantenimientoCuentas);
		      
				testCase.addScreenShotCurrentStep(u, "MTC Mantenimiento Cuentas y Comisiones");
		
		        
				System.out.println(
						"*****************************************PASO 5************************************************");
				
				addStep("Seleccionar la pestaña Comisiones, clic boton Crear");
		
				mantCuentasContables.btnComisionesElement.click();
				mantCuentasContables.btnCrearComElement.click();
		
				testCase.addScreenShotCurrentStep(u, "Crear");
				
				System.out.println(
						"*****************************************PASO 6************************************************");
				
				addStep("Ingresar los parametros, se marca * los campos obligatorios:");
				crearComisiones.estadoElement.click();
				comisionesTool.camposObligatoriosCrearComisiones(formaPago, comision, fechaDesde, fechaHasta);
				testCase.addScreenShotCurrentStep(u, "Crear Comisiones");
		
				System.out.println(
						"*****************************************PASO 7************************************************");
				
				addStep("Clic boton Aplicar");
				
				crearComisiones.aplicarElement.click();
				assertEquals(crearComisiones.msjComisionCreadaElement.getText(), msjComisionCreada);

				testCase.addScreenShotCurrentStep(u, "Mensaje Aviso");
				u.quit();
			}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestDescription() {
	
		return "Validar Alta de Comisiones que se efectue correctamente el alta de una comision";
	}
	
	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_012_Validar_Alta_de_Comisiones_que_se_efectue_correctamente_el_alta_de_una_comision_test";
	}
	
	@Override
	public String setTestInstanceID() {
		return null;
	}
	
	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
	
	}
}
