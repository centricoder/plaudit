package biz.netcentric.plaudit.service.system

trait ServiceName {

  def serviceName: String = {
    ServiceName.serviceName
  }

  def setServiceName(serviceName: String) {
    ServiceName.serviceName = serviceName
  }
}

object ServiceName {
  var serviceName: String = _
}