package es.caib.dir3caib.persistence.ejb;

import javax.ejb.Local;
import java.io.Serializable;

/**
 * 
 * @author anadal
 *
 */
@Local
public interface TimerDir3Local extends Serializable {
  
  void sincronitzar();
  
  void createTimer();
  
  void clearTimers();

}