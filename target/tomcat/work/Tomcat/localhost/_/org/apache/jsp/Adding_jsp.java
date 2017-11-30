/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2017-11-30 12:13:49 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class Adding_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"en\">\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"utf-8\">\r\n");
      out.write("    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
      out.write("    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->\r\n");
      out.write("    <title>Edit or add</title>\r\n");
      out.write("\r\n");
      out.write("    <!-- Bootstrap -->\r\n");
      out.write("    <link href=\"css/bootstrap.css\" rel=\"stylesheet\">\r\n");
      out.write("    <link href=\"style.css\" rel=\"stylesheet\">\r\n");
      out.write("\r\n");
      out.write("    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->\r\n");
      out.write("    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->\r\n");
      out.write("    <!--[if lt IE 9]>\r\n");
      out.write("    <script src=\"https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.js\"></script>\r\n");
      out.write("    <script src=\"https://oss.maxcdn.com/respond/1.4.2/respond.js\"></script>\r\n");
      out.write("\r\n");
      out.write("    <![endif]-->\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<div class=\"container\">\r\n");
      out.write("    <div class=\"row justify-content-md-center\">\r\n");
      out.write("        <div class=\"col-2 col-md-2 auhtorozationText\">\r\n");
      out.write("            <p>Title of item: </p>\r\n");
      out.write("            <p>Description: </p>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"col-6 col-md-3\">\r\n");
      out.write("            <p><input maxlength=\"25\" size=\"35\" id=\"title\"></p>\r\n");
      out.write("            <p><textarea id=\"description\"></textarea></p>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"row justify-content-md-center\">\r\n");
      out.write("        <div class=\"col-2 col-md-2 addText\">\r\n");
      out.write("            <p>Start price: </p>\r\n");
      out.write("            <p>Time left: </p>\r\n");
      out.write("            <p>Step of bid: </p>\r\n");
      out.write("            <p>Buy it now: </p>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"col-6 col-md-3\">\r\n");
      out.write("            <p><input maxlength=\"25\" size=\"35\" id=\"startPrice\"></p>\r\n");
      out.write("            <p><input maxlength=\"25\" size=\"35\" id=\"timeLeft\"></p>\r\n");
      out.write("            <p><input maxlength=\"25\" size=\"35\" id=\"step\"></p>\r\n");
      out.write("            <input type=\"checkbox\" id=\"buyItNowCheckBox\">\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"row justify-content-md-center addBtns\">\r\n");
      out.write("        <div class=\"col-2 col-md-2\">\r\n");
      out.write("            <button id=\"publishBtn\" >Publish/Add</button>\r\n");
      out.write("            <button>Reset</button>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"col-2 col-md-2\">\r\n");
      out.write("        <button onClick=\"location.href='/general'\">Back</button>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <p id=\"response\"></p>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->\r\n");
      out.write("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.js\"></script>\r\n");
      out.write("<!-- Include all compiled plugins (below), or include individual files as needed -->\r\n");
      out.write("<script src=\"js/bootstrap.js\"></script>\r\n");
      out.write("<script src=\"js/EditAdding.js\"></script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
