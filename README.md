# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

## My Sequence Diagram
https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWOZVYSgM536HHCkARYGGABBECE5cAJsOAAjYBxQxp8zJgDmUCAFdsMAMRpgVAJ4wASik1IOYKMKQQ0RgO4ALJGBSZEqUgBaAD4WakoALhgAbQAFAHkyABUAXRgAel1lKAAdNABvLMoTAFsUABoYXA4ON2hpSpQS4CQEAF9MCnDYELC2SQ4oqBs7HygACiKoUoqqpVr6xubWgEpO1nYOGF6hEXEBwZhNFDAAVWzJ7Jm13bEJKW3QtSiyAFEAGVe4JJgpmZgAGY6Eq-bKYW77B6BXpdfqcKJoXQIBDraibR4wCH3GpREDDYQoc6US7FYBlSrVBZQaQ3OSQmoY54wACSADk3pYfn8ybNKXVqUsWggWaykvFQbRYeidnTsYc8SgCaJdGAPCTpjzaXs5Yz5FE2RyuVceZVgCqPEkIABrdAisUwM2q8Gyg7bGEbAZRR0W63oVFwrbQ0KwyIO82Wm1of2UN2hfzoMBRABMAAYU3l8t6I+gOuhpBptHoDIZoPxjjB3hBbK5DJ5vL540Fg6xQ3FEqkMsoas40BnueS5jV+dIOiGes20Z6YAgq0g0OqZhT5sO1lKBhisQcosczhd+7zl-UVjBndrXb0mW9Pt8JRqB3z6oDgbfT3dz+7J-Db4vB1T82vOEeTcpFxfEfGVVUF01V96UDJ49RFQ1vxNMNVWzVw2Xtb0YJ1IM+k2L1w19KMAMDD8aCgQi0OI6Mel6RtExgVN0wKLNiNzNB8y0HR9CMbQUFtStND0Zhay8Hw-GQBMMTHKJogED5XiSV40nSLsOB7PI2MjWiZI9L8Z2ElVxm09BV30uDMRdECjhOCC1VMtAtTfKF4JeRSb0cp8IBBbDgIZPDSIRJEUVIjdrJxVQUAQE4UHskyiMjZzYN1KIFM+ZTUJ9SNvN880cPfCcA2C5F-WlcjQ0RUqx1jeApIwCJmMwPMCx44thmkCtRhgABxHktjE+tJICZgKsomIeqU1TNB5LTEr9GryII6dRgS6ikrK9cZTPGyd3ixzktwtzyA8n4vKBHysoKqElqnKrQos8Kdsi5B7D6soODW7KzOugLjqvL4fhmj7cqOHlfrIorluBzhNsAwKW3GmGuEWuN6sY5iM2RjiuMLXjDGwXQoGwGL4DAlR3pUQaJIYvTujkhJkmm2aTHm3sChh1keVHRG6eKmAFQJL7IEjSpOegsLtpcyK9vNL70MO89-tOrL0NBvyIsh-C7pCuHLP8+Vycp8ZxbKRXXNUBCDVeTkwbKLmBxh5kBDtcUYYh2qgrtlBnb1z3Ee3HlfcW+j0eTNMsaDgQcdaosjDMaKZzcGAACkIDnXqeSMeQEFAK1huksaGdODt0hhub1vQDMGLgCAZygMWo557o+eWgArdP50cyoa7r6BG7KZ3zM-fXNe3Oy5YOj2LwQgHPLZ9X8oN-2R5Kh6R6e6XDh3Y2nYEc2-st9zryBqPQfd5eEdXmB7r9qXYKiDu52NkB0dr+uB59-fp+O63bamB2KglACzfn3BueQ96u29tPW6X5b6SyLjfXWIc0YjUammZqnFY7420MACwiBFSwGANgEmhAnAuHcOJBs6M+atnSkpFSGQ1C6VgfKGKeBxjDwDJvB+IDCGiGRJwn+R9MSKUysAZEoMpjCKZPQ8RkiLp5SdJfY6cjXgOgUc+C+msV783gY9K+FE14sNDmgpqLUgA
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWOZVYSgM536HHCkARYGGABBECE5cAJsOAAjYBxQxp8zJgDmUCAFdsMAMRpgVAJ4wASik1IOYKMKQQ0RgO4ALJGBSZEqUgBaAD4WakoALhgAbQAFAHkyABUAXRgAel1lKAAdNABvLMoTAFsUABoYXA4ON2hpSpQS4CQEAF9MCnDYELC2SQ4oqBs7HygACiKoUoqqpVr6xubWgEpO1nYOGF6hEXEBwZhNFDAAVWzJ7Jm13bEJKW3QtSiyAFEAGVe4JJgpmZgAGY6Eq-bKYW77B6BXpdfqcKJoXQIBDraibR4wCH3GpREDDYQoc6US7FYBlSrVBZQaQ3OSQmoY54wACSADk3pYfn8ybNKXVqUsWggWaykvFQbRYeidnTsYc8SgCaJdGAPCTpjzaXs5Yz5FE2RyuVceZVgCqPEkIABrdAisUwM2q8Gyg7bGEbAZRR0W63oVFwrbQ0KwyIO82Wm1of2UN2hfzoMBRABMAAYU3l8t6I+gOuhpBptHoDIZoPxjjB3hBbK5DJ5vL540Fg6xQ3FEqkMsoas40BnueS5jV+dIOiGes20Z6YAgq0g0OqZhT5sO1lKBhisQcosczhd+7zl-UVjBndrXb0mW9Pt8JRqB3z6oDgbfT3dz+7J-Db4vB1T82vOEeTcpFxfEfGVVUF01V96UDJ49RFQ1vxNMNVWzVw2Xtb0YJ1IM+k2L1w19KMAMDD8aCgQi0OI6Mel6RtExgVN0wKLNiNzNB8y0HR9CMbQUFtStND0Zhay8Hw-GQBMMTHKJogED5XiSV40nSLsOB7PI2MjWiZI9L8Z2ElVxm09BV30uDMRdECjhOCC1VMtAtTfKF4JeRSb0cp8IBBbDgIZPDSIRJEUVIjdrJxVQUAQE4UHskyiMjZzYN1KIFM+ZTUJ9SNvN880cPfCcA2C5F-WlcjQ0RUqx1jeApIwCJmMwPMCx44thmkCtRhgABxHktjE+tJICZgKsomIeqU1TNB5LTEr9GryII6dRgS6ikrK9cZTPGyd3ixzktwtzyA8n4vKBHysoKqElqnKrQos8Kdsi5B7D6soODW7KzOugLjqvL4fhmj7cqOHlfrIorluBzhNsAwKW3GmGuEWuN6sY5iM2RjiuMLXjDGwXQoGwGL4DAlR3pUQaJIYvTujkhJkmm2aTHm3sChh1keVHRG6eKmAFQJL7IEjSpOegsLtpcyK9vNL70MO89-tOrL0NBvyIsh-C7pCuHLP8+Vycp8ZxbKRXXNUBCDVeTkwbKLmBxh5kBDtcUYYh2qgrtlBnb1z3Ee3HlfcW+j0eTNMsaDgQcdaosjDMaKZzcGAACkIDnXqeSMeQEFAK1huksaGdODt0hhub1vQDMGLgCAZygMWo557o+eWgArdP50cyoa7r6BG7KZ3zM-fXNe3Oy5YOj2LwQgHPLZ9X8oN-2R5Kh6R6e6XDh3Y2nYEc2-st9zryBqPQfd5eEdXmB7r9qXYKiDu52NkB0dr+uB59-fp+O63bamB2KglACzfn3BueQ96u29tPW6X5b6SyLjfXWIc0YjUammZqnFY7420MACwiBFSwGANgEmhAnAuHcOJBs6M+atnSkpFSGQ1C6VgfKGKeBxjDwDJvB+IDCGiGRJwn+R9MSKUysAZEoMpjCKZPQ8RkiLp5SdJfY6cjXgOgUc+C+msV783gY9K+FE14sNDmgpqLUgA