package com.jpa.nicoardizzolidev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "StudentIdCard")
@Table(name = "student_id_card", uniqueConstraints = {
        //esto es el equivalente a ponerle el unique en la @Column, nada mas que aca podemos elegir el nombre de la constraint
        @UniqueConstraint(name = "studentid_card_number_unique", columnNames = "card_number")
})
public class StudentIdCard {

    @Id //big serial datatype in a databse is always a sequence
    @SequenceGenerator(name = "student_id_card_sequence", sequenceName = "student_id_card_sequence", allocationSize = 1)
    //allocationSize es el incremento de la secuencia de cuanto en cuanto.
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_card_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "card_number", nullable = false, length = 15)
    private String cardNumber;

    //FETCHTYPE ES EAGER O LAZY (osea carga el objeto studentidcard. el objeto student no se carga si se pone el fetch en lazy, en eager si)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "student_id_card_student_id_fk"))
    private Student student;


    public StudentIdCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public StudentIdCard(String cardNumber, Student student) {
        this.cardNumber = cardNumber;
        this.student = student;
    }

}
