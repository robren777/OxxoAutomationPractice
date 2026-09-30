package WM.EvolucionRetiroSinTarjeta.migraciones.om.tablas;

@FunctionalInterface
public interface CustomEquals<T> {

	public boolean equals(T val1, T val2);
	
}
