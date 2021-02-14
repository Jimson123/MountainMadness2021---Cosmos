package ca.cmpt276.cosmos.classes;

public class Book {
    public String[] pages;

    public Book() {
        this.pages = new String[2];

        this.pages[0] = "{\n" + // multi-line strings are not in this version of Java. Insane workaround. Do not type manually. Put a ", then paste after it
                "  \"spaceship\": {\n" +
                "    \"x\": -70.0,\n" +
                "    \"y\": -20.0,\n" +
                "    \"rotation\": 0.0,\n" +
                "    \"radius\": 10.0,\n" +
                "    \"speed\": 0.0,\n" +
                "    \"velX\": 0.0,\n" +
                "    \"velY\": 0.0\n" +
                "  },\n" +
                "  \"asteroids\": [\n" +
                "  ],\n" +
                "  \"planets\": [\n" +
                "    {\n" +
                "      \"name\": \"moon\",\n" +
                "      \"x\": 30.0,\n" +
                "      \"y\": -200.0,\n" +
                "      \"radius\": 20.0,\n" +
                "      \"mass\": 0.0,\n" +
                "      \"gravityRadius\": 0.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"earth\",\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": 0.0,\n" +
                "      \"radius\": 50.0,\n" +
                "      \"mass\": 0.0,\n" +
                "      \"gravityRadius\": 0.0\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        this.pages[1] = "";
    }

    public String getPage(int index) {
        return pages[index];
    }

}
