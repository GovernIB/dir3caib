package es.caib.dir3caib.persistence.ejb;

import java.io.Serializable;

import javax.ejb.Local;

/**
 * 
 * @author anadal
 *
 */
@Local
public interface SincronitzacioDir3Local extends Serializable {
  
  public void sincronitzar();
  
  public void createTimer();
  
  public void clearTimers();

}
