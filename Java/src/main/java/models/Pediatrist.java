package main.java.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "Pediatrist")
@DiscriminatorValue("pediatrist")
public final class Pediatrist extends Person {

}
