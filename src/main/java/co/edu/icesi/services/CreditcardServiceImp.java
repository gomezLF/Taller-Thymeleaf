package co.edu.icesi.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import co.edu.icesi.dao.CreditcardDaoImp;
import org.springframework.stereotype.Service;

import co.edu.icesi.model.sales.Creditcard;
import co.edu.icesi.repositories.CreditcardRepo;

@Service
public class CreditcardServiceImp implements CreditcardService {
	
	CreditcardDaoImp creditcardDaoImp;
	
	
	public CreditcardServiceImp(CreditcardDaoImp creditcardDaoImp) {
		this.creditcardDaoImp = creditcardDaoImp;
	}

	
	@Override
	public void saveCreditCard(Creditcard creditcard) {
		creditcardDaoImp.save(creditcard);
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

			creditcardDaoImp.update(temp.get());
		}
	}
	
	@Override
	public Optional<Creditcard> findCreditCard(int id) {
		return Optional.ofNullable(creditcardDaoImp.findById(id));
	}

	@Override
	public Iterable<Creditcard> findAll() {
		return creditcardDaoImp.findAll();
	}

	public void deleteCreditcard(Creditcard creditcard){
		creditcardDaoImp.delete(creditcard);
	}

	public List<Creditcard> findAllByBussinesentityId(long bussinesentityId){
		return creditcardDaoImp.findAllByBussinesentityId(bussinesentityId);
	}

	public List<Creditcard> findAllByExpmonth(Integer expmonth){
		return creditcardDaoImp.findAllByExpmonth(expmonth);
	}

	public List<Creditcard> findAllByExpyear(Integer expyear){
		return creditcardDaoImp.findAllByExpyear(expyear);
	}
	

}
