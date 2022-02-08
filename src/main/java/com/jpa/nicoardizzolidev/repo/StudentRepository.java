package com.jpa.nicoardizzolidev.repo;

import com.jpa.nicoardizzolidev.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    //SE PUEDEN USAR ESTOS JPQL SNIPETS (QUE YA ESTAN ESCRITOS Y PODEMOS VERLOS EN LA DOCUMENTACION O EN EL AUTOCOMPLETADO DE INTELLIJ)
    Optional<Student> findStudentByEmail(String email);
    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(String firstName, Integer age);

    // O PODEMOS ESCRIBIR NUESTRAS PROPIAS QUERYS CON JPQL.
    // NOTA IMPORTANTE: EL Student que esta dentro de la query, es el Name de la @Entity de la clase.
    //tambien aparte de jpql se puede poner SQL directo agregandole al final del nombre del metodo la palabra Native y en el parametro de la queri nativeQuery = true
    //AUNQUE NO SE RECOMIENDA DIRECTAMENTE USAR SQL YA QUE QUEDAS PEGADO A UNA BASE DE DATOS EN ESPECIFICO. SI CAMBIAS DE SQL A Mongo por ej no funciona mas, pero JPQL si.
    @Query("select s from Student s where s.email = ?1 and s.age = ?2")
    Optional<Student> findStudentByEmailAndAge(String email, Integer age);


    //NAMEDPARAMS: SE PUEDEN USAR NAMEDPARAMS TMB PARA GENRAR LAS QUERYS.
    @Query("select s from Student s where s.lastName = :lastName ")
    Optional<Student> findStudentByLastName(@Param("lastName") String lastName);

    //PARA PODER BORRAR ALGO, O MODIFICAR TENEMOS QUE PONER EL TAG @Modify PARA AVISARLE A SPRING QUE NO ESPERAMOS NADA Y TAMBIEN HAY QUE PONER EL TRANSACTIONAL
    @Transactional
    @Modifying
    @Query("DELETE FROM Student s WHERE s.id = ?1")
    void deleteStudentById(Long id);

}
