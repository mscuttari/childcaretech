package main.java.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity(name = "Parent")
@DiscriminatorValue("parent")
public final class Parent extends Person {

}
