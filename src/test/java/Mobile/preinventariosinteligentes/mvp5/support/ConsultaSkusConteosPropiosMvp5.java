package Mobile.preinventariosinteligentes.mvp5.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Consulta SQL de solo lectura para obtener SKUs candidatos de Conteos Propios.
 */
public final class ConsultaSkusConteosPropiosMvp5 {
    private final Supplier<Connection> proveedorConexion;

    public ConsultaSkusConteosPropiosMvp5(Supplier<Connection> proveedorConexion) {
        this.proveedorConexion = proveedorConexion;
    }

    public List<String> obtenerSkusActivos(int cantidad, int offset) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de SKUs debe ser mayor a cero");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("El offset no puede ser negativo");
        }

        String sql = "SELECT DISTINCT TRIM(prod.Sku) AS SKU "
                + "FROM XposStore.dbo.ProductCacheDataVw prod "
                + "INNER JOIN XposStore.dbo.LE_INV_ITM kdx ON prod.Item_Id = kdx.ID_ITM "
                + "WHERE prod.Item_Status_Name = 'ACTIVE_ITEM_STATUS' "
                + "AND prod.Item_Type_Value = 'PRODUCT_ITEM' "
                + "AND prod.Product_Type_Code_Name = 'MREGUL_PRODUCT' "
                + "AND kdx.QU_CUR > 0 "
                + "ORDER BY SKU OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        List<String> skus = new ArrayList<>();
        try (Connection conexion = proveedorConexion.get();
                PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, offset);
            statement.setInt(2, cantidad);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String sku = rs.getString("SKU");
                    if (sku != null && !sku.trim().isEmpty()) {
                        skus.add(sku.trim());
                    }
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "No fue posible consultar SKUs activos para Conteos Propios en MVP5",
                    exception);
        }

        if (skus.isEmpty()) {
            throw new IllegalStateException(
                    "La consulta de SKUs activos no devolvio resultados para Conteos Propios");
        }
        return skus;
    }

    public int cantidadPlantillasPropiasActivasSemanaActual() {
        String sql = "SELECT COUNT(DISTINCT th.ID) AS TOTAL "
                + "FROM HC.II_HEADER ih "
                + "INNER JOIN HC.TEMPLATE_SELF_GENERATED_COUNTS_HEADER th ON th.II_FOLIO = ih.II_FOLIO "
                + "WHERE ih.II_TYPE = 'P' "
                + "AND YEAR(ih.II_STARTDATE) = YEAR(GETDATE()) "
                + "AND ih.II_WEEKNUMBER = DATEPART(ISO_WEEK, GETDATE()) "
                + "AND th.IS_ACTIVE = 1";

        try (Connection conexion = proveedorConexion.get();
                PreparedStatement statement = conexion.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("TOTAL");
            }
            return 0;
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "No fue posible consultar la existencia de plantillas propias activas en MVP5",
                    exception);
        }
    }

    public boolean hayPlantillasPropiasActivasSemanaActual() {
        return cantidadPlantillasPropiasActivasSemanaActual() > 0;
    }
}
