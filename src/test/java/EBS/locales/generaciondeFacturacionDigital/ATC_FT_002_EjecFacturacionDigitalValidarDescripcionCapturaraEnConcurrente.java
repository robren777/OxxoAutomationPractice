package EBS.locales.generaciondeFacturacionDigital;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.sikuli.api.robot.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.locales.OMLocales.metodosLocales;
import EBS.util.GlobalVariables;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.locales.IO.Excel;
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
 * DESCRIPCION: Clase de utileria para que se muestre en la información que se muestra en la factura PDF-LEYENDA MUESTRA LA DESCRIPCION CAPTURADA EN EL CONCURRENTE
 * <p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_002_Validar los ajustes realizados en la información que se muestra en la factura PDF-LEYENDA MUESTRA LA DESCRIPCION CAPTURADA EN EL CONCURRENTE
 * <p>
 * PREREQUISITOS:
 * - Acceso al portal web EBS
 * - Usar Microsoft Edge en modo Internet Explorer
 * - Tener responsabilidad AR a nivel plaza.
 * - Deben existir transacciones pendientes de facturar de servicios locales en la tabla de pago de servicios.
 * INSUMOS:
 * - Tener datos de Fac. Venta por Servicios Locales en Base de Datos
 *
 * @author David Quiroz Rodriguez
 * @date 04/Febrero/2025
 */

public class ATC_FT_002_EjecFacturacionDigitalValidarDescripcionCapturaraEnConcurrente extends BaseExecution {
    @Test(dataProvider = "data-provider")

    public void ATC_FT_002_EjecFacturacionDigitalValidarDescripcionCapturaraEnConcurrente_test(HashMap<String, String> data) throws Exception {
        /*Variables
         * */
        String ebsUrl = data.get("server");
        String userName = data.get("userName");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String carpetaResponsabilidad = data.get("carpetaResponsabilidad");
        String cambioResponsabilidad = data.get("cambioResponsabilidad");
        String nombreReporte = data.get("nombreReporte");
        String numeroServicio = data.get("numeroServicio");
        String fechaInicial = data.get("fechaInicial");
        String fechaFinal = data.get("fechaFinal");
        String agrupacionFactura = data.get("agrupacionFactura");
        String descripcionParaDigitalizacion = data.get("descripcionParaDigitalizacion");
        String nombreReporteNuevo = data.get("nombreReporteNuevo");
        String estadoFactura = data.get("estadoFactura");
        String fechaAdministrativaInicial = data.get("fechaAdministrativaInicial");
        String fechaAdministrativaFinal = data.get("fechaAdministrativaFinal");
        String inicioExtensionFactura = data.get("inicioExtensionFactura");
        String finFechaArchivo = Excel.getFormattedDate();
        //Nueva solicitud para digitalizar
        String nombreReporteParaDigitalizarFactura = data.get("nombreReporteParaDigitalizarFactura");
        String cambioNuevaResponsabilidad = data.get("cambioNuevaResponsabilidad");
        String tipoDeTransaccion = data.get("tipoDeTransaccion");
        //Se declaran vacias las siguientes 2 variables ya que se les asignara su valor con lo obtenido de la consulta de base de datos
        String numTransaccionInferior;
        String numTransaccionSuperior;
        String enviarFacturaPorCorreo = data.get("enviarFacturaPorCorreo");
        String reimprimirFactura = data.get("reimprimirFactura");
        String descripcionAValidar = descripcionParaDigitalizacion;
        //Fin de solicitud
        //Array para el caso
        ArrayList<String> nuevosValoresDeFacturas = new ArrayList<>();
        ArrayList<String> valoresDeFacturas;
        ArrayList<String> facturasObtenidasDelQuery = new ArrayList<>();
        ArrayList<String> nuevaDescripconAValidar = new ArrayList<>();
        String textoObtenidoDelPDF;
        //Variables de leyenda convertida
        String seperador = ",";
        String nuevacadena = descripcionAValidar.replace(" ", ",");
        System.out.println(nuevacadena);
        String[] arr = nuevacadena.split(java.util.regex.Pattern.quote(seperador));
        //Fin de leyenda convertida
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
        String Valor;
        String QueryCtReference;
        String Query1;
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
        boolean statusBotonLimpiar = CommonMethodsSikuli.theUserClicksOn("btnLimpiarParametros");
        assertTrue(statusBotonLimpiar);
        u.hardWait(3);
        boolean statusIngregarServicio = CommonMethodsSikuli.theUserSendTextIn("txtServicioItem", numeroServicio);
        assertTrue(statusIngregarServicio);
        u.hardWait(3);
        boolean statusFechaInicial = CommonMethodsSikuli.theUserSendTextIn("txtFechaInicial", fechaInicial);
        assertTrue(statusFechaInicial);
        u.hardWait(3);
        sk.screen.type(Key.BACKSPACE);
        boolean statusFechaFinal = CommonMethodsSikuli.theUserSendTextIn("txtFechaFinal", fechaFinal);
        assertTrue(statusFechaFinal);
        u.hardWait(3);
        boolean statusAgrupacionFactura = CommonMethodsSikuli.theUserSendTextIn("txtAgrupacionFactura", agrupacionFactura);
        assertTrue(statusAgrupacionFactura);
        u.hardWait(3);
        boolean statusDescripcionFacturaLinea = CommonMethodsSikuli.theUserSendTextIn("inputDescripcionFacturaLinea", descripcionParaDigitalizacion);
        assertTrue(statusDescripcionFacturaLinea);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se ingresaron los parametros y se aceptan para la solicitud");
        boolean statusClickBotonAceptarParametros = CommonMethodsSikuli.theUserClicksOn("btnAceptar");
        assertTrue(statusClickBotonAceptarParametros);
        u.hardWait(6);
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
        u.hardWait(30);
        //Paso 10
        addStep("Esperar a que se ejecute reporte");
        testCase.addScreenShotCurrentStep("El Reporte se esta ejecutando");
        CommonMethodsSikuli.theUserChecksIfTheReportIsRunning("faseEjecucionReporte", "botonRefrescarDatos");
        testCase.addScreenShotCurrentStep("Se Finalizo el reporte");
        u.hardWait(12);
        //Paso 11
        addStep("Visualizar la informacion del registro");
        boolean statusVisualizarRegistro = CommonMethodsSikuli.theUserClicksOn("btnVisualizarRegistro");
        assertTrue(statusVisualizarRegistro);
        u.hardWait(15);
        //Cerrar ventana adicional de autentificacion
        sk.screen.click();
        CommonMethodsSikuli.closeTabCiclico("lblMensajeAutentificacionFailed");
        System.out.println("Se genero  un ctrl w para cerrar la pestaña extra");
        //obtener la factura linea
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("Se revisa el contenido del Log para procesar la informacion y obtener el numero de factura");
        valoresDeFacturas = p.getTextContains(inicioExtensionFactura); //Obtenemos todos los valores despues de visualizar los registros "LOG"
        System.out.println("Valores obtenidos del LOG" + valoresDeFacturas);
        //Remover los datos string duplicados
        valoresDeFacturas = (ArrayList<String>) valoresDeFacturas.stream().distinct().collect(Collectors.toList()); //Se genera un arreglo donde limpia todos los valores que sean repetidos.
        System.out.println("Nuevos valores limpiando repetidos" + valoresDeFacturas);
        valoresDeFacturas.remove(" ");
        System.out.println("Nuevos valores removiendo los espacios en blanco" + valoresDeFacturas);
        valoresDeFacturas.remove(",");
        System.out.println("Nuevos valores removiendo las comas adicionales" + valoresDeFacturas);
        for (String referencias : valoresDeFacturas) { // generamos el ciclo para obtener todos los valores del arreglo
            int numTotalCaracteresPorFactura=referencias.length()+3;
            if (referencias.length() < numTotalCaracteresPorFactura) {
                System.out.println(referencias); //Imprimimos las referencias
                nuevosValoresDeFacturas.add(referencias.replaceAll("[^0-9]", ""));
            } else {
                System.out.println("El numero de factura o referencia es mayor por lo cual no se agregara al nuevo arreglo");
            }
        }
        nuevosValoresDeFacturas = (ArrayList<String>) nuevosValoresDeFacturas.stream().distinct().collect(Collectors.toList()); //Se genera un arreglo para limpiar valores repetidos
        System.out.println("Nuevo valores obtenidos " + nuevosValoresDeFacturas); //Imprimimos el arreglo;
        //Paso 12
        addStep("Obtencion de los numeros de factura en la base de datos y los porcentajes de comision de oxxo");
        System.out.println(GlobalVariables.DB_HOST_AVEBQA); //Imprimimos la base de datos que deseamos
        //Paso ejecucion sql y obtencion de facturas
        for (int inicio = 0; inicio < nuevosValoresDeFacturas.size(); inicio++) { //Ciclo para generar la Extraccion de datos y generacion de facturas
            Valor = nuevosValoresDeFacturas.get(inicio); //Declaramos que valor es igual al primer parametro de nuevos valores
            QueryCtReference = "select creation_date, ct_reference, TRX_NUMBER from apps.RA_CUSTOMER_TRX_ALL WHERE TRX_NUMBER='" + Valor + "'";
            System.out.println(QueryCtReference);
            SQLResult validarQueryCtReference = dbAvebqa.executeQuery(QueryCtReference);
            u.hardWait(5);
            testCase.addQueryEvidenceCurrentStep(validarQueryCtReference);
            String obtenerValorQueryCtReference = validarQueryCtReference.getData(0, 1); //Obtenemos el valor de la referencia
            System.out.println(obtenerValorQueryCtReference);
            if (validarQueryCtReference.isEmpty()) {
                System.out.println("Consulta vacia no aplica referencia");
            } else {
                Query1 = "select ct_reference, TRX_NUMBER from apps.RA_CUSTOMER_TRX_ALL where TRX_NUMBER = '" + Valor + "' " + " and ct_reference='" + obtenerValorQueryCtReference + "'"; //Se genera el query con el valor de la factura y referencia  para que se genere una evidencia mas limpia
                System.out.println(Query1); //Imprimimos el query
                SQLResult ValidQuery1 = dbAvebqa.executeQuery(Query1); //Generamos la consulta en la base de datos
                String obtenerValorConsulta = ""; // Se declara para obtener datos de la consulta que si contengan informacion
                if (ValidQuery1.isEmpty()) { //Si la consulta viene vacia generamos lo siguiente
                    testCase.addTextEvidenceCurrentStep("La consulta que se genero viene vacia "); // Agregamos el texto que la query viene vacia
                    testCase.addQueryEvidenceCurrentStep(ValidQuery1); // Agregamos el resultado de la query
                    testCase.addTextEvidenceCurrentStep("Revisar si el servico o proveedor cuenta con las caracteristicas necesarias para generar facturas "); // Agregamos el texto que la query viene vacia
                } else { //En caso de que si tenga resultados entonces
                    obtenerValorConsulta = ValidQuery1.getData(0, 1); //Obtenemos el valor de la factura
                    facturasObtenidasDelQuery.add(obtenerValorConsulta); //Agregamos al arreglo
                    testCase.addTextEvidenceCurrentStep("El valor obtenido de la columna es " + obtenerValorConsulta); //Agregamos esta leyenda en el template de evidencias
                    testCase.addQueryEvidenceCurrentStep(ValidQuery1);  // Agregamos el resultado de la query en el template
                }
            }
        }
        System.out.println("Los valores del arreglo de facturas son " + facturasObtenidasDelQuery);
        //Paso 13
        addStep("Ejecutar el reporte de XXFC - Reporte de Fac. Venta por Servicios Locales");
        boolean statusClickAppJava = CommonMethodsSikuli.theUserClicksOn("iconoAppJava");
        assertTrue(statusClickAppJava);
        u.hardWait(8);
        boolean statusClickEjecutarNuevaSolicitud = CommonMethodsSikuli.theUserClicksOn("EjecutarNuevaSolicitud");
        assertTrue(statusClickEjecutarNuevaSolicitud);
        u.hardWait(4);
        boolean statusIngresarNombreReporteNuevo = CommonMethodsSikuli.theUserSendTextIn("Nombre_EjecutarEstaSolicitud", nombreReporteNuevo);
        assertTrue(statusIngresarNombreReporteNuevo);
        testCase.addScreenShotCurrentStep("Se ingresa nombre de reporte XXFC - Reporte de Fac. Venta por Servicios Locales");
        u.hardWait(2);
        sk.screen.type(Key.TAB);
        u.hardWait(3);
        //Paso 14
        addStep("Ingresar parametros para nueva solicitud");
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
        //Paso 15
        addStep("Ejecutar la nueva Solicitud");
        boolean statusEjecutarNuevaSolicitud = CommonMethodsSikuli.theUserClicksOn("botonEjecutar");
        assertTrue(statusEjecutarNuevaSolicitud);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se ejecuta la solicitud y damos click en no para no ejecutar otra");
        boolean statusClickBotonNoPorNuevaSolicitud = CommonMethodsSikuli.theUserClicksOn("optionNo");
        assertTrue(statusClickBotonNoPorNuevaSolicitud);
        u.hardWait(5);
        //Paso 16
        addStep("Esperar a que se ejecute el nuevo reporte");
        testCase.addScreenShotCurrentStep("El nuevo reporte se esta ejecutando");
        CommonMethodsSikuli.theUserChecksIfTheReportIsRunning("faseEjecucionReporte", "botonRefrescarDatos");
        testCase.addScreenShotCurrentStep("Se Finalizo el reporte");
        //Paso 17
        addStep("Visualizar Output del nuevo reporte");
        boolean statusVisualizarOutput = CommonMethodsSikuli.theUserClicksOn("botonVisualizarOutput_Solicitudes");
        assertTrue(statusVisualizarOutput);
        u.hardWait(30);
        testCase.addScreenShotCurrentStep("Imagen del reporte descargado");
        //Abrir reporte
        boolean statusAbrirReporte = CommonMethodsSikuli.theUserClicksOn("btnLinkAbrirReporte");
        assertTrue(statusAbrirReporte);
        u.hardWait(15);
        sk.screen.type(Key.ENTER);
        u.hardWait(10);
        testCase.addScreenShotCurrentStep("Reporte abierto");
        String texto = CommonMethodsSikuli.getAllTextPage(); //Obtenemos el texto del documento
        System.out.println(texto);
        //Generar validacion del reporte
        for (String datosArreglo : facturasObtenidasDelQuery) {
            boolean expeted = texto.contains(datosArreglo);
            if (expeted == true) {
                testCase.addTextEvidenceCurrentStep("El valor " + datosArreglo + " se encuentra en el archivo descargado");
            } else {
                testCase.addTextEvidenceCurrentStep("El valor " + datosArreglo + " no se encuentra en el archivo descargado");
                System.out.println("Se para ejecucion ya que no se encuentra el valor en el archivo desvargado");
                System.exit(0);
            }
        }
        boolean statusCerrarReporte = CommonMethodsSikuli.theUserClicksOn("btnCerrarReporte");
        assertTrue(statusCerrarReporte);
        // Cerrar reporte
        //Inicio de proceso de facturacion
        //Cambiarte al aplicativo de Java
        boolean statusRegresarpJava = CommonMethodsSikuli.theUserClicksOn("iconoAppJava");
        assertTrue(statusRegresarpJava);
        testCase.addScreenShotCurrentStep("Regresar a la aplicacion de Java");
        System.out.println("Se Cambia al serverlet de java");
        u.hardWait(5);
        //Paso 18
        addStep("Cerrar apartado de solicitudes y cambiar a una nueva responsabilidad");
        boolean statusCerrarApartadoSolicitudes = CommonMethodsSikuli.theUserClicksOn("btnCerrarApartadoSolicitudes");
        assertTrue(statusCerrarApartadoSolicitudes);
        testCase.addScreenShotCurrentStep("Cerrado");
        u.hardWait(12);
        testCase.addScreenShotCurrentStep("Seleccionar el boton de cambio de responsabilidad ");
        boolean statusCambiarNuevaResponsabilidad = CommonMethodsSikuli.theUserClicksOn("cambiarResponsabilidad");
        assertTrue(statusCambiarNuevaResponsabilidad);
        testCase.addScreenShotCurrentStep("Seleccionar Nueva Responsabilidad");
        boolean statusSeleccionarNuevaResponsabilidad = CommonMethodsSikuli.theUserSendTextIn("txtIngresarResponsabilidad", cambioNuevaResponsabilidad);
        assertTrue(statusSeleccionarNuevaResponsabilidad);
        u.hardWait(5);
        testCase.addScreenShotCurrentStep("Ingresar Nueva Responsabilidad");
        u.hardWait(3);
        sk.screen.type(Key.ENTER);
        u.hardWait(3);
        //En caso de ser varias facturas se tendra que generar el mismo proceso para ver las facturas de una por 1 para que es sistema vaya en orden revisandolas
        for (int i = 0; i < facturasObtenidasDelQuery.size(); i++) {  //Declaramos i para generar la iteracion de del array y obtener los valores inferior y superior
            //Declaramos el valor de las varibales para la facturacion
            numTransaccionInferior = facturasObtenidasDelQuery.get(i);
            numTransaccionSuperior = facturasObtenidasDelQuery.get(i);
        //Paso 19 en caso de ser mas factruras cambia el numero por el ciclo repetitivo
        addStep("Ir a solicitudes");
        testCase.addScreenShotCurrentStep("Ir al menu Ver");
        boolean statusMenuVer2 = CommonMethodsSikuli.theUserClicksOn("optionMenuVer");
        assertTrue(statusMenuVer2);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Seleccionar solicitudes");
        boolean statusSeleccionarSolicitudes2 = CommonMethodsSikuli.theUserClicksOn("optionSolicitudes_Ver");
        assertTrue(statusSeleccionarSolicitudes2);
        u.hardWait(7);
        //Paso 20 en caso de ser mas factruras cambia el numero por el ciclo repetitivo
        addStep("Generar una solicitud para digitalizar");
        testCase.addScreenShotCurrentStep("Seleccionar boton Ejecutar Nueva Solicitud");
        boolean statusGenerarSolicitudParaDigitalizar = CommonMethodsSikuli.theUserClicksOn("EjecutarNuevaSolicitud");
        assertTrue(statusGenerarSolicitudParaDigitalizar);
        u.hardWait(4);
        boolean statusIngresarNombreReporteParaDigitalizar = CommonMethodsSikuli.theUserSendTextIn("Nombre_EjecutarEstaSolicitud", nombreReporteParaDigitalizarFactura);
        assertTrue(statusIngresarNombreReporteParaDigitalizar);
        testCase.addScreenShotCurrentStep("Se ingresa nombre de reporte para digitalizar");
        u.hardWait(2);
        sk.screen.type(Key.TAB);
        u.hardWait(3);
        //Paso 21 en caso de ser mas factruras cambia el numero por el ciclo repetitivo
        addStep("Ingresar parametros para solicitud de digitalizacion de factura");
        boolean statusBotonLimpiarParaSolicitudDigitalizar = CommonMethodsSikuli.theUserClicksOn("btnLimpiarParametros");
        assertTrue(statusBotonLimpiarParaSolicitudDigitalizar);
        u.hardWait(2);
        boolean statusTipoDeTransaccion = CommonMethodsSikuli.theUserSendTextIn("tipoDeTransaccion", tipoDeTransaccion);
        assertTrue(statusTipoDeTransaccion);
        sk.screen.type(Key.TAB);
        u.hardWait(2);
        boolean statusNumTransaccionInferior = CommonMethodsSikuli.theUserSendTextIn("inputNumTransaccionInferior", numTransaccionInferior);
        assertTrue(statusNumTransaccionInferior);
        u.hardWait(2);
        boolean statusNumTransaccionSuperior = CommonMethodsSikuli.theUserSendTextIn("inputNumTransaccionSuperior", numTransaccionSuperior);
        assertTrue(statusNumTransaccionSuperior);
        u.hardWait(3);
        boolean statusEnviarPorCorreo = CommonMethodsSikuli.theUserSendTextIn("inputEnviarPorCorreo", enviarFacturaPorCorreo);
        assertTrue(statusEnviarPorCorreo);
        u.hardWait(2);
        boolean statusReimprimirFactura = CommonMethodsSikuli.theUserSendTextIn("inputReimprimirFactura", reimprimirFactura);
        assertTrue(statusReimprimirFactura);
        u.hardWait(7);
        testCase.addScreenShotCurrentStep("Se ingresaron los parametros y se aceptan para la solicitud");
        boolean statusClickBotonAceptarParametrosParaDigitalizar = CommonMethodsSikuli.theUserClicksOn("btnAceptar");
        assertTrue(statusClickBotonAceptarParametrosParaDigitalizar);
        u.hardWait(6);
        //Paso 22 en caso de ser mas factruras cambia el numero por el ciclo repetitivo
        addStep("Ejecutar Solicitud");
        boolean statusEjecutarSolicitudParaDigitalizar = CommonMethodsSikuli.theUserClicksOn("botonEjecutar");
        assertTrue(statusEjecutarSolicitudParaDigitalizar);
        u.hardWait(3);
        testCase.addScreenShotCurrentStep("Se ejecuta la solicitud y damos click en no para no ejecutar otra");
        boolean statusClickBotonNo2 = CommonMethodsSikuli.theUserClicksOn("optionNo");
        assertTrue(statusClickBotonNo2);
        u.hardWait(5);
        //Paso 23 en caso de ser mas factruras cambia el numero por el ciclo repetitivo
        addStep("Encontrar Solicitud");
        testCase.addScreenShotCurrentStep("Selecionar boton encontrar");
        boolean statusBotonEncontrarSolicitud2 = CommonMethodsSikuli.theUserClicksOn("botonEncontrar");
        assertTrue(statusBotonEncontrarSolicitud2);
        u.hardWait(15);
        //Paso 24 en caso de ser mas factruras cambia el numero por el ciclo repetitivo
        addStep("Esperar a que se ejecute reporte");
        testCase.addScreenShotCurrentStep("El Reporte se esta ejecutando");
        CommonMethodsSikuli.theUserChecksIfTheReportIsRunning("faseEjecucionReporte", "botonRefrescarDatos");
        testCase.addScreenShotCurrentStep("Se Finalizo el reporte");
        //Paso 25 en caso de ser mas factruras cambia el numero por el ciclo repetitivo
        addStep("Visualizar Output");
        boolean statusVisualizarOutput2 = CommonMethodsSikuli.theUserClicksOn("botonVisualizarOutput_Solicitudes");
        assertTrue(statusVisualizarOutput2);
        u.hardWait(15);
        //Cerrar ventana adicional de autentificacion
        sk.screen.click();
        CommonMethodsSikuli.closeTabCiclico("lblMensajeAutentificacionFailed");
        u.hardWait(10);
        System.out.println("Se genero  un ctrl w para cerrar la pestaña extra");
        testCase.addScreenShotCurrentStep("Validar el PDF generado");
        u.hardWait(10);
        for (String ele : arr) {
            nuevaDescripconAValidar.add(ele);
            System.out.println(ele);
        }
        System.out.println(nuevaDescripconAValidar);
        //obtener el texto completo del pdf
        textoObtenidoDelPDF = CommonMethodsSikuli.getAllTextPage(); //Obtenemos el texto del pdf
        for (String valorLeyendaDividida : nuevaDescripconAValidar) { //Sacamos los valores de la nueva leyenda a validar
            boolean resultadoDeBusqueda = textoObtenidoDelPDF.contains(valorLeyendaDividida);
            if (resultadoDeBusqueda) { //Validamos que si el valor del arreglo existe imprimimos la leyenda
                System.out.println("El valor " + valorLeyendaDividida + " Si se encuentra en el PDF descargado"); //Imprimimos las referencias
                testCase.addTextEvidenceCurrentStep("El valor " + valorLeyendaDividida + " Si se encuentra en el PDF descargado");
            } else { //En caso de que no terminamos la validacion asi como el programa
                System.out.println("El valor " + valorLeyendaDividida + "No se encuentra en el PDF descargado"); //Imprimimos las referencias
                testCase.addTextEvidenceCurrentStep("El valor " + valorLeyendaDividida + " No se encuentra en el PDF descargado");
                System.exit(0);
            }
        }
            //Fin del  primer proceso de facturacion Digital en caso de ser mas se generara de nuevo la facturacion digital
            u.hardWait(3);
            //Cambiar al aplicativo de java, en caso de haber mas facturas se generara de nuevo el proceso en caso de que no se procede al paso 26
            boolean statusRegresarpJava2 = CommonMethodsSikuli.theUserClicksOn("iconoAppJava");
            assertTrue(statusRegresarpJava2);
            testCase.addScreenShotCurrentStep("Regresar a la aplicacion de Java para cerrar el aplicativo");
            System.out.println("Se Cambia al serverlet de java");
            u.hardWait(5);
        }
        //Paso final
        addStep("Cerrar Oracle");
        boolean statusRegresarAppJavaPasoFinal = CommonMethodsSikuli.theUserClicksOn("iconoAppJava");
        assertTrue(statusRegresarAppJavaPasoFinal);
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
        return "ATC_FT_002_EjecFacturacionDigitalValidarDescripcionCapturaraEnConcurrente_test";
    }

    @Override
    public String setTestDescription() {
        return "Validar que se visualize en la información que se muestra en la factura PDF-LEYENDA MUESTRA LA DESCRIPCION CAPTURADA EN EL CONCURRENTE";
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
        return " * \t- Acceso al portal web EBS\n" +
                " * \t- Usar Microsoft Edge en modo Internet Explorer\n" +
                " *  - Tener responsabilidad AR a nivel plaza.\n" +
                " *  - Deben existir transacciones pendientes de facturar de servicios locales en la tabla de pago de servicios.";
    }

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub
    }
}
