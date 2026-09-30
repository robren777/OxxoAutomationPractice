package WEB.cometa.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import WEB.util.GlobalVariables;
import modelo.TestCase;

/**
 * Object Model tecnico para validar objetos de base de datos PostgreSQL Cometa requeridos por los casos MVP5 de tablas proyectadas RTD y DE.
 * Centraliza consultas JDBC reutilizables para existencia de tablas, columnas, funciones y procedimientos.
 *
 * @author Mauricio Gurrola
 * @date 2026-07-13
 */
public class CometaBdValidationHelper {

    private final TestCase testCase;

    /**
     * Inicializa el helper de validaciones BD con el objeto de evidencia del framework.
     *
     * @param testCase caso de prueba usado para registrar evidencia textual.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-13
     */
    public CometaBdValidationHelper(TestCase testCase) {
        this.testCase = testCase;
    }

    /**
     * Valida que la conexion JDBC a PostgreSQL Cometa pueda establecerse con las variables globales configuradas.
     * Registra en evidencia el resultado PASSED o FAILED de la conexion.
     *
     * @return true si la conexion se establece correctamente; false en caso contrario.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-13
     */
    public boolean validarConexionBD() {
        try (Connection connection = abrirConexion()) {
            boolean conectada = connection != null && !connection.isClosed();
            registrarResultadoPaso(conectada, "Conexion PostgreSQL Cometa", "Esperado: conexion establecida | Actual: conexion no establecida");
            return conectada;
        } catch (Exception e) {
            registrarResultadoPaso(false, "Conexion PostgreSQL Cometa", "Excepcion: " + e.getMessage());
            return false;
        }
    }

    /**
     * Valida que una tabla exista como BASE TABLE en information_schema.tables.
     * Registra el schema y nombre encontrado cuando la tabla existe.
     *
     * @param tableName nombre de la tabla tecnica a validar.
     * @return true si la tabla existe como BASE TABLE; false si no existe o ocurre un error.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-13
     */
    public boolean validarTablaBase(String tableName) {
        String sql = "SELECT table_schema, table_name "
                + "FROM information_schema.tables "
                + "WHERE table_type = 'BASE TABLE' AND table_name = ?";

        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableName);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    agregarEvidencia("Tabla encontrada: " + rs.getString("table_schema") + "." + rs.getString("table_name"));
                    registrarResultadoPaso(true, "Validacion tabla base " + tableName, "");
                    return true;
                }

                registrarResultadoPaso(false, "Validacion tabla base " + tableName, "Esperado: tabla existente | Actual: no se encontro en information_schema.tables");
                return false;
            }
        } catch (Exception e) {
            registrarResultadoPaso(false, "Validacion tabla base " + tableName, "Excepcion: " + e.getMessage());
            return false;
        }
    }

    /**
     * Valida que una familia de tablas exista por patron y que incluya la tabla principal esperada.
     * Registra el total de objetos encontrados y la lista de nombres retornados por information_schema.tables.
     *
     * @param patron patron LIKE usado para localizar la familia de tablas.
     * @param tablaPrincipalEsperada nombre de la tabla principal que debe estar incluida en el resultado.
     * @return true si existe al menos un objeto y contiene la tabla principal esperada; false en caso contrario.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-13
     */
    public boolean validarFamiliaTablas(String patron, String tablaPrincipalEsperada) {
        Set<String> tablas = obtenerTablasPorPatron(patron);
        boolean contienePrincipal = tablas.contains(tablaPrincipalEsperada);
        boolean totalValido = tablas.size() >= 1;
        boolean resultadoPaso = totalValido && contienePrincipal;

        agregarEvidencia("Patron consultado: " + patron + " | Total objetos: " + tablas.size() + " | Objetos: " + tablas.toString());
        agregarEvidencia("Tabla principal esperada: " + tablaPrincipalEsperada + " | Resultado: "
                + (contienePrincipal ? "ENCONTRADA" : "NO ENCONTRADA"));
        registrarResultadoPaso(resultadoPaso, "Validacion familia de tablas " + patron,
                "Esperado: total >= 1 y tabla principal " + tablaPrincipalEsperada + " presente | Actual: total " + tablas.size()
                        + " y principal presente = " + contienePrincipal);
        return resultadoPaso;
    }

    /**
     * Obtiene los nombres de tablas BASE TABLE que coincidan con un patron LIKE en information_schema.tables.
     * La lista se devuelve ordenada por nombre de tabla de acuerdo con la consulta SQL.
     *
     * @param patron patron LIKE usado para buscar tablas.
     * @return conjunto ordenado de nombres de tabla encontrados; vacio si no hay resultados o ocurre un error.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-13
     */
    public Set<String> obtenerTablasPorPatron(String patron) {
        String sql = "SELECT table_name "
                + "FROM information_schema.tables "
                + "WHERE table_type = 'BASE TABLE' AND table_name LIKE ? "
                + "ORDER BY table_name";
        Set<String> tablas = new LinkedHashSet<String>();

        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patron);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    tablas.add(rs.getString("table_name"));
                }
            }
            agregarEvidencia("Consulta de tablas por patron ejecutada. Patron: " + patron + " | Total: " + tablas.size());
        } catch (Exception e) {
            agregarEvidencia("Resultado paso: FAILED | Motivo: error al consultar patron de tablas " + patron + ". Excepcion: " + e.getMessage());
        }

        return tablas;
    }

    /**
     * Obtiene los nombres de funciones o procedimientos registrados en pg_proc que coincidan con la lista esperada.
     * Registra en evidencia cada objeto encontrado con su schema.
     *
     * @param nombresEsperados nombres de funciones o procedimientos a buscar en pg_proc.
     * @return conjunto ordenado de nombres encontrados; vacio si no hay resultados o ocurre un error.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-13
     */
    public Set<String> obtenerProcedimientosOFunciones(String... nombresEsperados) {
        String placeholders = String.join(",", Collections.nCopies(nombresEsperados.length, "?"));
        String sql = "SELECT n.nspname, p.proname "
                + "FROM pg_proc p "
                + "JOIN pg_namespace n ON n.oid = p.pronamespace "
                + "WHERE p.proname IN (" + placeholders + ") "
                + "ORDER BY p.proname";
        Set<String> objetos = new LinkedHashSet<String>();

        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < nombresEsperados.length; i++) {
                statement.setString(i + 1, nombresEsperados[i]);
            }

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String schema = rs.getString("nspname");
                    String proname = rs.getString("proname");
                    objetos.add(proname);
                    agregarEvidencia("Objeto pg_proc encontrado: " + schema + "." + proname);
                }
            }
        } catch (Exception e) {
            agregarEvidencia("Resultado paso: FAILED | Motivo: error al consultar pg_proc. Excepcion: " + e.getMessage());
        }

        agregarEvidencia("Total objetos pg_proc encontrados: " + objetos.size() + " | Objetos: " + objetos.toString());
        return objetos;
    }

    /**
     * Valida que una tabla pueda ejecutar SELECT COUNT(*) sin error tecnico, aceptando total de registros igual a cero.
     * El nombre de tabla se valida para evitar caracteres no esperados antes de interpolarlo en SQL.
     *
     * @param tableName nombre de la tabla publica sobre la que se ejecutara COUNT.
     * @return true si la consulta COUNT se ejecuta y retorna un total mayor o igual a cero; false en caso contrario.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-13
     */
    public boolean validarConteoTablaSinError(String tableName) {
        if (!tableName.matches("[A-Za-z0-9_]+")) {
            registrarResultadoPaso(false, "Validacion COUNT public." + tableName,
                    "Esperado: nombre de tabla alfanumerico | Actual: " + tableName);
            return false;
        }

        String sql = "SELECT COUNT(*) AS total_registros FROM public." + tableName;

        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                long total = rs.getLong("total_registros");
                agregarEvidencia("Consulta COUNT ejecutada sin error tecnico. Tabla: public." + tableName
                        + " | Total registros: " + total);
                registrarResultadoPaso(total >= 0, "Validacion COUNT public." + tableName,
                        "Esperado: total >= 0 | Actual: " + total);
                return total >= 0;
            }

            registrarResultadoPaso(false, "Validacion COUNT public." + tableName,
                    "Esperado: una fila con total_registros | Actual: la consulta no retorno filas");
            return false;
        } catch (Exception e) {
            registrarResultadoPaso(false, "Validacion COUNT public." + tableName, "Excepcion: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el total de columnas configuradas en information_schema.columns para las tablas esperadas.
     * Registra en evidencia cada tabla encontrada y su total de columnas.
     *
     * @param tablasEsperadas nombres de tablas que deben existir en information_schema.columns.
     * @return mapa con nombre de tabla y total de columnas encontrado.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-13
     */
    public Map<String, Integer> obtenerTotalColumnasPorTabla(String... tablasEsperadas) {
        String placeholders = String.join(",", Collections.nCopies(tablasEsperadas.length, "?"));
        String sql = "SELECT table_name, COUNT(*) AS total_columnas "
                + "FROM information_schema.columns "
                + "WHERE table_name IN (" + placeholders + ") "
                + "GROUP BY table_name "
                + "ORDER BY table_name";
        Map<String, Integer> columnasPorTabla = new LinkedHashMap<String, Integer>();

        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < tablasEsperadas.length; i++) {
                statement.setString(i + 1, tablasEsperadas[i]);
            }

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String tableName = rs.getString("table_name");
                    int totalColumnas = rs.getInt("total_columnas");
                    columnasPorTabla.put(tableName, totalColumnas);
                    agregarEvidencia("Tabla: " + tableName + " | Total columnas: " + totalColumnas);
                }
            }
        } catch (Exception e) {
            agregarEvidencia("Resultado paso: FAILED | Motivo: error al consultar columnas en information_schema.columns. Excepcion: " + e.getMessage());
        }

        return columnasPorTabla;
    }

    /**
     * Abre una conexion JDBC a PostgreSQL Cometa usando las variables globales del framework.
     *
     * @return conexion JDBC activa.
     * @throws SQLException si DriverManager no puede establecer la conexion.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-13
     */
    private Connection abrirConexion() throws SQLException {
        return DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
                GlobalVariables.DB_USER_Cometa,
                GlobalVariables.DB_PASSWORD_Cometa);
    }

    /**
     * Registra un mensaje informativo en consola y en la evidencia del paso actual.
     *
     * @param mensaje texto que se escribira en consola y evidencia.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-13
     */
    private void agregarEvidencia(String mensaje) {
        System.out.println("[VALIDACION BD COMETA] " + mensaje);
        testCase.addBoldTextEvidenceCurrentStep(mensaje);
    }

    /**
     * Registra el resultado PASSED o FAILED de una validacion reutilizable.
     * Incluye el motivo de falla cuando el resultado es negativo.
     *
     * @param resultadoPaso resultado booleano de la validacion.
     * @param descripcion descripcion corta de la validacion ejecutada.
     * @param motivoFallo detalle de la condicion no cumplida cuando el resultado es false.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-13
     */
    private void registrarResultadoPaso(boolean resultadoPaso, String descripcion, String motivoFallo) {
        String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
        String mensaje = "Resultado paso: " + estadoPaso + " - " + descripcion
                + (resultadoPaso ? "" : " | Motivo: " + motivoFallo);
        System.out.println("[RESULTADO PASO] " + mensaje);
        testCase.addBoldTextEvidenceCurrentStep(mensaje);
    }

    /**
     * Obtiene la transferencia mas reciente disponible para el supplier asociado al usuario mediante PCSUPPLIERID.
     * La consulta descarta transferencias nulas o vacias y revisa los diez registros mas recientes de xxcm_historico_cedis_view.
     *
     * @param usuario usuario de Portal COMETA cuyo supplier se utilizara para filtrar la consulta.
     * @return transferencia valida mas reciente, o null cuando no existen datos o la consulta falla.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-22
     */
    public String obtenerTransferenciaValidaPorSupplier(String usuario) {
        String sql = "SELECT * "
                + "FROM public.xxcm_historico_cedis_view "
                + "WHERE transferencia IS NOT NULL "
                + "AND TRIM(transferencia::text) <> '' "
                + "AND supplier_id = ( "
                + "SELECT uk.key_value::bigint "
                + "FROM public.xxcm_user_keys uk "
                + "WHERE uk.short_name = 'PCSUPPLIERID' "
                + "AND uk.usuario_id = ( "
                + "SELECT u.usuario_id "
                + "FROM public.xxcm_usuarios u "
                + "WHERE u.usuario = ? "
                + ") LIMIT 1 "
                + ") ORDER BY fecha_registro DESC LIMIT 10";

        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String transferencia = rs.getString("transferencia");
                    String fechaRegistro = rs.getString("fecha_registro");
                    transferencia = transferencia == null ? null : transferencia.trim();
                    boolean resultadoPaso = transferencia != null && !transferencia.isEmpty();
                    agregarEvidencia("Usuario consultado: " + usuario + " | Transferencia: "
                            + transferencia + " | Fecha de registro: " + fechaRegistro);
                    registrarResultadoPaso(resultadoPaso,
                            "Transferencia valida obtenida por PCSUPPLIERID",
                            "Esperado: transferencia no vacia | Actual: " + transferencia);
                    return resultadoPaso ? transferencia : null;
                }
            }

            registrarResultadoPaso(false, "Transferencia valida obtenida por PCSUPPLIERID",
                    "La consulta no retorno registros para el usuario " + usuario);
            return null;
        } catch (Exception e) {
            registrarResultadoPaso(false, "Transferencia valida obtenida por PCSUPPLIERID",
                    "Excepcion: " + e.getMessage());
            return null;
        }
    }

    /**
    * Obtiene la transferencia global mas reciente disponible para perfiles con alcance administrativo.
    * La consulta descarta transferencias nulas o vacias y revisa los diez registros mas recientes de xxcm_historico_cedis_view.
     *
     * @return transferencia valida mas reciente, o null cuando no existen datos o la consulta falla.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-22
     */
    public String obtenerTransferenciaValidaGlobal() {
        String sql = "SELECT * "
            + "FROM public.xxcm_historico_cedis_view "
            + "WHERE transferencia IS NOT NULL "
            + "AND TRIM(transferencia::text) <> '' "
            + "ORDER BY fecha_registro DESC LIMIT 10";

        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                String transferencia = rs.getString("transferencia");
                String fechaRegistro = rs.getString("fecha_registro");
                transferencia = transferencia == null ? null : transferencia.trim();
                boolean resultadoPaso = transferencia != null && !transferencia.isEmpty();
                agregarEvidencia("Alcance consultado: ADMIN global | Transferencia: "
                        + transferencia + " | Fecha de registro: " + fechaRegistro);
                registrarResultadoPaso(resultadoPaso,
                        "Transferencia valida global obtenida para perfil ADMIN",
                        "Esperado: transferencia no vacia | Actual: " + transferencia);
                return resultadoPaso ? transferencia : null;
            }

            registrarResultadoPaso(false, "Transferencia valida global obtenida para perfil ADMIN",
                    "La consulta no retorno registros con transferencia valida");
            return null;
        } catch (Exception e) {
            registrarResultadoPaso(false, "Transferencia valida global obtenida para perfil ADMIN",
                    "Excepcion: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene un rango reciente con transferencias disponibles respetando el alcance del perfil del usuario.
     * Para CEDIS filtra por PCSUPPLIERID; para ADMIN u OXXO_OS permite alcance global.
     *
     * @param usuario usuario usado para resolver PCPERFIL y PCSUPPLIERID.
     * @param minimoTransferenciasDistintas cantidad minima de transferencias diferentes requeridas.
     * @return lista con fecha inicial y final en formato dd/MM/yyyy; vacia si no existen datos suficientes.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-22
     */
    public List<String> obtenerRangoHistoricoConDatos(String usuario, int minimoTransferenciasDistintas) {
        String sql = "WITH transferencias_recientes AS ( "
                + "SELECT TRIM(h.transferencia::text) AS transferencia, MAX(h.fecha_registro) AS fecha_registro "
                + "FROM public.xxcm_historico_cedis_view h "
                + "WHERE h.fecha_registro IS NOT NULL "
                + "AND h.transferencia IS NOT NULL AND TRIM(h.transferencia::text) <> '' "
                + "AND (EXISTS (SELECT 1 FROM public.xxcm_user_keys perfil "
                + "JOIN public.xxcm_usuarios u ON u.usuario_id = perfil.usuario_id "
                + "WHERE u.usuario = ? AND perfil.short_name = 'PCPERFIL' "
                + "AND UPPER(TRIM(perfil.key_value)) IN ('ADMIN', 'OXXO_OS')) "
                + "OR h.supplier_id = (SELECT uk.key_value::bigint FROM public.xxcm_user_keys uk "
                + "JOIN public.xxcm_usuarios u ON u.usuario_id = uk.usuario_id "
                + "WHERE u.usuario = ? AND uk.short_name = 'PCSUPPLIERID' LIMIT 1)) "
                + "GROUP BY TRIM(h.transferencia::text) "
                + "ORDER BY MAX(h.fecha_registro) DESC LIMIT ?) "
                + "SELECT MIN(fecha_registro)::date AS fecha_inicio, "
                + "MAX(fecha_registro)::date AS fecha_fin, COUNT(*) AS total_transferencias "
                + "FROM transferencias_recientes HAVING COUNT(*) >= ?";
        List<String> rango = new ArrayList<String>();

        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario);
            statement.setString(2, usuario);
            statement.setInt(3, minimoTransferenciasDistintas);
            statement.setInt(4, minimoTransferenciasDistintas);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    LocalDate fechaMinima = rs.getDate("fecha_inicio").toLocalDate();
                    LocalDate fechaFinal = rs.getDate("fecha_fin").toLocalDate();
                    LocalDate fechaInicial = fechaMinima.minusDays(1);
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    rango.add(fechaInicial.format(formato));
                    rango.add(fechaFinal.format(formato));
                    agregarEvidencia("Usuario: " + usuario + " | Rango historico dinamico: "
                            + rango.get(0) + " a " + rango.get(1) + " | Transferencias distintas: "
                            + rs.getInt("total_transferencias"));
                    registrarResultadoPaso(true, "Rango historico con datos obtenido desde BD", "");
                    return rango;
                }
            }

            registrarResultadoPaso(false, "Rango historico con datos obtenido desde BD",
                    "No existen " + minimoTransferenciasDistintas
                            + " transferencias distintas para el alcance del usuario " + usuario);
        } catch (Exception e) {
            registrarResultadoPaso(false, "Rango historico con datos obtenido desde BD",
                    "Excepcion: " + e.getMessage());
        }
        return rango;
    }

    /**
     * Obtiene una canastilla vigente y consultable respetando el alcance del perfil del usuario.
     * Replica la relacion y los filtros de get_xxcm_cedis_rtd_proyeccion_grid_filtrados: para CEDIS
     * filtra por PCSUPPLIERID y para ADMIN u OXXO_OS permite alcance global.
     *
     * @param usuario usuario usado para resolver PCPERFIL y PCSUPPLIERID.
     * @return codigo de canastilla como texto, o null cuando no existen datos o la consulta falla.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-23
     */
    public String obtenerCanastillaValidaSegunPerfil(String usuario) {
        String sql = "SELECT BTRIM(canastilla.codigo) AS canastilla, p.proveedor_id, p.fecha "
                + "FROM public.xxcm_cedis_rtd_proyeccion p "
                + "JOIN public.xxcm_cedis_rtd_proyeccion_grid g ON g.id = p.id "
                + "CROSS JOIN LATERAL UNNEST(g.codigos_canastilla) AS canastilla(codigo) "
                + "WHERE p.fecha >= (CURRENT_DATE - INTERVAL '3 years') "
                + "AND p.fecha < (CURRENT_DATE + INTERVAL '1 day') "
                + "AND NULLIF(BTRIM(canastilla.codigo), '') IS NOT NULL "
                + "AND (EXISTS (SELECT 1 FROM public.xxcm_user_keys perfil "
                + "JOIN public.xxcm_usuarios u ON u.usuario_id = perfil.usuario_id "
                + "WHERE u.usuario = ? AND perfil.short_name = 'PCPERFIL' "
                + "AND UPPER(TRIM(perfil.key_value)) IN ('ADMIN', 'OXXO_OS')) "
                + "OR p.proveedor_id = (SELECT uk.key_value::bigint FROM public.xxcm_user_keys uk "
                + "JOIN public.xxcm_usuarios u ON u.usuario_id = uk.usuario_id "
                + "WHERE u.usuario = ? AND uk.short_name = 'PCSUPPLIERID' LIMIT 1)) "
                + "ORDER BY p.fecha DESC, p.id DESC LIMIT 1";

        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario);
            statement.setString(2, usuario);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String canastilla = rs.getString("canastilla");
                    boolean resultadoPaso = canastilla != null && !canastilla.trim().isEmpty();
                    agregarEvidencia("Usuario: " + usuario + " | Canastilla: " + canastilla
                            + " | Proveedor: " + rs.getString("proveedor_id")
                            + " | Fecha: " + rs.getString("fecha"));
                    registrarResultadoPaso(resultadoPaso,
                            "Canastilla consultable obtenida segun el alcance del perfil",
                            "Esperado: codigo no vacio | Actual: " + canastilla);
                    return resultadoPaso ? canastilla.trim() : null;
                }
            }

            registrarResultadoPaso(false, "Canastilla consultable obtenida segun el alcance del perfil",
                    "La consulta no retorno canastillas para el usuario " + usuario);
            return null;
        } catch (Exception e) {
            registrarResultadoPaso(false, "Canastilla consultable obtenida segun el alcance del perfil",
                    "Excepcion: " + e.getMessage());
            return null;
        }
    }

    /**
    * Obtiene un mes proyectado con dos transferencias distintas respetando el perfil esperado.
    * Usa la misma tabla, fecha y proveedor que get_xxcm_cedis_rtd_proyeccion_grid_filtrados.
    * Para CEDIS exige PCSUPPLIERID aunque PCPERFIL no exista; para ADMIN valida PCPERFIL y usa alcance global.
     * La lista contiene fecha inicial, fecha final, Transferencia A y Transferencia B, en ese orden.
     *
     * @param usuario usuario utilizado para resolver PCPERFIL y PCSUPPLIERID.
     * @param perfilEsperado perfil CEDIS o ADMIN requerido por el caso.
     * @return lista de cuatro valores o lista vacia cuando el perfil, alcance o datos no son validos.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-23
     */
    public List<String> obtenerDosTransferenciasSegunPerfil(String usuario, String perfilEsperado) {
        String sql = "WITH configuracion AS ( "
                + "SELECT UPPER(TRIM(MAX(CASE WHEN uk.short_name = 'PCPERFIL' THEN uk.key_value END))) AS perfil, "
                + "MAX(CASE WHEN uk.short_name = 'PCSUPPLIERID' THEN uk.key_value END) AS supplier_id "
                + "FROM public.xxcm_usuarios u "
                + "LEFT JOIN public.xxcm_user_keys uk ON uk.usuario_id = u.usuario_id "
                + "WHERE u.usuario = ?), "
                + "datos_alcance AS ( "
                + "SELECT TRIM(p.transferencia::text) AS transferencia, p.fecha AS fecha_registro, "
                + "DATE_TRUNC('month', p.fecha)::date AS mes, "
                + "CASE WHEN ? = 'CEDIS' THEN 'CEDIS' ELSE c.perfil END AS perfil, c.supplier_id "
                + "FROM public.xxcm_cedis_rtd_proyeccion p CROSS JOIN configuracion c "
                + "WHERE p.fecha IS NOT NULL "
                + "AND p.fecha >= CURRENT_DATE - INTERVAL '3 years' "
                + "AND p.fecha < CURRENT_DATE + INTERVAL '1 day' "
                + "AND p.transferencia IS NOT NULL AND TRIM(p.transferencia::text) <> '' "
                + "AND ((? = 'ADMIN' AND c.perfil = 'ADMIN') "
                + "OR (? = 'CEDIS' AND c.supplier_id ~ '^[0-9]+$' "
                + "AND p.proveedor_id = c.supplier_id::bigint))), "
                + "mes_elegido AS ( "
                + "SELECT mes FROM datos_alcance GROUP BY mes "
                + "HAVING COUNT(DISTINCT transferencia) >= 2 ORDER BY mes DESC LIMIT 1), "
                + "transferencias_recientes AS ( "
                + "SELECT transferencia, MAX(fecha_registro) AS fecha_registro, "
                + "MAX(perfil) AS perfil, MAX(supplier_id) AS supplier_id "
                + "FROM datos_alcance d JOIN mes_elegido m ON m.mes = d.mes "
                + "GROUP BY transferencia "
                + "ORDER BY MAX(fecha_registro) DESC LIMIT 2), "
                + "rango AS (SELECT m.mes AS fecha_inicio, "
                + "LEAST((m.mes + INTERVAL '1 month - 1 day')::date, CURRENT_DATE) AS fecha_fin "
                + "FROM mes_elegido m) "
                + "SELECT r.fecha_inicio, r.fecha_fin, t.transferencia, t.fecha_registro, "
                + "t.perfil, t.supplier_id FROM rango r CROSS JOIN transferencias_recientes t "
                + "ORDER BY t.fecha_registro DESC";
        List<String> datos = new ArrayList<String>();
        String perfilNormalizado = perfilEsperado == null ? "" : perfilEsperado.trim().toUpperCase();

        if (!"CEDIS".equals(perfilNormalizado) && !"ADMIN".equals(perfilNormalizado)) {
            registrarResultadoPaso(false, "Dos transferencias obtenidas segun perfil",
                    "Perfil esperado no soportado: " + perfilEsperado);
            return datos;
        }

        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario);
            statement.setString(2, perfilNormalizado);
            statement.setString(3, perfilNormalizado);
            statement.setString(4, perfilNormalizado);
            try (ResultSet rs = statement.executeQuery()) {
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechaInicio = null;
                String fechaFin = null;
                String perfilResuelto = null;
                String supplierId = null;
                List<String> transferencias = new ArrayList<String>();
                while (rs.next()) {
                    if (fechaInicio == null) {
                        fechaInicio = rs.getDate("fecha_inicio").toLocalDate().format(formato);
                        fechaFin = rs.getDate("fecha_fin").toLocalDate().format(formato);
                        perfilResuelto = rs.getString("perfil");
                        supplierId = rs.getString("supplier_id");
                    }
                    String transferencia = rs.getString("transferencia");
                    if (transferencia != null && !transferencia.trim().isEmpty()
                            && !transferencias.contains(transferencia.trim())) {
                        transferencias.add(transferencia.trim());
                    }
                }

                boolean perfilValido = perfilNormalizado.equals(perfilResuelto);
                boolean supplierValido = !"CEDIS".equals(perfilNormalizado)
                        || (supplierId != null && !supplierId.trim().isEmpty());
                boolean resultadoPaso = perfilValido && supplierValido && transferencias.size() == 2
                        && !transferencias.get(0).equals(transferencias.get(1));
                if (resultadoPaso) {
                    datos.add(fechaInicio);
                    datos.add(fechaFin);
                    datos.add(transferencias.get(0));
                    datos.add(transferencias.get(1));
                    agregarEvidencia("Usuario: " + usuario + " | Perfil: " + perfilResuelto
                            + " | Alcance: " + ("CEDIS".equals(perfilNormalizado)
                                    ? "PCSUPPLIERID=" + supplierId : "ADMIN global")
                            + " | Rango: " + fechaInicio + " a " + fechaFin
                            + " | Transferencia A: " + transferencias.get(0)
                            + " | Transferencia B: " + transferencias.get(1));
                }
                registrarResultadoPaso(resultadoPaso, "Dos transferencias obtenidas segun perfil",
                        "Esperado: perfil " + perfilNormalizado
                                + " y dos transferencias distintas | Perfil actual: " + perfilResuelto
                                + " | Supplier: " + supplierId + " | Transferencias: " + transferencias);
            }
        } catch (Exception e) {
            registrarResultadoPaso(false, "Dos transferencias obtenidas segun perfil",
                    "Excepcion: " + e.getMessage());
        }
        return datos;
    }

    /**
     * Obtiene una transferencia proyectada y consultable con alcance global para perfil ADMIN.
     * Usa la misma fuente de transferencia, fecha y vigencia que el backend del grid de Recepcion.
     *
     * @return transferencia proyectada no vacia o null cuando no existen datos consultables.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-23
     */
    public String obtenerTransferenciaProyectadaGlobal() {
        String sql = "SELECT TRIM(p.transferencia::text) AS transferencia, p.fecha "
                + "FROM public.xxcm_cedis_rtd_proyeccion p "
                + "WHERE p.fecha >= CURRENT_DATE - INTERVAL '3 years' "
                + "AND p.fecha < CURRENT_DATE + INTERVAL '1 day' "
                + "AND p.transferencia IS NOT NULL "
                + "AND TRIM(p.transferencia::text) <> '' "
                + "ORDER BY p.fecha DESC, p.id DESC LIMIT 1";

        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                String transferencia = rs.getString("transferencia");
                transferencia = transferencia == null ? null : transferencia.trim();
                boolean resultadoPaso = transferencia != null && !transferencia.isEmpty();
                agregarEvidencia("Alcance consultado: ADMIN global | Transferencia proyectada: "
                        + transferencia + " | Fecha: " + rs.getString("fecha"));
                registrarResultadoPaso(resultadoPaso,
                        "Transferencia proyectada global obtenida para perfil ADMIN",
                        "Esperado: transferencia no vacia | Actual: " + transferencia);
                return resultadoPaso ? transferencia : null;
            }

            registrarResultadoPaso(false,
                    "Transferencia proyectada global obtenida para perfil ADMIN",
                    "La consulta no retorno transferencias proyectadas consultables");
            return null;
        } catch (Exception e) {
            registrarResultadoPaso(false,
                    "Transferencia proyectada global obtenida para perfil ADMIN",
                    "Excepcion: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene una Plaza con datos del mes actual desde la fuente de proyeccion del grid.
     * Para CEDIS filtra por PCSUPPLIERID; para ADMIN y OXXO_OS permite alcance global.
     *
     * @param usuario usuario de Portal COMETA usado para resolver el alcance.
     * @return texto de Plaza consultable en el grid, o null cuando no hay datos en el mes actual.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-27
     */
    public String obtenerPlazaHistoricaConDatos(String usuario) {
        String sql = "WITH configuracion AS ( "
                + "SELECT UPPER(TRIM(MAX(CASE WHEN uk.short_name = 'PCPERFIL' THEN uk.key_value END))) AS perfil, "
                + "MAX(CASE WHEN uk.short_name = 'PCSUPPLIERID' THEN uk.key_value END) AS supplier_id "
                + "FROM public.xxcm_usuarios u "
                + "LEFT JOIN public.xxcm_user_keys uk ON uk.usuario_id = u.usuario_id "
                + "WHERE u.usuario = ?), "
                + "plazas_validas AS ( "
                + "SELECT TRIM(p.plaza::text) AS plaza, MAX(p.fecha)::date AS fecha_registro, COUNT(*) AS filas "
                + "FROM public.xxcm_cedis_rtd_proyeccion p CROSS JOIN configuracion c "
                + "WHERE p.plaza IS NOT NULL AND TRIM(p.plaza::text) <> '' "
                + "AND p.fecha IS NOT NULL "
                + "AND p.fecha::date BETWEEN date_trunc('month', current_date)::date AND current_date "
                + "AND ((c.perfil IN ('ADMIN','OXXO_OS')) "
                + "OR (c.supplier_id ~ '^[0-9]+$' AND p.proveedor_id = c.supplier_id::bigint)) "
                + "GROUP BY TRIM(p.plaza::text)) "
                + "SELECT plaza, fecha_registro, filas FROM plazas_validas "
                + "ORDER BY fecha_registro DESC, filas DESC LIMIT 1";

        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, usuario);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String plaza = rs.getString("plaza");
                    String fechaRegistro = rs.getString("fecha_registro");
                    long filas = rs.getLong("filas");
                    plaza = plaza == null ? null : plaza.trim();
                    boolean resultadoPaso = plaza != null && !plaza.isEmpty();
                    agregarEvidencia("Usuario: " + usuario + " | Plaza para grid (mes actual): " + plaza
                            + " | Fecha de referencia: " + fechaRegistro + " | Filas mes actual: " + filas);
                    registrarResultadoPaso(resultadoPaso,
                            "Plaza para grid obtenida segun alcance del perfil",
                            "Esperado: plaza no vacia | Actual: " + plaza);
                    return resultadoPaso ? plaza : null;
                }
            }

            registrarResultadoPaso(false,
                    "Plaza para grid obtenida segun alcance del perfil",
                    "La consulta no retorno plazas con datos del mes actual para el usuario " + usuario);
            return null;
        } catch (Exception e) {
            registrarResultadoPaso(false,
                    "Plaza para grid obtenida segun alcance del perfil",
                    "Excepcion: " + e.getMessage());
            return null;
        }
    }

    /**
     * Valida que una columna especifica exista en una tabla de la base de datos.
     * Consulta information_schema.columns filtrando por nombre de tabla y nombre de columna.
     *
     * @param tableName  nombre de la tabla a inspeccionar.
     * @param columnName nombre de la columna a buscar.
     * @return true si la columna existe en la tabla; false si no existe o ocurre un error.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    public boolean validarColumnaEnTabla(String tableName, String columnName) {
        String sql = "SELECT column_name FROM information_schema.columns "
                + "WHERE table_name = ? AND column_name = ?";
        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableName);
            statement.setString(2, columnName);
            try (ResultSet rs = statement.executeQuery()) {
                boolean existe = rs.next();
                registrarResultadoPaso(existe,
                        "Columna " + columnName + " en tabla " + tableName,
                        "La columna " + columnName + " no existe en " + tableName);
                return existe;
            }
        } catch (Exception e) {
            registrarResultadoPaso(false,
                    "Columna " + columnName + " en tabla " + tableName,
                    "Excepcion: " + e.getMessage());
            return false;
        }
    }

    /**
     * Valida que el tipo de dato de una columna sea numerico (numeric, decimal, float, real, double precision, integer, bigint, smallint).
     * Consulta information_schema.columns para obtener el data_type de la columna indicada.
     *
     * @param tableName  nombre de la tabla.
     * @param columnName nombre de la columna a validar.
     * @return true si el data_type es numerico; false si no lo es o ocurre un error.
     *
     * @author Michelle Ceja
     * @date 2026-08-06
     */
    public boolean validarTipoDatoNumerico(String tableName, String columnName) {
        String sql = "SELECT data_type FROM information_schema.columns "
                + "WHERE table_name = ? AND column_name = ?";
        try (Connection connection = abrirConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableName);
            statement.setString(2, columnName);
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) {
                    registrarResultadoPaso(false,
                            "Tipo de dato numerico columna " + columnName,
                            "La columna " + columnName + " no existe en " + tableName);
                    return false;
                }
                String dataType = rs.getString("data_type").toLowerCase();
                boolean esNumerico = dataType.contains("numeric") || dataType.contains("decimal")
                        || dataType.contains("float") || dataType.contains("real")
                        || dataType.contains("double") || dataType.contains("integer")
                        || dataType.contains("bigint") || dataType.contains("smallint");
                registrarResultadoPaso(esNumerico,
                        "Tipo de dato numerico columna " + columnName + " (tipo: " + dataType + ")",
                        "El tipo de dato '" + dataType + "' de la columna " + columnName + " no es numerico");
                return esNumerico;
            }
        } catch (Exception e) {
            registrarResultadoPaso(false,
                    "Tipo de dato numerico columna " + columnName,
                    "Excepcion: " + e.getMessage());
            return false;
        }
    }
}