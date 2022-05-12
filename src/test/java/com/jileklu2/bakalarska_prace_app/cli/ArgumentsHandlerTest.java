package com.jileklu2.bakalarska_prace_app.cli;

import com.jileklu2.bakalarska_prace_app.handlers.arguments.ArgumentType;
import com.jileklu2.bakalarska_prace_app.handlers.arguments.ArgumentsCliHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Objects;

public class ArgumentsHandlerTest {

    private final String[] testArgs01 = {"-m", "in\\in_file.json", "out\\out_file.json"};
    private final String[] testArgs02 = {"-f", "in\\in_file.json", "out\\out_file.json"};
    private final String[] testArgs03 = {"test"};
    private final String[] testArgs04 = {"in\\in_file.json", "out\\out_file.json"};
    private final String[] testArgs05 = {"test", "out\\out_file.json"};

    @Test
    public void baseConstructorTest() {
        ArgumentsCliHandler testHandler = new ArgumentsCliHandler(testArgs01);
        longArgsCheck(testHandler, testArgs01);

        testHandler = new ArgumentsCliHandler(testArgs04);
        shortArgsCheck(testHandler, testArgs04);
    }

    private void longArgsCheck(ArgumentsCliHandler testHandler, String[] testArgs) {
        HashMap<ArgumentType, Object> argValues = testHandler.getArgValues();
        for(ArgumentType key : argValues.keySet()) {
            switch (key) {
                case IN_FILE:
                    Assertions.assertEquals(testArgs[1], argValues.get(key).toString());
                    break;
                case MENU_ON:
                    Assertions.assertEquals(testArgs[0], argValues.get(key).toString());
                    break;
                case OUT_FILE:
                    Assertions.assertEquals(testArgs[2], argValues.get(key).toString());
                    break;
                case UNKNOWN:
                    Assertions.fail();
            }
        }
    }

    private void shortArgsCheck(ArgumentsCliHandler testHandler, String[] testArgs) {
        HashMap<ArgumentType, Object> argValues = testHandler.getArgValues();
        for(ArgumentType key : argValues.keySet()) {
            switch (key) {
                case IN_FILE:
                    Assertions.assertEquals(testArgs[0], argValues.get(key).toString());
                    break;
                case OUT_FILE:
                    Assertions.assertEquals(testArgs[1], argValues.get(key).toString());
                    break;
                case UNKNOWN:
                    Assertions.fail();
            }
        }
    }

    @Test
    public void baseConstructorExceptionsTest() {
        try {
            ArgumentsCliHandler testHandler = new ArgumentsCliHandler(testArgs02);
            longArgsCheck(testHandler, testArgs02);
            Assertions.fail("No exception was thrown");
        }
        catch (IllegalArgumentException e) {
            if(!Objects.equals(e.getMessage(), "Unknown argument type."))
                Assertions.fail("Wrong exception message.");
        }

        try {
            ArgumentsCliHandler testHandler = new ArgumentsCliHandler(testArgs03);
            longArgsCheck(testHandler, testArgs03);
            Assertions.fail("No exception was thrown");
        }
        catch (IllegalArgumentException e) {
            if(!Objects.equals(e.getMessage(), "Wrong number of arguments."))
                Assertions.fail("Wrong exception message.");
        }

        try {
            ArgumentsCliHandler testHandler = new ArgumentsCliHandler(testArgs05);
            longArgsCheck(testHandler, testArgs05);
            Assertions.fail("No exception was thrown");
        }
        catch (IllegalArgumentException e) {
            if(!Objects.equals(e.getMessage(), "Invalid in-file path."))
                Assertions.fail("Wrong exception message.");
        }
    }
}
