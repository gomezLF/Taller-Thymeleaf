package co.edu.icesi.services;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.icesi.exception.LogicalException;
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
	public void editCreditCard(Creditcard creditcard) throws LogicalException {
		Optional<Creditcard> temp = findCreditCard(creditcard.getCreditcardid());
		
		if(temp.isPresent()) {
			temp.get().setCardnumber(creditcard.getCardnumber());
			temp.get().setCardtype(creditcard.getCardtype());
			temp.get().setExpmonth(creditcard.getExpmonth());
			temp.get().setExpyear(creditcard.getExpyear());
			temp.get().setModifieddate(LocalDate.now());
			
			validateConstraints(temp.get());
			creditcardRepo.save(temp.get());
		}
	}
	
	@Override
	public Optional<Creditcard> findCreditCard(int id) {
		return creditcardRepo.findById(id);
	}
	
	private void validateConstraints(Creditcard creditcard) throws LogicalException{
		if(creditcard.getCardnumber().length() != 12){
			throw new LogicalException("El número carácteres de la tarjeta de crédito es invalido");
			
		}else if(creditcard.getExpyear() < Year.now(ZoneId.of("America/Bogota")).getValue()){
			throw new LogicalException("La fecha de expiración de la tarjeta de crédito es menor a la fecha actual");
			
		}else if(creditcard.getExpyear() == Year.now(ZoneId.of("America/Bogota")).getValue()) {
			if(creditcard.getExpmonth() < YearMonth.now(ZoneId.of("America/Bogota")).getMonthValue()) {
				throw new LogicalException("La fecha de expiración de la tarjeta de crédito es menor a la fecha actual");
			}
		}
	}


	@Override
	public Iterable<Creditcard> findAll() {
		return creditcardRepo.findAll();
	}


	

}
