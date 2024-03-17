import me.jameschan.config.Config;
import me.jameschan.config.ConfigStack;
import me.jameschan.config.IllegalKeyException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class ConfigTest {
    @Test
    public void testBasicFunctionality() {
        final Set<String> keySet = new HashSet<>(List.of("username", "age", "sex"));
        final var config = new Config(keySet) {{
            set("username", "James Chan");
            set("age", 25);
            set("sex", "male");
        }};

        assertEquals(config.get("username"), "James Chan");
        assertEquals(config.get("age"), 25);
        assertEquals(config.get("sex"), "male");

        // It should throw an IllegalKeyException if users try to set a key that is not allowed
        assertThrowsExactly(IllegalKeyException.class, () -> config.set("password", "123456"));

        // Test loading data
        config.load(new HashMap<>() {{
            put("age", 24);
            put("username", "Andrew Mo");
        }});

        assertEquals(config.get("username"), "Andrew Mo");
        assertEquals(config.get("age"), 24);
    }

    @Test
    public void testConfigStack() {
        final Set<String> keySet = new HashSet<>(List.of("username", "age", "sex"));
        final var config = new Config(keySet) {{
            set("username", "James Chan");
            set("age", 25);
            set("sex", "male");
        }};

        // Create a config stack
        final var configStack = new ConfigStack(config);

        // Test dynamic config
        final var dynamicConfig = configStack.getDynamic();
        assertEquals(dynamicConfig.get("username"), "James Chan");
        assertEquals(dynamicConfig.get("age"), 25);
        assertEquals(dynamicConfig.get("sex"), "male");

        // Create a new config
        final var USER_CONFIG_LEVEL = "USER";
        final var userConfig = configStack.createConfig(USER_CONFIG_LEVEL);
        userConfig.set("username", "Andrew Mo");
        userConfig.set("age", 24);

        // The dynamic config should not change at this moment
        assertEquals(dynamicConfig.get("username"), "James Chan");

        // Test get(level)
        assertEquals(configStack.get(USER_CONFIG_LEVEL).get("age"), 24);

        // Update the config stack, the value of the key "username" should be updated then
        configStack.update();
        assertEquals(dynamicConfig.get("username"), "Andrew Mo");

        // Load dynamic
        configStack.load(new HashMap<>() {{
            put("username", "Evan");
        }});
        assertEquals(dynamicConfig.get("username"), "Evan");
    }

    @Test
    public void testConfigStackLoad() {
        final Set<String> keySet = new HashSet<>(List.of("username", "age", "sex"));
        final var config = new Config(keySet) {{
            set("username", "James Chan");
            set("age", 25);
            set("sex", "male");
        }};

        // Create a config stack
        final var configStack = new ConfigStack(config);

        // Create a user config
        final var USER_CONFIG_LEVEL = "USER";
        configStack.createConfig(USER_CONFIG_LEVEL);

        // Load some data tot eh user config
        configStack.load(new HashMap<>() {{
            put("username", "Andrew Mo");
            put("age", 24);
        }}, USER_CONFIG_LEVEL);

        final var dynamicConfig = configStack.getDynamic();
        assertEquals(dynamicConfig.get("username"), "Andrew Mo");
        assertEquals(dynamicConfig.get("age"), 24);
    }
}
