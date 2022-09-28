package com.marcus.silva.dev.libraryapi.model.repository;

import com.marcus.silva.dev.libraryapi.model.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
