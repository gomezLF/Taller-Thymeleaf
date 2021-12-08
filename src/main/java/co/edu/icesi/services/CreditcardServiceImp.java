package co.edu.icesi.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.model.sales.Creditcard;
import co.edu.icesi.repositories.CreditcardRepo;

@Service
public class CreditcardServiceImp implements CreditcardService {
	
	CreditcardRepo creditcardRepo;
	
	
	public CreditcardServiceImp(CreditcardRepo creditcardRepo) {
		this.creditcardRepo = creditcardRepo;
	}

	
	@Override
	public void saveCreditCard(Creditcard creditcard) {
		creditcardRepo.save(creditcard);
	}

	@Override
	public void editCreditCard(Creditcard creditcard){
		Optional<Creditcard> temp = findCreditCard(creditcard.getCreditcardid());
		
		if(temp.isPresent()) {
			temp.get().setCardnumber(creditcard.getCardnumber());
			temp.get().setCardtype(creditcard.getCardtype());
			temp.get().setExpmonth(creditcard.getExpmonth());
			temp.get().setExpyear(creditcard.getExpyear());
			temp.get().setModifieddate(LocalDate.now());
			
			creditcardRepo.save(temp.get());
		}
	}
	
	@Override
	public Optional<Creditcard> findCreditCard(int id) {
		return creditcardRepo.findById(id);
	}

	@Override
	public Iterable<Creditcard> findAll() {
		return creditcardRepo.findAll();
	}


	

}
