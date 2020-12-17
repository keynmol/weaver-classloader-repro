package example

import cats.effect.{IO, Resource}
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.stream.ActorMaterializer
import play.api.libs.ws.ahc.StandaloneAhcWSClient
import play.api.libs.ws.ahc.AhcWSClientConfig
import play.api.libs.ws.WSClientConfig

import scala.concurrent.duration._

trait Base { self: weaver.IOSuite =>

  override type Res = StandaloneAhcWSClient
  override def sharedResource = PlayWSResource.res

  test("Hello") { res =>
    IO.fromFuture(IO(res.url("http://google.co.uk").get())).map { resp =>
      expect(resp.status == 200)
    }
  }

  test("Hello") { res =>
    IO.fromFuture(IO(res.url("http://google.co.uk").get())).map { resp =>
      expect(resp.status == 200)
    }
  }
  
  test("Hello") { res =>
    IO.fromFuture(IO(res.url("http://google.co.uk").get())).map { resp =>
      expect(resp.status == 200)
    }
  }
}


object Weaver_060_Suite1 extends weaver.IOSuite with Base

object Weaver_060_Suite2 extends weaver.IOSuite with Base
