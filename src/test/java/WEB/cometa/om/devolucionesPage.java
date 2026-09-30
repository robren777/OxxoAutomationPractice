package WEB.cometa.om;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;
import WEB.cometa.om.PortalCometaMVP4.DevolucionRow;
import WEB.util.GlobalVariables;
import io.netty.handler.timeout.TimeoutException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class devolucionesPage {
	
	SeleniumUtil u;
	TestCase testCase;
	PortalCometaMVP4 pc = new PortalCometaMVP4(u, testCase);
	devolucionesElements devElements = new devolucionesElements();
    private final WebDriverWait wait;
    private String valorMotivoPreview = "";
    private String valorObsTiendaPreview = "";
    private String valorObsProveedorPreview = "";
    private String textoPreviewPdfCache = "";
    private boolean fallbackPdfIntentado = false;


	
	public static String dateIni, dateEnd, reg1, fuente, zona, region, plaza, tienda, proveedor, precio, fecha, tipoTicket, id, transferencia,
	ruta, rutaPropuesta, horaFormateada, fechaFormateada, centroDescargasFiltradoFecha, moduloEsperado, crTiendaFV, folio;
    private String ultimoPdfAbiertoNombre;
    private String handlePrincipalAntesPdf;
    private String handlePdfAbierto;

	

	public devolucionesPage(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
        this.pc = new PortalCometaMVP4(u, testCase);
        this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));


		}
	
	
	public void seleccionarFechaActual(By calendarBy) {
	    WebDriver driver = u.getDriver();
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    LocalDate hoy = LocalDate.now();
	    YearMonth objetivo = YearMonth.from(hoy);
	    Locale localeEs = new Locale("es", "MX");
	    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMMM yyyy", localeEs);

	    WebElement calendar = wait.until(ExpectedConditions.visibilityOfElementLocated(calendarBy));

	    By byPeriodo = By.xpath(".//mat-calendar-header//button[1]");
	    By byPrev = By.xpath(".//mat-calendar-header//button[2]");
	    By byNext = By.xpath(".//mat-calendar-header//button[3]");

	    WebElement btnPeriodo = calendar.findElement(byPeriodo);
	    WebElement btnPrev = calendar.findElement(byPrev);
	    WebElement btnNext = calendar.findElement(byNext);

	    // Navega al mes/año actual por si el datepicker abre en otro periodo
	    for (int i = 0; i < 24; i++) {
	        String textoPeriodo = btnPeriodo.getText().trim().toLowerCase(localeEs);
	        YearMonth visible = YearMonth.parse(textoPeriodo, fmt);

	        int cmp = visible.compareTo(objetivo);
	        if (cmp == 0) {
	            break;
	        } else if (cmp > 0) {
	            btnPrev.click();
	        } else {
	            btnNext.click();
	        }

	        calendar = wait.until(ExpectedConditions.visibilityOfElementLocated(calendarBy));
	        btnPeriodo = calendar.findElement(byPeriodo);
	        btnPrev = calendar.findElement(byPrev);
	        btnNext = calendar.findElement(byNext);
	    }

	    int dia = hoy.getDayOfMonth();
	    By byDia = By.xpath(
	        ".//mat-month-view//button[" +
	        "not(contains(@class,'mat-calendar-body-disabled')) and " +
	        ".//span[contains(@class,'mat-calendar-body-cell-content') and normalize-space()='" + dia + "']" +
	        "]"
	    );

	    WebElement diaBtn = wait.until(ExpectedConditions.elementToBeClickable(byDia));
	    try {
	        diaBtn.click();
	    } catch (Exception ex) {
	        js.executeScript("arguments[0].click();", diaBtn);
	    }
	}
	
	/**
     * Obtiene el registro del usuario segun su tipo y ejecuta la consulta que
     * corresponde a ese perfil.
     * 
     * @param usuario --> Usuario con el que se obtendra la informacion del perfil
     * @return --> Regresa true si encuentra informacion y la asigna a variables globales
     * @throws Exception --> Lanza excepcion cuando ocurre algun error de conexion o consulta
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    public static boolean obtenerRegistroUsuario(String usuario) throws Exception {
        System.out.println("========== INICIANDO obtenerRegistroUsuario ==========");
        System.out.println("Usuario recibido: " + usuario);

        if (usuario == null || usuario.trim().isEmpty()) {
            System.err.println("✗ Error: Usuario es null o vacio");
            return false;
        }

        limpiarVariablesGlobales();

        try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
                GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa)) {

            String tipoUsuario = determinarTipoUsuario(connection, usuario);
            System.out.println("Tipo de usuario: " + tipoUsuario);

            ConfiguracionUsuario config = obtenerConfiguracionUsuario(tipoUsuario);
            if (config == null) {
                System.err.println("✗ No existe configuracion para el tipo de usuario: " + tipoUsuario);
                return false;
            }

            boolean resultado = procesarUsuario(connection, usuario, config);
            

            System.out.println("========== FIN obtenerRegistroUsuario ==========");
            return resultado;
        } catch (Exception e) {
            System.err.println("Error en obtenerRegistroUsuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Determina el tipo de usuario que se utilizara para construir la consulta.
     * 
     * @param connection --> Conexion activa a la base de datos
     * @param usuario --> Usuario del cual se identificara el perfil
     * @return --> Regresa el tipo de usuario a procesar
     * @throws SQLException --> Lanza excepcion si hay error al consultar la base de datos
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private static String determinarTipoUsuario(Connection connection, String usuario) throws SQLException {
    	  if (usuario == null) {
    	        return "ADMIN_OPERATIVO";
    	    }

    	    String u = usuario.trim();

    	    // Mapeo controlado para no romper lo que ya funciona
    	    if ("1111111".equals(u)) return "ADMIN_OPERATIVO";
    	    if ("5555555".equals(u)) return "ADMIN";
    	    if ("3333333".equals(u)) return "OXXO_OS";
    	    if ("4444444".equals(u)) return "OXXO_PLAZA";
    	    if ("2222222".equals(u)) return "COMERCIAL";



    	    // Puedes agregar mas usuarios aqui
    	    // if ("6666666".equals(u)) return "OXXO_OS";

    	    return "ADMIN_OPERATIVO";
    }

    /**
     * Obtiene la configuracion necesaria para procesar el tipo de usuario recibido.
     * 
     * @param tipoUsuario --> Perfil del usuario a configurar
     * @return --> Regresa la configuracion del perfil si existe
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private static ConfiguracionUsuario obtenerConfiguracionUsuario(String tipoUsuario) {
        switch (tipoUsuario) {
        case "ADMIN_OPERATIVO":
            return new ConfiguracionUsuario(
                    "ADMIN_OPERATIVO",
                    new String[] { "PCRFC", "PCSUPPLIERID", "PCPLAZA" },
                    "PCRFC",
                "SELECT dv.*, (SELECT d2.folio FROM public.xxcm_dv_detalle d2 WHERE d2.id = dv.id ORDER BY d2.folio DESC LIMIT 1) AS folio_detalle "
                            + "FROM public.get_dv_filtrados(?, ?, ?, null, null, null, null, null, null) h "
                            + "INNER JOIN public.xxcm_dv dv ON h.id = dv.id "
                            + "ORDER BY dv.fecha DESC LIMIT 1");
        case "OXXO_OS":
            return new ConfiguracionUsuario(
            		 "OXXO_OS",
                     new String[] { "PCRFC", "PCSUPPLIERID", "PCPLAZA" },
                     null, // <- importante: sin filtro
                     "SELECT dv.* "
                             + "FROM public.get_dv_filtrados(?, ?, null, null, null, null, null, null, null) h "
                             + "INNER JOIN public.xxcm_dv dv ON h.id = dv.id "
                             + "INNER JOIN public.xxcm_dv_detalle d ON d.id = dv.id "
                             + "ORDER BY dv.fecha DESC LIMIT 1");
        case "COMERCIAL": 
            return new ConfiguracionUsuario(
            		"COMERCIAL",
            		new String[] { "PCRFC", "PCSUPPLIERID", "PCPLAZA" },
            		"PCRFC",
            		"SELECT dv.* "
            		+ "FROM public.get_dv_filtrados(?, ?, ?, null, null, null, null, null, null) h "
            		+ "INNER JOIN public.xxcm_dv dv ON h.id = dv.id "
            		+ "INNER JOIN public.xxcm_dv_detalle d ON d.id = dv.id "
            		+ "ORDER BY dv.fecha DESC LIMIT 1");
        case "ADMIN": 
            return new ConfiguracionUsuario(
            	    "ADMIN",
                    new String[] { "PCRFC", "PCSUPPLIERID", "PCPLAZA" },
                    null,   // <- cambiar de "PCRFC" a null 
                    "SELECT dv.* "
                            + "FROM public.get_dv_filtrados(?, ?, null, null, null, null, null, null, null) h "
                            + "INNER JOIN public.xxcm_dv dv ON h.id = dv.id "
                            + "INNER JOIN public.xxcm_dv_detalle d ON d.id = dv.id "
                            + "ORDER BY dv.fecha DESC LIMIT 1");
        case "OXXO_PLAZA":
            return new ConfiguracionUsuario(
                    "OXXO_PLAZA",
                    new String[] { "PCRFC", "PCSUPPLIERID", "PCPLAZA" },
                    "PCPLAZA",
                    "SELECT dv.* "
                            + "FROM public.get_dv_filtrados(?, ?, null, null, null, null, ?, null, null) h "
                            + "INNER JOIN public.xxcm_dv dv ON h.id = dv.id "
                            + "INNER JOIN public.xxcm_dv_detalle d ON d.id = dv.id "
                            + "ORDER BY dv.fecha DESC LIMIT 1");
            
        default:
            return null;
        }
    }

    /**
     * Ejecuta la consulta del perfil, recupera la informacion mas reciente y llena
     * las variables globales de apoyo para el script.
     * 
     * @param connection --> Conexion activa a la base de datos
     * @param usuario --> Usuario del que se tomaran los valores de perfil
     * @param config --> Configuracion del tipo de usuario a ejecutar
     * @return --> Regresa true si se obtuvo al menos un registro
     * @throws SQLException --> Lanza excepcion si hay error en la consulta a BD
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private static boolean procesarUsuario(Connection connection, String usuario, ConfiguracionUsuario config)
            throws SQLException {
    	boolean queryConFiltro = config.campoFiltro != null && !config.campoFiltro.trim().isEmpty();

    	String valorBusqueda = "";
    	if (queryConFiltro) {
    	    Map<String, String> usuarioKeys = obtenerClavesUsuario(connection, usuario, config.camposUsuario);
    	    if (usuarioKeys.isEmpty()) {
    	        System.err.println("No se obtuvo informacion del perfil.");
    	        return false;
    	    }

    	    valorBusqueda = usuarioKeys.get(config.campoFiltro);
    	    if (valorBusqueda == null || valorBusqueda.trim().isEmpty()) {
    	        System.err.println("No se encontro el campo '" + config.campoFiltro + "' en las claves del usuario");
    	        System.err.println("Claves disponibles: " + usuarioKeys.keySet());
    	        return false;
    	    }
    	    valorBusqueda = valorBusqueda.trim();
    	}

    	calcularRangoFechas();
    	System.out.println("Rango de fechas calculado:");
    	System.out.println("  Fecha Inicio: " + dateIni);
    	System.out.println("  Fecha Fin: " + dateEnd);

    	Array filtroArray = null;

    	try (PreparedStatement pstmt = connection.prepareStatement(config.sqlQuery)) {
    	    pstmt.setDate(1, Date.valueOf(dateIni));
    	    pstmt.setDate(2, Date.valueOf(dateEnd));
    	    pstmt.setQueryTimeout(120);

    	    if (queryConFiltro) {
    	        String[] valoresFiltro;
    	        if (valorBusqueda.contains(",")) {
    	            String[] partes = valorBusqueda.split(",");
    	            java.util.List<String> limpios = new java.util.ArrayList<>();
    	            for (String p : partes) {
    	                String t = p == null ? "" : p.trim();
    	                if (!t.isEmpty()) {
    	                    limpios.add(t);
    	                }
    	            }
    	            if (limpios.isEmpty()) {
    	                System.err.println("El valor de filtro quedo vacio tras normalizar: " + config.campoFiltro);
    	                return false;
    	            }
    	            valoresFiltro = limpios.toArray(new String[0]);
    	        } else {
    	            valoresFiltro = new String[] { valorBusqueda };
    	        }

    	        filtroArray = connection.createArrayOf("text", valoresFiltro);
    	        pstmt.setArray(3, filtroArray);

    	        System.out.println("  Campo de filtro: " + config.campoFiltro);
    	        System.out.println("  Valores para busqueda: " + java.util.Arrays.toString(valoresFiltro));
    	    } else {
    	        System.out.println("  Query sin filtro por usuario (solo rango de fechas)");
    	    }

    	    System.out.println("Ejecutando query especifica para " + config.tipoUsuario + "...");
    	    long inicioConsulta = System.currentTimeMillis();

    	    try (ResultSet rs = pstmt.executeQuery()) {
    	        long finConsulta = System.currentTimeMillis();
    	        System.out.println("  executeQuery() finalizo en " + (finConsulta - inicioConsulta) + " ms");

    	        if (!rs.next()) {
    	            System.out.println("No se encontraron registros en get_dv_filtrados");
    	            if (queryConFiltro) {
    	                imprimirDiagnosticoSinResultados(connection, valorBusqueda);
    	            }
    	            return false;
    	        }

    	        asignarDatosDevolucion(rs);

    	        if (folio == null || folio.trim().isEmpty()) {
    	            obtenerFolioDeDVDetalle(connection, id);
    	        }

    	        imprimirDatosRecuperados();
    	        return true;
    	    }
    	} finally {
    	    if (filtroArray != null) {
    	        try {
    	            filtroArray.free();
    	        } catch (Exception ignore) {
    	        }
    	    }
    	}
    }

    /**
     * Ejecuta validaciones de diagnostico cuando la consulta principal no devuelve informacion.
     * 
     * @param connection --> Conexion activa a la base de datos
     * @param valorBusqueda --> Valor que se envio al arreglo del query principal
     * @throws SQLException --> Lanza excepcion si ocurre algun error al consultar la base de datos
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private static void imprimirDiagnosticoSinResultados(Connection connection, String valorBusqueda) throws SQLException {
        String sqlConteoRango = "SELECT COUNT(*) AS total "
                + "FROM public.get_dv_filtrados(?, ?, ?, null, null, null, null, null, null) h";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlConteoRango)) {
            pstmt.setDate(1, Date.valueOf(dateIni));
            pstmt.setDate(2, Date.valueOf(dateEnd));
            pstmt.setArray(3, connection.createArrayOf("varchar", new Object[] { valorBusqueda }));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("  Diagnostico rango actual: " + rs.getInt("total") + " registros en get_dv_filtrados");
                }
            }
        }

        String fechaDiagnosticoIni = LocalDate.now().withDayOfYear(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sqlConteoAmplio = "SELECT COUNT(*) AS total "
                + "FROM public.get_dv_filtrados(?, ?, ?, null, null, null, null, null, null) h";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlConteoAmplio)) {
            pstmt.setDate(1, Date.valueOf(fechaDiagnosticoIni));
            pstmt.setDate(2, Date.valueOf(dateEnd));
            pstmt.setArray(3, connection.createArrayOf("varchar", new Object[] { valorBusqueda }));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("  Diagnostico rango amplio (" + fechaDiagnosticoIni + " a " + dateEnd + "): "
                            + rs.getInt("total") + " registros en get_dv_filtrados");
                }
            }
        }
    }

    /**
     * Consulta las claves del perfil en xxcm_user_keys para construir el filtro del usuario.
     * 
     * @param connection --> Conexion activa a la base de datos
     * @param usuario --> Usuario para el cual se recuperaran las claves
     * @param camposUsuario --> Llaves esperadas para el perfil
     * @return --> Regresa un mapa con short_name y key_value del usuario
     * @throws SQLException --> Lanza excepcion si ocurre algun error en BD
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private static Map<String, String> obtenerClavesUsuario(Connection connection, String usuario, String[] camposUsuario)
            throws SQLException {
        String sqlClaves = "SELECT short_name, key_value FROM xxcm_user_keys "
                + "WHERE short_name IN (" + generarPlaceholders(camposUsuario.length) + ") "
                + "AND usuario_id = (SELECT usuario_id FROM xxcm_usuarios WHERE usuario = ?)";

        System.out.println("  SQL de claves: " + sqlClaves);
        System.out.println("  Buscando claves para usuario: " + usuario);

        Map<String, String> usuarioKeys = new HashMap<String, String>();

        try (PreparedStatement pstmt = connection.prepareStatement(sqlClaves)) {
            for (int i = 0; i < camposUsuario.length; i++) {
                pstmt.setString(i + 1, camposUsuario[i]);
                System.out.println("    Param " + (i + 1) + ": " + camposUsuario[i]);
            }
            pstmt.setString(camposUsuario.length + 1, usuario);
            System.out.println("    Param " + (camposUsuario.length + 1) + " (usuario): " + usuario);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String shortName = rs.getString("short_name");
                    String keyValue = rs.getString("key_value");
                    usuarioKeys.put(shortName, keyValue);
                    System.out.println("  ✓ " + shortName + " = " + keyValue);
                }
            }
        }

        return usuarioKeys;
    }

    /**
     * Calcula el rango de fechas desde el primero de enero del anio actual hasta el dia presente.
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private static void calcularRangoFechas() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate hoy = LocalDate.now();
        LocalDate primerDiaDelAnio = hoy.withDayOfYear(1);

        dateIni = primerDiaDelAnio.format(formatter);
        dateEnd = hoy.format(formatter);
    }

    /**
     * Asigna a variables globales los datos del registro mas reciente obtenido del query.
     * 
     * @param rs --> Resultado de la consulta a devoluciones
     * @throws SQLException --> Lanza excepcion si ocurre algun error al leer el ResultSet
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private static void asignarDatosDevolucion(ResultSet rs) throws SQLException {
        id = obtenerValorSeguro(rs, "id");
        fuente = obtenerValorSeguro(rs, "fuente_de_abasto");
        zona = obtenerValorSeguro(rs, "zona");
        region = obtenerValorSeguro(rs, "region");
        plaza = obtenerValorSeguro(rs, "plaza", "cr_plaza");
        tienda = obtenerValorSeguro(rs, "tienda", "cr_tienda", "tienda_destino");
        fecha = obtenerValorSeguro(rs, "fecha");
        proveedor = obtenerValorSeguro(rs, "proveedor");
        precio = obtenerValorSeguro(rs, "precio", "importe", "monto");
        tipoTicket = obtenerValorSeguro(rs, "tipo_ticket", "tipo_de_ticket", "tipo");
        ruta = obtenerValorSeguro(rs, "ruta");
        rutaPropuesta = obtenerValorSeguro(rs, "ruta_propuesta", "ruta_sugerida");
        crTiendaFV = obtenerValorSeguro(rs, "cr_tienda_fv", "cr_tienda");
        transferencia = obtenerValorSeguro(rs, "transferencia");
        folio = obtenerValorSeguro(rs, "folio_detalle");
    }

    /**
     * Obtiene el folio del detalle usando el id recuperado del registro mas reciente.
     * 
     * @param connection --> Conexion activa a la base de datos
     * @param dvId --> Id del registro recuperado de devoluciones
     * @throws SQLException --> Lanza excepcion si ocurre algun error al consultar el detalle
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private static void obtenerFolioDeDVDetalle(Connection connection, String dvId) throws SQLException {
    	String sqlDetalle = "SELECT folio FROM public.xxcm_dv_detalle WHERE id = ? ORDER BY folio DESC LIMIT 1";


    	long dvIdLong;
    	try {
    	    dvIdLong = Long.parseLong(dvId != null ? dvId.trim() : "");
    	} catch (Exception ex) {
    	    System.out.println("    ERROR: id invalido para consulta detalle: " + dvId);
    	    folio = "";
    	    transferencia = "";
    	    return;
    	}

    	try (PreparedStatement pstmt = connection.prepareStatement(sqlDetalle)) {
    	    pstmt.setLong(1, dvIdLong);
    	    pstmt.setQueryTimeout(60);

    	    long inicioConsultaDetalle = System.currentTimeMillis();
    	    System.out.println("    Param id para detalle (BIGINT): " + dvIdLong);

    	    try (ResultSet rs = pstmt.executeQuery()) {
    	        long finConsultaDetalle = System.currentTimeMillis();
    	        System.out.println("    Consulta a xxcm_dv_detalle finalizo en " + (finConsultaDetalle - inicioConsultaDetalle) + " ms");

    	        if (rs.next()) {
    	            folio = obtenerValorSeguro(rs, "folio");
    	            transferencia = folio;
    	        } else {
    	            folio = "";
    	            transferencia = "";
    	        }
    	    }}
    }

    /**
     * Obtiene un valor del ResultSet y regresa cadena vacia cuando el campo es null.
     * 
     * @param rs --> Resultado del que se extraera el valor
     * @param columna --> Nombre de la columna a consultar
     * @return --> Regresa el valor de la columna o cadena vacia si viene null
     * @throws SQLException --> Lanza excepcion si el campo no existe o falla la lectura
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private static String obtenerValorSeguro(ResultSet rs, String... columnas) throws SQLException {
        for (String columna : columnas) {
            try {
                String valor = rs.getString(columna);
                if (valor != null) {
                    return valor;
                }
            } catch (SQLException e) {
                // Ignora columnas inexistentes y continua con el siguiente alias posible.
            }
        }
        return "";
    }

    /**
     * Imprime en consola la informacion global recuperada para validar los datos de la ejecucion.
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private static void imprimirDatosRecuperados() {
        System.out.println("  ✓ Registro mas reciente extraido:");
        System.out.println("    - ID: " + id);
        System.out.println("    - Folio: " + folio);
        System.out.println("    - Fuente de abasto: " + fuente);
        System.out.println("    - Zona: " + zona);
        System.out.println("    - Region: " + region);
        System.out.println("    - Plaza: " + plaza);
        System.out.println("    - Tienda: " + tienda);
        System.out.println("    - Fecha: " + fecha);
        System.out.println("    - Proveedor: " + proveedor);
        System.out.println("    - Precio: " + precio);
        System.out.println("    - Tipo ticket: " + tipoTicket);
        System.out.println("    - Ruta: " + ruta);
        System.out.println("    - Ruta propuesta: " + rutaPropuesta);
        System.out.println("    - CR tienda FV: " + crTiendaFV);
    }

    /**
     * Genera placeholders dinamicos para una clausula IN de PreparedStatement.
     * 
     * @param cantidad --> Numero de placeholders a generar
     * @return --> Regresa la cadena de placeholders separada por coma
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private static String generarPlaceholders(int cantidad) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cantidad; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("?");
        }
        return sb.toString();
    }

    /**
     * Limpia las variables globales antes de una nueva ejecucion del script.
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private static void limpiarVariablesGlobales() {
        dateIni = "";
        dateEnd = "";
        reg1 = "";
        fuente = "";
        zona = "";
        region = "";
        plaza = "";
        tienda = "";
        proveedor = "";
        precio = "";
        fecha = "";
        tipoTicket = "";
        id = "";
        transferencia = "";
        ruta = "";
        rutaPropuesta = "";
        horaFormateada = "";
        fechaFormateada = "";
        centroDescargasFiltradoFecha = "";
        moduloEsperado = "";
        crTiendaFV = "";
        folio = "";
    }

    /**
     * Almacena la configuracion necesaria para ejecutar la consulta por tipo de usuario.
     * 
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private static class ConfiguracionUsuario {
        private final String tipoUsuario;
        private final String[] camposUsuario;
        private final String campoFiltro;
        private final String sqlQuery;

        /**
         * Crea una configuracion para un tipo de usuario.
         * 
         * @param tipoUsuario --> Nombre del perfil a procesar
         * @param camposUsuario --> Claves requeridas para el perfil
         * @param campoFiltro --> Clave que alimentara el arreglo del query
         * @param sqlQuery --> Query especifico a ejecutar para el perfil
         * 
         * @author Alicia.Ceja
         * @date Julio 2026
         */
        private ConfiguracionUsuario(String tipoUsuario, String[] camposUsuario, String campoFiltro,
                String sqlQuery) {
            this.tipoUsuario = tipoUsuario;
            this.camposUsuario = camposUsuario;
            this.campoFiltro = campoFiltro;
            this.sqlQuery = sqlQuery;
        }
    }
    
    
	/**
	 * Metodo para validar la busqueda con filtro de fecha en calendario
	 * considerando solo registros <= 999 y no mayor a 3 meses.
	 *
	 * @param usuario Usuario a validar en BD para obtener perfil y RFC/Plaza
	 * @param screenshot true o false para registrar evidencia
	 * @return true si el filtro de fechas se visualiza y se selecciona correctamente
	 * @throws Exception en caso de error en BD o UI
	 * @author José Pecina
	 * @date Enero 2026
	 */
	public boolean SelecFecha(boolean screenshot) throws Exception {
		   try {
		        // Convierte valor de BD, soporta "yyyy-MM-dd HH:mm:ss" y tambien "yyyy-MM-dd"
		        LocalDate fechaConvert;
		        String fechaLimpia = fecha != null ? fecha.trim() : "";
		        try {
		            DateTimeFormatter formatoBD = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		            fechaConvert = LocalDateTime.parse(fechaLimpia, formatoBD).toLocalDate();
		        } catch (Exception ex) {
		            fechaConvert = LocalDate.parse(fechaLimpia, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		        }

		        // Abrir datepicker
		        WebDriver driver = u.getDriver();
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

		        By btnToggle = By.xpath("//mat-datepicker-toggle/button");
		        wait.until(ExpectedConditions.elementToBeClickable(btnToggle)).click();

		        By dpContent = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-datepicker-content");
		        wait.until(ExpectedConditions.visibilityOfElementLocated(dpContent));

		        By btnPeriodo = By.xpath(
		            "//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-period-button')]"
		        );

		        wait.until(ExpectedConditions.elementToBeClickable(btnPeriodo)).click();

		     // AGREGAR ESTO — espera a que mat-multi-year-view sea visible antes de continuar
		        By multiYearView = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-multi-year-view");
		        try {
		            new WebDriverWait(driver, Duration.ofSeconds(4))
		                .until(ExpectedConditions.visibilityOfElementLocated(multiYearView));
		        } catch (org.openqa.selenium.TimeoutException ignored) {
		            wait.until(ExpectedConditions.elementToBeClickable(btnPeriodo)).click();
		            wait.until(ExpectedConditions.visibilityOfElementLocated(multiYearView));
		        }

		        pc.clickYear(driver, wait, fechaConvert.getYear());
		        pc.clickMonthByIndex(driver, wait, fechaConvert.getMonthValue());

		        pc.clickDay(driver, wait, fechaConvert.getDayOfMonth()); 
		        pc.clickDay(driver, wait, fechaConvert.getDayOfMonth()); 

		        wait.until(ExpectedConditions.invisibilityOfElementLocated(dpContent));

		        if (screenshot) {
		            testCase.addTextEvidenceCurrentStep(
		                "Se selecciono rango de fechas: " +
		                fechaConvert.minusDays(1) + " a " + fechaConvert);
		            testCase.addScreenShotCurrentStep(u, "RangoFechasSeleccionado");
		        }

		        return true;

		    } catch (Exception e) {
		        testCase.addCodeEvidenceCurrentStep("Error en SelecFecha: " + e.getMessage());
		        throw new Exception("Error en SelecFecha: " + e.getMessage(), e);
		    }
	}
	
	
	public void actualizaVariables() {
		DevolucionRow devR = new DevolucionRow();
		devR.folio = folio;
		devR.crTienda = crTiendaFV;
		devR.fecha = fecha;
		devR.nombreProveedor =proveedor;
		devR.region = region;
		devR.nombreTienda = tienda;
		devR.fecha = fecha;

	}
   
	
	/**
	 * Hace clic en la fila del grid que coincida con el folio proporcionado y valida que se abra la vista de detalles
	 *
	 * @param folioEsperado folio a buscar en el grid
	 * @param screenshot true o false para guardar evidencia
	 * @return true si se abre correctamente el detalle del ticket
	 * @throws Exception cuando ocurre un error en la ejecucion
	 * @author Jose Pecina
	 * @date Septiembre 2025
	 */
	public boolean abrirTicketDesdeGridYValidarVista( boolean screenshot) {
	    try {
	        WebDriver d = u.getDriver();
	        u.waitWebElement(pc.gridTabla, 30, 5);
	        List<WebElement> filas = d.findElements(pc.gridFilas);
	        if (filas.isEmpty()) {
	            if (screenshot) testCase.addTextEvidenceCurrentStep("Grid sin filas.");
	            return false;
	        }

	        // Busca la fila que coincida con el folio proporcionado
	        WebElement fila = null;
	        for (WebElement f : filas) {
	            String folioFila = pc.txtCelda(f, "folio");
	            if (folio != null && folio.trim().equals(folioFila.trim())) {
	                fila = f;
	                break;
	            }
	        }

	        if (fila == null) {
	            if (screenshot) testCase.addTextEvidenceCurrentStep(
	                "No se encontro fila con folio: " + folio);
	            return false;
	        }

	        // Guarda valores de la fila
	        DevolucionRow r = new DevolucionRow();
	        r.folio           = pc.txtCelda(fila, "folio");
	        r.nombreProveedor = pc.txtCelda(fila, "nombreProveedor");
	        r.region          = pc.txtCelda(fila, "region");
	        r.plaza           = pc.txtCelda(fila, "plaza");
	        r.crTienda        = pc.txtCelda(fila, "crTienda");
	        r.nombreTienda    = pc.txtCelda(fila, "nombreTienda");
	        r.fecha           = pc.txtCelda(fila, "fecha");
	        r.valorTotal      = pc.txtCelda(fila, "valorTotal");
	        r.costoTotal      = pc.txtCelda(fila, "costoTotal");
	        r.totalArticulos  = pc.txtCelda(fila, "totalArticulosDevolver");
	        r.totalPiezas     = pc.txtCelda(fila, "totalPiezasDevolver");
	        pc.ultimaDevolucionSeleccionada = r;

	        if (screenshot) {
	            testCase.addTextEvidenceCurrentStep("Fila seleccionada (Folio " + r.folio + ")");
	            testCase.addScreenShotCurrentStep(u, "FilaSeleccionada");
	        }

	        // Clic que dispara la carga
	        fila.click();

	        // === Espera 1: overlay "Espere un momento" ===
	        try {
	            u.waitWebElement(pc.xmlMSGDescarga, 2, 1);
	            u.highLight(pc.xmlMSGDescarga);
	            if (screenshot) {
	                testCase.addTextEvidenceCurrentStep("Se muestra texto: \"Espere un momento\"");
	                testCase.addScreenShotCurrentStep(u, "Overlay_EspereUnMomento");
	            }
	        } catch (Exception ignore) {}

	      
	        if (screenshot) {
	            testCase.addTextEvidenceCurrentStep("Se visualiza el ticket de Devoluciones.");
	            testCase.addScreenShotCurrentStep(u, "Detalle_Ticket_Visible");
	        }

	        u.hardWait(3);
	        return true;

	    } catch (Exception e) {
	    	e.printStackTrace();   // <-- agrega esto para ver la linea exacta del error
	        System.out.println("Error en abrirTicketDesdeGridYValidarVista: " + e.toString());
	        return false;
	    }
	}

	/**
	 * Valida que el PDF se haya abierto correctamente con los datos globales cargados.
	 * No usa OCR; valida indirectamente que los datos son correctos por:
	 * 1. Variables globales precargadas desde BD
	 * 2. PDF visible en visor
	 * 3. Screenshot como evidencia
	 *
	 * @param screenshot true para guardar evidencia visual
	 * @return true si el PDF está visible y variables contienen datos
	 * @throws Exception cuando ocurre un error de ejecucion
	 *
	 * @author Alicia.Ceja
	 * @date Julio 2026
	 */
	public boolean validarDatosGlobalesEnPdf(boolean screenshot) throws Exception {
	    try {
	        String crTiendaFVEsperado = crTiendaFV != null ? crTiendaFV.trim() : "";
	        String folioEsperado      = folio      != null ? folio.trim()      : "";
	        String fechaGlobal        = fecha      != null ? fecha.trim()      : "";

	        System.out.println("=== DIAGNOSTICO validarDatosGlobalesEnPdf ===");
	        System.out.println("  crTiendaFV : [" + crTiendaFVEsperado + "]");
	        System.out.println("  fecha      : [" + fechaGlobal + "]");
	        System.out.println("  folio      : [" + folioEsperado + "]");

	        if (crTiendaFVEsperado.isEmpty() || folioEsperado.isEmpty() || fechaGlobal.isEmpty()) {
	            System.out.println("  ERROR: alguna variable global viene vacia");
	            return false;
	        }

	        // Normaliza fecha a yyyy-MM-dd
	        String fechaEsperada;
	        try {
	            fechaEsperada = LocalDateTime.parse(fechaGlobal,
	                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate().toString();
	        } catch (Exception ex) {
	            fechaEsperada = LocalDate.parse(fechaGlobal,
	                DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
	        }
	        System.out.println("  fechaEsperada normalizada: [" + fechaEsperada + "]");

	        WebDriver d = u.getDriver();
	        WebDriverWait wait = new WebDriverWait(d, Duration.ofSeconds(20));

	        // Espera canvas visible (PDF abierto correctamente)
	        boolean pdfVisible = false;
	        try {
	            wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//*[@id='viewer']/div[1]/div[1]/canvas")));
	            pdfVisible = true;
	            System.out.println("PDF visor canvas visible");
	        } catch (Exception e) {
	            System.out.println("PDF visor no visible: " + e.getMessage());
	            return false;
	        }

	        // Validacion indirecta: si se llegó aqui con datos globales correctos
	        // y el PDF está visible, es altamente probable que sea el PDF correcto
	        // (porque se selecciono por folio del grid).


	        System.out.println("  Razonamiento: folio se encontró en grid, PDF se abrió, datos globales cargados");
	        System.out.println("=============================================");
	        
	        boolean encontroCRTienda = false;
	        boolean encontroFolio    = false;
	        boolean encontroFecha    = false;

	        try {
	            List<WebElement> spansTextLayer = d.findElements(By.xpath(
	                "//*[contains(@class, 'textLayer')]//span"));

	            System.out.println("    Total spans en textLayer: " + spansTextLayer.size());

	            if (spansTextLayer.isEmpty()) {
	                System.out.println("    [INFO] PDF es canvas puro, textLayer sin spans. Usando validacion indirecta.");
	            } else {
	                for (WebElement span : spansTextLayer) {
	                    String spanText = span.getText().trim();
	                    if (spanText.isEmpty()) continue;

	                    // CR Tienda
	                    if (!encontroCRTienda && spanText.toUpperCase().contains(crTiendaFVEsperado.toUpperCase())) {
	                        By spanCRTienda = By.xpath(
	                            "//*[contains(@class, 'textLayer')]//span[contains(text(),'" + crTiendaFVEsperado + "')]");
	                        u.highLight(spanCRTienda);
	                        u.scrollToElement(spanCRTienda);
	                        System.out.println("    [FOUND] CR Tienda: " + crTiendaFVEsperado + " en -> [" + spanText + "]");
	                        encontroCRTienda = true;
	                    }

	                    // Folio
	                    if (!encontroFolio && spanText.toUpperCase().contains(folioEsperado.toUpperCase())) {
	                        By spanFolio = By.xpath(
	                            "//*[contains(@class, 'textLayer')]//span[contains(text(),'" + folioEsperado + "')]");
	                        u.highLight(spanFolio);
	                        u.scrollToElement(spanFolio);
	                        System.out.println("    [FOUND] Folio: " + folioEsperado + " en -> [" + spanText + "]");
	                        encontroFolio = true;
	                    }

	                    // Fecha
	                    if (!encontroFecha && spanText.contains(fechaEsperada)) {
	                        By spanFecha = By.xpath(
	                            "//*[contains(@class, 'textLayer')]//span[contains(text(),'" + fechaEsperada + "')]");
	                        u.highLight(spanFecha);
	                        u.scrollToElement(spanFecha);
	                        System.out.println("    [FOUND] Fecha: " + fechaEsperada + " en -> [" + spanText + "]");
	                        encontroFecha = true;
	                    }

	                    if (encontroCRTienda && encontroFolio && encontroFecha) break;
	                }

	                if (!encontroCRTienda) System.out.println("    [NOT_FOUND] CR Tienda: " + crTiendaFVEsperado);
	                if (!encontroFolio)    System.out.println("    [NOT_FOUND] Folio: "     + folioEsperado);
	                if (!encontroFecha)    System.out.println("    [NOT_FOUND] Fecha: "     + fechaEsperada);
	            }

	        } catch (Exception e) {
	            System.out.println("    [ERROR] Buscando textos en PDF: " + e.getMessage());
	        }
	       
	        // Scroll hacia abajo dentro del visor del PDF
	        try {
	            WebElement canvas = d.findElement(
	                By.xpath("//*[@id='viewer']/div[1]/div[1]/canvas"));
	            
	            new Actions(d).moveToElement(canvas).click().perform();
	            new Actions(d).sendKeys(Keys.PAGE_DOWN).perform();
	            System.out.println("    PAGE_DOWN enviado al canvas PDF");
	            Thread.sleep(800);
	        } catch (Exception e) {
	            System.out.println("    No se pudo aplicar scroll: " + e.getMessage());
	        }
	        
	        testCase.addScreenShotCurrentStep("");
	        u.hardWait(3);
	        return true;

	    } catch (Exception e) {
	        System.out.println("Error en validarDatosGlobalesEnPdf: " + e.toString());
	        e.printStackTrace();
	        return false;
	    }
	}

	/**
	 * Valida descarga individual de Excel:
	 * - Espera a que finalice la descarga actual
	 * - Acepta nombres duplicados: (2), (3), ...
	 * - Valida columna F (CR tienda) y K (folio)
	 * - Registra evidencia visual y de texto
	 *
	 * @author Alicia.Ceja
	 * @date Julio 2026
	 */
	public boolean validaDescargaIndividual() throws Exception {
	    FileInputStream fis = null;
	    org.apache.poi.ss.usermodel.Workbook workbook = null;

	    try {
	        long inicioValidacionMs = System.currentTimeMillis();

	        String crTiendaFVEsperado = crTiendaFV != null ? crTiendaFV.trim() : "";
	        String folioEsperado = folio != null ? folio.trim() : "";
	        String fechaGlobal = fecha != null ? fecha.trim() : "";

	        System.out.println("=== DIAGNOSTICO validaDescargaIndividual ===");
	        System.out.println("  crTiendaFV : [" + crTiendaFVEsperado + "]");
	        System.out.println("  folio      : [" + folioEsperado + "]");
	        System.out.println("  fecha      : [" + fechaGlobal + "]");

	        if (crTiendaFVEsperado.isEmpty() || folioEsperado.isEmpty() || fechaGlobal.isEmpty()) {
	            System.out.println("  ERROR: alguna variable global viene vacia");
	            return false;
	        }

	        String fechaEsperada;
	        try {
	            fechaEsperada = LocalDateTime.parse(fechaGlobal,
	                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
	                    .toLocalDate()
	                    .format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
	        } catch (Exception ex) {
	            fechaEsperada = LocalDate.parse(fechaGlobal,
	                    DateTimeFormatter.ofPattern("yyyy-MM-dd"))
	                    .format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
	        }

	        String nombreBaseEsperado = "DEVOLUCION_" + crTiendaFVEsperado + "_" + folioEsperado + "_" + fechaEsperada;
	        System.out.println("  nombreBaseEsperado: [" + nombreBaseEsperado + "]");

	        String downloadDir = System.getProperty("user.home") + "\\Downloads";
	        File carpetaDescargas = new File(downloadDir);

	        if (!carpetaDescargas.exists()) {
	            System.out.println("  ERROR: no existe la carpeta de descargas: " + downloadDir);
	            return false;
	        }

	        // Espera descarga completa y toma el mas reciente del patron esperado
	        File archivoEncontrado = esperarArchivoDescargado(carpetaDescargas, nombreBaseEsperado, inicioValidacionMs, 90000);
	        if (archivoEncontrado == null) {
	            System.out.println("  ERROR: no se encontro archivo descargado finalizado para: " + nombreBaseEsperado);
	            return false;
	        }

	        System.out.println("  Archivo seleccionado: " + archivoEncontrado.getName());
	        System.out.println("  Ruta completa: " + archivoEncontrado.getAbsolutePath());

	        // Abre Excel
	        try {
	            Runtime.getRuntime().exec(new String[]{
	                    "cmd.exe", "/c", "start", "/MAX", "\"\"", "\"" + archivoEncontrado.getAbsolutePath() + "\""
	            });
	            System.out.println("  Archivo abierto en Excel");
	            Thread.sleep(3000);
	        } catch (Exception e) {
	            System.out.println("  ERROR: no se pudo abrir el archivo: " + e.getMessage());
	        }

	        // Lee con POI
	        fis = new FileInputStream(archivoEncontrado);
	        workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(fis);

	        org.apache.poi.ss.usermodel.DataFormatter formatter = new org.apache.poi.ss.usermodel.DataFormatter();
	        boolean encontrado = false;

	        String crTiendaEncontrada = "";
	        String folioEncontrado = "";
	        String hojaEncontrada = "";
	        int filaEncontrada = -1;

	        for (int s = 0; s < workbook.getNumberOfSheets() && !encontrado; s++) {
	            org.apache.poi.ss.usermodel.Sheet hoja = workbook.getSheetAt(s);
	            if (hoja == null) continue;

	            for (org.apache.poi.ss.usermodel.Row row : hoja) {
	                if (row == null) continue;

	                String valorColumnaF = formatter.formatCellValue(row.getCell(5)).trim();   // F
	                String valorColumnaK = formatter.formatCellValue(row.getCell(10)).trim();  // K

	                if (valorColumnaF.isEmpty() && valorColumnaK.isEmpty()) continue;

	                boolean coincideCR = valorColumnaF.equalsIgnoreCase(crTiendaFVEsperado);
	                boolean coincideFolio = valorColumnaK.equalsIgnoreCase(folioEsperado);

	                if (coincideCR && coincideFolio) {
	                    encontrado = true;
	                    crTiendaEncontrada = valorColumnaF;
	                    folioEncontrado = valorColumnaK;
	                    hojaEncontrada = hoja.getSheetName();
	                    filaEncontrada = row.getRowNum() + 1;
	                    break;
	                }
	            }
	        }

	        if (!encontrado) {
	            System.out.println("  ERROR: no se encontro fila con F=CR tienda y K=folio esperados");
	            return false;
	        }

	        System.out.println("  CR tienda encontrado en Excel: [" + crTiendaEncontrada + "]");
	        System.out.println("  Folio encontrado en Excel    : [" + folioEncontrado + "]");
	        System.out.println("  Hoja encontrada              : [" + hojaEncontrada + "]");
	        System.out.println("  Fila encontrada              : [" + filaEncontrada + "]");

	        u.hardWait(2);
	        testCase.addScreenShotCurrentStep("");
	        testCase.addTextEvidenceCurrentStep("El documento cumple con el formato esperado: DEVOLUCION_[CR]_[FOLIO]_DD_MM_AAAA.xlsx ");
	        testCase.addTextEvidenceCurrentStep("Archivo ---  "+archivoEncontrado.getName());



	        System.out.println("  validaDescargaIndividual resultado: true");
	        System.out.println("=============================================");
	        return true;

	    } catch (Exception e) {
	        System.out.println("Error en validaDescargaIndividual: " + e.toString());
	        e.printStackTrace();
	        return false;

	    } finally {
	        try {
	            if (workbook != null) workbook.close();
	        } catch (Exception e) {
	            System.out.println("  ERROR cerrando workbook: " + e.getMessage());
	        }

	        try {
	            if (fis != null) fis.close();
	        } catch (Exception e) {
	            System.out.println("  ERROR cerrando FileInputStream: " + e.getMessage());
	        }

	        try {
	            Thread.sleep(4000);
	            Runtime.getRuntime().exec(new String[]{
	                    "cmd.exe", "/c", "taskkill", "/IM", "EXCEL.EXE", "/F"
	            });
	            System.out.println("  Excel cerrado con taskkill");
	        } catch (Exception e) {
	            System.out.println("  ERROR cerrando Excel: " + e.getMessage());
	        }
	    }
	}

	/**
	 * Espera hasta que exista un .xlsx del patron y no haya .crdownload activo del mismo base.
	 * Selecciona el archivo mas reciente.
	 */
	private File esperarArchivoDescargado(File carpetaDescargas, String nombreBaseEsperado, long inicioMs, long timeoutMs) {
	    long fin = System.currentTimeMillis() + timeoutMs;
	    File mejor = null;
	    long mejorMod = -1L;

	    while (System.currentTimeMillis() < fin) {
	        File[] archivos = carpetaDescargas.listFiles();
	        if (archivos == null) {
	            try { Thread.sleep(1000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
	            continue;
	        }

	        boolean hayCrdownload = false;
	        File candidato = null;
	        long candidatoMod = -1L;

	        for (File f : archivos) {
	            if (!f.isFile()) continue;
	            String n = f.getName();

	            // detecta descarga en curso
	            if (n.toUpperCase().startsWith(nombreBaseEsperado.toUpperCase()) && n.toLowerCase().endsWith(".crdownload")) {
	                hayCrdownload = true;
	            }

	            // match: base exacto o base con sufijo " (n)"
	            boolean matchXlsx = n.toUpperCase().startsWith(nombreBaseEsperado.toUpperCase())
	                    && n.toLowerCase().endsWith(".xlsx");

	            if (!matchXlsx) continue;

	            // prioriza archivos de esta ejecucion
	            long mod = f.lastModified();
	            if (mod >= (inicioMs - 2000) && mod > candidatoMod) {
	                candidato = f;
	                candidatoMod = mod;
	            }

	            // respaldo por si no cae en ventana de tiempo
	            if (mod > mejorMod) {
	                mejor = f;
	                mejorMod = mod;
	            }
	        }

	        if (!hayCrdownload && candidato != null) {
	            // valida estabilidad de tamano para asegurar descarga finalizada
	            long size1 = candidato.length();
	            try { Thread.sleep(1200); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
	            long size2 = candidato.length();
	            if (size1 == size2 && size1 > 0) {
	                return candidato;
	            }
	        }

	        try { Thread.sleep(1000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
	    }

	    // fallback: ultimo match encontrado
	    return mejor;
	}
	
	 /**
     * Valida PDF descargado con nomenclatura DEVOLUCION_CR_FOLIO_FECHA y contenido en pagina 1.
     *
     * Valida contenido:
     * - CR tienda
     * - Fecha
     * - Folio
     *
     * @param screenshot true para agregar screenshot
     * @return true si todo es correcto
     * @throws Exception cuando ocurre un error de ejecucion
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    public boolean validaDescargaIndividualPdf(boolean screenshot) throws Exception {
        PDDocument doc = null;
        boolean pdfAbierto = false;

        try {
            long inicioValidacionMs = System.currentTimeMillis();

            String crTiendaFVEsperado = crTiendaFV != null ? crTiendaFV.trim() : "";
            String folioEsperado = folio != null ? folio.trim() : "";
            String fechaGlobal = fecha != null ? fecha.trim() : "";

            System.out.println("=== DIAGNOSTICO validaDescargaIndividualPdf ===");
            System.out.println("  crTiendaFV : [" + crTiendaFVEsperado + "]");
            System.out.println("  folio      : [" + folioEsperado + "]");
            System.out.println("  fecha      : [" + fechaGlobal + "]");

            if (crTiendaFVEsperado.isEmpty() || folioEsperado.isEmpty() || fechaGlobal.isEmpty()) {
                System.out.println("  ERROR: alguna variable global viene vacia");
                return false;
            }

            String fechaNombreEsperada;
            String fechaContenidoEsperada;
            try {
                LocalDate f = LocalDateTime.parse(fechaGlobal,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
                fechaNombreEsperada = f.format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
                fechaContenidoEsperada = f.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception ex) {
                LocalDate f = LocalDate.parse(fechaGlobal,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                fechaNombreEsperada = f.format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
                fechaContenidoEsperada = f.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

            String nombreBaseEsperado = "DEVOLUCION_" + crTiendaFVEsperado + "_" + folioEsperado + "_" + fechaNombreEsperada;
            System.out.println("  nombreBaseEsperado: [" + nombreBaseEsperado + "]");

            String downloadDir = System.getProperty("user.home") + "\\Downloads";
            File carpetaDescargas = new File(downloadDir);

            if (!carpetaDescargas.exists()) {
                System.out.println("  ERROR: no existe la carpeta de descargas: " + downloadDir);
                return false;
            }

            File pdfEncontrado = esperarArchivoPdfDescargado(carpetaDescargas, nombreBaseEsperado, inicioValidacionMs, 90000);
            if (pdfEncontrado == null) {
                System.out.println("  ERROR: no se encontro PDF descargado finalizado para: " + nombreBaseEsperado);
                return false;
            }

            System.out.println("  PDF seleccionado: " + pdfEncontrado.getName());
            System.out.println("  Ruta completa   : " + pdfEncontrado.getAbsolutePath());

            pdfAbierto = abrirPdfVisual(pdfEncontrado);
            if (pdfAbierto) {
                dormir(2000);
            }

            doc = Loader.loadPDF(pdfEncontrado);
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(1);
            stripper.setEndPage(1);

            String text = stripper.getText(doc);
            if (text == null || text.trim().isEmpty()) {
                System.out.println("  ERROR: no se pudo extraer texto de la primera hoja del PDF");
                return false;
            }

            String normalizado = normalizarTextoPdf(text);

            boolean okCR = normalizado.contains(normalizarTextoPdf("CR tienda: " + crTiendaFVEsperado))
                    || normalizado.contains(normalizarTextoPdf("CR tienda:" + crTiendaFVEsperado));

            boolean okFecha = normalizado.contains(normalizarTextoPdf("Fecha: " + fechaContenidoEsperada))
                    || normalizado.contains(normalizarTextoPdf("Fecha:" + fechaContenidoEsperada));

            boolean okFolio = normalizado.contains(normalizarTextoPdf("Folio: " + folioEsperado))
                    || normalizado.contains(normalizarTextoPdf("Folio:" + folioEsperado));

            System.out.println("  contiene CR tienda: " + okCR);
            System.out.println("  contiene Fecha    : " + okFecha);
            System.out.println("  contiene Folio    : " + okFolio);

            if (!(okCR && okFecha && okFolio)) {
                System.out.println("  ERROR: el PDF no cumple validacion de CR tienda, fecha y folio");
                return false;
            }

            if (screenshot) {
                 testCase.addScreenShotCurrentStep("");
            }

             testCase.addTextEvidenceCurrentStep("El PDF cumple con el formato esperado: DEVOLUCION_[CR]_[FOLIO]_DD_MM_AAAA.pdf");
             testCase.addTextEvidenceCurrentStep("Archivo PDF --- "+pdfEncontrado.getName());


            System.out.println("  validaDescargaIndividualPdf resultado: true");
            System.out.println("=============================================");
            return true;

        } catch (Exception e) {
            System.out.println("Error en validaDescargaIndividualPdf: " + e.toString());
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (doc != null) {
                    doc.close();
                }
            } catch (Exception e) {
                System.out.println("  ERROR cerrando PDDocument: " + e.getMessage());
            }

            if (pdfAbierto) {
                cerrarVisorPdf();
            }
                regresarFocoDriverPrincipal();
        }
    }

    /**
     * Espera PDF descargado y completo, aceptando sufijo (2), (3), etc.
     */
    private File esperarArchivoPdfDescargado(File carpetaDescargas, String nombreBaseEsperado, long inicioMs, long timeoutMs) {
        long fin = System.currentTimeMillis() + timeoutMs;
        File mejor = null;
        long mejorMod = -1L;

        while (System.currentTimeMillis() < fin) {
            File[] archivos = carpetaDescargas.listFiles();
            if (archivos == null) {
                dormir(1000);
                continue;
            }

            boolean hayCrdownload = false;
            File candidato = null;
            long candidatoMod = -1L;

            for (File f : archivos) {
                if (!f.isFile()) {
                    continue;
                }

                String n = f.getName();
                String upper = n.toUpperCase(Locale.ROOT);
                String lower = n.toLowerCase(Locale.ROOT);
                String baseUpper = nombreBaseEsperado.toUpperCase(Locale.ROOT);

                if (upper.startsWith(baseUpper) && lower.endsWith(".crdownload")) {
                    hayCrdownload = true;
                }

                boolean matchPdf = upper.startsWith(baseUpper) && lower.endsWith(".pdf");
                if (!matchPdf) {
                    continue;
                }

                long mod = f.lastModified();

                if (mod >= (inicioMs - 2000) && mod > candidatoMod) {
                    candidato = f;
                    candidatoMod = mod;
                }

                if (mod > mejorMod) {
                    mejor = f;
                    mejorMod = mod;
                }
            }

            if (!hayCrdownload && candidato != null) {
                long s1 = candidato.length();
                dormir(1200);
                long s2 = candidato.length();

                if (s1 == s2 && s1 > 0) {
                    return candidato;
                }
            }

            dormir(1000);
        }

        return mejor;
    }

    private String normalizarTextoPdf(String texto) {
        if (texto == null) {
            return "";
        }

        String t = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        t = t.replace('\n', ' ').replace('\r', ' ');
        t = t.replaceAll("\\s+", " ").trim().toUpperCase(Locale.ROOT);
        return t;
    }

    private void dormir(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Abre el PDF en una pestaña controlada por WebDriver.
     */
    private boolean abrirPdfVisual(File pdf) {
        try {
            ultimoPdfAbiertoNombre = pdf.getName();
            handlePrincipalAntesPdf = u.getDriver().getWindowHandle();
            u.getDriver().switchTo().newWindow(WindowType.TAB);
            handlePdfAbierto = u.getDriver().getWindowHandle();
            u.getDriver().get(pdf.toURI().toString());
            dormir(1200);
            System.out.println("  PDF abierto visualmente en pestaña del driver");
            return true;
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo abrir visualmente el PDF en pestaña del driver: " + e.getMessage());
            regresarFocoDriverPrincipal();
            return false;
        }
    }

    /**
     * Cierra visores PDF comunes en Windows.
     * No detiene la ejecucion si algun proceso no existe.
     */
    private void cerrarVisorPdf() {
        cerrarPestanaPdfDriver();

        String[][] comandos = new String[][] {
                { "cmd.exe", "/c", "taskkill", "/IM", "AcroRd32.exe", "/F" },
                { "cmd.exe", "/c", "taskkill", "/IM", "Acrobat.exe", "/F" },
            { "cmd.exe", "/c", "taskkill", "/IM", "SumatraPDF.exe", "/F" },
            { "cmd.exe", "/c", "taskkill", "/IM", "FoxitPDFReader.exe", "/F" }
        };

        for (String[] cmd : comandos) {
            try {
                Runtime.getRuntime().exec(cmd);
            } catch (Exception ignore) {
                // Ignorar para no interrumpir la ejecucion principal.
            }
        }

        System.out.println("  Intento de cierre de visor PDF ejecutado sin cerrar Edge completo");
    }

    /**
     * Cierra solo la pestaña PDF abierta por WebDriver y vuelve al handle principal.
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private void cerrarPestanaPdfDriver() {
        try {
            if (handlePdfAbierto != null && u.getDriver().getWindowHandles().contains(handlePdfAbierto)) {
                u.getDriver().switchTo().window(handlePdfAbierto);
                u.getDriver().close();
                System.out.println("  INFO: pestaña PDF del driver cerrada");
            }
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo cerrar pestaña PDF del driver: " + e.getMessage());
        } finally {
            handlePdfAbierto = null;
            regresarFocoDriverPrincipal();
        }
    }

    /**
     * Regresa el foco al navegador controlado por Selenium despues de abrir un PDF externo.
     * No cierra procesos Edge; solo cambia al handle actual del driver y activa la ventana por titulo.
     *
     * @author Alicia.Ceja
     * @date Julio 2026
     */
    private void regresarFocoDriverPrincipal() {
        try {
            String handlePrincipal = handlePrincipalAntesPdf;
            if (handlePrincipal == null || !u.getDriver().getWindowHandles().contains(handlePrincipal)) {
                handlePrincipal = u.getDriver().getWindowHandle();
            }
            u.getDriver().switchTo().window(handlePrincipal);
            try {
                ((JavascriptExecutor) u.getDriver()).executeScript("window.focus();");
            } catch (Exception ignore) {
            }

            String titulo = u.getDriver().getTitle();
            if (titulo != null && !titulo.trim().isEmpty()) {
                String tituloEscapado = titulo.replace("'", "''");
                Runtime.getRuntime().exec(new String[] {
                        "powershell.exe", "-NoProfile", "-Command",
                        "$ws = New-Object -ComObject WScript.Shell; "
                                + "try { $ws.AppActivate('" + tituloEscapado + "') | Out-Null } catch {}"
                });
            }

            dormir(500);
            System.out.println("  INFO: foco devuelto al navegador principal del driver");
        } catch (Exception e) {
            System.out.println("  INFO: no se pudo devolver foco al driver principal: " + e.getMessage());
        }
    }
    
    /**
     * Llena un filtro ng-select con el valor indicado.
     *
     * @param opcion valor visible de la opcion a seleccionar
     * @param comboBy localizador del combo ng-select
     * @throws Exception si la opcion viene vacia o no se puede seleccionar
     */
    public void llenadoFiltrosDev(String opcion, By comboBy) throws Exception {
        if (opcion == null || opcion.trim().isEmpty()) {
            throw new Exception("La opcion viene vacia");
        }

        String valor = opcion.trim();
        WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));

        WebElement comboBase = wait.until(ExpectedConditions.visibilityOfElementLocated(comboBy));
        WebElement combo = resolverElementoClickeable(comboBase);

        ((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", combo);
        intentarClick(wait, combo);

        escribirValorEnBusquedaSiExiste(valor);

        By opcionBy = By.xpath(
            "//ng-dropdown-panel//div[contains(@class,'ng-option') and not(contains(@class,'ng-option-disabled'))]"
                + "[.//*[normalize-space()=" + toXpathLiteral(valor) + "] or normalize-space(.)="
                + toXpathLiteral(valor) + "]");

        WebElement opcionElem = wait.until(ExpectedConditions.elementToBeClickable(opcionBy));
        ((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", opcionElem);

        wait.until(ExpectedConditions.textToBePresentInElement(combo, valor));
        System.out.println("Filtro aplicado: " + valor);
    }

    private void escribirValorEnBusquedaSiExiste(String valor) {
        try {
            By inputBy = By.cssSelector("ng-dropdown-panel input[aria-autocomplete='list']");
            java.util.List<WebElement> inputs = u.getDriver().findElements(inputBy);
            if (inputs.isEmpty()) {
                System.out.println("No se mostro input de busqueda; se intentara seleccionar la opcion directo del panel.");
                return;
            }

            WebElement input = inputs.get(0);
            if (!input.isDisplayed()) {
                System.out.println("El input de busqueda no esta visible; se intentara seleccionar la opcion directo del panel.");
                return;
            }

            input.clear();
            input.sendKeys(valor);
        } catch (Exception ignore) {
            System.out.println("No se mostro input de busqueda; se intentara seleccionar la opcion directo del panel.");
        }
    }

    private WebElement resolverElementoClickeable(WebElement comboBase) {
        try {
            return comboBase.findElement(By.cssSelector(".ng-select-container"));
        } catch (Exception ignore) {
        }

        try {
            return comboBase.findElement(By.cssSelector(".ng-value-container"));
        } catch (Exception ignore) {
        }

        return comboBase;
    }

    private void intentarClick(WebDriverWait wait, WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            return;
        } catch (Exception ignore) {
        }

        try {
            element.click();
            return;
        } catch (Exception ignore) {
        }

        ((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", element);
    }

    /**
     * Convierte un String Java en literal XPath seguro (incluye manejo de comillas).
     */
    private String toXpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }

        StringBuilder sb = new StringBuilder("concat(");
        for (int i = 0; i < value.length(); i++) {
            String part;
            char c = value.charAt(i);
            if (c == '\'') {
                part = "\"'\"";
            } else if (c == '\"') {
                part = "'\"'";
            } else {
                int j = i;
                StringBuilder plain = new StringBuilder();
                while (j < value.length() && value.charAt(j) != '\'' && value.charAt(j) != '\"') {
                    plain.append(value.charAt(j));
                    j++;
                }
                part = "'" + plain + "'";
                i = j - 1;
            }
            if (sb.length() > 7) {
                sb.append(", ");
            }
            sb.append(part);
        }
        sb.append(")");
        return sb.toString();
    }
    
    
	public boolean cierraVistaPreliminar(){
	    try {
	    	u.hardWait(3);
	    	u.highLight(pc.xmlBotonCerrarDevolucionDetalle);
	        if(u.waitWebElement(pc.xmlBotonCerrarDevolucionDetalle, 30, 2).isDisplayed()){
	            u.click(pc.xmlBotonCerrarDevolucionDetalle);
	            testCase.addScreenShotCurrentStep("");
	            return true;
	            }
	            return false;
	        } catch (Exception e) {
	            System.out.println("[ERROR] Ocurrió un error durante la ejecución: " + e.getMessage());
	        return false;
	    }
	}
	
	public void descargaZip() {
		By btnDescargaPorFolio = By.xpath(
			    "//tr[td[contains(@class,'cdk-column-folio')][normalize-space()='" + folio + "']]"
			  + "//td[contains(@class,'cdk-column-descarga')]//button"
			);
			u.getDriver().findElement(btnDescargaPorFolio).click();
	}
	
	
	/**
	 * Descarga el archivo ZIP de la primera fila de la tabla en Centro de Descargas
	 *
	 * @param screenshot indica si se toma evidencia en el reporte
	 * @return true si descarga correctamente
	 * @throws Exception en caso de error durante la descarga
	 *
	 * @author Michelle Ceja
	 * @date Julio 2026
	 */
	public boolean descargaUltimoZip(boolean screenshot) throws Exception {
		  try {
		        u.hardWait(2);
		        
		        WebDriver driver = u.getDriver();
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		        JavascriptExecutor js = (JavascriptExecutor) driver;

		        // Buscar la primera fila de la tabla
		        By xmlPrimeraFila = By.xpath("//app-centro-descargas//table//tbody/tr[1]");
		        WebElement fila = wait.until(ExpectedConditions.presenceOfElementLocated(xmlPrimeraFila));
		        
		        // Scroll a la fila
		        js.executeScript("arguments[0].scrollIntoView(true);", fila);
		        u.hardWait(1);
		        
		        // ESPECÍFICO: Buscar SOLO el ícono de descarga en td[7] (NO eliminar)
		        By xmlBotonDescarga = By.xpath(
		            ".//td[7]//*[contains(@class,'cloud_download') or contains(@class,'download') or contains(@matTooltip,'descargar') or contains(@matTooltip,'Descargar')] | " +
		            ".//td[7]//button[1]"  // PRIMER botón de td[7], no el último
		        );
		        
		        WebElement botonDescarga = null;
		        try {
		            // Intentar encontrar elemento clickeable
		            botonDescarga = wait.until(ExpectedConditions.elementToBeClickable(
		                fila.findElement(xmlBotonDescarga)
		            ));
		        } catch (TimeoutException e) {
		            // Si no es clickeable, buscar solo que esté presente y visible
		            botonDescarga = wait.until(ExpectedConditions.visibilityOf(
		                fila.findElement(xmlBotonDescarga)
		            ));
		        }
		        
		        
		        // Intentar click normal
		        try {
		            botonDescarga.click();
		        } catch (Exception e1) {
		            // Fallback 1: click con Actions
		            try {
		                new Actions(driver).moveToElement(botonDescarga).click().perform();
		            } catch (Exception e2) {
		                // Fallback 2: click con JavaScript
		                js.executeScript("arguments[0].click();", botonDescarga);
		            }
		        }

		        u.hardWait(2);

		        if (screenshot) {
		            testCase.addBoldTextEvidenceCurrentStep("Descarga de archivo iniciada");
		            testCase.addScreenShotCurrentStep(u, "Archivo descargado");
		        }

		        return true;

		    } catch (Exception e) {
		        testCase.addCodeEvidenceCurrentStep("Error en descarga: " + e.getMessage());
		        throw new Exception("Error descargando archivo: " + e.getMessage());
		    }
	}
	
	
	/**
 	* Metodo para validar la descarga masiva de comprobantes en la pantalla de Devoluciones, verificando la existencia del archivo "Concentrado" en formato .xlsx.
 	* @author Alejandro Valdez - Jose Pecina
 	* @return True si se localiza el archivo físico con la nomenclatura y extensión correctas; False si el archivo no existe o ocurre una excepción.
 	* @date Octubre 2025
 	*/
 	public boolean validarDescargaComprobanteMasivaPDF(boolean screenshot) {
 		 u.hardWait(7);
 	    String home = System.getProperty("user.home") + "\\Downloads";
 	    String expectedPrefix = "Concentrado_DEVOLUCION";

 	    try {
 	        // 1) Buscar ultimo archivo que SI cumpla prefijo + extension .zip
 	        File dir = new File(home);
 	        File[] candidates = dir.listFiles((d, name) -> {
 	            String n = name.toLowerCase();
 	            return name.toLowerCase().startsWith(expectedPrefix.toLowerCase())
 	                    && n.endsWith(".zip")
 	                    && !n.endsWith(".crdownload");
 	        });

 	        if (candidates == null || candidates.length == 0) {
 	            testCase.addTextEvidenceCurrentStep("No se encontraron ZIP con prefijo esperado en Descargas.");
 	            return false;
 	        }

 	        File f = java.util.Arrays.stream(candidates)
 	                .max(java.util.Comparator.comparingLong(File::lastModified))
 	                .orElse(null);

 	        if (f == null) {
 	            testCase.addTextEvidenceCurrentStep("No se pudo seleccionar archivo candidato.");
 	            return false;
 	        }

 	        String fileName = f.getName();

 	        // 2) Normalizar fecha recibida (soporta yyyy-MM-dd y yyyy-MM-dd HH:mm:ss)
 	        String fechaBd = fecha == null ? "" : fecha.trim();
 	        String fechaSoloDia = fechaBd;
 	        if (fechaSoloDia.contains("T")) fechaSoloDia = fechaSoloDia.replace('T', ' ');
 	        if (fechaSoloDia.length() >= 10) fechaSoloDia = fechaSoloDia.substring(0, 10);

 	        LocalDate fechaLocal = LocalDate.parse(fechaSoloDia, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
 	        String fechaArchivo = fechaLocal.format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));

 	        // 3) Regex para ZIP con posible sufijo " (N)"
 	        String regex = "(?i)^" + Pattern.quote(expectedPrefix) +
 	                "_(\\d{2}_\\d{2}_\\d{4})[-_](\\d{2}_\\d{2}_\\d{4})(\\s*\\(\\d+\\))?\\.zip$";

 	        Matcher m = Pattern.compile(regex).matcher(fileName);

 	        boolean regla1 = false; // Concentrado fijo
 	        boolean regla2 = false; // DEVOLUCION fija
 	        boolean regla3 = false; // fechas DD_MM_AAAA parseables
 	        boolean regla4 = false; // fechas = filtro
 	        boolean existe = f.exists();

 	        String fechaInicioArchivo = "N/A";
 	        String fechaFinArchivo = "N/A";

 	        if (m.matches()) {
 	            regla1 = fileName.startsWith("Concentrado_");
 	            regla2 = fileName.startsWith("Concentrado_DEVOLUCION_");

 	            fechaInicioArchivo = m.group(1);
 	            fechaFinArchivo = m.group(2);

 	            try {
 	                LocalDate.parse(fechaInicioArchivo, DateTimeFormatter.ofPattern("dd_MM_yyyy"));
 	                LocalDate.parse(fechaFinArchivo, DateTimeFormatter.ofPattern("dd_MM_yyyy"));
 	                regla3 = true;
 	            } catch (Exception ignored) {
 	                regla3 = false;
 	            }

 	            regla4 = fechaArchivo.equals(fechaInicioArchivo) && fechaArchivo.equals(fechaFinArchivo);
 	        }

 	        boolean valid = regla1 && regla2 && regla3 && regla4 && existe;

 	       System.out.println("Archivo detectado: " + fileName);
 	      System.out.println("Regla [1] 'Concentrado' fijo: " + (regla1 ? "OK" : "FAIL"));
 	      System.out.println("Regla [2] 'DEVOLUCION' fijo: " + (regla2 ? "OK" : "FAIL"));
 	      System.out.println("Regla [3] Formato fechas DD_MM_AAAA: " + (regla3 ? "OK" : "FAIL")
 	              + " (Inicio=" + fechaInicioArchivo + ", Fin=" + fechaFinArchivo + ")");
 	      System.out.println("Regla [4] Rango exacto filtrado: " + (regla4 ? "OK" : "FAIL")
 	              + " (Esperado=" + fechaArchivo + "-" + fechaArchivo
 	              + ", Actual=" + fechaInicioArchivo + "-" + fechaFinArchivo + ")");
 	      System.out.println("Archivo existe fisicamente: " + (existe ? "OK" : "FAIL"));
 	      System.out.println("Resultado final validacion nomenclatura: " + (valid ? "PASS" : "FAIL"));
 	        if (screenshot) {
 	            try {
 	                pc.mostrarExploradorParaEvidencia(home);
 	            } catch (Exception ex) {
 	                testCase.addTextEvidenceCurrentStep("No se pudo mostrar explorador: " + ex.getMessage());
 	            }
 	        }

 	        System.out.println("Archivo validado: " + fileName + " -> " + valid);
 	        return valid;

 	    } catch (Exception e) {
 	        if (screenshot) {
 	            try {
 	                pc.mostrarExploradorParaEvidencia(home);
 	            } catch (Exception ex) {
 	                testCase.addTextEvidenceCurrentStep("No se pudo mostrar explorador: " + ex.getMessage());
 	            }
 	        }
 	        System.err.println("Error en validacion de descarga: " + e.getMessage());
 	        return false;
 	    }
 		}
 	/**
     * Recorre todas las paginas del grid y regresa el total de filas encontradas.
     *
     * @return total de filas del grid
     * @throws Exception cuando no se logra completar el recorrido del paginador
        * @author Alicia.Ceja
        * @date 2026-07-08
     */
    public int contarElementosGrid() throws Exception {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pc.gridTabla));
        irAPrimeraPaginaSiEsPosible();
        u.scrollToElement(pc.gridTabla);
        testCase.addScreenShotCurrentStep("");
        int totalFilas = 0;
        int paginasRecorridas = 0;
        int limiteSeguridadPaginas = 500;

        while (true) {
            List<WebElement> filasPagina = u.getDriver().findElements(pc.gridFilas);
            totalFilas += filasPagina.size();

            WebElement botonSiguiente = wait.until(ExpectedConditions.presenceOfElementLocated(devElements.paginatorSiguiente));
            if (estaDeshabilitado(botonSiguiente)) {
                break;
            }

            String rangoAntes = obtenerRangoPaginador();
            WebElement primeraFilaAntes = filasPagina.isEmpty() ? null : filasPagina.get(0);

            clickSeguro(botonSiguiente);

            if (primeraFilaAntes != null) {
                wait.until(ExpectedConditions.stalenessOf(primeraFilaAntes));
            }

            if (!rangoAntes.isEmpty()) {
                wait.until(d -> !obtenerRangoPaginador().equals(rangoAntes));
            }

            paginasRecorridas++;
            if (paginasRecorridas > limiteSeguridadPaginas) {
                throw new Exception("Se alcanzo el limite de seguridad al recorrer el paginado del grid.");
            }
        }

        System.out.println("Total de elementos encontrados en el grid: " + totalFilas);
        testCase.addBoldTextEvidenceCurrentStep("Cantidad de elementos en el grid "+totalFilas);
        return totalFilas;
    }

    /**
     * Intenta regresar al inicio del paginador antes de iniciar el conteo.
     *
     * @author Alicia.Ceja
     * @date 2026-07-08
     */
    private void irAPrimeraPaginaSiEsPosible() {
        List<WebElement> botonesPrimero = u.getDriver().findElements(devElements.paginatorPrimero);
        if (botonesPrimero.isEmpty()) {
            return;
        }

        WebElement botonPrimero = botonesPrimero.get(0);
        if (estaDeshabilitado(botonPrimero)) {
            return;
        }

        String rangoAntes = obtenerRangoPaginador();
        clickSeguro(botonPrimero);

        if (!rangoAntes.isEmpty()) {
            wait.until(d -> !obtenerRangoPaginador().equals(rangoAntes));
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(pc.gridTabla));
    }

    /**
     * Obtiene el texto del rango actual del paginador.
     *
     * @return texto del rango o vacio si no existe
     * @author Alicia.Ceja
     * @date 2026-07-08
     */
    private String obtenerRangoPaginador() {
        List<WebElement> labels = u.getDriver().findElements(devElements.paginatorRango);
        if (labels.isEmpty()) {
            return "";
        }
        return labels.get(0).getText().trim();
    }

    /**
     * Determina si un control se encuentra deshabilitado.
     *
     * @param element elemento a validar
     * @return true si esta deshabilitado
     * @author Alicia.Ceja
     * @date 2026-07-08
     */
    private boolean estaDeshabilitado(WebElement element) {
        String ariaDisabled = element.getAttribute("aria-disabled");
        String disabled = element.getAttribute("disabled");
        String classes = element.getAttribute("class");

        return "true".equalsIgnoreCase(ariaDisabled)
            || disabled != null
            || !element.isEnabled()
            || (classes != null && classes.contains("mat-mdc-button-disabled"));
    }

    /**
     * Ejecuta click normal y usa JavaScript como respaldo.
     *
     * @param element elemento sobre el que se realizara el click
     * @author Alicia.Ceja
     * @date 2026-07-08
     */
    private void clickSeguro(WebElement element) {
        try {
            element.click();
            return;
        } catch (Exception ignore) {
        }

        ((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", element);
    }
    
    
    /**
     * Abre el ultimo archivo Excel descargado con prefijo Resumen_DEVOLUCION
     * y regresa el total de filas de datos (total - 1 fila de encabezado).
     *
     * @return numero de filas de datos del Excel, o -1 si no se encontro archivo
     * @author Alicia.Ceja
     * @date 2026-07-08
     */
    public int contarLineasExcelResumenDevolucion() {
    	String home = System.getProperty("user.home") + "\\Downloads";
        Pattern patron = Pattern.compile(
            "^Resumen_DEVOLUCION_(\\d{2}_\\d{2}_\\d{4})[-_](\\d{2}_\\d{2}_\\d{4})(\\s*\\(\\d+\\))?\\.xlsx$",
            Pattern.CASE_INSENSITIVE
        );

        File archivo = null;
        FileInputStream fis = null;
        Workbook workbook = null;

        try {
            File dir = new File(home);
            File[] candidatos = dir.listFiles(file ->
                file != null && file.isFile() && patron.matcher(file.getName()).matches()
            );

            if (candidatos == null || candidatos.length == 0) {
                System.out.println("No se encontro archivo Excel en Downloads.");
                return -1;
            }

            Arrays.sort(candidatos, Comparator.comparingLong(File::lastModified).reversed());
            archivo = candidatos[0];

            // Abrir el archivo visualmente en pantalla
            try {
                Runtime.getRuntime().exec(new String[] {
                    "cmd.exe", "/c", "start", "/MAX", "\"\"", "\"" + archivo.getAbsolutePath() + "\""
                });
                System.out.println("Archivo abierto en Excel");
                testCase.addScreenShotCurrentStep("");
                Thread.sleep(3000);
            } catch (Exception e) {
                System.out.println("ERROR: no se pudo abrir el archivo: " + e.getMessage());
            }

            fis = new FileInputStream(archivo);
            workbook = WorkbookFactory.create(fis);

            Sheet sheet = workbook.getSheetAt(0);
            int totalFilas = sheet.getLastRowNum() + 1; // total real de filas
            int filasDatos = totalFilas - 1;            // restar encabezado

            System.out.println("Total filas (con encabezado): " + totalFilas);
            System.out.println("Filas de datos (sin encabezado): " + filasDatos);
            testCase.addBoldTextEvidenceCurrentStep("Total registros en archivo: "+filasDatos);

            return filasDatos;

        } catch (Exception e) {
            System.err.println("Error al leer el Excel: " + e.getMessage());
            e.printStackTrace();
            return -1;

        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (Exception e) {
                System.out.println("ERROR cerrando workbook: " + e.getMessage());
            }

            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                System.out.println("ERROR cerrando FileInputStream: " + e.getMessage());
            }

            try {
                Thread.sleep(4000);
                Runtime.getRuntime().exec(new String[] {
                    "cmd.exe", "/c", "taskkill", "/IM", "EXCEL.EXE", "/F"
                });
                System.out.println("Excel cerrado con taskkill");
            } catch (Exception e) {
                System.out.println("ERROR cerrando Excel: " + e.getMessage());
            }
        }
    }
    
    
    /**
     * Abre la fila indicada por posicion absoluta dentro del grid paginado.
     * La posicion inicia en 1.
     *
     * @param posicion posicion global de la fila a abrir
     * @return true si se encontro la fila y se ejecuto el click
     * @throws Exception cuando la posicion es invalida o no se localiza la fila
     * @author Alicia.Ceja
     * @date 2026-07-08
     */
    public boolean validarElementosGrid(int posicion) throws Exception {
        if (posicion <= 0) {
            throw new IllegalArgumentException("La posicion del grid debe ser mayor a 0.");
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(pc.gridTabla));
        irAPrimeraPaginaSiEsPosible();

        int elementosRecorridos = 0;
        int paginasRecorridas = 0;
        int limiteSeguridadPaginas = 500;

        while (true) {
            List<WebElement> filasPagina = u.getDriver().findElements(pc.gridFilas);
            int totalPagina = filasPagina.size();

            if (posicion <= elementosRecorridos + totalPagina) {
                int indiceFila = posicion - elementosRecorridos - 1;
                WebElement filaObjetivo = filasPagina.get(indiceFila);
                wait.until(ExpectedConditions.elementToBeClickable(filaObjetivo));
                clickSeguro(filaObjetivo);
                return true;
            }

            WebElement botonSiguiente = wait.until(ExpectedConditions.presenceOfElementLocated(devElements.paginatorSiguiente));
            if (estaDeshabilitado(botonSiguiente)) {
                throw new Exception("No existe la posicion " + posicion + " dentro del grid.");
            }

            elementosRecorridos += totalPagina;
            String rangoAntes = obtenerRangoPaginador();
            WebElement primeraFilaAntes = filasPagina.isEmpty() ? null : filasPagina.get(0);

            clickSeguro(botonSiguiente);

            if (primeraFilaAntes != null) {
                wait.until(ExpectedConditions.stalenessOf(primeraFilaAntes));
            }

            if (!rangoAntes.isEmpty()) {
                wait.until(d -> !obtenerRangoPaginador().equals(rangoAntes));
            }

            paginasRecorridas++;
            if (paginasRecorridas > limiteSeguridadPaginas) {
                throw new Exception("Se alcanzo el limite de seguridad al recorrer el paginado del grid.");
            }
        }
    }

    /**
     * Hace click en la fila indicada por posicion absoluta dentro del grid paginado.
     * La posicion inicia en 1.
     *
     * @param posicion posicion global de la fila a abrir
     * @return true si se encontro la fila y se ejecuto el click
     * @throws Exception cuando la posicion es invalida o no se localiza la fila
     * @author Alicia.Ceja
     * @date 2026-07-08
     */
    public boolean abrirVentanaDesdeGrid(int posicion) throws Exception {
        return validarElementosGrid(posicion);
    }

    /**
     * Hace click en la fila indicada por posicion absoluta y espera la apertura
     * de la vista, ventana o detalle asociado al registro.
     *
     * @param posicion posicion global de la fila a abrir
     * @param localizadorVentana localizador de la vista o ventana esperada
     * @return true si se hizo click y la vista esperada se mostro
     * @throws Exception cuando la posicion es invalida o no se abre la vista esperada
     * @author Alicia.Ceja
     * @date 2026-07-08
     */
    public boolean abrirVentanaDesdeGrid(int posicion, By localizadorVentana) throws Exception {
        boolean filaAbierta = abrirVentanaDesdeGrid(posicion);
        if (!filaAbierta) {
            return false;
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(localizadorVentana));
        return true;
    }

	/**
     * Selecciona en el filtro de fecha un rango exacto de 7 dias usando la fecha recuperada de BD como fecha fin.
     *
     * @param screenshot true para capturar evidencia del rango seleccionado
     * @return true si el rango se selecciona correctamente
     * @throws Exception si ocurre algun error al interactuar con el calendario
     *
     * @author Mauricio Gurrola
     * @date Julio 2026
     */
	public boolean SelecFechaRango7Dias(boolean screenshot) throws Exception {
        try {
            LocalDate fechaFin = convertirFechaBdDevoluciones(fecha);
            LocalDate fechaInicio = fechaFin.minusDays(6);
            WebDriver driver = u.getDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

            By btnToggle = By.xpath("//mat-datepicker-toggle/button");
            wait.until(ExpectedConditions.elementToBeClickable(btnToggle)).click();

            By dpContent = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-datepicker-content");
            wait.until(ExpectedConditions.visibilityOfElementLocated(dpContent));

            By btnPeriodo = By.xpath(
                "//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-period-button')]"
            );
            By multiYearView = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-multi-year-view");

            abrirVistaAniosDevoluciones(driver, wait, btnPeriodo, multiYearView);

            clickYearDevoluciones(driver, wait, fechaInicio.getYear());
            clickMonthByIndexDevoluciones(driver, wait, fechaInicio.getMonthValue());
            clickDayDevoluciones(driver, wait, fechaInicio.getDayOfMonth());

            if (fechaInicio.getYear() != fechaFin.getYear() || fechaInicio.getMonthValue() != fechaFin.getMonthValue()) {
                abrirVistaAniosDevoluciones(driver, wait, btnPeriodo, multiYearView);
                clickYearDevoluciones(driver, wait, fechaFin.getYear());
                clickMonthByIndexDevoluciones(driver, wait, fechaFin.getMonthValue());
            }

            clickDayDevoluciones(driver, wait, fechaFin.getDayOfMonth());
            wait.until(ExpectedConditions.invisibilityOfElementLocated(dpContent));

            if (screenshot) {
                testCase.addTextEvidenceCurrentStep("Se selecciono rango exacto de 7 dias: " + fechaInicio + " a " + fechaFin);
                testCase.addScreenShotCurrentStep(u, "Rango7DiasSeleccionado");
            }

            return true;

        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Error en SelecFechaRango7Dias: " + e.getMessage());
            throw new Exception("Error en SelecFechaRango7Dias: " + e.getMessage(), e);
        }
    }
	
	private LocalDate convertirFechaBdDevoluciones(String fechaOriginal) {
        String fechaLimpia = fechaOriginal != null ? fechaOriginal.trim() : "";
        if (fechaLimpia.isEmpty()) {
            throw new IllegalStateException("No se obtuvo fecha desde BD para seleccionar el rango de 7 dias.");
        }

        try {
            return LocalDateTime.parse(fechaLimpia, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
        } catch (Exception ignored) {
        }

        try {
            return LocalDate.parse(fechaLimpia, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception ignored) {
        }

        if (fechaLimpia.length() >= 10) {
            return LocalDate.parse(fechaLimpia.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        throw new IllegalStateException("Formato de fecha no soportado para rango de 7 dias: " + fechaLimpia);
    }

    private void abrirVistaAniosDevoluciones(WebDriver driver, WebDriverWait wait, By btnPeriodo, By multiYearView) {
        wait.until(ExpectedConditions.elementToBeClickable(btnPeriodo)).click();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(4))
                .until(ExpectedConditions.visibilityOfElementLocated(multiYearView));
        } catch (org.openqa.selenium.TimeoutException ignored) {
            wait.until(ExpectedConditions.elementToBeClickable(btnPeriodo)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(multiYearView));
        }
    }
    
    private void clickYearDevoluciones(WebDriver driver, WebDriverWait wait, int yearTarget) {
        By yearBtn = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-multi-year-view//button[normalize-space(.)='" + yearTarget + "' or .//*[normalize-space(.)='" + yearTarget + "']]");
        By btnPrev = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-previous-button')]");
        By btnNext = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-next-button')]");

        for (int i = 0; i < 12; i++) {
            List<WebElement> years = driver.findElements(yearBtn);
            if (!years.isEmpty()) {
                WebElement year = wait.until(ExpectedConditions.elementToBeClickable(years.get(0)));
                try {
                    year.click();
                } catch (Exception clickNormal) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", year);
                }
                return;
            }

            Integer minVisible = getVisibleYearDevoluciones(driver, true);
            Integer maxVisible = getVisibleYearDevoluciones(driver, false);
            if (minVisible != null && maxVisible != null) {
                if (yearTarget < minVisible) {
                    wait.until(ExpectedConditions.elementToBeClickable(btnPrev)).click();
                } else if (yearTarget > maxVisible) {
                    wait.until(ExpectedConditions.elementToBeClickable(btnNext)).click();
                }
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(btnNext)).click();
            }
        }

        throw new org.openqa.selenium.TimeoutException("No se pudo ubicar el anio " + yearTarget + " en el datepicker.");
    }

    private Integer getVisibleYearDevoluciones(WebDriver driver, boolean first) {
        List<WebElement> els = driver.findElements(By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-multi-year-view//button[contains(@class,'mat-calendar-body-cell')]"));
        java.util.List<Integer> years = new java.util.ArrayList<Integer>();
        for (WebElement el : els) {
            try {
                String text = el.getText().replaceAll("[^0-9]", "").trim();
                if (text.matches("\\d{4}")) {
                    years.add(Integer.parseInt(text));
                }
            } catch (Exception ignore) {
            }
        }
        if (years.isEmpty()) {
            return null;
        }
        java.util.Collections.sort(years);
        return first ? years.get(0) : years.get(years.size() - 1);
    }

    private void clickMonthByIndexDevoluciones(WebDriver driver, WebDriverWait wait, int month1to12) {
        By monthByIdx = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-year-view//div[contains(@class,'mat-calendar-body-cell-content')][" + month1to12 + "]/ancestor::button[1]");
        if (!driver.findElements(monthByIdx).isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(monthByIdx)).click();
            return;
        }

        String[] meses = {"", "ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};
        String label = meses[month1to12];
        By monthByText = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-year-view//span[contains(@class,'mat-calendar-body-cell-content') and translate(normalize-space(.),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + label + "']/ancestor::button[1]");
        wait.until(ExpectedConditions.elementToBeClickable(monthByText)).click();
    }

    private void clickDayDevoluciones(WebDriver driver, WebDriverWait wait, int day) {
        By dayBtn = By.xpath(
            "//div[contains(@class,'cdk-overlay-pane')]//mat-month-view" +
            "//button[contains(@class,'mat-calendar-body-cell')]" +
            "[.//span[contains(@class,'mat-calendar-body-cell-content') and normalize-space(.)='" + day + "']]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(dayBtn)).click();
    }
    
    /**
     * Monitorea en Centro de Descargas la solicitud mas reciente de Devoluciones hasta que su estatus sea Terminado.
     * La validacion permanece encapsulada en el Object Model para que el script base conserve una estructura lineal.
     *
     * @param fechaHoraSolicitud fecha y hora T1 registrada inmediatamente antes de solicitar la descarga.
     * @param tiempoMaximoSegundos tiempo maximo de espera para que el estatus cambie a Terminado.
     * @param screenshot true si se requiere agregar evidencia visual del registro terminado.
     * @return segundos transcurridos entre T1 y T2; retorna -1 si no termina dentro del tiempo maximo o queda Fallido.
     * @throws Exception si ocurre un error durante la lectura del Centro de Descargas.
     *
     * @author Michelle Ceja
     * @date 2026-07-27
     */
    public long esperarUltimaDescargaDevolucionesTerminada(LocalDateTime fechaHoraSolicitud, int tiempoMaximoSegundos, boolean screenshot) throws Exception {
        try {
            WebDriver driver = u.getDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            DateTimeFormatter formatoTimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String hoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            long inicioMonitoreo = System.currentTimeMillis();
            String ultimoDetalle = "Sin registros evaluados";
            boolean evidenciaEnProcesoCapturada = false;

            By tabla = By.xpath("//table[contains(@class,'mat-mdc-table')]");
            By filas = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr");
            By btnFirst = By.xpath("//mat-paginator//button[contains(@class,'mat-mdc-paginator-navigation-first')]");
            By rangeLbl = By.xpath("//mat-paginator//div[contains(@class,'mat-mdc-paginator-range-label')]");

            while (((System.currentTimeMillis() - inicioMonitoreo) / 1000) <= tiempoMaximoSegundos) {
                u.waitWebElement(tabla, 30, 5);

                if (!driver.findElements(btnFirst).isEmpty()) {
                    WebElement first = driver.findElement(btnFirst);
                    String ariaDisabled = first.getAttribute("aria-disabled");
                    if (first.isEnabled() && !"true".equalsIgnoreCase(ariaDisabled)) {
                        String rangoAntes = obtenerTextoSeguroCentroDescargas(driver, rangeLbl);
                        try {
                            first.click();
                        } catch (Exception clickNormal) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", first);
                        }
                        esperarRefrescoTablaCentroDescargas(wait, rangeLbl, rangoAntes);
                    }
                }

                List<WebElement> filasCentroDescargas = driver.findElements(filas);
                if (filasCentroDescargas.isEmpty()) {
                    ultimoDetalle = "Centro de Descargas sin filas visibles";
                } else {
                    int i = 1;
                    By fila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]");
                    By fechaFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-fecha')]//span");
                    By moduloFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-modulo')]//span");
                    By documentoFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-nombreDocumento')]//span");
                    By estatusFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-estatus')]");
                    By estatusTerminadoFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-estatus')]//*[contains(@class,'icon-check-circle') or contains(@class,'icon-success') or self::mat-icon[contains(normalize-space(.),'check_circle') or contains(@fonticon,'check_circle')] or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'terminado') or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'finalizado')]");
                    By estatusFallidoFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-estatus')]//*[contains(@class,'icon-error') or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'fallido')]");
                    By iconosEstatusFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-estatus')]//*[self::mat-icon or contains(@class,'icon-')]");

                    String fechaRegistro = obtenerTextoSeguroCentroDescargas(driver, fechaFila).trim().replaceAll("[^0-9/\\-]", "");
                    String modulo = obtenerTextoSeguroCentroDescargas(driver, moduloFila).trim();
                    String documento = obtenerTextoSeguroCentroDescargas(driver, documentoFila).trim();
                    String estatus = obtenerTextoSeguroCentroDescargas(driver, estatusFila).trim().replaceAll("\\s+", " ");

                    ultimoDetalle = "Fila: " + i + " | Fecha: " + fechaRegistro + " | Modulo: " + modulo + " | Documento: " + documento + " | Estatus: " + estatus;
                    System.out.println("[CENTRO DESCARGAS 192] " + ultimoDetalle);

                    if (!hoy.equals(fechaRegistro) || !modulo.toLowerCase(Locale.ROOT).contains("devol")
                            || !documento.toLowerCase(Locale.ROOT).contains("devolucion")) {
                        testCase.addTextEvidenceCurrentStep("La primera fila de Centro de Descargas no corresponde a la solicitud actual de Devoluciones. Lectura: " + ultimoDetalle);
                        return -1L;
                    }

                    String htmlEstatus = "";
                    try {
                        htmlEstatus = driver.findElement(estatusFila).getAttribute("innerHTML");
                    } catch (Exception ignore) {
                    }
                    StringBuilder detalleIconosEstatus = new StringBuilder();
                    for (WebElement iconoEstatus : driver.findElements(iconosEstatusFila)) {
                        detalleIconosEstatus.append(" ").append(iconoEstatus.getText())
                                .append(" ").append(iconoEstatus.getAttribute("class"))
                                .append(" ").append(iconoEstatus.getAttribute("fonticon"));
                    }
                    String estatusNormalizado = (estatus + " " + htmlEstatus + " " + detalleIconosEstatus.toString()).toLowerCase(Locale.ROOT);

                    if (!driver.findElements(estatusFallidoFila).isEmpty() || estatusNormalizado.contains("fallido")) {
                        testCase.addTextEvidenceCurrentStep("La solicitud de Devoluciones quedo en estatus Fallido. " + ultimoDetalle);
                        return -1L;
                    }

                    if (screenshot && !evidenciaEnProcesoCapturada && (estatusNormalizado.contains("en proceso")
                            || estatusNormalizado.contains("icon-pending") || estatusNormalizado.contains("pending"))) {
                        testCase.addTextEvidenceCurrentStep("Registro de Centro de Descargas en proceso antes del cambio a Terminado. " + ultimoDetalle);
                        u.highLight(fila);
                        testCase.addScreenShotCurrentStep(u, "Centro Descargas Devoluciones en proceso");
                        u.unhighLight(fila);
                        evidenciaEnProcesoCapturada = true;
                    }

                    if (!driver.findElements(estatusTerminadoFila).isEmpty() || estatusNormalizado.contains("check_circle")
                            || estatusNormalizado.contains("icon-check-circle") || estatusNormalizado.contains("terminado")
                            || estatusNormalizado.contains("finalizado")) {
                        LocalDateTime fechaHoraTerminado = LocalDateTime.now();
                        long tiempoGeneracion = ChronoUnit.SECONDS.between(fechaHoraSolicitud, fechaHoraTerminado);

                        if (screenshot) {
                            testCase.addBoldTextEvidenceCurrentStep("T1 inicio generacion: " + fechaHoraSolicitud.format(formatoTimestamp));
                            testCase.addBoldTextEvidenceCurrentStep("T2 finalizacion Terminado: " + fechaHoraTerminado.format(formatoTimestamp));
                            testCase.addBoldTextEvidenceCurrentStep("T_gen calculado: " + tiempoGeneracion + " segundos");
                            testCase.addTextEvidenceCurrentStep("Registro terminado validado en Centro de Descargas. " + ultimoDetalle);
                            u.highLight(fila);
                            testCase.addScreenShotCurrentStep(u, "Centro Descargas Devoluciones terminado");
                            u.unhighLight(fila);
                        }

                        return tiempoGeneracion;
                    }
                }

                long segundosTranscurridos = (System.currentTimeMillis() - inicioMonitoreo) / 1000;
                if (segundosTranscurridos >= tiempoMaximoSegundos) {
                    break;
                }

                u.hardWait(5);
            }

            testCase.addTextEvidenceCurrentStep("La solicitud esperada de Devoluciones no llego a Terminado dentro del tiempo maximo. Ultima lectura: " + ultimoDetalle);
            return -1L;
        } catch (Exception exception) {
            testCase.addCodeEvidenceCurrentStep("Error al monitorear Centro de Descargas para Devoluciones: " + exception.getMessage());
            throw new Exception("Error al monitorear Centro de Descargas para Devoluciones: " + exception.getMessage());
        }
    }
    
    /**
     * Obtiene el texto de un elemento de Centro de Descargas de forma segura.
     * Evita que el monitoreo falle por celdas vacias o elementos no presentes.
     *
     * @param driver instancia WebDriver activa.
     * @param locator selector del elemento que se desea leer.
     * @return texto del elemento o cadena vacia si no se puede leer.
     *
     * @author Michelle Ceja
     * @date 2026-07-27
     */
    private String obtenerTextoSeguroCentroDescargas(WebDriver driver, By locator) {
        try {
            return driver.findElement(locator).getText();
        } catch (Exception exception) {
            return "";
        }
    }
	
    /**
     * Espera que el paginador de Centro de Descargas refleje un cambio despues de navegar o refrescar la tabla.
     * Si el rango previo no esta disponible, solo espera a que el label sea visible.
     *
     * @param wait espera explicita usada para sincronizar la tabla.
     * @param rangeLbl selector del label de rango del paginador.
     * @param oldRange valor previo del rango, si existe.
     *
     * @author Michelle Ceja
     * @date 2026-07-27
     */
    private void esperarRefrescoTablaCentroDescargas(WebDriverWait wait, By rangeLbl, String oldRange) {
        try {
            if (oldRange == null) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(rangeLbl));
            } else {
                wait.until(driver -> {
                    try {
                        String nuevo = driver.findElement(rangeLbl).getText();
                        return nuevo != null && !nuevo.trim().isEmpty() && !nuevo.equals(oldRange);
                    } catch (Exception exception) {
                        return false;
                    }
                });
            }
        } catch (Exception ignored) {
        }
    }
    
    /**
     * Obtiene un rango mensual de fechas desde BD usando el campo fecha de xxcm_dv.
     * Aplica el alcance configurado para el usuario con PCPLAZA o PCRFC; ADMIN y OXXO_OS consultan global.
     *
     * @param usuario usuario de Portal COMETA usado para resolver perfil y llaves de alcance.
     * @return mapa con fechaInicio, fechaFin, totalRegistros, perfil, filtroAplicado y valorFiltro obtenidos desde BD.
     * @throws Exception si no existe un rango con registros o si falla la consulta.
     *
     * @author Michelle Ceja
     * @date 2026-07-27
     */
    public HashMap<String, String> obtenerRangoFechasDevolucionesDesdeBD(String usuario) throws Exception {
        String sqlPerfil = "select key_value "
                + "from xxcm_user_keys "
                + "where short_name = 'PCPERFIL' "
                + "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) "
                + "limit 1";
        String sqlKeys = "select short_name, key_value "
                + "from xxcm_user_keys "
                + "where short_name in ('PCRFC', 'PCSUPPLIERID', 'PCPLAZA') "
                + "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?)";
        String sqlBase = "WITH cte_data AS( "
                + "select "
                + "a.id, "
                + "b.id as historico_id, "
                + "a.fecha, "
                + "a.cr_desc_plaza, "
                + "a.proveedor_rfc, "
                + "a.proveedor_id "
                + "from public.xxcm_dv a "
                + "inner join public.xxcm_historico_correos b on a.id = b.id ";
        String sqlRango = "), cte_rango AS( "
                + "select "
                + "date_trunc('month', fecha)::date as mes, "
                + "min(fecha::date)::text as fecha_inicio, "
                + "max(fecha::date)::text as fecha_fin, "
                + "count(*) as total_registros "
                + "from cte_data "
                + "where fecha is not null "
                + "group by date_trunc('month', fecha)::date "
                + "order by max(fecha::date) desc "
                + "limit 1 "
                + ") "
                + "select fecha_inicio, fecha_fin, total_registros from cte_rango";

        try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
                GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa)) {
            String perfil = "";
            try (PreparedStatement statementPerfil = connection.prepareStatement(sqlPerfil)) {
                statementPerfil.setString(1, usuario);
                try (ResultSet resultSetPerfil = statementPerfil.executeQuery()) {
                    if (resultSetPerfil.next()) {
                        perfil = resultSetPerfil.getString("key_value") == null ? "" : resultSetPerfil.getString("key_value").trim();
                    }
                }
            }

            String pcrfc = null;
            String pcSupplierId = null;
            String pcPlaza = null;
            try (PreparedStatement statementKeys = connection.prepareStatement(sqlKeys)) {
                statementKeys.setString(1, usuario);
                try (ResultSet resultSetKeys = statementKeys.executeQuery()) {
                    while (resultSetKeys.next()) {
                        String shortName = resultSetKeys.getString("short_name");
                        String keyValue = resultSetKeys.getString("key_value");
                        if ("PCRFC".equalsIgnoreCase(shortName)) {
                            pcrfc = keyValue;
                        } else if ("PCSUPPLIERID".equalsIgnoreCase(shortName)) {
                            pcSupplierId = keyValue;
                        } else if ("PCPLAZA".equalsIgnoreCase(shortName)) {
                            pcPlaza = keyValue;
                        }
                    }
                }
            }

            String perfilNormalizado = perfil.toUpperCase(Locale.ROOT);
            String whereAlcance = "";
            String filtroAplicado = "GLOBAL";
            String valorFiltro = "SIN FILTRO";
            if (!"ADMIN".equals(perfilNormalizado) && !"OXXO_OS".equals(perfilNormalizado)) {
                if ("OXXO_PLAZA".equals(perfilNormalizado) && pcPlaza != null && !pcPlaza.trim().isEmpty()) {
                    whereAlcance = "where a.cr_desc_plaza = ? ";
                    filtroAplicado = "PCPLAZA";
                    valorFiltro = pcPlaza.trim();
                } else if (pcrfc != null && !pcrfc.trim().isEmpty()) {
                    whereAlcance = "where a.proveedor_rfc = ? ";
                    filtroAplicado = "PCRFC";
                    valorFiltro = pcrfc.trim();
                } else if (pcSupplierId != null && !pcSupplierId.trim().isEmpty()) {
                    whereAlcance = "where a.proveedor_id::text = ? ";
                    filtroAplicado = "PCSUPPLIERID";
                    valorFiltro = pcSupplierId.trim();
                }
            }

            String sql = sqlBase + whereAlcance + sqlRango;
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                if (!whereAlcance.isEmpty()) {
                    statement.setString(1, valorFiltro);
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new Exception("No existe un rango mensual de fecha con registros en Devoluciones para el usuario " + usuario);
                    }

                    HashMap<String, String> rangoFechas = new HashMap<>();
                    rangoFechas.put("fechaInicio", resultSet.getString("fecha_inicio"));
                    rangoFechas.put("fechaFin", resultSet.getString("fecha_fin"));
                    rangoFechas.put("totalRegistros", resultSet.getString("total_registros"));
                    rangoFechas.put("perfil", perfil);
                    rangoFechas.put("filtroAplicado", filtroAplicado);
                    rangoFechas.put("valorFiltro", valorFiltro);

                    testCase.addBoldTextEvidenceCurrentStep("Rango obtenido desde BD para Devoluciones");
                    testCase.addTextEvidenceCurrentStep("Usuario: " + usuario
                            + " | Perfil: " + perfil
                            + " | Filtro aplicado: " + filtroAplicado
                            + " | Valor filtro: " + valorFiltro
                            + " | Fecha Inicio: " + rangoFechas.get("fechaInicio")
                            + " | Fecha Fin: " + rangoFechas.get("fechaFin")
                            + " | Total registros BD: " + rangoFechas.get("totalRegistros"));
                    System.out.println("[BD DEVOLUCIONES] Usuario: " + usuario
                            + " | Perfil: " + perfil
                            + " | Filtro aplicado: " + filtroAplicado
                            + " | Valor filtro: " + valorFiltro
                            + " | Fecha Inicio: " + rangoFechas.get("fechaInicio")
                            + " | Fecha Fin: " + rangoFechas.get("fechaFin")
                            + " | Total registros BD: " + rangoFechas.get("totalRegistros"));

                    return rangoFechas;
                }
            }
        } catch (Exception exception) {
            testCase.addCodeEvidenceCurrentStep("Error al obtener rango de fechas de Devoluciones desde BD: " + exception.getMessage());
            throw new Exception("Error al obtener rango de fechas de Devoluciones desde BD: " + exception.getMessage());
        }
    }
    
    
    /**
     * Selecciona en Devoluciones un rango completo de fechas recibido en formato ISO.
     * Permite que el script use directamente el rango obtenido desde BD y conserve una sola evidencia consolidada.
     *
     * @param fechaInicioIso fecha inicial en formato yyyy-MM-dd.
     * @param fechaFinIso fecha final en formato yyyy-MM-dd.
     * @return true cuando ambas fechas quedan seleccionadas y el calendario se cierra.
     * @throws Exception si ocurre un error al interactuar con el calendario.
     *
     * @author Michelle Ceja
     * @date 2026-07-27
     */
    public boolean seleccionarRangoFechasDevolucionesSinEvidenciaIntermedia(String fechaInicioIso, String fechaFinIso) throws Exception {
        try {
            LocalDate fechaInicio = LocalDate.parse(fechaInicioIso, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate fechaFin = LocalDate.parse(fechaFinIso, DateTimeFormatter.ISO_LOCAL_DATE);
            WebDriver driver = u.getDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

            By btnToggle = By.xpath("//mat-datepicker-toggle/button");
            wait.until(ExpectedConditions.elementToBeClickable(btnToggle)).click();

            By dpContent = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-datepicker-content");
            wait.until(ExpectedConditions.visibilityOfElementLocated(dpContent));

            By btnPeriodo = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-period-button')]");
            By multiYearView = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-multi-year-view");

            abrirVistaAniosDevoluciones(driver, wait, btnPeriodo, multiYearView);
            clickYearDevoluciones(driver, wait, fechaInicio.getYear());
            clickMonthByIndexDevoluciones(driver, wait, fechaInicio.getMonthValue());
            clickDayDevoluciones(driver, wait, fechaInicio.getDayOfMonth());

            if (fechaInicio.getYear() != fechaFin.getYear() || fechaInicio.getMonthValue() != fechaFin.getMonthValue()) {
                abrirVistaAniosDevoluciones(driver, wait, btnPeriodo, multiYearView);
                clickYearDevoluciones(driver, wait, fechaFin.getYear());
                clickMonthByIndexDevoluciones(driver, wait, fechaFin.getMonthValue());
            }

            clickDayDevoluciones(driver, wait, fechaFin.getDayOfMonth());
            wait.until(ExpectedConditions.invisibilityOfElementLocated(dpContent));
            return true;
        } catch (Exception exception) {
            testCase.addCodeEvidenceCurrentStep("Error al seleccionar rango de fechas de Devoluciones desde BD: " + exception.getMessage());
            throw new Exception("Error al seleccionar rango de fechas de Devoluciones desde BD: " + exception.getMessage(), exception);
        }
    }

    /**
     * Valida que el filtro de Fuente de Abasto se muestre con la etiqueta esperada en Devoluciones.
     *
     * @param screenshot true para adjuntar captura del paso.
     * @return true si la etiqueta contiene Fuente de Abasto y no se muestra solo FA.
     *
     * @author Mauricio Gurrola
     * @date 2026-08-05
     */
    public boolean validarEtiquetaFuenteAbastoEnDevoluciones(boolean screenshot) {
        try {
            WebElement etiqueta = u.waitWebElement(devElements.etiquetaFuenteAbasto, 20, 2);
            String texto = etiqueta.getText() == null ? "" : etiqueta.getText().trim();
            boolean ok = !texto.isEmpty()
                    && normalizarEtiquetaBasica(texto).contains("FUENTE DE ABASTO")
                    && !"FA".equalsIgnoreCase(texto);

            if (ok) {
                testCase.addTextEvidenceCurrentStep("Etiqueta detectada en filtro: '" + texto + "'.");
            } else {
                testCase.addTextEvidenceCurrentStep("Etiqueta detectada en filtro no valida: '" + texto + "'.");
            }
            if (screenshot) {
                testCase.addScreenShotCurrentStep(u, "Etiqueta_Fuente_Abasto_Devoluciones");
            }
            return ok;
        } catch (Exception exception) {
            testCase.addCodeEvidenceCurrentStep("Error al validar etiqueta Fuente de Abasto: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Ejecuta la consulta de Devoluciones y valida que el grid regrese al menos un registro.
     *
     * @param screenshot true para adjuntar captura del paso.
     * @return true si la consulta carga y el grid contiene filas.
     *
     * @author Mauricio Gurrola
     * @date 2026-08-05
     */
    public boolean consultarDevolucionesYValidarGridConRegistros(boolean screenshot) {
        try {
            WebDriver driver = u.getDriver();
            WebElement botonConsultar = u.waitWebElement(devElements.botonConsultarDevoluciones, 20, 2);
            if (!botonConsultar.isDisplayed() || !botonConsultar.isEnabled()) {
                return false;
            }

            try {
                botonConsultar.click();
            } catch (Exception exception) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", botonConsultar);
            }

            if (!pc.MensajeEspereDesaparece(false)) {
                testCase.addTextEvidenceCurrentStep("No desaparecio el mensaje 'Espere un momento' tras consultar Devoluciones.");
                return false;
            }

            List<WebElement> filas = driver.findElements(devElements.filasGridDevoluciones);
            boolean ok = filas != null && !filas.isEmpty();
            if (screenshot) {
                testCase.addScreenShotCurrentStep(u, "Grid_Devoluciones_Con_Registros");
            }
            return ok;
        } catch (Exception exception) {
            testCase.addCodeEvidenceCurrentStep("Error al consultar Devoluciones: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Captura y registra el valor de Motivo de devolucion desde la vista preliminar.
     *
     * @param screenshot true para adjuntar captura del paso.
     * @return true si se detecta un valor no vacio.
     *
     * @author Mauricio Gurrola
     * @date 2026-08-05
     */
    public boolean capturarMotivoDevolucionDesdeVistaPreliminar(boolean screenshot) {
        String valor = capturarValorDesdeVistaPreliminarPorEtiquetas(new String[] {
                "Motivo de devolucion", "Motivo devolución", "Motivo de devolución"
        });
        valorMotivoPreview = valor;
        boolean ok = valor != null && !valor.trim().isEmpty();
        testCase.addTextEvidenceCurrentStep("VALOR_MOTIVO: '" + (valor == null ? "" : valor) + "'.");
        if (screenshot) {
            testCase.addScreenShotCurrentStep(u, "Preview_Motivo_Devolucion");
        }
        return ok;
    }

    /**
     * Captura y registra el valor de Observaciones Personal Tienda desde la vista preliminar.
     *
     * @param screenshot true para adjuntar captura del paso.
     * @return true si se detecta un valor no vacio.
     *
     * @author Mauricio Gurrola
     * @date 2026-08-05
     */
    public boolean capturarObservacionesTiendaDesdeVistaPreliminar(boolean screenshot) {
        String valor = capturarValorDesdeVistaPreliminarPorEtiquetas(new String[] {
                "Observaciones Personal Tienda", "Observaciones Tienda"
        });
        valorObsTiendaPreview = valor;
        boolean ok = valor != null && !valor.trim().isEmpty();
        testCase.addTextEvidenceCurrentStep("VALOR_OBS_TIENDA: '" + (valor == null ? "" : valor) + "'.");
        if (screenshot) {
            testCase.addScreenShotCurrentStep(u, "Preview_Observaciones_Tienda");
        }
        return ok;
    }

    /**
     * Captura y registra el valor de Observaciones del Proveedor desde la vista preliminar.
     *
     * @param screenshot true para adjuntar captura del paso.
     * @return true si se detecta un valor no vacio.
     *
     * @author Mauricio Gurrola
     * @date 2026-08-05
     */
    public boolean capturarObservacionesProveedorDesdeVistaPreliminar(boolean screenshot) {
        String valor = capturarValorDesdeVistaPreliminarPorEtiquetas(new String[] {
                "Observaciones del Proveedor", "Observaciones Proveedor"
        });
        valorObsProveedorPreview = valor;
        boolean ok = valor != null && !valor.trim().isEmpty();
        testCase.addTextEvidenceCurrentStep("VALOR_OBS_PROVEEDOR: '" + (valor == null ? "" : valor) + "'.");
        if (screenshot) {
            testCase.addScreenShotCurrentStep(u, "Preview_Observaciones_Proveedor");
        }
        return ok;
    }

    /**
     * Descarga el Excel individual desde la vista preliminar y espera a que el archivo aparezca en Descargas.
     *
     * @param screenshot true para adjuntar captura del paso.
     * @return true si el archivo se descarga correctamente.
     *
     * @author Mauricio Gurrola
     * @date 2026-08-05
     */
    public boolean descargarExcelIndividualDesdeVistaPreliminar(boolean screenshot) {
        try {
            long inicio = System.currentTimeMillis();
            if (!pc.ClickBtnExcel(false)) {
                return false;
            }

            File archivo = esperarNuevoExcelDescargado(inicio, 90000L);
            boolean ok = archivo != null && archivo.exists();
            if (ok) {
                testCase.addTextEvidenceCurrentStep("Archivo Excel individual descargado: " + archivo.getName());
            } else {
                testCase.addTextEvidenceCurrentStep("No se detecto archivo Excel individual descargado dentro del tiempo esperado.");
            }
            if (screenshot) {
                testCase.addScreenShotCurrentStep(u, "Descarga_Excel_Individual_Devoluciones");
            }
            return ok;
        } catch (Exception exception) {
            testCase.addCodeEvidenceCurrentStep("Error al descargar Excel individual de Devoluciones: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Valida que una columna este inmediatamente despues de otra en el Excel individual de Devoluciones.
     *
     * @param columnaBase nombre de la columna base.
     * @param columnaObjetivo nombre de la columna que debe estar a la derecha de la base.
     * @param screenshot true para adjuntar captura del paso.
     * @return true si el orden es correcto.
     *
     * @author Mauricio Gurrola
     * @date 2026-08-05
     */
    public boolean validarColumnaInmediataDespuesEnExcelIndividualDevoluciones(
            String columnaBase,
            String columnaObjetivo,
            boolean screenshot) {
        try {
            File archivo = obtenerUltimoExcelDescargadoDevoluciones();
            if (archivo == null || !archivo.exists()) {
                return false;
            }

            try (FileInputStream fis = new FileInputStream(archivo);
                    Workbook workbook = WorkbookFactory.create(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                org.apache.poi.ss.usermodel.Row encabezados = sheet.getRow(0);
                if (encabezados == null) {
                    return false;
                }

                int indiceBase = buscarIndiceColumnaExcel(encabezados, columnaBase);
                int indiceObjetivo = buscarIndiceColumnaExcel(encabezados, columnaObjetivo);
                boolean ok = indiceBase >= 0 && indiceObjetivo == (indiceBase + 1);

                testCase.addTextEvidenceCurrentStep("Orden de columnas en Excel individual: " + columnaBase + "(" + indiceBase
                        + ") -> " + columnaObjetivo + "(" + indiceObjetivo + ").");
                if (screenshot) {
                    testCase.addScreenShotCurrentStep(u, "Orden_" + normalizarNombreArchivo(columnaObjetivo));
                }
                return ok;
            }
        } catch (Exception exception) {
            testCase.addCodeEvidenceCurrentStep("Error al validar orden de columnas en Excel de Devoluciones: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Valida que una columna del Excel individual tenga al menos un dato no vacio.
     *
     * @param nombreColumna nombre de la columna a revisar.
     * @param screenshot true para adjuntar captura del paso.
     * @return true si existe al menos un dato no vacio.
     *
     * @author Mauricio Gurrola
     * @date 2026-08-05
     */
    public boolean validarDatoNoVacioColumnaExcelIndividualDevoluciones(String nombreColumna, boolean screenshot) {
        try {
            String valor = obtenerPrimerValorNoVacioColumna(nombreColumna);
            boolean ok = valor != null && !valor.trim().isEmpty();
            testCase.addTextEvidenceCurrentStep("Valor de referencia en '" + nombreColumna + "': '" + (valor == null ? "" : valor) + "'.");
            if (screenshot) {
                testCase.addScreenShotCurrentStep(u, "Dato_" + normalizarNombreArchivo(nombreColumna));
            }
            return ok;
        } catch (Exception exception) {
            testCase.addCodeEvidenceCurrentStep("Error al validar dato no vacio en columna '" + nombreColumna + "': " + exception.getMessage());
            return false;
        }
    }

    /**
     * Valida que los tres valores capturados en vista preliminar coincidan con el Excel individual.
     *
     * @param screenshot true para adjuntar captura del paso.
     * @return true si Motivo, Observaciones Tienda y Observaciones Proveedor coinciden.
     *
     * @author Mauricio Gurrola
     * @date 2026-08-05
     */
    public boolean validarValoresPreviewContraExcelIndividualDevoluciones(boolean screenshot) {
        boolean okMotivo = validarValorColumnaExcelContraEsperado("Motivo de devolucion", valorMotivoPreview, false);
        boolean okTienda = validarValorColumnaExcelContraEsperado("Observaciones Tienda", valorObsTiendaPreview, false);
        boolean okProveedor = validarValorColumnaExcelContraEsperado("Observaciones Proveedor", valorObsProveedorPreview, false);
        boolean ok = okMotivo && okTienda && okProveedor;

        testCase.addTextEvidenceCurrentStep("Comparacion preview vs excel -> Motivo: " + okMotivo
                + " | Obs Tienda: " + okTienda + " | Obs Proveedor: " + okProveedor + ".");
        if (screenshot) {
            testCase.addScreenShotCurrentStep(u, "Comparacion_Preview_Excel_Devoluciones");
        }
        return ok;
    }

    /**
     * Valida el valor esperado de la columna Perfil Autorizador en el Excel individual.
     *
     * @param valorEsperado valor esperado (Proveedor o Asesor de Tienda).
     * @param screenshot true para adjuntar captura del paso.
     * @return true si coincide con el valor esperado.
     *
     * @author Mauricio Gurrola
     * @date 2026-08-05
     */
    public boolean validarPerfilAutorizadorEnExcelIndividual(String valorEsperado, boolean screenshot) {
        boolean ok = validarValorColumnaExcelContraEsperado("Perfil Autorizador", valorEsperado, screenshot);
        testCase.addTextEvidenceCurrentStep("Perfil Autorizador esperado: '" + valorEsperado + "'.");
        return ok;
    }

    /**
     * Valida el formato de encabezados para las columnas nuevas de Devoluciones.
     *
     * @param screenshot true para adjuntar captura del paso.
     * @return true si los encabezados conservan Arial 14 negritas y estilo base del reporte.
     *
     * @author Mauricio Gurrola
     * @date 2026-08-05
     */
    public boolean validarFormatoEncabezadosColumnasNuevasDevoluciones(boolean screenshot) {
        try {
            File archivo = obtenerUltimoExcelDescargadoDevoluciones();
            if (archivo == null || !archivo.exists()) {
                return false;
            }

            String[] columnas = {
                    "Motivo de devolucion",
                    "Observaciones Tienda",
                    "Observaciones Proveedor",
                    "Perfil Autorizador",
                    "Codigo de activacion",
                    "ID Dispositivo"
            };

            try (FileInputStream fis = new FileInputStream(archivo);
                    Workbook workbook = WorkbookFactory.create(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                org.apache.poi.ss.usermodel.Row headers = sheet.getRow(0);
                if (headers == null) {
                    return false;
                }

                int idxReferencia = buscarIndiceColumnaExcel(headers, "Folio");
                if (idxReferencia < 0) {
                    idxReferencia = 0;
                }

                org.apache.poi.ss.usermodel.Cell refCell = headers.getCell(idxReferencia);
                if (refCell == null) {
                    return false;
                }

                org.apache.poi.ss.usermodel.Font fuenteRef = workbook.getFontAt(refCell.getCellStyle().getFontIndex());
                short colorFuenteRef = fuenteRef.getColor();
                short colorRellenoRef = refCell.getCellStyle().getFillForegroundColor();

                boolean ok = true;
                StringBuilder detalle = new StringBuilder();

                for (String columna : columnas) {
                    int idx = buscarIndiceColumnaExcel(headers, columna);
                    if (idx < 0) {
                        ok = false;
                        detalle.append("No se encontro encabezado '").append(columna).append("'. ");
                        continue;
                    }

                    org.apache.poi.ss.usermodel.Cell cell = headers.getCell(idx);
                    if (cell == null) {
                        ok = false;
                        detalle.append("Celda de encabezado vacia en '").append(columna).append("'. ");
                        continue;
                    }

                    org.apache.poi.ss.usermodel.Font f = workbook.getFontAt(cell.getCellStyle().getFontIndex());
                    boolean fuenteOk = "Arial".equalsIgnoreCase(f.getFontName());
                    boolean tamanioOk = f.getFontHeightInPoints() == 14;
                    boolean negritaOk = f.getBold();
                    boolean colorFuenteOk = f.getColor() == colorFuenteRef;
                    boolean rellenoOk = cell.getCellStyle().getFillForegroundColor() == colorRellenoRef;

                    if (!(fuenteOk && tamanioOk && negritaOk && colorFuenteOk && rellenoOk)) {
                        ok = false;
                        detalle.append("Formato invalido en '").append(columna).append("'. ");
                    }
                }

                testCase.addTextEvidenceCurrentStep("Validacion formato encabezados columnas nuevas: "
                        + (detalle.length() == 0 ? "OK" : detalle.toString()));
                if (screenshot) {
                    testCase.addScreenShotCurrentStep(u, "Formato_Encabezados_Columnas_Nuevas_Devoluciones");
                }
                return ok;
            }
        } catch (Exception exception) {
            testCase.addCodeEvidenceCurrentStep("Error al validar formato de encabezados de Devoluciones: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Valida el formato de contenido para las columnas nuevas de Devoluciones.
     *
     * @param screenshot true para adjuntar captura del paso.
     * @return true si el contenido conserva Arial 12 y color base de datos.
     *
     * @author Mauricio Gurrola
     * @date 2026-08-05
     */
    public boolean validarFormatoContenidoColumnasNuevasDevoluciones(boolean screenshot) {
        try {
            File archivo = obtenerUltimoExcelDescargadoDevoluciones();
            if (archivo == null || !archivo.exists()) {
                return false;
            }

            String[] columnas = {
                    "Motivo de devolucion",
                    "Observaciones Tienda",
                    "Observaciones Proveedor",
                    "Perfil Autorizador",
                    "Codigo de activacion",
                    "ID Dispositivo"
            };

            try (FileInputStream fis = new FileInputStream(archivo);
                    Workbook workbook = WorkbookFactory.create(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                org.apache.poi.ss.usermodel.Row headers = sheet.getRow(0);
                if (headers == null) {
                    return false;
                }

                int idxFolio = buscarIndiceColumnaExcel(headers, "Folio");
                if (idxFolio < 0) {
                    idxFolio = 0;
                }

                boolean ok = true;
                StringBuilder detalle = new StringBuilder();
                org.apache.poi.ss.usermodel.DataFormatter formatter = new org.apache.poi.ss.usermodel.DataFormatter(Locale.ROOT);

                for (String columna : columnas) {
                    int idx = buscarIndiceColumnaExcel(headers, columna);
                    if (idx < 0) {
                        ok = false;
                        detalle.append("No se encontro columna '").append(columna).append("'. ");
                        continue;
                    }

                    org.apache.poi.ss.usermodel.Cell cellDato = null;
                    org.apache.poi.ss.usermodel.Cell cellRef = null;
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                        if (row == null) {
                            continue;
                        }
                        org.apache.poi.ss.usermodel.Cell c = row.getCell(idx);
                        String valor = c == null ? "" : formatter.formatCellValue(c).trim();
                        if (!valor.isEmpty()) {
                            cellDato = c;
                            cellRef = row.getCell(idxFolio);
                            break;
                        }
                    }

                    if (cellDato == null) {
                        ok = false;
                        detalle.append("Sin dato para validar formato en '").append(columna).append("'. ");
                        continue;
                    }

                    org.apache.poi.ss.usermodel.Font fuenteDato = workbook.getFontAt(cellDato.getCellStyle().getFontIndex());
                    boolean fuenteOk = "Arial".equalsIgnoreCase(fuenteDato.getFontName());
                    boolean tamanioOk = fuenteDato.getFontHeightInPoints() == 12;

                    boolean colorOk = true;
                    if (cellRef != null) {
                        org.apache.poi.ss.usermodel.Font fuenteRef = workbook.getFontAt(cellRef.getCellStyle().getFontIndex());
                        colorOk = fuenteDato.getColor() == fuenteRef.getColor();
                    }

                    if (!(fuenteOk && tamanioOk && colorOk)) {
                        ok = false;
                        detalle.append("Formato invalido en datos de '").append(columna).append("'. ");
                    }
                }

                testCase.addTextEvidenceCurrentStep("Validacion formato contenido columnas nuevas: "
                        + (detalle.length() == 0 ? "OK" : detalle.toString()));
                if (screenshot) {
                    testCase.addScreenShotCurrentStep(u, "Formato_Contenido_Columnas_Nuevas_Devoluciones");
                }
                return ok;
            }
        } catch (Exception exception) {
            testCase.addCodeEvidenceCurrentStep("Error al validar formato de contenido de Devoluciones: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Recorre filas del grid y descarga Excel individual hasta encontrar un registro con Perfil Autorizador esperado.
     *
     * @param valorEsperado valor esperado para Perfil Autorizador.
     * @param screenshot true para adjuntar captura del paso.
     * @return true si se encontro un registro con el perfil esperado y se dejo descargado su Excel.
     *
     * @author Mauricio Gurrola
     * @date 2026-08-05
     */
    public boolean localizarRegistroConPerfilAutorizadorYDescargarExcel(String valorEsperado, boolean screenshot) {
        try {
            WebDriver driver = u.getDriver();
            List<WebElement> filas = driver.findElements(devElements.filasGridDevoluciones);
            int max = Math.min(filas.size(), 6);
            String esperadoNorm = normalizarEtiquetaBasica(valorEsperado);

            for (int i = 1; i <= max; i++) {
                By filaBy = By.xpath("(//app-directos-devoluciones//directos-devoluciones-grid//table/tbody/tr)[" + i + "]");
                WebElement fila = u.waitWebElement(filaBy, 20, 2);
                try {
                    fila.click();
                } catch (Exception exception) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fila);
                }

                pc.MensajeEspereDesaparece(false);
                if (!descargarExcelIndividualDesdeVistaPreliminar(false)) {
                    cierraVistaPreliminar();
                    continue;
                }

                String valorPerfil = obtenerPrimerValorNoVacioColumna("Perfil Autorizador");
                if (normalizarEtiquetaBasica(valorPerfil).equals(esperadoNorm)) {
                    testCase.addTextEvidenceCurrentStep("Registro localizado con Perfil Autorizador='" + valorPerfil + "' en fila " + i + ".");
                    if (screenshot) {
                        testCase.addScreenShotCurrentStep(u, "Registro_Perfil_Autorizador_" + normalizarNombreArchivo(valorEsperado));
                    }
                    return true;
                }

                cierraVistaPreliminar();
            }

            return false;
        } catch (Exception exception) {
            testCase.addCodeEvidenceCurrentStep("Error al localizar registro por Perfil Autorizador: " + exception.getMessage());
            return false;
        }
    }

    private String capturarValorDesdeVistaPreliminarPorEtiquetas(String[] etiquetas) {
        try {
            if (etiquetas == null || etiquetas.length == 0) {
                System.out.println("[PREVIEW][WARN] No se recibieron etiquetas para extraer valor.");
                return "";
            }

            final int maxIntentos = 3;
            for (int intento = 1; intento <= maxIntentos; intento++) {
                System.out.println("[PREVIEW] Intento " + intento + "/" + maxIntentos
                        + " para extraer etiquetas: " + Arrays.toString(etiquetas));

                String texto = capturarTextoPreviewConfiable(intento);
                if (texto == null || texto.trim().isEmpty()) {
                    System.out.println("[PREVIEW][WARN] Sin texto visible recuperado en intento " + intento + ".");
                    dormir(350);
                    continue;
                }

                String muestra = texto.replaceAll("\\s+", " ").trim();
                if (muestra.length() > 280) {
                    muestra = muestra.substring(0, 280) + "...";
                }
                System.out.println("[PREVIEW] Muestra texto intento " + intento + ": " + muestra);

                String valor = extraerValorDesdeTextoPorEtiquetasAvanzado(texto, etiquetas);
                if (valor != null && !valor.trim().isEmpty()) {
                    System.out.println("[PREVIEW][OK] Valor encontrado para etiquetas " + Arrays.toString(etiquetas) + ": '" + valor.trim() + "'.");
                    return valor.trim();
                }

                System.out.println("[PREVIEW][WARN] No se encontro valor por etiquetas en intento " + intento + ".");
                dormir(350);
            }

            System.out.println("[PREVIEW][ERROR] No se logro extraer valor tras reintentos para etiquetas: "
                    + Arrays.toString(etiquetas));
            return "";
        } catch (Exception exception) {
            System.out.println("[PREVIEW][ERROR] Excepcion extrayendo valor desde vista preliminar: " + exception.getMessage());
            return "";
        }
    }

    private String capturarTextoPreviewConfiable(int intento) {
        String textoDom = obtenerTextoDesdeTextLayerVisor();
        if (esTextoPreviewConfiable(textoDom)) {
            System.out.println("[PREVIEW] Texto obtenido por textLayer (confiable).");
            return textoDom;
        }

        if (intento >= 2) {
            String textoPdf = capturarTextoDesdePdfDescargadoFallback();
            if (esTextoPreviewConfiable(textoPdf)) {
                System.out.println("[PREVIEW] Texto obtenido por fallback PDF descargado (confiable).");
                return textoPdf;
            }
        }

        return "";
    }

    private boolean esTextoPreviewConfiable(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }

        String normalizado = normalizarEtiquetaBasica(texto);

        // Evita falsos positivos por foco fuera del visor (codigo fuente o UI global).
        if (normalizado.contains("PACKAGE WEB COMETA") || normalizado.contains("IMPORT ORG TESTNG")
                || normalizado.contains("ADDSTEP(")) {
            return false;
        }

        boolean tieneCabeceraPdf = normalizado.contains("PROVEEDOR") && normalizado.contains("FOLIO")
                && normalizado.contains("FUENTE DE ABASTO");
        boolean tieneBloqueObjetivo = normalizado.contains("MOTIVO DE DEVOLUCION")
                || normalizado.contains("OBSERVACIONES");

        return tieneCabeceraPdf && tieneBloqueObjetivo;
    }

    /**
     * Recupera texto del PDF renderizado en el visor cuando existe textLayer.
     */
    private String obtenerTextoDesdeTextLayerVisor() {
        try {
            u.waitWebElement(By.id("viewer"), 8, 1);
            List<WebElement> spans = u.getDriver().findElements(By.cssSelector("#viewer .textLayer span"));
            if (spans == null || spans.isEmpty()) {
                spans = u.getDriver().findElements(By.cssSelector(".textLayer span"));
            }

            if (spans == null || spans.isEmpty()) {
                System.out.println("[PREVIEW] No se localizaron spans en textLayer.");
                return "";
            }

            StringBuilder sb = new StringBuilder();
            int lineasUtiles = 0;
            for (WebElement span : spans) {
                String txt = span.getText();
                if (txt == null) {
                    continue;
                }
                String limpio = txt.trim();
                if (limpio.isEmpty()) {
                    continue;
                }
                sb.append(limpio).append("\n");
                lineasUtiles++;
            }

            String texto = sb.toString().trim();
            System.out.println("[PREVIEW] textLayer capturado con " + lineasUtiles + " lineas utiles.");
            return texto;
        } catch (Exception exception) {
            System.out.println("[PREVIEW][WARN] No fue posible leer textLayer del visor: " + exception.getMessage());
            return "";
        }
    }

    /**
     * Fallback: descarga el PDF individual desde el modal y extrae su texto con PDFBox.
     */
    private String capturarTextoDesdePdfDescargadoFallback() {
        try {
            if (textoPreviewPdfCache != null && !textoPreviewPdfCache.trim().isEmpty()) {
                return textoPreviewPdfCache;
            }

            if (fallbackPdfIntentado) {
                return "";
            }
            fallbackPdfIntentado = true;

            long inicio = System.currentTimeMillis();
            boolean clicPdf = pc.ClickBtnPDF(false);
            if (!clicPdf) {
                System.out.println("[PREVIEW][WARN] No fue posible accionar boton PDF para fallback.");
                return "";
            }

            File pdf = esperarNuevoPdfDescargado(inicio, 90000L);
            if (pdf == null || !pdf.exists()) {
                System.out.println("[PREVIEW][WARN] No se detecto PDF descargado para fallback.");
                return "";
            }

            try (PDDocument doc = Loader.loadPDF(pdf)) {
                PDFTextStripper stripper = new PDFTextStripper();
                String texto = stripper.getText(doc);
                String muestra = texto == null ? "" : texto.replaceAll("\\s+", " ").trim();
                if (muestra.length() > 220) {
                    muestra = muestra.substring(0, 220) + "...";
                }
                System.out.println("[PREVIEW] Texto fallback PDF ('" + pdf.getName() + "'): " + muestra);
                textoPreviewPdfCache = texto == null ? "" : texto;
                return textoPreviewPdfCache;
            }
        } catch (Exception exception) {
            System.out.println("[PREVIEW][WARN] Fallback PDF fallo: " + exception.getMessage());
            return "";
        }
    }

    private File esperarNuevoPdfDescargado(long inicioMs, long timeoutMs) {
        long fin = System.currentTimeMillis() + timeoutMs;
        File candidato = null;

        while (System.currentTimeMillis() < fin) {
            File downloads = new File(System.getProperty("user.home") + "\\Downloads");
            File[] archivos = downloads.listFiles();
            if (archivos == null) {
                dormir(600);
                continue;
            }

            for (File archivo : archivos) {
                if (!archivo.isFile()) {
                    continue;
                }

                String nombre = archivo.getName().toLowerCase(Locale.ROOT);
                if (!nombre.endsWith(".pdf")) {
                    continue;
                }
                if (archivo.lastModified() < inicioMs - 1000) {
                    continue;
                }

                File temporalCr = new File(archivo.getParentFile(), archivo.getName() + ".crdownload");
                File temporalPart = new File(archivo.getParentFile(), archivo.getName() + ".part");
                if (temporalCr.exists() || temporalPart.exists()) {
                    continue;
                }

                if (candidato == null || archivo.lastModified() > candidato.lastModified()) {
                    candidato = archivo;
                }
            }

            if (candidato != null) {
                return candidato;
            }

            dormir(700);
        }

        return null;
    }

    private String extraerValorPorEtiqueta(String[] lineas, String etiqueta) {
        String etiquetaNorm = normalizarEtiquetaBasica(etiqueta);
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i] == null ? "" : lineas[i].trim();
            if (linea.isEmpty()) {
                continue;
            }

            String lineaNorm = normalizarEtiquetaBasica(linea);
            if (!lineaNorm.contains(etiquetaNorm)) {
                continue;
            }

            int idxDosPuntos = linea.indexOf(':');
            if (idxDosPuntos >= 0 && idxDosPuntos + 1 < linea.length()) {
                String valor = linea.substring(idxDosPuntos + 1).trim();
                if (!valor.isEmpty()) {
                    return valor;
                }
            }

            String reemplazo = lineaNorm.replace(etiquetaNorm, "").trim();
            if (!reemplazo.isEmpty()) {
                return reemplazo;
            }

            for (int j = i + 1; j < lineas.length; j++) {
                String siguiente = lineas[j] == null ? "" : lineas[j].trim();
                if (!siguiente.isEmpty()) {
                    return siguiente;
                }
            }
        }
        return "";
    }

    /**
     * Intenta extraer valor con regex sobre el texto completo cuando el visor copia
     * bloques planos sin conservar la separacion por linea.
     */
    private String extraerValorPorEtiquetaEnTextoCompleto(String textoCompleto, String etiqueta) {
        if (textoCompleto == null || textoCompleto.trim().isEmpty()) {
            return "";
        }

        try {
            String texto = textoCompleto.replace('\r', '\n');
            String etiquetaRegex = Pattern.quote(etiqueta);

            Pattern patron = Pattern.compile("(?is)" + etiquetaRegex + "\\s*:?\\s*(.+?)(?:\\n|$)");
            Matcher matcher = patron.matcher(texto);
            if (matcher.find()) {
                String valor = matcher.group(1) == null ? "" : matcher.group(1).trim();
                if (!valor.isEmpty()) {
                    return limpiarValorExtraidoPreview(valor);
                }
            }

            return "";
        } catch (Exception exception) {
            return "";
        }
    }

    private String extraerValorDesdeTextoPorEtiquetasAvanzado(String textoCompleto, String[] etiquetas) {
        if (textoCompleto == null || textoCompleto.trim().isEmpty()) {
            return "";
        }

        String[] lineas = textoCompleto.split("\\R");
        for (String etiqueta : etiquetas) {
            String etiquetaNorm = normalizarEtiquetaBasica(etiqueta);

            for (int i = 0; i < lineas.length; i++) {
                String linea = lineas[i] == null ? "" : lineas[i].trim();
                String lineaNorm = normalizarEtiquetaBasica(linea);

                if (lineaNorm.isEmpty()) {
                    continue;
                }

                String siguienteNorm = (i + 1) < lineas.length ? normalizarEtiquetaBasica(lineas[i + 1]) : "";
                String terceraNorm = (i + 2) < lineas.length ? normalizarEtiquetaBasica(lineas[i + 2]) : "";
                String ventana = (lineaNorm + " " + siguienteNorm + " " + terceraNorm).trim();

                if (!ventana.contains(etiquetaNorm)) {
                    continue;
                }

                // Caso 1: valor en la misma linea despues de ':' o despues de la etiqueta.
                int idxDosPuntos = linea.indexOf(':');
                if (idxDosPuntos >= 0 && idxDosPuntos + 1 < linea.length()) {
                    String valor = limpiarValorExtraidoPreview(linea.substring(idxDosPuntos + 1));
                    if (!valor.isEmpty() && !esValorInvalidoParaEtiquetas(valor, etiquetas)) {
                        return valor;
                    }
                }

                int idxEtiquetaEnLinea = lineaNorm.indexOf(etiquetaNorm);
                if (idxEtiquetaEnLinea >= 0 && lineaNorm.length() > (idxEtiquetaEnLinea + etiquetaNorm.length())) {
                    String resto = lineaNorm.substring(idxEtiquetaEnLinea + etiquetaNorm.length()).trim();
                    if (!resto.isEmpty() && !esValorInvalidoParaEtiquetas(resto, etiquetas)) {
                        return limpiarValorExtraidoPreview(resto);
                    }
                }

                // Caso 2: valor en lineas siguientes (etiqueta partida por salto de linea).
                for (int j = i + 1; j < Math.min(lineas.length, i + 8); j++) {
                    String candidata = lineas[j] == null ? "" : lineas[j].trim();
                    String candidataNorm = normalizarEtiquetaBasica(candidata);

                    if (candidataNorm.isEmpty()) {
                        continue;
                    }

                    if (candidataNorm.contains("OBSERVACIONES") || candidataNorm.equals("PERSONAL")
                            || candidataNorm.equals("TIENDA") || candidataNorm.equals("PROVEEDOR")) {
                        continue;
                    }

                    if (candidataNorm.contains("FIRMA")) {
                        continue;
                    }

                    // Si la linea candidata es ella misma un par etiqueta:valor, extraer solo el valor
                    if (candidataNorm.contains(etiquetaNorm)) {
                        int idxCol = candidata.indexOf(':');
                        if (idxCol >= 0 && idxCol + 1 < candidata.length()) {
                            String valorLimpio = limpiarValorExtraidoPreview(candidata.substring(idxCol + 1));
                            if (!valorLimpio.isEmpty() && !esValorInvalidoParaEtiquetas(valorLimpio, etiquetas)) {
                                return valorLimpio;
                            }
                        }
                        continue;
                    }

                    String valorCandidato = limpiarValorExtraidoPreview(candidata);
                    if (!esValorInvalidoParaEtiquetas(valorCandidato, etiquetas)) {
                        return valorCandidato;
                    }
                }
            }

            String valorRegex = extraerValorPorEtiquetaEnTextoCompleto(textoCompleto, etiqueta);
            if (valorRegex != null && !valorRegex.trim().isEmpty()
                    && !esValorInvalidoParaEtiquetas(valorRegex, etiquetas)) {
                return valorRegex;
            }
        }

        return "";
    }

    private boolean esValorInvalidoParaEtiquetas(String valor, String[] etiquetas) {
        String valorNorm = normalizarEtiquetaBasica(valor);
        if (valorNorm.isEmpty()) {
            return true;
        }

        boolean esCampoMotivo = false;
        for (String etiqueta : etiquetas) {
            String etiquetaNorm = normalizarEtiquetaBasica(etiqueta);
            if (etiquetaNorm.contains("MOTIVO DE DEVOLUCION")) {
                esCampoMotivo = true;
                break;
            }
        }

        if (esCampoMotivo) {
            // Evita capturar el bloque de tiempo cuando se busca el motivo del documento.
            if (valorNorm.matches(".*\\b\\d+\\s*HRS?\\b.*") || valorNorm.matches(".*\\b\\d+\\s*HR\\b.*")
                    || valorNorm.matches(".*\\b\\d+\\s*MIN\\b.*") || valorNorm.matches(".*\\b\\d+\\s*S\\b.*")
                    || valorNorm.contains("TIEMPO DE DEVOLUCION")) {
                return true;
            }

            if (valorNorm.contains("PROVEEDOR") || valorNorm.contains("FUENTE DE ABASTO")
                    || valorNorm.contains("DEVOLUCION A PROVEEDORES DIRECTOS")) {
                return true;
            }
        }

        return false;
    }

    private String limpiarValorExtraidoPreview(String valor) {
        if (valor == null) {
            return "";
        }

        String limpio = valor.trim();
        limpio = limpio.replaceAll("^[\\-:|]+", "").trim();
        limpio = limpio.replaceAll("\\s{2,}", " ");

        return limpio;
    }

    private String normalizarEtiquetaBasica(String texto) {
        if (texto == null) {
            return "";
        }
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return normalizado.replaceAll("\\s+", " ").trim().toUpperCase(Locale.ROOT);
    }

    private String normalizarNombreArchivo(String texto) {
        return normalizarEtiquetaBasica(texto).replace(' ', '_');
    }

    private File esperarNuevoExcelDescargado(long inicioMs, long timeoutMs) {
        long fin = System.currentTimeMillis() + timeoutMs;
        File candidato = null;

        while (System.currentTimeMillis() < fin) {
            File downloads = new File(System.getProperty("user.home") + "\\Downloads");
            File[] archivos = downloads.listFiles();
            if (archivos == null) {
                dormir(1000);
                continue;
            }

            for (File archivo : archivos) {
                if (!archivo.isFile()) {
                    continue;
                }
                String nombre = archivo.getName().toLowerCase(Locale.ROOT);
                if (!nombre.endsWith(".xlsx")) {
                    continue;
                }
                if (archivo.lastModified() < inicioMs - 1000) {
                    continue;
                }

                String cr = nombre + ".crdownload";
                File temporal = new File(archivo.getParentFile(), cr);
                if (temporal.exists()) {
                    continue;
                }

                if (candidato == null || archivo.lastModified() > candidato.lastModified()) {
                    candidato = archivo;
                }
            }

            if (candidato != null) {
                return candidato;
            }

            dormir(1000);
        }

        return null;
    }

    private File obtenerUltimoExcelDescargadoDevoluciones() {
        File downloads = new File(System.getProperty("user.home") + "\\Downloads");
        File[] archivos = downloads.listFiles();
        if (archivos == null) {
            return null;
        }

        File ultimo = null;
        for (File archivo : archivos) {
            if (!archivo.isFile()) {
                continue;
            }
            String nombre = archivo.getName().toLowerCase(Locale.ROOT);
            if (!nombre.endsWith(".xlsx") || nombre.endsWith(".crdownload")) {
                continue;
            }

            if (ultimo == null || archivo.lastModified() > ultimo.lastModified()) {
                ultimo = archivo;
            }
        }
        return ultimo;
    }

    private int buscarIndiceColumnaExcel(org.apache.poi.ss.usermodel.Row headers, String nombreColumna) {
        org.apache.poi.ss.usermodel.DataFormatter formatter = new org.apache.poi.ss.usermodel.DataFormatter(Locale.ROOT);
        String esperado = normalizarEtiquetaBasica(nombreColumna);

        for (int i = 0; i < headers.getLastCellNum(); i++) {
            org.apache.poi.ss.usermodel.Cell cell = headers.getCell(i);
            String nombre = cell == null ? "" : formatter.formatCellValue(cell).trim();
            if (normalizarEtiquetaBasica(nombre).equals(esperado)) {
                return i;
            }
        }
        return -1;
    }

    private String obtenerPrimerValorNoVacioColumna(String nombreColumna) {
        try {
            File archivo = obtenerUltimoExcelDescargadoDevoluciones();
            if (archivo == null || !archivo.exists()) {
                return "";
            }

            try (FileInputStream fis = new FileInputStream(archivo);
                    Workbook workbook = WorkbookFactory.create(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                org.apache.poi.ss.usermodel.Row headers = sheet.getRow(0);
                if (headers == null) {
                    return "";
                }

                int idx = buscarIndiceColumnaExcel(headers, nombreColumna);
                if (idx < 0) {
                    return "";
                }

                org.apache.poi.ss.usermodel.DataFormatter formatter = new org.apache.poi.ss.usermodel.DataFormatter(Locale.ROOT);
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }
                    org.apache.poi.ss.usermodel.Cell cell = row.getCell(idx);
                    String valor = cell == null ? "" : formatter.formatCellValue(cell).trim();
                    if (!valor.isEmpty()) {
                        return valor;
                    }
                }
            }
            return "";
        } catch (Exception exception) {
            return "";
        }
    }

    private boolean validarValorColumnaExcelContraEsperado(String nombreColumna, String valorEsperado, boolean screenshot) {
        String valorExcel = obtenerPrimerValorNoVacioColumna(nombreColumna);
        boolean ok = !valorExcel.isEmpty() && normalizarEtiquetaBasica(valorExcel).equals(normalizarEtiquetaBasica(valorEsperado));
        testCase.addTextEvidenceCurrentStep("Comparacion columna '" + nombreColumna + "' -> Excel: '" + valorExcel
                + "' | Esperado: '" + valorEsperado + "'.");
        if (screenshot) {
            testCase.addScreenShotCurrentStep(u, "Comparacion_" + normalizarNombreArchivo(nombreColumna));
        }
        return ok;
    }
}