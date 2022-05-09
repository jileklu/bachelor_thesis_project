package com.jileklu2.bakalarska_prace_app.gui.fileHandling;

import com.jileklu2.bakalarska_prace_app.gui.MainContext;
import com.jileklu2.bakalarska_prace_app.gui.routeHandling.RoutesContext;

public interface FileExportWindowContext {
    void setRoutesContext(RoutesContext routesContext);

    void setMainContext(MainContext mainContext);
}
