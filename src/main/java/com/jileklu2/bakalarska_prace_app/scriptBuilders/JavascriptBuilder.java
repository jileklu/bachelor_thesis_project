package com.jileklu2.bakalarska_prace_app.scriptBuilders;

public class JavascriptBuilder {
    public static String createScriptString(String scriptName, Object... args) {
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
