package Mobile.preinventariosinteligentes.mvp5.support;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

/** Consultas de solo lectura para el estado inicial de un SKU en HU10. */
public final class ConsultaEstadoInicialHu10 {
    private final Supplier<Connection> proveedorConexion;

    public ConsultaEstadoInicialHu10(Supplier<Connection> proveedorConexion) {
        this.proveedorConexion = proveedorConexion;
    }

    /**
     * Elige un SKU asignado de la distribucion vigente, con teorico positivo
     * y sin ninguna captura fisica.
     */
    public Escenario consultarSkuNuncaContado() {
        String sql = "SELECT TOP 1 h.IO_Folio,"
                + "UPPER(LTRIM(RTRIM(SUBSTRING(h.IO_Description,"
                + "CHARINDEX('_',h.IO_Description)+1,LEN(h.IO_Description))))) SELECTIVO,"
                + "(SELECT COUNT(*) FROM hc.IO_DETAIL total "
                + "WHERE total.IO_Folio=h.IO_Folio) TOTAL,"
                + "TRIM(d.IO_SKU_REF) SKU,d.IO_OnHand TEORICO,d.IO_OnHandReal FISICO "
                + "FROM hc.IO_HEADER h JOIN hc.IO_DETAIL d ON d.IO_Folio=h.IO_Folio "
                + "JOIN hc.II_HEADER_IO_HEADER m ON m.IO_FOLIO=h.IO_Folio "
                + "WHERE m.II_FOLIO=(SELECT MAX(II_FOLIO) FROM hc.II_HEADER WHERE II_TYPE='I') "
                + "AND d.IO_OnHand>0 AND d.IO_OnHandReal IS NULL "
                + "AND (SELECT COUNT(*) FROM hc.IO_DETAIL repetido "
                + "WHERE repetido.IO_Folio=h.IO_Folio "
                + "AND repetido.IO_SKU_REF=d.IO_SKU_REF)=1 "
                + "AND (SELECT COUNT(*) FROM hc.IO_DETAIL total "
                + "WHERE total.IO_Folio=h.IO_Folio) BETWEEN 1 AND 15 "
                + "AND EXISTS (SELECT 1 FROM hc.II_ASSIGNMENTROLE a "
                + "WHERE a.IO_FOLIO=h.IO_Folio) "
                + "ORDER BY TOTAL,h.IO_Folio,d.IO_SKU_Order,d.IO_SKU_REF";
        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            try (ResultSet filas = sentencia.executeQuery()) {
                if (!filas.next()) {
                    return null;
                }
                return new Escenario(
                        filas.getInt("IO_Folio"),
                        filas.getString("SELECTIVO"),
                        filas.getInt("TOTAL"),
                        filas.getString("SKU"),
                        filas.getBigDecimal("TEORICO"),
                        filas.getBigDecimal("FISICO"));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible consultar el escenario inicial de HU10",
                    exception);
        }
    }

    /** Vuelve a consultar el mismo folio/SKU sin modificar su estado. */
    public Persistencia consultarPersistencia(int folio, String sku) {
        String sql = "SELECT COUNT(*) REPETICIONES,MAX(IO_OnHand) TEORICO,"
                + "MAX(IO_OnHandReal) FISICO FROM hc.IO_DETAIL "
                + "WHERE IO_Folio=? AND TRIM(IO_SKU_REF)=?";
        try (Connection conexion = proveedorConexion.get();
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, folio);
            sentencia.setString(2, sku);
            try (ResultSet filas = sentencia.executeQuery()) {
                filas.next();
                return new Persistencia(
                        filas.getInt("REPETICIONES"),
                        filas.getBigDecimal("TEORICO"),
                        filas.getBigDecimal("FISICO"));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(
                    "No fue posible validar el folio/SKU de HU10", exception);
        }
    }

    /** Datos inmutables del SKU nunca contado elegido. */
    public static final class Escenario {
        private final int folio;
        private final String nombre;
        private final int total;
        private final String sku;
        private final BigDecimal teorico;
        private final BigDecimal fisico;

        private Escenario(
                int folio,
                String nombre,
                int total,
                String sku,
                BigDecimal teorico,
                BigDecimal fisico) {
            this.folio = folio;
            this.nombre = nombre;
            this.total = total;
            this.sku = sku;
            this.teorico = teorico;
            this.fisico = fisico;
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

        public BigDecimal teorico() {
            return teorico;
        }

        public BigDecimal fisico() {
            return fisico;
        }
    }

    /** Resultado de la validacion final de persistencia. */
    public static final class Persistencia {
        private final int repeticiones;
        private final BigDecimal teorico;
        private final BigDecimal fisico;

        private Persistencia(
                int repeticiones,
                BigDecimal teorico,
                BigDecimal fisico) {
            this.repeticiones = repeticiones;
            this.teorico = teorico;
            this.fisico = fisico;
        }

        public int repeticiones() {
            return repeticiones;
        }

        public BigDecimal teorico() {
            return teorico;
        }

        public BigDecimal fisico() {
            return fisico;
        }
    }
}
