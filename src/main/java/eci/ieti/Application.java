package eci.ieti;

import eci.ieti.data.CustomerRepository;
import eci.ieti.data.ProductRepository;
import eci.ieti.data.TodoRepository;
import eci.ieti.data.UserRepository;
import eci.ieti.data.model.Customer;
import eci.ieti.data.model.Product;

import eci.ieti.data.model.Todo;
import eci.ieti.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

@SpringBootApplication
public class Application implements CommandLineRunner {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoOperations mongoOperation = (MongoOperations) applicationContext.getBean("mongoTemplate");

        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").is("Alice"));

        Customer customer = mongoOperation.findOne(query, Customer.class);
        System.out.println(customer);

        customerRepository.deleteAll();

        customerRepository.save(new Customer("Alice", "Smith"));
        customerRepository.save(new Customer("Bob", "Marley"));
        customerRepository.save(new Customer("Jimmy", "Page"));
        customerRepository.save(new Customer("Freddy", "Mercury"));
        customerRepository.save(new Customer("Michael", "Jackson"));

        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");

        customerRepository.findAll().stream().forEach(System.out::println);
        System.out.println();

        productRepository.deleteAll();

        productRepository.save(new Product(1L, "Samsung S8", "All new mobile phone Samsung S8"));
        productRepository.save(new Product(2L, "Samsung S8 plus", "All new mobile phone Samsung S8 plus"));
        productRepository.save(new Product(3L, "Samsung S9", "All new mobile phone Samsung S9"));
        productRepository.save(new Product(4L, "Samsung S9 plus", "All new mobile phone Samsung S9 plus"));
        productRepository.save(new Product(5L, "Samsung S10", "All new mobile phone Samsung S10"));
        productRepository.save(new Product(6L, "Samsung S10 plus", "All new mobile phone Samsung S10 plus"));
        productRepository.save(new Product(7L, "Samsung S20", "All new mobile phone Samsung S20"));
        productRepository.save(new Product(8L, "Samsung S20 plus", "All new mobile phone Samsung S20 plus"));
        productRepository.save(new Product(9L, "Samsung S20 ultra", "All new mobile phone Samsung S20 ultra"));

        System.out.println("Paginated search of products by criteria:");
        System.out.println("-------------------------------");

        productRepository.findByDescriptionContaining("plus", PageRequest.of(0, 2)).stream()
                .forEach(System.out::println);

        System.out.println();
        System.out.println("Creando Usuarios");

        userRepository.deleteAll();

        userRepository.save(new User(1L, "Cesar Villamil", "cesar@hotmail.com"));
        userRepository.save(new User(2L, "Juan villamil", "juanV@gmail.com"));
        userRepository.save(new User(3L, "Andres Rocha", "andres@gmail.com"));
        userRepository.save(new User(4L, "Norma Ramos", "normaR@hotmail.com"));


        userRepository.findAll().stream()
                .forEach(System.out::println);


        System.out.println("Creando Todo");

        todoRepository.deleteAll();


        todoRepository.save(new Todo(1L, "Prueba 1", 1, new Date(), "cesar@hotmail.com", "pending"));
        todoRepository.save(new Todo(2L, "Prueba 2", 1, new Date(), "andres@gmail.co", "ready"));
        todoRepository.save(new Todo(3L, "Prueba 3", 1, new Date(), "andres@gmail.co", "ready"));
        todoRepository.save(new Todo(4L, "Prueba 4", 1, new Date(), "juan@gmail.com", "pending"));

        todoRepository.findByResponsible("juan@gmail.com", PageRequest.of(0, 2)).stream()
                .forEach(System.out::println);


    }
}

