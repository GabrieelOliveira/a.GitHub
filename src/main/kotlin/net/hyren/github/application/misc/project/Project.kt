package net.hyren.github.application.misc.project

import net.hyren.github.application.GitHubApplicationConstants
import net.hyren.github.application.exceptions.ProjectNotFoundException
import net.hyren.github.application.misc.build.Build
import java.io.File

/**
 * @author Gutyerrez
 */
abstract class Project(
	val name: String,
	val description: String? = null
) {

	lateinit var currentBuilder: Build

	val PROJECT_FOLDER = File(
		"${GitHubApplicationConstants.Folders.PROJECTS_DIRECTORY}/$name"
	)

	lateinit var framework: File
	lateinit var installation: File

	var isKotlinDSL = false

	@Throws(
		ProjectNotFoundException::class
	)
	fun isGradleProject(): Boolean {
		if (!PROJECT_FOLDER.exists())
			throw ProjectNotFoundException(
				"Can't find project $name at: \"${GitHubApplicationConstants.Folders.PROJECTS_DIRECTORY}/$name\""
			)

		val gradlew = File(PROJECT_FOLDER, "gradlew")

		if (gradlew.exists()) {
			framework = gradlew

			isKotlinDSL = File(PROJECT_FOLDER, "build.gradle.kts").exists()
			installation = File(PROJECT_FOLDER, "gradle/wrapper/gradle-wrapper.jar")
			return true
		}

		return false
	}

	@Throws(
		ProjectNotFoundException::class
	)
	fun isMavenProject(): Boolean {
		if (!PROJECT_FOLDER.exists())
			throw ProjectNotFoundException(
				"Can't find project $name at: \"${GitHubApplicationConstants.Folders.PROJECTS_DIRECTORY}/$name\""
			)

		val pom = File(PROJECT_FOLDER, "pom.xml")

		if (pom.exists()) {
			framework = pom
			return true
		}

		return false
	}

}