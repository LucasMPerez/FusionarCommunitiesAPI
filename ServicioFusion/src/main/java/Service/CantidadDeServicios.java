package Service;

import java.util.ArrayList;
import java.util.List;

public class CantidadDeServicios implements  CriterioDeFusion{
    private double porcentaje;

    public CantidadDeServicios(double porcentaje){
        this.porcentaje = porcentaje;
    }

    public boolean cumple(RegistroEntrada unRegistro, RegistroEntrada otroRegistro) {
        double tamRegistro1 = unRegistro.getListaServicios().size();
        List<String> copiaListaServicios = new ArrayList<>(unRegistro.getListaServicios());
        ((ArrayList<?>) copiaListaServicios).retainAll(otroRegistro.getListaServicios());

        return (copiaListaServicios.size()/tamRegistro1) > porcentaje;
    }
}
