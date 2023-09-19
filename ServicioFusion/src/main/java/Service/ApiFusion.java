package Service;

import Service.CondicionDeFusion.CondicionDeFusion;
import Service.CondicionDeFusion.RechazoDePropuesta;
import Service.CriteriosDeFusion.CantidadDeEstablecimientos;
import Service.CriteriosDeFusion.CantidadDeMiembros;
import Service.CriteriosDeFusion.CantidadDeServicios;
import Service.CriteriosDeFusion.CriterioDeFusion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ApiFusion {
    private List<CriterioDeFusion> criterios;
    private List<RegistroEntrada> registrosAFusionar;
    private CondicionDeFusion condicionDeFusion;
    public ApiFusion(){
        this.criterios = new ArrayList<>();
        this.registrosAFusionar = new ArrayList<>();
        this.criterios.add(new CantidadDeServicios(0.75));
        this.criterios.add(new CantidadDeMiembros(0.75));
        this.criterios.add(new GradoDeConfianza());
        this.criterios.add(new CantidadDeEstablecimientos(0.75));
        this.condicionDeFusion = new RechazoDePropuesta();

    }

    public Boolean esFusionable(RegistroEntrada unRegistro, List<RegistroEntrada> registros, CriterioDeFusion unCriterio) {
        return registros.stream()
                .filter(otroRegistro -> otroRegistro.getId() != unRegistro.getId()
                        && unCriterio.cumple(unRegistro, otroRegistro))
                .findAny()
                .isPresent();
    }

    public List<Propuesta> propuestasDeFusion(List<RegistroEntrada> registros ){
        this.registrosAFusionar = registros.stream().filter(unRegistro -> criterios.stream().allMatch(unCriterio-> this.esFusionable(unRegistro,registros,unCriterio))).collect(Collectors.toList());
        Set<Integer> comunidadesAgregadas = new HashSet<>();
        Set<Propuesta> propuestasDeFusion = new HashSet<>();

        for (RegistroEntrada unRegistro : registrosAFusionar) {
            Propuesta unaPropuesta = new Propuesta();
            List<RegistroEntrada> conjuntoPropuestas = registrosAFusionar.stream()
                    .filter(otroRegistro -> criterios.stream().allMatch(f -> f.cumple(otroRegistro, unRegistro)))
                    .toList();


            List<Integer> idConjunto = conjuntoPropuestas.stream().map(RegistroEntrada::getId).toList();
            // Filtra las comunidades que aún no se han agregado a ninguna propuesta
            List<Integer> comunidadesNoAgregadas = idConjunto.stream()
                    .filter(comunidadId -> !comunidadesAgregadas.contains(comunidadId))
                    .toList();

            // Agrega las comunidades a la propuesta actual y marca como agregadas
            unaPropuesta.setIdComunidades(comunidadesNoAgregadas);
            comunidadesAgregadas.addAll(comunidadesNoAgregadas);
            // Solo agrega la propuesta si tiene comunidades
            if (condicionDeFusion.satisface(unRegistro,idConjunto) && !comunidadesNoAgregadas.isEmpty()) {
                unaPropuesta.setFecha(LocalDate.now());
                propuestasDeFusion.add(unaPropuesta);
            }
        }
       return propuestasDeFusion.stream().toList();
    }

    public List<RegistroEntrada> propuestasFusiones(List<RegistroEntrada> registros ) {
        Propuesta unaPropuesta = this.propuestasDeFusion(registros).get(0);
        List<RegistroEntrada> registrosFiltrados = registros.stream().filter(f->unaPropuesta.getIdComunidades().contains(f.getId())).toList();
        registrosFiltrados.forEach(f->f.agregarPropuesta(unaPropuesta));
        return registrosFiltrados;

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

        registros.removeIf(f->f.getId() == registrosAFusionar.get(0).getId() || f.getId() == registrosAFusionar.get(1).getId());
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

}
