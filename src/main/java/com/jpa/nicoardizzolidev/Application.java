package com.jpa.nicoardizzolidev;

import com.github.javafaker.Faker;
import com.jpa.nicoardizzolidev.model.*;
import com.jpa.nicoardizzolidev.repo.CourseRepository;
import com.jpa.nicoardizzolidev.repo.StudentIdCardRepository;
import com.jpa.nicoardizzolidev.repo.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository, StudentIdCardRepository studentIdCardRepository, CourseRepository courseRepository) {
        return args -> {

            Optional<Student> hartmann = repository.findById(23L);

            System.out.println(hartmann);
            hartmann.ifPresent(student -> {
                System.out.println("fetch books");
                List<Book> books = student.getBooks();
                books.forEach(book -> System.out.println(book.getBookName()));

                Optional<Course> course = courseRepository.findById(1L);

//                student.addEnrolment(new Enrolment(new EnrolmentId(student.getId(), 3L),student, course.get(),LocalDateTime.now()));

//                student.addCourse(new Course("Mathematics", "Exactas"));
//                student.addCourse(new Course("Computer science", "IT"));
//                repository.saveAndFlush(student);
            });






//            Book book1 = new Book("Nombre libro 1", LocalDateTime.now());
//            Book book2 = new Book("Nombre libro 1", LocalDateTime.now());
//            Book book3 = new Book("Nombre libro 1", LocalDateTime.now());
//
//            hartmann.addBook(book1);
//            hartmann.addBook(book2);
//            hartmann.addBook(book3);
//
//            repository.saveAndFlush(hartmann);

        };

    }


    // hacer consulta con paginacion.
    private void usarPaginacion(StudentRepository repository) {
        //la pagina 0 es la primer pagina, si queremos la pagina 2 (que seria la 1) cambiamos el pageRequest y le ponemos en page: 1 y listo)
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "firstName"));
        Page<Student> pageOfStudents = repository.findAll(pageRequest);
        System.out.println(pageOfStudents);
        pageOfStudents.forEach(student -> System.out.println(student.getFirstName()));
    }

    //consulta con orden (seria como un order by en sql), se pueden combinar con and
    private void sorting(StudentRepository repository) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName").and(Sort.by("age").descending());
        repository.findAll(sort).forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge()));
    }

    //USING FAKER TO ADD DATA TO DATABASE
    private void generateRandomDataUsingFaker(StudentRepository repository) {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);
            int age = faker.number().numberBetween(15, 70);
            Student student = new Student(firstName, lastName, email, age);
            repository.save(student);
        }
    }
}
