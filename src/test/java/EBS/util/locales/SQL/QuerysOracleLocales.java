package EBS.util.locales.SQL;

public class QuerysOracleLocales {

    //Seccion de querys para la extraccion de informacion de la base de datos para los reportes de XXFC - Reporte de Fac. Venta por Servicios

    /**
     * Metodo para regresar la primera parte del query de una consulta por parametros de No facturado o facturado, fecha de inicio y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteEstadoFacturaYFechas1(String inicioDeFecha, String finDeFecha, String responsabilidadReporte, String estadoFactura) {
        String queryEstadoFactura = "";
        if (estadoFactura.equals("NO FACTURADO")) {
            queryEstadoFactura = " AND s.FACTURA_AR_ID IS NULL ";
        } else if (estadoFactura.equals("FACTURADO")) {
            queryEstadoFactura = " AND s.FACTURA_AR_ID IS NOT NULL ";
        }
        return "SELECT  MIN(s.fecha_administrativa) f_admin_ini,\n" +
                "                                       MAX(s.fecha_administrativa) f_admin_fin,\n" +
                "                                       CASE WHEN s.FACTURA_AR_ID IS NULL THEN\n" +
                "                                             NULL\n" +
                "                                          ELSE\n" +
                "                                                (SELECT trx.trx_date\n" +
                "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\n" +
                "                                                WHERE trx.customer_trx_id = s.FACTURA_AR_ID\n" +
                "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\n" +
                "                                                AND ROWNUM = 1)\n" +
                "                                       END f_fact,\n" +
                "                                       s.plaza cr,\n" +
                "                                       s.servicio item " +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site" + queryEstadoFactura + "  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la segunda parte del query de una consulta por parametros de No facturado o facturado, fecha de inicio y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteEstadoFacturaYFechas2(String inicioDeFecha, String finDeFecha, String responsabilidadReporte, String estadoFactura) {
        String queryEstadoFactura = "";
        if (estadoFactura.equals("NO FACTURADO")) {
            queryEstadoFactura = " AND s.FACTURA_AR_ID IS NULL ";
        } else if (estadoFactura.equals("FACTURADO")) {
            queryEstadoFactura = " AND s.FACTURA_AR_ID IS NOT NULL ";
        }
        return "SELECT \n" +
                "   xit.SUP_NAME proveedor,\n" +
                "   aps.segment1 rfc,\n" +
                "   aps.vendor_name razon_soc " +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site" + queryEstadoFactura + "  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la tercera parte del query de una consulta por parametros de No facturado o facturado, fecha de inicio y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteEstadoFacturaYFechas3(String inicioDeFecha, String finDeFecha, String responsabilidadReporte, String estadoFactura) {
        String queryEstadoFactura = "";
        if (estadoFactura.equals("NO FACTURADO")) {
            queryEstadoFactura = " AND s.FACTURA_AR_ID IS NULL ";
        } else if (estadoFactura.equals("FACTURADO")) {
            queryEstadoFactura = " AND s.FACTURA_AR_ID IS NOT NULL ";
        }
        return "SELECT \n" +
                "  SUM(s.nro_trxs) num_trans,\n" +
                "  SUM(s.importe_orig) monto_trans,\n" +
                "  CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "   '$ '||s.comision_orig_calculada\n" +
                "   ELSE\n" +
                "     s.comision_orig_calculada||' %'\n" +
                "     END comision,\n" +
                "     CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "    ROUND(ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2)/(1+(s.tax_rate/100)),2)\n" +
                "     ELSE\n" +
                "      ROUND(ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2)/(1+(s.tax_rate/100)),2)\n" +
                "     END monto_factura " +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site " + queryEstadoFactura + "  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la cuarta parte del query de una consulta por parametros de No facturado o facturado, fecha de inicio y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteEstadoFacturaYFechas4(String inicioDeFecha, String finDeFecha, String responsabilidadReporte, String estadoFactura) {
        String queryEstadoFactura = "";
        if (estadoFactura.equals("NO FACTURADO")) {
            queryEstadoFactura = " AND s.FACTURA_AR_ID IS NULL ";
        } else if (estadoFactura.equals("FACTURADO")) {
            queryEstadoFactura = " AND s.FACTURA_AR_ID IS NOT NULL ";
        }
        return " SELECT \n" +
                "  CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "  ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2) - ROUND(ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2)/(1+(s.tax_rate/100)),2)\n" +
                "    ELSE\n" +
                "       ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2) - ROUND(ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2)/(1+(s.tax_rate/100)),2)\n" +
                "     END iva_factura,\n" +
                "    CASE WHEN s.FACTURA_AR_ID IS NULL THEN\n" +
                "     NULL\n" +
                "    ELSE\n" +
                "     (SELECT trx.trx_number\n" +
                "    FROM apps.RA_CUSTOMER_TRX_ALL trx\n" +
                "     WHERE trx.customer_trx_id = s.FACTURA_AR_ID\n" +
                "     AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\n" +
                "     AND ROWNUM = 1)\n" +
                "     END nro_factura,\n" +
                "     xit.sup_name,\n" +
                "     s.es_franja_fronteriza es_frontera " +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site" + queryEstadoFactura + "  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";

    }

    /**
     * Metodo para regresar la primera parte del query de una consulta por parametros de Facturado, fechas de inicio y fechan final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteFacturadoYFechas1(String inicioDeFecha, String finDeFecha, String responsabilidadReporte) {
        return "SELECT  MIN(s.fecha_administrativa) f_admin_ini,\n" +
                "                                       MAX(s.fecha_administrativa) f_admin_fin,\n" +
                "                                       CASE WHEN s.FACTURA_AR_ID IS NULL THEN\n" +
                "                                             NULL\n" +
                "                                          ELSE\n" +
                "                                                (SELECT trx.trx_date\n" +
                "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\n" +
                "                                                WHERE trx.customer_trx_id = s.FACTURA_AR_ID\n" +
                "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\n" +
                "                                                AND ROWNUM = 1)\n" +
                "                                       END f_fact,\n" +
                "                                       s.plaza cr,\n" +
                "                                       s.servicio item"
                + "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND s.FACTURA_AR_ID IS NOT NULL  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la segunda parte del query de una consulta por parametros de Facturado, fechas de inicio y fechan final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteFacturadoYFechas2(String inicioDeFecha, String finDeFecha, String responsabilidadReporte) {
        return "SELECT \n" +
                "                                       xit.SUP_NAME proveedor,\n" +
                "                                       aps.segment1 rfc,\n" +
                "                                       aps.vendor_name razon_soc" +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND s.FACTURA_AR_ID IS NOT NULL  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la tercera parte del query de una consulta por parametros de Facturado, fechas de inicio y fechan final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteFacturadoYFechas3(String inicioDeFecha, String finDeFecha, String responsabilidadReporte) {
        return "SELECT                                        SUM(s.nro_trxs) num_transa,\n" +
                "                                       SUM(s.importe_orig) monto_transa,\n" +
                "                                       CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "                                          '$ '||s.comision_orig_calculada\n" +
                "                                       ELSE\n" +
                "                                          s.comision_orig_calculada||' %'\n" +
                "                                       END comision,\n" +
                "                                       CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "                                          ROUND(ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       ELSE\n" +
                "                                          ROUND(ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       END monto_factura" +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND s.FACTURA_AR_ID IS NOT NULL  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la cuarta parte del query de una consulta por parametros de Facturado, fechas de inicio y fechan final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteFacturadoYFechas4(String inicioDeFecha, String finDeFecha, String responsabilidadReporte) {
        return "SELECT      \n" +
                "                                       CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "                                          ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2) - ROUND(ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       ELSE\n" +
                "                                          ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2) - ROUND(ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       END iva_factura,\n" +
                "                                       CASE WHEN s.FACTURA_AR_ID IS NULL THEN\n" +
                "                                             NULL\n" +
                "                                          ELSE\n" +
                "                                                (SELECT trx.trx_number\n" +
                "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\n" +
                "                                                WHERE trx.customer_trx_id = s.FACTURA_AR_ID\n" +
                "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\n" +
                "                                                AND ROWNUM = 1)\n" +
                "                                       END nro_factura,\n" +
                "                                       xit.sup_name,\n" +
                "                                       s.es_franja_fronteriza es_frontera  " +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND s.FACTURA_AR_ID IS NOT NULL  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la primera parte del query de una consulta por parametros de solo fecha inicial y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteSoloFechas1(String inicioDeFecha, String finDeFecha, String responsabilidadReporte) {
        return "SELECT  MIN(s.fecha_administrativa) f_admin_ini,\n" +
                "                                       MAX(s.fecha_administrativa) f_admin_fin,\n" +
                "                                       CASE WHEN s.FACTURA_AR_ID IS NULL THEN\n" +
                "                                             NULL\n" +
                "                                          ELSE\n" +
                "                                                (SELECT trx.trx_date\n" +
                "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\n" +
                "                                                WHERE trx.customer_trx_id = s.FACTURA_AR_ID\n" +
                "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\n" +
                "                                                AND ROWNUM = 1)\n" +
                "                                       END f_fact,\n" +
                "                                       s.plaza cr,\n" +
                "                                       s.servicio item" +
                "  FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la segunda parte del query de una consulta por parametros de solo fecha inicial y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteSoloFechas2(String inicioDeFecha, String finDeFecha, String responsabilidadReporte) {
        return "SELECT  \n" +
                "                                       xit.SUP_NAME proveedor,\n" +
                "                                       aps.segment1 rfc,\n" +
                "                                       aps.vendor_name razon_soc " +
                "  FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la tercera parte del query de una consulta por parametros de solo fecha inicial y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteSoloFechas3(String inicioDeFecha, String finDeFecha, String responsabilidadReporte) {
        return "SELECT  \n" +
                "                                       SUM(s.nro_trxs) num_transa,\n" +
                "                                       SUM(s.importe_orig) monto_transa,\n" +
                "                                       CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "                                          '$ '||s.comision_orig_calculada\n" +
                "                                       ELSE\n" +
                "                                          s.comision_orig_calculada||' %'\n" +
                "                                       END comision,\n" +
                "                                       CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "                                          ROUND(ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       ELSE\n" +
                "                                          ROUND(ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       END monto_factura" +
                "  FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la cuarta parte del query de una consulta por parametros de solo fecha inicial y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteSoloFechas4(String inicioDeFecha, String finDeFecha, String responsabilidadReporte) {
        return "SELECT  \n" +
                "\n" +
                "                                       CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "                                          ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2) - ROUND(ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       ELSE\n" +
                "                                          ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2) - ROUND(ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       END iva_factura,\n" +
                "                                       CASE WHEN s.FACTURA_AR_ID IS NULL THEN\n" +
                "                                             NULL\n" +
                "                                          ELSE\n" +
                "                                                (SELECT trx.trx_number\n" +
                "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\n" +
                "                                                WHERE trx.customer_trx_id = s.FACTURA_AR_ID\n" +
                "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\n" +
                "                                                AND ROWNUM = 1)\n" +
                "                                       END nro_factura,\n" +
                "                                       xit.sup_name,\n" +
                "                                       s.es_franja_fronteriza es_frontera  \n" +
                "  FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para extraer el codigo de customer ID para generar la query por medio del codigo que nos da el RFC ingresado
     *
     * @param RFCServicio
     * @return customer ID
     */
    public static String queryParaObtenerCustomerIDConRFC(String RFCServicio) {
        return "select Customer_Number, CUSTOMER_ID from apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  \n" +
                "                           AND   Customer_Number = '" + RFCServicio + "'";
    }

    /**
     * Metodo para regresar la primera parte del query de una consulta por parametros de  Proveedor RFC Estado de factura Facturado, fecha inicial y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return queryparte1
     */
    public static String queryReporteProveedorRFCFacturadoYFechas1(String inicioDeFecha, String finDeFecha, String responsabilidadReporte, String codigoRFC) {
        return "SELECT  MIN(s.fecha_administrativa) f_admin_ini,\n" +
                "                                       MAX(s.fecha_administrativa) f_admin_fin,\n" +
                "                                       CASE WHEN s.FACTURA_AR_ID IS NULL THEN\n" +
                "                                             NULL\n" +
                "                                          ELSE\n" +
                "                                                (SELECT trx.trx_date\n" +
                "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\n" +
                "                                                WHERE trx.customer_trx_id = s.FACTURA_AR_ID\n" +
                "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\n" +
                "                                                AND ROWNUM = 1)\n" +
                "                                       END f_fact,\n" +
                "                                       s.plaza cr,\n" +
                "                                       s.servicio item" +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND apss.vendor_site_code_alt = " + codigoRFC + "  AND s.FACTURA_AR_ID IS NOT NULL  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la segunda parte del query de una consulta por parametros de  Proveedor RFC Estado de factura Facturado, fecha inicial y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return queryparte1
     */
    public static String queryReporteProveedorRFCFacturadoYFechas2(String inicioDeFecha, String finDeFecha, String responsabilidadReporte, String codigoRFC) {
        return "SELECT \n" +
                "                                       xit.SUP_NAME proveedor,\n" +
                "                                       aps.segment1 rfc,\n" +
                "                                       aps.vendor_name razon_soc" +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND apss.vendor_site_code_alt = " + codigoRFC + "  AND s.FACTURA_AR_ID IS NOT NULL  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la tercera parte del query de una consulta por parametros de  Proveedor RFC Estado de factura Facturado, fecha inicial y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return queryparte1
     */
    public static String queryReporteProveedorRFCFacturadoYFechas3(String inicioDeFecha, String finDeFecha, String responsabilidadReporte, String codigoRFC) {
        return "SELECT \n" +
                "\n" +
                "                                       SUM(s.nro_trxs) num_transa,\n" +
                "                                       SUM(s.importe_orig) monto_transa,\n" +
                "                                       CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "                                          '$ '||s.comision_orig_calculada\n" +
                "                                       ELSE\n" +
                "                                          s.comision_orig_calculada||' %'\n" +
                "                                       END comision,\n" +
                "                                       CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "                                          ROUND(ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       ELSE\n" +
                "                                          ROUND(ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       END monto_factura" +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND apss.vendor_site_code_alt = " + codigoRFC + "  AND s.FACTURA_AR_ID IS NOT NULL  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la cuarta parte del query de una consulta por parametros de  Proveedor RFC Estado de factura Facturado, fecha inicial y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return queryparte1
     */
    public static String queryReporteProveedorRFCFacturadoYFechas4(String inicioDeFecha, String finDeFecha, String responsabilidadReporte, String codigoRFC) {
        return "SELECT \n" +
                "                                      CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "                                          ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2) - ROUND(ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       ELSE\n" +
                "                                          ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2) - ROUND(ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       END iva_factura,\n" +
                "                                       CASE WHEN s.FACTURA_AR_ID IS NULL THEN\n" +
                "                                             NULL\n" +
                "                                          ELSE\n" +
                "                                                (SELECT trx.trx_number\n" +
                "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\n" +
                "                                                WHERE trx.customer_trx_id = s.FACTURA_AR_ID\n" +
                "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\n" +
                "                                                AND ROWNUM = 1)\n" +
                "                                       END nro_factura,\n" +
                "                                       xit.sup_name,\n" +
                "                                       s.es_franja_fronteriza es_frontera" +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND apss.vendor_site_code_alt = " + codigoRFC + "  AND s.FACTURA_AR_ID IS NOT NULL  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "' GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    public static String queryParaObtenerIDGrupo(String nombreDeGrupo) {
        return "SELECT Grupo_Ejecucion, ID_GRUPO FROM xxfc_ar_gpos_ejec_snc_all\n" +
                " where Grupo_Ejecucion = '" + nombreDeGrupo + "'";
    }

    /**
     * Metodo para regresar la primera parte del query de una consulta por parametros de GRUPO DE FACTURACION / FACTURADO Estado de factura Facturado / FECHA ADMINISTRATIVA fecha inicial y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteGrpFacturacionFacturadoyFechasparte1(String inicioDeFecha, String finDeFecha, String responsabilidadReporte, String idDeGrupo) {
        return "SELECT  MIN(s.fecha_administrativa) f_admin_ini,\n" +
                "                                       MAX(s.fecha_administrativa) f_admin_fin,\n" +
                "                                       CASE WHEN s.FACTURA_AR_ID IS NULL THEN\n" +
                "                                             NULL\n" +
                "                                          ELSE\n" +
                "                                                (SELECT trx.trx_date\n" +
                "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\n" +
                "                                                WHERE trx.customer_trx_id = s.FACTURA_AR_ID\n" +
                "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\n" +
                "                                                AND ROWNUM = 1)\n" +
                "                                       END f_fact,\n" +
                "                                       s.plaza cr,\n" +
                "                                       s.servicio item " +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND s.FACTURA_AR_ID IS NOT NULL  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "'  AND EXISTS (SELECT 1\n" +
                "                                                           FROM xxfc_ar_gpos_ejec_snc_all xgs\n" +
                "                                                           WHERE id_grupo = TO_NUMBER(" + idDeGrupo + ") AND xgs.servicio = s.servicio  UNION ALL  SELECT 1  FROM  xxfc_ar_gpos_ejec_snc_all xgs  WHERE xgs.rfc = xit.supplier||''  AND   xgs.id_grupo = TO_NUMBER(" + idDeGrupo + ")) GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la segunda parte del query de una consulta por parametros de GRUPO DE FACTURACION / FACTURADO Estado de factura Facturado / FECHA ADMINISTRATIVA fecha inicial y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteGrpFacturacionFacturadoyFechasparte2(String inicioDeFecha, String finDeFecha, String responsabilidadReporte, String idDeGrupo) {
        return "SELECT \n" +
                "                                       xit.SUP_NAME proveedor,\n" +
                "                                       aps.segment1 rfc,\n" +
                "                                       aps.vendor_name razon_soc" +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND s.FACTURA_AR_ID IS NOT NULL  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "'  AND EXISTS (SELECT 1\n" +
                "                                                           FROM xxfc_ar_gpos_ejec_snc_all xgs\n" +
                "                                                           WHERE id_grupo = TO_NUMBER(" + idDeGrupo + ") AND xgs.servicio = s.servicio  UNION ALL  SELECT 1  FROM  xxfc_ar_gpos_ejec_snc_all xgs  WHERE xgs.rfc = xit.supplier||''  AND   xgs.id_grupo = TO_NUMBER(" + idDeGrupo + ")) GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la tercera parte del query de una consulta por parametros de GRUPO DE FACTURACION / FACTURADO Estado de factura Facturado / FECHA ADMINISTRATIVA fecha inicial y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteGrpFacturacionFacturadoyFechasparte3(String inicioDeFecha, String finDeFecha, String responsabilidadReporte, String idDeGrupo) {
        return "SELECT \n" +
                "                                       SUM(s.nro_trxs) num_transa,\n" +
                "                                       SUM(s.importe_orig) monto_transa,\n" +
                "                                       CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "                                          '$ '||s.comision_orig_calculada\n" +
                "                                       ELSE\n" +
                "                                          s.comision_orig_calculada||' %'\n" +
                "                                       END comision,\n" +
                "                                       CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "                                          ROUND(ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       ELSE\n" +
                "                                          ROUND(ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       END monto_factura " +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND s.FACTURA_AR_ID IS NOT NULL  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "'  AND EXISTS (SELECT 1\n" +
                "                                                           FROM xxfc_ar_gpos_ejec_snc_all xgs\n" +
                "                                                           WHERE id_grupo = TO_NUMBER(" + idDeGrupo + ") AND xgs.servicio = s.servicio  UNION ALL  SELECT 1  FROM  xxfc_ar_gpos_ejec_snc_all xgs  WHERE xgs.rfc = xit.supplier||''  AND   xgs.id_grupo = TO_NUMBER(" + idDeGrupo + ")) GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }

    /**
     * Metodo para regresar la cuarta parte del query de una consulta por parametros de GRUPO DE FACTURACION / FACTURADO Estado de factura Facturado / FECHA ADMINISTRATIVA fecha inicial y fecha final
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param responsabilidadReporte
     * @return Query completo con los parametros
     * @author David Quiroz Rodriguez
     */
    public static String queryReporteGrpFacturacionFacturadoyFechasparte4(String inicioDeFecha, String finDeFecha, String responsabilidadReporte, String idDeGrupo) {
        return "SELECT\n" +
                " CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "                                          ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2) - ROUND(ROUND(SUM(s.nro_trxs) * s.comision_orig_calculada,2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       ELSE\n" +
                "                                          ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2) - ROUND(ROUND(SUM(s.importe_orig) * (s.comision_orig_calculada/100),2)/(1+(s.tax_rate/100)),2)\n" +
                "                                       END iva_factura,\n" +
                "                                       CASE WHEN s.FACTURA_AR_ID IS NULL THEN\n" +
                "                                             NULL\n" +
                "                                          ELSE\n" +
                "                                                (SELECT trx.trx_number\n" +
                "                                                FROM apps.RA_CUSTOMER_TRX_ALL trx\n" +
                "                                                WHERE trx.customer_trx_id = s.FACTURA_AR_ID\n" +
                "                                                AND trx.INTERFACE_HEADER_ATTRIBUTE3 = 'Comision Cobro de Servicios'\n" +
                "                                                AND ROWNUM = 1)\n" +
                "                                       END nro_factura,\n" +
                "                                       xit.sup_name,\n" +
                "                                       s.es_franja_fronteriza es_frontera  " +
                "   FROM    apps.xxfc_ar_fact_vta_serv_loc   s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site  AND s.FACTURA_AR_ID IS NOT NULL  AND s.FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + responsabilidadReporte + "'  AND EXISTS (SELECT 1\n" +
                "                                                           FROM xxfc_ar_gpos_ejec_snc_all xgs\n" +
                "                                                           WHERE id_grupo = TO_NUMBER(" + idDeGrupo + ") AND xgs.servicio = s.servicio  UNION ALL  SELECT 1  FROM  xxfc_ar_gpos_ejec_snc_all xgs  WHERE xgs.rfc = xit.supplier||''  AND   xgs.id_grupo = TO_NUMBER(" + idDeGrupo + ")) GROUP BY s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.FACTURA_AR_ID,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 s.comision_orig_calculada,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 s.tax_rate,\n" +
                "                                                 s.es_franja_fronteriza";
    }
    //Fin de seccion de querys para la extraccion de informacion de la base de datos para los reportes de XXFC - Reporte de Fac. Venta por Servicios

    //Seccion de querys para la extraccion de informacion de la base de datos para los reportes de XXFC - Reporte de Facturacion Servicios Locales

    /**
     * Metodo para regresar la primera parte del query de una consulta por parametros solo fechas
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @return query
     */
    public static String queryReporteFactSerLocSoloFechas1(String inicioDeFecha, String finDeFecha) {
        return "SELECT   TO_CHAR (CT.TRX_DATE, 'MON', 'NLS_DATE_LANGUAGE=SPANISH')   MES,\n" +
                "               TO_CHAR(CT.TRX_DATE,'DD/MM/RRRR') FECHA,\n" +
                "               TO_CHAR(HR1.ATTRIBUTE1) COMPANIA,\n" +
                "               xc.descripcion NEGOCIO,\n" +
                "               (CASE WHEN RCTT.TYPE = 'INV' THEN 'FACTURA'\n" +
                "                     WHEN RCTT.TYPE = 'CM'  THEN 'NOTA CREDITO'\n" +
                "                     WHEN RCTT.TYPE = 'DM'  THEN 'NOTA DEBITO'\n" +
                "                     END) DOCUMENTO_FISCAL\n" +
                "      FROM     apps.ar_payment_schedules_all ps,\n" +
                "               apps.ra_customer_trx_all ct,\n" +
                "               apps.hr_all_organization_units hr1,\n" +
                "               apps.xxfc_cfd_factura_digital xcfd,\n" +
                "               apps.ra_cust_trx_types_all rctt,\n" +
                "               apps.xxfc_companias xc,\n" +
                "               apps.hz_cust_accounts hca,\n" +
                "               apps.hz_parties hz \n" +
                "      WHERE    rctt.cust_trx_type_id = ct.cust_trx_type_id\n" +
                "               AND ps.customer_trx_id = ct.customer_trx_id\n" +
                "               AND hr1.organization_id = ct.org_id\n" +
                "               AND xc.oracle_cia = hr1.attribute1\n" +
                "               AND hca.cust_account_id = ct.bill_to_customer_id\n" +
                "               AND hca.party_id = hz.party_id\n" +
                "               AND ct.customer_trx_id = xcfd.customer_trx_id(+)\n" +
                "               AND ct.previous_customer_trx_id IS NULL\n" +
                "               AND CT.TRX_DATE BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')\n" +
                "               AND rctt.name = 'FAC_Ventas_10MON'   AND 1=1";
    }

    /**
     * Metodo para regresar la segunda parte del query de una consulta por parametros solo fechas
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @return query
     */
    public static String queryReporteFactSerLocSoloFechas2(String inicioDeFecha, String finDeFecha) {
        return "SELECT   \n" +
                " XCFD.FOLIO FOLIO_DIGITAL,\n" +
                "               CT.TRX_NUMBER TRANSACCION,\n" +
                "               hca.ACCOUNT_NUMBER RFC,\n" +
                "               hz.PARTY_NAME RAZON_SOCIAL\n" +
                "      FROM     apps.ar_payment_schedules_all ps,\n" +
                "               apps.ra_customer_trx_all ct,\n" +
                "               apps.hr_all_organization_units hr1,\n" +
                "               apps.xxfc_cfd_factura_digital xcfd,\n" +
                "               apps.ra_cust_trx_types_all rctt,\n" +
                "               apps.xxfc_companias xc,\n" +
                "               apps.hz_cust_accounts hca,\n" +
                "               apps.hz_parties hz \n" +
                "      WHERE    rctt.cust_trx_type_id = ct.cust_trx_type_id\n" +
                "               AND ps.customer_trx_id = ct.customer_trx_id\n" +
                "               AND hr1.organization_id = ct.org_id\n" +
                "               AND xc.oracle_cia = hr1.attribute1\n" +
                "               AND hca.cust_account_id = ct.bill_to_customer_id\n" +
                "               AND hca.party_id = hz.party_id\n" +
                "               AND ct.customer_trx_id = xcfd.customer_trx_id(+)\n" +
                "               AND ct.previous_customer_trx_id IS NULL\n" +
                "               AND CT.TRX_DATE BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')\n" +
                "               AND rctt.name = 'FAC_Ventas_10MON'   AND 1=1";
    }

    /**
     * Metodo para regresar la tercera parte del query de una consulta por parametros solo fechas
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @return query
     */
    public static String queryReporteFactSerLocSoloFechas3(String inicioDeFecha, String finDeFecha) {
        return "SELECT   \n" +
                "               ( SELECT DESCRIPTION||' '||ATTRIBUTE1\n" +
                "               FROM APPS.RA_CUSTOMER_TRX_LINES_ALL\n" +
                "               WHERE CUSTOMER_TRX_ID = PS.CUSTOMER_TRX_ID\n" +
                "               AND ROWNUM < 2\n" +
                "               AND LINE_TYPE = 'LINE') CONCEPTO_FACTURA,\n" +
                "               PS.AMOUNT_LINE_ITEMS_ORIGINAL SUBTOTAL,\n" +
                "               PS.TAX_ORIGINAL IVA,\n" +
                "               PS.AMOUNT_DUE_ORIGINAL TOTAL,\n" +
                "               (SELECT gcc.segment4\n" +
                "               FROM apps.ra_cust_trx_line_gl_dist_all gd\n" +
                "               ,  apps.ra_customer_trx_lines_all tl\n" +
                "               ,  apps.gl_code_combinations  GCC\n" +
                "               WHERE gd.customer_trx_id = ct.CUSTOMER_TRX_ID\n" +
                "               AND gd.customer_trx_id = tl.customer_trx_id\n" +
                "               AND tl.line_type = 'LINE'\n" +
                "               AND tl.DESCRIPTION  = 'Comision por Cobro de Servicios'\n" +
                "               AND tl.CUSTOMER_TRX_LINE_ID = gd.CUSTOMER_TRX_LINE_ID\n" +
                "               AND gcc.CODE_COMBINATION_ID = gd.CODE_COMBINATION_ID\n" +
                "               AND ROWNUM = 1\n" +
                "               AND gd.ACCOUNT_CLASS = 'REV'\n" +
                "               AND gd.org_id = ct.ORG_ID\n" +
                "               AND gd.set_of_books_id = ct.set_of_books_id) CUENTA,\n" +
                "               (SELECT gcc.segment4\n" +
                "               FROM apps.ra_cust_trx_line_gl_dist_all gd\n" +
                "               ,  apps.ra_customer_trx_lines_all tl\n" +
                "               ,  apps.gl_code_combinations  GCC\n" +
                "               WHERE gd.customer_trx_id = ct.CUSTOMER_TRX_ID\n" +
                "               AND gd.customer_trx_id = tl.customer_trx_id\n" +
                "               AND tl.line_type = 'LINE'\n" +
                "               AND tl.DESCRIPTION  LIKE 'Comision%MPE%'\n" +
                "               AND tl.CUSTOMER_TRX_LINE_ID = gd.CUSTOMER_TRX_LINE_ID\n" +
                "               AND gcc.CODE_COMBINATION_ID = gd.CODE_COMBINATION_ID\n" +
                "               AND ROWNUM = 1\n" +
                "               AND gd.ACCOUNT_CLASS = 'REV'\n" +
                "               AND gd.org_id = ct.ORG_ID\n" +
                "               AND gd.set_of_books_id = ct.set_of_books_id) CUENTA_MPE \n" +
                "      FROM     apps.ar_payment_schedules_all ps,\n" +
                "               apps.ra_customer_trx_all ct,\n" +
                "               apps.hr_all_organization_units hr1,\n" +
                "               apps.xxfc_cfd_factura_digital xcfd,\n" +
                "               apps.ra_cust_trx_types_all rctt,\n" +
                "               apps.xxfc_companias xc,\n" +
                "               apps.hz_cust_accounts hca,\n" +
                "               apps.hz_parties hz \n" +
                "      WHERE    rctt.cust_trx_type_id = ct.cust_trx_type_id\n" +
                "               AND ps.customer_trx_id = ct.customer_trx_id\n" +
                "               AND hr1.organization_id = ct.org_id\n" +
                "               AND xc.oracle_cia = hr1.attribute1\n" +
                "               AND hca.cust_account_id = ct.bill_to_customer_id\n" +
                "               AND hca.party_id = hz.party_id\n" +
                "               AND ct.customer_trx_id = xcfd.customer_trx_id(+)\n" +
                "               AND ct.previous_customer_trx_id IS NULL\n" +
                "               AND CT.TRX_DATE BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')\n" +
                "               AND rctt.name = 'FAC_Ventas_10MON'   AND 1=1";
    }

    /**
     * Metodo para regresar la cuarta parte del query de una consulta por parametros solo fechas
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @return query
     */
    public static String queryReporteFactSerLocSoloFechas4(String inicioDeFecha, String finDeFecha) {
        return "SELECT   \n" +
                "               (CASE WHEN  (PS.AMOUNT_DUE_ORIGINAL != PS.AMOUNT_DUE_REMAINING AND NOT 0 = PS.AMOUNT_DUE_REMAINING) THEN 'PARCIALMENTE PAGADA'\n" +
                "                     WHEN 0 = PS.AMOUNT_DUE_REMAINING THEN 'PAGADA'\n" +
                "                     WHEN PS.AMOUNT_DUE_ORIGINAL = PS.AMOUNT_DUE_REMAINING THEN 'PENDIENTE DE PAGO' END) ESTADO_ADEUDO,\n" +
                "               NVL((SELECT (CASE WHEN APPLICATION_TYPE = 'CM' THEN  'CANCELADA' ELSE 'FACTURADA' END) ESTADO\n" +
                "                  FROM APPS.AR_RECEIVABLE_APPLICATIONS_ALL ARA\n" +
                "                  WHERE  ARA.DISPLAY = 'Y'\n" +
                "                  AND ARA.APPLIED_CUSTOMER_TRX_ID = CT.CUSTOMER_TRX_ID\n" +
                "                  AND ROWNUM < 2),'FACTURADA') ESTADO,\n" +
                "               PS.AMOUNT_DUE_REMAINING SALDO_DEUDOR,\n" +
                "               ( SELECT SUM(AMOUNT_APPLIED)\n" +
                "               FROM APPS.AR_RECEIVABLE_APPLICATIONS_ALL ARA\n" +
                "               WHERE ARA.APPLICATION_TYPE = 'CASH'\n" +
                "               AND ARA.DISPLAY = 'Y'\n" +
                "               AND ARA.APPLIED_CUSTOMER_TRX_ID = CT.CUSTOMER_TRX_ID) SALDO_COBRADO,\n" +
                "               TO_CHAR((SELECT MAX (APPLY_DATE)\n" +
                "                        FROM APPS.AR_RECEIVABLE_APPLICATIONS_ALL ARA,\n" +
                "                           APPS.AR_CASH_RECEIPTS_ALL ACR\n" +
                "                        WHERE ARA.APPLICATION_TYPE = 'CASH'\n" +
                "                        AND ACR.STATUS != 'REV'\n" +
                "                        AND ARA.DISPLAY = 'Y'\n" +
                "                        AND ACR.CASH_RECEIPT_ID = ARA.CASH_RECEIPT_ID\n" +
                "                        AND ARA.APPLIED_CUSTOMER_TRX_ID = CT.CUSTOMER_TRX_ID),'DD/MM/RRRR') FECHA_ULTIMO_RECIBO,\n" +
                "               ( SELECT (CASE WHEN ARM.NAME LIKE 'FEMSA_NET_17ADM' THEN 'COMPENSACION' ELSE 'TRANSFERENCIA' END) FORMA_PAGO\n" +
                "               FROM APPS.AR_RECEIPT_METHODS ARM,\n" +
                "                     APPS.AR_CASH_RECEIPTS_ALL AC,\n" +
                "                     APPS.AR_RECEIVABLE_APPLICATIONS_ALL AAA\n" +
                "               WHERE ARM.RECEIPT_METHOD_ID = AC.RECEIPT_METHOD_ID\n" +
                "                  AND AAA.APPLICATION_TYPE = 'CASH'\n" +
                "                  AND AC.STATUS != 'REV'\n" +
                "                  AND AAA.CASH_RECEIPT_ID = AC.CASH_RECEIPT_ID\n" +
                "                  AND AAA.APPLIED_CUSTOMER_TRX_ID = CT.CUSTOMER_TRX_ID\n" +
                "                  AND AAA.DISPLAY = 'Y'\n" +
                "                  AND AAA.CASH_RECEIPT_ID =\n" +
                "                                 (SELECT MAX (ARA.CASH_RECEIPT_ID)\n" +
                "                                 FROM APPS.AR_RECEIVABLE_APPLICATIONS_ALL ARA,\n" +
                "                                       APPS.AR_CASH_RECEIPTS_ALL ACR2\n" +
                "                                 WHERE ACR2.CASH_RECEIPT_ID = ARA.CASH_RECEIPT_ID\n" +
                "                                       AND ARA.APPLIED_CUSTOMER_TRX_ID = CT.CUSTOMER_TRX_ID\n" +
                "                                       AND ARA.APPLICATION_TYPE = 'CASH'\n" +
                "                                       AND ACR2.STATUS != 'REV'\n" +
                "                                       AND ARA.DISPLAY = 'Y')) FORMA_PAGO\n" +
                "      FROM     apps.ar_payment_schedules_all ps,\n" +
                "               apps.ra_customer_trx_all ct,\n" +
                "               apps.hr_all_organization_units hr1,\n" +
                "               apps.xxfc_cfd_factura_digital xcfd,\n" +
                "               apps.ra_cust_trx_types_all rctt,\n" +
                "               apps.xxfc_companias xc,\n" +
                "               apps.hz_cust_accounts hca,\n" +
                "               apps.hz_parties hz \n" +
                "      WHERE    rctt.cust_trx_type_id = ct.cust_trx_type_id\n" +
                "               AND ps.customer_trx_id = ct.customer_trx_id\n" +
                "               AND hr1.organization_id = ct.org_id\n" +
                "               AND xc.oracle_cia = hr1.attribute1\n" +
                "               AND hca.cust_account_id = ct.bill_to_customer_id\n" +
                "               AND hca.party_id = hz.party_id\n" +
                "               AND ct.customer_trx_id = xcfd.customer_trx_id(+)\n" +
                "               AND ct.previous_customer_trx_id IS NULL\n" +
                "               AND CT.TRX_DATE BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')\n" +
                "               AND rctt.name = 'FAC_Ventas_10MON'   AND 1=1";
    }

    /**
     * Metodo para regresar la quinta parte del query de una consulta por parametros solo fechas
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @return query
     */
    public static String queryReporteFactSerLocSoloFechas5(String inicioDeFecha, String finDeFecha) {
        return "SELECT   \n" +
                "               XCFD.UUID,\n" +
                "               DBMS_LOB.SUBSTR(XCFD.UUID_RELACIONADO) AS UUID_RELACIONADO,\n" +
                "               TO_CHAR(XCFD.FECHA_TIMBRE,'DD/MM/RRRR') FECHA_TIMBRADO,\n" +
                "               XCFD.ID_FACTURA_DIGITAL ID_TIMBRADO,\n" +
                "               (CASE WHEN XCFD.WM_STATUS = 'E' THEN 'TIMBRADO'\n" +
                "                     WHEN XCFD.WM_STATUS = 'C' THEN 'CANCELADO'\n" +
                "                     WHEN XCFD.WM_STATUS = 'L' THEN 'PENDIENTE DE TIMBRAR' ELSE 'NO TIMBRADO' END) ESTADO_TIMBRADO,\n" +
                "               RCTT.NAME  TIPO_TRANSACCION \n" +
                "      FROM     apps.ar_payment_schedules_all ps,\n" +
                "               apps.ra_customer_trx_all ct,\n" +
                "               apps.hr_all_organization_units hr1,\n" +
                "               apps.xxfc_cfd_factura_digital xcfd,\n" +
                "               apps.ra_cust_trx_types_all rctt,\n" +
                "               apps.xxfc_companias xc,\n" +
                "               apps.hz_cust_accounts hca,\n" +
                "               apps.hz_parties hz \n" +
                "      WHERE    rctt.cust_trx_type_id = ct.cust_trx_type_id\n" +
                "               AND ps.customer_trx_id = ct.customer_trx_id\n" +
                "               AND hr1.organization_id = ct.org_id\n" +
                "               AND xc.oracle_cia = hr1.attribute1\n" +
                "               AND hca.cust_account_id = ct.bill_to_customer_id\n" +
                "               AND hca.party_id = hz.party_id\n" +
                "               AND ct.customer_trx_id = xcfd.customer_trx_id(+)\n" +
                "               AND ct.previous_customer_trx_id IS NULL\n" +
                "               AND CT.TRX_DATE BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')\n" +
                "               AND rctt.name = 'FAC_Ventas_10MON'   AND 1=1";

    }

    /**
     * Metodo para regresar la primera parte del query de una consulta por RFC y fechas
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @return query
     */
    public static String queryReporteFactSerLocRFCFechas1(String inicioDeFecha, String finDeFecha, String namePlaza, String custumerID) {
        return "SELECT   \n" +
                " TO_CHAR (CT.TRX_DATE, 'MON', 'NLS_DATE_LANGUAGE=SPANISH')   MES,\n" +
                "               TO_CHAR(CT.TRX_DATE,'DD/MM/RRRR') FECHA,\n" +
                "               TO_CHAR(HR1.ATTRIBUTE1) COMPANIA,\n" +
                "               xc.descripcion NEGOCIO,\n" +
                "               (CASE WHEN RCTT.TYPE = 'INV' THEN 'FACTURA'\n" +
                "                     WHEN RCTT.TYPE = 'CM'  THEN 'NOTA CREDITO'\n" +
                "                     WHEN RCTT.TYPE = 'DM'  THEN 'NOTA DEBITO'\n" +
                "                     END) DOCUMENTO_FISCAL,\n" +
                "               XCFD.FOLIO FOLIO_DIGITAL\n" +
                "      FROM     apps.ar_payment_schedules_all ps,\n" +
                "               apps.ra_customer_trx_all ct,\n" +
                "               apps.hr_all_organization_units hr1,\n" +
                "               apps.xxfc_cfd_factura_digital xcfd,\n" +
                "               apps.ra_cust_trx_types_all rctt,\n" +
                "               apps.xxfc_companias xc,\n" +
                "               apps.hz_cust_accounts hca,\n" +
                "               apps.hz_parties hz \n" +
                "      WHERE    rctt.cust_trx_type_id = ct.cust_trx_type_id\n" +
                "               AND ps.customer_trx_id = ct.customer_trx_id\n" +
                "               AND hr1.organization_id = ct.org_id\n" +
                "               AND xc.oracle_cia = hr1.attribute1\n" +
                "               AND hca.cust_account_id = ct.bill_to_customer_id\n" +
                "               AND hca.party_id = hz.party_id\n" +
                "               AND ct.customer_trx_id = xcfd.customer_trx_id(+)\n" +
                "               AND ct.previous_customer_trx_id IS NULL\n" +
                "               AND CT.TRX_DATE BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')\n" +
                "               AND rctt.name = '" + namePlaza + "' \n" +
                "   AND ct.BILL_TO_CUSTOMER_ID = " + custumerID + "  AND 1=1";
    }

    /**
     * Metodo para regresar la segunda parte del query de una consulta por RFC y fechas
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @return query
     */
    public static String queryReporteFactSerLocRFCFechas2(String inicioDeFecha, String finDeFecha, String namePlaza, String custumerID) {
        return "SELECT  \n" +
                "CT.TRX_NUMBER TRANSACCION,\n" +
                "               hca.ACCOUNT_NUMBER RFC,\n" +
                "               hz.PARTY_NAME RAZON_SOCIAL\n" +
                "      FROM     apps.ar_payment_schedules_all ps,\n" +
                "               apps.ra_customer_trx_all ct,\n" +
                "               apps.hr_all_organization_units hr1,\n" +
                "               apps.xxfc_cfd_factura_digital xcfd,\n" +
                "               apps.ra_cust_trx_types_all rctt,\n" +
                "               apps.xxfc_companias xc,\n" +
                "               apps.hz_cust_accounts hca,\n" +
                "               apps.hz_parties hz \n" +
                "      WHERE    rctt.cust_trx_type_id = ct.cust_trx_type_id\n" +
                "               AND ps.customer_trx_id = ct.customer_trx_id\n" +
                "               AND hr1.organization_id = ct.org_id\n" +
                "               AND xc.oracle_cia = hr1.attribute1\n" +
                "               AND hca.cust_account_id = ct.bill_to_customer_id\n" +
                "               AND hca.party_id = hz.party_id\n" +
                "               AND ct.customer_trx_id = xcfd.customer_trx_id(+)\n" +
                "               AND ct.previous_customer_trx_id IS NULL\n" +
                "               AND CT.TRX_DATE BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')\n" +
                "               AND rctt.name = '" + namePlaza + "' \n" +
                "   AND ct.BILL_TO_CUSTOMER_ID = " + custumerID + "  AND 1=1";
    }

    /**
     * Metodo para regresar la tercera parte del query de una consulta por RFC y fechas
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @return query
     */
    public static String queryReporteFactSerLocRFCFechas3(String inicioDeFecha, String finDeFecha, String namePlaza, String custumerID) {
        return "SELECT   " +
                "               ( SELECT DESCRIPTION||' '||ATTRIBUTE1\n" +
                "               FROM APPS.RA_CUSTOMER_TRX_LINES_ALL\n" +
                "               WHERE CUSTOMER_TRX_ID = PS.CUSTOMER_TRX_ID\n" +
                "               AND ROWNUM < 2\n" +
                "               AND LINE_TYPE = 'LINE') CONCEPTO_FACTURA,\n" +
                "               PS.AMOUNT_LINE_ITEMS_ORIGINAL SUBTOTAL,\n" +
                "               PS.TAX_ORIGINAL IVA,\n" +
                "               PS.AMOUNT_DUE_ORIGINAL TOTAL\n" +
                "      FROM     apps.ar_payment_schedules_all ps,\n" +
                "               apps.ra_customer_trx_all ct,\n" +
                "               apps.hr_all_organization_units hr1,\n" +
                "               apps.xxfc_cfd_factura_digital xcfd,\n" +
                "               apps.ra_cust_trx_types_all rctt,\n" +
                "               apps.xxfc_companias xc,\n" +
                "               apps.hz_cust_accounts hca,\n" +
                "               apps.hz_parties hz \n" +
                "      WHERE    rctt.cust_trx_type_id = ct.cust_trx_type_id\n" +
                "               AND ps.customer_trx_id = ct.customer_trx_id\n" +
                "               AND hr1.organization_id = ct.org_id\n" +
                "               AND xc.oracle_cia = hr1.attribute1\n" +
                "               AND hca.cust_account_id = ct.bill_to_customer_id\n" +
                "               AND hca.party_id = hz.party_id\n" +
                "               AND ct.customer_trx_id = xcfd.customer_trx_id(+)\n" +
                "               AND ct.previous_customer_trx_id IS NULL\n" +
                "               AND CT.TRX_DATE BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')\n" +
                "               AND rctt.name = '" + namePlaza + "' \n" +
                "   AND ct.BILL_TO_CUSTOMER_ID = " + custumerID + "  AND 1=1";
    }

    /**
     * Metodo para regresar la cuarta parte del query de una consulta por RFC y fechas
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @return query
     */
    public static String queryReporteFactSerLocRFCFechas4(String inicioDeFecha, String finDeFecha, String namePlaza, String custumerID) {
        return "SELECT  \n" +
                "               (SELECT gcc.segment4\n" +
                "               FROM apps.ra_cust_trx_line_gl_dist_all gd\n" +
                "               ,  apps.ra_customer_trx_lines_all tl\n" +
                "               ,  apps.gl_code_combinations  GCC\n" +
                "               WHERE gd.customer_trx_id = ct.CUSTOMER_TRX_ID\n" +
                "               AND gd.customer_trx_id = tl.customer_trx_id\n" +
                "               AND tl.line_type = 'LINE'\n" +
                "               AND tl.DESCRIPTION  = 'Comision por Cobro de Servicios'\n" +
                "               AND tl.CUSTOMER_TRX_LINE_ID = gd.CUSTOMER_TRX_LINE_ID\n" +
                "               AND gcc.CODE_COMBINATION_ID = gd.CODE_COMBINATION_ID\n" +
                "               AND ROWNUM = 1\n" +
                "               AND gd.ACCOUNT_CLASS = 'REV'\n" +
                "               AND gd.org_id = ct.ORG_ID\n" +
                "               AND gd.set_of_books_id = ct.set_of_books_id) CUENTA,\n" +
                "               (SELECT gcc.segment4\n" +
                "               FROM apps.ra_cust_trx_line_gl_dist_all gd\n" +
                "               ,  apps.ra_customer_trx_lines_all tl\n" +
                "               ,  apps.gl_code_combinations  GCC\n" +
                "               WHERE gd.customer_trx_id = ct.CUSTOMER_TRX_ID\n" +
                "               AND gd.customer_trx_id = tl.customer_trx_id\n" +
                "               AND tl.line_type = 'LINE'\n" +
                "               AND tl.DESCRIPTION  LIKE 'Comision%MPE%'\n" +
                "               AND tl.CUSTOMER_TRX_LINE_ID = gd.CUSTOMER_TRX_LINE_ID\n" +
                "               AND gcc.CODE_COMBINATION_ID = gd.CODE_COMBINATION_ID\n" +
                "               AND ROWNUM = 1\n" +
                "               AND gd.ACCOUNT_CLASS = 'REV'\n" +
                "               AND gd.org_id = ct.ORG_ID\n" +
                "               AND gd.set_of_books_id = ct.set_of_books_id) CUENTA_MPE,\n" +
                "               (CASE WHEN  (PS.AMOUNT_DUE_ORIGINAL != PS.AMOUNT_DUE_REMAINING AND NOT 0 = PS.AMOUNT_DUE_REMAINING) THEN 'PARCIALMENTE PAGADA'\n" +
                "                     WHEN 0 = PS.AMOUNT_DUE_REMAINING THEN 'PAGADA'\n" +
                "                     WHEN PS.AMOUNT_DUE_ORIGINAL = PS.AMOUNT_DUE_REMAINING THEN 'PENDIENTE DE PAGO' END) ESTADO_ADEUDO,\n" +
                "               NVL((SELECT (CASE WHEN APPLICATION_TYPE = 'CM' THEN  'CANCELADA' ELSE 'FACTURADA' END) ESTADO\n" +
                "                  FROM APPS.AR_RECEIVABLE_APPLICATIONS_ALL ARA\n" +
                "                  WHERE  ARA.DISPLAY = 'Y'\n" +
                "                  AND ARA.APPLIED_CUSTOMER_TRX_ID = CT.CUSTOMER_TRX_ID\n" +
                "                  AND ROWNUM < 2),'FACTURADA') ESTADO,\n" +
                "               PS.AMOUNT_DUE_REMAINING SALDO_DEUDOR,\n" +
                "               ( SELECT SUM(AMOUNT_APPLIED)\n" +
                "               FROM APPS.AR_RECEIVABLE_APPLICATIONS_ALL ARA\n" +
                "               WHERE ARA.APPLICATION_TYPE = 'CASH'\n" +
                "               AND ARA.DISPLAY = 'Y'\n" +
                "               AND ARA.APPLIED_CUSTOMER_TRX_ID = CT.CUSTOMER_TRX_ID) SALDO_COBRADO\n" +
                "      FROM     apps.ar_payment_schedules_all ps,\n" +
                "               apps.ra_customer_trx_all ct,\n" +
                "               apps.hr_all_organization_units hr1,\n" +
                "               apps.xxfc_cfd_factura_digital xcfd,\n" +
                "               apps.ra_cust_trx_types_all rctt,\n" +
                "               apps.xxfc_companias xc,\n" +
                "               apps.hz_cust_accounts hca,\n" +
                "               apps.hz_parties hz \n" +
                "      WHERE    rctt.cust_trx_type_id = ct.cust_trx_type_id\n" +
                "               AND ps.customer_trx_id = ct.customer_trx_id\n" +
                "               AND hr1.organization_id = ct.org_id\n" +
                "               AND xc.oracle_cia = hr1.attribute1\n" +
                "               AND hca.cust_account_id = ct.bill_to_customer_id\n" +
                "               AND hca.party_id = hz.party_id\n" +
                "               AND ct.customer_trx_id = xcfd.customer_trx_id(+)\n" +
                "               AND ct.previous_customer_trx_id IS NULL\n" +
                "               AND CT.TRX_DATE BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')\n" +
                "               AND rctt.name = '" + namePlaza + "' \n" +
                "   AND ct.BILL_TO_CUSTOMER_ID = " + custumerID + "  AND 1=1";
    }

    /**
     * Metodo para regresar la quinta parte del query de una consulta por RFC y fechas
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @return query
     */
    public static String queryReporteFactSerLocRFCFechas5(String inicioDeFecha, String finDeFecha, String namePlaza, String custumerID) {
        return "SELECT  \n" +
                "                  TO_CHAR((SELECT MAX (APPLY_DATE)\n" +
                "                        FROM APPS.AR_RECEIVABLE_APPLICATIONS_ALL ARA,\n" +
                "                           APPS.AR_CASH_RECEIPTS_ALL ACR\n" +
                "                        WHERE ARA.APPLICATION_TYPE = 'CASH'\n" +
                "                        AND ACR.STATUS != 'REV'\n" +
                "                        AND ARA.DISPLAY = 'Y'\n" +
                "                        AND ACR.CASH_RECEIPT_ID = ARA.CASH_RECEIPT_ID\n" +
                "                        AND ARA.APPLIED_CUSTOMER_TRX_ID = CT.CUSTOMER_TRX_ID),'DD/MM/RRRR') FECHA_ULTIMO_RECIBO,\n" +
                "               ( SELECT (CASE WHEN ARM.NAME LIKE 'FEMSA_NET_17ADM' THEN 'COMPENSACION' ELSE 'TRANSFERENCIA' END) FORMA_PAGO\n" +
                "               FROM APPS.AR_RECEIPT_METHODS ARM,\n" +
                "                     APPS.AR_CASH_RECEIPTS_ALL AC,\n" +
                "                     APPS.AR_RECEIVABLE_APPLICATIONS_ALL AAA\n" +
                "               WHERE ARM.RECEIPT_METHOD_ID = AC.RECEIPT_METHOD_ID\n" +
                "                  AND AAA.APPLICATION_TYPE = 'CASH'\n" +
                "                  AND AC.STATUS != 'REV'\n" +
                "                  AND AAA.CASH_RECEIPT_ID = AC.CASH_RECEIPT_ID\n" +
                "                  AND AAA.APPLIED_CUSTOMER_TRX_ID = CT.CUSTOMER_TRX_ID\n" +
                "                  AND AAA.DISPLAY = 'Y'\n" +
                "                  AND AAA.CASH_RECEIPT_ID =\n" +
                "                                 (SELECT MAX (ARA.CASH_RECEIPT_ID)\n" +
                "                                 FROM APPS.AR_RECEIVABLE_APPLICATIONS_ALL ARA,\n" +
                "                                       APPS.AR_CASH_RECEIPTS_ALL ACR2\n" +
                "                                 WHERE ACR2.CASH_RECEIPT_ID = ARA.CASH_RECEIPT_ID\n" +
                "                                       AND ARA.APPLIED_CUSTOMER_TRX_ID = CT.CUSTOMER_TRX_ID\n" +
                "                                       AND ARA.APPLICATION_TYPE = 'CASH'\n" +
                "                                       AND ACR2.STATUS != 'REV'\n" +
                "                                       AND ARA.DISPLAY = 'Y')) FORMA_PAGO,\n" +
                "               XCFD.UUID,\n" +
                "               DBMS_LOB.SUBSTR(XCFD.UUID_RELACIONADO) AS UUID_RELACIONADO,\n" +
                "               TO_CHAR(XCFD.FECHA_TIMBRE,'DD/MM/RRRR') FECHA_TIMBRADO,\n" +
                "               XCFD.ID_FACTURA_DIGITAL ID_TIMBRADO,\n" +
                "               (CASE WHEN XCFD.WM_STATUS = 'E' THEN 'TIMBRADO'\n" +
                "                     WHEN XCFD.WM_STATUS = 'C' THEN 'CANCELADO'\n" +
                "                     WHEN XCFD.WM_STATUS = 'L' THEN 'PENDIENTE DE TIMBRAR' ELSE 'NO TIMBRADO' END) ESTADO_TIMBRADO,\n" +
                "               RCTT.NAME  TIPO_TRANSACCION    \n" +
                "      FROM     apps.ar_payment_schedules_all ps,\n" +
                "               apps.ra_customer_trx_all ct,\n" +
                "               apps.hr_all_organization_units hr1,\n" +
                "               apps.xxfc_cfd_factura_digital xcfd,\n" +
                "               apps.ra_cust_trx_types_all rctt,\n" +
                "               apps.xxfc_companias xc,\n" +
                "               apps.hz_cust_accounts hca,\n" +
                "               apps.hz_parties hz \n" +
                "      WHERE    rctt.cust_trx_type_id = ct.cust_trx_type_id\n" +
                "               AND ps.customer_trx_id = ct.customer_trx_id\n" +
                "               AND hr1.organization_id = ct.org_id\n" +
                "               AND xc.oracle_cia = hr1.attribute1\n" +
                "               AND hca.cust_account_id = ct.bill_to_customer_id\n" +
                "               AND hca.party_id = hz.party_id\n" +
                "               AND ct.customer_trx_id = xcfd.customer_trx_id(+)\n" +
                "               AND ct.previous_customer_trx_id IS NULL\n" +
                "               AND CT.TRX_DATE BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')\n" +
                "               AND rctt.name = '" + namePlaza + "' \n" +
                "   AND ct.BILL_TO_CUSTOMER_ID = " + custumerID + "  AND 1=1";
    }
    //Fin de seccion de querys para la extraccion de informacion de la base de datos para los reportes de XXFC - Reporte de Facturacion Servicios Locales

    //Seccion de querys para la extraccion de información de la base datos para los reportes XXFC - Detalle de transaccionalidad de proveedores
    public static String queryObtenerCodigoRFCRepDetalle(String rfcProveedor, String plaza) {
        return "select aps.Segment1 RFC,apss.vendor_site_code_alt codigoRFC, apss.vendor_site_code Plaza from apps.ap_suppliers           aps, apps.ap_supplier_sites_all  apss\n" +
                "where Segment1='" + rfcProveedor + "'\n" +
                "AND aps.vendor_id = apss.vendor_id\n" +
                "AND apss.vendor_site_code = '" + plaza + "'";
    }

    /**
     * Metodo para regresar la primera parte del query de una consulta numero proveedor, fecha inicial administrativa y fecha final administrativa
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param namePlaza
     * @return query
     */
    public static String queryReporteDetalleTransaccionalidadRFCYFechas1(String inicioDeFecha, String finDeFecha, String namePlaza, String codigoRFC) {
        return "SELECT  xps.trans_id     folio,\n" +
                "             xps.lote,\n" +
                "             xps.fecha_transaccion f_transac,\n" +
                "             xps.hora_transaccion h_transac,\n" +
                "             xps.xxfc_fecha_administrativa f_admin,\n" +
                "             xps.plaza " +
                "  FROM   apps.xxfc_pago_servicios         xps,\n" +
                "      apps.xxfc_retek_item_servicios_v xit,\n" +
                "      apps.ap_suppliers           aps,\n" +
                "      apps.ap_supplier_sites_all  apss  WHERE xps.servicio = xit.item\n" +
                "            AND xps.plaza = xit.site\n" +
                "            AND xit.nacional_local = 'Local'\n" +
                "            AND aps.vendor_id = apss.vendor_id\n" +
                "            AND to_char(xit.supplier) = apss.vendor_site_code_alt\n" +
                "            AND apss.vendor_site_code = xit.site\n" +
                "            AND xps.lote IS NOT NULL AND apss.vendor_site_code_alt = '" + codigoRFC + "'  \n" +
                "\t\t\tAND xps.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND xps.plaza = '" + namePlaza + "'";
    }

    /**
     * Metodo para regresar la segunda parte del query de una consulta numero proveedor, fecha inicial administrativa y fecha final administrativa
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param namePlaza
     * @return query
     */
    public static String queryReporteDetalleTransaccionalidadRFCYFechas2(String inicioDeFecha, String finDeFecha, String namePlaza, String codigoRFC) {
        return "SELECT    xps.tienda,\n" +
                "             xps.servicio,\n" +
                "             aps.segment1 rfc,\n" +
                "             xps.caja,\n" +
                "             xps.ticket,\n" +
                "             xps.valor" +
                "  FROM   apps.xxfc_pago_servicios         xps,\n" +
                "      apps.xxfc_retek_item_servicios_v xit,\n" +
                "      apps.ap_suppliers           aps,\n" +
                "      apps.ap_supplier_sites_all  apss  WHERE xps.servicio = xit.item\n" +
                "            AND xps.plaza = xit.site\n" +
                "            AND xit.nacional_local = 'Local'\n" +
                "            AND aps.vendor_id = apss.vendor_id\n" +
                "            AND to_char(xit.supplier) = apss.vendor_site_code_alt\n" +
                "            AND apss.vendor_site_code = xit.site\n" +
                "            AND xps.lote IS NOT NULL AND apss.vendor_site_code_alt = '" + codigoRFC + "'  \n" +
                "\t\t\tAND xps.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND xps.plaza = '" + namePlaza + "'";
    }

    /**
     * Metodo para regresar la tercera parte del query de una consulta numero proveedor, fecha inicial administrativa y fecha final administrativa
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param namePlaza
     * @return query
     */
    public static String queryReporteDetalleTransaccionalidadRFCYFechas3(String inicioDeFecha, String finDeFecha, String namePlaza, String codigoRFC) {
        return "SELECT    \n" +
                "             xps.comision,\n" +
                "             xps.estatus,\n" +
                "             xps.factura      factura_ap,\n" +
                "             CASE\n" +
                "     WHEN xps.factura_ar = 'N/A' THEN\n" +
                "        'N/A'\n" +
                "     WHEN xps.factura_ar = 'P'   THEN\n" +
                "        NULL\n" +
                "     WHEN xps.factura_ar IS NULL THEN\n" +
                "        NULL\n" +
                "     ELSE\n" +
                "        (\n" +
                "           SELECT trx.trx_number\n" +
                "           FROM xxfc_ar_fact_vta_serv_loc fv     /*CHG0053648*/\n" +
                "  , apps.ra_customer_trx_all         trx\n" +
                "           WHERE fv.servicio = xps.servicio\n" +
                "           AND fv.lote = xps.lote\n" +
                "           AND fv.compania = xps.org_id\n" +
                "           AND fv.tienda = xps.tienda\n" +
                "           AND fv.plaza = xps.plaza\n" +
                "           AND xps.factura_ar = fv.request_id\n" +
                "           AND fv.fecha_administrativa = xps.xxfc_fecha_administrativa\n" +
                "           AND trx.customer_trx_id = fv.factura_ar_id\n" +
                "           AND trx.interface_header_attribute3 = 'Comision Cobro de Servicios'\n" +
                "           AND ROWNUM = 1\n" +
                "        )\n" +
                "             END          factura_ar,\n" +
                "             xps.folio_transaccion f_transac,\n" +
                "             xps.folio_wm " +
                "  FROM   apps.xxfc_pago_servicios         xps,\n" +
                "      apps.xxfc_retek_item_servicios_v xit,\n" +
                "      apps.ap_suppliers           aps,\n" +
                "      apps.ap_supplier_sites_all  apss  WHERE xps.servicio = xit.item\n" +
                "            AND xps.plaza = xit.site\n" +
                "            AND xit.nacional_local = 'Local'\n" +
                "            AND aps.vendor_id = apss.vendor_id\n" +
                "            AND to_char(xit.supplier) = apss.vendor_site_code_alt\n" +
                "            AND apss.vendor_site_code = xit.site\n" +
                "            AND xps.lote IS NOT NULL AND apss.vendor_site_code_alt = '" + codigoRFC + "'  \n" +
                "\t\t\tAND xps.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND xps.plaza = '" + namePlaza + "'";
    }

    /**
     * Metodo para regresar la primera parte del query de una consulta por factura AP, fecha inicial administrativa y fecha final administrativa
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param namePlaza
     * @param facturaAP
     * @return query
     */
    public static String queryReporteDetalleTransaccionalidadFechasFacturaAP1(String inicioDeFecha, String finDeFecha, String namePlaza, String facturaAP) {
        return "SELECT  xps.trans_id     folio,\n" +
                "   xps.lote,\n" +
                "   xps.fecha_transaccion,\n" +
                "   xps.hora_transaccion,\n" +
                "   xps.xxfc_fecha_administrativa,\n" +
                "   xps.plaza " +
                "   FROM   apps.xxfc_pago_servicios         xps,\n" +
                "    apps.xxfc_retek_item_servicios_v xit,\n" +
                "    apps.ap_suppliers           aps,\n" +
                "    apps.ap_supplier_sites_all  apss  WHERE xps.servicio = xit.item\n" +
                "        AND xps.plaza = xit.site\n" +
                "        AND xit.nacional_local = 'Local'\n" +
                "        AND aps.vendor_id = apss.vendor_id\n" +
                "        AND to_char(xit.supplier) = apss.vendor_site_code_alt\n" +
                "        AND apss.vendor_site_code = xit.site\n" +
                "        AND xps.lote IS NOT NULL AND xps.factura = '" + facturaAP + "'  \n" +
                "\t\tAND xps.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND xps.plaza = '" + namePlaza + "'";
    }

    /**
     * Metodo para regresar la segunda parte del query de una consulta por factura AP, fecha inicial administrativa y fecha final administrativa
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param namePlaza
     * @param facturaAP
     * @return query
     */
    public static String queryReporteDetalleTransaccionalidadFechasFacturaAP2(String inicioDeFecha, String finDeFecha, String namePlaza, String facturaAP) {
        return "SELECT  \n" +
                "   xps.tienda,\n" +
                "   xps.servicio,\n" +
                "   aps.segment1 rfc,\n" +
                "   xps.caja,\n" +
                "   xps.ticket,\n" +
                "   xps.valor" +
                "   FROM   apps.xxfc_pago_servicios         xps,\n" +
                "    apps.xxfc_retek_item_servicios_v xit,\n" +
                "    apps.ap_suppliers           aps,\n" +
                "    apps.ap_supplier_sites_all  apss  WHERE xps.servicio = xit.item\n" +
                "        AND xps.plaza = xit.site\n" +
                "        AND xit.nacional_local = 'Local'\n" +
                "        AND aps.vendor_id = apss.vendor_id\n" +
                "        AND to_char(xit.supplier) = apss.vendor_site_code_alt\n" +
                "        AND apss.vendor_site_code = xit.site\n" +
                "        AND xps.lote IS NOT NULL AND xps.factura = '" + facturaAP + "'  \n" +
                "\t\tAND xps.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND xps.plaza = '" + namePlaza + "'";
    }

    /**
     * Metodo para regresar la tercera parte del query de una consulta por factura AP, fecha inicial administrativa y fecha final administrativa
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param namePlaza
     * @param facturaAP
     * @return query
     */
    public static String queryReporteDetalleTransaccionalidadFechasFacturaAP3(String inicioDeFecha, String finDeFecha, String namePlaza, String facturaAP) {
        return "SELECT  xps.comision,\n" +
                "   xps.estatus,\n" +
                "   xps.factura      factura_ap,\n" +
                "   CASE\n" +
                "         WHEN xps.factura_ar = 'N/A' THEN\n" +
                "  'N/A'\n" +
                "         WHEN xps.factura_ar = 'P'   THEN\n" +
                "  NULL\n" +
                "         WHEN xps.factura_ar IS NULL THEN\n" +
                "  NULL\n" +
                "         ELSE\n" +
                "  (\n" +
                "     SELECT trx.trx_number\n" +
                "     FROM xxfc_ar_fact_vta_serv_loc fv     /*CHG0053648*/\n" +
                "        , apps.ra_customer_trx_all         trx\n" +
                "     WHERE fv.servicio = xps.servicio\n" +
                "     AND fv.lote = xps.lote\n" +
                "     AND fv.compania = xps.org_id\n" +
                "     AND fv.tienda = xps.tienda\n" +
                "     AND fv.plaza = xps.plaza\n" +
                "     AND xps.factura_ar = fv.request_id\n" +
                "     AND fv.fecha_administrativa = xps.xxfc_fecha_administrativa\n" +
                "     AND trx.customer_trx_id = fv.factura_ar_id\n" +
                "     AND trx.interface_header_attribute3 = 'Comision Cobro de Servicios'\n" +
                "     AND ROWNUM = 1\n" +
                "  )\n" +
                "   END          factura_ar,\n" +
                "   xps.folio_transaccion folio_transa,\n" +
                "   xps.folio_wm  " +
                "   FROM   apps.xxfc_pago_servicios         xps,\n" +
                "    apps.xxfc_retek_item_servicios_v xit,\n" +
                "    apps.ap_suppliers           aps,\n" +
                "    apps.ap_supplier_sites_all  apss  WHERE xps.servicio = xit.item\n" +
                "        AND xps.plaza = xit.site\n" +
                "        AND xit.nacional_local = 'Local'\n" +
                "        AND aps.vendor_id = apss.vendor_id\n" +
                "        AND to_char(xit.supplier) = apss.vendor_site_code_alt\n" +
                "        AND apss.vendor_site_code = xit.site\n" +
                "        AND xps.lote IS NOT NULL AND xps.factura = '" + facturaAP + "'  \n" +
                "\t\tAND xps.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND xps.plaza = '" + namePlaza + "'";
    }

    /**
     * Metodo para regresar la primera parte del query de una consulta por factura AR, fecha inicial administrativa y fecha final administrativa
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param namePlaza
     * @param facturaARID
     * @return query
     */
    public static String queryReporteDetalleTransaccionalidadFechasFacturaAR1(String inicioDeFecha, String finDeFecha, String namePlaza, String facturaARID) {
        return "SELECT  xps.trans_id     folio,\n" +
                "   xps.lote,\n" +
                "   xps.fecha_transaccion,\n" +
                "   xps.hora_transaccion,\n" +
                "   xps.xxfc_fecha_administrativa,\n" +
                "   xps.plaza " +
                "   FROM   apps.xxfc_pago_servicios         xps,\n" +
                "    apps.xxfc_retek_item_servicios_v xit,\n" +
                "    apps.ap_suppliers           aps,\n" +
                "    apps.ap_supplier_sites_all  apss  WHERE xps.servicio = xit.item\n" +
                "            AND xps.plaza = xit.site\n" +
                "            AND xit.nacional_local = 'Local'\n" +
                "            AND aps.vendor_id = apss.vendor_id\n" +
                "            AND to_char(xit.supplier) = apss.vendor_site_code_alt\n" +
                "            AND apss.vendor_site_code = xit.site\n" +
                "            AND xps.lote IS NOT NULL AND EXISTS (SELECT 1\n" +
                "    FROM xxfc_ar_fact_vta_serv_loc   fv\n" +
                "       , apps.ra_customer_trx_all         trx\n" +
                "    WHERE fv.servicio = xps.servicio\n" +
                "    AND fv.lote = xps.lote\n" +
                "    AND fv.compania = xps.org_id\n" +
                "    AND fv.tienda = xps.tienda\n" +
                "    AND fv.plaza = xps.plaza\n" +
                "    AND xps.factura_ar = fv.request_id\n" +
                "    AND fv.fecha_administrativa = xps.xxfc_fecha_administrativa\n" +
                "    AND fv.factura_ar_id = '"+facturaARID+"')  AND xps.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('"+inicioDeFecha+" 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('"+finDeFecha+" 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND xps.plaza = '"+namePlaza+"'";
    }

    /**
     * Metodo para regresar la segunda parte del query de una consulta por factura AR, fecha inicial administrativa y fecha final administrativa
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param namePlaza
     * @param facturaARID
     * @return query
     */
    public static String queryReporteDetalleTransaccionalidadFechasFacturaAR2(String inicioDeFecha, String finDeFecha, String namePlaza, String facturaARID) {
        return "SELECT  \n" +
                "   xps.tienda,\n" +
                "   xps.servicio,\n" +
                "   aps.segment1 rfc,\n" +
                "   xps.caja,\n" +
                "   xps.ticket,\n" +
                "   xps.valor " +
                "   FROM   apps.xxfc_pago_servicios         xps,\n" +
                "    apps.xxfc_retek_item_servicios_v xit,\n" +
                "    apps.ap_suppliers           aps,\n" +
                "    apps.ap_supplier_sites_all  apss  WHERE xps.servicio = xit.item\n" +
                "            AND xps.plaza = xit.site\n" +
                "            AND xit.nacional_local = 'Local'\n" +
                "            AND aps.vendor_id = apss.vendor_id\n" +
                "            AND to_char(xit.supplier) = apss.vendor_site_code_alt\n" +
                "            AND apss.vendor_site_code = xit.site\n" +
                "            AND xps.lote IS NOT NULL AND EXISTS (SELECT 1\n" +
                "    FROM xxfc_ar_fact_vta_serv_loc   fv\n" +
                "       , apps.ra_customer_trx_all         trx\n" +
                "    WHERE fv.servicio = xps.servicio\n" +
                "    AND fv.lote = xps.lote\n" +
                "    AND fv.compania = xps.org_id\n" +
                "    AND fv.tienda = xps.tienda\n" +
                "    AND fv.plaza = xps.plaza\n" +
                "    AND xps.factura_ar = fv.request_id\n" +
                "    AND fv.fecha_administrativa = xps.xxfc_fecha_administrativa\n" +
                "    AND fv.factura_ar_id = '"+facturaARID+"')  AND xps.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('"+inicioDeFecha+" 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('"+finDeFecha+" 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND xps.plaza = '"+namePlaza+"'";
    }

    /**
     * Metodo para regresar la tercera parte del query de una consulta por factura AR, fecha inicial administrativa y fecha final administrativa
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param namePlaza
     * @param facturaARID
     * @return query
     */
    public static String queryReporteDetalleTransaccionalidadFechasFacturaAR3(String inicioDeFecha, String finDeFecha, String namePlaza, String facturaARID) {
        return "SELECT  xps.comision,\n" +
                "   xps.estatus,\n" +
                "   xps.factura      factura_ap,\n" +
                "   CASE\n" +
                "         WHEN xps.factura_ar = 'N/A' THEN\n" +
                "  'N/A'\n" +
                "         WHEN xps.factura_ar = 'P'   THEN\n" +
                "  NULL\n" +
                "         WHEN xps.factura_ar IS NULL THEN\n" +
                "  NULL\n" +
                "         ELSE\n" +
                "  (\n" +
                "     SELECT trx.trx_number\n" +
                "     FROM xxfc_ar_fact_vta_serv_loc fv     /*CHG0053648*/\n" +
                "        , apps.ra_customer_trx_all         trx\n" +
                "     WHERE fv.servicio = xps.servicio\n" +
                "     AND fv.lote = xps.lote\n" +
                "     AND fv.compania = xps.org_id\n" +
                "     AND fv.tienda = xps.tienda\n" +
                "     AND fv.plaza = xps.plaza\n" +
                "     AND xps.factura_ar = fv.request_id\n" +
                "     AND fv.fecha_administrativa = xps.xxfc_fecha_administrativa\n" +
                "     AND trx.customer_trx_id = fv.factura_ar_id\n" +
                "     AND trx.interface_header_attribute3 = 'Comision Cobro de Servicios'\n" +
                "     AND ROWNUM = 1\n" +
                "  )\n" +
                "   END          factura_ar,\n" +
                "   xps.folio_transaccion folio_transa,\n" +
                "   xps.folio_wm  " +
                "   FROM   apps.xxfc_pago_servicios         xps,\n" +
                "    apps.xxfc_retek_item_servicios_v xit,\n" +
                "    apps.ap_suppliers           aps,\n" +
                "    apps.ap_supplier_sites_all  apss  WHERE xps.servicio = xit.item\n" +
                "            AND xps.plaza = xit.site\n" +
                "            AND xit.nacional_local = 'Local'\n" +
                "            AND aps.vendor_id = apss.vendor_id\n" +
                "            AND to_char(xit.supplier) = apss.vendor_site_code_alt\n" +
                "            AND apss.vendor_site_code = xit.site\n" +
                "            AND xps.lote IS NOT NULL AND EXISTS (SELECT 1\n" +
                "    FROM xxfc_ar_fact_vta_serv_loc   fv\n" +
                "       , apps.ra_customer_trx_all         trx\n" +
                "    WHERE fv.servicio = xps.servicio\n" +
                "    AND fv.lote = xps.lote\n" +
                "    AND fv.compania = xps.org_id\n" +
                "    AND fv.tienda = xps.tienda\n" +
                "    AND fv.plaza = xps.plaza\n" +
                "    AND xps.factura_ar = fv.request_id\n" +
                "    AND fv.fecha_administrativa = xps.xxfc_fecha_administrativa\n" +
                "    AND fv.factura_ar_id = '"+facturaARID+"')  AND xps.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('"+inicioDeFecha+" 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('"+finDeFecha+" 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND xps.plaza = '"+namePlaza+"'";
    }
    //Fin de seccion de querys para la extraccion de información de la base de datos para los reportes XXFC - Detalle de transaccionalidad de proveedores

    //Inicio de querys para caso 7 de HU54-47

    /**
     * Metodo para regresar la query con el numero de transacciones y monto de transaccion que vemos en el reporte de facturacion
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param namePlaza
     * @return query
     */
    public static String queryCountSumXPSValor(String inicioDeFecha, String finDeFecha, String namePlaza) {
        return "SELECT        count(*) num_transacciones,\n" +
                "                                       CASE WHEN xit.porc_cantidad = '$' THEN\n" +
                "                                          '$ '||xit.comision_oxxo\n" +
                "                                       ELSE\n" +
                "                                          xit.comision_oxxo||' %'\n" +
                "                                       END comision\n" +
                "                                         FROM    apps.xxfc_pago_servicios         s,\n" +
                "                                 apps.xxfc_retek_item_servicios_v xit,\n" +
                "                                 apps.ap_suppliers                aps,\n" +
                "                                 apps.ap_supplier_sites_all       apss,\n" +
                "                                 apps.xxco_tiendas_cnt_v          xtc  WHERE   xit.nacional_local = 'Local'\n" +
                "                           AND    s.servicio = xit.item\n" +
                "                           AND    aps.vendor_id = apss.vendor_id\n" +
                "                           AND    s.plaza = xit.site\n" +
                "                           AND    TO_CHAR(xit.supplier) = apss.vendor_site_code_alt\n" +
                "                           AND    apss.vendor_site_code = xit.site\n" +
                "                           AND    xtc.cr_plaza = s.plaza\n" +
                "                           AND    xtc.cr_tienda = s.tienda\n" +
                "                           AND    s.lote IS NOT NULL\n" +
                "                           AND    s.estatus = 'E'  AND s.factura_ar IS NOT NULL\n" +
                "                                               AND s.factura_ar <> 'P'\n" +
                "                                               AND s.factura_ar <> 'N/A'  AND s.XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS') AND TO_DATE('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')  AND s.plaza = '" + namePlaza + "' \n" +
                "                                               GROUP BY s.lote,\n" +
                "                                                 s.xxfc_fecha_administrativa,\n" +
                "                                                 s.plaza,\n" +
                "                                                 s.servicio,\n" +
                "                                                 xit.SUP_NAME,\n" +
                "                                                 aps.segment1,\n" +
                "                                                 aps.vendor_name,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 s.factura_ar,\n" +
                "                                                 xit.sup_name,\n" +
                "                                                 aps.vendor_id,\n" +
                "                                                 xit.comision_oxxo,\n" +
                "                                                 xit.porc_cantidad,\n" +
                "                                                 xtc.es_franja_fronteriza,\n" +
                "                                                 DECODE(xit.incluye_iva||DECODE(SUBSTR(REPLACE(s.ref1||s.ref2,' '),-1),'S','S','N'),'SS','1','0')";
    }

    /**
     * Metodo para regresar la query con el porcentaje de  comision de oxxo que se ve en el reporte de facturacion
     *
     * @param inicioDeFecha
     * @param finDeFecha
     * @param namePlaza
     * @param numeroReferencia
     * @return query
     */
    public static String queryCountComisionOxxo(String inicioDeFecha, String finDeFecha, String namePlaza, String numeroReferencia) {
        return "select xit.COMISION_OXXO, count(*)\n" +
                "   from apps.xxfc_pago_servicios xps,\n" +
                "   xxfc_retek_item_servicios_v xit\n" +
                "   where xps.xxfc_fecha_administrativa >= TO_DATE ('" + inicioDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')\n" +
                "  AND xps.xxfc_fecha_administrativa <= TO_DATE ('" + finDeFecha + " 00:00:00','YYYY/MM/DD HH24:MI:SS')\n" +
                "      And xps.estatus = 'E'\n" +
                "      And xps.servicio = xit.item\n" +
                "      And xps.plaza = xit.site\n" +
                "      And xps.plaza = '" + namePlaza + "'\n" +
                "      And factura_ar = '" + numeroReferencia + "'\n" +
                "      group By xit.COMISION_OXXO";
    }

    /**
     * Metodo para regresar la query de una consulta de un grupo de ejecucion o de excepcion creado
     *
     * @param grupoEjecucion
     * @return
     */
    public static String queryConsultaGrupoEjecucion(String grupoEjecucion) {
        return "select ID_HIST_GRUPO, GRUPO_EJECUCION GRP_EJECU, MOVIMIENTO_DESCRIPCION MOV_DES, CREATION_DATE\n" +
                "FROM apps.XXFC_AP_GPOS_SNC_HIST_ALL\n" +
                "where tipo_servicio='LOCAL'\n" +
                "AND GRUPO_EJECUCION='" + grupoEjecucion + "'";
    }

    /**
     * Metodo para regresar la primera parte del query de una consulta de creacion item para grupo de ejecucion o excepcion
     *
     * @param grupoEjecucion
     * @return query
     */
    public static String queryConsultaAsignarGruposDeEjecucionOExcepcion1(String grupoEjecucion) {
        return "select ID_HIST_GRUPO, ID_GRUPO, ID_GRUPO_ITEM, SERVICIO, TIPO_SERVICIO \n" +
                "FROM apps.XXFC_AP_GPOS_SNC_HIST_ALL\n" +
                "where tipo_servicio='LOCAL'\n" +
                "AND GRUPO_EJECUCION='" + grupoEjecucion + "'\n" +
                "AND MOVIMIENTO_DESCRIPCION='Crea Item Grupo Ejecucion'\n";
    }

    /**
     * Metodo para regresar la segunda parte del query de una consulta de creacion item para grupo de ejecucion o excepcion
     *
     * @param grupoEjecucion
     * @return query
     */
    public static String queryConsultaAsignarGruposDeEjecucionOExcepcion2(String grupoEjecucion) {
        return "select GRUPO_EJECUCION, HORA_EJEC, ATRIBUTO1, MOVIMIENTO_DESCRIPCION MOV_Descripcion\n" +
                "FROM apps.XXFC_AP_GPOS_SNC_HIST_ALL\n" +
                "where tipo_servicio='LOCAL'\n" +
                "AND GRUPO_EJECUCION='" + grupoEjecucion + "'\n" +
                "AND MOVIMIENTO_DESCRIPCION='Crea Item Grupo Ejecucion'\n";
    }

    /**
     * Metodo para regresar la consulta de accion de cambio de hora item para grupo de ejecucion o excepcion
     *
     * @param grupoEjecucion
     * @return query
     */
    public static String queryConsultaHoraNueva(String grupoEjecucion) {
        return "Select GRUPO_EJECUCION, SERVICIO, HORA_EJEC\n" +
                "FROM apps.XXFC_AP_GPOS_SNC_HIST_ALL\n" +
                "where tipo_servicio='LOCAL'\n" +
                "AND GRUPO_EJECUCION='" + grupoEjecucion + "'\n" +
                "AND MOVIMIENTO_CODIGO='CAMBIO'\n" +
                "ORDER BY ID_HIST_GRUPO DESC";
    }

    /**
     * Metodo para regresar la consulta de un grupo de ejecucion o excepcion que se elimino
     *
     * @param grupoEjecucion
     * @return query
     */
    public static String queryConsultaEliminacionGrupo(String grupoEjecucion) {
        return "select  ID_HIST_GRUPO, TIPO_SERVICIO , GRUPO_EJECUCION , MOVIMIENTO_DESCRIPCION MOV_DESCRIP\n" +
                "FROM apps.XXFC_AP_GPOS_SNC_HIST_ALL\n" +
                "where tipo_servicio='LOCAL'\n" +
                "AND GRUPO_EJECUCION='" + grupoEjecucion + "'\n" +
                "AND MOVIMIENTO_CODIGO = 'BAJA'";
    }

    /**
     * Metodo para regresar la consulta de un grupo de ejecucion o excepcion que se elimino el item
     *
     * @param grupoEjecucion
     * @return query
     */
    public static String queryConsultaEliminacionItem(String grupoEjecucion) {
        return "select  ID_HIST_GRUPO, TIPO_SERVICIO , GRUPO_EJECUCION , MOVIMIENTO_DESCRIPCION MOV_DESCRIP\n" +
                "FROM apps.XXFC_AP_GPOS_SNC_HIST_ALL\n" +
                "where tipo_servicio='LOCAL'\n" +
                "AND GRUPO_EJECUCION='" + grupoEjecucion + "'\n" +
                "AND MOVIMIENTO_CODIGO = 'BAJA'\n" +
                "AND MOVIMIENTO_DESCRIPCION = 'Elimina Item Grupo Ejecucion'";
    }
}