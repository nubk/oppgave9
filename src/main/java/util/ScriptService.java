package util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;
import org.jruby.embed.jsr223.JRubyEngineFactory;
import org.python.jsr223.PyScriptEngineFactory;

public class ScriptService {

    static {
        // javascript is already registered
        new ScriptEngineManager().registerEngineName("python", new PyScriptEngineFactory());
        new ScriptEngineManager().registerEngineName("ruby", new JRubyEngineFactory());
        new ScriptEngineManager().registerEngineName("groovy", new GroovyScriptEngineFactory());
    }

    public static String runScript(String language, String userCode) {
        try {
            // Create writers
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            StringWriter errorStringWriter = new StringWriter();
            PrintWriter errorPrintWriter = new PrintWriter(errorStringWriter);

            // Do engine work
            ScriptEngine engine = getEngine(language);
            engine.getContext().setErrorWriter(errorPrintWriter);
            engine.getContext().setWriter(printWriter);
            engine.eval(userCode);

            // Close writers
            printWriter.close();
            errorPrintWriter.close();
            stringWriter.close();
            errorStringWriter.close();

            // return result
            return stringWriter.toString();
        } catch (Throwable t) {
            return t.toString();
        }
    }

    public static String runScriptWithTest(String language, String userCode, Map<String, String> testCode) {
        try {
            ScriptEngine engine = getEngine(language);
            engine.eval(userCode);
            if ((boolean) engine.eval(testCode.get(language))) {
                return "Your solution is correct, good job!";
            }
            return "Your solution is not correct, try again.";
        } catch (Throwable t) {
            return "An error occurred while running your code: " + formatStackTrace(t);
        }
    }

    private static ScriptEngine getEngine(String language) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(language);
        engine.getContext().setWriter(new PrintWriter(new StringWriter())); // mute engine by default
        if ("javascript".equals(language)) { // add console.log to js
            engine.eval("var console = { log: print };");
        }
        return engine;
    }

    private static String formatStackTrace(Throwable t) {
        return Arrays.stream(t.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n"));
    }

}
