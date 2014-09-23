package com.devbliss.doctest

import com.devbliss.doctest.task.DoctestTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

/**
 *
 * @author Dennis Schumann <dennis.schumann@devbliss.com>
 */
class DoctestPlugin implements Plugin<Project> {
	
  def void apply(Project project) {
    project.task('runDoctest', type: DoctestTask)
  }
  
}

