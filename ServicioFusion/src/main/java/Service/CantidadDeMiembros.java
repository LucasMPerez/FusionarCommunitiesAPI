package Service;

import java.util.ArrayList;
import java.util.List;

public class CantidadDeMiembros implements CriterioDeFusion{
    private double porcentaje;

    public CantidadDeMiembros(double porcentaje){
        this.porcentaje = porcentaje;
    }

    public boolean cumple(RegistroEntrada unRegistro, RegistroEntrada otroRegistro) {
        double tamRegistro1 = unRegistro.getListaMiembros().size();
        List<String> copiaListaMiembros = new ArrayList<>(unRegistro.getListaEstablecimientos());
        ((ArrayList<?>) copiaListaMiembros).retainAll(otroRegistro.getListaEstablecimientos());


        return (copiaListaMiembros.size()/tamRegistro1) > porcentaje;
    }
}
