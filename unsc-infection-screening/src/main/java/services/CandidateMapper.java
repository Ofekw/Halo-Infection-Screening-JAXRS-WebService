package services;

import workers.CandidateWorker;
import dto.CandidateDTO;
import domain.Candidate;
import domain.Species;

public class CandidateMapper {
	
	private static CandidateWorker candidateWorker = new CandidateWorker();

	static Candidate toDomainModel(CandidateDTO candidateDTO) {
		String specieName = candidateDTO.getSpecies();
		Species species = candidateWorker.getSpeciesByName(specieName);
		Candidate candidate = new Candidate(candidateDTO.getLastname(), candidateDTO.getFirstname(), 
				candidateDTO.getDob(),	candidateDTO.getGender(), species);
		candidate.setId(candidateDTO.getId());
		candidate.setCandidateAddress(candidateWorker.getAddressById(candidateDTO.getAddress()));
				return candidate;
	}

	static CandidateDTO toDto(Candidate candidate) {
		CandidateDTO candidateDTO = new CandidateDTO(candidate);
		return candidateDTO;

	}
}
