package services;

import dto.CandidateDTO;
import constants.Species;
import domain.Candidate;

public class CandidateMapper {
	

	static Candidate toDomainModel(CandidateDTO candidateDTO) {
		Candidate candidate = new Candidate(
				candidateDTO.getLastname(), 
				candidateDTO.getFirstname(), 
				candidateDTO.getDob(),	
				candidateDTO.getGender(),
				candidateDTO.getSpecies());
				return candidate;
	}

	static CandidateDTO toDto(Candidate candidate) {
		CandidateDTO candidateDTO = new CandidateDTO(
				candidate.getId(),
				candidate.getLastname(), 
				candidate.getFirstname(), 
				candidate.getDob(),	
				candidate.getGender(),
				candidate.getSpecie());
		return candidateDTO;

	}
}
