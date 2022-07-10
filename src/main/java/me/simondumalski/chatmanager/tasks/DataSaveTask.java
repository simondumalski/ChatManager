package me.simondumalski.chatmanager.tasks;

import me.simondumalski.chatmanager.Main;

public class DataSaveTask implements Runnable {

    private final Main plugin;

    /**
     * Constructor for the DataSaveTask
     * @param plugin Instance of the main plugin class
     */
    public DataSaveTask(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getDataConfig().saveData();
    }

}
