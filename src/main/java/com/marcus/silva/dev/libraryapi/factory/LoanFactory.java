package com.marcus.silva.dev.libraryapi.factory;

import com.marcus.silva.dev.libraryapi.dto.request.LoanSaveForm;
import com.marcus.silva.dev.libraryapi.model.entities.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanFactory {

    public Loan convertSaveFormToEntity(LoanSaveForm loanSaveForm){
        return new Loan(loanSaveForm.getDescription(), loanSaveForm.getNamePerson());
    }
}
