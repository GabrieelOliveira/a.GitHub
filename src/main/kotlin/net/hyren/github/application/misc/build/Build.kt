package net.hyren.github.application.misc.build

import com.redefantasy.core.shared.CoreProvider
import com.redefantasy.core.shared.echo.packets.project.ProjectFailedBuildEchoPacket
import com.redefantasy.core.shared.echo.packets.project.ProjectSuccessBuildEchoPacket
import net.hyren.github.application.frameworks.Framework
import net.hyren.github.application.frameworks.implementations.GradleFramework
import org.gradle.tooling.GradleConnectionException
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ResultHandler

/**
 * @author Gutyerrez
 */
class Build(
	val id: Int,
	val framework: Framework,
	vararg val parameters: Any?
) {

	fun start() {
		if (framework is GradleFramework) {
			val connector = parameters[0] as GradleConnector
			val tasks = parameters.copyOfRange(
				1,
				parameters.size
			).map { it.toString() }.toTypedArray()

			val projectConnection = connector.connect()
			val buildLauncher = projectConnection.newBuild()!!

			buildLauncher.forTasks(*tasks)
			buildLauncher.run(object : ResultHandler<Void> {

				val startTime = System.currentTimeMillis()

				init {
					framework.onStart(id)
				}

				override fun onComplete(
					result: Void?
				) {
					val packet = ProjectSuccessBuildEchoPacket(
						id,
						System.currentTimeMillis() - startTime
					)

					CoreProvider.Databases.Redis.ECHO.provide().publishToAll(packet)
				}

				override fun onFailure(
					failure: GradleConnectionException?
				) {
					val packet = ProjectFailedBuildEchoPacket(
						id,
						failure?.message
					)

					CoreProvider.Databases.Redis.ECHO.provide().publishToAll(packet)
				}

			})

			projectConnection.close()
		}
	}

}
