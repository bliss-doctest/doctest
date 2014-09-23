package com.devbliss.doctest.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 *
 * @author Dennis Schumann <dennis.schumann@devbliss.com>
 */
class DoctestTask extends DefaultTask {
	
  @TaskAction
  public void run() {
    println "Run Doctests"
  }
}

