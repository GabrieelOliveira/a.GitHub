package net.hyren.github.application.frameworks.implementations

import net.hyren.github.application.frameworks.Framework
import net.hyren.github.application.misc.project.Project

/**
 * @author Gutyerrez
 */
class MavenFramework : Framework {

	override fun startBuild(
		project: Project
	) = apply {
		if (project.isMavenProject()) {
			val pom = project.framework


		}
	}

}