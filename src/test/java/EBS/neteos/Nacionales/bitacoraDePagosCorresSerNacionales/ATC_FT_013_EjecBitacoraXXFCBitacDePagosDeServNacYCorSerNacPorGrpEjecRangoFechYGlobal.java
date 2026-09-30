package EBS.neteos.Nacionales.bitacoraDePagosCorresSerNacionales;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.locales.OMLocales.metodosLocales;
import EBS.neteos.Nacionales.OMNacionales.metodosNacionales;
import EBS.util.GlobalVariables;
import EBS.util.locales.IO.Excel;
import EBS.util.locales.SQL.QuerysBitacoraDePagosNacionales;
import EBS.util.locales.Sikuli.CommonMethodsSikuli;
import EBS.util.neteos.SikuliNeteos.MetodosSikuli;
import EBS.util.neteos.SikuliNeteos.PatternUtil;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION: Clase de utileria para Creacion de la bitacora XXFC - Bitacora de Pagos de Servicios Nacionales y Corresponsalías de Servicios Nacionales por grupo de ejecucion, rango de fechas y de forma Global.
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-013-Generacion de reporte Bitacora de Pagos para SN por Grupo de Ejecucion, Rango de Fechas y Global
 * <p>
 * PREREQUISITOS:
 * 1. Contar con la responsabilidad "MXOXXO-AP17ADM-USUARIO_OS_ADTIVO_OS".
 * 2. Deben exisistir facturas ya pagadas para Servicios Nacionales.
 * 3. Los proveedores deben de estar activos.
 * 4. Los servicios deben estar activos en vista XXFC_RETEK_ITEM_SERVICIOS_V.
 * 5. Acceso al portal web EBS.
 *
 * @author David Quiroz Rodriguez
 * @date 23/Mayo/2025
 */

public class ATC_FT_013_EjecBitacoraXXFCBitacDePagosDeServNacYCorSerNacPorGrpEjecRangoFechYGlobal extends BaseExecution {
    @Test(dataProvider = "data-provider")
    public void ATC_FT_013_EjecBitacoraXXFCBitacDePagosDeServNacYCorSerNacPorGrpEjecRangoFechYGlobal_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
        String cambioResponsabilidad = data.get("cambioResponsabilidad");
        String nombreReporte = data.get("nombreReporte");
        String tipoServicio = data.get("tipoServicio");
        String grupoDeEjecucion = data.get("grupoDeEjecucion");
        String fechaPagoInicial = data.get("fechaPagoInicial");
        String fechaPagoFinal = data.get("fechaPagoFinal");
        String tipoBitacora = data.get("tipoBitacora");
        String fechaInicio = Excel.convertDateFormat(fechaPagoInicial);
        String fechaFin = Excel.convertDateFormat(fechaPagoFinal);
        String tipoDeServicioUppercase = tipoServicio.toUpperCase();
        String obtenerValorDeCantidadTotal = "";
        String obtenerValorDeGrupoEjecucion = "";
        String obtenerValorDeNombreDeServicio = "";
        ArrayList<String> grupoDeCantidadesObtenidasDelQuery = new ArrayList<>();
        ArrayList<String> grupoDeGruposDeEjecucionObtenidasDelQuery= new ArrayList<>();
        ArrayList<String> grupoDeNombreDeServiciosObtenidasDelQuery= new ArrayList<>();
        String path = "\\Patterns\\Locales\\";
        int totalRows;
        String finFechaArchivo = Excel.getFormattedDate();
        String reporteAValidar = System.getProperty("user.home") + "\\Downloads\\XXFC_Bitacora_de_Pagos_de_Serv_" + finFechaArchivo + ".xls";
        /*Utilerias
         * */
        SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
        metodosLocales MTLocales = new metodosLocales(u, testCase, ebsUrl, userName, password);
        metodosNacionales MTNacionales = new metodosNacionales(u, testCase);
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
        String tipoServicioN=tipoDeServicioUppercase.substring(10,18);
        System.out.println(tipoServicioN);
        String queryCodigoDeGrupo= QuerysBitacoraDePagosNacionales.queryConsultaCodigoDeGrupoDeEjecucion(grupoDeEjecucion,tipoServicioN);
        SQLResult validarQueryCodigoDeGrupo=dbAvebqa.executeQuery(queryCodigoDeGrupo);
        String codigodeGrupoObtenido=validarQueryCodigoDeGrupo.getData(0,1);
        System.out.println("Numero de codigo de grupo obtenido: "+ codigodeGrupoObtenido);
        String queryBitacoraGlobalGrupoDeEjecucion = QuerysBitacoraDePagosNacionales.queryCorresponsaliasYServNacGrupoDeEjecucionGlobal(fechaInicio, fechaFin, codigodeGrupoObtenido);
        System.out.println(queryBitacoraGlobalGrupoDeEjecucion);
        /*Pasos para el caso de prueba
         * */
        //Paso 1
        addStep(" Ingresar a la Url de EBS y generar login");
        MTLocales.LoginIEEBS();
        u.hardWait(15);
        //Paso 2
        addStep("Seleccionar carpeta responsabilidad y app javaServerlet en pagina EBS");
        MTLocales.theUserSelectsTheValueOfTheEBSPage(carpetaResponsabilidad); //Seleccionamos la responsabilidad
        MTLocales.theUserSelectTheAppJavaUsuarios(carpetaResponsabilidad); // Se selecciona la app Java de la carpeta responsabilidad
        u.hardWait(5);
        MTNacionales.abrirServerletDeJavaDesdeDescargas();
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
        testCase.addScreenShotCurrentStep("Seleccionar el boton de de sombrero ");
        boolean statusCambiarResponsabilidad = CommonMethodsSikuli.theUserClicksOn("cambiarResponsabilidad");
        assertTrue(statusCambiarResponsabilidad);
        testCase.addScreenShotCurrentStep("Seleccionar Responsabilidad");
        boolean statusSeleccionarResponsabilidad = CommonMethodsSikuli.theUserSendTextIn("txtIngresarResponsabilidad", cambioResponsabilidad);
        assertTrue(statusSeleccionarResponsabilidad);
        testCase.addScreenShotCurrentStep("Ingresar Responsabilidad");
        u.hardWait(3);
        sk.screen.type(org.sikuli.script.Key.ENTER);
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
        sk.screen.type(org.sikuli.script.Key.TAB);
        u.hardWait(3);
        //Paso 7 Ingresar parametros
        addStep("Ingresar parametros para solicitud de la bitacora XXFC - Bitacora de Pagos de Servicios Nacionales y Corresponsalías");
        boolean statusBotonLimpiar = CommonMethodsSikuli.theUserClicksOn("btnLimpiarParametros");
        assertTrue(statusBotonLimpiar);
        u.hardWait(3);
        boolean statusTipoServicio = CommonMethodsSikuli.theUserSendValueIn("inputTipoServicioB", tipoServicio, "bitacoraDePagos");
        assertTrue(statusTipoServicio);
        sk.screen.type(Key.TAB);
        u.hardWait(4);
        boolean statusProveedor = CommonMethodsSikuli.theUserSendValueIn("inputGrupoDeEjecucionB", grupoDeEjecucion, "bitacoraDePagos");
        assertTrue(statusProveedor);
        u.hardWait(4);
        boolean statusFechaPagoInicial = CommonMethodsSikuli.theUserSendValueIn("inputFechaPagoInicialB", fechaPagoInicial, "bitacoraDePagos");
        assertTrue(statusFechaPagoInicial);
        u.hardWait(4);
        boolean statusFechaPagoFinal = CommonMethodsSikuli.theUserSendValueIn("inputFechaPagoFinalB", fechaPagoFinal, "bitacoraDePagos");
        assertTrue(statusFechaPagoFinal);
        u.hardWait(4);
        boolean statusTipoBitacora = CommonMethodsSikuli.theUserSendValueIn("inputTipoBitacoraB", tipoBitacora, "bitacoraDePagos");
        assertTrue(statusTipoBitacora);
        u.hardWait(4);
        testCase.addScreenShotCurrentStep("Se ingresaron los parametros y se aceptan para la solicitud");
        boolean statusClickBotonAceptarParametros = CommonMethodsSikuli.theUserClicksOn("btnAceptar");
        assertTrue(statusClickBotonAceptarParametros);
        u.hardWait(6);
        //Paso 8
        addStep("Ejecutar la nueva Solicitud");
        boolean statusEjecutarNuevaSolicitudNoFacturado = CommonMethodsSikuli.theUserClicksOn("botonEjecutar");
        assertTrue(statusEjecutarNuevaSolicitudNoFacturado);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se ejecuta la solicitud y damos click en no para no ejecutar otra");
        boolean statusClickBotonNoPorNuevaSolicitud = CommonMethodsSikuli.theUserClicksOn("optionNo");
        assertTrue(statusClickBotonNoPorNuevaSolicitud);
        u.hardWait(5);
        //Paso 9
        addStep("Encontrar Solicitud");
        testCase.addScreenShotCurrentStep("Selecionar boton encontrar");
        boolean statusBotonEncontrarSolicitud = CommonMethodsSikuli.theUserClicksOn("botonEncontrar");
        assertTrue(statusBotonEncontrarSolicitud);
        u.hardWait(15);
        //Paso 10
        addStep("Esperar a que se ejecute el reporte ");
        testCase.addScreenShotCurrentStep("El nuevo reporte se esta ejecutando");
        CommonMethodsSikuli.theUserChecksIfTheReportIsRunning("faseEjecucionReporte", "botonRefrescarDatos");
        testCase.addScreenShotCurrentStep("Se Finalizo el reporte");
        //Paso 11
        addStep("Visualizar Output del reporte");
        boolean statusVisualizarOutputNoFacturado = CommonMethodsSikuli.theUserClicksOn("botonVisualizarOutput_Solicitudes");
        assertTrue(statusVisualizarOutputNoFacturado);
        u.hardWait(15);
        testCase.addScreenShotCurrentStep("Imagen del reporte descargado");
        //Paso 12
        addStep("Extraer la informacion de la base de datos");
        SQLResult validarQuery = dbAvebqa.executeQuery(queryBitacoraGlobalGrupoDeEjecucion);
        if (validarQuery.isEmpty()) {
            testCase.addTextEvidenceCurrentStep("La consulta que se genero viene vacia "); // Agregamos el texto que la query viene vacia
            testCase.addQueryEvidenceCurrentStep(validarQuery); // Agregamos el resultado de la query
            testCase.addTextEvidenceCurrentStep("Revisar las fechas ingresadas, ya no contienen datos"); // Agregamos el texto que la query viene vacia
        } else {
            totalRows = validarQuery.getRowCount(); //Obtenemos el numero total de rows que tiene la consulta
            testCase.addTextEvidenceCurrentStep("Resultado obtenido de la consulta "); //Agregamos esta leyenda en el template de evidencias
            // Agregamos el resultado de la query en el template
            testCase.addQueryEvidenceCurrentStep(validarQuery);
            System.out.println("Numero de columnas total " + totalRows);
            for (int inicioRecolecion = 0; inicioRecolecion < totalRows; inicioRecolecion++) {
                obtenerValorDeCantidadTotal = validarQuery.getData(inicioRecolecion, 4);
                obtenerValorDeNombreDeServicio = validarQuery.getData(inicioRecolecion, 2);
                obtenerValorDeGrupoEjecucion = validarQuery.getData(inicioRecolecion, 5);
                grupoDeCantidadesObtenidasDelQuery.add(obtenerValorDeCantidadTotal); // Se obtiene los montos totales de los servicios que se muestra en la bitacora
                grupoDeGruposDeEjecucionObtenidasDelQuery.add(obtenerValorDeGrupoEjecucion); // Se obtiene el nombre de los grupos de ejecucion
                grupoDeNombreDeServiciosObtenidasDelQuery.add(obtenerValorDeNombreDeServicio); //Se obtiene el nombre de los servicios.
            }
        }
        System.out.println(grupoDeCantidadesObtenidasDelQuery);
        System.out.println(grupoDeGruposDeEjecucionObtenidasDelQuery);
        System.out.println(grupoDeNombreDeServiciosObtenidasDelQuery);
        //Paso 13
        addStep("Validacion de base de datos VS Bitacora");
        MTNacionales.abrirReportesEnGeneralXXFC();
        u.hardWait(15);
        sk.screen.type(Key.ENTER);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("Reporte abierto");
        u.hardWait(5);
        String texto = CommonMethodsSikuli.getAllTextPage(); //Obtenemos el texto del documento
        u.hardWait(5);
        System.out.println(texto);
        for (String montoTotal : grupoDeCantidadesObtenidasDelQuery) {
            if (montoTotal != null) {
                boolean expected = texto.contains(montoTotal);
                if (expected == true) {
                    testCase.addTextEvidenceCurrentStep("El monto $" + montoTotal + " si esta en la Bitacora");
                } else {
                    testCase.addTextEvidenceCurrentStep("El monto $" + montoTotal + " no se encuentra en la Bitacora verificar la consulta de la base de datos");
                    System.out.println("El monto $" + montoTotal + " no se encuentra en la Bitacora verificar la consulta de la base de datos");
                    System.exit(0);
                }
            } else {
                System.out.println("Se descarta la evaluacion dentro del reporte ya que el valor es nulo: " + montoTotal);
            }
        }
        for (String nombresGrupoEjecucion : grupoDeGruposDeEjecucionObtenidasDelQuery) {
            if (nombresGrupoEjecucion != null) {
                boolean expected = texto.contains(nombresGrupoEjecucion);
                if (expected == true) {
                    testCase.addTextEvidenceCurrentStep("El grupo de ejecucion " + nombresGrupoEjecucion + " si esta en la Bitacora");
                } else {
                    testCase.addTextEvidenceCurrentStep("El grupo de ejecucion " + nombresGrupoEjecucion + " no se encuentra en la Bitacora verificar la consulta de la base de datos");
                    System.out.println("El grupo de ejecucion " + nombresGrupoEjecucion + " no se encuentra en la Bitacora verificar la consulta de la base de datos");
                    System.exit(0);
                }
            } else {
                System.out.println("Se descarta la evaluacion dentro del reporte ya que el valor es nulo: " + nombresGrupoEjecucion);
            }
        }
        for (String nombresDeServicio : grupoDeNombreDeServiciosObtenidasDelQuery) {
            if (nombresDeServicio != null) {
                boolean expected = texto.contains(nombresDeServicio);
                if (expected == true) {
                    testCase.addTextEvidenceCurrentStep("El valor perteneciente al nombre del servicio " + nombresDeServicio + " si esta en la Bitacora");
                } else {
                    testCase.addTextEvidenceCurrentStep("El valor perteneciente al nombre del servicio " + nombresDeServicio + " no se encuentra en la Bitacora verificar la consulta de la base de datos");
                    System.out.println("El valor perteneciente al nombre del servicio " + nombresDeServicio + " no se encuentra en la Bitacora verificar la consulta de la base de datos");
                    System.exit(0);
                }
            } else {
                System.out.println("Se descarta la evaluacion dentro del reporte ya que el valor es nulo: " + nombresDeServicio);
            }
        }
        // Cerrar reporte
        boolean statusCerrarReporte = CommonMethodsSikuli.theUserClicksOn("btnCerrarReporte");
        assertTrue(statusCerrarReporte);
        //Cerrar aplicacion de java
        u.hardWait(3);
        //Paso 14
        addStep("Cerrar Oracle");
        //Cambiarte al aplicativo de Java
        boolean statusRegresarpJava2 = CommonMethodsSikuli.theUserClicksOn("iconoAppJava");
        assertTrue(statusRegresarpJava2);
        testCase.addScreenShotCurrentStep("Regresar a la aplicacion de Java para cerrar el aplicativo");
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
        return "ATC_FT_013_EjecBitacoraXXFCBitacDePagosDeServNacYCorSerNacPorGrpEjecRangoFechYGlobal_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se genere la bitacora XXFC - Bitacora de Pagos de Servicios Nacionales y Corresponsalías de Servicios Nacionales por grupo de ejecucion, rango de fechas y de forma Global.";
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
        return " * 1. Contar con la responsabilidad \"MXOXXO-AP17ADM-USUARIO_OS_ADTIVO_OS\".\n" +
                " * 2. Deben exisistir facturas ya pagadas para Servicios Nacionales.\n" +
                " * 3. Los proveedores deben de estar activos.\n" +
                " * 4. Los servicios deben estar activos en vista XXFC_RETEK_ITEM_SERVICIOS_V" +
                " * 5. Acceso al portal web EBS.";
    }

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub
    }
}
