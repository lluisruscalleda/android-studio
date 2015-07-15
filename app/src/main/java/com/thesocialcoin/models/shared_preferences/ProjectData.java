package com.thesocialcoin.models.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 15/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class ProjectData {

    /** The m preferences. */
    protected SharedPreferences mPreferences;

    /** The m editor. */
    protected SharedPreferences.Editor mEditor;

    /** The Constant SHARED_PREFERENCES_FILE. */
    private static final String SHARED_PREFERENCES_FILE = "thesocialcoin_project_data";

    /** The Constant PROJECT_DATA. */
    private static final String PROJECT_DATA = "project_data";

    /** The Constant INFO_PROJECT_DATA. */
    private static final String INFO_PROJECT_DATA = "info_project_data";

    /** The Constant PROJECT_MENU_ITEMS. */
    private static final String INFO_PROJECT_MENU_ITEMS = "project_menu_items";




    /**
     * Instantiates a new session data.
     *
     * @param context the context
     */
    public ProjectData(Context context) {
        mPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, 0);
    }

    /**
     * Gets the project data json string.
     *
     * @return the project data
     */
    public String getProjectData() {
        return mPreferences.getString(PROJECT_DATA, null);
    }

    /**
     * Sets the project data.
     *
     * @param projectData the new project data
     */
    public void setProjectData(String projectData) {
        getEditor().putString(PROJECT_DATA, projectData);
    }

    /**
     * Gets the project data json string.
     *
     * @return the info project data
     */
    public String getInfoProjectData() {
        return mPreferences.getString(INFO_PROJECT_DATA, null);
    }

    /**
     * Sets the project data.
     *
     * @param infoProjectData the new info project data
     */
    public void setInfoProjectData(String infoProjectData) {
        getEditor().putString(INFO_PROJECT_DATA, infoProjectData);
    }

    public String getInfoProjectMenuItems() {
        return mPreferences.getString(INFO_PROJECT_MENU_ITEMS, null);
    }
    public void setInfoProjectMenuItems(String infoProjectMenuItems) {
        getEditor().putString(INFO_PROJECT_MENU_ITEMS, infoProjectMenuItems);
    }


    public void clearEditor(){
        getEditor().clear();
        apply();
    }

    /**
     * Apply.
     */
    public void apply() {
        if (mEditor != null) {
            mEditor.apply();
            mEditor = null;
        }
    }

    /**
     * Gets the editor.
     *
     * @return the editor
     */
    protected SharedPreferences.Editor getEditor() {
        if (mEditor == null) {
            mEditor = mPreferences.edit();
        }

        return mEditor;
    }
}
