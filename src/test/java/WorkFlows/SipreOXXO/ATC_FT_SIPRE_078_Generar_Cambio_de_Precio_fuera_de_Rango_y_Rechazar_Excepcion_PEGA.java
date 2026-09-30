package WorkFlows.SipreOXXO;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreOXXO.pomSipreOXXO;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.ExcelUtil;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_SIPRE_078_Generar_Cambio_de_Precio_fuera_de_Rango_y_Rechazar_Excepcion_PEGA extends BaseExecution{
	SeleniumUtil u;
	SeleniumUtil u1;
	
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_SIPRE_078_Generar_Cambio_de_Precio_fuera_de_Rango_y_Rechazar_Excepcion_PEGA_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Workflows Library: Sipre Oxxo");
	
	/**
	 * Variables
	 * 
	 */
		
		String region = data.get("Region");
		String plazaPrecio = data.get("PlazaPrecio");
		String zonaPrecio = data.get("ZonaPrecio");
		String superGrupo  = data.get("SuperGrupo");
		String categorias = data.get("Categorias");
		String tipoRegla = data.get("TipoRegla");
		String buscar = data.get("Buscar");
		String racional = data.get("Racional");
		String nuevoPuntoEjecuccion = data.get("NuevoPuntoEjecuccion");
		String rutaExcel = data.get("ExcelPath");
		String estatus = data.get("Estatus");
		String respuesta = data.get("Respuesta");
		
		
		

	


	/**
	 * 
	 * Utilerias
	 */
		u = new SeleniumUtil(new FirefoxTest(), true);
	
		ExcelUtil Excel = new ExcelUtil(rutaExcel);
		String windowUltimus = u.getDriver().getWindowHandle();
		pomSipreOXXO util = new pomSipreOXXO(u,testCase);
		pomUltimus ultimus = new pomUltimus (u);
		
		
		
		
		

				// paso 1 
				addStep("Seleccionar el Menu Configuracion");
				util.loginPega(GlobalVariables.userSipreOXXOPlazaPEGA, GlobalVariables.passSipreOXXOPlazaPEGA);
				testCase.addScreenShotCurrentStep("Login");
				util.selectMenuSistemaPrecios();
				testCase.addScreenShotCurrentStep("Sistema De Precios");
				
				// paso 2
				addStep("Seleccionar Puntos de Ejecución");
				util.clickMenuConfiguracion("Punto ejecución");
				testCase.addScreenShotCurrentStep("Seleccionar Puntos de Ejecución");
		
				// paso 3
				addStep("Validar que el contador de Puntos de Ejecucion Pendientes sea mayor a 0"); 
				util.ValidarContadorPuntosEjecucionPendientes(); testCase.
				addScreenShotCurrentStep("Validar que el contador de Puntos de Ejecucion Pendientes sea mayor a 0");

				// paso 4 
				addStep("Seleccionar los siguientes filtros:" +
						"1. Region (la configurada en palza)" + "2. Plaza Precios: Opción" +
						"3. Estatus: PENDIENTES PUNTOS DE EJECUCIÓN" + "4.Zona de Precio:Opción" +
						"5. Supergrupo: Opción");


				// paso 5 
				addStep("Dar click a la opción Buscar");
				util.filtrosPuntoEjecucion(plazaPrecio, superGrupo, categorias, estatus,zonaPrecio, buscar);

				// paso 6 
				addStep("Seleccionar un item");


				// paso 7 
				addStep("Seleccionar uno o más chekbox de Articulos Zona");

				// paso 8
				addStep("En el cuadrante 4, de Punto de Ejecuión Precio, Capturar en el campo Punto de Ejecucion un valor que sobrepse el valor maximo de Punto de Ejecucion que nos muestra el cuadrante 3");

				// paso 9 
				addStep("Presionar la tecla TAB");

				// paso 10 
				addStep("Dar clic en boton aplicar");

				// paso 11 
				addStep("Dar clic a boton Aceptar");


				// paso 12
				addStep("Validar que aparezca el Folio de Excepcion en la columna Folio de Excepcion");

				// paso 13
				addStep("Seleccionar el racional de : Excepción solicitada por categoria");

				// paso 14 
				addStep("Seleccionar la duración de 30 dias en el radio boton");

				// paso 15 
				addStep("Dar clic en aplicar"); 
				util.AplicarCambioPrecio(racional,nuevoPuntoEjecuccion);
				u.getDriver().quit();
				
				

				// paso 16 
				addStep("Abrir navegador IE11/Edge");



				// paso 17
				addStep("17. Ingresar a la liga http://fcovgleen01.comercio.fne/UltWeb/UltThinClient/Login.aspx");
				



				// paso 18
				addStep(" Ingresar las credenciales de Usuario Category" + 
						"Usuario: 6006021 (Yogurt Batido)" + 
						"Contraseña:(Sin contraseña)" + 
						"Dominio: comercio.fne");
			
				
				u1 = new SeleniumUtil(new FirefoxTest(), true);
				pomSipreOXXO util1 = new pomSipreOXXO(u1,testCase);
				pomUltimus ultimus1 = new pomUltimus (u1);
				
			
				
				// paso 19
				addStep("Dar click al boton Conectarse");


				// paso 20
				addStep("Seleccionar la opcion Iniciar del menu de Vistas");

				// paso 21
				addStep("Dar doble click a la opcion del proceso de SIPRE");
			
				ultimus1.loginUltimus(GlobalVariables.userSipreOXXOUltimusCategory , GlobalVariables.passSipreOXXOUltimusCategory);
				testCase.addScreenShotCurrentStep("Login");
				ultimus1.seleccionarVista("Iniciar");
				ultimus1.openSipreOXXO(GlobalVariables.userPortalSipreOXXO, GlobalVariables.passPortalSipreOXXO);
				testCase.addScreenShotCurrentStep("SIPRE");

				

				// paso 22
				addStep("Seleccionar el menu de Configuracion");
		
				u1.waitWebElement(By.xpath("//*[@id='text-container']"), 80, 20);
				String url = u1.getDriver().getCurrentUrl();
				System.out.print("URL---> "+ url);
				String[] arrSplit = url.split("https://fcowlq09");
				String nuevaURL = "https://fcportalqa" + arrSplit[1];
				System.out.println("Nueva URL---> "+ nuevaURL);
				u1.getDriver().close();
				util1.switchToAnotherWindow(1, "cambiar", true);
				WebElement body = u1.getDriver().findElement(By.tagName("body"));
				body.sendKeys(Keys.CONTROL + "t");
				
		
				
				// paso 23
				addStep("Seleccionar el menu Respuesta a Excepcion");
				util1.clickMenuConfiguracion("Respuesta de Excepciones");
				testCase.addScreenShotCurrentStep("Respuesta de Excepciones");

				// paso 24
				addStep("Seleccionar los filtros que se necesitan");
				
				// paso 25
				addStep("Dar clic en buscar");
				util1.filtrosRespuestaExcepciones(region, superGrupo, categorias, buscar);

				// paso 26
				addStep("Seleccionar el item");


				// paso 27
				addStep("Seleccionar en el campo Respues, la opción rechazar");


				// paso 28
				addStep("Capturar información de racional de category");

				// paso 29
				addStep("Dar click en Aplicar");

				util.AplicarCambioPrecioRespuestaExcepciones(respuesta, nuevoPuntoEjecuccion);

	}
	
	@AfterMethod
	public void afterMethod() {
		u1.getDriver().quit();
	}
	
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Tener Acceso Dominio Oxxo" + 
				"Contar con Orgchart de sipre" + 
				"Contar con usuarios configurados al orgchart" + 
				"Contar con JFG de perfil adecuado en Ultimus o Pega" + 
				"Contar con Acceso a Control -M" + 
				"Solicitar el Holdeo de los JOBS a ejecutar" + 
				"Contar con Acceso al esquema de XXWF_SIPRE2" + 
				"CONEXION FCIAS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se rechaze una Excepción de precio fuera de rango de bandas permitidad por el usuario category";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_SIPRE_078_Generar_Cambio_de_Precio_fuera_de_Rango_y_Rechazar_Excepcion_PEGA_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
