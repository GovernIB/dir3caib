package es.caib.dir3caib.persistence.utils;

/**
 * Created 30/07/14 14:33
 *
 * @author mgonzalez
 *
 * Clase que representa la tupla(id-Descripción) de un objeto del catálogo de dir3caib(Ambitoterritorial, Pais, etc)
 * Se emplea en los métodos rest para devolver un conjunto de datos reducidos.
 */
public class CodigoValor {

     private Object id;
     private String descripcion;

     public CodigoValor() {
     }

     public Object getId() {
        return id;
     }

     public void setId(Object id) {
        this.id = id;
     }

      public String getDescripcion() {
         return descripcion;
     }

     public void setDescripcion(String descripcion) {
         this.descripcion = descripcion;
     }
}
