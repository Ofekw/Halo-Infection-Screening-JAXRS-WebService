package singleton;

import workers.CandidateWorker;

public class CandidateWorkerSingleton {

private static CandidateWorker candidateWorker;
	
	public CandidateWorkerSingleton(){
		candidateWorker = new CandidateWorker(EntityManagerFactorySingleton.getInstance());
		}
	
	public static CandidateWorker getInstance(){
		if (candidateWorker == null){
			candidateWorker = new CandidateWorker(EntityManagerFactorySingleton.getInstance());
		}
		return candidateWorker;
	}
}
