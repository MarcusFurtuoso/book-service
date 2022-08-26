package br.com.microsservice.bookservice.repository;

import br.com.microsservice.bookservice.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    
}
