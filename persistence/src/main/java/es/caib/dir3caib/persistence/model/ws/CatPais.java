package es.caib.dir3caib.persistence.model.ws;

/**
 * @author mgonzalez
 * 02/03/2022
 */
public class CatPais {

    private Long codigoPais;
    private String descripcionPais;
    private String alfa3Pais;
    private String alfa2Pais;


    public Long getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(Long codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getDescripcionPais() {
        return descripcionPais;
    }

    public void setDescripcionPais(String descripcionPais) {
        this.descripcionPais = descripcionPais;
    }

    public String getAlfa3Pais() {
        return alfa3Pais;
    }

    public void setAlfa3Pais(String alfa3Pais) {
        this.alfa3Pais = alfa3Pais;
    }

    public String getAlfa2Pais() {
        return alfa2Pais;
    }

    public void setAlfa2Pais(String alfa2Pais) {
        this.alfa2Pais = alfa2Pais;
    }

    public void rellenar(es.caib.dir3caib.persistence.model.CatPais catPais) {

        this.setCodigoPais(catPais.getCodigoPais());
        this.setDescripcionPais(catPais.getDescripcionPais());
        this.setAlfa2Pais(catPais.getAlfa2Pais());
        this.setAlfa3Pais(catPais.getAlfa3Pais());

    }

    public static CatPais generar(es.caib.dir3caib.persistence.model.CatPais catPais) {
        CatPais catPais1 = new CatPais();
        if (catPais != null) {
            catPais1.rellenar(catPais);
        }
        return catPais1;
    }
}
