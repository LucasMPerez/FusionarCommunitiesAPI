package example;

import Controller.ComunidadController;
import Controller.FusionController;
import Service.ApiFusion;
import Service.RegistroEntrada;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class  Main {
    public static void main(String[] args) throws IOException {


        Javalin app = Javalin.create(config -> {
            config.plugins.register(new SwaggerPlugin(new SwaggerConfiguration()));  // CONECTARLO CON SWAGGER
            config.plugins.enableCors(corsContainer -> corsContainer.add(it->it.anyHost())); // POR EL POST
        }).start(7000);
        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Origin", "*"); // Esto permite solicitudes desde cualquier origen. Ajusta según tus necesidades.
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });
        JavalinJackson.defaultMapper(); // POR EL TEMA DEL SERIALIADO
        String urlJson = "src/test/java/example/prueba.JSON";

        File jsonFile = new File(urlJson); // PRUEBA DEL JSON
        ObjectMapper objectMapper = new ObjectMapper(); // MAPEA EL JSON PARA TRANSFORMARLO EN LA LISTA
        objectMapper.registerModule(new JavaTimeModule());
        List<RegistroEntrada> registros = new ArrayList<>();
        registros = objectMapper.readValue(jsonFile, new TypeReference<List<RegistroEntrada>>() {});

        FusionController fusionController = new FusionController(app);
        ComunidadController comunidadController = new ComunidadController(app,registros);

        // CONTROLADORES FUSION
        fusionController.agregarPropuesta(registros); // POST
        fusionController.mostrarPropuestas(); // GET

        // CONTROLADORES REGISTROS
        comunidadController.verRegistros(); // GET
        comunidadController.verRegistroPorID(); // GET x ID
        comunidadController.agregarRegistro(); // POST
        comunidadController.aceptarPropuesta(); // PUT


        // Agregar en json las propuestas de fusion.
        // Agregar en el json la lista de propuesta (Aceptada/Rechazada)

    }


}
