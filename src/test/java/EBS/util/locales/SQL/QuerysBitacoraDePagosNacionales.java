package EBS.util.locales.SQL;

public class QuerysBitacoraDePagosNacionales {
    /**
     * Metodo para regresar la informacion de una consulta de una Bitacora de Pagos de Servicios Nacionales y Corresponsalías por medio de los valores Corresponsalias o Nacional, fecha inicio, fecha final,Servicio de forma Global
     *
     * @param fechaInicio
     * @param fechaFin
     * @param numServicio
     * @param tipoServicio
     * @return query
     */
    public static String queryCorresponsaliasServiciosYServiciosNacGlobal(String fechaInicio, String fechaFin, String numServicio, String tipoServicio) {
        String sqlLinea3 = "";
        String sqlLinea13 = "";
        String sqlLinea45AntesDeUnion = "";
        String sqlLineaTablaDF = "";
        String sqlLinea82o81CorSerNac = "";
        String sqlLinea83o84CorSerNac = "";
        String sqlLinea88CorSerNac = "";
        if (tipoServicio.equals("CORRESPONSALIAS")) {
            sqlLinea3 = " FROM (SELECT  aia.global_attribute2 c_servicio \n";
            sqlLinea13 = " , MIN(gh.grupo_ejecucion) c_gpo_ejecucion \n";
            sqlLinea45AntesDeUnion = "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aia.global_attribute2 = '" + numServicio + "' AND   gh.tipo_servicio = '" + tipoServicio + "' AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n" +
                    " GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n";
            sqlLineaTablaDF = " , apps.xxfc_ap_ar_pag_scn_tmp apt \n";
            sqlLinea82o81CorSerNac = " AND TO_CHAR(gh.id_grupo) = aia.global_attribute1 \n";
            sqlLinea83o84CorSerNac = " AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aia.global_attribute2 = '" + numServicio + "'AND   gh.tipo_servicio = '" + tipoServicio + "' AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n";
            sqlLinea88CorSerNac = "  ";
        } else if (tipoServicio.equals("SERVICIOS NACIONALES")) {
            tipoServicio = "NACIONAL";
            sqlLinea3 = " FROM (SELECT * FROM (SELECT  aia.global_attribute2 c_servicio \n";
            sqlLinea13 = " , DECODE(gh.tipo_servicio, '" + tipoServicio + "', MIN(gh.grupo_ejecucion), 'SIN GRUPO' ) c_gpo_ejecucion \n";
            sqlLinea45AntesDeUnion = "  AND (   gh.tipo_servicio = '" + tipoServicio + "'\n" +
                    "  OR aia.pay_group_lookup_code LIKE '%SERVHL' ) \n" +
                    "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aia.global_attribute2 = '" + numServicio + "'\n" +
                    "  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name, gh.tipo_servicio \n";
            sqlLineaTablaDF = " ";
            sqlLinea82o81CorSerNac = " AND   TO_CHAR(gh.id_grupo) = aia.global_attribute1 \n";
            sqlLinea83o84CorSerNac = " AND   gh.tipo_servicio = '" + tipoServicio + "' \n" +
                    "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aia.global_attribute2 = '" + numServicio + "' \n";
            sqlLinea88CorSerNac = " UNION\n" +
                    "              SELECT  DISTINCT aia.global_attribute2 c_servicio\n" +
                    "                        , (SELECT DISTINCT xrs.item_desc\n" +
                    "                           FROM   xxfc_retek_item_servicios_v xrs\n" +
                    "                           WHERE  aia.global_attribute2 = xrs.item) c_nombre_serv\n" +
                    "                        , aca.amount c_cantidad\n" +
                    "                        , aia.invoice_date c_fecha_fact\n" +
                    "                        , aia.invoice_num c_factura\n" +
                    "                        , aca.check_date c_fecha_pago\n" +
                    "                        , aps.vendor_name c_nombre_prov\n" +
                    "                        , 'SIN GRUPO'  c_gpo_ejecucion\n" +
                    "                  FROM apps.ap_invoice_payments_all aipa\n" +
                    "                     , apps.ap_invoices_all aia\n" +
                    "                     , apps.ap_checks_all aca\n" +
                    "                     , apps.ap_suppliers    aps\n" +
                    "                     , apps.ap_supplier_sites_all apss\n" +
                    "                  WHERE 1=1\n" +
                    "                  AND   aca.check_id               = aipa.check_id\n" +
                    "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                    "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                    "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                    "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                    "                  AND   apss.vendor_site_code      = '17ADM'\n" +
                    "                  AND   aps.enabled_flag           = 'Y'\n" +
                    "                  AND   aia.set_of_books_id        = 2022\n" +
                    "                  AND   NOT EXISTS (SELECT DISTINCT ge.grupo_ejecucion\n" +
                    "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all ge\n" +
                    "                                        , APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                    "                                        , xxfc_retek_item_servicios_v xri\n" +
                    "                                     WHERE gh.id_grupo = ge.id_grupo\n" +
                    "                                     AND   gh.atributo3 = 'SERVNACCOR'\n" +
                    "                                     AND   gh.atributo4 != 'Y'\n" +
                    "                                     AND (  (   ge.servicio = xri.item\n" +
                    "                                             AND   ge.servicio IS NOT NULL)\n" +
                    "                                          OR (     ge.rfc = apss.vendor_site_code_alt\n" +
                    "                                                AND  ge.rfc IS NOT NULL)\n" +
                    "                                          )\n" +
                    "                                     AND apss.vendor_site_code_alt  = TO_CHAR(xri.supplier)\n" +
                    "                                     )\n" +
                    "                  --CHG0053691_8 INICIO\n" +
                    "                  AND    NOT EXISTS (SELECT gh.id_grupo\n" +
                    "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                    "                                     WHERE gh.atributo3 = 'SERVNACCOR'\n" +
                    "                                     AND   gh.atributo4 != 'Y'\n" +
                    "                                     AND   gh.id_grupo = aia.global_attribute1\n" +
                    "                                     )  --CHG0053691_8 FIN \n" +
                    "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aia.global_attribute2 = '" + numServicio + "') \n";
        }
        return " SELECT c_servicio, c_nombre_serv, c_nombre_prov, c_fecha_pago, SUM(c_cantidad) c_cantidad, c_gpo_ejecucion \n" +
                sqlLinea3 +
                "  , CASE\n" +
                "   WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "  xrs.item_desc\n" +
                "  END c_nombre_serv\n" +
                "  , aca.amount c_cantidad\n" +
                "  , aia.invoice_date c_fecha_fact\n" +
                "  , aia.invoice_num c_factura\n" +
                "  , aca.check_date c_fecha_pago\n" +
                "  , aps.vendor_name c_nombre_prov \n" +
                sqlLinea13 +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/ \n" +
                sqlLinea45AntesDeUnion +
                "UNION\n" +
                "              SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END c_nombre_serv\n" +
                "                        , aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                "                        , MIN(gh.grupo_ejecucion) c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss \n" +
                sqlLineaTablaDF +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y' \n" +
                sqlLinea82o81CorSerNac +
                " AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/ \n" +
                sqlLinea83o84CorSerNac +
                " GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                sqlLinea88CorSerNac +
                " ) " +
                " GROUP BY c_servicio, c_nombre_serv, c_nombre_prov, c_fecha_pago, c_gpo_ejecucion ORDER BY c_nombre_prov, c_fecha_pago";
    }

    /**
     * Metodo para regresar la primera parte de la informacion de una consulta de una Bitacora de Pagos de Servicios Nacionales y Corresponsalías por medio de los valores Corresponsalias,fecha inicio, fecha final, Servicio de forma Detallada
     *
     * @param fechaInicio
     * @param fechaFin
     * @param numServicio
     * @param tipoServicio
     * @return query
     */
    public static String queryCorresponsaliasServiciosDetallado1(String fechaInicio, String fechaFin, String numServicio, String tipoServicio) {
        return "SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END c_nombre_serv\n" +
                "                        , aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/ \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "AND aia.global_attribute2 = '" + numServicio + "'AND   gh.tipo_servicio = '" + tipoServicio + "'AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                "UNION\n" +
                "              SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END c_nombre_serv\n" +
                "                        , aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_ap_ar_pag_scn_tmp apt\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "                  AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/ \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "AND aia.global_attribute2 = '" + numServicio + "'AND   gh.tipo_servicio = '" + tipoServicio + "'AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                "ORDER BY  c_fecha_pago";
    }

    /**
     * Metodo para regresar la segunda parte de la informacion de una consulta de una Bitacora de Pagos de Servicios Nacionales y Corresponsalías por medio de los valores Corresponsalias,fecha inicio, fecha final, Servicio de forma Detallada
     *
     * @param fechaInicio
     * @param fechaFin
     * @param numServicio
     * @param tipoServicio
     * @return
     */
    public static String queryCorresponsaliasServiciosDetallado2(String fechaInicio, String fechaFin, String numServicio, String tipoServicio) {
        return "SELECT   aps.vendor_name c_nombre_prov\n" +
                "                        , MIN(gh.grupo_ejecucion) c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/ \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "AND aia.global_attribute2 = '" + numServicio + "'AND   gh.tipo_servicio = '" + tipoServicio + "'AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                "UNION\n" +
                "              SELECT   aps.vendor_name c_nombre_prov\n" +
                "                        , MIN(gh.grupo_ejecucion) c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_ap_ar_pag_scn_tmp apt\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "                  AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/ \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "AND aia.global_attribute2 = '" + numServicio + "'AND   gh.tipo_servicio = '" + tipoServicio + "'AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                "ORDER BY c_nombre_prov";
    }

    /**
     * Metodo para regresar la consulta donde viene el numero de proveedor y el codigo de proveedor.
     *
     * @param numProveedor
     * @return codigo de proveedor.
     */
    public static String queryConsultaCodigoProveedor(String numProveedor) {
        return "select VENDOR_SITE_CODE_ALT Proveedor, vendor_id Codigo_De_Proveedor from apps.ap_supplier_sites_all where VENDOR_SITE_CODE_ALT = '" + numProveedor + "'";
    }

    /**
     * Metodo para regresar la informacion de una consulta de una Bitacora de Pagos de Servicios Nacionales y Corresponsalías por medio de los valores Corresponsalias o Nacional,fecha inicio, fecha final, proveedor de forma Global
     *
     * @param fechaInicio
     * @param fechaFin
     * @param codigoProveedor
     * @param tipoServicio
     * @return query
     */
    public static String queryCorresponsaliasYServNacProveedorGlobal(String fechaInicio, String fechaFin, String codigoProveedor, String tipoServicio) {
        String sqlLinea3 = "";
        String sqlLinea13 = "";
        String sqlLinea45AntesDeUnion = "";
        String sqlLineaTablaAPT = "";
        String sqlLinea83o84CorSerNac = "";
        String sqlLinea88CorSerNac = "";
        if (tipoServicio.equals("CORRESPONSALIAS")) {
            sqlLinea3 = " FROM (SELECT  aia.global_attribute2 c_servicio\n";
            sqlLinea13 = " , MIN(gh.grupo_ejecucion) c_gpo_ejecucion\n";
            sqlLinea45AntesDeUnion = " AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aps.vendor_id = '" + codigoProveedor + "' AND   gh.tipo_servicio = '" + tipoServicio + "' AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n" +
                    "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n";
            sqlLineaTablaAPT = " , apps.xxfc_ap_ar_pag_scn_tmp apt";
            sqlLinea83o84CorSerNac = " AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aps.vendor_id = '" + codigoProveedor + "' AND   gh.tipo_servicio = '" + tipoServicio + "' AND aia.pay_group_lookup_code NOT LIKE '%SERVHL'  \n";
            sqlLinea88CorSerNac = "   ";

        } else if (tipoServicio.equals("SERVICIOS NACIONALES")) {
            tipoServicio = "NACIONAL";
            sqlLinea3 = " FROM (SELECT * FROM (SELECT  aia.global_attribute2 c_servicio\n";
            sqlLinea13 = " , DECODE(gh.tipo_servicio, '" + tipoServicio + "', MIN(gh.grupo_ejecucion), 'SIN GRUPO' ) c_gpo_ejecucion\n";
            sqlLinea45AntesDeUnion = " AND (   gh.tipo_servicio = '" + tipoServicio + "'\n" +
                    "                       OR aia.pay_group_lookup_code LIKE '%SERVHL' ) \n" +
                    "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aps.vendor_id = '" + codigoProveedor + "'\n" +
                    "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name, gh.tipo_servicio \n";
            sqlLineaTablaAPT = " ";
            sqlLinea83o84CorSerNac = "  AND   gh.tipo_servicio = '" + tipoServicio + "' \n" +
                    " AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    " AND aps.vendor_id = '" + codigoProveedor + "' \n";
            sqlLinea88CorSerNac = "UNION\n" +
                    "              SELECT  DISTINCT aia.global_attribute2 c_servicio\n" +
                    "                        , (SELECT DISTINCT xrs.item_desc\n" +
                    "                           FROM   xxfc_retek_item_servicios_v xrs\n" +
                    "                           WHERE  aia.global_attribute2 = xrs.item) c_nombre_serv\n" +
                    "                        , aca.amount c_cantidad\n" +
                    "                        , aia.invoice_date c_fecha_fact\n" +
                    "                        , aia.invoice_num c_factura\n" +
                    "                        , aca.check_date c_fecha_pago\n" +
                    "                        , aps.vendor_name c_nombre_prov\n" +
                    "                        , 'SIN GRUPO'  c_gpo_ejecucion\n" +
                    "                  FROM apps.ap_invoice_payments_all aipa\n" +
                    "                     , apps.ap_invoices_all aia\n" +
                    "                     , apps.ap_checks_all aca\n" +
                    "                     , apps.ap_suppliers    aps\n" +
                    "                     , apps.ap_supplier_sites_all apss\n" +
                    "                  WHERE 1=1\n" +
                    "                  AND   aca.check_id               = aipa.check_id\n" +
                    "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                    "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                    "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                    "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                    "                  AND   apss.vendor_site_code      = '17ADM'\n" +
                    "                  AND   aps.enabled_flag           = 'Y'\n" +
                    "                  AND   aia.set_of_books_id        = 2022\n" +
                    "                  AND   NOT EXISTS (SELECT DISTINCT ge.grupo_ejecucion\n" +
                    "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all ge\n" +
                    "                                        , APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                    "                                        , xxfc_retek_item_servicios_v xri\n" +
                    "                                     WHERE gh.id_grupo = ge.id_grupo\n" +
                    "                                     AND   gh.atributo3 = 'SERVNACCOR'\n" +
                    "                                     AND   gh.atributo4 != 'Y'\n" +
                    "                                     AND (  (   ge.servicio = xri.item\n" +
                    "                                             AND   ge.servicio IS NOT NULL)\n" +
                    "                                          OR (     ge.rfc = apss.vendor_site_code_alt\n" +
                    "                                                AND  ge.rfc IS NOT NULL)\n" +
                    "                                          )\n" +
                    "                                     AND apss.vendor_site_code_alt  = TO_CHAR(xri.supplier)\n" +
                    "                                     )\n" +
                    "                  --CHG0053691_8 INICIO\n" +
                    "                  AND    NOT EXISTS (SELECT gh.id_grupo\n" +
                    "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                    "                                     WHERE gh.atributo3 = 'SERVNACCOR'\n" +
                    "                                     AND   gh.atributo4 != 'Y'\n" +
                    "                                     AND   gh.id_grupo = aia.global_attribute1\n" +
                    "                                     )  --CHG0053691_8 FIN \n" +
                    "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aps.vendor_id = '" + codigoProveedor + "') \n";
        }
        return " SELECT  c_nombre_prov, c_fecha_pago, SUM(c_cantidad) c_cantidad, c_gpo_ejecucion \n" +
                sqlLinea3 +
                ", CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END c_nombre_serv\n" +
                "                        , aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov \n" +
                sqlLinea13 +
                "FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/ \n" +
                sqlLinea45AntesDeUnion +
                "UNION\n" +
                "              SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END c_nombre_serv\n" +
                "                        , aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                "                        , MIN(gh.grupo_ejecucion) c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss \n" +
                sqlLineaTablaAPT +
                ", apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "                  AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/ \n" +
                sqlLinea83o84CorSerNac +
                " GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                sqlLinea88CorSerNac +
                " )\n" +
                " GROUP BY c_servicio, c_nombre_serv, c_nombre_prov, c_fecha_pago, c_gpo_ejecucion ORDER BY c_nombre_prov, c_fecha_pago ";
    }

    /**
     * Metodo para regresar la informacion de una consulta de una Bitacora de Pagos de Servicios Nacionales y Corresponsalías por medio de los valores Corresponsalias o Nacional,fecha inicio, fecha final, proveedor de forma Detallada
     *
     * @param fechaInicio
     * @param fechaFin
     * @param codigoProveedor
     * @param tipoServicio
     * @return query
     */
    public static String queryCorresponsaliasYServNacProveedorDetallado(String fechaInicio, String fechaFin, String codigoProveedor, String tipoServicio) {
        String sqlLinea2 = "";
        String sqlLinea7 = "";
        String sqlLinea39AntesDeUnion = "";
        String sqlLineaTablaAPT = "";
        String sqlLinea73o75CorSerNac = "";
        String sqlLinea77CorSerNac = "";
        if (tipoServicio.equals("CORRESPONSALIAS")) {
            sqlLinea2 = "SELECT   aca.amount c_cantidad\n";
            sqlLinea7 = ", MIN(gh.grupo_ejecucion) c_gpo_ejec\n";
            sqlLinea39AntesDeUnion = "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aps.vendor_id = '" + codigoProveedor + "' AND   gh.tipo_servicio = '" + tipoServicio + "' AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n" +
                    "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n";
            sqlLineaTablaAPT = ", apps.xxfc_ap_ar_pag_scn_tmp apt\n";
            sqlLinea73o75CorSerNac = "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aps.vendor_id = '" + codigoProveedor + "' AND   gh.tipo_servicio = '" + tipoServicio + "' AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n";
            sqlLinea77CorSerNac = "  ";
        } else if (tipoServicio.equals("SERVICIOS NACIONALES")) {
            tipoServicio = "NACIONAL";
            sqlLinea2 = " SELECT * FROM (SELECT  aca.amount c_cantidad\n ";
            sqlLinea7 = ", DECODE(gh.tipo_servicio, '" + tipoServicio + "', MIN(gh.grupo_ejecucion), 'SIN GRUPO' ) c_gpo_ejecucion\n";
            sqlLinea39AntesDeUnion = "AND (   gh.tipo_servicio = '" + tipoServicio + "'\n" +
                    "                       OR aia.pay_group_lookup_code LIKE '%SERVHL' ) \n" +
                    "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aps.vendor_id = '" + codigoProveedor + "'\n" +
                    "                  GROUP BY  xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name, gh.tipo_servicio \n";
            sqlLineaTablaAPT = "   ";
            sqlLinea73o75CorSerNac = "AND   gh.tipo_servicio = '" + tipoServicio + "' \n" +
                    "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aps.vendor_id = '" + codigoProveedor + "'";
            sqlLinea77CorSerNac = "UNION\n" +
                    "              SELECT  DISTINCT  aca.amount c_cantidad\n" +
                    "                        , aia.invoice_date c_fecha_fact\n" +
                    "                        , aia.invoice_num c_factura\n" +
                    "                        , aca.check_date c_fecha_pago\n" +
                    "                        , aps.vendor_name c_nombre\n" +
                    "                        , 'SIN GRUPO'  c_gpo_ejecucion\n" +
                    "                  FROM apps.ap_invoice_payments_all aipa\n" +
                    "                     , apps.ap_invoices_all aia\n" +
                    "                     , apps.ap_checks_all aca\n" +
                    "                     , apps.ap_suppliers    aps\n" +
                    "                     , apps.ap_supplier_sites_all apss\n" +
                    "                  WHERE 1=1\n" +
                    "                  AND   aca.check_id               = aipa.check_id\n" +
                    "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                    "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                    "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                    "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                    "                  AND   apss.vendor_site_code      = '17ADM'\n" +
                    "                  AND   aps.enabled_flag           = 'Y'\n" +
                    "                  AND   aia.set_of_books_id        = 2022\n" +
                    "                  AND   NOT EXISTS (SELECT DISTINCT ge.grupo_ejecucion\n" +
                    "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all ge\n" +
                    "                                        , APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                    "                                        , xxfc_retek_item_servicios_v xri\n" +
                    "                                     WHERE gh.id_grupo = ge.id_grupo\n" +
                    "                                     AND   gh.atributo3 = 'SERVNACCOR'\n" +
                    "                                     AND   gh.atributo4 != 'Y'\n" +
                    "                                     AND (  (   ge.servicio = xri.item\n" +
                    "                                             AND   ge.servicio IS NOT NULL)\n" +
                    "                                          OR (     ge.rfc = apss.vendor_site_code_alt\n" +
                    "                                                AND  ge.rfc IS NOT NULL)\n" +
                    "                                          )\n" +
                    "                                     AND apss.vendor_site_code_alt  = TO_CHAR(xri.supplier)\n" +
                    "                                     )\n" +
                    "                  --CHG0053691_8 INICIO\n" +
                    "                  AND    NOT EXISTS (SELECT gh.id_grupo\n" +
                    "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                    "                                     WHERE gh.atributo3 = 'SERVNACCOR'\n" +
                    "                                     AND   gh.atributo4 != 'Y'\n" +
                    "                                     AND   gh.id_grupo = aia.global_attribute1\n" +
                    "                                     )  --CHG0053691_8 FIN \n" +
                    "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "AND aps.vendor_id = '" + codigoProveedor + "') \n";
        }
        return sqlLinea2 +
                ", aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre \n" +
                sqlLinea7 +
                "FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/ \n" +
                sqlLinea39AntesDeUnion +
                "UNION\n" +
                "              SELECT   aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre\n" +
                "                        , MIN(gh.grupo_ejecucion) c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss \n" +
                sqlLineaTablaAPT +
                ", apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "                  AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/ \n" +
                sqlLinea73o75CorSerNac +
                " GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name  \n" +
                sqlLinea77CorSerNac +
                " ORDER BY c_nombre, c_fecha_pago ";
    }

    /**
     * Metodo para obtener la consulta donde viene el codigo de grupo de ejecucion, el grupo de ejecucion y al tipo de servicio que corresponde.
     *
     * @param nombreDeGrupo
     * @param tipoDeServicio
     * @return query
     */
    public static String queryConsultaCodigoDeGrupoDeEjecucion(String nombreDeGrupo, String tipoDeServicio) {
        return "select grupo_Ejecucion,id_Grupo,tipo_servicio from apps.xxfc_ar_gpos_ejec_snc_all_h where grupo_Ejecucion ='" + nombreDeGrupo + "' AND tipo_servicio='" + tipoDeServicio + "'";
    }

    /**
     * Metodo para regresar la informacion de una consulta de una Bitacora de Pagos de Servicios NAcionales y Corresponsalías por medio de los valores Corresponsalias o Nacional, Codigo de grupo de ejecucion, fecha inicio, fecha fin y de forma Global.
     *
     * @param fechaInicio
     * @param fechaFin
     * @param codigoDeGrupoDeEjecucion
     * @return
     */
    public static String queryCorresponsaliasYServNacGrupoDeEjecucionGlobal(String fechaInicio, String fechaFin, String codigoDeGrupoDeEjecucion) {
        return "SELECT c_servicio, Nombre_Ser, Nombre_Prov, Fecha_Pago, SUM(Cantidad) Cantidad, Gpo_Ejec \n" +
                "FROM (SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END Nombre_Ser\n" +
                "                        , aca.amount Cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date Fecha_Pago\n" +
                "                        , aps.vendor_name Nombre_Prov\n" +
                "                        , MIN(gh.grupo_ejecucion) Gpo_Ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/ \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS'))" +
                " AND (aia.invoice_num LIKE 'SN_17ADM_%' )AND   gh.id_grupo = '" + codigoDeGrupoDeEjecucion + "'\n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                "UNION\n" +
                "              SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END Nombre_Ser\n" +
                "                        , aca.amount Cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date Fecha_Pago\n" +
                "                        , aps.vendor_name Nombre_Prov\n" +
                "                        , MIN(gh.grupo_ejecucion) Gpo_Ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_ap_ar_pag_scn_tmp apt\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "                  AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/ \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS'))" +
                " AND (aia.invoice_num LIKE 'SN_17ADM_%' )AND   gh.id_grupo = '" + codigoDeGrupoDeEjecucion + "'\n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                ")\n" +
                "GROUP BY c_servicio, Nombre_Ser, Nombre_Prov, Fecha_Pago, Gpo_Ejec ORDER BY Nombre_Prov, Fecha_Pago";
    }

    /**
     * Metodo para regresar la informacion de una consulta de una Bitacora de PAgos de Servicios NAcionales y Corresponsalías por medio de los valores Corresponsalias o Nacional, Codigo de grupo de ejecucion, fecha inicio, fecha fin y de forma Detallada.
     *
     * @param fechaInicio
     * @param fechaFin
     * @param codigoDeGrupoDeEjecucion
     * @return
     */
    public static String queryCorresponsaliasYServiciosNacGrupoDeEjecucionDetallado(String fechaInicio, String fechaFin, String codigoDeGrupoDeEjecucion) {
        return "SELECT   aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre\n" +
                "                        , MIN(gh.grupo_ejecucion) c_gpo_ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/ \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )" +
                " AND   gh.id_grupo = '" + codigoDeGrupoDeEjecucion + "'AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                "UNION\n" +
                "              SELECT aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre\n" +
                "                        , MIN(gh.grupo_ejecucion) c_gpo_ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_ap_ar_pag_scn_tmp apt\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "                  AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/ \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )" +
                " AND   gh.id_grupo = '" + codigoDeGrupoDeEjecucion + "'AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                "ORDER BY c_nombre, c_fecha_pago";
    }

    /**
     * Metodo para regresar la informacion de una consulta de una Bitacora de Pagos de Servicios Nacionales y Corresponsalías por medio de los valores Corresponsalias o Nacional, fecha inicio, fecha fin y de forma Global.
     *
     * @param fechaInicio
     * @param fechaFin
     * @param tipoServicio
     * @return query
     */
    public static String queryCorresponsaliasYServiciosNacSoloFechasGlobal(String fechaInicio, String fechaFin, String tipoServicio) {
        String sqlLinea3 = "";
        String sqlLinea13 = "";
        String sqlLinea45AntesDeUnion = "";
        String sqlLineaTablaAPT = "";
        String sqlLinea82o83CorSerNac = "";
        String sqlLinea86CorSerNac = "";
        if (tipoServicio.equals("CORRESPONSALIAS")) {
            sqlLinea3 = "FROM (SELECT  aia.global_attribute2 c_servicio\n";
            sqlLinea13 = ", MIN(gh.grupo_ejecucion) Gpo_Ejec\n";
            sqlLinea45AntesDeUnion = "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS'))" +
                    " AND (aia.invoice_num LIKE 'SN_17ADM_%' ) AND   gh.tipo_servicio = '" + tipoServicio + "' AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n" +
                    "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n";
            sqlLineaTablaAPT = ", apps.xxfc_ap_ar_pag_scn_tmp apt\n";
            sqlLinea82o83CorSerNac = "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS'))" +
                    " AND (aia.invoice_num LIKE 'SN_17ADM_%' ) AND   gh.tipo_servicio = '" + tipoServicio + "' AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n";
            sqlLinea86CorSerNac = "  ";
        } else if (tipoServicio.equals("SERVICIOS NACIONALES")) {
            tipoServicio = "NACIONAL";
            sqlLinea3 = " FROM (SELECT * FROM (SELECT  aia.global_attribute2 c_servicio\n ";
            sqlLinea13 = ", DECODE(gh.tipo_servicio, '" + tipoServicio + "', MIN(gh.grupo_ejecucion), 'SIN GRUPO' ) Gpo_Ejec\n";
            sqlLinea45AntesDeUnion = "AND (   gh.tipo_servicio = '" + tipoServicio + "'\n" +
                    "                       OR aia.pay_group_lookup_code LIKE '%SERVHL' ) \n" +
                    "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                    "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name, gh.tipo_servicio \n";
            sqlLineaTablaAPT = "   ";
            sqlLinea82o83CorSerNac = "AND   gh.tipo_servicio = '" + tipoServicio + "' \n" +
                    "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' ) \n";
            sqlLinea86CorSerNac = "UNION\n" +
                    "              SELECT  DISTINCT aia.global_attribute2 c_servicio\n" +
                    "                        , (SELECT DISTINCT xrs.item_desc\n" +
                    "                           FROM   xxfc_retek_item_servicios_v xrs\n" +
                    "                           WHERE  aia.global_attribute2 = xrs.item) Nombre_Ser\n" +
                    "                        , aca.amount Cantidad\n" +
                    "                        , aia.invoice_date c_fecha_fact\n" +
                    "                        , aia.invoice_num c_factura\n" +
                    "                        , aca.check_date Fech_Pago\n" +
                    "                        , aps.vendor_name Nombre_Prov\n" +
                    "                        , 'SIN GRUPO'  Gpo_Ejec\n" +
                    "                  FROM apps.ap_invoice_payments_all aipa\n" +
                    "                     , apps.ap_invoices_all aia\n" +
                    "                     , apps.ap_checks_all aca\n" +
                    "                     , apps.ap_suppliers    aps\n" +
                    "                     , apps.ap_supplier_sites_all apss\n" +
                    "                  WHERE 1=1\n" +
                    "                  AND   aca.check_id               = aipa.check_id\n" +
                    "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                    "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                    "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                    "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                    "                  AND   apss.vendor_site_code      = '17ADM'\n" +
                    "                  AND   aps.enabled_flag           = 'Y'\n" +
                    "                  AND   aia.set_of_books_id        = 2022\n" +
                    "                  AND   NOT EXISTS (SELECT DISTINCT ge.grupo_ejecucion\n" +
                    "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all ge\n" +
                    "                                        , APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                    "                                        , xxfc_retek_item_servicios_v xri\n" +
                    "                                     WHERE gh.id_grupo = ge.id_grupo\n" +
                    "                                     AND   gh.atributo3 = 'SERVNACCOR'\n" +
                    "                                     AND   gh.atributo4 != 'Y'\n" +
                    "                                     AND (  (   ge.servicio = xri.item\n" +
                    "                                             AND   ge.servicio IS NOT NULL)\n" +
                    "                                          OR (     ge.rfc = apss.vendor_site_code_alt\n" +
                    "                                                AND  ge.rfc IS NOT NULL)\n" +
                    "                                          )\n" +
                    "                                     AND apss.vendor_site_code_alt  = TO_CHAR(xri.supplier)\n" +
                    "                                     )\n" +
                    "                  --CHG0053691_8 INICIO\n" +
                    "                  AND    NOT EXISTS (SELECT gh.id_grupo\n" +
                    "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                    "                                     WHERE gh.atributo3 = 'SERVNACCOR'\n" +
                    "                                     AND   gh.atributo4 != 'Y'\n" +
                    "                                     AND   gh.id_grupo = aia.global_attribute1\n" +
                    "                                     )  --CHG0053691_8 FIN \n" +
                    "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' ))\n";
        }
        return "SELECT c_servicio, Nombre_Ser, Nombre_Prov, Fech_Pago, SUM(Cantidad) Cantidad, Gpo_Ejec \n" +
                sqlLinea3 +
                ", CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END Nombre_Ser\n" +
                "                        , aca.amount Cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date Fech_Pago\n" +
                "                        , aps.vendor_name Nombre_Prov\n" +
                sqlLinea13 +
                "FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/ \n" +
                sqlLinea45AntesDeUnion +
                "UNION\n" +
                "              SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END Nombre_Ser\n" +
                "                        , aca.amount Cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date Fech_Pago\n" +
                "                        , aps.vendor_name Nombre_Prov\n" +
                "                        , MIN(gh.grupo_ejecucion) Gpo_Ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss \n" +
                sqlLineaTablaAPT +
                ", apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "                  AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/ \n" +
                sqlLinea82o83CorSerNac +
                " GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                sqlLinea86CorSerNac +
                ")\n" +
                "GROUP BY c_servicio, Nombre_Ser, Nombre_Prov, Fech_Pago, Gpo_Ejec ORDER BY Nombre_Prov, Fech_Pago";
    }

    /**
     * Metodo para regresar la informacion de una consulta de una Bitacora de Pagos de Servicios Nacionales y Corresponsalías por medio de los valores Corresponsalias o Nacional, fecha inicio, fecha fin y de forma Detallada.
     *
     * @param fechaInicio
     * @param fechaFin
     * @param tipoDeServicio
     * @return query
     */
    public static String queryCorresponsaliasYServiciosNacSoloFechasDetallado(String fechaInicio, String fechaFin, String tipoDeServicio) {
        return "SELECT  aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre\n" +
                "                        , MIN(gh.grupo_ejecucion) c_gpo_ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/ \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )" +
                " AND   gh.tipo_servicio = '" + tipoDeServicio + "' AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                "UNION\n" +
                "              SELECT   aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre\n" +
                "                        , MIN(gh.grupo_ejecucion) c_gpo_ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_ap_ar_pag_scn_tmp apt\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "                  AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/ \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )" +
                " AND   gh.tipo_servicio = '" + tipoDeServicio + "' AND aia.pay_group_lookup_code NOT LIKE '%SERVHL' \n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                "ORDER BY c_nombre, c_fecha_pago";
    }

    /**
     * Metodo para regresar la primera parte de una consulta de una Bitacora de Pagos de Servicios Nacionales y Corresponsalías por medio de los valores  Nacional, fecha inicio, fecha fin y de forma Detallada.
     *
     * @param fechaInicio
     * @param fechaFin
     * @param numServicio
     * @param tipoServicio
     * @return query
     */
    public static String queryServiciosNacServicioDetallado1(String fechaInicio, String fechaFin, String numServicio, String tipoServicio) {
        if (tipoServicio.equals("SERVICIOS NACIONALES")) {
            tipoServicio = "NACIONAL";
        }
        return " SELECT * FROM (SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END c_nombre_serv\n" +
                "                        , aca.amount Cantidad\n" +
                "                        , aia.invoice_date Fecha_Fac\n" +
                "                        , aia.invoice_num Factura\n" +
                "                        , DECODE(gh.tipo_servicio, '" + tipoServicio + "', MIN(gh.grupo_ejecucion), 'SIN GRUPO' ) Gpo_ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/\n" +
                "                  AND (   gh.tipo_servicio = '" + tipoServicio + "'\n" +
                "                       OR aia.pay_group_lookup_code LIKE '%SERVHL' ) \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "AND aia.global_attribute2 = '" + numServicio + "'\n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, gh.tipo_servicio \n" +
                "UNION\n" +
                "              SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END c_nombre_serv\n" +
                "                        , aca.amount Cantidad\n" +
                "                        , aia.invoice_date Fecha_Fac\n" +
                "                        , aia.invoice_num Factura\n" +
                "                        , MIN(gh.grupo_ejecucion) Gpo_Ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND   TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "                  AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/\n" +
                "                  AND   gh.tipo_servicio = '" + tipoServicio + "' \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "AND aia.global_attribute2 = '" + numServicio + "'\n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num\n" +
                "UNION\n" +
                "              SELECT  DISTINCT aia.global_attribute2 c_servicio\n" +
                "                        , (SELECT DISTINCT xrs.item_desc\n" +
                "                           FROM   xxfc_retek_item_servicios_v xrs\n" +
                "                           WHERE  aia.global_attribute2 = xrs.item) c_nombre_serv\n" +
                "                        , aca.amount Cantidad\n" +
                "                        , aia.invoice_date Fecha_Fac\n" +
                "                        , aia.invoice_num Factura\n" +
                "                        , 'SIN GRUPO'  Gpo_Ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code      = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   NOT EXISTS (SELECT DISTINCT ge.grupo_ejecucion\n" +
                "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                                        , APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                                        , xxfc_retek_item_servicios_v xri\n" +
                "                                     WHERE gh.id_grupo = ge.id_grupo\n" +
                "                                     AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                                     AND   gh.atributo4 != 'Y'\n" +
                "                                     AND (  (   ge.servicio = xri.item\n" +
                "                                             AND   ge.servicio IS NOT NULL)\n" +
                "                                          OR (     ge.rfc = apss.vendor_site_code_alt\n" +
                "                                                AND  ge.rfc IS NOT NULL)\n" +
                "                                          )\n" +
                "                                     AND apss.vendor_site_code_alt  = TO_CHAR(xri.supplier)\n" +
                "                                     )\n" +
                "                  --CHG0053691_8 INICIO\n" +
                "                  AND    NOT EXISTS (SELECT gh.id_grupo\n" +
                "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                                     WHERE gh.atributo3 = 'SERVNACCOR'\n" +
                "                                     AND   gh.atributo4 != 'Y'\n" +
                "                                     AND   gh.id_grupo = aia.global_attribute1\n" +
                "                                     )  --CHG0053691_8 FIN \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "AND aia.global_attribute2 = '" + numServicio + "')  \n";
    }

    /**
     * Metodo para regresar la segunda parte de una consulta de una Bitacora de Pagos de Servicios Nacionales y Corresponsalias por medio de los valores Nacional, fecha inicio, fecha fin y de forma Detallada.
     * @param fechaInicio
     * @param fechaFin
     * @param numServicio
     * @param tipoServicio
     * @return query
     */
    public static String queryServiciosNacServicioDetallado2(String fechaInicio, String fechaFin, String numServicio, String tipoServicio) {
        if (tipoServicio.equals("SERVICIOS NACIONALES")) {
            tipoServicio = "NACIONAL";
        }
        return "SELECT * FROM (SELECT  aia.global_attribute2 c_servicio \n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                " , DECODE(gh.tipo_servicio, '" + tipoServicio + "', MIN(gh.grupo_ejecucion), 'SIN GRUPO' ) Grp_Ejec \n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/ \n" +
                " AND (   gh.tipo_servicio = '" + tipoServicio + "'\n" +
                "                       OR aia.pay_group_lookup_code LIKE '%SERVHL' ) \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "AND aia.global_attribute2 = '" + numServicio + "'\n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.check_date, aps.vendor_name, gh.tipo_servicio \n" +
                "UNION\n" +
                "              SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                "                        , MIN(gh.grupo_ejecucion) Grp_Ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "  , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                " AND TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/ \n" +
                "AND   gh.tipo_servicio = '" + tipoServicio + "' \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "AND aia.global_attribute2 = '" + numServicio + "' \n" +
                "GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aca.check_date, aps.vendor_name \n" +
                " UNION\n" +
                "              SELECT  DISTINCT aia.global_attribute2 c_servicio\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                "                        , 'SIN GRUPO'  Grp_Ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code      = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   NOT EXISTS (SELECT DISTINCT ge.grupo_ejecucion\n" +
                "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                                        , APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                                        , xxfc_retek_item_servicios_v xri\n" +
                "                                     WHERE gh.id_grupo = ge.id_grupo\n" +
                "                                     AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                                     AND   gh.atributo4 != 'Y'\n" +
                "                                     AND (  (   ge.servicio = xri.item\n" +
                "                                             AND   ge.servicio IS NOT NULL)\n" +
                "                                          OR (     ge.rfc = apss.vendor_site_code_alt\n" +
                "                                                AND  ge.rfc IS NOT NULL)\n" +
                "                                          )\n" +
                "                                     AND apss.vendor_site_code_alt  = TO_CHAR(xri.supplier)\n" +
                "                                     )\n" +
                "                  --CHG0053691_8 INICIO\n" +
                "                  AND    NOT EXISTS (SELECT gh.id_grupo\n" +
                "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                                     WHERE gh.atributo3 = 'SERVNACCOR'\n" +
                "                                     AND   gh.atributo4 != 'Y'\n" +
                "                                     AND   gh.id_grupo = aia.global_attribute1\n" +
                "                                     )  --CHG0053691_8 FIN \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('" + fechaFin + " 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "AND aia.global_attribute2 = '" + numServicio + "') \n" +
                " ORDER BY c_nombre_prov, c_fecha_pago";
    }

    /**
     * Metodo para regresar la primera parte de una consulta de una Bitacora de Pagos de Servicios Nacionales y Corresponsalias por medio de los valores Nacional, fecha inicio, fecha fin y de forma detallada.
     * @param fechaInicio
     * @param fechaFin
     * @param tipoServicio
     * @return query
     */
    public static String queryServiciosNacSoloFechasDetallado1(String fechaInicio, String fechaFin, String tipoServicio){
        if (tipoServicio.equals("SERVICIOS NACIONALES")) {
            tipoServicio = "NACIONAL";
        }
        return "SELECT * FROM (SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END c_nombre_serv\n" +
                "                        , aca.amount Cantidad\n" +
                "                        , aia.invoice_date Fecha_Fac\n" +
                "                        , aia.invoice_num Factura\n" +
                "                        , DECODE(gh.tipo_servicio, '"+tipoServicio+"', MIN(gh.grupo_ejecucion), 'SIN GRUPO' ) Gpo_ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/\n" +
                "                  AND (   gh.tipo_servicio = '"+tipoServicio+"'\n" +
                "                       OR aia.pay_group_lookup_code LIKE '%SERVHL' ) \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('"+fechaInicio+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('"+fechaFin+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name, gh.tipo_servicio \n" +
                "UNION\n" +
                "              SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END c_nombre_serv\n" +
                "                        , aca.amount Cantidad\n" +
                "                        , aia.invoice_date Fecha_Fac\n" +
                "                        , aia.invoice_num Factura\n" +
                "                        , MIN(gh.grupo_ejecucion) Gpo_Ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND   TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "                  AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/\n" +
                "                  AND   gh.tipo_servicio = '"+tipoServicio+"' \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('"+fechaInicio+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('"+fechaFin+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, gh.tipo_servicio\n" +
                "UNION\n" +
                "              SELECT  DISTINCT aia.global_attribute2 c_servicio\n" +
                "                        , (SELECT DISTINCT xrs.item_desc\n" +
                "                           FROM   xxfc_retek_item_servicios_v xrs\n" +
                "                           WHERE  aia.global_attribute2 = xrs.item) c_nombre_serv\n" +
                "                        , aca.amount Cantidad\n" +
                "                        , aia.invoice_date Fecha_Fac\n" +
                "                        , aia.invoice_num Factura\n" +
                "                        , 'SIN GRUPO'  Gpo_Ejec\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code      = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   NOT EXISTS (SELECT DISTINCT ge.grupo_ejecucion\n" +
                "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                                        , APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                                        , xxfc_retek_item_servicios_v xri\n" +
                "                                     WHERE gh.id_grupo = ge.id_grupo\n" +
                "                                     AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                                     AND   gh.atributo4 != 'Y'\n" +
                "                                     AND (  (   ge.servicio = xri.item\n" +
                "                                             AND   ge.servicio IS NOT NULL)\n" +
                "                                          OR (     ge.rfc = apss.vendor_site_code_alt\n" +
                "                                                AND  ge.rfc IS NOT NULL)\n" +
                "                                          )\n" +
                "                                     AND apss.vendor_site_code_alt  = TO_CHAR(xri.supplier)\n" +
                "                                     )\n" +
                "                  --CHG0053691_8 INICIO\n" +
                "                  AND    NOT EXISTS (SELECT gh.id_grupo\n" +
                "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                                     WHERE gh.atributo3 = 'SERVNACCOR'\n" +
                "                                     AND   gh.atributo4 != 'Y'\n" +
                "                                     AND   gh.id_grupo = aia.global_attribute1\n" +
                "                                     )  --CHG0053691_8 FIN \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('"+fechaInicio+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('"+fechaFin+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' ))";
    }

    /**
     * Metodo para regresar la segunda parte de una consulta de una Bitacora de Pagos de Servicios Nacionales y Corresponsalias por medio de los valores Nacional, fecha inicio, fecha fin y de forma detallada.
     * @param fechaInicio
     * @param fechaFin
     * @param tipoServicio
     * @return query
     */
    public static String queryServiciosNacSoloFechasDetallado2(String fechaInicio, String fechaFin, String tipoServicio){
        if (tipoServicio.equals("SERVICIOS NACIONALES")) {
            tipoServicio = "NACIONAL";
        }
        return "SELECT * FROM (SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                "                        , DECODE(gh.tipo_servicio, '"+tipoServicio+"', MIN(gh.grupo_ejecucion), 'SIN GRUPO' ) c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/\n" +
                "                  AND (   gh.tipo_servicio = '"+tipoServicio+"'\n" +
                "                       OR aia.pay_group_lookup_code LIKE '%SERVHL' ) \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('"+fechaInicio+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('"+fechaFin+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name, gh.tipo_servicio \n" +
                "UNION\n" +
                "              SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                "                        , MIN(gh.grupo_ejecucion) c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND   TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "                  AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/\n" +
                "                  AND   gh.tipo_servicio = '"+tipoServicio+"' \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('"+fechaInicio+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('"+fechaFin+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                "UNION\n" +
                "              SELECT  DISTINCT aia.global_attribute2 c_servicio\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                "                        , 'SIN GRUPO'  c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code      = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   NOT EXISTS (SELECT DISTINCT ge.grupo_ejecucion\n" +
                "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                                        , APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                                        , xxfc_retek_item_servicios_v xri\n" +
                "                                     WHERE gh.id_grupo = ge.id_grupo\n" +
                "                                     AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                                     AND   gh.atributo4 != 'Y'\n" +
                "                                     AND (  (   ge.servicio = xri.item\n" +
                "                                             AND   ge.servicio IS NOT NULL)\n" +
                "                                          OR (     ge.rfc = apss.vendor_site_code_alt\n" +
                "                                                AND  ge.rfc IS NOT NULL)\n" +
                "                                          )\n" +
                "                                     AND apss.vendor_site_code_alt  = TO_CHAR(xri.supplier)\n" +
                "                                     )\n" +
                "                  --CHG0053691_8 INICIO\n" +
                "                  AND    NOT EXISTS (SELECT gh.id_grupo\n" +
                "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                                     WHERE gh.atributo3 = 'SERVNACCOR'\n" +
                "                                     AND   gh.atributo4 != 'Y'\n" +
                "                                     AND   gh.id_grupo = aia.global_attribute1\n" +
                "                                     )  --CHG0053691_8 FIN \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('"+fechaInicio+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('"+fechaFin+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' ))  \n" +
                "ORDER BY c_nombre_prov, c_fecha_pago";
    }

    /**
     * Metodo para regresar la primera parte de una consulta de una Bitacora de Pagos de Servicios Nacionales y Corresponsalias por medio de los valores Nacional, grupo de ejecucion "SIN GRUPO", fecha inicio, fecha fin y de forma global.
     * @param fechaInicio
     * @param fechaFin
     * @param tipoServicio
     * @return query
     */
    public static String queryServiciosNacGpoEjecSinGrupoGlobal(String fechaInicio, String fechaFin, String tipoServicio){
        return "SELECT  c_nombre_prov, c_fecha_pago, SUM(c_cantidad) c_cantidad, c_gpo_ejecucion \n" +
                "FROM (SELECT * FROM (SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END c_nombre_serv\n" +
                "                        , aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                "                        , DECODE(gh.tipo_servicio, '"+tipoServicio+"', MIN(gh.grupo_ejecucion), 'SIN GRUPO' ) c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/\n" +
                "                  AND (   gh.tipo_servicio = '"+tipoServicio+"'\n" +
                "                       OR aia.pay_group_lookup_code LIKE '%SERVHL' ) \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('"+fechaInicio+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('"+fechaFin+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name, gh.tipo_servicio \n" +
                "UNION\n" +
                "              SELECT  aia.global_attribute2 c_servicio\n" +
                "                        , CASE\n" +
                "                           WHEN aia.global_attribute2 IS NOT NULL THEN\n" +
                "                              xrs.item_desc\n" +
                "                          END c_nombre_serv\n" +
                "                        , aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                "                        , MIN(gh.grupo_ejecucion) c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND   TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "                  AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/\n" +
                "                  AND   gh.tipo_servicio = '"+tipoServicio+"' \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('"+fechaInicio+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('"+fechaFin+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                "UNION\n" +
                "              SELECT  DISTINCT aia.global_attribute2 c_servicio\n" +
                "                        , (SELECT DISTINCT xrs.item_desc\n" +
                "                           FROM   xxfc_retek_item_servicios_v xrs\n" +
                "                           WHERE  aia.global_attribute2 = xrs.item) c_nombre_serv\n" +
                "                        , aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                "                        , 'SIN GRUPO'  c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code      = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   NOT EXISTS (SELECT DISTINCT ge.grupo_ejecucion\n" +
                "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                                        , APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                                        , xxfc_retek_item_servicios_v xri\n" +
                "                                     WHERE gh.id_grupo = ge.id_grupo\n" +
                "                                     AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                                     AND   gh.atributo4 != 'Y'\n" +
                "                                     AND (  (   ge.servicio = xri.item\n" +
                "                                             AND   ge.servicio IS NOT NULL)\n" +
                "                                          OR (     ge.rfc = apss.vendor_site_code_alt\n" +
                "                                                AND  ge.rfc IS NOT NULL)\n" +
                "                                          )\n" +
                "                                     AND apss.vendor_site_code_alt  = TO_CHAR(xri.supplier)\n" +
                "                                     )\n" +
                "                  --CHG0053691_8 INICIO\n" +
                "                  AND    NOT EXISTS (SELECT gh.id_grupo\n" +
                "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                                     WHERE gh.atributo3 = 'SERVNACCOR'\n" +
                "                                     AND   gh.atributo4 != 'Y'\n" +
                "                                     AND   gh.id_grupo = aia.global_attribute1\n" +
                "                                     )  --CHG0053691_8 FIN \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('"+fechaInicio+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('"+fechaFin+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )) WHERE c_gpo_ejecucion = 'SIN GRUPO' \n" +
                ")\n" +
                "GROUP BY c_servicio, c_nombre_serv, c_nombre_prov, c_fecha_pago, c_gpo_ejecucion ORDER BY c_nombre_prov, c_fecha_pago";
    }

    /**
     * Metodo para regresar la segunda parte de una consulta de una Bitacora de Pagos de Servicios Nacionales y Corresponsalias por medio de los valores Nacional, grupo de ejecucion "SIN GRUPO", fecha inicio, fecha fin y de forma global.
     * @param fechaInicio
     * @param fechaFin
     * @param tipoServicio
     * @return query
     */
    public static String queryServiciosNacGpoEjecSinGrupoDetallado(String fechaInicio, String fechaFin, String tipoServicio){
        return "SELECT * FROM (SELECT   aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                "                        , DECODE(gh.tipo_servicio, '"+tipoServicio+"', MIN(gh.grupo_ejecucion), 'SIN GRUPO' ) c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier(+))\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.id_grupo = ge.id_grupo\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND  (  (    ge.servicio = xrs.item\n" +
                "                          AND ge.servicio IS NOT NULL\n" +
                "                          )\n" +
                "                          OR\n" +
                "                          (   ge.rfc = apss.vendor_site_code_alt\n" +
                "                          AND   ge.rfc IS NOT NULL\n" +
                "                          )\n" +
                "                      )\n" +
                "                  AND NVL(aia.global_attribute1, -1) IN (-1, 9999) /*CHG0053691_7*/\n" +
                "                  AND (   gh.tipo_servicio = '"+tipoServicio+"'\n" +
                "                       OR aia.pay_group_lookup_code LIKE '%SERVHL' ) \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('"+fechaInicio+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('"+fechaFin+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name, gh.tipo_servicio \n" +
                "UNION\n" +
                "              SELECT   aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                "                        , MIN(gh.grupo_ejecucion) c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                     , apps.xxfc_retek_item_servicios_v xrs\n" +
                "                     , apps.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code_alt  = TO_CHAR(xrs.supplier)\n" +
                "                  AND   nvl(aia.global_attribute2, xrs.item)= xrs.item\n" +
                "                  AND   xrs.site                   = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                  AND   gh.atributo4 != 'Y'\n" +
                "                  AND   TO_CHAR(gh.id_grupo) = aia.global_attribute1\n" +
                "                  AND NVL(aia.global_attribute1, -1) NOT IN (-1, 9999) /*CHG0053691_7*/\n" +
                "                  AND   gh.tipo_servicio = '"+tipoServicio+"' \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('"+fechaInicio+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('"+fechaFin+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )\n" +
                "                  GROUP BY aia.global_attribute2, xrs.item_desc, aca.amount, aia.invoice_date, aia.invoice_num, aca.check_date, aps.vendor_name \n" +
                "UNION\n" +
                "              SELECT   aca.amount c_cantidad\n" +
                "                        , aia.invoice_date c_fecha_fact\n" +
                "                        , aia.invoice_num c_factura\n" +
                "                        , aca.check_date c_fecha_pago\n" +
                "                        , aps.vendor_name c_nombre_prov\n" +
                "                        , 'SIN GRUPO'  c_gpo_ejecucion\n" +
                "                  FROM apps.ap_invoice_payments_all aipa\n" +
                "                     , apps.ap_invoices_all aia\n" +
                "                     , apps.ap_checks_all aca\n" +
                "                     , apps.ap_suppliers    aps\n" +
                "                     , apps.ap_supplier_sites_all apss\n" +
                "                  WHERE 1=1\n" +
                "                  AND   aca.check_id               = aipa.check_id\n" +
                "                  AND   aipa.invoice_id            = aia.invoice_id\n" +
                "                  AND   aia.vendor_id              = aps.vendor_id\n" +
                "                  AND   aps.vendor_id              = apss.vendor_id\n" +
                "                  AND   apss.vendor_site_id        = aia.vendor_site_id\n" +
                "                  AND   apss.vendor_site_code      = '17ADM'\n" +
                "                  AND   aps.enabled_flag           = 'Y'\n" +
                "                  AND   aia.set_of_books_id        = 2022\n" +
                "                  AND   NOT EXISTS (SELECT DISTINCT ge.grupo_ejecucion\n" +
                "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all ge\n" +
                "                                        , APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                                        , xxfc_retek_item_servicios_v xri\n" +
                "                                     WHERE gh.id_grupo = ge.id_grupo\n" +
                "                                     AND   gh.atributo3 = 'SERVNACCOR'\n" +
                "                                     AND   gh.atributo4 != 'Y'\n" +
                "                                     AND (  (   ge.servicio = xri.item\n" +
                "                                             AND   ge.servicio IS NOT NULL)\n" +
                "                                          OR (     ge.rfc = apss.vendor_site_code_alt\n" +
                "                                                AND  ge.rfc IS NOT NULL)\n" +
                "                                          )\n" +
                "                                     AND apss.vendor_site_code_alt  = TO_CHAR(xri.supplier)\n" +
                "                                     )\n" +
                "                  --CHG0053691_8 INICIO\n" +
                "                  AND    NOT EXISTS (SELECT gh.id_grupo\n" +
                "                                     FROM  APPS.xxfc_ar_gpos_ejec_snc_all_h gh\n" +
                "                                     WHERE gh.atributo3 = 'SERVNACCOR'\n" +
                "                                     AND   gh.atributo4 != 'Y'\n" +
                "                                     AND   gh.id_grupo = aia.global_attribute1\n" +
                "                                     )  --CHG0053691_8 FIN \n" +
                "AND TRUNC(aca.check_date) BETWEEN TRUNC(TO_DATE('"+fechaInicio+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND TRUNC(TO_DATE('"+fechaFin+" 00:00:00','RRRR/MM/DD HH24:MI:SS')) AND (aia.invoice_num LIKE 'SN_17ADM_%' )) WHERE c_gpo_ejecucion = 'SIN GRUPO' \n" +
                "ORDER BY c_nombre_prov, c_fecha_pago";
    }


}
