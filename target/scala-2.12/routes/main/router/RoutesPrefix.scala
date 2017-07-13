
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/daniel/Descargas/ncs-music/conf/routes
// @DATE:Wed Jul 12 22:26:40 COT 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
