package WorkFlows.util.Panp3;

public class UtilsPanp {
	
	public String generaRfc() {
		
		int anio;
		int mes;
		int dia;
		
		String nombre = "AAAA";
		String clave = "QQ8";
		String rfc;
		
		anio = (int)(Math.random()*99);
		System.out.println(anio);
		
		if(anio == 0) {
			anio = anio + 1;
		}
		if(anio < 10) {
			rfc = nombre+"0"+anio;
		}else {
			rfc = nombre+anio;
		}
		
		mes = (int)(Math.random()*12);
		System.out.println(mes);
		
		if(mes == 0) {
			mes = mes + 1;
		}
		if(mes < 10) {
			rfc = rfc+"0"+mes;
		}else {
			rfc = rfc+mes;
		}
		
		dia = (int)(Math.random()*31);
		System.out.println(dia);
		
		if(dia == 0) {
			dia = dia + 1;
		}
		if(dia < 10) {
			rfc = rfc+"0"+dia;
		}else {
			rfc = rfc+dia;
		}
		
		return rfc+clave;
	}
}
