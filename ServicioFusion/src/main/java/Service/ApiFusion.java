package Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ApiFusion {
    private List<CriterioDeFusion> criterios;
    private List<RegistroEntrada> registrosAFusionar;
    public ApiFusion(){
        this.criterios = new ArrayList<>();
        this.registrosAFusionar = new ArrayList<>();
        this.criterios.add(new CantidadDeMiembros(0.75));
        this.criterios.add(new GradoDeConfianza());
        this.criterios.add(new CantidadDeServicios(0.75));
        this.criterios.add(new CantidadDeEstablecimientos(0.75));

    }

    public Boolean esFusionable(RegistroEntrada unRegistro, List<RegistroEntrada> registros, CriterioDeFusion unCriterio) {
        return registros.stream()
                .filter(otroRegistro -> otroRegistro.getId() != unRegistro.getId()
                        && unCriterio.cumple(unRegistro, otroRegistro))
                .findAny()
                .isPresent();
    }

    public List<RegistroEntrada> propuestasFusiones(List<RegistroEntrada> registros ) {

            this.registrosAFusionar = registros.stream().filter(unRegistro -> criterios.stream().allMatch(unCriterio-> this.esFusionable(unRegistro,registros,unCriterio))).collect(Collectors.toList());
            //[ [1,2,3] , [4,5] ] ----- [ [1,2],[1,3],[4,5] ]
            // [1,2,3,4] ==> 1

            if( !this.registrosAFusionar.isEmpty() ) {
                RegistroEntrada unRegistro =  registrosAFusionar.get(0);
                this.registrosAFusionar = registrosAFusionar.stream().filter(otroRegistro-> criterios.stream().allMatch(f->f.cumple(otroRegistro,unRegistro)) ).toList();

                Propuesta unaPropuestaNueva = new Propuesta();
                unaPropuestaNueva.setFecha(LocalDate.now());
                unaPropuestaNueva.setIdComunidades( registrosAFusionar.stream().map(f->f.getId()).collect(Collectors.toList()) );

                registrosAFusionar.forEach(f->f.agregarPropuesta(unaPropuestaNueva));
                return registrosAFusionar;
               }
            else {
                System.out.println("No hay ningun registro para fusionar");
                return null;
                }


    }

    public List<RegistroEntrada> aceptarFusion(List<RegistroEntrada> registros){
        List<RegistroEntrada> registrosAFusionar = this.propuestasFusiones(registros);

        List<String> miembros = this.unionSinRepetidos(registrosAFusionar.get(0).getListaMiembros(),registrosAFusionar.get(1).getListaMiembros());
        List<String> establecimientos = this.unionSinRepetidos(registrosAFusionar.get(0).getListaEstablecimientos(),registrosAFusionar.get(1).getListaEstablecimientos());
        List<String> serviciosPrestados = this.unionSinRepetidos(registrosAFusionar.get(0).getListaServicios(),registrosAFusionar.get(1).getListaServicios());

        RegistroEntrada registroFusionado = new RegistroEntrada();

        registroFusionado.setId(registrosAFusionar.get(0).getId());
        registroFusionado.setGradoConfianza(registrosAFusionar.get(0).getGradoConfianza());

        registroFusionado.setListaServicios(serviciosPrestados);
        registroFusionado.setListaMiembros(miembros);
        registroFusionado.setListaEstablecimientos(establecimientos);

        registros = this.eliminarRegistrosPorID(registros,registrosAFusionar);
        List<RegistroEntrada> registrosNuevos = new ArrayList<>(registros);
        registrosNuevos.add(registroFusionado); // Porque registros es inmutable
        return registrosNuevos;
    }

    private static <T> List<T> unionSinRepetidos(List<T> lista1, List<T> lista2) {
        Set<T> conjunto = new HashSet<>(lista1);

        for (T elemento : lista2) {
            conjunto.add(elemento);
        }
        return new ArrayList<>(conjunto);
    }

    private static List<RegistroEntrada> eliminarRegistrosPorID(List<RegistroEntrada> registrosUno, List<RegistroEntrada> registroDos) {
        List<Integer> idsRegistroDos = registroDos.stream().map(RegistroEntrada::getId).toList();
        List<RegistroEntrada> registrosFiltrados = registrosUno.stream()
                .filter(registro -> !idsRegistroDos.contains(registro.getId()))
                .toList();

        return registrosFiltrados;
    }

}
