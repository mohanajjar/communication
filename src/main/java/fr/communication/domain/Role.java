package fr.communication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;


@Entity
@Table(name = "operation")
@Data
public class Role implements Serializable{

        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
        @SequenceGenerator(name = "sequenceGenerator")
        private Long id;

        @JsonIgnore
        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        public Role() {
        }

}
