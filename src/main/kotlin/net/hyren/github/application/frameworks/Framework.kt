package net.hyren.github.application.frameworks

import com.redefantasy.core.shared.CoreProvider
import com.redefantasy.core.shared.echo.packets.project.ProjectStartedBuildEchoPacket
import net.hyren.github.application.misc.project.Project

/**
 * @author Gutyerrez
 */
interface Framework {

	fun startBuild(
		project: Project
	): Framework

	fun onStart(
		buildId: Int
	) {
		val packet = ProjectStartedBuildEchoPacket(
			buildId,
			System.currentTimeMillis()
		)

		CoreProvider.Databases.Redis.ECHO.provide().publishToAll(packet)
	}

}