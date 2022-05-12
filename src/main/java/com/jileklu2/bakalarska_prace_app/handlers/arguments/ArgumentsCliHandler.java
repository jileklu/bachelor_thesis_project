package com.jileklu2.bakalarska_prace_app.handlers.arguments;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.util.HashMap;

import static com.jileklu2.bakalarska_prace_app.handlers.arguments.ArgumentType.*;

/**
 *
 */
public class ArgumentsCliHandler {
    HashMap<ArgumentType, Object> argValues;

    /**
     *
     * @param args
     */
    public ArgumentsCliHandler(String[] args) {
        if(args.length > 3 || args.length == 1)
            throw new IllegalArgumentException("Wrong number of arguments.");

        this.argValues = new HashMap<>();

        if(args.length == 0) {
            argValues.put(MENU_ON,"");
        }
        else if(args.length == 3) {
            processOptArg(args[0]);
            processInFileArg(args[1]);
            processOutFileArg(args[2]);
        } else {
            processInFileArg(args[0]);
            processOutFileArg(args[1]);
        }

    }

    /**
     *
     * @param arg
     */
    private void processOptArg(String arg) {
        if(arg.charAt(0) != '-') {
            argValues.put(UNKNOWN, arg);
            throw new IllegalArgumentException("Unknown argument type.");
        }

        for(int i = 1; i < arg.length(); i++) {
            switch (arg.charAt(i)){
                case('m'):
                    argValues.put(MENU_ON, arg);
                    break;
                default:
                    argValues.put(UNKNOWN, arg);
                    throw new IllegalArgumentException("Unknown argument type.");
            }
        }
    }

    /**
     *
     * @param arg
     */
    private void processInFileArg(String arg) {
        if(arg.length() < 5) {
            throw new IllegalArgumentException("Invalid in-file path.");
        }

        try {
            argValues.put(IN_FILE, Paths.get(arg));
        } catch (InvalidPathException | NullPointerException ex) {
            throw new IllegalArgumentException("Invalid in-file path.");
        }

    }

    /**
     *
     * @param arg
     */
    private void processOutFileArg(String arg) {
        if(arg.length() < 5) {
            throw new IllegalArgumentException("Invalid out-file path.");
        }

        try {
            argValues.put(OUT_FILE, Paths.get(arg));
        } catch (InvalidPathException | NullPointerException ex) {
            throw new IllegalArgumentException("Invalid out-file path.");
        }

        if (argValues.get(IN_FILE).equals(OUT_FILE)) {
            throw new IllegalArgumentException("Out-file can not has same path as the in-file.");
        }
    }

    /**
     *
     * @return
     */
    public HashMap<ArgumentType, Object> getArgValues() {
        return argValues;
    }

    /**
     *
     * @param argType
     * @return
     * @throws InvalidKeyException
     */
    public Object getArgValue(ArgumentType argType) throws InvalidKeyException {
        if(!argValues.containsKey(argType))
            throw new InvalidKeyException("Argument was never given.");

        return argValues.get(argType);
    }
}
