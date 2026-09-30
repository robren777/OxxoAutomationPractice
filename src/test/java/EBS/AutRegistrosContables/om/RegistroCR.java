package EBS.AutRegistrosContables.om;

public class RegistroCR {
    private String importe;
    private String descripcion_servicio;
    private String cuenta_distribucion;

    // Getters y setters
    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getDescripcion_servicio() {
        return descripcion_servicio;
    }

    public void setDescripcion_servicio(String descripcion_servicio) {
        this.descripcion_servicio = descripcion_servicio;
    }

    public String getCuenta_distribucion() {
        return cuenta_distribucion;
    }

    public void setCuenta_distribucion(String cuenta_distribucion) {
        this.cuenta_distribucion = cuenta_distribucion;
    }
}

