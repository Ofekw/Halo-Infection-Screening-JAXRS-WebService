package services;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import domain.Candidate;

/**
 * ContextResolver implementation to return a customised JAXBContext
 */
public class CandidateResolver implements ContextResolver<JAXBContext> {
	private JAXBContext _context;

	public CandidateResolver() {
		try {
			// The JAXB Context should be able to marshal and unmarshal the
			// specified classes.
			_context = JAXBContext.newInstance(Candidate.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JAXBContext getContext(Class<?> type) {
		if (type.equals(Candidate.class)) {
			return _context;
		} else {
			return null;
		}
	}
}
