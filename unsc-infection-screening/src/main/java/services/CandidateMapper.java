package services;

import dto.CandidateDTO;
import constants.Species;
import domain.Candidate;

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
