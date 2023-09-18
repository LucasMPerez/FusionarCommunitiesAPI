package Controller;

import Service.ApiFusion;
import Service.RegistroEntrada;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.List;

public class FusionController {

    private Javalin app;
    private List<RegistroEntrada> propuestas;
    public FusionController(Javalin app){
        this.app = app;
        this.propuestas = new ArrayList<>();
    }



    //@POST
    public void agregarPropuesta(List<RegistroEntrada> registro) {

        app.post("/propuestas", ctx -> {
            ApiFusion api = new ApiFusion(); // INICIO DE LA API
            this.propuestas = api.propuestasFusiones(registro);
            // Envía una respuesta con un mensaje que confirme la creación de las propuestas
            ctx.result("  Propuestas agregadas ");
        });
    }
    //@GET
    public void mostrarPropuestas(){
        app.get("/propuestas", ctx -> {
            ctx.json(propuestas);
        });
    }


}

