package Service;

import java.util.ArrayList;
import java.util.List;

public class CantidadDeEstablecimientos implements CriterioDeFusion{



    private double porcentaje;

    public CantidadDeEstablecimientos(double porcentaje){
        this.porcentaje = porcentaje;//75%---0.75
    }

    public boolean cumple(RegistroEntrada unRegistro, RegistroEntrada otroRegistro) {
       double tamRegistro1 = unRegistro.getListaEstablecimientos().size();
        List<String> copiaListaEstablecimientos = new ArrayList<>(unRegistro.getListaEstablecimientos());
        ((ArrayList<?>) copiaListaEstablecimientos).retainAll(otroRegistro.getListaEstablecimientos());

      return (copiaListaEstablecimientos.size()/ tamRegistro1) > porcentaje;
    }
}
