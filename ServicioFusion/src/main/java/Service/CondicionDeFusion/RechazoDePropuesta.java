package Service.CondicionDeFusion;

import Service.Propuesta;
import Service.RegistroEntrada;
import org.eclipse.jetty.server.RequestLog;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RechazoDePropuesta implements  CondicionDeFusion{


    public boolean satisface(RegistroEntrada registro, List<Integer> idPropuestas) {
        if(registro.getPropuestas().get(0).getFecha() == null) return true;

        List<Propuesta> propuestasSinCaducar = registro.getPropuestas().stream()
                .filter(propuesta -> {
                    LocalDate fechaPropuesta = propuesta.getFecha();
                    // Calcula la diferencia en meses entre la fecha de la propuesta y la fecha actual
                    long mesesDiferencia = fechaPropuesta.until(LocalDate.now()).toTotalMonths();
                    // Comprueba si han pasado más de 6 meses
                    return mesesDiferencia < 6;
                })
                .collect(Collectors.toList());

        propuestasSinCaducar = propuestasSinCaducar.stream().filter(f->!f.getIdComunidades().equals(idPropuestas)).toList();

        return !propuestasSinCaducar.isEmpty();
    }
}
