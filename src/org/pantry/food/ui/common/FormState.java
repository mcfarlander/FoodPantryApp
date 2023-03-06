/*
  Copyright 2011 Dave Johnson

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/
package org.pantry.food.ui.common;

import javax.swing.JInternalFrame;

/**
 * A class to keep track of the current forms (java frames) open in. If the user
 * select another form, the current form should close and this class should track
 * the new one.
 * 
 * Note: singleton pattern
 * 
 * @author mcfarland_davej
 */
public class FormState {

	/** The instance of this class in the singleton pattern. */
    private static FormState instance = null;

    /** Constructor shouldn't ever be used. */
    protected FormState() {
      // Exists only to defeat instantiation.
    }

    /**
     * Get the instance of FormState.
     * @return	FormState	the singleton instance
     */
    public static FormState getInstance() {
      if(instance == null) {
         instance = new FormState();
      }
      return instance;
    }

    private boolean m_isFormOpen = false;
    private JInternalFrame m_currentForm = null;
    private boolean showInactiveVisits = false;
    private boolean showAllYearVisits = false;
    private boolean showInactiveCustomers = false;
    private boolean showAll = false;
    private String clipboardEventName = "";

    public boolean isFormOpen(){ return this.m_isFormOpen; }
    
    public void setFormOpen(boolean isopen){ this.m_isFormOpen = isopen; }
    
    public void setFormOpen(boolean isopen, JInternalFrame openForm) {
        this.m_isFormOpen = isopen;
        this.m_currentForm = openForm;
    }


    public JInternalFrame getCurrentForm() { return m_currentForm; }
    public void setCurrentForm(JInternalFrame openForm) { this.m_currentForm = openForm; }

    public boolean isShowInactiveVisits() { return this.showInactiveVisits; }
    public void setShowInactiveVisits(boolean showInactive) { this.showInactiveVisits = showInactive; }

    public boolean isShowAllYearVisits() { return this.showAllYearVisits; }
    public void setShowAllYearVisits(boolean showAllVisits) { this.showAllYearVisits = showAllVisits; }

    public boolean isShowInactiveCustomers() { return this.showInactiveCustomers; }
    public void setShowInactiveCustomers(boolean showInactive) { this.showInactiveCustomers = showInactive; }
    
    public boolean isShowAll() { return this.showAll; }
    public void setShowAll(boolean showAllNow) { this.showAll = showAllNow; }
    
    public String getClipbardEventName() { return clipboardEventName; }
    public void setClipbardEventName(String eventName) { clipboardEventName = eventName; }

}// end of class
