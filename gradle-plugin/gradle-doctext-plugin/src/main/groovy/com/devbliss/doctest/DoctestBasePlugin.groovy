package com.devbliss.doctest

import org.gradle.api.Plugin
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.plugins.JavaBasePlugin

/**
 *
 * @author Dennis Schumann <dennis.schumann@devbliss.com>
 */
class DoctestBasePlugin implements Plugin<ProjectInternal> {
  
  private ProjectInternal project;
	
  public void apply(ProjectInternal project) {
    this.project = project
    
    JavaBasePlugin javaBasePlugin = project.getPlugins().apply(JavaBasePlugin.class)
    
    configureSourceSetDefaults(javaBasePlugin)
  }
}

