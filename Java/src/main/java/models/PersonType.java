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
     * Get string representation of person type
     *
     * @param   person      person
     * @return  person type
     */
    public static String getPersonType(Person person) {
        if (person instanceof Child) {
            return CHILD.toString();
        } else if (person instanceof Contact) {
            return CONTACT.toString();
        } else if (person instanceof Parent) {
            return PARENT.toString();
        } else if (person instanceof Pediatrist) {
            return PEDIATRIST.toString();
        } else if (person instanceof Staff) {
            return STAFF.toString();
        }

        return null;
    }

}
