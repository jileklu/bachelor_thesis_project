package com.jileklu2.bakalarska_prace_app.builders.scriptBuilders;

import com.jileklu2.bakalarska_prace_app.exceptions.builders.scriptBuilders.BlankScriptNameStringException;
import com.jileklu2.bakalarska_prace_app.exceptions.strings.BlankStringException;

public class JavascriptBuilder {
    public static String createScriptString(String scriptName, Object... args) throws BlankScriptNameStringException {
        if(scriptName == null)
            throw new NullPointerException("Arguments can't be null.");

        if(scriptName.isBlank())
            throw new BlankScriptNameStringException("Script name can't be left blank.");

        StringBuilder finalScriptString = new StringBuilder();
        finalScriptString.append(scriptName);
        finalScriptString.append("(");

        for(Object arg : args) {
            finalScriptString.append(arg.toString());
        }

        finalScriptString.append(")");
        return finalScriptString.toString();
    }
}
