package net.hyren.github.application.frameworks.implementations

import net.hyren.github.application.GitHubApplication
import net.hyren.github.application.GitHubApplicationConstants
import net.hyren.github.application.frameworks.Framework
import net.hyren.github.application.misc.build.Build
import net.hyren.github.application.misc.project.Project
import org.gradle.tooling.GradleConnector
import java.io.File
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.attribute.PosixFilePermissions

/**
 * @author Gutyerrez
 */
class GradleFramework : Framework {

	override fun startBuild(
		project: Project
	) = apply {
		if (project.isGradleProject()) {
			val gradlew = project.framework
			val path = gradlew.toPath()

			val connector = GradleConnector.newConnector()

			connector.forProjectDirectory(project.PROJECT_FOLDER)

			if (System.getProperty("os") == "linux") {
				Files.setPosixFilePermissions(
					path,
					PosixFilePermissions.fromString("rwxrwxrwx")
				)
			}

			val buildGradleKts = if (project.isKotlinDSL) File(
				"${GitHubApplicationConstants.Folders.PROJECTS_DIRECTORY}/${project.name}/build.gradle.kts"
			) else File(
				"${GitHubApplicationConstants.Folders.PROJECTS_DIRECTORY}/${project.name}/build.gradle"
			)

			val data = InputStreamReader(buildGradleKts.inputStream()).readText()

			when {
				data.contains("com.github.johnrengelman.shadow") -> {
					val build = Build(
						GitHubApplication.BUILD_ID.getAndIncrement(),
						this,
						connector,
						"shadowJar"
					)

					build.start()
				}
				data.contains("org.springframework.boot") -> {
					val build = Build(
						GitHubApplication.BUILD_ID.getAndIncrement(),
						this,
						connector,
						"bootJar"
					)

					build.start()
				}
				else -> {
					val build = Build(
						GitHubApplication.BUILD_ID.getAndIncrement(),
						this,
						connector,
						"jar"
					)

					build.start()
				}
			}
		}
	}

}