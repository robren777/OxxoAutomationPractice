package EBS.util.locales.SQL;

public class QuerysOracleNacionales {

    /**
     * Metodo para regresar la primera parte del query de una consulta de creacion item para grupo de ejecucion para corresponsalias o servicios nacionales en la seccion de Nacionales
     *
     * @param grupoEjecucionCorresponsalias
     * @param Servicio
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaGrupoEjecucionCorresponsaliasyNacionales1(String grupoEjecucionCorresponsalias, String Servicio) {
        return "select id_grupo, grupo_ejecucion, tipo_servicio, servicio, rfc \n" +
                "from xxfc.xxfc_ap_gpos_snc_hist_all\n" +
                "where GRUPO_EJECUCION= '" + grupoEjecucionCorresponsalias + "'\n" +
                "And movimiento_descripcion = 'Crea Grupo Ejecucion'\n" +
                "And tipo_Servicio='" + Servicio + "'";
    }

    /**
     * Metodo para regresar la segunda parte del query de una consulta de creacion item para grupo de ejecucion para corresponsalias o servicios nacionales en la seccion de Nacionales
     *
     * @param grupoEjecucionCorresponsalias
     * @param Servicio
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaGrupoEjecucionCorresponsaliasyNacionales2(String grupoEjecucionCorresponsalias, String Servicio) {
        return "select hora_ejec, atributo1, movimiento_codigo, Movimiento_descripcion\n" +
                "from xxfc.xxfc_ap_gpos_snc_hist_all\n" +
                "where GRUPO_EJECUCION= '" + grupoEjecucionCorresponsalias + "'\n" +
                "And movimiento_descripcion = 'Crea Grupo Ejecucion'\n" +
                "And tipo_Servicio='" + Servicio + "'";
    }

    /**
     * Metodo para regresar la primera parte del query de una consulta de creacion item para grupo de corrresponsalias y servicios Nacionales en la seccion de Nacionales
     *
     * @param grupoEjecucion
     * @param tipoServicio
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaAsignarGrupoCorresponsaliasyServicioNacionales1(String grupoEjecucion, String tipoServicio) {
        return "select ID_HIST_GRUPO, ID_GRUPO, ID_GRUPO_ITEM, SERVICIO, TIPO_SERVICIO\n" +
                "FROM apps.XXFC_AP_GPOS_SNC_HIST_ALL\n" +
                "where tipo_servicio='" + tipoServicio + "'\n" +
                "AND GRUPO_EJECUCION='" + grupoEjecucion + "'\n" +
                "AND MOVIMIENTO_DESCRIPCION = 'Crea Item Grupo Ejecucion'";
    }

    /**
     * Metodo para regresar la segunda parte del query de una consulta de creacion item para grupo de corrresponsalias y servicios Nacionales en la seccion de Nacionales en la  pagina EBS
     *
     * @param grupoEjecucion
     * @param tipoServicio
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaAsignarGrupoCorresponsaliasyServicioNacionales2(String grupoEjecucion, String tipoServicio) {
        return "select GRUPO_EJECUCION, HORA_EJEC, ATRIBUTO1, MOVIMIENTO_DESCRIPCION MOV_Descripcion\n" +
                "FROM apps.XXFC_AP_GPOS_SNC_HIST_ALL\n" +
                "where tipo_servicio='" + tipoServicio + "'\n" +
                "AND GRUPO_EJECUCION='" + grupoEjecucion + "'\n" +
                "AND MOVIMIENTO_DESCRIPCION = 'Crea Item Grupo Ejecucion'";
    }

    /**
     * Metodo para regresar la consulta de las actualizaciones de hora de un item de proveedor para grupo de corresponsalias y servicios nacionales en la seccion de Nacionales en la  pagina EBS
     *
     * @param grupoEjecucion
     * @param tipoServicio
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultarNuevaHoraCorresponsaliasYServiciosNacionalesProveedor(String grupoEjecucion, String tipoServicio) {
        return "Select GRUPO_EJECUCION, RFC PROVEEDOR, HORA_EJEC, Movimiento_Descripcion MOV_DESCR, LAST_UPDATE_DATE Ult_Actualizacion\n " +
                " FROM apps.XXFC_AP_GPOS_SNC_HIST_ALL\n" +
                " where tipo_servicio = '" + tipoServicio + "'\n" +
                " AND GRUPO_EJECUCION = '" + grupoEjecucion + "'\n" +
                " AND MOVIMIENTO_CODIGO='CAMBIO'\n" +
                " ORDER BY ID_HIST_GRUPO DESC";
    }

    /**
     * Metodo para regresar la consulta de las actualizaciones de hora de un item de proveedor para grupo de corresponsalias y servicios nacionales en la seccion de Nacionales en la  pagina EBS
     *
     * @param grupoEjecucion
     * @param tipoServicio
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultarNuevaHoraCorresponsaliasYServiciosNacionalesServicio(String grupoEjecucion, String tipoServicio) {
        return "Select GRUPO_EJECUCION, SERVICIO, HORA_EJEC, Movimiento_Descripcion MOV_DESCR, LAST_UPDATE_DATE Ult_Actualizacion\n " +
                " FROM apps.XXFC_AP_GPOS_SNC_HIST_ALL\n" +
                " where tipo_servicio = '" + tipoServicio + "'\n" +
                " AND GRUPO_EJECUCION = '" + grupoEjecucion + "'\n" +
                " AND MOVIMIENTO_CODIGO='CAMBIO'\n" +
                " ORDER BY ID_HIST_GRUPO DESC";
    }

    /**
     * Metodo para regresar la consulta de un grupo de ejecucion o excepcion  de corresponsalias o servicios nacionales que se elimino en la parte de Nacionales en la pagina de EBS
     *
     * @param grupoEjecucion
     * @param tipoServicio
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaEliminacionGrupoCorresponsaliasYSerNacionales(String grupoEjecucion, String tipoServicio) {
        return "select  ID_HIST_GRUPO, TIPO_SERVICIO , GRUPO_EJECUCION , MOVIMIENTO_DESCRIPCION MOV_DESCRIP\n" +
                " FROM apps.XXFC_AP_GPOS_SNC_HIST_ALL\n" +
                " where tipo_servicio='" + tipoServicio + "'\n" +
                " AND GRUPO_EJECUCION='" + grupoEjecucion + "'\n" +
                " AND MOVIMIENTO_CODIGO = 'BAJA'";
    }

    /**
     * Metodo para regresar la consulta de un grupo de ejecucion o excepcion que se elimino el item de corresponsalias o servicios nacionales que se elimino en la parte de Nacionales en la pagina de EBS
     *
     * @param grupoEjecucion
     * @param tipoServicio
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaEliminacionItemCorresponsaliasOSerNacionales(String grupoEjecucion, String tipoServicio) {
        return "select  ID_HIST_GRUPO, TIPO_SERVICIO , GRUPO_EJECUCION , MOVIMIENTO_DESCRIPCION MOV_DESCRIP\n" +
                " FROM apps.XXFC_AP_GPOS_SNC_HIST_ALL\n" +
                " where tipo_servicio='" + tipoServicio + "'\n" +
                " AND GRUPO_EJECUCION='" + grupoEjecucion + "'\n" +
                " AND MOVIMIENTO_CODIGO = 'BAJA'\n" +
                " AND MOVIMIENTO_DESCRIPCION = 'Elimina Item Grupo Ejecucion'";
    }

    /**
     * Metodo para regresar la primera parte de una consulta de un informe de Historial de grupos de ejecucion por tipo de servicio y fechas
     *
     * @param tipoServicio
     * @param fechaInicio
     * @param fechaFin
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaHistoricoTipoServicioYFechas1(String tipoServicio, String fechaInicio, String fechaFin) {
        return "SELECT xshst.grupo_ejecucion gpo,--grupo,\n" +
                "                        DECODE(xshst.tipo_servicio,'NACIONAL','SERVICIOS NACIONALES',xshst.tipo_servicio) tpos,--tipo_servicio,\n" +
                "                        DECODE(xshst.rfc,NULL,'SERVICIO','PROVEEDOR') cls,--clasificacion,\n" +
                "                        DECODE(xshst.rfc,NULL,xshst.servicio,xshst.rfc) item,--serv_prov,\n" +
                "                        xshst.atributo1 descr\n " +
                "                 FROM   apps.xxfc_ap_gpos_snc_hist_all xshst,\n" +
                "                        apps.fnd_user fndu,\n" +
                "                        apps.per_all_people_f perapf\n" +
                "                 WHERE  xshst.last_updated_by  = fndu.user_id\n" +
                "                 AND    fndu.employee_id       = perapf.person_id(+)\n" +
                "                 AND    (xshst.servicio        IS NOT NULL\n" +
                "                         OR xshst.rfc          IS NOT NULL)\n" +
                "                 AND    xshst.tipo_servicio    = NVL('" + tipoServicio + "',xshst.tipo_servicio)\n" +
                "                 AND    xshst.grupo_ejecucion  = NVL('',xshst.grupo_ejecucion)\n" +
                "                 AND    TRUNC(xshst.last_update_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                                                      AND     TRUNC(TO_DATE('" + fechaFin + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                 AND EXISTS (SELECT xshstsnc.id_grupo\n" +
                "                             FROM   apps.xxfc_ap_gpos_snc_hist_all xshstsnc\n" +
                "                             WHERE  xshstsnc.atributo3 = 'SERVNACCOR'\n" +
                "                             AND    xshstsnc.id_grupo  = xshst.id_grupo) ORDER BY xshst.last_update_date DESC";
    }

    /**
     * Metodo para regresar la segunda parte de una consulta de un informe de Historial de grupos de ejecucion por tipo de servicio y fechas
     *
     * @param tipoServicio
     * @param fechaInicio
     * @param fechaFin
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaHistoricoTipoServicioYFechas2(String tipoServicio, String fechaInicio, String fechaFin) {
        return "SELECT --desc,\n" +
                "                        --xshst.creation_date fhcr,--fecha_alta,\n" +
                "                        (select creation_date\n" +
                "                        from apps.xxfc_ap_gpos_snc_hist_all\n" +
                "                        where id_grupo = xshst.id_grupo\n" +
                "                        and id_grupo_item =xshst.id_grupo_item\n" +
                "                        and tipo_servicio = xshst.tipo_servicio\n" +
                "                        and movimiento_codigo = 'ALTA') fhcr,--fecha_alta,\n" +
                "                        xshst.hora_ejec hre,--horario_ejec,\n" +
                "                        xshst.last_update_date fhac,--fecha_act,\n" +
                "                        xshst.movimiento_codigo tpac,--tipo_act,\n" +
                "                        fndu.user_name||' - '||full_name usr--usuario\n" +
                "                 FROM   apps.xxfc_ap_gpos_snc_hist_all xshst,\n" +
                "                        apps.fnd_user fndu,\n" +
                "                        apps.per_all_people_f perapf\n" +
                "                 WHERE  xshst.last_updated_by  = fndu.user_id\n" +
                "                 AND    fndu.employee_id       = perapf.person_id(+)\n" +
                "                 AND    (xshst.servicio        IS NOT NULL\n" +
                "                         OR xshst.rfc          IS NOT NULL)\n" +
                "                 AND    xshst.tipo_servicio    = NVL('" + tipoServicio + "',xshst.tipo_servicio)\n" +
                "                 AND    xshst.grupo_ejecucion  = NVL('',xshst.grupo_ejecucion)\n" +
                "                 AND    TRUNC(xshst.last_update_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                                                      AND     TRUNC(TO_DATE('" + fechaFin + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                 AND EXISTS (SELECT xshstsnc.id_grupo\n" +
                "                             FROM   apps.xxfc_ap_gpos_snc_hist_all xshstsnc\n" +
                "                             WHERE  xshstsnc.atributo3 = 'SERVNACCOR'\n" +
                "                             AND    xshstsnc.id_grupo  = xshst.id_grupo) ORDER BY xshst.last_update_date DESC";
    }

    /**
     * Metodo para regresar la primera parte de una consulta de un informe de Historial de grupos de ejecucion por grupo de ejecucion y fechas
     *
     * @param grupoEjecucion
     * @param fechaInicio
     * @param fechaFin
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaHistoricoGruposDeEjecucionYFechas1(String grupoEjecucion, String fechaInicio, String fechaFin) {
        return "SELECT xshst.grupo_ejecucion gpo,--grupo,\n" +
                "                        DECODE(xshst.tipo_servicio,'NACIONAL','SERVICIOS NACIONALES',xshst.tipo_servicio) tpos,--tipo_servicio,\n" +
                "                        DECODE(xshst.rfc,NULL,'SERVICIO','PROVEEDOR') cls,--clasificacion,\n" +
                "                        DECODE(xshst.rfc,NULL,xshst.servicio,xshst.rfc) item,--serv_prov,\n" +
                "                        xshst.atributo1 descr\n" +
                "                 FROM   apps.xxfc_ap_gpos_snc_hist_all xshst,\n" +
                "                        apps.fnd_user fndu,\n" +
                "                        apps.per_all_people_f perapf\n" +
                "                 WHERE  xshst.last_updated_by  = fndu.user_id\n" +
                "                 AND    fndu.employee_id       = perapf.person_id(+)\n" +
                "                 AND    (xshst.servicio        IS NOT NULL\n" +
                "                         OR xshst.rfc          IS NOT NULL)\n" +
                "                 AND    xshst.tipo_servicio    = NVL('',xshst.tipo_servicio)\n" +
                "                 AND    xshst.grupo_ejecucion  = NVL('" + grupoEjecucion + "',xshst.grupo_ejecucion)\n" +
                "                 AND    TRUNC(xshst.last_update_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                                                      AND     TRUNC(TO_DATE('" + fechaFin + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                 AND EXISTS (SELECT xshstsnc.id_grupo\n" +
                "                             FROM   apps.xxfc_ap_gpos_snc_hist_all xshstsnc\n" +
                "                             WHERE  xshstsnc.atributo3 = 'SERVNACCOR'\n" +
                "                             AND    xshstsnc.id_grupo  = xshst.id_grupo) ORDER BY xshst.last_update_date DESC";
    }

    /**
     * Metodo para regresar la segunda parte de una consulta de un informe de Historial de grupos de ejecucion por grupo de ejecucion y fechas
     *
     * @param grupoEjecucion
     * @param fechaInicio
     * @param fechaFin
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaHistoricoGruposDeEjecucionYFechas2(String grupoEjecucion, String fechaInicio, String fechaFin) {
        return "SELECT --desc,\n" +
                "                        --xshst.creation_date fhcr,--fecha_alta,\n" +
                "                        (select creation_date\n" +
                "                        from apps.xxfc_ap_gpos_snc_hist_all\n" +
                "                        where id_grupo = xshst.id_grupo\n" +
                "                        and id_grupo_item =xshst.id_grupo_item\n" +
                "                        and tipo_servicio = xshst.tipo_servicio\n" +
                "                        and movimiento_codigo = 'ALTA') fhcr,--fecha_alta,\n" +
                "                        xshst.hora_ejec hre,--horario_ejec,\n" +
                "                        xshst.last_update_date fhac,--fecha_act,\n" +
                "                        xshst.movimiento_codigo tpac,--tipo_act,\n" +
                "                        fndu.user_name||' - '||full_name usr--usuario\n" +
                "                 FROM   apps.xxfc_ap_gpos_snc_hist_all xshst,\n" +
                "                        apps.fnd_user fndu,\n" +
                "                        apps.per_all_people_f perapf\n" +
                "                 WHERE  xshst.last_updated_by  = fndu.user_id\n" +
                "                 AND    fndu.employee_id       = perapf.person_id(+)\n" +
                "                 AND    (xshst.servicio        IS NOT NULL\n" +
                "                         OR xshst.rfc          IS NOT NULL)\n" +
                "                 AND    xshst.tipo_servicio    = NVL('',xshst.tipo_servicio)\n" +
                "                 AND    xshst.grupo_ejecucion  = NVL('" + grupoEjecucion + "',xshst.grupo_ejecucion)\n" +
                "                 AND    TRUNC(xshst.last_update_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                                                      AND     TRUNC(TO_DATE('" + fechaFin + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                 AND EXISTS (SELECT xshstsnc.id_grupo\n" +
                "                             FROM   apps.xxfc_ap_gpos_snc_hist_all xshstsnc\n" +
                "                             WHERE  xshstsnc.atributo3 = 'SERVNACCOR'\n" +
                "                             AND    xshstsnc.id_grupo  = xshst.id_grupo) ORDER BY xshst.last_update_date DESC";
    }

    /**
     * Metodo para regresar la primera parte de una consulta de un informe de Historial de grupos de ejecucion por solo fechas
     *
     * @param fechaInicio
     * @param fechaFin
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaHistoricoSoloFechas1(String fechaInicio, String fechaFin) {
        return "SELECT xshst.grupo_ejecucion gpo,--grupo,\n" +
                "                        DECODE(xshst.tipo_servicio,'NACIONAL','SERVICIOS NACIONALES',xshst.tipo_servicio) tpos,--tipo_servicio,\n" +
                "                        DECODE(xshst.rfc,NULL,'SERVICIO','PROVEEDOR') cls,--clasificacion,\n" +
                "                        DECODE(xshst.rfc,NULL,xshst.servicio,xshst.rfc) item,--serv_prov,\n" +
                "                        xshst.atributo1 descr\n" +
                "                 FROM   apps.xxfc_ap_gpos_snc_hist_all xshst,\n" +
                "                        apps.fnd_user fndu,\n" +
                "                        apps.per_all_people_f perapf\n" +
                "                 WHERE  xshst.last_updated_by  = fndu.user_id\n" +
                "                 AND    fndu.employee_id       = perapf.person_id(+)\n" +
                "                 AND    (xshst.servicio        IS NOT NULL\n" +
                "                         OR xshst.rfc          IS NOT NULL)\n" +
                "                 AND    xshst.tipo_servicio    = NVL('',xshst.tipo_servicio)\n" +
                "                 AND    xshst.grupo_ejecucion  = NVL('',xshst.grupo_ejecucion)\n" +
                "                 AND    TRUNC(xshst.last_update_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                                                      AND     TRUNC(TO_DATE('" + fechaFin + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                 AND EXISTS (SELECT xshstsnc.id_grupo\n" +
                "                             FROM   apps.xxfc_ap_gpos_snc_hist_all xshstsnc\n" +
                "                             WHERE  xshstsnc.atributo3 = 'SERVNACCOR'\n" +
                "                             AND    xshstsnc.id_grupo  = xshst.id_grupo) ORDER BY xshst.last_update_date DESC";
    }

    /**
     * Metodo para regresar la segunda parte de una consulta de un informe de Historial de grupos de ejecucion por solo fechas
     *
     * @param fechaInicio
     * @param fechaFin
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaHistoricoSoloFechas2(String fechaInicio, String fechaFin) {
        return "SELECT --desc,\n" +
                "                        --xshst.creation_date fhcr,--fecha_alta,\n" +
                "                        (select creation_date\n" +
                "                        from apps.xxfc_ap_gpos_snc_hist_all\n" +
                "                        where id_grupo = xshst.id_grupo\n" +
                "                        and id_grupo_item =xshst.id_grupo_item\n" +
                "                        and tipo_servicio = xshst.tipo_servicio\n" +
                "                        and movimiento_codigo = 'ALTA') fhcr,--fecha_alta,\n" +
                "                        xshst.hora_ejec hre,--horario_ejec,\n" +
                "                        xshst.last_update_date fhac,--fecha_act,\n" +
                "                        xshst.movimiento_codigo tpac,--tipo_act,\n" +
                "                        fndu.user_name||' - '||full_name usr--usuario\n" +
                "                 FROM   apps.xxfc_ap_gpos_snc_hist_all xshst,\n" +
                "                        apps.fnd_user fndu,\n" +
                "                        apps.per_all_people_f perapf\n" +
                "                 WHERE  xshst.last_updated_by  = fndu.user_id\n" +
                "                 AND    fndu.employee_id       = perapf.person_id(+)\n" +
                "                 AND    (xshst.servicio        IS NOT NULL\n" +
                "                         OR xshst.rfc          IS NOT NULL)\n" +
                "                 AND    xshst.tipo_servicio    = NVL('',xshst.tipo_servicio)\n" +
                "                 AND    xshst.grupo_ejecucion  = NVL('',xshst.grupo_ejecucion)\n" +
                "                 AND    TRUNC(xshst.last_update_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                                                      AND     TRUNC(TO_DATE('" + fechaFin + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                 AND EXISTS (SELECT xshstsnc.id_grupo\n" +
                "                             FROM   apps.xxfc_ap_gpos_snc_hist_all xshstsnc\n" +
                "                             WHERE  xshstsnc.atributo3 = 'SERVNACCOR'\n" +
                "                             AND    xshstsnc.id_grupo  = xshst.id_grupo) ORDER BY xshst.last_update_date DESC";
    }

    /**
     * Metodo para regresar la primera parte de una consulta de un informe de Historial de grupos de ejecucion por tipo de servicio, grupo de ejecucion y fechas
     *
     * @param tipoServicio
     * @param grupoEjecucion
     * @param fechaInicio
     * @param fechaFin
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaHistoricoTipoSerGrpEjeYFechas1(String tipoServicio, String grupoEjecucion, String fechaInicio, String fechaFin) {
        return "SELECT xshst.grupo_ejecucion gpo,--grupo,\n" +
                "                        DECODE(xshst.tipo_servicio,'NACIONAL','SERVICIOS NACIONALES',xshst.tipo_servicio) tpos,--tipo_servicio,\n" +
                "                        DECODE(xshst.rfc,NULL,'SERVICIO','PROVEEDOR') cls,--clasificacion,\n" +
                "                        DECODE(xshst.rfc,NULL,xshst.servicio,xshst.rfc) item,--serv_prov,\n" +
                "                        xshst.atributo1 descr\n" +
                "                 FROM   apps.xxfc_ap_gpos_snc_hist_all xshst,\n" +
                "                        apps.fnd_user fndu,\n" +
                "                        apps.per_all_people_f perapf\n" +
                "                 WHERE  xshst.last_updated_by  = fndu.user_id\n" +
                "                 AND    fndu.employee_id       = perapf.person_id(+)\n" +
                "                 AND    (xshst.servicio        IS NOT NULL\n" +
                "                         OR xshst.rfc          IS NOT NULL)\n" +
                "                 AND    xshst.tipo_servicio    = NVL('" + tipoServicio + "',xshst.tipo_servicio)\n" +
                "                 AND    xshst.grupo_ejecucion  = NVL('" + grupoEjecucion + "',xshst.grupo_ejecucion)\n" +
                "                 AND    TRUNC(xshst.last_update_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                                                      AND     TRUNC(TO_DATE('" + fechaFin + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                 AND EXISTS (SELECT xshstsnc.id_grupo\n" +
                "                             FROM   apps.xxfc_ap_gpos_snc_hist_all xshstsnc\n" +
                "                             WHERE  xshstsnc.atributo3 = 'SERVNACCOR'\n" +
                "                             AND    xshstsnc.id_grupo  = xshst.id_grupo) ORDER BY xshst.last_update_date DESC";
    }

    /**
     * Metodo para regresar la segunda parte de una consulta de un informe de Historial de grupos de ejecucion por tipo de servicio, grupo de ejecucion y fechas
     *
     * @param tipoServicio
     * @param grupoEjecucion
     * @param fechaInicio
     * @param fechaFin
     * @return query
     * @author David Quiroz Rodriguez
     */
    public static String queryConsultaHistoricoTipoSerGrpEjeYFechas2(String tipoServicio, String grupoEjecucion, String fechaInicio, String fechaFin) {
        return "SELECT --desc,\n" +
                "                        --xshst.creation_date fhcr,--fecha_alta,\n" +
                "                        (select creation_date\n" +
                "                        from apps.xxfc_ap_gpos_snc_hist_all\n" +
                "                        where id_grupo = xshst.id_grupo\n" +
                "                        and id_grupo_item =xshst.id_grupo_item\n" +
                "                        and tipo_servicio = xshst.tipo_servicio\n" +
                "                        and movimiento_codigo = 'ALTA') fhcr,--fecha_alta,\n" +
                "                        xshst.hora_ejec hre,--horario_ejec,\n" +
                "                        xshst.last_update_date fhac,--fecha_act,\n" +
                "                        xshst.movimiento_codigo tpac,--tipo_act,\n" +
                "                        fndu.user_name||' - '||full_name usr--usuario\n" +
                "                 FROM   apps.xxfc_ap_gpos_snc_hist_all xshst,\n" +
                "                        apps.fnd_user fndu,\n" +
                "                        apps.per_all_people_f perapf\n" +
                "                 WHERE  xshst.last_updated_by  = fndu.user_id\n" +
                "                 AND    fndu.employee_id       = perapf.person_id(+)\n" +
                "                 AND    (xshst.servicio        IS NOT NULL\n" +
                "                         OR xshst.rfc          IS NOT NULL)\n" +
                "                 AND    xshst.tipo_servicio    = NVL('" + tipoServicio + "',xshst.tipo_servicio)\n" +
                "                 AND    xshst.grupo_ejecucion  = NVL('" + grupoEjecucion + "',xshst.grupo_ejecucion)\n" +
                "                 AND    TRUNC(xshst.last_update_date) BETWEEN TRUNC(TO_DATE('" + fechaInicio + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                                                      AND     TRUNC(TO_DATE('" + fechaFin + "','DD/MM/RRRR HH24:MI:SS'))\n" +
                "                 AND EXISTS (SELECT xshstsnc.id_grupo\n" +
                "                             FROM   apps.xxfc_ap_gpos_snc_hist_all xshstsnc\n" +
                "                             WHERE  xshstsnc.atributo3 = 'SERVNACCOR'\n" +
                "                             AND    xshstsnc.id_grupo  = xshst.id_grupo) ORDER BY xshst.last_update_date DESC";
    }

    /**
     * Metodo para regresar la primera parte de una consulta de un informe de grupos de ejecucion por los valores ingresados (tipo servicio, grupo ejecucion)
     * @param tipoServicio
     * @param grupoEjecucion
     * @return query
     * @Author David Quiroz Rodriguez
     */
    public static String queryConsultaInformesDeEjecucion1(String tipoServicio, String grupoEjecucion) {
        if(tipoServicio.equals("SERVICIOS NACIONALES")){
            tipoServicio="NACIONAL";
        }
        String tipoServicioFin;
        String grupoEjecucionFin;
        String queryTipoServicio;
        String queryGrupoEjecucion;
        if (tipoServicio == " " || tipoServicio == null || tipoServicio == "") {
            tipoServicioFin = null;
            queryTipoServicio="    --AND  xge.tipo_servicio = " + tipoServicioFin + "\n";
        } else {
            tipoServicioFin = " '" + tipoServicio + "'";
            queryTipoServicio="    AND  xge.tipo_servicio = " + tipoServicioFin + "\n";
        }
        if (grupoEjecucion.equals(" ") || grupoEjecucion == null || grupoEjecucion == "") {
            grupoEjecucionFin = null;
            queryGrupoEjecucion="    --AND  xge.grupo_ejecucion = " + grupoEjecucionFin +"\n";
        } else {
            grupoEjecucionFin = " '" + grupoEjecucion + "'";
            queryGrupoEjecucion="    AND  xge.grupo_ejecucion = " + grupoEjecucionFin +"\n";

        }
        System.out.println(tipoServicioFin);
        System.out.println(grupoEjecucionFin);

        return "SELECT xge.GRUPO_EJECUCION   \n" +
                "        ,DECODE(xge.tipo_servicio,'NACIONAL','SERVICIOS NACIONALES', xge.tipo_servicio) AS TIPO_SERVICIO\n" +
                "        ,'PROVEEDOR'AS TIPO_REGISTRO\n" +
                "        ,xge.rfc  AS ITEM\n" +
                "        ,' ' AS SUBSERVICIOS\n" +
                "   FROM xxfc.xxfc_ar_gpos_ejec_snc_all xge \n" +
                "       ,xxfc.xxfc_ar_gpos_ejec_snc_all_h xgh\n" +
                "   WHERE xge.id_grupo = xgh.id_grupo\n" +
                "   AND xgh.tipo_servicio IN ('NACIONAL','CORRESPONSALIAS')\n" +
                "   AND xgh.atributo3 = 'SERVNACCOR' \n" +
                "   /*Se sustituiran las siguientes lineas de codigo\n" +
                "   --AND  xge.tipo_servicio = NVL(:PV_TIPO_SERVICIO,xge.tipo_servicio)\n" +
                "   --AND  xge.grupo_ejecucion = NVL(:PV_GRUPO_EJECUCION, xge.grupo_ejecucion)\n" +
                "    por las siguientes lineas para generar la query directa*/\n" +
                queryTipoServicio+
                queryGrupoEjecucion+
                "   AND xge.rfc IS NOT NULL\n" +
                "   \n" +
                "   UNION ALL\n" +
                "   SELECT xge.GRUPO_EJECUCION\n" +
                "         ,DECODE(xge.tipo_servicio,'NACIONAL','SERVICIOS NACIONALES', xge.tipo_servicio) AS TIPO_SERVICIO\n" +
                "         ,'PROVEEDOR' AS TIPO_REGISTRO\n" +
                "         ,xge.rfc AS ITEM\n" +
                "         ,ris.item AS SUBSERVICIOS\n" +
                "   FROM   xxfc.xxfc_ar_gpos_ejec_snc_all xge\n" +
                "         ,xxfc_retek_item_servicios_v ris\n" +
                "         ,xxfc.xxfc_ar_gpos_ejec_snc_all_h xgh\n" +
                "   WHERE ris.nacional_local = 'Nacional'\n" +
                "   AND xgh.tipo_servicio IN ('NACIONAL','CORRESPONSALIAS')\n" +
                "   AND xgh.atributo3 = 'SERVNACCOR'\n" +
                "   AND  (xge.rfc = to_char(ris.supplier) OR xge.servicio = ris.item)\n" +
                "   AND   ris.site = '17ADM'\n" +
                "   AND   xge.id_grupo = xgh.id_grupo\n" +
                "   /*Se sustituiran las siguientes lineas de codigo\n" +
                "   --AND  xge.tipo_servicio = NVL(:PV_TIPO_SERVICIO,xge.tipo_servicio)\n" +
                "   --AND  xge.grupo_ejecucion = NVL(:PV_GRUPO_EJECUCION, xge.grupo_ejecucion)\n" +
                "    por las siguientes lineas para generar la query directa*/\n" +
                queryTipoServicio+
                queryGrupoEjecucion+
                "\n" +
                "   AND   xge.rfc IS NOT NULL\n" +
                "  \n" +
                "   UNION ALL\n" +
                "   SELECT xge.GRUPO_EJECUCION\n" +
                "         ,DECODE(xge.tipo_servicio,'NACIONAL','SERVICIOS NACIONALES', xge.tipo_servicio) AS TIPO_SERVICIO\n" +
                "         ,'SERVICIO' AS TIPO_REGISTRO\n" +
                "         ,xge.servicio AS ITEM\n" +
                "         ,NULL AS SUBSERVICIOS\n" +
                "   FROM  xxfc.xxfc_ar_gpos_ejec_snc_all xge\n" +
                "        ,xxfc_retek_item_servicios_v ris\n" +
                "        ,xxfc.xxfc_ar_gpos_ejec_snc_all_h xgh\n" +
                "   WHERE ris.nacional_local = 'Nacional'\n" +
                "   AND xgh.tipo_servicio IN ('NACIONAL','CORRESPONSALIAS')\n" +
                "   AND xgh.atributo3 = 'SERVNACCOR'\n" +
                "   AND  (xge.rfc = to_char(ris.supplier) OR xge.servicio = ris.item)\n" +
                "   AND  ris.site = '17ADM'\n" +
                "   AND  xge.id_grupo = xgh.id_grupo\n" +
                "   /*Se sustituiran las siguientes lineas de codigo\n" +
                "   --AND  xge.tipo_servicio = NVL(:PV_TIPO_SERVICIO,xge.tipo_servicio)\n" +
                "   --AND  xge.grupo_ejecucion = NVL(:PV_GRUPO_EJECUCION, xge.grupo_ejecucion)\n" +
                "    por las siguientes lineas para generar la query directa*/\n" +
                queryTipoServicio +
                queryGrupoEjecucion+
                "   AND  xge.servicio IS NOT NULL\n" +
                " \n" +
                "   ORDER BY GRUPO_EJECUCION, TIPO_SERVICIO, TIPO_REGISTRO, ITEM, SUBSERVICIOS";
    }

    /**
     * Metodo para regresar la segunda parte de una consulta de informe de grupos de ejecucion por los valores ingresados (tipo servicio, grupo ejecucion)
     * @param tipoServicio
     * @param grupoEjecucion
     * @return query
     * @Author David Quiroz Rodriguez
     */
    public static String queryConsultaInformesDeEjecucion2(String tipoServicio, String grupoEjecucion) {
        if(tipoServicio.equals("SERVICIOS NACIONALES")){
            tipoServicio="NACIONAL";
        }
        String tipoServicioFin;
        String grupoEjecucionFin;
        String queryTipoServicio;
        String queryGrupoEjecucion;
        if (tipoServicio == " " || tipoServicio == null || tipoServicio == "") {
            tipoServicioFin = null;
            queryTipoServicio="    --AND  xge.tipo_servicio = " + tipoServicioFin + "\n";
        } else {
            tipoServicioFin = " '" + tipoServicio + "'";
            queryTipoServicio="    AND  xge.tipo_servicio = " + tipoServicioFin + "\n";
        }
        if (grupoEjecucion.equals(" ") || grupoEjecucion == null || grupoEjecucion == "") {
            grupoEjecucionFin = null;
            queryGrupoEjecucion="    --AND  xge.grupo_ejecucion = " + grupoEjecucionFin +"\n";
        } else {
            grupoEjecucionFin = " '" + grupoEjecucion + "'";
            queryGrupoEjecucion="    AND  xge.grupo_ejecucion = " + grupoEjecucionFin +"\n";

        }
        System.out.println(tipoServicioFin);
        System.out.println(grupoEjecucionFin);

        return " SELECT xge.GRUPO_EJECUCION   \n" +
                "        ,' ' AS SUBSERVICIOS\n" +
                "        ,xge.atributo1 as DESCRIPCION\n" +
                "   ,TO_CHAR(xge.creation_date,'DD/MM/YYYY HH24:MM:SS') as FECHA_ALTA\n" +
                "   ,xgh.hora_ejec as HORA_EJECUCION\n" +
                "   FROM xxfc.xxfc_ar_gpos_ejec_snc_all xge \n" +
                "       ,xxfc.xxfc_ar_gpos_ejec_snc_all_h xgh\n" +
                "   WHERE xge.id_grupo = xgh.id_grupo\n" +
                "   AND xgh.tipo_servicio IN ('NACIONAL','CORRESPONSALIAS')\n" +
                "   AND xgh.atributo3 = 'SERVNACCOR' \n" +
                "   /*Se sustituiran las siguientes lineas de codigo\n" +
                "   --AND  xge.tipo_servicio = NVL(:PV_TIPO_SERVICIO,xge.tipo_servicio)\n" +
                "   --AND  xge.grupo_ejecucion = NVL(:PV_GRUPO_EJECUCION, xge.grupo_ejecucion)\n" +
                "    por las siguientes lineas para generar la query directa*/\n" +
                queryTipoServicio +
                queryGrupoEjecucion+
                "   AND xge.rfc IS NOT NULL\n" +
                "   \n" +
                "   UNION ALL\n" +
                "   SELECT xge.GRUPO_EJECUCION\n" +
                "         ,ris.item AS SUBSERVICIOS\n" +
                "         ,ris.item_desc AS DESCRIPCION\n" +
                "         ,TO_CHAR(xge.creation_date,'DD/MM/YYYY HH24:MM:SS') as FECHA_ALTA\n" +
                "         ,xgh.hora_ejec as HORA_EJECUCION\n" +
                "   FROM   xxfc.xxfc_ar_gpos_ejec_snc_all xge\n" +
                "         ,xxfc_retek_item_servicios_v ris\n" +
                "         ,xxfc.xxfc_ar_gpos_ejec_snc_all_h xgh\n" +
                "   WHERE ris.nacional_local = 'Nacional'\n" +
                "   AND xgh.tipo_servicio IN ('NACIONAL','CORRESPONSALIAS')\n" +
                "   AND xgh.atributo3 = 'SERVNACCOR'\n" +
                "   AND  (xge.rfc = to_char(ris.supplier) OR xge.servicio = ris.item)\n" +
                "   AND   ris.site = '17ADM'\n" +
                "   AND   xge.id_grupo = xgh.id_grupo\n" +
                "   /*Se sustituiran las siguientes lineas de codigo\n" +
                "   --AND  xge.tipo_servicio = NVL(:PV_TIPO_SERVICIO,xge.tipo_servicio)\n" +
                "   --AND  xge.grupo_ejecucion = NVL(:PV_GRUPO_EJECUCION, xge.grupo_ejecucion)\n" +
                "    por las siguientes lineas para generar la query directa*/\n" +
                queryTipoServicio +
                queryGrupoEjecucion+
                "   AND   xge.rfc IS NOT NULL\n" +
                "  \n" +
                "   UNION ALL\n" +
                "   SELECT xge.GRUPO_EJECUCION\n" +
                "         ,NULL AS SUBSERVICIOS\n" +
                "         ,xge.atributo1 AS DESCRIPCION\n" +
                "         ,TO_CHAR(xge.creation_date,'DD/MM/YYYY HH24:MM:SS') as FECHA_ALTA\n" +
                "         ,xgh.hora_ejec as HORA_EJECUCION\n" +
                "   FROM  xxfc.xxfc_ar_gpos_ejec_snc_all xge\n" +
                "        ,xxfc_retek_item_servicios_v ris\n" +
                "        ,xxfc.xxfc_ar_gpos_ejec_snc_all_h xgh\n" +
                "   WHERE ris.nacional_local = 'Nacional'\n" +
                "   AND xgh.tipo_servicio IN ('NACIONAL','CORRESPONSALIAS')\n" +
                "   AND xgh.atributo3 = 'SERVNACCOR'\n" +
                "   AND  (xge.rfc = to_char(ris.supplier) OR xge.servicio = ris.item)\n" +
                "   AND  ris.site = '17ADM'\n" +
                "   AND  xge.id_grupo = xgh.id_grupo\n" +
                "   /*Se sustituiran las siguientes lineas de codigo\n" +
                "   --AND  xge.tipo_servicio = NVL(:PV_TIPO_SERVICIO,xge.tipo_servicio)\n" +
                "   --AND  xge.grupo_ejecucion = NVL(:PV_GRUPO_EJECUCION, xge.grupo_ejecucion)\n" +
                "    por las siguientes lineas para generar la query directa*/\n" +
                queryTipoServicio +
                queryGrupoEjecucion+
                "   AND  xge.servicio IS NOT NULL\n" +
                " \n" +
                "   ORDER BY GRUPO_EJECUCION, SUBSERVICIOS\n";
    }


}
