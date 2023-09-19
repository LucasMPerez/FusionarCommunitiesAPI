package Controller;

import Service.ApiFusion;
import Service.RegistroEntrada;
import io.javalin.Javalin;

import java.util.List;

public class ComunidadController {


    private Javalin app;
    private List<RegistroEntrada> registros;

    public ComunidadController(Javalin app, List<RegistroEntrada> registros){
        this.registros = registros;
        this.app = app;
    }

    //@GET
    public void verRegistros() {
        app.get("/comunidades",context -> context.json(registros));
    }

    //@GET
    public void verRegistroPorID(){
        app.get("/comunidades/{id}", ctx -> {
            int itemId = Integer.parseInt(ctx.pathParam("id"));
            RegistroEntrada item = findItemById(registros, itemId);
            if (item != null) {
                ctx.json(item); // Devuelve el registro en formato JSON
            } else {
                ctx.status(404).result("Registro no encontrado"); // Si no se encuentra el registro, devuelve un estado 404
            }
        });

    }
    private static RegistroEntrada findItemById(List<RegistroEntrada> itemList, int id) {
        for (RegistroEntrada item : itemList) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
    // @POST
    public void agregarRegistro(){
        app.post("/comunidades", ctx -> {
            // Obtén los datos del cuerpo de la solicitud como un objeto JSON
            RegistroEntrada nuevoRegistro = ctx.bodyAsClass(RegistroEntrada.class);

            // Agrega el nuevo registro a la lista de registros
            registros.add(nuevoRegistro);

            // Envía una respuesta con un mensaje que confirme la creación del registro
            ctx.result("Registro agregado: " + nuevoRegistro.getId());
        });
    }

    // @PUT
    public void aceptarPropuesta(){
        app.put("/comunidades", ctx -> {
            ApiFusion api = new ApiFusion();
            List<RegistroEntrada> propuestaAceptada = api.aceptarFusion(registros);
            this.registros = propuestaAceptada;
            ctx.status(200);
        });
    }


}
