package es.caib.dir3caib.ws.api.test;

import es.caib.dir3caib.persistence.model.Dir3caibConstantes;
import es.caib.dir3caib.utils.Utils;
import es.caib.dir3caib.ws.api.catalogo.*;
import es.caib.dir3caib.ws.api.unidad.Dir3CaibObtenerUnidadesWs;
import es.caib.dir3caib.ws.api.unidad.UnidadWs;
import org.junit.Test;

import java.util.List;

/**
 * @author mgonzalez
 * 21/03/2022
 */
public class TestCatalogoV2Ws extends Dir3CaibTestUtils{

    @Test
    public void obtenerCatEstadoEntidad() {
        try{
            Dir3CaibObtenerCatalogosWs apiCatalogos = getObtenerCatalogosApi(true);

            Long start = System.currentTimeMillis();
            List<CatEstadoEntidad> estadoEntidadList = apiCatalogos.obtenerCatEstadoEntidad();
            List<CatEstadoEntidadWs> estadoEntidadListV2 = apiCatalogos.obtenerCatEstadoEntidadV2();
            List<CatEstadoEntidadWs> estadoEntidadListByEstado = apiCatalogos.obtenerCatEstadoEntidadByEstado(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA ESTADO ENTIDAD: " + Utils.formatElapsedTime(end - start));
            System.out.println("Estados encontrados " + estadoEntidadList.size());
            System.out.println("Estados encontrados " + estadoEntidadListV2.size());
            System.out.println("Estados encontrados " + estadoEntidadListByEstado.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void obtenerCatNivelAdministracion() {
        try{
            Dir3CaibObtenerCatalogosWs apiCatalogos = getObtenerCatalogosApi(true);

            Long start = System.currentTimeMillis();
            List<CatNivelAdministracion> list = apiCatalogos.obtenerCatNivelAdministracion();
            List<CatNivelAdministracionWs> listV2 = apiCatalogos.obtenerCatNivelAdministracionV2();
            List<CatNivelAdministracionWs> listByEstado = apiCatalogos.obtenerCatNivelAdministracionByEstado(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA: " + Utils.formatElapsedTime(end - start));
            System.out.println("Objetos encontrados " + list.size());
            System.out.println("Objetos encontrados " + listV2.size());
            System.out.println("Objetos encontrados " + listByEstado.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void obtenerCatPais() {
        try{
            Dir3CaibObtenerCatalogosWs apiCatalogos = getObtenerCatalogosApi(true);

            Long start = System.currentTimeMillis();
            List<CatPais> list = apiCatalogos.obtenerCatPais();
            List<CatPaisWs> listV2 = apiCatalogos.obtenerCatPaisV2();
            List<CatPaisWs> listByEstado = apiCatalogos.obtenerCatPaisByEstado(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA: " + Utils.formatElapsedTime(end - start));
            System.out.println("Objetos encontrados " + list.size());
            System.out.println("Objetos encontrados " + listV2.size());
            System.out.println("Objetos encontrados " + listByEstado.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void obtenerCatComunidadAutonoma() {
        try{
            Dir3CaibObtenerCatalogosWs apiCatalogos = getObtenerCatalogosApi(true);

            Long start = System.currentTimeMillis();
            List<CatComunidadAutonomaTF> list = apiCatalogos.obtenerCatComunidadAutonoma();
            List<CatComunidadAutonomaWs> listV2 = apiCatalogos.obtenerCatComunidadAutonomaV2();
            List<CatComunidadAutonomaWs> listByEstado = apiCatalogos.obtenerCatComunidadAutonomaByEstado(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA: " + Utils.formatElapsedTime(end - start));
            System.out.println("Objetos encontrados " + list.size());
            System.out.println("Objetos encontrados " + listV2.size());
            System.out.println("Objetos encontrados " + listByEstado.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void obtenerCatProvincia() {
        try{
            Dir3CaibObtenerCatalogosWs apiCatalogos = getObtenerCatalogosApi(true);

            Long start = System.currentTimeMillis();
            List<CatProvinciaTF> list = apiCatalogos.obtenerCatProvincia();
            List<CatProvinciaWs> listV2 = apiCatalogos.obtenerCatProvinciaV2();
            List<CatProvinciaWs> listByEstado = apiCatalogos.obtenerCatProvinciaByEstado(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA: " + Utils.formatElapsedTime(end - start));
            System.out.println("Objetos encontrados " + list.size());
            System.out.println("Objetos encontrados " + listV2.size());
            System.out.println("Objetos encontrados " + listByEstado.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void obtenerCatLocalidad() {
        try{
            Dir3CaibObtenerCatalogosWs apiCatalogos = getObtenerCatalogosApi(true);

            Long start = System.currentTimeMillis();
            List<CatLocalidadTF> list = apiCatalogos.obtenerCatLocalidad();
            List<CatLocalidadWs> listV2 = apiCatalogos.obtenerCatLocalidadV2();
            List<CatLocalidadWs> listByEstado = apiCatalogos.obtenerCatLocalidadByEstado(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA: " + Utils.formatElapsedTime(end - start));
            System.out.println("Objetos encontrados " + list.size());
            System.out.println("Objetos encontrados " + listV2.size());
            System.out.println("Objetos encontrados " + listByEstado.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void obtenerCatEntidadGeografica() {
        try{
            Dir3CaibObtenerCatalogosWs apiCatalogos = getObtenerCatalogosApi(true);

            Long start = System.currentTimeMillis();
            List<CatEntidadGeograficaTF> list = apiCatalogos.obtenerCatEntidadGeografica();
            List<CatEntidadGeograficaWs> listV2 = apiCatalogos.obtenerCatEntidadGeograficaV2();
            List<CatEntidadGeograficaWs> listByEstado = apiCatalogos.obtenerCatEntidadGeograficaByEstado(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA: " + Utils.formatElapsedTime(end - start));
            System.out.println("Objetos encontrados " + list.size());
            System.out.println("Objetos encontrados " + listV2.size());
            System.out.println("Objetos encontrados " + listByEstado.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void obtenerCatTipoVia() {
        try{
            Dir3CaibObtenerCatalogosWs apiCatalogos = getObtenerCatalogosApi(true);

            Long start = System.currentTimeMillis();
            List<CatTipoVia> list = apiCatalogos.obtenerCatTipoVia();
            List<CatTipoViaWs> listV2 = apiCatalogos.obtenerCatTipoViaV2();
            List<CatTipoViaWs> listByEstado = apiCatalogos.obtenerCatTipoViaByEstado(Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE);
            Long end = System.currentTimeMillis();

            System.out.println("TIEMPO CARGA: " + Utils.formatElapsedTime(end - start));
            System.out.println("Objetos encontrados " + list.size());
            System.out.println("Objetos encontrados " + listV2.size());
            System.out.println("Objetos encontrados " + listByEstado.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
