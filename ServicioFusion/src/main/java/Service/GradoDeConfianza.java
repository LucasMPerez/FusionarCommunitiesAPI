package Service;

import Service.CriteriosDeFusion.CriterioDeFusion;

public class GradoDeConfianza implements CriterioDeFusion {
    public boolean cumple(RegistroEntrada unRegistro, RegistroEntrada otroRegistro) {
        return unRegistro.getGradoConfianza() == otroRegistro.getGradoConfianza();
    }
}
