package RMS.util.PageObject;

import java.sql.SQLException;

public class PromoEjecutor extends Promociones {
	Promociones p = new Promociones(sk, u, cb, r, pathimg, ListImage, ImgPatter, patterns, Fol, status, dbRms, st,
			data);
	static String status;

	public PromoEjecutor(Promociones p) throws SQLException {
		super(sk, u, cb, r, pathimg, ListImage, ImgPatter, patterns, Fol, status, dbRms, st, data);
	}

	/**
	 * @author 72843
	 * @return Regresa un string el estatus de la promocion en base de datos.
	 * @comments Ejecuta la generaci�n y configuraci�n de una promocion simple de
	 *           M�xico.
	 */
	public String EjecutarPromoSimple() {

		try {
//			if(data.get("name").contains("baja")) {
//				p.Ejecutar_Job_Update_Promo("IT_setup.sh");
//				p.Ejecutar_Job_Update_Promo("dtesys");
//			}
//			p.Login_En_RMS();
//			p.Crear_Promocion();
//			if(data.get("name").contains("ItemList")) {
//				p.Configurar_Promocion_Simple_Item_List();
//			}else {
//				p.configurePromSI();
//			}			
//			p.Submit_Promocion();
//			p.Ejecutar_Job_Update_Promo("rr_setup.sh_2");
//			p.Validar_DB();
//			p.Aprovar_Promocion();
//			p.Ejecutar_Job_Update_Promo("rr_setup.sh_2");
//			status = p.Validar_DB();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return status;
	}

//	public String EjecutarPromo() {
//		
//		String KeyValue = data.get("name");
//		
//		if (KeyValue.contains("ItemList")) {
//			
//			status = EjecutarPromoSimple();
//						
//		}else if (KeyValue.contains("SingleItem")) {
//			
//			status = EjecutarPromoSimple();
//			
//		}
//		
//		
//		return status;
//		
//	}

}
