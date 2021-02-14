package ca.cmpt276.cosmos.classes;

public class Book {
    public String[] pages;

    public Book() {
        this.pages = new String[5];

        // multi-line strings are not in this version of Java. Insane workaround. Do not type manually. Put a ", then paste after it

        this.pages[0] = "{\n" +
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

        this.pages[1] = "{\n" +
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
                "    {\n" +
                "      \"name\": \"asteroid5\",\n" +
                "      \"orbit\": {\n" +
                "        \"x\": 0.0,\n" +
                "        \"y\": 0.0,\n" +
                "        \"radius\": 100.0,\n" +
                "        \"period\": 10.0,\n" +
                "        \"epoch\": 0.0\n" +
                "      },\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": 0.0,\n" +
                "      \"radius\": 10.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"asteroid6\",\n" +
                "      \"orbit\": {\n" +
                "        \"x\": 0.0,\n" +
                "        \"y\": 0.0,\n" +
                "        \"radius\": 100.0,\n" +
                "        \"period\": 10.0,\n" +
                "        \"epoch\": 2.5\n" +
                "      },\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": 0.0,\n" +
                "      \"radius\": 10.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"asteroid7\",\n" +
                "      \"orbit\": {\n" +
                "        \"x\": 0.0,\n" +
                "        \"y\": 0.0,\n" +
                "        \"radius\": 100.0,\n" +
                "        \"period\": 10.0,\n" +
                "        \"epoch\": 5.0\n" +
                "      },\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": 0.0,\n" +
                "      \"radius\": 10.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"asteroid8\",\n" +
                "      \"orbit\": {\n" +
                "        \"x\": 0.0,\n" +
                "        \"y\": 0.0,\n" +
                "        \"radius\": 100.0,\n" +
                "        \"period\": 10.0,\n" +
                "        \"epoch\": 7.5\n" +
                "      },\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": 0.0,\n" +
                "      \"radius\": 10.0\n" +
                "    }\n" +
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

        this.pages[2] = "{\n" +
                "  \"spaceship\": {\n" +
                "    \"x\": 0.0,\n" +
                "    \"y\": -65.0,\n" +
                "    \"rotation\": 0.0,\n" +
                "    \"radius\": 10.0,\n" +
                "    \"speed\": 0.0,\n" +
                "    \"velX\": 0.0,\n" +
                "    \"velY\": 0.0\n" +
                "  },\n" +
                "  \"asteroids\": [\n" +
                "    {\n" +
                "      \"name\": \"asteroid1\",\n" +
                "      \"orbit\": {\n" +
                "        \"x\": 0.0,\n" +
                "        \"y\": -250.0,\n" +
                "        \"radius\": 60.0,\n" +
                "        \"period\": 5.0,\n" +
                "        \"epoch\": 0.0\n" +
                "      },\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": 0.0,\n" +
                "      \"radius\": 10.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"asteroid2\",\n" +
                "      \"orbit\": {\n" +
                "        \"x\": 0.0,\n" +
                "        \"y\": -250.0,\n" +
                "        \"radius\": 60.0,\n" +
                "        \"period\": 5.0,\n" +
                "        \"epoch\": 2.5\n" +
                "      },\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": 0.0,\n" +
                "      \"radius\": 10.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"asteroid3\",\n" +
                "      \"orbit\": {\n" +
                "        \"x\": 0.0,\n" +
                "        \"y\": -250.0,\n" +
                "        \"radius\": 90.0,\n" +
                "        \"period\": 7.5,\n" +
                "        \"epoch\": 0.0\n" +
                "      },\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": 0.0,\n" +
                "      \"radius\": 10.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"asteroid4\",\n" +
                "      \"orbit\": {\n" +
                "        \"x\": 0.0,\n" +
                "        \"y\": -250.0,\n" +
                "        \"radius\": 90.0,\n" +
                "        \"period\": 7.5,\n" +
                "        \"epoch\": 3.75\n" +
                "      },\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": 0.0,\n" +
                "      \"radius\": 10.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"asteroid5\",\n" +
                "      \"orbit\": {\n" +
                "        \"x\": 0.0,\n" +
                "        \"y\": -250.0,\n" +
                "        \"radius\": 120.0,\n" +
                "        \"period\": 10.0,\n" +
                "        \"epoch\": 0.0\n" +
                "      },\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": 0.0,\n" +
                "      \"radius\": 10.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"asteroid6\",\n" +
                "      \"orbit\": {\n" +
                "        \"x\": 0.0,\n" +
                "        \"y\": -250.0,\n" +
                "        \"radius\": 120.0,\n" +
                "        \"period\": 10.0,\n" +
                "        \"epoch\": 5.0\n" +
                "      },\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": 0.0,\n" +
                "      \"radius\": 10.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"asteroid7\",\n" +
                "      \"orbit\": {\n" +
                "        \"x\": 0.0,\n" +
                "        \"y\": -250.0,\n" +
                "        \"radius\": 150.0,\n" +
                "        \"period\": 12.5,\n" +
                "        \"epoch\": 0.0\n" +
                "      },\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": 0.0,\n" +
                "      \"radius\": 10.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"asteroid8\",\n" +
                "      \"orbit\": {\n" +
                "        \"x\": 0.0,\n" +
                "        \"y\": -250.0,\n" +
                "        \"radius\": 150.0,\n" +
                "        \"period\": 12.5,\n" +
                "        \"epoch\": 6.25\n" +
                "      },\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": 0.0,\n" +
                "      \"radius\": 10.0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"planets\": [\n" +
                "    {\n" +
                "      \"name\": \"moon\",\n" +
                "      \"x\": 0.0,\n" +
                "      \"y\": -250.0,\n" +
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

        this.pages[3] = "{\n" +
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
                "      \"x\": 80.0,\n" +
                "      \"y\": -500.0,\n" +
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
                "    },\n" +
                "    {\n" +
                "      \"name\": \"venus\",\n" +
                "      \"x\": -60.0,\n" +
                "      \"y\": -220.0,\n" +
                "      \"radius\": 40.0,\n" +
                "      \"mass\": 100.0,\n" +
                "      \"gravityRadius\": 120.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"mars\",\n" +
                "      \"x\": 140.0,\n" +
                "      \"y\": -350.0,\n" +
                "      \"radius\": 30.0,\n" +
                "      \"mass\": 75.0,\n" +
                "      \"gravityRadius\": 90.0\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        this.pages[4] = "{\n" +
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
                "      \"x\": 250.0,\n" +
                "      \"y\": -700.0,\n" +
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
                "    },\n" +
                "    {\n" +
                "      \"name\": \"sun\",\n" +
                "      \"x\": 125.0,\n" +
                "      \"y\": -350.0,\n" +
                "      \"radius\": 80.0,\n" +
                "      \"mass\": 1000.0,\n" +
                "      \"gravityRadius\": 275.0\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    public String getPage(int index) {
        return pages[index];
    }

}
