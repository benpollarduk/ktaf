module com.github.benpollarduk.ktaf.ktor {
    requires kotlin.stdlib;
    requires org.apache.logging.log4j;
    requires log4j.api.kotlin;
    requires com.github.benpollarduk.ktaf;
    requires io.ktor.server.host.common;
    requires io.ktor.server.core;
    requires io.ktor.server.netty;

    exports com.github.benpollarduk.ktaf.ktor;
}