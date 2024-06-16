package ui.simulator;

import ui.simulator.DemoEnvironment;

public class Demo {
    public static void main(String[] args) {
        DemoEnvironment demoEnvironment;
        if ("inconsistent".equals(args[0])) {
            demoEnvironment = new DemoEnvironment(false);
        } else {
            demoEnvironment = new DemoEnvironment(true);
        }
        demoEnvironment.startDemo();
    }
}
