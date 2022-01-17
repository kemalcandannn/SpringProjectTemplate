/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tr.bays.common.view;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

@SuppressWarnings("serial")
@Controller
//@SessionScoped
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GuestPreferences implements Serializable {
	private String color = "blue";
    
    private boolean dark = false;
        
    private String layoutMode = "horizontal";

	public void updateTheme(String color, boolean dark) {
		this.color = color;
        this.dark = dark;
	}
	
	public void updateTheme(String color) {
		this.color = color;
	}
    
    public String getTheme() {
        return this.color + "-" + (this.dark ? "dark" : "light");
    }
    
    public void changeScheme() {
        this.dark = !this.dark;
        this.updateTheme(this.color, this.dark);
    }
        
    public String getLayoutMode() {
        return this.layoutMode;
    }
    
    public void setLayoutMode(String value) {
        this.layoutMode = value;
    }
    
    public boolean isDark() {
        return this.dark;
    }
    
    public void setDark(boolean dark) {
        this.dark = dark;
    }
}
