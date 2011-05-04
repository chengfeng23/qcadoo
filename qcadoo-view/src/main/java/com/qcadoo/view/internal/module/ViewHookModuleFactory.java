package com.qcadoo.view.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.internal.HookDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;
import com.qcadoo.view.internal.hooks.HookFactory;

public class ViewHookModuleFactory extends ModuleFactory<ViewHookModule> {

    @Autowired
    private HookFactory hookFactory;

    @Autowired
    private InternalViewDefinitionService viewDefinitionService;

    @Override
    public ViewHookModule parse(final String pluginIdentifier, final Element element) {
        String plugin = element.getAttributeValue("plugin");
        String view = element.getAttributeValue("view");
        String hookTypeStr = element.getAttributeValue("type");
        String className = element.getAttributeValue("class");
        String method = element.getAttributeValue("method");
        Preconditions.checkNotNull(plugin, "View hook extension error: plugin not defined");
        Preconditions.checkNotNull(view, "View hook extension error: view not defined");
        Preconditions.checkNotNull(hookTypeStr, "View hook extension error: hookType not defined");
        Preconditions.checkNotNull(className, "View hook extension error: class not defined");
        Preconditions.checkNotNull(method, "View hook extension error: method not defined");

        HookDefinition hook = hookFactory.getHook(className, method, pluginIdentifier);

        InternalViewDefinition.HookType hookType;
        if ("afterInitialize".equals(hookTypeStr)) {
            hookType = InternalViewDefinition.HookType.POST_INITIALIZE;
        } else if ("beforeInitalize".equals(hookTypeStr)) {
            hookType = InternalViewDefinition.HookType.PRE_INITIALIZE;
        } else if ("beforeRender".equals(hookTypeStr)) {
            hookType = InternalViewDefinition.HookType.PRE_RENDER;
        } else {
            throw new IllegalStateException("Unknow view extension hook type: " + hookTypeStr);
        }

        return new ViewHookModule(viewDefinitionService, plugin, view, hookType, hook);
    }

    @Override
    public String getIdentifier() {
        return "view-hook";
    }

}
