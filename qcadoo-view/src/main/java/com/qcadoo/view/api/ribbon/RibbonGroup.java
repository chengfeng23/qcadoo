/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo MES
 * Version: 0.3.0
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */

package com.qcadoo.view.api.ribbon;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents ribbon items group
 * 
 * @since 0.4.0
 */
public final class RibbonGroup {

    private final String name;

    private String extensionPluginIdentifier;

    private final List<RibbonActionItem> items = new LinkedList<RibbonActionItem>();;

    public RibbonGroup(final String name) {
        this.name = name;
    }

    /**
     * Get identifier of this ribbon group
     * 
     * @return identifier of this ribbon group
     */
    public String getName() {
        return name;
    }

    /**
     * Get items of this group
     * 
     * @return items of this group
     */
    public List<RibbonActionItem> getItems() {
        return items;
    }

    /**
     * Get item by name
     * 
     * @return item or null when no item witch such name
     */
    public RibbonActionItem getItemByName(String itemName) {
        for (RibbonActionItem item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Add item to this group
     * 
     * @param item
     *            item to add
     */
    public void addItem(final RibbonActionItem item) {
        items.add(item);
    }

    /**
     * Generates JSON representation of this ribbon group
     * 
     * @return JSON representation of this ribbon group
     * @throws JSONException
     */
    public JSONObject getAsJson() throws JSONException {
        JSONObject groupObject = new JSONObject();
        groupObject.put("name", name);
        JSONArray itemsArray = new JSONArray();
        for (RibbonActionItem item : items) {
            itemsArray.put(item.getAsJson());
        }
        groupObject.put("items", itemsArray);
        return groupObject;
    }

    /**
     * Gets copy of this group - internal usage only
     * 
     * @return copy of this group
     */
    public RibbonGroup getCopy() {
        RibbonGroup copy = new RibbonGroup(name);
        copy.setExtensionPluginIdentifier(extensionPluginIdentifier);
        for (RibbonActionItem item : items) {
            copy.addItem(item.getCopy());
        }
        return copy;
    }

    /**
     * Gets ribbon group with only updated items - internal usage only
     * 
     * @return ribon group with only updated fields
     */
    public RibbonGroup getUpdate() {
        RibbonGroup diff = new RibbonGroup(name);
        diff.setExtensionPluginIdentifier(extensionPluginIdentifier);
        boolean isDiffrence = false;
        for (RibbonActionItem item : items) {
            if (item.isShouldBeUpdated()) {
                diff.addItem(item);
                isDiffrence = true;
            }
        }
        if (isDiffrence) {
            return diff;
        }
        return null;
    }

    /**
     * Returns identifier of plugin that created this group or null when no such plugin identifier defined
     * 
     * @return identifier of plugin that created this group or null when no such plugin identifier defined
     */
    public String getExtensionPluginIdentifier() {
        return extensionPluginIdentifier;
    }

    /**
     * Sets identifier of plugin that created this group - internal usage only
     * 
     * @param extensionPluginIdentifier
     *            identifier of plugin that created this group
     */
    public void setExtensionPluginIdentifier(String extensionPluginIdentifier) {
        this.extensionPluginIdentifier = extensionPluginIdentifier;
    }
}
