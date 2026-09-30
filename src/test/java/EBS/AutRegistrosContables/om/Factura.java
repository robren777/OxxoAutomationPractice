package EBS.AutRegistrosContables.om;

import java.util.List;

public class Factura {
    private String numero_oc;
    private String socio_comercial;
    private String nro_proveedor;
    private String fecha_factura;
    private String nro_factura;
    private String div_factura;
    private String importe_factura;
    private String importe_impuesto;
    private String descripcion_servicio_contador;
    private List<RegistroCR> registros_cr;

    // Getters y setters
    public String getNumero_oc() {
        return numero_oc;
    }

    public void setNumero_oc(String numero_oc) {
        this.numero_oc = numero_oc;
    }

    public String getSocio_comercial() {
        return socio_comercial;
    }

    public void setSocio_comercial(String socio_comercial) {
        this.socio_comercial = socio_comercial;
    }

    public String getNro_proveedor() {
        return nro_proveedor;
    }

    public void setNro_proveedor(String nro_proveedor) {
        this.nro_proveedor = nro_proveedor;
    }

    public String getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(String fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    public String getNro_factura() {
        return nro_factura;
    }

    public void setNro_factura(String nro_factura) {
        this.nro_factura = nro_factura;
    }

    public String getDiv_factura() {
        return div_factura;
    }

    public void setDiv_factura(String div_factura) {
        this.div_factura = div_factura;
    }

    public String getImporte_factura() {
        return importe_factura;
    }

    public void setImporte_factura(String importe_factura) {
        this.importe_factura = importe_factura;
    }

    public String getImporte_impuesto() {
        return importe_impuesto;
    }

    public void setImporte_impuesto(String importe_impuesto) {
        this.importe_impuesto = importe_impuesto;
    }

    public String getDescripcion_servicio_contador() {
        return descripcion_servicio_contador;
    }

    public void setDescripcion_servicio_contador(String descripcion_servicio_contador) {
        this.descripcion_servicio_contador = descripcion_servicio_contador;
    }

    public List<RegistroCR> getRegistros_cr() {
        return registros_cr;
    }

    public void setRegistros_cr(List<RegistroCR> registros_cr) {
        this.registros_cr = registros_cr;
    }
}

