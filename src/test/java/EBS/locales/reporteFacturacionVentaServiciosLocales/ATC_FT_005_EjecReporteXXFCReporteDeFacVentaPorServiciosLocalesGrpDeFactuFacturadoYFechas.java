package EBS.locales.reporteFacturacionVentaServiciosLocales;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.api.robot.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.locales.OMLocales.metodosLocales;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.locales.IO.Excel;
import EBS.util.locales.SQL.QuerysOracleLocales;
import EBS.util.locales.Sikuli.CommonMethodsSikuli;
import EBS.util.patternObject.PatternUtil;
import modelo.BaseExecution; import EBS.util.neteos.LoginNeteos.ClaseLogin;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
* DESCRIPCION: Clase de utileria para generar reporte de Fac. Venta por Servicios Locales - GRUPO DE FACTURACION / FACTURADO / FECHA ADMINISTRATIVA
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC-FT-005-Ejecucion Reporte XXFC - Reporte de Fac. Venta por Servicios Locales - GRUPO DE FACTURACION / FACTURADO / FECHA ADMINISTRATIVA
* 
* PREREQUISITOS:
* 	- Acceso al portal web EBS
* 	- Usar Microsoft Edge en modo Internet Explorer
* 
* INSUMOS:
* 	- Tener datos de Fac. Venta por Servicios Locales en Base de Datos
*
 * @author Francisco Javier Mendoza Tamayo
 * @author David Quiroz Rodriguez
 * @date 17/Diciembre/2024
 * * // Actualizacion de scripts para entrar en metricas de fabrica
 * @date 23/Enero/2025
*/

public class ATC_FT_005_EjecReporteXXFCReporteDeFacVentaPorServiciosLocalesGrpDeFactuFacturadoYFechas extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_EjecReporteXXFCReporteDeFacVentaPorServiciosLocalesGrpDeFactuFacturadoYFechas_test(HashMap<String, String> data) throws Exception {
		/*Variables
		 * */
		String ebsUrl = data.get("server");
		String userName = data.get("userName");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
		String cambioResponsabilidad = data.get("cambioResponsabilidad");
		String nombreReporte = data.get("nombreReporte");
		String grupoEjecucion = data.get("grupoEjecucion");
		String estadoFactura = data.get("estadoFactura");
		String fechaAdministrativaInicial = data.get("fechaAdministrativaInicial");
		String fechaAdministrativaFinal = data.get("fechaAdministrativaFinal");
		String responsabilidadReporte = data.get("responsabilidadReporte");
		String inicioDeFecha = Excel.convertDateFormat(fechaAdministrativaInicial);
		String finDeFecha = Excel.convertDateFormat(fechaAdministrativaFinal);
		String finFechaArchivo = Excel.getFormattedDate();
		String obtenerValorDeCasislla = ""; // Se declara para obtener los datos de lotes para generar una validacion
		String obtenerValorEncasoDeSerVacio = ""; //Se declara para que en caso de que no se encuentre en lote tomar otro valor
		String obtenerIdGrupo="";
		int totalRows;
		//Arrays para validaciones
		ArrayList<String> valoresObtenidosDeQuerySQL = new ArrayList<>();
		//Archivo Reporte
		String reporteAValidar = System.getProperty("user.home") + "\\Downloads\\XXFC___Reporte_de_Fac__Venta_p_" + finFechaArchivo + ".xls";

		String path = "\\Patterns\\Locales\\";
		/*Utilerias
		 * */
		SeleniumUtil u = new SeleniumUtil(new IETest(), true);
		metodosLocales MTLocales = new metodosLocales(u, testCase, ebsUrl, userName, password);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("EBS\\"+"locales");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		MetodosSikuli p = new MetodosSikuli(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		PatternUtil patternUtil = new PatternUtil(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol);
		/*Utilerias SQL
		 * */
		SQLUtil dbAvebqa = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA,
				GlobalVariables.DB_USER_AVEBQA, GlobalVariables.DB_PASSWORD_AVEBQA);
		/*Variables SQL
		 */
		/*Obtner el customer id para servicio*/
		String queryCurtomerID=QuerysOracleLocales.queryParaObtenerIDGrupo(grupoEjecucion);
		SQLResult ValidarQueryCustID = dbAvebqa.executeQuery(queryCurtomerID);
		obtenerIdGrupo= ValidarQueryCustID.getData(0,1);
		System.out.println(obtenerIdGrupo);
		String Query1 = QuerysOracleLocales.queryReporteGrpFacturacionFacturadoyFechasparte1(inicioDeFecha, finDeFecha, responsabilidadReporte,obtenerIdGrupo);
		String Query2=QuerysOracleLocales.queryReporteGrpFacturacionFacturadoyFechasparte2(inicioDeFecha, finDeFecha, responsabilidadReporte,obtenerIdGrupo);;
		String Query3=QuerysOracleLocales.queryReporteGrpFacturacionFacturadoyFechasparte3(inicioDeFecha, finDeFecha, responsabilidadReporte,obtenerIdGrupo);;;
		String Query4=QuerysOracleLocales.queryReporteGrpFacturacionFacturadoyFechasparte4(inicioDeFecha,finDeFecha,responsabilidadReporte,obtenerIdGrupo);
		/*Inicio de pasos*/
		//Paso 1
		addStep(" Ingresar a la Url de EBS y generar login");
		MTLocales.LoginIEEBS();
		u.hardWait(25);
		//Paso 2
		addStep("Seleccionar carpeta responsabilidad y app javaServerlet en pagina EBS");
		MTLocales.theUserSelectsTheValueOfTheEBSPage(carpetaResponsabilidad); //Seleccionamos la responsabilidad
		MTLocales.theUserSelectTheAppJavaUsuarios(carpetaResponsabilidad); // Se selecciona la app Java de la carpeta responsabilidad
		//Paso 3
		addStep("Abrir el appserveler");
		//Run app Serverlet java
		CommonMethodsSikuli.theUserWaitsToApproveAndRunApplication("checkboxIAcceptTheRiskAndWantToRunThisApplication", "buttonRun");
		testCase.addScreenShotCurrentStep("Abriendo la aplicacion java");
		//Paso 4
		addStep("Cambio de responsabilidad");
		boolean statusSeccionUsuarios = CommonMethodsSikuli.waitForImage("seccionUsuarios.png", path);
		assertTrue(statusSeccionUsuarios);
		testCase.addScreenShotCurrentStep("Visualizar apartado Usuarios");
		boolean statusCerrarSesionUsuarios = CommonMethodsSikuli.theUserClicksOn("btnCerrarApartadoUsuarios");
		assertTrue(statusCerrarSesionUsuarios);
		testCase.addScreenShotCurrentStep("Cerrar apartado Usuarios");
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Seleccionar el boton de cambio de responsabilidad ");
		boolean statusCambiarResponsabilidad = CommonMethodsSikuli.theUserClicksOn("cambiarResponsabilidad");
		assertTrue(statusCambiarResponsabilidad);
		testCase.addScreenShotCurrentStep("Seleccionar Responsabilidad");
		boolean statusSeleccionarResponsabilidad = CommonMethodsSikuli.theUserSendTextIn("txtIngresarResponsabilidad", cambioResponsabilidad);
		assertTrue(statusSeleccionarResponsabilidad);
		testCase.addScreenShotCurrentStep("Ingresar Responsabilidad");
		u.hardWait(3);
		sk.screen.type(Key.ENTER);
		u.hardWait(3);
		//Paso 5
		addStep("Ir a solicitudes");
		testCase.addScreenShotCurrentStep("Ir al menu Ver");
		boolean statusMenuVer = CommonMethodsSikuli.theUserClicksOn("optionMenuVer");
		assertTrue(statusMenuVer);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Seleccionar solicitudes");
		boolean statusSeleccionarSolicitudes = CommonMethodsSikuli.theUserClicksOn("optionSolicitudes_Ver");
		assertTrue(statusSeleccionarSolicitudes);
		u.hardWait(7);
		//Paso 6
		addStep("Generar una nueva solicitud");
		testCase.addScreenShotCurrentStep("Seleccionar boton Ejecutar Nueva Solicitud");
		boolean statusGenerarNuevaSolicitud = CommonMethodsSikuli.theUserClicksOn("EjecutarNuevaSolicitud");
		assertTrue(statusGenerarNuevaSolicitud);
		u.hardWait(4);
		boolean statusIngresarNombreReporte = CommonMethodsSikuli.theUserSendTextIn("Nombre_EjecutarEstaSolicitud", nombreReporte);
		assertTrue(statusIngresarNombreReporte);
		testCase.addScreenShotCurrentStep("Se ingresa nombre de reporte");
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		u.hardWait(3);
		//Paso 7
		addStep("Ingresar parametros para solicitud");
		boolean statusInputNumeroProveedor = CommonMethodsSikuli.theUserSendTextIn("inputGrupoEjecucion_Parametros", grupoEjecucion);
		assertTrue(statusInputNumeroProveedor);
		u.hardWait(3);
		boolean statusInputEstadoFacturaParametros = CommonMethodsSikuli.theUserSendTextIn("inputEstadoFactura_Parametros", estadoFactura);
		assertTrue(statusInputEstadoFacturaParametros);
		u.hardWait(3);
		boolean statusInputFechaAdministrativaInicial = CommonMethodsSikuli.theUserSendTextIn("inputFechaAdministrativaInicial_Parametros", fechaAdministrativaInicial);
		assertTrue(statusInputFechaAdministrativaInicial);
		u.hardWait(3);
		sk.screen.type(Key.BACKSPACE);
		boolean statusInputFechaAdministrativaFinal = CommonMethodsSikuli.theUserSendTextIn("inputFechaAdministrativaInicial_Parametros", fechaAdministrativaFinal);
		assertTrue(statusInputFechaAdministrativaFinal);
		testCase.addScreenShotCurrentStep("Se ingresaron los parametros y se aceptan para la solicitud");
		boolean statusAceptarParametros = CommonMethodsSikuli.theUserClicksOn("botonAceptarParametros");
		assertTrue(statusAceptarParametros);
		u.hardWait(3);
		//Paso 8
		addStep("Ejecutar Solicitud");
		boolean statusEjecutarSolicitud = CommonMethodsSikuli.theUserClicksOn("botonEjecutar");
		assertTrue(statusEjecutarSolicitud);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Se ejecuta la solicitud y damos click en no para no ejecutar otra");
		boolean statusClickBotonNo = CommonMethodsSikuli.theUserClicksOn("optionNo");
		assertTrue(statusClickBotonNo);
		u.hardWait(5);
		//Paso 9
		addStep("Encontrar Solicitud");
		testCase.addScreenShotCurrentStep("Selecionar boton encontrar");
		boolean statusBotonEncontrarSolicitud = CommonMethodsSikuli.theUserClicksOn("botonEncontrar");
		assertTrue(statusBotonEncontrarSolicitud);
		u.hardWait(15);
		//Paso 10
		addStep("Esperar a que se ejecute reporte");
		testCase.addScreenShotCurrentStep("El Reporte se esta ejecutando");
		CommonMethodsSikuli.theUserChecksIfTheReportIsRunning("faseEjecucionReporte", "botonRefrescarDatos");
		testCase.addScreenShotCurrentStep("Se Finalizo el reporte");
		//Paso 11
		addStep("Visualizar Output");
		boolean statusVisualizarOutput = CommonMethodsSikuli.theUserClicksOn("botonVisualizarOutput_Solicitudes");
		assertTrue(statusVisualizarOutput);
		u.hardWait(30);
		testCase.addScreenShotCurrentStep("Imagen del reporte descargado");
		//Paso 12
		addStep("Extraer la informacion de la base de datos");
		System.out.println(GlobalVariables.DB_HOST_AVEBQA); //Imprimimos a la base de datos que deseamos ingresar
		System.out.println(Query1);
		SQLResult ValidQuery1 = dbAvebqa.executeQuery(Query1); //Generamos la consulta en la base de datos
		SQLResult ValidQuery2= dbAvebqa.executeQuery(Query2);
		SQLResult ValidQuery3= dbAvebqa.executeQuery(Query3);
		SQLResult ValidQuery4= dbAvebqa.executeQuery(Query4);
		if (ValidQuery1.isEmpty()) { //Si la consulta viene vacia generamos lo siguiente
			testCase.addTextEvidenceCurrentStep("La consulta que se genero viene vacia "); // Agregamos el texto que la query viene vacia
			testCase.addQueryEvidenceCurrentStep(ValidQuery1); // Agregamos el resultado de la query
			testCase.addTextEvidenceCurrentStep("Revisar las fechas ingresadas, ya no contienen datos No facturados"); // Agregamos el texto que la query viene vacia
			System.exit(0);
		} else { //En caso de que si tenga resultados entonces
			totalRows = ValidQuery1.getRowCount(); //Obtenemos el numero total de rows que tiene la consulta
			testCase.addTextEvidenceCurrentStep("Resultado obtenido de la consulta "); //Agregamos esta leyenda en el template de evidencias
			testCase.addQueryEvidenceCurrentStep(ValidQuery1);  // Agregamos el resultado de la query en el template
			testCase.addQueryEvidenceCurrentStep(ValidQuery2);
			testCase.addQueryEvidenceCurrentStep(ValidQuery3);
			testCase.addQueryEvidenceCurrentStep(ValidQuery4);
			System.out.println("Numero de columnas total " + totalRows);
			for (int inicioRecolecion = 0; inicioRecolecion < totalRows; inicioRecolecion++) {
				obtenerValorDeCasislla = ValidQuery3.getData(inicioRecolecion, 0);
				if (obtenerValorDeCasislla == null) { //Se revisa si el valor obtenido es nulo tenemos que extraer el numero de transaccion
					obtenerValorEncasoDeSerVacio = ValidQuery3.getData(inicioRecolecion, 1);//Se obtiene el numero de transaccion.
					valoresObtenidosDeQuerySQL.add(obtenerValorEncasoDeSerVacio); //Se obtiene el numero de transaciones que se guarda en el arreglo
				} else {
					valoresObtenidosDeQuerySQL.add(obtenerValorDeCasislla); //Se obtiene el numero de lote que se guarda en el arreglo.
				}
			}
		}
		System.out.println("Valores obtenidos en el arreglo: " + valoresObtenidosDeQuerySQL);
		//Paso 13
		u.hardWait(12);
		addStep("Validar los datos de base de datos vs la del reporte");
		//Abrir reporte
		boolean statusAbrirReporte = CommonMethodsSikuli.theUserClicksOn("btnLinkAbrirReporte");
		assertTrue(statusAbrirReporte);
		u.hardWait(15);
		sk.screen.type(Key.ENTER);
		u.hardWait(10);
		String  texto = CommonMethodsSikuli.getAllTextPage(); //Obtenemos el texto del documento
		System.out.println(texto);
		for (String datosArreglo : valoresObtenidosDeQuerySQL) {
			boolean expeted = texto.contains(datosArreglo);
			if (expeted == true) {
				testCase.addTextEvidenceCurrentStep("El valor " + datosArreglo + " se encuentra en el archivo descargado");
			} else {
				testCase.addTextEvidenceCurrentStep("El valor " + datosArreglo + " no se encuentra en el archivo descargado");
				System.out.println("Se para ejecucion ya que no se encuentra el valor en el archivo desvargado");
				System.exit(0);
			}
		}
		testCase.addScreenShotCurrentStep("Reporte abierto");
		boolean statusCerrarReporte = CommonMethodsSikuli.theUserClicksOn("btnCerrarReporte");
		assertTrue(statusCerrarReporte);
		// Cerrar reporte
		//Cambiar a la aplicacion de Java
		// PASO 14
		u.hardWait(3);
		addStep("Cerrar Oracle");
		boolean statusClickAppJava = CommonMethodsSikuli.theUserClicksOn("iconoAppJava");
		assertTrue(statusClickAppJava);
		testCase.addScreenShotCurrentStep("Regresar a la aplicacion de Java");
		System.out.println("Se Cambia al serverlet de java");
		u.hardWait(5);
		boolean statusCerrarOracle = CommonMethodsSikuli.theUserClicksOn("btnCerrarAppJava");
		assertTrue(statusCerrarOracle);
		u.hardWait(7);
		testCase.addScreenShotCurrentStep("Cerrar aplicacion Java");
		boolean statusAceptarSalirOracleApplications = CommonMethodsSikuli.theUserClicksOn("botonAceptarSalirOracleApplications");
		assertTrue(statusAceptarSalirOracleApplications);
		u.hardWait(3);
		if (u != null) {
			u.quit();
		}
		Excel.deleteFile(reporteAValidar);
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_EjecuciOn_Reporte_XXFC_Reporte_de_Fac_Venta_por_Servicios_Locales_GRUPO_DE_FACTURACION_FACTURADO_FECHA_ADMINISTRATIVA_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se genere Reporte_XXFC_Reporte_de_Fac_Venta_por_Servicios_Locales_GRUPO_DE_FACTURACION_FACTURADO_FECHA_ADMINISTRATIVA";
	}

	@Override
	public String setTestDesigner() {
		return "davidq1@hexaware.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con ambientaci�n en BD para los registros de reporte";
	}

	@Override
	public void beforeTest() {

	}
}