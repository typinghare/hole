package me.jameschan.hole.plugin;

import me.jameschan.config.Config;
import me.jameschan.hole.command.TokenIterator;
import me.jameschan.hole.common.Bundle;
import me.jameschan.hole.extend.HoleApp;
import me.jameschan.hole.extend.HoleAppBased;
import me.jameschan.hole.handler.Handler;
import me.jameschan.hole.handler.HandlerManager;

import java.util.Collection;

public class Plugin extends HoleAppBased implements
    ConfigurablePlugin,
    ExecutionAopFunctionality,
    PrintBundleFunctionality {

    /**
     * Constructs an HoleAppBased object.
     * @param app The application instance this object is based upon.
     */
    public Plugin(final HoleApp app) {
        super(app);
    }

    @Override
    public void init() {
        super.init();

        this.initHandlers();
    }

    @Override
    public Collection<String> configKeys() {
        return null;
    }

    @Override
    public void setConfig(Config config) {
    }

    @Override
    public void beforeExecute(final Bundle bundle, final TokenIterator tokenIterator) {
    }

    @Override
    public void beforePrint(final Bundle bundle) {
    }

//    /**
//     * Called when an entry is loaded.
//     * @param entry The entry to be loaded.
//     * @param data  data
//     */
//    public void onLoadEntry(Entry entry, Map<String, String> data) {
//    }
//
//    /**
//     * Called when an entry is being created.
//     * @param entry The entry is being created.
//     * @param data  data
//     */
//    public void onCreateEntry(Entry entry, Map<String, String> data) {
//    }
//
//    /**
//     * Called when an entry is being converted an entry object.
//     * @param entry The entry to be converted.
//     * @param data  data
//     */
//    public void onEntryToObject(Entry entry, Map<String, String> data) {
//    }
//
//    /**
//     * Called when an entry is being printed to the console.
//     * @param entry The entry is being printed.
//     * @param data  data
//     */
//    public void onPrintEntry(Entry entry, Map<String, String> data) {
//    }

    /**
     * A convenient method for derived classes to override and registers essential handlers in it.
     */
    protected void initHandlers() {
    }

    /**
     * Registers a handler.
     * @param name     The name of the handler.
     * @param handler  The handler to register.
     * @param override Whether to cover if the command already exists.
     */
    protected void registerHandler(final String name, final Handler handler, final boolean override) {
        app.use(HandlerManager.class).registerHandler(name, handler, override);
    }
}
