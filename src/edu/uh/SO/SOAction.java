package edu.uh.SO;

import com.intellij.execution.ExecutionListener;
import com.intellij.execution.ExecutionManager;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.process.DefaultJavaProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.ExecutionConsole;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.icons.AllIcons;
import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.execution.console.ConsoleExecuteAction;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;
import sun.java2d.loops.ProcessPath;

import java.io.*;
import java.nio.charset.Charset;


public class SOAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        // TODO: insert action logic here
        ExecutionEnvironment ec = anActionEvent.getData(DataKeys.EXECUTION_ENVIRONMENT);
        RunProfile rp = anActionEvent.getData(DataKeys.RUN_PROFILE);
        IdeView iv = anActionEvent.getData(DataKeys.IDE_VIEW);
        ConsoleView  consoleView = anActionEvent.getData(DataKeys.CONSOLE_VIEW);
        RunProfile runProfile = ec.getRunProfile();
        Project project = anActionEvent.getProject();
        MessageBus messageBus = project.getMessageBus();

        PrintWriter f = null;
        try {
            f = new PrintWriter("/home/alipour/text.txt","UTF-8");
            f.print("Started in " + project.getBasePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
// or
// MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();
        MessageBusConnection connection = messageBus.connect();
        connection.subscribe(ExecutionManager.EXECUTION_TOPIC, new ExecutionListener() {
            @Override
            public void processStarted(@NotNull String executorId, @NotNull ExecutionEnvironment env, @NotNull ProcessHandler handler) {
                handler.startNotify();

            }

            @Override
            public void processTerminating(@NotNull String executorId, @NotNull ExecutionEnvironment env, @NotNull ProcessHandler handler) {
                handler.startNotify();
                consoleView.attachToProcess(handler);
                handler.addProcessListener(new ProcessAdapter() {
                    @Override
                    public void processTerminated(ProcessEvent event) {
                        System.out.println(event.getText());
                        super.processTerminated(event);
                    }
                });
            }
        });
//
//        ee.attachToProcess();
//        ee.addMessageFilter();
//        ec.getRunProfile().getState(null, null, null).execute(null).getExecutionConsole().
//
//
//
//
//                String command = "mvn.bat -f " + execPath + " -Dtest=" + psiClass.getName() + " test";
//        Process process = Runtime.getRuntime().exec(command);
//
//        ProcessPath.ProcessHandler processHandler = new DefaultJavaProcessHandler(process, null, Charset.defaultCharset());
//        processHandler.startNotify()
//
//
//
//        AllIcons.Welcome.Project project = e.getData(CommonDataKeys.PROJECT);
//        RunContentDescriptor descriptor = e.getData(LangDataKeys.RUN_CONTENT_DESCRIPTOR);
//        ExecutionEnvironment environment = e.getData(LangDataKeys.EXECUTION_ENVIRONMENT);
    }
}
