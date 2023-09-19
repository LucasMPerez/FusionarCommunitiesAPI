package Service.CriteriosDeFusion;

import Service.RegistroEntrada;

import java.util.ArrayList;
import java.util.List;

public class CantidadDeMiembros implements CriterioDeFusion{
    private double porcentaje;

    public CantidadDeMiembros(double porcentaje){
        this.porcentaje = porcentaje;
    }

    public boolean cumple(RegistroEntrada unRegistro, RegistroEntrada otroRegistro) {
        List<String> miembrosUnRegistro = unRegistro.getListaMiembros();
        List<String> miembrosOtroRegistro = otroRegistro.getListaMiembros();

        // Intersección de miembros comunes sin importar el orden
        List<String> miembrosComunes = new ArrayList<>(miembrosUnRegistro);
        miembrosComunes.retainAll(miembrosOtroRegistro);

        double tamMiembrosUnRegistro = miembrosUnRegistro.size();
        double tamMiembrosOtroRegistro = miembrosOtroRegistro.size();

        // Calcular la proporción de miembros comunes con respecto al total de miembros
        double proporcionMiembrosUnRegistro = miembrosComunes.size() / tamMiembrosUnRegistro;
        double proporcionMiembrosOtroRegistro = miembrosComunes.size() / tamMiembrosOtroRegistro;

        // Verificar si la proporción es mayor o igual al porcentaje dado
        return proporcionMiembrosUnRegistro >= porcentaje && proporcionMiembrosOtroRegistro >= porcentaje;
    }

}
