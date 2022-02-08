package com.jpa.nicoardizzolidev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

//significa que podemos embeber esta clase en otra entity. Es para hacer una clave compuesta que sean PK y FK
@Embeddable

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class EnrolmentId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "course_id")
    private Long courseId;
}
