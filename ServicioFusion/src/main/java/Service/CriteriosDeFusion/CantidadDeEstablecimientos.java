package Service.CriteriosDeFusion;

import Service.RegistroEntrada;

import java.util.ArrayList;
import java.util.List;

public class CantidadDeEstablecimientos implements CriterioDeFusion{



    private double porcentaje;

    public CantidadDeEstablecimientos(double porcentaje){
        this.porcentaje = porcentaje;//75%---0.75
    }

    public boolean cumple(RegistroEntrada unRegistro, RegistroEntrada otroRegistro) {
        List<String> establecimientosUnRegistro = unRegistro.getListaEstablecimientos();
        List<String> establecimientosOtroRegistro = otroRegistro.getListaEstablecimientos();

        // Intersección de establecimientos comunes sin importar el orden
        List<String> establecimientosComunes = new ArrayList<>(establecimientosUnRegistro);
        establecimientosComunes.retainAll(establecimientosOtroRegistro);

        double tamEstablecimientosUnRegistro = establecimientosUnRegistro.size();
        double tamEstablecimientosOtroRegistro = establecimientosOtroRegistro.size();

        // Calcular la proporción de establecimientos comunes con respecto al total de establecimientos
        double proporcionEstablecimientosUnRegistro = establecimientosComunes.size() / tamEstablecimientosUnRegistro;
        double proporcionEstablecimientosOtroRegistro = establecimientosComunes.size() / tamEstablecimientosOtroRegistro;

        // Verificar si la proporción es mayor o igual al porcentaje dado
        return proporcionEstablecimientosUnRegistro >= porcentaje && proporcionEstablecimientosOtroRegistro >= porcentaje;
    }
}
