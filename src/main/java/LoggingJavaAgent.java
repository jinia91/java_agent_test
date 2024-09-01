import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

public class LoggingJavaAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
                .type(ElementMatchers.any())
                .transform((builder, typeDescription, classLoader, module, protectionDomain) ->
                        builder.visit(Advice.to(LoggingJavaAgent.LoggingAdvice.class).on(ElementMatchers.any()))
                ).installOn(inst);
    }

    public static class LoggingAdvice {
        @Advice.OnMethodEnter
        public static void onEnter(@Advice.Origin String method) {
            System.out.println("Entering method: " + method);
        }

        @Advice.OnMethodExit
        public static void onExit(@Advice.Origin String method) {
            System.out.println("Exiting method: " + method);
        }
    }
}