package RMS.util;

public class QueriesRegresion {
	
	public static final String POS_SAL = "SELECT * "
			+ "FROM pos_sal "
			+ "WHERE pid_id = '%s'";
	
	public static final String POS_RTV = "SELECT * "
			+ "FROM pos_rtv "
			+ "WHERE pid_id = '%s'";
	
	public static final String RTV_HEAD = "SELECT * "
			+ "FROM rtv_head "
			+ "WHERE rtv_order_no = '%s' "
			+ "and supplier = '%s' "
			+ "and store = '%s' "
			+ "and fem_remision = '%s'";
	
	public static final String RTV_DETAIL = "SELECT * "
			+ "FROM rtv_detail "
			+ "WHERE rtv_order_no = '%s' "
			+ "and item = '%s'";
	
	public static final String POS_SAL_DETL = "SELECT * "
			+ "FROM pos_sal_detl "
			+ "WHERE pid_id = '%s' "
			+ "and item = '%s'";
	
	public static final String POS_RTV_DETL = "SELECT * "
			+ "FROM pos_rtv_detl "
			+ "WHERE pid_id = '%s'";
	
	public static final String POS_SAL_DETL_2 = "SELECT * "
			+ "FROM pos_sal_detl "
			+ "WHERE pid_id = '%s' "
			+ "and item in ('%s', '%s')";
	
	public static final String POS_INBOUND_DOCS = "SELECT * "
			+ "FROM pos_inbound_docs "
			+ "WHERE pv_doc_name = '%s'";
	
	public static final String WMUSER_RTK_INBOUND_DOCS = "SELECT * "
			+ "FROM WMUSER.RTK_INBOUND_DOCS "
			+ "WHERE cr_tienda = '%s' "
			+ "and doc_type = '%s' "
			+ "and rtk_filename = '%s'";
	
	public static final String WMUSER_RTK_INBOUND_DOCS_2 = "SELECT * "
			+ "FROM WMUSER.RTK_INBOUND_DOCS "
			+ "WHERE cr_plaza = '%s' "
			+ "and cr_tienda = '%s' "
			+ "and rtk_filename = '%s'";
	
	public static final String TRAN_DATA = "SELECT * "
			+ "FROM tran_data "
			+ "WHERE store = '%s' "
			+ "and item = '%s' "
			+ "and tran_date >= '%s'";
	
	public static final String TRAN_DATA_2 = "SELECT * "
			+ "FROM tran_data "
			+ "WHERE tran_code = '%s' "
			+ "and store = '%s' "
			+ "and item = '%s' "
			+ "and tran_date >= '%s'";
	
	public static final String TRAN_DATA_3 = "SELECT * "
			+ "FROM tran_data "
			+ "WHERE tran_code = '%s' "
			+ "and tran_date >= '%s' "
			+ "and store = '%s' ";
	
	public static final String FEM_FIF_STG = "SELECT * "
			+ "FROM fem_fif_stg "
			+ "WHERE cr_plaza = '%s' "
			+ "and store = '%s' "
			+ "and item = '%s' "
			+ "and tran_date >= '%s' "
			+ "and reference_3 is '%s'";
	
	public static final String FEM_FIF_STG_2 = "SELECT * "
			+ "FROM fem_fif_stg "
			+ "WHERE tran_code = '%s' "
			+ "and cr_plaza = '%s' "
			+ "and store = '%s' "
			+ "and item = '%s' "
			+ "and tran_date >= '%s' "
			+ "and reference_3 is '%s'";
	
	public static final String FEM_FIF_STG_3 = "SELECT * "
			+ "FROM fem_fif_stg "
			+ "WHERE cr_plaza = '%s' "
			+ "and tran_date >= '%s' "
			+ "and tran_code = '%s' "
			+ "and reference_3 is '%s'";
	
	public static final String FEM_FIF_STG_packitem = "SELECT * "
			+ "FROM fem_fif_stg "
			+ "WHERE cr_plaza = '%s' "
			+ "and store = '%s' "
			+ "and tran_date >= '%s' "
			+ "and reference_3 is '%s' "
			+ "and item in (select item from packitem where pack_no = '%s')";
	
	public static final String FEM_FIF_STG_reference = "SELECT * "
			+ "FROM fem_fif_stg "
			+ "WHERE cr_plaza = '%s' "
			+ "and store = '%s' "
			+ "and item = '%s' "
			+ "and tran_date >= '%s'";
	
	public static final String FEM_FIF_STG_reference_2 = "SELECT * "
			+ "FROM fem_fif_stg "
			+ "WHERE tran_code = '%s' "
			+ "and cr_plaza = '%s' "
			+ "and store = '%s' "
			+ "and item = '%s' "
			+ "and tran_date >= '%s'";
	
	public static final String FEM_FIF_STG_reference_3 = "SELECT * "
			+ "FROM fem_fif_stg "
			+ "WHERE cr_plaza = '%s' "
			+ "and tran_date >= '%s' "
			+ "and store = '%s' "
			+ "and tran_code = '%s'";
	
	public static final String GL_INTERFACE = "SELECT * "
			+ "FROM gl_interface "
			+ "WHERE reference6 in ('%s','%s')";
	
	public static final String GL_INTERFACE_2 = "SELECT * "
			+ "FROM gl_interface "
			+ "WHERE reference6 = '%s')";
	
	public static final String POS_ADJ = "SELECT * "
			+ "FROM pos_adj "
			+ "WHERE pid_id = '%s'";
	
	public static final String POS_ADJ_DETL = "SELECT * "
			+ "FROM pos_adj_detl "
			+ "WHERE pid_id = '%s'";
	
	public static final String INV_ADJ = "SELECT * "
			+ "FROM inv_adj "
			+ "WHERE reason = '%s' "
			+ "and location = '%s' "
			+ "order by adj_date desc";
	
	public static final String ORHEAD = "SELECT * "
			+ "FROM orhead "
			+ "WHERE order_no in ('%s','%s')";
	
	public static final String ORHEAD2 = "SELECT * "
			+ "FROM orhead "
			+ "WHERE order_no = '%s'";
	
	public static final String ORDLOC = "SELECT * "
			+ "FROM ordloc "
			+ "WHERE order_no = '%s' "
			+ "and item = '%s'";
	
	public static final String ORDLOC_DISCOUNT = "SELECT * "
			+ "FROM ordloc_discount "
			+ "WHERE order_no = '%s' "
			+ "and item = '%s'";
	
	public static final String ORDSKU = "SELECT * "
			+ "FROM ordsku "
			+ "WHERE order_no = '%s' "
			+ "and item = '%s'";
	
	public static final String FEM_RECHEAD_TEMP = "SELECT * "
			+ "FROM fem_rechead_temp "
			+ "WHERE order_no in ('%s','%s')";
	
	public static final String FEM_RECDETAIL_TEMP = "SELECT * "
			+ "FROM fem_recdetail_temp "
			+ "WHERE order_no in ('%s','%s')";
	
	public static final String TSFHEAD = "SELECT tsf_no, from_loc_type, from_loc, to_loc_type, to_loc, status, create_date, ext_ref_no "
			+ "FROM tsfhead "
			+ "WHERE TSF_NO = '%s'";
	
	public static final String TSFDETAIL = "SELECT * "
			+ "FROM tsfdetail "
			+ "WHERE TSF_NO = '%s'";
	
	public static final String STOCK_ORDER = "SELECT * "
			+ "FROM STOCK_ORDER "
			+ "WHERE DISTRO_NBR = '%s'";
	
	public static final String STOCK_ALLOCATION = "SELECT * "
			+ "FROM STOCK_ALLOCATION "
			+ "WHERE DISTRO_NBR = '%s' "
			+ "and item_id = '%s'";
	
	public static final String rdm100_xxfc_base_tipo_distro = "SELECT * "
			+ "FROM rdm100.xxfc_base_tipo_distro "
			+ "WHERE DISTRO_NBR = '%s'";
	
	public static final String rdm100_xxfc_base_programacion = "SELECT * "
			+ "FROM rdm100.xxfc_base_programacion "
			+ "WHERE DISTRO_NBR = '%s'";
	
	public static final String rdm100_xxfc_base_programacion_zona = "SELECT * "
			+ "FROM rdm100.xxfc_base_programacion_zona "
			+ "WHERE DISTRO_NBR = '%s'";
	
	public static final String SHIPSKU = "SELECT * "
			+ "FROM SHIPSKU "
			+ "WHERE DISTRO_NO = '%s'";
	
	public static final String SHIPMENT = "SELECT * "
			+ "FROM SHIPMENT "
			+ "WHERE SHIPMENT = '%s'";
	
	
	public static final String COST_SUSP_SUP_HEAD = "SELECT * "
			+ "FROM COST_SUSP_SUP_HEAD "
			+ "where COST_CHANGE = '%s'";
	
	public static final String COST_SUSP_SUP_DETAIL  = "SELECT * "
			+ "FROM COST_SUSP_SUP_DETAIL "
			+ "where COST_CHANGE = '%s'";
	
	public static final String COST_SUSP_SUP_DETAIL_LOC  = "SELECT * "
			+ "FROM COST_SUSP_SUP_DETAIL_LOC "
			+ "where COST_CHANGE = '%s' "
			+ "and rownum <= 1";
	
	public static final String ITEM_SUSPP_COUNTRY = "SELECT * "
			+ "FROM item_supp_country "
			+ "where item = '%s' "
			+ "and supplier = '%s'";
	
	public static final String ITEM_SUSPP_COUNTRY_LOC = "SELECT * "
			+ "FROM item_supp_country_loc "
			+ "where item = '%s' "
			+ "and supplier = '%s' "
			+ "and loc = '%s'";
	
	public static final String POS_MODS = "SELECT * "
			+ "FROM pos_mods "
			+ "where item = '%s' "
			+ "and store = '%s'";
	
	public static final String WMUSER_POS_ITM_PRM_HEAD = "SELECT * "
			+ "FROM WMUSER.POS_ITM_PRM_HEAD  "
			+ "where location = '%s' "
			+ "order by load_batch_id"
			+ "desc";
	
	public static final String FEM_RTVHEAD_TEMP = "SELECT * "
			+ "FROM fem_rtvhead_temp "
			+ "WHERE rtv_order_no = '%s'";
	
	public static final String FEM_RTVDETAIL_TEMP = "SELECT * "
			+ "FROM fem_rtvdetail_temp "
			+ "WHERE rtv_order_no = '%s'";
	
	public static final String ITEM_LOC = "SELECT * FROM ITEM_LOC IL, ITEM_SUPP_COUNTRY_LOC ISCL WHERE \r\n"
			+ "IL.ITEM = ISCL.ITEM AND\r\n"
			+ "IL.LOC = ISCL.LOC AND \r\n"
			+ "IL.PRIMARY_SUPP = ISCL.SUPPLIER AND\r\n"
			+ "IL.STATUS = 'A' AND IL.LOC = '%s' AND IL.ITEM = '%s'";
	
	public static final String ITEM_LOC_TRAITS = "SELECT * FROM ITEM_LOC_TRAITS "
			+ "WHERE ITEM = '%s' "
			+ "AND LOC = '%s'";
	
	public static final String vat_item = "SELECT ITEM, VAT_REGION, ACTIVE_DATE, VAT_CODE FROM vat_item "
			+ "WHERE ITEM = '%s'";
	
	public static final String uda_item_ff = "SELECT * FROM uda_item_ff "
			+ "WHERE uda_id = '9545' "
			+ "AND ITEM = '%s'";
	
	public static final String xxfc_supply_line_detail = "SELECT * FROM xxfc_supply_line_detail "
			+ "WHERE ITEM = '%s'";
	
	
	
	
	
	
	
	
	
}
