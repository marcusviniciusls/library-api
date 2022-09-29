package com.marcus.silva.dev.libraryapi.factory;

import com.marcus.silva.dev.libraryapi.dto.request.LoanSaveForm;
import com.marcus.silva.dev.libraryapi.dto.response.LoanResponse;
import com.marcus.silva.dev.libraryapi.model.entities.Loan;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoanFactory {

    @Autowired private ModelMapper modelMapper;

    public Loan convertSaveFormToEntity(LoanSaveForm loanSaveForm){
        return modelMapper.map(loanSaveForm, Loan.class);
    }

    public LoanResponse convertEntityInResponse(Loan loan){
        return modelMapper.map(loan, LoanResponse.class);
    }
}
