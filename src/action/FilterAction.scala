package action

import javax.servlet.{Filter, FilterChain, FilterConfig, ServletRequest, ServletResponse}
import javax.servlet.http.{HttpServlet, HttpServletRequest => HSReq, HttpServletResponse => HSResp, Cookie}
import model.User
import workflow.FilterWorkflow

class FilterAction extends Filter {

    override def init(arg0: FilterConfig) {
    }

    override def doFilter(req: ServletRequest, resp: ServletResponse, chain: FilterChain) {
        val servletRequest = req.asInstanceOf[HSReq]
        val servletResponse = resp.asInstanceOf[HSResp]
        val cookies = servletRequest.getCookies()
        if (cookies != null) {
            cookies.foreach {
                case ck => {
                    if (ck.getName().equals("account")) {
                        val account = ck.getValue()
                        val user = User(account, null, null, null, 0)
                        val workflow = new FilterWorkflow().getUser(user)
                        if (user.email != null) {
                            servletResponse.sendRedirect("/contact-app/home/#/contacts")
                            return
                        }
                    }
                }
            }
        }
        servletResponse.sendRedirect("/contact-app/home/#/login")
    }

    override def destroy() {
    }

}