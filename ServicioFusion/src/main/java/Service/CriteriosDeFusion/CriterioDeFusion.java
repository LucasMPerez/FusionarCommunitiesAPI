package Service.CriteriosDeFusion;

import Service.RegistroEntrada;

public interface CriterioDeFusion {

    public boolean cumple(RegistroEntrada unRegistro, RegistroEntrada otroRegistro);

}
