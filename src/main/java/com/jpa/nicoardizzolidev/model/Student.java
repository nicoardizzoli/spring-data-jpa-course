package com.jpa.nicoardizzolidev.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// lombok //
@Getter
@Setter
@NoArgsConstructor
// FIN lombok //

@Entity(name = "Student")
@Table(name = "student", uniqueConstraints = {
        //esto es el equivalente a ponerle el unique en la @Column, nada mas que aca podemos elegir el nombre de la constraint
        @UniqueConstraint(name = "student_email_unique", columnNames = "email")
})
public class Student {

    @Id //big serial datatype in a databse is always a sequence
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    //allocationSize es el incremento de la secuencia de cuanto en cuanto.
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT", unique = true)
    private String email;

    @Column(name = "age", nullable = false)
    private Integer age;

    //esto es para tener una referencia, y cuando se cargue el objeto student, se cargue tmb su idCard (hace bidireccional la relacion, ya que antes teniamos solo la relacion en la idCard)
    //OJO CON LOS METODOS TOSTRING DE LOS DOS, YA QUE SI SE LLAMA AL TOSTRING DE UNO Y ESE UNO TIENE EL TOSTRING EL OTRO ENTRA EN BUCLE Y PUM.
    //el orphanRemoval es para que borre la idCard del student, si se borra el student. sino no nos va a dejar borrar el student por la FK.
    @OneToOne(mappedBy = "student", fetch = FetchType.EAGER, orphanRemoval = true)
    private StudentIdCard studentIdCard;

    //la relacion ManyToOne era unidireccional si la poniamos en el book solamente, la hacemos bidireccional poniendo tambien aca.
    //hay que acordarse de hacer el metodo Add y remove el cual setea la bidireccionalidad.
    @OneToMany(mappedBy = "student", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();



//    ACORDARSE DE HACER LOS METODOS ADD Y REMOVE PARA LA BIDIRECCIONALIDAD. ESTO LO HIZO PARA QUE SE CREE LA TABLA, PERO DESPUES CREO LA TABLA INTERMEDIA Y HIZO LO Q SIGUE ABAJO
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "enrolment",
//            joinColumns = @JoinColumn(name = "student_id",
//                    foreignKey = @ForeignKey(name = "enrolment_student_id_fk")
//            ),
//            inverseJoinColumns = @JoinColumn(name = "course_id",
//                    foreignKey = @ForeignKey(name = "enrolment_course_id_fk"))
//    )
//    private List<Course> courses = new ArrayList<>();


//    public void addCourse(Course course) {
//      this.courses.add(course);
//      course.getStudents().add(this);
//    }
//
//    public void removeCourse(Course course){
//        if (this.courses.contains(course)) {
//            this.courses.remove(course);
//            course.getStudents().remove(this);
//        }
//    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<Enrolment> enrolments = new ArrayList<>();

    public void addEnrolment(Enrolment enrolment) {
        if (!this.enrolments.contains(enrolment)) {
            this.enrolments.add(enrolment);
        }
    }

    public void removeEnrolment(Enrolment enrolment) {
        this.enrolments.remove(enrolment);
    }




    public Student(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public void addBook(Book book) {
        if (!this.getBooks().contains(book)) {
            this.getBooks().add(book);
            book.setStudent(this);
        }
    }

    public void removeBook(Book book) {
        if (this.getBooks().contains(book)) {
            this.getBooks().remove(book);
            book.setStudent(null);
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
