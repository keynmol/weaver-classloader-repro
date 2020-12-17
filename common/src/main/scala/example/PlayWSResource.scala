package example

import cats.effect.{IO, Resource}
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.stream.ActorMaterializer
import play.api.libs.ws.ahc.StandaloneAhcWSClient
import play.api.libs.ws.ahc.AhcWSClientConfig
import play.api.libs.ws.WSClientConfig

import scala.concurrent.duration._
import cats.effect.ContextShift

object PlayWSResource {
  def res(implicit cs: ContextShift[IO]): Resource[IO, StandaloneAhcWSClient] = {
    val aquire = IO {
      val classLoader = Thread.currentThread().getContextClassLoader()

      implicit val as =
        ActorSystem("global", ConfigFactory.empty(), classLoader)
      implicit val mat = ActorMaterializer()

      val playClient = StandaloneAhcWSClient(
        config = AhcWSClientConfig(
          WSClientConfig(
            connectionTimeout = 30.seconds,
            idleTimeout = 30.seconds,
            requestTimeout = 40.seconds
          )
        )
      )

      (as, mat, playClient)
    }

    val release =
      (as: ActorSystem, mat: ActorMaterializer, pl: StandaloneAhcWSClient) =>
        IO.fromFuture(IO(as.terminate())) *> IO(mat.shutdown()) *> IO(
          pl.close()
        )

    Resource.make(aquire)(release.tupled).map(_._3)
  }
}
