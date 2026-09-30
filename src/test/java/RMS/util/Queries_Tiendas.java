package RMS.util;

import java.util.HashMap;

public class Queries_Tiendas {
	static HashMap<String, String> data;

	public static String tbl_deal_head = "select * from deal_head where status = 'A' and type = 'P'and close_date <= '%s' order by close_date desc";

	public static String tbl_deal_head2 = "select * from deal_head where status = 'C' and type = 'P'and close_date <= '%s' order by close_date desc";

	public static final String Tbl_Store = "select VAT_REGION from store where store = %s";

	public static final String Tbl_Store_Cierre = "select STORE_CLOSE_DATE, STOP_ORDER_DAYS,TRANSFER_ZONE From store where store = %s";

	public static final String loc_list_head = "select * from loc_list_head where LOC_LIST = %s";

	public static final String loc_list_detail = "select * from loc_list_detail where LOC_LIST = %s";

	public static final String loc_list_criteria = "SELECT * FROM loc_list_criteria where LOC_LIST = %s";

	public static final String price_zone_group_store = "SELECT * FROM price_zone_group_store where zone_group_id = %s";

	public static final String tbl_DEAL_HEAD = "SELECT * FROM DEAL_HEAD where DEAL_ID = %s";

	public static final String tbl_DEAL_DETAIL = "SELECT * FROM DEAL_DETAIL where DEAL_ID = %s";

	public static final String tbl_DEAL_ITEMLOC = "SELECT * FROM DEAL_ITEMLOC where DEAL_ID = %s";

	public static final String tbl_DEAL_THRESHOLD = "SELECT * FROM DEAL_THRESHOLD where DEAL_ID = %s";

	public static final String tbl_item_PackNo = "SELECT * FROM packitem where PACK_NO = %s";

	public static final String tbl_Item_Master = "SELECT * FROM item_master where item = %s";

	public static final String tbl_item_loc = "SELECT * FROM item_loc where item = %s";

	public static final String tbl_item_supplier = "SELECT * FROM item_loc where item = %s";

	public static final String tbl_item_supp_country = "SELECT * FROM item_supp_country where item = %s";

	public static final String tbl_item_supp_country_dim = "SELECT * FROM item_supp_country_dim where item = %s";
	
	public static final String tbl_pos_mods = "select * from pos_mods where TRAN_TYPE = 1 and item = %s";
	
//	public static final String tbl_DEAL_HEAD = "SELECT * FROM DEAL_DETAIL where DEAL_ID = %s";

	public static final String price_zone = "SELECT * FROM price_zone where zone_group_id = %s";

	// Consultas para promociones
	public static final String tbl_wmuser_supp_promhead = "SELECT * FROM  wmuser.PROMHEAD where PROMOTION = %s";

	public static final String tbl_wmuser_supp_promstore = "SELECT * FROM wmuser.PROMSTORE where PROMOTION = %s";

	public static final String tbl_promhead = "SELECT * FROM PROMHEAD where PROMOTION = %s";
	
	public static final String tbl_promstore = "SELECT * FROM PROMSTORE where PROMOTION = %s";
	
	public static final String tbl_prom_threshold_sku = "SELECT * FROM prom_threshold_sku where PROMOTION = %s";
	
	public static final String tbl_prom_threshold_head = "SELECT * FROM prom_threshold_head where PROMOTION = %s";
	
	public static final String tbl_prom_threshold_detail = "SELECT * FROM prom_threshold_detail where PROMOTION = %s";
	
	public static final String tbl_xxfc_xxfc_PROMO_PRMEXT_T  = " select * from xxfc.xxfc_promo_prmext_t  where promotion = %s";
	
	public static final String tbl_pos_prom_detail  = " select * from pos_prom_detail where promotion = %s";
	
	public static final String tbl_pos_mods_prom = "select * from pos_mods where promotion = %s";
	
	public static final String tbl_pos_outbound_docs = "select * from pos_outbound_docs where DOC_NAME = %s";
	
	public static final String tbl_wmuser_pos_itm_prm_head = "select * from wmuser.pos_itm_prm_head where location = %s";
	
	public static final String tbl_wmuser_fem_pos_prom_detail_stg = "select * from wmuser.fem_pos_prom_detail_stg where load_batch_id in (%s) "
			+ "and promotion = %s";
	
	public static final String tbl_wmuser_fem_pos_mods_stg = "select * from wmuser.fem_pos_mods_stg where "
			+ "load_batch_id in (%s) and"
			+ "promotion = %s";
	
	public static final String tbl_wmuser_fem_pos_detail_stg = "select * from wmuser.fem_pos_detail_stg where "
			+ "load_batch_id in (%s) and"
			+ "promotion = %s";
	
	public static final String tbl_promevent = "SELECT * FROM promevent where event = '%s'";
	
	
	//Consultas para promociones
	public static final String tbl_cost_susp_sup_head = "SELECT * FROM COST_SUSP_SUP_HEAD where COST_CHANGE = '%s'";
	
	public static final String tbl_cost_susp_sup_detail  = "SELECT * FROM COST_SUSP_SUP_DETAIL where COST_CHANGE = '%s'";
	
	//Consultas extras
	public static final String tbl_xxfc_configuracion_general = "select * from XXFC.XXFC_CONFIGURACION_GENERAL where parametro='ENCENDIDO_ORIGEN_GEOCOM'";
	
	public static final String tbl_xxfc_tiendas_migradas_geocom  = " select * from xxfc.xxfc_tiendas_migradas_geocom  where estatus=1 and ID_TIENDA = '%s'";
	
	//Consultas para precios
	public static final String price_susp_head = "SELECT * FROM price_susp_head where PRICE_CHANGE = '%s'";
	
	public static final String price_susp_detail  = "SELECT * FROM price_susp_detail where PRICE_CHANGE = '%s'";

}
