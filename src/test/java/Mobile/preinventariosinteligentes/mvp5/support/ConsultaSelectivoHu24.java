package Mobile.preinventariosinteligentes.mvp5.support;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

/**
 * Consultas de solo lectura para elegir y validar el selectivo usado por HU24.
 */
public final class ConsultaSelectivoHu24 {
    private final Supplier<Connection> proveedorConexion;

    public ConsultaSelectivoHu24(Supplier<Connection> proveedorConexion) {
        this.proveedorConexion = proveedorConexion;
    }

    /** Busca el selectivo asignado mas grande que no exceda quince SKU. */
    public Escenario consultarAsignadoMaximo15(String colaborador) {
        String sql = "SELECT TOP 1 h.IO_Folio,"
                + "UPPER(LTRIM(RTRIM(SUBSTRING(h.IO_Description,"
                + "CHARINDEX('_',h.IO_Description)+1,LEN(h.IO_Description))))) SELECTIVO,"
                + "COUNT(*) TOTAL "
                + "FROM hc.IO_HEADER h JOIN hc.IO_DETAIL d ON d.IO_Folio=h.IO_Folio "
                + "JOIN hc.II_HEADER_IO_HEADER m ON m.IO_FOLIO=h.IO_Folio "
                + "WHERE m.II_FOLIO=(SELECT MAX(II_FOLIO) FROM hc.II_HEADER WHERE II_TYPE='I') "
                + "AND EXISTS (SELECT 1 FROM hc.II_ASSIGNMENTROLE a "
                + "WHERE a.IO_FOLIO=h.IO_Folio AND EXISTS ("
                + "SELECT 1 FROM hc.II_STORE_TEMPLATE_DETAIL t "
                + "WHERE t.COLLABORATOR_ID=a.II_OPERATOR_ID "
                + "AND t.COLLABORATOR_NAME LIKE ?)) "
                + "GROUP BY h.IO_Folio,h.IO_Description "
                + "HAVING COUNT(*) BETWEEN 1 AND 15 "
                + "AND SUM(CASE WHEN d.IO_OnHand>0 "
                + "AND d.IO_OnHandReal IS NULL THEN 1 ELSE 0 END)>0 "
                + "ORDER BY COUNT(*) DESC,h.IO_Folio";
        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, colaborador + "%");
            try (ResultSet filas = sentencia.executeQuery()) {
                if (!filas.next()) {
                    return null;
                }
                return new Escenario(
                        filas.getInt("IO_Folio"),
                        filas.getString("SELECTIVO"),
                        filas.getInt("TOTAL"));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible consultar el selectivo HU24", exception);
        }
    }

    /** Variante de preflight sin dependencia del colaborador visible en UI. */
    public Escenario consultarAsignadoMaximo15SinFiltro() {
        return consultarAsignadoConRangoSinFiltro(1, 15);
    }

    /**
     * Variante de preflight sin filtro de colaborador y con rango configurable.
     * Si maximoSku es menor o igual a cero, no se aplica limite superior.
     */
    public Escenario consultarAsignadoConRangoSinFiltro(int minimoSku, int maximoSku) {
        if (minimoSku <= 0) {
            throw new IllegalArgumentException("minimoSku debe ser mayor a cero");
        }

        String havingRango = maximoSku > 0
                ? "COUNT(*) BETWEEN ? AND ? "
                : "COUNT(*) >= ? ";
        String sql = "SELECT TOP 1 h.IO_Folio,"
                + "UPPER(LTRIM(RTRIM(SUBSTRING(h.IO_Description,"
                + "CHARINDEX('_',h.IO_Description)+1,LEN(h.IO_Description))))) SELECTIVO,"
                + "COUNT(*) TOTAL "
                + "FROM hc.IO_HEADER h JOIN hc.IO_DETAIL d ON d.IO_Folio=h.IO_Folio "
                + "JOIN hc.II_HEADER_IO_HEADER m ON m.IO_FOLIO=h.IO_Folio "
                + "WHERE m.II_FOLIO=(SELECT MAX(II_FOLIO) FROM hc.II_HEADER WHERE II_TYPE='I') "
                + "GROUP BY h.IO_Folio,h.IO_Description "
                + "HAVING " + havingRango
                + "AND SUM(CASE WHEN d.IO_OnHand>0 "
                + "AND d.IO_OnHandReal IS NULL THEN 1 ELSE 0 END)>0 "
                + "ORDER BY COUNT(*) ASC,h.IO_Folio";
        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, minimoSku);
            if (maximoSku > 0) {
                sentencia.setInt(2, maximoSku);
            }
            try (ResultSet filas = sentencia.executeQuery()) {
            if (!filas.next()) {
                return null;
            }
            return new Escenario(
                    filas.getInt("IO_Folio"),
                    filas.getString("SELECTIVO"),
                    filas.getInt("TOTAL"));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible consultar el selectivo HU24 sin filtro de colaborador y con rango",
                    exception);
        }
    }

    /**
     * Variante sin filtro de colaborador que prioriza selectivos cercanos a una
     * letra objetivo para reducir desplazamiento en UI.
     */
    public Escenario consultarAsignadoCercanoALetraSinFiltro(
            String letraReferencia,
            int minimoSku,
            int maximoSku) {
        if (minimoSku <= 0) {
            throw new IllegalArgumentException("minimoSku debe ser mayor a cero");
        }
        if (letraReferencia == null || letraReferencia.trim().isEmpty()) {
            throw new IllegalArgumentException("letraReferencia no puede estar vacia");
        }

        String letra = letraReferencia.trim().toUpperCase().substring(0, 1);
        String havingRango = maximoSku > 0
                ? "COUNT(*) BETWEEN ? AND ? "
                : "COUNT(*) >= ? ";

        String sql = "WITH candidatos AS ("
                + "SELECT h.IO_Folio,"
                + "UPPER(LTRIM(RTRIM(SUBSTRING(h.IO_Description,"
                + "CHARINDEX('_',h.IO_Description)+1,LEN(h.IO_Description))))) SELECTIVO,"
                + "COUNT(*) TOTAL "
                + "FROM hc.IO_HEADER h JOIN hc.IO_DETAIL d ON d.IO_Folio=h.IO_Folio "
                + "JOIN hc.II_HEADER_IO_HEADER m ON m.IO_FOLIO=h.IO_Folio "
                + "WHERE m.II_FOLIO=(SELECT MAX(II_FOLIO) FROM hc.II_HEADER WHERE II_TYPE='I') "
                + "GROUP BY h.IO_Folio,h.IO_Description "
                + "HAVING " + havingRango
                + "AND SUM(CASE WHEN d.IO_OnHand>0 "
                + "AND d.IO_OnHandReal IS NULL THEN 1 ELSE 0 END)>0) "
                + "SELECT TOP 1 IO_Folio,SELECTIVO,TOTAL FROM candidatos "
                + "ORDER BY "
                + "CASE "
                + "WHEN SELECTIVO LIKE ? + '%' THEN 0 "
                + "WHEN SELECTIVO LIKE '[A-Z]%' THEN ABS(ASCII(LEFT(SELECTIVO,1)) - ASCII(?)) "
                + "ELSE 999 END,"
                + "SELECTIVO ASC,TOTAL ASC,IO_Folio";

        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, minimoSku);
            int indice = 2;
            if (maximoSku > 0) {
                sentencia.setInt(indice, maximoSku);
                indice++;
            }
            sentencia.setString(indice, letra);
            sentencia.setString(indice + 1, letra);

            try (ResultSet filas = sentencia.executeQuery()) {
                if (!filas.next()) {
                    return null;
                }
                return new Escenario(
                        filas.getInt("IO_Folio"),
                        filas.getString("SELECTIVO"),
                        filas.getInt("TOTAL"));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible consultar el selectivo HU24 cercano a letra sin filtro",
                    exception);
        }
    }

    /** Busca varios selectivos distintos, pequenos y con pendientes positivos. */
    public List<Escenario> consultarAsignadosMaximo15(
            String colaborador,
            int cantidad) {
        String sql = "SELECT TOP (?) h.IO_Folio,"
                + "UPPER(LTRIM(RTRIM(SUBSTRING(h.IO_Description,"
                + "CHARINDEX('_',h.IO_Description)+1,LEN(h.IO_Description))))) SELECTIVO,"
                + "COUNT(*) TOTAL "
                + "FROM hc.IO_HEADER h JOIN hc.IO_DETAIL d ON d.IO_Folio=h.IO_Folio "
                + "JOIN hc.II_HEADER_IO_HEADER m ON m.IO_FOLIO=h.IO_Folio "
                + "WHERE m.II_FOLIO=(SELECT MAX(II_FOLIO) FROM hc.II_HEADER WHERE II_TYPE='I') "
                + "AND EXISTS (SELECT 1 FROM hc.II_ASSIGNMENTROLE a "
                + "WHERE a.IO_FOLIO=h.IO_Folio AND EXISTS ("
                + "SELECT 1 FROM hc.II_STORE_TEMPLATE_DETAIL t "
                + "WHERE t.COLLABORATOR_ID=a.II_OPERATOR_ID "
                + "AND t.COLLABORATOR_NAME LIKE ?)) "
                + "GROUP BY h.IO_Folio,h.IO_Description "
                + "HAVING COUNT(*) BETWEEN 1 AND 15 "
                + "AND SUM(CASE WHEN d.IO_OnHand>0 "
                + "AND d.IO_OnHandReal IS NULL THEN 1 ELSE 0 END)>0 "
                + "ORDER BY COUNT(*) ASC,h.IO_Folio";
        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, cantidad);
            sentencia.setString(2, colaborador + "%");
            try (ResultSet filas = sentencia.executeQuery()) {
                List<Escenario> escenarios = new ArrayList<>();
                while (filas.next()) {
                    escenarios.add(new Escenario(
                            filas.getInt("IO_Folio"),
                            filas.getString("SELECTIVO"),
                            filas.getInt("TOTAL")));
                }
                return escenarios;
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible consultar varios selectivos HU24", exception);
        }
    }

    /**
     * Variante backend-first sin filtro de colaborador para obtener varios
     * selectivos asignados con pendientes positivos y rango configurable.
     */
    public List<Escenario> consultarAsignadosConRangoSinFiltro(
            int cantidad,
            int minimoSku,
            int maximoSku) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("cantidad debe ser mayor a cero");
        }
        if (minimoSku <= 0) {
            throw new IllegalArgumentException("minimoSku debe ser mayor a cero");
        }

        String havingRango = maximoSku > 0
                ? "COUNT(*) BETWEEN ? AND ? "
                : "COUNT(*) >= ? ";

        String sql = "SELECT TOP (?) h.IO_Folio,"
                + "UPPER(LTRIM(RTRIM(SUBSTRING(h.IO_Description,"
                + "CHARINDEX('_',h.IO_Description)+1,LEN(h.IO_Description))))) SELECTIVO,"
                + "COUNT(*) TOTAL "
                + "FROM hc.IO_HEADER h JOIN hc.IO_DETAIL d ON d.IO_Folio=h.IO_Folio "
                + "JOIN hc.II_HEADER_IO_HEADER m ON m.IO_FOLIO=h.IO_Folio "
                + "WHERE m.II_FOLIO=(SELECT MAX(II_FOLIO) FROM hc.II_HEADER WHERE II_TYPE='I') "
                + "GROUP BY h.IO_Folio,h.IO_Description "
                + "HAVING " + havingRango
                + "AND SUM(CASE WHEN d.IO_OnHand>0 "
                + "AND d.IO_OnHandReal IS NULL THEN 1 ELSE 0 END)>0 "
                + "ORDER BY COUNT(*) ASC,h.IO_Folio";
        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, cantidad);
            sentencia.setInt(2, minimoSku);
            if (maximoSku > 0) {
                sentencia.setInt(3, maximoSku);
            }
            try (ResultSet filas = sentencia.executeQuery()) {
                List<Escenario> escenarios = new ArrayList<>();
                while (filas.next()) {
                    escenarios.add(new Escenario(
                            filas.getInt("IO_Folio"),
                            filas.getString("SELECTIVO"),
                            filas.getInt("TOTAL")));
                }
                return escenarios;
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible consultar varios selectivos HU24 sin filtro de colaborador",
                    exception);
        }
    }

    /** Obtiene los SKU positivos que aun requieren captura, en orden funcional. */
    public CachedRowSet consultarPendientesPositivos(int folio) {
        String sql = "SELECT TRIM(IO_SKU_REF) SKU FROM hc.IO_DETAIL "
                + "WHERE IO_Folio=? AND IO_OnHand>0 "
                + "AND IO_OnHandReal IS NULL ORDER BY IO_SKU_Order";
        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, folio);
            try (ResultSet filas = sentencia.executeQuery()) {
                CachedRowSet pendientes = RowSetProvider.newFactory()
                        .createCachedRowSet();
                pendientes.populate(filas);
                pendientes.beforeFirst();
                return pendientes;
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible consultar los SKU pendientes de HU24", exception);
        }
    }

    /** Cuenta estados incompatibles con un selectivo completamente contabilizado. */
    public int contarInconsistenciasFinales(int folio) {
        String sql = "SELECT COUNT(*) FROM hc.IO_DETAIL WHERE IO_Folio=? AND ("
                + "IO_OnHand IS NULL OR IO_OnHand<0 OR "
                + "(IO_OnHand=0 AND IO_OnHandReal IS NOT NULL) OR "
                + "(IO_OnHand>0 AND IO_OnHandReal IS NULL))";
        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, folio);
            try (ResultSet filas = sentencia.executeQuery()) {
                filas.next();
                return filas.getInt(1);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible validar el estado final de HU24", exception);
        }
    }

    /**
     * Elige un SKU con captura previa de la distribucion inteligente vigente,
     * asignado al colaborador visible y sin duplicidad previa folio/SKU.
     */
    public EscenarioEdicion consultarSkuCapturado(String colaborador) {
        String sql = "WITH candidatos AS ("
                + "SELECT h.IO_Folio,"
                + "UPPER(LTRIM(RTRIM(SUBSTRING(h.IO_Description,"
                + "CHARINDEX('_',h.IO_Description)+1,LEN(h.IO_Description))))) SELECTIVO,"
                + "(SELECT COUNT(*) FROM hc.IO_DETAIL total "
                + "WHERE total.IO_Folio=h.IO_Folio) TOTAL,"
                + "TRIM(d.IO_SKU_REF) SKU,d.IO_OnHandReal FISICO,"
                + "(SELECT COUNT(*) FROM hc.IO_DETAIL repetido "
                + "WHERE repetido.IO_Folio=h.IO_Folio "
                + "AND repetido.IO_SKU_REF=d.IO_SKU_REF) REPETICIONES,"
                + "d.IO_SKU_Order "
                + "FROM hc.IO_HEADER h JOIN hc.IO_DETAIL d ON d.IO_Folio=h.IO_Folio "
                + "JOIN hc.II_HEADER_IO_HEADER m ON m.IO_FOLIO=h.IO_Folio "
                + "WHERE m.II_FOLIO=(SELECT MAX(II_FOLIO) FROM hc.II_HEADER WHERE II_TYPE='I') "
                + "AND d.IO_OnHandReal IS NOT NULL "
                + "AND EXISTS (SELECT 1 FROM hc.II_ASSIGNMENTROLE a "
                + "WHERE a.IO_FOLIO=h.IO_Folio AND EXISTS ("
                + "SELECT 1 FROM hc.II_STORE_TEMPLATE_DETAIL t "
                + "WHERE t.COLLABORATOR_ID=a.II_OPERATOR_ID "
                + "AND t.COLLABORATOR_NAME LIKE ?))) "
                + "SELECT TOP 1 IO_Folio,SELECTIVO,TOTAL,SKU,FISICO "
                + "FROM candidatos WHERE REPETICIONES=1 "
                + "ORDER BY SELECTIVO,IO_Folio,IO_SKU_Order,SKU";
        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, colaborador + "%");
            try (ResultSet filas = sentencia.executeQuery()) {
                if (!filas.next()) {
                    return null;
                }
                return new EscenarioEdicion(
                        filas.getInt("IO_Folio"),
                        filas.getString("SELECTIVO"),
                        filas.getInt("TOTAL"),
                        filas.getString("SKU"),
                        filas.getBigDecimal("FISICO"));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible consultar un SKU capturado de HU24", exception);
        }
    }

    /** Variante de preflight sin dependencia del colaborador visible en UI. */
    public EscenarioEdicion consultarSkuCapturadoSinFiltro() {
        String sql = "WITH candidatos AS ("
                + "SELECT h.IO_Folio,"
                + "UPPER(LTRIM(RTRIM(SUBSTRING(h.IO_Description,"
                + "CHARINDEX('_',h.IO_Description)+1,LEN(h.IO_Description))))) SELECTIVO,"
                + "(SELECT COUNT(*) FROM hc.IO_DETAIL total "
                + "WHERE total.IO_Folio=h.IO_Folio) TOTAL,"
                + "TRIM(d.IO_SKU_REF) SKU,d.IO_OnHandReal FISICO,"
                + "(SELECT COUNT(*) FROM hc.IO_DETAIL repetido "
                + "WHERE repetido.IO_Folio=h.IO_Folio "
                + "AND repetido.IO_SKU_REF=d.IO_SKU_REF) REPETICIONES,"
                + "d.IO_SKU_Order "
                + "FROM hc.IO_HEADER h JOIN hc.IO_DETAIL d ON d.IO_Folio=h.IO_Folio "
                + "JOIN hc.II_HEADER_IO_HEADER m ON m.IO_FOLIO=h.IO_Folio "
                + "WHERE m.II_FOLIO=(SELECT MAX(II_FOLIO) FROM hc.II_HEADER WHERE II_TYPE='I') "
                + "AND d.IO_OnHandReal IS NOT NULL) "
                + "SELECT TOP 1 IO_Folio,SELECTIVO,TOTAL,SKU,FISICO "
                + "FROM candidatos WHERE REPETICIONES=1 "
                + "ORDER BY SELECTIVO,IO_Folio,IO_SKU_Order,SKU";
        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet filas = sentencia.executeQuery()) {
            if (!filas.next()) {
                return null;
            }
            return new EscenarioEdicion(
                    filas.getInt("IO_Folio"),
                    filas.getString("SELECTIVO"),
                    filas.getInt("TOTAL"),
                    filas.getString("SKU"),
                    filas.getBigDecimal("FISICO"));
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible consultar un SKU capturado HU24 sin filtro de colaborador",
                    exception);
        }
    }

    /** Consulta el valor persistido y la cantidad de filas del mismo folio/SKU. */
    public PersistenciaSku consultarPersistenciaSku(int folio, String sku) {
        String sql = "SELECT COUNT(*) REPETICIONES,MAX(IO_OnHandReal) FISICO "
                + "FROM hc.IO_DETAIL WHERE IO_Folio=? AND TRIM(IO_SKU_REF)=?";
        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, folio);
            sentencia.setString(2, sku);
            try (ResultSet filas = sentencia.executeQuery()) {
                filas.next();
                return new PersistenciaSku(
                        filas.getInt("REPETICIONES"),
                        filas.getBigDecimal("FISICO"));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible validar la persistencia del SKU HU24", exception);
        }
    }

    /**
     * Elige un selectivo pequeno totalmente pendiente y su primer SKU.
     * La consulta solo considera la distribucion vigente y el colaborador visible.
     */
    public EscenarioParcial consultarTotalmentePendiente(String colaborador) {
        String sql = "SELECT TOP 1 h.IO_Folio,"
                + "UPPER(LTRIM(RTRIM(SUBSTRING(h.IO_Description,"
                + "CHARINDEX('_',h.IO_Description)+1,LEN(h.IO_Description))))) SELECTIVO,"
                + "COUNT(*) TOTAL,"
                + "(SELECT TOP 1 TRIM(primero.IO_SKU_REF) FROM hc.IO_DETAIL primero "
                + "WHERE primero.IO_Folio=h.IO_Folio "
                + "ORDER BY primero.IO_SKU_Order,primero.IO_SKU_REF) PRIMER_SKU "
                + "FROM hc.IO_HEADER h JOIN hc.IO_DETAIL d ON d.IO_Folio=h.IO_Folio "
                + "JOIN hc.II_HEADER_IO_HEADER m ON m.IO_FOLIO=h.IO_Folio "
                + "WHERE m.II_FOLIO=(SELECT MAX(II_FOLIO) FROM hc.II_HEADER WHERE II_TYPE='I') "
                + "AND EXISTS (SELECT 1 FROM hc.II_ASSIGNMENTROLE a "
                + "WHERE a.IO_FOLIO=h.IO_Folio AND EXISTS ("
                + "SELECT 1 FROM hc.II_STORE_TEMPLATE_DETAIL t "
                + "WHERE t.COLLABORATOR_ID=a.II_OPERATOR_ID "
                + "AND t.COLLABORATOR_NAME LIKE ?)) "
                + "GROUP BY h.IO_Folio,h.IO_Description "
                + "HAVING COUNT(*) BETWEEN 2 AND 15 "
                + "AND SUM(CASE WHEN d.IO_OnHandReal IS NOT NULL THEN 1 ELSE 0 END)=0 "
                + "AND SUM(CASE WHEN d.IO_OnHand IS NULL OR d.IO_OnHand<=0 THEN 1 ELSE 0 END)=0 "
                + "ORDER BY SELECTIVO,h.IO_Folio";
        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, colaborador + "%");
            try (ResultSet filas = sentencia.executeQuery()) {
                if (!filas.next()) {
                    return null;
                }
                return new EscenarioParcial(
                        filas.getInt("IO_Folio"),
                        filas.getString("SELECTIVO"),
                        filas.getInt("TOTAL"),
                        filas.getString("PRIMER_SKU"));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible consultar el escenario parcial HU24", exception);
        }
    }

    /** Variante parcial sin dependencia del colaborador visible en UI. */
    public EscenarioParcial consultarTotalmentePendienteSinFiltro() {
        return consultarTotalmentePendienteCercanoALetraSinFiltro("A", 2, 15);
    }

    /**
     * Variante parcial sin filtro de colaborador y con prioridad por letra para
     * reducir desplazamiento en UI.
     */
    public EscenarioParcial consultarTotalmentePendienteCercanoALetraSinFiltro(
            String letraReferencia,
            int minimoSku,
            int maximoSku) {
        if (minimoSku <= 0) {
            throw new IllegalArgumentException("minimoSku debe ser mayor a cero");
        }
        if (letraReferencia == null || letraReferencia.trim().isEmpty()) {
            throw new IllegalArgumentException("letraReferencia no puede estar vacia");
        }

        String letra = letraReferencia.trim().toUpperCase().substring(0, 1);
        String havingRango = maximoSku > 0
                ? "COUNT(*) BETWEEN ? AND ? "
                : "COUNT(*) >= ? ";

        String sql = "WITH candidatos AS ("
                + "SELECT h.IO_Folio,"
                + "UPPER(LTRIM(RTRIM(SUBSTRING(h.IO_Description,"
                + "CHARINDEX('_',h.IO_Description)+1,LEN(h.IO_Description))))) SELECTIVO,"
                + "COUNT(*) TOTAL,"
                + "(SELECT TOP 1 TRIM(primero.IO_SKU_REF) FROM hc.IO_DETAIL primero "
                + "WHERE primero.IO_Folio=h.IO_Folio "
                + "ORDER BY primero.IO_SKU_Order,primero.IO_SKU_REF) PRIMER_SKU "
                + "FROM hc.IO_HEADER h JOIN hc.IO_DETAIL d ON d.IO_Folio=h.IO_Folio "
                + "JOIN hc.II_HEADER_IO_HEADER m ON m.IO_FOLIO=h.IO_Folio "
                + "WHERE m.II_FOLIO=(SELECT MAX(II_FOLIO) FROM hc.II_HEADER WHERE II_TYPE='I') "
                + "GROUP BY h.IO_Folio,h.IO_Description "
                + "HAVING " + havingRango
                + "AND SUM(CASE WHEN d.IO_OnHandReal IS NOT NULL THEN 1 ELSE 0 END)=0 "
                + "AND SUM(CASE WHEN d.IO_OnHand IS NULL OR d.IO_OnHand<=0 THEN 1 ELSE 0 END)=0) "
                + "SELECT TOP 1 IO_Folio,SELECTIVO,TOTAL,PRIMER_SKU FROM candidatos "
                + "ORDER BY "
                + "CASE "
                + "WHEN SELECTIVO LIKE ? + '%' THEN 0 "
                + "WHEN SELECTIVO LIKE '[A-Z]%' THEN ABS(ASCII(LEFT(SELECTIVO,1)) - ASCII(?)) "
                + "ELSE 999 END,"
                + "SELECTIVO ASC,IO_Folio";

        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, minimoSku);
            int indice = 2;
            if (maximoSku > 0) {
                sentencia.setInt(indice, maximoSku);
                indice++;
            }
            sentencia.setString(indice, letra);
            sentencia.setString(indice + 1, letra);

            try (ResultSet filas = sentencia.executeQuery()) {
                if (!filas.next()) {
                    return null;
                }
                return new EscenarioParcial(
                        filas.getInt("IO_Folio"),
                        filas.getString("SELECTIVO"),
                        filas.getInt("TOTAL"),
                        filas.getString("PRIMER_SKU"));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible consultar escenario parcial HU24 sin filtro",
                    exception);
        }
    }

    /** Datos inmutables del selectivo elegido por la consulta. */
    public static final class Escenario {
        private final int folio;
        private final String nombre;
        private final int total;

        private Escenario(int folio, String nombre, int total) {
            this.folio = folio;
            this.nombre = nombre;
            this.total = total;
        }

        public int folio() {
            return folio;
        }

        public int total() {
            return total;
        }

        public String encabezado() {
            return nombre + " - " + total + " SKU";
        }
    }

    /** Datos de solo lectura usados para la recaptura de un SKU existente. */
    public static final class EscenarioEdicion {
        private final int folio;
        private final String nombre;
        private final int total;
        private final String sku;
        private final BigDecimal fisicoInicial;

        private EscenarioEdicion(
                int folio,
                String nombre,
                int total,
                String sku,
                BigDecimal fisicoInicial) {
            this.folio = folio;
            this.nombre = nombre;
            this.total = total;
            this.sku = sku;
            this.fisicoInicial = fisicoInicial;
        }

        public int folio() {
            return folio;
        }

        public String encabezado() {
            return nombre + " - " + total + " SKU";
        }

        public String sku() {
            return sku;
        }

        public BigDecimal fisicoInicial() {
            return fisicoInicial;
        }
    }

    /** Resultado de la validacion final del detalle persistido. */
    public static final class PersistenciaSku {
        private final int repeticiones;
        private final BigDecimal fisico;

        private PersistenciaSku(int repeticiones, BigDecimal fisico) {
            this.repeticiones = repeticiones;
            this.fisico = fisico;
        }

        public int repeticiones() {
            return repeticiones;
        }

        public BigDecimal fisico() {
            return fisico;
        }
    }

    /** Selectivo totalmente pendiente usado para generar el estado parcial. */
    public static final class EscenarioParcial {
        private final int folio;
        private final String nombre;
        private final int total;
        private final String primerSku;

        private EscenarioParcial(
                int folio,
                String nombre,
                int total,
                String primerSku) {
            this.folio = folio;
            this.nombre = nombre;
            this.total = total;
            this.primerSku = primerSku;
        }

        public int folio() {
            return folio;
        }

        public int total() {
            return total;
        }

        public String encabezado() {
            return nombre + " - " + total + " SKU";
        }

        public String primerSku() {
            return primerSku;
        }
    }
}
