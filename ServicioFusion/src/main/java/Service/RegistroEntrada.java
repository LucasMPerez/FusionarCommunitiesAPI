package Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter
public class RegistroEntrada {

    public RegistroEntrada(){
        this.listaServicios = new ArrayList<>();
        this.listaEstablecimientos = new ArrayList<>();
        this.listaMiembros = new ArrayList<>();
        this.propuestas = new ArrayList<>();
    }


    @JsonProperty("id")
    private int id; // ID COMUNIDAD

    @JsonProperty("listaEstablecimientos")
    private List<String> listaEstablecimientos; // Lista de establecimientos observados

    @JsonProperty("listaServicios")
    private List<String> listaServicios; // Lista de servicios observados

    @JsonProperty("gradoConfianza")
    private int gradoConfianza; // Grado de confianza

    @JsonProperty("listaMiembros")
    private List<String> listaMiembros; // Lista de miembros

    @JsonProperty("propuestasDeFusion")
    private List<Propuesta> propuestas;

    public void agregarPropuesta(Propuesta unaPropuesta){
        if(propuestas.get(0).getIdComunidades().isEmpty()){
            propuestas.set(0,unaPropuesta);
        } else{
            propuestas.add(unaPropuesta);
        }

    }
}
