package main.java.models;

public enum PersonType {

    CHILD("Bambino"),
    CONTACT("Contatto"),
    PARENT("Genitore"),
    PEDIATRIST("Pediatra"),
    STAFF("Staff");

    private String name;

    PersonType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }


    /**
     * Get the person type
     *
     * @param   person              person
     * @return  person type
     * @throws  RuntimeException    if the person is of an unknown type
     */
    public static PersonType getPersonType(Person person) throws RuntimeException {
        if (person instanceof Child) {
            return CHILD;
        } else if (person instanceof Contact) {
            return CONTACT;
        } else if (person instanceof Parent) {
            return PARENT;
        } else if (person instanceof Pediatrist) {
            return PEDIATRIST;
        } else if (person instanceof Staff) {
            return STAFF;
        }

        throw new RuntimeException("Invalid person type");
    }

}
