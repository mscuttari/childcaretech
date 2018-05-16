package main.java.models;

public enum MenuType {

    ALTERNATIVEMENU("Menu alternativo"),
    REGULARMENU("Menu regolare");

    private String name;

    MenuType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }


    /**
     * Get string representation of menu type
     *
     * @param   menu      menu
     * @return  menu type
     */
    public static String getMenuType(Menu menu) {
        if (menu instanceof AlternativeMenu) {
            return ALTERNATIVEMENU.toString();
        } else if (menu instanceof RegularMenu) {
            return REGULARMENU.toString();
        }

        return null;
    }

}
