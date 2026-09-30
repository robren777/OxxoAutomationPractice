package RMS.util;

import RMS.util.PageObject.Promociones;

public class Queries_Items {

	public static final String item_master = "Select * from Item_Master where ITEM =" + Promociones.cb + ";";

	public static final String ITEM_LOC = "Select * from ITEM_LOC where ITEM =" + Promociones.cb + ";";

	public static final String ITEM_SUPPLIER = "Select * from ITEM_SUPPLIER where ITEM =" + Promociones.cb + ";";

	public static final String ITEM_SUPPLIER_COUNTRY = "Select * from ITEM_SUPPLIER_COUNTRY where ITEM ="
			+ Promociones.cb + ";";

	public static final String ITEM_SUPPLIER_COUNTRY_DIM = "Select * from ITEM_SUPPLIER_COUNTRY_DIM where ITEM ="
			+ Promociones.cb + ";";

	public static final String POS_MODS = "Select * from POS_MODS where ITEM =" + Promociones.cb + ";";

	public static final String POS_ITM_PRM_HEAD = "Select * from WMUSER.POS_ITM_PRM_HEAD where ITEM =" + Promociones.cb
			+ ";";

}
