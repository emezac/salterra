import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the RoomConnection entity.
 */
class RoomConnectionGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://localhost:8080"""

    val httpConf = http
        .baseUrl(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connectionHeader("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
        .silentResources // Silence all resources like css or css so they don't clutter the results

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authentication = Map(
        "Content-Type" -> """application/json""",
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "Authorization" -> "${access_token}"
    )

    val scn = scenario("Test the RoomConnection entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        ).exitHereIfFailed
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authenticate")
        .headers(headers_http_authentication)
        .body(StringBody("""{"username":"admin", "password":"admin"}""")).asJson
        .check(header("Authorization").saveAs("access_token"))).exitHereIfFailed
        .pause(2)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200)))
        .pause(10)
        .repeat(2) {
            exec(http("Get all roomConnections")
            .get("/api/room-connections")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new roomConnection")
            .post("/api/room-connections")
            .headers(headers_http_authenticated)
            .body(StringBody("""{
                "r1":"0"
                , "r2":"0"
                , "r3":"0"
                , "r4":"0"
                , "r5":"0"
                , "r6":"0"
                , "r7":"0"
                , "r8":"0"
                , "r9":"0"
                , "r10":"0"
                , "r11":"0"
                , "r12":"0"
                , "r13":"0"
                , "r14":"0"
                , "r15":"0"
                , "r16":"0"
                , "r17":"0"
                , "r18":"0"
                , "r19":"0"
                , "r20":"0"
                , "r21":"0"
                , "r22":"0"
                , "r23":"0"
                , "r24":"0"
                , "r25":"0"
                , "r26":"0"
                , "r27":"0"
                , "r28":"0"
                , "r29":"0"
                , "r30":"0"
                , "r31":"0"
                , "r32":"0"
                , "r33":"0"
                , "r34":"0"
                , "r35":"0"
                }""")).asJson
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_roomConnection_url"))).exitHereIfFailed
            .pause(10)
            .repeat(5) {
                exec(http("Get created roomConnection")
                .get("${new_roomConnection_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created roomConnection")
            .delete("${new_roomConnection_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(Integer.getInteger("users", 100)) during (Integer.getInteger("ramp", 1) minutes))
    ).protocols(httpConf)
}
