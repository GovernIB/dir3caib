package es.caib.dir3caib.persistence.utils;

import java.util.ArrayList;
import java.util.List;

import es.caib.dir3caib.persistence.model.Descarga;

/**
 * 
 * @author anadal
 * 
 */
public class ResultadosImportacion {

  protected List<String> procesados = new ArrayList<String>();
  protected List<String> inexistentes = new ArrayList<String>();
  protected Descarga descarga = new Descarga();
  protected List<String> existentes = new ArrayList<String>();

  /**
   * 
   */
  public ResultadosImportacion() {
    super();
    // TODO Auto-generated constructor stub
  }

  public List<String> getProcesados() {
    return procesados;
  }

  public void setProcesados(List<String> procesados) {
    this.procesados = procesados;
  }

  public List<String> getInexistentes() {
    return inexistentes;
  }

  public void setInexistentes(List<String> inexistentes) {
    this.inexistentes = inexistentes;
  }

  public Descarga getDescarga() {
    return descarga;
  }

  public void setDescarga(Descarga descarga) {
    this.descarga = descarga;
  }

  public List<String> getExistentes() {
    return existentes;
  }

  public void setExistentes(List<String> existentes) {
    this.existentes = existentes;
  }

}
