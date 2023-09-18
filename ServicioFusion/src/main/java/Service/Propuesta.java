package Service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Propuesta {



    @JsonProperty("idComunidades")
    private List<Integer> idComunidades;

    @JsonProperty("fechaPropuesta")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDate fecha;

    public Propuesta(){
        this.idComunidades = new ArrayList<>();
    }

}
