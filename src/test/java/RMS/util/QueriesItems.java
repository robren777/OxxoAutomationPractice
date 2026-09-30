// queries relacionadas al modulo de items

package RMS.util;

public class QueriesItems {

	public static final String ITEM_MASTER_ITEM = "SELECT * FROM ITEM_MASTER WHERE ITEM = %s";
	public static final String ITEM_LOC_ITEM = "SELECT * FROM ITEM_LOC WHERE ITEM = %s";
	public static final String ITEM_SUPPLIER_ITEM = "SELECT * FROM ITEM_SUPPLIER WHERE ITEM = %s";
	public static final String ITEM_SUPP_COUNTRY_ITEM = "SELECT * FROM ITEM_SUPP_COUNTRY WHERE ITEM = %s";
	public static final String ITEM_SUPP_COUNTRY_DIM_ITEM = "SELECT * FROM ITEM_SUPP_COUNTRY_DIM " + "WHERE ITEM = %s";

	public static final String POS_MODS_TRAN_1 = "SELECT * FROM POS_MODS WHERE ITEM = % AND TRAN_TYPE = 1";

	public static final String WM_POS_ITM_PRM_HEAD = "SELECT * FROM WMUSER.POS_ITM_PRM_HEAD "
			+ "WHERE LOCATION = %s ORDER BY LOAD_BATCH_ID DESC";

	public static final String WM_FEM_POS_MODS_STG = "SELECT * FROM WMUSER.FEM_POS_MODS_STG "
			+ "WHERE ITEM = %s AND LOAD_BATCH_ID = %s";

	public static final String POS_OUTBOUND_DOCS_ITM = "SELECT * FROM POSUSER.POS_OUTBOUND_DOCS "
			+ "WHERE DOC_NAME = %s";

	public static final String DAILY_PURGE_KEY_VALUE = "SELECT * FROM DAILY_PURGE " + "WHERE KEY_VALUE = %s";

}
