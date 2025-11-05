package dev.lkdcode


import org.gradle.api.Project
import org.yaml.snakeyaml.Yaml

class YmlService {
    private static final SPRING = "spring"
    private static final SPRING_PROFILE_ACTIVE = "spring.profiles.active"
    private static final FLYWAY = "flyway"
    private static final URL = "url"
    private static final DRIVER = "driver-class-name"
    private static final USER = "user"
    private static final PASSWORD = "password"
    private static final SCHEMA = "schemas"
    private static final LOCATIONS = "locations"

    private final Project project

    YmlService(Project project) {
        this.project = project
    }

    String getFlywayDriver() {
        return String.valueOf(navigate(loadYml(), SPRING, FLYWAY, DRIVER))
    }

    String getFlywayUrl() {
        return String.valueOf(navigate(loadYml(), SPRING, FLYWAY, URL))
    }

    String getFlywayUser() {
        return String.valueOf(navigate(loadYml(), SPRING, FLYWAY, USER))
    }

    String getFlywayPassword() {
        return String.valueOf(navigate(loadYml(), SPRING, FLYWAY, PASSWORD))
    }

    String getFlywaySchema() {
        return String.valueOf(navigate(loadYml(), SPRING, FLYWAY, SCHEMA))
    }

    List<String> getFlywayLocations() {
        return navigate(loadYml(), SPRING, FLYWAY, LOCATIONS) as List<String>
    }

    private static Object navigate(final Map yml, final Object... keys) {
        def current = yml
        for (key in keys) {
            if (!(current instanceof Map)) return null
            current = current[key as String]
            if (current == null) return null
        }
        return current
    }

    private Map loadYml() {
        def ymlFile = project.file("src/main/resources/application-${getActiveProfile()}.yml")

        if (!ymlFile.exists()) {
            throw new FileNotFoundException("YAML file not found: ${ymlFile.path}")
        }

        return new Yaml().load(ymlFile.text) as Map ?: [:]
    }

    private String getActiveProfile() {
        return (project.findProperty(SPRING_PROFILE_ACTIVE) as String) ?: 'local'
    }
}