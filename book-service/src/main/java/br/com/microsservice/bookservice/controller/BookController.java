package br.com.microsservice.bookservice.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.microsservice.bookservice.model.Book;
import br.com.microsservice.bookservice.proxy.CambioProxy;
import br.com.microsservice.bookservice.repository.BookRepository;

@RestController
@RequestMapping("book-service")
public class BookController {

    @Autowired
    private Environment environment; 

    @Autowired
    private BookRepository repository; 

    @Autowired
    private CambioProxy proxy; 
    
    @GetMapping(value = "/{id}/{currency}")
    @ResponseStatus(HttpStatus.OK)
    public Book findyBook(@PathVariable ("id") Long id, @PathVariable("currency") String currency) {

        var book = repository.getReferenceById(id);
        if(book == null) throw new RuntimeException("Book not found!");

        var cambio = proxy.getCambio(book.getPrice(), "USD", currency);

        var port = environment.getProperty("local.server.port");
        book.setEnvironment(port + "FEINGN");
        book.setPrice(cambio.getConvertedValue());
        return book;
    }
}
