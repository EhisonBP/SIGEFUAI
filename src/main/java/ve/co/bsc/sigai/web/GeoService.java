package ve.co.bsc.sigai.web;
import java.util.Set;

import ve.co.bsc.sigai.domain.Ciudad;
import ve.co.bsc.sigai.domain.Estado;


public interface GeoService {

	public Set<Estado> findAllStates();

	public Set<Ciudad> findCitiesForState(String estado);

}