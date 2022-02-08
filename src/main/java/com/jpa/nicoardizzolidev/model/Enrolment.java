package com.jpa.nicoardizzolidev.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

//esta tabla intermedia la creamos despues de hacer el mapeo con JPA, le hicimos la clave compuesta enrolmentID las cuales son pk y fk a la vez, y
//mapeamos el student y el course, donde muchos enrolment tienen 1 student y 1 course.
@Entity(name = "Enrolment")
@Table(name = "enrolment")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Enrolment {

    @EmbeddedId
    private EnrolmentId enrolmentId;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "enrolment_student_id_fk"))
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "enrolment_course_id_fk"))
    private Course course;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIMEZONE")
    private LocalDateTime createdAt;

    public Enrolment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }
}
