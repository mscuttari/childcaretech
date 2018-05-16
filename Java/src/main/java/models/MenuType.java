package main.java.models;

public enum MenuType {

    ALTERNATIVE_MENU("Menù alternativo"),
    REGULAR_MENU("Menù regolare");

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
            return ALTERNATIVE_MENU.toString();
        } else if (menu instanceof RegularMenu) {
            return REGULAR_MENU.toString();
        }

        return null;
    }

}
