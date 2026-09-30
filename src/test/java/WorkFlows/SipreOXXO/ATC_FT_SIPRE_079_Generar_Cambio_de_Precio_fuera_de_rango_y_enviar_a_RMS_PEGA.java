package WorkFlows.SipreOXXO;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreOXXO.pomSipreOXXO;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.ExcelUtil;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_SIPRE_079_Generar_Cambio_de_Precio_fuera_de_rango_y_enviar_a_RMS_PEGA extends BaseExecution{
	@Test(dataProvider = "data-provider")
	 public void ATC_FT_SIPRE_079_Generar_Cambio_de_Precio_fuera_de_rango_y_enviar_a_RMS_PEGA_Test(HashMap<String, String> data) throws Exception {
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
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
	
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
				addStep("Ingresar a la liga http://fcovgleen01.comercio.fne/UltWeb/UltThinClient/Login.aspx");
				



				// paso 18
				addStep(" Ingresar las credenciales de Usuario Category" + 
						"Usuario: 6006021 (Yogurt Batido)" + 
						"Contraseña:(Sin contraseña)" + 
						"Dominio: comercio.fne");
				u.getDriver().quit();
				
				SeleniumUtil u1 = new SeleniumUtil(new FirefoxTest(), true);
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
		

				// paso 23
				addStep("Seleccionar el menu Respuesta a Excepcion");
				util1.clickMenuConfiguracion("Respuesta de Excepciones");
				testCase.addScreenShotCurrentStep("Respuesta de Excepciones");

				// paso 24
				addStep("Seleccionar los filtros que se necesitan");

				// paso 25
				addStep("Dar clic en buscar");

				// paso 26
				addStep("Seleccionar el item");


				// paso 27
				addStep("Seleccionar en el campo Respues, la opción rechazar");


				// paso 28
				addStep("Capturar información de racional de category");

				// paso 29
				addStep("Dar click en Aplicar");
				util.AplicarCambioPrecioRespuestaExcepciones(respuesta, nuevoPuntoEjecuccion);
				
				// paso 30
				addStep("Abrir navegador Edge/FireFOX/Chrome");
				
				// paso 31
				addStep("Ingresar a la liga http://10.185.176.11:7011/prweb/app/default/beEBp4uRVTogorRwSwWqbOtn9IL2fwdI*/!STANDARD");
			
				// paso 32
				addStep("Ingresar las credenciales de Usuario Plaza" + 
						"Usuario: (Cuenta de Red Configurada)" + 
						"Contraseña: Contraseña de la cuenta de red");

				// paso 33
				addStep("Dar click al boton Iniciar Sesion");
				
				// paso 34
				addStep("Seleccionar la opcion  y dar click Mostrar Aplicaciones WEB");
				
				// paso 35
				addStep("Seleccionar la opción Sistema De Precios");
				
				// paso 36
				addStep("Seleccionar el Menu Configuracion");
				
				// paso 37
				addStep("Seleccionar Mantenimientos de precio");
				
				// paso 38
				addStep("Se llenan los siguientes filtros:" + 
						"Region: La configurada para el usuario" + 
						"PLAZAPRECIOS:TODOS" + 
						"ESTATUS: EXCEPCIÓN ACEPTADA" + 
						"ZONA DE PRECIOS: OPCIÓN" + 
						"SUPERGRUPO: OPCIÓN" + 
						"CATEGORIA: OPCIÓN" + 
						"SUBCATEGORIA: OPCIÓN" + 
						"SEGMENTO: OPCIÓN" + 
						"SUBSEGMENTO: OPCIÓN" + 
						"BUSQUEDA: ITEM QUE CAMBIAMOS PRECIOS");
				// paso 39
				addStep("Dar clic en buscar");
	}
	
	@AfterMethod
	public void afterMethod() {
		//
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
		return "ATC_FT_SIPRE_079_Generar_Cambio_de_Precio_fuera_de_rango_y_enviar_a_RMS_PEGA_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
