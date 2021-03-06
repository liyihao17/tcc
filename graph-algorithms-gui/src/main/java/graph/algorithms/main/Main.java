package graph.algorithms.main;

import graph.algorithms.gui.ControlPanel;
import graph.algorithms.gui.ExecutionBatchControl;
import graph.algorithms.gui.ExecutionChartPanel;
import graph.algorithms.gui.ExecutionList;
import graph.algorithms.gui.ExecutionPanel;
import graph.algorithms.gui.MainFrame;
import graph.algorithms.gui.TaskList;
import graph.algorithms.gui.TaskPanel;
import graph.algorithms.task.EadesKobylanskiTask;
import graph.algorithms.task.EadesTask;
import graph.algorithms.task.GraphInput;
import graph.algorithms.task.KobylanskiTask;
import graph.algorithms.task.SaabTask;
import graph.algorithms.task.Task;
import graph.algorithms.task.execution.ExecutionBatchFactory;
import graph.algorithms.task.execution.ExecutionLoader;
import graph.algorithms.task.execution.ExecutionSignal;
import graph.algorithms.task.execution.Executor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.Startable;
import org.picocontainer.behaviors.Caching;

public class Main implements Startable {

    private TaskList taskList;
    private static DefaultPicoContainer container;

    public Main(TaskList taskList) {
        this.taskList = taskList;
    }

    public static void main(String[] args) {
        setNimbusLookAndFeel();
        setupMiniframe();
        startContainer();
    }

    private static void setupMiniframe() {
        MiniFrame miniFrame = new MiniFrame();
        miniFrame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                startContainer();
            }
        });
    }

    private static void dispose() {
        container.dispose();
    }

    private static void startContainer() {
        container = createContainer();
        container.start();
    }

    private static DefaultPicoContainer createContainer() {
        final DefaultPicoContainer container = new DefaultPicoContainer(new Caching());
        container.addComponent(Main.class);
        container.addComponent(MainFrame.class);
        container.addComponent(TaskPanel.class);
        container.addComponent(TaskList.class);
        container.addComponent(ExecutionPanel.class);
        container.addComponent(ExecutionList.class);
        container.addComponent(ControlPanel.class);
        container.addComponent(ExecutionBatchFactory.class);
        container.addComponent(ExecutionBatchControl.class);
        container.addComponent(ExecutionSignal.class);
        container.addComponent(Executor.class);
        container.addComponent(ExecutionLoader.class);
        container.addComponent(ExecutionChartPanel.class);
        container.addComponent(ChartConverter.class);

        return container;
    }

    public static void setNimbusLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void start() {
        List<Task<GraphInput>> tasks = new ArrayList<>();
        tasks.add(new EadesTask());
        tasks.add(new KobylanskiTask());
        tasks.add(new SaabTask());
        tasks.add(new EadesKobylanskiTask());
        taskList.setListData(tasks.toArray(new Task[0]));
    }

    @Override
    public void stop() {
    }
}
