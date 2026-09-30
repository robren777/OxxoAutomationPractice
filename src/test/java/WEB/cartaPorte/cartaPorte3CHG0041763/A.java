package WEB.cartaPorte.cartaPorte3CHG0041763;

import utils.password.PasswordUtil;

public class A {

	public static void main(String arg []) {
		PasswordUtil.generateEncryptPass();
		String ubicarVariables[][]= {{"8","1"},{"9","1"},{"10","1"},{"4","2"},{"5","2"},{"10","3"},{"2","4"},{"7","4"},
				{"2","5"},{"4","5"}};
		for(int i=0; i<ubicarVariables.length; i++) {
			for(int j=0; j<ubicarVariables[i].length; j++) {
				//System.out.println("El valor de i: "+ i+ " El valor j: "+j+" Valor Matriz= "+ubicarVariables[i][j]);
			}
		}
		
		String renglon[]= {"8","9","10","4","5","10","2","7","2","4"};
		String pag[]= {"1","1","1","2","2","3","4","4","5","5"};
		
		for(int i=0; i<renglon.length && i<pag.length; i++) {
			System.out.println("El valor de i: "+ i+" RENGLON: "+renglon[i]+" pagina: "+pag[i]);
		}
		
		
	}
}
