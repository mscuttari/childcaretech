package main.java.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity(name = "Pediatrist")
@DiscriminatorValue("pediatrist")
public final class Pediatrist extends Person {

}
