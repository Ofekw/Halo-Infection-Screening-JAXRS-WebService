package services;

import dto.CandidateDTO;
import constants.Species;
import domain.Candidate;
/**
 * Used by JAX-B to map the @Candidate to the @CandidateDTO
 * @author Ofek | UPI: OWIT454
 *
 */

public class CandidateMapper {
	

	static Candidate toDomainModel(CandidateDTO candidateDTO) {
		Candidate candidate = new Candidate(candidateDTO);
				return candidate;
	}

	static CandidateDTO toDto(Candidate candidate) {
		CandidateDTO candidateDTO = new CandidateDTO(candidate);
		return candidateDTO;

	}
}
