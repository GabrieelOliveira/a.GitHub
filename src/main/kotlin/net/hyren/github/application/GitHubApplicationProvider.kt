package net.hyren.github.application

import com.redefantasy.core.shared.CoreProvider
import com.redefantasy.core.shared.applications.data.Application

/**
 * @author Gutyerrez
 */
object GitHubApplicationProvider {

	fun prepare(
		application: Application
	) {
		CoreProvider.prepare(application)

		val echo = CoreProvider.Databases.Redis.ECHO.provide()

		echo.defaultSubscriber = echo.subscribe { _, runnable ->
			runnable.run()
		}
	}

}