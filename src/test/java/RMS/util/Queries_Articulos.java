package RMS.util;

import java.util.HashMap;

public class Queries_Articulos {
	static HashMap<String, String> data;

	public static String tbl_deal_head = "select * from deal_head where status = 'A' and type = 'P'and close_date <= sysdate order by close_date desc";

	public static String tbl_deal_head2 = "select * from deal_head where status = 'A' and type = 'P'and close_date <= sysdate order by close_date desc";

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

	public static final String tbl_pos_mods = "select * from pos_mods where TRAN_TYPE = 90 and item = %s";

//	public static final String tbl_DEAL_HEAD = "SELECT * FROM DEAL_DETAIL where DEAL_ID = %s";

	public static final String price_zone = "SELECT * FROM price_zone where zone_group_id = %s";

	// Consultas para promociones
	public static final String tbl_wmuser_supp_promhead = "SELECT * FROM  wmuser.PROMHEAD where PROMOTION = %s";

	public static final String tbl_wmuser_supp_promstore = "SELECT * FROM wmuser.PROMSTORE where PROMOTION = %s";

	public static final String tbl_promhead = "SELECT * FROM PROMHEAD where PROMOTION = %s";

	// Consultas para promociones
	public static final String tbl_cost_susp_sup_head = "SELECT * FROM COST_SUSP_SUP_HEAD where COST_CHANGE = %s";

	public static final String tbl_cost_susp_sup_detail = "SELECT * FROM COST_SUSP_SUP_DETAIL where COST_CHANGE = %s";
	
	//Articulos
	public static final String ITEM_LOC  = "SELECT * FROM ITEM_LOC WHERE STATUS = 'I' AND LOC = '1610' AND ITEM =  %s";
	
	public static final String POS_MODS  = "SELECT * FROM POS_MODS WHERE ITEM = %s AND LOC = '1610'";
	
	public static final String XXFC_SUPPLY_LINE_DETAIL  = "SELECT * FROM XXFC_SUPPLY_LINE_DETAIL WHERE ITEM = %s";
	
	public static final String VAT_ITEM  = "SELECT * FROM VAT_ITEM WHERE ITEM = %s";
	
	public static final String POS_MODS_TRAN_TYPE  = "SELECT * FROM POS_MODS WHERE ITEM = %s AND LOC = '1610' AND TRAN_TYPE IN (%s)";

}
