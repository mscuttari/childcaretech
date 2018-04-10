package main.java.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "Child")
@DiscriminatorValue("child")
public final class Child extends Person {

}
