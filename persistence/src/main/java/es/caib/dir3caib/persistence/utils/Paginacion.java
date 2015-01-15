package es.caib.dir3caib.persistence.utils;

import es.caib.dir3caib.persistence.ejb.BaseEjbJPA;

import java.util.List;

/**
 * Created by Fundació BIT.
 *
 * @author earrivi
 *         Date: 20/02/14
 */
public class Paginacion {

    private int totalResults;
    private int totalPages;
    private int beginIndex;
    private int endIndex;
    private int currentIndex;
    private List<Object> listado;

    public Paginacion(int total, int pageNumber) {
        totalResults = total;
        totalPages = (int) (totalResults / BaseEjbJPA.RESULTADOS_PAGINACION);
        if(totalResults % BaseEjbJPA.RESULTADOS_PAGINACION != 0){
            totalPages = totalPages +1;
        }

        currentIndex = pageNumber;
        beginIndex = Math.max(1, currentIndex - BaseEjbJPA.RESULTADOS_PAGINACION);
        endIndex = Math.min(beginIndex + 10, totalPages);

    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getBeginIndex() {
        return beginIndex;
    }


    public int getEndIndex() {
        return endIndex;
    }


    public int getCurrentIndex() {
        return currentIndex;
    }


    public List<Object> getListado() {
        return listado;
    }

    public void setListado(List<Object> listado) {
        this.listado = listado;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
