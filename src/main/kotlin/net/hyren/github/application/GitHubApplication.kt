package net.hyren.github.application

import com.redefantasy.core.shared.applications.ApplicationType
import com.redefantasy.core.shared.applications.data.Application
import net.hyren.github.application.frameworks.implementations.GradleFramework
import net.hyren.github.application.misc.project.Project
import java.net.InetSocketAddress
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Gutyerrez
 */
object GitHubApplication {

	val BUILD_ID = AtomicInteger(1)

	@JvmStatic
	fun main(args: Array<String>) {
		GitHubProvider.prepare(
			Application(
				"github-application",
				"GitHub Application",
				null,
				InetSocketAddress(
					"127.0.0.1",
					0
				),
				ApplicationType.GENERIC,
				null,
				null
			)
		)

		val gradleFramework = GradleFramework()

		val project = object : Project("discord-bot") {
			//
		}

		gradleFramework.startBuild(project)
	}

}