package dev.example.eventsourcing

import javax.ws.rs.core.Response.Status._
import javax.ws.rs.core.UriBuilder
import javax.xml.bind.annotation._

import dev.example.eventsourcing.domain.DomainError

package object web {
  @XmlRootElement(name = "sys-error")
  @XmlAccessorType(XmlAccessType.FIELD)
  case class SysError(message: String) {
    def this() = this(null)
  }

  object SysError {
    val NotFound = SysError(NOT_FOUND.getReasonPhrase)
  }

  @XmlRootElement(name = "app-error")
  case class AppError(errors: DomainError) {
    import scala.collection.JavaConverters._

    def this() = this(null)

    @XmlElement
    def getMessage: java.util.List[String] = errors.asJava
  }

  val rootPath = "/dev/example/eventsourcing"
  
  def errorPath(templateName: String) = "%s/error/%s" format (rootPath, templateName)
  def homePath(templateName: String) = "%s/home/%s" format (rootPath, templateName)
  def webPath(templateName: String) = "%s/web/%s" format (rootPath, templateName)

  def uri(path: String) = UriBuilder.fromPath(path).build()
}