package Service.CriteriosDeFusion;

import Service.RegistroEntrada;

import java.util.ArrayList;
import java.util.List;

public class CantidadDeServicios implements  CriterioDeFusion{
    private double porcentaje;

    public CantidadDeServicios(double porcentaje){
        this.porcentaje = porcentaje;
    }

    public boolean cumple(RegistroEntrada unRegistro, RegistroEntrada otroRegistro) {
        List<String> serviciosUnRegistro = unRegistro.getListaServicios();
        List<String> serviciosOtroRegistro = otroRegistro.getListaServicios();

        // Intersección de servicios comunes sin importar el orden
        List<String> serviciosComunes = new ArrayList<>(serviciosUnRegistro);
        serviciosComunes.retainAll(serviciosOtroRegistro);

        double tamServiciosUnRegistro = serviciosUnRegistro.size();
        double tamServiciosOtroRegistro = serviciosOtroRegistro.size();

        // Calcular la proporcion de servicios comunes con respecto al total de servicios
        double proporcionComunesUnRegistro = serviciosComunes.size() / tamServiciosUnRegistro;
        double proporcionComunesOtroRegistro = serviciosComunes.size() / tamServiciosOtroRegistro;

        // Verificar si la proporcion es mayor o igual al porcentaje dado
        return proporcionComunesUnRegistro >= porcentaje && proporcionComunesOtroRegistro >= porcentaje;
    }
}
