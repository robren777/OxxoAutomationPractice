// queries relacionadas al modulo de recetas

package RMS.util;

public class QueriesRecetas {

	public static final String PACKITEM_PACK_ITEM_QUANTITY = "SELECT PACK_NO, ITEM, PACK_QTY, LAST_UPDATE_DATETIME "
			+ "FROM PACKITEM " + "WHERE PACK_NO = %s AND ITEM = %s AND PACK_QTY = '%s'";

	public static final String ITEM_MASTER_ITEM = "SELECT ITEM, ITEM_NUMBER_TYPE, ITEM_DESC " + "FROM ITEM_MASTER "
			+ "WHERE ITEM = %s";

	public static final String ITEM_LOC_ITEM_STORE_STATUS_A = "SELECT ITEM, LOC, LOCAL_SHORT_DESC " + "FROM ITEM_LOC "
			+ "WHERE ITEM = %s AND STATUS = 'A' AND LOC = %s";

}
