package RMS.util;

import java.sql.SQLException;
import java.util.List;

import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class BuscadorPromo {

	private SQLUtil db;

	public BuscadorPromo(SQLUtil db) {
		this.db = db;
	}

//	public String seleccionarPromo(String status) throws SQLException {
//		String query = String.format(Queries_promociones.BUSCAR_PROMO_ESTATUS, status.toUpperCase());
//		System.out.println(query);
//
//		String promotion = null;
//		
//		//List<String[]> list = 
//		SQLResult executeQuery = this.db.executeQuery(query);//getAllData();
//		promotion = executeQuery.getData(0, "promotion");
//	}
//		/*
//=======
//
//		List<String[]> list = this.db.executeQuery(query).getAllData();
//		SQLResult executeQuery = this.db.executeQuery(query);
//		promotion = executeQuery.getData(0, "promotion");
//
//>>>>>>> a15c3691eb0f782af71f41925b561dfe8768dca6
//		for (String[] item : list) {
//			boolean esCompleja = false;
//
//			for (int i = 0; i < item.length; i++) {
//<<<<<<< HEAD
//				esCompleja = this.esPromoCompleja(item[i]);
//				
//=======
//				esCompleja = this.esPromoComplejaValida(item[i]);
//
//>>>>>>> a15c3691eb0f782af71f41925b561dfe8768dca6
//				if (esCompleja) {
//					promotion = item[i];
//					break;
//				}
//			}
//
//			if (esCompleja) {
//				break;
//			}
//<<<<<<< HEAD
//			
//		}*/
		



	public String seleccionarPromoComplejaVigente() throws SQLException {
		String query = Queries_promociones.OBTENER_PROMO_NACIONAL_VIGENTE;
		System.out.println(query);

		String promotion = null;

		List<String[]> list = this.db.executeQuery(query).getAllData();

		for (String[] item : list) {
			boolean esCompleja = false;

			for (int i = 0; i < item.length; i++) {
				esCompleja = this.esPromoComplejaValida(item[i]);

				if (esCompleja) {
					promotion = item[i];
					break;
				}
			}

			if (esCompleja) {
				break;
			}

		}

		return promotion;
	}


	public String seleccionarPromo(String evento, String status, String operadorPromhead, String operadorPromstore)
			throws SQLException {

		String query = String.format(Queries.VALIDAR_PROMHEAD_PROMSTORE, evento, status, operadorPromhead,
				operadorPromstore);

		System.out.println(query);

		String promotion = null;

		SQLResult executeQuery = this.db.executeQuery(query);
		boolean statusQuery = !executeQuery.isEmpty();

		if (statusQuery) {
			promotion = executeQuery.getData(0, "PROMOTION");
		}

		return promotion;

	}

	private boolean esPromoComplejaValida(String promotion) throws SQLException {
		String query = String.format(Queries.VALIDAR_COMPLEJA, promotion);

		System.out.println(query);
		SQLResult execute = this.db.executeQuery(query);

		
		return !execute.isEmpty();
	}

	public String seleccionarPromo(String statusInicial) {
		// TODO Auto-generated method stub
		return null;
	}
}

