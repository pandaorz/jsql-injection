/*******************************************************************************
 * Copyhacked (H) 2012-2014.
 * This program and the accompanying materials
 * are made available under no term at all, use it like
 * you want, but share and discuss about it
 * every time possible with every body.
 * 
 * Contributors:
 *      ron190 at ymail dot com - initial implementation
 ******************************************************************************/
package com.jsql.view.swing.tree.model;

import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.SwingWorker;
import javax.swing.tree.TreePath;

import com.jsql.model.accessible.DataAccess;
import com.jsql.model.bean.database.Database;
import com.jsql.view.swing.HelperUi;

/**
 * Database model displaying the database icon on the label.
 */
public class NodeModelDatabase extends AbstractNodeModel {
    /**
     * Node as a database model.
     * @param database Element database coming from model
     */
    public NodeModelDatabase(Database database) {
        super(database);
    }

    @Override
    Icon getLeafIcon(boolean leaf) {
        if (leaf) {
            return HelperUi.DATABASE_ICON_GO;
        } else {
            return HelperUi.DATABASE_ICON;
        }
    }

    @Override
    public void runAction() {
        final Database selectedDatabase = (Database) this.dataObject;
        if (!this.isSearched && !this.isRunning) {
            new SwingWorker<Object, Object>(){
                @Override
                protected Object doInBackground() throws Exception {
                    DataAccess.listTables(selectedDatabase);
                    return null;
                }
            }.execute();
            this.isRunning = true;
        }
    }

    @Override 
    public boolean isPopupDisplayable() {
        return !this.isSearched && this.isRunning;
    }

    @Override 
    void displayMenu(JPopupMenu tablePopupMenu, TreePath path) {
        // Do nothing
    }
}
