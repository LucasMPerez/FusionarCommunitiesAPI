package Service.CondicionDeFusion;

import Service.Propuesta;
import Service.RegistroEntrada;

import java.util.List;

public interface CondicionDeFusion {
    public boolean satisface(RegistroEntrada registro, List<Integer> idPropuestas);
}
