import com.google.inject._
import com.google.gerrit.extensions.annotations._
import javax.servlet.http._

@Singleton
@Export("/")
class GerritSupportPlugin extends HttpServlet {

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) = {

    resp.getWriter print
      "[GerritSupportPlugin initial template] "+Scala.list.mkString("-");
  }
}
// this has been inserted to be sure we can access scala runtime from Gerrit
object Scala {
  val list = List("1","2","3");
}
