package main.java.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity(name = "Contact")
@DiscriminatorValue("contact")
public final class Contact extends Person {

}
